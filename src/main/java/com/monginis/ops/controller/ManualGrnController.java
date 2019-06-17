package com.monginis.ops.controller;

import java.math.BigDecimal;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hamcrest.core.IsEqual;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.monginis.ops.billing.Info;
import com.monginis.ops.billing.SellBillDataCommon;
import com.monginis.ops.billing.SellBillDetail;
import com.monginis.ops.billing.SellBillHeader;
import com.monginis.ops.common.Firebase;
import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.SellBillDetailList;
import com.monginis.ops.model.frsetting.FrSetting;
import com.monginis.ops.model.grngvn.GetBillsForFr;
import com.monginis.ops.model.grngvn.GetBillsForFrList;
import com.monginis.ops.model.grngvn.GetGrnGvnConfResponse;
import com.monginis.ops.model.grngvn.GetGrnItemConfig;
import com.monginis.ops.model.grngvn.GrnGvn;
import com.monginis.ops.model.grngvn.GrnGvnHeader;
import com.monginis.ops.model.grngvn.GrnGvnHeaderList;
import com.monginis.ops.model.grngvn.PostGrnGvnList;
import com.monginis.ops.model.grngvn.ShowGrnBean;
import com.monginis.ops.model.remarks.GetAllRemarks;
import com.monginis.ops.model.remarks.GetAllRemarksList;

@Controller
@Scope("session")
public class ManualGrnController {

	List<GetBillsForFr> frBillList;

	@RequestMapping(value = "/showManGrn", method = RequestMethod.GET)
	public ModelAndView showGvnProcess(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView modelAndView = new ModelAndView("grngvn/manGrn");

		HttpSession session = request.getSession();
		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

		RestTemplate restTemplate = new RestTemplate();
		GetBillsForFrList billsForFr = new GetBillsForFrList();
		try {

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			// String view = request.getParameter("view_opt");

			int frId = frDetails.getFrId();

			map.add("frId", frId);
			java.util.Date cDate = new java.util.Date();

			String curDate = new SimpleDateFormat("dd-MM-yyyy").format(cDate);

			map.add("curDate", curDate);

			billsForFr = new GetBillsForFrList();

			billsForFr = restTemplate.postForObject(Constant.URL + "getBillsForFr", map, GetBillsForFrList.class);

			frBillList = new ArrayList<>();

			frBillList = billsForFr.getGetBillsForFr();

			System.out.println("FR BILL LIST " + frBillList.toString());

			modelAndView.addObject("frBillList", frBillList);

		} catch (Exception e) {

			System.out.println("ex in get Bills For Fr gvn " + e.getMessage());

			e.printStackTrace();
		}

		return modelAndView;
	}

	public GetGrnGvnConfResponse grnGvnConfResponse = new GetGrnGvnConfResponse();
	public List<GetGrnItemConfig> grnConfList;

	List<ShowGrnBean> objShowGvnList, gvnList;;
	GetAllRemarksList allRemarksList, getAllRemarksList;
	List<GetAllRemarks> getAllRemarks;
	List<ShowGrnBean> objShowGrnList = new ArrayList<>();
	public static float roundUp(float d) {
		return BigDecimal.valueOf(d).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
	}

	@RequestMapping(value = "/getGrnBillDetail", method = RequestMethod.GET)
	public ModelAndView getGrnBillDetail(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView modelAndView = new ModelAndView("grngvn/manGrn");

		// modelAndView.addObject("frBillList", frBillList);

		try {

			//int billNo = Integer.parseInt(request.getParameter("bill_no"));
			//System.out.println("selected bill no " + billNo);

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			int frId=frDetails.getFrId();
			//map.add("billNo", billNo);
			map.add("frId", frId);
			grnGvnConfResponse = restTemplate.postForObject(Constant.URL + "getGrnItemConfig", map,
					GetGrnGvnConfResponse.class);

			grnConfList = new ArrayList<>();

			grnConfList = grnGvnConfResponse.getGetGrnItemConfigs();
			if(grnConfList.isEmpty()==false || grnConfList!=null) {
			System.out.println("gvn conf list " + grnConfList.toString());
			}

			modelAndView.addObject("frBillList", frBillList);
			//modelAndView.addObject("selctedBillNo", billNo);

			objShowGrnList = new ArrayList<>();

			ShowGrnBean objShowGrn = null;

			for (int i = 0; i < grnConfList.size(); i++) {

					objShowGrn = new ShowGrnBean();

					objShowGrn.setDiscPer(grnConfList.get(i).getDiscPer());
					objShowGrn.setHsnCode(grnConfList.get(i).getHsnCode());//new
					objShowGrn.setBillDate(grnConfList.get(i).getBillDate());
					objShowGrn.setBillDetailNo(grnConfList.get(i).getBillDetailNo());
					objShowGrn.setBillNo(grnConfList.get(i).getBillNo());
					objShowGrn.setBillQty(grnConfList.get(i).getBillQty());

					objShowGrn.setCgstPer(grnConfList.get(i).getCgstPer());
					objShowGrn.setFrId(grnConfList.get(i).getFrId());
					objShowGrn.setGrnType(grnConfList.get(i).getGrnType());
					objShowGrn.setIgstPer(grnConfList.get(i).getIgstPer());
					objShowGrn.setItemId(grnConfList.get(i).getItemId());
					objShowGrn.setItemName(grnConfList.get(i).getItemName());
					objShowGrn.setMrp(grnConfList.get(i).getMrp());
					objShowGrn.setRate(grnConfList.get(i).getRate());
					objShowGrn.setSgstPer(grnConfList.get(i).getSgstPer());

					float calcBaseRate = grnConfList.get(i).getRate() * 100
							/ (grnConfList.get(i).getSgstPer() + grnConfList.get(i).getCgstPer() + 100);

					objShowGrn.setCalcBaseRate(roundUp(calcBaseRate));

					objShowGrn.setAutoGrnQty(grnConfList.get(i).getAutoGrnQty());

					float baseRate = objShowGrn.getRate() * 100
							/ (objShowGrn.getSgstPer() + objShowGrn.getCgstPer() + 100);

					float grnBaseRate = 0.0f;

					float grnRate = 0.0f;

					if (objShowGrn.getGrnType() == 0) {
						grnBaseRate = baseRate * 80 / 100;

						// grnRate = (objShowGrn.getRate() * 80) / 100;

						grnRate = (baseRate * 80) / 100;
					}

					if (objShowGrn.getGrnType() == 1) {
						grnBaseRate = baseRate * 70 / 100;
						// grnRate = (objShowGrn.getRate() * 70) / 100;

						grnRate = (baseRate * 70) / 100;
					}

					if (objShowGrn.getGrnType() == 2 || objShowGrn.getGrnType() == 4) {

						grnBaseRate = baseRate;
						// grnRate = objShowGrn.getRate();
						grnRate = baseRate;
					}
					// objShowGrn.setGrnRate(roundUp(grnRate));

					float taxableAmt = grnRate * objShowGrn.getAutoGrnQty();
					float discAmt=(taxableAmt*objShowGrn.getDiscPer()/100);
					taxableAmt=taxableAmt-discAmt;
					objShowGrn.setTaxableAmt(roundUp(taxableAmt));

					float totalTax = (taxableAmt * (objShowGrn.getSgstPer() + objShowGrn.getCgstPer())) / 100;

					float grandTotal = taxableAmt + totalTax;

					float finalAmt = grnRate * objShowGrn.getAutoGrnQty();

					objShowGrn.setGrnAmt(roundUp(grandTotal));

					float taxPer = objShowGrn.getSgstPer() + objShowGrn.getCgstPer();

					objShowGrn.setTaxPer(taxPer);

					objShowGrn.setMenuId(grnConfList.get(i).getMenuId());
					objShowGrn.setCatId(grnConfList.get(i).getCatId());
					objShowGrn.setInvoiceNo(grnConfList.get(i).getInvoiceNo());
					objShowGrn.setBillDateTime(grnConfList.get(i).getBillDateTime());

					objShowGrn.setTaxAmt(roundUp(totalTax));

					System.out.println("OBJ SHOW GRN " + objShowGrn.toString());
					objShowGrnList.add(objShowGrn);

					// objShowGrnList.add(objShowGrn);

					// objShowGrnList.add(objShowGrn);

					// objShowGrnList.add(objShowGrn);

			

			} // End of For Loop

			System.out.println("bean new " + objShowGrnList.toString());

			modelAndView.addObject("grnConfList", objShowGrnList);
			//modelAndView.addObject("billNo", billNo);
			
			
			map = new LinkedMultiValueMap<String, Object>();

			map = new LinkedMultiValueMap<String, Object>();
			map.add("isFrUsed", 1);
			map.add("moduleId", 1);
			map.add("subModuleId", 1);
			getAllRemarksList = restTemplate.postForObject(Constant.URL + "/getAllRemarks", map,
					GetAllRemarksList.class);

			// allRemarksList = restTemplate.getForObject(Constants.url + "getAllRemarks",
			// GetAllRemarksList.class);

			getAllRemarks = new ArrayList<>();
			getAllRemarks = getAllRemarksList.getGetAllRemarks();

			// allRemarksList = restTemplate.getForObject(Constant.URL + "getAllRemarks",
			// GetAllRemarksList.class);

			// getAllRemarks = allRemarksList.getGetAllRemarks();

			System.out.println("remark list " + getAllRemarks.toString());

			modelAndView.addObject("remarkList", getAllRemarks);

		} catch (Exception e) {
			System.out.println("show gvn error " + e.getMessage());
			e.printStackTrace();
		}

		return modelAndView;

	}
	
	
	List<GrnGvnHeader> grnHeaderList=new ArrayList<>();
	
	@RequestMapping(value = "/postManualGrn", method = RequestMethod.POST)
	public String insertGrnProcess(HttpServletRequest request, HttpServletResponse response) {
System.err.println("Inside Manual Grn POST method ");
		ModelAndView modelAndView = new ModelAndView("grngvn/showgrn");

		HttpSession session = request.getSession();

		Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");
		int fraId = frDetails.getFrId();
		try {

			java.sql.Date grnGvnDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			List<GrnGvn> postGrnGvnList = new ArrayList<GrnGvn>();

			PostGrnGvnList postGrnList = new PostGrnGvnList();

			GrnGvnHeader grnHeader = new GrnGvnHeader();

			grnConfList = grnGvnConfResponse.getGetGrnItemConfigs();

			float sumTaxableAmt = 0;
			float sumTaxAmt = 0;
			float sumTotalAmt = 0;

			String curDateTime = null;
			String[] selectToGrn = request.getParameterValues("select_to_grn");
			
			System.err.println("show List objShowGrnList before  " +objShowGrnList.toString());

			List<ShowGrnBean> tempGrnBean=objShowGrnList;
			
			System.err.println("show List tempGrnBean   " +tempGrnBean.toString());

			System.err.println("selected Bills " +selectToGrn[0]);
			objShowGrnList=new ArrayList<>();
			for(int i=0;i<selectToGrn.length;i++) {
				
				for(int j=0;j<tempGrnBean.size();j++) {
					if(Integer.parseInt(selectToGrn[i])==(tempGrnBean.get(j).getBillDetailNo())) {
						objShowGrnList.add(tempGrnBean.get(j));
					}
				}
			}
			
			System.err.println("show List objShowGrnList new  " +objShowGrnList.toString());

			for (int i = 0; i < objShowGrnList.size(); i++) {

				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Calendar cal = Calendar.getInstance();
				DateFormat dateFormatDate = new SimpleDateFormat("yyyy-MM-dd");
				Calendar calDate = Calendar.getInstance();
				GrnGvn postGrnGvn = new GrnGvn();
				
				

				String tempGrnQtyAuto = request.getParameter("grnqtyauto" + objShowGrnList.get(i).getBillDetailNo() + "");
				/*tempGrnQtyAuto="2";
				String tempGrnQty = request.getParameter("grnqty" + objShowGrnList.get(i).getItemId() + "");
				tempGrnQty="3";
				System.out.println("tempGrnQty ===" + tempGrnQty);

				System.out.println("tempGrnQtyAuto ===" + tempGrnQtyAuto);
*/
				int grnQty = Integer.parseInt(tempGrnQtyAuto);
				/*int fixedGrnQty = Integer.parseInt(tempGrnQty);
				int isEdit = 0;
				if (grnQty != fixedGrnQty) {
					isEdit = 1;

				} else {
					isEdit = 0;
				}*/
				int isEdit=1;
				String frGrnRemark = request.getParameter("grn_remark" + objShowGrnList.get(i).getBillDetailNo());

				if (frGrnRemark == null || frGrnRemark == "") {
					frGrnRemark = "no remark entered";

				}

				float baseRate = objShowGrnList.get(i).getRate() * 100
						/ (objShowGrnList.get(i).getSgstPer() + objShowGrnList.get(i).getCgstPer() + 100);

				float grnBaseRate = 0.0f;

				float grnRate = 0.0f;

				if (objShowGrnList.get(i).getGrnType() == 0) {
					grnBaseRate = baseRate * 80 / 100;

					grnRate = (objShowGrnList.get(i).getRate() * 80) / 100;
					// postGrnGvn.setGrnGvnAmt(roundUp(grnAmt));
				}

				if (objShowGrnList.get(i).getGrnType() == 1) {
					grnBaseRate = baseRate * 70 / 100;
					grnRate = (objShowGrnList.get(i).getRate() * 70) / 100;
					// postGrnGvn.setGrnGvnAmt(roundUp(grnAmt));
				}

				if (objShowGrnList.get(i).getGrnType() == 2 || objShowGrnList.get(i).getGrnType() == 4) {
					// postGrnGvn.setGrnGvnAmt(roundUp(grnAmt));

					grnBaseRate = baseRate;
					grnRate = objShowGrnList.get(i).getRate();
				}

				float taxableAmt = grnBaseRate * grnQty;
				float discAmt=(taxableAmt*objShowGrnList.get(i).getDiscPer()/100);
				taxableAmt=taxableAmt-discAmt;
				float totalTax = (taxableAmt
						* (objShowGrnList.get(i).getSgstPer() + objShowGrnList.get(i).getCgstPer())) / 100;

				float grandTotal = taxableAmt + totalTax;

				//float finalAmt = grnRate * grnQty;
				float finalAmt = (objShowGrnList.get(i).getRate() * grnQty)-((objShowGrnList.get(i).getRate() * grnQty) *   objShowGrnList.get(i).getDiscPer()/100);

				postGrnGvn.setGrnGvnAmt(roundUp(grandTotal));
				float roundUpAmt = finalAmt - grandTotal;

				/*
				 * if(frDetails.getFrGstType()==0) { grnAmt = grnQty *
				 * grnConfList.get(i).getRate(); grnAmt = roundUp(grnAmt);
				 * 
				 * } else {
				 * 
				 * float baseRate= grnConfList.get(i).getRate()*100/
				 * (grnConfList.get(i).getSgstPer() + grnConfList.get(i).getCgstPer()+100);
				 * grnAmt=grnQty*baseRate;
				 * 
				 * }
				 */

				if (grnQty > 0) {
					postGrnGvn.setGrnGvnDate(grnGvnDate);

					postGrnGvn.setBillDetailNo(objShowGrnList.get(i).getBillDetailNo());// 15 Feb added

					curDateTime = dateFormat.format(cal.getTime());
					
					postGrnGvn.setItemMrp(objShowGrnList.get(i).getDiscPer());//setting disc Per in Grn_gvn detail 4 Feb 2019
					postGrnGvn.setHsnCode(objShowGrnList.get(i).getHsnCode());
					
					postGrnGvn.setBillNo(objShowGrnList.get(i).getBillNo());
					postGrnGvn.setFrId(frDetails.getFrId());
					postGrnGvn.setItemId(objShowGrnList.get(i).getItemId());
					postGrnGvn.setItemRate(objShowGrnList.get(i).getRate());
					//postGrnGvn.setItemMrp(objShowGrnList.get(i).getMrp());
					postGrnGvn.setGrnGvnQty(grnQty);
					postGrnGvn.setGrnType(objShowGrnList.get(i).getGrnType());
					postGrnGvn.setIsGrn(1);
					postGrnGvn.setIsGrnEdit(isEdit);
					postGrnGvn.setGrnGvnEntryDateTime(dateFormat.format(cal.getTime()));
					postGrnGvn.setFrGrnGvnRemark(frGrnRemark);
					postGrnGvn.setGvnPhotoUpload1("grn:no photo");
					postGrnGvn.setGvnPhotoUpload2("grn:no photo");
					postGrnGvn.setGrnGvnStatus(2);//Changed on May 9 By Sachin
					postGrnGvn.setApprovedLoginGate(0);
					postGrnGvn.setApproveimedDateTimeGate(dateFormat.format(cal.getTime()));
					postGrnGvn.setApprovedRemarkGate(" ");

					postGrnGvn.setApprovedLoginStore(0);
					postGrnGvn.setApprovedDateTimeStore(dateFormat.format(cal.getTime()));
					postGrnGvn.setApprovedRemarkStore(" ");
					postGrnGvn.setApprovedLoginAcc(0);
					postGrnGvn.setGrnApprovedDateTimeAcc(dateFormat.format(cal.getTime()));
					postGrnGvn.setApprovedRemarkAcc(" ");

					postGrnGvn.setDelStatus(0);
					postGrnGvn.setGrnGvnQtyAuto(grnQty);

					// newly added

					postGrnGvn.setIsTallySync(0);
					postGrnGvn.setBaseRate(roundUp(baseRate));
					postGrnGvn.setSgstPer(objShowGrnList.get(i).getSgstPer());
					postGrnGvn.setCgstPer(objShowGrnList.get(i).getCgstPer());
					postGrnGvn.setIgstPer(objShowGrnList.get(i).getIgstPer());

					postGrnGvn.setTaxableAmt(roundUp(taxableAmt));
					postGrnGvn.setTotalTax(roundUp(totalTax));
					postGrnGvn.setFinalAmt(roundUp(finalAmt));
					postGrnGvn.setRoundUpAmt(roundUp(roundUpAmt));

					postGrnGvn.setIsCreditNote(0);

					postGrnGvn.setCatId(objShowGrnList.get(i).getCatId());
					postGrnGvn.setMenuId(objShowGrnList.get(i).getMenuId());

					postGrnGvn.setRefInvoiceDate(objShowGrnList.get(i).getBillDate());
					postGrnGvn.setInvoiceNo(objShowGrnList.get(i).getInvoiceNo());

					// setting new field added on 23 FEB

					postGrnGvn.setAprQtyGate(grnQty);
					postGrnGvn.setAprQtyStore(0);
					postGrnGvn.setAprQtyAcc(0);
					postGrnGvn.setAprTaxableAmt(0);
					postGrnGvn.setAprTotalTax(0);
					postGrnGvn.setAprSgstRs(0);
					postGrnGvn.setAprCgstRs(0);
					postGrnGvn.setAprIgstRs(0);
					postGrnGvn.setAprGrandTotal(0);
					postGrnGvn.setAprROff(0);
					postGrnGvn.setIsSameState(frDetails.getIsSameState());

					System.out.println("post grn ref inv date " + postGrnGvn.getRefInvoiceDate());

					// 15 Feb
					sumTaxableAmt = sumTaxableAmt + postGrnGvn.getTaxableAmt();
					sumTaxAmt = sumTaxAmt + postGrnGvn.getTotalTax();
					sumTotalAmt = sumTotalAmt + postGrnGvn.getGrnGvnAmt();

					postGrnGvnList.add(postGrnGvn);

				} // end of if checking for grnQty
			} // end of for

			grnHeader.setGrnGvn(postGrnGvnList);

			grnHeader.setFrId(fraId);
			grnHeader.setApporvedAmt(0);
			grnHeader.setApprovedDatetime(curDateTime);
			grnHeader.setCreditNoteId("");
			grnHeader.setGrngvnDate(new SimpleDateFormat("dd-MM-yyyy").format(grnGvnDate));
			grnHeader.setGrngvnSrno(getGrnGvnSrNo(request, response,frDetails.getFrCode()));
			grnHeader.setGrngvnStatus(2);//Changed on May 9 By Sachin 1 with 2
			grnHeader.setIsCreditNote(0);
			grnHeader.setIsGrn(1);
			grnHeader.setApporvedAmt(0);

			grnHeader.setTaxableAmt(roundUp(sumTaxableAmt));
			grnHeader.setTaxAmt(roundUp(sumTaxAmt));
			grnHeader.setTotalAmt(roundUp(sumTotalAmt));
			grnHeader.setGrnGvn(postGrnGvnList);
			grnHeader.setAprGrandTotal(0);

			//modelAndView.addObject("grnConfList", objShowGrnList);
			System.out.println("grnHeader ************----- " + grnHeader.toString());

			System.out.println("****postGrnGvnList size*******-- " + postGrnGvnList.size());

			// postGrnList.setGrnGvn(postGrnGvnList);

			postGrnList.setGrnGvnHeader(grnHeader);
			System.out.println("post grn for rest----- " + postGrnList.toString());
			// System.out.println("post grn for rest size " +
			// postGrnList.getGrnGvn().size());

			Info insertGrn = restTemplate.postForObject(Constant.URL + "insertGrnGvn", postGrnList, Info.class);
			//Info insertGrn=null;
			if (insertGrn.getError() == false) {
				
				System.err.println("insertGrn.getError==false ");

				map = new LinkedMultiValueMap<String, Object>();

				map.add("frId", frDetails.getFrId());

				SellBillDataCommon sellBillResponse = restTemplate
						.postForObject(Constant.URL + "/showNotDayClosedRecord", map, SellBillDataCommon.class);

				if (!sellBillResponse.getSellBillHeaderList().isEmpty()) {
                System.err.println("Inside sellBillResponse != null");
					List<SellBillHeader> sellBillHeaderList = sellBillResponse.getSellBillHeaderList();

					int count = sellBillHeaderList.size();
					SellBillHeader billHeader = sellBillResponse.getSellBillHeaderList().get(0);

					map = new LinkedMultiValueMap<String, Object>();

					map.add("billNo", billHeader.getSellBillNo());

					SellBillDetailList sellBillDetailList = restTemplate
							.postForObject(Constant.URL + "/getSellBillDetails", map, SellBillDetailList.class);

					List<SellBillDetail> sellBillDetails = sellBillDetailList.getSellBillDetailList();

					for (int x = 0; x < sellBillDetails.size(); x++) {

						billHeader.setTaxableAmt(billHeader.getTaxableAmt() + sellBillDetails.get(x).getTaxableAmt());

						billHeader.setTotalTax(billHeader.getTotalTax() + sellBillDetails.get(x).getTotalTax());
						billHeader.setGrandTotal(sellBillDetails.get(x).getGrandTotal() + billHeader.getGrandTotal());

						// billHeader.setBillDate(billHeader.getBillDate());

						billHeader.setDiscountPer(billHeader.getDiscountPer());

					}

					billHeader.setGrandTotal(Math.round(billHeader.getGrandTotal()));
					billHeader.setPaidAmt(billHeader.getGrandTotal());
					billHeader.setPayableAmt(billHeader.getGrandTotal());
					
					String start_dt =billHeader.getBillDate();
					DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy"); 
					java.util.Date date = (java.util.Date)formatter.parse(start_dt);
				
					SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd");
					String finalString = newFormat.format(date);
					billHeader.setBillDate(finalString);
					billHeader = restTemplate.postForObject(Constant.URL + "saveSellBillHeader", billHeader,
							SellBillHeader.class);

					System.out.println("Bill Header Response " + billHeader.toString());

				} // end of if ex bill not null

				postGrnList = new PostGrnGvnList();

				//

				// 14 FEB update Bill Detail for GRN GVN Insert Rest API

				// UpdateBillDetailForGrnGvnRepository package com.ats.webapi.repository;

				// did it on web service side
				/*
				 * for (int i = 0; i < objShowGrnList.size(); i++) {
				 * 
				 * String tempGrnQtyAuto = request.getParameter("grnqtyauto" +
				 * objShowGrnList.get(i).getItemId() + ""); int grnQty =
				 * Integer.parseInt(tempGrnQtyAuto);
				 * 
				 * if (grnQty > 0) {
				 * 
				 * map = new LinkedMultiValueMap<String, Object>(); map.add("billDetailNo",
				 * objShowGrnList.get(i).getBillDetailNo());
				 * 
				 * Info info = restTemplate.postForObject(Constant.URL +
				 * "updateBillDetailforGrnGvnInsert", map, Info.class); }
				 * 
				 * }
				 */

				// update frSetting value for frGrnGvnSrNo
				map = new LinkedMultiValueMap<String, Object>();

				map.add("frId", frDetails.getFrId());
				FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map,
						FrSetting.class);

				int grnGvnSrNo = frSetting.getGrnGvnNo();

				grnGvnSrNo = grnGvnSrNo + 1;

				map = new LinkedMultiValueMap<String, Object>();

				map.add("frId", frDetails.getFrId());
				map.add("grnGvnNo", grnGvnSrNo);

				Info info = restTemplate.postForObject(Constant.URL + "updateFrSettingGrnGvnNo", map, Info.class);

				System.out.println("/updateFrSettingGrnGvnNo: Response @GrnGvnController  info=  " + info.toString());
				
				//-----------------------For Notification-----------------
				String frToken="";
			
				try {
					map = new LinkedMultiValueMap<String, Object>();
					  map.add("frId",frDetails.getFrId());
					   
                     frToken= restTemplate.postForObject(Constant.URL+"getFrToken", map, String.class);
			         Firebase.sendPushNotifForCommunication(frToken,"GRN Punched","GRN has been punched against value of Rs."+grnHeader.getTotalAmt()+ " Thank You..Team Monginis","inbox");
			   	
			        }
			        catch(Exception e2)
			        {
				      e2.printStackTrace();
			        }
				
				//-----------------------------------------------------


			}

		} catch (Exception e) {

			System.out.println("exce in grn insert or Express Bill Day close " + e.getMessage());
			e.printStackTrace();

		}

		ModelAndView modelAndView2 = new ModelAndView("grngvn/viewGrn");

		try {

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
			int frId = frDetails.getFrId();

			java.util.Date grnDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());

			DateFormat sdFormat = new SimpleDateFormat("dd-MM-yyyy");

			String cDate = sdFormat.format(grnDate);

			map.add("frIdList", frId);
			map.add("fromDate", cDate);
			map.add("toDate", cDate);
			map.add("isGrn", 1);
			// getFrGrnDetail
			//List<GrnGvnHeader> grnHeaderList = new ArrayList<>();

			try {
				grnHeaderList = new ArrayList<>();

				GrnGvnHeaderList headerList = restTemplate.postForObject(Constant.URL + "getGrnGvnHeader", map,
						GrnGvnHeaderList.class);

				grnHeaderList = headerList.getGrnGvnHeader();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("EXCE in getting gvn Header List " + e.getMessage());
			}

			// model.addObject("gvnList", gvnHeaderList);

			// model.addObject("url", Constant.GVN_IMAGE_URL);
			// modelAndView2.addObject("cDate", cDate); // End comment
			modelAndView2.addObject("grnList", grnHeaderList);
			// modelAndView2.addObject("grnList", grnGvnDetailsList);

			modelAndView2.addObject("cDate", cDate);
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println(e.getMessage());

		}

		return "redirect:/displayGrn";

	}

	public String getGrnGvnSrNo(HttpServletRequest request, HttpServletResponse response, String frCode) {
		String grnGvnNo = null;
		try {
			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			RestTemplate restTemplate = new RestTemplate();

			HttpSession session = request.getSession();

			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			int frId = frDetails.getFrId();

			// String frCode = frDetails.getFrCode();

			map.add("frId", frId);
			FrSetting frSetting = restTemplate.postForObject(Constant.URL + "getFrSettingValue", map, FrSetting.class);

			int grnGvnSrNo = frSetting.getGrnGvnNo();

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

			java.util.Date date = new java.util.Date();
			Calendar cale = Calendar.getInstance();
			cale.setTime(date);
			int month = cale.get(Calendar.MONTH);
			month = month + 1;
			if (month <= 3) {

				curStrYear = preMarchStrYear + curStrYear;
				System.out.println("Month <= 3::Cur Str Year " + curStrYear);
			} else if (month >= 4) {

				curStrYear = curStrYear + nextStrYear;
				System.out.println("Month >=4::Cur Str Year " + curStrYear);
			}

			////

			int length = String.valueOf(grnGvnSrNo).length();

			String invoiceNo = null;

			if (length == 1)

				invoiceNo = curStrYear + "-"+"000" + grnGvnSrNo;
			if (length == 2)

				invoiceNo = curStrYear + "-"+"00" + grnGvnSrNo;

			if (length == 3)

				invoiceNo = curStrYear + "-"+"0" + grnGvnSrNo;

			System.out.println("*** settingValue= " + grnGvnSrNo);

			grnGvnNo = frDetails.getFrCode()+invoiceNo;
			// return grnGvnNo;

		} catch (Exception e) {

		}

		return grnGvnNo;

	}

	
}
