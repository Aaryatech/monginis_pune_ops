<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
     <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Customer Bill</title>

<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <title>
    </title>
    </head>
<style type="text/css">

.style2 {font-size: 12px;padding-top: 2px;}
.style5 {font-size: 10px;padding-top: 2px;}
.style6 {font-size: 9px;padding-top: 2px;}
.style7 {
	font-size: 11px;
	font-weight: bold;padding-top: 2px;
}
.style8 {
	font-size: 11px;
	font-weight: bold;padding-top: 2px;
}

</style>
</head>

</head>
<body >
	 
				  <table width="250" border="0" cellspacing="0" cellpadding="0" style="padding:2px; font-family:verdana; font-size:12px; border:1px solid #E7E7E7;">
  <tbody>
  <tr>
      <td colspan="2" align="center" style="padding:0px;"><span>BILL OF SUPPLY</span>
      
      
      </td>
 </tr>
     <tr>
       <td colspan="2" align="center" style="padding:0px;border-top: 1px solid #E7E7E7;">
      <span><b>${billList[0].frName}</b><br /><span style="font-size: 10px; font-family: Arial;">(The Monginis Cake Shop)</span></span>
                <br /> <span style="font-size: 10px;">GSTIN:<b> ${billList[0].gstn} </b>  </span>
      
      </td>
    </tr>
    <%-- tr>
      <td colspan="2" align="center" style="padding:1px; border-bottom:1px solid #E7E7E7;"><p class="style2" ><b>${billList[0].frName}</b><br /><span style="font-size: 10px; font-family: Arial;">(The Monginis Cake Shop)</span></p>
       </td>
    </tr> --%>
    <tr>
      <td colspan="2" align="center" style="padding:2px;font-family: Arial; border-bottom:1px solid #E7E7E7; font-size:9px;"><span >${billList[0].frAddress}

         
        <%--  <c:choose>
            <c:when test="${frGstType==2000000}">
                GSTIN:<b> ${billList[0].gstn}</b> <br />
             </c:when>
        </c:choose>
          Phone:<strong>${billList[0].frMob}</strong><br/><br/>
        <c:choose>
          <c:when test="${frGstType==2000000}">
              <span style="font-size:9px; font-family: Arial;">COMPOSITION TAXABLE FIRM, NOT SUPPOSED TO<br />
                BE COLLECT TAX ON SUPPLIES</span>
          </c:when>
        </c:choose></td> --%>
        </span>
    </tr>
    <tr>
      <td colspan="2">
      <table width="100%" border="0" cellspacing="0" cellpadding="2">
  <tbody>
    <tr>
      <td  align="left" style="font-size: 10px;">Invoice No: </td>
      <td align="left" style="font-size: 10px;">${billList[0].invoiceNo} </td>
       <fmt:parseDate pattern="yyyy-MM-dd" value="${billList[0].billDate}" var="dateparsed" />
      <td style="font-size: 10px;"><fmt:formatDate pattern="dd-MM-yyyy" value="${dateparsed}" /></td>
    </tr>
    <tr>
      <td style="font-size: 10px;">Name</td>
      <td colspan="3" style="font-size: 10px;">${billList[0].custName}</td>
    
      </tr><tr>
        <td style="font-size: 10px;">GST NO:</td>
      <td colspan="3" style="font-size: 10px;">${billList[0].userGstNo}</td>
      </tr>
    <tr>
      <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="3" class="tbl-inner">
        <tbody>
          <tr>
            <th width="43%" align="left" bgcolor="#ECECEC">Item</th>
            <th width="8%" bgcolor="#ECECEC">Qty</th>
            <th width="13%" bgcolor="#ECECEC">Rate</th>
            <th width="29%" align="center" bgcolor="#ECECEC">Amt</th>
          </tr>
          <c:forEach items="${billList}" var="billList" varStatus="count">
          <tr>
            <td><span style="font-size:11px">${billList.itemName}</span><br>
				<span style="font-size:10px">HSN-${billList.hsnCode}</span></td>
            <td align="center"><span style="font-size:11px">${billList.qty}</span></td>
            <td align="center"><span style="font-size:11px">${billList.mrp}</span></td>
            <td align="right"><span style="font-size:11px">
            <fmt:formatNumber type="number"
			maxFractionDigits="2" minFractionDigits="2" value=" ${billList.qty*billList.mrp}"/>
           </span></td>
          </tr>
          </c:forEach>
          <tr>
            <td rowspan="3">&nbsp;</td>
            <td colspan="2" align="right"><span class="style5"><strong>Total :</strong></span></td>
            
            
            <td align="right"><span class="style5"><strong><c:choose><c:when test="${billType eq 'R'}"><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${billList[0].discountAmt}"/></c:when><c:otherwise><fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${billList[0].intBillAmt}"/></c:otherwise> </c:choose> </strong></span></td>
          </tr>
          <c:if test="${billList[0].discountPer gt 0 or billList[0].intDiscAmt gt 0}">
          <tr>
           <td colspan="2" align="right"><span class="style5"><strong>Discount <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${billList[0].discountPer}"/> % :</strong></span></td>
           <td colspan="2" align="right"><span class="style5"><strong> <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${billList[0].intDiscAmt}"/></strong></span></td>
          </tr>
          </c:if>
          <tr>
            <td colspan="2" align="right"><span class="style7">Bill Total:</span></td>
            <td align="right"><span class="style7"><c:choose><c:when test="${billType eq 'R'}">
            <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${billList[0].discountAmt-billList[0].intDiscAmt}"/>
            </c:when><c:otherwise> <fmt:formatNumber type="number" maxFractionDigits="2" minFractionDigits="2" value="${billList[0].intBillAmt}"/></c:otherwise> </c:choose></span></td>
          </tr>
        </tbody>
      </table></td>
      </tr>
  </tbody>
</table>      </td>
    </tr>
    <tr>
      <td colspan="2">
        <table width="100%" border="0" cellspacing="0" cellpadding="2" >
  
    <tr>
      <td align="center" style="border-top:1px solid #E7E7E7; padding:1px;font-size: 9px;"><span> Customer Care: 7352244444
									</span>       </td>
    </tr>
    <tr>
      <td style="border-top:1px solid #E7E7E7; padding:2px;font-size: 9px;">Kindly consume all Fresh Cream Product within 1 hour unless refrigerated.<br/> <c:if test="${frGstType=='2000000'}">Seller Registered under Composition Scheme not allowed to collect taxes.<br /></c:if>  
        This is computer generated Invoice does not require signature.</td>
    </tr>
</table>      </td>
    </tr>
    <tr>
      <td width="200" align="center" style="border-top:1px solid #E7E7E7; padding:2px;"><strong> ${billList[0].frName}</strong></td>
    </tr>
  </tbody>
</table>
			</body>	<body onload="directPrint()">
	<script>
	function directPrint()
	{
		//alert("JJ");
		window.print();
		//window.close();
	}
	
	</script>
</body>
</html>