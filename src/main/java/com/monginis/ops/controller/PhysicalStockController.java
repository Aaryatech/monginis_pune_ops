package com.monginis.ops.controller;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.time.Year;
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

import com.monginis.ops.billing.SellBillDetail;
import com.monginis.ops.billing.SellBillHeader;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.AllItemsListResponse;
import com.monginis.ops.model.CategoryList;
import com.monginis.ops.model.CurrentStockResponse;
import com.monginis.ops.model.FrMenu;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetCurrentStockDetails;
import com.monginis.ops.model.Info;
import com.monginis.ops.model.Item;
import com.monginis.ops.model.MCategory;
import com.monginis.ops.model.PostFrItemStockHeader;
import com.monginis.ops.model.frsetting.FrSetting;
import com.steadystate.css.ParseException;

@Controller
@Scope("session")
public class PhysicalStockController {

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

	@RequestMapping(value = "/showstockdetailAddPhysicalStock")
	public ModelAndView showstockdetailAddPhysicalStock(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		ModelAndView model = new ModelAndView("stock/add_physical_stock");

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

	CurrentStockResponse currentStockResponse = new CurrentStockResponse();

	@RequestMapping(value = "/getStockDetailsForPhysicalStock", method = RequestMethod.GET)
	public @ResponseBody CurrentStockResponse getStockDetailsForPhysicalStock(HttpServletRequest request,
			HttpServletResponse response) {

		catId = request.getParameter("cat_id");
		String showOption = request.getParameter("show_option");

		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		menuList = (ArrayList<FrMenu>) session.getAttribute("allMenuList");
		System.out.println("Menu List " + menuList.toString());

		int menuId = 0;
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
			System.err.println(
					"Current Cal Month " + calCurrentMonth + "menuList" + menuList.toString() + "itemShow" + itemShow);

			System.out.println("Day Of Month is: " + dayOfMonth);

			if (runningMonth < calCurrentMonth) {

				isMonthCloseApplicable = true;
				System.out.println("Day Of Month End ......");

			} else if (runningMonth == 12 && calCurrentMonth == 1) {
				isMonthCloseApplicable = true;
			}

			if (isMonthCloseApplicable) {
				System.err.println("### Inside iMonthclose app");
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

			ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef2 = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
			};
			ResponseEntity<List<GetCurrentStockDetails>> responseEntity2 = restTemplate
					.exchange(Constant.URL + "getCurrentStock", HttpMethod.POST, new HttpEntity<>(map), typeRef2);

			currentStockDetailList = responseEntity2.getBody();
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
			} catch (java.text.ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			try {
				to = sdf1.format(sdf2.parse(toDate));
			} catch (java.text.ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
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

		currentStockResponse = new CurrentStockResponse();
		currentStockResponse.setMonthClosed(isMonthCloseApplicable);
		currentStockResponse.setCurrentStockDetailList(currentStockDetailList);

		return currentStockResponse;
	}

	// insertDocTerm

	AllItemsListResponse allItemsListResponse;

	@RequestMapping(value = "/insertSellBillHeader", method = RequestMethod.POST)
	public String insertSellBillHeader(HttpServletRequest request, HttpServletResponse response) {

		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			System.err.println("Inside insert insertDocTerm method");

			int catId = Integer.parseInt(request.getParameter("select_category"));

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			String curDate = dateFormat.format(new Date());
			SellBillHeader sellBillHead = new SellBillHeader();

			float finalGrandTotal = 0;

			float finalTotalTax = 0;
			float finalTaxableAmtt = 0;

			allItemsListResponse = restTemplate.getForObject(Constant.URL + "getAllItems", AllItemsListResponse.class);

			List<Item> itemsList = new ArrayList<Item>();
			itemsList = allItemsListResponse.getItems();

			List<SellBillDetail> docDetailList = new ArrayList<>();

			for (int i = 0; i < currentStockResponse.getCurrentStockDetailList().size(); i++) {

				GetCurrentStockDetails stockDetails = currentStockDetailList.get(i);

				int physicalStockQty = Integer
						.parseInt(request.getParameter("physicalStockQty" + stockDetails.getStockDetailId()));

				System.out.println("intStockDiffintStockDiffintStockDiffintStockDiffintStockDiff" + physicalStockQty);

				int intStockDiff = 0;

				int currentStock = (stockDetails.getCurrentRegStock());

				intStockDiff = currentStock - physicalStockQty;

				for (int k = 0; k < itemsList.size(); k++) {

					if (itemsList.get(k).getId() == stockDetails.getId()) {

						String rate = String.valueOf(itemsList.get(k).getItemRate1());
						String tax1 = String.valueOf(itemsList.get(k).getItemTax1());
						String tax2 = String.valueOf(itemsList.get(k).getItemTax2());
						String tax3 = String.valueOf(itemsList.get(k).getItemTax3());

						float rate1 = Float.parseFloat(rate);
						float tax1Float = Float.parseFloat(tax1);
						float tax2Float = Float.parseFloat(tax2);
						float tax3Float = Float.parseFloat(tax3);

						float mrpBaseRate = ((rate1 * 100) / (100 + (tax1Float + tax2Float)));
						mrpBaseRate = roundUp(mrpBaseRate);

						System.out.println("Mrp: " + rate1);
						System.out.println("Tax1 : " + tax1Float);
						System.out.println("tax2 : " + tax2Float);
						System.out.println("tax3 : " + tax3Float);

						Float taxableAmt = (mrpBaseRate * intStockDiff);
						taxableAmt = roundUp(taxableAmt);
						// -----------------------------------------

						float sgstRs = ((taxableAmt * tax1Float) / 100);
						float cgstRs = ((taxableAmt * tax2Float) / 100);
						float igstRs = ((taxableAmt * tax3Float) / 100);

						sgstRs = roundUp(sgstRs);
						cgstRs = roundUp(cgstRs);
						igstRs = roundUp(igstRs);

						Float totalTax = sgstRs + cgstRs;
						totalTax = roundUp(totalTax);

						Float grandTotal = totalTax + taxableAmt;
						grandTotal = roundUp(grandTotal);
						if (intStockDiff > 0) {
							SellBillDetail dDetail = new SellBillDetail();

							dDetail.setDelStatus(0);
							dDetail.setBillStockType(1);
							dDetail.setCatId(catId);
							dDetail.setCgstPer(tax2Float);
							dDetail.setCgstRs(cgstRs);
							dDetail.setGrandTotal(grandTotal);
							dDetail.setIgstPer(tax3Float);
							dDetail.setIgstRs(igstRs);
							dDetail.setItemId(stockDetails.getId());
							dDetail.setItemName(stockDetails.getItemName());
							dDetail.setMrp((float) stockDetails.getSpTotalPurchase());
							dDetail.setMrpBaseRate(mrpBaseRate);
							dDetail.setQty(intStockDiff);
							dDetail.setRemark("-");
							dDetail.setSellBillDetailNo(0);
							dDetail.setSellBillNo(0);
							dDetail.setSgstPer(tax1Float);
							dDetail.setSgstRs(sgstRs);
							dDetail.setTotalTax(totalTax);
							dDetail.setTaxableAmt(taxableAmt);

							finalGrandTotal = finalGrandTotal + grandTotal;
							finalTaxableAmtt = finalTaxableAmtt + taxableAmt;
							finalTotalTax = finalTotalTax + totalTax;

							System.out.println("finalGrandTotal" + finalGrandTotal);
							System.out.println("finalTaxableAmtt" + finalTaxableAmtt);
							System.out.println("finalTotalTax" + finalTotalTax);

							docDetailList.add(dDetail);
						}
					}

				}

			}

			sellBillHead.setDelStatus(0);
			sellBillHead.setBillDate(curDate);
			sellBillHead.setBillType('C');
			sellBillHead.setDiscountAmt(0);
			sellBillHead.setDiscountPer(0);
			sellBillHead.setFrCode(frDetails.getFrCode());
			sellBillHead.setFrId(frDetails.getFrId());
			sellBillHead.setGrandTotal(finalGrandTotal);
			sellBillHead.setInvoiceNo(getInvoiceNo(request, response));
			sellBillHead.setPaidAmt(0);
			sellBillHead.setPayableAmt(0);
			sellBillHead.setPaymentMode(1);
			sellBillHead.setRemainingAmt(0);
			sellBillHead.setSellBillNo(0);
			sellBillHead.setStatus(1);
			sellBillHead.setTaxableAmt(finalTaxableAmtt);
			sellBillHead.setTotalTax(finalTotalTax);
			sellBillHead.setUserGstNo("-");
			sellBillHead.setUserName("Consolated");
			sellBillHead.setUserPhone("-");
			sellBillHead.setSellBillDetailsList(docDetailList);

			System.out.println();

			System.out.println("sellBillHeadsellBillHeadsellBillHeadsellBillHead" + sellBillHead.toString());

			if (docDetailList.size() > 0) {

				SellBillHeader sellBillHeaderRes = restTemplate.postForObject(Constant.URL + "insertSellBillData",
						sellBillHead, SellBillHeader.class);

				System.out.println("docInsertResdocInsertResdocInsertResdocInsertResdocInsertResdocInsertRes"
						+ sellBillHeaderRes.toString());

				if (sellBillHeaderRes != null) {

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

				}
			}

		} catch (Exception e) {

			System.err.println("Exce In insertEnq method  " + e.getMessage());
			e.printStackTrace();

		}

		return "redirect:/showstockdetailAddPhysicalStock  ";

	}

	public String getInvoiceNo(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		RestTemplate restTemplate = new RestTemplate();

		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		int frId = frDetails.getFrId();

		// String frCode = frDetails.getFrCode();

		map.add("frId", frId);
		FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map, FrSetting.class);

		int billNo = frSetting.getSellBillNo();

		int settingValue = billNo;

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

		month = month + 1;

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

		invoiceNo = frDetails.getFrCode() + invoiceNo;
		System.out.println("*** settingValue= " + settingValue);
		return invoiceNo;

	}

}
