package com.monginis.ops.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.creditnote.CreditNoteHeaderPrint;
import com.monginis.ops.model.creditnote.CreditPrintBean;
import com.monginis.ops.model.creditnote.CrnDetailsSummary;
import com.monginis.ops.model.creditnote.CrnSrNoDateBean;
import com.monginis.ops.model.creditnote.GetCreditNoteHeaders;
import com.monginis.ops.model.creditnote.GetCreditNoteHeadersList;
import com.monginis.ops.model.creditnote.GetCrnDetails;
import com.monginis.ops.model.creditnote.GetCrnDetailsList;

import com.monginis.ops.constant.Constant;

@Controller
@Scope("session")
public class CreditNoteController {

	LinkedHashMap<Integer, GetCreditNoteHeaders> crnHeadersMap = null;
	LinkedHashMap<Integer, List<GetCrnDetails>> crnDetailsMap = null;

	@RequestMapping(value = "/showInsertCreditNote", method = RequestMethod.GET)
	public ModelAndView showCrediNotePage(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView model = null;
		HttpSession session = request.getSession();
		crnHeadersMap = new LinkedHashMap<Integer, GetCreditNoteHeaders>();
		crnDetailsMap = new LinkedHashMap<Integer, List<GetCrnDetails>>();

		String pattern = "dd-MM-yyyy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String date = simpleDateFormat.format(new Date());

		model = new ModelAndView("creditnote/creditNoteHeaders");
		model.addObject("type", 1);

		model.addObject("fromDate", date);
		model.addObject("toDate", date);
		return model;

	}

	String fromDate, toDate, crnFr;
	GetCreditNoteHeadersList headerResponse = new GetCreditNoteHeadersList();
	List<GetCreditNoteHeaders> creditHeaderList = new ArrayList<GetCreditNoteHeaders>();

	List<GetCrnDetails> crnDetailList = new ArrayList<GetCrnDetails>();

	@RequestMapping(value = "/getHeaders", method = RequestMethod.GET)
	public @ResponseBody List<GetCreditNoteHeaders> getHeaders(HttpServletRequest request,
			HttpServletResponse response) {

		System.out.println("inside Ajax Call");

		ModelAndView model = new ModelAndView("creditNote/creditNoteHeaders");

		try {

			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			try {
				HttpSession session = request.getSession();
				Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

				fromDate = request.getParameter("fromDate");
				toDate = request.getParameter("toDate");

				System.out.println("From Date " + fromDate + "toDate " + toDate);

				map.add("fromDate", fromDate);

				map.add("toDate", toDate);
				map.add("frIdList", frDetails.getFrId());

				System.out.println(frDetails.getFrId().toString());
				map.add("isGrn", "-1");
				headerResponse = restTemplate.postForObject(Constant.URL + "getCreditNoteHeaders", map,
						GetCreditNoteHeadersList.class);

				creditHeaderList = headerResponse.getCreditNoteHeaders();

				System.err.println("CH List " + creditHeaderList.toString());

			} catch (Exception e) {
				System.out.println("Exception in getAllFrIdName" + e.getMessage());
				e.printStackTrace();
			}

		} catch (Exception e) {
			System.err.println("Exce in viewving credit note page");
		}

		return creditHeaderList;
	}

	GetCrnDetailsList crnDetailResponse = new GetCrnDetailsList();

	@RequestMapping(value = "/getCrnDetailList/{crnId}", method = RequestMethod.GET)
	public ModelAndView getGrnDetailList(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("crnId") int crnId) {
		System.out.println("In detail Page");
		ModelAndView model = new ModelAndView("creditnote/crnDetails");

		try {

			HttpSession session = request.getSession();
			Franchisee frDetails = (Franchisee) session.getAttribute("frDetails");

			String fromDate = request.getParameter("fromDate");
			String toDate = request.getParameter("toDate");

			GetCreditNoteHeaders creditNoteHeaders = new GetCreditNoteHeaders();
			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			map.add("crnId", crnId);
			crnDetailResponse = restTemplate.postForObject(Constant.URL + "getCrnDetails", map,
					GetCrnDetailsList.class);

			crnDetailList = crnDetailResponse.getCrnDetails();

			System.out.println("crn Detail List******** " + crnDetailList);
			for (int i = 0; i < creditHeaderList.size(); i++) {
				if (crnId == creditHeaderList.get(i).getCrnId()) {
					creditNoteHeaders = creditHeaderList.get(i);
					break;
				}
			}
			crnHeadersMap.put(crnId, creditNoteHeaders);
			crnDetailsMap.put(crnId, crnDetailList);// added crn details
			System.err.println(crnDetailsMap.toString() + "crnDetailsMap");
			model.addObject("crnDetailList", crnDetailList);
			model.addObject("creditNoteHeaders", creditNoteHeaders);
			model.addObject("fromDate", fromDate);
			model.addObject("toDate", toDate);
			model.addObject("selectFr", frDetails.getFrId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}

	@RequestMapping(value = "pdf/getCrnCheckedHeadersNew/{checked}", method = RequestMethod.GET)
	public ModelAndView getCrnCheckedHeadersNew(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("checked") String[] checked) {

		ModelAndView model = new ModelAndView("creditnote/creditnotePdf");

		try {
			RestTemplate restTemplate = new RestTemplate();

			MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

			System.out.println("In detail Page");

			// String[] checked = request.getParameterValues("select_to_agree");
			String crnIdList = new String();
			System.out.println("checked of zero " + checked[0]);

			for (int i = 0; i < checked.length; i++) {
				System.err.println("Value checked  " + checked[i]);
				crnIdList = crnIdList + "," + checked[i];
			}

			// Getting crn Headers

			map.add("crnIdList", crnIdList);
			headerResponse = restTemplate.postForObject(Constant.URL + "getCreditNoteHeadersByCrnIds", map,
					GetCreditNoteHeadersList.class);

			creditHeaderList = headerResponse.getCreditNoteHeaders();
			System.err.println("Crn Id List " + crnIdList);

			System.out.println("Headers = " + creditHeaderList.toString());
			crnIdList = crnIdList.substring(1, crnIdList.length());
			System.err.println("Crn Id List on removing First comma " + crnIdList);

			// Getting crn Details

			map = new LinkedMultiValueMap<String, Object>();
			map.add("crnId", crnIdList);
			crnDetailResponse = restTemplate.postForObject(Constant.URL + "getCrnDetails", map,
					GetCrnDetailsList.class);
			crnDetailList = new ArrayList<>();

			crnDetailList = crnDetailResponse.getCrnDetails();

			// List<GetCrnDetails> crnPrintDetailList=new ArrayList<>()

			DateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");

			List<CreditPrintBean> printList = new ArrayList<>();

			System.err.println("header data " + creditHeaderList.toString());
			System.err.println(
					"Size of Header = " + creditHeaderList.size() + "Size of Detail =  " + crnDetailList.toString());
			CreditPrintBean printBean = new CreditPrintBean();

			for (int i = 0; i < creditHeaderList.size(); i++) {
				printBean = new CreditPrintBean();
				CreditNoteHeaderPrint cNoteHeaderPrint = new CreditNoteHeaderPrint();

				System.err.println(creditHeaderList.size() + "I = " + i);
				try {
					map = new LinkedMultiValueMap<String, Object>();
					map.add("crnId", creditHeaderList.get(i).getCrnId());
					List<CrnDetailsSummary> crnSummaryList = restTemplate
							.postForObject(Constant.URL + "getCrnDetailsSummary", map, List.class);
					cNoteHeaderPrint.setCrnDetailsSummaryList(crnSummaryList);
					System.err.println(crnSummaryList.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}

				cNoteHeaderPrint.setFrAddress(creditHeaderList.get(i).getFrAddress());
				cNoteHeaderPrint.setFrId(creditHeaderList.get(i).getFrId());

				cNoteHeaderPrint.setFrName(creditHeaderList.get(i).getFrName());
				cNoteHeaderPrint.setCrnId(creditHeaderList.get(i).getCrnId());
				cNoteHeaderPrint.setCrnDate(creditHeaderList.get(i).getCrnDate());

				cNoteHeaderPrint.setFrGstNo(creditHeaderList.get(i).getFrGstNo());
				cNoteHeaderPrint.setIsGrn(creditHeaderList.get(i).getIsGrn());

				List<GetCrnDetails> crnPrintDetailList = new ArrayList<>();

				List<String> srNoList = new ArrayList<String>();
				List<CrnSrNoDateBean> srNoDateList = new ArrayList<CrnSrNoDateBean>();

				String fDate = null, tDate = null;

				for (int j = 0; j < crnDetailList.size(); j++) {

					// System.err.println("J = " + j);

					if (creditHeaderList.get(i).getCrnId() == crnDetailList.get(j).getCrnId()) {

						// System.err.println("Match found = " + j);

						crnPrintDetailList.add(crnDetailList.get(j));

						Date initDateFrom = fmt.parse(crnDetailList.get(0).getGrnGvnDate());
						Date toLastDate = fmt.parse(crnDetailList.get(0).getGrnGvnDate());

						/*
						 * if (!srNoList.contains(crnDetailList.get(j).getGrngvnSrno())) {
						 * srNoList.add(crnDetailList.get(j).getGrngvnSrno()); }
						 */

						boolean isPrev = false;
						for (CrnSrNoDateBean bean : srNoDateList) {

							if (bean.getSrNo().equalsIgnoreCase(crnDetailList.get(j).getGrngvnSrno())) {
								isPrev = true;
							}

						}

						if (!isPrev) {

							CrnSrNoDateBean bean = new CrnSrNoDateBean();
							bean.setGrnGvnDate(crnDetailList.get(j).getGrnGvnDate());
							bean.setSrNo(crnDetailList.get(j).getGrngvnSrno());

							// srNoDateList.get(j).setGrnGvnDate(crnDetailList.get(j).getGrnGvnDate());
							// srNoDateList.get(j).setSrNo(crnDetailList.get(j).getGrngvnSrno());
							srNoDateList.add(bean);

						}
						/*
						 * if(!srNoDateList.contains(crnDetailList.get(j).getGrngvnSrno())) {
						 * 
						 * CrnSrNoDateBean bean=new CrnSrNoDateBean();
						 * bean.setGrnGvnDate(crnDetailList.get(j).getGrnGvnDate());
						 * bean.setSrNo(crnDetailList.get(j).getGrngvnSrno());
						 * 
						 * //srNoDateList.get(j).setGrnGvnDate(crnDetailList.get(j).getGrnGvnDate());
						 * //srNoDateList.get(j).setSrNo(crnDetailList.get(j).getGrngvnSrno());
						 * srNoDateList.add(bean);
						 * 
						 * }
						 */

						if (initDateFrom.before(fmt.parse(crnDetailList.get(j).getGrnGvnDate()))) {

						} else {
							initDateFrom = fmt.parse(crnDetailList.get(j).getGrnGvnDate());
						}

						if (toLastDate.after(fmt.parse(crnDetailList.get(j).getGrnGvnDate()))) {

						} else {
							toLastDate = fmt.parse(crnDetailList.get(j).getGrnGvnDate());
						}
						fDate = fmt.format(initDateFrom);
						tDate = fmt.format(toLastDate);
					} // end of if

				} // end of Inner for

				cNoteHeaderPrint.setFromDate(fDate);
				cNoteHeaderPrint.setToDate(tDate);

				cNoteHeaderPrint.setCrnDetails(crnPrintDetailList);
				cNoteHeaderPrint.setCrnNo(creditHeaderList.get(i).getCrnNo());
				cNoteHeaderPrint.setSrNoDateList(srNoDateList);
				cNoteHeaderPrint.setSrNoList(srNoList);
				cNoteHeaderPrint.setExInt1(creditHeaderList.get(i).getExInt1());
				cNoteHeaderPrint.setExVarchar1(creditHeaderList.get(i).getExVarchar1());
				printBean.setCreditHeader(cNoteHeaderPrint);

				printList.add(printBean);
				System.err.println("printList = " + printList.toString());
			} // end of outer for

			System.err.println("printList = " + printList.toString());
			model.addObject("crnPrint", printList);
			model.addObject("FACTORYNAME", Constant.FACTORYNAME);
			model.addObject("FACTORYADDRESS", Constant.FACTORYADDRESS);
			System.out.println("crn Detail List******** " + crnDetailList);

		} catch (Exception e) {
			System.err.println("Exce Occured ");
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		return model;
	}

}
