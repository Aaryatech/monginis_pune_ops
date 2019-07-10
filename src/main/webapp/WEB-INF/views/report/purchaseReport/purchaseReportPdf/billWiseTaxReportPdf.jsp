<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="com.monginis.ops.constant.Constant" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill wise Purchase Tax Report</title>

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
<h4 align="center">Purchase Billwise Tax (Input) Report</h4>
<div align="center"> <h6>  ${frName} &nbsp;&nbsp;&nbsp;&nbsp;From &nbsp; ${fromDate}  &nbsp;To &nbsp; ${toDate}</h6></div>
	<table width="100%"  border="1" cellspacing="0"
														cellpadding="1" id="table_grid" class="table table-bordered">
								<thead >
									<tr class="bgpink">
									<th  style="text-align:center;">Sr. NO.</th>
									<th  style="text-align:center;">Party Name</th>
								<!-- 	<th>GSTIN</th> --><!-- 
									<th  style="text-align:center;">Bill No</th>
									<th  style="text-align:center;">Bill Date</th> -->
									
									<th  style="text-align:center;">Taxable Amt</th>
									<th  style="text-align:center;">Tax Rate</th>
									<th  style="text-align:center;">IGST</th>
									<th  style="text-align:center;">CGST</th>
									<th  style="text-align:center;">SGST</th>
									<th  style="text-align:center;">Bill Amount</th>
									<th  style="text-align:center;">CESS</th>
								  </tr>
								</thead>
								
								 <tbody >
								  <tbody >
								 <c:set var="taxAmount"   value="${0}"/>
								<c:set var="igst"  value="${0 }"/>
								<c:set var="cgst"  value="${0 }"/>
								<c:set var="sgst"  value="${0 }"/>
								<c:set var="grandTotal"  value="${0 }"/>
								  	<c:forEach items="${reportList}" var="reportList" varStatus="count">
												<tr>
													<td align="center"><c:out value="${count.index+1}" /></td>
													<td>${Constant.FACTORYNAME}</td>
												<!-- 	<td>#012</td> -->
													<%-- <td><c:out value="${reportList.billNo}" /></td>
													<td><c:out value="${reportList.billDate}" /></td> --%>
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${reportList.taxableAmt}"/></td>
													 <c:set var="taxAmount" value="${taxAmount + reportList.taxableAmt}"/>
													 <td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${reportList.taxRate}"/></td>
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${reportList.igstRs}"/></td>
														<c:set var="igst"  value="${igst + reportList.igstRs}"/>
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${reportList.cgstRs}"/></td>
														<c:set var="cgst"  value="${cgst+reportList.cgstRs }"/>
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${reportList.sgstRs}"/></td>
														<c:set var="sgst"  value="${sgst+reportList.sgstRs }"/>
													
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${reportList.grandTotal}"/></td>
														<c:set var="grandTotal"  value="${grandTotal+reportList.grandTotal}"/>
													<td style="text-align:right;"><c:out value="${reportList.cess}" /></td>
												</tr>
												</c:forEach>
								  <tr>
								  <td colspan='2'><b>Total</b></td>
								  <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "0"  maxFractionDigits = "0" value ="${taxAmount}"/></b></td>
								    <td></td>
								     <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "0"  maxFractionDigits = "0" value = "${igst}"/></b></td>
								      <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "0"  maxFractionDigits = "0" value = "${cgst}"/></b></td>
								       <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "0"  maxFractionDigits = "0" value = "${sgst}"/></b></td>
								   
								     <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "0"  maxFractionDigits = "0" value = "${grandTotal}"/></b></td>
								      <td  style="text-align:right;">0.0</td>
								  </tr>
							 </tbody>
								</table>

	
</body>
</html>