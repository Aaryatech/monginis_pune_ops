package com.monginis.ops.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.monginis.ops.HomeController;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.AllMenuResponse;
import com.monginis.ops.model.CategoryList;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetCustBillTax;
import com.monginis.ops.model.GetItemHsnCode;
import com.monginis.ops.model.GetRegSpCakeOrders;
import com.monginis.ops.model.ItemOrderHis;
import com.monginis.ops.model.ItemOrderList;
import com.monginis.ops.model.MCategory;
import com.monginis.ops.model.Main;
import com.monginis.ops.model.Menus;
import com.monginis.ops.model.RegularSpCake;
import com.monginis.ops.model.RegularSpCkOrders;
import com.monginis.ops.model.SpHistoryExBill;
import com.monginis.ops.model.SpOrderHis;
import com.monginis.ops.model.SpOrderHisList;
import com.sun.org.apache.xalan.internal.xsltc.compiler.sym;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Scope("session")
public class HistoryController {
	public List<Menus> menusList;
	List<MCategory> mCategoryList;

	AllMenuResponse allMenuResponse;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	List<SpOrderHis> spOrderHistory;
	List<GetRegSpCakeOrders> regSpHistory;
	
	
	@RequestMapping(value = "/orderHistory", method = RequestMethod.GET)
	public ModelAndView ordersHistory(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("history/orderhistory");
		logger.info("/login request mapping.");
		RestTemplate rest=new RestTemplate();
	    allMenuResponse=rest.getForObject(
				Constant.URL+"/getAllMenu",
				AllMenuResponse.class);
		
		menusList= new ArrayList<Menus>();
		menusList=allMenuResponse.getMenuConfigurationPage();
		
		CategoryList catList=rest.getForObject(Constant.URL+"showAllCategory",CategoryList.class);
		mCategoryList=catList.getmCategoryList();
		System.out.println("MENU LIST= "+menusList.toString());
		model.addObject("catList",mCategoryList);
		
		System.out.println("menu list is"+menusList.toString());
		model.addObject("catId", 0);
		return model;

	}

	@RequestMapping(value = "/itemHistory", method = RequestMethod.POST)
	public ModelAndView OrderHistory(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		ModelAndView model = new ModelAndView("history/orderhistory");

		try {
			String spDeliveryDt = request.getParameter("datepicker");
			String menuTitle = "";
			HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
			int frId = frDetails.getFrId();
			Menus selectedMenu = new Menus();
			int catId = Integer.parseInt(request.getParameter("catId"));
			String parsedDate = Main.formatDate(spDeliveryDt);

			List<ItemOrderHis> itemOrderHistory;
			if (catId == 5) {
				System.out.println("sp cake order " + catId + "-" + parsedDate + "-" + frDetails.getFrCode());

				spOrderHistory = spHistory(parsedDate, frDetails.getFrCode());
				//System.out.println("sp cake order:" + spOrderHistory.toString());
				model.addObject("orderHistory", spOrderHistory);

			} else if (catId ==42|| catId ==80) {
				regSpHistory = regHistory(catId,parsedDate, frId);
				//System.out.println("regSpHistory:" + regSpHistory.toString());
				model.addObject("orderHistory", regSpHistory);
			} else if (catId != 5) {
				itemOrderHistory = orderHistory(catId, parsedDate, frId);
				//System.out.println("itemOrderHistory:" + itemOrderHistory.toString());
				model.addObject("orderHistory", itemOrderHistory);

			}

			menuTitle = selectedMenu.getMenuTitle();
			System.out.println("MenuTitle:" + menuTitle);
			model.addObject("menuTitle", menuTitle);

			model.addObject("catList", mCategoryList);
			model.addObject("catId", catId);
			model.addObject("spDeliveryDt", spDeliveryDt);
		} catch (Exception e) {
			System.out.println("Exception in order history" + e.getMessage());
		}
		return model;

	}
	@RequestMapping(value = "/getSpOrder", method = RequestMethod.GET)
	public @ResponseBody SpHistoryExBill getSpOrder(HttpServletRequest request, HttpServletResponse response) throws ParseException
	{
		SpHistoryExBill spOrderList=new SpHistoryExBill();
	 try {
			String orderno=request.getParameter("orderno");
			RestTemplate rest=new RestTemplate();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		    map.add("orderNo",orderno);		       
	        map.add("date","aa");
	        map.add("menuId","1");
	        map.add("frId","1");
		 spOrderList=rest.postForObject(Constant.URL+"/getSpCkOrderForExBill",map,SpHistoryExBill.class);
		  ArrayList<GetRegSpCakeOrders> regSpecialHistory=new ArrayList<GetRegSpCakeOrders>(Arrays.asList(spOrderList.getRegularSpCkOrders()));
		  ArrayList<SpOrderHis> spOrderHistoryRes=new ArrayList<SpOrderHis>(Arrays.asList(spOrderList.getSpCakeOrder()));

		  
		  spOrderHistory=spOrderHistoryRes;
		 regSpHistory=regSpecialHistory;
			System.out.println("spOrderHistory"+spOrderHistory.toString());
			System.out.println("regSpHistory"+regSpHistory.toString());

		}
		catch(Exception e)
		{
			System.out.println("Exception in order history"+e.getMessage());
		}
		
		return spOrderList;

	}
	@RequestMapping(value = "/getSpOrders", method = RequestMethod.GET)
	public @ResponseBody SpHistoryExBill getSpOrders(HttpServletRequest request, HttpServletResponse response) throws ParseException
	{
		SpHistoryExBill spHistoryExBill=new SpHistoryExBill();
		try {
			RestTemplate rest=new RestTemplate();

		String spDeliveryDt=request.getParameter("date");

		HttpSession session=request.getSession();
		Franchisee frDetails= (Franchisee) session.getAttribute("frDetails");
	
	    int menuId=Integer.parseInt(request.getParameter("group"));
		System.out.println("spDeliveryDt"+spDeliveryDt);
		
		String parsedDate=Main.formatDate(spDeliveryDt);
		
	     System.out.println("date"+parsedDate);
	     MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
	        map.add("date",parsedDate);
	        if(menuId==0)
	        map.add("menuId","40,41,42");
	        else
	        map.add("menuId",menuId);
	        map.add("frId",frDetails.getFrId());
	        map.add("orderNo","0");
		  spHistoryExBill=rest.postForObject(Constant.URL+"/getSpCkOrderForExBill",map,SpHistoryExBill.class);
		  
		  ArrayList<GetRegSpCakeOrders> regSpecialHistory=new ArrayList<GetRegSpCakeOrders>(Arrays.asList(spHistoryExBill.getRegularSpCkOrders()));
		  ArrayList<SpOrderHis> spOrderHistoryRes=new ArrayList<SpOrderHis>(Arrays.asList(spHistoryExBill.getSpCakeOrder()));

		  
		  spOrderHistory=spOrderHistoryRes;
		 regSpHistory=regSpecialHistory;
		 System.out.println("selected2:"+spHistoryExBill.toString());

		}
		catch(Exception e)
		{
			System.out.println("Exception in order history"+e.getMessage());
		}
		
		return spHistoryExBill;

	}
	public List<ItemOrderHis> orderHistory(int catId,String parsedDate,int frId)
	{
	
		
		RestTemplate rest=new RestTemplate();
		 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
	        map.add("catId",catId);
	        map.add("deliveryDt",parsedDate);
	        map.add("frId",frId);
		ItemOrderList itemOrderList=rest.postForObject(
				Constant.URL+"/orderHistory",map,
				ItemOrderList.class);
		List<ItemOrderHis> itemHistory=itemOrderList.getItemOrderList();
		System.out.println("OrderList"+itemHistory.toString());
		return itemHistory;
	
	}
	
   public List<SpOrderHis> spHistory(String parsedDate,String frCode)
	{
	  
	   System.out.println("spHistory");
		RestTemplate rest=new RestTemplate();
		 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
	      /*  map.add("catId",catId);*/
	        map.add("spDeliveryDt",parsedDate);
	        map.add("frCode",frCode);
		SpOrderHisList spOrderList=rest.postForObject(
				Constant.URL+"/SpCakeOrderHistory",map,
				SpOrderHisList.class);
		List<SpOrderHis> spCkHisList=spOrderList.getSpOrderList();
		System.out.println("OrderList"+spCkHisList.toString());
		return spCkHisList;
		
	}
   public List<GetRegSpCakeOrders> regHistory(int catId,String parsedDate,int frId)
   {
	   
	   System.out.println("spHistory");
	 		RestTemplate rest=new RestTemplate();
	 		 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
	 	        map.add("spDeliveryDt",parsedDate);
	 	        map.add("frId",frId);
	 	       map.add("catId",catId);
	 	        
	 	       GetRegSpCakeOrders[] rspOrderList=rest.postForObject(Constant.URL+"/getRegSpCakeOrderHistory",map,GetRegSpCakeOrders[].class);
	 	
	 		System.out.println("OrderList"+rspOrderList.toString());
	 		ArrayList<GetRegSpCakeOrders> regSpecialHistory=new ArrayList<GetRegSpCakeOrders>(Arrays.asList(rspOrderList));
	 		System.out.println("OrderList"+rspOrderList.toString());

	 		regSpHistory=regSpecialHistory;
	 		return regSpecialHistory;
   }
// -----------------For Showing Special Cake order PDF------------------------------
	@RequestMapping(value = "/showSpCakeOrderHisPDF/{spOrderNo}", method = RequestMethod.GET)
	public ModelAndView showSpCakeOrderHisPDF(@PathVariable int spOrderNo,HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/order");
		try {
			SpOrderHis spOrderHisSelected=null;
			for(SpOrderHis spOrderHis:spOrderHistory) 
			{
			   if(spOrderHis.getSpOrderNo()==spOrderNo)
			   {
				   spOrderHisSelected=spOrderHis;
				   break;
			   }
			}
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

			model.addObject("spCakeOrder", spOrderHisSelected);
			model.addObject("currDate", currentDate);
			model.addObject("currTime", time);
			model.addObject("shopName", shopName);
			model.addObject("tel", tel);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}
	// ----------------------------------END------------------------------------------------------------------
	// -----------------For Showing Regular Cake order PDF------------------------------
	@RequestMapping(value = "/showRegCakeOrderHisPDF/{rspId}", method = RequestMethod.GET)
	public ModelAndView showRegCakeOrderHisPDF(@PathVariable int rspId,HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("history/regorderMemo");
      try {
    	  GetRegSpCakeOrders rspOrderHisSelected=null;
			for(GetRegSpCakeOrders rspOrderHis:regSpHistory) 
			{
				System.err.println(rspOrderHis.getRspId());
			   if(rspOrderHis.getRspId()==rspId)
			   {
				   rspOrderHisSelected=rspOrderHis;
				   break;
			   }
			}
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

		model.addObject("regularSpCake", rspOrderHisSelected);
		model.addObject("currDate", currentDate);
		model.addObject("currTime", time);
		model.addObject("shopName", shopName);
		model.addObject("tel", tel);
      }
      catch (Exception e) {
		e.printStackTrace();
	}
		return model;

	}

	@RequestMapping(value = "/printSpCkBill/{spOrderNo}", method = RequestMethod.GET)
	public ModelAndView showExpressBillPrint(@PathVariable int spOrderNo,HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("history/spBillInvoice");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {
	
			SpOrderHis spOrderHisSelected=null;
			for(SpOrderHis spOrderHis:spOrderHistory) 
			{
			   if(spOrderHis.getSpOrderNo()==spOrderNo)
			   {
				   spOrderHisSelected=spOrderHis;
				   break;
			   }
			}
	
		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		int frGstType = frDetails.getFrGstType();
		
		model.addObject("date", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		
		//model.addObject("invNo",sellInvoiceGlobal);
		model.addObject("frGstType", frGstType);
		model.addObject("spCakeOrder", spOrderHisSelected);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
}
