package com.monginis.ops.controller;

import java.math.BigDecimal;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

import com.itextpdf.text.log.SysoCounter;
import com.monginis.ops.billing.SellBillDetail;
import com.monginis.ops.billing.SellBillHeader;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.CategoryList;
import com.monginis.ops.model.CustomerBillData;
import com.monginis.ops.model.CustomerBillItem;
import com.monginis.ops.model.FrItemStockConfiResponse;
import com.monginis.ops.model.FrItemStockConfigure;
import com.monginis.ops.model.FrItemStockConfigureList;
import com.monginis.ops.model.FrMenu;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetCurrentStockDetails;
import com.monginis.ops.model.GetCustBillTax;
import com.monginis.ops.model.GetCustmoreBillResponse;
import com.monginis.ops.model.GetFrItem;

import com.monginis.ops.model.GetSellBillDetail;
import com.monginis.ops.model.GetSellBillHeader;
import com.monginis.ops.model.Info;
import com.monginis.ops.model.Item;
import com.monginis.ops.model.ItemResponse;
import com.monginis.ops.model.MCategory;
import com.monginis.ops.model.Main;
import com.monginis.ops.model.PostFrItemStockHeader;
import com.monginis.ops.model.SellBillDataCommon;
import com.monginis.ops.model.SellBillDetailEdit;
import com.monginis.ops.model.SellBillDetailList;
import com.monginis.ops.model.SellBillEditBean;
import com.monginis.ops.model.SpOrderHis;
import com.monginis.ops.model.frsetting.FrSetting;

@Controller
@Scope("session")
public class CustomerBillController {

	public CustomerBillItem currentNewItem;
	public int prevItemId;
	public int frGstType = 0;
	public List<Item> newItemsList;
	String fromDate="";
	String toDate="";
	public List<GetCurrentStockDetails> currentStockDetailList = new ArrayList<>();//

	// static ItemList

	public List<CustomerBillItem> customerBillItemList = new ArrayList<CustomerBillItem>();

	public CustomerBillData customerBillDataToken1 = new CustomerBillData();

	public CustomerBillData commonCustomerBillData = new CustomerBillData();

	public CustomerBillData customerBillDataToken2 = new CustomerBillData();

	public CustomerBillData customerBillDataToken3 = new CustomerBillData();

	public CustomerBillData customerBillDataToken4 = new CustomerBillData();

	public CustomerBillData customerBillDataToken5 = new CustomerBillData();

	public CustomerBillData customerBillDataToken6 = new CustomerBillData();

	public CustomerBillData customerBillDataToken7 = new CustomerBillData();

	List<GetSellBillHeader> getSellBillHeaderList;
	List<GetSellBillDetail> getSellBillDetailList;

	int menuId;
	private String itemShowGlobal;
	Franchisee frDetails;

	@RequestMapping(value = "/viewBill", method = RequestMethod.GET)
	public ModelAndView viewBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("frSellBilling/showSellBill");
		HttpSession ses = request.getSession();
		String pattern = "dd-MM-yyyy";

		String dateInString = new SimpleDateFormat(pattern).format(new Date());
        if(fromDate!=""&&toDate!="") {
        	ses.setAttribute("fromSellBillDate", fromDate);
    		ses.setAttribute("toSellBillDate", toDate);
        }else {
		ses.setAttribute("fromSellBillDate", dateInString);
		ses.setAttribute("toSellBillDate", dateInString);
        }
		return model;
	}
	
	@RequestMapping(value = "/getSellBillHeader", method = RequestMethod.GET)
	public @ResponseBody List<GetSellBillHeader> getSellBillHeader(HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("in method");
		 fromDate = request.getParameter("fromDate");
		 toDate = request.getParameter("toDate");

		HttpSession ses = request.getSession();
		frDetails = (Franchisee) ses.getAttribute("frDetails");
		ses.setAttribute("fromSellBillDate", fromDate);
		ses.setAttribute("toSellBillDate", toDate);
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int frId = frDetails.getFrId();
		map.add("frId", frId);
		map.add("fromDate", fromDate);
		map.add("toDate", toDate);
		// getFrGrnDetail
		try {

			ParameterizedTypeReference<List<GetSellBillHeader>> typeRef = new ParameterizedTypeReference<List<GetSellBillHeader>>() {
			};
			ResponseEntity<List<GetSellBillHeader>> responseEntity = restTemplate
					.exchange(Constant.URL + "getSellBillHeader", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getSellBillHeaderList = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("Sell Bill Header " + getSellBillHeaderList.toString());

		return getSellBillHeaderList;

	}
String printInvoiceNo;
String selBillDate;
	@RequestMapping(value = "/viewBillDetails", method = RequestMethod.GET)
	public ModelAndView viewBillDetails(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("frSellBilling/showSellBillDetails");

		System.out.println("in method");

		String sellBill_no = request.getParameter("sellBillNo");

		String billDate = request.getParameter("billDate");
		
		selBillDate=billDate;

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		int sellBillNo = Integer.parseInt(sellBill_no);
		map.add("sellBillNo", sellBillNo);
		
for(int i=0;i<getSellBillHeaderList.size();i++) {
	
	if(Integer.parseInt(sellBill_no)==getSellBillHeaderList.get(i).getSellBillNo()) {
		
		printInvoiceNo=getSellBillHeaderList.get(i).getInvoiceNo();
	}
}
		try {

			ParameterizedTypeReference<List<GetSellBillDetail>> typeRef = new ParameterizedTypeReference<List<GetSellBillDetail>>() {
			};
			ResponseEntity<List<GetSellBillDetail>> responseEntity = restTemplate
					.exchange(Constant.URL + "getSellBillDetail", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getSellBillDetailList = responseEntity.getBody();
			
			
			map.add("billNo", sellBill_no);

			SellBillDetailList sellBillDetailList = restTemplate.postForObject(Constant.URL + "/getSellBillDetails",
					map, SellBillDetailList.class);

			List<SellBillDetail> sellBillDetails = sellBillDetailList.getSellBillDetailList();
			selectedSellBillDetailList = sellBillDetails;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			model.addObject("getSellBillDetailList", getSellBillDetailList);
			model.addObject("sellBillNo", sellBillNo);
			model.addObject("billDate", billDate);

		}

		System.out.println("Sell Bill Detail " + getSellBillDetailList.toString());

		model.addObject("getSellBillDetailList", getSellBillDetailList);
		model.addObject("sellBillNo", sellBillNo);
		model.addObject("billDate", billDate);

		return model;
	}
	SellBillEditBean sellBillEditBean=null;
	@RequestMapping(value = "/editBillDetails", method = RequestMethod.GET)
	public ModelAndView editBillDetails(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("frSellBilling/editSellBill");

		int sellBillNo = Integer.parseInt(request.getParameter("sellBillNo"));

		String selBillDate = request.getParameter("billDate");
	    RestTemplate restTemplate = new RestTemplate();

		try {
	     	MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		    map.add("billNo", sellBillNo);
			 sellBillEditBean= restTemplate.postForObject(Constant.URL + "/getSellBillHeaderAndDetails",
					map, SellBillEditBean.class);

			List<SellBillDetailEdit> sellBillDetails = sellBillEditBean.getSellBillDetailList();
		    System.err.println("sellBillDetails"+sellBillDetails.toString());
			model.addObject("sellBillDetails", sellBillDetails);
			model.addObject("sellBillHeader", sellBillEditBean.getSellBillHeader());
		} catch (Exception e) {
			e.printStackTrace();
			}
		model.addObject("sellBillNo", sellBillNo);
		model.addObject("billDate", selBillDate);

		return model;
	}
	
	@RequestMapping(value = "/editSellBill", method = RequestMethod.POST)
	public String editSellBill(HttpServletRequest request, HttpServletResponse response) {
		int sellBillNo=0;
		String billDate="";
		try {
			 sellBillNo=Integer.parseInt(request.getParameter("sellBillNo"));
			 billDate=request.getParameter("billDate");
			float paidAmt=Float.parseFloat(request.getParameter("paidAmt"));
			if(sellBillEditBean!=null)
			{
				
				float sumTaxableAmt = 0, sumTotalTax = 0, sumGrandTotal = 0, sumMrp = 0;
				List<SellBillDetail> sellBillDetailList=new ArrayList<SellBillDetail>();
				
				for(int i=0;i<sellBillEditBean.getSellBillDetailList().size();i++)
				{
					int qty=Integer.parseInt(request.getParameter("qty"+sellBillEditBean.getSellBillDetailList().get(i).getSellBillDetailNo()));
					SellBillDetail sellBillDetail = new SellBillDetail();
					//System.err.println(sellBillEditBean.getSellBillDetailList().get(i).getSellBillDetailNo()+"sellBillEditBean.getSellBillDetailList().get(i).getSellBillDetailNo()");
					sellBillDetail.setSellBillDetailNo(sellBillEditBean.getSellBillDetailList().get(i).getSellBillDetailNo());
					sellBillDetail.setSellBillNo(sellBillEditBean.getSellBillDetailList().get(i).getSellBillNo());
					Float rate = (float) sellBillEditBean.getSellBillDetailList().get(i).getMrp();

					Float tax1 = (float) sellBillEditBean.getSellBillDetailList().get(i).getSgstPer();
					Float tax2 = (float) sellBillEditBean.getSellBillDetailList().get(i).getCgstPer();
					Float tax3 = (float) sellBillEditBean.getSellBillDetailList().get(i).getIgstPer();

					Float mrpBaseRate = (rate * 100) / (100 + (tax1 + tax2));
					mrpBaseRate = roundUp(mrpBaseRate);

					System.out.println("Mrp: " + rate);
					System.out.println("Tax1 : " + tax1);
					System.out.println("tax2 : " + tax2);

					Float taxableAmt = (float) (mrpBaseRate * qty);
					taxableAmt = roundUp(taxableAmt);
					// -----------------------------------------

					float discAmt = ((taxableAmt * sellBillEditBean.getSellBillHeader().getDiscountPer()) / 100);
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

					sellBillDetail.setCatId(sellBillEditBean.getSellBillDetailList().get(i).getCatId());
					sellBillDetail.setSgstPer(tax1);
					sellBillDetail.setSgstRs(sgstRs);
					sellBillDetail.setCgstPer(tax2);
					sellBillDetail.setCgstRs(cgstRs);
					sellBillDetail.setDelStatus(0);
					sellBillDetail.setGrandTotal(grandTotal);
					sellBillDetail.setIgstPer(tax3);
					sellBillDetail.setIgstRs(igstRs);
					sellBillDetail.setItemId(sellBillEditBean.getSellBillDetailList().get(i).getItemId());
					sellBillDetail.setMrp(rate);
					sellBillDetail.setMrpBaseRate(mrpBaseRate);
					sellBillDetail.setQty(qty);
					sellBillDetail.setRemark("");
					sellBillDetail.setBillStockType(sellBillEditBean.getSellBillDetailList().get(i).getBillStockType());

					sumMrp = sumMrp + (rate * qty);
					sumTaxableAmt = sumTaxableAmt + taxableAmt;
					sumTotalTax = sumTotalTax + totalTax;
					sumGrandTotal = sumGrandTotal + grandTotal;

					sellBillDetail.setTaxableAmt(taxableAmt);
					sellBillDetail.setTotalTax(totalTax);

					sellBillDetailList.add(sellBillDetail);
				}
				
				String convertedDate=null;
				try {
					SimpleDateFormat ymdSDF = new SimpleDateFormat("yyyy-MM-dd");
					SimpleDateFormat dmySDF = new SimpleDateFormat("dd-MM-yyyy");
					Date dmyDate = dmySDF.parse(sellBillEditBean.getSellBillHeader().getBillDate());
					
					convertedDate=ymdSDF.format(dmyDate);
					
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sellBillEditBean.getSellBillHeader().setBillDate(convertedDate);
				sellBillEditBean.getSellBillHeader().setPaidAmt(Math.round(paidAmt));
				sellBillEditBean.getSellBillHeader().setTaxableAmt(sumTaxableAmt);
				// float discountAmt = 0;
				// if(discountPer!=0.0)
				// discountAmt = ((sumGrandTotal * discountPer) / 100);
				float payableAmt = sumGrandTotal;

				payableAmt = roundUp(payableAmt);

				sellBillEditBean.getSellBillHeader().setDiscountAmt(sumMrp);
				sellBillEditBean.getSellBillHeader().setPayableAmt(Math.round(payableAmt));
				sellBillEditBean.getSellBillHeader().setTotalTax(sumTotalTax);
				sellBillEditBean.getSellBillHeader().setGrandTotal(Math.round(sumGrandTotal));

				float calRemainingTotal = (payableAmt - paidAmt);//commented paid amt

				if (calRemainingTotal < 0) {
					sellBillEditBean.getSellBillHeader().setRemainingAmt(0);

				} else {

					sellBillEditBean.getSellBillHeader().setRemainingAmt(calRemainingTotal);
				}
				if (calRemainingTotal <= 0) {

					sellBillEditBean.getSellBillHeader().setStatus(1);
				} else if (calRemainingTotal == payableAmt) {
					sellBillEditBean.getSellBillHeader().setStatus(2);

				} else if (payableAmt > calRemainingTotal) {
					sellBillEditBean.getSellBillHeader().setStatus(3);
				}

				System.out.println("SellBillHeaderEditRes :" + sellBillEditBean.toString());
				SellBillHeader sellBillHeader=new SellBillHeader();
				sellBillHeader=sellBillEditBean.getSellBillHeader();
				
				RestTemplate restTemplate = new RestTemplate();
				SellBillHeader	sellBillHeaderEditRes = restTemplate.postForObject(Constant.URL + "insertSellBillData", sellBillHeader,
						SellBillHeader.class);
				
				List<SellBillDetail>	sellBillDetailEditRes = restTemplate.postForObject(Constant.URL + "insertSellBillDetails", sellBillDetailList,
						List.class);

				System.out.println("SellBillHeaderEditRes :" + sellBillHeaderEditRes.toString());

				
			}
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/editBillDetails?sellBillNo="+sellBillNo+"&billDate="+billDate;
	}

	//print function
	List<SellBillDetail> BillDetailList = new ArrayList<SellBillDetail>();
	List<GetCustBillTax> getCustBillTaxList;
	
	List<SellBillDetail> selectedSellBillDetailList;

	@RequestMapping(value = "/billDetailPrint", method = RequestMethod.GET)
	public void getSelectedIdForPrint(HttpServletRequest request, HttpServletResponse response) {

		System.out.println("IN Metjod");
        try {
		BillDetailList = new ArrayList<SellBillDetail>();
		String selectedId = request.getParameter("id");
		
		System.err.println("Selected Id s" +selectedId);
		selectedId = selectedId.substring(1, selectedId.length() - 1);
		selectedId = selectedId.replaceAll("\"", "");

		System.out.println("selectedId  " + selectedId);

		List<String> selectedIdList = new ArrayList<>();
		System.out.println("sellBillDetailList  " + selectedSellBillDetailList.toString());
		selectedIdList = Arrays.asList(selectedId.split(","));
		for (int i = 0; i < selectedSellBillDetailList.size(); i++) {
			for (int j = 0; j < selectedIdList.size(); j++) {
				if (Integer.parseInt(selectedIdList.get(j)) == selectedSellBillDetailList.get(i)
						.getSellBillDetailNo()) {
					System.out.println(i);
					BillDetailList.add(selectedSellBillDetailList.get(i));
				}
			}
		}
		getCustBillTaxList=new ArrayList<GetCustBillTax>();
	
		for (int i = 0; i < BillDetailList.size(); i++) {
			boolean innerLoop=false;
			
			for (int j= 0; j < getCustBillTaxList.size(); j++) {
				
				if((BillDetailList.get(i).getCgstPer()==getCustBillTaxList.get(j).getCgstPer())&&(BillDetailList.get(i).getSgstPer()==getCustBillTaxList.get(j).getSgstPer()))
				{
					innerLoop=true;
					
					getCustBillTaxList.get(j).setCgstRs(getCustBillTaxList.get(j).getCgstRs()+BillDetailList.get(i).getCgstRs());
					getCustBillTaxList.get(j).setSgstRs(getCustBillTaxList.get(j).getSgstRs()+BillDetailList.get(i).getSgstRs());
					getCustBillTaxList.get(j).setTaxableAmt(getCustBillTaxList.get(j).getTaxableAmt()+BillDetailList.get(i).getTaxableAmt());
				}
			}
			if(innerLoop==false)
			{
				GetCustBillTax getCustBillTax=new GetCustBillTax();
				getCustBillTax.setCgstPer(BillDetailList.get(i).getCgstPer());
				getCustBillTax.setSgstPer(BillDetailList.get(i).getSgstPer());
				getCustBillTax.setCgstRs(BillDetailList.get(i).getCgstRs());
				getCustBillTax.setSgstRs(BillDetailList.get(i).getSgstRs());
				getCustBillTax.setSellBillDetailNo(BillDetailList.get(i).getSellBillDetailNo());
				getCustBillTax.setTaxableAmt(BillDetailList.get(i).getTaxableAmt());
				getCustBillTaxList.add(getCustBillTax);
			}
		}
		System.out.println("getCustBillTaxList"+getCustBillTaxList.toString());
        }
        catch (Exception e) {
			e.printStackTrace();
			
		}
	}

	@RequestMapping(value = "/printSelectedBillDetail", method = RequestMethod.GET)
	public ModelAndView printSelectedOrder(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = new ModelAndView("expressBill/frSelectedExBillPrint");
		System.out.println("IN Print Selected Order");
		try {
		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		
		System.out.println("Selected List " + BillDetailList.toString());
		model.addObject("exBill", BillDetailList);
		model.addObject("custBilltax", getCustBillTaxList);
		model.addObject("invNo",printInvoiceNo);
       		model.addObject("frGstType", frDetails.getFrGstType());


		//model.addObject("date", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
       		model.addObject("date",selBillDate);
       		
		System.out.println("After print ");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	

	PostFrItemStockHeader frItemStockHeader;
	int runningMonth;
	
	

	
	@RequestMapping(value = "/showCustomerBill", method = RequestMethod.GET)
	public ModelAndView displayCustomerBill1(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("frSellBilling/customerBill");
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		resetData1();
		resetData2();
		resetData3();
		resetData4();
		resetData5();
		resetData6();
		resetData7();
		currentStockDetailList = new ArrayList<GetCurrentStockDetails>();
		// System.out.println(" new currentStockDetail List " +
		// currentStockDetailList.toString());

		HttpSession session = request.getSession();
		//---------------------------------Start---Month End--------------------------------------
	     Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
	
	     RestTemplate restTemplate = new RestTemplate();
			
	  
		
	     try {
				
			 map = new LinkedMultiValueMap<String, Object>();
				map.add("frId", frDetails.getFrId());
				
				com.monginis.ops.model.Info info= restTemplate.postForObject(Constant.URL + "checkIsMonthClosed", map,
						com.monginis.ops.model.Info.class);
				
				
				System.out.println(" 	"+info.toString() );

			if (info.isError()) {

				
				System.out.println("need to complete Month End ......" );

				model = new ModelAndView("stock/stockdetails");
				model.addObject("message","Please do month end process first");
				
				List<MCategory> mAllCategoryList = new ArrayList<MCategory>();

				CategoryList categoryList = new CategoryList();
				
				try {
					 map = new LinkedMultiValueMap<String, Object>();
					map.add("frId", frDetails.getFrId());
					
					List<PostFrItemStockHeader> list = restTemplate.postForObject(Constant.URL + "getCurrentMonthOfCatId", map,
							List.class);
					
					System.out.println("list " + list);

					frItemStockHeader = restTemplate.postForObject(Constant.URL + "getRunningMonth", map,
							PostFrItemStockHeader.class);
					
					System.out.println("Fr Opening Stock "+frItemStockHeader.toString());
					runningMonth = frItemStockHeader.getMonth();
					
					int monthNumber = runningMonth;
					String mon=Month.of(monthNumber).name();
					
					System.err.println("Month name "+mon);
					model.addObject("getMonthList", list);
					

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
					System.out.println("Day Of Month End ......" );

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
			
			} catch (Exception e) {
				System.out.println("Exception in runningMonth" + e.getMessage());
				e.printStackTrace();

			}		
			//---------------------------------End---Month End--------------------------------------

		try {

			ArrayList<FrMenu> menuList = (ArrayList<FrMenu>) session.getAttribute("allMenuList");
			frDetails = (Franchisee) session.getAttribute("frDetails");

			System.out.println("menuList" + menuList.toString());
			ArrayList<Integer> itemList = new ArrayList<Integer>();

			String items;
			StringBuilder builder = new StringBuilder();
			for (FrMenu frMenu : menuList) {

				if (frMenu.getMenuId() == 26 || frMenu.getMenuId() == 31 || frMenu.getMenuId() == 33
						|| frMenu.getMenuId() == 34 || frMenu.getMenuId()==63|| frMenu.getMenuId()==66|| frMenu.getMenuId()==68|| frMenu.getMenuId()==81|| frMenu.getMenuId()==82||frMenu.getMenuId()==86) {

					String str = frMenu.getItemShow();
					System.out.println("getItemShow" + frMenu.getItemShow());

					builder.append("," + frMenu.getItemShow());

				}

			}
			items = builder.toString();
			items = items.substring(1, items.length());

			System.out.println("Item Show List is " + items);
			itemShowGlobal = items;

			// getAllSubCategories

			CategoryList catergoryList = restTemplate.getForObject(Constant.URL + "/showAllCategory",
					CategoryList.class);
			System.out.println("Category List " + catergoryList.toString());

			map = new LinkedMultiValueMap<String, Object>();
			map.add("itemList", items);

			ItemResponse itemsList = restTemplate.postForObject(Constant.URL + "/getItemsNameById", map,
					ItemResponse.class);

			newItemsList = itemsList.getItemList();

			customerBillItemList = new ArrayList<CustomerBillItem>();

			for (int i = 0; i < newItemsList.size(); i++) {

				Item item = newItemsList.get(i);

				CustomerBillItem customerBillItem = new CustomerBillItem();
				customerBillItem.setCatId(item.getItemGrp1());
				customerBillItem.setId(item.getId());
				customerBillItem.setItemId(item.getItemId());
				customerBillItem.setItemName(item.getItemName());
				customerBillItem.setQty(0);
				customerBillItem.setItemTax1(item.getItemTax1());
				customerBillItem.setItemTax2(item.getItemTax2());
				customerBillItem.setItemTax3(item.getItemTax3());

				if (frDetails.getFrRateCat() == 1) {
					customerBillItem.setMrp(item.getItemMrp1());
					customerBillItem.setRate(item.getItemRate1());
				} else if (frDetails.getFrRateCat() == 2) {
					customerBillItem.setMrp(item.getItemMrp2());

					customerBillItem.setRate(item.getItemRate2());

				} else if (frDetails.getFrRateCat() == 3) {
					customerBillItem.setMrp(item.getItemMrp3());

					customerBillItem.setRate(item.getItemRate3());
				}

				customerBillItemList.add(customerBillItem);

			}

			System.out.println("*********customerBillItemList***********" + customerBillItemList.toString());
			model.addObject("catList", catergoryList.getmCategoryList());
			model.addObject("itemListResponse", customerBillItemList);
		} catch (Exception e) {
			System.out.println("Exception in Display Customer Bill");
			e.printStackTrace();
			model.addObject("itemListResponse", customerBillItemList);
		}

		return model;

	}

	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	public void resetData1() {
		customerBillDataToken1 = new CustomerBillData();
		List<CustomerBillItem> customerBillList = new ArrayList<>();
		customerBillDataToken1.setCustomerBillList(customerBillList);

		customerBillDataToken1.setTotal(0.0);
		customerBillDataToken1.setGrandTotal(0.0);
		customerBillDataToken1.setDiscount(0.0);
	}

	public void resetData2() {
		customerBillDataToken2 = new CustomerBillData();
		List<CustomerBillItem> customerBillList = new ArrayList<>();
		customerBillDataToken2.setCustomerBillList(customerBillList);
	}

	public void resetData3() {
		customerBillDataToken3 = new CustomerBillData();
		List<CustomerBillItem> customerBillList = new ArrayList<>();
		customerBillDataToken3.setCustomerBillList(customerBillList);
	}

	public void resetData4() {
		customerBillDataToken4 = new CustomerBillData();
		List<CustomerBillItem> customerBillList = new ArrayList<>();
		customerBillDataToken4.setCustomerBillList(customerBillList);
	}

	public void resetData5() {
		customerBillDataToken5 = new CustomerBillData();
		List<CustomerBillItem> customerBillList = new ArrayList<>();
		customerBillDataToken5.setCustomerBillList(customerBillList);
	}

	public void resetData6() {
		customerBillDataToken6 = new CustomerBillData();
		List<CustomerBillItem> customerBillList = new ArrayList<>();
		customerBillDataToken6.setCustomerBillList(customerBillList);
	}

	public void resetData7() {
		customerBillDataToken7 = new CustomerBillData();
		List<CustomerBillItem> customerBillList = new ArrayList<>();
		customerBillDataToken7.setCustomerBillList(customerBillList);
	}

	// -----------------------------------------------------------------------------------------------
	@RequestMapping(value = "/getItemById", method = RequestMethod.GET)
	public @ResponseBody CustomerBillItem getItemById(@RequestParam(value = "id", required = true) int id,
			HttpServletRequest request, HttpServletResponse response) {

		System.out.println("**************GETITEMBYID**********************" + id);

		CustomerBillItem selectedItem = new CustomerBillItem();

		for (int i = 0; i < customerBillItemList.size(); i++) {

			if (id == customerBillItemList.get(i).getId()) {

				selectedItem = customerBillItemList.get(i);

			}
		}

		System.out.println("Item Selected is : " + selectedItem.toString());
		return selectedItem;

	}

	// Set New Item to Bean
	public CustomerBillItem setNewItem(int itemId, int itemQty) {
		CustomerBillItem currentNewItem = new CustomerBillItem();
		for (int i = 0; i < customerBillItemList.size(); i++) {

			if (customerBillItemList.get(i).getId() == itemId) {
				System.out.println("Data Matched : " + customerBillItemList.get(i).getId());

				currentNewItem.setBillStockType(customerBillItemList.get(i).getBillStockType());
				currentNewItem.setCatId(customerBillItemList.get(i).getCatId());
				currentNewItem.setId(customerBillItemList.get(i).getId());
				currentNewItem.setItemId(customerBillItemList.get(i).getItemId());
				currentNewItem.setItemName(customerBillItemList.get(i).getItemName());
				currentNewItem.setItemTax1(customerBillItemList.get(i).getItemTax1());
				currentNewItem.setItemTax2(customerBillItemList.get(i).getItemTax2());
				currentNewItem.setItemTax3(customerBillItemList.get(i).getItemTax3());
				currentNewItem.setMrp(customerBillItemList.get(i).getMrp());
				currentNewItem.setQty(customerBillItemList.get(i).getQty());
				currentNewItem.setRate(customerBillItemList.get(i).getRate());
				currentNewItem.setRegOpStockGretor(customerBillItemList.get(i).isRegOpStockGretor());
				currentNewItem.setRegOpStockLess(customerBillItemList.get(i).isRegOpStockLess());
				currentNewItem.setSpStockGretor(customerBillItemList.get(i).isSpStockGretor());
				currentNewItem.setSpStockLessthanQty(customerBillItemList.get(i).isSpStockLessthanQty());

				currentNewItem.setCurrentStockOver(customerBillItemList.get(i).isCurrentStockOver());
				currentNewItem.setTotalRegStock(customerBillItemList.get(i).getTotalRegStock());
				currentNewItem.setTotalSpStock(customerBillItemList.get(i).getTotalSpStock());

				currentNewItem.setQty(itemQty);

				System.out.println("currentNewItem" + currentNewItem.toString());
			}

		}
		return currentNewItem;
	}

	// -----------------addCurrentItem-------------------------

	CustomerBillData useSpStock(CustomerBillItem customerBillItem, int token)

	{
		List<CustomerBillItem> customerBillItemList = new ArrayList<CustomerBillItem>();
		CustomerBillData customerBillData = new CustomerBillData();

		for (int i = 0; i < currentStockDetailList.size(); i++) {

			System.out.println("useSpStock Stock" + currentStockDetailList.get(i).toString());
			System.out.println("useSpStock New Item " + customerBillItem.toString());

			if (customerBillItem.getId() == currentStockDetailList.get(i).getId()) {
				System.out.println("useSpStock Stock" + currentStockDetailList.get(i).getCurrentSpStock());
				System.out.println("useSpStock New Item " + customerBillItem.getQty());
				customerBillItem.setBillStockType(2);

				currentStockDetailList.get(i).setCurrentSpStock(
						currentStockDetailList.get(i).getCurrentSpStock() - customerBillItem.getQty());

			}

		}
		switch (token) {
		case 1:
			System.out.println("useSpStock token1 list before insertion: "
					+ customerBillDataToken1.getCustomerBillList().toString());

			List<CustomerBillItem> prevCustomerBillItemList = customerBillDataToken1.getCustomerBillList();

			prevCustomerBillItemList.add(customerBillItem);
			customerBillDataToken1.setCustomerBillList(prevCustomerBillItemList);
			customerBillData = customerBillDataToken1;

			break;

		case 2:
			List<CustomerBillItem> prevCustomerBillItemList2 = customerBillDataToken2.getCustomerBillList();

			prevCustomerBillItemList2.add(customerBillItem);
			customerBillDataToken2.setCustomerBillList(prevCustomerBillItemList2);

			customerBillData = customerBillDataToken2;
			break;

		case 3:
			List<CustomerBillItem> prevCustomerBillItemList3 = customerBillDataToken3.getCustomerBillList();

			prevCustomerBillItemList3.add(customerBillItem);
			customerBillDataToken3.setCustomerBillList(prevCustomerBillItemList3);

			customerBillData = customerBillDataToken3;
			break;

		case 4:
			List<CustomerBillItem> prevCustomerBillItemList4 = customerBillDataToken4.getCustomerBillList();

			prevCustomerBillItemList4.add(customerBillItem);
			customerBillDataToken4.setCustomerBillList(prevCustomerBillItemList4);

			customerBillData = customerBillDataToken4;
			break;

		case 5:
			List<CustomerBillItem> prevCustomerBillItemList5 = customerBillDataToken5.getCustomerBillList();

			prevCustomerBillItemList5.add(customerBillItem);
			customerBillDataToken5.setCustomerBillList(prevCustomerBillItemList5);

			customerBillData = customerBillDataToken5;
			break;
		case 6:
			List<CustomerBillItem> prevCustomerBillItemList6 = customerBillDataToken6.getCustomerBillList();

			prevCustomerBillItemList6.add(customerBillItem);
			customerBillDataToken6.setCustomerBillList(prevCustomerBillItemList6);

			customerBillData = customerBillDataToken6;
			break;

		case 7:
			List<CustomerBillItem> prevCustomerBillItemList7 = customerBillDataToken7.getCustomerBillList();

			prevCustomerBillItemList7.add(customerBillItem);
			customerBillDataToken7.setCustomerBillList(prevCustomerBillItemList7);

			customerBillData = customerBillDataToken7;
			break;

		}

		return customerBillData;

	}

	CustomerBillData useRegStock(CustomerBillItem customerBillItem, int token) {
		List<CustomerBillItem> customerBillItemList = new ArrayList<CustomerBillItem>();
		CustomerBillData customerBillData = new CustomerBillData();

		System.out.println("In Reg Stock method");
		for (int i = 0; i < currentStockDetailList.size(); i++) {

			System.out.println("useRegStock Stock" + currentStockDetailList.get(i).toString());
			System.out.println("useRegStock New Item " + customerBillItem.toString());

			if (customerBillItem.getId() == currentStockDetailList.get(i).getId()) {
				System.out.println("useRegStock Stock" + currentStockDetailList.get(i).getCurrentRegStock());
				System.out.println("useRegStock New Item " + customerBillItem.getQty());
				customerBillItem.setBillStockType(1);

				currentStockDetailList.get(i).setCurrentRegStock(
						currentStockDetailList.get(i).getCurrentRegStock() - customerBillItem.getQty());

			}

		}
		switch (token) {
		case 1:
			System.out.println("useRegStock token list before insertion: "
					+ customerBillDataToken1.getCustomerBillList().toString());

			List<CustomerBillItem> prevCustomerBillItemList = customerBillDataToken1.getCustomerBillList();

			prevCustomerBillItemList.add(customerBillItem);
			customerBillDataToken1.setCustomerBillList(prevCustomerBillItemList);
			customerBillData = customerBillDataToken1;

			break;

		case 2:
			List<CustomerBillItem> prevCustomerBillItemList2 = customerBillDataToken2.getCustomerBillList();

			prevCustomerBillItemList2.add(customerBillItem);
			customerBillDataToken2.setCustomerBillList(prevCustomerBillItemList2);

			customerBillData = customerBillDataToken2;
			break;

		case 3:
			List<CustomerBillItem> prevCustomerBillItemList3 = customerBillDataToken3.getCustomerBillList();

			prevCustomerBillItemList3.add(customerBillItem);
			customerBillDataToken3.setCustomerBillList(prevCustomerBillItemList3);

			customerBillData = customerBillDataToken3;
			break;

		case 4:
			List<CustomerBillItem> prevCustomerBillItemList4 = customerBillDataToken4.getCustomerBillList();

			prevCustomerBillItemList4.add(customerBillItem);
			customerBillDataToken4.setCustomerBillList(prevCustomerBillItemList4);

			customerBillData = customerBillDataToken4;
			break;

		case 5:
			List<CustomerBillItem> prevCustomerBillItemList5 = customerBillDataToken5.getCustomerBillList();

			prevCustomerBillItemList5.add(customerBillItem);
			customerBillDataToken5.setCustomerBillList(prevCustomerBillItemList5);

			customerBillData = customerBillDataToken5;
			break;
		case 6:
			List<CustomerBillItem> prevCustomerBillItemList6 = customerBillDataToken6.getCustomerBillList();

			prevCustomerBillItemList6.add(customerBillItem);
			customerBillDataToken6.setCustomerBillList(prevCustomerBillItemList6);

			customerBillData = customerBillDataToken6;
			break;

		case 7:
			List<CustomerBillItem> prevCustomerBillItemList7 = customerBillDataToken7.getCustomerBillList();

			prevCustomerBillItemList7.add(customerBillItem);
			customerBillDataToken7.setCustomerBillList(prevCustomerBillItemList7);

			customerBillData = customerBillDataToken7;
			break;

		}

		return customerBillData;
	}

	// ---------------------First Ajax------------------

	// ---------------------use Reg Stock------------------

	@RequestMapping(value = "/useRegStock", method = RequestMethod.GET)
	public @ResponseBody List<CustomerBillItem> RegStock(HttpServletRequest request, HttpServletResponse response) {

		int qty = Integer.parseInt(request.getParameter("qty"));
		int token = Integer.parseInt(request.getParameter("token"));

		System.out.println("IN  useRegStock");
		CustomerBillData customerBillData = new CustomerBillData();
		currentNewItem.setQty(qty);
		customerBillData = useRegStock(currentNewItem, token);
		System.out.println("After useRegStock");
		System.out.println(
				"Final List FROM REG after insert item : " + customerBillData.getCustomerBillList().toString());
		return customerBillData.getCustomerBillList();

	}

	// --------------------Use SpStock-------------------

	@RequestMapping(value = "/useSpStock", method = RequestMethod.GET)
	public @ResponseBody List<CustomerBillItem> SpStock(HttpServletRequest request, HttpServletResponse response) {

		int qty = Integer.parseInt(request.getParameter("qty"));
		int token = Integer.parseInt(request.getParameter("token"));

		System.out.println("IN  /useSpStock");
		currentNewItem.setQty(qty);
		CustomerBillData customerBillData = new CustomerBillData();

		customerBillData = useSpStock(currentNewItem, token);

		System.out.println("Final List after insert item : " + customerBillData.getCustomerBillList().toString());
		return customerBillData.getCustomerBillList();

	}

	// ------------------------------------Calculate Stock
	// -------------------------------------------------------------------------------------------
	public List<GetCurrentStockDetails> getStockFromServer(CustomerBillItem currentNewItem, Franchisee frDetails) {

		Integer runningMonth = 0;

		PostFrItemStockHeader frItemStockHeader;
		// -------------------------------------Stock---------------------------------------
		RestTemplate restTemplate = new RestTemplate();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		DateFormat yearFormat = new SimpleDateFormat("yyyy");

		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());

			frItemStockHeader = restTemplate.postForObject(Constant.URL + "getRunningMonth", map,
					PostFrItemStockHeader.class);
			runningMonth = frItemStockHeader.getMonth();
			System.err.println("Runing month in cust Bill ");

		} catch (Exception e) {
			System.out.println("Exception in runningMonth" + e.getMessage());
			e.printStackTrace();

		}

		Date todaysDate = new Date();
		System.out.println(dateFormat.format(todaysDate));

		Calendar cal = Calendar.getInstance();
		cal.setTime(todaysDate);

		cal.set(Calendar.DAY_OF_MONTH, 1);

		Date firstDay = cal.getTime();

		System.out.println("First Day of month " + firstDay);

		String strFirstDay = dateFormat.format(firstDay);

		System.out.println("Year " + yearFormat.format(todaysDate));

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("frId", frDetails.getFrId());
		map.add("frStockType", frDetails.getStockType());
		map.add("fromDate", dateFormat.format(firstDay));
		map.add("toDate", dateFormat.format(todaysDate));
		map.add("currentMonth", String.valueOf(runningMonth));
		map.add("year", yearFormat.format(todaysDate));
		System.out.println("currentNewItem catId:" + currentNewItem.getCatId());
		map.add("catId", currentNewItem.getCatId());
		map.add("itemIdList", currentNewItem.getId());

		List<GetCurrentStockDetails> getCurrentStockDetailsList = new ArrayList<GetCurrentStockDetails>();

		ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef = new ParameterizedTypeReference<List<GetCurrentStockDetails>>() {
		};
		ResponseEntity<List<GetCurrentStockDetails>> responseEntity = restTemplate
				.exchange(Constant.URL + "getCurrentStock", HttpMethod.POST, new HttpEntity<>(map), typeRef);

		// getCurrentStockDetailsList.addAll(responseEntity.getBody());
		getCurrentStockDetailsList = responseEntity.getBody();
		System.out.println("Current Stock Details : " + getCurrentStockDetailsList.toString());
		for (int i = 0; i < getCurrentStockDetailsList.size(); i++) {
			currentStockDetailList.add(getCurrentStockDetailsList.get(i));
		}
		// -----------------------------------------------------------------------------------
		return currentStockDetailList;
	}

	// ---------------------first Ajax Call----------------------

	@RequestMapping(value = "/currentNewItem", method = RequestMethod.GET)
	public @ResponseBody CustomerBillItem getAllAddedItem1(HttpServletRequest request, HttpServletResponse response) {

		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

		System.out.println("**************getAllAddedItem1*******************");

		int prevQty = 0;
		int token = Integer.parseInt(request.getParameter("token"));
		System.out.println("Token is : " + token);

		int id = Integer.parseInt(request.getParameter("id"));
		System.out.println("id : " + id);

		int itemQty = Integer.parseInt(request.getParameter("qty"));
		System.out.println("itemQty : " + itemQty);

		// List<CustomerBillItem> prevCustomerBillItemList =
		// customerBillDataToken1.getCustomerBillList();

		try {

			// CustomerBillItem currentNewItem=setNewItem(id,itemQty);
			currentNewItem = setNewItem(id, itemQty);
			boolean isPrevItem = false;

			// ------------------------------------------------------------------------------------------
			if (currentStockDetailList != null) {
				for (int i = 0; i < currentStockDetailList.size(); i++) {

					if (currentNewItem.getId() == currentStockDetailList.get(i).getId()) {
						isPrevItem = true;
					}
				}
			}
			if (currentStockDetailList == null || !isPrevItem) {
				System.out.println("If current Detail List is NULL");
				currentStockDetailList = getStockFromServer(currentNewItem, frDetails);// stock calculation
			}

			for (int i = 0; i < currentStockDetailList.size(); i++) {
				if (currentNewItem.getId() == currentStockDetailList.get(i).getId()) {
					isPrevItem = true;
					currentNewItem.setTotalSpStock(currentStockDetailList.get(i).getCurrentSpStock());
					currentNewItem.setTotalRegStock(currentStockDetailList.get(i).getCurrentRegStock());
				}

			}

		} catch (Exception e) {
			System.out.println("Exception adding item: " + e.getMessage());
			e.printStackTrace();

		}
		
		
if(currentNewItem.getCatId()==7) {
	currentNewItem.setTotalRegStock(itemQty+5);
	currentNewItem.setTotalSpStock(0);
	

	
}		

		System.out.println("Customer   " + currentNewItem.toString());

		return currentNewItem;
	}

	@RequestMapping(value = "/editQty", method = RequestMethod.GET)
	public @ResponseBody int editQty(HttpServletRequest request, HttpServletResponse response) {

		int qty = Integer.parseInt(request.getParameter("qty"));
		int key = Integer.parseInt(request.getParameter("key"));
		int token = Integer.parseInt(request.getParameter("token"));
		int id = Integer.parseInt(request.getParameter("id"));
		int alltotal = 0;
		boolean deleteFlag = false;
		int deleteIndex = key;

		switch (token) {
		case 1:
			System.out.println("token list before edit:  " + customerBillDataToken1.getCustomerBillList().toString());

			for (int i = 0; i < customerBillDataToken1.getCustomerBillList().size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = customerBillDataToken1.getCustomerBillList().get(i).getBillStockType();

						int prevQty = customerBillDataToken1.getCustomerBillList().get(i).getQty();
						if (prevQty < qty) {
							int newQty = qty - prevQty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() - newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() - newQty);
						} else if (qty == 0) {
							deleteFlag = true;
							deleteIndex = i;
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						} else {
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						}
						customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);
				alltotal += customerBillDataToken1.getCustomerBillList().get(i).getMrp()
						* customerBillDataToken1.getCustomerBillList().get(i).getQty();

			}
			if (deleteFlag) {
				customerBillDataToken1.getCustomerBillList().remove(deleteIndex);

			}

			break;

		case 2:
			System.out.println("token list before edit:  " + customerBillDataToken2.getCustomerBillList().toString());

			for (int i = 0; i < customerBillDataToken2.getCustomerBillList().size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = customerBillDataToken2.getCustomerBillList().get(i).getBillStockType();

						int prevQty = customerBillDataToken2.getCustomerBillList().get(i).getQty();
						if (prevQty < qty) {
							int newQty = qty - prevQty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() - newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() - newQty);
						} else if (qty == 0) {
							deleteFlag = true;
							deleteIndex = i;
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						} else {
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						}
						customerBillDataToken2.getCustomerBillList().get(i).setQty(qty);
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);
				alltotal += customerBillDataToken2.getCustomerBillList().get(i).getMrp()
						* customerBillDataToken2.getCustomerBillList().get(i).getQty();

			}
			if (deleteFlag) {
				customerBillDataToken2.getCustomerBillList().remove(deleteIndex);

			}

			break;
		case 3:
			System.out.println("token list before edit:  " + customerBillDataToken3.getCustomerBillList().toString());

			for (int i = 0; i < customerBillDataToken3.getCustomerBillList().size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = customerBillDataToken3.getCustomerBillList().get(i).getBillStockType();

						int prevQty = customerBillDataToken3.getCustomerBillList().get(i).getQty();
						if (prevQty < qty) {
							int newQty = qty - prevQty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() - newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() - newQty);
						} else if (qty == 0) {
							deleteFlag = true;
							deleteIndex = i;
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						} else {
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						}
						customerBillDataToken3.getCustomerBillList().get(i).setQty(qty);
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);
				alltotal += customerBillDataToken3.getCustomerBillList().get(i).getMrp()
						* customerBillDataToken3.getCustomerBillList().get(i).getQty();

			}
			if (deleteFlag) {
				customerBillDataToken7.getCustomerBillList().remove(deleteIndex);

			}

			break;

		case 4:
			System.out.println("token list before edit: : " + customerBillDataToken4.getCustomerBillList().toString());

			for (int i = 0; i < customerBillDataToken4.getCustomerBillList().size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = customerBillDataToken4.getCustomerBillList().get(i).getBillStockType();

						int prevQty = customerBillDataToken4.getCustomerBillList().get(i).getQty();
						if (prevQty < qty) {
							int newQty = qty - prevQty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() - newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() - newQty);
						} else if (qty == 0) {
							deleteFlag = true;
							deleteIndex = i;
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						} else {
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						}
						customerBillDataToken4.getCustomerBillList().get(i).setQty(qty);
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);
				alltotal += customerBillDataToken4.getCustomerBillList().get(i).getMrp()
						* customerBillDataToken4.getCustomerBillList().get(i).getQty();

			}
			if (deleteFlag) {
				customerBillDataToken7.getCustomerBillList().remove(deleteIndex);

			}

			break;

		case 5:
			System.out.println("token list before edit: " + customerBillDataToken5.getCustomerBillList().toString());

			for (int i = 0; i < customerBillDataToken5.getCustomerBillList().size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = customerBillDataToken5.getCustomerBillList().get(i).getBillStockType();

						int prevQty = customerBillDataToken5.getCustomerBillList().get(i).getQty();
						if (prevQty < qty) {
							int newQty = qty - prevQty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() - newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() - newQty);
						} else if (qty == 0) {
							deleteFlag = true;
							deleteIndex = i;
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						} else {
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						}
						customerBillDataToken5.getCustomerBillList().get(i).setQty(qty);
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);
				alltotal += customerBillDataToken5.getCustomerBillList().get(i).getMrp()
						* customerBillDataToken5.getCustomerBillList().get(i).getQty();

			}
			if (deleteFlag) {
				customerBillDataToken7.getCustomerBillList().remove(deleteIndex);

			}

			break;

		case 6:
			System.out.println("  token list before edit: " + customerBillDataToken6.getCustomerBillList().toString());

			for (int i = 0; i < customerBillDataToken6.getCustomerBillList().size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = customerBillDataToken6.getCustomerBillList().get(i).getBillStockType();

						int prevQty = customerBillDataToken6.getCustomerBillList().get(i).getQty();
						if (prevQty < qty) {
							int newQty = qty - prevQty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() - newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() - newQty);
						} else if (qty == 0) {
							deleteFlag = true;
							deleteIndex = i;
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						} else {
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						}
						customerBillDataToken6.getCustomerBillList().get(i).setQty(qty);
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);
				alltotal += customerBillDataToken6.getCustomerBillList().get(i).getMrp()
						* customerBillDataToken6.getCustomerBillList().get(i).getQty();

			}
			if (deleteFlag) {
				customerBillDataToken7.getCustomerBillList().remove(deleteIndex);

			}

			break;

		case 7:
			System.out.println("  token list before edit: " + customerBillDataToken7.getCustomerBillList().toString());

			for (int i = 0; i < customerBillDataToken7.getCustomerBillList().size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = customerBillDataToken7.getCustomerBillList().get(i).getBillStockType();

						int prevQty = customerBillDataToken7.getCustomerBillList().get(i).getQty();
						if (prevQty < qty) {
							int newQty = qty - prevQty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() - newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() - newQty);
						} else if (qty == 0) {
							deleteFlag = true;
							deleteIndex = i;
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						} else {
							int newQty = prevQty - qty;
							if (stockType == 1)
								currentStockDetailList.get(j).setCurrentRegStock(
										currentStockDetailList.get(j).getCurrentRegStock() + newQty);
							else
								currentStockDetailList.get(j)
										.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + newQty);
						}
						customerBillDataToken7.getCustomerBillList().get(i).setQty(qty);
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);
				alltotal += customerBillDataToken7.getCustomerBillList().get(i).getMrp()
						* customerBillDataToken7.getCustomerBillList().get(i).getQty();

			}
			if (deleteFlag) {
				customerBillDataToken7.getCustomerBillList().remove(deleteIndex);

			}

			break;
		}

		return alltotal;

	}

	CustomerBillData deleteOrder(int token, int key, int id) {

		CustomerBillData customerBillData = new CustomerBillData();

		switch (token) {
		case 1:

			List<CustomerBillItem> prevCustomerBillItemList1 = customerBillDataToken1.getCustomerBillList();

			System.out.println("token list before Delete:  " + customerBillDataToken1.getCustomerBillList().toString());

			for (int i = 0; i < prevCustomerBillItemList1.size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = prevCustomerBillItemList1.get(i).getBillStockType();

						int prevQty = prevCustomerBillItemList1.get(i).getQty();

						if (stockType == 1)
							currentStockDetailList.get(j)
									.setCurrentRegStock(currentStockDetailList.get(j).getCurrentRegStock() + prevQty);
						else
							currentStockDetailList.get(j)
									.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + prevQty);

					}

				}

			}
			prevCustomerBillItemList1.remove(key);
			customerBillDataToken1.setCustomerBillList(prevCustomerBillItemList1);

			customerBillData = customerBillDataToken1;

			break;

		case 2:

			List<CustomerBillItem> prevCustomerBillItemList2 = customerBillDataToken2.getCustomerBillList();

			System.out.println("token list before Delete:  " + prevCustomerBillItemList2.toString());

			for (int i = 0; i < prevCustomerBillItemList2.size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = prevCustomerBillItemList2.get(i).getBillStockType();

						int prevQty = prevCustomerBillItemList2.get(i).getQty();

						if (stockType == 1)
							currentStockDetailList.get(j)
									.setCurrentRegStock(currentStockDetailList.get(j).getCurrentRegStock() + prevQty);
						else
							currentStockDetailList.get(j)
									.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + prevQty);

					}

				}

			}
			prevCustomerBillItemList2.remove(key);
			customerBillDataToken2.setCustomerBillList(prevCustomerBillItemList2);

			customerBillData = customerBillDataToken2;

			break;

		case 3:

			List<CustomerBillItem> prevCustomerBillItemList3 = customerBillDataToken3.getCustomerBillList();

			System.out.println("token list before Delete:  " + prevCustomerBillItemList3.toString());

			for (int i = 0; i < prevCustomerBillItemList3.size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = prevCustomerBillItemList3.get(i).getBillStockType();

						int prevQty = prevCustomerBillItemList3.get(i).getQty();

						if (stockType == 1)
							currentStockDetailList.get(j)
									.setCurrentRegStock(currentStockDetailList.get(j).getCurrentRegStock() + prevQty);
						else
							currentStockDetailList.get(j)
									.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + prevQty);

					}

				}

			}
			prevCustomerBillItemList3.remove(key);
			customerBillDataToken3.setCustomerBillList(prevCustomerBillItemList3);

			customerBillData = customerBillDataToken3;

			break;

		case 4:

			List<CustomerBillItem> prevCustomerBillItemList4 = customerBillDataToken4.getCustomerBillList();

			System.out.println("token list before Delete:  " + prevCustomerBillItemList4.toString());

			for (int i = 0; i < prevCustomerBillItemList4.size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = prevCustomerBillItemList4.get(i).getBillStockType();

						int prevQty = prevCustomerBillItemList4.get(i).getQty();

						if (stockType == 1)
							currentStockDetailList.get(j)
									.setCurrentRegStock(currentStockDetailList.get(j).getCurrentRegStock() + prevQty);
						else
							currentStockDetailList.get(j)
									.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + prevQty);

						/*
						 * prevCustomerBillItemList4.get(i).setQty(qty);
						 * prevCustomerBillItemList4.remove(i);
						 */
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);

			}
			prevCustomerBillItemList4.remove(key);
			customerBillDataToken4.setCustomerBillList(prevCustomerBillItemList4);

			customerBillData = customerBillDataToken4;

			break;

		case 5:

			List<CustomerBillItem> prevCustomerBillItemList5 = customerBillDataToken5.getCustomerBillList();

			// prevCustomerBillItemList1.remove(1);

			System.out.println("token list before Delete:  " + prevCustomerBillItemList5.toString());

			for (int i = 0; i < prevCustomerBillItemList5.size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = prevCustomerBillItemList5.get(i).getBillStockType();

						int prevQty = prevCustomerBillItemList5.get(i).getQty();

						if (stockType == 1)
							currentStockDetailList.get(j)
									.setCurrentRegStock(currentStockDetailList.get(j).getCurrentRegStock() + prevQty);
						else
							currentStockDetailList.get(j)
									.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + prevQty);

						/*
						 * prevCustomerBillItemList5.get(i).setQty(qty);
						 * prevCustomerBillItemList5.remove(i);
						 */
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);

			}
			prevCustomerBillItemList5.remove(key);
			customerBillDataToken5.setCustomerBillList(prevCustomerBillItemList5);

			customerBillData = customerBillDataToken5;

			break;

		case 6:

			List<CustomerBillItem> prevCustomerBillItemList6 = customerBillDataToken6.getCustomerBillList();

			// prevCustomerBillItemList1.remove(1);

			System.out.println("token list before Delete:  " + prevCustomerBillItemList6.toString());

			for (int i = 0; i < prevCustomerBillItemList6.size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = prevCustomerBillItemList6.get(i).getBillStockType();

						int prevQty = prevCustomerBillItemList6.get(i).getQty();

						if (stockType == 1)
							currentStockDetailList.get(j)
									.setCurrentRegStock(currentStockDetailList.get(j).getCurrentRegStock() + prevQty);
						else
							currentStockDetailList.get(j)
									.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + prevQty);

						/*
						 * prevCustomerBillItemList6.get(i).setQty(qty);
						 * prevCustomerBillItemList6.remove(i);
						 */
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);

			}
			prevCustomerBillItemList6.remove(key);
			customerBillDataToken6.setCustomerBillList(prevCustomerBillItemList6);

			customerBillData = customerBillDataToken6;

			break;

		case 7:

			List<CustomerBillItem> prevCustomerBillItemList7 = customerBillDataToken7.getCustomerBillList();

			// prevCustomerBillItemList1.remove(1);

			System.out.println("token list before Delete:  " + prevCustomerBillItemList7.toString());

			for (int i = 0; i < prevCustomerBillItemList7.size(); i++) {
				for (int j = 0; j < currentStockDetailList.size(); j++) {
					if (i == key && currentStockDetailList.get(j).getId() == id) {
						int stockType = prevCustomerBillItemList7.get(i).getBillStockType();

						int prevQty = prevCustomerBillItemList7.get(i).getQty();

						if (stockType == 1)
							currentStockDetailList.get(j)
									.setCurrentRegStock(currentStockDetailList.get(j).getCurrentRegStock() + prevQty);
						else
							currentStockDetailList.get(j)
									.setCurrentSpStock(currentStockDetailList.get(j).getCurrentSpStock() + prevQty);

						/*
						 * prevCustomerBillItemList7.get(i).setQty(qty);
						 * prevCustomerBillItemList7.remove(i);
						 */
					}

				}
				// customerBillDataToken1.getCustomerBillList().get(i).setQty(qty);

			}
			prevCustomerBillItemList7.remove(key);
			customerBillDataToken7.setCustomerBillList(prevCustomerBillItemList7);

			customerBillData = customerBillDataToken7;

			break;
		}
		return customerBillData;
	}

	@RequestMapping(value = "/deleteOrder", method = RequestMethod.GET)
	public @ResponseBody List<CustomerBillItem> deleteOrder(HttpServletRequest request, HttpServletResponse response) {

		int qty = Integer.parseInt(request.getParameter("qty"));
		int key = Integer.parseInt(request.getParameter("key"));
		int token = Integer.parseInt(request.getParameter("token"));
		int id = Integer.parseInt(request.getParameter("id"));

		System.out.println("IN  /Delete Order");

		CustomerBillData customerBillData = new CustomerBillData();

		customerBillData = deleteOrder(token, key, id);

		System.out.println("Final List after Delete item : " + customerBillData.getCustomerBillList().toString());
		return customerBillData.getCustomerBillList();

	}

	@RequestMapping(value = "/generateBill", method = RequestMethod.GET)
	public @ResponseBody SellBillHeader generateSellBill(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		int token = Integer.parseInt(request.getParameter("token"));
		int isB2b=0;
		if(token==1)
		{   try {
			 isB2b=Integer.parseInt(request.getParameter("isb2b"));
			 
			  }
		catch (NullPointerException e) {
			isB2b=0;
			
		}catch (Exception e) {
			isB2b=0;
		}
		 
		}
		System.out.println("Token " + token);
		System.out.println("Token Data " + customerBillDataToken1.toString());

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		java.sql.Date cDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		SellBillHeader sellBillHeaderRes = new SellBillHeader();
		try {

			double billGrandTotal;
			double calBillGrandTotal;
			double billTotal;
			String gstNo = "";
			String phoneNo = "";

			String custName = request.getParameter("custName");
			System.out.println("Customer Name:" + custName);

			gstNo = request.getParameter("gstNo");
			System.out.println("Gst No:" + gstNo);

			phoneNo = request.getParameter("phoneNo");
			System.out.println("phoneNo:" + phoneNo);

			float discountPer = Float.parseFloat(request.getParameter("discount"));
			System.out.println("discount:" + discountPer);

			int paymentMode = Integer.parseInt(request.getParameter("paymentMode"));
			System.out.println("paymentMode:" + paymentMode);

			float paidAmount = Float.parseFloat(request.getParameter("paidAmount"));
			System.out.println("paidAmount:" + paidAmount);

			switch (token) {
			case 1:
				if (discountPer != 0) {
					billTotal = customerBillDataToken1.getTotal();

					calBillGrandTotal = billTotal - (billTotal * (discountPer / 100));
				} else {

					billTotal = customerBillDataToken1.getTotal();

					calBillGrandTotal = billTotal;

				}
				break;
			case 2:
				if (discountPer != 0) {
					billTotal = customerBillDataToken2.getTotal();

					calBillGrandTotal = billTotal - (billTotal * (discountPer / 100));
				} else {

					billTotal = customerBillDataToken2.getTotal();

					calBillGrandTotal = billTotal;

				}
				break;
			case 3:
				if (discountPer != 0) {
					billTotal = customerBillDataToken3.getTotal();

					calBillGrandTotal = billTotal - (billTotal * (discountPer / 100));
				} else {

					billTotal = customerBillDataToken3.getTotal();

					calBillGrandTotal = billTotal;

				}
				break;

			case 4:
				if (discountPer != 0) {
					billTotal = customerBillDataToken4.getTotal();

					calBillGrandTotal = billTotal - (billTotal * (discountPer / 100));
				} else {

					billTotal = customerBillDataToken4.getTotal();

					calBillGrandTotal = billTotal;

				}
				break;
			case 5:
				if (discountPer != 0) {
					billTotal = customerBillDataToken5.getTotal();

					calBillGrandTotal = billTotal - (billTotal * (discountPer / 100));
				} else {

					billTotal = customerBillDataToken5.getTotal();

					calBillGrandTotal = billTotal;

				}
				break;
			case 6:
				if (discountPer != 0) {
					billTotal = customerBillDataToken6.getTotal();

					calBillGrandTotal = billTotal - (billTotal * (discountPer / 100));
				} else {

					billTotal = customerBillDataToken6.getTotal();

					calBillGrandTotal = billTotal;

				}
				break;
			case 7:
				if (discountPer != 0) {
					billTotal = customerBillDataToken7.getTotal();

					calBillGrandTotal = billTotal - (billTotal * (discountPer / 100));
				} else {

					billTotal = customerBillDataToken7.getTotal();

					calBillGrandTotal = billTotal;

				}
				break;
			}

			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate localDate = LocalDate.now();
			System.out.println(dtf.format(localDate)); // 2016/11/16

			SellBillHeader sellBillHeader = new SellBillHeader();

			sellBillHeader.setFrId(frDetails.getFrId());
			sellBillHeader.setFrCode(frDetails.getFrCode());
			sellBillHeader.setDelStatus(0);
			sellBillHeader.setUserName(custName);
			sellBillHeader.setBillDate(dtf.format(localDate));

			sellBillHeader.setInvoiceNo(getInvoiceNo(request,response));
			sellBillHeader.setPaidAmt(Math.round(paidAmount));
			sellBillHeader.setPaymentMode(paymentMode);
			if(isB2b==1)
			{
				sellBillHeader.setBillType('B');
			}else
			{
				sellBillHeader.setBillType('R');
			}
			

			sellBillHeader.setSellBillNo(0);

			sellBillHeader.setUserGstNo(gstNo);

			sellBillHeader.setUserPhone(phoneNo);

			System.out.println("Sell Bill Header: " + sellBillHeader.toString());

			List<SellBillDetail> sellBillDetailList = new ArrayList<SellBillDetail>();
			List<CustomerBillItem> customerBillItemList = new ArrayList<CustomerBillItem>();

			switch (token) {
			case 1:
				customerBillItemList = customerBillDataToken1.getCustomerBillList();
				System.out.println("Token 1 List : " + customerBillDataToken1.toString());
				break;
			case 2:
				customerBillItemList = customerBillDataToken2.getCustomerBillList();
				break;
			case 3:
				customerBillItemList = customerBillDataToken3.getCustomerBillList();
				break;
			case 4:
				customerBillItemList = customerBillDataToken4.getCustomerBillList();
				break;
			case 5:
				customerBillItemList = customerBillDataToken5.getCustomerBillList();
				break;
			case 6:
				customerBillItemList = customerBillDataToken6.getCustomerBillList();
				break;
			case 7:
				customerBillItemList = customerBillDataToken7.getCustomerBillList();
				break;
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

				float discAmt = ((taxableAmt * discountPer) / 100);
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
				sellBillDetail.setRemark("");
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
			sellBillHeader.setTaxableAmt(sumTaxableAmt);
			sellBillHeader.setDiscountPer(discountPer);
			// float discountAmt = 0;
			// if(discountPer!=0.0)
			// discountAmt = ((sumGrandTotal * discountPer) / 100);
			float payableAmt = sumGrandTotal;

			payableAmt = roundUp(payableAmt);

			sellBillHeader.setDiscountAmt(sumMrp);
			sellBillHeader.setPayableAmt(Math.round(payableAmt));
			System.out.println("Payable amt  : " + payableAmt);
			sellBillHeader.setTotalTax(sumTotalTax);
			sellBillHeader.setGrandTotal(Math.round(sumGrandTotal));

			float calRemainingTotal = (payableAmt - paidAmount);

			if (calRemainingTotal < 0) {
				sellBillHeader.setRemainingAmt(0);

			} else {

				sellBillHeader.setRemainingAmt(calRemainingTotal);
			}
			if (calRemainingTotal <= 0) {

				sellBillHeader.setStatus(1);
			} else if (calRemainingTotal == payableAmt) {
				sellBillHeader.setStatus(2);

			} else if (payableAmt > calRemainingTotal) {
				sellBillHeader.setStatus(3);
			}

			sellBillHeader.setSellBillDetailsList(sellBillDetailList);

			System.out.println("Sell Bill Detail: " + sellBillHeader.toString());

			RestTemplate restTemplate = new RestTemplate();

			sellBillHeaderRes = restTemplate.postForObject(Constant.URL + "insertSellBillData", sellBillHeader,
					SellBillHeader.class);

			System.out.println("info :" + sellBillHeaderRes.toString());

			if (sellBillHeaderRes != null) {

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

			}
			if (sellBillHeaderRes != null) {
				if(token==1)
				resetData1();
				if(token==2)
				resetData2();
				if(token==3)
				resetData3();
				if(token==4)
				resetData4();
				if(token==5)
				resetData5();
				if(token==6)
				resetData6();
				if(token==7)
				resetData7();

			}
		} catch (Exception e) {

			System.out.println("Exception:" + e.getMessage());
			e.printStackTrace();

		}

		System.out.println("Order Response:" + sellBillHeaderRes.toString());

		return sellBillHeaderRes;

	}

	public String getInvoiceNo(HttpServletRequest request, HttpServletResponse response) {

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

	@RequestMapping(value = "/pdfSellBill", method = RequestMethod.GET)
	public ModelAndView demoBill(HttpServletRequest request, HttpServletResponse response) {
		String sellBillNo = request.getParameter("billNo");
		String billType=request.getParameter("type");
		int billNo = Integer.parseInt(sellBillNo);
		// int billNo=Integer.parseInt(fr_Id);
		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
		frGstType = frDetails.getFrGstType();
		ModelAndView model = new ModelAndView("report/franchCompInvoice");

		RestTemplate rest = new RestTemplate();
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		map.add("billNo", billNo);
		if (frGstType == 10000000) {
			model = new ModelAndView("report/franchTaxInvoice");
			List<GetCustBillTax> getCustBilTaxList = rest.postForObject(Constant.URL + "getCustomerBillTax", map,
					List.class);

			model.addObject("custBilltax", getCustBilTaxList);
		}

		ParameterizedTypeReference<List<GetCustmoreBillResponse>> typeRef = new ParameterizedTypeReference<List<GetCustmoreBillResponse>>() {
		};
		ResponseEntity<List<GetCustmoreBillResponse>> responseEntity = rest.exchange(Constant.URL + "getCustomerBill",
				HttpMethod.POST, new HttpEntity<>(map), typeRef);

		List<GetCustmoreBillResponse> getCustmoreBillResponseList = responseEntity.getBody();

		GetCustmoreBillResponse billResponse = getCustmoreBillResponseList.get(0);

		float billAmt = billResponse.getIntDiscAmt();
		float discPer = billResponse.getDiscountPer();

		int intDiscAmt = Math.round((billAmt * discPer) / 100);

		getCustmoreBillResponseList.get(0).setIntDiscAmt(intDiscAmt);

		System.out.println("bill no:" + billNo + "Custmore Bill : " + getCustmoreBillResponseList.toString());

		model.addObject("billList", getCustmoreBillResponseList);
		model.addObject("frGstType", frGstType);
		model.addObject("billType", billType);
		return model;
	}
	@RequestMapping(value = "/printSpCkBillPrint/{spOrderNo}", method = RequestMethod.GET)
	public ModelAndView showExpressBillPrint(@PathVariable int spOrderNo, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView("history/spBillInvoice");
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		try {
			RestTemplate rest = new RestTemplate();
			map.add("spOrderNo", spOrderNo);
			SpOrderHis spOrderHisSelected = rest.postForObject(Constant.URL + "/getSpCkOrderForExBillPrint", map, SpOrderHis.class);
			
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

			// model.addObject("invNo",sellInvoiceGlobal);
			model.addObject("frGstType", frGstType);
			model.addObject("spCakeOrder", spOrderHisSelected);
			model.addObject("spInvoiceHsn", settingValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	@RequestMapping(value = "/getItemIdByBarcode", method = RequestMethod.GET)
	public @ResponseBody int getItemIdByBarcode(HttpServletRequest request, HttpServletResponse response) {

		String itemId = request.getParameter("itemId");
		System.out.println("Item Id :" + itemId);
		int id = 0;

		for (int i = 0; i < newItemsList.size(); i++) {

			if (newItemsList.get(i).getItemId().equals(itemId)) {

				id = newItemsList.get(i).getId();
			}
		}

		return id;
	}

	@RequestMapping(value = "/clearData", method = RequestMethod.GET)
	public @ResponseBody int clearData(HttpServletRequest request, HttpServletResponse response) {

		int token = Integer.parseInt(request.getParameter("token"));
		switch (token) {
		case 1:
			resetData1();

			break;
		case 2:
			resetData2();

			break;
		case 3:
			resetData3();

			break;
		case 4:
			resetData4();

			break;
		case 5:
			resetData5();

			break;
		case 6:
			resetData6();

			break;
		case 7:
			resetData7();
			break;
		}
		return token;
	}
}