package com.monginis.ops.controller;

import java.text.DateFormat;
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
import com.monginis.ops.model.FrMenu;
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

import org.bouncycastle.cert.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Scope("session")
public class HistoryController {
	public List<Menus> menusList;ArrayList<FrMenu> menuList=null;
	List<MCategory> mCategoryList=null;
	List<FrMenu> menuListSelected=null;List<FrMenu> menuListNotSelected=null;
	AllMenuResponse allMenuResponse;
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	List<SpOrderHis> spOrderHistory;
	List<GetRegSpCakeOrders> regSpHistory;
	ArrayList<FrMenu> regOrderMenuList=null;ArrayList<FrMenu> spOrderMenuList=null;
	
	@RequestMapping(value = "/orderHistory", method = RequestMethod.GET)
	public ModelAndView ordersHistory(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("history/orderhistory");
		RestTemplate rest=new RestTemplate();
	    try {
    		 HttpSession session = request.getSession();
	    	  menuList = (ArrayList<FrMenu>) session.getAttribute("menuList");
	    	  regOrderMenuList=new  ArrayList<FrMenu>();
	    	  spOrderMenuList=new  ArrayList<FrMenu>();
			   model.addObject("menuListSelected", menuListSelected);

	    	 for(FrMenu frMenu:menuList) {
	    		 if(frMenu.getCatId()==5)
	    		 {
	    			 spOrderMenuList.add(frMenu);
	    			 menuListNotSelected=spOrderMenuList;
	    		 }else
	    		 {
	    			 regOrderMenuList.add(frMenu);
	    			 menuListNotSelected=regOrderMenuList;

	    		 }
	    	 }
		    CategoryList catList=rest.getForObject(Constant.URL+"showAllCategory",CategoryList.class);
		    mCategoryList=catList.getmCategoryList();
		
		    model.addObject("catList",mCategoryList);
		    model.addObject("menuListNotSelected", menuListNotSelected);

		    //model.addObject("regOrderMenuList", regOrderMenuList);
		    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			String todaysDate = df.format(new Date());
            model.addObject("spDeliveryDt", todaysDate);
		   // model.addObject("catId", 0);
            model.addObject("orderType", 0);
	    }catch (Exception e) {
			e.printStackTrace();
		}
		    return model;

	}

	@RequestMapping(value = "/getMenus", method = RequestMethod.GET)
	public @ResponseBody List<FrMenu> getMenus(HttpServletRequest request, HttpServletResponse response){
		List<FrMenu> list=new ArrayList<>();
		try {
		int type=Integer.parseInt(request.getParameter("type"));
		if(type==1)
		{
			list=regOrderMenuList;
		}else
		{
			list=spOrderMenuList;
		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return list;
	}
	@RequestMapping(value = "/itemHistory", method = RequestMethod.POST)
	public ModelAndView OrderHistory(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		ModelAndView model = new ModelAndView("history/orderhistory");
		 menuListSelected=new ArrayList<>();
		 menuListNotSelected=new ArrayList<>();
		ArrayList<FrMenu> menuListActualNotSelected=new ArrayList<>();
		try {
   		 	  HttpSession session = request.getSession();
			  menuList = (ArrayList<FrMenu>) session.getAttribute("menuList");
	    	  regOrderMenuList=new  ArrayList<FrMenu>();
	    	  spOrderMenuList=new  ArrayList<FrMenu>();
				int orderType = Integer.parseInt(request.getParameter("orderType"));//new

	    	 for(FrMenu frMenu:menuList) {
	    		 	if(frMenu.getCatId()==5)
	    			 	{
	    			 		spOrderMenuList.add(frMenu);
	    			 	}else
	    			 	{
	    			 		regOrderMenuList.add(frMenu);

	    			 	}
	    			
	    	       }
	    	 if(orderType==1)
 			{
	    		 menuListNotSelected=regOrderMenuList;
 			}else
 			{
	    		 menuListNotSelected=spOrderMenuList;

 			}
			String spDeliveryDt = request.getParameter("datepicker");
			String menuTitle = "";
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
			int frId = frDetails.getFrId();
			Menus selectedMenu = new Menus(); 
			String catId[]=request.getParameterValues("catId[]");
			String parsedDate = Main.formatDate(spDeliveryDt);

			StringBuilder sb = new StringBuilder();
            List<Integer> catList=new ArrayList<>();
			for (int i = 0; i < catId.length; i++) {System.err.println(catId[i]);
				sb = sb.append(catId[i] + ",");
				catList.add(Integer.parseInt(catId[i]));
				
			}
			String catIdStr = sb.toString();
			catIdStr = catIdStr.substring(0, catIdStr.length() - 1);
			
			List<ItemOrderHis> itemOrderHistory;
			if (catList.contains(40) ||catList.contains(41)||catList.contains(83)||catList.contains(85)) {//if catId==5
				System.out.println("sp cake order " + catId + "-" + parsedDate + "-" + frDetails.getFrCode());

				spOrderHistory = spHistory(parsedDate, frDetails.getFrCode());
				//System.out.println("sp cake order:" + spOrderHistory.toString());
				model.addObject("orderHistory", spOrderHistory);

			} else if (catList.contains(42)||catList.contains(80)) {
				regSpHistory = regHistory(catIdStr,parsedDate, frId);
				//System.out.println("regSpHistory:" + regSpHistory.toString());
				model.addObject("orderHistory", regSpHistory);
			} else{ // if (catId != 5)  prev
				itemOrderHistory = orderHistory(catIdStr, parsedDate, frId);
				//System.out.println("itemOrderHistory:" + itemOrderHistory.toString());
				model.addObject("orderHistory", itemOrderHistory);

			}
			System.err.println(catList.toString()+"fghj");
			for(int j=0;j<menuListNotSelected.size();j++)
			{	int flag=0;
					for(int m=0;m<catList.size();m++)
					{
						if(catList.get(m)==menuListNotSelected.get(j).getMenuId())
						{
							menuListSelected.add(menuListNotSelected.get(j));
							flag=1;
						}
					}
					if(flag==0)
					{
						menuListActualNotSelected.add(menuListNotSelected.get(j));
					}
					
				
			}
			
			menuTitle = selectedMenu.getMenuTitle();
			System.out.println("MenuTitle:" + menuTitle);
			model.addObject("menuTitle", menuTitle);

			model.addObject("catList", mCategoryList);
			model.addObject("catId", catList.get(0));
			model.addObject("spDeliveryDt", spDeliveryDt);
			model.addObject("orderType", orderType);
			model.addObject("menuListSelected", menuListSelected);
			model.addObject("menuListNotSelected", menuListActualNotSelected);
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
	public List<ItemOrderHis> orderHistory(String catId,String parsedDate,int frId)
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
   public List<GetRegSpCakeOrders> regHistory(String catId,String parsedDate,int frId)
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
