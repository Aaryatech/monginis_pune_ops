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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
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
import com.monginis.ops.billing.GetBillDetail;
import com.monginis.ops.billing.Info;
import com.monginis.ops.common.DateConvertor;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.ExportToExcel;
import com.monginis.ops.model.FrSupplier;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.GetBillHeader;
import com.monginis.ops.model.Item;
import com.monginis.ops.model.ItemResponse;
import com.monginis.ops.model.OtherBillDetail;
import com.monginis.ops.model.OtherBillHeader;
import com.monginis.ops.model.OtherItemStockDetail;
import com.monginis.ops.model.OtherItemStockHeader;
import com.monginis.ops.model.OtherStockItem;
import com.monginis.ops.model.otheritems.Otheritems;

@Controller
public class OtherItemsController {

	List<OtherBillDetail> otherBillDetailList = new ArrayList<OtherBillDetail>();
	RestTemplate rest = new RestTemplate();
	MultiValueMap<String, Object> map = null;

	@RequestMapping(value = "/toOtherItem", method = RequestMethod.GET)
	public ModelAndView toOtherItem(HttpServletRequest request, HttpServletResponse response) {

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		otherBillDetailList = new ArrayList<OtherBillDetail>();

		map = new LinkedMultiValueMap<String, Object>();
		map.add("frId", frDetails.getFrId());

		FrSupplier[] list = rest.postForObject(Constant.URL + "/getAllFrSupplierListByFrId", map, FrSupplier[].class);
		ArrayList<FrSupplier> supplierList = new ArrayList<>(Arrays.asList(list));
		System.out.println("Supplier List=" + supplierList);

		ModelAndView mav = new ModelAndView("otheritems/addOtherItem");
		List<Otheritems> otitem = rest.getForObject(Constant.URL + "/getOtherItemListByDel", List.class);
		System.out.println(otitem);

		mav.addObject("items", otitem);
		mav.addObject("supplierList", supplierList);
		return mav;

	}

	@RequestMapping(value = "/insertOtherItem", method = RequestMethod.GET)
	public @ResponseBody List<OtherBillDetail> newOtherItem(HttpServletRequest req, HttpServletResponse resp) {

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			System.out.println("hi There");
			float mrpBaseRate = 0;
			int qty = 0;
			float discPer = 00;
			int id = 0;
			float rate = 0;

			id = Integer.parseInt(req.getParameter("id"));
			rate = Float.parseFloat(req.getParameter("rate"));
			discPer = Float.parseFloat(req.getParameter("discPer"));
			qty = Integer.parseInt(req.getParameter("qty"));
			// System.out.println("Data Disc Qty="+id+" "+discPer+" "+qty);
			map.add("id", id);
			Otheritems item = rest.postForObject(Constant.URL + "/getItemById", map, Otheritems.class);
			// System.out.println("List2="+item);

			mrpBaseRate = rate;
			// System.out.println("Base Rate without tax: "+mrpBaseRate);

			mrpBaseRate = (rate * 100) / (100 + item.getCgstPer() + item.getSgstPer());
			mrpBaseRate = roundUp(mrpBaseRate);
			// System.out.println("Base Rate with tax: "+mrpBaseRate);

			float taxableAmt = mrpBaseRate * qty;
			// System.out.println("taxableAmt:"+taxableAmt);

			float discAmt = (taxableAmt * discPer) / 100;
			// System.out.println("discAmt:"+discAmt);

			taxableAmt = taxableAmt - discAmt;
			// System.out.println("taxableAmt: "+taxableAmt);

			float sgstRs = (taxableAmt * item.getSgstPer()) / 100;
			sgstRs = roundUp(sgstRs);
			// System.out.println("sgstRs: "+sgstRs);

			float cgstRs = (taxableAmt * item.getCgstPer()) / 100;
			cgstRs = roundUp(cgstRs);
			// System.out.println("cgstRs: "+cgstRs);

			float igstRs = (taxableAmt * item.getIgstPer()) / 100;
			igstRs = roundUp(igstRs);
			// System.out.println("igstRs: "+igstRs);

			float cessRs = (taxableAmt * item.getCessPer()) / 100;
			cessRs = roundUp(cessRs);
			// System.out.println("cessRs: "+cessRs);

			float totalTax = sgstRs + cgstRs;
			// System.out.println("totalTax: "+totalTax);

			float grandTotal = totalTax + taxableAmt;
			// System.out.println("grandTotal: "+grandTotal);

			discAmt = roundUp(discAmt);
			System.out.println("discAmt: " + discAmt);
			taxableAmt = roundUp(taxableAmt);
			System.out.println("taxableAmt: " + taxableAmt);
			grandTotal = roundUp(grandTotal);
			System.out.println("grandTotal: " + grandTotal);
			grandTotal = (float) Math.ceil((double) grandTotal);

			// OtherBillDetail bill = new OtherBillDetail();
			System.out.println("up List:" + otherBillDetailList.toString());
			if (otherBillDetailList.isEmpty()) {

				System.out.println("inside first if");
				OtherBillDetail bill = new OtherBillDetail();
				bill.setBillDetailNo(0);
				bill.setBillNo(0);
				bill.setItemId(id);
				bill.setMrp(rate);
				bill.setBaseRate(mrpBaseRate);
				bill.setBillQty(qty);

				bill.setCessPer(item.getCessPer());
				bill.setCessPer(cessRs);

				bill.setCgstPer(item.getCgstPer());
				bill.setCgstRs(cgstRs);

				bill.setSgstPer(item.getSgstPer());
				bill.setSgstRs(sgstRs);

				bill.setIgstPer(item.getIgstPer());
				bill.setIgstRs(igstRs);

				bill.setDiscPer(discPer);
				bill.setDiscRs(discAmt);

				bill.setTaxableAmt(taxableAmt);
				bill.setTotalTax(totalTax);
				bill.setGrandTotal(grandTotal);

				bill.setDelStatus(1);
				bill.setCatId(0);
				bill.setGrnType(0);
				bill.setMenuId(0);
				bill.setIsGrngvnApplied(0);
				bill.setExpiryDate("0000-00-00");
				otherBillDetailList.add(bill);
			} else {
				int itemPresent = 0;
				for (int i = 0; i < otherBillDetailList.size(); i++) {
					System.out.println("inside for");
					if (id == otherBillDetailList.get(i).getItemId()) {
						itemPresent = 1;
						System.out.println("inside for if:" + itemPresent);
						otherBillDetailList.get(i).setBillDetailNo(0);
						otherBillDetailList.get(i).setBillNo(0);
						otherBillDetailList.get(i).setItemId(id);
						otherBillDetailList.get(i).setMrp(rate);
						otherBillDetailList.get(i).setBaseRate(mrpBaseRate);
						otherBillDetailList.get(i).setBillQty(qty);

						otherBillDetailList.get(i).setCessPer(item.getCessPer());
						otherBillDetailList.get(i).setCessPer(cessRs);

						otherBillDetailList.get(i).setCgstPer(item.getCgstPer());
						otherBillDetailList.get(i).setCgstRs(cgstRs);

						otherBillDetailList.get(i).setSgstPer(item.getSgstPer());
						otherBillDetailList.get(i).setSgstRs(sgstRs);

						otherBillDetailList.get(i).setIgstPer(item.getIgstPer());
						otherBillDetailList.get(i).setIgstRs(igstRs);

						otherBillDetailList.get(i).setDiscPer(discPer);
						otherBillDetailList.get(i).setDiscRs(discAmt);

						otherBillDetailList.get(i).setTaxableAmt(taxableAmt);
						otherBillDetailList.get(i).setTotalTax(totalTax);
						otherBillDetailList.get(i).setGrandTotal(grandTotal);

						otherBillDetailList.get(i).setDelStatus(1);
						otherBillDetailList.get(i).setCatId(0);
						otherBillDetailList.get(i).setGrnType(0);
						otherBillDetailList.get(i).setMenuId(0);
						otherBillDetailList.get(i).setIsGrngvnApplied(0);
						otherBillDetailList.get(i).setExpiryDate("0000-00-00");
					}
				}

				//
				// itemPresent = 0;
				if (itemPresent == 0) {
					System.out.println("itemPresent : " + itemPresent);
					OtherBillDetail bill = new OtherBillDetail();
					bill.setBillDetailNo(0);
					bill.setBillNo(0);
					bill.setItemId(id);
					bill.setMrp(rate);
					bill.setBaseRate(mrpBaseRate);
					bill.setBillQty(qty);

					bill.setCessPer(item.getCessPer());
					bill.setCessPer(cessRs);

					bill.setCgstPer(item.getCgstPer());
					bill.setCgstRs(cgstRs);

					bill.setSgstPer(item.getSgstPer());
					bill.setSgstRs(sgstRs);

					bill.setIgstPer(item.getIgstPer());
					bill.setIgstRs(igstRs);

					bill.setDiscPer(discPer);
					bill.setDiscRs(discAmt);

					bill.setTaxableAmt(taxableAmt);
					bill.setTotalTax(totalTax);
					bill.setGrandTotal(grandTotal);

					bill.setDelStatus(1);
					bill.setCatId(0);
					bill.setGrnType(0);
					bill.setMenuId(0);
					bill.setIsGrngvnApplied(0);
					bill.setExpiryDate("0000-00-00");
					otherBillDetailList.add(bill);

				}

			}
			System.out.println("Ifelse out");

			System.err.println("List Found : " + otherBillDetailList);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return otherBillDetailList;

	}

	@RequestMapping(value = "/insertOtherBill", method = RequestMethod.POST)
	public String insertBill(HttpServletRequest req, HttpServletResponse resp) {

		ModelAndView model = null;

		int suppId = Integer.parseInt(req.getParameter("suppId"));
		String billDate = req.getParameter("billDate");
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String time = formatter.format(date);

		OtherBillHeader header = new OtherBillHeader();

		float grandTotal = 0;
		float taxableAmt = 0;
		float totalTaxPer = 0.0f;
		float discAmt = 0;
		float cgstAmtTotal = 0;
		float sgstAmtTotal = 0;
		float igstAmtTotal = 0;
		float totalTax = 0;

		HttpSession session = req.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		header.setBillNo(0);
		header.setFrId(frDetails.getFrId());
		header.setFrCode("000");
		header.setSuppId(suppId);
		header.setTime(time);
		header.setInvoiceNo("00/00");
		header.setBillDate(billDate);
		header.setDelStatus(0);
		for (int i = 0; i < otherBillDetailList.size(); i++) {
			if (otherBillDetailList.get(i).getDelStatus() == 1) {
				grandTotal = otherBillDetailList.get(i).getGrandTotal() + grandTotal;
				taxableAmt = otherBillDetailList.get(i).getTaxableAmt() + taxableAmt;
				discAmt = otherBillDetailList.get(i).getDiscRs() + discAmt;
				cgstAmtTotal = cgstAmtTotal + otherBillDetailList.get(i).getCgstRs();
				sgstAmtTotal = sgstAmtTotal + otherBillDetailList.get(i).getSgstRs();
				igstAmtTotal = igstAmtTotal + otherBillDetailList.get(i).getIgstRs();
				totalTax = totalTax + otherBillDetailList.get(i).getTotalTax();
				totalTaxPer = totalTaxPer
						+ (otherBillDetailList.get(i).getCgstPer() + otherBillDetailList.get(i).getSgstPer());
			}
		}

		header.setCgstSum(roundUp(cgstAmtTotal));
		header.setSgstSum(roundUp(sgstAmtTotal));
		header.setIgstSum(roundUp(igstAmtTotal));
		header.setTotalTax(roundUp(totalTax));

		header.setRoundOff(0);
		header.setTaxApplicable(totalTaxPer);
		header.setGrandTotal(roundUp(grandTotal));
		header.setTaxableAmt(roundUp(taxableAmt));
		header.setDiscAmt(roundUp(discAmt));
		header.setOtherBillDetailList(otherBillDetailList);

		OtherBillHeader insertBillHeader = rest.postForObject(Constant.URL + "/postOtherBillHeaderAndDetail", header,
				OtherBillHeader.class);
		System.out.println("*****************************************");
		System.out.println("Header List:" + insertBillHeader);
		System.out.println("*****************************************");

		if (insertBillHeader != null) {
			System.out.println("sucess");
		} else {
			System.err.println("failed");
		}

		return "redirect:/toOtherItem";

	}

	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	@RequestMapping(value = "/viewOtherItemBill", method = RequestMethod.GET)
	public ModelAndView viewOtherItemBill() {
		System.out.println("View Bill Service Called");
		ModelAndView mav = new ModelAndView("otheritems/showOtherItembills");

		try {

			String fromDate = null, toDate = null;
			Calendar date = Calendar.getInstance();
			date.set(Calendar.DAY_OF_MONTH, 1);

			Date firstDate = date.getTime();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-YYYY");
			fromDate = dateFormat.format(firstDate);
			toDate = dateFormat.format(new Date());
			mav.addObject("fromDate", fromDate);
			mav.addObject("toDate", toDate);
			// Get All Suppliers

		} catch (Exception e) {
			e.printStackTrace();
		}

		return mav;
	}

	@RequestMapping(value = "/getOtherBillBetweenDate1", method = RequestMethod.GET)
	@ResponseBody
	public List<GetBillHeader> getOtherBillBetweenDate(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		List<GetBillHeader> otherBillHeaderlist = new ArrayList<>();
		try {
			int suppId = 0;
			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			System.out.println("Dates:" + fromDate + " " + toDate + " " + frDetails.getFrId());

			// suppId = Integer.parseInt(request.getParameter("suppId"));
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", DateConvertor.convertToYMD(fromDate));
			map.add("toDate", DateConvertor.convertToYMD(toDate));
			map.add("suppId", suppId);
			System.out.println("map" + map);
			RestTemplate rest = new RestTemplate();
			GetBillHeader[] list = rest.postForObject(Constant.URL + "/getOtherBillHeaderBetweenDate", map,
					GetBillHeader[].class);
			otherBillHeaderlist = new ArrayList<>(Arrays.asList(list));
			System.out.println("otherBillHeaderlist " + otherBillHeaderlist);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return otherBillHeaderlist;
	}

	List<GetBillDetail> billItemList = null;
	GetBillHeader billHead = null;

	@RequestMapping(value = "/editBill/{billNo}", method = RequestMethod.GET)
	public ModelAndView editBill(HttpServletRequest request, HttpServletResponse response, @PathVariable int billNo) {

		ModelAndView model = new ModelAndView("otheritems/editOtherItem");

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		map = new LinkedMultiValueMap<String, Object>();
		map.add("frId", frDetails.getFrId());

		FrSupplier[] list = rest.postForObject(Constant.URL + "/getAllFrSupplierListByFrId", map, FrSupplier[].class);
		ArrayList<FrSupplier> supplierList = new ArrayList<>(Arrays.asList(list));
		System.out.println("Supplier List On Edit=" + supplierList);
		model.addObject("supplierList", supplierList);

		System.out.println("Bill No:" + billNo);
		map.add("billNo", billNo);
		billHead = rest.postForObject(Constant.URL + "/getBillHeaderById", map, GetBillHeader.class);
		System.out.println("Bill Head Return:" + billHead);
		model.addObject("billHead", billHead);

		GetBillDetail[] billItemListRes = rest.postForObject(Constant.URL + "/getBillOthertems", map,
				GetBillDetail[].class);
		billItemList = new ArrayList<>(Arrays.asList(billItemListRes));
		System.out.println("Edit Item List:" + billItemList);
		model.addObject("billItemList", billItemList);

		return model;

	}

	@RequestMapping(value = "/editOtherBillItem", method = RequestMethod.GET)
	public @ResponseBody List<GetBillDetail> editOtherItem(HttpServletRequest req, HttpServletResponse resp) {

		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		System.out.println("hi There");
		float mrpBaseRate = 0;
		float mrp = 0;
		int qty = 0;
		float discPer = 00;
		int id = 0;
		float rate = 0;
		try {
			id = Integer.parseInt(req.getParameter("id"));
			rate = Float.parseFloat(req.getParameter("rate"));
			discPer = Float.parseFloat(req.getParameter("discPer"));
			qty = Integer.parseInt(req.getParameter("qty"));

			System.out.println("Data=" + id + " " + rate + " " + discPer + " " + qty);

			for (int i = 0; i < billItemList.size(); i++) {
				System.out.println("Inside For:" + id);
				if (id == billItemList.get(i).getBillDetailNo()) {
					mrp = billItemList.get(i).getMrp();
					mrpBaseRate = (mrp * 100)
							/ (100 + billItemList.get(i).getCgstPer() + billItemList.get(i).getSgstPer());
					mrpBaseRate = roundUp(mrpBaseRate);

					float taxableAmt = mrpBaseRate * qty;

					float discAmt = (taxableAmt * discPer) / 100;

					taxableAmt = taxableAmt - discAmt;

					float sgstRs = (taxableAmt * billItemList.get(i).getSgstPer()) / 100;
					sgstRs = roundUp(sgstRs);

					float cgstRs = (taxableAmt * billItemList.get(i).getCgstPer()) / 100;
					cgstRs = roundUp(cgstRs);

					float igstRs = (taxableAmt * billItemList.get(i).getIgstPer()) / 100;
					igstRs = roundUp(igstRs);

					float cessRs = (taxableAmt * billItemList.get(i).getCgstPer()) / 100;
					cessRs = roundUp(cessRs);

					float totalTax = sgstRs + cgstRs;

					float grandTotal = totalTax + taxableAmt;

					discAmt = roundUp(discAmt);
					System.out.println("discAmt: " + discAmt);
					taxableAmt = roundUp(taxableAmt);
					System.out.println("taxableAmt: " + taxableAmt);
					grandTotal = roundUp(grandTotal);
					System.out.println("grandTotal: " + grandTotal);
					grandTotal = (float) Math.ceil((double) grandTotal);

					billItemList.get(i).setMrp(mrp);
					billItemList.get(i).setBaseRate(mrpBaseRate);
					billItemList.get(i).setBillQty(qty);

					// sac comment
					// //billItemList.get(i).setCessPer(billItemList.get(i).getCessPer());
					// billItemList.get(i).setCessRs(cessRs);

					billItemList.get(i).setCgstPer(billItemList.get(i).getCgstPer());
					billItemList.get(i).setCgstRs(cgstRs);

					billItemList.get(i).setSgstPer(billItemList.get(i).getSgstPer());
					billItemList.get(i).setSgstRs(sgstRs);

					billItemList.get(i).setIgstPer(billItemList.get(i).getIgstPer());
					billItemList.get(i).setIgstRs(igstRs);

					billItemList.get(i).setDiscPer(discPer);
					// billItemList.get(i).setDiscRs(discAmt);

					billItemList.get(i).setTaxableAmt(taxableAmt);
					billItemList.get(i).setTotalTax(totalTax);
					billItemList.get(i).setGrandTotal(grandTotal);

				}
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}

		System.out.println("Updated List" + billItemList);
		return billItemList;

	}

	@RequestMapping(value = "/insertEditedBill", method = RequestMethod.POST)
	public String insertEditedBill(HttpServletRequest req, HttpServletResponse resp) {

		ModelAndView model = null;

		String billDate = req.getParameter("billDate");
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date date = new Date();
		String time = formatter.format(date);

		OtherBillHeader header = new OtherBillHeader();

		float grandTotal = 0;
		float taxableAmt = 0;
		float totalTaxPer = 0.0f;
		float discAmt = 0;
		float cgstAmtTotal = 0;
		float sgstAmtTotal = 0;
		float igstAmtTotal = 0;
		float totalTax = 0;

		header.setBillNo(billHead.getBillNo());
		header.setFrId(billHead.getFrId());
		header.setFrCode(billHead.getFrCode());
		header.setSuppId(billHead.getSuppId());
		header.setTime(billHead.getTime());
		header.setInvoiceNo(billHead.getInvoiceNo());
		header.setBillDate(billHead.getBillDate());
		header.setDelStatus(billHead.getDelStatus());
		List<OtherBillDetail> otherEditedBillDetailList = new ArrayList<OtherBillDetail>();
		for (int i = 0; i < billItemList.size(); i++) {
			OtherBillDetail bill = new OtherBillDetail();
			bill.setBillDetailNo(billItemList.get(i).getBillDetailNo());
			bill.setBillNo(billItemList.get(i).getBillNo());
			bill.setItemId(billItemList.get(i).getItemId());
			bill.setMrp(billItemList.get(i).getMrp());
			bill.setBaseRate(billItemList.get(i).getBaseRate());
			bill.setBillQty(billItemList.get(i).getBillQty());

			// bill.setCessPer(billItemList.get(i).getCessPer());//sac c
			// bill.setCessPer(billItemList.get(i).getCessRs());

			bill.setCgstPer(billItemList.get(i).getCgstPer());
			bill.setCgstRs(billItemList.get(i).getCgstRs());

			bill.setSgstPer(billItemList.get(i).getSgstPer());
			bill.setSgstRs(billItemList.get(i).getSgstRs());

			bill.setIgstPer(billItemList.get(i).getIgstPer());
			bill.setIgstRs(billItemList.get(i).getIgstRs());

			bill.setDiscPer(billItemList.get(i).getDiscPer());
			// bill.setDiscRs(billItemList.get(i).getDiscRs());//sac c

			bill.setTaxableAmt(billItemList.get(i).getTaxableAmt());
			bill.setTotalTax(billItemList.get(i).getTotalTax());
			bill.setGrandTotal(billItemList.get(i).getGrandTotal());

			bill.setDelStatus(billItemList.get(i).getDelStatus());
			bill.setCatId(0);
			bill.setGrnType(0);
			bill.setMenuId(0);
			bill.setIsGrngvnApplied(0);
			bill.setExpiryDate("0000-00-00");
			otherEditedBillDetailList.add(bill);

			grandTotal = billItemList.get(i).getGrandTotal() + grandTotal;
			taxableAmt = billItemList.get(i).getTaxableAmt() + taxableAmt;
			// discAmt=billItemList.get(i).getDiscRs()+discAmt;//sac comment
			discAmt = 0;
			cgstAmtTotal = cgstAmtTotal + billItemList.get(i).getCgstRs();
			sgstAmtTotal = sgstAmtTotal + billItemList.get(i).getSgstRs();
			igstAmtTotal = igstAmtTotal + billItemList.get(i).getIgstRs();
			totalTax = totalTax + billItemList.get(i).getTotalTax();
			totalTaxPer = totalTaxPer + (billItemList.get(i).getCgstPer() + billItemList.get(i).getSgstPer());

		}

		header.setCgstSum(roundUp(cgstAmtTotal));
		header.setSgstSum(roundUp(sgstAmtTotal));
		header.setIgstSum(roundUp(igstAmtTotal));
		header.setTotalTax(roundUp(totalTax));

		header.setRoundOff(0);
		header.setTaxApplicable(totalTaxPer);
		header.setGrandTotal(roundUp(grandTotal));
		header.setTaxableAmt(roundUp(taxableAmt));
		header.setDiscAmt(roundUp(discAmt));
		header.setOtherBillDetailList(otherEditedBillDetailList);

		OtherBillHeader insertBillHeader = rest.postForObject(Constant.URL + "/postOtherBillHeaderAndDetail", header,
				OtherBillHeader.class);
		System.out.println("*****************************************");
		System.out.println("Header List:" + insertBillHeader);
		System.out.println("*****************************************");

		if (insertBillHeader != null) {
			System.out.println("sucess");
		} else {
			System.err.println("failed");
		}

		return "redirect:/toOtherItem";

	}

	@RequestMapping(value = "/deleteBill/{billNo}", method = RequestMethod.GET)
	public String deleteBill(HttpServletRequest request, HttpServletResponse response, @PathVariable int billNo) {
		System.out.println("Data Found");
		// try {
		System.out.println("Bill Data Id=" + billNo);
		MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
		map.add("billNo", billNo);
		Info info = rest.postForObject(Constant.URL + "/deletetBill", map, Info.class);
		/*
		 * }catch(Exception e) { System.out.println(e); }
		 */

		return "redirect:/toOtherItem";

	}

	List<OtherStockItem> getotherStockList = null;

	@RequestMapping(value = "/toOtherStock", method = RequestMethod.GET)
	public ModelAndView toOtherStock(HttpServletRequest request, HttpServletResponse response) {

		getotherStockList = new ArrayList<>();

		ObjectMapper mapper = new ObjectMapper();
		ModelAndView mav = new ModelAndView("otheritems/openingStock");

		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
			HttpSession session = request.getSession();

			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
			System.out.println(frDetails.getFrId());

			map = new LinkedMultiValueMap<>();
			map.add("frId", frDetails.getFrId());

			OtherItemStockHeader othStkHead = new OtherItemStockHeader();

			List<OtherItemStockHeader> stockHeader = null;
			OtherItemStockHeader[] stockHeadObj = rest.postForObject(Constant.URL + "/getOtherStockHeaderByFrId", map,
					OtherItemStockHeader[].class);
			stockHeader = new ArrayList<>(Arrays.asList(stockHeadObj));
			System.out.println("Stock Header List:" + stockHeader);

			map = new LinkedMultiValueMap<>();
			map.add("frId", frDetails.getFrId());
			map.add("catId", 7);

			ItemResponse itemsList = rest.postForObject(Constant.URL + "/getOtherItemsForFr", map, ItemResponse.class);

			List<Item> otherItmList = itemsList.getItemList();

			int flag = 0;

			if (stockHeader.size() > 0) {

				for (int i = 0; i < otherItmList.size(); i++) {

					flag = 0;

					for (int j = 0; j < stockHeader.get(0).getOtherItemStockList().size(); j++) {

						System.out.println("value of j" + j);
						if (stockHeader.get(0).getOtherItemStockList().get(j).getOtherItemId() == otherItmList.get(i)
								.getId()) {
							System.out.println("Record Found=" + otherItmList.get(i).getItemId());
							flag = 1;
							OtherStockItem otherStock = new OtherStockItem();
							otherStock.setOtherStockItemName(otherItmList.get(i).getItemName());
							otherStock.setOtherStockItemId(
									stockHeader.get(0).getOtherItemStockList().get(j).getOtherItemId());
							otherStock.setOpeningStock(
									stockHeader.get(0).getOtherItemStockList().get(j).getOpeningStock());
							getotherStockList.add(otherStock);
						}
					}

					if (flag == 0) {
						OtherStockItem otherStock = new OtherStockItem();

						// Otheritems objOtheritems = mapper.convertValue(otherItmList.get(i),
						// Otheritems.class);
						System.out.println("ItemId=" + " " + otherItmList.get(i).getId());
						otherStock.setOtherStockItemId(otherItmList.get(i).getId());
						otherStock.setOtherStockItemName(otherItmList.get(i).getItemName());
						otherStock.setOpeningStock(0);

						getotherStockList.add(otherStock);
					}
				}
			} else {
				System.out.println("No Record Found");
				System.out.println("otherItmList print =" + " " + otherItmList);
				for (int i = 0; i < otherItmList.size(); i++) {
					OtherStockItem otherStock = new OtherStockItem();

					otherStock.setOtherStockItemId(otherItmList.get(i).getId());
					otherStock.setOtherStockItemName(otherItmList.get(i).getItemName());
					otherStock.setOpeningStock(0);
					System.out.println("ItemId 11=" + " " + otherItmList.get(i).getId());
					getotherStockList.add(otherStock);

				}
			}

		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
		}
		List<ExportToExcel> exportToExcelList = new ArrayList<ExportToExcel>();

		ExportToExcel expoExcel = new ExportToExcel();
		List<String> rowData = new ArrayList<String>();

		rowData.add("Sr. No.");
		rowData.add("Item Code");
		rowData.add("Item Name");
		rowData.add("Opening Stock");

		expoExcel.setRowData(rowData);
		exportToExcelList.add(expoExcel);
		for (int i = 0; i < getotherStockList.size(); i++) {
			expoExcel = new ExportToExcel();
			rowData = new ArrayList<String>();

			rowData.add("" + (i + 1));
			rowData.add("" + getotherStockList.get(i).getOtherStockItemId());

			rowData.add("" + getotherStockList.get(i).getOtherStockItemName());
			rowData.add("" + getotherStockList.get(i).getOpeningStock());

			expoExcel.setRowData(rowData);
			exportToExcelList.add(expoExcel);

		}

		HttpSession session = request.getSession();
		session.setAttribute("exportExcelList", exportToExcelList);
		session.setAttribute("excelName", "Other Item Stock Detail");
		return mav;

	}

	@RequestMapping(value = "/getToOtherItemStockPdf", method = RequestMethod.GET)
	public void getToOtherItemStockPdf(HttpServletRequest request, HttpServletResponse response) {

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

		PdfPTable table = new PdfPTable(4);
		try {
			System.out.println("Inside PDF Table try");
			table.setWidthPercentage(100);
			table.setWidths(new float[] { 0.5f, 1.8f, 1.8f, 1.2f });
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

			int index = 0;

			for (OtherStockItem advance : getotherStockList) {
				index++;
				PdfPCell cell;

				cell = new PdfPCell(new Phrase(String.valueOf(index), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_CENTER);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(advance.getOtherStockItemId()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(advance.getOtherStockItemName(), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_LEFT);
				cell.setPaddingRight(5);
				table.addCell(cell);

				cell = new PdfPCell(new Phrase(String.valueOf(advance.getOpeningStock()), headFont));
				cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
				cell.setPaddingRight(5);
				table.addCell(cell);

			}

			doc.open();

			Paragraph heading = new Paragraph("Report-Other Item Opening Stock");
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
					inputStream =

							new BufferedInputStream(new FileInputStream(file));
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

	List<OtherItemStockDetail> otherStockList = null;

	@RequestMapping(value = "/insertOtherStockDetail", method = RequestMethod.GET)
	public @ResponseBody List<OtherItemStockDetail> insertOtherStockDetail(HttpServletRequest req,
			HttpServletResponse resp) {
		otherStockList = new ArrayList<>();
		try {
			int openingStock = 0;
			int stockItem = Integer.parseInt(req.getParameter("id"));
			openingStock = Integer.parseInt(req.getParameter("openingStock"));

			for (int i = 0; i < getotherStockList.size(); i++) {
				if (getotherStockList.get(i).getOtherStockItemId() == stockItem) {
					System.out.println("Opening stock123 = " + openingStock);
					getotherStockList.get(i).setOpeningStock(openingStock);
					System.err.println("other stock  " + getotherStockList.get(i).toString());

				}

			}
			System.err.println("getotherStockList " + getotherStockList.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return otherStockList;
	}

	@RequestMapping(value = "/insertOtherStockBill", method = RequestMethod.POST)
	public String insertOtherStockBill(HttpServletRequest req, HttpServletResponse resp) {
		otherStockList = new ArrayList<>();
		try {
			map = new LinkedMultiValueMap<String, Object>();
			HttpSession session = req.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
			System.out.println("FrId=" + frDetails.getFrId());
			map.add("frId", frDetails.getFrId());
			String data = "NA";

			List<OtherItemStockHeader> stockHeader = null;
			OtherItemStockHeader[] stockHeadObj = rest.postForObject(Constant.URL + "/getOtherStockHeaderByFrId", map,
					OtherItemStockHeader[].class);
			stockHeader = new ArrayList<>(Arrays.asList(stockHeadObj));
			System.out.println("Stock Header List:" + stockHeader);

			OtherItemStockHeader otherStockHeader = new OtherItemStockHeader();

			if (stockHeader.size() == 0) {
				System.err.println(" In if stock Header size =0;");

				// System.out.println("Detail List="+otherStockList);
				ModelAndView model = null;

				// System.out.println(frDetails.getFrId());

				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);

				otherStockHeader.setOtherStockHeaderId(0);
				otherStockHeader.setFrId(frDetails.getFrId());
				otherStockHeader.setMonth(month + 1);
				otherStockHeader.setYear(year);
				otherStockHeader.setDelStatus(0);
				otherStockHeader.setExFloat1(0);
				otherStockHeader.setExInt1(0);
				otherStockHeader.setExInt2(0);
				otherStockHeader.setExVar1(data);
				otherStockHeader.setExVar2(data);

				for (int i = 0; i < getotherStockList.size(); i++) {

					OtherItemStockDetail otherStockDetail = new OtherItemStockDetail();

					otherStockDetail.setOtherStockDetailId(0);
					otherStockDetail.setOtherStockHeaderId(0);

					otherStockDetail.setOtherItemId(getotherStockList.get(i).getOtherStockItemId());
					System.out.println("ItemId 22=" + " " + getotherStockList.get(i).getOtherStockItemId());
					otherStockDetail.setOpeningStock(getotherStockList.get(i).getOpeningStock());
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

					otherStockList.add(otherStockDetail);
					// System.out.println("Stock List"+otherStockList);
					System.err.println("Item Added " + otherStockDetail.getOtherItemName());
				}
				otherStockHeader.setOtherItemStockList(otherStockList);

				System.out.println("Stock Header before insert =" + otherStockHeader);

				OtherItemStockHeader stockHead = rest.postForObject(Constant.URL + "/insertNewOtherStock",
						otherStockHeader, OtherItemStockHeader.class);

				System.err.println("Stock Header insert response " + stockHead.toString());

			}

			else {

				System.err.println(" In else stock Header size >0 ;");

				System.out.println("Stock List2:" + getotherStockList);
				int stock_header_id = stockHeader.get(0).getOtherStockHeaderId();
				otherStockHeader = stockHeader.get(0);
				map.add("headerId", stock_header_id);

				List<OtherItemStockDetail> detail = null;
				OtherItemStockDetail[] headObj = rest.postForObject(
						Constant.URL + "/getStockHeaderByOtherStockHeaderIdAndOtherItemId", map,
						OtherItemStockDetail[].class);
				detail = new ArrayList<>(Arrays.asList(headObj));
				// System.out.println("Header/="+detail);
				OtherItemStockDetail otherStockDetail = new OtherItemStockDetail();

				/*
				 * for (int i = 0; i < getotherStockList.size(); i++) {
				 * 
				 * int item_id =getotherStockList.get(i).getOtherStockItemId(); int opening
				 * =getotherStockList.get(i).getOpeningStock();
				 * 
				 * int floag=0; for (int j = 0; j < detail.size(); j++) {
				 * 
				 * if (item_id==detail.get(j).getOtherItemId()) {
				 * 
				 * System.out.println("DATA000:"+stock_header_id+" "+item_id+" "+opening+" // "
				 * +detail.get(j).getOtherItemId()); map.add("headId",stock_header_id);
				 * map.add("item_id",item_id); map.add("opnStock",opening); Info
				 * stckDetail=rest.postForObject(Constant.URL+"/updateOtherStockDetail",map,Info
				 * .class); // set opening stock from passing item _id & header_id in detail
				 * table floag=1; detail.remove(j); break;
				 * 
				 * 
				 * 
				 * }
				 * 
				 * //System.out.println("Inner for out");
				 * 
				 * }
				 */

				/*
				 * if (floag==0) {
				 * 
				 * 
				 * // INSERT RECORD IN DETAIL TABLE BY PASSING HEADER ID
				 * 
				 * 
				 * 
				 * 
				 * otherStockDetail.setOtherStockDetailId(0);
				 * otherStockDetail.setOtherStockHeaderId(stock_header_id);
				 * 
				 * otherStockDetail.setOtherItemId(item_id); //
				 * System.out.println("ItemId 22="+" "+getotherStockList.get(i).
				 * getOtherStockItemId()); otherStockDetail.setOpeningStock(opening);
				 * otherStockDetail.setPurchaseStock(0); otherStockDetail.setSalesStock(0);
				 * otherStockDetail.setClosingStock(0); otherStockDetail.setDamageStock(0);
				 * otherStockDetail.setDelStatus(0); otherStockDetail.setExFloat1(0);
				 * otherStockDetail.setExFloat2(0); otherStockDetail.setExInt1(0);
				 * otherStockDetail.setExInt2(0); otherStockDetail.setExVar1(data);
				 * otherStockDetail.setExVar2(data);
				 * 
				 * // System.out.println("Headere 101"+detail); //
				 * System.out.println("New Opening Stock="+getotherStockList.get(i).
				 * getOpeningStock());
				 * otherStockDetail.setOpeningStock(getotherStockList.get(i).getOpeningStock());
				 * 
				 * otherStockList.add(otherStockDetail);
				 * otherStockHeader.setOtherItemStockList(otherStockList); //
				 * System.out.println("Stock Header2="+otherStockHeader); OtherItemStockHeader
				 * stockHead = rest.postForObject(Constant.URL+"/insertNewOtherStock",
				 * otherStockHeader, OtherItemStockHeader.class);
				 * 
				 * }
				 * 
				 * }
				 */
				// sac code

				for (int i = 0; i < getotherStockList.size(); i++) {
					int flag = 1;
					for (int j = 0; j < detail.size(); j++) {
						if (detail.get(j).getOtherItemId() == getotherStockList.get(i).getOtherStockItemId()) {
							detail.get(i).setOpeningStock(getotherStockList.get(i).getOpeningStock());
							flag = 0;
						}

					}
					if (flag == 1) {

						otherStockDetail = new OtherItemStockDetail();

						otherStockDetail.setOtherStockDetailId(0);
						otherStockDetail.setOtherStockHeaderId(stock_header_id);
						otherStockDetail.setOtherItemId(getotherStockList.get(i).getOtherStockItemId());
						otherStockDetail.setOpeningStock(getotherStockList.get(i).getOpeningStock());
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

						detail.add(otherStockDetail);

					}

				}

				otherStockHeader.setOtherItemStockList(detail);
				OtherItemStockHeader stockHead = rest.postForObject(Constant.URL + "/insertNewOtherStock",
						otherStockHeader, OtherItemStockHeader.class);

				// end of Sac code

			} // end of else

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/toOtherStock";
	}
}

// package com.ats.webapi.controller;
// OtherBillApiController