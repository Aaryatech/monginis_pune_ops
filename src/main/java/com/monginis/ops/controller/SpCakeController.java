package com.monginis.ops.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.Year;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monginis.ops.common.Firebase;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.constant.VpsImageUpload;
import com.monginis.ops.model.AllspMessageResponse;
import com.monginis.ops.model.ErrorMessage;
import com.monginis.ops.model.Flavour;
import com.monginis.ops.model.FlavourList;
import com.monginis.ops.model.FrMenu;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.Info;
import com.monginis.ops.model.Main;
import com.monginis.ops.model.SearchSpCakeResponse;
import com.monginis.ops.model.SpCakeOrder;
import com.monginis.ops.model.SpCakeOrderRes;
import com.monginis.ops.model.SpCakeResponse;
import com.monginis.ops.model.SpMessage;
import com.monginis.ops.model.SpecialCake;
import com.monginis.ops.model.frsetting.FrSetting;

@Controller
@Scope("session")
public class SpCakeController {

	private static final Logger logger = LoggerFactory.getLogger(SpCakeController.class);

	FlavourList flavourList;
	List<SpMessage> spMessageList;
	List<String> configuredSpCodeList;

	private int globalIndex = 2;
	ArrayList<FrMenu> menuList;

	private int currentMenuId = 0;
	SpCakeOrder spCakeOrder = new SpCakeOrder();

	List<SpecialCake> specialCakeList;
	SpecialCake specialCake;
	String flavour = "";
	String spImage= "";// Default Image to spCake order Page

	//--------------------------------------------------------
	String menuTitle="";
	int spType; 
	SpCakeOrder spCake=new SpCakeOrder();
	String spName="";
	String productionTime="";
	String flavourName="";
	String isCustCh="";
	String spPhoUpload="";
	SpCakeOrderRes spCakeOrderRes=new SpCakeOrderRes();
	// ------------------------Show Special Cake Order
	// Page-------------------------------------------
	@RequestMapping(value = "/showSpCakeOrder/{index}", method = RequestMethod.GET)
	public ModelAndView displaySpCakeOrder(@PathVariable("index") int index, HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("order/spcakeorder");

		HttpSession session = request.getSession();

		logger.info("/Special Cake order request mapping. index:" + index);

		RestTemplate restTemplate = new RestTemplate();
		String menuTitle = "Order Special Cake";
		try {
			menuList = (ArrayList<FrMenu>) session.getAttribute("menuList");

			int isSameDayApplicable = menuList.get(globalIndex).getIsSameDayApplicable();

			currentMenuId = menuList.get(index).getMenuId();
			System.out.println("MenuList" + currentMenuId);
			globalIndex = index;
			menuTitle = menuList.get(index).getMenuTitle();

			SpCakeResponse spCakeResponse = restTemplate.getForObject(Constant.URL + "/showSpecialCakeList",
					SpCakeResponse.class);

			HttpSession ses = request.getSession();

			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			String itemShow = menuList.get(globalIndex).getItemShow();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("menuId", currentMenuId);
			map.add("items", itemShow);
			logger.info("itemShow" + itemShow);

			String[] configuredSpCodeArr = restTemplate.postForObject(Constant.URL + "/searchSpCodes", map,
					String[].class);

			configuredSpCodeList = Arrays.asList(configuredSpCodeArr); // Configured SpCode for Franchisee

			System.out.println("SpCake configuredSpCodeList " + configuredSpCodeList.toString());

			flavourList = restTemplate.getForObject(Constant.URL + "/showFlavourList", FlavourList.class);
			logger.info("flavour Controller flavourList Response " + flavourList.toString());

			AllspMessageResponse allspMessageList = restTemplate.getForObject(Constant.URL + "getAllSpMessage",
					AllspMessageResponse.class);

			spMessageList = allspMessageList.getSpMessage();
			// eventList = restTemplate.getForObject(Constant.URL + "/showEventList",
			// EventList.class);
			// logger.info("Event Controller EventList Response " + eventList.toString());

			specialCakeList = new ArrayList<SpecialCake>();

			specialCakeList = spCakeResponse.getSpecialCake();
			logger.info("MenuList Response " + menuList.toString());
			String spNo="";
		      try {
		    	  spNo=RegularSpCakeController.getSpNo(request,response);
		      }catch (Exception e) {
		    	  spNo="";
				e.printStackTrace();
			}
			//System.out.println("Special Cake List:" + specialCakeList.toString());
			    model.addObject("spNo", spNo);

			model.addObject("menuList", menuList);
			model.addObject("specialCakeList", specialCakeList);
			model.addObject("eventList", spMessageList);
			model.addObject("flavourList", flavourList);
			model.addObject("spBookb4", 0);
			model.addObject("configuredSpCodeList", configuredSpCodeList);
			model.addObject("isSameDayApplicable", isSameDayApplicable);
			model.addObject("menuTitle", menuTitle);
			model.addObject("menuId", currentMenuId);
			model.addObject("url", Constant.SPCAKE_IMAGE_URL);

		} catch (Exception e) {
			System.out.println("Show Sp Cake List Excep: " + e.getMessage());
			model.addObject("spNo", " ");
			model.addObject("menuList", menuList);
			model.addObject("specialCakeList", specialCakeList);
			model.addObject("eventList", spMessageList);
			model.addObject("flavourList", flavourList);
			model.addObject("spBookb4", 0);
			model.addObject("configuredSpCodeList", configuredSpCodeList);
			model.addObject("url", Constant.SPCAKE_IMAGE_URL);
			model.addObject("menuTitle", menuTitle);
			model.addObject("menuId", currentMenuId);
		}

		return model;
	}
	// -------------------------------------END-------------------------------------------------

	// ------------------------Search Special
	// CakeProcess---------------------------------------

	@RequestMapping(value = "/searchSpCake", method = RequestMethod.POST)
	public ModelAndView searchSpCakeBySpCode(HttpServletRequest request, HttpServletResponse response) {

		logger.info("inside Search Sp Cake Request");

		String spCode = request.getParameter("sp_code");
		ModelAndView model = new ModelAndView("order/spcakeorder");
		String menuTitle = "";
		List<Float> weightList = new ArrayList<>();

		RestTemplate restTemplate = new RestTemplate();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		 String arraySp[]= spCode.split("~~~");
			map.add("spCode", arraySp[0]);
		try {
			SearchSpCakeResponse searchSpCakeResponse = restTemplate.postForObject(Constant.URL + "/searchSpecialCake",
					map, SearchSpCakeResponse.class);
			ErrorMessage errorMessage = searchSpCakeResponse.getErrorMessage();
			specialCake = searchSpCakeResponse.getSpecialCake();
          //-------------------------------------------------Order No Generation------------------------------
			String spNo="";
		      try {
		    	  spNo=RegularSpCakeController.getSpNo(request,response);
		      }catch (Exception e) {
		    	  spNo="";
				e.printStackTrace();
			}
			//System.out.println("Special Cake List:" + specialCakeList.toString());
			    model.addObject("spNo", spNo);
         //---------------------------------------------------------------------------------------------
			    
			if (errorMessage.getError() == false) {

				String itemShow = menuList.get(globalIndex).getItemShow();
				currentMenuId = menuList.get(globalIndex).getMenuId();
				menuTitle = menuList.get(globalIndex).getMenuTitle();
				HttpSession session = request.getSession();

				Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

				List<String> arrShowItem = Arrays.asList(itemShow.split("\\s*,\\s*"));

				int isfound = 0;
				System.out.println("item Show " + itemShow);

				for (String spId : arrShowItem) {
					if (Integer.parseInt(spId) == specialCake.getSpId()) {
						isfound = 1;
						System.out.println("Sp Cake Math " + spId);

					}
				}

				if (isfound == 0) {

					System.out.println("Sp Cake Not  Math ");
					model = new ModelAndView("order/spcakeorder");

					model.addObject("menuList", menuList);
					model.addObject("eventList", spMessageList);
					model.addObject("flavourList", flavourList);
					model.addObject("url", Constant.SPCAKE_IMAGE_URL);
					model.addObject("spBookb4", 0);
					model.addObject("sprRate", 0);
					model.addObject("specialCakeList", specialCakeList);
					model.addObject("weightList", weightList);
					model.addObject("menuId", currentMenuId);
					model.addObject("menuTitle", menuTitle);
					return model;

				}

				String spImage = specialCake.getSpImage();

				System.out.println("Sp RESPONSE" + specialCake.toString());
				float sprRate;
				float spBackendRate;

				float minWt = Float.valueOf(specialCake.getSpMinwt());

				float maxWt = Float.valueOf(specialCake.getSpMaxwt());

				weightList.add(minWt);
				float currentWt = minWt;
				while (currentWt < maxWt) {
					currentWt = currentWt + specialCake.getSpRate2();//spr rate 2 means weight increment by 
					weightList.add(currentWt);
				} 

				System.out.println("Weight List for SP Cake: " + weightList.toString());

				if (frDetails.getFrRateCat() == 1) {
					sprRate = specialCake.getMrpRate1();
					spBackendRate = specialCake.getSpRate1();

				} /*else if (frDetails.getFrRateCat() == 2) {
					sprRate = specialCake.getMrpRate2();
					spBackendRate = specialCake.getSpRate2(); //No frRateCate 2 is present in franchisee
				}*/ else {
					sprRate = specialCake.getMrpRate3();
					spBackendRate = specialCake.getSpRate3();

				}

				model.addObject("sprRate", sprRate);
				model.addObject("spBackendRate", spBackendRate);

				if (specialCake.getIsAddonRateAppli() == 0) {
					model.addObject("addonRatePerKG", 0);
				} else {
					model.addObject("addonRatePerKG", specialCake.getSprAddOnRate());
				}

				System.out.println("Sp cake search: \n Back End Rate " + spBackendRate);
				System.out.println("Sp cake search: \n Add On Rate " + sprRate);

				model.addObject("specialCake", specialCake);
				model.addObject("eventList", spMessageList);

				int spBookb4 = Integer.parseInt(specialCake.getSpBookb4());

				System.out.println("spBookb4" + spBookb4);

				model.addObject("spBookb4", spBookb4);
				model.addObject("isFound", "");
				model.addObject("specialCakeList", specialCakeList);
				model.addObject("configuredSpCodeList", configuredSpCodeList);
				model.addObject("weightList", weightList);
				model.addObject("menuId", currentMenuId);
				model.addObject("menuTitle", menuTitle);
			} else {

				System.out.println(" inside else:");
				model = new ModelAndView("order/spcakeorder");
				model.addObject("menuList", menuList);
				model.addObject("eventList", spMessageList);
				model.addObject("flavourList", flavourList);
				model.addObject("url", Constant.SPCAKE_IMAGE_URL);
				model.addObject("spBookb4", 0);
				model.addObject("sprRate", 0);
				model.addObject("isFound", false);
				model.addObject("specialCakeList", specialCakeList);
				model.addObject("weightList", weightList);
				model.addObject("menuId", currentMenuId);
				model.addObject("menuTitle", menuTitle);
				return model;

			}
		} catch (Exception e) {
			model = new ModelAndView("order/spcakeorder");

			System.out.println("search  Sp Cake  Excep: " + e.getMessage());
			model.addObject("menuList", menuList);
			model.addObject("eventList", spMessageList);
			model.addObject("flavourList", flavourList);
			model.addObject("url", Constant.SPCAKE_IMAGE_URL);
			model.addObject("spBookb4", 0);
			model.addObject("sprRate", 0);
			model.addObject("isFound", false);
			model.addObject("specialCakeList", specialCakeList);
			model.addObject("configuredSpCodeList", configuredSpCodeList);
			model.addObject("weightList", weightList);
			model.addObject("menuId", currentMenuId);
			model.addObject("menuTitle", menuTitle);
			return model;
		}

		model.addObject("menuList", menuList);
		model.addObject("eventList", spMessageList);
		model.addObject("flavourList", flavourList);
		model.addObject("url", Constant.SPCAKE_IMAGE_URL);
		model.addObject("isFound", "");
		model.addObject("specialCakeList", specialCakeList);
		model.addObject("configuredSpCodeList", configuredSpCodeList);
		model.addObject("menuId", currentMenuId);
		model.addObject("menuTitle", menuTitle);
		return model;
		
	}
   
	// --------------------------END----------------------------------------------------------------------------
	@RequestMapping(value = "/getFlavourBySpfId", method = RequestMethod.GET)
	public @ResponseBody List<Flavour> flavourById(@RequestParam(value = "spType", required = true) int spType) {

		List<Flavour> flavoursListWithAddonRate = new ArrayList<Flavour>();

		System.out.println("SpType:: " + spType);

		List<Flavour> flavoursList = new ArrayList<Flavour>();
		List<Flavour> filterFlavoursList = new ArrayList<Flavour>();

		try {

			flavoursList = flavourList.getFlavour();

			System.out.println("FLAVOURS:" + flavoursList.toString());

			System.out.println("DB Sptype" + flavoursList.get(0).getSpType());

			for (int i = 0; i < flavoursList.size(); i++) {

				if (flavoursList.get(i).getSpType() == spType) {
					filterFlavoursList.add(flavoursList.get(i));

				}
			}
			for (Flavour flavour : filterFlavoursList) {
				if (specialCake.getIsAddonRateAppli() == 1) {
					List<String> list = Arrays.asList(specialCake.getErpLinkcode().split(","));
					if (list.contains(""+flavour.getSpfId())) {
						flavour.setSpfAdonRate(0.0);
					}
					flavoursListWithAddonRate.add(flavour);
					System.err.println(flavour.getSpfId());
					System.err.println(flavoursListWithAddonRate.toString());
				} else {
					flavour.setSpfAdonRate(0.0);
					flavoursListWithAddonRate.add(flavour);
				}
			}
			System.out.println("Filter Item List " + flavoursListWithAddonRate.toString());
		} catch (Exception e) {
			System.out.println("Exception in getFlavourBySpfId() AJAX CALL");

		}

		return flavoursListWithAddonRate;

	}

	// ------------------------Get Addon Rate AJAX method(spcakeorder)-----------------------------------
	@RequestMapping(value = "/getAddOnRate", method = RequestMethod.GET)
	public @ResponseBody Flavour getAddOnRate(@RequestParam(value = "spfId", required = true) double spfId) {
		List<Flavour> flavoursList = new ArrayList<Flavour>();
		Flavour filteredFlavour = new Flavour();
		flavoursList = flavourList.getFlavour();

		for (Flavour flavour : flavoursList) {

			if (flavour.getSpfId() == spfId) {

				filteredFlavour = flavour;
			}
		}
		return filteredFlavour;
	}

	int globalFrId;

	// --------------------------------------END--------------------------------------------------------------
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

		java.util.Date date = new Date();
		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		int month = cale.get(Calendar.MONTH);

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

	// ------------------------Order Special Cake
	// Process-----------------------------------------------------
	@RequestMapping(value = "/orderSpCake", method = RequestMethod.POST)
	public String addItemProcess(HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "order_photo", required = false) List<MultipartFile> orderPhoto,
			@RequestParam(value = "cust_choice_ck", required = false) List<MultipartFile> custChoiceCk)
			throws JsonProcessingException {

		ModelAndView mav = new ModelAndView("order/orderRes");

		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		int frId = frDetails.getFrId();

		globalFrId = frId;
		 menuList = (ArrayList<FrMenu>) session.getAttribute("menuList");

		// menu timing verification
		String fromTime = menuList.get(globalIndex).getFromTime();
		String toTime = menuList.get(globalIndex).getToTime();

		System.out.println("before order placing: from time " + fromTime + " to time " + toTime);

		ZoneId z = ZoneId.of("Asia/Calcutta");
		LocalTime now = LocalTime.now(z); // Explicitly specify the desired/expected time zone.

		LocalTime fromTimeLocalTime = LocalTime.parse(fromTime);
		LocalTime toTimeLocalTIme = LocalTime.parse(toTime);

		Boolean isLate = now.isAfter(toTimeLocalTIme);
		Boolean isEarly = now.isBefore(fromTimeLocalTime);
		int isSameDayApplicable = menuList.get(globalIndex).getIsSameDayApplicable();

		if (!isLate && !isEarly) {
			
			int spId = Integer.parseInt(request.getParameter("sp_id"));
			logger.info("1" + spId);
			String spCode = request.getParameter("sp_code");
			logger.info("2" + spCode);
			 menuTitle = request.getParameter("menu_title");// For Notification
			logger.info("menuTitle" + menuTitle);
			spName = request.getParameter("sp_name");
			logger.info("3" + spName);

			String spMinWeight = request.getParameter("sp_min_weight");
			logger.info("5" + spMinWeight);

			String spMaxWeight = request.getParameter("sp_max_weight");
			logger.info("6" + spMaxWeight);

			String spProTime = request.getParameter("sp_pro_time");
			logger.info("7" + spProTime);

			int prodTime = Integer.parseInt(spProTime);
			logger.info("prodTime" + prodTime);

			String spEdt = request.getParameter("sp_est_del_date");
			logger.info("8" + spEdt);

			spType = Integer.parseInt(request.getParameter("sptype"));
			logger.info("9" + spType);

			String spFlavour = request.getParameter("spFlavour");
			logger.info("10" + spFlavour);

			float spWeight = Float.parseFloat(request.getParameter("spwt"));
			logger.info("11" + spWeight);

			String spEvents = request.getParameter("sp_event");
			logger.info("12" + spEvents);

			String spInstructions = request.getParameter("sp_inst1");
			logger.info("Marathi Inst :" + spInstructions);

			if (spInstructions.isEmpty() || spInstructions == null) {
				spInstructions = request.getParameter("sp_inst2");
				logger.info("English Inst :" + spInstructions);

			}

			String spDeliveryDt = request.getParameter("datepicker");
			logger.info("14" + spDeliveryDt);

			String spBookForDate = request.getParameter("datepicker3");
			logger.info("16" + spBookForDate);

			String spCustDOB = request.getParameter("datepicker4");
			logger.info("17" + spCustDOB);

			String spBookForDOB = request.getParameter("datepicker5");
			logger.info("18" + spBookForDOB);

			String spCustMobileNo = request.getParameter("sp_cust_mobile_no");
			logger.info("19" + spCustMobileNo);

			String spBookForNum = request.getParameter("sp_book_for_number");
			logger.info("20" + spBookForNum);

			String spCustName = request.getParameter("sp_cust_name");
			logger.info("21" + spCustName);

			String spBookedForName = request.getParameter("sp_booked_for_name");
			logger.info("22" + spBookedForName);

			String spGrand = request.getParameter("sp_grand");
			logger.info("23" + spGrand);

			String spPrice = request.getParameter("sp_calc_price");
			logger.info("24" + spPrice);

			String spAddRate = request.getParameter("sp_add_rate");
			logger.info("25" + spAddRate);
			float dbAdonRate = Float.parseFloat(request.getParameter("dbAdonRate"));

			float spSubTotal = Float.parseFloat(request.getParameter("sp_sub_total"));
			logger.info("26" + spSubTotal);

			float tax1 = Float.parseFloat(request.getParameter("tax1"));
			logger.info("27" + tax1);

			float tax2 = Float.parseFloat(request.getParameter("tax2"));
			logger.info("28" + tax2);

			float tax1Amt = Float.parseFloat(request.getParameter("t1amt"));
			logger.info("29" + tax1Amt);

			float tax2Amt = Float.parseFloat(request.getParameter("t2amt"));
			logger.info("30" + tax2Amt);

			String rmAmount = request.getParameter("rm_amount");
			logger.info("31" + rmAmount);

			float spAdvance =Float.parseFloat(request.getParameter("adv"));
			logger.info("32" + spAdvance);

			String spPlace = request.getParameter("sp_place");
			logger.info("33" + spPlace);
 
			 spPhoUpload = request.getParameter("spPhoUpload");

			String eventName = request.getParameter("event_name");

			isCustCh = request.getParameter("isCustCh");

			productionTime = request.getParameter("production_time");
			int isSlotUsed = 0;
			isSlotUsed = Integer.parseInt(request.getParameter("isSlotUsed")); // isSlotUsed

			spImage = request.getParameter("prevImage");

			// ---------isCustSpCk And isSpPhoUpload Special Cake Value(1/0)-------
			int isCustSpCk = Integer.parseInt(request.getParameter("isCustChoiceCk"));
		    int	isSpPhoUpload = Integer.parseInt(request.getParameter("spPhoUpload"));

			String addonRatePerKG = request.getParameter("addonRatePerKG");

			float backendSpRate = Float.parseFloat(request.getParameter("spBackendRate"));
			/*Calendar cal1 = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			System.out.println(sdf.format(cal1.getTime()));

			String curTimeStamp = sdf.format(cal1.getTime());*/
			String curTimeStamp = new SimpleDateFormat("yyyy_MM_dd.HH_mm_ss").format(new Date());

			String custChCk = "";
			String orderPhoto1 = "";

			if (isSpPhoUpload == 1) {

				System.out.println("Empty image");
				// orderPhoto1 = ImageS3Util.uploadPhotoCakeImage(orderPhoto);

				VpsImageUpload upload = new VpsImageUpload();

				

				try {
					orderPhoto1 = curTimeStamp+""+orderPhoto.get(0).getOriginalFilename();
					upload.saveUploadedFiles(orderPhoto, Constant.SPCAKE_IMAGE_TYPE,
							curTimeStamp+""+orderPhoto.get(0).getOriginalFilename());
					System.out.println("upload method called " + orderPhoto.toString());

				} catch (IOException e) {

					System.out.println("Exce in File Upload In Sp Cake Photo Insert " + e.getMessage());
					e.printStackTrace();
				}

			}

			if (isCustSpCk == 1) {

				System.out.println("Empty image");
				// custChCk = ImageS3Util.uploadPhotoCakeImage(custChoiceCk);

				// orderPhoto1 = ImageS3Util.uploadPhotoCakeImage(orderPhoto);

				VpsImageUpload upload = new VpsImageUpload();

				//Calendar cal = Calendar.getInstance();
				//SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				//System.out.println(sdf.format(cal.getTime()));

				//String curTimeStamp = sdf.format(cal.getTime());

				try {
					orderPhoto1 = curTimeStamp+""+orderPhoto.get(0).getOriginalFilename();

					upload.saveUploadedFiles(orderPhoto, Constant.SPCAKE_IMAGE_TYPE,
							curTimeStamp+""+orderPhoto.get(0).getOriginalFilename());
					custChCk = curTimeStamp+""+custChoiceCk.get(0).getOriginalFilename();
					upload.saveUploadedFiles(custChoiceCk, Constant.CUST_CHIOICE_IMAGE_TYPE,
							curTimeStamp+""+custChoiceCk.get(0).getOriginalFilename());

					System.out.println("upload method called for two photo   " + orderPhoto.get(0).getName());

				} catch (IOException e) {

					System.out.println("Exce in File Upload In Sp Cake Photo Insert " + e.getMessage());
					e.printStackTrace();
				}

			}
			spCakeOrder = new SpCakeOrder();
			spCakeOrder.setFrCode(frDetails.getFrCode());

			spCakeOrder.setFrId(frId);

			// -----Order Date And Production Date------

			Date delDate = Main.stringToDate(spDeliveryDt);
			final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date currentDate = new Date();

			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);

			// Current Date
			Date orderDate = c.getTime();

			Calendar cal = Calendar.getInstance();
			cal.setTime(delDate);
            if(isSameDayApplicable==1)
            {
            	cal.add(Calendar.DATE, 0);
            }
            else {
			cal.add(Calendar.DATE, -prodTime);
            }
			// manipulate date
			// c.add(Calendar.DATE, prodTime);
			Date deliDateMinusProdTime = cal.getTime();

			java.sql.Date sqlProdDate = new java.sql.Date(deliDateMinusProdTime.getTime());

			System.out.println("Todays date is: " + currentDate);
			System.out.println("Prod date is: " + deliDateMinusProdTime);

			// ---------------------------------------------------------------

			final SimpleDateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy");

		    Date date = new Date();
			try {

				date = dmyFormat.parse(spCustDOB);
				System.out.println(date);
				System.out.println(dmyFormat.format(date));

			} catch (ParseException e) {
				e.printStackTrace();
			}

			java.util.Date utilSpEdt = new java.util.Date();
			try {
				utilSpEdt = dmyFormat.parse(spEdt);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			java.util.Date utilSpDeliveryDt = new java.util.Date();
			try {
				utilSpDeliveryDt = dmyFormat.parse(spDeliveryDt);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			java.sql.Date sqlBookForDob = new java.sql.Date(deliDateMinusProdTime.getTime());
			java.sql.Date sqlSpCustDOB = new java.sql.Date(date.getTime());
			java.sql.Date sqlSpEdt = new java.sql.Date(utilSpEdt.getTime());

			java.sql.Date sqlSpDeliveryDt = new java.sql.Date(utilSpDeliveryDt.getTime());

			spCakeOrder.setItemId(spCode);
			spCakeOrder.setOrderDate(dateFormat.format(orderDate));
			float rmAmt=spSubTotal-spAdvance;
			spCakeOrder.setRmAmount(rmAmt);
			spCakeOrder.setSpTotalAddRate(Float.valueOf(spAddRate));
			spCakeOrder.setSpAdvance(spAdvance);

			spCakeOrder.setSpBookedForName(spBookedForName);
			spCakeOrder.setSpBookForDob(sqlBookForDob);
			spCakeOrder.setSpBookForMobNo(getInvoiceNo(request,response));
			spCakeOrder.setSpCustDob(sqlSpCustDOB);
			spCakeOrder.setSpInstructions(spInstructions);
			spCakeOrder.setOrderPhoto(orderPhoto1);
			spCakeOrder.setOrderPhoto2(custChCk);

			spCakeOrder.setSpCustMobNo(spCustMobileNo);
			spCakeOrder.setSpCustName(spCustName);
			spCakeOrder.setSpDeliveryDate(sqlSpDeliveryDt);
			spCakeOrder.setSpEstDeliDate(sqlSpEdt);
			spCakeOrder.setSpEvents(spEvents);
			spCakeOrder.setSpEventsName(eventName);
			spCakeOrder.setSpFlavourId(Integer.parseInt(spFlavour));
			spCakeOrder.setSpGrandTotal(Float.parseFloat(spGrand));
			spCakeOrder.setSpId(spId);
			spCakeOrder.setSpMaxWeight(Float.valueOf(spMaxWeight));
			spCakeOrder.setSpMinWeight(Float.valueOf(spMinWeight));
			spCakeOrder.setSpSelectedWeight(spWeight);
 
			String spNo="";
			try {
			 spNo=RegularSpCakeController.getSpNo(request,response);
			}
			catch (Exception e) {
				spNo=spPlace;
				e.printStackTrace();
			}
			spCakeOrder.setSpDeliveryPlace(spNo);
			spCakeOrder.setSpPrice(Float.valueOf(spPrice));
			spCakeOrder.setSpProdDate(sqlProdDate);
			spCakeOrder.setSpProdTime(Integer.parseInt(spProTime));
			spCakeOrder.setSpSubTotal(spSubTotal);
			spCakeOrder.setSpType(spType);

			spCakeOrder.setTax1(tax1);
			spCakeOrder.setTax1Amt(tax1Amt);
			spCakeOrder.setTax2Amt(tax2Amt);
			spCakeOrder.setTax2(tax2);

			spCakeOrder.setMenuId(currentMenuId);
			spCakeOrder.setIsSlotUsed(isSlotUsed);
			spCakeOrder.setIsAllocated(0);

			// Float floatBackEndRate = backendSpRate*spWeight;
			// float intAddonRatePerKG = Float.parseFloat(spAddRate);

			float intAddonRatePerKG = (dbAdonRate * 0.8f);
			float floatBackEndRate = (backendSpRate + intAddonRatePerKG) * spWeight;
			System.out.println("Placing Order: \n Back End Rate " + floatBackEndRate);
			System.out.println("Placing Order: \n Add On Rate " + intAddonRatePerKG);

			spCakeOrder.setSpBackendRate(floatBackEndRate);
			try {
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.set("Content-Type", "application/json");

				ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(spCakeOrder);
				System.out.println("All Sp Order Data" + jsonInString.toString());

				HttpEntity<String> httpEntity = new HttpEntity<String>(jsonInString.toString(), httpHeaders);

				RestTemplate restTemplate = new RestTemplate();
				spCakeOrderRes = restTemplate.postForObject(Constant.URL + "/placeSpCakeOrder",
						httpEntity, SpCakeOrderRes.class);
				System.out.println("ORDER PLACED " + spCakeOrderRes.toString());
				spCakeOrder.setSpInstructions(spCakeOrderRes.getSpCakeOrder().getSpInstructions());
				spCake = spCakeOrderRes.getSpCakeOrder();
				if (spCakeOrderRes.getErrorMessage().getError() != true) {
					System.out.println("ORDER PLACED " + spCakeOrderRes.toString());
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
					
					
					map = new LinkedMultiValueMap<String, Object>();

					map.add("frId", frDetails.getFrId());

					Info updateFrSettingGrnGvnNo = restTemplate.postForObject(Constant.URL + "updateFrSettingSpNo", map, Info.class);


				}
				List<Flavour> flavoursList = new ArrayList<Flavour>();
				Flavour filteredFlavour = new Flavour();

				flavoursList = flavourList.getFlavour();

				for (Flavour flavour : flavoursList) {

					if (flavour.getSpfId() == Integer.parseInt(spFlavour)) {

						filteredFlavour = flavour;
					}
				}

				flavourName = filteredFlavour.getSpfName();
				flavour = flavourName;
				mav.addObject("menuTitle", menuTitle);
				mav.addObject("spType", spType);
				mav.addObject("specialCake", spCake);
				mav.addObject("spImage", spImage);
				mav.addObject("url", Constant.SPCAKE_IMAGE_URL);
				mav.addObject("SPCAKE_URL", Constant.SP_CAKE_FOLDER);
				mav.addObject("spName", spName);
				mav.addObject("productionTime", productionTime);
				mav.addObject("flavourName", flavourName);
				mav.addObject("isCustCh", isCustCh);
				mav.addObject("spPhoUpload", spPhoUpload);
				mav.addObject("PHOTO_URL", Constant.PHOTO_CAKE_URL);
				mav.addObject("globalIndex", globalIndex);
				System.out.println("SpCakeRes:" + spCake.toString());

				// -----------------------For Notification-----------------
				String frToken = "";
				if (spCakeOrderRes.getErrorMessage().getError() != true) {
				try {
					MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
					map.add("frId", frDetails.getFrId());

					frToken = restTemplate.postForObject(Constant.URL + "getFrToken", map, String.class);
					Firebase.sendPushNotifForCommunication(frToken, menuTitle + " Order Placed Sucessfully",
							"Your SP Order has been saved. Order Saved is--SP Code--" + spCode + "-Sp name--" + spName
									+ "--Weight--" + spWeight + "--Flavour--" + flavourName + "--Message--"
									+ spCakeOrderRes.getSpCakeOrder().getSpInstructions() + "--Total Amount-" + spGrand
									+ ". Thank You.Team Monginis",
							"inbox");

				} catch (Exception e2) {
					e2.printStackTrace();
				}
				}

				// -----------------------------------------------------
			} catch (Exception e) {
				System.out.println("Exc" + e.getMessage());
				mav.addObject("eventList", spMessageList);
				mav.addObject("flavourList", flavourList);

			}
		} else // End of if -Timeout
		{
			mav = new ModelAndView("order/spcakeorder");
			mav.addObject("errorMessage", "Special Cake Order TimeOut");
			mav.addObject("menuList", menuList);
			mav.addObject("eventList", spMessageList);
			mav.addObject("flavourList", flavourList);
			mav.addObject("url", Constant.SPCAKE_IMAGE_URL);
			mav.addObject("spBookb4", 0);
			mav.addObject("sprRate", 0);
			mav.addObject("isFound", false);
		}
		return "redirect:/orderRes";

	}
	// ----------------------------------END----------------------------------------

	// -----------------Showing of order Datails Page------------------------------
	@RequestMapping(value = "/orderRes", method = RequestMethod.GET)
	public ModelAndView displayHome(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("order/orderRes");
		
		try
		{
			if(spCake!=null)
			{
				if(spCakeOrderRes.getErrorMessage().getError()!=true)
				{
					
					mav.addObject("menuTitle", menuTitle);
					mav.addObject("spType", spType);
					mav.addObject("specialCake", spCake);
					mav.addObject("spImage", spImage);
					mav.addObject("url", Constant.SPCAKE_IMAGE_URL);
					mav.addObject("SPCAKE_URL", Constant.SP_CAKE_FOLDER);
					mav.addObject("spName", spName);
					mav.addObject("productionTime", productionTime);
					mav.addObject("flavourName", flavourName);
					mav.addObject("isCustCh", isCustCh);
					mav.addObject("spPhoUpload", spPhoUpload);
					mav.addObject("PHOTO_URL", Constant.PHOTO_CAKE_URL);
					mav.addObject("globalIndex", globalIndex);
					mav.addObject("isFound", true);
				}
				else
				{
					
					mav.addObject("errorMessage", "Special Cake Order TimeOut");
					mav.addObject("menuList", menuList);
					mav.addObject("eventList", spMessageList);
					mav.addObject("flavourList", flavourList);
					mav.addObject("url", Constant.SPCAKE_IMAGE_URL);
					mav.addObject("spBookb4", 0);
					mav.addObject("sprRate", 0);
					mav.addObject("globalIndex", globalIndex);

					mav.addObject("isFound", false);
				}
				
			}
			else
			{
				mav.addObject("errorMessage", "Special Cake Order TimeOut");
				mav.addObject("menuList", menuList);
				mav.addObject("eventList", spMessageList);
				mav.addObject("flavourList", flavourList);
				mav.addObject("url", Constant.SPCAKE_IMAGE_URL);
				mav.addObject("spBookb4", 0);
				mav.addObject("sprRate", 0);
				mav.addObject("isFound", false);
				mav.addObject("globalIndex", globalIndex);

			}
			
		}catch (Exception e) {
			System.out.println("Exc" + e.getMessage());
			mav.addObject("eventList", spMessageList);
			mav.addObject("flavourList", flavourList);
			mav.addObject("globalIndex", globalIndex);

			e.printStackTrace();
			mav.addObject("isFound", false);

		}
		return mav;
	}
	// ----------------------------------END--------------------------------------------

	// -----------------For Showing Special Cake order PDF------------------------------
	@RequestMapping(value = "/showSpCakeOrderPDF", method = RequestMethod.GET)
	public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/order");
		try {
			HttpSession session = request.getSession();

			Franchisee franchisee = (Franchisee) session.getAttribute("frDetails");

			Calendar cal = Calendar.getInstance();

			Date date = new Date();
			date.getTime();

			SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat formatTime = new SimpleDateFormat("hh-mm-ss a");

			System.out.println(cal.getTime());

			String formatted = format1.format(cal.getTime());
			System.out.println(formatted);

			String currentDate = format1.format(date);
			String time = formatTime.format(cal.getTime());
			String shopName = franchisee.getFrName();
			String tel = franchisee.getFrMob();

			model.addObject("spCakeOrder", spCakeOrder);
			model.addObject("currDate", currentDate);
			model.addObject("currTime", time);
			model.addObject("shopName", shopName);
			model.addObject("tel", tel);
			model.addObject("flavourName", flavour);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}
	// ----------------------------------END------------------------------------------------------------------

	@RequestMapping(value = "/orderRegularSpCake", method = RequestMethod.GET)
	public ModelAndView displayRegularSpCakeOrder(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("order/regularSpCkOrder");

		return model;

	}
	// ----------------------------------END------------------------------------------------------------------------

	// -----------------CALLING AJAX method FOR GETTING AVAILABLE SLOT BYPRODUCTION
	// DATE----------------------------
	@RequestMapping(value = "/getAvailableSlot", method = RequestMethod.GET)
	public @ResponseBody int getAvailableSlot(@RequestParam(value = "deldate", required = true) String deldate,
			@RequestParam(value = "prodTime", required = true) int prodTime) {

		System.out.println("Get Available Slots");
		// Manipulate date
		Date delDate = Main.stringToDate(deldate);

		Calendar cal = Calendar.getInstance();
		cal.setTime(delDate);
		cal.add(Calendar.DATE, -prodTime);

		Date deliDateMinusProdTime = cal.getTime();
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		String spProduDate = dateFormat.format(deliDateMinusProdTime);

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("spProduDate", spProduDate);

		RestTemplate restTemplate = new RestTemplate();

		// Getting Total Used Slot
		Integer totalSlotUsed = restTemplate.postForObject(Constant.URL + "/getCountByProduDate", map, Integer.class);

		// Getting Total Available Slot
		Integer totalAvailableSlot = restTemplate.postForObject(Constant.URL + "/getTotalAvailableSlot", map,
				Integer.class);

		int availableSlots = totalAvailableSlot - totalSlotUsed;

		return availableSlots;
	}
	// --------------------------------------END------------------------------------

}