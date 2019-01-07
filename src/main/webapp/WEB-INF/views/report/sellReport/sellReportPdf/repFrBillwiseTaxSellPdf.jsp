<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
        <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix = "fmt" uri = "http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill wise Sell Tax Report</title>

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
									<th align="center" >Sr no.</th>
									<th align="center">GST No.</th>
									<th align="center">Bill no.</th>
									<th align="center"style="width:100px">Bill Date</th>
									<th align="center">Bill Amount</th>
									<th align="center">Taxable Amount</th>
									<th align="center">Tax Rate</th>
									<th align="center">IGST</th>
								 	<th align="center">CGST</th>
									<th align="center">SGST</th> 
									<!-- <th align="center">CESS</th>  -->
								  </tr>
								</thead>
								<tbody >
								 <c:set var="taxAmount"   value="${0}"/>
								 <c:set var="billAmount"   value="${0}"/>
								 
								<c:set var="igst"  value="${0 }"/>
								<c:set var="cgst"  value="${0 }"/>
								<c:set var="sgst"  value="${0 }"/>
								  	<c:forEach items="${reportList}" var="reportList" varStatus="count">
												<tr>
													<td align="center"><c:out value="${count.index+1}" /></td>
													<td></td>
													<td><c:out value="${reportList.sellBillNo}" /></td>
													<td><c:out value="${reportList.billDate}" /></td>
													<td><c:out value="${reportList.bill_amount}" /></td>
													<c:set var="billAmount" value="${billAmount + reportList.bill_amount}"/>
													<td><c:out value="${reportList.tax_amount}" /></td>
													<c:set var="taxAmount" value="${taxAmount + reportList.tax_amount}"/>
													<td><c:out value="${reportList.tax_per}" /> %</td>
													 
													<td><c:out value="${reportList.igst}" /></td>
														<c:set var="igst"  value="${igst + reportList.igst}"/>
													<td><c:out value="${reportList.cgst}" /></td>
														<c:set var="cgst"  value="${cgst+reportList.cgst }"/>
													<td><c:out value="${reportList.sgst}" /></td>
														<c:set var="sgst"  value="${sgst+reportList.sgst }"/>
													<%-- <td><c:out value="${reportList.sess}" /></td> --%>
													
													
												</tr>
												</c:forEach>
								  <tr>
								  <td colspan='4'><b>Total</b></td>
								  <td><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value ="${billAmount}"/></b></td>
								    
								  <td><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value ="${taxAmount}"/></b></td>
								    <td></td>
								     <td><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${igst}"/></b></td>
								      <td><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${cgst}"/></b></td>
								       <td><b><fmt:formatNumber type = "number"  maxFractionDigits = "2" value = "${sgst}"/></b></td>
								     <!--  <td><b>Total</b></td> -->
								  </tr>
							 </tbody>
								</table>

	
</body>
</html>