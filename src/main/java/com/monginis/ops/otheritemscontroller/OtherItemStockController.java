package com.monginis.ops.otheritemscontroller;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.monginis.ops.constant.Constant;
import com.monginis.ops.model.FrSupplier;
import com.monginis.ops.model.Franchisee;
import com.monginis.ops.model.OtherItemStockDetail;
import com.monginis.ops.model.OtherItemStockHeader;
import com.monginis.ops.model.othitemstock.OtherItemCurStock;

@Controller
public class OtherItemStockController {
	
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
        System.out.println("Maximum for " + monthStr +" is "
                + calendar.getActualMaximum(Calendar.DATE));
 
        boolean isLastDay = calendar.get(Calendar.DATE) == calendar.getActualMaximum(Calendar.DATE);
        System.out.println("The calendar date " +
                (isLastDay ? "is " : "is not ") +
                "the last day of the month.");
        
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
	        month = month+1;
        int isMonthEndAppli=0;
      //  System.err.println("stock month " +stockHeader.get(0).getMonth());
        System.err.println("Month " +month);
        if(stockHeader.size()>0)
        if(month>stockHeader.get(0).getMonth()) {
        	isMonthEndAppli=1;
        }else if(stockHeader.get(0).getMonth()==12 && month==1) {
        	
        	isMonthEndAppli=1;
        	
        }
        System.err.println("isMonthEndAppli " +isMonthEndAppli);
        model.addObject("isMonthEndAppli", isMonthEndAppli);
		model.addObject("supplierList", supplierList);
		return model;

	}
	
	
	
	
	
	
	@RequestMapping(value = "/getOtherStock", method = RequestMethod.GET)
	public @ResponseBody List<OtherItemCurStock> getOtherStock(HttpServletRequest request, HttpServletResponse response) {
		
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
		
		String	strDate=stockHeader.get(0).getYear()+"/"+stockHeader.get(0).getMonth()+"/01";
		System.err.println("str Date from Date  " +strDate);

		map = new LinkedMultiValueMap<String, Object>();
		map.add("frId", frDetails.getFrId());
		map.add("fromDate", strDate);
		map.add("toDate", dateFormat.format(todaysDate));
		map.add("month", stockHeader.get(0).getMonth());
		map.add("catId",7 );

		OtherItemCurStock[] list = rest.postForObject(Constant.URL + "/getOtherItemCurStock", map, OtherItemCurStock[].class);
		otherStockList = new ArrayList<>(Arrays.asList(list));
		
		
		}else {
			
			
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
	        Date d1= cal.getTime();
	      //  int month=fDate1.getMonth();
	        
	        int month = cal.get(Calendar.MONTH)+1;

	        System.err.println("month is " +month);
			
			System.err.println("Its Stock Bet Date d1 =   " +d1);
			
			String prevFromDate= sdf1.format(d1);
			
			String prevToDate=incrementDate(fr,-1);
			
			System.err.println("prevFromDate =   " +prevFromDate +"prev toDate " +prevToDate);
			
			System.out.println("FromDate "+fr);

			System.out.println("toDate "+to);
			
			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", fr);
			map.add("toDate", to);
			map.add("prevFromDate", prevFromDate);
			map.add("prevToDate",prevToDate );
			map.add("catId", 7);
			map.add("month", month);
			
			OtherItemCurStock[] list = rest.postForObject(Constant.URL + "/getOtherItemStockBetDate", map, OtherItemCurStock[].class);
			otherStockList = new ArrayList<>(Arrays.asList(list));
		}
		System.err.println("Stock list " +otherStockList.toString());

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
		
		
		//stockHeadList.get(0).setStatus(1);
		
		
		
		OtherItemStockHeader stockHeader=stockHeadList.get(0);
		
		
			System.err.println(" In if stock Header size =0;");
			
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

			Date todaysDate = new Date();
			
			String	strDate=stockHeadList.get(0).getYear()+"/"+stockHeadList.get(0).getMonth()+"/01";
			System.err.println("str Date from Date  " +strDate);
			
			
			
			
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
	        Date d1= cal.getTime();


			String thisMonthLastDate= sdf1.format(d1);
			System.err.println("thisMonthLastDate   " +thisMonthLastDate);
			//

			map = new LinkedMultiValueMap<String, Object>();
			map.add("frId", frDetails.getFrId());
			map.add("fromDate", strDate);
			map.add("toDate", thisMonthLastDate);
			map.add("month", stockHeadList.get(0).getMonth());
			map.add("catId",7 );

			OtherItemCurStock[] list = rest.postForObject(Constant.URL + "/getOtherItemCurStock", map, OtherItemCurStock[].class);
			otherStockList = new ArrayList<>(Arrays.asList(list));
			

			// System.out.println("Detail List="+otherStockList);
			ModelAndView model = null;

			// System.out.println(frDetails.getFrId());

			
			stockHeader.setStatus(1);
			map = new LinkedMultiValueMap<String, Object>();
			map.add("headerId", stockHeader.getOtherStockHeaderId());
			
			List<OtherItemStockDetail> detail = null;
			OtherItemStockDetail[] detObj = rest.postForObject(
					Constant.URL + "/getStockHeaderByOtherStockHeaderIdAndOtherItemId", map,
					OtherItemStockDetail[].class);
			detail = new ArrayList<>(Arrays.asList(detObj));
			for (int i = 0; i < detail.size(); i++) {
				
				
				for (int j = 0; j < otherStockList.size(); j++) {
					
				if(detail.get(i).getOtherItemId()==otherStockList.get(j).getId()) {
					int damageQty=Integer.parseInt(request.getParameter("damagedStock"+otherStockList.get(j).getId()));
					System.err.println("Damged for " +otherStockList.get(j).getId() +""+damageQty);
					int closingStock=(int) ((otherStockList.get(j).getOpeningStock()+otherStockList.get(j).getPurchaseQty())-(otherStockList.get(j).getSellQty()+damageQty));
					
					detail.get(i).setDamageStock(damageQty);
					//detail.get(i).setDamageStock(1);
					detail.get(i).setClosingStock(closingStock);
					detail.get(i).setPurchaseStock((int)otherStockList.get(j).getPurchaseQty());
					detail.get(i).setSalesStock((int)otherStockList.get(j).getSellQty());
					
					break;
				}//end of if 
				
				} //end of inner for
			}//end of outer for
			
			
			
			

			
			stockHeader.setOtherItemStockList(detail);

			System.out.println("Stock Header before insert =" + stockHeadList);

			OtherItemStockHeader stockHeadResp = rest.postForObject(Constant.URL + "/insertNewOtherStock",
					stockHeader, OtherItemStockHeader.class);

			System.err.println("Stock Header insert response " + stockHeadResp.toString());

			OtherItemStockHeader otherStockHeader = new OtherItemStockHeader();

											
			Calendar c1 = Calendar.getInstance();
			int year1 = c1.get(Calendar.YEAR);
			int month1 = c1.get(Calendar.MONTH);
			String data = "NA";
			
			
			otherStockHeader.setStatus(0);
			
			otherStockHeader.setOtherStockHeaderId(0);
			otherStockHeader.setFrId(frDetails.getFrId());
			
			if(stockHeader.getMonth()!=12) {
			otherStockHeader.setMonth(stockHeader.getMonth()+1);
			otherStockHeader.setYear(stockHeader.getYear());
			}else {
				otherStockHeader.setMonth(1);
				otherStockHeader.setYear(stockHeader.getYear()+1);
			}
			otherStockHeader.setDelStatus(0);
			otherStockHeader.setExFloat1(0);
			otherStockHeader.setExInt1(0);
			otherStockHeader.setExInt2(0);
			otherStockHeader.setExVar1(data);
			otherStockHeader.setExVar2(data);
			
			
			List<OtherItemStockDetail> newStockList = null;
			newStockList=stockHeadResp.getOtherItemStockList();
			List<OtherItemStockDetail> newOtherStockList = new ArrayList<>();

			for(int i=0;i<newStockList.size();i++) {
				
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
	
	/* BETWEEN DATE QUERY CONTINUE
	 SELECT m_item.id,m_item.item_name,m_item.item_id, m_franchisee.fr_name,

COALESCE((SELECT SUM(other_item_stock_detail.opening_stock) FROM other_item_stock_detail,other_item_stock_header WHERE  m_franchisee.fr_id=other_item_stock_header.fr_id and other_item_stock_header.other_stock_header_id=other_item_stock_detail.other_stock_header_id AND other_item_stock_detail.other_item_id=m_item.id AND other_item_stock_header.month  IN(2,3)),0) as opening_stock,

COALESCE((SELECT sum(other_item_stock_detail.damage_stock) FROM other_item_stock_detail,other_item_stock_header WHERE other_item_stock_header.month=2 AND m_franchisee.fr_id=other_item_stock_header.fr_id and other_item_stock_header.other_stock_header_id=other_item_stock_detail.other_stock_header_id AND other_item_stock_detail.other_item_id=m_item.id AND other_item_stock_header.month  IN(2,3)),0) as damaged_stock1,

COALESCE((SELECT SUM(t_other_bill_detail.bill_qty) FROM t_other_bill_header,t_other_bill_detail WHERE 
       t_other_bill_header.bill_no=t_other_bill_detail.bill_no AND t_other_bill_header.fr_id=m_franchisee.fr_id AND t_other_bill_header.bill_date BETWEEN '2019-02-01' AND '2019-02-28' AND t_other_bill_detail.item_id=m_item.id),0) AS purchase_qty1,
       
  COALESCE((SELECT SUM(t_sell_bill_detail.qty) FROM t_sell_bill_detail,t_sell_bill_header WHERE 
       t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no AND t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.bill_date BETWEEN '2019-02-01' AND '2019-02-28' AND t_sell_bill_detail.item_id=m_item.id),0) AS sell_qty1,
       
       
       
       COALESCE((SELECT sum(other_item_stock_detail.damage_stock) FROM other_item_stock_detail,other_item_stock_header WHERE other_item_stock_header.month=2 AND m_franchisee.fr_id=other_item_stock_header.fr_id and other_item_stock_header.other_stock_header_id=other_item_stock_detail.other_stock_header_id AND other_item_stock_detail.other_item_id=m_item.id),0) as damaged_stock,

COALESCE((SELECT SUM(t_other_bill_detail.bill_qty) FROM t_other_bill_header,t_other_bill_detail WHERE 
       t_other_bill_header.bill_no=t_other_bill_detail.bill_no AND t_other_bill_header.fr_id=m_franchisee.fr_id AND t_other_bill_header.bill_date BETWEEN '2019-02-01' AND '2019-02-28' AND t_other_bill_detail.item_id=m_item.id),0) AS purchase_qty,
       
  COALESCE((SELECT SUM(t_sell_bill_detail.qty) FROM t_sell_bill_detail,t_sell_bill_header WHERE 
       t_sell_bill_header.sell_bill_no=t_sell_bill_detail.sell_bill_no AND t_sell_bill_header.fr_id=m_franchisee.fr_id AND t_sell_bill_header.bill_date BETWEEN '2019-02-01' AND '2019-02-28' AND t_sell_bill_detail.item_id=m_item.id),0) AS sell_qty
       
       FROM m_item,m_franchisee 
       
       WHERE m_item.item_grp1=7 AND m_franchisee.fr_id=113 GROUP by m_item.id
       
       
       STOCK BET DATE 15-30 march

firstopening=sum(opening) where stock.month in(month(fromDate), month(toDate))
purchase1=sum(purchase) date bet 1-3 to 1-14
sell1 =sum(sell) date bet 1-3 to 1-14
damage1 sum(damage)  where stock.month in(month(fromDate), month(toDate))


purchase= bet 15-30 march
sell= bet 15-30 march
damage in damage1 sum(damage)  where stock.month in(month(fromDate))

tempOpe=(firstopening+purchase1)-(sell1+damage1)

curStock=(tempOpe+purchase)-(sell+damage)

	 */
		
}
