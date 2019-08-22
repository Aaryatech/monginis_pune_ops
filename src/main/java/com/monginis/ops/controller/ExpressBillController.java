package com.monginis.ops.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.monginis.ops.billing.SellBillDataCommon;
import com.monginis.ops.billing.SellBillDetail;
import com.monginis.ops.billing.SellBillHeader;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.AllMenuResponse;
import com.monginis.ops.model.CategoryList;
import com.monginis.ops.model.CustomerBillItem;
import com.monginis.ops.model.FrItemStockConfigureList;
import com.monginis.ops.model.FrMenu;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetCurrentStockDetails;
import com.monginis.ops.model.GetCustBillTax;
import com.monginis.ops.model.GetCustmoreBillResponse;
import com.monginis.ops.model.GetItemHsnCode;
import com.monginis.ops.model.Info;
import com.monginis.ops.model.Item;
import com.monginis.ops.model.ItemResponse;
import com.monginis.ops.model.MCategory;
import com.monginis.ops.model.Menus;
import com.monginis.ops.model.PostFrItemStockHeader;
import com.monginis.ops.model.SellBillDetailList;
import com.monginis.ops.model.frsetting.FrSetting;
import com.sun.org.apache.regexp.internal.RE;

@Controller
@Scope("session")
public class ExpressBillController {

	public List<GetCurrentStockDetails> currentStockDetailList = new ArrayList<GetCurrentStockDetails>();
	public List<CustomerBillItem> customerBillItemList = new ArrayList<CustomerBillItem>();
	SellBillHeader sellBillHeaderGlobal = new SellBillHeader();
	SellBillDetail sellBillDetailRes;
	List<SellBillDetail> selectedSellBillDetailList;
	List<SellBillDetail> BillDetailList = new ArrayList<SellBillDetail>();
	List<GetCustBillTax> getCustBillTaxList;
	String sellInvoiceGlobal="";
	
	int globalFrId=0;
	PostFrItemStockHeader frItemStockHeader;
	int runningMonth;
	@RequestMapping(value = "/showExpressBill", method = RequestMethod.GET)
	public ModelAndView showExpressBill(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("expressBill/expressBill");
		int count = 0;
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		Calendar cal = Calendar.getInstance();
		System.out.println(new SimpleDateFormat("MMM").format(cal.getTime()));
		String curMonth = new SimpleDateFormat("MMM").format(cal.getTime());

		if (curMonth.matches("Feb")) {
			System.out.println("It is Feb ");

		} else {

			System.out.println("It is Not March ");
		}
		HttpSession session = request.getSession();
	     Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
	
	     RestTemplate restTemplate = new RestTemplate();
			
	     
	     try {
				
			 map = new LinkedMultiValueMap<String, Object>();
				map.add("frId", frDetails.getFrId());
				
				com.monginis.ops.model.Info info= restTemplate.postForObject(Constant.URL + "checkIsMonthClosed", map,
						com.monginis.ops.model.Info.class);
				
				
				System.out.println(" 	"+info.toString() );

			if (info.isError()) {

				
				System.out.println("need to complete Month End ......" );

				model = new ModelAndView("stock/stockdetails");
				model.addObject("message","Please do month end process first");
				
				List<MCategory> mAllCategoryList = new ArrayList<MCategory>();

				CategoryList categoryList = new CategoryList();
				
				try {
					 map = new LinkedMultiValueMap<String, Object>();
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

				boolean isMonthCloseApplicable = true;

				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date date = new java.util.Date();
				System.out.println(dateFormat.format(date));
				cal = Calendar.getInstance();
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
			
			} catch (Exception e) {
				System.out.println("Exception in runningMonth" + e.getMessage());
				e.printStackTrace();

			}
	

		ArrayList<FrMenu> menuList = (ArrayList<FrMenu>) session.getAttribute("allMenuList");

		try {
			System.out.println("menuList" + menuList.toString());
			ArrayList<Integer> itemList = new ArrayList<Integer>();

			String items;
			StringBuilder builder = new StringBuilder();
			for (FrMenu frMenu : menuList) {

				if (frMenu.getMenuId() == 26 || frMenu.getMenuId() == 31 || frMenu.getMenuId() == 33
						|| frMenu.getMenuId() == 34 || frMenu.getMenuId()==63 || frMenu.getMenuId()==49||frMenu.getMenuId()==82||frMenu.getMenuId()==86) {

					String str = frMenu.getItemShow();
					System.out.println("getItemShow" + frMenu.getItemShow());

					builder.append("," + frMenu.getItemShow());

				}

			}
			items = builder.toString();
			items = items.substring(1, items.length());

			System.out.println("Item Show List is " + items);

			MultiValueMap<String, Object> mvm = new LinkedMultiValueMap<String, Object>();

			mvm.add("itemList", items);

			ItemResponse itemsList = restTemplate.postForObject(Constant.URL + "/getItemsNameById", mvm,
					ItemResponse.class);

			List<Item> newItemsList = itemsList.getItemList();

			customerBillItemList = new ArrayList<CustomerBillItem>();

			for (int i = 0; i < newItemsList.size(); i++) {

				Item item = newItemsList.get(i);

				CustomerBillItem customerBillItem = new CustomerBillItem();
				customerBillItem.setCatId(item.getItemGrp1());
				customerBillItem.setId(item.getId());
				customerBillItem.setItemId(item.getItemId());
				customerBillItem.setItemName(item.getItemName());
				customerBillItem.setQty(0);
				customerBillItem.setItemTax1(item.getItemTax1());
				customerBillItem.setItemTax2(item.getItemTax2());
				customerBillItem.setItemTax3(item.getItemTax3());

				if (frDetails.getFrRateCat() == 1) {
					customerBillItem.setMrp(item.getItemMrp1());
					customerBillItem.setRate(item.getItemRate1());
				} else if (frDetails.getFrRateCat() == 2) {
					customerBillItem.setMrp(item.getItemMrp2());

					customerBillItem.setRate(item.getItemRate2());

				} else if (frDetails.getFrRateCat() == 3) {
					customerBillItem.setMrp(item.getItemMrp3());

					customerBillItem.setRate(item.getItemRate3());
				}

				customerBillItemList.add(customerBillItem);

			}

			//System.out.println("*********customerBillItemList***********" + customerBillItemList.toString());

			model.addObject("itemsList", customerBillItemList);
			// model.addObject("count", count);
			// ----------------------------------------------------------------------------

			RestTemplate rest = new RestTemplate();
			MultiValueMap<String, Object> mav = new LinkedMultiValueMap<String, Object>();

			mav.add("frId", frDetails.getFrId());

			SellBillDataCommon sellBillResponse = rest.postForObject(Constant.URL + "/showNotDayClosedRecord", mav,
					SellBillDataCommon.class);

			List<SellBillHeader> sellBillHeaderList = sellBillResponse.getSellBillHeaderList();

			count = sellBillHeaderList.size();
			SellBillHeader sellBillHeader = sellBillResponse.getSellBillHeaderList().get(0);
			
			sellInvoiceGlobal=sellBillHeader.getInvoiceNo();

			// -------------------------------------------------
			sellBillHeaderGlobal = sellBillHeader;
			// --------------------------------------------------

			Date billDate = new SimpleDateFormat("dd-MM-yyyy").parse(sellBillHeader.getBillDate());
			// 1 Date dmyBillDate=new
			// SimpleDateFormat("dd-MM-yyyy").parse(sellBillHeader.getBillDate());
			// ------------Todays Date-----------
			Date date = new Date();
			String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

			System.out.println("Todays Date: " + todaysDate);
			
			System.err.println("Bill Date: " + billDate);
			// -------------------------------------
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = sdf.parse(todaysDate);
			// -------------------------------------
			if (count != 0) {
				if (billDate.equals(currentDate)) {
				 map = new LinkedMultiValueMap<String, Object>();
					System.err.println("bill date eq cur date");
					map.add("billNo", sellBillHeader.getSellBillNo());

					SellBillDetailList sellBillDetailList = rest.postForObject(Constant.URL + "/getSellBillDetails",
							map, SellBillDetailList.class);

					List<SellBillDetail> sellBillDetails = sellBillDetailList.getSellBillDetailList();
					selectedSellBillDetailList = sellBillDetails;
					System.out.println("selectedSellBillDetailList  " + selectedSellBillDetailList.toString());
					model.addObject("sellBillDetails", sellBillDetails);
					model.addObject("count", 2);
					model.addObject("listSize", sellBillDetails.size());

					model.addObject("sellBillHeader", sellBillHeader);

				} else if (billDate.before(currentDate)) {
				map = new LinkedMultiValueMap<String, Object>();
					System.err.println("bill date before cur date");

					map.add("billNo", sellBillHeader.getSellBillNo());

					SellBillDetailList sellBillDetailList = rest.postForObject(Constant.URL + "/getSellBillDetails",
							map, SellBillDetailList.class);

					List<SellBillDetail> sellBillDetails = sellBillDetailList.getSellBillDetailList();
					selectedSellBillDetailList = sellBillDetails;
					System.out.println("selectedSellBillDetailList  " + selectedSellBillDetailList.toString());
					model.addObject("sellBillDetails", sellBillDetails);
					model.addObject("listSize", sellBillDetails.size());
					model.addObject("count", 3);
					model.addObject("sellBillHeader", sellBillHeader);
					/*model.addObject("menuList", menuList);*/
				}
			} else {
				model.addObject("count", count);

			}
			AllMenuResponse allMenuResponse=rest.getForObject(
						Constant.URL+"/getAllMenu",
						AllMenuResponse.class);
				
			List<Menus>	menusList= new ArrayList<Menus>();
				menusList=allMenuResponse.getMenuConfigurationPage();
				
				System.out.println("MENU LIST= "+menusList.toString());
				model.addObject("menusList",menusList);
		} catch (Exception e) {
			model.addObject("count", 0);

			model.addObject("itemsList", customerBillItemList);

			System.out.println("Exception In ExpressBillController View Page");
		}
		return model;
	}

	@RequestMapping(value = "/calcStock", method = RequestMethod.GET)
	public @ResponseBody int getStock(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "itemId", required = true) String itemId,
			@RequestParam(value = "qty", required = true) int qty) {
		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		int curStock = 0;
		try {
			boolean isPrevItem = false;

			int catId = 0;
			int id = 0;

			for (CustomerBillItem item : customerBillItemList) {
				if (item.getItemId().equalsIgnoreCase(itemId)) {
					id = item.getId();
					catId = item.getCatId();

				}
			}
if(catId!=7) {
			// ------------------------------------------------------------------------------------------
			if (currentStockDetailList != null) {
				for (int i = 0; i < currentStockDetailList.size(); i++) {

					if (id == currentStockDetailList.get(i).getId()) {
						isPrevItem = true;
					}
				}
			}
			if (currentStockDetailList == null || !isPrevItem) {

				//System.out.println("If current Detail List is NULL for item Id " + id);
				currentStockDetailList = getStockFromServer(id, catId, frDetails);// stock calculation
			}

			for (int i = 0; i < currentStockDetailList.size(); i++) {
				if (id == currentStockDetailList.get(i).getId()) {
					isPrevItem = true;

					curStock = (currentStockDetailList.get(i).getCurrentRegStock());
				}
			}
}//end of if catId!=5;
else {
	System.err.println("Cat Id =7 ");
	
	curStock=qty;
}
		} catch (Exception e) {
			//System.out.println("Exception in cal Stock for Express Bill  " + e.getMessage());
			e.printStackTrace();

		}

		return curStock;
	}

	public List<GetCurrentStockDetails> getStockFromServer(int currentNewItem, int catId, Franchisee frDetails) {
		//System.out.println("Input para ");
		//System.out.println("item ID " + currentNewItem);
		//System.out.println("cat ID " + catId);
		//System.out.println("Fr ID " + frDetails.getFrId());

		Integer runningMonth = 0;

		PostFrItemStockHeader frItemStockHeader;
		// -------------------------------------Stock---------------------------------------
		RestTemplate restTemplate = new RestTemplate();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat yearFormat = new SimpleDateFormat("yyyy");

		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());

			frItemStockHeader = restTemplate.postForObject(Constant.URL + "getRunningMonth", map,
					PostFrItemStockHeader.class);
			runningMonth = frItemStockHeader.getMonth();
			
			Date todaysDate = new Date();
			System.out.println(dateFormat.format(todaysDate));

			Calendar cal = Calendar.getInstance();
			cal.setTime(todaysDate);

			cal.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDay = cal.getTime();

			//System.out.println("First Day of month " + firstDay);

			String strFirstDay = dateFormat.format(firstDay);

			System.out.println("Year " + yearFormat.format(todaysDate));
			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("frStockType",1);
			map.add("fromDate", dateFormat.format(firstDay));
			map.add("toDate", dateFormat.format(todaysDate));
			map.add("currentMonth", String.valueOf(runningMonth));
			map.add("year", yearFormat.format(todaysDate));
			map.add("catId", catId);
			map.add("itemIdList", currentNewItem);

			List<GetCurrentStockDetails> getCurrentStockDetailsList = new ArrayList<>();

			ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
			};
			ResponseEntity<List<GetCurrentStockDetails>> responseEntity = restTemplate
					.exchange(Constant.URL + "getCurrentStock", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			// getCurrentStockDetailsList.addAll(responseEntity.getBody());

			getCurrentStockDetailsList = responseEntity.getBody();

			//System.out.println("get Cur Stock De List " + getCurrentStockDetailsList.toString());
			// System.out.println("Current Stock Details : " +
			// currentStockDetailList.toString());

			if (!getCurrentStockDetailsList.isEmpty()) {

				for (int i = 0; i < getCurrentStockDetailsList.size(); i++) {
					currentStockDetailList.add(getCurrentStockDetailsList.get(i));
				}
			}

		} catch (Exception e) {

			System.out.println("cure Sto de li " + currentStockDetailList.toString());
			System.out.println("Exception in stock web Service " + e.getMessage());
			e.printStackTrace();

		}

		System.out.println("cure Sto de li " + currentStockDetailList.toString());

		return currentStockDetailList;

	}

	@RequestMapping(value = "/insertItem", method = RequestMethod.GET)
	public @ResponseBody List<SellBillDetail> insertItem(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "itemId", required = true) String itemId,
			@RequestParam(value = "qty", required = true) int qty) {
		System.out.println("********ItemId********" + itemId);
		RestTemplate restTemplate = new RestTemplate();
HttpSession ses=request.getSession();

if(ses==null) {
	
	System.err.println("Sesssion is null ");
}else {

	System.err.println("Sesssion is Alive ");
}


		float sumTaxableAmt = 0, sumTotalTax = 0, sumGrandTotal = 0;
		for (CustomerBillItem item : customerBillItemList) {
			if (item.getItemId().equalsIgnoreCase(itemId)) {

				SellBillDetail sellBillDetail = new SellBillDetail();

				Float rate = (float) item.getMrp();

				Float tax1 = (float) item.getItemTax1();
				Float tax2 = (float) item.getItemTax2();
				Float tax3 = (float) item.getItemTax3();

				Float mrpBaseRate = (rate * 100) / (100 + (tax1 + tax2));
				mrpBaseRate = roundUp(mrpBaseRate);

				System.out.println("Mrp: " + rate);
				System.out.println("Tax1 : " + tax1);
				System.out.println("tax2 : " + tax2);

				Float taxableAmt = (float) (mrpBaseRate * qty);
				taxableAmt = roundUp(taxableAmt);

				// -----------------------------------------

				float sgstRs = (taxableAmt * tax1) / 100;
				float cgstRs = (taxableAmt * tax2) / 100;
				float igstRs = (taxableAmt * tax3) / 100;

				sgstRs = roundUp(sgstRs);
				cgstRs = roundUp(cgstRs);
				igstRs = roundUp(igstRs);

				Float totalTax = sgstRs + cgstRs;
				totalTax = roundUp(totalTax);

				Float grandTotal = totalTax + taxableAmt;
				grandTotal = roundUp(grandTotal);

				sellBillDetail.setCatId(item.getCatId());
				sellBillDetail.setSgstPer(tax1);
				sellBillDetail.setSgstRs(sgstRs);
				sellBillDetail.setCgstPer(tax2);
				sellBillDetail.setCgstRs(cgstRs);
				sellBillDetail.setDelStatus(0);
				sellBillDetail.setGrandTotal(grandTotal);
				sellBillDetail.setIgstPer(0);
				sellBillDetail.setIgstRs(0);
				sellBillDetail.setItemId(item.getId());
				sellBillDetail.setMrp(rate);
				sellBillDetail.setMrpBaseRate(mrpBaseRate);
				sellBillDetail.setQty(qty);
				sellBillDetail.setRemark("");
				sellBillDetail.setSellBillDetailNo(0);
				sellBillDetail.setSellBillNo(sellBillHeaderGlobal.getSellBillNo());
				sellBillDetail.setBillStockType(1);

				sumTaxableAmt = sumTaxableAmt + taxableAmt;
				sumTotalTax = sumTotalTax + totalTax;
				sumGrandTotal = sumGrandTotal + grandTotal;

				sellBillDetail.setTaxableAmt(taxableAmt);
				sellBillDetail.setTotalTax(totalTax);
				//System.out.println("**SellBillDetail Response:** " + sellBillDetail.toString() + "GlobalBillNo."
				//		+ sellBillHeaderGlobal.getSellBillNo());

				sellBillDetailRes = restTemplate.postForObject(Constant.URL + "saveSellBillDetail", sellBillDetail,
						SellBillDetail.class);
				//System.out.println("After Insert Item  " + sellBillDetailRes.toString());
				if (sellBillDetailRes != null) {

					for (int i = 0; i < currentStockDetailList.size(); i++) {

						if (currentStockDetailList.get(i).getId() == item.getId()) {

							currentStockDetailList.get(i)
									.setCurrentRegStock(currentStockDetailList.get(i).getCurrentRegStock() - (qty));

						}

					} // end of For

				}

				//System.out.println("**SellBillDetail Response:** " + sellBillDetail.toString());
			} else {
				//System.out.println("********elseItemId********" + item.getItemId().toString());
			}
		}

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		map.add("billNo", sellBillHeaderGlobal.getSellBillNo());

		SellBillDetailList sellBillDetailList = restTemplate.postForObject(Constant.URL + "/getSellBillDetails", map,
				SellBillDetailList.class);

		List<SellBillDetail> sellBillDetails = sellBillDetailList.getSellBillDetailList();
		selectedSellBillDetailList = sellBillDetails;

		return sellBillDetails;
	}

	// ----------------------------roundUp()---------------------------------------------

	public static float roundUp(float d) {

		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

	}

	public  String getInvoiceNo(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		RestTemplate restTemplate = new RestTemplate();

		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		int frId = frDetails.getFrId();

		// String frCode = frDetails.getFrCode();

		map.add("frId", frId);
		FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map, FrSetting.class);

		int settingValue = frSetting.getSellBillNo();

		System.out.println("Setting Value Received " + settingValue);
		int year = Year.now().getValue();
		String curStrYear = String.valueOf(year);
		curStrYear = curStrYear.substring(2);

		int preMarchYear = Year.now().getValue() - 1;
		String preMarchStrYear = String.valueOf(preMarchYear);
		preMarchStrYear = preMarchStrYear.substring(2);

		System.out.println("Pre MArch year ===" + preMarchStrYear);

		int nextYear = Year.now().getValue() + 1;
		String nextStrYear = String.valueOf(nextYear);
		nextStrYear = nextStrYear.substring(2);

		System.out.println("Next  year ===" + nextStrYear);

		int postAprilYear = nextYear + 1;
		String postAprilStrYear = String.valueOf(postAprilYear);
		postAprilStrYear = postAprilStrYear.substring(2);

		System.out.println("Post April   year ===" + postAprilStrYear);

		java.util.Date date = new Date();
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		int month = cale.get(Calendar.MONTH);
		month=month+1;
		if (month <= 3) {

			curStrYear = preMarchStrYear + curStrYear;
			System.out.println("Month <= 3::Cur Str Year " + curStrYear);
		} else if (month >= 4) {

			curStrYear = curStrYear + nextStrYear;
			System.out.println("Month >=4::Cur Str Year " + curStrYear);
		}

		////

		int length = String.valueOf(settingValue).length();

		String invoiceNo = null;

		if (length == 1)

			invoiceNo = curStrYear + "-" + "0000" + settingValue;
		if (length == 2)

			invoiceNo = curStrYear + "-" + "000" + settingValue;

		if (length == 3)

			invoiceNo = curStrYear + "-" + "00" + settingValue;

		if (length == 4)

			invoiceNo = curStrYear + "-" + "0" + settingValue;

		
		invoiceNo=frDetails.getFrCode()+invoiceNo;
		System.out.println("*** settingValue= " + settingValue);
		return invoiceNo;

	}
	// -----------------------------------------------------------------------------------

	@RequestMapping(value = "/insertHeader", method = RequestMethod.GET)
	public @ResponseBody SellBillHeader insertHeader(HttpServletRequest request, HttpServletResponse response) {
		CustomerBillItem customerBillItem = new CustomerBillItem();
		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		
			 globalFrId=frDetails.getFrId();
		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		System.out.println(dtf.format(localDate)); // 2016/11/16

		SellBillHeader sellBillHeader = new SellBillHeader();
		String invNo = getInvoiceNo(request,response);

		sellBillHeader.setFrId(frDetails.getFrId());
		sellBillHeader.setFrCode(frDetails.getFrCode());
		sellBillHeader.setDelStatus(0);
		sellBillHeader.setUserName(frDetails.getFrName());
		sellBillHeader.setBillDate(dtf.format(localDate));

		sellBillHeader.setInvoiceNo(invNo);// hardcoded
		sellBillHeader.setPaidAmt(0);// hardcoded
		sellBillHeader.setPaymentMode(1);// hardcoded

		sellBillHeader.setSellBillNo(0);// hardcoded

		sellBillHeader.setUserGstNo(frDetails.getFrGstNo());

		sellBillHeader.setUserPhone(frDetails.getFrMob());

		sellBillHeader.setTaxableAmt(0);// hardcoded
		sellBillHeader.setDiscountPer(0);// hardcoded

		sellBillHeader.setDiscountAmt(0);// hardcoded
		sellBillHeader.setPayableAmt(0);// hardcoded

		sellBillHeader.setTotalTax(0);// hardcoded
		sellBillHeader.setGrandTotal(0);// hardcoded

		sellBillHeader.setRemainingAmt(0);// hardcoded

		sellBillHeader.setStatus(1);
		sellBillHeader.setBillType('E');

		RestTemplate restTemplate = new RestTemplate();

		sellBillHeader = restTemplate.postForObject(Constant.URL + "saveSellBillHeader", sellBillHeader,
				SellBillHeader.class);
	
		sellInvoiceGlobal=sellBillHeader.getInvoiceNo();
		
		if (sellBillHeader != null) {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frDetails.getFrId());
			FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map,
					FrSetting.class);

			int sellBillNo = frSetting.getSellBillNo();

			sellBillNo = sellBillNo + 1;

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frDetails.getFrId());
			map.add("sellBillNo", sellBillNo);

			Info info = restTemplate.postForObject(Constant.URL + "updateFrSettingBillNo", map, Info.class);

			//Info updateSetting = restTemplate.postForObject(Constant.URL + "updateSeetingForPB", map, Info.class);
		}
		return sellBillHeader;
	}

	@RequestMapping(value = "/findItemDetails", method = RequestMethod.GET)
	public @ResponseBody CustomerBillItem getBillDetails(
			@RequestParam(value = "itemId", required = true) String itemId) {
		System.out.println("********ItemId********" + itemId);
		String stringItemId = itemId.replace("\"", "");
		System.out.println("********ItemIdstring********" + stringItemId);

		CustomerBillItem resItem = new CustomerBillItem();

		for (CustomerBillItem item : customerBillItemList) {
			if (item.getItemId().equalsIgnoreCase(stringItemId)) {

				resItem = item;
			} else {
				System.out.println("********elseItemId********" + item.getItemId().toString());
			}
		}
		System.out.println("**Selected Item**:" + resItem.toString());
		return resItem;
	}

	@RequestMapping(value = "/dayClose", method = RequestMethod.GET)
	public @ResponseBody int dayClose(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		RestTemplate restTemplate = new RestTemplate();
		System.out.println("inside day close ");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		map.add("billNo", sellBillHeaderGlobal.getSellBillNo());

		SellBillDetailList sellBillDetailList = restTemplate.postForObject(Constant.URL + "/getSellBillDetails", map,
				SellBillDetailList.class);

		List<SellBillDetail> sellBillDetails = sellBillDetailList.getSellBillDetailList();
		
		System.err.println("Day close detail list");

		System.out.println("sellBillDetails inside dayClose are " + sellBillDetails.toString());

		map = new LinkedMultiValueMap<String, Object>();

		System.out.println("global BILL NO ************" + sellBillHeaderGlobal.getSellBillNo());

		map.add("sellBillNo", sellBillHeaderGlobal.getSellBillNo());

		System.out.println("sellBillHeaderGlobal.getSellBillNo()" + sellBillHeaderGlobal.getSellBillNo());
		SellBillHeader billHeader = restTemplate.postForObject(Constant.URL + "/getSellBillHeaderForDayClose", map,
				SellBillHeader.class);
		
		sellInvoiceGlobal=billHeader.getInvoiceNo();

		System.out.println("billHeader " + billHeader.toString());

		if (sellBillDetails.isEmpty()) {

			/*
			 * System.out.println("Bill Detail is Empty "); billHeader.setTaxableAmt(0);
			 * 
			 * billHeader.setTotalTax(1);
			 * 
			 * billHeader.setGrandTotal(1);
			 * 
			 * billHeader.setDiscountPer(1);
			 */

			int responseDelete = restTemplate.postForObject(Constant.URL + "deleteExBillHeader", map, Integer.class);

			System.out.println("Bill Header Response if Bill Detail Is Empty " + billHeader.toString());

		} else {
			System.out.println("Bill Detail Not Empty ");

			for (int x = 0; x < sellBillDetails.size(); x++) {

				billHeader.setTaxableAmt(billHeader.getTaxableAmt() + sellBillDetails.get(x).getTaxableAmt());

				billHeader.setTotalTax(billHeader.getTotalTax() + sellBillDetails.get(x).getTotalTax());
				billHeader.setGrandTotal(sellBillDetails.get(x).getGrandTotal() + billHeader.getGrandTotal());

				// billHeader.setBillDate(billHeader.getBillDate());
				
				billHeader.setDiscountPer(billHeader.getDiscountPer());

			}
			billHeader.setGrandTotal(Math.round(billHeader.getGrandTotal()));
			billHeader.setPaidAmt(billHeader.getGrandTotal());
			billHeader.setPayableAmt(billHeader.getGrandTotal());

			
			System.err.println("bill Header data for Day close " +billHeader.toString());
			String start_dt =billHeader.getBillDate();
			DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
			Date date = (Date)formatter.parse(start_dt);
		
			SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
			String finalString = newFormat.format(date);
			billHeader.setBillDate(finalString);
			
			billHeader = restTemplate.postForObject(Constant.URL + "saveSellBillHeader", billHeader,
					SellBillHeader.class);

			
			sellInvoiceGlobal=billHeader.getInvoiceNo();
			System.out.println("Bill Header Response if Bill Detail Not Empty " + billHeader.toString());

			System.out.println("billHeader new " + billHeader.toString());

		}
		/*
		 * billHeader=restTemplate.postForObject(Constant.URL + "saveSellBillHeader",
		 * billHeader, SellBillHeader.class);
		 */
		ModelAndView model = new ModelAndView("expressBill/expressBill");

		System.out.println("redirecting to model ex bill ");

		return 1;
	}

	// /deleteItem request Maoping

	@RequestMapping(value = "/deleteItem", method = RequestMethod.GET)
	public @ResponseBody List<SellBillDetail> deleteItem(
			@RequestParam(value = "sellBillDetailNo", required = true) int sellBillDetailNo,
			@RequestParam(value = "qty", required = true) int qty,	@RequestParam(value = "id", required = true) int id) {
		System.out.println("********ItemId********" + sellBillDetailNo);

		System.out.println("********inside Delete********" + sellBillDetailNo+ "qty "+ qty + "Id " +id);
		//

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

	
		map.add("sellBillDetailNo", sellBillDetailNo);

		Info info = restTemplate.postForObject(Constant.URL + "/deleteSellBillDetail", map, Info.class);

		System.out.println("Info.to " + info.toString());
		
		if(info.isError()==false) {
			
			for(int i=0;i<currentStockDetailList.size();i++) {
				
				if(currentStockDetailList.get(i).getId()==id) {
					
					currentStockDetailList.get(i).setCurrentRegStock(currentStockDetailList.get(i).getCurrentRegStock()+qty);
					System.err.println("Stock List updated Successfully "+currentStockDetailList.get(i));
					break;
				}
			}
			
		}

		map = new LinkedMultiValueMap<String, Object>();

		map.add("billNo", sellBillHeaderGlobal.getSellBillNo());

		SellBillDetailList sellBillDetailList = restTemplate.postForObject(Constant.URL + "/getSellBillDetails", map,
				SellBillDetailList.class);

		List<SellBillDetail> sellBillDetails = sellBillDetailList.getSellBillDetailList();

		return sellBillDetails;

	}

	@RequestMapping(value = "/printExBill", method = RequestMethod.GET)
	public ModelAndView showExpressBillPrint(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("expressBill/frExBillPrint");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {
		System.out.println("Item Id " + sellBillDetailRes.getItemId());
		map.add("itemId", sellBillDetailRes.getItemId());
		GetItemHsnCode getItemHsnCode = new RestTemplate().postForObject(Constant.URL + "/getItemHsnCode", map,
				GetItemHsnCode.class);
		// System.out.println("HSN CODE "+getItemHsnCode.toString());
	

		model.addObject("exBill", sellBillDetailRes);
	
		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		int frGstType = frDetails.getFrGstType();
		
		
		GetCustBillTax getCustBillTax=new GetCustBillTax();
		getCustBillTax.setSellBillDetailNo(sellBillDetailRes.getSellBillDetailNo());
		getCustBillTax.setCgstPer(sellBillDetailRes.getCgstPer());
		getCustBillTax.setSgstPer(sellBillDetailRes.getSgstPer());
		getCustBillTax.setCgstRs(sellBillDetailRes.getCgstRs());
		getCustBillTax.setSgstRs(sellBillDetailRes.getSgstRs());
		getCustBillTax.setTaxableAmt(sellBillDetailRes.getTaxableAmt());
		
		if (getItemHsnCode != null) {
			model.addObject("itemName", getItemHsnCode.getItemName());
			model.addObject("itemHsn", getItemHsnCode.getHsncd());
		}
		model.addObject("date", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		
		model.addObject("custBilltax",getCustBillTax);
		model.addObject("invNo",sellInvoiceGlobal);
		System.out.println("After print ");
		model.addObject("frGstType", frGstType);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getSelectedIdForPrint", method = RequestMethod.GET)
	public void getSelectedIdForPrint(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("IN Metjod");
        try {
		BillDetailList = new ArrayList<SellBillDetail>();
		String selectedId = request.getParameter("id");
		selectedId = selectedId.substring(1, selectedId.length() - 1);
		selectedId = selectedId.replaceAll("\"", "");

		System.out.println("selectedId  " + selectedId);

		List<String> selectedIdList = new ArrayList<>();
		System.out.println("sellBillDetailList  " + selectedSellBillDetailList.toString());
		selectedIdList = Arrays.asList(selectedId.split(","));
		for (int i = 0; i < selectedSellBillDetailList.size(); i++) {
			for (int j = 0; j < selectedIdList.size(); j++) {
				if (Integer.parseInt(selectedIdList.get(j)) == selectedSellBillDetailList.get(i)
						.getSellBillDetailNo()) {
					System.out.println(i);
					BillDetailList.add(selectedSellBillDetailList.get(i));
				}
			}
		}
		getCustBillTaxList=new ArrayList<GetCustBillTax>();
	
		for (int i = 0; i < BillDetailList.size(); i++) {
			boolean innerLoop=false;
			
			for (int j= 0; j < getCustBillTaxList.size(); j++) {
				
				if((BillDetailList.get(i).getCgstPer()==getCustBillTaxList.get(j).getCgstPer())&&(BillDetailList.get(i).getSgstPer()==getCustBillTaxList.get(j).getSgstPer()))
				{
					innerLoop=true;
					
					getCustBillTaxList.get(j).setCgstRs(getCustBillTaxList.get(j).getCgstRs()+BillDetailList.get(i).getCgstRs());
					getCustBillTaxList.get(j).setSgstRs(getCustBillTaxList.get(j).getSgstRs()+BillDetailList.get(i).getSgstRs());
					getCustBillTaxList.get(j).setTaxableAmt(getCustBillTaxList.get(j).getTaxableAmt()+BillDetailList.get(i).getTaxableAmt());
				}
			}
			if(innerLoop==false)
			{
				GetCustBillTax getCustBillTax=new GetCustBillTax();
				getCustBillTax.setCgstPer(BillDetailList.get(i).getCgstPer());
				getCustBillTax.setSgstPer(BillDetailList.get(i).getSgstPer());
				getCustBillTax.setCgstRs(BillDetailList.get(i).getCgstRs());
				getCustBillTax.setSgstRs(BillDetailList.get(i).getSgstRs());
				getCustBillTax.setSellBillDetailNo(BillDetailList.get(i).getSellBillDetailNo());
				getCustBillTax.setTaxableAmt(BillDetailList.get(i).getTaxableAmt());
				getCustBillTaxList.add(getCustBillTax);
			}
		}
		System.out.println("getCustBillTaxList"+getCustBillTaxList.toString());
        }
        catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	@RequestMapping(value = "/printSelectedOrder", method = RequestMethod.GET)
	public ModelAndView printSelectedOrder(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("expressBill/frSelectedExBillPrint");
		System.out.println("IN Print Selected Order");
		try {
		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		

	/*	RestTemplate rest = new RestTemplate();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		map.add("billNo", BillDetailList.get(0).getSellBillNo());
		if (frGstType == 10000000) {
			model = new ModelAndView("report/franchTaxInvoice");
			List<GetCustBillTax> getCustBilTaxList = rest.postForObject(Constant.URL + "getCustomerBillTax", map,
					List.class);

			ParameterizedTypeReference<List<GetCustmoreBillResponse>> typeRef = new ParameterizedTypeReference<List<GetCustmoreBillResponse>>() {
			};
			ResponseEntity<List<GetCustmoreBillResponse>> responseEntity = rest.exchange(Constant.URL + "getCustomerBill",
					HttpMethod.POST, new HttpEntity<>(map), typeRef);

			List<GetCustmoreBillResponse> getCustmoreBillResponseList = responseEntity.getBody();

			GetCustmoreBillResponse billResponse = getCustmoreBillResponseList.get(0);

			int billAmt = billResponse.getIntDiscAmt();
			float discPer = billResponse.getDiscountPer();

			int intDiscAmt = Math.round((billAmt * discPer) / 100);

			getCustmoreBillResponseList.get(0).setIntDiscAmt(intDiscAmt);

			System.out.println("bill no:" + BillDetailList.get(0).getSellBillNo() + "Custmore Bill : " + getCustmoreBillResponseList.toString());

			model.addObject("billList", BillDetailList);
			model.addObject("frGstType", frGstType);
			model.addObject("custBilltax", getCustBillTaxList);
		}*/
		
		System.out.println("Selected List " + BillDetailList.toString());
		model.addObject("exBill", BillDetailList);
		model.addObject("custBilltax", getCustBillTaxList);
		model.addObject("invNo",sellInvoiceGlobal);
        model.addObject("frGstType", frDetails.getFrGstType());

		model.addObject("date", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		System.out.println("After print ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
