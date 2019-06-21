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
						<h2 class="pageTitle">GST Register Done Report</h2>
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
						<button class="btn search_btn pull-left" onclick="searchReport()">Search</button>
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

										<th class="col-sm-1" style="text-align: center;">Invoice
											No</th>
										<th class="col-sm-1" style="text-align: center;">Invoice
											Date</th>
										<th class="col-sm-1" style="text-align: center;">Party
											Name</th>
										<th class="col-md-1" style="text-align: center;">GST No</th>
										<th class="col-md-1" style="text-align: center;">HSN Code</th>
										<th class="col-md-1" style="text-align: center;">Billed
											Qty</th>
										<th class="col-md-1" style="text-align: center;">Taxable
											Amt</th>
										<th class="col-md-1" style="text-align: center;">Cgst %</th>
										<th class="col-md-1" style="text-align: center;">Cgst Amt</th>
										<th class="col-md-1" style="text-align: center;">Sgst %</th>
										<th class="col-md-1" style="text-align: center;">Sgst Amt</th>
										<th class="col-md-1" style="text-align: center;">Bill Amt</th>
									</tr>

								</thead>
								<tbody>
							</table>
						</div>
						<div class="table-wrap">
							<table id="table_grid" class="main-table" border="1">
								<thead>
									<tr class="bgpink">

										<th class="col-sm-1">Sr.No.</th>

										<th class="col-sm-1" style="text-align: center;">Invoice
											No</th>
										<th class="col-sm-1" style="text-align: center;">Invoice
											Date</th>
										<th class="col-sm-1" style="text-align: center;">Party
											Name</th>
										<th class="col-md-1" style="text-align: center;">GST No</th>
										<th class="col-md-1" style="text-align: center;">HSN Code</th>
										<th class="col-md-1" style="text-align: center;">Billed
											Qty</th>
										<th class="col-md-1" style="text-align: center;">Taxable
											Amt</th>
										<th class="col-md-1" style="text-align: center;">Cgst %</th>
										<th class="col-md-1" style="text-align: center;">Cgst Amt</th>
										<th class="col-md-1" style="text-align: center;">Sgst %</th>
										<th class="col-md-1" style="text-align: center;">Sgst Amt</th>
										<th class="col-md-1" style="text-align: center;">Bill Amt</th>
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
	function searchReport() {
		//	var isValid = validate();
		//document.getElementById('chart').style.display ="display:none";
		document.getElementById("table_grid").style = "block";

		var isValid = validate();
		if (isValid == true) {
			//var selectedFr = $("#selectFr").val();
			var from_date = $("#fromdatepicker").val();
			var to_date = $("#todatepicker").val();

			$('#loader').show();

			$.getJSON('${getGstRegister}',

			{
				//fr_id_list : JSON.stringify(selectedFr),
				fromDate : from_date,
				toDate : to_date,
				ajax : 'true'

			}, function(data) {

				$('#table_grid td').remove();
				$('#loader').hide();
				if (data == "") {
					alert("No records found !!");
					//document.getElementById("expExcel").disabled = true;
					//document.getElementById("PDFButton").disabled = true;

				}

				$.each(data, function(key, report) {

					//document.getElementById("expExcel").disabled = false;
					//document.getElementById("PDFButton").disabled = false;

					//document.getElementById('range').style.display = 'block';

					var index = key + 1;
					//var tr = "<tr>";
					var tr = $('<tr></tr>');
					tr.append($('<td></td>').html(report.invoiceNo));
					tr.append($('<td></td>').html(report.billDate));
					tr.append($('<td style="text-align:left;"></td>').html(
							report.frName));
					tr.append($('<td style="text-align:left;"></td>').html(
							report.frGstNo));
					tr.append($('<td style="text-align:left;"></td>').html(
							report.hsnCode));
					tr.append($('<td style="text-align:right;"></td>').html(
							(report.billQty)));
					tr.append($('<td style="text-align:right;"></td>').html(
							report.taxableAmt.toFixed(2)));
					tr.append($('<td style="text-align:right;"></td>').html(
							report.cgstPer));
					tr.append($('<td style="text-align:right;"></td>').html(
							report.cgstAmt.toFixed(2)));

					tr.append($('<td style="text-align:right;"></td>').html(
							report.sgstPer));
					tr.append($('<td style="text-align:right;"></td>').html(
							report.sgstAmt.toFixed(2)));
					tr.append($('<td style="text-align:right;"></td>').html(
							report.grandTotal.toFixed(2)));

					$('#table_grid tbody').append(tr);

				})

			});
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
