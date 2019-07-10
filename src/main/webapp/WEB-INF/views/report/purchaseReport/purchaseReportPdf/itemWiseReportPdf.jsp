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
<title>Item wise Purchase Report</title>

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
<h4 align="center">Itemwise Purchase Report</h4>
<div align="center"> <h6>  ${frName} &nbsp;&nbsp;&nbsp;&nbsp;From &nbsp; ${fromDate}  &nbsp;To &nbsp; ${toDate}</h6></div>
	<table width="100%" border="1" cellspacing="0"
														cellpadding="1" id="table_grid" class="table table-bordered">
								<thead >
									<tr class="bgpink">
									<th style="text-align:center;">Sr.No.</th>
									<th style="text-align:center;">Party Name</th>
									<th style="text-align:center;">Item Name</th>
									<th style="text-align:center;">Qty</th>
									<th style="text-align:center;">Rate</th>
									<th style="text-align:center;">Amount</th>
								
								  </tr>
								</thead>
								
								 <tbody >
								  <tbody >
								
								<c:set var="grandTotal"  value="${0 }"/>
								  	<c:forEach items="${reportList}" var="reportList" varStatus="count">
												<tr>
													<td align="center"><c:out value="${count.index+1}" /></td>
													
													<td >${Constant.FACTORYNAME}</td>
													<td><c:out value="${reportList.itemName}" /></td>
													
													<td style="text-align:right;"><c:out value="${reportList.qty}" /></td>
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${reportList.rate}"/></td>
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${reportList.total}"/></td>
														<c:set var="grandTotal"  value="${grandTotal+reportList.total}"/>
													
												</tr>
												</c:forEach>
								  <tr>
								  <td colspan='5'><b>Total</b></td>
							
								     <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${grandTotal}"/></b></td>
								      
								  </tr>
							 </tbody>
								</table>

	
</body>
</html>