package com.monginis.ops.controller;

import java.io.IOException;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.monginis.ops.common.DateConvertor;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.constant.VpsImageUpload;
import com.monginis.ops.model.Company;
import com.monginis.ops.model.Expense;
import com.monginis.ops.model.ExpenseType;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.Info;
import com.monginis.ops.model.frsetting.FrSetting;

@Controller
@Scope("session")
public class ExpenseController {

	int chSeq = 0;
	
	@RequestMapping(value = "/showAddExpense")
	public ModelAndView addRate(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("expense/addExpense");

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			RestTemplate restTemplate = new RestTemplate();

			HttpSession session = request.getSession();

			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			int frId = frDetails.getFrId();

			map.add("frId", frId);
			FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map, FrSetting.class);
			//chSeq = frSetting.getCount();

			String gfg3 = String.format("%04d", chSeq);
			mav.addObject("imageUrl", Constant.GVN_IMAGE_URL);

			// System.out.println(frDetails.getFrCode());

			Company company = restTemplate.getForObject(Constant.URL + "getCompany", Company.class);
			// System.err.println("year is "+company.getFromDate());

			String myString = String.valueOf(DateConvertor.convertToDMY(company.getFromDate()));
			myString = myString.substring(myString.indexOf('-') + 3);

			String myString1 = String.valueOf(DateConvertor.convertToDMY(company.getToDate()));
			myString1 = myString1.substring(myString1.indexOf('-') + 3);

			String a = frDetails.getFrCode().concat("-").concat(String.valueOf(myString.charAt(3)))
					.concat(String.valueOf(myString.charAt(4))).concat("-").concat(String.valueOf(myString1.charAt(3)))
					.concat(String.valueOf(myString1.charAt(4))).concat("-").concat(gfg3);
			mav.addObject("chSeq", a);
			Expense ep = new Expense();

			mav.addObject("expEdit", ep);
			mav.addObject("isEdit", 0);

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String todaysDate = sdf.format(Calendar.getInstance().getTime());
			mav.addObject("todaysDate", todaysDate);

			ExpenseType[] typeArray = restTemplate.postForObject(Constant.URL + "getAllExpenseType", map, ExpenseType[].class);
			List<ExpenseType> expTypeList = new ArrayList<ExpenseType>(Arrays.asList(typeArray));
			mav.addObject("typeList", expTypeList);
			
		} catch (Exception e) {
			System.out.println("Exception In Add  showAddExpense Process:" + e.getMessage());

		}

		return mav;
	}
	
	
	@RequestMapping(value = "/addSubmitExpense", method = RequestMethod.POST)
	public String addSubmitExpense(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("photo") List<MultipartFile> photo) {

		try {
			
			RestTemplate restTemplate = new RestTemplate();

			HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			String expId = request.getParameter("expId");

			Expense exp = new Expense();
			if (expId != null || expId != "") {
				exp.setExpId(Integer.parseInt(expId));
			}

			System.out.println("expId: " + expId);
			String chalanNo = request.getParameter("chalanNo");

			String expDate = request.getParameter("fromdatepicker");

			String amount = request.getParameter("amount");

			String isActive = request.getParameter("isActive");
			String remark = request.getParameter("remark");

			System.err.println("asda" + isActive);

			VpsImageUpload upload = new VpsImageUpload();

			Calendar cale = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_ss");
			System.out.println(sdf.format(cale.getTime()));

			String curTimeStamp = sdf.format(cale.getTime());
			String gvnPhoto1 = null;

			for (int i = 0; i < photo.size(); i++) {

				if (photo.get(i).getOriginalFilename() != "") {
					try {
						gvnPhoto1 = curTimeStamp + "-" + photo.get(i).getOriginalFilename().replace(' ', '_');

						upload.saveUploadedFiles(photo, Constant.GVN_IMAGE_TYPE,
								curTimeStamp + "-" + photo.get(i).getOriginalFilename());

					} catch (IOException e) {

						System.out.println("Exce in File Upload In Expense  Insert " + e.getMessage());
						e.printStackTrace();
					}
				} else {
					gvnPhoto1 = request.getParameter("profPic");
					System.out.println(gvnPhoto1);
				}

			}

			exp.setChalanNo(chalanNo);
			exp.setChAmt(amount);
			exp.setDelStatus(0);
			
			exp.setExpDate(expDate);
			exp.setExpType(isActive);
			exp.setRemark(remark);
			exp.setStatus(Integer.parseInt(isActive));
			exp.setImgName(gvnPhoto1);

			exp.setExInt1(0);
			exp.setExInt2(0);
			exp.setExInt3(0);
			exp.setExInt4(0);

			exp.setExVar1("NA");
			exp.setExVar2("NA");
			exp.setExVar3("NA");
			exp.setExVar4("NA");

			exp.setFrId(frDetails.getFrId());

			

			Info errorMessage = restTemplate.postForObject(Constant.URL + "/saveExpense", exp, Info.class);
			System.out.println("Response: " + errorMessage.toString());

			/*
			 * if (errorMessage.isError() == false && expId.equals("0")) {
			 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
			 * Object>(); map = new LinkedMultiValueMap<String, Object>();
			 * System.err.println("incr " + chSeq + 1); map.add("frId",
			 * frDetails.getFrId()); map.add("chSeq", chSeq + 1);
			 * 
			 * Info info = restTemplate.postForObject(Constant.URL + "updateFrSettingCount",
			 * map, Info.class);
			 * 
			 * }
			 */

		} catch (Exception e) {
			System.out.println("Exception In Add  SubCategory Process:" + e.getMessage());

			return "redirect:/showAddExpense";

		}

		return "redirect:/showExpenseList";
	}
	
	
	@RequestMapping(value = "/showExpenseList", method = RequestMethod.GET)
	public ModelAndView showExpenseList(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();
		String fromDate = "";
		String toDate = "";
		String type = "";
		model = new ModelAndView("expense/expenseList");

		fromDate = request.getParameter("fromDate");
		toDate = request.getParameter("toDate");
		type = request.getParameter("type");

		if (fromDate == null && toDate == null) {

			System.err.println("in catch");
			type = "0";
			Date date = new Date();
			SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
			fromDate = formatter.format(date);
			toDate = formatter.format(date);

		}
		
		Calendar currCal=Calendar.getInstance();
		SimpleDateFormat ss = new SimpleDateFormat("dd-MM-yyyy");
		
		
		model.addObject("currDate", ss.format(currCal.getTime()));

		model.addObject("fromDate", fromDate);
		model.addObject("toDate", toDate);
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			RestTemplate restTemplate = new RestTemplate();

			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			int frId = frDetails.getFrId();

			map.add("frId", frId);
			map.add("type", type);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			
			System.err.println("FR - "+frId+"     TYPE - "+type+"    FROM DATE - "+fromDate+"     TO DATE - "+toDate);

			Expense[] frSetting = restTemplate.postForObject(Constant.URL + "getExpenseByFrId", map, Expense[].class);

			List<Expense> expList = new ArrayList<Expense>(Arrays.asList(frSetting));

			model.addObject("expList", expList);
			
			ExpenseType[] typeArray = restTemplate.postForObject(Constant.URL + "getAllExpenseType", map, ExpenseType[].class);
			List<ExpenseType> expTypeList = new ArrayList<ExpenseType>(Arrays.asList(typeArray));
			model.addObject("typeList", expTypeList);

		} catch (Exception e) {
			System.out.println("Exception In Add  showAddExpense Process:" + e.getMessage());

		}

		return model;
	}
	
	
	@RequestMapping(value = "/showEditExpense/{id}", method = RequestMethod.GET)
	public ModelAndView updateOtherItem(@PathVariable("id") int id, HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("expense/addExpense");

		RestTemplate restTemplate = new RestTemplate();

		try {
			HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("expId", id);

			Expense expEdit = restTemplate.postForObject("" + Constant.URL + "getExpenseByExpId", map, Expense.class);
			mav.addObject("expEdit", expEdit);
			mav.addObject("isEdit", 1);
			mav.addObject("imageUrl", Constant.GVN_IMAGE_URL);
			
			ExpenseType[] typeArray = restTemplate.postForObject(Constant.URL + "getAllExpenseType", map, ExpenseType[].class);
			List<ExpenseType> expTypeList = new ArrayList<ExpenseType>(Arrays.asList(typeArray));
			mav.addObject("typeList", expTypeList);
			

		} catch (Exception e) {
			System.out.println("Exc In /showEditExpense" + e.getMessage());
		}

		return mav;

	}
	
	
	@RequestMapping(value = "/deleteExpense/{id}", method = RequestMethod.GET)
	public String deleteOtherItem(@PathVariable int id, HttpServletRequest request, HttpServletResponse response) {

		try {
			RestTemplate rest = new RestTemplate();
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("expId", id);
			Info info = rest.postForObject("" + Constant.URL + "deleteExpense", map, Info.class);
			System.out.println(info.toString());

			System.out.println("info " + info);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/showExpenseList";
	}
	
	
	
}
