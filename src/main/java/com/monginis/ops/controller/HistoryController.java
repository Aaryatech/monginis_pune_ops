package com.monginis.ops.controller;

import java.io.BufferedInputStream;
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
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.monginis.ops.HomeController;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.AllMenuResponse;
import com.monginis.ops.model.CategoryList;
import com.monginis.ops.model.ExportToExcel;
import com.monginis.ops.model.FrItemStockConfiResponse;
import com.monginis.ops.model.FrItemStockConfigure;
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

//import org.bouncycastle.cert.ocsp.Req;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@Scope("session")
public class HistoryController { 
	public List<Menus> menusList;ArrayList<FrMenu> menuList=null;
	List<MCategory> mCategoryList=null;
	List<FrMenu> menuListSelected=null;List<FrMenu> menuListNotSelected=null;
	AllMenuResponse allMenuResponse;
	List<SpOrderHis> spOrderHistory;int flag=0;
	List<GetRegSpCakeOrders> regSpHistory;	List<ItemOrderHis> itemOrderHistory;
	ArrayList<FrMenu> regOrderMenuList=null;ArrayList<FrMenu> spOrderMenuList=null;
	
	@RequestMapping(value = "/orderHistory", method = RequestMethod.GET)
	public ModelAndView ordersHistory(HttpServletRequest request, HttpServletResponse response)
	{
		ModelAndView model = new ModelAndView("history/orderhistory");
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
	    		 }else
	    		 {
	    			 regOrderMenuList.add(frMenu);
	    			 menuListNotSelected=regOrderMenuList;

	    		 }
	    	 }
		    model.addObject("menuListNotSelected", menuListNotSelected);
		    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
			String todaysDate = df.format(new Date());
            model.addObject("spDeliveryDt", todaysDate);
            model.addObject("orderType", 0);
 		   // model.addObject("catId", 0);
		   //model.addObject("regOrderMenuList", regOrderMenuList);
           /*CategoryList catList=rest.getForObject(Constant.URL+"showAllCategory",CategoryList.class);
		    mCategoryList=catList.getmCategoryList();
		    model.addObject("catList",mCategoryList);*/
            
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
			String parsedDate = Main.formatDate(spDeliveryDt);

			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
			int frId = frDetails.getFrId();
			String catId[]=request.getParameterValues("catId");

			StringBuilder sb = new StringBuilder();
            List<Integer> catList=new ArrayList<>();
            
			for (int i = 0; i < catId.length; i++) {
				sb = sb.append(catId[i] + ",");
				catList.add(Integer.parseInt(catId[i]));
			}
			String catIdStr = sb.toString();
			catIdStr = catIdStr.substring(0, catIdStr.length() - 1);
			
			//List<ItemOrderHis> itemOrderHistory;
			if (orderType==2 || catList.contains(40) ||catList.contains(41)||catList.contains(83)||catList.contains(85)) {//if catId==5
				flag=1;
				spOrderHistory = spHistory(catIdStr,parsedDate, frDetails.getFrCode());
				model.addObject("orderHistory", spOrderHistory);
				/***************************Excel************************************/
				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();

				rowData.add("Item name");

				rowData.add("Flavour");

				rowData.add("Delivery Date");
				rowData.add("Rate");

				rowData.add("Add On Rate");

				rowData.add("Total");
				rowData.add("Advance");
																//orderList.spGrandTotal-orderList.spTotalAddRate					
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				for (int i = 0; i < spOrderHistory.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					float price = spOrderHistory.get(i).getSpGrandTotal()-spOrderHistory.get(i).getSpTotalAddRate(); 
							
					rowData.add("" + spOrderHistory.get(i).getSpName());
					//rowData.add("" + spCkHisList.get(i).getItemId());
					rowData.add("" + spOrderHistory.get(i).getSpfName());
					rowData.add("" + spOrderHistory.get(i).getSpDeliveryDate());
					rowData.add("" + price);
					rowData.add("" + spOrderHistory.get(i).getSpTotalAddRate());
					rowData.add("" + spOrderHistory.get(i).getSpGrandTotal());
					rowData.add("" + spOrderHistory.get(i).getSpAdvance());
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}
                //HttpSession session = request.getSession();
				session.setAttribute("exportExcelList", exportToExcelList);
				session.setAttribute("excelName", "SpOrderHistory");

			} else if (orderType==1 && (catList.contains(42)||catList.contains(80))) {
				
				regSpHistory = regHistory(catIdStr,parsedDate, frId);
				model.addObject("orderHistory", regSpHistory);
			} else if(orderType==1){
				// if (catId != 5)  prev
				flag=2;
				itemOrderHistory = orderHistory(catIdStr, parsedDate, frId);
				model.addObject("orderHistory", itemOrderHistory);
				
				List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

				ExportToExcel expoExcel = new ExportToExcel();
				List<String> rowData = new ArrayList<String>();
				
				rowData.add("Item name");

				rowData.add("MRP");

				rowData.add("Qty.");
				rowData.add("Rate");

				rowData.add("Total");
																//orderList.spGrandTotal-orderList.spTotalAddRate					
				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);
				for (int i = 0; i < itemOrderHistory.size(); i++) {
					expoExcel = new ExportToExcel();
					rowData = new ArrayList<String>();
					double total = itemOrderHistory.get(i).getOrderQty()*itemOrderHistory.get(i).getOrderRate();
					rowData.add("" + itemOrderHistory.get(i).getItemName());
					rowData.add("" + itemOrderHistory.get(i).getOrderMrp() );
					rowData.add("" + itemOrderHistory.get(i).getOrderQty());
				
					rowData.add("" + itemOrderHistory.get(i).getOrderRate());
					rowData.add("" + total);
					
					expoExcel.setRowData(rowData);
					exportToExcelList.add(expoExcel);

				}

				//HttpSession session = request.getSession();
				session.setAttribute("exportExcelList", exportToExcelList);
				session.setAttribute("excelName", "ItemHistoryReport");
				
			}
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
			//model.addObject("catList", mCategoryList);
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
	
   public List<SpOrderHis> spHistory(String catId,String parsedDate,String frCode)
	{
	  
		RestTemplate rest=new RestTemplate();
		 MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
	        map.add("catId",catId);
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
		RestTemplate restTemplate = new RestTemplate();

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
			int settingValue = 0;
			try {
				FrItemStockConfiResponse frItemStockConfiResponse = restTemplate
						.getForObject(Constant.URL + "getfrItemConfSetting", FrItemStockConfiResponse.class);
				List<FrItemStockConfigure> frItemStockConfigures = new ArrayList<FrItemStockConfigure>();
				frItemStockConfigures = frItemStockConfiResponse.getFrItemStockConfigure();

				for (int i = 0; i < frItemStockConfigures.size(); i++) {

					if (frItemStockConfigures.get(i).getSettingKey().equalsIgnoreCase("spInvoiceHsn")) {
						settingValue = frItemStockConfigures.get(i).getSettingValue();
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		int frGstType = frDetails.getFrGstType();
		
		model.addObject("date", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
		
		//model.addObject("invNo",sellInvoiceGlobal);
		model.addObject("frGstType", frGstType);
		model.addObject("spCakeOrder", spOrderHisSelected);
		model.addObject("spInvoiceHsn", settingValue);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	@RequestMapping(value = "pdf/showOrderHistoryPdf", method = RequestMethod.GET)
	public void showOrderHistoryPdf(HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {

		if(flag==1) {
		
		Document doc=new Document();
		
		String FILE_PATH=Constant.REPORT_SAVE;
		File file=new File(FILE_PATH);
		
		PdfWriter writer = null;
		
		
		 FileOutputStream out = null;
		try {
			out = new FileOutputStream(FILE_PATH);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		   try {
			    writer=PdfWriter.getInstance(doc,out);
		} catch (DocumentException e) {
			
			e.printStackTrace();
		}
		
		 PdfPTable table = new PdfPTable(8);
		 try {
		 System.out.println("Inside PDF Table try");
		 table.setWidthPercentage(100);
	     table.setWidths(new float[]{ 1.0f, 6.2f, 2.2f, 2.2f, 2.2f, 2.2f, 2.2f,2.2f});
	     Font headFont = new Font(FontFamily.TIMES_ROMAN,12, Font.NORMAL, BaseColor.BLACK);
	     Font headFont1 = new Font(FontFamily.TIMES_ROMAN,12, Font.BOLD, BaseColor.BLACK);
	     Font f=new Font(FontFamily.TIMES_ROMAN,12.0f,Font.UNDERLINE,BaseColor.BLUE);
	     
	     PdfPCell hcell;
	     hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
	     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     table.addCell(hcell);

	     hcell = new PdfPCell(new Phrase("Item Name", headFont1));
	     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     table.addCell(hcell);
	    
	     
	     hcell = new PdfPCell(new Phrase("Flavour", headFont1));
	     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     table.addCell(hcell);
	     
	     hcell = new PdfPCell(new Phrase("Delivery Date", headFont1));
	     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     table.addCell(hcell);
	    
	     hcell = new PdfPCell(new Phrase("Rate", headFont1));
	     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     table.addCell(hcell);
	 
	     hcell = new PdfPCell(new Phrase("Add On Rate", headFont1));
	     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     table.addCell(hcell);
	     
	     hcell = new PdfPCell(new Phrase("Total", headFont1));
	     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     table.addCell(hcell);
	     
	     hcell = new PdfPCell(new Phrase("Advance", headFont1));
	     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
	     table.addCell(hcell);
	     
	     int index=0;
	     
	     
	     for (SpOrderHis work : spOrderHistory) {
		       index++;
		         PdfPCell cell;
		         float price = work.getSpGrandTotal()-work.getSpTotalAddRate(); 
		         
		         cell = new PdfPCell(new Phrase(String.valueOf(index),headFont));
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         table.addCell(cell);

		        
		         cell = new PdfPCell(new Phrase( work.getSpName(),headFont));
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		         cell.setPaddingRight(5);
		         table.addCell(cell);
		         
		         
		         cell = new PdfPCell(new Phrase(String.valueOf(work.getSpfName()),headFont));
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		         cell.setPaddingRight(5);
		         table.addCell(cell);
		         
		         
		         cell = new PdfPCell(new Phrase(String.valueOf(work.getSpDeliveryDate()),headFont));
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
		         cell.setPaddingRight(5);
		         table.addCell(cell);
		         
		         
		         cell = new PdfPCell(new Phrase(String.valueOf(price),headFont));
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		         cell.setPaddingRight(5);
		         table.addCell(cell);
		         
		         cell = new PdfPCell(new Phrase(String.valueOf(work.getSpTotalAddRate()),headFont));
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		         cell.setPaddingRight(5);
		         table.addCell(cell);
		         
		         cell = new PdfPCell(new Phrase(String.valueOf( work.getSpGrandTotal()),headFont));
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		         cell.setPaddingRight(5);
		         table.addCell(cell);
		         
		         cell = new PdfPCell(new Phrase(String.valueOf(work.getSpAdvance()),headFont));
		         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		         cell.setPaddingRight(5);
		         table.addCell(cell);
		         
		         
	     }  
	     doc.open();
	     
	     Paragraph heading = new Paragraph("Special Cake Order History");
	     heading.setAlignment(Element.ALIGN_CENTER);
	     doc.add(heading);
	     DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new java.util.Date());
			
			doc.add(new Paragraph(""+ reportDate));
			doc.add(new Paragraph("\n"));
	     //document.add(new Paragraph(" "));
	     doc.add(table);
	     
	     doc.close();
	     
		   //Atul Sir code to open a Pdf File 
				if (file != null) {

					String mimeType = URLConnection.guessContentTypeFromName(file.getName());

					if (mimeType == null) {

						mimeType = "application/pdf";

					}

					response.setContentType(mimeType);

					response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

					// response.setHeader("Content-Disposition", String.format("attachment;
					// filename=\"%s\"", file.getName()));

					response.setContentLength((int) file.length());

					InputStream inputStream = null;
					try {
						inputStream = new BufferedInputStream(new FileInputStream(file));
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						FileCopyUtils.copy(inputStream, response.getOutputStream());
					} catch (IOException e) {
						System.out.println("Excep in Opening a Pdf File for Mixing");
						e.printStackTrace();
					}
				}
		     
		 } catch (DocumentException ex) {
		 
			 System.out.println("Pdf Generation Error: Prod From Orders"+ex.getMessage());
			 
			 ex.printStackTrace();
		   
		 }
		}
		else if(flag==2){
			System.out.println(itemOrderHistory);

			
			Document doc=new Document();
			
			String FILE_PATH=Constant.REPORT_SAVE;
			File file=new File(FILE_PATH);
			
			PdfWriter writer = null;
			
			
			 FileOutputStream out = null;
			try {
				out = new FileOutputStream(FILE_PATH);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			   try {
				    writer=PdfWriter.getInstance(doc,out);
			} catch (DocumentException e) {
				
				e.printStackTrace();
			}
			
			 PdfPTable table = new PdfPTable(6);
			 try {
			 System.out.println("Inside PDF Table try");
			 table.setWidthPercentage(100);
		     table.setWidths(new float[]{1.0f,6.2f, 2.2f, 2.2f, 2.2f,2.2f});
		     Font headFont = new Font(FontFamily.TIMES_ROMAN, 8, Font.NORMAL, BaseColor.BLACK);
		     Font headFont1 = new Font(FontFamily.TIMES_ROMAN, 8, Font.BOLD, BaseColor.BLACK);
		     Font f=new Font(FontFamily.TIMES_ROMAN,12.0f,Font.UNDERLINE,BaseColor.BLUE);
		     
		     PdfPCell hcell;
		     hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
		     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		     table.addCell(hcell);

		     hcell = new PdfPCell(new Phrase("Item Name", headFont1));
		     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		     table.addCell(hcell);
		    
		     
		     hcell = new PdfPCell(new Phrase("MRP", headFont1));
		     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		     table.addCell(hcell);
		     
		     hcell = new PdfPCell(new Phrase("Qty", headFont1));
		     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		     table.addCell(hcell);
		    
		     hcell = new PdfPCell(new Phrase("Rate", headFont1));
		     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		     table.addCell(hcell);
		 
		    
		     hcell = new PdfPCell(new Phrase("Total", headFont1));
		     hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
		     table.addCell(hcell);
		     
		   
		     
		     int index=0;
		     double qtyTotal=0; double allTotal=0;
		     double mrpTl=0;
		     double qtyTtl=0;
		     double rateTtl=0;
		     double finalTtl=0;

		     for (ItemOrderHis work : itemOrderHistory) {
			       index++;
			         PdfPCell cell;
			         
			         double total = work.getOrderQty()*work.getOrderRate();
			         //double mrp = mrpTl+work.getOrderMrp();
			         allTotal=allTotal+total;
			         qtyTotal=qtyTotal+work.getOrderQty();
			         cell = new PdfPCell(new Phrase(String.valueOf(index),headFont));
			         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			         table.addCell(cell);

			        
			         cell = new PdfPCell(new Phrase( work.getItemName(),headFont));
			         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			         cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			         cell.setPaddingRight(5);
			         table.addCell(cell);
			         
			         
			         cell = new PdfPCell(new Phrase(String.valueOf(work.getOrderMrp()),headFont));
			         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			         cell.setPaddingRight(5);
			         table.addCell(cell);
			         
			         
			         cell = new PdfPCell(new Phrase(String.valueOf(work.getOrderQty()),headFont));
			         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			         cell.setPaddingRight(5);
			         table.addCell(cell);
			         
			         cell = new PdfPCell(new Phrase(String.valueOf(work.getOrderRate()),headFont));
			         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			         cell.setPaddingRight(5);
			         table.addCell(cell);
			         
			         cell = new PdfPCell(new Phrase(roundUp(total)+"",headFont));
			         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			         cell.setPaddingRight(5);
			         table.addCell(cell);
			         
			        
			         
			         
		     }  
		     
		     PdfPCell cell;
	       
	         cell = new PdfPCell(new Phrase(String.valueOf(""),headFont));
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         table.addCell(cell);
	         
	         cell = new PdfPCell(new Phrase(String.valueOf("Total"),headFont));
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	         table.addCell(cell);
	         
	         cell = new PdfPCell(new Phrase(String.valueOf(""),headFont));
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         table.addCell(cell);
	         
	         cell = new PdfPCell(new Phrase(String.valueOf(""+qtyTotal),headFont));
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	         table.addCell(cell);
	         
	         cell = new PdfPCell(new Phrase(String.valueOf(""),headFont));
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	         table.addCell(cell);
	         
	         cell = new PdfPCell(new Phrase(roundUp(allTotal)+"",headFont));
	         cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	         cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
	         table.addCell(cell);
	         
		     doc.open();
		     
		     Paragraph heading = new Paragraph("Regular Cake Order History");
		     heading.setAlignment(Element.ALIGN_CENTER);
		     doc.add(heading);
		     DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
				String reportDate = DF.format(new java.util.Date());
				
				doc.add(new Paragraph(""+ reportDate));
				doc.add(new Paragraph("\n"));
		     //document.add(new Paragraph(" "));
		     doc.add(table);
		     
		     doc.close();
		     
			   //Atul Sir code to open a Pdf File 
					if (file != null) {

						String mimeType = URLConnection.guessContentTypeFromName(file.getName());

						if (mimeType == null) {

							mimeType = "application/pdf";

						}

						response.setContentType(mimeType);

						response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));

						// response.setHeader("Content-Disposition", String.format("attachment;
						// filename=\"%s\"", file.getName()));

						response.setContentLength((int) file.length());

						InputStream inputStream = null;
						try {
							inputStream = new BufferedInputStream(new FileInputStream(file));
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							FileCopyUtils.copy(inputStream, response.getOutputStream());
						} catch (IOException e) {
							System.out.println("Excep in Opening a Pdf File for Mixing");
							e.printStackTrace();
						}
					}
			     
			 } catch (DocumentException ex) {
			 
				 System.out.println("Pdf Generation Error: Prod From Orders"+ex.getMessage());
				 
				 ex.printStackTrace();
			   
			 }
			
		}
		
	}
	public static float roundUp(double d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}
}
