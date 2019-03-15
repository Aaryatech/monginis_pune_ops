<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Month wise Purchase Tax Report</title>

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
<h4 align="center">Monthwise Purchase Report</h4>
<div align="center"> <h6>  ${frName} &nbsp;&nbsp;&nbsp;&nbsp;From &nbsp; ${fromDate}  &nbsp;To &nbsp; ${toDate}</h6></div>
	<table width="100%" border="1" cellspacing="0"
														cellpadding="1" id="table_grid" class="table table-bordered">
								<thead >
									<tr class="bgpink">
									
									<th style="text-align:center;">Sr.No.</th>
									<th style="text-align:center;">MONTH</th>
									<th style="text-align:center;">Taxable Amt</th>
									<th style="text-align:center;">IGST</th>
									<th style="text-align:center;">CGST</th>
									<th style="text-align:center;">SGST</th>
									<th style="text-align:center;">CESS</th>
								<!-- 	<th style="text-align:center;">ROFF</th> -->
									<th style="text-align:center;">TOTAL</th>
								
								  </tr>
								
								</thead>
								
								<tbody >
								 <c:set var="taxAmount"   value="${0}"/>
								<c:set var="igst"  value="${0}"/>
								<c:set var="cgst"  value="${0}"/>
								<c:set var="sgst"  value="${0}"/>
								<c:set var="grandTotal"  value="${0}"/>
								  	<c:forEach items="${reportList}" var="reportList" varStatus="count">
												<tr>
													<td align="center"><c:out value="${count.index+1}" /></td>
											<%-- 	<c:forEach items="${month}" var="month" varStatus="count1">
													<c:choose>
																	<c:when test="${count1.index==reportList.month}">
																		
																		<td style="text-align:center;"><c:out value="${month}" /></td>
																	</c:when>
													</c:choose>
														
													</c:forEach> --%>
													<td style="text-align:center;"><c:out value="${reportList.month}" /></td>
													<td style="text-align:right;">${reportList.taxableAmt}</td>
													 <c:set var="taxAmount" value="${taxAmount + reportList.taxableAmt}"/>
													<td style="text-align:right;">${reportList.igstRs}</td>
														<c:set var="igst"  value="${igst + reportList.igstRs}"/>
														
														<c:set var="cgstRs"  value="${reportList.cgstRs}"/>
								
													<td style="text-align:right;">${reportList.cgstRs}</td>
														<c:set var="cgst"  value="${cgst+reportList.cgstRs}"/>
														<c:set var="sgstRs"  value="${sgst+reportList.sgstRs}"/>
													<td style="text-align:right;">${reportList.sgstRs}</td>
														<c:set var="sgst"  value="${sgst+reportList.sgstRs}"/>
														
													<td style="text-align:right;"><c:out value="${reportList.sess}" /></td>
												<%-- 	<td style="text-align:right;"><c:out value="${reportList.roundOff}" /></td> --%>
													<td style="text-align:right;">${reportList.grandTotal}</td>
														<c:set var="grandTotal"  value="${grandTotal+reportList.grandTotal}"/>
													
												</tr>
												</c:forEach>
								  <tr>
								  <td colspan='2'><b>Total</b></td>
								  <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "0" maxFractionDigits = "0" value ="${taxAmount}"/></b></td>
								     <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "0" maxFractionDigits = "0" value = "${igst}"/></b></td>
								      <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "0" maxFractionDigits = "0" value = "${cgst}"/></b></td>
								       <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "0" maxFractionDigits = "0" value = "${sgst}"/></b></td>
								     <td></td>
								  <!--     <td></td> -->
								     <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "0" maxFractionDigits = "0" value = "${grandTotal}"/></b></td>
								      <!--  <td><b>Total</b></td> -->
								  </tr>
							 </tbody>
								</table>

	
</body>
</html>