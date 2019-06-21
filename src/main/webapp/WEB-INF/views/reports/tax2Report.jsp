<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<style>
table, th, td {
	border: 1px solid #9da88d;
}
</style>

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
</script>
<!--rightNav-->


</head>
<body> --%>
<!--datepicker-->
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

<c:url var="getGstRegister" value="/getGstRegister"></c:url>


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
						<h2 class="pageTitle">Tax Report</h2>
					</div>
				</div>

				<div class="box-content">
					<form action="${pageContext.request.contextPath}/showTax2Report"
						class="form-horizontal" method="get" id="validation-form">
						<div class="row">
							<div class="row">
								<input type="hidden" name="frId" id="frId" value="${frId}">

								<div class="col-md-2 from_date">
									<h4 class="pull-left">From Date:-</h4>
								</div>
								<div class="col-md-2 ">
									<input id="fromdatepicker" autocomplete="off"
										value="${fromDate}" class="texboxitemcode texboxcal"
										placeholder="DD-MM-YYYY" name="fromDate" type="text">
								</div>
								<div class="col-md-1">
									<h4 class="pull-left">To Date:-</h4>
								</div>
								<div class="col-md-2 ">
									<input id="todatepicker" autocomplete="off" value="${toDate}"
										class="texboxitemcode texboxcal" placeholder="DD-MM-YYYY"
										name="toDate" type="text">
								</div>
								<div class="col-md-2">
									<button class="btn search_btn pull-left"
										onclick="searchReport()">Search</button>

								</div>

							</div>
						</div>
					</form>

					<div class="row">
						<div class="clearfix"></div>


						<div id="table-scroll" class="table-scroll">
							<div id="faux-table" class="faux-table" aria="hidden">
								<table id="table_grid1" class="main-table" border="1">
									<thead>
										<tr class="bgpink">


											<th>Sr.No.</th>
											<th>Invoice No</th>
											<th>Bill No.</th>
											<th>Bill_Date</th>
											<th>Party Name</th>
											<th>GSTIN</th>
											<th>Sell @ 28%</th>
											<th>Sell @ 18%</th>
											<th>Sell @ 12%</th>
											<th>Sell @ 5%</th>
											<th>Sell @ 0%</th>
											<th>Taxable Value</th>
											<th>SGST @ 14%</th>
											<th>CGST @ 14%</th>
											<th>SGST @ 9%</th>
											<th>CGST @ 9%</th>
											<th>SGST @ 6%</th>
											<th>CGST @ 6%</th>
											<th>SGST @ 2.5%</th>
											<th>CGST @ 2.5%</th>
											<th>SGST @ 0%</th>
											<th>CGST @ 0%</th>
											<th>SGST Value</th>
											<th>CGST Value</th>
											<th>GROSS BILL</th>


										</tr>

									</thead>

								</table>
							</div>
							<div class="table-wrap">
								<table id="table_grid" class="main-table">
									<thead>
										<tr class="bgpink">


											<th>Sr.No.</th>
											<th>Invoice No</th>
											<th>Bill No.</th>
											<th>Bill_Date</th>
											<th>Party Name</th>
											<th>GSTIN</th>
											<th>Sell @ 28%</th>
											<th>Sell @ 18%</th>
											<th>Sell @ 12%</th>
											<th>Sell @ 5%</th>
											<th>Sell @ 0%</th>
											<th>Taxable Value</th>
											<th>SGST @ 14%</th>
											<th>CGST @ 14%</th>
											<th>SGST @ 9%</th>
											<th>CGST @ 9%</th>
											<th>SGST @ 6%</th>
											<th>CGST @ 6%</th>
											<th>SGST @ 2.5%</th>
											<th>CGST @ 2.5%</th>
											<th>SGST @ 0%</th>
											<th>CGST @ 0%</th>
											<th>SGST Value</th>
											<th>CGST Value</th>
											<th>GROSS BILL</th>


										</tr>

									</thead>
									<tbody>

										<c:set var="totalCgstAmt" value="0" />
										<c:set var="totalSgstAmt" value="0" />
										<c:set var="totalTaxableAmt" value="0" />

										<c:set var="totalGrandTotal" value="0" />

										<c:forEach items="${taxReportList}" var="taxList"
											varStatus="count">




											<tr>
												<td><c:out value="${count.index+1}" /></td>
												<td><c:out value="${taxList.invoiceNo}" /></td>
												<td><c:out value="${taxList.billNo}" /></td>
												<td><c:out value="${taxList.billDate}" /></td>
												<td><c:out value="${taxList.frName}" /></td>
												<td><c:out value="${taxList.frGstNo}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.taxableAmtTwentyEight}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.taxableAmtEighteen}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.taxableAmtTwelve}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.taxableAmtFive}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.taxableAmtZero}" /></td>
												<c:set var="taxableAmt">
													<fmt:formatNumber type="number" minFractionDigits="2"
														maxFractionDigits="2"
														value="${(taxList.taxableAmtZero)+(taxList.taxableAmtFive)+(taxList.taxableAmtTwelve)+(taxList.taxableAmtEighteen)+(taxList.taxableAmtTwentyEight)}" />
												</c:set>
												<c:set var="sgstAmt">
													<fmt:formatNumber type="number" minFractionDigits="2"
														maxFractionDigits="2"
														value="${(taxList.sgstAmtTwentyEight)+(taxList.sgstAmtEighteen)+(taxList.sgstAmtTwelve)+(taxList.sgstAmtFive)+(taxList.sgstAmtZero)}" />
												</c:set>
												<c:set var="cgstAmt">
													<fmt:formatNumber type="number" minFractionDigits="2"
														maxFractionDigits="2"
														value="${(taxList.cgstAmtTwentyEight)+(taxList.cgstAmtEighteen)+(taxList.cgstAmtTwelve)+(taxList.cgstAmtFive)+(taxList.cgstAmtZero)}" />
												</c:set>
												<c:set var="total">
													<fmt:formatNumber type="number" minFractionDigits="2"
														maxFractionDigits="2"
														value="${((taxList.taxableAmtZero)+(taxList.taxableAmtFive)+(taxList.taxableAmtTwelve)+(taxList.taxableAmtEighteen)+(taxList.taxableAmtTwentyEight))+((taxList.sgstAmtTwentyEight)+(taxList.sgstAmtEighteen)+(taxList.sgstAmtTwelve)+(taxList.sgstAmtFive)+(taxList.sgstAmtZero))+((taxList.cgstAmtTwentyEight)+(taxList.cgstAmtEighteen)+(taxList.cgstAmtTwelve)+(taxList.cgstAmtFive)+(taxList.cgstAmtZero))}" />
												</c:set>

												<td style="text-align: right;"><c:out
														value="${taxableAmt}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.sgstAmtTwentyEight}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.cgstAmtTwentyEight}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.sgstAmtEighteen}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.cgstAmtEighteen}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.sgstAmtTwelve}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.cgstAmtTwelve}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.sgstAmtFive}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.cgstAmtFive}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.sgstAmtZero}" /></td>
												<td style="text-align: right;"><c:out
														value="${taxList.cgstAmtZero}" /></td>
												<td style="text-align: right;"><c:out
														value="${sgstAmt}" /></td>
												<td style="text-align: right;"><c:out
														value="${cgstAmt}" /></td>
												<td style="text-align: right;">${total}</td>

												<c:set var="totalCgstAmt"
													value="${totalCgstAmt+(taxList.cgstAmtTwentyEight)+(taxList.cgstAmtEighteen)+(taxList.cgstAmtTwelve)+(taxList.cgstAmtFive)+(taxList.cgstAmtZero)}" />
												<c:set var="totalSgstAmt"
													value="${totalSgstAmt+(taxList.sgstAmtTwentyEight)+(taxList.sgstAmtEighteen)+(taxList.sgstAmtTwelve)+(taxList.sgstAmtFive)+(taxList.sgstAmtZero)}" />
												<c:set var="totalTaxableAmt"
													value="${totalTaxableAmt+(taxList.taxableAmtZero)+(taxList.taxableAmtFive)+(taxList.taxableAmtTwelve)+(taxList.taxableAmtEighteen)+(taxList.taxableAmtTwentyEight)}" />
												<c:set var="totalGrandTotal"
													value="${totalGrandTotal+((taxList.taxableAmtZero)+(taxList.taxableAmtFive)+(taxList.taxableAmtTwelve)+(taxList.taxableAmtEighteen)+(taxList.taxableAmtTwentyEight))+((taxList.sgstAmtTwentyEight)+(taxList.sgstAmtEighteen)+(taxList.sgstAmtTwelve)+(taxList.sgstAmtFive)+(taxList.sgstAmtZero))+((taxList.cgstAmtTwentyEight)+(taxList.cgstAmtEighteen)+(taxList.cgstAmtTwelve)+(taxList.cgstAmtFive)+(taxList.cgstAmtZero))}" />
											</tr>
										</c:forEach>


										<tr>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>


											<td style="text-align: left;">Total</td>
											<td style="text-align: right;"><fmt:formatNumber
													type="number" maxFractionDigits="2" minFractionDigits="2"
													value="${totalTaxableAmt}" /></td>

											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>
											<td></td>

											<td style="text-align: right;"><fmt:formatNumber
													type="number" maxFractionDigits="2" minFractionDigits="2"
													value="${totalSgstAmt}" /></td>


											<td style="text-align: right;"><fmt:formatNumber
													type="number" maxFractionDigits="2" minFractionDigits="2"
													value="${totalCgstAmt}" /></td>

											<td style="text-align: right;"><fmt:formatNumber
													type="number" maxFractionDigits="2" minFractionDigits="2"
													value="${totalGrandTotal}" /></td>

										</tr>
									</tbody>
								</table>

							</div>

						</div>
						<!--table end-->
						<br>
						<div class="form-group" id="range">
							<div class="col-sm-3  controls">
								<input type="button" id="expExcel" class="btn btn-primary"
									value="EXPORT TO Excel" onclick="exportToExcel();">
							</div>
						</div>
					</div>
				</div>




			</div>
			<!--rightSidebar-->

		</div>
		<!--fullGrid-->
	</div>
	<!--rightContainer-->

</div>
<!--wrapper-end-->

<!--easyTabs-->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
<!--easyTabs-->


<script type="text/javascript">
	function validate() {

		var fromDate = $("#fromdatepicker").val();
		var toDate = $("#todatepicker").val();

		var isValid = true;

		if (fromDate == "" || fromDate == null) {

			isValid = false;
			alert("Please select From Date");
		} else if (toDate == "" || toDate == null) {

			isValid = false;
			alert("Please select To Date");
		}
		return isValid;

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

	function exportToExcel() {

		window.open("${pageContext.request.contextPath}/exportToExcelNew");
		document.getElementById("expExcel").disabled = true;
	}
</script>
<script type="text/javascript">
	function genPdf() {
		var isValid = validate();
		if (isValid == true) {
			var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			var frId = document.getElementById("frId").value;
			window
					.open('${pageContext.request.contextPath}/pdf?reportURL=pdf/showPurchaseBillwiseReportPdf/'
							+ fromDate + '/' + toDate + '/' + frId);
		}
	}
</script>
</body>
</html>
