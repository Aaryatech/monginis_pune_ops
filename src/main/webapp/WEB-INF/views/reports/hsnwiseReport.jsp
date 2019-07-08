<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<c:url var="getBillList" value="/getReportHSNwise"></c:url>



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
						<h2 class="pageTitle">View HSN Wise Report</h2>
					</div>
				</div>

				<div class="row">
					<input type="hidden" name="frId" id="frId" value="${frId}">

					<div class="col-md-2 from_date">
						<h4 class="pull-left">From Date:-</h4>
					</div>
					<div class="col-md-2 ">
						<input id="fromdatepicker" autocomplete="off"
							class="texboxitemcode texboxcal" placeholder="DD-MM-YYYY"
							name="fromDate" type="text" value="${todaysDate}">
					</div>
					<div class="col-md-1">
						<h4 class="pull-left">To Date:-</h4>
					</div>
					<div class="col-md-2 ">
						<input id="todatepicker" autocomplete="off"
							class="texboxitemcode texboxcal" placeholder="DD-MM-YYYY"
							name="toDate" type="text" value="${todaysDate}">
					</div>

				</div>
				<br>
				<div class="row">
					<div class="form-group">
						<label class="col-sm-3 col-lg-2	 control-label">Select
							Type</label>
						<div class="col-sm-2 col-lg-4  controls">

							<select data-placeholder="Select Type"
								class="form-control chosen" id="type" name="type" required>
								<option value="">Select Type</option>
								<option value="1">Bill wise</option>
								<option value="2">Credit Note Wise</option>
								<option value="3">Consolidated</option>
							</select>
						</div>



						<div class="col-md-4">
							<button class="btn btn-info" onclick="searchReport()">Search
								Report</button>
							<button class="btn btn-primary" value="PDF" id="PDFButton"
								onclick="genPdf()">PDF</button>

						</div>


					</div>
				</div>
				<br>


				<div class="row">
					<div class="clearfix"></div>


					<div id="table-scroll" class="table-scroll">
						<div id="faux-table" class="faux-table" aria="hidden">
							<table id="table_grid1" class="main-table" border="1">
								<thead>
									<tr class="bgpink">

										<th>Sr.No.</th>
										<th>HSN</th>
										<th>TAX %</th>
										<th>MANUF</th>
										<th>RET</th>
										<th>TOTAL</th>
										<th>TAXABLE AMT</th>
										<th>CGST</th>
										<th>CGST AMT</th>
										<th>SGST</th>
										<th>SGST AMT</th>
										<th>Total</th>
									</tr>


								</thead>
								<tbody>
							</table>
						</div>
						<div class="table-wrap">
							<table id="table_grid" class="main-table" border="1">
								<thead>
									<tr class="bgpink">

										<th>Sr.No.</th>
										<th>HSN</th>
										<th>TAX %</th>
										<th>MANUF</th>
										<th>RET</th>
										<th>TOTAL</th>
										<th>TAXABLE AMT</th>
										<th>CGST</th>
										<th>CGST AMT</th>
										<th>SGST</th>
										<th>SGST AMT</th>
										<th>Total</th>
									</tr>



								</thead>
								<tbody>
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
	function searchReport() {
		//	var isValid = validate();

		var from_date = $("#fromdatepicker").val();
		var to_date = $("#todatepicker").val();
		var type = $("#type").val();

		$('#loader').show();

		$.getJSON('${getBillList}',

		{

			fromDate : from_date,
			toDate : to_date,
			type : type,
			ajax : 'true'

		}, function(data) {

			$('#table_grid td').remove();
			$('#loader').hide();

			if (data == "") {
				alert("No records found !!");
				document.getElementById("expExcel").disabled = true;
			}

			var totalTaxableAmt = 0;
			var totalSgst = 0;
			var totalCgst = 0;
			var totalFinal = 0;

			$.each(data, function(key, report) {

				document.getElementById("expExcel").disabled = false;
				document.getElementById('range').style.display = 'block';
				var index = key + 1;
				//var tr = "<tr>";

				var tr = $('<tr></tr>');

				tr.append($('<td></td>').html(key + 1));

				tr.append($('<td></td>').html(report.itemHsncd));

				tr.append($('<td></td>')
						.html(report.itemTax1 + report.itemTax2));

				tr.append($('<td  style="text-align:right;"></td>').html(
						report.billQty));

				tr.append($('<td  style="text-align:right;"></td>').html(
						report.grnGvnQty));
				tr.append($('<td  style="text-align:right;"></td>').html(
						report.billQty - report.grnGvnQty));

				totalTaxableAmt = totalTaxableAmt + report.taxableAmt;

				totalSgst = totalSgst + report.sgstRs;
				totalCgst = totalCgst + report.cgstRs;

				totalFinal = totalFinal + report.cgstRs + report.sgstRs
						+ report.taxableAmt;

				tr.append($('<td style="text-align:right;"></td>').html(
						report.taxableAmt.toFixed(2)));
				tr.append($('<td style="text-align:right;"></td>').html(
						report.itemTax1));
				tr.append($('<td style="text-align:right;"></td>').html(
						report.cgstRs.toFixed(2)));
				tr.append($('<td style="text-align:right;"></td>').html(
						report.itemTax2));
				tr.append($('<td style="text-align:right;"></td>').html(
						report.sgstRs.toFixed(2)));
				tr.append($('<td style="text-align:right;"></td>').html(
						(report.cgstRs + report.sgstRs + report.taxableAmt)
								.toFixed(2)));

				$('#table_grid tbody').append(tr);

			})

			var tr = $('<tr></tr>');

			tr.append($('<td></td>').html(""));
			tr.append($('<td></td>').html(""));
			tr.append($('<td></td>').html(""));
			tr.append($('<td></td>').html(""));
			tr.append($('<td></td>').html(""));
			tr.append($('<td style="font-weight:bold;"></td>').html("Total"));
			tr.append($('<td style="text-align:right;"></td>').html(
					totalTaxableAmt.toFixed(2)));
			tr.append($('<td></td>').html(""));
			tr.append($('<td style="text-align:right;"></td>').html(
					totalCgst.toFixed(2)));
			tr.append($('<td></td>').html(""));
			tr.append($('<td style="text-align:right;"></td>').html(
					totalSgst.toFixed(2)));

			tr.append($('<td style="text-align:right;"></td>').html(
					totalFinal.toFixed(2)));

			$('#table_grid tbody').append(tr);

		});

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
		var from_date = $("#fromdatepicker").val();
		var to_date = $("#todatepicker").val();

		/* 	window
					.open('${pageContext.request.contextPath}/pdfForReport?url=pdf/showSaleReportByDatePdf/'
							+ from_date + '/' + to_date);
		 */
		window.open("${pageContext.request.contextPath}/getHsnWisePdf/"
				+ from_date + "/" + to_date);

	}
</script>
</body>
</html>
