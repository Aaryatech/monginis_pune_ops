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
      <td colspan="2" align="center" style="padding:1px;"><p>BILL OF SUPPLY</p>
      <p class="style2" ><b>${billList[0].frName}</b><br /><span style="font-size: 10px; font-family: Arial;">(The Monginis Cake Shop)</span></p>
                      GSTIN:<b> ${billList[0].gstn}</b> <br />
      
      </td>
    </tr>
    <%-- tr>
      <td colspan="2" align="center" style="padding:1px; border-bottom:1px solid #E7E7E7;"><p class="style2" ><b>${billList[0].frName}</b><br /><span style="font-size: 10px; font-family: Arial;">(The Monginis Cake Shop)</span></p>
       </td>
    </tr> --%>
    <tr>
      <td colspan="2" align="center" style="padding:3px;font-family: Arial; border-bottom:1px solid #E7E7E7; font-size:12px;"><p class="style5">${billList[0].frAddress}
         <br /> 
         
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
        
    </tr>
    <tr>
      <td colspan="2">
      <table width="100%" border="0" cellspacing="0" cellpadding="7">
  <tbody>
    <tr>
      <td  align="left">Invoice No: </td>
      <td align="left">${billList[0].invoiceNo} </td>
     <fmt:parseDate pattern="yyyy-MM-dd" value="${billList[0].billDate}" var="dateparsed" />
      <td style="font-size: 10px;"><fmt:formatDate pattern="dd-MM-yyyy" value="${dateparsed}" /></td>
    </tr>
    <tr>
      <td>Name</td>
      <td colspan="3">${billList[0].custName}</td>
     
      </tr>
      <tr>
        <td >GST NO:</td>
      <td colspan="3">${billList[0].userGstNo}</td>
      </tr>
    <tr>
      <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="5" class="tbl-inner">
        <tbody>
          <tr>
            <th width="43%" align="left" bgcolor="#ECECEC">Item</th>
            <th width="8%" bgcolor="#ECECEC">Qty</th>
            <th width="13%" bgcolor="#ECECEC">Rate</th>
            <th width="29%" align="center" bgcolor="#ECECEC">Amt</th>
          </tr>
          <c:forEach items="${billList}" var="billList" varStatus="count">
          <tr>
            <td><p style="font-size:12px">${billList.itemName}</p>
				<p style="font-size:10px">${billList.hsnCode}</p></td>
            <td align="center"><p style="font-size:12px">${billList.qty}</p></td>
            <td align="center"><p style="font-size:12px">${billList.mrp}</p></td>
            <td align="right"><p style="font-size:12px">   <fmt:formatNumber type="number"
			maxFractionDigits="2" minFractionDigits="2" value=" ${billList.qty*billList.mrp}"/></p></td>
          </tr>
          </c:forEach>
          <tr>
            <td rowspan="3">&nbsp;</td>
            <td colspan="2" align="right"><span class="style5"><strong>Total :</strong></span></td>
            
                        <td align="right"><span class="style5"><strong><c:choose><c:when test="${billList[0].billType eq 'R'}">${billList[0].discountAmt}</c:when><c:otherwise>${billList[0].intBillAmt}</c:otherwise> </c:choose> </strong></span></td>
            
           <%--  <td align="right"><span class="style5"><strong>${billList[0].discountAmt}</strong></span></td> --%>
          </tr>
          <c:if test="${billList[0].discountPer gt 0 or billList[0].intDiscAmt gt 0}">
          <tr>
           <td colspan="2" align="right"><span class="style5"><strong>Discount ${billList[0].discountPer}% :</strong></span></td>
           <td colspan="2" align="right"><span class="style5"><strong>${billList[0].intDiscAmt}</strong></span></td>
          </tr>
          </c:if>
          <tr>
            <td colspan="2" align="right"><span class="style7">Bill Total:</span></td>
<%--             <td align="right"><span class="style7">${billList[0].intBillAmt}</span></td>
 --%>            <td align="right"><span class="style7"><c:choose><c:when test="${billType eq 'R'}">${billList[0].discountAmt-billList[0].intDiscAmt}</c:when><c:otherwise>${billList[0].intBillAmt}</c:otherwise> </c:choose></span></td>
         
   </tr>
        </tbody>
      </table></td>
      </tr>
  </tbody>
</table>      </td>
    </tr>
    <tr>
      <td colspan="2">
        <table width="100%" border="0" cellspacing="0" cellpadding="7" >
  
 <!--    <tr>
      <td align="center" style="border-top:1px solid #E7E7E7; padding:5px 7px;"><p  class="style6"> Customer Care:7352244444
									</p>       </td>
    </tr> -->
    <tr>
      <td style="border-top:1px solid #E7E7E7; padding:5px 7px;">Kindly consume all Fresh Cream Product within 1 hour unless refrigerated<br/>Seller Registered under Composition Scheme not allowed to collect taxes.<br />
        This is computer generated Invoice does not require signature</td>
    </tr>
</table>      </td>
    </tr>
    <tr>
      <td width="200" align="center" style="border-top:1px solid #E7E7E7; padding:5px 7px;"><strong> ${billList[0].frName}</strong></td>
    </tr>
  </tbody>
</table>
			</body>	<body onload="directPrint()">
	<script>
	function directPrint()
	{
		//alert("JJ");
		window.print();
		window.close();
	}
	
	</script>
</body>
</html>