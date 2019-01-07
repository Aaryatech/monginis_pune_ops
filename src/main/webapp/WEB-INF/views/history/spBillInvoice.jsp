<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Special Cake Invoice</title>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>
    </title>
    </head>
<style type="text/css">
<!--
.style2 {font-size: 14px}
.style5 {font-size: 10px}
.style6 {font-size: 9px}
.style7 {
	font-size: 12px;
	font-weight: bold;
}
.style8 {
	font-size: 11px;
	font-weight: bold;
}
-->
</style>
</head>

</head>
<body >
	 
				  <table width="250" border="0" cellspacing="0" cellpadding="0" style="padding:5px; font-family:verdana; font-size:12px; border:1px solid #E7E7E7;">

  <tbody> 
  <tr>
  <td align="right" style="padding:0px;"></td>  </tr><tr>
     <c:choose>
      <c:when test="${frGstType==10000000}">
        <td colspan="2" align="center" style="padding:1px;"><p>TAX INVOICE</p></td> 
 
      </c:when>
      <c:otherwise>
         <td colspan="2" align="center" style="padding:1px;"><p>BILL OF SUPPLY</p></td> 
      </c:otherwise>
      </c:choose>
    </tr>
    <tr>
      <td colspan="2" align="center" style="padding:2px; border-bottom:1px solid #E7E7E7;"><p class="style2" ><b>${sessionScope.frDetails.frName}</b><br /><span style="font-size: 10px; font-family: Arial;">(The Monginis Cake Shop)</span></p>
       </td>
    </tr>
    <tr>
      <td colspan="2" align="center" style="padding:3px;font-family: Arial; border-bottom:1px solid #E7E7E7; font-size:12px;"><p class="style5">
         <br />
          <strong>${sessionScope.frDetails.frAddress}</strong><br/><br/>
           <c:choose>
            <c:when test="${frGstType>=2000000}">
                GSTIN:<strong>${sessionScope.frDetails.frGstNo}</strong><br/>
             </c:when>
        </c:choose>
       
          Phone:<strong>${sessionScope.frDetails.frMob}</strong><br/>
          
           <c:choose>
          <c:when test="${frGstType==2000000}">
              <span style="font-size:9px; font-family: Arial;">COMPOSITION TAXABLE FIRM, NOT SUPPOSED TO<br />
                BE COLLECT TAX ON SUPPLIES</span>
          </c:when>
          <c:when test="${frGstType==10000000}">
           <span style="font-size:9px; font-family: Arial;">COMPOSITION TAXABLE PERSON, NOT TO<br />
          COLLECT TAX ON SUPPLIES        </span>
          
          </c:when>
        </c:choose>
       
    </tr>
    <tr>
      <td colspan="2">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
  <tbody>
    <tr>
      <td  style="font-size:8px">Invoice No: </td>
      <td style="font-size:9px">${spCakeOrder.spBookForMobNo}(${spCakeOrder.spOrderNo})</td>
      <td style="font-size:8px">Date:</td>
        
      <td style="font-size:9px">${date} </td>
    </tr >
     <tr>
      <td>Order No</td>
      <td colspan="3">${spCakeOrder.spDeliveryPlace}</td>
     
      </tr> 
    <tr>
      <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="5" class="tbl-inner">
        <tbody>
          <tr>
            <th width="43%" align="left" bgcolor="#ECECEC">Item</th>
            <th width="8%" bgcolor="#ECECEC">KG.</th>
            <th width="13%" bgcolor="#ECECEC">Rate</th>
            <th width="29%" align="right" bgcolor="#ECECEC">Amt</th>
          </tr>
          <%-- <c:forEach items="${billList}" var="billList" varStatus="count"> --%>
          <tr>
            <td><p style="font-size:12px">${spCakeOrder.spName}(${spCakeOrder.itemId})-${spCakeOrder.spfName}</p>
            <td align="center"><p style="font-size:12px">${spCakeOrder.spSelectedWeight}</p></td>
            <td align="center"><p style="font-size:12px">${spCakeOrder.spSubTotal/spCakeOrder.spSelectedWeight}</p></td>
            <td align="right"><p style="font-size:12px">${spCakeOrder.spSubTotal}</p></td>
          </tr>
        <%--   </c:forEach> --%>
         <tr>
          <!--   <td rowspan="3">&nbsp;</td> -->
           <%--  <td colspan="3" align="right"><span class="style5"><strong>Add Rate :</strong></span></td>
            <td align="right"><span class="style5"><strong>${spCakeOrder.spTotalAddRate}</strong></span></td> --%>
          </tr>
          <tr>
          <!--   <td rowspan="3">&nbsp;</td> -->
            <td colspan="3" align="right"><span class="style5"><strong>Total :</strong></span></td>
            <td align="right"><span class="style5"><strong>${spCakeOrder.spSubTotal}</strong></span></td>
          </tr>
            <tr>
           <!--  <td rowspan="3">&nbsp;</td> -->
            <td colspan="3" align="right"><span class="style5"><strong>Advance :</strong></span></td>
            <td align="right"><span class="style5"><strong>${spCakeOrder.spAdvance}</strong></span></td>
          </tr>
          <tr>
            <td colspan="3" align="right"><span class="style5"><strong>Remaining Amt:</strong></span></td>
            <td align="right"><span class="style5"><strong>${spCakeOrder.rmAmount}</strong></span></td>
          </tr>
        </tbody>
      </table></td>
      </tr>
  </tbody>
</table>      </td>
    </tr>
      <c:choose>
      <c:when test="${frGstType==10000000 }">
    		<tr>
				<td colspan="2"><table width="100%" border="0" cellspacing="0"
						cellpadding="7">
						<tr>
							<th width="17%" align="left" bgcolor="#ECECEC" rowspan="2">Taxable<br />
								Value
							</th>
							<th bgcolor="#ECECEC" colspan="2">CGST</th>
							<th bgcolor="#ECECEC" colspan="2">SGST</th>
							<th width="25%" align="center" bgcolor="#ECECEC">Total</th>
						</tr>

						<tr>
							<th width="14%" bgcolor="#ECECEC">%</th>
							<th width="15%" bgcolor="#ECECEC">Amt</th>
							<th width="16%" bgcolor="#ECECEC">%</th>
							<th width="13%" bgcolor="#ECECEC">Amt</th>
							<th width="25%" align="center" bgcolor="#ECECEC">GST</th>
						</tr>
						<c:set var="taxAmount" value="${0}" />
						<c:set var="totaltax" value="${0 }" />
						<c:set var="cgst" value="${0 }" />
						<c:set var="sgst" value="${0 }" />
					<%-- 	<c:forEach items="${custBilltax}" var="custBilltax"
							varStatus="count"> --%>
							<tr>
								<td><fmt:formatNumber
									type="number" maxFractionDigits="2" minFractionDigits="2"  value="${spCakeOrder.spSubTotal-(spCakeOrder.tax1Amt+spCakeOrder.tax2Amt)}" /></td>
								<c:set var="taxAmount"
									value="${spCakeOrder.spSubTotal-(spCakeOrder.tax1Amt+spCakeOrder.tax2Amt)}" />
								<td>${spCakeOrder.tax1}</td>
								<td>${spCakeOrder.tax1Amt}</td>
								<c:set var="cgst" value="${cgst+spCakeOrder.tax1Amt }" />
								<td>${spCakeOrder.tax2}</td>
								<td>${spCakeOrder.tax2Amt}</td>
								<c:set var="sgst" value="${sgst+spCakeOrder.tax2Amt }" />
								
								<td><fmt:formatNumber
									type="number" maxFractionDigits="2" minFractionDigits="2"  value="${spCakeOrder.spSubTotal-taxAmount}" /></td>
								<c:set var="totaltax"
									value="${totaltax+(spCakeOrder.tax1Amt+spCakeOrder.tax2Amt) }" />
							</tr>
					<%-- 	</c:forEach> --%>
						<td width="14%" colspan="6">&nbsp;</td>

						</tr>
						<tr>
							<td bgcolor="#ECECEC"><b><fmt:formatNumber type="number"
										maxFractionDigits="2" minFractionDigits="2"  value="${taxAmount}" /></b></td>

							<td bgcolor="#ECECEC"></td>
							<td bgcolor="#ECECEC"><b><fmt:formatNumber type="number"
									maxFractionDigits="2" minFractionDigits="2"  value="${cgst}" /></b></td>
							<td bgcolor="#ECECEC"></td>
							<td bgcolor="#ECECEC"><b><fmt:formatNumber type="number"
									maxFractionDigits="2" minFractionDigits="2"  value="${sgst}" /></b></td>
							<td align="center" bgcolor="#ECECEC"><b><fmt:formatNumber
									type="number" maxFractionDigits="2" minFractionDigits="2"  value="${totaltax}" /></b></td>
						</tr>
						
					</table></td>
			</tr>
			</c:when>
			</c:choose>
 <tr>
							<td align="center"
								style="border-top: 1px solid #E7E7E7; padding: 5px 7px;"
								colspan="6"><p class="style8">Thank You, Visit Again
									!!!
									</p></td>
						</tr>
						<tr>
							<td style="border-top: 1px solid #E7E7E7; padding: 5px 7px;"
								colspan="6">We declare that this invoice shows the actual
								price of the goods<br /> described and that all particulars are
								true and correct.<br /> <span style="font-size: 10px"> I/
									We hereby certify that food/oods mentioned in this invoice are
									warranted to be the nature and quality which this
									purports/purport to be.</span>
							</td>
						</tr>
    <tr>
      <td width="200" align="center" style="border-top:1px solid #E7E7E7; padding:5px 7px;"><strong>For ${sessionScope.frDetails.frName}</strong></td>
    </tr>
  </tbody>
</table>
			</body>	<body onload="directPrint()">
	<script>
	 function directPrint()
	{
		 
		window.print();
		window.close();
	} 
	
	</script>
</body>
</html>