<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.monginis.ops.constant.Constant" %>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
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

<c:url var="getBillWisePurchase" value="/findBillWisePurchase" />

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
						<h2 class="pageTitle">Billwise Purchase Report</h2>
					</div>
				</div>

				<div class="row">
					<input type="hidden" name="frId" id="frId" value="${frId}">
					<input type="hidden" name="factoryName" id="factoryName" value="${Constant.FACTORYNAME}">
					<div class="col-md-2 from_date">
						<h4 class="pull-left">From Date:-</h4>
					</div>
					<div class="col-md-2 ">
						<input id="fromdatepicker" autocomplete="off"
							class="texboxitemcode texboxcal" placeholder="DD-MM-YYYY"
							name="fromDate" type="text">
					</div>
					<div class="col-md-1">
						<h4 class="pull-left">To Date:-</h4>
					</div>
					<div class="col-md-2 ">
						<input id="todatepicker" autocomplete="off"
							class="texboxitemcode texboxcal" placeholder="DD-MM-YYYY"
							name="toDate" type="text">
					</div>
					<div class="col-md-2">
						<button class="btn search_btn pull-left"
							onclick="billWisePurchaseReport()">Search</button>
						<%-- 		  &nbsp;&nbsp;&nbsp;   <a href='${pageContext.request.contextPath}/pdf?reportURL=showPurchaseBillwiseReportPdf' id="btn_pdf" class="btn search_btn" style="display: none">PDF</a>
 --%>
						<button class="btn btn-primary" value="PDF" id="PDFButton"
							onclick="genPdf()">PDF</button>

					</div>

				</div>

				<div class="row">
					<div class="clearfix"></div>


					<div id="table-scroll" class="table-scroll">
						<div id="faux-table" class="faux-table" aria="hidden">
							<table id="table_grid1" class="main-table" border="1">
								<thead>
									<tr class="bgpink">

										<th class="col-sm-1">Sr.No.</th>
										<!-- <th class="col-md-1">Party Name</th> -->
										<!-- 									<th class="col-md-1">GSTIN</th>
 -->
										<th class="col-sm-1" style="text-align: center;">Invoice
											No</th>
										<th class="col-sm-2" style="text-align: center;">Bill
											Date</th>
										<th class="col-md-2" style="text-align: center;">Taxable
											Amt</th>
										<th class="col-md-1" style="text-align: center;">IGST Amt</th>
										<th class="col-md-1" style="text-align: center;">CGST Amt</th>
										<th class="col-md-1" style="text-align: center;">SGST Amt</th>
										<!-- <th class="col-md-1" style="text-align: center;">R.off</th> -->
										<th class="col-md-2" style="text-align: center;">Bill
											Amount</th>
									</tr>

								</thead>
								<tbody>
							</table>
						</div>
						<div class="table-wrap">
							<table id="table_grid" class="main-table" border="1">
								<thead>
									<tr class="bgpink">

										<th class="col-sm-1" style="text-align: center;">Sr.No.</th>
										<!-- <th class="col-md-1">Party Name</th> -->
										<!-- 									<th class="col-md-1">GSTIN</th>
 -->
										<th class="col-sm-1" style="text-align: center;">Invoice
											No</th>
										<th class="col-sm-2" style="text-align: center;">Bill
											Date</th>
										<th class="col-md-2" style="text-align: center;">Taxable
											Amt</th>
										<th class="col-md-1" style="text-align: center;">IGST Amt</th>
										<th class="col-md-1" style="text-align: center;">CGST Amt</th>
										<th class="col-md-1" style="text-align: center;">SGST Amt</th>
										<!-- 	<th class="col-md-1" style="text-align: center;">R.off</th> -->
										<th class="col-md-2" style="text-align: center;">Bill
											Amount</th>
									</tr>

								</thead>
								<tbody>
							</table>

						</div>

					</div>
					<!--table end-->
					<br>
					<div class="form-group" style="display: none;" id="range">
						<div class="col-sm-3  controls">
							<input type="button" id="expExcel" class="btn btn-primary"
								value="EXPORT TO Excel" onclick="exportToExcel();"
								disabled="disabled">
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
	function billWisePurchaseReport() {
		$('#table_grid td').remove();

		var isValid = validate();

		if (isValid) {

			//document.getElementById('btn_pdf').style.display = "block";
			var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			var factoryName= document.getElementById("factoryName").value;
			$.getJSON(
							'${getBillWisePurchase}',
							{

								fromDate : fromDate,
								toDate : toDate,
								ajax : 'true',

							},
							function(data) {

								var taxTotal = 0;
								var igstTotal = 0;
								var cgstTotal = 0;
								var sgstTotal = 0;
								var roundOffTotal = 0;
								var billTotal = 0;

								$('#loader').hide();
								var len = data.length;

								if (data == "") {
									document.getElementById("expExcel").disabled = true;
								}

								$('#table_grid td').remove();

								$
										.each(
												data,
												function(key,
														billWisePurchaseData) {

													document
															.getElementById("expExcel").disabled = false;
													document
															.getElementById('range').style.display = 'block';

													var partyname =factoryName;
													var gstNo = "#012";

													var tr = $('<tr></tr>');

													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			key + 1));
													/* 	tr.append($('<td class="col-md-1" ></td>').html(partyname));
													 */
													/* 							  	tr.append($('<td class="col-md-1"></td>').html(gstNo));
													 */
													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			billWisePurchaseData.invoiceNo));

													tr
															.append($(
																	'<td class="col-md-2" style="text-align:center;"></td>')
																	.html(
																			billWisePurchaseData.billDate));

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			(billWisePurchaseData.taxableAmt)
																					.toFixed(2)));

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			(billWisePurchaseData.igstRs)
																					.toFixed(2)));

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			(billWisePurchaseData.cgstRs)
																					.toFixed(2)));

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			(billWisePurchaseData.sgstRs)
																					.toFixed(2)));

													/* 	tr
																.append($(
																		'<td class="col-md-1" style="text-align:right"></td>')
																		.html(
																				(billWisePurchaseData.roundOff)
																						.toFixed(2))); */

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			(billWisePurchaseData.grandTotal)
																					.toFixed(2)));

													taxTotal = taxTotal
															+ billWisePurchaseData.taxableAmt;
													cgstTotal = cgstTotal
															+ billWisePurchaseData.cgstRs;
													sgstTotal = sgstTotal
															+ billWisePurchaseData.sgstRs;
													igstTotal = igstTotal
															+ billWisePurchaseData.igstRs;

													roundOffTotal = roundOffTotal
															+ billWisePurchaseData.roundOff;

													billTotal = billTotal
															+ billWisePurchaseData.grandTotal;

													$('#table_grid tbody')
															.append(tr);

												});

								var tr = $('<tr></tr>');

								tr.append($('<td class="col-md-1"></td>').html(
										""));

								tr.append($('<td class="col-md-1"></td>').html(
										""));

								tr
										.append($(
												'<td class="col-md-2" style="font-weight:bold;"></td>')
												.html("Total"));

								tr
										.append($(
												'<td class="col-md-1" style="text-align:right"></td>')
												.html((taxTotal).toFixed(2)));
								tr
										.append($(
												'<td class="col-md-1" style="text-align:right"></td>')
												.html(igstTotal.toFixed(2)));

								tr
										.append($(
												'<td class="col-md-1" style="text-align:right"></td>')
												.html(cgstTotal.toFixed(2)));

								tr
										.append($(
												'<td class="col-md-1" style="text-align:right"></td>')
												.html(sgstTotal.toFixed(2)));

								/* 	tr
											.append($(
													'<td class="col-md-1" style="text-align:right"></td>')
													.html(roundOffTotal.toFixed(2))); */

								tr
										.append($(
												'<td class="col-md-1" style="text-align:right"></td>')
												.html(billTotal.toFixed(2)));

								$('#table_grid tbody').append(tr);

							}

					);
		}
	}
</script>
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
