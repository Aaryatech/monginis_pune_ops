<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Item wise Sale Report</title>

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
<h4 align="center">Categorywise- Itemwise Sale Report</h4>
<div align="center"> <h6>  ${frName} &nbsp;&nbsp;&nbsp;&nbsp;From &nbsp; ${fromDate}  &nbsp;To &nbsp; ${toDate}</h6></div>
	<table width="100%" border="1" cellspacing="0"
														cellpadding="1" id="table_grid" class="table table-bordered">
								<thead >
									<tr class="bgpink">
									<th style="text-align:center;">Sr no.</th>
									
									<th style="text-align:center;">Item Name</th>
									<!-- <th style="text-align:center;">Item Id</th> -->
									<th style="text-align:center;">Group Name</th>
								 	<th style="text-align:center;">Quantity</th>
									<th style="text-align:center;">Amount</th> 
								  </tr>
								</thead>
								 <tbody >
								 <c:set var="totalAmount"   value="${0}"/>
								<c:set var="qty"  value="${0 }"/>
									  	<c:forEach items="${reportList}" var="reportList" varStatus="count">
												<tr>
													<td align="center"><c:out value="${count.index+1}" /></td>
													
													<td><c:out value="${reportList.itemName}" /></td>
													<%-- <td><c:out value="${reportList.itemId}" /></td> --%>
													<td style="text-align:center;"><c:out value="${reportList.catName}" /></td>
													<td style="text-align:right;"><c:out value="${reportList.qty}" /></td>
													<c:set var="qty"  value="${qty + reportList.qty }"/>
													
													
													<td style="text-align:right;"><fmt:formatNumber type = "number"  minFractionDigits = "2" maxFractionDigits = "2" value="${reportList.amount}" /></td>
													 <c:set var="totalAmount" value="${totalAmount + reportList.amount}"/>
													 
													
													
												</tr>
										</c:forEach>
								  <tr>
								  <td colspan='3'><b>Total</b></td>
								  <td style="text-align:right;"><b><c:out value="${qty}" /></b></td>
								     <td style="text-align:right;"><b><fmt:formatNumber type = "number"  minFractionDigits = "2"  maxFractionDigits = "2" value = "${totalAmount}"/></b></td>
								     
								     <!--  <td><b>Total</b></td> -->
								  </tr>
							 </tbody>
								</table>

	
</body>
</html>