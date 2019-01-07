<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
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

	<table width="100%" border="0" cellspacing="0"
														cellpadding="0" id="table_grid" class="table table-bordered">
								<thead >
									<tr class="bgpink">
									<th>Sr. NO.</th>
									<th>Party Name</th>
									<th>GSTIN</th>
									<th>Bill No</th>
									<th>Bill Date</th>
									
									<th>Taxable Amt</th>
									<th>Tax Rate</th>
									<th>IGST</th>
									<th>CGST</th>
									<th>SGST</th>
									<th>Bill Amount</th>
									<th>CESS</th>
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
													<td>GFPL</td>
													<td>#012</td>
													<td><c:out value="${reportList.billNo}" /></td>
													<td><c:out value="${reportList.billDate}" /></td>
													<td><c:out value="${reportList.taxableAmt}" /></td>
													 <c:set var="taxAmount" value="${taxAmount + reportList.taxableAmt}"/>
													 <td><c:out value="${reportList.taxRate}" /></td>
													<td><c:out value="${reportList.igstRs}" /></td>
														<c:set var="igst"  value="${igst + reportList.igstRs}"/>
													<td><c:out value="${reportList.cgstRs}" /></td>
														<c:set var="cgst"  value="${cgst+reportList.cgstRs }"/>
													<td><c:out value="${reportList.sgstRs}" /></td>
														<c:set var="sgst"  value="${sgst+reportList.sgstRs }"/>
													
													<td><c:out value="${reportList.grandTotal}" /></td>
														<c:set var="grandTotal"  value="${grandTotal+reportList.grandTotal }"/>
													<td><c:out value="${reportList.cess}" /></td>
												</tr>
												</c:forEach>
								  <tr>
								  <td colspan='5'><b>Total</b></td>
								  <td><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value ="${taxAmount}"/></b></td>
								    <td></td>
								     <td><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${igst}"/></b></td>
								      <td><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${cgst}"/></b></td>
								       <td><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${sgst}"/></b></td>
								   
								     <td><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${grandTotal}"/></b></td>
								       <td></td>
								  </tr>
							 </tbody>
								</table>

	
</body>
</html>