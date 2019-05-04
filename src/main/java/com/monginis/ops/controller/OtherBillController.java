package com.monginis.ops.controller;
  

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
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
 
import com.monginis.ops.billing.SellBillDataCommon;
import com.monginis.ops.common.Common;
import com.monginis.ops.common.DateConvertor;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.AddItemInOtherBill;
import com.monginis.ops.model.FrSupplier;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.Info;
import com.monginis.ops.model.Item;
import com.monginis.ops.model.MRule;
import com.monginis.ops.model.OtherBillDetail;
import com.monginis.ops.model.OtherBillHeader;
   

@Controller
@Scope("session")
public class OtherBillController {
	
	List<Item> itemsList = new ArrayList<>();
	List<AddItemInOtherBill> additemsList = new ArrayList<>();
	
	@RequestMapping(value = "/addSupplier", method = RequestMethod.GET)
	public ModelAndView addSupplier(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("frSellBilling/addSupplier");
		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		RestTemplate rest = new RestTemplate();
		try
		{
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			FrSupplier[] list = rest.postForObject(Constant.URL + "/getAllFrSupplierListByFrId",map,
					FrSupplier[].class);
			ArrayList<FrSupplier> supplierList = new ArrayList<>(Arrays.asList(list)); 
			model.addObject("supplierList",supplierList);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		 
		return model; 
	}
	
	@RequestMapping(value = "/editFrSupplier", method = RequestMethod.GET)
	@ResponseBody
	public FrSupplier editFrSupplier(HttpServletRequest request, HttpServletResponse response) {

		FrSupplier frSupplier = new FrSupplier();
		RestTemplate rest = new RestTemplate();
		try
		{
			int suppId = Integer.parseInt(request.getParameter("suppId"));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("suppId", suppId);
			frSupplier = rest.postForObject(Constant.URL + "/getFrSupplierById",map,
					FrSupplier.class); 
			 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		 
		return frSupplier; 
	}
	
	@RequestMapping(value = "/deleteSupplier/{suppId}", method = RequestMethod.GET)
	public String deleteSupplier(@PathVariable int suppId, HttpServletRequest request, HttpServletResponse response) {
		 
		try
		{
			 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("suppId", suppId);
			RestTemplate rest = new RestTemplate();
			Info info = rest.postForObject(Constant.URL + "/deleteFrSupplier", map,
					Info.class);
			System.out.println("info " + info);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
 
		 
		return "redirect:/addSupplier"; 
	}
	
	@RequestMapping(value = "/insertSupplier", method = RequestMethod.POST)
	public String insertSupplier(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		try
		{
			String suppId = request.getParameter("suppId");
			String suppName = request.getParameter("suppName");
			String suppAdd = request.getParameter("suppAdd");
			String city = request.getParameter("city");
			int isSameState = Integer.parseInt(request.getParameter("isSameState"));
			String mob = request.getParameter("mob");
			String email = request.getParameter("email");
			String gstnNo = request.getParameter("gstnNo");
			String panNo = request.getParameter("panNo");
			String liceNo = request.getParameter("liceNo");
			int creditDays = Integer.parseInt(request.getParameter("creditDays"));
			 
			FrSupplier frSupplier = new FrSupplier();
			if(suppId.equals("") || suppId==null)
				frSupplier.setSuppId(0);
    		else
    			frSupplier.setSuppId(Integer.parseInt(suppId));
			frSupplier.setSuppName(suppName);
			frSupplier.setSuppAddr(suppAdd);
			frSupplier.setSuppCity(city);
			frSupplier.setIsSameState(isSameState);
			frSupplier.setMobileNo(mob);
			frSupplier.setEmail(email);
			frSupplier.setGstnNo(gstnNo);
			frSupplier.setPanNo(panNo);
			frSupplier.setSuppFdaLic(liceNo);
			frSupplier.setSuppCreditDays(creditDays);
			frSupplier.setFrId(frDetails.getFrId());
			
			RestTemplate rest = new RestTemplate();
			FrSupplier insert = rest.postForObject(Constant.URL + "/postFrSupplier", frSupplier,
					FrSupplier.class);
			System.out.println("insert supp " + insert);
			
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
 
		 
		return "redirect:/addSupplier"; 
	}
	 
	@RequestMapping(value = "/showOtherBill", method = RequestMethod.GET)
	public ModelAndView showOtherBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("frSellBilling/otherBill"); 
		try
		{
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("itemGrp1", 7);
			RestTemplate rest = new RestTemplate();
			Item[] items  = rest.postForObject(Constant.URL + "/getItemsByCatId", map,
					Item[].class);
			
			
			additemsList = new ArrayList<>();
			itemsList = new ArrayList<>();
			itemsList =new ArrayList<>(Arrays.asList(items));
			 System.out.println(itemsList);
			 
			 HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails"); 
			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			 FrSupplier[] list = rest.postForObject(Constant.URL + "/getAllFrSupplierListByFrId",map,
						FrSupplier[].class);
				ArrayList<FrSupplier> supplierList = new ArrayList<>(Arrays.asList(list)); 
				
			 model.addObject("itemsList",itemsList);
			 model.addObject("supplierList",supplierList);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return model; 
	}
	
	@RequestMapping(value = "/findItemRateById", method = RequestMethod.GET)
	@ResponseBody
	public Item findItemRateById(HttpServletRequest request, HttpServletResponse response) {

		Item item = new Item(); 
		try
		{
			int id = Integer.parseInt(request.getParameter("id")); 
			for(int i=0;i<itemsList.size();i++)
			{
				if(id==itemsList.get(i).getId())
					item = itemsList.get(i); 
			}
			 System.out.println("item " + item);
			 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		 
		return item; 
	}
	
	@RequestMapping(value = "/addItemInList", method = RequestMethod.GET)
	@ResponseBody
	public List<AddItemInOtherBill> addItemInList(HttpServletRequest request, HttpServletResponse response) {
		DecimalFormat df = new DecimalFormat("#.00000");
		AddItemInOtherBill item = new AddItemInOtherBill(); 
		try
		{
			int id = Integer.parseInt(request.getParameter("id")); 
			int qty1 = Integer.parseInt(request.getParameter("qty1"));
			float discPer = Float.parseFloat(request.getParameter("discPer")); 
			for(int i=0;i<itemsList.size();i++)
			{
				if(id==itemsList.get(i).getId())
				{
					item.setQty(qty1);
					item.setId(itemsList.get(i).getId());
					item.setItemId(itemsList.get(i).getItemId());
					item.setItemName(itemsList.get(i).getItemName());
					item.setItemRate1(itemsList.get(i).getItemRate1()); 
					item.setItemGrp1(itemsList.get(i).getItemGrp1());
					item.setItemMrp1(itemsList.get(i).getItemMrp1());
					item.setItemTax1(itemsList.get(i).getItemTax1());
					item.setItemTax2(itemsList.get(i).getItemTax2());
					item.setItemTax3(itemsList.get(i).getItemTax3());
					item.setBaseRate(Double.valueOf(df.format((item.getItemRate1()*100)/(100+item.getItemTax3()))));
					System.out.println("baseRate " + item.getBaseRate());
					item.setDiscPer(discPer);
					Double value = Double.valueOf(df.format(item.getBaseRate()*qty1));
					item.setDiscAmt(Float.valueOf(df.format(((discPer/100)*value))));
					Double taxableAmt = value-item.getDiscAmt();
					item.setTaxableAmt(Double.valueOf(df.format(taxableAmt)));
					item.setItemTax3rs(Double.valueOf(df.format((item.getItemTax3()/100)*item.getTaxableAmt())));
					item.setGrandTotal(Double.valueOf(df.format(item.getTaxableAmt()+item.getItemTax3rs())));
					item.setShelfLife(itemsList.get(i).getShelfLife());
					break;
				}
					  
			}
			additemsList.add(item);
			 System.out.println("additemsList " + additemsList);
			 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		 
		return additemsList; 
	}
	
	@RequestMapping(value = "/updateQtyOtherBill", method = RequestMethod.GET)
	@ResponseBody
	public List<AddItemInOtherBill> updateQtyOtherBill(HttpServletRequest request, HttpServletResponse response) {

		DecimalFormat df = new DecimalFormat("#.00000");
		try
		{
			int index = Integer.parseInt(request.getParameter("index"));
			int updateQty = Integer.parseInt(request.getParameter("updateQty"));
			float discPer = Float.parseFloat(request.getParameter("discPer"));
			additemsList.get(index).setQty(updateQty);
			additemsList.get(index).setDiscPer(discPer);
			Double value = Double.valueOf(df.format(additemsList.get(index).getBaseRate()*updateQty));
			Double discAmt = (additemsList.get(index).getDiscPer()/100)*value;
			additemsList.get(index).setDiscAmt(Float.valueOf(df.format((discAmt)))); 
			Double taxableAmt = value-additemsList.get(index).getDiscAmt();
			additemsList.get(index).setTaxableAmt(Double.valueOf(df.format(taxableAmt)));
			additemsList.get(index).setItemTax3rs(Double.valueOf(df.format((additemsList.get(index).getItemTax3()/100)*additemsList.get(index).getTaxableAmt())));
			additemsList.get(index).setGrandTotal(Double.valueOf(df.format(additemsList.get(index).getTaxableAmt()+additemsList.get(index).getItemTax3rs())));
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		 
		return additemsList; 
	}
	
	@RequestMapping(value = "/deleteItemInOtherBill", method = RequestMethod.GET)
	@ResponseBody
	public List<AddItemInOtherBill> deleteItemInOtherBill(HttpServletRequest request, HttpServletResponse response) {

		 
		try
		{
			int index = Integer.parseInt(request.getParameter("index")); 
			additemsList.remove(index);
			 
			 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		 
		return additemsList; 
	}
	
	@RequestMapping(value = "/submitOtherBill", method = RequestMethod.POST)
	public String submitOtherBill(HttpServletRequest request, HttpServletResponse response) {
		DecimalFormat df = new DecimalFormat("#.00000");
		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		try
		{
			float sgst = 0;
			float cgst = 0;
			float igst = 0;
			  DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
              Calendar cal = Calendar.getInstance();
              String curDateTime = dateFormat.format(cal.getTime());
			
			int suppId = Integer.parseInt(request.getParameter("suppId")); 
			
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("suppId", suppId);
			RestTemplate rest = new RestTemplate(); 
			 FrSupplier frSupplier = rest.postForObject(Constant.URL + "/getFrSupplierById",map,
						FrSupplier.class);
			 
			 
			String invoiceNo = request.getParameter("invoiceNo"); 
			String billDate = request.getParameter("billDate"); 
			float taxableAmount = Float.parseFloat(request.getParameter("totalSumText"));
			float totalTax = Float.parseFloat(request.getParameter("taxtotalText"));
			float grandTotal = Float.parseFloat(request.getParameter("grandTotalText")); 
			float discTotalText = Float.parseFloat(request.getParameter("discTotalText")); 
			 
			OtherBillHeader otherBillHeader = new OtherBillHeader();
			otherBillHeader.setBillDate(billDate);
			otherBillHeader.setInvoiceNo(invoiceNo);
			otherBillHeader.setFrId(frDetails.getFrId());
			otherBillHeader.setFrCode(frDetails.getFrCode()); 
			otherBillHeader.setSuppId(suppId);
			otherBillHeader.setTaxableAmt(taxableAmount);
			otherBillHeader.setTotalTax(totalTax);
			otherBillHeader.setGrandTotal(grandTotal);
			
			List<OtherBillDetail> otherBillDetailList = new ArrayList<OtherBillDetail>();
			for(int i=0;i<additemsList.size();i++)
			{
				OtherBillDetail otherBillDetail = new OtherBillDetail();
				otherBillDetail.setItemId(additemsList.get(i).getId());//CHANGED BY SACHIN 27 FEB
				otherBillDetail.setMenuId(additemsList.get(i).getItemGrp1());
				otherBillDetail.setCatId(additemsList.get(i).getItemGrp1());
				otherBillDetail.setBillQty(additemsList.get(i).getQty());
				otherBillDetail.setMrp((float)(additemsList.get(i).getItemMrp1()/1));
				otherBillDetail.setRate((float)(additemsList.get(i).getItemRate1()/1));
				otherBillDetail.setBaseRate((float)(additemsList.get(i).getBaseRate()/1));
				otherBillDetail.setDiscPer(additemsList.get(i).getDiscPer());
				otherBillDetail.setDiscRs(additemsList.get(i).getDiscAmt());
				otherBillDetail.setTaxableAmt((float)(additemsList.get(i).getTaxableAmt()/1));
				if(frSupplier.getIsSameState()==1)
				{
					otherBillDetail.setSgstPer((float)(additemsList.get(i).getItemTax3()/2));
					otherBillDetail.setSgstRs(Float.valueOf(df.format((float)(additemsList.get(i).getItemTax3rs()/2))));
					sgst=sgst+otherBillDetail.getSgstRs();
					otherBillDetail.setCgstPer((float)(additemsList.get(i).getItemTax3()/2)); 
					otherBillDetail.setCgstRs(Float.valueOf(df.format((float)(additemsList.get(i).getItemTax3rs()/2))));
					cgst=cgst+otherBillDetail.getCgstRs();
				}
				else
				{
					otherBillDetail.setIgstPer((float)(additemsList.get(i).getItemTax3()/1));
					otherBillDetail.setIgstRs(Float.valueOf(df.format((float)(additemsList.get(i).getItemTax3rs()/1))));
					igst=igst+otherBillDetail.getIgstRs();
				}
				otherBillDetail.setTotalTax(Float.valueOf(df.format((float)(additemsList.get(i).getItemTax3rs()/1))));
				otherBillDetail.setGrandTotal((float)(additemsList.get(i).getGrandTotal()/1)); 
				String calculatedDate = incrementDate(billDate, additemsList.get(i).getShelfLife());
				otherBillDetail.setExpiryDate(calculatedDate);
				otherBillDetailList.add(otherBillDetail);
			}
			otherBillHeader.setCgstSum(Float.valueOf(df.format(cgst)));
			otherBillHeader.setSgstSum(Float.valueOf(df.format(sgst)));
			otherBillHeader.setIgstSum(Float.valueOf(df.format(igst)));
			otherBillHeader.setTime(curDateTime);
			otherBillHeader.setDiscAmt(discTotalText);
			otherBillHeader.setOtherBillDetailList(otherBillDetailList);
			System.out.println("final header with detailed " + otherBillHeader.toString());
			
			OtherBillHeader insert = rest.postForObject(Constant.URL + "/postOtherBillHeaderAndDetail",otherBillHeader,
					OtherBillHeader.class);
			
			System.out.println("insert " + insert);
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
 
		 
		return "redirect:/showOtherBill"; 
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
	
	@RequestMapping(value = "/viewOtherBill", method = RequestMethod.GET)
	public ModelAndView viewOtherBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("frSellBilling/viewOtherBills"); 
		try
		{
			HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails"); 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			RestTemplate rest = new RestTemplate(); 
			FrSupplier[] list = rest.postForObject(Constant.URL + "/getAllFrSupplierListByFrId",map,
					FrSupplier[].class);
			ArrayList<FrSupplier> supplierList = new ArrayList<>(Arrays.asList(list)); 
			model.addObject("supplierList",supplierList);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return model; 
	}
	
	@RequestMapping(value = "/getOtherBillBetweenDate", method = RequestMethod.GET)
	@ResponseBody
	public List<OtherBillHeader> getOtherBillBetweenDate(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		 List<OtherBillHeader> otherBillHeaderlist = new ArrayList<>();
		try
		{
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			int suppId = Integer.parseInt(request.getParameter("suppId"));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("suppId", suppId);
			System.out.println("map" + map);
			RestTemplate rest = new RestTemplate(); 
			OtherBillHeader[] list = rest.postForObject(Constant.URL + "/getOtherBillHeaderBetweenDate",map,
					OtherBillHeader[].class);
			otherBillHeaderlist = new ArrayList<>(Arrays.asList(list));
			System.out.println("otherBillHeaderlist " + otherBillHeaderlist);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		 
		return otherBillHeaderlist; 
	}
	
	@RequestMapping(value = "/frSupplierList", method = RequestMethod.GET)
	@ResponseBody
	public List<FrSupplier> frSupplierList(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		ArrayList<FrSupplier> supplierList = new ArrayList<>();
		try
		{
			 
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			RestTemplate rest = new RestTemplate(); 
			FrSupplier[] list = rest.postForObject(Constant.URL + "/getAllFrSupplierListByFrId",map,
					FrSupplier[].class);
			supplierList = new ArrayList<>(Arrays.asList(list)); 
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		 
		return supplierList; 
	}
	
	@RequestMapping(value = "/viewOtherBillDetail/{billNo}", method = RequestMethod.GET)
	public ModelAndView viewOtherBillDetail(@PathVariable int billNo, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("frSellBilling/viewOtherBillDetail"); 
		try
		{
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("billNo", billNo);
			RestTemplate rest = new RestTemplate(); 
			OtherBillHeader otherBillHeader = rest.postForObject(Constant.URL + "/getOtherBillHeaderAndDetailByBillNo",map,
					OtherBillHeader.class);
			map = new LinkedMultiValueMap<String, Object>();
			map.add("suppId", otherBillHeader.getSuppId());
			FrSupplier supplier = rest.postForObject(Constant.URL + "/getFrSupplierById",map,
					FrSupplier.class);
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("itemGrp1", 7); 
			Item[] items  = rest.postForObject(Constant.URL + "/getItemsByCatId", map,
					Item[].class);  
			ArrayList<Item> itemsList =new ArrayList<>(Arrays.asList(items));
			 
			
			model.addObject("otherBillHeader", otherBillHeader);
			model.addObject("supplier", supplier);
			model.addObject("itemsList", itemsList);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return model; 
	}
	
	String document1;
	String document2;
	String document5;
	
	@RequestMapping(value = "/showRuleFilePdf", method = RequestMethod.GET)
	public ModelAndView showRuleFilePdf(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("frSellBilling/showRuleFilePdf"); 
		try
		{
			RestTemplate rest = new RestTemplate(); 
			MRule[] list = rest.getForObject(Constant.URL + "getRuleFile",
					MRule[].class);
			ArrayList<MRule> fileList = new ArrayList<>(Arrays.asList(list));
			System.out.println("fileList"+fileList);
			document1=fileList.get(0).getFileName();
			document2=fileList.get(1).getFileName();
			document5=fileList.get(4).getFileName(); 
			model.addObject("url", Constant.LOGIS_BILL_URL);
			model.addObject("document1", document1);
			model.addObject("document2", document2);
			model.addObject("document5", document5);
			model.addObject("date1", fileList.get(0).getDate());
			model.addObject("date2", fileList.get(1).getDate());
			model.addObject("date5", fileList.get(4).getDate());
			   
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return model; 
	}
	
	@RequestMapping(value = "/viewRuleDocumentFile/{flag}", method = RequestMethod.GET)
	public void viewDocumentFile(@PathVariable int flag,HttpServletRequest request, HttpServletResponse response) {

		File file = null;
		 if(flag==1)
		 {
			 file = new File(Constant.LOGIS_BILL_URL+document1);
		 }
		 else if(flag==2)
		 {
			file = new File(Constant.LOGIS_BILL_URL+document2);
		 }
		  
			System.out.println("file"+file);
			if(file != null) {

                String mimeType = URLConnection.guessContentTypeFromName(file.getName());

                if (mimeType == null) {

                    mimeType = "application/pdf";

                }

                response.setContentType(mimeType);

                response.addHeader("content-disposition", String.format("inline; filename=\"%s\"", file.getName()));
 

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
                    System.out.println("Excep in Opening a Pdf File");
                    e.printStackTrace();
                }
            }
 
	}

}
