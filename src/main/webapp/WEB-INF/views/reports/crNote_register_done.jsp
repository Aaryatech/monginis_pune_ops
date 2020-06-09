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

<c:url var="getCRNoteRegister" value="/getCRNoteRegisterDone"></c:url>



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
						<h2 class="pageTitle">Credit Note Register Done Report</h2>
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
					<div class="col-md-2">
						<button class="btn search_btn pull-left" onclick="searchReport()">Search</button>
						<button class="btn btn-primary" value="PDF" id="PDFButton"
							onclick="genPdf()" disabled="disabled">PDF</button>


					</div>

				</div>

				<div class="row">
					<div class="clearfix"></div>


					<div id="table-scroll" class="table-scroll">
						<!-- <div id="faux-table" class="faux-table" aria="hidden">
							<table id="table_grid1" class="main-table" border="1">
								<thead>
									<tr class="bgpink">

										<th>Sr</th>
										<th>CRN No</th>
										<th>CRN Date</th>
										<th>Invoice No</th>
										<th>Invoice Date</th>
										<th>Party Name</th>
										<th>GST No</th>
										<th>Tax Rate</th>
										<th>Crn Qty</th>
										<th>Taxable Amt</th>

										<th>Cgst Amt</th>

										<th>Sgst Amt</th>
										<th>Crn Amt</th>
									</tr>



								</thead>
								<tbody>
							</table>
						</div> -->
						<div class="table-responsive">
							<table id="table_grid" class="main-table" border="1">
								<thead>
									<tr class="bgpink">

										<th>Sr</th>
										<th>CRN No</th>
										<th>CRN Date</th>
										<th>Invoice No</th>
										<th>Invoice Date</th>
										<th>Party Name</th>
										<th>GST No</th>
										<th>Tax Rate</th>
										<th>Crn Qty</th>
										<th>Taxable Amt</th>

										<th>Cgst Amt</th>

										<th>Sgst Amt</th>
										<th>Crn Amt</th>
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
		//document.getElementById('chart').style.display ="display:none";
		document.getElementById("table_grid").style = "block";

		var from_date = $("#fromdatepicker").val();
		var to_date = $("#todatepicker").val();
		$('#loader').show();

		$.getJSON('${getCRNoteRegister}',

		{
			fromDate : from_date,
			toDate : to_date,
			ajax : 'true'

		}, function(data) {

			//alert(data);

			$('#table_grid td').remove();
			$('#loader').hide();

			if (data == "") {
				alert("No records found !!");
				document.getElementById("expExcel").disabled = true;
				document.getElementById("PDFButton").disabled = true;
			}
			var crnQty = 0;
			var crnTaxable = 0;
			var cgstAmt = 0;
			var sgstAmt = 0;
			var crnAmt = 0;
			$.each(data, function(key, report) {

				document.getElementById("expExcel").disabled = false;
				document.getElementById("PDFButton").disabled = false;

				document.getElementById('range').style.display = 'block';

				var index = key + 1;
				//var tr = "<tr>";
				var tr = $('<tr></tr>');
				tr.append($('<td></td>').html("" + index));
				tr.append($('<td></td>').html(report.frCode));
				tr.append($('<td></td>').html(report.crnDate));
				tr.append($('<td></td>').html(report.invoiceNo));
				tr.append($('<td></td>').html(report.billDate));
				tr.append($('<td style="text-align:left;"></td>').html(
						report.frName));
				tr.append($('<td style="text-align:left;"></td>').html(
						report.frGstNo));
				crnQty = crnQty + report.crnQty;
				crnTaxable = crnTaxable + report.crnTaxable;
				cgstAmt = cgstAmt + report.cgstAmt;
				sgstAmt = sgstAmt + report.sgstAmt;
				crnAmt = crnAmt + report.crnAmt;
				tr.append($('<td style="text-align:left;"></td>').html(
						(report.cgstPer + report.sgstPer).toFixed(2)));
				tr.append($('<td style="text-align:right;"></td>').html(
						addCommas((report.crnQty))));
				tr.append($('<td style="text-align:right;"></td>').html(
						addCommas(report.crnTaxable.toFixed(2))));

				tr.append($('<td style="text-align:right;"></td>').html(
						addCommas(report.cgstAmt.toFixed(2))));

				tr.append($('<td style="text-align:right;"></td>').html(
						addCommas(report.sgstAmt.toFixed(2))));
				tr.append($('<td style="text-align:right;"></td>').html(
						addCommas(report.crnAmt.toFixed(2))));

				$('#table_grid tbody').append(tr);

			})
			var tr = $('<tr></tr>');
			tr.append($('<td></td>').html(""));
			tr.append($('<td></td>').html(""));
			tr.append($('<td></td>').html(""));
			tr.append($('<td></td>').html(""));
			tr.append($('<td></td>').html(""));
			tr.append($('<td></td>').html(""));

			tr.append($('<td style="font-weight:12px;"></td>').html(""));
			tr.append($('<td></td>').html("Total"));
			tr.append($('<td style="font-weight:12px;text-align:right;"></td>').html(
					"" + addCommas(crnQty.toFixed(2))));
			tr.append($('<td style="font-weight:12px;text-align:right;"></td>').html(
					"" + addCommas(crnTaxable.toFixed(2))));

			tr.append($('<td style="font-weight:12px;text-align:right;"></td>').html(
					"" + addCommas(cgstAmt.toFixed(2))));

			tr.append($('<td style="font-weight:12px;text-align:right;"></td>').html(
					"" + addCommas(sgstAmt.toFixed(2))));
			tr.append($('<td style="font-weight:12px;text-align:right;"></td>').html(
					"" + addCommas(crnAmt.toFixed(0))));
			$('#table_grid tbody').append(tr);

		});

	}
</script>

<script>

function addCommas(x){

	x=String(x).toString();
	 var afterPoint = '';
	 if(x.indexOf('.') > 0)
	    afterPoint = x.substring(x.indexOf('.'),x.length);
	 x = Math.floor(x);
	 x=x.toString();
	 var lastThree = x.substring(x.length-3);
	 var otherNumbers = x.substring(0,x.length-3);
	 if(otherNumbers != '')
	     lastThree = ',' + lastThree;
	 return otherNumbers.replace(/\B(?=(\d{2})+(?!\d))/g, ",") + lastThree + afterPoint;
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
		var fromdate = $("#fromdatepicker").val();
		var todate = $("#todatepicker").val();
		window
				.open('${pageContext.request.contextPath}/getCRNoteRegisterDonePdf/'
						+ fromdate + '/' + todate + '/');

	}
</script>
</body>
</html>
