package com.monginis.ops.controller;

import java.awt.Dimension;

import java.awt.Insets;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.bouncycastle.jcajce.provider.symmetric.AES.CBC;
import org.springframework.context.annotation.Scope;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.monginis.ops.common.DateConvertor;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.dailysales.DailySalesReportDao;
import com.monginis.ops.model.BillWisePurchaseList;
import com.monginis.ops.model.BillWisePurchaseReport;
import com.monginis.ops.model.BillWiseTaxReport;
import com.monginis.ops.model.BillWiseTaxReportList;
import com.monginis.ops.model.CategoryList;
import com.monginis.ops.model.ExportToExcel;
import com.monginis.ops.model.Franchisee;

import com.monginis.ops.model.GetRepFrDatewiseSellResponse;
import com.monginis.ops.model.GetRepFrItemwiseSellResponse;
import com.monginis.ops.model.GetRepMenuwiseSellResponse;
import com.monginis.ops.model.GetRepTaxSell;
import com.monginis.ops.model.GetSellBillHeader;
import com.monginis.ops.model.ItemWiseDetail;
import com.monginis.ops.model.ItemWiseDetailList;
import com.monginis.ops.model.ItemWiseReport;
import com.monginis.ops.model.ItemWiseReportList;
import com.monginis.ops.model.MCategory;
import com.monginis.ops.model.Main;
import com.monginis.ops.model.MonthWiseReport;
import com.monginis.ops.model.MonthWiseReportList;
import com.monginis.ops.model.grngvn.GrnGvnHeader;
import com.monginis.ops.model.reportv2.CrNoteRegItem;
import com.monginis.ops.model.reportv2.CrNoteRegSp;
import com.monginis.ops.model.reportv2.CrNoteRegisterList;
import com.monginis.ops.model.spadvreport.GetSpAdvTaxReport;
import com.monginis.ops.model.spadvreport.GetSpAdvTaxReportList;
import com.monginis.ops.model.spadvreport.GetSpAdvanceReport;
import com.monginis.ops.model.spadvreport.GetSpAdvanceReportList;

@Controller
@Scope("session")
public class ReportsController {
	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	public List<GetRepTaxSell> getRepTaxSell;
	public List<GetRepFrDatewiseSellResponse> getRepFrDatewiseSellResponse;
	public List<GetSellBillHeader> getSellBillHeaderList;
	// List<GetRepFrDatewiseSellResponse> getRepFrDatewiseSellResponse;
	public List<GetRepMenuwiseSellResponse> getRepFrMenuwiseSellResponseList;
	public List<GetRepFrItemwiseSellResponse> getRepFrItemwiseSellResponseList;
	// List<GetRepFrItemwiseSellResponse> getRepFrItemwiseSellResponseList;
	// List<GetRepFrItemwiseSellResponse> getRepFrItemwiseSellResponseList;
	public List<BillWisePurchaseReport> billWisePurchaseReportList;
	public List<ItemWiseDetail> itemWiseDetailReportList;
	public List<ItemWiseReport> itemWiseReportList;
	public List<BillWiseTaxReport> billWiseTaxReport;
	public List<MonthWiseReport> monthWiseReportList;
	public int frGstType = 0;

	@RequestMapping(value = "/showSpAdvanceReport", method = RequestMethod.GET)
	public ModelAndView showSpAdvanceReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/spadvance");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();

		}
		return model;
	}

	List<GetSpAdvanceReport> advList = new ArrayList<>();

	@RequestMapping(value = "/getSpAdvance", method = RequestMethod.GET)
	public @ResponseBody List<GetSpAdvanceReport> getGvnHeaderList(HttpServletRequest request,
			HttpServletResponse response) {
		// ModelAndView modelAndView = new ModelAndView("grngvn/displaygrn");
		advList = new ArrayList<>();

		String fromDate = "";
		String toDate = "";
		try {
			System.out.println("in method /getSpAdvance");
			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			RestTemplate restTemplate = new RestTemplate();
			System.err.println("from " + fromDate + "toDate " + toDate);

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));

			map.add("toDate", DateConvertor.convertToYMD(toDate));

			map.add("frId", frId);

			GetSpAdvanceReportList spAdv = restTemplate.postForObject(Constant.URL + "spProduction/getSpAdvanceReport",
					map, GetSpAdvanceReportList.class);
			advList = spAdv.getSpAdvanceReport();

			System.err.println("Adv List " + spAdv.toString());
		} catch (Exception e) {
			System.err.println("Ex in gettting sp adv list " + e.getMessage());
			e.printStackTrace();
		}

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Customer Name");
		rowData.add("Item Name");
		rowData.add("Order Date");
		rowData.add("MRP");
		rowData.add("Adavnce Amt");

		expoExcel.setRowData(rowData);

		exportToExcelList.add(expoExcel);

		float baseMrp = 0.0f;
		float advAmt = 0.0f;

		for (int i = 0; i < advList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("" + (i + 1));
			rowData.add("" + advList.get(i).getCustName());
			rowData.add("" + advList.get(i).getItemName());
			rowData.add("" + advList.get(i).getOrderDate());

			rowData.add("" + roundUp(advList.get(i).getTotalMrp()));
			rowData.add("" + roundUp(advList.get(i).getAdvAmt()));

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			baseMrp = baseMrp + advList.get(i).getTotalMrp();
			advAmt = advAmt + advList.get(i).getAdvAmt();
		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();

		rowData.add("");
		rowData.add("Total");
		rowData.add("");
		rowData.add("");
		rowData.add("" + roundUp(baseMrp));
		rowData.add("" + roundUp(advAmt));
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelListNew", exportToExcelList);
		session.setAttribute("excelNameNew", "GetSpAdvanceReport");
		session.setAttribute("reportNameNew", "Sp   Report");
		session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
		session.setAttribute("mergeUpto1", "$A$1:$F$1");
		session.setAttribute("mergeUpto2", "$A$2:$F$2");

		return advList;

	}

	@RequestMapping(value = "/getSpAdvPdf", method = RequestMethod.GET)
	public void advList(HttpServletRequest request, HttpServletResponse response) {

		Document doc = new Document();

		String FILE_PATH = Constant.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(FILE_PATH);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			writer = PdfWriter.getInstance(doc, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(6);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 0.5f, 1.8f, 1.8f, 1.2f, 1.0f, 1.2f });
			Font headFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Customer Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Order Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("MRP", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Advance Amt", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			int index = 0;
			float totalMrp = 0;
			float totalAmt = 0;

			for (GetSpAdvanceReport advance : advList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(advance.getCustName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(advance.getItemName()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(advance.getOrderDate()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(advance.getTotalMrp()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(advance.getAdvAmt()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);
				totalMrp = totalMrp + advance.getTotalMrp();
				totalAmt = totalAmt + advance.getAdvAmt();

			}
			hcell = new PdfPCell();
			hcell = new PdfPCell(new Phrase("", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(roundUp(totalMrp) + "", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(roundUp(totalAmt) + "", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(hcell);

			doc.open();

			Paragraph heading = new Paragraph("Report-Sp Cake Advance");
			heading.setAlignment(Element.ALIGN_CENTER);
			doc.add(heading);
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new java.util.Date());

			doc.add(new Paragraph("" + reportDate));
			doc.add(new Paragraph("\n"));
			// document.add(new Paragraph(" "));
			doc.add(table);

			doc.close();

			// Atul Sir code to open a Pdf File
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

			System.out.println("Pdf Generation Error: Prod From Orders" + ex.getMessage());

			ex.printStackTrace();

		}

	}

	// sp tax advance report using delivery date
	@RequestMapping(value = "/showSpAdvTaxReport", method = RequestMethod.GET)
	public ModelAndView showSpAdvTaxReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/spAdvTaxReport");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();

		}
		return model;
	}

	List<GetSpAdvTaxReport> spAdvTaxList = new ArrayList<>();

	String fd, tD, frName;

	// getSpAdvTaxReport ajax
	@RequestMapping(value = "/getSpAdvTaxReport", method = RequestMethod.GET)
	public @ResponseBody List<GetSpAdvTaxReport> getSpAdvTaxReport(HttpServletRequest request,
			HttpServletResponse response) {
		// ModelAndView modelAndView = new ModelAndView("grngvn/displaygrn");
		spAdvTaxList = new ArrayList<>();
		String fromDate = "";
		String toDate = "";
		try {
			System.out.println("in method /getSpAdvTaxReport");
			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");
			fd = fromDate;
			tD = toDate;
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			RestTemplate restTemplate = new RestTemplate();
			System.err.println("from " + fromDate + "toDate " + toDate);
			frName = frDetails.getFrName();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));

			map.add("toDate", DateConvertor.convertToYMD(toDate));

			map.add("frId", frId);

			GetSpAdvTaxReportList spTaxAdv = restTemplate.postForObject(Constant.URL + "spProduction/getSpAdvTaxRepor",
					map, GetSpAdvTaxReportList.class);
			spAdvTaxList = spTaxAdv.getSpAdvTaxReport();

			System.err.println("Sp tax List List " + spTaxAdv.toString());

			// export to excel

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No");
			rowData.add("Invoice No.");
			rowData.add("Item name");
			rowData.add("HSN Code");
			rowData.add("Delivery Date");
			rowData.add("Base MRP");
			rowData.add("CGST %");
			rowData.add("SGST %");
			rowData.add("CGST Amt");
			rowData.add("SGST Amt");

			rowData.add("Total");
			rowData.add("Remaning");
			expoExcel.setRowData(rowData);

			exportToExcelList.add(expoExcel);

			float baseMrp = 0.0f;
			float cgstSum = 0.0f;
			float sgstSum = 0.0f;

			float grandTotal = 0.0f;
			float remFinalTotal = 0.0f;
			for (int i = 0; i < spAdvTaxList.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add("" + (i + 1));
				rowData.add("" + spAdvTaxList.get(i).getInvoiceNo());
				rowData.add("" + spAdvTaxList.get(i).getSpName());
				rowData.add("" + spAdvTaxList.get(i).getSpHsncd());
				rowData.add("" + spAdvTaxList.get(i).getDelDate());

				rowData.add("" + roundUp(spAdvTaxList.get(i).getBaseMrp()));
				rowData.add("" + roundUp(spAdvTaxList.get(i).getTax1()));
				rowData.add("" + roundUp(spAdvTaxList.get(i).getTax2()));
				/* rowData.add("" + billWisePurchaseReportList.get(i).getRoundOff()); */
				rowData.add("" + roundUp(spAdvTaxList.get(i).getTax1Amt()));
				rowData.add("" + roundUp(spAdvTaxList.get(i).getTax2Amt()));

				rowData.add("" + roundUp(spAdvTaxList.get(i).getTotal()));
				rowData.add("" + roundUp(spAdvTaxList.get(i).getRmAmount()));

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

				baseMrp = baseMrp + spAdvTaxList.get(i).getBaseMrp();
				cgstSum = cgstSum + spAdvTaxList.get(i).getTax1Amt();
				sgstSum = sgstSum + spAdvTaxList.get(i).getTax2Amt();
				remFinalTotal = remFinalTotal + spAdvTaxList.get(i).getRmAmount();

				grandTotal = grandTotal + spAdvTaxList.get(i).getTotal();
			}

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("");
			rowData.add("Total");
			rowData.add("");

			rowData.add("");
			rowData.add("");
			rowData.add("" + roundUp(baseMrp));
			rowData.add("");
			rowData.add("");
			rowData.add("" + roundUp(cgstSum));
			rowData.add("" + roundUp(sgstSum));

			rowData.add("" + roundUp(grandTotal));
			rowData.add("" + roundUp(remFinalTotal));

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "GetSpAdvTaxReport");
			session.setAttribute("reportNameNew", "Sp Tax Report");
			session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

		} catch (Exception e) {
			System.err.println("Ex in gettting sp tax list " + e.getMessage());
			e.printStackTrace();
		}
		return spAdvTaxList;

	}
	// getSpAdvTaxPdf

	@RequestMapping(value = "/getSpAdvTaxPdf", method = RequestMethod.GET)
	public void getSpAdvTaxPdf(HttpServletRequest request, HttpServletResponse response) {

		Document doc = new Document();

		String FILE_PATH = Constant.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = null;
		try {
			out = new FileOutputStream(FILE_PATH);
		} catch (FileNotFoundException e1) {

			System.err.println("File not found Exception" + e1.getMessage());
			e1.printStackTrace();
		}
		try {
			writer = PdfWriter.getInstance(doc, out);
		} catch (DocumentException e) {
			System.err.println("DocumentException Exception" + e.getMessage());

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(13);
		try {
			System.out.println("Inside PDF Table try /getSpAdvTaxPdf");
			table.setWidthPercentage(100);
			table.setWidths(
					new float[] { 1.2f, 1.4f, 1.5f, 1.4f, 1.5f, 1.6f, 0.8f, 0.8f, 1.2f, 1.3f, 1.2f, 1.3f, 1.4f });
			Font headFont = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLUE);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Invoice No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Hsn Code", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Delivery Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Base MRP", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("CGST %", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("SGST %", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("CGST Rs", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("SGST Rs", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Advance", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Remaining", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(hcell);

			int index = 0;

			float baseMrpTotal = 0;

			float cgstTotal = 0;
			float sgstTotal = 0;

			float totalAmt = 0;
			float advanceAmt = 0;
			float totalRemaining = 0;

			for (GetSpAdvTaxReport spTax : spAdvTaxList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(spTax.getInvoiceNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(spTax.getSpName()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(spTax.getSpHsncd()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(spTax.getDelDate()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_LEFT);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(roundUp(spTax.getBaseMrp())), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				baseMrpTotal = baseMrpTotal + spTax.getBaseMrp();
				cgstTotal = cgstTotal + spTax.getTax1Amt();
				sgstTotal = sgstTotal + spTax.getTax2Amt();
				totalAmt = totalAmt + spTax.getTotal();
				advanceAmt = advanceAmt + spTax.getSpAdvance();
				totalRemaining = totalRemaining + spTax.getRmAmount();

				cell = new PdfPCell(new Phrase(String.valueOf(spTax.getTax1()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(spTax.getTax2()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(spTax.getTax1Amt()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(spTax.getTax2Amt()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(spTax.getTotal()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(spTax.getSpAdvance()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(12);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(spTax.getRmAmount()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(12);
				table.addCell(cell);

			}

			hcell = new PdfPCell();
			hcell = new PdfPCell(new Phrase("", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Total", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(roundUp(baseMrpTotal) + "", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(roundUp(cgstTotal) + "", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(roundUp(sgstTotal) + "", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(roundUp(totalAmt) + "", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(roundUp(advanceAmt) + "", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase(roundUp(totalRemaining) + "", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(hcell);

			doc.open();

			Paragraph heading = new Paragraph("Report-Sp Cake Tax ");
			heading.setAlignment(Element.ALIGN_CENTER);
			doc.add(heading);
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new java.util.Date());

			doc.add(new Paragraph("" + reportDate));
			doc.add(new Paragraph(frName + " From " + fd + " To " + tD));
			doc.add(new Paragraph("\n "));
			doc.add(table);

			doc.close();

			// Atul Sir code to open a Pdf File
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

			System.out.println("Pdf Generation Error: Prod From Orders" + ex.getMessage());

			ex.printStackTrace();

		}
		fd = null;
		tD = null;
		frName = null;
	}

	@RequestMapping(value = "/viewBillWisePurchaseReport", method = RequestMethod.GET)
	public ModelAndView viewBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/purchaseReport/billWisePurchaseReport");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();

		}
		return model;
	}

	@RequestMapping(value = "/viewBillTaxPurchaseReport", method = RequestMethod.GET)
	public ModelAndView billTaxReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/purchaseReport/billWiseTaxReport");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/viewItemWiseDetailReport", method = RequestMethod.GET)
	public ModelAndView itemWiseDetailReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/purchaseReport/itemWiseDetail");
		try {
			RestTemplate restTemplate = new RestTemplate();

			CategoryList allCategoryResponse = restTemplate.getForObject(Constant.URL + "showAllCategory",
					CategoryList.class);

			List<MCategory> catList = allCategoryResponse.getmCategoryList();

			System.out.println("catList :" + catList.toString());
			model.addObject("catList", catList);
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/viewItemWiseReport", method = RequestMethod.GET)
	public ModelAndView itemWiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/purchaseReport/itemWiseReport");
		try {
			RestTemplate restTemplate = new RestTemplate();

			CategoryList allCategoryResponse = restTemplate.getForObject(Constant.URL + "showAllCategory",
					CategoryList.class);

			List<MCategory> catList = allCategoryResponse.getmCategoryList();

			System.out.println("catList :" + catList.toString());
			model.addObject("catList", catList);
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/viewMonthWisePurchaseReport", method = RequestMethod.GET)
	public ModelAndView monthWisePurchaseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/purchaseReport/monthWisePurchase");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	// ----------------------------------Bill Wise Purchase
	// Report---------------------------------------------
	@RequestMapping(value = "/findBillWisePurchase", method = RequestMethod.GET)
	public @ResponseBody List<BillWisePurchaseReport> getBillWisePurchase(HttpServletRequest request,
			HttpServletResponse response) {
		String fromDate = "";
		String toDate = "";

		try {
			System.out.println("in method");
			fromDate = request.getParameter("fromDate");
			System.out.println("fromDate" + fromDate);

			toDate = request.getParameter("toDate");
			System.out.println("toDate" + toDate);

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			frGstType = frDetails.getFrGstType();
			System.out.println("frId" + frId);

			map.add("frId", frId);
			map.add("fromDate", Main.formatDate(fromDate));
			map.add("toDate", Main.formatDate(toDate));

			billWisePurchaseReportList = new ArrayList<BillWisePurchaseReport>();

			BillWisePurchaseList billWisePurchaseList = restTemplate
					.postForObject(Constant.URL + "/showBillWisePurchaseReport", map, BillWisePurchaseList.class);

			billWisePurchaseReportList = billWisePurchaseList.getBillWisePurchaseList();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("BillReportBillWise: " + billWisePurchaseReportList.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Invoice No.");
		rowData.add("Bill Date");
		rowData.add("Taxable Amount");
		rowData.add("CGST Amt");
		rowData.add("SGST Amt");
		rowData.add("IGST Amt");
		/* rowData.add("Round Off"); */
		rowData.add("Grand Total");
		expoExcel.setRowData(rowData);

		exportToExcelList.add(expoExcel);

		float taxableAmt = 0.0f;
		float cgstSum = 0.0f;
		float sgstSum = 0.0f;
		float igstSum = 0.0f;

		float grandTotal = 0.0f;

		for (int i = 0; i < billWisePurchaseReportList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("" + (i + 1));
			rowData.add("" + billWisePurchaseReportList.get(i).getInvoiceNo());
			rowData.add("" + billWisePurchaseReportList.get(i).getBillDate());
			rowData.add("" + roundUp(billWisePurchaseReportList.get(i).getTaxableAmt()));

			rowData.add("" + roundUp(billWisePurchaseReportList.get(i).getCgstRs()));
			rowData.add("" + roundUp(billWisePurchaseReportList.get(i).getSgstRs()));
			rowData.add("" + roundUp(billWisePurchaseReportList.get(i).getIgstRs()));
			/* rowData.add("" + billWisePurchaseReportList.get(i).getRoundOff()); */

			rowData.add("" + roundUp(billWisePurchaseReportList.get(i).getGrandTotal()));

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			taxableAmt = taxableAmt + billWisePurchaseReportList.get(i).getTaxableAmt();
			cgstSum = cgstSum + billWisePurchaseReportList.get(i).getCgstRs();
			sgstSum = sgstSum + billWisePurchaseReportList.get(i).getSgstRs();
			igstSum = igstSum + billWisePurchaseReportList.get(i).getIgstRs();

			grandTotal = grandTotal + billWisePurchaseReportList.get(i).getGrandTotal();

		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();

		rowData.add("");
		rowData.add("Total");
		rowData.add("");
		rowData.add("" + roundUp(taxableAmt));
		rowData.add("" + roundUp(cgstSum));
		rowData.add("" + roundUp(sgstSum));
		rowData.add("" + roundUp(igstSum));
		rowData.add("" + roundUp(grandTotal));

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelListNew", exportToExcelList);
		session.setAttribute("excelNameNew", "BillWisePurchaseReport");
		session.setAttribute("reportNameNew", "Billwise Purchase Report");
		session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
		session.setAttribute("mergeUpto1", "$A$1:$L$1");
		session.setAttribute("mergeUpto2", "$A$2:$L$2");

		return billWisePurchaseReportList;

	}

	// ---------------------------------------------------------------------------------------------------------------------

	// ----------------------------------Item Wise Detail
	// Report---------------------------------------------
	@RequestMapping(value = "/findItemWiseDetailReport", method = RequestMethod.GET)
	public @ResponseBody List<ItemWiseDetail> findItemWiseDetailReport(HttpServletRequest request,
			HttpServletResponse response) {
		String fromDate = "";
		String toDate = "";
		try {
			System.out.println("in method");
			fromDate = request.getParameter("fromDate");
			System.out.println("fromDate" + fromDate);

			toDate = request.getParameter("toDate");
			System.out.println("toDate" + toDate);

			int catId = Integer.parseInt(request.getParameter("catId"));

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			System.out.println("frId" + frId);

			map.add("frId", frId);
			map.add("fromDate", Main.formatDate(fromDate));
			map.add("toDate", Main.formatDate(toDate));
			map.add("catId", catId);

			itemWiseDetailReportList = new ArrayList<ItemWiseDetail>();

			ItemWiseDetailList itemWiseDetailList = restTemplate
					.postForObject(Constant.URL + "/showItemWiseDetailsReport", map, ItemWiseDetailList.class);

			itemWiseDetailReportList = itemWiseDetailList.getItemWiseDetailList();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("ItemWiseDetailReport: " + itemWiseDetailReportList.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();
		rowData.add("Sr. No.");
		rowData.add("Bill Date");
		rowData.add("Purchase Bill No");
		rowData.add("Item Id");
		rowData.add("Item Name");
		rowData.add("Rate");
		rowData.add("Quantity");
		rowData.add("Total Amount");
		rowData.add("Grn type");

		float totalQty = 0.0f;
		float totalAmt = 0.0f;

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < itemWiseDetailReportList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("" + (i + 1));
			rowData.add("" + itemWiseDetailReportList.get(i).getBillDate());
			rowData.add("" + itemWiseDetailReportList.get(i).getBillNo());

			rowData.add("" + itemWiseDetailReportList.get(i).getItemId());
			rowData.add("" + itemWiseDetailReportList.get(i).getItemName());
			rowData.add("" + itemWiseDetailReportList.get(i).getRate());

			rowData.add("" + itemWiseDetailReportList.get(i).getQty());
			rowData.add("" + itemWiseDetailReportList.get(i).getTotal());
			if (itemWiseDetailReportList.get(i).getGrnType() == 0) {
				rowData.add("GRN 1");
			} else if (itemWiseDetailReportList.get(i).getGrnType() == 1) {
				rowData.add("GRN 2");
			} else if (itemWiseDetailReportList.get(i).getGrnType() == 2) {
				rowData.add("GRN 3");
			} else if (itemWiseDetailReportList.get(i).getGrnType() == 3) {
				rowData.add("No GRN");
			} else if (itemWiseDetailReportList.get(i).getGrnType() == 4) {
				rowData.add("GRN 4");
			}

			totalQty = totalQty + itemWiseDetailReportList.get(i).getQty();
			totalAmt = totalAmt + itemWiseDetailReportList.get(i).getTotal();

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();

		rowData.add("");
		rowData.add("Total");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("");
		rowData.add("" + roundUp(totalQty));
		rowData.add("" + roundUp(totalAmt));
		rowData.add("");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelListNew", exportToExcelList);
		session.setAttribute("excelNameNew", "ItemWiseDetailPurchaseReport");
		session.setAttribute("reportNameNew", "Itemwise-Billwise-Datewise Purchase Report");
		session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
		session.setAttribute("mergeUpto1", "$A$1:$L$1");
		session.setAttribute("mergeUpto2", "$A$2:$L$2");

		return itemWiseDetailReportList;

	}

	// ---------------------------------------------------------------------------------------------------------------------
	// ----------------------------------Item Wise Detail
	// Report---------------------------------------------
	@RequestMapping(value = "/findItemWiseReport", method = RequestMethod.GET)
	public @ResponseBody List<ItemWiseReport> findItemWiseReport(HttpServletRequest request,
			HttpServletResponse response) {

		String fromDate = "";
		String toDate = "";

		try {
			System.out.println("in method");
			fromDate = request.getParameter("fromDate");
			System.out.println("fromDate" + fromDate);

			toDate = request.getParameter("toDate");
			System.out.println("toDate" + toDate);

			int catId = Integer.parseInt(request.getParameter("catId"));

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			System.out.println("frId" + frId);

			map.add("frId", frId);
			map.add("fromDate", Main.formatDate(fromDate));
			map.add("toDate", Main.formatDate(toDate));
			map.add("catId", catId);

			itemWiseReportList = new ArrayList<ItemWiseReport>();

			ItemWiseReportList itemWiseList = restTemplate.postForObject(Constant.URL + "/showItemWiseReport", map,
					ItemWiseReportList.class);

			itemWiseReportList = itemWiseList.getItemWiseReportList();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("ItemWiseDetailReport: " + itemWiseReportList.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr.No");
		rowData.add("Party Name");
		rowData.add("Item Name");
		rowData.add("Rate");

		rowData.add("Quantity");

		rowData.add("Total Amount");

		float totalQty = 0.0f;
		float totalAmt = 0.0f;

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < itemWiseReportList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("" + (i + 1));
			rowData.add(Constant.FACTORYNAME);

			/* rowData.add("" + itemWiseReportList.get(i).getItemId()); */
			rowData.add("" + itemWiseReportList.get(i).getItemName());
			rowData.add("" + itemWiseReportList.get(i).getRate());

			rowData.add("" + itemWiseReportList.get(i).getQty());

			rowData.add("" + itemWiseReportList.get(i).getTotal());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			totalQty = totalQty + itemWiseReportList.get(i).getQty();
			totalAmt = totalAmt + itemWiseReportList.get(i).getTotal();

		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();

		rowData.add("");
		rowData.add("Total");
		rowData.add("");
		rowData.add("");

		rowData.add("" + roundUp(totalQty));
		rowData.add("" + roundUp(totalAmt));

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelListNew", exportToExcelList);
		session.setAttribute("excelNameNew", "ItemWisePurchaseReport");
		session.setAttribute("reportNameNew", "Itemwise Purchase Report");
		session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
		session.setAttribute("mergeUpto1", "$A$1:$L$1");
		session.setAttribute("mergeUpto2", "$A$2:$L$2");

		return itemWiseReportList;

	}

	// ---------------------------------------------------------------------------------------------------------------------

	// ----------------------------------Bill Wise Tax
	// Report---------------------------------------------
	@RequestMapping(value = "/findBillWiseTaxReport", method = RequestMethod.GET)
	public @ResponseBody List<BillWiseTaxReport> getBillWiseTaxReport(HttpServletRequest request,
			HttpServletResponse response) {

		String fromDate = "";
		String toDate = "";

		try {
			System.out.println("in method");
			fromDate = request.getParameter("fromDate");
			System.out.println("fromDate" + fromDate);

			toDate = request.getParameter("toDate");
			System.out.println("toDate" + toDate);

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			System.out.println("frId" + frId);

			map.add("frId", frId);
			map.add("fromDate", Main.formatDate(fromDate));
			map.add("toDate", Main.formatDate(toDate));

			billWiseTaxReport = new ArrayList<BillWiseTaxReport>();

			BillWiseTaxReportList billWiseTaxReportList = restTemplate
					.postForObject(Constant.URL + "/showBillWiseTaxReport", map, BillWiseTaxReportList.class);

			billWiseTaxReport = billWiseTaxReportList.getBillWiseTaxReportList();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("billWiseTaxReport: " + billWiseTaxReport.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No.");
		rowData.add("Party Name");
		rowData.add("Taxable Amount");
		rowData.add("Tax Rate");
		rowData.add("CGST");
		rowData.add("SGST");
		rowData.add("IGST");
		rowData.add("CESS");
		rowData.add("Grand Amount");
		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		float taxableAmt = 0.0f;
		float cgstSum = 0.0f;
		float sgstSum = 0.0f;
		float igstSum = 0.0f;
		float totalCess = 0.0f;
		float grandTotal = 0.0f;

		for (int i = 0; i < billWiseTaxReport.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("" + (i + 1));
			rowData.add(Constant.FACTORYNAME);
			rowData.add("" + roundUp(billWiseTaxReport.get(i).getTaxableAmt()));
			rowData.add("" + roundUp(billWiseTaxReport.get(i).getTaxRate()));
			rowData.add("" + roundUp(billWiseTaxReport.get(i).getCgstRs()));
			rowData.add("" + roundUp(billWiseTaxReport.get(i).getSgstRs()));
			rowData.add("" + roundUp(billWiseTaxReport.get(i).getIgstRs()));
			rowData.add("" + roundUp(billWiseTaxReport.get(i).getCess()));
			rowData.add("" + roundUp(billWiseTaxReport.get(i).getGrandTotal()));

			taxableAmt = taxableAmt + billWiseTaxReport.get(i).getTaxableAmt();
			cgstSum = cgstSum + billWiseTaxReport.get(i).getCgstRs();
			sgstSum = sgstSum + billWiseTaxReport.get(i).getSgstRs();
			igstSum = igstSum + billWiseTaxReport.get(i).getIgstRs();
			totalCess = totalCess + billWiseTaxReport.get(i).getCess();
			grandTotal = grandTotal + billWiseTaxReport.get(i).getGrandTotal();

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();

		rowData.add("");
		rowData.add("Total");

		rowData.add("" + roundUp(taxableAmt));
		rowData.add("");
		rowData.add("" + roundUp(cgstSum));
		rowData.add("" + roundUp(sgstSum));
		rowData.add("" + roundUp(igstSum));
		rowData.add("" + roundUp(totalCess));
		rowData.add("" + roundUp(grandTotal));

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelListNew", exportToExcelList);
		session.setAttribute("excelNameNew", "BillWiseTaxPurchaseReport");
		session.setAttribute("reportNameNew", "Purchase Billwise Tax (Input) Report");
		session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
		session.setAttribute("mergeUpto1", "$A$1:$L$1");
		session.setAttribute("mergeUpto2", "$A$2:$L$2");

		return billWiseTaxReport;

	}

	// ---------------------------------------------------------------------------------------------------------------------
	// ----------------------------------Bill Wise Tax
	// Report---------------------------------------------
	@RequestMapping(value = "/getMonthWisePurchaseReport", method = RequestMethod.GET)
	public @ResponseBody List<MonthWiseReport> getMonthWiseReport(HttpServletRequest request,
			HttpServletResponse response) {
		String frName = "";

		String fromDate = "";
		String toDate = "";
		try {
			System.out.println("in method");
			fromDate = request.getParameter("fromDate");
			System.out.println("fromDate" + fromDate);

			toDate = request.getParameter("toDate");
			System.out.println("toDate" + toDate);

			DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-uuuu");
			YearMonth ym = YearMonth.parse(fromDate, f);

			LocalDate fDate = ym.atDay(1);
			System.out.println("fdate" + fDate);

			YearMonth ym1 = YearMonth.parse(toDate, f);
			LocalDate tDate = ym1.atEndOfMonth();
			System.out.println("tdate" + tDate);

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			frName = frDetails.getFrName();
			System.out.println("frId" + frId);

			map.add("frId", frId);
			map.add("fromDate", "" + fDate);
			map.add("toDate", "" + tDate);

			monthWiseReportList = new ArrayList<MonthWiseReport>();

			MonthWiseReportList monthWiseReportListBean = restTemplate
					.postForObject(Constant.URL + "/showMonthWiseReport", map, MonthWiseReportList.class);

			monthWiseReportList = monthWiseReportListBean.getMonthWiseReportList();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("monthWiseReportList: " + monthWiseReportList.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Month");
		rowData.add("Franchisee Name");
		rowData.add("Taxable Amount");
		rowData.add("cgst");

		rowData.add("sgst");
		rowData.add("igst");

		rowData.add("cess");
		/* rowData.add("Round Off"); */
		rowData.add("Grand Amount");

		float taxableAmt = 0.0f;
		float cgstSum = 0.0f;
		float sgstSum = 0.0f;
		float igstSum = 0.0f;
		float totalSess = 0.0f;
		float grandTotal = 0.0f;

		String[] monthNames = { "0", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
				"Dec" };

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < monthWiseReportList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			/* rowData.add("" + monthWiseReportList.get(i).getBillNo()); */
			rowData.add("" + (i + 1));
			rowData.add("" + monthWiseReportList.get(i).getMonth());
			rowData.add("" + frName);
			rowData.add("" + roundUp(monthWiseReportList.get(i).getTaxableAmt()));
			rowData.add("" + roundUp(monthWiseReportList.get(i).getCgstRs()));
			rowData.add("" + roundUp(monthWiseReportList.get(i).getSgstRs()));

			rowData.add("" + roundUp(monthWiseReportList.get(i).getIgstRs()));

			rowData.add("" + roundUp(monthWiseReportList.get(i).getSess()));

			/* rowData.add("" + monthWiseReportList.get(i).getRoundOff()); */

			rowData.add("" + roundUp(monthWiseReportList.get(i).getGrandTotal()));

			taxableAmt = taxableAmt + monthWiseReportList.get(i).getTaxableAmt();
			cgstSum = cgstSum + monthWiseReportList.get(i).getCgstRs();
			sgstSum = sgstSum + monthWiseReportList.get(i).getSgstRs();
			igstSum = igstSum + monthWiseReportList.get(i).getIgstRs();
			totalSess = totalSess + monthWiseReportList.get(i).getSess();
			grandTotal = grandTotal + monthWiseReportList.get(i).getGrandTotal();

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();

		rowData.add("");
		rowData.add("Total");
		rowData.add("");

		rowData.add("" + roundUp(taxableAmt));
		rowData.add("" + roundUp(cgstSum));
		rowData.add("" + roundUp(sgstSum));
		rowData.add("" + roundUp(igstSum));
		rowData.add("" + roundUp(totalSess));
		rowData.add("" + roundUp(grandTotal));

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelListNew", exportToExcelList);
		session.setAttribute("excelNameNew", "MonthWiseTaxPurchaseReport");
		session.setAttribute("reportNameNew", "Monthwise Purchase Report");
		session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
		session.setAttribute("mergeUpto1", "$A$1:$I$1");
		session.setAttribute("mergeUpto2", "$A$2:$I$2");

		return monthWiseReportList;

	}

	// ---------------------------------------------------------------------------------------------------------------------
	// Sell Report Start

	@RequestMapping(value = "/viewBillwiseSell", method = RequestMethod.GET)
	public ModelAndView viewBillwiseSell(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/repFrSellBillwiseSell");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getBilwiselReport", method = RequestMethod.GET)
	public @ResponseBody List<GetSellBillHeader> getSellBillHeader(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			System.out.println("in method");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);

			getSellBillHeaderList = new ArrayList<GetSellBillHeader>();

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

		// export to excel

		/*
		 * Collections.sort(getSellBillHeaderList, new Comparator<GetSellBillHeader>() {
		 * public int compare(GetSellBillHeader c1, GetSellBillHeader c2) {
		 * 
		 * String[] ar1=c1.getInvoiceNo().split("-"); int a=Integer.parseInt(ar1[1]);
		 * 
		 * String[] ar2=c2.getInvoiceNo().split("-"); int b=Integer.parseInt(ar2[1]);
		 * 
		 * if (a > b) return 1; if (a < b) return -1; return 0; }});
		 */

		Collections.sort(getSellBillHeaderList, new Comparator<GetSellBillHeader>() {
			public int compare(GetSellBillHeader c1, GetSellBillHeader c2) {

				String s1 = c1.getInvoiceNo();
				String s2 = c2.getInvoiceNo();
				return s1.compareToIgnoreCase(s2);
			}
		});

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No");
		rowData.add("Invoice No");
		rowData.add("Franchisee Name");
		rowData.add("Bill Date");
		rowData.add("Discount Per");
		rowData.add("Taxable Amt");
		rowData.add("Total Tax");
		rowData.add("Grand Total");
		rowData.add("Payable Amt");
		rowData.add("Paid Amt");
		rowData.add("Remaining Amt");

		rowData.add("Payment Mode");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getSellBillHeaderList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("" + (i + 1));
			rowData.add("" + getSellBillHeaderList.get(i).getInvoiceNo());
			rowData.add("" + getSellBillHeaderList.get(i).getFrName());
			rowData.add("" + getSellBillHeaderList.get(i).getBillDate());
			rowData.add("" + getSellBillHeaderList.get(i).getDiscountPer());
			rowData.add("" + roundUp(getSellBillHeaderList.get(i).getTaxableAmt()));
			rowData.add("" + roundUp(getSellBillHeaderList.get(i).getTotalTax()));
			rowData.add("" + roundUp(getSellBillHeaderList.get(i).getGrandTotal()));
			rowData.add("" + roundUp(getSellBillHeaderList.get(i).getPayableAmt()));
			rowData.add("" + roundUp(getSellBillHeaderList.get(i).getPaidAmt()));
			rowData.add("" + roundUp(getSellBillHeaderList.get(i).getRemainingAmt()));

			if (getSellBillHeaderList.get(i).getPaymentMode() == 1) {
				rowData.add("Cash");

			} else if (getSellBillHeaderList.get(i).getPaymentMode() == 2) {
				rowData.add("Card");

			} else if (getSellBillHeaderList.get(i).getPaymentMode() == 3) {
				rowData.add("other");
			}

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "BillWiseSell");

		return getSellBillHeaderList;

	}

	@RequestMapping(value = "/viewDatewiseSellBill", method = RequestMethod.GET)
	public ModelAndView viewDatewiseSellBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/repFrSelldatewise");

		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getDatewiselReport", method = RequestMethod.GET)
	public @ResponseBody List<GetRepFrDatewiseSellResponse> getDatewiseSellBill(HttpServletRequest request,
			HttpServletResponse response) {
		System.out.println("in method");

		List<GetRepFrDatewiseSellResponse> tempList = null;
		try {
			System.out.println("in method");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			System.out.println("toDate" + toDate);

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);

			getRepFrDatewiseSellResponse = new ArrayList<GetRepFrDatewiseSellResponse>();

			ParameterizedTypeReference<List<GetRepFrDatewiseSellResponse>> typeRef = new ParameterizedTypeReference<List<GetRepFrDatewiseSellResponse>>() {
			};
			ResponseEntity<List<GetRepFrDatewiseSellResponse>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepDatewiseSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepFrDatewiseSellResponse = responseEntity.getBody();

			/*
			 * LinkedHashMap<String, GetRepFrDatewiseSellResponse> hashList = new
			 * LinkedHashMap<String, GetRepFrDatewiseSellResponse>();
			 * 
			 * for (int i = 0; i < getRepFrDatewiseSellResponse.size(); i++) { float cash =
			 * 0, card = 0, other = 0;
			 * 
			 * if (hashList.containsKey(getRepFrDatewiseSellResponse.get(i).getBillDate())
			 * == false) {
			 * 
			 * for (int j = 0; j < getRepFrDatewiseSellResponse.size(); j++) {
			 * 
			 * if (getRepFrDatewiseSellResponse.get(j).getBillDate()
			 * .equals(getRepFrDatewiseSellResponse.get(i).getBillDate())) { cash = cash +
			 * getRepFrDatewiseSellResponse.get(j).getCash(); card = card +
			 * getRepFrDatewiseSellResponse.get(j).getCard(); other = other +
			 * getRepFrDatewiseSellResponse.get(j).getOther(); } }
			 * 
			 * //System.err.println(getRepFrDatewiseSellResponse.get(i).getBillDate() +
			 * " cash " + cash + "card " //+ card + "other " + other);
			 * getRepFrDatewiseSellResponse.get(i).setCash(cash);
			 * getRepFrDatewiseSellResponse.get(i).setCard(card);
			 * getRepFrDatewiseSellResponse.get(i).setOther(other);
			 * hashList.put(getRepFrDatewiseSellResponse.get(i).getBillDate(),
			 * getRepFrDatewiseSellResponse.get(i));
			 * 
			 * } }
			 * 
			 * tempList = new ArrayList<GetRepFrDatewiseSellResponse>(hashList.values());
			 */

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("Sell Bill Header " + getRepFrDatewiseSellResponse.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		/*
		 * rowData.add("Sell Bill No"); rowData.add("Franchise Id");
		 */
		rowData.add("Sr. No.");
		rowData.add("Franchise Name");
		rowData.add("Day");
		rowData.add("Bill Date");
		rowData.add("Total Day Sale");
		rowData.add("Cash");
		rowData.add("Card");
		/* rowData.add("Other"); */

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getRepFrDatewiseSellResponse.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			/*
			 * rowData.add(""+getRepFrDatewiseSellResponse.get(i).getSellBillNo());
			 * rowData.add(""+getRepFrDatewiseSellResponse.get(i).getFrId());
			 */
			rowData.add("" + (i + 1));
			rowData.add("" + getRepFrDatewiseSellResponse.get(i).getFrName());

			rowData.add("" + getRepFrDatewiseSellResponse.get(i).getDay());
			rowData.add("" + getRepFrDatewiseSellResponse.get(i).getBillDate());
			float totalAmt = getRepFrDatewiseSellResponse.get(i).getCash()
					+ getRepFrDatewiseSellResponse.get(i).getCard() + getRepFrDatewiseSellResponse.get(i).getOther();
			rowData.add("" + totalAmt);
			rowData.add("" + getRepFrDatewiseSellResponse.get(i).getCash());
			rowData.add("" + getRepFrDatewiseSellResponse.get(i).getCard());
			/* rowData.add(""+getRepFrDatewiseSellResponse.get(i).getOther()); */

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "DateWiseSell");

		return getRepFrDatewiseSellResponse;

	}

	@RequestMapping(value = "/viewMonthwiseSellBill", method = RequestMethod.GET)
	public ModelAndView viewMonthwiseSellBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/repFrMonthwiseSell");
		try {
			Calendar now = Calendar.getInstance();
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			String toyear = "2018";
			String fromyear = "2018";

			if ((now.get(Calendar.MONTH) + 1) > 3) {
				fromyear = "" + (now.get(Calendar.YEAR));
				toyear = "" + (now.get(Calendar.YEAR) + 1);

			} else {
				fromyear = "" + (now.get(Calendar.YEAR) - 1);
				toyear = "" + (now.get(Calendar.YEAR));
			}

			model.addObject("frommonth", "03-" + fromyear);
			model.addObject("tomonth", "04-" + toyear);
			model.addObject("frId", frDetails.getFrId());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getMonthwiselReport", method = RequestMethod.GET)
	public @ResponseBody List<GetRepFrDatewiseSellResponse> getMonthwiseSellBill(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			System.out.println("in method");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-uuuu");
			YearMonth ym = YearMonth.parse(fromDate, f);

			LocalDate fDate = ym.atDay(1);
			System.out.println("fdate" + fDate);

			YearMonth ym1 = YearMonth.parse(toDate, f);
			LocalDate tDate = ym1.atEndOfMonth();
			System.out.println("tdate" + tDate);

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("frId", frId);

			map.add("fromDate", "" + fDate);
			map.add("toDate", "" + tDate);

			getRepFrDatewiseSellResponse = new ArrayList<GetRepFrDatewiseSellResponse>();

			ParameterizedTypeReference<List<GetRepFrDatewiseSellResponse>> typeRef = new ParameterizedTypeReference<List<GetRepFrDatewiseSellResponse>>() {
			};
			ResponseEntity<List<GetRepFrDatewiseSellResponse>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepMonthwiseSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepFrDatewiseSellResponse = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("Sell Bill Header " + getRepFrDatewiseSellResponse.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();
		rowData.add("Sr. No");
		/*
		 * rowData.add("Sale Bill No"); rowData.add("Franchise Id");
		 */
		rowData.add("Franchise Name");

		rowData.add("Month");
		rowData.add("Total Month Sale");
		rowData.add("Cash");
		rowData.add("Card");
		rowData.add("Other");

		String[] monthNames = { "0", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
				"Dec" };

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getRepFrDatewiseSellResponse.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("" + (i + 1));
			/*
			 * rowData.add("" + getRepFrDatewiseSellResponse.get(i).getSellBillNo());
			 * rowData.add("" + getRepFrDatewiseSellResponse.get(i).getFrId());
			 */
			rowData.add("" + getRepFrDatewiseSellResponse.get(i).getFrName());

			rowData.add("" + getRepFrDatewiseSellResponse.get(i).getMonth());
			float totalAmt = getRepFrDatewiseSellResponse.get(i).getCash()
					+ getRepFrDatewiseSellResponse.get(i).getCard() + getRepFrDatewiseSellResponse.get(i).getOther();
			rowData.add("" + totalAmt);
			rowData.add("" + getRepFrDatewiseSellResponse.get(i).getCash());
			rowData.add("" + getRepFrDatewiseSellResponse.get(i).getCard());
			rowData.add("" + getRepFrDatewiseSellResponse.get(i).getOther());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "MonthWiseSell");

		return getRepFrDatewiseSellResponse;

	}

	@RequestMapping(value = "/viewItemwiseSellBill", method = RequestMethod.GET)
	// @RequestMapping(value = "/viewChart", method = RequestMethod.GET)
	public ModelAndView viewItemwiseSellBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/repFrItemwiseSell");
		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
		model.addObject("frId", frDetails.getFrId());
		/*
		 * List<MCategory> mCategoryList; RestTemplate restTemplate = new
		 * RestTemplate();
		 * 
		 * CategoryList categoryList = restTemplate.getForObject(Constant.URL +
		 * "showAllCategory", CategoryList.class);
		 * 
		 * mCategoryList = categoryList.getmCategoryList();
		 * 
		 * System.out.println("Category list  " +mCategoryList); List<MCategory>
		 * newMcategoryList=new ArrayList<MCategory>(); for(int
		 * i=0;i<mCategoryList.size();i++) { if(mCategoryList.get(i).getCatId()!=5) {
		 * newMcategoryList.add(mCategoryList.get(i)); } }
		 * System.out.println("New Category list  " +newMcategoryList);
		 * model.addObject("unSelectedCatList", newMcategoryList);
		 */
		return model;
	}

	@RequestMapping(value = "/getMenuwiselReport", method = RequestMethod.GET)
	public @ResponseBody List<GetRepMenuwiseSellResponse> getMenuwisellReport(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			System.out.println("in method");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			// String catId=request.getParameter("category");

			/*
			 * catId = catId.substring(1, catId.length() - 1); catId =
			 * catId.replaceAll("\"", "");
			 */

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);

			getRepFrMenuwiseSellResponseList = new ArrayList<GetRepMenuwiseSellResponse>();

			ParameterizedTypeReference<List<GetRepMenuwiseSellResponse>> typeRef = new ParameterizedTypeReference<List<GetRepMenuwiseSellResponse>>() {
			};
			ResponseEntity<List<GetRepMenuwiseSellResponse>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepMenuwiseSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepFrMenuwiseSellResponseList = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("Sell Bill Header " + getRepFrMenuwiseSellResponseList.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr.No.");
		rowData.add("Franchise Name");
		rowData.add("Cat Name");
		rowData.add("Quantity");
		rowData.add("Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getRepFrMenuwiseSellResponseList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("" + (i + 1));
			rowData.add("" + getRepFrMenuwiseSellResponseList.get(i).getFrName());

			rowData.add("" + getRepFrMenuwiseSellResponseList.get(i).getCatName());
			rowData.add("" + getRepFrMenuwiseSellResponseList.get(i).getQty());
			rowData.add("" + getRepFrMenuwiseSellResponseList.get(i).getAmount());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "MenuWiseSummarySell");

		return getRepFrMenuwiseSellResponseList;

	}

	@RequestMapping(value = "/getItemwiselReport", method = RequestMethod.GET)
	public @ResponseBody List<GetRepFrItemwiseSellResponse> getItemwisellReport(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			System.out.println("in method");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String catId = request.getParameter("category");

			/*
			 * catId = catId.substring(1, catId.length() - 1); catId =
			 * catId.replaceAll("\"", "");
			 */

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("catId", catId);
			System.out.println(catId);
			getRepFrItemwiseSellResponseList = new ArrayList<GetRepFrItemwiseSellResponse>();

			ParameterizedTypeReference<List<GetRepFrItemwiseSellResponse>> typeRef = new ParameterizedTypeReference<List<GetRepFrItemwiseSellResponse>>() {
			};
			ResponseEntity<List<GetRepFrItemwiseSellResponse>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepItemwiseSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepFrItemwiseSellResponseList = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("Sell Bill Header " + getRepFrItemwiseSellResponseList.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();
		rowData.add("Sr No.");
		/*
		 * rowData.add("Sell Bill No");
		 *//*
			 * rowData.add("Franchise Id");
			 */ rowData.add("Franchise Name");
		/*
		 * rowData.add("Bill Date");
		 *//*
			 * rowData.add("Item Id");
			 */
		rowData.add("Item Name");

		/*
		 * rowData.add("Cat Id");
		 */
		rowData.add("Cat Name");
		rowData.add("Quantity");
		rowData.add("Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getRepFrItemwiseSellResponseList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("" + (i + 1));
			/*
			 * rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getSellBillNo());
			 * rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getFrId());
			 */
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getFrName());
			/*
			 * rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getBillDate());
			 */
			/*
			 * rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getItemId());
			 */ rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getItemName());
			/*
			 * rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getCatId());
			 */ rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getCatName());
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getQty());
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getAmount());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "ItemWiseSummarySell");

		return getRepFrItemwiseSellResponseList;

	}

	@RequestMapping(value = "/viewDateItemwiseSellBill", method = RequestMethod.GET)
	public ModelAndView viewDateItemwiseSellBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/repFrDateItemwiseSell");
		try {
			List<MCategory> mCategoryList;
			RestTemplate restTemplate = new RestTemplate();

			CategoryList categoryList = restTemplate.getForObject(Constant.URL + "showAllCategory", CategoryList.class);

			mCategoryList = categoryList.getmCategoryList();

			System.out.println("Category list  " + mCategoryList);
			List<MCategory> newMcategoryList = new ArrayList<MCategory>();
			for (int i = 0; i < mCategoryList.size(); i++) {
				// if (mCategoryList.get(i).getCatId() != 5) {
				newMcategoryList.add(mCategoryList.get(i));
				// }
			}
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
			System.out.println("New Category list  " + newMcategoryList);
			model.addObject("unSelectedCatList", newMcategoryList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getDateItemwiselReport", method = RequestMethod.GET)
	public @ResponseBody List<GetRepFrItemwiseSellResponse> getDateItemwiselReport(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			System.out.println("in method");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String catId = request.getParameter("category");

			/*
			 * catId = catId.substring(1, catId.length() - 1); catId =
			 * catId.replaceAll("\"", "");
			 */

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("catId", catId);
			System.out.println(catId);
			getRepFrItemwiseSellResponseList = new ArrayList<GetRepFrItemwiseSellResponse>();

			ParameterizedTypeReference<List<GetRepFrItemwiseSellResponse>> typeRef = new ParameterizedTypeReference<List<GetRepFrItemwiseSellResponse>>() {
			};
			ResponseEntity<List<GetRepFrItemwiseSellResponse>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepDateItemwiseSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepFrItemwiseSellResponseList = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("Sell Bill Header " + getRepFrItemwiseSellResponseList.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();
		rowData.add("Sr.No");
		/* rowData.add("Sell Bill No"); */
		/* rowData.add("Franchise Id"); */
		rowData.add("Franchise Name");
		rowData.add("Bill Date");
		/* rowData.add("Item Id"); */

		rowData.add("Item Name");

		/* rowData.add("Cat Id"); */

		rowData.add("Cat Name");
		rowData.add("Quantity");
		rowData.add("Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getRepFrItemwiseSellResponseList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("" + (i + 1));
			/* rowData.add(""+getRepFrItemwiseSellResponseList.get(i).getSellBillNo()); */
			/* rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getFrId()); */
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getFrName());
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getBillDate());

			/* rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getItemId()); */
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getItemName());
			/* rowData.add(""+getRepFrItemwiseSellResponseList.get(i).getCatId()); */
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getCatName());
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getQty());
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getAmount());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "ItemWiseReportSell");

		return getRepFrItemwiseSellResponseList;

	}

	@RequestMapping(value = "/getDateCatwisellReport", method = RequestMethod.GET)
	public @ResponseBody List<GetRepFrItemwiseSellResponse> getDateCatwiselReport(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			System.out.println("in method");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String catId = request.getParameter("category");

			/*
			 * catId = catId.substring(1, catId.length() - 1); catId =
			 * catId.replaceAll("\"", "");
			 */

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("catId", catId);
			System.out.println(catId);
			getRepFrItemwiseSellResponseList = new ArrayList<GetRepFrItemwiseSellResponse>();

			ParameterizedTypeReference<List<GetRepFrItemwiseSellResponse>> typeRef = new ParameterizedTypeReference<List<GetRepFrItemwiseSellResponse>>() {
			};
			ResponseEntity<List<GetRepFrItemwiseSellResponse>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepDateCatwiseSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepFrItemwiseSellResponseList = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("Sell Bill Header " + getRepFrItemwiseSellResponseList.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sell Bill No");
		/* rowData.add("Franchise Id"); */
		rowData.add("Franchise Name");
		rowData.add("Bill Date");
		/* rowData.add("Item Id"); */

		rowData.add("Item Name");

		/* rowData.add("Cat Id"); */

		rowData.add("Cat Name");
		rowData.add("Quantity");
		rowData.add("Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getRepFrItemwiseSellResponseList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getSellBillNo());
			/* rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getFrId()); */
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getFrName());
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getBillDate());

			/* rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getItemId()); */
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getItemName());
			/* rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getCatId()); */
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getCatName());
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getQty());
			rowData.add("" + getRepFrItemwiseSellResponseList.get(i).getAmount());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "ItemWiseReportSell");

		return getRepFrItemwiseSellResponseList;

	}

	@RequestMapping(value = "/viewFrTaxSellBill", method = RequestMethod.GET)
	public ModelAndView viewFrTaxSellBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/repFrTaxSell");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getTaxSellReport", method = RequestMethod.GET)
	public @ResponseBody List<GetRepTaxSell> getFrTaxSellBill(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			System.out.println("in method");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			getRepTaxSell = new ArrayList<GetRepTaxSell>();
			getRepTaxSell = new ArrayList<GetRepTaxSell>();

			ParameterizedTypeReference<List<GetRepTaxSell>> typeRef = new ParameterizedTypeReference<List<GetRepTaxSell>>() {
			};
			ResponseEntity<List<GetRepTaxSell>> responseEntity = restTemplate.exchange(Constant.URL + "getRepTaxSell",
					HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepTaxSell = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("Sell Bill Header " + getRepTaxSell.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();
		rowData.add("Sr. No.");
		/*
		 * rowData.add("Sell Bill No"); rowData.add("Franchise Id");
		 */
		rowData.add("Franchise Name");
		/* rowData.add("Bill Date"); */
		rowData.add("Tax %");
		rowData.add("CGST");
		rowData.add("SGST");

		rowData.add("IGST");

		rowData.add("CESS");
		/* rowData.add("GST"); */
		rowData.add("Tax Amount");
		rowData.add("Bill Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getRepTaxSell.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("" + (i + 1));
			/*
			 * rowData.add("" + getRepTaxSell.get(i).getSellBillNo()); rowData.add("" +
			 * getRepTaxSell.get(i).getFrId());
			 */
			rowData.add("" + getRepTaxSell.get(i).getFrName());
			/*
			 * rowData.add("" + getRepTaxSell.get(i).getBillDate());
			 */
			rowData.add("" + roundUp(getRepTaxSell.get(i).getTax_per()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getCgst()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getSgst()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getIgst()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getCess()));
			/* rowData.add("" + getRepTaxSell.get(i).getGstn()); */
			rowData.add("" + roundUp(getRepTaxSell.get(i).getTax_amount()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getBill_amount()));

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "TaxReportSell");

		return getRepTaxSell;

	}

	@RequestMapping(value = "/viewFrBillwiseTaxSellBill", method = RequestMethod.GET)
	public ModelAndView viewFrBillwiseTaxSellBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/repFrBillwiseTaxSell");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getBillwiseTaxSellReport", method = RequestMethod.GET)
	public @ResponseBody List<GetRepTaxSell> getFrBillwiseTaxSellBill(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			System.out.println("in method");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			// getFrGrnDetail
			System.out.println(frId + fromDate + toDate);
			getRepTaxSell = new ArrayList<GetRepTaxSell>();

			ParameterizedTypeReference<List<GetRepTaxSell>> typeRef = new ParameterizedTypeReference<List<GetRepTaxSell>>() {
			};
			ResponseEntity<List<GetRepTaxSell>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepBillwiseTaxSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepTaxSell = responseEntity.getBody();

			Collections.sort(getRepTaxSell, new Comparator<GetRepTaxSell>() {
				public int compare(GetRepTaxSell c1, GetRepTaxSell c2) {

					String s1 = c1.getSellBillNo();
					String s2 = c2.getSellBillNo();
					return s1.compareToIgnoreCase(s2);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("Sell Bill Header " + getRepTaxSell.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();
		rowData.add("Sr.No");
		rowData.add("Sell Bill No");
		// rowData.add("Franchise Id");
		rowData.add("Franchise Name");
		rowData.add("Bill Date");
		rowData.add("Tax %");
		rowData.add("cgst");
		rowData.add("sgst");

		rowData.add("igst");

		rowData.add("cess");
		rowData.add("Gst");
		rowData.add("Tax Amount");
		rowData.add("Bill Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getRepTaxSell.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("" + (i + 1));
			rowData.add("" + getRepTaxSell.get(i).getSellBillNo());
			// rowData.add("" + getRepTaxSell.get(i).getFrId());
			rowData.add("" + getRepTaxSell.get(i).getFrName());
			rowData.add("" + getRepTaxSell.get(i).getBillDate());

			rowData.add("" + roundUp(getRepTaxSell.get(i).getTax_per()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getCgst()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getSgst()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getIgst()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getCess()));
			rowData.add("" + getRepTaxSell.get(i).getGstn());
			rowData.add("" + roundUp(getRepTaxSell.get(i).getTax_amount()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getBill_amount()));

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "BillWiseSellTaxReport");

		return getRepTaxSell;

	}

	@RequestMapping(value = "/viewFrDatewiseTaxSellBill", method = RequestMethod.GET)
	public ModelAndView viewFrDatewiseTaxSellBill(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/repFrDatewiseTaxSell");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getDatewiseTaxSellReport", method = RequestMethod.GET)
	public @ResponseBody List<GetRepTaxSell> getFrDatewiseTaxSellBill(HttpServletRequest request,
			HttpServletResponse response) {

		try {
			System.out.println("in method");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			getRepTaxSell = new ArrayList<GetRepTaxSell>();

			ParameterizedTypeReference<List<GetRepTaxSell>> typeRef = new ParameterizedTypeReference<List<GetRepTaxSell>>() {
			};
			ResponseEntity<List<GetRepTaxSell>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepDatewiseTaxSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepTaxSell = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		System.out.println("Sell Bill Header " + getRepTaxSell.toString());

		// export to excel

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr.No");
		// rowData.add("Franchise Id");
		rowData.add("Franchise Name");
		rowData.add("Bill Date");
		rowData.add("Tax %");
		rowData.add("cgst");
		rowData.add("sgst");

		rowData.add("igst");

		rowData.add("cess");
		rowData.add("Gst");
		rowData.add("Tax Amount");
		rowData.add("Bill Amount");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getRepTaxSell.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("" + (i + 1));
			// rowData.add("" + getRepTaxSell.get(i).getFrId());
			rowData.add("" + getRepTaxSell.get(i).getFrName());
			rowData.add("" + getRepTaxSell.get(i).getBillDate());

			rowData.add("" + roundUp(getRepTaxSell.get(i).getTax_per()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getCgst()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getSgst()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getIgst()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getCess()));
			rowData.add("" + getRepTaxSell.get(i).getGstn());
			rowData.add("" + roundUp(getRepTaxSell.get(i).getTax_amount()));
			rowData.add("" + roundUp(getRepTaxSell.get(i).getBill_amount()));

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "DateWiseSellTaxReport");

		return getRepTaxSell;

	}

	// Sell report End
	/*
	 * @RequestMapping(value = "/view1Chart", method = RequestMethod.GET) public
	 * ModelAndView viewChart(HttpServletRequest request, HttpServletResponse
	 * response) {
	 * 
	 * ModelAndView model = new ModelAndView("report/sellReport/datewiseChart");
	 * model.addObject("msg", "hhhh"); return model; }
	 */

	// Report PDF Begins

	@RequestMapping(value = "pdf/showSellDatewiseReportpPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView showSellDatewiseReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/repFrSellDatewisePdf");
		RestTemplate restTemplate = new RestTemplate();
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);

			getRepFrDatewiseSellResponse = new ArrayList<GetRepFrDatewiseSellResponse>();

			ParameterizedTypeReference<List<GetRepFrDatewiseSellResponse>> typeRef = new ParameterizedTypeReference<List<GetRepFrDatewiseSellResponse>>() {
			};
			ResponseEntity<List<GetRepFrDatewiseSellResponse>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepDatewiseSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepFrDatewiseSellResponse = responseEntity.getBody();

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = restTemplate.getForObject(Constant.URL + "getFranchisee?frId={frId}",
					Franchisee.class, frId);
			model.addObject("frName", franchisee.getFrName());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		model.addObject("fromDate", fromDate);
		model.addObject("toDate", toDate);

		model.addObject("reportList", getRepFrDatewiseSellResponse);
		return model;
	}

	@RequestMapping(value = "pdf/showSellMonthwiseReportpPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView showSellMonthwiseReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/repFrMonthwiseSellPdf");
		try {
			String month[] = { "0", "Jan", "Feb", "Mar", "April", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov",
					"Dec" };

			DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-uuuu");
			YearMonth ym = YearMonth.parse(fromDate, f);

			LocalDate fDate = ym.atDay(1);
			System.out.println("fdate" + fDate);

			YearMonth ym1 = YearMonth.parse(toDate, f);
			LocalDate tDate = ym1.atEndOfMonth();
			System.out.println("tdate" + tDate);

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);

			map.add("fromDate", "" + fDate);
			map.add("toDate", "" + tDate);

			getRepFrDatewiseSellResponse = new ArrayList<GetRepFrDatewiseSellResponse>();

			ParameterizedTypeReference<List<GetRepFrDatewiseSellResponse>> typeRef = new ParameterizedTypeReference<List<GetRepFrDatewiseSellResponse>>() {
			};
			ResponseEntity<List<GetRepFrDatewiseSellResponse>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepMonthwiseSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepFrDatewiseSellResponse = responseEntity.getBody();

			model.addObject("reportList", getRepFrDatewiseSellResponse);
			model.addObject("month", month);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = restTemplate.getForObject(Constant.URL + "getFranchisee?frId={frId}",
					Franchisee.class, frId);
			model.addObject("frName", franchisee.getFrName());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "pdf/showSellItemwiseReportpPdf/{fromDate}/{toDate}/{frId}/{catId}", method = RequestMethod.GET)
	public ModelAndView showSellItemwiseReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, @PathVariable int catId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/repFrItemwiseSellPdf");
		RestTemplate restTemplate = new RestTemplate();
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("catId", catId);
			getRepFrItemwiseSellResponseList = new ArrayList<GetRepFrItemwiseSellResponse>();

			ParameterizedTypeReference<List<GetRepFrItemwiseSellResponse>> typeRef = new ParameterizedTypeReference<List<GetRepFrItemwiseSellResponse>>() {
			};
			ResponseEntity<List<GetRepFrItemwiseSellResponse>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepItemwiseSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepFrItemwiseSellResponseList = responseEntity.getBody();

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = restTemplate.getForObject(Constant.URL + "getFranchisee?frId={frId}",
					Franchisee.class, frId);
			model.addObject("frName", franchisee.getFrName());
			model.addObject("reportList", getRepFrItemwiseSellResponseList);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "pdf/showSellDateItemwisewiseReportpPdf/{fromDate}/{toDate}/{frId}/{catId}", method = RequestMethod.GET)
	public ModelAndView showSellDateItemwisewiseReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, @PathVariable int catId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/repFrDateItemwiseSellPdf");
		RestTemplate restTemplate = new RestTemplate();
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			map.add("catId", catId);
			getRepFrItemwiseSellResponseList = new ArrayList<GetRepFrItemwiseSellResponse>();

			ParameterizedTypeReference<List<GetRepFrItemwiseSellResponse>> typeRef = new ParameterizedTypeReference<List<GetRepFrItemwiseSellResponse>>() {
			};
			ResponseEntity<List<GetRepFrItemwiseSellResponse>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepDateItemwiseSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepFrItemwiseSellResponseList = responseEntity.getBody();

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = restTemplate.getForObject(Constant.URL + "getFranchisee?frId={frId}",
					Franchisee.class, frId);
			model.addObject("frName", franchisee.getFrName());

			System.out.println("LList " + getRepFrItemwiseSellResponseList.toString());
			model.addObject("reportList", getRepFrItemwiseSellResponseList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "pdf/showSellBillwiseReportPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView showSellBillwiseReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BILL LIST pdf");

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/repFrSellBillwiseSellPdf");
		try {
			System.out.println("BILL LIST try");

			/*
			 * HttpSession ses = request.getSession(); Franchisee frDetails = (Franchisee)
			 * ses.getAttribute("frDetails");
			 */
			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			// int frId=frDetails.getFrId();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);

			getSellBillHeaderList = new ArrayList<GetSellBillHeader>();

			ParameterizedTypeReference<List<GetSellBillHeader>> typeRef = new ParameterizedTypeReference<List<GetSellBillHeader>>() {
			};
			ResponseEntity<List<GetSellBillHeader>> responseEntity = restTemplate
					.exchange(Constant.URL + "getSellBillHeader", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getSellBillHeaderList = responseEntity.getBody();
			System.out.println("BILL LIST" + getSellBillHeaderList.toString());

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = restTemplate.getForObject(Constant.URL + "getFranchisee?frId={frId}",
					Franchisee.class, frId);
			model.addObject("frName", franchisee.getFrName());

			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		model.addObject("reportList", getSellBillHeaderList);
		return model;
	}

	@RequestMapping(value = "pdf/showSellTaxReportpPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView showSellTaxReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/repFrTaxSellPdf");

		RestTemplate restTemplate = new RestTemplate();
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			getRepTaxSell = new ArrayList<GetRepTaxSell>();
			getRepTaxSell = new ArrayList<GetRepTaxSell>();

			ParameterizedTypeReference<List<GetRepTaxSell>> typeRef = new ParameterizedTypeReference<List<GetRepTaxSell>>() {
			};
			ResponseEntity<List<GetRepTaxSell>> responseEntity = restTemplate.exchange(Constant.URL + "getRepTaxSell",
					HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepTaxSell = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		model.addObject("reportList", getRepTaxSell);
		return model;
	}

	@RequestMapping(value = "pdf/showSellTaxDatewiseReportpPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView showSellTaxDatewiseReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/repFrDatewiseTaxSellPdf");
		RestTemplate restTemplate = new RestTemplate();
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("fromDate", fromDate);
			map.add("toDate", toDate);
			getRepTaxSell = new ArrayList<GetRepTaxSell>();

			ParameterizedTypeReference<List<GetRepTaxSell>> typeRef = new ParameterizedTypeReference<List<GetRepTaxSell>>() {
			};
			ResponseEntity<List<GetRepTaxSell>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepDatewiseTaxSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepTaxSell = responseEntity.getBody();

			model.addObject("reportList", getRepTaxSell);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "pdf/showSellTaxBillwiseReportpPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView showSellTaxBillwiseReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/repFrBillwiseTaxSellPdf");
		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("frId", frId);
		map.add("fromDate", fromDate);
		map.add("toDate", toDate);
		// getFrGrnDetail
		System.out.println(frId + fromDate + toDate);
		getRepTaxSell = new ArrayList<GetRepTaxSell>();
		try {

			ParameterizedTypeReference<List<GetRepTaxSell>> typeRef = new ParameterizedTypeReference<List<GetRepTaxSell>>() {
			};
			ResponseEntity<List<GetRepTaxSell>> responseEntity = restTemplate
					.exchange(Constant.URL + "getRepBillwiseTaxSell", HttpMethod.POST, new HttpEntity<>(map), typeRef);

			getRepTaxSell = responseEntity.getBody();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		model.addObject("reportList", getRepTaxSell);
		return model;
	}

	@RequestMapping(value = "pdf/showPurchaseTaxBillwiseReportPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView showPurchaseTaxBillwiseReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/purchaseReport/purchaseReportPdf/billWiseTaxReportPdf");
		try {
			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			map.add("fromDate", Main.formatDate(fromDate));
			map.add("toDate", Main.formatDate(toDate));

			billWiseTaxReport = new ArrayList<BillWiseTaxReport>();

			BillWiseTaxReportList billWiseTaxReportList = restTemplate
					.postForObject(Constant.URL + "/showBillWiseTaxReport", map, BillWiseTaxReportList.class);

			billWiseTaxReport = billWiseTaxReportList.getBillWiseTaxReportList();

			model.addObject("reportList", billWiseTaxReport);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "pdf/showPurchaseMonthwiseReportPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView showPurchaseMonthwiseReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/purchaseReport/purchaseReportPdf/monthWisePurchasePdf");
		try {
			String month[] = { "0", "Jan", "Feb", "Mar", "April", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov",
					"Dec" };
			DateTimeFormatter f = DateTimeFormatter.ofPattern("MM-uuuu");
			YearMonth ym = YearMonth.parse(fromDate, f);

			LocalDate fDate = ym.atDay(1);
			System.out.println("fdate" + fDate);

			YearMonth ym1 = YearMonth.parse(toDate, f);
			LocalDate tDate = ym1.atEndOfMonth();
			System.out.println("tdate" + tDate);

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			map.add("fromDate", "" + fDate);
			map.add("toDate", "" + tDate);

			monthWiseReportList = new ArrayList<MonthWiseReport>();

			MonthWiseReportList monthWiseReportListBean = restTemplate
					.postForObject(Constant.URL + "/showMonthWiseReport", map, MonthWiseReportList.class);

			monthWiseReportList = monthWiseReportListBean.getMonthWiseReportList();
			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = restTemplate.getForObject(Constant.URL + "getFranchisee?frId={frId}",
					Franchisee.class, frId);
			model.addObject("frName", franchisee.getFrName());

			model.addObject("month", month);
			model.addObject("reportList", monthWiseReportList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "pdf/showPurchaseItemwiseReportpPdf/{fromDate}/{toDate}/{frId}/{catId}", method = RequestMethod.GET)
	public ModelAndView showPurchaseItemwiseReportpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, @PathVariable int catId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/purchaseReport/purchaseReportPdf/itemWiseReportPdf");
		RestTemplate restTemplate = new RestTemplate();
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			map.add("fromDate", Main.formatDate(fromDate));
			map.add("toDate", Main.formatDate(toDate));
			map.add("catId", catId);

			itemWiseReportList = new ArrayList<ItemWiseReport>();

			ItemWiseReportList itemWiseList = restTemplate.postForObject(Constant.URL + "/showItemWiseReport", map,
					ItemWiseReportList.class);

			itemWiseReportList = itemWiseList.getItemWiseReportList();
			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = restTemplate.getForObject(Constant.URL + "getFranchisee?frId={frId}",
					Franchisee.class, frId);
			model.addObject("frName", franchisee.getFrName());

			model.addObject("reportList", itemWiseReportList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return model;
	}

	@RequestMapping(value = "pdf/showPurchaseItemwiseDetailPdf/{fromDate}/{toDate}/{frId}/{catId}", method = RequestMethod.GET)
	public ModelAndView showPurchaseItemwiseDetailpPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, @PathVariable int catId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/purchaseReport/purchaseReportPdf/itemWiseDetailPdf");
		try {
			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			map.add("fromDate", Main.formatDate(fromDate));
			map.add("toDate", Main.formatDate(toDate));
			map.add("catId", catId);

			itemWiseDetailReportList = new ArrayList<ItemWiseDetail>();

			ItemWiseDetailList itemWiseDetailList = restTemplate
					.postForObject(Constant.URL + "/showItemWiseDetailsReport", map, ItemWiseDetailList.class);

			itemWiseDetailReportList = itemWiseDetailList.getItemWiseDetailList();

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = restTemplate.getForObject(Constant.URL + "getFranchisee?frId={frId}",
					Franchisee.class, frId);
			model.addObject("frName", franchisee.getFrName());

			model.addObject("reportList", itemWiseDetailReportList);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return model;
	}

	@RequestMapping(value = "pdf/showPurchaseBillwiseReportPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView showPurchaseBillwiseReportPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/purchaseReport/purchaseReportPdf/billWisePurchaseReportPdf");

		RestTemplate restTemplate = new RestTemplate();

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		System.out.println("frId" + frId);

		map.add("frId", frId);
		map.add("fromDate", Main.formatDate(fromDate));
		map.add("toDate", Main.formatDate(toDate));

		billWisePurchaseReportList = new ArrayList<BillWisePurchaseReport>();
		try {
			BillWisePurchaseList billWisePurchaseList = restTemplate
					.postForObject(Constant.URL + "/showBillWisePurchaseReport", map, BillWisePurchaseList.class);

			billWisePurchaseReportList = billWisePurchaseList.getBillWisePurchaseList();

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = restTemplate.getForObject(Constant.URL + "getFranchisee?frId={frId}",
					Franchisee.class, frId);
			model.addObject("frName", franchisee.getFrName());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		model.addObject("fromDate", fromDate);
		model.addObject("toDate", toDate);
		model.addObject("reportList", billWisePurchaseReportList);
		return model;
	}

	/*
	 * @RequestMapping(value = "/pdfCustBill", method = RequestMethod.GET) public
	 * ModelAndView demoBill(HttpServletRequest request, HttpServletResponse
	 * response) { String sellBillNo=request.getParameter("billNo"); //String
	 * fr_Id=request.getParameter("frId"); int billNo=Integer.parseInt(sellBillNo);
	 * //int billNo=Integer.parseInt(fr_Id); //HttpSession ses =
	 * request.getSession(); //Franchisee frDetails = (Franchisee)
	 * ses.getAttribute("frDetails");
	 * 
	 * 
	 * ModelAndView model=new ModelAndView("report/franchCompInvoice");
	 * 
	 * RestTemplate rest=new RestTemplate(); MultiValueMap<String, Object> map=new
	 * LinkedMultiValueMap<String, Object>();
	 * 
	 * map.add("billNo", billNo); if(frGstType!=0) { model=new
	 * ModelAndView("report/franchTaxInvoice"); List<GetCustBillTax>
	 * getCustBilTaxList=rest.postForObject(Constant.URL+"getCustomerBillTax", map,
	 * List.class);
	 * 
	 * model.addObject("custBilltax", getCustBilTaxList); }
	 * 
	 * List<GetCustmoreBillResponse>
	 * getCustmoreBillResponseList=rest.postForObject(Constant.URL+
	 * "getCustomerBill", map, List.class);
	 * 
	 * System.out.println("Custmore Bill : "+getCustmoreBillResponseList.toString())
	 * ;
	 * 
	 * model.addObject("billList", getCustmoreBillResponseList); return model; }
	 */

	// --------------------------DAILY SALES
	// REPORT--30APR2019------------------------------
	@RequestMapping(value = "/viewDailySalesReport", method = RequestMethod.GET)
	public ModelAndView viewSalesReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReport");
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			model.addObject("frId", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "/getDailySalesReport/{date}", method = RequestMethod.GET)
	public ModelAndView getDailySalesReport(@PathVariable String date, HttpServletRequest request,
			HttpServletResponse response) {
		RestTemplate restTemplate = new RestTemplate();
		ModelAndView model = new ModelAndView("report/sellReport/sellReportJsp");

		try {
			HttpSession session = request.getSession();

			CategoryList catList = restTemplate.getForObject(Constant.URL + "showAllCategory", CategoryList.class);

			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			DateFormat yearFormat = new SimpleDateFormat("yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

			java.util.Date todaysDate = new java.util.Date();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(todaysDate);

			int calCurrentMonth = cal1.get(Calendar.MONTH) + 1;

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("currentMonth", calCurrentMonth);
			map.add("date", DateConvertor.convertToYMD(date));
			map.add("frId", frDetails.getFrId());
			map.add("year", yearFormat.format(todaysDate));
			DailySalesReportDao getDailySalesDataList = restTemplate.postForObject(Constant.URL + "getDailySalesData",
					map, DailySalesReportDao.class);
			System.err.println("getDailySalesDataList" + getDailySalesDataList.toString());
			model.addObject("catList", catList.getmCategoryList());
			model.addObject("regularList", getDailySalesDataList.getDailySalesRegularList());
			model.addObject("spList", getDailySalesDataList.getSpDailySalesList());

			System.out.println("catList" + catList.toString());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return model;

	}

	@RequestMapping(value = "/showDailySalesReport/{date}", method = RequestMethod.GET)
	public void showDailySalesReport(@PathVariable String date, HttpServletRequest request,
			HttpServletResponse response) {

		RestTemplate restTemplate = new RestTemplate();
		try {

			CategoryList catList = restTemplate.getForObject(Constant.URL + "showAllCategory", CategoryList.class);
			HttpSession session = request.getSession();

			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			DateFormat yearFormat = new SimpleDateFormat("yyyy");
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");

			java.util.Date todaysDate = new java.util.Date();
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(todaysDate);

			int calCurrentMonth = cal1.get(Calendar.MONTH) + 1;

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("currentMonth", calCurrentMonth);
			map.add("date", DateConvertor.convertToYMD(date));
			map.add("frId", frDetails.getFrId());
			map.add("year", yearFormat.format(todaysDate));
			DailySalesReportDao getDailySalesDataList = restTemplate.postForObject(Constant.URL + "getDailySalesData",
					map, DailySalesReportDao.class);
			System.err.println("catList" + getDailySalesDataList.toString());

			Document doc = new Document();

			String FILE_PATH = Constant.REPORT_SAVE;
			File file = new File(FILE_PATH);

			PdfWriter writer = null;

			FileOutputStream out = null;
			try {
				out = new FileOutputStream(FILE_PATH);
			} catch (FileNotFoundException e1) {

				System.err.println("File not found Exception" + e1.getMessage());
				e1.printStackTrace();
			}
			try {
				writer = PdfWriter.getInstance(doc, out);
			} catch (DocumentException e) {
				System.err.println("DocumentException Exception" + e.getMessage());

				e.printStackTrace();

			}
			doc.open();

			Paragraph heading = new Paragraph("Daily Sales Report");
			heading.setAlignment(Element.ALIGN_CENTER);
			doc.add(heading);
			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new java.util.Date());

			doc.add(new Paragraph("Date :" + date));
			doc.add(new Paragraph("\n "));

			try {
				Font headFont = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
				Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLUE);
				Font headFont2 = new Font(FontFamily.HELVETICA, 9, Font.BOLD, BaseColor.BLACK);
				catList.getmCategoryList().remove(4);
				for (int i = 0; i < catList.getmCategoryList().size(); i++) {
					if (catList.getmCategoryList().get(i).getCatId() != 5
							&& catList.getmCategoryList().get(i).getCatId() != 7) {

						/*
						 * java.util.Date date1=new java.util.Date(); // wherever you get this from
						 * 
						 * Calendar cal = Calendar.getInstance(); cal.setTime(date1);
						 * cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
						 * 
						 * java.util.Date dateRes =cal.getTime(); map = new LinkedMultiValueMap<String,
						 * Object>(); map.add("itemGrp1",catList.getmCategoryList().get(i).getCatId());
						 * RestTemplate rest = new RestTemplate(); Item[] items =
						 * rest.postForObject(Constant.URL + "/getItemsByCatId", map, Item[].class);
						 * 
						 * List<Item> itemsList =new ArrayList<>(Arrays.asList(items)); String
						 * itemsByCat="0"; for(Item item:itemsList) {
						 * itemsByCat=itemsByCat+","+item.getId(); } float regOpeningStock=0.0f;float
						 * regOpeningStockRate=0.0f;float regOpeningStockMrp=0.0f; map = new
						 * LinkedMultiValueMap<String, Object>(); map.add("frId", frDetails.getFrId());
						 * map.add("fromDate",sdf1.format(dateRes)); map.add("toDate",
						 * DateConvertor.convertToYMD(date)); map.add("itemIdList", itemsByCat);
						 * map.add("catId", catList.getmCategoryList().get(i).getCatId());
						 * map.add("frStockType", frDetails.getStockType());
						 * 
						 * try { ParameterizedTypeReference<List<GetCurrentStockDetails>> typeRef = new
						 * ParameterizedTypeReference<List<GetCurrentStockDetails>>() { };
						 * ResponseEntity<List<GetCurrentStockDetails>> responseEntity =
						 * restTemplate.exchange( Constant.URL + "/getStockBetweenDates",
						 * HttpMethod.POST, new HttpEntity<>(map), typeRef);
						 * 
						 * List<GetCurrentStockDetails> currentStockDetailList =
						 * responseEntity.getBody();
						 * System.out.println("Current Stock Details Monthwise : " +
						 * currentStockDetailList.toString());
						 * 
						 * for(GetCurrentStockDetails getCurrentStockDetails:currentStockDetailList) {
						 * regOpeningStock=regOpeningStock+getCurrentStockDetails.getRegOpeningStock();
						 * regOpeningStockRate=regOpeningStockRate+(getCurrentStockDetails.
						 * getSpOpeningStock()*getCurrentStockDetails.getRegOpeningStock());
						 * regOpeningStockMrp=regOpeningStockMrp+(getCurrentStockDetails.
						 * getSpTotalPurchase()*getCurrentStockDetails.getRegOpeningStock());
						 * 
						 * }
						 * 
						 * } catch (Exception e) { e.printStackTrace(); }
						 */

						PdfPTable table = new PdfPTable(4);
						table.setHeaderRows(1);
						table.setWidthPercentage(100);
						table.setWidths(new float[] { 4.2f, 1.0f, 1.5f, 1.5f });

						PdfPCell hcell;
						hcell = new PdfPCell(
								new Phrase("" + catList.getmCategoryList().get(i).getCatName(), headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Qty", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Rate", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("MRP", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						/*
						 * PdfPCell cell;
						 * 
						 * cell = new PdfPCell(new Phrase("OP", headFont));
						 * cell.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
						 * 
						 * cell = new PdfPCell(new Phrase(""+regOpeningStock, headFont));
						 * cell.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
						 * 
						 * 
						 * cell = new PdfPCell(new Phrase(""+regOpeningStockRate, headFont));
						 * cell.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
						 * 
						 * 
						 * cell = new PdfPCell(new Phrase(""+regOpeningStockMrp, headFont));
						 * cell.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
						 */

						PdfPCell cell1;
						cell1 = new PdfPCell(new Phrase("Purchase", headFont));
						cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell1);

						cell1 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(i).getBillQty(), headFont));
						cell1.setVerticalAlignment(Element.ALIGN_LEFT);
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell1);

						cell1 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(i).getBillQtyRate(),
								headFont));
						cell1.setVerticalAlignment(Element.ALIGN_LEFT);
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell1);

						cell1 = new PdfPCell(
								new Phrase("" + getDailySalesDataList.getDailySalesRegularList().get(i).getBillQtyMrp(),
										headFont));
						cell1.setVerticalAlignment(Element.ALIGN_LEFT);
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell1);

						PdfPCell cell2;
						cell2 = new PdfPCell(new Phrase("GRN/GVN", headFont));
						cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell2);

						cell2 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(i).getGrnGvnQty(), headFont));
						cell2.setVerticalAlignment(Element.ALIGN_LEFT);
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell2);

						cell2 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(i).getGrnGvnAmt(), headFont));
						cell2.setVerticalAlignment(Element.ALIGN_LEFT);
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell2);

						cell2 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(i).getGrnGvnAmt(), headFont));
						cell2.setVerticalAlignment(Element.ALIGN_LEFT);
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell2);

						PdfPCell cell3;
						cell3 = new PdfPCell(new Phrase("Sales", headFont));
						cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell3);

						cell3 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(i).getSellQty(), headFont));
						cell3.setVerticalAlignment(Element.ALIGN_LEFT);
						cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell3);

						cell3 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(i).getSellQtyRate(),
								headFont));
						cell3.setVerticalAlignment(Element.ALIGN_LEFT);
						cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell3);

						cell3 = new PdfPCell(
								new Phrase("" + getDailySalesDataList.getDailySalesRegularList().get(i).getSellQtyMrp(),
										headFont));
						cell3.setVerticalAlignment(Element.ALIGN_LEFT);
						cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell3);

						// float
						// closingQty=(regOpeningStock+getDailySalesDataList.getDailySalesRegularList().get(i).getBillQty())-(getDailySalesDataList.getDailySalesRegularList().get(i).getSellQty()+getDailySalesDataList.getDailySalesRegularList().get(i).getGrnGvnQty());

						/*
						 * PdfPCell cell4; cell4 = new PdfPCell(new Phrase("CLOSING", headFont2));
						 * cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
						 * cell4.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell4);
						 * 
						 * 
						 * cell4 = new PdfPCell(new Phrase(""+closingQty, headFont2));
						 * cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell4.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell4);
						 * 
						 * cell4 = new PdfPCell(new Phrase("", headFont2));
						 * cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell4.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell4); float
						 * closing=(regOpeningStock+getDailySalesDataList.getDailySalesRegularList().get
						 * (i).getBillQty())-(getDailySalesDataList.getDailySalesRegularList().get(i).
						 * getSellQty()+getDailySalesDataList.getDailySalesRegularList().get(i).
						 * getGrnGvnQty()); cell4 = new PdfPCell(new Phrase(""+Math.round(closing),
						 * headFont2)); cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell4.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell4);
						 */

						PdfPCell cell5;
						cell5 = new PdfPCell(new Phrase("PROFIT", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase("", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase("", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);
						float profit = (getDailySalesDataList.getDailySalesRegularList().get(i).getSellQtyMrp()
								- getDailySalesDataList.getDailySalesRegularList().get(i).getSellQtyRate());
						cell5 = new PdfPCell(new Phrase("" + Math.round(profit), headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						doc.add(table);
					} else if (catList.getmCategoryList().get(i).getCatId() == 7) {
						PdfPTable table = new PdfPTable(4);
						table.setHeaderRows(1);
						table.setWidthPercentage(100);
						table.setWidths(new float[] { 4.2f, 1.0f, 1.5f, 1.5f });

						PdfPCell hcell = new PdfPCell();
						hcell.setBackgroundColor(BaseColor.PINK);

						hcell = new PdfPCell(
								new Phrase("" + catList.getmCategoryList().get(i).getCatName(), headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Qty", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Rate", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("MRP", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						/*
						 * PdfPCell cell=new PdfPCell();
						 * 
						 * cell = new PdfPCell(new Phrase("OP", headFont));
						 * cell.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
						 * 
						 * cell = new PdfPCell(new
						 * Phrase(""+getDailySalesDataList.getDailySalesRegularList().get(5).
						 * getRegOpeningStock(), headFont));
						 * cell.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
						 * 
						 * 
						 * cell = new PdfPCell(new
						 * Phrase(""+getDailySalesDataList.getDailySalesRegularList().get(5).
						 * getRegOpeningStockRate(), headFont));
						 * cell.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
						 * 
						 * 
						 * cell = new PdfPCell(new
						 * Phrase(""+getDailySalesDataList.getDailySalesRegularList().get(5).
						 * getRegOpeningStockMrp(), headFont));
						 * cell.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell);
						 */

						PdfPCell cell1 = new PdfPCell();
						cell1 = new PdfPCell(new Phrase("Purchase", headFont));
						cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell1);

						cell1 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(5).getBillQty(), headFont));
						cell1.setVerticalAlignment(Element.ALIGN_LEFT);
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell1);

						cell1 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(5).getBillQtyRate(),
								headFont));
						cell1.setVerticalAlignment(Element.ALIGN_LEFT);
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell1);

						cell1 = new PdfPCell(
								new Phrase("" + getDailySalesDataList.getDailySalesRegularList().get(5).getBillQtyMrp(),
										headFont));
						cell1.setVerticalAlignment(Element.ALIGN_LEFT);
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell1);

						PdfPCell cell2 = new PdfPCell();
						cell2 = new PdfPCell(new Phrase("GRN/GVN", headFont));
						cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell2);

						cell2 = new PdfPCell(new Phrase("0", headFont));
						cell2.setVerticalAlignment(Element.ALIGN_LEFT);
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell2);

						cell2 = new PdfPCell(new Phrase("0", headFont));
						cell2.setVerticalAlignment(Element.ALIGN_LEFT);
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell2);

						cell2 = new PdfPCell(new Phrase("0", headFont));
						cell2.setVerticalAlignment(Element.ALIGN_LEFT);
						cell2.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell2);

						PdfPCell cell3 = new PdfPCell();
						cell3 = new PdfPCell(new Phrase("Sales", headFont));
						cell3.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell3.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell3);

						cell3 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(5).getSellQty(), headFont));
						cell3.setVerticalAlignment(Element.ALIGN_LEFT);
						cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell3);

						cell3 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyRate(),
								headFont));
						cell3.setVerticalAlignment(Element.ALIGN_LEFT);
						cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell3);

						cell3 = new PdfPCell(
								new Phrase("" + getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyMrp(),
										headFont));
						cell3.setVerticalAlignment(Element.ALIGN_LEFT);
						cell3.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell3);

						// float
						// closingQty=(getDailySalesDataList.getDailySalesRegularList().get(5).getRegOpeningStock()+getDailySalesDataList.getDailySalesRegularList().get(5).getBillQty())-(getDailySalesDataList.getDailySalesRegularList().get(5).getSellQty());
						float profit = (getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyMrp()
								- getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyRate());

						/*
						 * PdfPCell cell4=new PdfPCell(); cell4 = new PdfPCell(new Phrase("CLOSING",
						 * headFont2)); cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
						 * cell4.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell4);
						 * 
						 * 
						 * cell4 = new PdfPCell(new Phrase(""+closingQty, headFont2));
						 * cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell4.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell4);
						 * 
						 * cell4 = new PdfPCell(new Phrase("", headFont2));
						 * cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell4.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell4); float
						 * closing=(getDailySalesDataList.getDailySalesRegularList().get(5).
						 * getRegOpeningStock()+getDailySalesDataList.getDailySalesRegularList().get(5).
						 * getBillQty())-(getDailySalesDataList.getDailySalesRegularList().get(5).
						 * getSellQty()+getDailySalesDataList.getDailySalesRegularList().get(5).
						 * getGrnGvnQty()); float
						 * profit=(getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyMrp
						 * ()+getDailySalesDataList.getDailySalesRegularList().get(5).getGrnGvnAmt())-(
						 * getDailySalesDataList.getDailySalesRegularList().get(5).getBillQtyRate()+
						 * getDailySalesDataList.getDailySalesRegularList().get(5).
						 * getRegOpeningStockRate());
						 * 
						 * cell4 = new PdfPCell(new Phrase(""+Math.round(closing), headFont2));
						 * cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						 * cell4.setHorizontalAlignment(Element.ALIGN_LEFT); table.addCell(cell4);
						 */

						PdfPCell cell5 = new PdfPCell();
						cell5 = new PdfPCell(new Phrase("PROFIT", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase("", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase("", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase("" + profit, headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						doc.add(table);
					}

				}
				MCategory mCat = new MCategory();
				mCat.setCatId(5);
				mCat.setCatName("Special Cake");

				catList.getmCategoryList().add(mCat);
				for (int i = 0; i < catList.getmCategoryList().size(); i++) {
					if (catList.getmCategoryList().get(i).getCatId() == 5) {
						PdfPTable table = new PdfPTable(5);
						table.setHeaderRows(1);
						table.setWidthPercentage(100);
						table.setWidths(new float[] { 4.2f, 1.0f, 1.5f, 1.5f, 1.5f });

						PdfPCell hcell = new PdfPCell();

						hcell = new PdfPCell(new Phrase("Special Cake", headFont1));
						hcell.setBackgroundColor(BaseColor.PINK);
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Qty", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Rate", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("MRP", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Advance", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						PdfPCell cell = new PdfPCell();

						cell = new PdfPCell(new Phrase("Sp Order", headFont));
						cell.setVerticalAlignment(Element.ALIGN_LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell);

						cell = new PdfPCell(
								new Phrase("" + getDailySalesDataList.getSpDailySalesList().get(0).getQty(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getSpDailySalesList().get(0).getRate(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell);

						cell = new PdfPCell(
								new Phrase("" + getDailySalesDataList.getSpDailySalesList().get(0).getMrp(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell);

						cell = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getSpDailySalesList().get(0).getAdvance(), headFont));
						cell.setVerticalAlignment(Element.ALIGN_LEFT);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell);

						PdfPCell cell1 = new PdfPCell();
						cell1 = new PdfPCell(new Phrase("Bill Cost", headFont));
						cell1.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell1);

						cell1 = new PdfPCell(
								new Phrase("" + getDailySalesDataList.getSpDailySalesList().get(1).getQty(), headFont));
						cell1.setVerticalAlignment(Element.ALIGN_LEFT);
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell1);

						cell1 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getSpDailySalesList().get(1).getRate(), headFont));
						cell1.setVerticalAlignment(Element.ALIGN_LEFT);
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell1);

						cell1 = new PdfPCell(
								new Phrase("" + getDailySalesDataList.getSpDailySalesList().get(1).getMrp(), headFont));
						cell1.setVerticalAlignment(Element.ALIGN_LEFT);
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell1);

						cell1 = new PdfPCell(new Phrase(
								"" + getDailySalesDataList.getSpDailySalesList().get(1).getAdvance(), headFont));
						cell1.setVerticalAlignment(Element.ALIGN_LEFT);
						cell1.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell1);

						PdfPCell cell4 = new PdfPCell();
						cell4 = new PdfPCell(new Phrase("Total Sale", headFont2));
						cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell4);

						cell4 = new PdfPCell(new Phrase("", headFont2));
						cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell4);

						cell4 = new PdfPCell(new Phrase("", headFont2));
						cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell4);

						cell4 = new PdfPCell(new Phrase("", headFont2));
						cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell4);

						cell4 = new PdfPCell(new Phrase(
								((getDailySalesDataList.getSpDailySalesList().get(1).getMrp()
										- getDailySalesDataList.getSpDailySalesList().get(1).getAdvance())
										+ getDailySalesDataList.getSpDailySalesList().get(0).getAdvance()) + "",
								headFont2));
						cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell4);

						PdfPCell cell5 = new PdfPCell();
						cell5 = new PdfPCell(new Phrase("PROFIT", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase("", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase("", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase("", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase(
								(getDailySalesDataList.getSpDailySalesList().get(1).getMrp()
										- getDailySalesDataList.getSpDailySalesList().get(1).getRate()) + "",
								headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						doc.add(table);
					}
				}
//////////////////////////////////////////////////////////////////////////////
				float totalQty = 0;
				float totalRate = 0;
				float totalMrp = 0;
				float totalProfit = 0;
				System.err.println("Category List:" + catList.getmCategoryList().toString());
				for (int i = 0; i < catList.getmCategoryList().size(); i++) {
					if (catList.getmCategoryList().get(i).getCatId() != 5
							&& catList.getmCategoryList().get(i).getCatId() != 7) {

						PdfPTable table = new PdfPTable(5);
						table.setHeaderRows(1);
						table.setWidthPercentage(100);
						table.setWidths(new float[] { 4.2f, 1.0f, 1.5f, 1.5f, 1.5f });

						PdfPCell hcell;
						hcell = new PdfPCell(
								new Phrase("" + catList.getmCategoryList().get(i).getCatName(), headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Qty", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Rate", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("MRP", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("PROFIT", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						PdfPCell cell5;
						cell5 = new PdfPCell(new Phrase("Sales", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell5);

						cell5 = new PdfPCell(
								new Phrase("" + (getDailySalesDataList.getDailySalesRegularList().get(i).getSellQty()),
										headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);
						totalQty = totalQty + getDailySalesDataList.getDailySalesRegularList().get(i).getSellQty();
						totalRate = totalRate
								+ getDailySalesDataList.getDailySalesRegularList().get(i).getSellQtyRate();
						totalMrp = totalMrp + getDailySalesDataList.getDailySalesRegularList().get(i).getSellQtyMrp();

						cell5 = new PdfPCell(new Phrase(
								"" + (getDailySalesDataList.getDailySalesRegularList().get(i).getSellQtyRate()),
								headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);
						float profit = (getDailySalesDataList.getDailySalesRegularList().get(i).getSellQtyMrp()
								- getDailySalesDataList.getDailySalesRegularList().get(i).getSellQtyRate());

						cell5 = new PdfPCell(new Phrase(
								"" + Math
										.round(getDailySalesDataList.getDailySalesRegularList().get(i).getSellQtyMrp()),
								headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase("" + Math.round(profit), headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						doc.add(table);

						totalProfit = totalProfit + profit;
					} else if (catList.getmCategoryList().get(i).getCatId() == 7) {
						System.err.println("catList" + catList.toString());
						PdfPTable table = new PdfPTable(5);
						table.setHeaderRows(1);
						table.setWidthPercentage(100);
						table.setWidths(new float[] { 4.2f, 1.0f, 1.5f, 1.5f, 1.5f });

						PdfPCell hcell = new PdfPCell();
						hcell.setBackgroundColor(BaseColor.PINK);

						hcell = new PdfPCell(
								new Phrase("" + catList.getmCategoryList().get(i).getCatName(), headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Qty", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Rate", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("MRP", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("PROFIT", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						totalQty = totalQty + getDailySalesDataList.getDailySalesRegularList().get(5).getSellQty();
						totalRate = totalRate
								+ getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyRate();
						totalMrp = totalMrp + getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyMrp();

						float profit = (getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyMrp()
								- getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyRate());

						totalProfit = totalProfit + profit;

						PdfPCell cell5 = new PdfPCell();

						cell5 = new PdfPCell(new Phrase("Sales", headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell5.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell5);

						cell5 = new PdfPCell(
								new Phrase("" + (getDailySalesDataList.getDailySalesRegularList().get(5).getSellQty()),
										headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase(
								"" + (getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyRate()),
								headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase(
								"" + Math
										.round(getDailySalesDataList.getDailySalesRegularList().get(5).getSellQtyMrp()),
								headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);

						cell5 = new PdfPCell(new Phrase("" + Math.round(profit), headFont2));
						cell5.setVerticalAlignment(Element.ALIGN_LEFT);
						cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell5);
						doc.add(table);//
					}

				}

				for (int i = 0; i < catList.getmCategoryList().size(); i++) {
					if (catList.getmCategoryList().get(i).getCatId() == 5) {
						PdfPTable table = new PdfPTable(5);
						table.setHeaderRows(1);
						table.setWidthPercentage(100);
						table.setWidths(new float[] { 4.2f, 1.0f, 1.5f, 1.5f, 1.5f });

						PdfPCell hcell = new PdfPCell();

						hcell = new PdfPCell(new Phrase("Special Cake", headFont1));
						hcell.setBackgroundColor(BaseColor.PINK);
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Qty", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Rate", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("MRP", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						hcell = new PdfPCell(new Phrase("Profit", headFont1));
						hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
						hcell.setBackgroundColor(BaseColor.PINK);

						table.addCell(hcell);

						PdfPCell cell4 = new PdfPCell();
						cell4 = new PdfPCell(new Phrase("Total Sale", headFont2));
						cell4.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell4.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell4);

						cell4 = new PdfPCell(new Phrase("", headFont2));
						cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell4);

						cell4 = new PdfPCell(new Phrase("", headFont2));
						cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell4);

						cell4 = new PdfPCell(new Phrase(
								((getDailySalesDataList.getSpDailySalesList().get(1).getMrp()
										- getDailySalesDataList.getSpDailySalesList().get(1).getAdvance())
										+ getDailySalesDataList.getSpDailySalesList().get(0).getAdvance()) + "",
								headFont2));
						cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell4);

						cell4 = new PdfPCell(new Phrase(
								(getDailySalesDataList.getSpDailySalesList().get(1).getMrp()
										- getDailySalesDataList.getSpDailySalesList().get(1).getRate()) + "",
								headFont2));
						cell4.setVerticalAlignment(Element.ALIGN_LEFT);
						cell4.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell4);

						PdfPCell cell6 = new PdfPCell();

						cell6 = new PdfPCell(new Phrase("Grand Total", headFont2));
						cell6.setVerticalAlignment(Element.ALIGN_MIDDLE);
						cell6.setHorizontalAlignment(Element.ALIGN_LEFT);
						table.addCell(cell6);

						cell6 = new PdfPCell(new Phrase("" + Math.round(totalQty), headFont2));
						cell6.setVerticalAlignment(Element.ALIGN_LEFT);
						cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell6);

						cell6 = new PdfPCell(new Phrase("" + Math.round(totalRate), headFont2));
						cell6.setVerticalAlignment(Element.ALIGN_LEFT);
						cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell6);

						cell6 = new PdfPCell(new Phrase("" + Math.round(totalMrp), headFont2));
						cell6.setVerticalAlignment(Element.ALIGN_LEFT);
						cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell6);

						totalProfit = totalProfit + (getDailySalesDataList.getSpDailySalesList().get(1).getMrp()
								- getDailySalesDataList.getSpDailySalesList().get(1).getRate());

						cell6 = new PdfPCell(new Phrase("" + Math.round(totalProfit), headFont2));
						cell6.setVerticalAlignment(Element.ALIGN_LEFT);
						cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell6);

						cell6 = new PdfPCell(new Phrase("", headFont2));
						cell6.setVerticalAlignment(Element.ALIGN_LEFT);
						cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
						table.addCell(cell6);
						doc.add(table);

						break;
					}
				}

				doc.close();

				if (file != null) {

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

				System.out.println("Pdf Generation Error: Prod From Orders" + ex.getMessage());

				ex.printStackTrace();

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	// ----------------------------------------------------------------------------------------

	/*
	 * @RequestMapping(value = "/pdfCustBill", method = RequestMethod.GET) public
	 * ModelAndView demoBill(HttpServletRequest request, HttpServletResponse
	 * response) { String sellBillNo=request.getParameter("billNo"); //String
	 * fr_Id=request.getParameter("frId"); int billNo=Integer.parseInt(sellBillNo);
	 * //int billNo=Integer.parseInt(fr_Id); //HttpSession ses =
	 * request.getSession(); //Franchisee frDetails = (Franchisee)
	 * ses.getAttribute("frDetails");2
	 * 
	 * 
	 * ModelAndView model=new ModelAndView("report/franchCompInvoice");
	 * 
	 * RestTemplate rest=new RestTemplate(); MultiValueMap<String, Object> map=new
	 * LinkedMultiValueMap<String, Object>();
	 * 
	 * map.add("billNo", billNo); if(frGstType!=0) { model=new
	 * ModelAndView("report/franchTaxInvoice"); List<GetCustBillTax>
	 * getCustBilTaxList=rest.postForObject(Constant.URL+"getCustomerBillTax", map,
	 * List.class);
	 * 
	 * model.addObject("custBilltax", getCustBilTaxList); }
	 * 
	 * List<GetCustmoreBillResponse>
	 * getCustmoreBillResponseList=rest.postForObject(Constant.URL+
	 * "getCustomerBill", map, List.class);
	 * 
	 * System.out.println("Custmore Bill : "+getCustmoreBillResponseList.toString())
	 * ;
	 * 
	 * model.addObject("billList", getCustmoreBillResponseList); return model; }
	 */

	private Dimension format = PD4Constants.A4;
	private boolean landscapeValue = false;
	private int topValue = 8;
	private int leftValue = 0;
	private int rightValue = 0;
	private int bottomValue = 8;
	private String unitsValue = "m";
	private String proxyHost = "";
	private int proxyPort = 0;

	private int userSpaceWidth = 750;
	private static int BUFFER_SIZE = 1024;

	@RequestMapping(value = "/pdf", method = RequestMethod.GET)
	public void showPDF(HttpServletRequest request, HttpServletResponse response) {

		String url = request.getParameter("reportURL");
		File f = new File(Constant.CRN_REPORT_PATH);
		// File f = new File("/opt/apache-tomcat-8.5.6/webapps/uploads/report.pdf");
		// File f = new File("/home/ats-12/pdf/ordermemo221.pdf");
		try {
			runConverter(Constant.ReportURL + url, f, request, response);
		} catch (IOException e) {
			// TODO Auto-generated catch block

			System.out.println("Pdf conversion exception " + e.getMessage());
		}

		// get absolute path of the application
		ServletContext context = request.getSession().getServletContext();
		String appPath = context.getRealPath("");
		String filename = "ordermemo221.pdf";
		// String filePath = "/opt/apache-tomcat-8.5.6/webapps/uploads/report.pdf";
		String filePath =Constant.CRN_REPORT_PATH;// "/opt/apache-tomcat-8.5.37/webapps/uploadspune/crn.pdf";
		// String filePath = "/opt/apache-tomcat-8.5.6/webapps/uploads/report.pdf";
		// String filePath="/home/ats-12/pdf/ordermemo221.pdf";
		// construct the complete absolute path of the file
		String fullPath = appPath + filePath;
		File downloadFile = new File(filePath);
		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(downloadFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			// get MIME type of the file
			String mimeType = context.getMimeType(fullPath);
			if (mimeType == null) {
				// set to binary type if MIME mapping not found
				mimeType = "application/pdf";
			}
			System.out.println("MIME type: " + mimeType);

			String headerKey = "Content-Disposition";

			// response.addHeader("Content-Disposition", "attachment;filename=report.pdf");
			response.setContentType("application/pdf");

			// get output stream of the response
			OutputStream outStream;

			outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream

			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void runConverter(String urlstring, File output, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		if (urlstring.length() > 0) {
			if (!urlstring.startsWith("http://") && !urlstring.startsWith("file:")) {
				urlstring = "http://" + urlstring;
			}
			System.out.println("PDF URL " + urlstring);
			java.io.FileOutputStream fos = new java.io.FileOutputStream(output);

			PD4ML pd4ml = new PD4ML();

			try {

				Dimension landscapeA4 = pd4ml.changePageOrientation(PD4Constants.A4);
				pd4ml.setPageSize(landscapeA4);

				PD4PageMark footer = new PD4PageMark();

				footer.setPageNumberTemplate("Page $[page] of $[total]");
				footer.setPageNumberAlignment(PD4PageMark.RIGHT_ALIGN);
				footer.setFontSize(10);
				footer.setAreaHeight(20);

				pd4ml.setPageFooter(footer);

			} catch (Exception e) {
				System.out.println("Pdf conversion method excep " + e.getMessage());
			}

			if (unitsValue.equals("mm")) {
				pd4ml.setPageInsetsMM(new Insets(topValue, leftValue, bottomValue, rightValue));
			} else {
				pd4ml.setPageInsets(new Insets(topValue, leftValue, bottomValue, rightValue));
			}

			pd4ml.setHtmlWidth(userSpaceWidth);

			pd4ml.render(urlstring, fos);
		}
	}

	@RequestMapping(value = "/showCRNoteRegisterDone", method = RequestMethod.GET)
	public ModelAndView showCRNoteRegisterDone(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("reports/crNote_register_done");

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

	List<CrNoteRegItem> crNoteRegItemListDone = new ArrayList<>();
	// getCRNoteRegister Ajax

	@RequestMapping(value = "/getCRNoteRegisterDone", method = RequestMethod.GET)
	public @ResponseBody List<CrNoteRegItem> getCRNoteRegisterDone(HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException {

		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		RestTemplate restTemplate = new RestTemplate();

		System.out.println("inside getCRNoteRegister ajax call");

		String fromDate = request.getParameter("fromDate");
		String toDate = request.getParameter("toDate");
		try {

			map.add("fromDate", DateConvertor.convertToYMD(fromDate));

			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("frId", frDetails.getFrId());

			CrNoteRegisterList crnArray = restTemplate.postForObject(Constant.URL + "getCrNoteRegisterDoneByFrId", map,
					CrNoteRegisterList.class);

			List<CrNoteRegSp> crnRegSpList = new ArrayList<>();

			crNoteRegItemListDone = crnArray.getCrNoteRegItemList();
			crnRegSpList = crnArray.getCrNoteRegSpList();

			for (int j = 0; j < crnRegSpList.size(); j++) {
				int flag = 0;

				for (int i = 0; i < crNoteRegItemListDone.size(); i++) {

					if (crNoteRegItemListDone.get(i).getCrnId() == crnRegSpList.get(j).getCrnId()
							&& crNoteRegItemListDone.get(i).getHsnCode().equals(crnRegSpList.get(j).getHsnCode())) {
						flag = 1;
						crNoteRegItemListDone.get(i)
								.setCrnQty(crNoteRegItemListDone.get(i).getCrnQty() + crnRegSpList.get(j).getCrnQty());

						crNoteRegItemListDone.get(i).setCrnTaxable(
								(crNoteRegItemListDone.get(i).getCrnTaxable() + crnRegSpList.get(j).getCrnTaxable()));

						crNoteRegItemListDone.get(i).setCgstAmt(
								(crNoteRegItemListDone.get(i).getCgstAmt() + crnRegSpList.get(j).getCgstAmt()));
						crNoteRegItemListDone.get(i).setSgstAmt(
								(crNoteRegItemListDone.get(i).getSgstAmt() + crnRegSpList.get(j).getSgstAmt()));
						crNoteRegItemListDone.get(i).setCrnAmt(
								(crNoteRegItemListDone.get(i).getCrnAmt() + crnRegSpList.get(j).getCrnAmt()));

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

					crNoteRegItemListDone.add(regItem);
				}
			}

			System.err.println("crNoteRegItemList combined  " + crNoteRegItemListDone.toString());

			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr. No.");
			rowData.add("GSTIN/UIN of Recipient");
			rowData.add("Receiver Name");
			rowData.add("Invoice/Advance Receipt Number");
			rowData.add("Invoice/Advance Receipt date");
			rowData.add("Note/Refund Voucher Number");
			rowData.add("Note/Refund Voucher date");
			rowData.add("Document Type");
			rowData.add("Place Of Supply");
			rowData.add("Note/Refund Voucher Value");
			rowData.add("Applicable % of Tax Rate");
			rowData.add("Rate");
			rowData.add("Taxable Value");
			rowData.add("Cess Amount");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);
			float crnQty = 0.0f;
			float crnTaxable = 0.0f;
			float cgstAmt = 0.0f;
			float sgstAmt = 0.0f;
			float crnAmt = 0.0f;

			for (int i = 0; i < crNoteRegItemListDone.size(); i++) {
				float crnTotal = 0.0f;
				for (int j = 0; j < crNoteRegItemListDone.size(); j++) {
					if (crNoteRegItemListDone.get(i).getCrnId() == crNoteRegItemListDone.get(j).getCrnId()) {
						crnTotal = crnTotal + roundUp(crNoteRegItemListDone.get(j).getCrnAmt());
					}
				}

				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();
				rowData.add("" + (i + 1));
				rowData.add("" + crNoteRegItemListDone.get(i).getFrGstNo());
				rowData.add("" + crNoteRegItemListDone.get(i).getFrName());
				rowData.add("" + crNoteRegItemListDone.get(i).getInvoiceNo());
				rowData.add("" + crNoteRegItemListDone.get(i).getBillDate());
				rowData.add("" + crNoteRegItemListDone.get(i).getFrCode());
				rowData.add("" + crNoteRegItemListDone.get(i).getCrnDate());
				rowData.add(" ");
				rowData.add(""+Constant.STATE);
				rowData.add("" + roundUp(crnTotal));
				rowData.add(" ");
				rowData.add(
						"" + (crNoteRegItemListDone.get(i).getCgstPer() + crNoteRegItemListDone.get(i).getSgstPer()));
				rowData.add("" + roundUp(crNoteRegItemListDone.get(i).getCrnTaxable()));
				rowData.add("0");

				crnQty = crnQty + crNoteRegItemListDone.get(i).getCrnQty();
				crnTaxable = crnTaxable + crNoteRegItemListDone.get(i).getCrnTaxable();
				cgstAmt = cgstAmt + crNoteRegItemListDone.get(i).getCgstAmt();
				sgstAmt = sgstAmt + crNoteRegItemListDone.get(i).getSgstAmt();
				crnAmt = crnAmt + crNoteRegItemListDone.get(i).getCrnAmt();

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
			rowData.add("");
			rowData.add("");
			rowData.add("");
			rowData.add("");

			rowData.add("" + Math.round(crnAmt));
			rowData.add("");
			rowData.add("");

			rowData.add("" + roundUp(crnTaxable));

			rowData.add("");

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "CR Note Register");
			session.setAttribute("reportNameNew", "Credit Note Register Report");
			session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
			session.setAttribute("mergeUpto1", "$A$1:$M$1");
			session.setAttribute("mergeUpto2", "$A$2:$M$2");

		} catch (Exception e) {

			e.printStackTrace();

		}
		return crNoteRegItemListDone;
	}

	@RequestMapping(value = "/getCRNoteRegisterDonePdf/{fromdate}/{todate}", method = RequestMethod.GET)
	public void getCRNoteRegisterDonePdf(@PathVariable String fromdate, @PathVariable String todate,
			HttpServletRequest request, HttpServletResponse response) throws FileNotFoundException {

		Document document = new Document(PageSize.A4);
		document.setPageSize(PageSize.A4.rotate());
		// ByteArrayOutputStream out = new ByteArrayOutputStream();

		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		Calendar cal = Calendar.getInstance();

		System.out.println("timegetCRNoteRegisterPdf PDF ==" + dateFormat.format(cal.getTime()));
		String timeStamp = dateFormat.format(cal.getTime());
		String FILE_PATH = Constant.REPORT_SAVE;
		File file = new File(FILE_PATH);

		PdfWriter writer = null;

		FileOutputStream out = new FileOutputStream(FILE_PATH);

		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e) {

			e.printStackTrace();
		}

		PdfPTable table = new PdfPTable(13);
		table.setHeaderRows(1);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(
					new float[] { 0.7f, 1.1f, 2.0f, 2.1f, 2.3f, 2.0f, 2.2f, 1.2f, 1.2f, 1.2f, 0.9f, 0.9f, 1.2f });
			Font headFont = new Font(FontFamily.HELVETICA, 8, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
			Font f = new Font(FontFamily.TIMES_ROMAN, 10.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Sr.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("CRN No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("CRN Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Invoice No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Invoice Date", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Party Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("GST No", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Tax Rate", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Bill Qty", headFont1)); // Varience title replaced with P2 Production
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Taxable Amt", headFont1)); // Varience title replaced with P2 Production
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("CGST Amt", headFont1)); // Varience title replaced with P2 Production
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("SGST Amt", headFont1)); // Varience title replaced with P2 Production
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Bill Amt", headFont1)); // Varience title replaced with P2 Production
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			hcell.setBackgroundColor(BaseColor.PINK);
			table.addCell(hcell);

			int index = 0;
			for (int j = 0; j < crNoteRegItemListDone.size(); j++) {

				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase("" + crNoteRegItemListDone.get(j).getFrCode(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(1);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(crNoteRegItemListDone.get(j).getCrnDate()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(1);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(crNoteRegItemListDone.get(j).getInvoiceNo(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(1);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(crNoteRegItemListDone.get(j).getBillDate()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(1);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(crNoteRegItemListDone.get(j).getFrName()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(1);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(crNoteRegItemListDone.get(j).getFrGstNo()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(1);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(
						String.valueOf(roundUp(
								crNoteRegItemListDone.get(j).getCgstPer() + crNoteRegItemListDone.get(j).getSgstPer())),
						headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(1);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(String.valueOf(roundUp(crNoteRegItemListDone.get(j).getCrnQty())), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(8);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(String.valueOf(roundUp(crNoteRegItemListDone.get(j).getCrnTaxable())), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(8);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(String.valueOf(roundUp(crNoteRegItemListDone.get(j).getCgstAmt())), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(8);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(String.valueOf(roundUp(crNoteRegItemListDone.get(j).getSgstAmt())), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(8);
				table.addCell(cell);

				cell = new PdfPCell(
						new Phrase(String.valueOf(roundUp(crNoteRegItemListDone.get(j).getCrnAmt())), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(8);
				table.addCell(cell);

			}
			document.open();

			Paragraph heading = new Paragraph(
					"Credit Note Register Report \n From Date:" + fromdate + " To Date:" + todate);
			heading.setAlignment(Element.ALIGN_CENTER);
			document.add(heading);

			DateFormat DF = new SimpleDateFormat("dd-MM-yyyy");
			String reportDate = DF.format(new Date(index));

			document.add(new Paragraph("\n"));

			document.add(table);

			document.close();

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

				InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

				try {
					FileCopyUtils.copy(inputStream, response.getOutputStream());
				} catch (IOException e) {
					System.out.println("Excep in Opening a Pdf File");
					e.printStackTrace();
				}

			}

		} catch (DocumentException ex) {

			System.out.println("Pdf Generation Error: Prod From Orders" + ex.getMessage());

			ex.printStackTrace();

		}
	}

}
