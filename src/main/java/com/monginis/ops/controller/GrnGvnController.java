package com.monginis.ops.controller;

import static org.hamcrest.CoreMatchers.is;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.monginis.ops.billing.Info;
import com.monginis.ops.billing.SellBillDataCommon;
import com.monginis.ops.billing.SellBillDetail;
import com.monginis.ops.billing.SellBillHeader;
import com.monginis.ops.common.Firebase;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.constant.VpsImageUpload;
import com.monginis.ops.model.CategoryList;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetCurrentStockDetails;
import com.monginis.ops.model.MCategory;
import com.monginis.ops.model.PostFrItemStockHeader;
import com.monginis.ops.model.SellBillDetailList;
import com.monginis.ops.model.creditnote.CreditNoteHeaderPrint;
import com.monginis.ops.model.creditnote.CreditPrintBean;
import com.monginis.ops.model.creditnote.CrnDetailsSummary;
import com.monginis.ops.model.creditnote.CrnSrNoDateBean;
import com.monginis.ops.model.creditnote.GetCreditNoteHeaders;
import com.monginis.ops.model.creditnote.GetCreditNoteHeadersList;
import com.monginis.ops.model.creditnote.GetCrnDetails;
import com.monginis.ops.model.creditnote.GetCrnDetailsList;
import com.monginis.ops.model.frsetting.FrSetting;
import com.monginis.ops.model.grngvn.GetBillsForFr;
import com.monginis.ops.model.grngvn.GetBillsForFrList;
import com.monginis.ops.model.grngvn.GetGrnConfResponse;
import com.monginis.ops.model.grngvn.GetGrnGvnConfResponse;
import com.monginis.ops.model.grngvn.GetGrnGvnDetails;
import com.monginis.ops.model.grngvn.GetGrnGvnDetailsList;
import com.monginis.ops.model.grngvn.GetGrnItemConfig;
import com.monginis.ops.model.grngvn.GrnGvn;
import com.monginis.ops.model.grngvn.GrnGvnHeader;
import com.monginis.ops.model.grngvn.GrnGvnHeaderList;
import com.monginis.ops.model.grngvn.GrnGvnPdf;
import com.monginis.ops.model.grngvn.PostGrnGvnList;
import com.monginis.ops.model.grngvn.ShowGrnBean;
import com.monginis.ops.model.grngvn.StockForAutoGrnGvn;
import com.monginis.ops.model.remarks.GetAllRemarks;
import com.monginis.ops.model.remarks.GetAllRemarksList;

@Controller
@Scope("session")
public class GrnGvnController {
	public String getInvoiceNo(HttpServletRequest request, HttpServletResponse response) {

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

		java.util.Date date = new java.util.Date();
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		int month = cale.get(Calendar.MONTH);
		month = month + 1;
		System.err.println("month calendar" + month);

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
		System.out.println("*** settingValue= " + invoiceNo);
		return invoiceNo;

	}

	public String incrementDate(String date, int day) {

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(date));

		} catch (ParseException e) {

			System.out.println("Exception while incrementing date " + e.getMessage());

			e.printStackTrace();
		}

		c.add(Calendar.DATE, day); // number of days to add

		date = sdf.format(c.getTime());

		return date;

	}

	GetAllRemarksList allRemarksList, getAllRemarksList;
	List<GetAllRemarks> getAllRemarks;

	public GetGrnGvnConfResponse grnGvnConfResponse = new GetGrnGvnConfResponse();
	public List<GetGrnItemConfig> grnConfList;
	public List<GetGrnGvnDetails> grnGvnDetailsList;
	GetGrnGvnDetailsList getGrnGvnDetailsList;
	PostFrItemStockHeader frItemStockHeader;
	Integer runningMonth = 0;

	List<GetBillsForFr> frBillList;

	List<ShowGrnBean> objShowGrnList = new ArrayList<>();

	List<ShowGrnBean> sellBillData = new ArrayList<>();

	List<ShowGrnBean> objShowGvnList, gvnList;;

	List<StockForAutoGrnGvn> stockForAutoGrn = new ArrayList<StockForAutoGrnGvn>();

	List<GetCurrentStockDetails> currentStockDetailList = new ArrayList<GetCurrentStockDetails>();

	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	@RequestMapping(value = "/showGrn", method = RequestMethod.GET)
	public ModelAndView showBill(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("show Grn displayed");
		ModelAndView modelAndView = new ModelAndView("grngvn/showgrn");
		/*
		 * java.util.Date date = new
		 * SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(curDateTime);
		 * 
		 * Calendar caleInstance = Calendar.getInstance();
		 * 
		 * caleInstance.setTime(date);
		 * 
		 * caleInstance.set(Calendar.SECOND, (caleInstance.get(Calendar.SECOND) +5));
		 * 
		 * System.out.println("*****Calender Gettime == " + caleInstance.getTime());
		 * 
		 * 
		 * System.out.println("*****Calender Gettime == " + caleInstance.getTime());
		 */
		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		RestTemplate restTemplate = new RestTemplate();

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());

			com.monginis.ops.model.Info info = restTemplate.postForObject(Constant.URL + "checkIsMonthClosed", map,
					com.monginis.ops.model.Info.class);

			System.out.println(" 	" + info.toString());

			if (info.isError()) {

				System.out.println("need to complete Month End ......");

				modelAndView = new ModelAndView("stock/stockdetails");
				modelAndView.addObject("message", "Please do month end process first");

				List<MCategory> mAllCategoryList = new ArrayList<MCategory>();

				CategoryList categoryList = new CategoryList();

				try {
					map = new LinkedMultiValueMap<String, Object>();
					map.add("frId", frDetails.getFrId());

					List<PostFrItemStockHeader> list = restTemplate
							.postForObject(Constant.URL + "getCurrentMonthOfCatId", map, List.class);

					System.out.println("list " + list);

					frItemStockHeader = restTemplate.postForObject(Constant.URL + "getRunningMonth", map,
							PostFrItemStockHeader.class);

					System.out.println("Fr Opening Stock " + frItemStockHeader.toString());
					runningMonth = frItemStockHeader.getMonth();

					int monthNumber = runningMonth;
					String mon = Month.of(monthNumber).name();

					System.err.println("Month name " + mon);
					modelAndView.addObject("getMonthList", list);

				} catch (Exception e) {
					System.out.println("Exception in runningMonth" + e.getMessage());
					e.printStackTrace();

				}

				boolean isMonthCloseApplicable = true;

				DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				java.util.Date date = new java.util.Date();
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

				modelAndView.addObject("category", mAllCategoryList);
				modelAndView.addObject("isMonthCloseApplicable", isMonthCloseApplicable);

				return modelAndView;

			}

		} catch (Exception e) {
			System.out.println("Exception in runningMonth" + e.getMessage());
			e.printStackTrace();

		}

		String sro = getGrnGvnSrNo(request, response);

		// System.out.println("#########" + srNo + "################");

		int month = 0;
		try {

			// getRunniugMonth
			try {
				MultiValueMap<String, Object> mapForRunningMonth = new LinkedMultiValueMap<String, Object>();
				mapForRunningMonth.add("frId", frDetails.getFrId());

				frItemStockHeader = restTemplate.postForObject(Constant.URL + "getRunningMonth", mapForRunningMonth,
						PostFrItemStockHeader.class);
				runningMonth = frItemStockHeader.getMonth();
				System.out.println("Runnign Month= " + runningMonth);

				java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

				month = currentDate.getMonth() + 1;

				System.out.println(" Month= " + month);

			} catch (Exception e) {

				System.out.println("Exception in runningMonth" + e.getMessage());
				e.printStackTrace();

			}
			// end of get Running month

			/*
			 * ZoneId zoneId = ZoneId.of( "Asia/Calcutta" ); ZonedDateTime zdt =
			 * ZonedDateTime.now( zoneId ); System.out.println("time =="+zdt.format(null));
			 * 
			 */

			// Ganesh Remrk

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map = new LinkedMultiValueMap<String, Object>();
			map.add("isFrUsed", 1);
			map.add("moduleId", 1);
			map.add("subModuleId", 1);
			getAllRemarksList = restTemplate.postForObject(Constant.URL + "/getAllRemarks", map,
					GetAllRemarksList.class);

			// allRemarksList = restTemplate.getForObject(Constants.url + "getAllRemarks",
			// GetAllRemarksList.class);

			getAllRemarks = new ArrayList<>();
			getAllRemarks = getAllRemarksList.getGetAllRemarks();

			// allRemarksList = restTemplate.getForObject(Constant.URL + "getAllRemarks",
			// GetAllRemarksList.class);

			// getAllRemarks = allRemarksList.getGetAllRemarks();

			System.out.println("remark list " + getAllRemarks.toString());

			modelAndView.addObject("remarkList", getAllRemarks);

			if (runningMonth != month) {

				String msg = "Please Close Your Month First";
				modelAndView.addObject("alert", msg);

			}

			// RestTemplate restTemplate = new RestTemplate();

			map = new LinkedMultiValueMap<String, Object>();

			int frId = frDetails.getFrId();

			map.add("frId", frId);

			System.out.println("fr Id = " + frDetails.getFrId());
			grnGvnConfResponse = restTemplate.postForObject(Constant.URL + "getGrnItemConfig", map,
					GetGrnGvnConfResponse.class);
			grnConfList = new ArrayList<>();

			grnConfList = grnGvnConfResponse.getGetGrnItemConfigs();

			System.out.println("bill table data " + grnConfList.toString());

			// new web service autogrnQty atul Sir

			java.sql.Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			DateFormat yearFormat = new SimpleDateFormat("yyyy");

			java.util.Date todaysDate = new java.util.Date();

			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.DAY_OF_MONTH, 1);
			java.util.Date firstDay = cal.getTime();

			System.out.println("First Day of month " + firstDay);

			System.out.println("Year " + yearFormat.format(todaysDate));

			Calendar calendar = Calendar.getInstance();
			// subtracting a day
			calendar.add(Calendar.DATE, -1);
			todaysDate = calendar.getTime();

			MultiValueMap<String, Object> stockMap = new LinkedMultiValueMap<String, Object>();

			List<StockForAutoGrnGvn> templList = new ArrayList<>();

			DateFormat dateFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cale = Calendar.getInstance();
			System.out.println("************* Date Time " + dateFmt.format(cale.getTime()));

			for (int i = 0; i < grnConfList.size(); i++) {

				stockMap = new LinkedMultiValueMap<String, Object>();
				/*
				 * java.util.Date billDate = grnConfList.get(i).getBillDate(); Calendar cal3 =
				 * Calendar.getInstance(); cal3.setTime(billDate); cal3.add(Calendar.DATE, -1);
				 */

				String strBillDateTime = grnConfList.get(i).getBillDateTime();
				java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(strBillDateTime);

				Calendar caleInstance = Calendar.getInstance();

				caleInstance.setTime(date);

				caleInstance.set(Calendar.SECOND, (caleInstance.get(Calendar.SECOND) - 1));

				System.out.println("*****Calender Gettime == " + caleInstance.getTime());

				java.util.Date beforeBillAcceptDate = caleInstance.getTime();

				String strBeforeBillAcceptDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(beforeBillAcceptDate);

				stockMap.add("frId", frDetails.getFrId());
				stockMap.add("fromDate", dateFormat.format(grnConfList.get(i).getBillDate()));
				// stockMap.add("fromDateTime", grnConfList.get(i).getBillDateTime());

				stockMap.add("fromDateTime", strBeforeBillAcceptDate);
				stockMap.add("toDateTime", dateFmt.format(cale.getTime()));

				stockMap.add("currentMonth", currentDate.getMonth() + 1);
				stockMap.add("year", yearFormat.format(todaysDate));
				stockMap.add("catId", grnConfList.get(i).getCatId());
				stockMap.add("itemIdList", grnConfList.get(i).getItemId());

				ParameterizedTypeReference<List<StockForAutoGrnGvn>> typeRef = new ParameterizedTypeReference<List<StockForAutoGrnGvn>>() {
				};
				ResponseEntity<List<StockForAutoGrnGvn>> responseEntity = restTemplate.exchange(
						Constant.URL + "getStockForAutoGrnGvn", HttpMethod.POST, new HttpEntity<>(stockMap), typeRef);

				templList = responseEntity.getBody();
				stockForAutoGrn.add(templList.get(0));

			}

			System.out.println(" stockForAutoGrn Details : " + stockForAutoGrn.toString());

			// end new web service

			// calculate autogrnQty
			for (int i = 0; i < grnConfList.size(); i++) {
				// nw code
				int purQty = grnConfList.get(i).getBillQty();
				int tempGQty = grnConfList.get(i).getAutoGrnQty();

				for (int k = 0; k < i; k++) {
					System.out.println(
							"I Item = " + grnConfList.get(i).getItemId() + " K Item " + grnConfList.get(k).getItemId());
					if (grnConfList.get(i).getItemId().equals(grnConfList.get(k).getItemId())) {
						purQty = purQty + grnConfList.get(k).getBillQty();
						tempGQty = tempGQty + grnConfList.get(k).getAutoGrnQty();
						System.out.println("/////////// Item Name " + grnConfList.get(i).getItemName() + " Pur Qty "
								+ purQty + "Temp G qty " + tempGQty);

					} // end of item Id match

				} // end of k for

				for (int j = 0; j < stockForAutoGrn.size(); j++) {

					if (grnConfList.get(i).getItemId().equals(stockForAutoGrn.get(j).getId())) {

						if (grnConfList.get(i).getGrnType() != 4) {

							int autoGrnQty = (stockForAutoGrn.get(j).getRegCurrentStock() + purQty)
									- (stockForAutoGrn.get(j).getRegSellQty() + stockForAutoGrn.get(j).getGrnGvnQty()
											+ tempGQty);

							System.out.println("*********");
							System.out.println("item Id= " + grnConfList.get(i).getItemId());
							System.out.println("cur reg stock =  " + stockForAutoGrn.get(j).getRegCurrentStock());
							System.out.println(" bill qty = " + grnConfList.get(i).getBillQty());
							System.out.println("tot sell = " + stockForAutoGrn.get(j).getRegSellQty());
							System.out.println("grn = " + stockForAutoGrn.get(j).getGrnGvnQty());
							System.out.println("*********");
							grnConfList.get(i).setAutoGrnQty(autoGrnQty);
						} // end of inner if
						else {
							System.out.println("In Else ");
							int autoGrnQty = (stockForAutoGrn.get(j).getRegCurrentStock() + purQty)
									- (stockForAutoGrn.get(j).getRegSellQty() + stockForAutoGrn.get(j).getGrnGvnQty()
											+ tempGQty);
							System.out.println("4444444");
							System.out.println("item Id= " + grnConfList.get(i).getItemId());
							System.out.println("Pur Qty " + purQty);
							System.out.println("Temp G Qty " + tempGQty);
							System.out.println("bill date =" + grnConfList.get(i).getBillDate());
							System.out.println("cur reg stock =  " + stockForAutoGrn.get(j).getRegCurrentStock());
							System.out.println(" bill qty = " + grnConfList.get(i).getBillQty());
							System.out.println("tot sell = " + stockForAutoGrn.get(j).getRegSellQty());
							System.out.println("grn = " + stockForAutoGrn.get(j).getGrnGvnQty());
							System.out.println(
									"auto Grn Qty " + autoGrnQty + "for item id " + grnConfList.get(i).getItemId());
							System.out.println("5555555");
							grnConfList.get(i).setAutoGrnQty(autoGrnQty);
						} // end of else
					} // end of outer if
				}
			} // end of calc autogrnQty

			objShowGrnList = new ArrayList<>();

			ShowGrnBean objShowGrn = null;

			for (int i = 0; i < grnConfList.size(); i++) {

				if (!(grnConfList.get(i).getAutoGrnQty() <= 0)) {
					System.err.println("inside qty is >0 ");

					objShowGrn = new ShowGrnBean();

					objShowGrn.setDiscPer(grnConfList.get(i).getDiscPer());
					objShowGrn.setHsnCode(grnConfList.get(i).getHsnCode());//new omn 4jun19
					objShowGrn.setBillDate(grnConfList.get(i).getBillDate());
					objShowGrn.setBillDetailNo(grnConfList.get(i).getBillDetailNo());
					objShowGrn.setBillNo(grnConfList.get(i).getBillNo());
					objShowGrn.setBillQty(grnConfList.get(i).getBillQty());

					objShowGrn.setCgstPer(grnConfList.get(i).getCgstPer());
					objShowGrn.setFrId(grnConfList.get(i).getFrId());
					objShowGrn.setGrnType(grnConfList.get(i).getGrnType());
					objShowGrn.setIgstPer(grnConfList.get(i).getIgstPer());
					objShowGrn.setItemId(grnConfList.get(i).getItemId());
					objShowGrn.setItemName(grnConfList.get(i).getItemName());
					objShowGrn.setMrp(grnConfList.get(i).getMrp());
					objShowGrn.setRate(grnConfList.get(i).getRate());
					objShowGrn.setSgstPer(grnConfList.get(i).getSgstPer());

					float calcBaseRate = grnConfList.get(i).getRate() * 100
							/ (grnConfList.get(i).getSgstPer() + grnConfList.get(i).getCgstPer() + 100);

					objShowGrn.setCalcBaseRate(roundUp(calcBaseRate));

					objShowGrn.setAutoGrnQty(grnConfList.get(i).getAutoGrnQty());

					float baseRate = objShowGrn.getRate() * 100
							/ (objShowGrn.getSgstPer() + objShowGrn.getCgstPer() + 100);

					float grnBaseRate = 0.0f;

					float grnRate = 0.0f;

					if (objShowGrn.getGrnType() == 0) {
						grnBaseRate = baseRate * 80 / 100;

						grnRate = (baseRate * 80) / 100;
					}

					if (objShowGrn.getGrnType() == 1) {
						grnBaseRate = baseRate * 70 / 100;

						grnRate = (baseRate * 70) / 100;
					}

					if (objShowGrn.getGrnType() == 2 || objShowGrn.getGrnType() == 4) {

						grnBaseRate = baseRate;
						grnRate = baseRate;
					}

					float taxableAmt = grnRate * objShowGrn.getAutoGrnQty();
					float discAmt = (taxableAmt * objShowGrn.getDiscPer() ) / 100;//4 Feb 2019

					taxableAmt = taxableAmt - discAmt;

					objShowGrn.setTaxableAmt(roundUp(taxableAmt));

					float totalTax = (taxableAmt * (objShowGrn.getSgstPer() + objShowGrn.getCgstPer())) / 100;

					float grandTotal = taxableAmt + totalTax;

					float finalAmt = grnRate * objShowGrn.getAutoGrnQty();

					objShowGrn.setGrnAmt(roundUp(grandTotal));

					float taxPer = objShowGrn.getSgstPer() + objShowGrn.getCgstPer();

					objShowGrn.setTaxPer(taxPer);

					objShowGrn.setMenuId(grnConfList.get(i).getMenuId());
					objShowGrn.setCatId(grnConfList.get(i).getCatId());
					objShowGrn.setInvoiceNo(grnConfList.get(i).getInvoiceNo());
					objShowGrn.setBillDateTime(grnConfList.get(i).getBillDateTime());

					objShowGrn.setTaxAmt(roundUp(totalTax));

					System.out.println("OBJ SHOW GRN " + objShowGrn.toString());
					objShowGrnList.add(objShowGrn);

				} // end of if

			} // End of For Loop

			System.out.println("bean new " + objShowGrnList.toString());

			modelAndView.addObject("grnConfList", objShowGrnList);

		} catch (Exception e) {
			e.printStackTrace();

			System.out.println("Error in getting grn Item config " + e.getMessage());
		}

		return modelAndView;

	}

	public String getGrnGvnSrNo(HttpServletRequest request, HttpServletResponse response) {
		String grnGvnNo = null;
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();

			HttpSession session = request.getSession();

			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			int frId = frDetails.getFrId();

			// String frCode = frDetails.getFrCode();

			map.add("frId", frId);
			FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map, FrSetting.class);

			int grnGvnSrNo = frSetting.getGrnGvnNo();

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

			java.util.Date date = new java.util.Date();
			Calendar cale = Calendar.getInstance();
			cale.setTime(date);
			int month = cale.get(Calendar.MONTH);
			month = month + 1;
			System.err.println("RUnning month calendar " + month);

			if (month <= 3) {

				curStrYear = preMarchStrYear + curStrYear;
				System.out.println("Month <= 3::Cur Str Year " + curStrYear);
			} else if (month >= 4) {

				curStrYear = curStrYear + nextStrYear;
				System.out.println("Month >=4::Cur Str Year " + curStrYear);
			}

			////

			int length = String.valueOf(grnGvnSrNo).length();

			String invoiceNo = null;

			if (length == 1)

				invoiceNo = curStrYear + "-" + "000" + grnGvnSrNo;
			if (length == 2)

				invoiceNo = curStrYear + "-" + "00" + grnGvnSrNo;

			if (length == 3)

				invoiceNo = curStrYear + "-" + "0" + grnGvnSrNo;

			System.out.println("*** settingValue= " + grnGvnSrNo);

			grnGvnNo = frDetails.getFrCode() + invoiceNo;
			// return grnGvnNo;

		} catch (Exception e) {

		}

		return grnGvnNo;

	}

	@RequestMapping(value = "/insertGrnProcess", method = RequestMethod.POST)
	public String insertGrnProcess(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView("grngvn/showgrn");

		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		int fraId = frDetails.getFrId();
		try {

			java.sql.Date grnGvnDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			List<GrnGvn> postGrnGvnList = new ArrayList<GrnGvn>();

			PostGrnGvnList postGrnList = new PostGrnGvnList();

			GrnGvnHeader grnHeader = new GrnGvnHeader();

			grnConfList = grnGvnConfResponse.getGetGrnItemConfigs();

			float sumTaxableAmt = 0;
			float sumTaxAmt = 0;
			float sumTotalAmt = 0;

			String curDateTime = null;

			for (int i = 0; i < objShowGrnList.size(); i++) {

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				DateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calDate = Calendar.getInstance();

				String tempGrnQtyAuto = request
						.getParameter("grnqtyauto" + objShowGrnList.get(i).getBillDetailNo() + "");

				String tempGrnQty = request.getParameter("grnqty" + objShowGrnList.get(i).getBillDetailNo() + "");

				System.out.println("tempGrnQty ===" + tempGrnQty);

				System.out.println("tempGrnQtyAuto ===" + tempGrnQtyAuto);

				int grnQty = Integer.parseInt(tempGrnQtyAuto);
				int fixedGrnQty = Integer.parseInt(tempGrnQty);

				int isEdit = 0;
				if (grnQty != fixedGrnQty) {
					isEdit = 1;

				} else {
					isEdit = 0;
				}

				String frGrnRemark = request.getParameter("grn_remark" + objShowGrnList.get(i).getBillDetailNo());

				if (frGrnRemark == null || frGrnRemark == "") {
					frGrnRemark = "no remark entered";

				}

				/* if (grnQty > 0) { */

				GrnGvn postGrnGvn = new GrnGvn();

				float baseRate = objShowGrnList.get(i).getRate() * 100
						/ (objShowGrnList.get(i).getSgstPer() + objShowGrnList.get(i).getCgstPer() + 100);

				float grnBaseRate = 0.0f;

				float grnRate = 0.0f;

				if (objShowGrnList.get(i).getGrnType() == 0) {
					grnBaseRate = baseRate * 80 / 100;

					grnRate = (objShowGrnList.get(i).getRate() * 80) / 100;
					// postGrnGvn.setGrnGvnAmt(roundUp(grnAmt));
				}

				if (objShowGrnList.get(i).getGrnType() == 1) {
					grnBaseRate = baseRate * 70 / 100;
					grnRate = (objShowGrnList.get(i).getRate() * 70) / 100;
					// postGrnGvn.setGrnGvnAmt(roundUp(grnAmt));
				}

				if (objShowGrnList.get(i).getGrnType() == 2 || objShowGrnList.get(i).getGrnType() == 4) {
					// postGrnGvn.setGrnGvnAmt(roundUp(grnAmt));

					grnBaseRate = baseRate;
					grnRate = objShowGrnList.get(i).getRate();
				}

				float taxableAmt = grnBaseRate * grnQty;

				float discAmt = (taxableAmt * objShowGrnList.get(i).getDiscPer()) / 100;//4 Feb 2019
				taxableAmt = taxableAmt - discAmt;
				float totalTax = (taxableAmt
						* (objShowGrnList.get(i).getSgstPer() + objShowGrnList.get(i).getCgstPer())) / 100;

				float grandTotal = taxableAmt + totalTax;

				//float finalAmt = grnRate * grnQty;
				float finalAmt = (objShowGrnList.get(i).getRate() * grnQty)-((objShowGrnList.get(i).getRate() * grnQty) *   objShowGrnList.get(i).getDiscPer()/100);

				postGrnGvn.setGrnGvnAmt(roundUp(grandTotal));
				postGrnGvn.setItemMrp(objShowGrnList.get(i).getDiscPer());//setting disc Per in Grn detail 4 Feb 2019
				float roundUpAmt = finalAmt - grandTotal;

				/*
				 * if(frDetails.getFrGstType()==0) { grnAmt = grnQty *
				 * grnConfList.get(i).getRate(); grnAmt = roundUp(grnAmt);
				 * 
				 * } else {
				 * 
				 * float baseRate= grnConfList.get(i).getRate()*100/
				 * (grnConfList.get(i).getSgstPer() + grnConfList.get(i).getCgstPer()+100);
				 * grnAmt=grnQty*baseRate;
				 * 
				 * }
				 */
				curDateTime = dateFormat.format(cal.getTime());

				postGrnGvn.setGrnGvnDate(grnGvnDate);

				postGrnGvn.setBillDetailNo(objShowGrnList.get(i).getBillDetailNo());// 15 Feb added

				curDateTime = dateFormat.format(cal.getTime());
                
				postGrnGvn.setBillNo(objShowGrnList.get(i).getBillNo());
				postGrnGvn.setFrId(frDetails.getFrId());
				postGrnGvn.setItemId(objShowGrnList.get(i).getItemId());
				postGrnGvn.setHsnCode(objShowGrnList.get(i).getHsnCode());//new on 4jun19
				postGrnGvn.setItemRate(objShowGrnList.get(i).getRate());
				//postGrnGvn.setItemMrp(objShowGrnList.get(i).getMrp());
				postGrnGvn.setGrnGvnQty(grnQty);
				postGrnGvn.setGrnType(objShowGrnList.get(i).getGrnType());
				postGrnGvn.setIsGrn(1);
				postGrnGvn.setIsGrnEdit(isEdit);
				postGrnGvn.setGrnGvnEntryDateTime(dateFormat.format(cal.getTime()));
				postGrnGvn.setFrGrnGvnRemark(frGrnRemark);
				postGrnGvn.setGvnPhotoUpload1("grn:no photo");
				postGrnGvn.setGvnPhotoUpload2("grn:no photo");
				postGrnGvn.setGrnGvnStatus(2);//changed to 2 from 1 on May 9 Sachin
				postGrnGvn.setApprovedLoginGate(0);
				postGrnGvn.setApproveimedDateTimeGate(dateFormat.format(cal.getTime()));
				postGrnGvn.setApprovedRemarkGate(" ");

				postGrnGvn.setApprovedLoginStore(0);
				postGrnGvn.setApprovedDateTimeStore(dateFormat.format(cal.getTime()));
				postGrnGvn.setApprovedRemarkStore(" ");
				postGrnGvn.setApprovedLoginAcc(0);
				postGrnGvn.setGrnApprovedDateTimeAcc(dateFormat.format(cal.getTime()));
				postGrnGvn.setApprovedRemarkAcc(" ");

				postGrnGvn.setDelStatus(0);
				postGrnGvn.setGrnGvnQtyAuto(fixedGrnQty);

				// newly added

				postGrnGvn.setIsTallySync(0);
				postGrnGvn.setBaseRate(roundUp(baseRate));
				postGrnGvn.setSgstPer(objShowGrnList.get(i).getSgstPer());
				postGrnGvn.setCgstPer(objShowGrnList.get(i).getCgstPer());
				postGrnGvn.setIgstPer(objShowGrnList.get(i).getIgstPer());

				postGrnGvn.setTaxableAmt(roundUp(taxableAmt));
				postGrnGvn.setTotalTax(roundUp(totalTax));
				postGrnGvn.setFinalAmt(roundUp(finalAmt));
				postGrnGvn.setRoundUpAmt(roundUp(roundUpAmt));

				postGrnGvn.setIsCreditNote(0);

				postGrnGvn.setCatId(objShowGrnList.get(i).getCatId());
				postGrnGvn.setMenuId(objShowGrnList.get(i).getMenuId());

				postGrnGvn.setRefInvoiceDate(objShowGrnList.get(i).getBillDate());
				postGrnGvn.setInvoiceNo(objShowGrnList.get(i).getInvoiceNo());

				// setting new field added on 23 FEB

				postGrnGvn.setAprQtyGate(Integer.parseInt(tempGrnQty));
				postGrnGvn.setAprQtyStore(0);
				postGrnGvn.setAprQtyAcc(0);
				postGrnGvn.setAprTaxableAmt(0);
				postGrnGvn.setAprTotalTax(0);
				postGrnGvn.setAprSgstRs(0);
				postGrnGvn.setAprCgstRs(0);
				postGrnGvn.setAprIgstRs(0);
				postGrnGvn.setAprGrandTotal(0);
				postGrnGvn.setAprROff(0);
				postGrnGvn.setIsSameState(frDetails.getIsSameState());

				System.out.println("post grn ref inv date " + postGrnGvn.getRefInvoiceDate());

				// 15 Feb
				sumTaxableAmt = sumTaxableAmt + postGrnGvn.getTaxableAmt();
				sumTaxAmt = sumTaxAmt + postGrnGvn.getTotalTax();
				sumTotalAmt = sumTotalAmt + postGrnGvn.getGrnGvnAmt();

				postGrnGvnList.add(postGrnGvn);

				if (objShowGrnList.get(i).getAutoGrnQty() - postGrnGvn.getGrnGvnQty() > 0) {

					System.err.println("Item Name " + objShowGrnList.get(i).getItemName());
					System.err.println("Qty Variation found : For Item Id " + postGrnGvn.getItemId() + "Qty = "
							+ (objShowGrnList.get(i).getAutoGrnQty() - postGrnGvn.getGrnGvnQty()));
					int exBillQty = objShowGrnList.get(i).getAutoGrnQty() - postGrnGvn.getGrnGvnQty();

					objShowGrnList.get(i).setAutoGrnQty(exBillQty);
					sellBillData.add(objShowGrnList.get(i));

					// sellBillData.get(i).setAutoGrnQty(exBillQty);
				}

				// } // end of if checking for grnQty
			} // end of for
			System.err.println("Selll Bill Data " + sellBillData.toString());

			grnHeader.setGrnGvn(postGrnGvnList);

			grnHeader.setFrId(fraId);
			grnHeader.setApporvedAmt(0);
			grnHeader.setApprovedDatetime(curDateTime);
			grnHeader.setCreditNoteId("");
			grnHeader.setGrngvnDate(new SimpleDateFormat("dd-MM-yyyy").format(grnGvnDate));
			grnHeader.setGrngvnSrno(getGrnGvnSrNo(request, response));
			grnHeader.setGrngvnStatus(2);//changed to 2 from 1 on May 9 Sachin
			grnHeader.setIsCreditNote(0);
			grnHeader.setIsGrn(1);
			grnHeader.setApporvedAmt(0);

			grnHeader.setTaxableAmt(roundUp(sumTaxableAmt));
			grnHeader.setTaxAmt(roundUp(sumTaxAmt));
			grnHeader.setTotalAmt(roundUp(sumTotalAmt));
			grnHeader.setGrnGvn(postGrnGvnList);

			modelAndView.addObject("grnConfList", objShowGrnList);
			System.out.println("postGrnGvnList************----- " + postGrnGvnList.toString());

			System.out.println("****postGrnGvnList size*******-- " + postGrnGvnList.size());

			// postGrnList.setGrnGvn(postGrnGvnList);

			postGrnList.setGrnGvnHeader(grnHeader);
			System.out.println("post grn for rest----- " + postGrnList.toString());
			// System.out.println("post grn for rest size " +
			// postGrnList.getGrnGvn().size());

			Info insertGrn = null;
			if (postGrnList != null && postGrnList.getGrnGvnHeader().getTaxableAmt() > 0) {
				System.err.println("Inserting Grn ");
				insertGrn = restTemplate.postForObject(Constant.URL + "insertGrnGvn", postGrnList, Info.class);

			}
			// Info insertGrn=null;
			if (insertGrn.getError() == false) {

				System.err.println("Update Grn Sr No for GRN insert  where insertGrn.getError() is false ");

				map.add("frId", frDetails.getFrId());
				FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map,
						FrSetting.class);

				int grnGvnSrNo = frSetting.getGrnGvnNo();

				grnGvnSrNo = grnGvnSrNo + 1;

				map = new LinkedMultiValueMap<String, Object>();

				map.add("frId", frDetails.getFrId());
				map.add("grnGvnNo", grnGvnSrNo);

				Info updateFrSettingGrnGvnNo = restTemplate.postForObject(Constant.URL + "updateFrSettingGrnGvnNo", map,
						Info.class);

				System.out.println("/updateFrSettingGrnGvnNo: Response @GrnGvnController  info=  "
						+ updateFrSettingGrnGvnNo.toString());

				map = new LinkedMultiValueMap<String, Object>();

				map.add("frId", frDetails.getFrId());

				SellBillDataCommon sellBillResponse = restTemplate
						.postForObject(Constant.URL + "/showNotDayClosedRecord", map, SellBillDataCommon.class);

				if (!sellBillResponse.getSellBillHeaderList().isEmpty()) {

					List<SellBillHeader> sellBillHeaderList = sellBillResponse.getSellBillHeaderList();

					int count = sellBillHeaderList.size();
					SellBillHeader billHeader = sellBillResponse.getSellBillHeaderList().get(0);

					map = new LinkedMultiValueMap<String, Object>();

					map.add("billNo", billHeader.getSellBillNo());

					SellBillDetailList sellBillDetailList = restTemplate
							.postForObject(Constant.URL + "/getSellBillDetails", map, SellBillDetailList.class);

					List<SellBillDetail> sellBillDetails = sellBillDetailList.getSellBillDetailList();
					if (sellBillDetails.size() > 0) {

						for (int x = 0; x < sellBillDetails.size(); x++) {

							billHeader
									.setTaxableAmt(billHeader.getTaxableAmt() + sellBillDetails.get(x).getTaxableAmt());

							billHeader.setTotalTax(billHeader.getTotalTax() + sellBillDetails.get(x).getTotalTax());
							billHeader
									.setGrandTotal(sellBillDetails.get(x).getGrandTotal() + billHeader.getGrandTotal());

							// billHeader.setBillDate(billHeader.getBillDate());

							billHeader.setDiscountPer(billHeader.getDiscountPer());

						}
						billHeader.setGrandTotal(Math.round(billHeader.getGrandTotal()));
						billHeader.setPaidAmt(billHeader.getGrandTotal());
						billHeader.setPayableAmt(billHeader.getGrandTotal());
						System.err.println("bill Header data for Day close " + billHeader.toString());

						String start_dt = billHeader.getBillDate();
						DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
						java.util.Date date = (java.util.Date) formatter.parse(start_dt);

						SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
						String finalString = newFormat.format(date);
						billHeader.setBillDate(finalString);

						billHeader = restTemplate.postForObject(Constant.URL + "saveSellBillHeader", billHeader,
								SellBillHeader.class);

						System.out.println("Bill Header Response " + billHeader.toString());

					} else {

						// update time

						map = new LinkedMultiValueMap<String, Object>();

						DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						Calendar cal = Calendar.getInstance();

						map.add("sellBillNo", billHeader.getSellBillNo());

						java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(curDateTime);

						Calendar caleInstance = Calendar.getInstance();

						caleInstance.setTime(date);
						caleInstance.set(Calendar.SECOND, (caleInstance.get(Calendar.SECOND) + 5));

						String incTime = dateFormat.format(caleInstance.getTime());

						System.out.println("*****Calender Gettime == " + caleInstance.getTime());

						System.out.println("*****Inc time Gettime == " + incTime);

						map.add("timeStamp", incTime);

						Info info = restTemplate.postForObject(Constant.URL + "updateSellBillTimeStamp", map,
								Info.class);

					}

				} // end of if ex bill not null

				postGrnList = new PostGrnGvnList();

				// insert Into Sell Bill Table as Bill Type 'G'

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				LocalDate localDate = LocalDate.now();
				System.out.println(dtf.format(localDate)); // 2016/11/16

				SellBillHeader sellBillHeaderRes = null;
				SellBillHeader sellBillHeader = null;

				if (sellBillData.size() > 0) {

					System.err.println("inside if sellBillData.size() > 0");

					sellBillHeader = new SellBillHeader();

					sellBillHeader.setFrId(frDetails.getFrId());
					sellBillHeader.setFrCode(frDetails.getFrCode());
					sellBillHeader.setDelStatus(0);
					sellBillHeader.setUserName("dummy");
					sellBillHeader.setBillDate(dtf.format(localDate));

					sellBillHeader.setInvoiceNo(getInvoiceNo(request, response));

					sellBillHeader.setPaymentMode(1);

					sellBillHeader.setBillType('G');

					sellBillHeader.setSellBillNo(0);

					sellBillHeader.setUserGstNo("");

					sellBillHeader.setUserPhone("");

					List<SellBillDetail> sellBillDetailList = new ArrayList<SellBillDetail>();

					float sumTaxableAmt1 = 0, sumTotalTax = 0, sumGrandTotal = 0, sumMrp = 0;

					for (int i = 0; i < sellBillData.size(); i++) {

						System.err.println("sellBillData for loop ");

						System.err.println("sell Bill Data at index " + i + sellBillData.get(i).toString());

						SellBillDetail sellBillDetail = new SellBillDetail();

						Float rate = (float) sellBillData.get(i).getMrp();

						float tax1 = sellBillData.get(i).getSgstPer();
						float tax2 = sellBillData.get(i).getCgstPer();
						float tax3 = sellBillData.get(i).getIgstPer();

						int qty = sellBillData.get(i).getAutoGrnQty();

						Float mrpBaseRate = (rate * 100) / (100 + (tax1 + tax2));
						mrpBaseRate = roundUp(mrpBaseRate);

						System.out.println("Mrp: " + rate);
						System.out.println("Tax1 : " + tax1);
						System.out.println("tax2 : " + tax2);

						Float taxableAmt = (float) (mrpBaseRate * qty);
						taxableAmt = roundUp(taxableAmt);

						float discAmt = 0;
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

						sellBillDetail.setCatId(sellBillData.get(i).getCatId());
						sellBillDetail.setSgstPer(tax1);
						if (frDetails.getIsSameState() == 1) {
							sellBillDetail.setSgstRs(sgstRs);
							sellBillDetail.setCgstRs(cgstRs);

						} else {
							sellBillDetail.setIgstRs(igstRs);
						}

						sellBillDetail.setCgstPer(tax2);

						sellBillDetail.setIgstPer(tax3);

						sellBillDetail.setDelStatus(0);
						sellBillDetail.setGrandTotal(grandTotal);
						sellBillDetail.setItemId(sellBillData.get(i).getItemId());
						sellBillDetail.setMrp(rate);
						sellBillDetail.setMrpBaseRate(mrpBaseRate);
						sellBillDetail.setQty(sellBillData.get(i).getAutoGrnQty());
						sellBillDetail.setRemark("");
						sellBillDetail.setSellBillDetailNo(0);
						sellBillDetail.setSellBillNo(0);
						sellBillDetail.setBillStockType(1);

						sumMrp = sumMrp + (rate * qty);
						sumTaxableAmt1 = sumTaxableAmt1 + taxableAmt;
						sumTotalTax = sumTotalTax + totalTax;
						sumGrandTotal = sumGrandTotal + grandTotal;

						sellBillDetail.setTaxableAmt(taxableAmt);
						sellBillDetail.setTotalTax(totalTax);

						sellBillDetailList.add(sellBillDetail);

					}
					sellBillHeader.setTaxableAmt(sumTaxableAmt1);
					sellBillHeader.setDiscountPer(0);

					float payableAmt = sumGrandTotal;

					payableAmt = roundUp(payableAmt);

					sellBillHeader.setDiscountAmt(0);

					System.out.println("Payable amt  : " + payableAmt);
					sellBillHeader.setTotalTax(sumTotalTax);
					sellBillHeader.setGrandTotal(Math.round(sumGrandTotal));
					sellBillHeader.setPayableAmt(Math.round(sumGrandTotal));

					sellBillHeader.setPaidAmt(Math.round(sumGrandTotal));
					sellBillHeader.setSellBillDetailsList(sellBillDetailList);

					sellBillHeaderRes = restTemplate.postForObject(Constant.URL + "insertSellBillData", sellBillHeader,
							SellBillHeader.class);
				} // end of if sellBillData size>0

				if (sellBillHeaderRes != null) {

					map = new LinkedMultiValueMap<String, Object>();

					map.add("frId", frDetails.getFrId());
					FrSetting frSetting1 = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map,
							FrSetting.class);

					int sellBillNo = frSetting1.getSellBillNo();

					sellBillNo = sellBillNo + 1;

					map = new LinkedMultiValueMap<String, Object>();

					map.add("frId", frDetails.getFrId());
					map.add("sellBillNo", sellBillNo);

					Info info = restTemplate.postForObject(Constant.URL + "updateFrSettingBillNo", map, Info.class);

				}

				// System.out.println("info :" + sellBillHeaderRes.toString());

				// update frSetting value for frGrnGvnSrNo
				map = new LinkedMultiValueMap<String, Object>();

				// -----------------------For Notification-----------------
				String frToken = "";

				try {
					map = new LinkedMultiValueMap<String, Object>();
					map.add("frId", frDetails.getFrId());

					frToken = restTemplate.postForObject(Constant.URL + "getFrToken", map, String.class);
					Firebase.sendPushNotifForCommunication(frToken, "GRN Punched",
							"GRN has been punched against value of Rs." + grnHeader.getTotalAmt()
									+ " Thank You..Team Monginis",
							"inbox");

				} catch (Exception e2) {
					e2.printStackTrace();
				}

				// -----------------------------------------------------

			}

		} catch (Exception e) {

			System.out.println("exce in grn insert or Express Bill Day close " + e.getMessage());
			e.printStackTrace();

		}
		ModelAndView modelAndView2 = new ModelAndView("grngvn/viewGrn");

		try {

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();

			java.util.Date grnDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			DateFormat sdFormat = new SimpleDateFormat("dd-MM-yyyy");

			String cDate = sdFormat.format(grnDate);

			map.add("frIdList", frId);
			map.add("fromDate", cDate);
			map.add("toDate", cDate);
			map.add("isGrn", 1);
			// getFrGrnDetail
			// List<GrnGvnHeader> grnHeaderList = new ArrayList<>();

			try {
				grnHeaderList = new ArrayList<>();

				GrnGvnHeaderList headerList = restTemplate.postForObject(Constant.URL + "getGrnGvnHeader", map,
						GrnGvnHeaderList.class);

				grnHeaderList = headerList.getGrnGvnHeader();
				modelAndView2.addObject("grnList", grnHeaderList);
				// modelAndView2.addObject("grnList", grnGvnDetailsList);

				modelAndView2.addObject("cDate", cDate);

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("EXCE in getting gvn Header List " + e.getMessage());
			}

			// model.addObject("gvnList", gvnHeaderList);

			// model.addObject("url", Constant.GVN_IMAGE_URL);
			// modelAndView2.addObject("cDate", cDate); // End comment

		} catch (Exception e2) {

			e2.printStackTrace();
			System.out.println(e2.getMessage());

		}

		return "redirect:/displayGrn";

	}

	String view = "0";
	String billDate = null;

	@RequestMapping(value = "/getViewGvnOption", method = RequestMethod.GET)
	public String getViewGvnOption(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView("grngvn/showgvn");

		view = request.getParameter("view_opt");
		System.out.println("View Option Received " + view);

		billDate = request.getParameter("bill_date");

		System.out.println("BILL DATE SELECTED TO RECIEVE BILL NOS " + billDate);

		modelAndView = showGvnProcess(request, response);
		return "redirect:/showGvn";
	}

	@RequestMapping(value = "/showGvn", method = RequestMethod.GET)
	public ModelAndView showGvnProcess(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView("grngvn/showgvn");

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		RestTemplate restTemplate = new RestTemplate();
		GetBillsForFrList billsForFr = new GetBillsForFrList();
		try {
			java.util.Date cDate = new java.util.Date();
			String curDate = new SimpleDateFormat("dd-MM-yyyy").format(cDate);
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			// String view = request.getParameter("view_opt");

			int frId = frDetails.getFrId();

			if (view.contains("0")) {

				System.out.println("view contains zero value");

				map.add("frId", frId);
			    map.add("curDate", curDate);

				billsForFr = new GetBillsForFrList();

				billsForFr = restTemplate.postForObject(Constant.URL + "getBillsForFr", map, GetBillsForFrList.class);

			} else {

				System.out.println("view is non zero : its for Date ");
				map = new LinkedMultiValueMap<String, Object>();

				System.out.println("BILL DATE SELECTED TO RECIEVE BILL NOS " + billDate + "fr Id " + frId);

				map.add("frId", frId);

				map.add("billDate", billDate);
				billsForFr = new GetBillsForFrList();
				billsForFr = restTemplate.postForObject(Constant.URL + "getBillsForFrByBillDate", map,
						GetBillsForFrList.class);

			}

			frBillList = new ArrayList<>();

			frBillList = billsForFr.getGetBillsForFr();

			System.out.println("FR BILL LIST " + frBillList.toString());

			modelAndView.addObject("frBillList", frBillList);
			modelAndView.addObject("curDate", curDate);

		} catch (Exception e) {

			System.out.println("ex in get Bills For Fr gvn " + e.getMessage());

			e.printStackTrace();
		}
		// view=null;

		return modelAndView;
	}

	@RequestMapping(value = "/getGvnBillDetails", method = RequestMethod.GET)
	public ModelAndView getGvnBillDetails(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView("grngvn/showgvn");

		// modelAndView.addObject("frBillList", frBillList);

		try {

			int billNo = Integer.parseInt(request.getParameter("bill_no"));
			System.out.println("selected bill no " + billNo);

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("billNo", billNo);

			grnGvnConfResponse = restTemplate.postForObject(Constant.URL + "getGvnItemConfig", map,
					GetGrnGvnConfResponse.class);

			grnConfList = new ArrayList<>();

			grnConfList = grnGvnConfResponse.getGetGrnItemConfigs();
			System.out.println("gvn conf list " + grnConfList.toString());

			modelAndView.addObject("frBillList", frBillList);
			modelAndView.addObject("selctedBillNo", billNo);

			objShowGvnList = new ArrayList<>();

			ShowGrnBean objShowGvn = null;

			for (int i = 0; i < grnConfList.size(); i++) {

				objShowGvn = new ShowGrnBean();

				objShowGvn.setBillDate(grnConfList.get(i).getBillDate());
				objShowGvn.setBillDetailNo(grnConfList.get(i).getBillDetailNo());
				objShowGvn.setBillNo(grnConfList.get(i).getBillNo());
				objShowGvn.setBillQty(grnConfList.get(i).getBillQty());
				objShowGvn.setMenuId(grnConfList.get(i).getMenuId());//new jun
				objShowGvn.setCgstPer(grnConfList.get(i).getCgstPer());
				objShowGvn.setFrId(grnConfList.get(i).getFrId());
				objShowGvn.setGrnType(grnConfList.get(i).getGrnType());
				objShowGvn.setIgstPer(grnConfList.get(i).getIgstPer());
				objShowGvn.setItemId(grnConfList.get(i).getItemId());
				objShowGvn.setItemName(grnConfList.get(i).getItemName());
				objShowGvn.setMrp(grnConfList.get(i).getMrp());
				objShowGvn.setRate(grnConfList.get(i).getRate());
				objShowGvn.setSgstPer(grnConfList.get(i).getSgstPer());
				objShowGvn.setCatId(grnConfList.get(i).getCatId());//newly  aaded
				objShowGvn.setInvoiceNo(grnConfList.get(i).getInvoiceNo());
				objShowGvn.setHsnCode(grnConfList.get(i).getHsnCode());//new for hsn code
				float calcBaseRate = grnConfList.get(i).getRate() * 100
						/ (grnConfList.get(i).getSgstPer() + grnConfList.get(i).getCgstPer() + 100);
				
				float discAmt=(calcBaseRate *grnConfList.get(i).getDiscPer())/100;//4 FEB 2019
				
				calcBaseRate=calcBaseRate-discAmt;
				
				objShowGvn.setCalcBaseRate(roundUp(calcBaseRate));
				objShowGvn.setDiscPer(grnConfList.get(i).getDiscPer());

				objShowGvnList.add(objShowGvn);

			}

			map = new LinkedMultiValueMap<String, Object>();

			map = new LinkedMultiValueMap<String, Object>();
			map.add("isFrUsed", 1);
			map.add("moduleId", 1);
			map.add("subModuleId", 2);
			getAllRemarksList = restTemplate.postForObject(Constant.URL + "/getAllRemarks", map,
					GetAllRemarksList.class);

			getAllRemarks = new ArrayList<>();
			getAllRemarks = getAllRemarksList.getGetAllRemarks();

			System.out.println("remark list " + getAllRemarks.toString());

			modelAndView.addObject("remarkList", getAllRemarks);
			modelAndView.addObject("gvnConfList", objShowGvnList);
			modelAndView.addObject("billNo", billNo);

		} catch (Exception e) {
			System.out.println("show gvn error " + e.getMessage());
			e.printStackTrace();
		}

		return modelAndView;

	}

	@RequestMapping(value = "/addTempGvn", method = RequestMethod.POST)
	public ModelAndView addTempGvn(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView("grngvn/proceedGvn");

		System.out.println("HII ");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		RestTemplate restTemplate = new RestTemplate();

		try {

			map = new LinkedMultiValueMap<String, Object>();
			map.add("isFrUsed", 1);
			map.add("moduleId", 1);
			map.add("subModuleId", 2);
			getAllRemarksList = restTemplate.postForObject(Constant.URL + "/getAllRemarks", map,
					GetAllRemarksList.class);

			getAllRemarks = new ArrayList<>();
			getAllRemarks = getAllRemarksList.getGetAllRemarks();

			System.out.println("remark list " + getAllRemarks.toString());

			modelAndView.addObject("remarkList", getAllRemarks);
			modelAndView.addObject("gvnConfList", objShowGvnList);
			// modelAndView.addObject("billNo", billNo);

			String[] gvnItemBillDetailNo = request.getParameterValues("select_to_gvn");

			/*
			 * for (int i = 0; i < gvnItemBillDetailNo.length; i++) {
			 * 
			 * System.out.println("Selected Bill Detail No ::: " + gvnItemBillDetailNo[i]);
			 * }
			 */
			gvnList = new ArrayList<>();

			System.out.println("OBJ BEAN " + objShowGrnList.toString());

			for (int i = 0; i < gvnItemBillDetailNo.length; i++) {

				for (int j = 0; j < objShowGvnList.size(); j++) {

					if (objShowGvnList.get(j).getBillDetailNo() == (Integer.parseInt(gvnItemBillDetailNo[i]))) {

						System.out.println("Bill Detail Matched " + gvnItemBillDetailNo[i]);

						String strGvnQty = request.getParameter("gvn_qty" + objShowGvnList.get(j).getBillDetailNo());

						int gvnQty = Integer.parseInt(strGvnQty);

						System.out.println("GVN QTY " + gvnQty);
						objShowGvnList.get(j).setAutoGrnQty(gvnQty);
						gvnList.add(objShowGvnList.get(j));
						

					}
				}
				// gvnList.get(i).setAutoGrnQty(gvnQty);
			}
			System.out.println("GVN LIST " + gvnList.toString());
			modelAndView.addObject("tempGvnList", gvnList);
		} catch (Exception e) {
			System.out.println("Exce in add Temp gvn " + e.getMessage());
			e.printStackTrace();
		}
		return modelAndView;
	}

	@RequestMapping(value = "/addGvnProcess", method = RequestMethod.POST)
	public String addGvnProcess(@RequestParam("gvn_photo1") List<MultipartFile> photo1,

			@RequestParam("gvn_photo2") List<MultipartFile> photo2, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("grngvn/proceedGvn");

		ModelAndView model = null;

		try {

			java.sql.Date grnGvnDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			System.out.println();

			RestTemplate restTemplate = new RestTemplate();
			grnConfList = grnGvnConfResponse.getGetGrnItemConfigs();

			GrnGvnHeader grnHeader = new GrnGvnHeader();

			HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			List<GrnGvn> postGrnGvnList = new ArrayList<GrnGvn>();

			PostGrnGvnList postGrnList = new PostGrnGvnList();

			float sumTaxableAmt = 0;
			float sumTaxAmt = 0;
			float sumTotalAmt = 0;
			String curDateTime = null;
			boolean isCustComplaint = false;
			for (int i = 0; i < gvnList.size(); i++) {

				// String strGvnQty = request.getParameter("gvn_qty" +
				// gvnList.get(i).getItemId());
				// int gvnQty = Integer.parseInt(strGvnQty);

				int gvnQty = gvnList.get(i).getAutoGrnQty();
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();

				DateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calDate = Calendar.getInstance();

				String frGvnRemark = request.getParameter("gvn_remark" + gvnList.get(i).getBillDetailNo());

				if (frGvnRemark.equalsIgnoreCase("Customer Complaint")) {

					isCustComplaint = true;
					grnHeader.setIsGrn(2);
				}

				if (isCustComplaint) {
					grnHeader.setIsGrn(2);
				}

				else {
					grnHeader.setIsGrn(0);
				}

				if (frGvnRemark == null || frGvnRemark.equals("")) {
					frGvnRemark = "no remark from franchisee";

				}else if(frGvnRemark.equalsIgnoreCase("Other")) {
					frGvnRemark=request.getParameter("gvn_remark_text" + gvnList.get(i).getBillDetailNo());
					System.err.println("It iS Other Remark " +frGvnRemark);
				}

				if (gvnQty > 0) {

					GrnGvn postGrnGvn = new GrnGvn();

					float baseRate = gvnList.get(i).getRate() * 100
							/ (gvnList.get(i).getSgstPer() + gvnList.get(i).getCgstPer() + 100);

					float gvnAmt = gvnQty * baseRate;

					float taxableAmt = baseRate * gvnQty;
					System.err.println("disc Per === " +gvnList.get(i).getDiscPer());
				System.err.println("***taxable " +taxableAmt +"disc Per " +gvnList.get(i).getDiscPer());
					
					float discAmt=(taxableAmt *gvnList.get(i).getDiscPer())/100;//4 FEB 2019
					
					taxableAmt=taxableAmt-discAmt;
					System.err.println("discAmt " +discAmt +"taxableAmt*** "+ taxableAmt);


					float totalTax = (taxableAmt * (gvnList.get(i).getSgstPer() + gvnList.get(i).getCgstPer())) / 100;

					float grandTotal = taxableAmt + totalTax;
					

					float finalAmt = (gvnList.get(i).getRate() * gvnQty)-((gvnList.get(i).getRate() * gvnQty) *   gvnList.get(i).getDiscPer()/100);

					float roundUpAmt = finalAmt - grandTotal;

					gvnAmt = roundUp(gvnAmt);

					postGrnGvn.setGrnGvnAmt(roundUp(grandTotal));
					postGrnGvn.setGrnGvnDate(grnGvnDate);// 1
					postGrnGvn.setItemMrp(gvnList.get(i).getDiscPer());//setting disc Per in Grn_gvn detail 4 Feb 2019

					postGrnGvn.setBillNo(gvnList.get(i).getBillNo());// 2
					postGrnGvn.setFrId(frDetails.getFrId());// 3
					postGrnGvn.setItemId(gvnList.get(i).getItemId());// 4
					postGrnGvn.setItemRate(gvnList.get(i).getRate());// 5
					//postGrnGvn.setItemMrp(gvnList.get(i).getMrp());// 6
					postGrnGvn.setGrnGvnQty(gvnList.get(i).getAutoGrnQty());// 7 postGrnGvn.setGrnType(4);// 9
					postGrnGvn.setIsGrn(0);// 10 postGrnGvn.setIsGrnEdit(0);// 11
					postGrnGvn.setGrnGvnEntryDateTime(dateFormat.format(cal.getTime()));// 12
					postGrnGvn.setFrGrnGvnRemark(frGvnRemark);// 13
					postGrnGvn.setGrnGvnStatus(2);//changed to 2 from 1 on May 9 By Sachin // 16 postGrnGvn.setApprovedLoginGate(0);// 17
					postGrnGvn.setApproveimedDateTimeGate(dateFormat.format(cal.getTime()));// 18
					postGrnGvn.setApprovedRemarkGate("");// 19
					curDateTime = dateFormat.format(cal.getTime());
					postGrnGvn.setApprovedLoginStore(0);// 20
					postGrnGvn.setApprovedDateTimeStore(dateFormat.format(cal.getTime()));// 21
					postGrnGvn.setApprovedRemarkStore("");// 22
					postGrnGvn.setApprovedLoginAcc(0);// 23
					postGrnGvn.setGrnApprovedDateTimeAcc(dateFormat.format(cal.getTime()));// 24
					postGrnGvn.setApprovedRemarkAcc("");// 25
					postGrnGvn.setHsnCode(gvnList.get(i).getHsnCode());//new for hsn code
					postGrnGvn.setDelStatus(0);// 26 postGrnGvn.setGrnGvnQtyAuto(gvnQty);// 27

					VpsImageUpload upload = new VpsImageUpload();

					Calendar cale = Calendar.getInstance();
					SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
					System.out.println(sdf.format(cale.getTime()));

					String curTimeStamp = sdf.format(cale.getTime());
					String gvnPhoto1 = null;
					String gvnPhoto2 = null;

					try {
						gvnPhoto1 = curTimeStamp + "-" + photo1.get(i).getOriginalFilename();
						gvnPhoto2 = curTimeStamp + "-" + photo2.get(i).getOriginalFilename();
						upload.saveUploadedFiles(photo1, Constant.GVN_IMAGE_TYPE,
								curTimeStamp + "-" + photo1.get(i).getOriginalFilename());
						upload.saveUploadedFiles(photo2, Constant.GVN_IMAGE_TYPE,
								curTimeStamp + "-" + photo2.get(i).getOriginalFilename());

						System.out.println("upload method called " + photo1.toString());

					} catch (IOException e) {

						System.out.println("Exce in File Upload In gvn  Insert " + e.getMessage());
						e.printStackTrace();
					}

					// String gvnPhoto1 = ImageS3Util.uploadSpCakeImage(photo1[i]);
					postGrnGvn.setGvnPhotoUpload1(gvnPhoto1); // String gvnPhoto2 =
					// ImageS3Util.uploadSpCakeImage(photo2[i]);

					postGrnGvn.setGvnPhotoUpload2(gvnPhoto2);

					// new fields postGrnGvn.setGrnGvnAmt(roundUp(grandTotal));

					postGrnGvn.setIsTallySync(0);
					postGrnGvn.setBaseRate(roundUp(baseRate));
					postGrnGvn.setSgstPer(gvnList.get(i).getSgstPer());
					postGrnGvn.setCgstPer(gvnList.get(i).getCgstPer());
					postGrnGvn.setIgstPer(gvnList.get(i).getIgstPer());

					postGrnGvn.setTaxableAmt(roundUp(taxableAmt));
					postGrnGvn.setTotalTax(roundUp(totalTax));
					postGrnGvn.setFinalAmt(roundUp(finalAmt));
					postGrnGvn.setRoundUpAmt(roundUp(roundUpAmt));

					postGrnGvn.setIsCreditNote(0);

					postGrnGvn.setCatId(gvnList.get(i).getCatId());
					postGrnGvn.setMenuId(gvnList.get(i).getMenuId());
					postGrnGvn.setBillDetailNo(gvnList.get(i).getBillDetailNo());
					postGrnGvn.setRefInvoiceDate(gvnList.get(i).getBillDate());
					postGrnGvn.setInvoiceNo(gvnList.get(i).getInvoiceNo());

					// setting new field added on 23 FEB

					postGrnGvn.setAprQtyGate(gvnQty);//changed to 2 from 1 on May 9 Sachin
					postGrnGvn.setAprQtyStore(0);
					postGrnGvn.setAprQtyAcc(0);
					postGrnGvn.setAprTaxableAmt(0);
					postGrnGvn.setAprTotalTax(0);
					postGrnGvn.setAprSgstRs(0);
					postGrnGvn.setAprCgstRs(0);
					postGrnGvn.setAprIgstRs(0);
					postGrnGvn.setAprGrandTotal(0);
					postGrnGvn.setAprROff(0);
					postGrnGvn.setIsSameState(frDetails.getIsSameState());

					//

					sumTaxableAmt = sumTaxableAmt + postGrnGvn.getTaxableAmt();
					sumTaxAmt = sumTaxAmt + postGrnGvn.getTotalTax();
					sumTotalAmt = sumTotalAmt + postGrnGvn.getGrnGvnAmt();

					postGrnGvnList.add(postGrnGvn);

				} // end of if

			} // end of for
				// modelAndView.addObject("tempGvnList",postGrnGvnList);
			postGrnList.setGrnGvnHeader(grnHeader);

			grnHeader.setGrnGvn(postGrnGvnList);

			grnHeader.setFrId(frDetails.getFrId());
			grnHeader.setApporvedAmt(0);
			grnHeader.setApprovedDatetime(curDateTime);
			grnHeader.setCreditNoteId("");
			grnHeader.setGrngvnDate(new SimpleDateFormat("dd-MM-yyyy").format(grnGvnDate));
			grnHeader.setGrngvnSrno(getGrnGvnSrNo(request, response));
			grnHeader.setGrngvnStatus(2);//changed to 2 from 1 on May 9 Sachin
			grnHeader.setIsCreditNote(0);
			// grnHeader.setIsGrn(0);
			grnHeader.setApporvedAmt(0);

			grnHeader.setTaxableAmt(roundUp(sumTaxableAmt));
			grnHeader.setTaxAmt(roundUp(sumTaxAmt));
			grnHeader.setTotalAmt(roundUp(sumTotalAmt));

			postGrnList.setGrnGvnHeader(grnHeader);

			System.out.println("post grn for rest----- " + postGrnList.toString());
			// System.out.println("post grn for rest size " +
			// postGrnList.getGrnGvn().size());
			Info insertGvn = null;
			if (postGrnList != null && postGrnList.getGrnGvnHeader().getTaxableAmt() > 0) {

				insertGvn = restTemplate.postForObject(Constant.URL + "insertGrnGvn", postGrnList, Info.class);

			}

			if (insertGvn.getError() == false) {
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				map = new LinkedMultiValueMap<String, Object>();

				map.add("frId", frDetails.getFrId());
				FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map,
						FrSetting.class);

				int grnGvnSrNo = frSetting.getGrnGvnNo();

				grnGvnSrNo = grnGvnSrNo + 1;

				map = new LinkedMultiValueMap<String, Object>();

				map.add("frId", frDetails.getFrId());
				map.add("grnGvnNo", grnGvnSrNo);

				Info info = restTemplate.postForObject(Constant.URL + "updateFrSettingGrnGvnNo", map, Info.class);

				System.out.println("/updateFrSettingGrnGvnNo: Response @GrnGvnController  info=  " + info.toString());

				// -----------------------For Notification-----------------
				String frToken = "";

				try {
					map = new LinkedMultiValueMap<String, Object>();
					map.add("frId", frDetails.getFrId());

					frToken = restTemplate.postForObject(Constant.URL + "getFrToken", map, String.class);
					Firebase.sendPushNotifForCommunication(frToken, "GVN Punched",
							"GVN has been punched against value of Rs." + grnHeader.getTotalAmt()
									+ " Thank You..Team Monginis",
							"inbox");

				} catch (Exception e2) {
					e2.printStackTrace();
				}

				// -----------------------------------------------------

			}
			// Redirect to Gvn List after insert

			model = new ModelAndView("grngvn/viewGvn");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();

			java.util.Date grnDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			DateFormat sdFormat = new SimpleDateFormat("dd-MM-yyyy");

			String cDate = sdFormat.format(grnDate);
			String s = new String();
			s = "0" + "," + "2";
			map.add("frIdList", frId);
			map.add("fromDate", cDate);
			map.add("toDate", cDate);
			map.add("isGrn", s);
			// getFrGrnDetail
			// List<GrnGvnHeader> gvnHeaderList = new ArrayList<>();

			try {
				gvnHeaderList = new ArrayList<>();

				GrnGvnHeaderList headerList = restTemplate.postForObject(Constant.URL + "getGrnGvnHeader", map,
						GrnGvnHeaderList.class);

				gvnHeaderList = headerList.getGrnGvnHeader();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("EXCE in getting gvn Header List " + e.getMessage());
			}

			// model.addObject("gvnList", gvnHeaderList);

			// model.addObject("url", Constant.GVN_IMAGE_URL);
			model.addObject("cDate", cDate); // End comment
			model.addObject("gvnList", gvnHeaderList);
		} catch (Exception e) {
			System.out.println("failed to insert Gvn " + e.getMessage());
			e.printStackTrace();
		}
		return "redirect:/displayGvn";
	}

	String gstType, frAddress;

	List<GrnGvnHeader> grnHeaderList = new ArrayList<>();

	@RequestMapping(value = "/getGrnHeaderList", method = RequestMethod.GET)
	public @ResponseBody List<GrnGvnHeader> getGrnHeaderList(HttpServletRequest request, HttpServletResponse response) {
		// ModelAndView modelAndView = new ModelAndView("grngvn/displaygrn");

		System.out.println("in method");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String grnSrNO = request.getParameter("headerId");

		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
		System.err.println("GST TYpe from session " + frDetails.getFrGstType());
		if (frDetails.getFrGstType().equals(10000000)) {

			gstType = "Regular";

		} else {
			gstType = "Composite";
		}
		frAddress = frDetails.getFrAddress();
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		grnHeaderList = new ArrayList<>();

		int frId = frDetails.getFrId();

		// if(fromDate==null || fromDate=="") {
		if (!grnSrNO.equals("0") || grnSrNO == "") {

			System.out.println("NULL DATE");
			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("isGrn", 1);
			map.add("grnGvnSrNo", grnSrNO);

			try {
				grnHeaderList = new ArrayList<>();

				GrnGvnHeaderList headerList = restTemplate.postForObject(Constant.URL + "getGrnGvnHeaderByHeaderId",
						map, GrnGvnHeaderList.class);

				grnHeaderList = headerList.getGrnGvnHeader();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		} else {
			map = new LinkedMultiValueMap<String, Object>();

			System.out.println(" no NULL DATE");
			map.add("frIdList", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("isGrn", 1);
			// getFrGrnDetail

			try {
				grnHeaderList = new ArrayList<>();

				GrnGvnHeaderList headerList = restTemplate.postForObject(Constant.URL + "getGrnGvnHeader", map,
						GrnGvnHeaderList.class);

				grnHeaderList = headerList.getGrnGvnHeader();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

			System.out.println("grn  list  grnHeaderList " + grnHeaderList.toString());
		} // end of else
		return grnHeaderList;

	}

	@RequestMapping(value = "/getGrnDetailList/{headerId}", method = RequestMethod.GET)
	public ModelAndView getGrnDetailList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("headerId") int headerId) {
		ModelAndView modelAndView = new ModelAndView("grngvn/displaygrn");
		List<GetGrnGvnDetails> grnDetailList = new ArrayList<>();
		System.out.println("in method");
		// String grnGvnHeaderId = request.getParameter("headerId");
		System.out.println("He ader " + headerId);

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		map.add("grnGvnHeaderId", headerId);
		// getFrGrnDetail
		try {
			GrnGvnHeader grnHeader = new GrnGvnHeader();

			for (int i = 0; i < grnHeaderList.size(); i++) {

				if (grnHeaderList.get(i).getGrnGvnHeaderId() == headerId) {

					grnHeader = grnHeaderList.get(i);
				}
			}

			GetGrnGvnDetailsList ab = restTemplate.postForObject(Constant.URL + "getFrGrnDetails", map,
					GetGrnGvnDetailsList.class);

			grnDetailList = ab.getGrnGvnDetails();

			for (int i = 0; i < grnDetailList.size(); i++) {
				float refRate = 0;
				if (grnDetailList.get(i).getGrnType() == 0) {

					refRate = grnDetailList.get(i).getItemRate() * 80 / 100;
				}
				if (grnDetailList.get(i).getGrnType() == 1) {

					refRate = grnDetailList.get(i).getItemRate() * 70 / 100;
				}
				if (grnDetailList.get(i).getGrnType() == 2 || grnDetailList.get(i).getGrnType() == 4) {

					refRate = grnDetailList.get(i).getItemRate();
				}

				grnDetailList.get(i).setBaseRate(refRate);
			}

			System.out.println("GRN Detail   " + grnDetailList.toString());

			modelAndView.addObject("grnSrNo", grnHeader.getGrngvnSrno());
			modelAndView.addObject("aprAmt", grnHeader.getAprGrandTotal());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ex in grn Detail " + e.getMessage());
		}
		String grnDate = grnDetailList.get(0).getGrnGvnDate();

		modelAndView.addObject("grnList", grnDetailList);
		modelAndView.addObject("grnDate", grnDate);

		return modelAndView;

	}

	@RequestMapping(value = "/displayGrn", method = RequestMethod.GET)
	public ModelAndView showGrnDetails(HttpServletRequest request, HttpServletResponse response) {

		// ModelAndView modelAndView = new ModelAndView("grngvn/displaygrn");

		ModelAndView modelAndView = new ModelAndView("grngvn/viewGrn");
		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
		System.out.println("GST" + frDetails.getFrGstType());

		java.util.Date date = new java.util.Date();
		DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

		String fromDate = df.format(date);
		String toDate = df.format(date);
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int frId = frDetails.getFrId();
		map = new LinkedMultiValueMap<String, Object>();

		System.out.println(" no NULL DATE");
		map.add("frIdList", frId);
		map.add("fromDate", fromDate);
		map.add("toDate", toDate);
		map.add("isGrn", 1);
		// getFrGrnDetail

		try {
			grnHeaderList = new ArrayList<>();

			GrnGvnHeaderList headerList = restTemplate.postForObject(Constant.URL + "getGrnGvnHeader", map,
					GrnGvnHeaderList.class);

			grnHeaderList = headerList.getGrnGvnHeader();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("grn  list  grnHeaderList " + grnHeaderList.toString());

		modelAndView.addObject("gstType", frDetails.getFrGstType());

		modelAndView.addObject("grnList", grnHeaderList);

		modelAndView.addObject("cDate", fromDate);
		modelAndView.addObject("cDate", toDate);

		return modelAndView;

	}

	@RequestMapping(value = "/displayGvn", method = RequestMethod.GET)
	public ModelAndView showGvnDetails(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView("grngvn/viewGvn");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			System.out.println("GST" + frDetails.getFrGstType());

			java.util.Date date = new java.util.Date();
			DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

			String fromDate = df.format(date);
			String toDate = df.format(date);
			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map = new LinkedMultiValueMap<String, Object>();

			System.out.println(" no NULL DATE");
			map.add("frIdList", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("isGrn", "0" + "," + "2");

			// getFrGrnDetail

			try {
				gvnHeaderList = new ArrayList<>();

				GrnGvnHeaderList headerList = restTemplate.postForObject(Constant.URL + "getGrnGvnHeader", map,
						GrnGvnHeaderList.class);

				gvnHeaderList = headerList.getGrnGvnHeader();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

			System.out.println("gvn  list  gvnHeaderList " + gvnHeaderList.toString());

			modelAndView.addObject("gstType", frDetails.getFrGstType());

			modelAndView.addObject("gvnList", gvnHeaderList);

			modelAndView.addObject("cDate", fromDate);
			modelAndView.addObject("cDate", toDate);

			modelAndView.addObject("gstType", frDetails.getFrGstType());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modelAndView;

	}

	@RequestMapping(value = "/getGvnList", method = RequestMethod.GET)
	public @ResponseBody List<GetGrnGvnDetails> getGvnDetails(HttpServletRequest request,
			HttpServletResponse response) {
		// ModelAndView modelAndView = new ModelAndView("grngvn/displaygvn");

		System.out.println("in method");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		System.out.println("From " + fromDate + "   To   " + toDate);
		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int frId = frDetails.getFrId();
		map.add("frId", frId);
		map.add("fromDate", fromDate);
		map.add("toDate", toDate);
		// getFrGrnDetail
		try {

			ParameterizedTypeReference<GetGrnGvnDetailsList> typeRef = new ParameterizedTypeReference<GetGrnGvnDetailsList>() {
			};
			ResponseEntity<GetGrnGvnDetailsList> responseEntity = restTemplate
					.exchange(Constant.URL + "getFrGvnDetails", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getGrnGvnDetailsList = responseEntity.getBody();

			// System.err.println("Assigned "+grnpdfList.toString());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		grnGvnDetailsList = getGrnGvnDetailsList.getGrnGvnDetails();

		for (int i = 0; i < grnGvnDetailsList.size(); i++) {
			grnGvnDetailsList.get(i)
					.setGvnPhotoUpload1(Constant.GVN_IMAGE_URL + grnGvnDetailsList.get(i).getGvnPhotoUpload1());
			grnGvnDetailsList.get(i)
					.setGvnPhotoUpload2(Constant.GVN_IMAGE_URL + grnGvnDetailsList.get(i).getGvnPhotoUpload2());

		}
		System.out.println("gvn  list " + grnGvnDetailsList);
		return grnGvnDetailsList;

	}

	List<GrnGvnHeader> gvnHeaderList = new ArrayList<>();

	@RequestMapping(value = "/getGvnHeaderList", method = RequestMethod.GET)
	public @ResponseBody List<GrnGvnHeader> getGvnHeaderList(HttpServletRequest request, HttpServletResponse response) {
		// ModelAndView modelAndView = new ModelAndView("grngvn/displaygrn");

		System.out.println("in method /getGvnHeaderList");
		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		String grnSrNO = request.getParameter("headerId");

		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
		System.out.println("GST" + frDetails.getFrGstType());
		if (frDetails.getFrGstType().equals(10000000)) {

			gstType = "Regular";

		} else {
			gstType = "Composite";
		}
		frAddress = frDetails.getFrAddress();

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		int frId = frDetails.getFrId();

		// if(fromDate==null || fromDate=="") {

		String s = new String();
		s = "0" + "," + "2";
		if (!grnSrNO.equals("0") || grnSrNO == "") {

			System.out.println("NULL DATE");
			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			// map.add("isGrn", 0);
			map.add("isGrn", "0" + "," + "2");

			map.add("grnGvnSrNo", grnSrNO);

			try {
				gvnHeaderList = new ArrayList<>();

				GrnGvnHeaderList headerList = restTemplate.postForObject(Constant.URL + "getGrnGvnHeaderByHeaderId",
						map, GrnGvnHeaderList.class);

				gvnHeaderList = headerList.getGrnGvnHeader();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}

		} else {
			map = new LinkedMultiValueMap<String, Object>();

			System.out.println(" no NULL DATE");
			map.add("frIdList", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("isGrn", "0" + "," + "2");
			// getFrGrnDetail

			try {
				gvnHeaderList = new ArrayList<>();

				GrnGvnHeaderList headerList = restTemplate.postForObject(Constant.URL + "getGrnGvnHeader", map,
						GrnGvnHeaderList.class);

				gvnHeaderList = headerList.getGrnGvnHeader();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("EXCE in getting gvn Header List " + e.getMessage());
			}

			System.out.println("grn  list  grnHeaderList " + gvnHeaderList.toString());
		} // end of else
		return gvnHeaderList;

	}

	@RequestMapping(value = "/getGvnDetailList/{headerId}", method = RequestMethod.GET)
	public ModelAndView getGvnDetailList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("headerId") int headerId) {

		ModelAndView modelAndView = new ModelAndView("grngvn/displaygvn");

		List<GetGrnGvnDetails> grnDetailList = new ArrayList<>();

		System.out.println("in method /getGvnDetailList");
		// String grnGvnHeaderId = request.getParameter("headerId");
		System.out.println("He ader " + headerId);

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		map.add("grnGvnHeaderId", headerId);
		// getFrGrnDetail
		try {

			GrnGvnHeader gvnHeader = new GrnGvnHeader();

			for (int i = 0; i < gvnHeaderList.size(); i++) {

				if (gvnHeaderList.get(i).getGrnGvnHeaderId() == headerId) {

					gvnHeader = gvnHeaderList.get(i);

					break;
				}

			}
			GetGrnGvnDetailsList ab = restTemplate.postForObject(Constant.URL + "getFrGvnDetails", map,
					GetGrnGvnDetailsList.class);

			grnDetailList = ab.getGrnGvnDetails();

			modelAndView.addObject("gvnSrNo", gvnHeader.getGrngvnSrno());

			modelAndView.addObject("aprAmt", gvnHeader.getAprGrandTotal());

			System.out.println("GRN Detail   " + grnDetailList.toString());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Ex in getting GVN  Detail " + e.getMessage());
		}

		String gvnDate = grnDetailList.get(0).getGrnGvnDate();

		modelAndView.addObject("url", Constant.GVN_IMAGE_URL);
		modelAndView.addObject("gvnList", grnDetailList);
		modelAndView.addObject("gvnDate", gvnDate);

		return modelAndView;

	}

	int globalHeaderId;

	@RequestMapping(value = "pdf/getGrnPdf/{fromDate}/{toDate}/{headerId}/{type}/{gsttype}", method = RequestMethod.GET)
	public ModelAndView getGrnPdf(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("fromDate") String fromDate, @PathVariable("toDate") String toDate,
			@PathVariable("headerId") int headerId, @PathVariable("type") int type,
			@PathVariable("gsttype") int gsttype) {

		ModelAndView model;
		System.out.println("type=== " + type);

		System.err.println("Gst Type Received " + gsttype);

		if (gsttype == 10000000) {

			// model = new ModelAndView("grngvn/pdf/grnComposite");
			model = new ModelAndView("grngvn/pdf/grnPdf");
		} else {

			// model = new ModelAndView("grngvn/pdf/grnPdf");
			model = new ModelAndView("grngvn/pdf/grnComposite");
		}

		try {
			// System.out.println("Inside getGrnPdf " +grnpdfList.toString());
			List<GetGrnGvnDetails> grnDetailList = new ArrayList<>();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			RestTemplate restTemplate = new RestTemplate();
			GrnGvnPdf grnPdf = new GrnGvnPdf();

			// List<GetGrnGvnDetails> grnpdfList;

			map.add("grnGvnHeaderId", headerId);
			if (type == 1) {
				GetGrnGvnDetailsList details = restTemplate.postForObject(Constant.URL + "getFrGrnDetails", map,
						GetGrnGvnDetailsList.class);

				grnDetailList = details.getGrnGvnDetails();
			}

			if (type == 0) {
				GetGrnGvnDetailsList details = restTemplate.postForObject(Constant.URL + "getFrGvnDetails", map,
						GetGrnGvnDetailsList.class);

				grnDetailList = details.getGrnGvnDetails();
			}

			System.out.println("GRN Detail   " + grnDetailList.toString());

			GrnGvnHeader header = new GrnGvnHeader();
			map = new LinkedMultiValueMap<String, Object>();
			map.add("headerId", headerId);
			header = restTemplate.postForObject(Constant.URL + "getHeaderByHeaderId", map, GrnGvnHeader.class);

			System.err.println("Header received " + header.toString());

			grnPdf.setDate(header.getGrngvnDate());

			grnPdf.setFrName(grnDetailList.get(0).getFrName());

			grnPdf.setSrNo(header.getGrngvnSrno());
			grnPdf.setTaxableAmt(header.getTaxableAmt());
			grnPdf.setDetail(grnDetailList);
			grnPdf.setType(type);
			grnPdf.setFrAddress(frAddress);

			model.addObject("grnPdf", grnPdf);

		} catch (Exception e) {
			System.err.println("Err in get Grn/gvn Pdf " + e.getMessage());
			e.printStackTrace();
		}
		return model;

	}
	GetCrnDetailsList crnDetailResponse=null;
	List<GetCreditNoteHeaders> creditHeaderList=null;List<GetCrnDetails> crnDetailList=null;
	@RequestMapping(value = "pdf/getCrnCheckedHeaders/{checked}", method = RequestMethod.GET)
	public ModelAndView getCrnCheckedHeaders(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("checked") int grnGvnHeaderId) {
		DateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");

		ModelAndView model = new ModelAndView("creditnote/creditnotePdf");

		try {
			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("grnGvnHeaderId", grnGvnHeaderId);
			GetCreditNoteHeadersList	headerResponse = restTemplate.postForObject(Constant.URL + "getCrnHeadersByGrnGvnHeaderId", map,
					GetCreditNoteHeadersList.class);

			creditHeaderList = headerResponse.getCreditNoteHeaders();

			// Getting crn Details

			map = new LinkedMultiValueMap<String, Object>();
			map.add("grnGvnHeaderId", grnGvnHeaderId);
			crnDetailResponse = restTemplate.postForObject(Constant.URL + "getCrnDetailsByGrnGvnHeaderId", map,
					GetCrnDetailsList.class);
			crnDetailList = new ArrayList<>();

			crnDetailList = crnDetailResponse.getCrnDetails();
			List<CreditPrintBean> printList = new ArrayList<>();
        	CreditPrintBean printBean = new CreditPrintBean();

			for (int i = 0; i < creditHeaderList.size(); i++) {
				printBean = new CreditPrintBean();
				CreditNoteHeaderPrint cNoteHeaderPrint = new CreditNoteHeaderPrint();

				 try {
				 map = new LinkedMultiValueMap<String, Object>();
				 map.add("crnId", creditHeaderList.get(i).getCrnId());
				 List<CrnDetailsSummary> crnSummaryList = restTemplate.postForObject(Constant.URL + "getCrnDetailsSummary", map,
							List.class);
				 cNoteHeaderPrint.setCrnDetailsSummaryList(crnSummaryList);
				 System.err.println(crnSummaryList.toString());
				 }
				 catch (Exception e) {
					e.printStackTrace();
				}

				cNoteHeaderPrint.setFrAddress(creditHeaderList.get(i).getFrAddress());
				cNoteHeaderPrint.setFrId(creditHeaderList.get(i).getFrId());

				cNoteHeaderPrint.setFrName(creditHeaderList.get(i).getFrName());
				cNoteHeaderPrint.setCrnId(creditHeaderList.get(i).getCrnId());
				cNoteHeaderPrint.setCrnDate(creditHeaderList.get(i).getCrnDate());

				cNoteHeaderPrint.setFrGstNo(creditHeaderList.get(i).getFrGstNo());
				cNoteHeaderPrint.setIsGrn(creditHeaderList.get(i).getIsGrn());

				List<GetCrnDetails> crnPrintDetailList = new ArrayList<>();

				List<String> srNoList = new ArrayList<String>();
				List<CrnSrNoDateBean> srNoDateList = new ArrayList<CrnSrNoDateBean>();

				String fDate = null, tDate = null;

				for (int j = 0; j < crnDetailList.size(); j++) {

					if (creditHeaderList.get(i).getCrnId() == crnDetailList.get(j).getCrnId()) {

						crnPrintDetailList.add(crnDetailList.get(j));

						java.util.Date initDateFrom = fmt.parse(crnDetailList.get(0).getGrnGvnDate());
						java.util.Date toLastDate = fmt.parse(crnDetailList.get(0).getGrnGvnDate());

						
						boolean isPrev=false;
						for(CrnSrNoDateBean bean: srNoDateList) {
							
								if(bean.getSrNo().equalsIgnoreCase(crnDetailList.get(j).getGrngvnSrno())) {
									isPrev=true;
								}
							
						}
						
						if(!isPrev) {
							
							CrnSrNoDateBean bean=new CrnSrNoDateBean();
							bean.setGrnGvnDate(crnDetailList.get(j).getGrnGvnDate());
							bean.setSrNo(crnDetailList.get(j).getGrngvnSrno());
							
							srNoDateList.add(bean);
							
						}
		
						

						if (initDateFrom.before(fmt.parse(crnDetailList.get(j).getGrnGvnDate()))) {

						} else {
							initDateFrom = fmt.parse(crnDetailList.get(j).getGrnGvnDate());
						}

						if (toLastDate.after(fmt.parse(crnDetailList.get(j).getGrnGvnDate()))) {

						} else {
							toLastDate = fmt.parse(crnDetailList.get(j).getGrnGvnDate());
						}
						fDate = fmt.format(initDateFrom);
						tDate = fmt.format(toLastDate);
					} // end of if

				} // end of Inner for

				cNoteHeaderPrint.setFromDate(fDate);
				cNoteHeaderPrint.setToDate(tDate);

				cNoteHeaderPrint.setCrnDetails(crnPrintDetailList);
				cNoteHeaderPrint.setCrnNo(creditHeaderList.get(i).getCrnNo());
				cNoteHeaderPrint.setSrNoDateList(srNoDateList);
				cNoteHeaderPrint.setSrNoList(srNoList);
				cNoteHeaderPrint.setExInt1(creditHeaderList.get(i).getExInt1());
				cNoteHeaderPrint.setExVarchar1(creditHeaderList.get(i).getExVarchar1());
				printBean.setCreditHeader(cNoteHeaderPrint);

				printList.add(printBean);
				System.err.println("printList = " + printList.toString());
			} // end of outer for

			System.err.println("printList = " + printList.toString());
			model.addObject("crnPrint", printList);
			model.addObject("FACTORYNAME", Constant.FACTORYNAME);
			model.addObject("FACTORYADDRESS", Constant.FACTORYADDRESS);

		} catch (Exception e) {
			System.err.println("Exce Occured ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		return model;
	}
}
