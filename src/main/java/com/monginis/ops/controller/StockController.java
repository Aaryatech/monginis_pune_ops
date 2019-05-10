package com.monginis.ops.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
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
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.CategoryList;
import com.monginis.ops.model.CurrentStockResponse;
import com.monginis.ops.model.ExportToExcel;
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
	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

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

			System.out.println("Fr Opening Stock " + frItemStockHeader.toString());
			runningMonth = frItemStockHeader.getMonth();

			int monthNumber = runningMonth;
			String mon = Month.of(monthNumber).name();

			System.err.println("Month name " + mon);
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
			System.out.println("Day Of Month End ......");

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
		int selectRate = Integer.parseInt(request.getParameter("selectRate"));
		String showOption = request.getParameter("show_option");

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		menuList = (ArrayList<FrMenu>) session.getAttribute("allMenuList");
		System.out.println("Menu List " + menuList.toString());

		int menuId = 0;

		/*
		 * if (catId.equalsIgnoreCase("1")) {
		 * 
		 * menuId = 26;
		 * 
		 * } else if (catId.equalsIgnoreCase("2")) {
		 * 
		 * menuId = 31;
		 * 
		 * } else if (catId.equalsIgnoreCase("3")) {
		 * 
		 * menuId = 33;
		 * 
		 * } else if (catId.equalsIgnoreCase("4")) {
		 * 
		 * menuId = 34;
		 * 
		 * } else if (catId.equalsIgnoreCase("6")) {//added to sp day
		 * 
		 * menuId =81;
		 * 
		 * }
		 * 
		 * String itemShow = "";
		 * 
		 * for (int i = 0; i < menuList.size(); i++) {
		 * 
		 * if (menuList.get(i).getMenuId() == menuId) {
		 * 
		 * itemShow = menuList.get(i).getItemShow();
		 * 
		 * }
		 * 
		 * }
		 */
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

		} else if (catId.equalsIgnoreCase("6")) {

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

		String strFirstDay = dateFormat.format(firstDay);

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

			/*
			 * if (dayOfMonth == Constant.dayOfMonthEnd && runningMonth != calCurrentMonth)
			 * {
			 * 
			 * isMonthCloseApplicable = true; System.out.println("Day Of Month End ......"
			 * );
			 * 
			 * }
			 */
			if (runningMonth < calCurrentMonth) {

				isMonthCloseApplicable = true;
				System.out.println("Day Of Month End ......");

			} else if (runningMonth == 12 && calCurrentMonth == 1) {
				isMonthCloseApplicable = true;
			}

			if (isMonthCloseApplicable) {
				System.err.println("Inside iMonthclose app");
				String strDate;
				int year;
				if (runningMonth == 12) {
					System.err.println("running month =12");
					year = (Calendar.getInstance().getWeekYear() - 1);
					System.err.println("year value " + year);
				} else {
					System.err.println("running month not eq 12");
					year = Calendar.getInstance().getWeekYear();
					System.err.println("year value " + year);
				}

				// strDate="01/"+runningMonth+"/"+year;

				strDate = year + "/" + runningMonth + "/01";

				map.add("fromDate", strDate);
			} else {

				map.add("fromDate", dateFormat.format(firstDay));

			}

			map.add("frId", frDetails.getFrId());
			map.add("frStockType", frDetails.getStockType());
			// map.add("fromDate", dateFormat1.format(firstDay));
			map.add("toDate", dateFormat.format(todaysDate));
			map.add("currentMonth", String.valueOf(runningMonth));
			map.add("year", yearFormat.format(todaysDate));
			map.add("catId", catId);
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
			System.out.println("FromDate " + fr);

			System.out.println("toDate " + to);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", fr);
			map.add("toDate", to);
			map.add("itemIdList", itemShow);
			map.add("catId", catId);
			map.add("frStockType", frDetails.getStockType());

			try {
				ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
				};
				ResponseEntity<List<GetCurrentStockDetails>> responseEntity = restTemplate.exchange(
						Constant.URL + "/getStockBetweenDates", HttpMethod.POST, new HttpEntity<>(map), typeRef);

				currentStockDetailList = responseEntity.getBody();
				System.out.println("Current Stock Details Monthwise : " + currentStockDetailList.toString());

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		CurrentStockResponse currentStockResponse = new CurrentStockResponse();
		currentStockResponse.setMonthClosed(isMonthCloseApplicable);
		currentStockResponse.setCurrentStockDetailList(currentStockDetailList);
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr No");
		rowData.add("Item Id");
		rowData.add("Item Name");
		rowData.add("Rate/MRP");
		rowData.add("Op Stock Qty");
		rowData.add("Op Stock Value");
		rowData.add("Pur Qty");
		rowData.add("Pur Value");
		rowData.add("Grn/Gvn Qty");
		rowData.add("Grn/Gvn Value");
		rowData.add("Regular Sale");
		rowData.add("Regular Sale Value");
		rowData.add("Cur Stock");
		rowData.add("Cur Stock Value");
		expoExcel.setRowData(rowData);

		exportToExcelList.add(expoExcel);

		double totalOpStock = 0;
		double totalOpStockValue = 0;
		double pureQtyTotal = 0;
		double pureTotalValue = 0;
		double grnGvnTotal = 0;
		double grnGvnTotalValue = 0;
		double regularSaleTotal = 0;
		double regularSaleTotalValue = 0;
		double reorderTotalQty = 0;
		double totalCurrentStock = 0;
		double totalCurrentStockValue = 0;

		for (int i = 0; i < currentStockDetailList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("" + (i + 1));
			rowData.add("" + currentStockDetailList.get(i).getItemId());
			rowData.add("" + currentStockDetailList.get(i).getItemName());
			if (selectRate == 1) {

				totalOpStock = totalOpStock + currentStockDetailList.get(i).getRegOpeningStock();

				totalOpStockValue = totalOpStockValue + (currentStockDetailList.get(i).getSpOpeningStock()
						* currentStockDetailList.get(i).getRegOpeningStock());
				pureQtyTotal = pureQtyTotal + currentStockDetailList.get(i).getRegTotalPurchase();
				pureTotalValue = pureTotalValue + (currentStockDetailList.get(i).getSpOpeningStock()
						* currentStockDetailList.get(i).getRegTotalPurchase());

				grnGvnTotal = grnGvnTotal + currentStockDetailList.get(i).getRegTotalGrnGvn();

				grnGvnTotalValue = grnGvnTotalValue + (currentStockDetailList.get(i).getSpOpeningStock()
						* currentStockDetailList.get(i).getRegTotalGrnGvn());

				regularSaleTotal = regularSaleTotal + currentStockDetailList.get(i).getRegTotalSell();
				reorderTotalQty = reorderTotalQty + currentStockDetailList.get(i).getReOrderQty();
				regularSaleTotalValue = regularSaleTotalValue + (currentStockDetailList.get(i).getSpOpeningStock()
						* currentStockDetailList.get(i).getRegTotalSell());
				totalCurrentStock = totalCurrentStock + currentStockDetailList.get(i).getCurrentRegStock();
				totalCurrentStockValue = totalCurrentStockValue + (currentStockDetailList.get(i).getSpOpeningStock()
						* currentStockDetailList.get(i).getCurrentRegStock());

				rowData.add("" + currentStockDetailList.get(i).getSpOpeningStock());

				rowData.add("" + currentStockDetailList.get(i).getRegOpeningStock());
				rowData.add("" + (currentStockDetailList.get(i).getSpOpeningStock()
						* currentStockDetailList.get(i).getRegOpeningStock()));

				rowData.add("" + currentStockDetailList.get(i).getRegTotalPurchase());
				rowData.add("" + (currentStockDetailList.get(i).getSpOpeningStock()
						* currentStockDetailList.get(i).getRegTotalPurchase()));

				rowData.add("" + currentStockDetailList.get(i).getRegTotalGrnGvn());
				rowData.add("" + (currentStockDetailList.get(i).getSpOpeningStock()
						* currentStockDetailList.get(i).getRegTotalGrnGvn()));

				rowData.add("" + currentStockDetailList.get(i).getRegTotalSell());
				rowData.add("" + (currentStockDetailList.get(i).getSpOpeningStock()
						* currentStockDetailList.get(i).getRegTotalSell()));

				/*
				 * rowData.add("" + currentStockDetailList.get(i).getReOrderQty());
				 * 
				 * rowData.add("" + currentStockDetailList.get(i).getRegTotalSell());
				 * rowData.add("" + (currentStockDetailList.get(i).getSpOpeningStock()
				 * currentStockDetailList.get(i).getRegTotalSell()));
				 */
				rowData.add("" + currentStockDetailList.get(i).getCurrentRegStock());
				rowData.add("" + (currentStockDetailList.get(i).getSpOpeningStock()
						* currentStockDetailList.get(i).getCurrentRegStock()));

			} else {

				totalOpStock = totalOpStock + currentStockDetailList.get(i).getRegOpeningStock();
				totalOpStockValue = totalOpStockValue + (currentStockDetailList.get(i).getSpTotalPurchase()
						* currentStockDetailList.get(i).getRegOpeningStock());
				pureQtyTotal = pureQtyTotal + currentStockDetailList.get(i).getRegTotalPurchase();
				pureTotalValue = pureTotalValue + (currentStockDetailList.get(i).getSpTotalPurchase()
						* currentStockDetailList.get(i).getRegTotalPurchase());
				grnGvnTotal = grnGvnTotal + currentStockDetailList.get(i).getRegTotalGrnGvn();
				grnGvnTotalValue = grnGvnTotalValue + (currentStockDetailList.get(i).getSpTotalPurchase()
						* currentStockDetailList.get(i).getRegTotalGrnGvn());
				regularSaleTotal = regularSaleTotal + currentStockDetailList.get(i).getRegTotalSell();
				regularSaleTotalValue = regularSaleTotalValue + (currentStockDetailList.get(i).getSpTotalPurchase()
						* currentStockDetailList.get(i).getRegTotalSell());
				reorderTotalQty = reorderTotalQty + currentStockDetailList.get(i).getReOrderQty();
				totalCurrentStock = totalCurrentStock + currentStockDetailList.get(i).getCurrentRegStock();

				totalCurrentStockValue = totalCurrentStockValue + (currentStockDetailList.get(i).getSpTotalPurchase()
						* currentStockDetailList.get(i).getCurrentRegStock());

				rowData.add("" + currentStockDetailList.get(i).getSpTotalPurchase());

				rowData.add("" + currentStockDetailList.get(i).getRegOpeningStock());
				rowData.add("" + (currentStockDetailList.get(i).getRegOpeningStock()
						* currentStockDetailList.get(i).getSpTotalPurchase()));

				rowData.add("" + currentStockDetailList.get(i).getRegTotalPurchase());
				rowData.add("" + (currentStockDetailList.get(i).getSpTotalPurchase()
						* currentStockDetailList.get(i).getRegTotalPurchase()));

				rowData.add("" + currentStockDetailList.get(i).getRegTotalGrnGvn());
				rowData.add("" + (currentStockDetailList.get(i).getSpTotalPurchase()
						* currentStockDetailList.get(i).getRegTotalGrnGvn()));

				rowData.add("" + currentStockDetailList.get(i).getRegTotalSell());
				rowData.add("" + (currentStockDetailList.get(i).getSpTotalPurchase()
						* currentStockDetailList.get(i).getRegTotalSell()));
				/*
				 * rowData.add("" + currentStockDetailList.get(i).getReOrderQty());
				 * 
				 * rowData.add("" + currentStockDetailList.get(i).getRegTotalSell());
				 * rowData.add("" + (currentStockDetailList.get(i).getSpTotalPurchase()
				 * currentStockDetailList.get(i).getRegTotalSell()));
				 */

				rowData.add("" + currentStockDetailList.get(i).getCurrentRegStock());
				rowData.add("" + (currentStockDetailList.get(i).getSpTotalPurchase()
						* currentStockDetailList.get(i).getCurrentRegStock()));
			}
			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();

		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("Total");
		rowData.add(" " + roundUp((float) totalOpStock));
		rowData.add(" " + roundUp((float) totalOpStockValue));
		rowData.add(" " + roundUp((float) pureQtyTotal));
		rowData.add(" " + roundUp((float) pureTotalValue));
		rowData.add(" " + roundUp((float) grnGvnTotal));
		rowData.add(" " + roundUp((float) grnGvnTotalValue));
		rowData.add(" " + roundUp((float) regularSaleTotal));
		rowData.add(" " + roundUp((float) regularSaleTotalValue));

		rowData.add(" " + roundUp((float) totalCurrentStock));
		rowData.add(" " + roundUp((float) totalCurrentStockValue));

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Stock Details");
		return currentStockResponse;
	}

	/*
	 * @RequestMapping(value = "/monthEndProcess", method = RequestMethod.POST)
	 * public String showCurrentMonthStock(HttpServletRequest request,
	 * HttpServletResponse response) { System.out.println("in end month");
	 * 
	 * PostFrItemStockHeader postFrItemStockHeader = new PostFrItemStockHeader();
	 * postFrItemStockHeader.setFrId(frItemStockHeader.getFrId());
	 * postFrItemStockHeader.setMonth(runningMonth);
	 * postFrItemStockHeader.setIsMonthClosed(1);
	 * postFrItemStockHeader.setCatId(frItemStockHeader.getCatId());
	 * postFrItemStockHeader.setOpeningStockHeaderId(frItemStockHeader.
	 * getOpeningStockHeaderId());
	 * postFrItemStockHeader.setYear(frItemStockHeader.getYear());
	 * 
	 * List<PostFrItemStockDetail> stockDetailList = new
	 * ArrayList<PostFrItemStockDetail>();
	 * 
	 * for (int i = 0; i < currentStockDetailList.size(); i++) {
	 * 
	 * GetCurrentStockDetails stockDetails = currentStockDetailList.get(i);
	 * 
	 * PostFrItemStockDetail postFrItemStockDetail = new PostFrItemStockDetail();
	 * 
	 * String physicalStockQty = request.getParameter("physicalStockQty" +
	 * stockDetails.getItemId()); // String
	 * stockDiff=request.getParameter("stockDiff"+stockDetails.getItemId()); int
	 * intPhysicalStock = Integer.parseInt(physicalStockQty);
	 * 
	 * postFrItemStockDetail.setItemId(String.valueOf(stockDetails.getId()));
	 * postFrItemStockDetail.setItemName(stockDetails.getItemName());
	 * postFrItemStockDetail.setRegOpeningStock(stockDetails.getRegOpeningStock());
	 * postFrItemStockDetail.setOpeningStockDetailId(stockDetails.getStockDetailId()
	 * );
	 * postFrItemStockDetail.setOpeningStockHeaderId(stockDetails.getStockHeaderId()
	 * ); postFrItemStockDetail.setPhysicalStock(intPhysicalStock);
	 * postFrItemStockDetail.setRemark("");
	 * 
	 * int intStockDiff = 0;
	 * 
	 * int currentStock = (stockDetails.getCurrentRegStock() +
	 * stockDetails.getRegTotalPurchase()) - (stockDetails.getRegTotalGrnGvn() +
	 * stockDetails.getRegTotalSell());
	 * 
	 * if (currentStock > intPhysicalStock) { intStockDiff = currentStock -
	 * intPhysicalStock; } else { intStockDiff = intPhysicalStock - currentStock; }
	 * 
	 * postFrItemStockDetail.setStockDifference(intStockDiff);
	 * postFrItemStockDetail.setRegTotalGrnGvn(stockDetails.getRegTotalGrnGvn());
	 * postFrItemStockDetail.setRegTotalPurchase(stockDetails.getRegTotalPurchase())
	 * ; postFrItemStockDetail.setRegTotalSell(stockDetails.getRegTotalSell());
	 * 
	 * stockDetailList.add(postFrItemStockDetail);
	 * 
	 * }
	 * 
	 * postFrItemStockHeader.setPostFrItemStockDetailList(stockDetailList);
	 * 
	 * System.out.println("Post Fr Op Stock  " + postFrItemStockHeader.toString());
	 * 
	 * RestTemplate restTemplate = new RestTemplate();
	 * 
	 * Info info = restTemplate.postForObject(Constant.URL + "updateEndMonth",
	 * postFrItemStockHeader, Info.class);
	 * 
	 * System.out.println("Post Fr Op Stock response " + info.toString());
	 * 
	 * return "redirect:/showstockdetail";
	 * 
	 * }
	 */
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

	@RequestMapping(value = "/showStockDetailsPdf/{selectRate}", method = RequestMethod.GET)
	public void showStockDetailsPdf(@PathVariable("selectRate") int selectRate, HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException {
		BufferedOutputStream outStream = null;
		System.out.println("Inside Pdf showPOReportPdf");
		Document document = new Document(PageSize.A4);

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("time in Gen Bill PDF ==" + dateFormat.format(cal.getTime()));
		String FILE_PATH = Constant.REPORT_SAVE;
		File file = new File(FILE_PATH);
		String type = null;

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}
		document.setPageSize(PageSize.A4.rotate());

		PdfPTable table = new PdfPTable(14);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setHeaderRows(1);
			table.setWidths(
					new float[] { 1.5f, 5.5f, 2.0f, 2.0f, 3.2f, 2.0f, 3.2f, 2.0f, 3.2f, 2.0f, 3.2f, 2.0f, 2.0f, 3.2f });
			Font headFont = new Font(FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
			headFont1.setColor(BaseColor.WHITE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell = new PdfPCell();
			hcell.setBackgroundColor(BaseColor.PINK);

			hcell.setPadding(3);
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name ", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			if (selectRate == 1) {
				hcell = new PdfPCell(new Phrase("Rate", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);

				table.addCell(hcell);
			} else {
				hcell = new PdfPCell(new Phrase("MRP", headFont1));
				hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
				hcell.setBackgroundColor(BaseColor.PINK);

				table.addCell(hcell);
			}

			hcell = new PdfPCell(new Phrase("Op Stock Qty", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);
			hcell = new PdfPCell(new Phrase("Op Stock Value", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Pur Qty", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Pur Value", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Grn/Gvn Qty", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Grn/Gvn Value", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Reg sale", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Reg sale Value", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Reorder Qty", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Cur Stock", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Cur Stock Value", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);

			table.addCell(hcell);

			int index = 0;
			double totalOpStock = 0;
			double totalOpStockValue = 0;
			double pureQtyTotal = 0;
			double pureTotalValue = 0;
			double grnGvnTotal = 0;
			double grnGvnTotalValue = 0;
			double regularSaleTotal = 0;
			double regularSaleTotalValue = 0;
			double reorderTotalQty = 0;
			double totalCurrentStock = 0;
			double totalCurrentStockValue = 0;
			for (GetCurrentStockDetails work : currentStockDetailList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPadding(3);
				cell.setPaddingRight(2);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + work.getItemName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(2);
				cell.setPadding(3);
				table.addCell(cell);

				if (selectRate == 1) {
					type = "Rate";

					cell = new PdfPCell(new Phrase("" + work.getSpOpeningStock(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					totalOpStock = totalOpStock + work.getRegOpeningStock();
					totalOpStockValue = totalOpStockValue + (work.getSpOpeningStock() * work.getRegOpeningStock());
					pureQtyTotal = pureQtyTotal + work.getRegTotalPurchase();
					pureTotalValue = pureTotalValue + (work.getSpOpeningStock() * work.getRegTotalPurchase());

					grnGvnTotal = grnGvnTotal + work.getRegTotalGrnGvn();
					grnGvnTotalValue = grnGvnTotalValue + (work.getSpOpeningStock() * work.getRegTotalGrnGvn());
					regularSaleTotal = regularSaleTotal + work.getRegTotalSell();
					reorderTotalQty = reorderTotalQty + work.getReOrderQty();
					regularSaleTotalValue = regularSaleTotalValue + (work.getSpOpeningStock() * work.getRegTotalSell());
					totalCurrentStock = totalCurrentStock + work.getCurrentRegStock();
					totalCurrentStockValue = totalCurrentStockValue
							+ (work.getSpOpeningStock() * work.getCurrentRegStock());

					cell = new PdfPCell(new Phrase("" + work.getRegOpeningStock(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(
							"" + (roundUp((float) (work.getSpOpeningStock() * work.getRegOpeningStock()))), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getRegTotalPurchase(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(
							"" + (roundUp((float) (work.getSpOpeningStock() * work.getRegTotalPurchase()))), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getRegTotalGrnGvn(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(
							"" + (roundUp((float) (work.getSpOpeningStock() * work.getRegTotalGrnGvn()))), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getRegTotalSell(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(
							"" + (roundUp((float) (work.getSpOpeningStock() * work.getRegTotalSell()))), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getReOrderQty(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getCurrentRegStock(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase(
							"" + (roundUp((float) (work.getSpOpeningStock() * work.getCurrentRegStock()))), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);
				} else {
					type = "MRP";

					cell = new PdfPCell(new Phrase("" + work.getSpTotalPurchase(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					totalOpStock = totalOpStock + work.getRegOpeningStock();
					totalOpStockValue = totalOpStockValue + (work.getSpTotalPurchase() * work.getRegOpeningStock());
					pureQtyTotal = pureQtyTotal + work.getRegTotalPurchase();
					pureTotalValue = pureTotalValue + (work.getSpTotalPurchase() * work.getRegTotalPurchase());
					grnGvnTotal = grnGvnTotal + work.getRegTotalGrnGvn();

					grnGvnTotalValue = grnGvnTotalValue + (work.getSpTotalPurchase() * work.getRegTotalGrnGvn());
					regularSaleTotal = regularSaleTotal + work.getRegTotalSell();
					regularSaleTotalValue = regularSaleTotalValue
							+ (work.getSpTotalPurchase() * work.getRegTotalSell());
					reorderTotalQty = reorderTotalQty + work.getReOrderQty();
					totalCurrentStock = totalCurrentStock + work.getCurrentRegStock();

					totalCurrentStockValue = totalCurrentStockValue
							+ (work.getSpOpeningStock() * work.getCurrentRegStock());

					cell = new PdfPCell(new Phrase("" + work.getRegOpeningStock(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase("" + (work.getSpTotalPurchase() * work.getRegOpeningStock()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getRegTotalPurchase(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase("" + (work.getSpTotalPurchase() * work.getRegTotalPurchase()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getRegTotalGrnGvn(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase("" + (work.getSpTotalPurchase() * work.getRegTotalGrnGvn()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getRegTotalSell(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase("" + (work.getSpTotalPurchase() * work.getRegTotalSell()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getReOrderQty(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(new Phrase("" + work.getCurrentRegStock(), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

					cell = new PdfPCell(
							new Phrase("" + (work.getSpTotalPurchase() * work.getCurrentRegStock()), headFont));
					cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
					cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell.setPaddingRight(2);
					cell.setPadding(3);
					table.addCell(cell);

				}

			}
			PdfPCell cell;

			cell = new PdfPCell(new Phrase("", headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("", headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Total", headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) totalOpStock), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + totalOpStockValue, headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) pureQtyTotal), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) pureTotalValue), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) grnGvnTotal), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) grnGvnTotalValue), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) regularSaleTotal), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) regularSaleTotalValue), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) reorderTotalQty), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) totalCurrentStock), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) totalCurrentStockValue), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			document.open();
			Paragraph name = new Paragraph("Moniginis\n", f);
			name.setAlignment(Element.ALIGN_CENTER);
			document.add(name);
			document.add(new Paragraph(" "));
			Paragraph company = new Paragraph("Stock Details Report" + " " + type + "\n", f);
			company.setAlignment(Element.ALIGN_CENTER);
			document.add(company);
			document.add(new Paragraph(" "));

			document.add(table);

			int totalPages = writer.getPageNumber();

			System.out.println("Page no " + totalPages);

			document.close();

			if (file != null) {

				String mimeType = URLConnection.guessContentTypeFromName(file.getName());

				if (mimeType == null) {

					mimeType = "application/pdf";

				}

				response.setContentType(mimeType);

				response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

				response.setContentLength((int) file.length());

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}
			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error: " + ex.getMessage());

			ex.printStackTrace();

		}

	}
}