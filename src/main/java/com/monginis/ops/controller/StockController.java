package com.monginis.ops.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.CategoryList;
import com.monginis.ops.model.CurrentStockResponse;
import com.monginis.ops.model.FrMenu;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetCurrentStockDetails;
import com.monginis.ops.model.Info;
import com.monginis.ops.model.Item;
import com.monginis.ops.model.MCategory;
import com.monginis.ops.model.PostFrItemStockDetail;
import com.monginis.ops.model.PostFrItemStockHeader;

@Controller
@Scope("session")
public class StockController {

	List<MCategory> mAllCategoryList = new ArrayList<MCategory>();

	CategoryList categoryList;

	ArrayList<FrMenu> menuList;

	List<Item> itemList;
	
	List<GetCurrentStockDetails> currentStockDetailList = new ArrayList<GetCurrentStockDetails>();
	
	Integer runningMonth = 0;
	
	PostFrItemStockHeader frItemStockHeader;
	String catId = null;
	
	@RequestMapping(value = "/showstockdetail")
	public ModelAndView showStockDetail(HttpServletRequest request, HttpServletResponse response) {
		
		
		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		
		ModelAndView model = new ModelAndView("stock/stockdetails");

		RestTemplate restTemplate = new RestTemplate();
	
		try {
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			
			List<PostFrItemStockHeader> list = restTemplate.postForObject(Constant.URL + "getCurrentMonthOfCatId", map,
					List.class);
			
			System.out.println("list " + list);

			frItemStockHeader = restTemplate.postForObject(Constant.URL + "getRunningMonth", map,
					PostFrItemStockHeader.class);
			
			System.out.println("Fr Opening Stock "+frItemStockHeader.toString());
			runningMonth = frItemStockHeader.getMonth();
			
			int monthNumber = runningMonth;
			String mon=Month.of(monthNumber).name();
			
			System.err.println("Month name "+mon);
			model.addObject("getMonthList", list);
			

		} catch (Exception e) {
			System.out.println("Exception in runningMonth" + e.getMessage());
			e.printStackTrace();

		}

		boolean isMonthCloseApplicable = false;

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Date date = new Date();
		System.out.println(dateFormat.format(date));

		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		Integer dayOfMonth = cal.get(Calendar.DATE);

		Integer calCurrentMonth = cal.get(Calendar.MONTH) + 1;
		System.out.println("Current Cal Month " + calCurrentMonth);

		System.out.println("Day Of Month is: " + dayOfMonth);

		if (dayOfMonth == Constant.dayOfMonthEnd && runningMonth != calCurrentMonth) {

			isMonthCloseApplicable = true;
			System.out.println("Day Of Month End ......" );

		}

		try {

			categoryList = restTemplate.getForObject(Constant.URL + "showAllCategory", CategoryList.class);

		} catch (Exception e) {
			System.out.println("Exception in getAllGategory" + e.getMessage());
			e.printStackTrace();

		}

		mAllCategoryList = categoryList.getmCategoryList();

		System.out.println(" All Category " + mAllCategoryList.toString());

		model.addObject("category", mAllCategoryList);
		model.addObject("isMonthCloseApplicable", isMonthCloseApplicable);

		return model;
	}

	// AJAX Call
	@RequestMapping(value = "/getStockDetails", method = RequestMethod.GET)
	public @ResponseBody CurrentStockResponse getMenuListByFr(HttpServletRequest request,
			HttpServletResponse response) {

		 catId = request.getParameter("cat_id");
		String showOption = request.getParameter("show_option");

		
		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		menuList = (ArrayList<FrMenu>) session.getAttribute("allMenuList");
		System.out.println("Menu List "+menuList.toString());

		int menuId = 0;

		/*if (catId.equalsIgnoreCase("1")) {

			menuId = 26;

		} else if (catId.equalsIgnoreCase("2")) {

			menuId = 31;

		} else if (catId.equalsIgnoreCase("3")) {

			menuId = 33;

		} else if (catId.equalsIgnoreCase("4")) {

			menuId = 34;

		} else if (catId.equalsIgnoreCase("6")) {//added to sp day 

			menuId =81;

		}

		String itemShow = "";

		for (int i = 0; i < menuList.size(); i++) {

			if (menuList.get(i).getMenuId() == menuId) {

				itemShow = menuList.get(i).getItemShow();

			}

		}*/
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		map.add("frId", frDetails.getFrId());
		RestTemplate restTemplate = new RestTemplate();

		ParameterizedTypeReference<List<PostFrItemStockHeader>> typeRef1 = new ParameterizedTypeReference<List<PostFrItemStockHeader>>() {
		};
		ResponseEntity<List<PostFrItemStockHeader>> responseEntity1 = restTemplate
				.exchange(Constant.URL + "getCurrentMonthOfCatId", HttpMethod.POST, new HttpEntity<>(map), typeRef1);
		List<PostFrItemStockHeader> list = responseEntity1.getBody();
		int intCatId = Integer.parseInt(catId);
		System.out.println("## catId" + intCatId);

		if (catId.equalsIgnoreCase("1")) {

			for (PostFrItemStockHeader header : list) {

				if (header.getCatId() == intCatId) {
					runningMonth = header.getMonth();
				}

			}

			menuId = 26;

		} else if (catId.equalsIgnoreCase("2")) {

			menuId = 31;
			for (PostFrItemStockHeader header : list) {


				if (header.getCatId() == intCatId) {
					runningMonth = header.getMonth();
				}

			}
		} else if (catId.equalsIgnoreCase("3")) {

			menuId = 33;
			for (PostFrItemStockHeader header : list) {


				if (header.getCatId() == intCatId) {
					runningMonth = header.getMonth();
				}

			}

		} else if (catId.equalsIgnoreCase("4")) {

			menuId = 34;
			for (PostFrItemStockHeader header : list) {


				if (header.getCatId() == intCatId) {
					runningMonth = header.getMonth();
				}

			}

		}else if (catId.equalsIgnoreCase("6")) {

			menuId = 49;
			for (PostFrItemStockHeader header : list) {


				if (header.getCatId() == intCatId) {
					runningMonth = header.getMonth();
				}

			}

		}
		System.err.println("Cat Id: " + catId + "running month " + runningMonth);

		String itemShow = "";

		for (int i = 0; i < menuList.size(); i++) {

			if (menuList.get(i).getMenuId() == menuId) {

				itemShow = menuList.get(i).getItemShow();

			}

		}

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat yearFormat = new SimpleDateFormat("yyyy");

		Date todaysDate = new Date();
		System.out.println(dateFormat.format(todaysDate));

		Calendar cal = Calendar.getInstance();
		cal.setTime(todaysDate);

		cal.set(Calendar.DAY_OF_MONTH, 1);

		Date firstDay = cal.getTime();

		System.out.println("First Day of month " + firstDay);

		String strFirstDay=dateFormat.format(firstDay);
		
		System.out.println("Year " + yearFormat.format(todaysDate));
		boolean isMonthCloseApplicable = false;

		if (showOption.equals("1")) {
			 map = new LinkedMultiValueMap<String, Object>();

			DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
			Date date = new Date();
			System.out.println(dateFormat1.format(date));

			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(date);

			int dayOfMonth = cal1.get(Calendar.DATE);

			int calCurrentMonth = cal1.get(Calendar.MONTH) + 1;
			System.out.println("Current Cal Month " + calCurrentMonth);

			System.out.println("Day Of Month is: " + dayOfMonth);

			/*if (dayOfMonth == Constant.dayOfMonthEnd && runningMonth != calCurrentMonth) {

				isMonthCloseApplicable = true;
				System.out.println("Day Of Month End ......" );

			}*/
			if (runningMonth < calCurrentMonth) {

				isMonthCloseApplicable = true;
				System.out.println("Day Of Month End ......");

			}else if (runningMonth==12 && calCurrentMonth==1) 
			{
				isMonthCloseApplicable = true;
			}
			
			if(isMonthCloseApplicable) {
				System.err.println("Inside iMonthclose app");
				String strDate;
				int year;
				if(runningMonth==12) {
					System.err.println("running month =12");
					 year=(Calendar.getInstance().getWeekYear()-1);
					 System.err.println("year value " +year);
				}
				else {
					System.err.println("running month not eq 12");
					year=Calendar.getInstance().getWeekYear();
					System.err.println("year value " +year);
				}
				
			//	strDate="01/"+runningMonth+"/"+year;
				
				strDate=year+"/"+runningMonth+"/01";
				
				map.add("fromDate", strDate);
			}
			else {
				
				map.add("fromDate", dateFormat.format(firstDay));
				
			}
			
			map.add("frId", frDetails.getFrId());
			map.add("frStockType", frDetails.getStockType());
			//map.add("fromDate", dateFormat1.format(firstDay));
			map.add("toDate", dateFormat.format(todaysDate));
			map.add("currentMonth", String.valueOf(runningMonth));
			map.add("year", yearFormat.format(todaysDate));
			map.add("catId",catId );
			map.add("itemIdList", itemShow);

			ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
			};
			ResponseEntity<List<GetCurrentStockDetails>> responseEntity = restTemplate
					.exchange(Constant.URL + "getCurrentStock", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			currentStockDetailList = responseEntity.getBody();
			System.out.println("Current Stock Details : " + currentStockDetailList.toString());
			

		} else {
			
			System.out.println("inside get stock between dates");

			
			String fromDate = request.getParameter("fromDate");

			String toDate = request.getParameter("toDate");
			
			
			
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
			String fr = null;
			String to = null;
			try {
				fr = sdf1.format(sdf2.parse(fromDate));

				to = sdf1.format(sdf2.parse(toDate));
			} catch (ParseException e) {
				
				e.printStackTrace();
			}
			System.out.println("FromDate "+fr);

			System.out.println("toDate "+to);
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", fr);
			map.add("toDate", to);
			map.add("itemIdList", itemShow);
			map.add("catId",catId );
			map.add("frStockType", frDetails.getStockType());
			
			try {
			ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
			};
			ResponseEntity<List<GetCurrentStockDetails>> responseEntity = restTemplate
					.exchange(Constant.URL + "/getStockBetweenDates", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			currentStockDetailList = responseEntity.getBody();
			System.out.println("Current Stock Details Monthwise : " + currentStockDetailList.toString());
			
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			
		} 
		CurrentStockResponse currentStockResponse=new CurrentStockResponse();
		currentStockResponse.setMonthClosed(isMonthCloseApplicable);
		currentStockResponse.setCurrentStockDetailList(currentStockDetailList);
		
		return currentStockResponse;
	}

	/*@RequestMapping(value = "/monthEndProcess", method = RequestMethod.POST)
	public String showCurrentMonthStock(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("in end month");
	
		PostFrItemStockHeader postFrItemStockHeader = new PostFrItemStockHeader();
		postFrItemStockHeader.setFrId(frItemStockHeader.getFrId());
		postFrItemStockHeader.setMonth(runningMonth);
		postFrItemStockHeader.setIsMonthClosed(1);
		postFrItemStockHeader.setCatId(frItemStockHeader.getCatId());
		postFrItemStockHeader.setOpeningStockHeaderId(frItemStockHeader.getOpeningStockHeaderId());
		postFrItemStockHeader.setYear(frItemStockHeader.getYear());

		List<PostFrItemStockDetail> stockDetailList = new ArrayList<PostFrItemStockDetail>();

		for (int i = 0; i < currentStockDetailList.size(); i++) {

			GetCurrentStockDetails stockDetails = currentStockDetailList.get(i);

			PostFrItemStockDetail postFrItemStockDetail = new PostFrItemStockDetail();

			String physicalStockQty = request.getParameter("physicalStockQty" + stockDetails.getItemId());
			// String stockDiff=request.getParameter("stockDiff"+stockDetails.getItemId());
			int intPhysicalStock = Integer.parseInt(physicalStockQty);

			postFrItemStockDetail.setItemId(String.valueOf(stockDetails.getId()));
			postFrItemStockDetail.setItemName(stockDetails.getItemName());
			postFrItemStockDetail.setRegOpeningStock(stockDetails.getRegOpeningStock());
			postFrItemStockDetail.setOpeningStockDetailId(stockDetails.getStockDetailId());
			postFrItemStockDetail.setOpeningStockHeaderId(stockDetails.getStockHeaderId());
			postFrItemStockDetail.setPhysicalStock(intPhysicalStock);
			postFrItemStockDetail.setRemark("");

			int intStockDiff = 0;

			int currentStock = (stockDetails.getCurrentRegStock() + stockDetails.getRegTotalPurchase())
					- (stockDetails.getRegTotalGrnGvn() + stockDetails.getRegTotalSell());

			if (currentStock > intPhysicalStock) {
				intStockDiff = currentStock - intPhysicalStock;
			} else {
				intStockDiff = intPhysicalStock - currentStock;
			}

			postFrItemStockDetail.setStockDifference(intStockDiff);
			postFrItemStockDetail.setRegTotalGrnGvn(stockDetails.getRegTotalGrnGvn());
			postFrItemStockDetail.setRegTotalPurchase(stockDetails.getRegTotalPurchase());
			postFrItemStockDetail.setRegTotalSell(stockDetails.getRegTotalSell());

			stockDetailList.add(postFrItemStockDetail);

		}

		postFrItemStockHeader.setPostFrItemStockDetailList(stockDetailList);

		System.out.println("Post Fr Op Stock  " + postFrItemStockHeader.toString());

		RestTemplate restTemplate = new RestTemplate();

		Info info = restTemplate.postForObject(Constant.URL + "updateEndMonth", postFrItemStockHeader, Info.class);

		System.out.println("Post Fr Op Stock response " + info.toString());

		return "redirect:/showstockdetail";

	}*/
	@RequestMapping(value = "/monthEndProcess", method = RequestMethod.POST)
	public String showCurrentMonthStock(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("in end month for cat Id " + catId);

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		int frId = frDetails.getFrId();
		System.err.println("Fr Id In stock Month End " + frId);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("frId", frId);
		map.add("catId", catId);

		RestTemplate restTemplate = new RestTemplate();

		PostFrItemStockHeader postFrItemStockHeader = restTemplate
				.postForObject(Constant.URL + "getCurrentMonthByCatIdFrId", map, PostFrItemStockHeader.class);

		System.out.println("prev stock header " + postFrItemStockHeader);

		// PostFrItemStockHeader postFrItemStockHeader = new PostFrItemStockHeader();
		// postFrItemStockHeader.setFrId(frItemStockHeader.getFrId());
		// postFrItemStockHeader.setMonth(runningMonth);
		// postFrItemStockHeader.setIsMonthClosed(1);
		// postFrItemStockHeader.setCatId(frItemStockHeader.getCatId());
		// postFrItemStockHeader.setOpeningStockHeaderId(frItemStockHeader.getOpeningStockHeaderId());
		// postFrItemStockHeader.setYear(frItemStockHeader.getYear());

		postFrItemStockHeader.setIsMonthClosed(1);

		List<PostFrItemStockDetail> stockDetailList = new ArrayList<PostFrItemStockDetail>();

		for (int i = 0; i < currentStockDetailList.size(); i++) {

			GetCurrentStockDetails stockDetails = currentStockDetailList.get(i);

			PostFrItemStockDetail postFrItemStockDetail = new PostFrItemStockDetail();

			String physicalStockQty = request.getParameter("physicalStockQty" + stockDetails.getItemId());
			// String stockDiff=request.getParameter("stockDiff"+stockDetails.getItemId());
			int intPhysicalStock = Integer.parseInt(physicalStockQty);

			postFrItemStockDetail.setItemId(String.valueOf(stockDetails.getId()));
			postFrItemStockDetail.setItemName(stockDetails.getItemName());
			postFrItemStockDetail.setRegOpeningStock(stockDetails.getRegOpeningStock());
			postFrItemStockDetail.setOpeningStockDetailId(stockDetails.getStockDetailId());
			postFrItemStockDetail.setOpeningStockHeaderId(stockDetails.getStockHeaderId());
			postFrItemStockDetail.setPhysicalStock(intPhysicalStock);
			postFrItemStockDetail.setRemark("");

			int intStockDiff = 0;

			int currentStock = (stockDetails.getCurrentRegStock() + stockDetails.getRegTotalPurchase())
					- (stockDetails.getRegTotalGrnGvn() + stockDetails.getRegTotalSell());

			if (currentStock > intPhysicalStock) {
				intStockDiff = currentStock - intPhysicalStock;
			} else {
				intStockDiff = intPhysicalStock - currentStock;
			}

			postFrItemStockDetail.setStockDifference(intStockDiff);
			postFrItemStockDetail.setRegTotalGrnGvn(stockDetails.getRegTotalGrnGvn());
			postFrItemStockDetail.setRegTotalPurchase(stockDetails.getRegTotalPurchase());
			postFrItemStockDetail.setRegTotalSell(stockDetails.getRegTotalSell());

			stockDetailList.add(postFrItemStockDetail);

		}

		postFrItemStockHeader.setPostFrItemStockDetailList(stockDetailList);

		System.out.println("#### Month End Data - Post Fr Op Stock  " + postFrItemStockHeader.toString() + " ### ");

		Info info = restTemplate.postForObject(Constant.URL + "updateEndMonth", postFrItemStockHeader, Info.class);

		System.out.println("Post Fr Op Stock response " + info.toString());

		return "redirect:/showstockdetail";

	}
	
}