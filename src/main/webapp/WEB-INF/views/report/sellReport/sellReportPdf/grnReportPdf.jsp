<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GRN Report</title>

<style type="text/css">
table {
    border-collapse: collapse;
    width: 100%;
}

th, td {
    text-align: left;
      padding: 2px;
    font-size: 10;
}


th {
    background-color: #EA3291;
    color: white;
}
</style>
</head>
<body onload="myFunction()">
<h4 align="center">GRN Report</h4>
<div align="center"> <h6>  ${frName} &nbsp;&nbsp;&nbsp;&nbsp;From &nbsp; ${fromDate}  &nbsp;To &nbsp; ${toDate}</h6></div>
	<table width="100%" border="1" cellspacing="0"
														cellpadding="1" id="table_grid" class="table table-bordered">
								<thead >
									<tr class="bgpink">
									<th style="text-align:center;">Sr no.</th>
									<th style="text-align:center;" >Date</th>
									<th style="text-align:center;" >Item Name</th>
									<th style="text-align:center;">Tax Rate</th> 
									<th style="text-align:center;">Taxable Amt</th>
								 	<th style="text-align:center;">Total Tax</th>
									<th style="text-align:center;">GRN Amt</th>   
									<th style="text-align:center;">Apr Taxable Amt</th>
									<th style="text-align:center;">Apr CGST</th>
									<th style="text-align:center;">Apr SGST</th>
									<th style="text-align:center;">Apr IGST</th>
									<th style="text-align:center;">Apr Total</th> 
									<!-- <th align="center">CESS</th>  -->
								  </tr>
								</thead>
								<tbody >
								 <c:set var="taxAmount"   value="${0}"/>
								 <c:set var="taxableAmt"   value="${0}"/>
								 <c:set var="total"   value="${0}"/>
								<c:set var="igst"  value="${0 }"/>
								<c:set var="cgst"  value="${0 }"/>
								<c:set var="sgst"  value="${0 }"/>
								<c:set var="aprTotal"  value="${0 }"/>
								<c:set var="aprTaxable"  value="${0 }"/>
								  	<c:forEach items="${reportList}" var="reportList" varStatus="count">
												<tr>
													<td align="center"><c:out value="${count.index+1}" /></td>
													 <td><c:out value="${reportList.grnGvnDate}" /></td> 
													<td><c:out value="${reportList.itemName}" /></td> 
													<td style="text-align:right"><c:out value="${reportList.taxRate}" /></td> 
													<td style="text-align:right">
													<fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value = "${reportList.taxableAmt}"/>
													</td>
													  <c:set var="taxableAmt"  value="${taxableAmt+reportList.taxableAmt }"/>
													  
													  <td style="text-align:right">
													<fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value = "${reportList.totalTax}"/>
													</td> 
														<c:set var="taxAmount"  value="${taxAmount+reportList.totalTax }"/>
														 
														 <td style="text-align:right">
													<fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value = "${reportList.grnGvnAmt}"/>
													</td> 
														<c:set var="total"  value="${total + reportList.grnGvnAmt}"/>
														 
													
													<td style="text-align:right">
													<fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value = "${reportList.aprTaxableAmt}"/>
													</td> 
														<c:set var="aprTaxable"  value="${aprTaxable + reportList.aprTaxableAmt}"/>
														
														<td style="text-align:right">
													<fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value = "${reportList.aprCgstRs}"/>
													</td>  
														<c:set var="cgst"  value="${cgst + reportList.aprCgstRs}"/>
													 
													 <td style="text-align:right">
													<fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value = "${reportList.aprSgstRs}"/>
													</td>
														<c:set var="sgst"  value="${sgst + reportList.aprSgstRs}"/>
														
														 <td style="text-align:right">
													<fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value = "${reportList.aprIgstRs}"/>
													</td>
														<c:set var="igst"  value="${igst + reportList.aprIgstRs}"/>
														
														 <td style="text-align:right">
													<fmt:formatNumber type = "number"  maxFractionDigits = "2" minFractionDigits="2" value = "${reportList.aprGrandTotal}"/>
													</td>
														<c:set var="aprTotal"  value="${aprTotal + reportList.aprGrandTotal}"/>
													<%-- <td><c:out value="${reportList.sess}" /></td> --%>
													
													
												</tr>
												</c:forEach>
								  <tr>
								  <td colspan='4'><b>Total</b></td>
								  <td style="text-align:right"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value ="${taxableAmt}"/></b></td>
								   <td style="text-align:right"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${taxAmount}"/></b></td> 
								   <td style="text-align:right"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${total}"/></b></td>
								   <td style="text-align:right"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${igst}"/></b></td>
								  <td style="text-align:right"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value ="${cgst}"/></b></td> 
								  <td style="text-align:right"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value ="${sgst}"/></b></td> 
								     <td style="text-align:right"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${igst}"/></b></td> 
								     <td style="text-align:right"><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${aprTotal}"/></b></td>
								     
								       
								     <!--  <td><b>Total</b></td> -->
								  </tr>
							 </tbody>
								</table>

	
</body>
</html>