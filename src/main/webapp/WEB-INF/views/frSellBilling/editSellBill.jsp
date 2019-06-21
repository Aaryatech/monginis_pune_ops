<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/views/include/header.jsp" />

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%-- <!DOCTYPE html>
<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">

<title>Monginis</title>


<link
	href="${pageContext.request.contextPath}/resources/css/monginis.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet" type="text/css"/>	
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/feviconicon.png"
	type="image/x-icon" />
	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>	
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>

<!--rightNav-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/menuzord.js"></script>
	
<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery("#menuzord").menuzord({
		align:"left"
	});
});
</script> --%>
<!--rightNav-->
<!--datepicker-->

<c:url var="getSelectedIdForPrint" value="/billDetailPrint" />


<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
	$(function() {
		$("#todatepicker").datepicker({
			dateFormat : 'dd-mm-yy'
		});
	});
	$(function() {
		$("#fromdatepicker").datepicker({
			dateFormat : 'dd-mm-yy'
		});
	});
</script>
<!--datepicker-->


<!-- </head>
<body>
 -->

<div class="sidebarOuter"></div>

<div class="wrapper">

	<!--topHeader-->

	<jsp:include page="/WEB-INF/views/include/logo.jsp">
		<jsp:param name="frDetails" value="${frDetails}" />

	</jsp:include>

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

			<!-- Place Actual content of page inside this div -->
			<div class="sidebarright">


				<div class="row">
					<div class="col-md-12">
						<h2 class="pageTitle">Edit Bill Details</h2>
					</div>
				</div>

				<div class="row">

					<div class="col-md-2 from_date">
						<h4 class="pull-left">
							Bill No:- <b>${sellBillNo}</b>
						</h4>
					</div>
					<div class="col-md-2 "></div>
					<div class="col-md-3">
						<h4 class="pull-left">
							Bill Date:- <b> ${billDate}</b>
						</h4>
					</div>
					<div class="col-md-2 "></div>

				</div>
				<br />
	<form name="editSellBill" id="editSellBill" method="post"
					action="${pageContext.request.contextPath}/editSellBill">
			<input type="hidden" name="sellBillNo" id="sellBillNo" value="${sellBillNo}" />
				<input type="hidden" name="billDate" id="billDate" value="${billDate}" />
				<div class="row">
					<div class="col-md-12">
						<!--table-->
						<div class="table-responsive">
							<div class="shInnerwidth">

							
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									id="table_grid6" class="table table-bordered">
									<tr class="bgpink">
										<th>Index</th>
										<th>Item Name
										</th>
										<th>Qty
										</th>
										<th>Edit Qty
										</th>
										<th>MRP Base Rate
										</th>
										<th>Taxable Amount
										</th>
										<th>Tax %
										</th>
										<th>Total Tax
										</th>
										<th>MRP
										</th>

										<th>Total
										</th>


									</tr>
									<tbody>

										<c:set var="taxableSum" value="0"></c:set>
										<c:set var="taxSum" value="0"></c:set>
										<c:set var="totalSum" value="0"></c:set>
										<c:set var="qtySum" value="0"></c:set>


										<c:forEach items="${sellBillDetails}" var="sellBill"
											varStatus="count">

											<tr>
												<td><c:out value="${count.index+1}" /></td>
											
												<td align="left"><c:out value="${sellBill.itemName}" /></td><td align="left">${sellBill.qty}</td>
												<td align="left"><input type="number" style="width:50px;" value="${sellBill.qty}" id="qty${sellBill.sellBillDetailNo}" name="qty${sellBill.sellBillDetailNo}"  onchange="onQtyChange(${sellBill.sellBillDetailNo},${sellBillHeader.discountPer},${sellBill.mrpBaseRate},${sellBill.sgstPer},${sellBill.cgstPer},this.value)"/></td>
												<td align="left"><c:out value="${sellBill.mrpBaseRate}" /></td>
												<td align="left" id="taxableAmt${sellBill.sellBillDetailNo}"><c:out value="${sellBill.taxableAmt}" /></td>
												<td align="left"><c:out
														value="${sellBill.sgstPer+sellBill.cgstPer}" /></td>
												<td align="left" id="totalTax${sellBill.sellBillDetailNo}"><c:out value="${sellBill.totalTax}" /></td>
												<td align="left" ><c:out value="${sellBill.mrp}" /></td>
												<td align="left" id="grandTotal${sellBill.sellBillDetailNo}"><c:out value="${sellBill.grandTotal}" /></td>

												<c:set var="taxableSum"
													value="${sellBill.taxableAmt +taxableSum}"></c:set>
												<c:set var="taxSum" value="${sellBill.totalTax +taxSum}"></c:set>
												<c:set var="totalSum"
													value="${sellBill.grandTotal +totalSum}"></c:set>
												<c:set var="qtySum" value="${sellBill.qty+qtySum}"></c:set>

											</tr>
										</c:forEach>
									<%-- 	<tr align="right" >
											<td width="100" colspan='3' align="right"><b>Total</b></td>
											
											<td width="100" align="right"><b><fmt:formatNumber
														type="number" maxFractionDigits="0" minFractionDigits="0"
														value="${qtySum}" /></b></td>
														<td></td>
											<td width="100" align="right"><b><fmt:formatNumber
														type="number" maxFractionDigits="0" minFractionDigits="0"
														value="${taxableSum}" /></b></td>
											<td></td>
											<td width="100" align="right"><b><fmt:formatNumber
														type="number" maxFractionDigits="2" minFractionDigits="2"
														value="${taxSum}" /></b></td>
											<td></td>
											<td width="100" align="right"><b><fmt:formatNumber
														type="number" maxFractionDigits="0" minFractionDigits="0"
														value="${totalSum}" /></b></td>
										</tr>
 --%>
									</tbody>

								</table>

							</div>
							</div>
						<!--table end-->

					</div>
				</div>
					<div class="row">
					<div class="col-md-2"><b>PrevPaid Amt:</b>&nbsp;${sellBillHeader.paidAmt}
				</div>
				<div class="col-md-1"><b>Paid Amt</b>
				</div>
				<div class="col-md-2">
					<input type="number" name="paidAmt" class="form-control"  id="paidAmt" value="${sellBillHeader.grandTotal}" min="0" onchange="onPaidAmt(${sellBillHeader.paidAmt},${sellBillHeader.remainingAmt},this.value)"/>
				</div>
				<div class="col-md-1"><b>Remaining Amt</b>
				</div>
				<div class="col-md-1" id="remainingAmt">
				       0.00
				</div>
				</div>
				 <div align="center">
				<input	name="submit" class="buttonsaveorder" value="Update" type="submit">
				</div> 
</form>
				<%-- <div align="center">
					<a href="${pageContext.request.contextPath}/viewBill"><input
						name="" class="buttonsaveorder" value="Go Back" align="center"
						type="button"></a>
				</div> --%>
			</div>
			<!--rightSidebar-->

		</div>
		<!--fullGrid-->
	</div>
	<!--rightContainer-->
</div>
<script >
function onPaidAmt(paidAmt,remainingAmt,currentPaidAmt)
{
	var remAmt=remainingAmt-currentPaidAmt;
	document.getElementById("remainingAmt").innerHTML=remAmt.toFixed(2);
}
</script>
<script>
function onQtyChange(sellBillDetailNo,discountPer,mrpBaseRate,sgstPer,cgstPer,qty){
	
	var taxableAmt =parseFloat(mrpBaseRate * qty);

	var discAmt = ((taxableAmt * discountPer) / 100);
	taxableAmt = taxableAmt - discAmt;

	var sgstRs = (taxableAmt * sgstPer) / 100;
	var cgstRs = (taxableAmt * cgstPer) / 100;


	var totalTax = sgstRs + cgstRs;

	var grandTotal = totalTax + taxableAmt;
	
	document.getElementById("taxableAmt"+sellBillDetailNo).innerHTML=taxableAmt.toFixed(2);
	document.getElementById("totalTax"+sellBillDetailNo).innerHTML=totalTax.toFixed(2);
	document.getElementById("grandTotal"+sellBillDetailNo).innerHTML=grandTotal.toFixed(2);
	
}
</script>
<!--wrapper-end-->
<!--easyTabs-->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
<!--easyTabs-->


</body>
</html>
