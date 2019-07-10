package com.monginis.ops.controller;

import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;
import org.zefer.pd4ml.PD4PageMark;
import com.monginis.ops.common.DateConvertor;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.ExportToExcel;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GrnGvnReport;
import com.monginis.ops.model.TSellReport;

@Controller
@Scope("session")
public class SellReport {

	@RequestMapping(value = "/hsnWiseReport", method = RequestMethod.GET)
	public ModelAndView hsnWiseReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/hsnWiseReport");

		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

		model.addObject("frId", frDetails.getFrId());
		model.addObject("frName", frDetails.getFrName());

		return model;
	}

	@RequestMapping(value = "/getHsnWiseReport", method = RequestMethod.GET)
	@ResponseBody
	public List<TSellReport> getHsnWiseReport(HttpServletRequest request, HttpServletResponse response) {

		List<TSellReport> tSellReport = new ArrayList<TSellReport>();
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			RestTemplate rest = new RestTemplate();
			TSellReport tSellReportList[] = rest.postForObject(Constant.URL + "/tSellReport", map, TSellReport[].class);

			System.out.println("tSellReport " + tSellReport);

			tSellReport = new ArrayList<>(Arrays.asList(tSellReportList));
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr No");
			rowData.add("Item Name");
			rowData.add("HSN Code");
			rowData.add("CGST");
			rowData.add("SGST");
			rowData.add("IGST");
			rowData.add("Taxable Amt");
			rowData.add("Tax Amt");
			rowData.add("Grand Total");
			expoExcel.setRowData(rowData);

			exportToExcelList.add(expoExcel);

			for (int i = 0; i < tSellReport.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add("" + (i + 1));
				rowData.add("" + tSellReport.get(i).getItemName());
				rowData.add("" + tSellReport.get(i).getHsnNo());
				rowData.add("" + roundUp(tSellReport.get(i).getCgst()));
				rowData.add("" + roundUp(tSellReport.get(i).getSgst()));
				rowData.add("" + roundUp(tSellReport.get(i).getIgst()));
				rowData.add("" + roundUp(tSellReport.get(i).getTaxableAmt()));
				rowData.add("" + roundUp(tSellReport.get(i).getTotalTax()));
				rowData.add("" + roundUp(tSellReport.get(i).getGrandTotal()));

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelList", exportToExcelList);
			session.setAttribute("excelName", "TSellReport");

		} catch (Exception e) {
			e.printStackTrace();

		}
		return tSellReport;
	}

	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	@RequestMapping(value = "pdf/getHsnWiseReportPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView getHsnWiseReportPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/hscCodeWisePdf");
		try {
			List<TSellReport> tSellReport = new ArrayList<TSellReport>();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));

			RestTemplate rest = new RestTemplate();
			tSellReport = rest.postForObject(Constant.URL + "/tSellReport", map, List.class);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = rest.getForObject(Constant.URL + "getFranchisee?frId={frId}", Franchisee.class,
					frId);

			System.out.println("tSellReport " + tSellReport);
			model.addObject("reportList", tSellReport);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
			model.addObject("frName", franchisee.getFrName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/grnReport", method = RequestMethod.GET)
	public ModelAndView grnGvnReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/grnReport");

		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

		model.addObject("frId", frDetails.getFrId());
		model.addObject("frName", frDetails.getFrName());

		return model;
	}

	@RequestMapping(value = "/getgrnReport", method = RequestMethod.GET)
	@ResponseBody
	public List<GrnGvnReport> getgrnReport(HttpServletRequest request, HttpServletResponse response) {

		String fromDate = "";
		String toDate = "";

		List<GrnGvnReport> getgrnReport = new ArrayList<GrnGvnReport>();
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			fromDate = request.getParameter("fromDate");
			toDate = request.getParameter("toDate");
			String isGrn = "1";
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("isGrn", isGrn);
			System.out.println("map " + map);
			RestTemplate rest = new RestTemplate();
			GrnGvnReport grnGvnReportList[] = rest.postForObject(Constant.URL + "/grnGvnReport", map,
					GrnGvnReport[].class);

			getgrnReport = new ArrayList<>(Arrays.asList(grnGvnReportList));
			System.out.println("getgrnReport " + getgrnReport);
			System.out.println("grnGvnReportList " + grnGvnReportList);
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr No");
			rowData.add("Grn Gvn Date");
			rowData.add("Item Name");
			rowData.add("Tax Rate");
			rowData.add("Taxable Amt");
			rowData.add("Total Tax");
			rowData.add("Grn Gvn Amt");
			rowData.add("Approved Taxable Amt");
			rowData.add("Approved CGST Amt");
			rowData.add("Approved SGST Amt");
			rowData.add("Approved IGST Amt");
			rowData.add("Approved Grand Total");
			expoExcel.setRowData(rowData);

			exportToExcelList.add(expoExcel);

			float taxableAmt = 0.0f;
			float grnGvnAmt = 0.0f;
			float appTaxableAmt = 0.0f;
			float appCgstSum = 0.0f;
			float appSgstSum = 0.0f;
			float appIgstSum = 0.0f;
			float totalTax = 0.0f;
			float grandTotal = 0.0f;

			for (int i = 0; i < getgrnReport.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				taxableAmt = taxableAmt + getgrnReport.get(i).getTaxableAmt();
				totalTax = totalTax + getgrnReport.get(i).getTotalTax();
				grnGvnAmt = grnGvnAmt + getgrnReport.get(i).getGrnGvnAmt();
				appTaxableAmt = appTaxableAmt + getgrnReport.get(i).getAprTaxableAmt();
				appCgstSum = appCgstSum + getgrnReport.get(i).getAprCgstRs();
				appSgstSum = appSgstSum + getgrnReport.get(i).getAprSgstRs();
				appIgstSum = appIgstSum + getgrnReport.get(i).getAprIgstRs();
				grandTotal = grandTotal + getgrnReport.get(i).getAprGrandTotal();

				rowData.add("" + (i + 1));
				rowData.add("" + getgrnReport.get(i).getGrnGvnDate());
				rowData.add("" + getgrnReport.get(i).getItemName());
				rowData.add("" + getgrnReport.get(i).getTaxRate());
				rowData.add("" + roundUp(getgrnReport.get(i).getTaxableAmt()));
				rowData.add("" + roundUp(getgrnReport.get(i).getTotalTax()));
				rowData.add("" + roundUp(getgrnReport.get(i).getGrnGvnAmt()));
				rowData.add("" + roundUp(getgrnReport.get(i).getAprTaxableAmt()));
				rowData.add("" + roundUp(getgrnReport.get(i).getAprCgstRs()));
				rowData.add("" + roundUp(getgrnReport.get(i).getAprSgstRs()));
				rowData.add("" + roundUp(getgrnReport.get(i).getAprIgstRs()));
				rowData.add("" + roundUp(getgrnReport.get(i).getAprGrandTotal()));

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}

			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("");
			rowData.add("Total");
			rowData.add("");
			rowData.add("");

			rowData.add("" + roundUp(taxableAmt));
			rowData.add("" + roundUp(totalTax));
			rowData.add("" + roundUp(grnGvnAmt));
			rowData.add("" + roundUp(appTaxableAmt));
			rowData.add("" + roundUp(appCgstSum));
			rowData.add("" + roundUp(appSgstSum));
			rowData.add("" + roundUp(appIgstSum));
			rowData.add("" + roundUp(grandTotal));

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "GrnGvnReport");
			session.setAttribute("reportNameNew", "View GRN Report");
			session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");

		} catch (Exception e) {
			e.printStackTrace();

		}
		return getgrnReport;
	}

	@RequestMapping(value = "pdf/getgrnReportPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView getgrnReportPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/grnReportPdf");
		try {
			List<GrnGvnReport> getgrnReport = new ArrayList<GrnGvnReport>();
			String isGrn = "1";
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("isGrn", isGrn);
			RestTemplate rest = new RestTemplate();
			getgrnReport = rest.postForObject(Constant.URL + "/grnGvnReport", map, List.class);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = rest.getForObject(Constant.URL + "getFranchisee?frId={frId}", Franchisee.class,
					frId);

			System.out.println("getgrnReport " + getgrnReport);
			model.addObject("reportList", getgrnReport);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
			model.addObject("frName", franchisee.getFrName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

	@RequestMapping(value = "/gvnReport", method = RequestMethod.GET)
	public ModelAndView gvnReport(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/gvnReport");

		HttpSession ses = request.getSession();
		Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");

		model.addObject("frId", frDetails.getFrId());
		model.addObject("frName", frDetails.getFrName());

		return model;
	}

	@RequestMapping(value = "/getgvnReport", method = RequestMethod.GET)
	@ResponseBody
	public List<GrnGvnReport> getgvnReport(HttpServletRequest request, HttpServletResponse response) {

		List<GrnGvnReport> getgrnReport = new ArrayList<GrnGvnReport>();
		try {
			HttpSession ses = request.getSession();
			Franchisee frDetails = (Franchisee) ses.getAttribute("frDetails");
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");
			String isGrn = "0,2";
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("isGrn", isGrn);
			System.out.println("map " + map);
			RestTemplate rest = new RestTemplate();
			GrnGvnReport[] grnGvnReportList = rest.postForObject(Constant.URL + "/grnGvnReport", map,
					GrnGvnReport[].class);

			getgrnReport = new ArrayList<GrnGvnReport>(Arrays.asList(grnGvnReportList));
			System.out.println("getgrnReport " + getgrnReport);
			System.out.println("grnGvnReportList " + grnGvnReportList);
			List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

			ExportToExcel expoExcel = new ExportToExcel();
			List<String> rowData = new ArrayList<String>();

			rowData.add("Sr No");
			rowData.add("Grn Gvn Date");
			rowData.add("Item Name");
			rowData.add("Tax Rate");
			rowData.add("Taxable Amt");
			rowData.add("Total Tax");
			rowData.add("Grn Gvn Amt");
			rowData.add("Approved Taxable Amt");
			rowData.add("Approved CGST Amt");
			rowData.add("Approved SGST Amt");
			rowData.add("Approved IGST Amt");
			rowData.add("Approved Grand Total");
			expoExcel.setRowData(rowData);

			exportToExcelList.add(expoExcel);

			float taxableAmt = 0.0f;
			float grnGvnAmt = 0.0f;
			float appTaxableAmt = 0.0f;
			float appCgstSum = 0.0f;
			float appSgstSum = 0.0f;
			float appIgstSum = 0.0f;
			float totalTax = 0.0f;
			float grandTotal = 0.0f;

			for (int i = 0; i < getgrnReport.size(); i++) {
				expoExcel = new ExportToExcel();
				rowData = new ArrayList<String>();

				rowData.add("" + (i + 1));
				rowData.add("" + getgrnReport.get(i).getGrnGvnDate());
				rowData.add("" + getgrnReport.get(i).getItemName());
				rowData.add("" + getgrnReport.get(i).getTaxRate());
				rowData.add("" + getgrnReport.get(i).getTaxableAmt());
				rowData.add("" + getgrnReport.get(i).getTotalTax());
				rowData.add("" + getgrnReport.get(i).getGrnGvnAmt());
				rowData.add("" + getgrnReport.get(i).getAprTaxableAmt());
				rowData.add("" + getgrnReport.get(i).getAprCgstRs());
				rowData.add("" + getgrnReport.get(i).getAprSgstRs());
				rowData.add("" + getgrnReport.get(i).getAprIgstRs());
				rowData.add("" + getgrnReport.get(i).getAprGrandTotal());

				taxableAmt = taxableAmt + getgrnReport.get(i).getTaxableAmt();
				totalTax = totalTax + getgrnReport.get(i).getTotalTax();
				grnGvnAmt = grnGvnAmt + getgrnReport.get(i).getGrnGvnAmt();
				appTaxableAmt = appTaxableAmt + getgrnReport.get(i).getAprTaxableAmt();
				appCgstSum = appCgstSum + getgrnReport.get(i).getAprCgstRs();
				appSgstSum = appSgstSum + getgrnReport.get(i).getAprSgstRs();
				appIgstSum = appIgstSum + getgrnReport.get(i).getAprIgstRs();
				grandTotal = grandTotal + getgrnReport.get(i).getAprGrandTotal();

				expoExcel.setRowData(rowData);
				exportToExcelList.add(expoExcel);

			}
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();
			rowData.add("");
			rowData.add("Total");
			rowData.add("");
			rowData.add("");

			rowData.add("" + roundUp(taxableAmt));
			rowData.add("" + roundUp(totalTax));
			rowData.add("" + roundUp(grnGvnAmt));
			rowData.add("" + roundUp(appTaxableAmt));
			rowData.add("" + roundUp(appCgstSum));
			rowData.add("" + roundUp(appSgstSum));
			rowData.add("" + roundUp(appIgstSum));
			rowData.add("" + roundUp(grandTotal));

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

			HttpSession session = request.getSession();
			session.setAttribute("exportExcelListNew", exportToExcelList);
			session.setAttribute("excelNameNew", "GrnGvnReport");
			session.setAttribute("reportNameNew", "View GVN Report");
			session.setAttribute("searchByNew", "From Date: " + fromDate + "  To Date: " + toDate + " ");
			session.setAttribute("mergeUpto1", "$A$1:$L$1");
			session.setAttribute("mergeUpto2", "$A$2:$L$2");
		} catch (Exception e) {
			e.printStackTrace();

		}
		return getgrnReport;
	}

	@RequestMapping(value = "pdf/getgvnReportPdf/{fromDate}/{toDate}/{frId}", method = RequestMethod.GET)
	public ModelAndView getgvnReportPdf(@PathVariable String fromDate, @PathVariable String toDate,
			@PathVariable int frId, HttpServletRequest request, HttpServletResponse response) {

		ModelAndView model = new ModelAndView("report/sellReport/sellReportPdf/gvnReportPdf");
		try {
			List<GrnGvnReport> getgrnReport = new ArrayList<GrnGvnReport>();
			String isGrn = "0,2";
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frId);
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("isGrn", isGrn);
			RestTemplate rest = new RestTemplate();
			getgrnReport = rest.postForObject(Constant.URL + "/grnGvnReport", map, List.class);

			map = new LinkedMultiValueMap<String, Object>();

			map.add("frId", frId);
			Franchisee franchisee = rest.getForObject(Constant.URL + "getFranchisee?frId={frId}", Franchisee.class,
					frId);

			System.out.println("getgrnReport " + getgrnReport);
			model.addObject("reportList", getgrnReport);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
			model.addObject("frName", franchisee.getFrName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;

	}

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

	@RequestMapping(value = "/tSellReport", method = RequestMethod.GET)
	public void showPDF(HttpServletRequest request, HttpServletResponse response) {

		String url = request.getParameter("reportURL");

		File f = new File(Constant.SELL_REPORT_PATH);
		// File f = new File("C:/pdf/ordermemo221.pdf");
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
		// String filePath = "/opt/tomcat-latest/webapps/uploads/ordermemo.pdf";
		String filePath =Constant.SELL_REPORT_PATH;
		// String filePath = "C:/pdf/ordermemo221.pdf";

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

}
