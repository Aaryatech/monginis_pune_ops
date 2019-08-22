package com.monginis.ops.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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

import com.monginis.ops.common.Common;
import com.monginis.ops.common.Firebase;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.CustList;
import com.monginis.ops.model.CustomerBillItem;
import com.monginis.ops.model.FrMenu;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetFrItem;
import com.monginis.ops.model.GetOrder;
import com.monginis.ops.model.GetOrderList;
import com.monginis.ops.model.Info;
import com.monginis.ops.model.LoginInfo;
import com.monginis.ops.model.Orders;
import com.monginis.ops.model.TabTitleData;
import com.monginis.ops.model.setting.NewSetting;

@Controller
@Scope("session")
public class ItemController {

	// private static final Logger logger =
	// LoggerFactory.getLogger(ItemController.class);
	private List<GetFrItem> frItemList = new ArrayList<>();
	private List<GetFrItem> prevFrItemList = new ArrayList<>();

	private int globalIndex = 0;
	private int currentMenuId = 0;
	List<String> subCatList = new ArrayList<>();
	public MultiValueMap<String, Object> map;
	public String qtyAlert = "Enter the Quantity as per the Limit.";

	@RequestMapping(value = "/showCutomerList")
	public ModelAndView showCutomerList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = null;

		mav = new ModelAndView("report/custList");

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		try {
			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			RestTemplate restTemplate = new RestTemplate();
			CustList[] custList1 = restTemplate.postForObject(Constant.URL + "getCutslListFroFranchasee", map,
					CustList[].class);
			List<CustList> custList = new ArrayList<CustList>(Arrays.asList(custList1));

			System.out.println("custListcustListcustListcustList" + custList.toString());

			mav.addObject("custList", custList);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return mav;

	}

	@RequestMapping(value = "/showSavouries/{index}", method = RequestMethod.GET)
	public ModelAndView displaySavouries(@PathVariable("index") int index, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		ModelAndView model = new ModelAndView("order/itemorder");
		// logger.info("/item order request mapping. index:" + index);

		subCatList = new ArrayList<>();
		globalIndex = index;

		Date date = new Date(Calendar.getInstance().getTime().getTime());
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		DateFormat dfdmy = new SimpleDateFormat("dd-MM-yyyy");

		String currentDate = df.format(date);
		String currentDateFc = dfdmy.format(date);

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		ArrayList<FrMenu> menuList = (ArrayList<FrMenu>) session.getAttribute("menuList");

		DateFormat dfReg = new SimpleDateFormat("yyyy-MM-dd");

		String todaysDate = dfReg.format(date);

		// order ,production ,delivery date logic

		int isSameDayApplicable = menuList.get(index).getIsSameDayApplicable();
		String menuTitle = menuList.get(index).getMenuTitle();

		System.out.println("Menu Title: " + menuTitle);

		String fromTime = menuList.get(index).getFromTime();
		String toTime = menuList.get(index).getToTime();

		ZoneId z = ZoneId.of("Asia/Calcutta");
		LocalTime currentTime = LocalTime.now(z); // Explicitly specify the desired/expected time zone.

		LocalTime formatedFromTime = LocalTime.parse(fromTime);
		LocalTime formatedToTime = LocalTime.parse(toTime);

		// currentTime = currentTime.plusHours(15);
		System.out.println("current time " + currentTime);
		System.out.println("from time " + formatedFromTime);

		String orderDate = "";
		String productionDate = "";
		String deliveryDate = "";

		if (formatedFromTime.isBefore(formatedToTime)) {

			orderDate = todaysDate;
			productionDate = todaysDate;

			if (isSameDayApplicable == 0 || isSameDayApplicable == 2) {

				deliveryDate = incrementDate(todaysDate, 1);
				System.out.println("inside 1.1");

			} else if (isSameDayApplicable == 1) {

				deliveryDate = todaysDate;

				System.out.println("inside 1.2");

			}

		} else {

			if (currentTime.isAfter(formatedFromTime)) {

				orderDate = todaysDate;
				productionDate = incrementDate(todaysDate, 1);

				deliveryDate = incrementDate(todaysDate, 2);

				System.out.println("inside 2.1");
			} else {

				orderDate = todaysDate;
				productionDate = todaysDate;
				deliveryDate = incrementDate(todaysDate, 1);
				System.out.println("inside 2.2");
			}

		}

		System.out.println("Order date: " + orderDate);
		System.out.println("Production date: " + productionDate);
		System.out.println("Delivery date: " + deliveryDate);

		frItemList = new ArrayList<GetFrItem>();
		prevFrItemList = new ArrayList<GetFrItem>();
		List<GetOrder> orderList = new ArrayList<GetOrder>();
		int flagRes = 0;
		NewSetting settingValue=new NewSetting();
		try {
			RestTemplate rest = new RestTemplate();
			//new on 10 july
			//-----------------------------------------------------------------------------
			map = new LinkedMultiValueMap<String, Object>();
			map.add("settingKey", "show_prev_order");
			 settingValue= rest.postForObject(Constant.URL + "/findNewSettingByKey", map, NewSetting.class);	
			//------------------------------------------------------------------------------
			
			System.out.println("Date is : " + currentDate);
			currentMenuId = menuList.get(index).getMenuId();
			if(currentMenuId==Integer.parseInt(settingValue.getSettingValue1())) {
			  try {
				map = new LinkedMultiValueMap<String, Object>();
				map.add("frId", frDetails.getFrId());
				map.add("date", currentDateFc);
				map.add("menuId", settingValue.getSettingValue2());//new on 10 july
				orderList = rest.postForObject(Constant.URL + "/getOrdersListRes", map, List.class);
				System.err.println("orderList:" + orderList.toString());
				model.addObject("orderList", orderList);

				flagRes = 1;
				model.addObject("flagRes", flagRes);
			  } catch (Exception e) {
				flagRes = 0;
				model.addObject("flagRes", flagRes);

				e.printStackTrace();
			  }
			}
			map = new LinkedMultiValueMap<String, Object>();

			map.add("items", menuList.get(index).getItemShow());
			map.add("frId", frDetails.getFrId());
			map.add("date", productionDate);
			map.add("menuId", menuList.get(index).getMenuId());
			map.add("isSameDayApplicable", isSameDayApplicable);

			RestTemplate restTemplate = new RestTemplate();

			ParameterizedTypeReference<List<GetFrItem>> typeRef = new ParameterizedTypeReference<List<GetFrItem>>() {
			};
			ResponseEntity<List<GetFrItem>> responseEntity = restTemplate.exchange(Constant.URL + "/getFrItems",
					HttpMethod.POST, new HttpEntity<>(map), typeRef);

			frItemList = responseEntity.getBody();
			prevFrItemList = responseEntity.getBody();
			System.out.println("Fr Item List " + frItemList.toString());
		} catch (Exception e) {

			System.out.println("Exception Item List " + e.getMessage());
		}

		Set<String> setName = new HashSet<String>();

		double grandTotal = 0;

		for (int i = 0; i < frItemList.size(); i++) {

			if (frDetails.getFrRateCat() == 1) {
				grandTotal = grandTotal + (frItemList.get(i).getItemQty() * frItemList.get(i).getItemRate1());
			} else if (frDetails.getFrRateCat() == 2) {
				grandTotal = grandTotal + (frItemList.get(i).getItemQty() * frItemList.get(i).getItemRate2());

			} else if (frDetails.getFrRateCat() == 3) {
				grandTotal = grandTotal + (frItemList.get(i).getItemQty() * frItemList.get(i).getItemRate3());

			}
			setName.add(frItemList.get(i).getSubCatName());

		}

		subCatList.addAll(setName);

		List<TabTitleData> subCatListWithQtyTotal = new ArrayList<>();

		for (int i = 0; i < subCatList.size(); i++) {

			String subCat = subCatList.get(i);
			int qty = 0;
			double total = 0;

			for (int j = 0; j < frItemList.size(); j++) {

				if (frItemList.get(j).getSubCatName().equalsIgnoreCase(subCat)) {

					qty = qty + frItemList.get(j).getItemQty();

					if (frDetails.getFrRateCat() == 1) {

						total = total + (frItemList.get(j).getItemRate1() * frItemList.get(j).getItemQty());

					} else if (frDetails.getFrRateCat() == 2) {

						total = total + (frItemList.get(j).getItemRate2() * frItemList.get(j).getItemQty());

					} else if (frDetails.getFrRateCat() == 3) {

						total = total + (frItemList.get(j).getItemRate3() * frItemList.get(j).getItemQty());

					}

				}

			}

			TabTitleData tabTitleData = new TabTitleData();
			tabTitleData.setName(subCat);

			if (isSameDayApplicable != 2) {
				tabTitleData.setHeader(subCat + " (Rs." + total + ")" + "(Qty- " + qty + ")");
			} else if (isSameDayApplicable == 2) {
				if (subCat.equalsIgnoreCase("Pastries")) {
					tabTitleData.setHeader(subCat + " (Rs." + total + ")" + "(Qty- " + qty + ")" + "(Limit- "
							+ frDetails.getFrKg1() + ")");

				} else if (subCat.equalsIgnoreCase("1/2 Kg Cake")) {
					tabTitleData.setHeader(subCat + " (Rs." + total + ")" + "(Qty- " + qty + ")" + "(Limit- "
							+ frDetails.getFrKg2() + ")");

				} else if (subCat.equalsIgnoreCase("1 Kg Cake")) {
					tabTitleData.setHeader(subCat + " (Rs." + total + ")" + "(Qty- " + qty + ")" + "(Limit- "
							+ frDetails.getFrKg3() + ")");

				} else if (subCat.equalsIgnoreCase("Above 1 Kg Cake")) {
					tabTitleData.setHeader(subCat + " (Rs." + total + ")" + "(Qty- " + qty + ")" + "(Limit- "
							+ frDetails.getFrKg4() + ")");

				}

			}
			subCatListWithQtyTotal.add(tabTitleData);

		}

		System.out.println(subCatList);

		// toTime

		SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
		SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
		java.util.Date toTime12Hrs = null;
		try {
			toTime12Hrs = _24HourSDF.parse(toTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		DateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
		Date itemOrderDate;
		Date itemDeliveryDate;

		itemOrderDate = (Date) parser.parse(orderDate);
		itemDeliveryDate = (Date) parser.parse(deliveryDate);

		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strOrderDate = formatter.format(itemOrderDate);

		String strDeliveryDate = formatter.format(itemDeliveryDate);

		model.addObject("menuList", menuList);

		model.addObject("subCatListTitle", subCatListWithQtyTotal);

		model.addObject("itemList", frItemList);
		model.addObject("grandTotal", grandTotal);
		model.addObject("frDetails", frDetails);

		model.addObject("currentDate", todaysDate);
		model.addObject("toTime", _12HourSDF.format(toTime12Hrs));
		model.addObject("orderDate", strOrderDate);
		model.addObject("productionDate", productionDate);
		model.addObject("deliveryDate", strDeliveryDate);
		model.addObject("menuTitle", menuTitle);
		model.addObject("menuIdFc", menuList.get(index).getMenuId());
		model.addObject("menuIdShow",settingValue.getSettingValue1());
		System.out.println("isSameDayApplicable" + isSameDayApplicable);
		model.addObject("isSameDayApplicable", isSameDayApplicable);
		model.addObject("qtyMessage", qtyAlert);
		model.addObject("url", Constant.ITEM_IMAGE_URL);

		return model;

	}

	// -----------------------------------------------------------------------------------------
	@RequestMapping(value = "/quantityValidation", method = RequestMethod.GET)
	public @ResponseBody Info qtyValidation(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("AJAX CALL Qty Validation");
		Info info = new Info();

		List<GetFrItem> tempFrItemList = new ArrayList<GetFrItem>();
		;
		tempFrItemList = prevFrItemList;

		List<GetFrItem> tempOrderList = new ArrayList<>();
		boolean isValidQty = true;

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		for (int i = 0; i < prevFrItemList.size(); i++) {

			GetFrItem tempFrItem = prevFrItemList.get(i);

			try {
				Integer id = tempFrItem.getId();
				System.out.println("id " + id);
				System.out.println("prev qty " + tempFrItem.getItemQty());

				String strQty = request.getParameter(String.valueOf(id));
				int qty = Integer.parseInt(strQty);

				System.out.println(" " + id + ":" + strQty);

				tempFrItem.setItemQty(qty);
				tempOrderList.add(tempFrItem);

			} catch (Exception e) {
				System.out.println("Except OrderList " + e.getMessage());
			}

		}

		System.out.println(" tempOrder List " + tempOrderList.toString());

		System.out.println(" frItemList List " + frItemList.toString());

		int kg1Qty = 0;
		int kg2Qty = 0;
		int kg3Qty = 0;
		int kg4Qty = 0;

		// kg1= pastries kg2= half kg, kg3=1kg, kg4=above 1kg

		for (int i = 0; i < tempOrderList.size(); i++) {

			GetFrItem item = tempOrderList.get(i);

			if (item.getSubCatName().equalsIgnoreCase("Pastries")) {

				kg1Qty = kg1Qty + item.getItemQty();

			} else if (item.getSubCatName().equalsIgnoreCase("1/2 Kg Cake")) {

				kg2Qty = kg2Qty + item.getItemQty();

			} else if (item.getSubCatName().equalsIgnoreCase("1 Kg Cake")) {

				kg3Qty = kg3Qty + item.getItemQty();

			} else if (item.getSubCatName().equalsIgnoreCase("Above 1 Kg Cake")) {

				kg4Qty = kg4Qty + item.getItemQty();
			}

		}

		System.out.println("limit : " + frDetails.getFrKg1() + "new qty:  kg1:" + kg1Qty);
		System.out.println("limit : " + frDetails.getFrKg2() + "new qty:  kg2:" + kg2Qty);
		System.out.println("limit : " + frDetails.getFrKg3() + "new qty:  kg3:" + kg3Qty);
		System.out.println("limit : " + frDetails.getFrKg4() + "new qty:  kg4:" + kg4Qty);

		if (frDetails.getFrKg1() < kg1Qty) {
			isValidQty = false;

			info.setError(true);
			info.setMessage("You have exceeded Max limit for Pastries");
		} else if (frDetails.getFrKg2() < kg2Qty) {
			isValidQty = false;

			info.setError(true);
			info.setMessage("You have exceeded Max limit for 1/2 Kg Cake");
		} else if (frDetails.getFrKg3() < kg3Qty) {
			isValidQty = false;
			info.setError(true);
			info.setMessage("You have exceeded Max limit for 1 Kg Cake");
		} else if (frDetails.getFrKg4() < kg4Qty) {
			isValidQty = false;
			info.setError(true);
			info.setMessage("You have exceeded Max limit for Above 1 Kg Cake");
		}

		return info;
	}

	// --------------------------------------------------------------------------------------------
	@RequestMapping("/saveOrder")
	public ModelAndView helloWorld(HttpServletRequest request, HttpServletResponse res) throws IOException {

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		LoginInfo loginInfo = (LoginInfo) session.getAttribute("loginInfo");


		ModelAndView mav = new ModelAndView("redirect:/showSavouries/" + globalIndex);
		RestTemplate restTemplate = new RestTemplate();

		String orderDate = "";
		String productionDate = "";
		String deliveryDate = "";

		int kg1QtyN = 0;// Notification
		int kg2QtyN = 0;// Notification
		int kg3QtyN = 0;// Notification
		int kg4QtyN = 0;// Notification

		String menuId = request.getParameter("menuId");
		int rateCat = frDetails.getFrRateCat();
		ArrayList<FrMenu> menuList = (ArrayList<FrMenu>) session.getAttribute("menuList");

		//-----------------------------------------------------------------------------
		map = new LinkedMultiValueMap<String, Object>();
		map.add("settingKey", "grn_type3");
		NewSetting settingValue= restTemplate.postForObject(Constant.URL + "/findNewSettingByKey", map, NewSetting.class);

		List<Integer> settingMenuIds = Stream.of(settingValue.getSettingValue1().split(",")).map(Integer::parseInt)
				.collect(Collectors.toList());
		
		//------------------------------------------------------------------------------
		int isSameDayApplicable = menuList.get(globalIndex).getIsSameDayApplicable();
		String menuTitle = request.getParameter("menuTitle");// For Notification
		System.out.println("Fr Rate Cat " + rateCat);

		System.out.println("Current menu id: " + currentMenuId + " menu id from jsp: " + menuId);

		List<GetFrItem> tempOrderList = new ArrayList<>();
		boolean isValidQty = true;
		System.out.println(" frItemList List before limit condition " + frItemList.toString());

		if (isSameDayApplicable == 2) { // if category is cake and pastries with limit then check for limit

			List<GetFrItem> tempFrItemList = new ArrayList<GetFrItem>();
			tempFrItemList = prevFrItemList;

			for (int i = 0; i < prevFrItemList.size(); i++) {

				GetFrItem tempFrItem = prevFrItemList.get(i);

				try {
					Integer id = tempFrItem.getId();
					System.out.println("id " + id);
					System.out.println("prev qty " + tempFrItem.getItemQty());

					String strQty = request.getParameter(String.valueOf(id));
					int qty = Integer.parseInt(strQty);

					System.out.println(" " + id + ":" + strQty);

					tempFrItem.setItemQty(qty);
					tempOrderList.add(tempFrItem);

				} catch (Exception e) {
					System.out.println("Except OrderList " + e.getMessage());
				}

			}

			System.out.println(" tempOrder List " + tempOrderList.toString());

			System.out.println(" frItemList List " + frItemList.toString());

			int kg1Qty = 0;
			int kg2Qty = 0;
			int kg3Qty = 0;
			int kg4Qty = 0;

			// kg1= pastries kg2= half kg, kg3=1kg, kg4=above 1kg
			// mav.addObject("qtyError", "0");
			// mav.addObject("qtyMessage", "");
			qtyAlert = "";
			for (int i = 0; i < tempOrderList.size(); i++) {

				GetFrItem item = tempOrderList.get(i);

				if (item.getSubCatName().equalsIgnoreCase("Pastries")) {

					kg1Qty = kg1Qty + item.getItemQty();

				} else if (item.getSubCatName().equalsIgnoreCase("1/2 Kg Cake")) {

					kg2Qty = kg2Qty + item.getItemQty();

				} else if (item.getSubCatName().equalsIgnoreCase("1 Kg Cake")) {

					kg3Qty = kg3Qty + item.getItemQty();

				} else if (item.getSubCatName().equalsIgnoreCase("Above 1 Kg Cake")) {

					kg4Qty = kg4Qty + item.getItemQty();

				}

			}

			System.out.println("limit : " + frDetails.getFrKg1() + "new qty:  kg1:" + kg1Qty);
			System.out.println("limit : " + frDetails.getFrKg2() + "new qty:  kg2:" + kg2Qty);
			System.out.println("limit : " + frDetails.getFrKg3() + "new qty:  kg3:" + kg3Qty);
			System.out.println("limit : " + frDetails.getFrKg4() + "new qty:  kg4:" + kg4Qty);

			if (frDetails.getFrKg1() < kg1Qty) {
				isValidQty = false;
				qtyAlert = "You have exceeded max limit for Pastries";

			} else if (frDetails.getFrKg2() < kg2Qty) {
				isValidQty = false;
				qtyAlert = "You have exceeded max limit for 1/2 Kg Cake";

			} else if (frDetails.getFrKg3() < kg3Qty) {
				isValidQty = false;
				qtyAlert = "You have exceeded max limit for 1 Kg Cake";

			} else if (frDetails.getFrKg4() < kg4Qty) {
				isValidQty = false;
				qtyAlert = "You have exceeded max limit for Above 1 Kg Cake";

			}

			if (isValidQty) {
				frItemList = new ArrayList<GetFrItem>();


				ParameterizedTypeReference<List<GetFrItem>> typeRef = new ParameterizedTypeReference<List<GetFrItem>>() {
				};
				ResponseEntity<List<GetFrItem>> responseEntity = restTemplate.exchange(Constant.URL + "/getFrItems",
						HttpMethod.POST, new HttpEntity<>(map), typeRef);

				frItemList = responseEntity.getBody();
				
				
			}

		}

		if (isValidQty) {

			// menu timing verification

			String fromTime = menuList.get(globalIndex).getFromTime();
			String toTime = menuList.get(globalIndex).getToTime();

			ZoneId z = ZoneId.of("Asia/Calcutta");
			LocalTime now = LocalTime.now(z); // Explicitly specify the desired/expected time zone.

			LocalTime fromTimeLocalTime = LocalTime.parse(fromTime);
			LocalTime toTimeLocalTIme = LocalTime.parse(toTime);

			Boolean isLate = now.isAfter(toTimeLocalTIme);
			Boolean isEarly = now.isBefore(fromTimeLocalTime);

			System.out.println("\nLocal time" + now + "Is Early :" + isLate);
			System.out.println("Local time" + now + "Is Late :" + isLate);

			Boolean isSameDay = fromTimeLocalTime.isBefore(toTimeLocalTIme);
			Boolean isValid = false;
			System.out.println("before order placing: from time " + fromTimeLocalTime + " to time " + toTimeLocalTIme);

			if (isSameDay) {

				if (!isLate && !isEarly) {

					isValid = true;
				}
			} else {

				if (now.isAfter(fromTimeLocalTime)) {
					isValid = true;
				} else if (toTimeLocalTIme.isAfter(now)) {
					isValid = true;
				} else {
					isValid = false;
				}
			}
			System.out.println(" is valid " + isValid);

			if (isValid) {
				// date verification

				System.out.println("current time " + now);
				System.out.println("from time " + fromTimeLocalTime);

				String todaysDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

				if (fromTimeLocalTime.isBefore(toTimeLocalTIme)) {

					orderDate = todaysDate;
					productionDate = todaysDate;

					if (isSameDayApplicable == 0 || isSameDayApplicable == 2) {

						deliveryDate = incrementDate(todaysDate, 1);
						System.out.println("inside 1.1");

					} else if (isSameDayApplicable == 1) {

						deliveryDate = todaysDate;

						System.out.println("inside 1.2");

					}

				} else {

					if (now.isAfter(fromTimeLocalTime)) {

						orderDate = todaysDate;
						productionDate = incrementDate(todaysDate, 1);
						deliveryDate = incrementDate(todaysDate, 2);

						System.out.println("inside 2.1");
					} else {

						orderDate = todaysDate;
						productionDate = todaysDate;
						deliveryDate = incrementDate(todaysDate, 1);
						System.out.println("inside 2.2");
					}

				}

				System.out.println("Order date: " + orderDate);
				System.out.println("Production date: " + productionDate);
				System.out.println("Delivery date: " + deliveryDate);

				// if date time verified then place order
// 
				List<GetFrItem> orderList = new ArrayList<>();

				for (int i = 0; i < frItemList.size(); i++) {

					GetFrItem frItem = frItemList.get(i);

					try {
						Integer id = frItem.getId();
						System.out.println("id " + id);

						String strQty = request.getParameter(String.valueOf(id));

						int qty = Integer.parseInt(strQty);

						System.out.println(" " + frItem.getItemQty() + "=?" + strQty);

						if (qty != frItem.getItemQty()) {

							frItem.setItemQty(qty);
							orderList.add(frItem);
						}

					} catch (Exception e) {
						System.out.println("Except OrderList " + e.getMessage());
					}

				}
				try {

					String url = Constant.URL + "placeOrder";

					ObjectMapper mapperObj = new ObjectMapper();

					List<Orders> orders = new ArrayList<>();

					for (int i = 0; i < orderList.size(); i++) {

						GetFrItem frItem = orderList.get(i);

						Orders order = new Orders();

						int frGrnTwo = frDetails.getGrnTwo();
					
						if (frGrnTwo == 1) {

							order.setGrnType(1);

						} else {

							order.setGrnType(0);
						}

						// for no grn these menuIds
						if (settingMenuIds.contains(menuList.get(globalIndex).getMenuId())) {
							order.setGrnType(3);
						}

						order.setDeliveryDate(Common.stringToSqlDate(deliveryDate));
						order.setEditQty(frItem.getItemQty());
						order.setFrId(frDetails.getFrId());
						order.setIsEdit(0);
						order.setIsPositive(frItem.getDiscPer());// new
						order.setItemId(frItem.getId().toString());
						order.setMenuId(frItem.getMenuId());
						order.setOrderDate(Common.stringToSqlDate(orderDate));
						order.setOrderDatetime(todaysDate);
						order.setOrderQty(frItem.getItemQty());
						order.setOrderSubType(Integer.parseInt(frItem.getItemGrp2()));
						order.setOrderType(Integer.parseInt(frItem.getItemGrp1()));
						order.setProductionDate(Common.stringToSqlDate(productionDate));
						order.setRefId(0);// frItem.getId()on 21 feb
						order.setUserId(loginInfo.getAccessRight());//new c
						order.setMenuId(currentMenuId);

						System.out.println("order qty===***************" + frItem.getItemQty());

						if (rateCat == 1) {
							order.setOrderMrp(frItem.getItemMrp1());
							order.setOrderRate(frItem.getItemRate1());

						}  else if (rateCat == 3) {
							order.setOrderMrp(frItem.getItemMrp3());
							order.setOrderRate(frItem.getItemRate3());

						}

						orders.add(order);

					}

					String jsonStr = null;

					try {
						jsonStr = mapperObj.writeValueAsString(orders);
						System.out.println("Converted JSON: " + jsonStr);
					} catch (IOException e) {
						System.out.println("Excep converting java 2 json " + e.getMessage());
						e.printStackTrace();
					}

					HttpHeaders headers = new HttpHeaders();
					headers.setContentType(MediaType.APPLICATION_JSON);

					HttpEntity<String> entity = new HttpEntity<String>(jsonStr, headers);

					ResponseEntity<String> orderListResponse = restTemplate.exchange(url, HttpMethod.POST, entity,
							String.class);

					System.out.println("Place Order Response" + orderListResponse.toString());

					for (int i = 0; i < prevFrItemList.size(); i++) {

						GetFrItem tempFrItem = prevFrItemList.get(i);

						try {
							Integer id = tempFrItem.getId();
							System.out.println("id " + id);
							System.out.println("prev qty " + tempFrItem.getItemQty());

							String strQty = request.getParameter(String.valueOf(id));
							int qty = Integer.parseInt(strQty);

							System.out.println(" " + id + ":" + strQty);

							tempFrItem.setItemQty(qty);
							tempOrderList.add(tempFrItem);

						} catch (Exception e) {
							System.out.println("Except OrderList " + e.getMessage());
						}

					}

					// ------------------------------------------------For
					// Notification-----------------------------------------------------
					int catId = 0;
					int kg1QtyN1 = 0;// Notification
					int kg2QtyN1 = 0;// Notification
					int kg3QtyN1 = 0;// Notification
					int kg4QtyN1 = 0;// Notification
					for (int i = 0; i < tempOrderList.size(); i++) {

						GetFrItem item = tempOrderList.get(i);
						System.out.println("Item111" + item.toString());

						if (Integer.parseInt(item.getItemGrp1()) == 2) {
							catId = 2;

							if (Integer.parseInt(item.getItemGrp2()) == 14) {

								kg1QtyN1 = kg1QtyN1 + item.getItemQty();

							} else if (Integer.parseInt(item.getItemGrp2()) == 15) {

								kg2QtyN1 = kg2QtyN1 + item.getItemQty();

							} else if (Integer.parseInt(item.getItemGrp2()) == 16) {

								kg3QtyN1 = kg3QtyN1 + item.getItemQty();

							} else if (Integer.parseInt(item.getItemGrp2()) == 17) {

								kg4QtyN1 = kg4QtyN1 + item.getItemQty();

							}
						} else if (Integer.parseInt(item.getItemGrp1()) == 1) {
							catId = 1;

							if (Integer.parseInt(item.getItemGrp2()) == 11) {

								kg1QtyN = kg1QtyN + item.getItemQty();

							} else if (Integer.parseInt(item.getItemGrp2()) == 12) {

								kg2QtyN = kg2QtyN + item.getItemQty();

							} else if (Integer.parseInt(item.getItemGrp2()) == 13) {

								kg3QtyN = kg3QtyN + item.getItemQty();

							}

						} else if (Integer.parseInt(item.getItemGrp1()) == 3) {
							catId = 3;

							if (Integer.parseInt(item.getItemGrp2()) == 18) {

								kg1QtyN = kg1QtyN + item.getItemQty();

							} else if (Integer.parseInt(item.getItemGrp2()) == 19) {

								kg2QtyN = kg2QtyN + item.getItemQty();

							}

						} else if (Integer.parseInt(item.getItemGrp1()) == 4) {
							catId = 4;

							if (Integer.parseInt(item.getItemGrp2()) == 20) {

								kg1QtyN = kg1QtyN + item.getItemQty();

							}

						}
					}

					// -----------------------For Notification-----------------
					String frToken = "";

					try {
						String strMessage = "";
						if (catId == 1) {
							strMessage = "Your Order has been saved. Total Ordered- Puffs & Pattice --[" + kg1QtyN
									+ "]-Breads--[" + kg2QtyN + "]-Long Shelf--[" + kg3QtyN
									+ "] Thank You..Team Monginis";

						} else if (catId == 2) {
							if (isSameDayApplicable == 2) {
								strMessage = "Your Order has been saved. Total Ordered- Pastries --[" + kg1QtyN1 / 2
										+ "]-1/2 Kg Cake--[" + kg2QtyN1 / 2 + "]-1 Kg Cake--[" + kg3QtyN1 / 2
										+ "]-Above 1 Kg Cake--[" + kg4QtyN1 / 2 + "] Thank You..Team Monginis";
							} else {
								strMessage = "Your Order has been saved. Total Ordered- Pastries --[" + kg1QtyN1
										+ "]-1/2 Kg Cake--[" + kg2QtyN1 + "]-1 Kg Cake--[" + kg3QtyN1
										+ "]-Above 1 Kg Cake--[" + kg4QtyN1 + "] Thank You..Team Monginis";

							}
						} else if (catId == 3) {

							strMessage = "Your Order has been saved. Total Ordered- Packing Materials --[" + kg1QtyN
									+ "]-Celebrations & Party Items--[" + kg2QtyN + "]- Thank You..Team Monginis";
						} else if (catId == 4) {
							strMessage = "Your Order has been saved. Total Ordered- Pack Product --[" + kg1QtyN
									+ "]-- Thank You..Team Monginis";

						}

						MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
						map.add("frId", frDetails.getFrId());

						frToken = restTemplate.postForObject(Constant.URL + "getFrToken", map, String.class);
						Firebase.sendPushNotifForCommunication(frToken, menuTitle + " Order Placed Successfully",
								strMessage, "inbox");

					} catch (Exception e2) {
						e2.printStackTrace();
					}

					// -----------------------------------------------------End-
					// Notif------------------------------------------------------------

				} catch (Exception e) {
					System.out.println("Except Placing order " + e.getMessage());
				}

			} else { // time out for place order

				mav.addObject("errorMessage", "Timeout for placing order");
			}

		} else

		{ // qty exceed limit

			// mav.addObject("errorMessage", "You have exceed maximum limit");
		}
		return mav;

	}

	public String incrementDate(String date, int day) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

}
