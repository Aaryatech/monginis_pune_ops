package com.monginis.ops.controller;

import java.util.Date;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.ConfiguredSpDayCkResponse;
import com.monginis.ops.model.DateResponse;
import com.monginis.ops.model.Flavour;
import com.monginis.ops.model.FrMenu;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetConfiguredSpDayCk;
import com.monginis.ops.model.GetFrItem;
import com.monginis.ops.model.Main;
import com.monginis.ops.model.Orders;
import com.monginis.ops.model.TabTitleData;

@Controller
@Scope("session")
public class SpDayCakeController {

	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
	private  List<GetFrItem> frItemList = new ArrayList<>();
	private  List<GetFrItem> prevFrItemList = new ArrayList<>();
	Date productionDate;
	Date deliDate;
	public  int spdayId=0;
	public String delDate=null;
	String fromDate=null;
	String toDate=null;
	private int currentMenuId = 0;
	List<String> subCatList = new ArrayList<>();
	public MultiValueMap<String, Object> map;

	ConfiguredSpDayCkResponse configuredSpDayCkRes;
	
    //--------------------------SHOW SPECIAL DAY CAKE FORM SHOW-----------------------------------------
	List<GetConfiguredSpDayCk> configureSpDayFrList;
	
	@RequestMapping(value = "/showSpDayCake", method = RequestMethod.GET)
	public ModelAndView displaySpDayCake(HttpServletRequest request,
			HttpServletResponse response) {
	
		
		    ModelAndView model = new ModelAndView("order/spdaycake");
	
		    HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

          try {
            
		    RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			
			map.add("frId", frDetails.getFrId());
			
		    configuredSpDayCkRes = restTemplate.postForObject(Constant.URL + "/getSpDayCkList",
			        	map,ConfiguredSpDayCkResponse.class);
		  configureSpDayFrList = new ArrayList<GetConfiguredSpDayCk>();

		    configureSpDayFrList = configuredSpDayCkRes.getConfiguredSpDayCkList();
			
            }
            catch(Exception e)
            {
                System.out.println("Exception In showSpDayCake");
            	model.addObject("configureSpDayFrList", configureSpDayFrList);
       		    model.addObject("spdayId",0);
            }
	
		   model.addObject("configureSpDayFrList", configureSpDayFrList);
		   model.addObject("spdayId",0);
		
		
		 return model;
	}	
	
	//-----------------------Getting Delivery FromDate And ToDate------------------
  @RequestMapping(value = "/getDelToAndFromDate", method = RequestMethod.GET)
	public @ResponseBody DateResponse getDelToAndFromDate(@RequestParam(value = "spdayId", required = true) int spdayId) {
		
		DateResponse dateResponse=new DateResponse();
		ZoneId z = ZoneId.of("Asia/Calcutta");
		LocalTime currentTime = LocalTime.now(z); // Explicitly specify the desired/expected time zone.
		System.out.println("current time " + currentTime);
		
		for(GetConfiguredSpDayCk getConfSpDay:configureSpDayFrList)
		{
		    if(getConfSpDay.getSpdayId()==spdayId)
		    {
			   dateResponse.setDeliveryFromDate(getConfSpDay.getDeliveryFromDate());
			   dateResponse.setDeliveryToDate(getConfSpDay.getDeliveryToDate());
			   dateResponse.setFromTime(getConfSpDay.getFromTime());
			   dateResponse.setToTime(getConfSpDay.getToTime());
			   dateResponse.setCurrTime(""+currentTime);
		    }
		
		}
		System.out.println("dateResponse: "+dateResponse.toString());
		return dateResponse;
		
	}
	//-----------------------SEARCH ITEMS-------------------------------------------
	
	@RequestMapping(value = "/searchItems", method = RequestMethod.POST)
	public ModelAndView searchItems(HttpServletRequest request,
			HttpServletResponse response) {

		ModelAndView model = new ModelAndView("order/spdaycake");
		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		ArrayList<FrMenu> menuList = (ArrayList<FrMenu>) session.getAttribute("menuList");
		
		List<GetConfiguredSpDayCk> configureSpDayFrList = new ArrayList<GetConfiguredSpDayCk>();

		configureSpDayFrList = configuredSpDayCkRes.getConfiguredSpDayCkList();
		
		System.out.println("SpdayId"+spdayId);
		
		 spdayId=Integer.parseInt(request.getParameter("spdayId"));
	
		
		GetConfiguredSpDayCk spDayCk=new GetConfiguredSpDayCk();
		
	   for(GetConfiguredSpDayCk getConfiguredSpDayCk:configureSpDayFrList)
	   {
		  if(getConfiguredSpDayCk.getSpdayId()==spdayId)
		  {
			  spDayCk=getConfiguredSpDayCk;
		  }
	   }
	   System.out.println("Special Day Cake Response:"+spDayCk.toString());
	   
		 delDate=request.getParameter("datepicker");
		 fromDate=request.getParameter("fromDate");
		 toDate=request.getParameter("toDate");
		 
		try {
			
	       Date deliveryDate=Main.stringToDate(delDate);
			
			DateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy");
			
			deliveryDate = dmyFormat.parse(delDate);
		
	      System.out.println("Delivery date "+deliveryDate);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(deliveryDate);

		 deliDate = cal.getTime();
		 
		  cal.add(Calendar.DATE, -1);
		
		// manipulate date
		//c.add(Calendar.DATE, prodTime);
			productionDate = cal.getTime();

			
			DateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");

			String strProdDate = ymdFormat.format(productionDate);
		
			
			System.out.println("String ymd  date is: " +strProdDate);

	    	 map = new LinkedMultiValueMap<String, Object>();

			map.add("items",spDayCk.getItemId());
			map.add("frId", frDetails.getFrId());
			map.add("date", strProdDate);
			map.add("menuId",spDayCk.getMenuId());

			RestTemplate restTemplate = new RestTemplate();

			ParameterizedTypeReference<List<GetFrItem>> typeRef = new ParameterizedTypeReference<List<GetFrItem>>() {};
			ResponseEntity<List<GetFrItem>> responseEntity = restTemplate.exchange(Constant.URL + "/getFrItems",
					HttpMethod.POST, new HttpEntity<>(map), typeRef);

			frItemList = responseEntity.getBody();
			prevFrItemList = responseEntity.getBody();
			System.out.println("Fr Item List " + frItemList.toString());
			
			model.addObject("frDetails", frDetails);
			model.addObject("itemList", frItemList);
			model.addObject("menuId", spDayCk.getMenuId());
			model.addObject("fromDate",fromDate);
			model.addObject("toDate",toDate);
			model.addObject("delDate",delDate );
			model.addObject("spdayId",spdayId);

			model.addObject("configureSpDayFrList", configureSpDayFrList);
	       } catch (Exception e) {

	        	System.out.println("Exception Item List " + e.getMessage());
	      }
		

		return model;
	}		
	//-----------------------SEARCH ITEMS-------------------------------------------
	
		@RequestMapping(value = "/spDayCake", method = RequestMethod.GET)
		public ModelAndView redirectToSpDayCake(HttpServletRequest request,
				HttpServletResponse response) {

			ModelAndView model = new ModelAndView("order/spdaycake");
			HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			ArrayList<FrMenu> menuList = (ArrayList<FrMenu>) session.getAttribute("menuList");
			
			List<GetConfiguredSpDayCk> configureSpDayFrList = new ArrayList<GetConfiguredSpDayCk>();

			configureSpDayFrList = configuredSpDayCkRes.getConfiguredSpDayCkList();
			
			System.out.println("SpdayId"+spdayId);
			
			
			GetConfiguredSpDayCk spDayCk=new GetConfiguredSpDayCk();
			
		   for(GetConfiguredSpDayCk getConfiguredSpDayCk:configureSpDayFrList)
		   {
			  if(getConfiguredSpDayCk.getSpdayId()==spdayId)
			  {
				  spDayCk=getConfiguredSpDayCk;
			  }
		   }
		   System.out.println("Special Day Cake Response:"+spDayCk.toString());
		   
			try {
				
		       Date deliveryDate=Main.stringToDate(delDate);
				
				DateFormat dmyFormat = new SimpleDateFormat("dd-MM-yyyy");
				
				deliveryDate = dmyFormat.parse(delDate);
			
		      System.out.println("Delivery date "+deliveryDate);
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(deliveryDate);

			 deliDate = cal.getTime();
			 
			  cal.add(Calendar.DATE, -1);
			
			// manipulate date
			//c.add(Calendar.DATE, prodTime);
				productionDate = cal.getTime();

				
				DateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd");

				String strProdDate = ymdFormat.format(productionDate);
			
				
				System.out.println("String ymd  date is: " +strProdDate);

		    	 map = new LinkedMultiValueMap<String, Object>();

				map.add("items",spDayCk.getItemId());
				map.add("frId", frDetails.getFrId());
				map.add("date", strProdDate);
				map.add("menuId",spDayCk.getMenuId());

				RestTemplate restTemplate = new RestTemplate();

				ParameterizedTypeReference<List<GetFrItem>> typeRef = new ParameterizedTypeReference<List<GetFrItem>>() {};
				ResponseEntity<List<GetFrItem>> responseEntity = restTemplate.exchange(Constant.URL + "/getFrItems",
						HttpMethod.POST, new HttpEntity<>(map), typeRef);

				frItemList = responseEntity.getBody();
				prevFrItemList = responseEntity.getBody();
				System.out.println("Fr Item List " + frItemList.toString());
				
				model.addObject("frDetails", frDetails);
				model.addObject("itemList", frItemList);
				model.addObject("menuId", spDayCk.getMenuId());
				
				model.addObject("delDate",delDate );
				model.addObject("spdayId",spdayId);
				model.addObject("fromDate",fromDate);
				model.addObject("toDate",toDate);
				model.addObject("configureSpDayFrList", configureSpDayFrList);
		       } catch (Exception e) {

		        	System.out.println("Exception Item List " + e.getMessage());
		      }
			

			return model;
		}		
				
	//--------------------------SAVE SPECIAL DAY CAKE ORDER----------------------------------------	
	@RequestMapping("/saveSpDayCakeOrder")
	public String  saveSpDayCakeOrder(HttpServletRequest request, HttpServletResponse res) throws IOException {

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		ModelAndView mav = new ModelAndView("redirect:/showSpDayCake");

		String orderDate = "";
		

		String todaysDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		SimpleDateFormat dateFrmt = new SimpleDateFormat("yyyy-MM-dd");

		orderDate = todaysDate;
		String prodDate = dateFrmt.format(productionDate);
		String deliveryDt = dateFrmt.format(deliDate);
		//String fromDate=request.getParameter("fromDate");//
		//String toDate=request.getParameter("toDate");//
		System.out.println(fromDate);
		int menuId = Integer.parseInt(request.getParameter("menuId"));
		int rateCat = frDetails.getFrRateCat();
		
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

		System.out.println("Order List " + orderList.toString());

		try {

			RestTemplate restTemplate = new RestTemplate();

			String url = Constant.URL + "placeOrder";

			ObjectMapper mapperObj = new ObjectMapper();

			List<Orders> orders = new ArrayList<>();

			for (int i = 0; i < orderList.size(); i++) {

				GetFrItem frItem = orderList.get(i);

				Orders order = new Orders();
				
				
				int frGrnTwo=frDetails.getGrnTwo();
				System.out.println("Franchisee Grn Two*****************"+frGrnTwo);
				System.out.println("Item Grn Two*****************"+frItem.getGrnTwo());

				if(frItem.getGrnTwo()==1)
				{
					
					if(frGrnTwo==1)
					{
						
						order.setGrnType(1);
						
						
					}
					else
					{
						order.setGrnType(0);
					}
					
				}
				else {
					
					if(frItem.getGrnTwo()==2){
				
						order.setGrnType(2);
					
					 }
				     else
				     {
					  order.setGrnType(0);
				     }
				
				}//else End
    			order.setDeliveryDate(Common.stringToSqlDate(deliveryDt));
				order.setEditQty(frItem.getItemQty());
				order.setFrId(frDetails.getFrId());
				order.setIsEdit(0);
				order.setIsPositive(1);
				order.setItemId(frItem.getId().toString());
				order.setMenuId(menuId);
				order.setOrderDate(Common.stringToSqlDate(orderDate));
				order.setOrderDatetime(todaysDate);
				order.setOrderQty(frItem.getItemQty());
				order.setOrderSubType(Integer.parseInt(frItem.getItemGrp2()));
				order.setOrderType(Integer.parseInt(frItem.getItemGrp1()));
				order.setProductionDate(Common.stringToSqlDate(prodDate));
				order.setRefId(0);
				order.setUserId(0);
			

				if (rateCat == 1) {
					order.setOrderMrp(frItem.getItemMrp1());
					order.setOrderRate(frItem.getItemRate1());

				}else if (rateCat == 3) {
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

		} catch (Exception e) {
			System.out.println("Except Placing order " + e.getMessage());
			e.printStackTrace();
		}

	
	return "redirect:/spDayCake";
	}
	//-------------------------------------------------------------------------------
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
//----------------------------------------------------------------------------------