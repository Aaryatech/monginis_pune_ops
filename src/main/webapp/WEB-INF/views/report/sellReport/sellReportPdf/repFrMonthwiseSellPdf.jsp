<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Month wise Sale Report</title>
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
<h4 align="center">Monthwise Sale Report</h4>
<div align="center"> <h6>  ${frName} &nbsp;&nbsp;&nbsp;&nbsp;From &nbsp; ${fromDate}  &nbsp;To &nbsp; ${toDate}</h6></div>
	<table width="100%" border="1" cellspacing="0"
														cellpadding="1" id="table_grid" class="table table-bordered">
								<thead >
									<tr class="bgpink">
									<th style="text-align:center;" style="width:100px">Sr no.</th>
									<!-- <th align="center">Bill No</th> -->
									<th style="text-align:center;">Month</th>
									<th style="text-align:center;">Amount</th>
									<th style="text-align:center;">Cash</th>
								 	<th style="text-align:center;">Card</th>
									<th style="text-align:center;">Other </th> 
								  </tr>
								</thead>
								
								
								 <tbody >
								 <c:set var="totalAmount"   value="${0}"/>
								<c:set var="totalCash"  value="${0 }"/>
								<c:set var="totalCard"  value="${0 }"/>
								<c:set var="totalOther"  value="${0 }"/>
								  	<c:forEach items="${reportList}" var="reportList" varStatus="count">
												<tr>
													<td style="text-align:center;"><c:out value="${count.index+1}" /></td>
													<%-- <c:forEach items="${month}" var="month" varStatus="count1">
													<c:choose>
																	<c:when test="${count1.index==reportList.month}">
																		
																		<td style="text-align:center;"><c:out value="${month}" /></td>
																	</c:when>
													</c:choose>
														
													</c:forEach> --%>
													<td style="text-align:left;"><c:out value="${reportList.month}" /></td>
													<c:set var="amt"  value="${reportList.cash + reportList.card + reportList.other }"/>
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${amt}" /></td>
													 <c:set var="totalAmount" value="${totalAmount + amt}"/>
													 
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${reportList.cash}" /></td>
														<c:set var="totalCash"  value="${totalCash + reportList.cash}"/>
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${reportList.card}" /></td>
														<c:set var="totalCard"  value="${totalCard+reportList.card }"/>
													<td style="text-align:right;"><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value="${reportList.other}" /></td>
														<c:set var="totalOther"  value="${totalOther+reportList.other }"/>
													<%-- <td><c:out value="${reportList.sess}" /></td> --%>
													
													
												</tr>
												</c:forEach>
								  <tr>
								  <td colspan='2'><b>Total</b></td>
								  <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "2"  maxFractionDigits = "2" value ="${totalAmount}"/></b></td>
								     <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${totalCash}"/></b></td>
								     <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${totalCard}"/></b></td>
								       <td style="text-align:right;"><b><fmt:formatNumber type = "number" minFractionDigits = "2" maxFractionDigits = "2" value = "${totalOther}"/></b></td>
								     <!--  <td><b>Total</b></td> -->
								  </tr>
							 </tbody>
								</table>

	
</body>
</html>