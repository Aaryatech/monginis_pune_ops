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
import com.monginis.ops.billing.SellBillDetail;
import com.monginis.ops.billing.SellBillHeader;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.CategoryList;
import com.monginis.ops.model.CurrentStockResponse;
import com.monginis.ops.model.CustomerBillItem;
import com.monginis.ops.model.ExportToExcel;
import com.monginis.ops.model.FrConfigModel;
import com.monginis.ops.model.FrMenu;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetCurrentStockDetails;
import com.monginis.ops.model.Info;
import com.monginis.ops.model.Item;
import com.monginis.ops.model.ItemResponse;
import com.monginis.ops.model.MCategory;
import com.monginis.ops.model.PostFrItemStockDetail;
import com.monginis.ops.model.PostFrItemStockHeader;
import com.monginis.ops.model.SubCategory;
import com.monginis.ops.model.TabTitleData;
import com.monginis.ops.model.frsetting.FrSetting;

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
	public List<Item> newItemsList;

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
	
	
	List<GetCurrentStockDetails> stockMatchList=new ArrayList<>();
	@RequestMapping(value = "/showStockMatchUtility")
	public ModelAndView showStockMatchUtility(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("stock/stockMatchUtility");

		try {
			CategoryList categoryList=null;
			try {
				RestTemplate restTemplate = new RestTemplate();

				categoryList = restTemplate.getForObject(Constant.URL + "showAllCategory", CategoryList.class);

			} catch (Exception e) {
				System.out.println("Exception in getAllGategory" + e.getMessage());
				e.printStackTrace();

			}

			mAllCategoryList = categoryList.getmCategoryList();

			model.addObject("category", mAllCategoryList);
			
			
		 String catId = request.getParameter("cat_id");
		 if(catId!=null)
		 {       HttpSession session = request.getSession();
				Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

				menuList = (ArrayList<FrMenu>) session.getAttribute("allMenuList");
				System.out.println("Menu List " + menuList.toString());

				List<Integer> menuIdList=null;
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
					menuIdList =new ArrayList<>();
					for (PostFrItemStockHeader header : list) {

						if (header.getCatId() == intCatId) {
							runningMonth = header.getMonth();
						}

					}

					menuIdList.add(26);

				} else if (catId.equalsIgnoreCase("2")) {
					menuIdList =new ArrayList<>();
				
					for (PostFrItemStockHeader header : list) {

						if (header.getCatId() == intCatId) {
							runningMonth = header.getMonth();
						}

					}
						menuIdList.add(82);
				} else if (catId.equalsIgnoreCase("3")) {
					menuIdList =new ArrayList<>();
					for (PostFrItemStockHeader header : list) {

						if (header.getCatId() == intCatId) {
							runningMonth = header.getMonth();
						}

					}
					menuIdList.add(33);
				} else if (catId.equalsIgnoreCase("4")) {
					menuIdList =new ArrayList<>();
					for (PostFrItemStockHeader header : list) {

						if (header.getCatId() == intCatId) {
							runningMonth = header.getMonth();
						}

					}
					menuIdList.add(34);
				} else if (catId.equalsIgnoreCase("6")) {
					menuIdList =new ArrayList<>();
					for (PostFrItemStockHeader header : list) {

						if (header.getCatId() == intCatId) {
							runningMonth = header.getMonth();
						}

					}
					menuIdList.add(49);
				}
				

				String itemShow = "";

//				for (int i = 0; i < menuList.size(); i++) {
//					for(int j=0;j<menuIdList.size();j++) {
//					if (menuList.get(i).getMenuId() == menuIdList.get(j)) {
//
//						itemShow = menuList.get(i).getItemShow();
//
//					}
//					}
//				}
				
				
				
				//--------------------------------------
				
				map = new LinkedMultiValueMap<String, Object>();

				map.add("frId", frDetails.getFrId());
				map.add("catId", catId);
				
				
				ParameterizedTypeReference<List<FrConfigModel>> frCfList = new ParameterizedTypeReference<List<FrConfigModel>>() {
				};
				ResponseEntity<List<FrConfigModel>> responseEntityFr = restTemplate
						.exchange(Constant.URL + "getFrConfgByFrAndCat", HttpMethod.POST, new HttpEntity<>(map), frCfList);
				List<FrConfigModel> tempList = responseEntityFr.getBody();
				
				if(tempList!=null) {
					for(int i=0;i<tempList.size();i++) {
						itemShow=String.join(",",tempList.get(i).getItemShow());
					}
				}
				
				
				
				
				//--------------------------------------
				
				
				
				
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
				DateFormat yearFormat = new SimpleDateFormat("yyyy");

				Date todaysDate = new Date();
				System.out.println(dateFormat.format(todaysDate));

				Calendar cal = Calendar.getInstance();
				cal.setTime(todaysDate);

				cal.set(Calendar.DAY_OF_MONTH, 1);

				Date firstDay = cal.getTime();


				boolean isMonthCloseApplicable = false;

					map = new LinkedMultiValueMap<String, Object>();

					DateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
					Date date = new Date();
					System.out.println(dateFormat1.format(date));

					Calendar cal1 = Calendar.getInstance();
					cal1.setTime(date);

					int calCurrentMonth = cal1.get(Calendar.MONTH) + 1;
					
					if (runningMonth < calCurrentMonth) {

						isMonthCloseApplicable = true;
						System.out.println("Day Of Month End ......");

					} else if (runningMonth == 12 && calCurrentMonth == 1) {
						isMonthCloseApplicable = true;
					}

					if (isMonthCloseApplicable) {
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

						strDate = year + "/" + runningMonth + "/01";

						map.add("fromDate", strDate);
					} else {

						map.add("fromDate", dateFormat.format(firstDay));

					}

					map.add("frId", frDetails.getFrId());
					map.add("frStockType", frDetails.getStockType());
					map.add("toDate", dateFormat.format(todaysDate));
					map.add("currentMonth", String.valueOf(runningMonth));
					map.add("year", yearFormat.format(todaysDate));
					map.add("catId", catId);
					map.add("itemIdList", itemShow);
					System.out.println("itemShow : " + itemShow.toString());
					ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
					};
					ResponseEntity<List<GetCurrentStockDetails>> responseEntity = restTemplate
							.exchange(Constant.URL + "getCurrentStock", HttpMethod.POST, new HttpEntity<>(map), typeRef);

					currentStockDetailList = responseEntity.getBody();

				
				SubCategory[] subCatList = restTemplate.getForObject(Constant.URL + "getAllSubCatList",
						SubCategory[].class);

				ArrayList<SubCategory> subCatAList = new ArrayList<SubCategory>(Arrays.asList(subCatList));
				
				map = new LinkedMultiValueMap<String, Object>();
				map.add("itemGrp1", catId);
				Item[] itemList = restTemplate.postForObject(Constant.URL + "getItemsByCatId",
					map,Item[].class);
				ArrayList<Item> itemListRes = new ArrayList<Item>(Arrays.asList(itemList));

				
				List<TabTitleData> subCatListWithQtyTotal = new ArrayList<>();
               for(SubCategory subCat:subCatAList) {
            	   if(subCat.getCatId()==Integer.parseInt(catId)) {
				TabTitleData tabTitleData=new TabTitleData();
				tabTitleData.setHeader(""+subCat.getSubCatId());
				tabTitleData.setName(subCat.getSubCatName());
				subCatListWithQtyTotal.add(tabTitleData);
            	   }
               }
            	   for(int i=0;i<currentStockDetailList.size();i++)
            	   {
            		   for(Item item:itemListRes)
            		   {
            			   if(currentStockDetailList.get(i).getId()==item.getId())
            			   {
            				   currentStockDetailList.get(i).setSubCatId(item.getItemGrp2());
            			   }
            		   }
            	   }
					System.out.println("Current Stock Details : " + currentStockDetailList.toString());
					stockMatchList=currentStockDetailList;
   			    System.err.println("currentStockDetailList"+currentStockDetailList.toString());
   			    System.err.println("subCatListWithQtyTotal"+subCatListWithQtyTotal.toString());
				model.addObject("stockDetailList", currentStockDetailList);
				model.addObject("subCatListTitle", subCatListWithQtyTotal);
						 }

		 model.addObject("catId", catId);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return model;
	}
	
	
	
	@RequestMapping(value = "/addSellBill", method = RequestMethod.POST)
	public  String addSellBill(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		SellBillHeader sellBillHeaderRes = new SellBillHeader();
		try {
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();
			SellBillHeader sellBillHeader = new SellBillHeader();

			List<SellBillDetail> sellBillDetailList = new ArrayList<SellBillDetail>();
			List<CustomerBillItem> customerBillItemList = new ArrayList<CustomerBillItem>();
			RestTemplate restTemplate = new RestTemplate();
			
			String items;
			StringBuilder builder = new StringBuilder();
			for (FrMenu frMenu : menuList) {

				if (frMenu.getMenuId() == 26 || frMenu.getMenuId() == 31 || frMenu.getMenuId() == 33
						|| frMenu.getMenuId() == 34 || frMenu.getMenuId()==63|| frMenu.getMenuId()==66|| frMenu.getMenuId()==68|| frMenu.getMenuId()==81|| frMenu.getMenuId()==82||frMenu.getMenuId()==86) {

					builder.append("," + frMenu.getItemShow());

				}

			}
			items = builder.toString();
			items = items.substring(1, items.length());

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("itemList", items);
			map.add("frId", frDetails.getFrId());

			ItemResponse itemsList = restTemplate.postForObject(Constant.URL + "/getItemsNameByIdWithOtherItem", map,
					ItemResponse.class);

			newItemsList = itemsList.getItemList();

			customerBillItemList = new ArrayList<CustomerBillItem>();

			for (int i = 0; i < newItemsList.size(); i++) {

				Item item = newItemsList.get(i);
				String stQty=request.getParameter("qty"+item.getId());
				if(stQty!=null && stQty!="") {
                int qty=Integer.parseInt(stQty);
                if(qty>0) {
				CustomerBillItem customerBillItem = new CustomerBillItem();
				customerBillItem.setCatId(item.getItemGrp1());
				customerBillItem.setId(item.getId());
				customerBillItem.setItemId(item.getItemId());
				customerBillItem.setItemName(item.getItemName());
				customerBillItem.setHsnCode(item.getItemImage());//hsn code from itemImage --query
				customerBillItem.setQty(qty);
				customerBillItem.setItemTax1(item.getItemTax1());
				customerBillItem.setItemTax2(item.getItemTax2());
				customerBillItem.setItemTax3(item.getItemTax3());

				if (frDetails.getFrRateCat() == 1) {
					customerBillItem.setMrp(item.getItemMrp1());
					customerBillItem.setRate(item.getItemRate1());
				}  else if (frDetails.getFrRateCat() == 3) {
					customerBillItem.setMrp(item.getItemMrp3());

					customerBillItem.setRate(item.getItemRate3());
				}
				customerBillItem.setBillStockType(1);//1 means regular Stock
				customerBillItemList.add(customerBillItem);
                }
				}
			}
			float sumTaxableAmt = 0, sumTotalTax = 0, sumGrandTotal = 0, sumMrp = 0;

			for (int i = 0; i < customerBillItemList.size(); i++) {
				System.out.println("dddd");

				SellBillDetail sellBillDetail = new SellBillDetail();

				Float rate = (float) customerBillItemList.get(i).getMrp();

				Float tax1 = (float) customerBillItemList.get(i).getItemTax1();
				Float tax2 = (float) customerBillItemList.get(i).getItemTax2();
				Float tax3 = (float) customerBillItemList.get(i).getItemTax3();

				int qty = customerBillItemList.get(i).getQty();

				Float mrpBaseRate = (rate * 100) / (100 + (tax1 + tax2));
				mrpBaseRate = roundUp(mrpBaseRate);

				System.out.println("Mrp: " + rate);
				System.out.println("Tax1 : " + tax1);
				System.out.println("tax2 : " + tax2);

				Float taxableAmt = (float) (mrpBaseRate * qty);
				taxableAmt = roundUp(taxableAmt);

				// -----------------------------------------

				float discAmt = ((taxableAmt * 0) / 100);
				taxableAmt = taxableAmt - discAmt;

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

				sellBillDetail.setCatId(customerBillItemList.get(i).getCatId());
				sellBillDetail.setSgstPer(tax1);
				sellBillDetail.setSgstRs(sgstRs);
				sellBillDetail.setCgstPer(tax2);
				sellBillDetail.setCgstRs(cgstRs);
				sellBillDetail.setDelStatus(0);
				sellBillDetail.setGrandTotal(grandTotal);
				sellBillDetail.setIgstPer(tax3);
				sellBillDetail.setIgstRs(igstRs);
				sellBillDetail.setItemId(customerBillItemList.get(i).getId());
				sellBillDetail.setMrp(rate);
				sellBillDetail.setMrpBaseRate(mrpBaseRate);
				sellBillDetail.setQty(customerBillItemList.get(i).getQty());
				sellBillDetail.setRemark(customerBillItemList.get(i).getHsnCode());//hsncode --new
				sellBillDetail.setSellBillDetailNo(0);
				sellBillDetail.setSellBillNo(0);
				sellBillDetail.setBillStockType(customerBillItemList.get(i).getBillStockType());

				sumMrp = sumMrp + (rate * qty);
				sumTaxableAmt = sumTaxableAmt + taxableAmt;
				sumTotalTax = sumTotalTax + totalTax;
				sumGrandTotal = sumGrandTotal + grandTotal;

				sellBillDetail.setTaxableAmt(taxableAmt);
				sellBillDetail.setTotalTax(totalTax);

				sellBillDetailList.add(sellBillDetail);

			}

			sellBillHeader.setFrId(frDetails.getFrId());
			sellBillHeader.setFrCode(frDetails.getFrCode());
			sellBillHeader.setDelStatus(0);
			sellBillHeader.setUserName("Physical stock");
			sellBillHeader.setBillDate(dtf.format(localDate));

			sellBillHeader.setInvoiceNo(getInvoiceNoForStkMatch(request,response));
			sellBillHeader.setPaymentMode(1);
			sellBillHeader.setBillType('P');
			sellBillHeader.setSellBillNo(0);
			sellBillHeader.setUserGstNo("-");
     		sellBillHeader.setUserPhone("-");

			System.out.println("Sell Bill Header: " + sellBillHeader.toString());
			
			
			sellBillHeader.setTaxableAmt(sumTaxableAmt);
			sellBillHeader.setDiscountPer(0);
		
			float payableAmt = sumGrandTotal;
			sellBillHeader.setPaidAmt(Math.round(payableAmt));
			payableAmt = roundUp(payableAmt);

			sellBillHeader.setDiscountAmt(sumMrp);
			sellBillHeader.setPayableAmt(Math.round(payableAmt));
			System.out.println("Payable amt  : " + payableAmt);
			sellBillHeader.setTotalTax(sumTotalTax);
			sellBillHeader.setGrandTotal(Math.round(sumGrandTotal));
            sellBillHeader.setRemainingAmt(0);
			sellBillHeader.setStatus(1);
			

			sellBillHeader.setSellBillDetailsList(sellBillDetailList);
			System.out.println("Sell Bill Detail: " + sellBillHeader.toString());
			sellBillHeaderRes = restTemplate.postForObject(Constant.URL + "insertSellBillData", sellBillHeader,
					SellBillHeader.class);

			System.out.println("info :" + sellBillHeaderRes.toString());

			if (sellBillHeaderRes != null) {

				map = new LinkedMultiValueMap<String, Object>();
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
			
		} catch (Exception e) {

			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();

		}
		System.out.println("Order Response:" + sellBillHeaderRes.toString());

		return "redirect:/showStockMatchUtility";

	}

	public String getInvoiceNoForStkMatch(HttpServletRequest request, HttpServletResponse response) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		RestTemplate restTemplate = new RestTemplate();


		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		int frId = frDetails.getFrId();

		// String frCode = frDetails.getFrCode();

		map.add("frId", frId);
		FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map, FrSetting.class);

		int billNo = frSetting.getSellBillNo();

		int settingValue =billNo;

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
	
	
}