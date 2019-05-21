package com.monginis.ops.otheritemscontroller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.ExportToExcel;
import com.monginis.ops.model.FrSupplier;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.OtherItemStockDetail;
import com.monginis.ops.model.OtherItemStockHeader;
import com.monginis.ops.model.othitemstock.OtherItemCurStock;

@Controller
public class OtherItemStockController {
	
	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	List<OtherItemCurStock> otherStockList = new ArrayList<OtherItemCurStock>();
	MultiValueMap<String, Object> map = null;
	RestTemplate rest = new RestTemplate();

	@RequestMapping(value = "/showOthItemStock", method = RequestMethod.GET)
	public ModelAndView showOthItemStock(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		otherStockList = new ArrayList<OtherItemCurStock>();

		map = new LinkedMultiValueMap<String, Object>();
		map.add("frId", frDetails.getFrId());

		FrSupplier[] list = rest.postForObject(Constant.URL + "/getAllFrSupplierListByFrId", map, FrSupplier[].class);
		ArrayList<FrSupplier> supplierList = new ArrayList<>(Arrays.asList(list));
		System.out.println("Supplier List=" + supplierList);

		ModelAndView model = new ModelAndView("stock/other_item_stock");

		Calendar calendar = Calendar.getInstance();

		SimpleDateFormat monthFormat = new SimpleDateFormat("MMMMM");

		String monthStr = monthFormat.format(calendar.getTime());
		System.out.println("Maximum for " + monthStr + " is " + calendar.getActualMaximum(Calendar.DATE));

		boolean isLastDay = calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE);
		System.out.println("The calendar date " + (isLastDay ? "is " : "is not ") + "the last day of the month.");

		map = new LinkedMultiValueMap<>();
		map.add("frId", frDetails.getFrId());

		OtherItemStockHeader othStkHead = new OtherItemStockHeader();

		List<OtherItemStockHeader> stockHeader = null;
		OtherItemStockHeader[] stockHeadObj = rest.postForObject(Constant.URL + "/getOtherStockHeaderByFrId", map,
				OtherItemStockHeader[].class);
		stockHeader = new ArrayList<>(Arrays.asList(stockHeadObj));
		int month;
		GregorianCalendar date = new GregorianCalendar();
		month = date.get(Calendar.MONTH);
		month = month + 1;
		int isMonthEndAppli = 0;
		// System.err.println("stock month " +stockHeader.get(0).getMonth());
		System.err.println("Month " + month);
		if (stockHeader.size() > 0)
			if (month > stockHeader.get(0).getMonth()) {
				isMonthEndAppli = 1;
			} else if (stockHeader.get(0).getMonth() == 12 && month == 1) {

				isMonthEndAppli = 1;

			}
		System.err.println("isMonthEndAppli " + isMonthEndAppli);
		model.addObject("isMonthEndAppli", isMonthEndAppli);
		model.addObject("supplierList", supplierList);
		return model;

	}

	@RequestMapping(value = "/getOtherStock", method = RequestMethod.GET)
	public @ResponseBody List<OtherItemCurStock> getOtherStock(HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		otherStockList = new ArrayList<OtherItemCurStock>();

		String showOption = request.getParameter("show_option");

		if (showOption.equals("1")) {

			map = new LinkedMultiValueMap<>();
			map.add("frId", frDetails.getFrId());

			OtherItemStockHeader othStkHead = new OtherItemStockHeader();

			List<OtherItemStockHeader> stockHeader = null;
			OtherItemStockHeader[] stockHeadObj = rest.postForObject(Constant.URL + "/getOtherStockHeaderByFrId", map,
					OtherItemStockHeader[].class);
			stockHeader = new ArrayList<>(Arrays.asList(stockHeadObj));

			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

			Date todaysDate = new Date();

			String strDate = stockHeader.get(0).getYear() + "/" + stockHeader.get(0).getMonth() + "/01";
			System.err.println("str Date from Date  " + strDate);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", strDate);
			map.add("toDate", dateFormat.format(todaysDate));
			map.add("month", stockHeader.get(0).getMonth());
			map.add("catId", 7);

			OtherItemCurStock[] list = rest.postForObject(Constant.URL + "/getOtherItemCurStock", map,
					OtherItemCurStock[].class);
			otherStockList = new ArrayList<>(Arrays.asList(list));

		} else {

			String fromDate = request.getParameter("fromDate");

			String toDate = request.getParameter("toDate");

			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
			String fr = null;
			String to = null;
			try {
				fr = sdf1.format(sdf2.parse(fromDate));

				to = sdf1.format(sdf2.parse(toDate));
			} catch (ParseException e) {

				e.printStackTrace();
			}

			Calendar cal = Calendar.getInstance();
			Date fDate1 = null;
			try {
				fDate1 = sdf1.parse(fr);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cal.setTime(fDate1);
			cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
			Date d1 = cal.getTime();
			// int month=fDate1.getMonth();

			int month = cal.get(Calendar.MONTH) + 1;

			System.err.println("month is " + month);

			System.err.println("Its Stock Bet Date d1 =   " + d1);

			String prevFromDate = sdf1.format(d1);

			String prevToDate = incrementDate(fr, -1);

			System.err.println("prevFromDate =   " + prevFromDate + "prev toDate " + prevToDate);

			System.out.println("FromDate " + fr);

			System.out.println("toDate " + to);

			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", fr);
			map.add("toDate", to);
			map.add("prevFromDate", prevFromDate);
			map.add("prevToDate", prevToDate);
			map.add("catId", 7);
			map.add("month", month);

			OtherItemCurStock[] list = rest.postForObject(Constant.URL + "/getOtherItemStockBetDate", map,
					OtherItemCurStock[].class);
			otherStockList = new ArrayList<>(Arrays.asList(list));
		}
		System.err.println("Stock list " + otherStockList.toString());

		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No.");
		rowData.add("Item Code");
		rowData.add("Item Name");
		rowData.add("Opening Stock");
		rowData.add("Purchase Qty");
		rowData.add("Sale Qty");
		rowData.add("Damage Qty");
		rowData.add("Current Stock");

		double openingStockTotal = 0;
		double purQtyTotal = 0;
		double saleQtytotal = 0;
		double damageQtyTotal = 0;
		double curStockTotal = 0;

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < otherStockList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			openingStockTotal = openingStockTotal + otherStockList.get(i).getOpeningStock();
			purQtyTotal = purQtyTotal + otherStockList.get(i).getPurchaseQty();
			saleQtytotal = saleQtytotal + otherStockList.get(i).getSellQty();
			damageQtyTotal = damageQtyTotal + otherStockList.get(i).getDamagedStock();
			curStockTotal = curStockTotal + (otherStockList.get(i).getOpeningStock()
					+ otherStockList.get(i).getPurchaseQty() - otherStockList.get(i).getSellQty());

			rowData.add("" + (i + 1));
			rowData.add("" + otherStockList.get(i).getItemId());

			rowData.add("" + otherStockList.get(i).getItemName());
			rowData.add("" + otherStockList.get(i).getOpeningStock());
			rowData.add("" + otherStockList.get(i).getPurchaseQty());

			rowData.add("" + otherStockList.get(i).getSellQty());
			rowData.add("" + otherStockList.get(i).getDamagedStock());
			rowData.add("" + (otherStockList.get(i).getOpeningStock() + otherStockList.get(i).getPurchaseQty()
					- otherStockList.get(i).getSellQty()));

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		expoExcel = new ExportToExcel();
		rowData = new ArrayList<String>();
		rowData.add("");
		rowData.add("");

		rowData.add("Total");
		rowData.add(" " + openingStockTotal);
		rowData.add(" " + purQtyTotal);
		rowData.add(" " + saleQtytotal);
		rowData.add(" " + damageQtyTotal);
		rowData.add(" " + curStockTotal);

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);

		session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Other Item Stock Detail");

		return otherStockList;

	}

	public String incrementDate(String date, int day) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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

	@RequestMapping(value = "/getOtherItemStockPdf", method = RequestMethod.GET)
	public void getOtherItemStockPdf(HttpServletRequest request, HttpServletResponse response) {

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

		PdfPTable table = new PdfPTable(8);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 0.5f, 1.8f, 1.8f, 1.2f, 1.0f, 1.2f, 1.2f, 1.2f });
			Font headFont = new Font(FontFamily.HELVETICA, 10, Font.NORMAL, BaseColor.BLACK);
			Font headFont1 = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
			Font f = new Font(FontFamily.TIMES_ROMAN, 12.0f, Font.UNDERLINE, BaseColor.BLUE);

			PdfPCell hcell;
			hcell = new PdfPCell(new Phrase("Sr.No.", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Code", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Item Name", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Opening Stock", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Purchase Qty", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Sale Qty", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Damage Qty", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			hcell = new PdfPCell(new Phrase("Current Stock", headFont1));
			hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
			table.addCell(hcell);

			int index = 0;
			double openingStockTotal = 0;
			double pureQtyTotal = 0;
			double saleQtytotal = 0;
			double damageQtyTotal = 0;
			double curStockTotal = 0;

			for (OtherItemCurStock advance : otherStockList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(advance.getItemId(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(advance.getItemName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				openingStockTotal = openingStockTotal + advance.getOpeningStock();
				pureQtyTotal = pureQtyTotal + advance.getPurchaseQty();
				saleQtytotal = saleQtytotal + advance.getSellQty();
				damageQtyTotal = damageQtyTotal + advance.getDamagedStock();
				curStockTotal = curStockTotal + advance.getOpeningStock() + advance.getPurchaseQty()
						- advance.getSellQty();

				cell = new PdfPCell(new Phrase(String.valueOf(advance.getOpeningStock()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(advance.getPurchaseQty()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(advance.getSellQty()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(advance.getDamagedStock()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(
						String.valueOf(advance.getOpeningStock() + advance.getPurchaseQty() - advance.getSellQty()),
						headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

			}
			
			PdfPCell cell;

			cell = new PdfPCell(new Phrase("", headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("", headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("Total", headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) openingStockTotal), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

		 

			cell = new PdfPCell(new Phrase("" + roundUp((float) pureQtyTotal), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) saleQtytotal), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) damageQtyTotal), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell);

			cell = new PdfPCell(new Phrase("" + roundUp((float) curStockTotal), headFont));
			cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setPaddingRight(2);
			cell.setPadding(3);
			table.addCell(cell); 
		 
			

			doc.open();

			Paragraph heading = new Paragraph("Report-Other Item Stock Detail ");
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

				BufferedInputStream inputStream = null;
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


	//

	@RequestMapping(value = "/otherItemMonthEndProcess", method = RequestMethod.POST)
	public String otherItemMonthEndProcess(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		int frId = frDetails.getFrId();
		System.err.println("Fr Id In stock Month End " + frId);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
		map.add("frId", frId);

		OtherItemStockHeader othStkHead = new OtherItemStockHeader();

		List<OtherItemStockHeader> stockHeadList = null;
		OtherItemStockHeader[] stockHeadObj = rest.postForObject(Constant.URL + "/getOtherStockHeaderByFrId", map,
				OtherItemStockHeader[].class);
		stockHeadList = new ArrayList<>(Arrays.asList(stockHeadObj));

		// stockHeadList.get(0).setStatus(1);

		OtherItemStockHeader stockHeader = stockHeadList.get(0);

		System.err.println(" In if stock Header size =0;");

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

		Date todaysDate = new Date();

		String strDate = stockHeadList.get(0).getYear() + "/" + stockHeadList.get(0).getMonth() + "/01";
		System.err.println("str Date from Date  " + strDate);

		//

		SimpleDateFormat sdf1 = new SimpleDateFormat("yyy/MM/dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy");
		Calendar cal = Calendar.getInstance();
		Date fDate1 = null;
		try {
			fDate1 = sdf1.parse(strDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(fDate1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		Date d1 = cal.getTime();

		String thisMonthLastDate = sdf1.format(d1);
		System.err.println("thisMonthLastDate   " + thisMonthLastDate);
		//

		map = new LinkedMultiValueMap<String, Object>();
		map.add("frId", frDetails.getFrId());
		map.add("fromDate", strDate);
		map.add("toDate", thisMonthLastDate);
		map.add("month", stockHeadList.get(0).getMonth());
		map.add("catId", 7);

		OtherItemCurStock[] list = rest.postForObject(Constant.URL + "/getOtherItemCurStock", map,
				OtherItemCurStock[].class);
		otherStockList = new ArrayList<>(Arrays.asList(list));

		// System.out.println("Detail List="+otherStockList);
		ModelAndView model = null;

		// System.out.println(frDetails.getFrId());

		stockHeader.setStatus(1);
		map = new LinkedMultiValueMap<String, Object>();
		map.add("headerId", stockHeader.getOtherStockHeaderId());

		List<OtherItemStockDetail> detail = null;
		OtherItemStockDetail[] detObj = rest.postForObject(
				Constant.URL + "/getStockHeaderByOtherStockHeaderIdAndOtherItemId", map, OtherItemStockDetail[].class);
		detail = new ArrayList<>(Arrays.asList(detObj));
		for (int i = 0; i < detail.size(); i++) {

			for (int j = 0; j < otherStockList.size(); j++) {

				if (detail.get(i).getOtherItemId() == otherStockList.get(j).getId()) {
					int damageQty = Integer
							.parseInt(request.getParameter("damagedStock" + otherStockList.get(j).getId()));
					System.err.println("Damged for " + otherStockList.get(j).getId() + "" + damageQty);
					int closingStock = (int) ((otherStockList.get(j).getOpeningStock()
							+ otherStockList.get(j).getPurchaseQty())
							- (otherStockList.get(j).getSellQty() + damageQty));

					detail.get(i).setDamageStock(damageQty);
					// detail.get(i).setDamageStock(1);
					detail.get(i).setClosingStock(closingStock);
					detail.get(i).setPurchaseStock((int) otherStockList.get(j).getPurchaseQty());
					detail.get(i).setSalesStock((int) otherStockList.get(j).getSellQty());

					break;
				} // end of if

			} // end of inner for
		} // end of outer for

		stockHeader.setOtherItemStockList(detail);

		System.out.println("Stock Header before insert =" + stockHeadList);

		OtherItemStockHeader stockHeadResp = rest.postForObject(Constant.URL + "/insertNewOtherStock", stockHeader,
				OtherItemStockHeader.class);

		System.err.println("Stock Header insert response " + stockHeadResp.toString());

		OtherItemStockHeader otherStockHeader = new OtherItemStockHeader();

		Calendar c1 = Calendar.getInstance();
		int year1 = c1.get(Calendar.YEAR);
		int month1 = c1.get(Calendar.MONTH);
		String data = "NA";

		otherStockHeader.setStatus(0);

		otherStockHeader.setOtherStockHeaderId(0);
		otherStockHeader.setFrId(frDetails.getFrId());

		if (stockHeader.getMonth() != 12) {
			otherStockHeader.setMonth(stockHeader.getMonth() + 1);
			otherStockHeader.setYear(stockHeader.getYear());
		} else {
			otherStockHeader.setMonth(1);
			otherStockHeader.setYear(stockHeader.getYear() + 1);
		}
		otherStockHeader.setDelStatus(0);
		otherStockHeader.setExFloat1(0);
		otherStockHeader.setExInt1(0);
		otherStockHeader.setExInt2(0);
		otherStockHeader.setExVar1(data);
		otherStockHeader.setExVar2(data);

		List<OtherItemStockDetail> newStockList = null;
		newStockList = stockHeadResp.getOtherItemStockList();
		List<OtherItemStockDetail> newOtherStockList = new ArrayList<>();

		for (int i = 0; i < newStockList.size(); i++) {

			OtherItemStockDetail otherStockDetail = new OtherItemStockDetail();

			otherStockDetail.setOtherStockDetailId(0);
			otherStockDetail.setOtherStockHeaderId(0);

			otherStockDetail.setOtherItemId(newStockList.get(i).getOtherItemId());
			otherStockDetail.setOpeningStock(newStockList.get(i).getClosingStock());
			otherStockDetail.setPurchaseStock(0);
			otherStockDetail.setSalesStock(0);
			otherStockDetail.setClosingStock(0);
			otherStockDetail.setDamageStock(0);
			otherStockDetail.setDelStatus(0);
			otherStockDetail.setExFloat1(0);
			otherStockDetail.setExFloat2(0);
			otherStockDetail.setExInt1(0);
			otherStockDetail.setExInt2(0);
			otherStockDetail.setExVar1(data);
			otherStockDetail.setExVar2(data);
			System.err.println("other stock  " + otherStockDetail.toString());

			newOtherStockList.add(otherStockDetail);

		}

		otherStockHeader.setOtherItemStockList(newOtherStockList);

		OtherItemStockHeader stockHeadRespNew = rest.postForObject(Constant.URL + "/insertNewOtherStock",
				otherStockHeader, OtherItemStockHeader.class);

		return "redirect:/showOthItemStock";

	}

	/*
	 * BETWEEN DATE QUERY CONTINUE SELECT m_item.id,m_item.item_name,m_item.item_id,
	 * m_franchisee.fr_name,
	 * 
	 * COALESCE((SELECT SUM(other_item_stock_detail.opening_stock) FROM
	 * other_item_stock_detail,other_item_stock_header WHERE
	 * m_franchisee.fr_id=other_item_stock_header.fr_id and
	 * other_item_stock_header.other_stock_header_id=other_item_stock_detail.
	 * other_stock_header_id AND other_item_stock_detail.other_item_id=m_item.id AND
	 * other_item_stock_header.month IN(2,3)),0) as opening_stock,
	 * 
	 * COALESCE((SELECT sum(other_item_stock_detail.damage_stock) FROM
	 * other_item_stock_detail,other_item_stock_header WHERE
	 * other_item_stock_header.month=2 AND
	 * m_franchisee.fr_id=other_item_stock_header.fr_id and
	 * other_item_stock_header.other_stock_header_id=other_item_stock_detail.
	 * other_stock_header_id AND other_item_stock_detail.other_item_id=m_item.id AND
	 * other_item_stock_header.month IN(2,3)),0) as damaged_stock1,
	 * 
	 * COALESCE((SELECT SUM(t_other_bill_detail.bill_qty) FROM
	 * t_other_bill_header,t_other_bill_detail WHERE
	 * t_other_bill_header.bill_no=t_other_bill_detail.bill_no AND
	 * t_other_bill_header.fr_id=m_franchisee.fr_id AND
	 * t_other_bill_header.bill_date BETWEEN '2019-02-01' AND '2019-02-28' AND
	 * t_other_bill_detail.item_id=m_item.id),0) AS purchase_qty1,
	 * 
	 * COALESCE((SELECT SUM(t_sell_bill_detail.qty) FROM
	 * t_sell_bill_detail,t_sell_bill_header WHERE
	 * t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no AND
	 * t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.bill_date
	 * BETWEEN '2019-02-01' AND '2019-02-28' AND
	 * t_sell_bill_detail.item_id=m_item.id),0) AS sell_qty1,
	 * 
	 * 
	 * 
	 * COALESCE((SELECT sum(other_item_stock_detail.damage_stock) FROM
	 * other_item_stock_detail,other_item_stock_header WHERE
	 * other_item_stock_header.month=2 AND
	 * m_franchisee.fr_id=other_item_stock_header.fr_id and
	 * other_item_stock_header.other_stock_header_id=other_item_stock_detail.
	 * other_stock_header_id AND other_item_stock_detail.other_item_id=m_item.id),0)
	 * as damaged_stock,
	 * 
	 * COALESCE((SELECT SUM(t_other_bill_detail.bill_qty) FROM
	 * t_other_bill_header,t_other_bill_detail WHERE
	 * t_other_bill_header.bill_no=t_other_bill_detail.bill_no AND
	 * t_other_bill_header.fr_id=m_franchisee.fr_id AND
	 * t_other_bill_header.bill_date BETWEEN '2019-02-01' AND '2019-02-28' AND
	 * t_other_bill_detail.item_id=m_item.id),0) AS purchase_qty,
	 * 
	 * COALESCE((SELECT SUM(t_sell_bill_detail.qty) FROM
	 * t_sell_bill_detail,t_sell_bill_header WHERE
	 * t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no AND
	 * t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.bill_date
	 * BETWEEN '2019-02-01' AND '2019-02-28' AND
	 * t_sell_bill_detail.item_id=m_item.id),0) AS sell_qty
	 * 
	 * FROM m_item,m_franchisee
	 * 
	 * WHERE m_item.item_grp1=7 AND m_franchisee.fr_id=113 GROUP by m_item.id
	 * 
	 * 
	 * STOCK BET DATE 15-30 march
	 * 
	 * firstopening=sum(opening) where stock.month in(month(fromDate),
	 * month(toDate)) purchase1=sum(purchase) date bet 1-3 to 1-14 sell1 =sum(sell)
	 * date bet 1-3 to 1-14 damage1 sum(damage) where stock.month
	 * in(month(fromDate), month(toDate))
	 * 
	 * 
	 * purchase= bet 15-30 march sell= bet 15-30 march damage in damage1 sum(damage)
	 * where stock.month in(month(fromDate))
	 * 
	 * tempOpe=(firstopening+purchase1)-(sell1+damage1)
	 * 
	 * curStock=(tempOpe+purchase)-(sell+damage)
	 * 
	 */

}
