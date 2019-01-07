package com.monginis.ops.controller;



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
import com.monginis.ops.model.SubCategoryResponse;
import com.monginis.ops.model.frsetting.FrSetting;
import com.monginis.ops.model.AllspMessageResponse;
import com.monginis.ops.model.ErrorMessage;
import com.monginis.ops.model.EventList;
import com.monginis.ops.model.Flavour;
import com.monginis.ops.model.FrMenu;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetRegularSpCkItem;
import com.monginis.ops.model.Info;
import com.monginis.ops.model.MCategory;
import com.monginis.ops.model.Main;
import com.monginis.ops.model.RegularSpCake;
import com.monginis.ops.model.RegularSpCkItemResponse;
import com.monginis.ops.model.SpMessage;
import com.monginis.ops.model.SubCategory;
   
@Controller
@Scope("session")
public class RegularSpCakeController {
	
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);
	EventList eventList;
	ArrayList<FrMenu> menuList;
	private  int currentMenuId = 0;
	private  int globalIndex = 2;
 	String itemShow;
	 List<MCategory>  mCategories;
	 RegularSpCake regularSpCake=null;
	 String spName=""; int qty=1;	
	 String rspDeliveryDt="";float rgCkPrice=0; float tax=0; float rspSubTotal=0; 	String rspPlace="";String rspCustName="";  String spCustDOB="";String rspCustMobileNo="";
	 int rspSubCat=0;int itemId=0;String rspEvents="";float rspRemainingAmt=0; String rspEventsName=""; 
	 float totalAmt=0; float rgGstAmount=0; float gstRs=0; float rspAdvanceAmt=0;	float rgCkGrand=0; float rspMrp=0; 
	 float rspRate=0; float tax1=0; float tax2=0; float tax1Amt=0;  float tax2Amt=0;
	 List<SpMessage> spMessageList;
	 
	 RegularSpCkItemResponse regularSpCkItemList;
	String spImage = "0463a490-b678-46d7-b31d-d7d6bae5c954-ats.png";//Default Image to spCake order Page
   //--------------------------Show Regular Special Cake Order Page--------------------------------------
		@RequestMapping(value = "/showRegularSpCakeOrder/{index}", method = RequestMethod.GET)
		public ModelAndView displayregularSpCakeOrder(@PathVariable("index") int index,HttpServletRequest request,
				HttpServletResponse response) {
			
			logger.info("/regularSpCkOrder  request mapping");

			ModelAndView model = new ModelAndView("order/regularSpCkOrder");
			HttpSession session = request.getSession();
			
			
			RestTemplate restTemplate = new RestTemplate();
	         try {
	        	     menuList = (ArrayList<FrMenu>) session.getAttribute("menuList");

	 			    currentMenuId = menuList.get(index).getMenuId();
	 			    String menutitle=menuList.get(index).getMenuTitle();
	 			    System.out.println("MenuList" + currentMenuId);
	 			    globalIndex = index;
	 			    
	 			   Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
	 				itemShow = menuList.get(globalIndex).getItemShow();
	 				
				    logger.info("/ITEMSHOW"+itemShow);

	        	 //   MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				  //  map.add("catId",2);
		
				    SubCategoryResponse categoryResponse = restTemplate.getForObject(Constant.URL + "/showAllCategory",SubCategoryResponse.class);
				    logger.info("/regularSpCkOrder  request mapping"+categoryResponse.toString());
              
					AllspMessageResponse allspMessageList = restTemplate.getForObject(Constant.URL + "getAllSpMessage",
							AllspMessageResponse.class);

					spMessageList = allspMessageList.getSpMessage();
	                /*EventList eventList = restTemplate.getForObject(Constant.URL + "/showEventList", EventList.class);
			        logger.info("/regularSpCkOrder Event List"+eventList.toString());*/
		
					List<MCategory> catList=new ArrayList<>();
			        mCategories=categoryResponse.getmCategoryList();
			        
			    	for (MCategory mCategory : mCategories) {
			    		if(currentMenuId==42) {
						if (mCategory.getCatId() == 2) {
							catList.add(mCategory);
                            model.addObject("mainCatId", mCategory.getCatId());
						}
			    		}
			    		else
			    			if(currentMenuId==29||currentMenuId==30||currentMenuId==80)
			    			{
			    				if (mCategory.getCatId() == 1) {
									catList.add(mCategory);
		                            model.addObject("mainCatId", mCategory.getCatId());
								}
			    			}
			    		
					}
			    	String spNo="";
			      try {
			    	  spNo=getSpNo(request,response);
			      }catch (Exception e) {
			    	  spNo="";
					e.printStackTrace();
				}
				    model.addObject("frDetails",frDetails);
				    
				    model.addObject("eventList", spMessageList);
			
				   // model.addObject("categoryResponse", subCategories);
				    
				    model.addObject("mCategories", catList);
				     
				    model.addObject("url", Constant.SPCAKE_IMAGE_URL);
				    model.addObject("title",menutitle);
				    model.addObject("currentMenuId",currentMenuId);
				    model.addObject("spNo", spNo);
			} catch (Exception e) {
				
				System.out.println("Show Regular Sp Cake Page Excep: " + e.getMessage());
				

			}

			return model;
		}
		
	@RequestMapping(value = "/showRegularSpCakeOrderRes", method = RequestMethod.GET)
	public ModelAndView showRegularSpCakeOrderRes(HttpServletRequest request, HttpServletResponse response) {

		logger.info("/regularSpCkOrder  request mapping");

		ModelAndView mav = new ModelAndView("order/regularSpOrderRes");

		try {
			if (regularSpCake != null) {
				mav.addObject("rspEvents", rspEvents);
				mav.addObject("rspEventsName", rspEventsName);
				mav.addObject("qty", qty);
				mav.addObject("URL", Constant.SPCAKE_IMAGE_URL);
				mav.addObject("isFound", true);
				mav.addObject("rspDeliveryDt", rspDeliveryDt);
				mav.addObject("rspCustName", rspCustName);
				mav.addObject("rspCustMobileNo", rspCustMobileNo);
				mav.addObject("rspCustMobileNo", rspCustMobileNo);
				mav.addObject("rgCkPrice", rgCkPrice);
				mav.addObject("rgCkGrand", rgCkGrand);
				mav.addObject("rspSubTotal", rspSubTotal);
				mav.addObject("tax", tax);
				mav.addObject("gstRs", gstRs);
				mav.addObject("totalAmt", totalAmt);
				mav.addObject("rspAdvanceAmt", rspAdvanceAmt);
				mav.addObject("rspRemainingAmt", rspRemainingAmt);
				mav.addObject("spPlace", rspPlace);
				mav.addObject("spCustDOB", spCustDOB);
				mav.addObject("spName", spName);
				mav.addObject("rspName", "");
				mav.addObject("rgGstAmount", rgGstAmount);
				mav.addObject("globalIndex", globalIndex);
			} else {
				mav.addObject("rspEvents", rspEvents);
				mav.addObject("rspEventsName", rspEventsName);
				mav.addObject("qty", qty);
				mav.addObject("URL", Constant.SPCAKE_IMAGE_URL);
				mav.addObject("isFound", false);
				mav.addObject("convertedDelDate", rspDeliveryDt);
				mav.addObject("rspCustName", rspCustName);
				mav.addObject("rspCustMobileNo", rspCustMobileNo);
				mav.addObject("rspCustMobileNo", rspCustMobileNo);
				mav.addObject("rgCkPrice", rgCkPrice);
				mav.addObject("rgCkGrand", rgCkGrand);
				mav.addObject("rspSubTotal", rspSubTotal);
				mav.addObject("tax", tax);
				mav.addObject("gstRs", gstRs);
				mav.addObject("totalAmt", totalAmt);
				mav.addObject("rspAdvanceAmt", rspAdvanceAmt);
				mav.addObject("rspRemainingAmt", rspRemainingAmt);
				mav.addObject("spPlace", rspPlace);
				mav.addObject("spCustDOB", spCustDOB);
				mav.addObject("spName", spName);
				mav.addObject("rspName", "");
				mav.addObject("rgGstAmount", rgGstAmount);
				mav.addObject("globalIndex", globalIndex);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

		public static String getSpNo(HttpServletRequest request, HttpServletResponse response) {
			String spNoNewStr= "";
			try {
			
				MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

				RestTemplate restTemplate = new RestTemplate();

				HttpSession session = request.getSession();

				Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

				int frId = frDetails.getFrId();

				map.add("frId", frId);
				FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map, FrSetting.class);

				int spNo = frSetting.getSpNo();

				int length = String.valueOf(spNo).length();
				length = 5 - length;
				StringBuilder spNoNew = new StringBuilder(frSetting.getFrCode()+"-");

				for (int i = 0; i < length; i++) {
					String j = "0";
					spNoNew.append(j);
				}
				spNoNew.append(String.valueOf(spNo));
				spNoNewStr=""+spNoNew;
				
			} catch (Exception e) {

			}

			return spNoNewStr;

		}
	    //-----------------------------------END-----------------------------------------
		@RequestMapping(value = "/findSubCategory", method = RequestMethod.GET)
		public @ResponseBody  List<SubCategory> findSubCategory(@RequestParam(value = "catId", required = true) int catId) {
		    List<SubCategory> subCategories = new ArrayList<SubCategory>();

			  logger.info(" Category Id:"+catId);
			  try {
					RestTemplate restTemplate = new RestTemplate();

				   MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
				    map.add("catId",catId);
		
				    SubCategoryResponse categoryResponse = restTemplate.postForObject(Constant.URL + "/getSubCategories",
		    		             map,SubCategoryResponse.class);
			
				     for(MCategory  mCategory:categoryResponse.getmCategoryList())
				     {
				    	 subCategories=mCategory.getSubCategoryList();
				     }
				     
			  }
			  catch (Exception e) {
				e.printStackTrace();
			}
			  return subCategories;
		}
		 //-------------------------GET ALL Regular Cakes(AJAX METHOD)-------------------------
		@RequestMapping(value = "/getAllRegularSpCk", method = RequestMethod.GET)
		public @ResponseBody List<GetRegularSpCkItem> getAllItems(@RequestParam(value = "regular_sp_cake", required = true) int subCatId) {

			  logger.info("Sub Category Id:"+subCatId);
			
			List<GetRegularSpCkItem> regularSpCkItems=new ArrayList<GetRegularSpCkItem>();
			 RestTemplate restTemplate = new RestTemplate();
			try {
			 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		     map.add("itemGrp2", subCatId);
		     map.add("items", itemShow);

		   regularSpCkItemList=restTemplate.postForObject(Constant.URL + "/getRegularSpCkItems",map,RegularSpCkItemResponse.class);
		     
		    regularSpCkItems=regularSpCkItemList.getGetRegularSpCkItems();

		     
		    logger.info("AJAXITEMSHOW"+itemShow);

		    logger.info("regularSpCkItems"+regularSpCkItems.toString());
			}
			catch(Exception e)
			{
	           	 logger.error("Exception in Get All Regular Sp Cake Ajax Method.");
			
		    }
			return regularSpCkItems;

		}
		//----------------------------------------END--------------------------------------------

		 //-------------------------GET Regular Cake (AJAX METHOD)-------------------------
		@RequestMapping(value = "/getRegSpecialCkById", method = RequestMethod.GET)
		public @ResponseBody GetRegularSpCkItem getSpecialCkById(@RequestParam(value = "id", required = true) int id) {
			
			List<GetRegularSpCkItem> regularSpCkItems=new ArrayList<GetRegularSpCkItem>();
		    regularSpCkItems=regularSpCkItemList.getGetRegularSpCkItems();
		    GetRegularSpCkItem getRegularSpCkItem=new GetRegularSpCkItem();

		     for(GetRegularSpCkItem regularSpCkItem:regularSpCkItems)
		     {
		    	 if(regularSpCkItem.getId()==id)
		    	 {
		    		 getRegularSpCkItem=regularSpCkItem;
		    	 }
		     
		}
				return getRegularSpCkItem;
		}
		  //------------------------Order Regular Special Cake Process----------------------------
		@RequestMapping(value = "/orderRegularSpCake", method = RequestMethod.POST)
		public String orderRegularSpCake(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, ParseException {

			ModelAndView mav;
			HttpSession session = request.getSession();
           try {
			    Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
	      
			    ArrayList<FrMenu> menuList = (ArrayList<FrMenu>) session.getAttribute("menuList");
			
			    rspSubCat = Integer.parseInt(request.getParameter("regular_sp_cake"));
			    logger.info("1" + rspSubCat);
			
			     itemId = Integer.parseInt(request.getParameter("regSpCkItem"));
			     logger.info("2" + itemId);

			     spName = request.getParameter("rg_sp_name");
			
			     rspEvents = request.getParameter("sp_event");
			     logger.info("7" + rspEvents);
			
			     rspDeliveryDt = request.getParameter("datepicker");
			     logger.info("8" + rspDeliveryDt);
			 
			     String convertedDelDate=Main.formatDate(rspDeliveryDt);               
		
			     spCustDOB = request.getParameter("datepicker2");
			     logger.info("9" + spCustDOB);

			     rspCustMobileNo = request.getParameter("sp_cust_mobile_no");
			     logger.info("10" + rspCustMobileNo);

			     rspCustName = request.getParameter("sp_cust_name");
			     logger.info("11" + rspCustName);

		
			     rgCkGrand = Float.parseFloat(request.getParameter("sp_grand"));
			     logger.info("12" + rgCkGrand);
			
			     rspMrp = Float.parseFloat(request.getParameter("MRP"));
			     logger.info("12" + rgCkGrand);
			
			     rspRate = Float.parseFloat(request.getParameter("rate"));
			     logger.info("12" + rgCkGrand);

			     rgCkPrice = Float.parseFloat(request.getParameter("sp_calc_price"));
			 	logger.info("13" + rgCkPrice);

		
			 	rspSubTotal = Float.parseFloat(request.getParameter("sp_sub_total"));
			 	logger.info("14" + rspSubTotal);

			 	tax = Float.parseFloat(request.getParameter("t3"));
			 	logger.info("15" + tax);
			 
			 	tax1 = Float.parseFloat(request.getParameter("t1"));
			 	logger.info("15" + tax1);
			 
			 	tax2 = Float.parseFloat(request.getParameter("t2"));
			 	logger.info("15" + tax2);
			 
			 	tax1Amt = Float.parseFloat(request.getParameter("t1Amt"));
			 	logger.info("15" + tax1Amt);
			 	tax2Amt = Float.parseFloat(request.getParameter("t2Amt"));
			 	logger.info("15" + tax2Amt);

			 rspRemainingAmt = Float.parseFloat(request.getParameter("rm_amount"));
			 logger.info("16" + rspRemainingAmt);

			 rspAdvanceAmt = Float.parseFloat(request.getParameter("adv"));
			 logger.info("16" + rspAdvanceAmt);

			 rspPlace = request.getParameter("sp_place");
			 logger.info("17" + rspPlace);

			 rspEventsName = request.getParameter("event_name");
			 logger.info("18" + rspEventsName);

			
			 gstRs = Float.parseFloat(request.getParameter("gst_rs"));
			 logger.info("19" + gstRs);

			 rgGstAmount = Float.parseFloat(request.getParameter("m_gst_amt"));
			 logger.info("20" + rgGstAmount);

			 totalAmt = Float.parseFloat(request.getParameter("total_amt"));
			 logger.info("21" + totalAmt);

			 qty= Integer.parseInt(request.getParameter("sp_qty"));
			 logger.info("22" + qty);

			
			RegularSpCake regularSpCakeOrder = new RegularSpCake();
			

			// -----Order Date And Production Date------

		  SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Date currentDate = new Date();

			// convert date to calendar
			Calendar c = Calendar.getInstance();
			c.setTime(currentDate);
			Date calOrderDate = c.getTime();
			
			String convertedOrderDate = dateFormat.format(calOrderDate);
			System.out.println("Todays date is: " + calOrderDate);
			// -------------------------------------------
			Date newProdDate=dateFormat.parse(rspDeliveryDt);
			Calendar cal = Calendar.getInstance();
			cal.setTime (newProdDate); // convert your date to Calendar object
			cal.add(Calendar.DATE, -1);
			 Date  produDate = cal.getTime();
			 System.out.println("production Date:"+dateFormat.format(produDate));
			//---------------------------------------------
			// Date yesdate = new Date (System.currentTimeMillis()-24*60*60*1000);
			 
			regularSpCakeOrder.setFrCode(frDetails.getFrCode());
			regularSpCakeOrder.setMenuId(currentMenuId);

			regularSpCakeOrder.setFrId(frDetails.getFrId());
			
			regularSpCakeOrder.setItemId(itemId);
			regularSpCakeOrder.setMenuId(currentMenuId);
			regularSpCakeOrder.setMrp(rspMrp);
			regularSpCakeOrder.setRate(rspRate);
			regularSpCakeOrder.setQty(qty);
			regularSpCakeOrder.setRateCat(frDetails.getFrRateCat());
			regularSpCakeOrder.setRspAdvanceAmt(rspAdvanceAmt);
			regularSpCakeOrder.setRspCustMobileNo(rspCustMobileNo);
			regularSpCakeOrder.setRspCustName(rspCustName);
			regularSpCakeOrder.setRspDeliveryDt(rspDeliveryDt);
			regularSpCakeOrder.setOrderDate(convertedOrderDate);
			regularSpCakeOrder.setRspEvents(rspEvents);
			regularSpCakeOrder.setRspSubCat(rspSubCat);
			regularSpCakeOrder.setRspSubTotal(rspSubTotal);
			regularSpCakeOrder.setRspRemainingAmt(rspRemainingAmt);
			regularSpCakeOrder.setTax1(tax1);
			regularSpCakeOrder.setTax1Amt(tax1Amt);
			regularSpCakeOrder.setTax2(tax2);
			regularSpCakeOrder.setTax2Amt(tax2Amt);
			
			regularSpCakeOrder.setRspEventsName(rspEventsName);
			regularSpCakeOrder.setRspProduDate(dateFormat.format(produDate));
			
			String spNo="";
			try {
			 spNo=getSpNo(request,response);
			}
			catch (Exception e) {
				spNo=rspPlace;
				e.printStackTrace();
			}
            regularSpCakeOrder.setRspPlace(spNo);
			
			
				HttpHeaders httpHeaders = new HttpHeaders();
				httpHeaders.set("Content-Type", "application/json");

				ObjectMapper mapper = new ObjectMapper();
				String jsonInString = mapper.writeValueAsString(regularSpCakeOrder);
				System.out.println("All Sp Order Data" + jsonInString.toString());

				HttpEntity<String> httpEntity = new HttpEntity<String>(jsonInString.toString(), httpHeaders);
		try {
					  
				RestTemplate restTemplate = new RestTemplate();
				
				 regularSpCake= restTemplate.postForObject(Constant.URL + "/insertRegularSpCake", httpEntity,
						RegularSpCake.class);
				 
				 if(regularSpCake!=null)
				 {
					 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

						map.add("frId", frDetails.getFrId());

						Info updateFrSettingGrnGvnNo = restTemplate.postForObject(Constant.URL + "updateFrSettingSpNo", map, Info.class);

				 }
                                
				//-----------------------For Notification-----------------
				String frToken="";
			
				 try {
					   MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
					   map.add("frId",frDetails.getFrId());
					    
                      frToken= restTemplate.postForObject(Constant.URL+"getFrToken", map, String.class);
			          Firebase.sendPushNotifForCommunication(frToken,"Regular Cake as Special Order Placed Sucessfully","Your Regular cake Order has been saved. Order Saved is--Item name--"+spName+"--Qty--"+qty+"--Total Amount-"+rgCkGrand+". Thank You.Team Monginis","inbox");
			    	
			         }
			         catch(Exception e2)
			         {
				       e2.printStackTrace();
			         }
				
				//-----------------------------------------------------

			} catch (Exception e) {
				
					System.out.println("Exc" + e.getMessage());
				
			}
           }
           catch (Exception e) {
        	   
		       e.printStackTrace();
		}
			return "redirect:/showRegularSpCakeOrderRes/";

		}
		// -----------------For Showing Regular Cake order PDF------------------------------
		@RequestMapping(value = "/showRegCakeOrderPDF", method = RequestMethod.GET)
		public ModelAndView displayLogin(HttpServletRequest request, HttpServletResponse response) {

			ModelAndView model = new ModelAndView("order/regorderpdf");
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

			model.addObject("regularSpCake", regularSpCake);
			model.addObject("currDate", currentDate);
			model.addObject("currTime", time);
			model.addObject("shopName", shopName);
			model.addObject("rspName", spName);
			model.addObject("tel", tel);
	      }
	      catch (Exception e) {
			e.printStackTrace();
		}
			return model;

		}
		// ----------------------------------END------------------------------------------------------------------

		

}
//----------------------------------END----------------------------------------