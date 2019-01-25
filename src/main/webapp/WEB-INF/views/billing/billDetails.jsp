<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<title>Monginis</title>
<link
	href="${pageContext.request.contextPath}/resources/css/monginis.css"
	rel="stylesheet" type="text/css" />
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/feviconicon.png"
	type="image/x-icon" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>

<!--rightNav-->

<!--datepicker-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
	$(function() {
		$("#fromdatepicker").datepicker({ dateFormat: 'dd-mm-yy' });
	});
	$(function() {
		$("#todatepicker").datepicker({ dateFormat: 'dd-mm-yy' });
	});
</script>
<!--datepicker-->


<style>
#snackbar {
    visibility: hidden; /* Hidden by default. Visible on click */
    min-width: 250px; /* Set a default minimum width */
    margin-left: -125px; /* Divide value of min-width by 2 */
    background-color: #333; /* Black background color */
    color: #fff; /* White text color */
    text-align: center; /* Centered text */
    border-radius: 2px; /* Rounded borders */
    padding: 16px; /* Padding */
    position: fixed; /* Sit on top of the screen */
    z-index: 1; /* Add a z-index if needed */
    left: 50%; /* Center the snackbar */
    bottom: 30px; /* 30px from the bottom */
}

/* Show the snackbar when clicking on a button (class added with JavaScript) */
#snackbar.show {
    visibility: visible; /* Show the snackbar */

/* Add animation: Take 0.5 seconds to fade in and out the snackbar. 
However, delay the fade out process for 2.5 seconds */
    -webkit-animation: fadein 0.5s, fadeout 0.5s 2.5s;
    animation: fadein 0.5s, fadeout 0.5s 2.5s;
}

/* Animations to fade the snackbar in and out */
@-webkit-keyframes fadein {
    from {bottom: 0; opacity: 0;} 
    to {bottom: 30px; opacity: 1;}
}

@keyframes fadein {
    from {bottom: 0; opacity: 0;}
    to {bottom: 30px; opacity: 1;}
}

@-webkit-keyframes fadeout {
    from {bottom: 30px; opacity: 1;} 
    to {bottom: 0; opacity: 0;}
}

@keyframes fadeout {
    from {bottom: 30px; opacity: 1;}
    to {bottom: 0; opacity: 0;}
}
</style>
<!--new css added by kalpesh -->
	<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet" type="text/css" />
	
	<!--new css added by kalpesh -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery.mCustomScrollbar.css">
	
</head>
<body onload="myFunction(${billStatus})">
<c:url var="updateBillStatus" value="/updateBillStatus" />

	<!--topLeft-nav-->
	<div class="sidebarOuter"></div>
	<!--topLeft-nav-->

	<!--wrapper-start-->
	<div class="wrapper">

		<!--topHeader-->
		<%-- <c:url var="findAddOnRate" value="/getAddOnRate" />
<c:url var="findItemsByCatId" value="/getFlavourBySpfId" />
<c:url var="findAllMenus" value="/getAllTypes" /> --%>
		<jsp:include page="/WEB-INF/views/include/logo.jsp"></jsp:include>


		<!--topHeader-->

		<!--rightContainer-->
		<div class="fullGrid center">
			<!--fullGrid-->
			<div class="wrapperIn2">

				<!--leftNav-->

				<jsp:include page="/WEB-INF/views/include/left.jsp">
					<jsp:param name="myMenu" value="${menuList}" />
				</jsp:include>


				<!--leftNav-->



				<!--rightSidebar-->
				<div class="sidebarright">
					
		<div class="sidebarright">
		<br>
	
		<div class=" col-md-2">
		    <h2 class=" pull-left">Invoice No:-  <b>${invoiceNo}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h2>
		</div>
	
		<div class="col-md-2">
		    <h2 class="pull-left">Bill Date:- <b> ${billDate}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h2>
		</div>
			<div class="col-md-3">
		    <h2 class="pull-left">Grand Total:- <b> ${grandTotal}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></h2>
		</div>
		<div class="col-md-4 ">
			  <h2 class="pull-left">Bill Status:-<input type="hidden" value="${billStatus}" name="billstatus" id="billstatus"> <c:choose>
																	<c:when test="${billStatus==1}">
																		<b><label  id="status1">Pending</label></b>
																	</c:when>
																	<c:when test="${billStatus==2}">
																		<b><label  id="status2">Received</label></b>
																		
																	</c:when>
																	<c:when test="${billStatus== 3}">
																		<b><label  id="status3">GVN Apply</label></b>
																	</c:when>
																	<c:when test="${billStatus== 4}">
																		<b><label  id="status4">GVN Approve</label></b>
																	</c:when>
																	<c:when test="${billStatus== 5}">
																		<b><label  id="status5">GRN Apply</label></b>
																	</c:when>
																		<c:when test="${billStatus== 6}">
																		<b><label  id="status6">GRN Approve</label></b>
																	</c:when>
																		<c:when test="${billStatus== 7}">
							 									<b><label  id="status7">Closed</label></b>
																	</c:when>

																</c:choose></h2>
		</div>
		
		 <div class="col-md-2 ">
								<input name="" class="buttonsaveorder" value="Received"
									type="button" id="updateStatus" onclick="updateStatus(${billNo})">
							</div>
    </div>
					<div id="snackbar" style="background:pink;">Order Received</div>

					<!--tabNavigation-->
					<div class="cd-tabs">
						<!--tabMenu-->

						<!--tabMenu-->
					<div class="clearfix"></div>


				<div id="table-scroll" class="table-scroll">
					<div id="faux-table" class="faux-table" aria="hidden">
<!-- 					<div class="table-wrap">
 -->						 </div>
							<div class="table-wrap">
						<table id="table_grid" class="main-table">
							<thead>
								
											<tr class="bgpink">
												<th width="138" style="width: 18px" align="left">No</th>
													<th class="col-md-1">Item Name</th>
													<th class="col-md-1">Group</th>
													<th class="col-md-1">Order Qty</th>
													<th class="col-md-1">Billed Qty</th>
												
													<th class="col-md-1">MRP</th>
														<th class="col-md-1">Rate</th>
														<th class="col-md-1">Taxable Amt</th>
													<th class="col-md-1">GST %</th>
													
													<th class="col-md-1">Tax Amt</th>
													<th class="col-md-1">Total</th>
													
													<th class="col-md-1">GRN Type</th>
											</tr>
											</thead>				
											
											
							<tbody>
													
											<c:forEach items="${billDetailsList}" var="billDetailsList" varStatus="count">
												<tr>
													<td class="col-md-1" align="right"><c:out value="${count.index+1}" /></td>
													<td class="col-md-1" align="right"><c:out value="${billDetailsList.itemName}" /></td>
													<td class="col-md-1" align="right"><c:out value="${billDetailsList.catName}" /></td>
													<td class="col-md-1" align="right"><c:out value="${billDetailsList.orderQty}" /></td>
													<td class="col-md-1" align="right"><c:out value="${billDetailsList.billQty}" /></td>
<fmt:formatNumber var="formattedmrp" type="number" minFractionDigits="2" maxFractionDigits="2" value="${billDetailsList.mrp}" />
<c:set var="formattedmrp" value="${formattedmrp}" />
													<td class="col-md-1"align="right"><c:out value="${formattedmrp}" /></td>
<fmt:formatNumber var="formattedrate" type="number" minFractionDigits="2" maxFractionDigits="2" value="${billDetailsList.baseRate}" />
<c:set var="formattedrate" value="${formattedrate}" />													
													<td class="col-md-1"align="right"><c:out value="${formattedrate}" /></td>
<fmt:formatNumber var="formattedtaxableAmt" type="number" minFractionDigits="2" maxFractionDigits="2" value="${billDetailsList.taxableAmt}" />
<c:set var="formattedtaxableAmt" value="${formattedtaxableAmt}" />														
													<td class="col-md-1"align="right"><c:out value="${formattedtaxableAmt}" /></td>
<fmt:formatNumber var="formattedsgstper" type="number" minFractionDigits="2" maxFractionDigits="2" value="${billDetailsList.sgstPer}" />
<c:set var="formattedsgstper" value="${formattedsgstper}" />															
													<c:set var="sgstPer" value="${formattedsgstper}"/>
<fmt:formatNumber var="formattedcgstPer" type="number" minFractionDigits="2" maxFractionDigits="2" value="${billDetailsList.cgstPer}" />
<c:set var="formattedcgstPer" value="${formattedcgstPer}" />													
													<c:set var="cgstPer" value="${formattedcgstPer}"/>
													<td class="col-md-1"align="right"><c:out value="${sgstPer+cgstPer}" /></td>
<fmt:formatNumber var="formattedtotalTax" type="number" minFractionDigits="2" maxFractionDigits="2" value="${billDetailsList.totalTax}" />
<c:set var="formattedtotalTax" value="${formattedtotalTax}" />															
													<td class="col-md-1" align="right"><c:out value="${formattedtotalTax}" /></td>
<fmt:formatNumber var="formattedgrandTotal" type="number" minFractionDigits="2" maxFractionDigits="2" value="${billDetailsList.grandTotal}" />
<c:set var="formattedgrandTotal" value="${formattedgrandTotal}" />														
													<td class="col-md-1" align="right"><c:out value="${formattedgrandTotal}" /></td>
													
													<td class="col-md-1"align="left">
											<c:choose>
											<c:when test="${billDetailsList.grnType==0}">
										     <c:out value="GRN-1" />
										    </c:when>
											<c:when test="${billDetailsList.grnType==1}">
										     <c:out value="GRN-2" />
										    </c:when>
										    <c:when test="${billDetailsList.grnType==2}">
										     <c:out value="GRN-3" />
										    </c:when>
										    <c:when test="${billDetailsList.grnType==3}">
										     <c:out value="No GRN" />
										    </c:when>
										    <c:when test="${billDetailsList.grnType==4}">
										     <c:out value="GRN-3" />
										    </c:when>
										</c:choose>
										</td>
												</tr>
												</c:forEach>
											
										</tbody>

						</table>
					</div></div>
				</div>

					</div><br>
					<div align="center">
								<a href="${pageContext.request.contextPath}/showBill"><input name="" class="buttonsaveorder" value="Go Back"
									  align="center" type="button"></a><!-- onclick="goBack()" -->
							</div>
					 
					<!--tabNavigation-->

				

				</div>
				<!--rightSidebar-->

			</div>
			<!--fullGrid-->
		</div>
		<!--rightContainer-->

	</div>
	<!--wrapper-end-->

	<!--easyTabs-->
	<!--easyTabs-->
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
	<!--easyTabs-->

<script type="text/javascript">
function myFunction(status){
	//alert(status);
	if(status==2)
		{
		document.getElementById("updateStatus").style="display:none";
		
		}
	
}

function updateStatus(billNo)
{
	var x = document.getElementById("snackbar")
	var status=document.getElementById("billstatus").value;
	document.getElementById("status"+status).innerHTML = "Recieved";	
	$.getJSON('${updateBillStatus}',{
		
		billNo : billNo,
		
		ajax : 'true',

	});
	document.getElementById("updateStatus").style="display:none";
	x.className = "show";
	setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
}
function goBack() {
    window.history.back();
}
</script>

<script>
	/*
//  jquery equivalent
jQuery(document).ready(function() {
   jQuery(".main-table").clone(true).appendTo('#table-scroll .faux-table').addClass('clone');
   jQuery(".main-table.clone").clone(true).appendTo('#table-scroll .faux-table').addClass('clone2'); 
 });
*/
(function() {
  var fauxTable = document.getElementById("faux-table");
  var mainTable = document.getElementById("table_grid");
  var clonedElement = table_grid.cloneNode(true);
  var clonedElement2 = table_grid.cloneNode(true);
  clonedElement.id = "";
  clonedElement2.id = "";
  fauxTable.appendChild(clonedElement);
  fauxTable.appendChild(clonedElement2);
})();


	</script>
</body>
</html>
