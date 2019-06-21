package com.monginis.ops.controller;

import java.io.FileNotFoundException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.android.gcm.server.Constants;
import com.monginis.ops.common.DateConvertor;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.AllFrIdNameList;
import com.monginis.ops.model.ExportToExcel;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.Tax1Report;
import com.monginis.ops.model.Tax2Report;
import com.monginis.ops.model.reportv2.CrNoteRegItem;
import com.monginis.ops.model.reportv2.CrNoteRegSp;
import com.monginis.ops.model.reportv2.CrNoteRegisterList;
import com.monginis.ops.model.reportv2.GstRegisterItem;
import com.monginis.ops.model.reportv2.GstRegisterList;
import com.monginis.ops.model.reportv2.GstRegisterSp;

@Controller
@Scope("session")
public class ReportControllerV2 {

	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	public AllFrIdNameList allFrIdNameList = new AllFrIdNameList();

 
	
	@RequestMapping(value = "/showCRNoteRegister", method = RequestMethod.GET)
	public ModelAndView showCRNoteRegister(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("reports/crNote_register");

		try {

			ZoneId z = ZoneId.of("Asia/Calcutta");

			LocalDate date = LocalDate.now(z);
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
			String todaysDate = date.format(formatters);

			model.addObject("todaysDate", todaysDate);

		} catch (Exception e) {
			System.out.println("Exce in showRegCakeSpOrderReport " + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}
	List<CrNoteRegItem> crNoteRegItemList = new ArrayList<>();
	// getCRNoteRegister Ajax

	@RequestMapping(value = "/getCRNoteRegister", method = RequestMethod.GET)
	public @ResponseBody List<CrNoteRegItem> getCRNoteRegister(HttpServletRequest request, HttpServletResponse response)
			throws FileNotFoundException {

		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

		String frIdString = "";

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		RestTemplate restTemplate = new RestTemplate();

		System.out.println("inside getCRNoteRegister ajax call");

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		try {

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));

			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("frId", frDetails.getFrId());

			CrNoteRegisterList crnArray = restTemplate.postForObject(Constant.URL + "getCrNoteRegisterByFrId", map,
					CrNoteRegisterList.class);

			List<CrNoteRegSp> crnRegSpList = new ArrayList<>();

			crNoteRegItemList = crnArray.getCrNoteRegItemList();
			crnRegSpList = crnArray.getCrNoteRegSpList();

			for (int j = 0; j < crnRegSpList.size(); j++) {
				int flag = 0;

				for (int i = 0; i < crNoteRegItemList.size(); i++) {

					if (crNoteRegItemList.get(i).getCrnId() == crnRegSpList.get(j).getCrnId()
							&& crNoteRegItemList.get(i).getHsnCode().equals(crnRegSpList.get(j).getHsnCode())) {
						flag = 1;
						crNoteRegItemList.get(i)
								.setCrnQty(crNoteRegItemList.get(i).getCrnQty() + crnRegSpList.get(j).getCrnQty());

						crNoteRegItemList.get(i).setCrnTaxable(
								(crNoteRegItemList.get(i).getCrnTaxable() + crnRegSpList.get(j).getCrnTaxable()));

						crNoteRegItemList.get(i)
								.setCgstAmt((crNoteRegItemList.get(i).getCgstAmt() + crnRegSpList.get(j).getCgstAmt()));
						crNoteRegItemList.get(i)
								.setSgstAmt((crNoteRegItemList.get(i).getSgstAmt() + crnRegSpList.get(j).getSgstAmt()));
						crNoteRegItemList.get(i)
								.setCrnAmt((crNoteRegItemList.get(i).getCrnAmt() + crnRegSpList.get(j).getCrnAmt()));

					}

				}

				if (flag == 0) {

					System.err.println("New hsn code item found ");

					CrNoteRegItem regItem = new CrNoteRegItem();

					regItem.setCrnDate(crnRegSpList.get(j).getCrnDate());

					regItem.setBillDate(crnRegSpList.get(j).getBillDate());
					regItem.setCrndId(crnRegSpList.get(j).getCrndId());
					regItem.setCrnId(crnRegSpList.get(j).getCrnId());
					regItem.setCrnQty(crnRegSpList.get(j).getCrnQty());
					regItem.setCgstAmt(crnRegSpList.get(j).getCgstAmt());
					regItem.setCgstPer(crnRegSpList.get(j).getCgstPer());
					regItem.setFrGstNo(crnRegSpList.get(j).getFrGstNo());
					regItem.setFrName(crnRegSpList.get(j).getFrName());
					regItem.setCrnAmt(crnRegSpList.get(j).getCrnAmt());
					regItem.setHsnCode(crnRegSpList.get(j).getHsnCode());
					regItem.setInvoiceNo(crnRegSpList.get(j).getInvoiceNo());
					regItem.setSgstAmt(crnRegSpList.get(j).getSgstAmt());
					regItem.setSgstPer(crnRegSpList.get(j).getSgstPer());
					regItem.setCrnTaxable(crnRegSpList.get(j).getCrnTaxable());

					regItem.setFrCode(crnRegSpList.get(j).getFrCode());

					crNoteRegItemList.add(regItem);
				}
			}

			System.err.println("crNoteRegItemList combined  " + crNoteRegItemList.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No.");
			rowData.add("CRN No");
			rowData.add("CRN Date");
			rowData.add("Invoice No");
			rowData.add("Invoice Date");
			rowData.add("Party Name");
			rowData.add("GST No");
			rowData.add("HSN Code");
			rowData.add("CRN Qty");
			rowData.add("Taxable Amt");
			rowData.add("CGST %");
			rowData.add("CGST Amt");
			rowData.add("SGST %");
			rowData.add("SGST Amt");
			rowData.add("Total Amt");
			rowData.add("CRN Amt");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			float crnQty = 0.0f;
			float crnTaxable = 0.0f;
			float cgstAmt = 0.0f;
			float sgstAmt = 0.0f;
			float crnAmt = 0.0f;

			for (int i = 0; i < crNoteRegItemList.size(); i++) {

				float crnTotalAmt = 0;
				for (int k = 0; k < crNoteRegItemList.size(); k++) {

					if (crNoteRegItemList.get(i).getCrnId() == crNoteRegItemList.get(k).getCrnId()) {
						crnTotalAmt = crnTotalAmt + roundUp(crNoteRegItemList.get(k).getCrnAmt());
					}
				}

				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add("" + (i + 1));
				rowData.add("" + crNoteRegItemList.get(i).getFrCode());
				rowData.add("" + crNoteRegItemList.get(i).getCrnDate());
				rowData.add("" + crNoteRegItemList.get(i).getInvoiceNo());
				rowData.add("" + crNoteRegItemList.get(i).getBillDate());
				rowData.add("" + crNoteRegItemList.get(i).getFrName());
				rowData.add("" + crNoteRegItemList.get(i).getFrGstNo());

				rowData.add("" + crNoteRegItemList.get(i).getHsnCode());
				rowData.add("" + roundUp(crNoteRegItemList.get(i).getCrnQty()));

				crnQty = crnQty + crNoteRegItemList.get(i).getCrnQty();
				crnTaxable = crnTaxable + crNoteRegItemList.get(i).getCrnTaxable();
				cgstAmt = cgstAmt + crNoteRegItemList.get(i).getCgstAmt();
				sgstAmt = sgstAmt + crNoteRegItemList.get(i).getSgstAmt();
				crnAmt = crnAmt + crNoteRegItemList.get(i).getCrnAmt();

				rowData.add("" + roundUp(crNoteRegItemList.get(i).getCrnTaxable()));
				rowData.add("" + crNoteRegItemList.get(i).getCgstPer());
				rowData.add("" + roundUp(crNoteRegItemList.get(i).getCgstAmt()));
				rowData.add("" + crNoteRegItemList.get(i).getSgstPer());
				rowData.add("" + roundUp(crNoteRegItemList.get(i).getSgstAmt()));

				rowData.add("" + roundUp(crNoteRegItemList.get(i).getCrnAmt()));
				rowData.add("" + roundUp(crnTotalAmt));

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("Total");
			rowData.add("" + roundUp(crnQty));
			rowData.add("" + roundUp(crnTaxable));
			rowData.add("");
			rowData.add("" + roundUp(cgstAmt));
			rowData.add("");
			rowData.add("" + roundUp(sgstAmt));
			rowData.add("" + Math.round(crnAmt));
			rowData.add("");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "CR Note Register");
			session.setAttribute("reportNameNew", "Credit Note Register Report");
			session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
			session.setAttribute("mergeUpto1", "$A$1:$P$1");
			session.setAttribute("mergeUpto2", "$A$2:$P$2");

		} catch (Exception e) {

			e.printStackTrace();

		}
		return crNoteRegItemList;
	}

	@RequestMapping(value = "/showGstRegister", method = RequestMethod.GET)
	public ModelAndView showGstRegister(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("reports/gst_register");

		try {

			RestTemplate restTemplate = new RestTemplate();

			ZoneId z = ZoneId.of("Asia/Calcutta");

			LocalDate date = LocalDate.now(z);
			DateTimeFormatter formatters = DateTimeFormatter.ofPattern("d-MM-uuuu");
			String todaysDate = date.format(formatters);

			allFrIdNameList = restTemplate.getForObject(Constant.URL + "getAllFrIdName", AllFrIdNameList.class);

			model.addObject("todaysDate", todaysDate);
			model.addObject("allFrIdNameList", allFrIdNameList.getFrIdNamesList());

		} catch (Exception e) {
			System.out.println("Exce in showRegCakeSpOrderReport " + e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

	List<GstRegisterItem> gstRegItemList = new ArrayList<>();

	/*
	 * @RequestMapping(value = "/getGstRegister", method = RequestMethod.GET)
	 * public @ResponseBody List<GstRegisterItem> getGstRegister(HttpServletRequest
	 * request, HttpServletResponse response) throws FileNotFoundException {
	 * 
	 * HttpSession ses = request.getSession(); Franchisee frDetails = (Franchisee)
	 * ses.getAttribute("frDetails");
	 * 
	 * String frIdString = "";
	 * 
	 * String fromDate = ""; String toDate = "";
	 * 
	 * MultiValueMap<String, Object> map = new LinkedMultiValueMap<String,
	 * Object>();
	 * 
	 * RestTemplate restTemplate = new RestTemplate();
	 * 
	 * System.out.println("inside getSalesReport ajax call");
	 * 
	 * frIdString = request.getParameter("fr_id_list"); fromDate =
	 * request.getParameter("fromDate"); toDate = request.getParameter("toDate");
	 * 
	 * 
	 * frIdString = frIdString.substring(1, frIdString.length() - 1); frIdString =
	 * frIdString.replaceAll("\"", "");
	 * 
	 * List<String> franchIds = new ArrayList();
	 * 
	 * franchIds = Arrays.asList(frIdString); if (franchIds.contains("-1")) {
	 * map.add("frIdList", -1);
	 * 
	 * } else {
	 * 
	 * map.add("frIdList", frIdString); } System.err.println("frId string " +
	 * frIdString);
	 * 
	 * try {
	 * 
	 * map.add("frIdList", frDetails.getFrId()); map.add("fromDate",
	 * DateConvertor.convertToYMD(fromDate)); map.add("toDate",
	 * DateConvertor.convertToYMD(toDate));
	 * 
	 * GstRegisterList gstArray = restTemplate.postForObject(Constant.URL +
	 * "getGstRegister", map, GstRegisterList.class);
	 * 
	 * List<GstRegisterSp> gstRegSpList = new ArrayList<>();
	 * 
	 * gstRegItemList = gstArray.getGstRegItemList(); gstRegSpList =
	 * gstArray.getGstRegSpList(); for (int j = 0; j < gstRegSpList.size(); j++) {
	 * int flag = 0;
	 * 
	 * for (int i = 0; i < gstRegItemList.size(); i++) {
	 * 
	 * if (gstRegItemList.get(i).getBillNo() == gstRegSpList.get(j).getBillNo() &&
	 * gstRegItemList.get(i).getHsnCode().equals(gstRegSpList.get(j).getHsnCode()))
	 * { flag = 1; gstRegItemList.get(i)
	 * .setBillQty(gstRegItemList.get(i).getBillQty() +
	 * gstRegSpList.get(j).getBillQty());
	 * 
	 * gstRegItemList.get(i).setTaxableAmt( (gstRegItemList.get(i).getTaxableAmt() +
	 * gstRegSpList.get(j).getTaxableAmt()));
	 * 
	 * gstRegItemList.get(i) .setCgstAmt((gstRegItemList.get(i).getCgstAmt() +
	 * gstRegSpList.get(j).getCgstAmt())); gstRegItemList.get(i)
	 * .setSgstAmt((gstRegItemList.get(i).getSgstAmt() +
	 * gstRegSpList.get(j).getSgstAmt())); gstRegItemList.get(i).setGrandTotal(
	 * (gstRegItemList.get(i).getGrandTotal() +
	 * gstRegSpList.get(j).getGrandTotal()));
	 * 
	 * }
	 * 
	 * }
	 * 
	 * if (flag == 0) {
	 * 
	 * System.err.println("New hsn code item found ");
	 * 
	 * GstRegisterItem regItem = new GstRegisterItem();
	 * 
	 * regItem.setBillDate(gstRegSpList.get(j).getBillDate());
	 * regItem.setBillDetailNo(gstRegSpList.get(j).getBillDetailNo());
	 * regItem.setBillNo(gstRegSpList.get(j).getBillNo());
	 * regItem.setBillQty(gstRegSpList.get(j).getBillQty());
	 * regItem.setCgstAmt(gstRegSpList.get(j).getCgstAmt());
	 * regItem.setCgstPer(gstRegSpList.get(j).getCgstPer());
	 * regItem.setFrGstNo(gstRegSpList.get(j).getFrGstNo());
	 * regItem.setFrName(gstRegSpList.get(j).getFrName());
	 * regItem.setGrandTotal(gstRegSpList.get(j).getGrandTotal());
	 * regItem.setHsnCode(gstRegSpList.get(j).getHsnCode());
	 * regItem.setInvoiceNo(gstRegSpList.get(j).getInvoiceNo());
	 * regItem.setSgstAmt(gstRegSpList.get(j).getSgstAmt());
	 * regItem.setSgstPer(gstRegSpList.get(j).getSgstPer());
	 * regItem.setTaxableAmt(gstRegSpList.get(j).getTaxableAmt());
	 * regItem.setTaxPer(gstRegSpList.get(j).getTaxPer());
	 * regItem.setTotalTax(gstRegSpList.get(j).getTotalTax());
	 * 
	 * gstRegItemList.add(regItem); } }
	 * 
	 * System.err.println("gstRegItemList combined  " + gstRegItemList.toString());
	 * 
	 * List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();
	 * 
	 * ExportToExcel expoExcel = new ExportToExcel(); List<String> rowData = new
	 * ArrayList<String>();
	 * 
	 * rowData.add("Sr. No."); rowData.add("Invoice No");
	 * rowData.add("Invoice Date"); rowData.add("Party Name");
	 * rowData.add("GST No"); rowData.add("HSN Code"); rowData.add("Bill Qty");
	 * rowData.add("Taxable Amt"); rowData.add("CGST %"); rowData.add("CGST Amt");
	 * rowData.add("SGST %"); rowData.add("SGST Amt"); rowData.add("Total Amt");
	 * rowData.add("Bill Amt");
	 * 
	 * float billQty = 0.0f; float taxableAmt = 0.0f; float cgstSum = 0.0f; float
	 * sgstSum = 0.0f; float grandTotal = 0.0f; float totalFinal = 0.0f;
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel); for (int i =
	 * 0; i < gstRegItemList.size(); i++) { float totalAmt = 0.0f; for (int j = 0; j
	 * < gstRegItemList.size(); j++) { if (gstRegItemList.get(i).getBillNo() ==
	 * gstRegItemList.get(j).getBillNo()) { totalAmt = totalAmt +
	 * roundUp(gstRegItemList.get(j).getGrandTotal()); }
	 * 
	 * }
	 * 
	 * expoExcel = new ExportToExcel(); rowData = new ArrayList<String>();
	 * rowData.add("" + (i + 1)); rowData.add("" +
	 * gstRegItemList.get(i).getInvoiceNo()); rowData.add("" +
	 * gstRegItemList.get(i).getBillDate()); rowData.add("" +
	 * gstRegItemList.get(i).getFrName()); rowData.add("" +
	 * gstRegItemList.get(i).getFrGstNo());
	 * 
	 * rowData.add("" + gstRegItemList.get(i).getHsnCode()); rowData.add("" +
	 * roundUp(gstRegItemList.get(i).getBillQty())); rowData.add("" +
	 * roundUp(gstRegItemList.get(i).getTaxableAmt())); rowData.add("" +
	 * gstRegItemList.get(i).getCgstPer()); rowData.add("" +
	 * roundUp(gstRegItemList.get(i).getCgstAmt())); rowData.add("" +
	 * gstRegItemList.get(i).getSgstPer()); rowData.add("" +
	 * roundUp(gstRegItemList.get(i).getSgstAmt()));
	 * 
	 * rowData.add("" + roundUp(gstRegItemList.get(i).getGrandTotal()));
	 * rowData.add("" + roundUp(totalAmt)); expoExcel.setRowData(rowData);
	 * exportToExcelList.add(expoExcel);
	 * 
	 * billQty = billQty + gstRegItemList.get(i).getBillQty(); taxableAmt =
	 * taxableAmt + gstRegItemList.get(i).getTaxableAmt(); cgstSum = cgstSum +
	 * gstRegItemList.get(i).getCgstAmt(); sgstSum = sgstSum +
	 * gstRegItemList.get(i).getSgstAmt(); grandTotal = grandTotal +
	 * gstRegItemList.get(i).getGrandTotal(); totalFinal = totalFinal + totalAmt;
	 * 
	 * }
	 * 
	 * 
	 * expoExcel = new ExportToExcel(); rowData = new ArrayList<String>();
	 * 
	 * rowData.add(""); rowData.add("Total"); rowData.add(""); rowData.add("");
	 * rowData.add(""); rowData.add(""); rowData.add("" + roundUp(billQty));
	 * rowData.add("" + roundUp(taxableAmt)); rowData.add("" + roundUp(cgstSum));
	 * rowData.add("" + roundUp(sgstSum)); rowData.add("" + roundUp(grandTotal));
	 * rowData.add("" + roundUp(totalFinal));
	 * 
	 * expoExcel.setRowData(rowData); exportToExcelList.add(expoExcel);
	 * 
	 * 
	 * HttpSession session = request.getSession();
	 * session.setAttribute("exportExcelListNew", exportToExcelList);
	 * session.setAttribute("excelName", "Sales_Report");
	 * session.setAttribute("reportNameNew", "GST Register Report By Franchise");
	 * session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: "
	 * + toDate + " "); session.setAttribute("mergeUpto1", "$A$1:$L$1");
	 * session.setAttribute("mergeUpto2", "$A$2:$L$2");
	 * 
	 * } catch (Exception e) {
	 * 
	 * e.printStackTrace();
	 * 
	 * } return gstRegItemList; }
	 */
	@RequestMapping(value = "/showTaxReport", method = RequestMethod.GET)
	public ModelAndView showTaxReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();
		String fromDate = "";
		String toDate = "";

		model = new ModelAndView("reports/tax1Report");
		// Constants.mainAct =2;
		// Constants.subAct =20;
		List<Tax1Report> taxReportList = null;

		try {

			RestTemplate restTemplate = new RestTemplate();

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");

			if (fromDate == null && toDate == null) {
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				fromDate = formatter.format(date);
				toDate = formatter.format(date);
			}
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("frId", frDetails.getFrId());

			ParameterizedTypeReference<List<Tax1Report>> typeRef = new ParameterizedTypeReference<List<Tax1Report>>() {
			};
			ResponseEntity<List<Tax1Report>> responseEntity = restTemplate
					.exchange(Constant.URL + "getTax1ReportByFrId", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			taxReportList = responseEntity.getBody();
			model.addObject("taxReportList", taxReportList);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

			System.out.println("taxReportListtaxReportListtaxReportList" + taxReportList.toString());
			System.out.println("taxReportListtaxReportListtaxReportList" + fromDate);
			System.out.println("taxReportListtaxReportListtaxReportList" + toDate);

			// exportToExcel
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("Sr.No.");
			rowData.add("GSTIN/UIN of Recipient");
			rowData.add("Receiver Name");
			rowData.add("Invoice No");
			rowData.add("Invoice date");
			rowData.add("Invoice Value");
			rowData.add("Place of Supply");
			rowData.add("Reverse Charge");

			rowData.add("Applicable % of Tax Rate");
			rowData.add("Invoice Type");
			rowData.add("E Commerce GSTIN");
			rowData.add("Rate");
			rowData.add("Taxable Value");
			rowData.add("Cess Amount");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			float taxableAmt = 0.0f;
			float cgstSum = 0.0f;
			float sgstSum = 0.0f;

			float totalTax = 0.0f;
			float totalFinal = 0.0f;
			float grandTotal = 0.0f;

			for (int i = 0; i < taxReportList.size(); i++) {
				float finalTotal = 0;
				for (int j = 0; j < taxReportList.size(); j++) {

					if (taxReportList.get(j).getBillNo() == taxReportList.get(i).getBillNo()) {
						finalTotal = finalTotal + taxReportList.get(j).getGrandTotal();
					}
				}

				taxableAmt = taxableAmt + taxReportList.get(i).getTaxableAmt();
				cgstSum = cgstSum + taxReportList.get(i).getCgstAmt();
				sgstSum = sgstSum + taxReportList.get(i).getSgstAmt();

				totalTax = totalTax + taxReportList.get(i).getTotalTax();
				grandTotal = grandTotal + taxReportList.get(i).getGrandTotal();
				totalFinal = totalFinal + finalTotal;

				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add((i + 1) + "");
				rowData.add("" + taxReportList.get(i).getFrGstNo());
				rowData.add("" + taxReportList.get(i).getFrName());
				rowData.add("" + taxReportList.get(i).getInvoiceNo());
				rowData.add("" + taxReportList.get(i).getBillDate());

				rowData.add("" + finalTotal);
				rowData.add("27-Maharashtra");
				rowData.add("N");
				rowData.add(" ");

				rowData.add("Regular");
				rowData.add(" ");

				rowData.add("" + (taxReportList.get(i).getCgstPer() + taxReportList.get(i).getSgstPer()));
				rowData.add("" + taxReportList.get(i).getTaxableAmt());
				rowData.add("0");

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("Total");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("" + roundUp(totalFinal));
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "Tax1Report");
			session.setAttribute("reportNameNew", "Tax_Repot1");
			session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
			session.setAttribute("mergeUpto1", "$A$1:$N$1");
			session.setAttribute("mergeUpto2", "$A$2:$N$2");

		} catch (Exception e) {
			System.out.println("Exc in Tax Report" + e.getMessage());
			e.printStackTrace();
		}

		return model;

	}

	@RequestMapping(value = "/showTax2Report", method = RequestMethod.GET)
	public ModelAndView showTax2Report(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = null;
		HttpSession session = request.getSession();

		String fromDate = "";
		String toDate = "";

		model = new ModelAndView("reports/tax2Report");
		// Constants.mainAct =2;
		// Constants.subAct =20;
		List<Tax2Report> taxReportList = null;
		fromDate = "";
		toDate = "";
		try {

			RestTemplate restTemplate = new RestTemplate();

			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");

			if (fromDate == null && toDate == null) {
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				fromDate = formatter.format(date);
				toDate = formatter.format(date);
			}
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("frId", frDetails.getFrId());

			ParameterizedTypeReference<List<Tax2Report>> typeRef = new ParameterizedTypeReference<List<Tax2Report>>() {
			};
			ResponseEntity<List<Tax2Report>> responseEntity = restTemplate
					.exchange(Constant.URL + "getTax2ReportByFrId", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			taxReportList = responseEntity.getBody();
			model.addObject("taxReportList", taxReportList);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);

			// exportToExcel
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr.No.");
			rowData.add("Invoice No");
			rowData.add("Bill No");
			rowData.add("Bill Date");
			rowData.add("Party Name");
			rowData.add("GSTIN");

			rowData.add("Sell @ 28%");
			rowData.add("Sell @ 18%");
			rowData.add("Sell @ 12%");
			rowData.add("Sell @ 5%");
			rowData.add("Sell @ 0%");
			rowData.add("Taxable Value");
			rowData.add("SGST @ 14%");
			rowData.add("CGST @ 14%");
			rowData.add("SGST @ 9%");
			rowData.add("CGST @ 9%");
			rowData.add("SGST @ 6%");
			rowData.add("CGST @ 6%");
			rowData.add("SGST @ 2.5%");
			rowData.add("CGST @ 2.5%");
			rowData.add("SGST @ 0%");
			rowData.add("CGST @ 0%");
			rowData.add("SGST Value");
			rowData.add("CGST Value");
			rowData.add("GROSS BILL");

			float totalTaxableValue = 0.0f;
			float totalCgstValue = 0.0f;
			float totalSgstValue = 0.0f;
			float totalGrossBill = 0.0f;

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			for (int i = 0; i < taxReportList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add((i + 1) + "");
				rowData.add("" + taxReportList.get(i).getInvoiceNo());
				rowData.add("" + taxReportList.get(i).getBillNo());
				rowData.add("" + taxReportList.get(i).getBillDate());

				rowData.add("" + taxReportList.get(i).getFrName());
				rowData.add("" + taxReportList.get(i).getFrGstNo());

				rowData.add("" + taxReportList.get(i).getTaxableAmtTwentyEight());
				rowData.add("" + taxReportList.get(i).getTaxableAmtEighteen());
				rowData.add("" + taxReportList.get(i).getTaxableAmtTwelve());
				rowData.add("" + taxReportList.get(i).getTaxableAmtFive());
				rowData.add("" + taxReportList.get(i).getTaxableAmtZero());
				float taxableAmt = Math.round((taxReportList.get(i).getTaxableAmtTwentyEight()
						+ taxReportList.get(i).getTaxableAmtEighteen() + taxReportList.get(i).getTaxableAmtTwelve()
						+ taxReportList.get(i).getTaxableAmtFive() + taxReportList.get(i).getTaxableAmtZero()));

				rowData.add("" + taxableAmt);
				rowData.add("" + taxReportList.get(i).getSgstAmtTwentyEight());
				rowData.add("" + taxReportList.get(i).getCgstAmtTwentyEight());
				rowData.add("" + taxReportList.get(i).getSgstAmtEighteen());
				rowData.add("" + taxReportList.get(i).getCgstAmtEighteen());
				rowData.add("" + taxReportList.get(i).getSgstAmtTwelve());
				rowData.add("" + taxReportList.get(i).getCgstAmtTwelve());
				rowData.add("" + taxReportList.get(i).getSgstAmtFive());
				rowData.add("" + taxReportList.get(i).getCgstAmtFive());
				rowData.add("" + taxReportList.get(i).getSgstAmtZero());
				rowData.add("" + taxReportList.get(i).getCgstAmtZero());
				float sgstSum = Math.round((taxReportList.get(i).getSgstAmtTwentyEight()
						+ taxReportList.get(i).getSgstAmtEighteen() + taxReportList.get(i).getSgstAmtTwelve()
						+ taxReportList.get(i).getSgstAmtFive() + taxReportList.get(i).getSgstAmtZero()));
				float cgstSum = Math.round((taxReportList.get(i).getCgstAmtTwentyEight()
						+ taxReportList.get(i).getCgstAmtEighteen() + taxReportList.get(i).getCgstAmtTwelve()
						+ taxReportList.get(i).getCgstAmtFive() + taxReportList.get(i).getCgstAmtZero()));
				float grossSell = Math.round((taxableAmt + sgstSum + cgstSum));
				rowData.add("" + sgstSum);
				rowData.add("" + cgstSum);
				rowData.add("" + grossSell);

				totalTaxableValue = totalTaxableValue + taxableAmt;
				totalCgstValue = totalCgstValue + cgstSum;
				totalSgstValue = totalSgstValue + sgstSum;
				totalGrossBill = totalGrossBill + grossSell;

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("");

			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("Total");
			rowData.add("");

			rowData.add("" + roundUp(totalTaxableValue));

			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("" + roundUp(totalSgstValue));
			rowData.add("" + roundUp(totalCgstValue));

			rowData.add("" + roundUp(totalGrossBill));

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			session = request.getSession();
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "Tax_Repot2");
			session.setAttribute("reportNameNew", "Bill-wise Report");
			session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

		} catch (Exception e) {
			System.out.println("Exc in Tax Report" + e.getMessage());
			e.printStackTrace();
		}

		return model;

	}

}
