<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

<!--topLeft-nav-->
<div class="sidebarOuter"></div>
<!--topLeft-nav-->

<!--wrapper-start-->
<div class="wrapper">

	<!--topHeader-->
	<c:url var="getSpAdvTaxReport" value="/getSpAdvTaxReport" />
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
				<div class="order-left">
					<h2 class="pageTitle">Sp Tax Report</h2>

				</div>
				<form id="validation-form">
					<input type="hidden" value="${gstType}" name="type" id="type" />

					<div class="colOuter">
						<!-- copy div kalpesh -->

						<div class="calender-title">From</div>
						<div class="col-md-2">
							<input id="datepicker" class="texboxitemcode texboxcal"
								value="${cDate}" autocomplete="off" name="from_Date" type="text">
						</div>

						<div class="calender-title">TO</div>
						<div class="col-md-2">
							<input id="datepicker2" class="texboxitemcode texboxcal"
								value="${cDate}" autocomplete="off" name="to_Date" type="text">
						</div>

						<div class="col-md-1"></div>
						<div class="col-md-1">
							<button type="button" class="btn  buttonsaveorder"
								onclick="searchSpTaxAdvRepo()">Search</button>
						</div>

						<div class="col-md-1">
							<button type="button" class="btn  buttonsaveorder" id='pdf'
								onclick="genPdf()" disabled>Generate Pdf</button>
						</div>
					</div>

					<!--tabNavigation-->
					<div class="cd-tabs">
						<!--tabMenu-->

						<!--tabMenu-->

						<div id="table-scroll" class="table-scroll">
							<div id="faux-table" class="faux-table" aria="hidden">
								<table id="table_grid" class="main-table" border="1">
									<thead>
										<tr class="bgpink">
											<!-- 	<th class="col-md-1">Sr No</th>
											<th class="col-md-2">Invoice No</th>
											<th class="col-md-3">Item Name</th>
											<th class="col-md-2">HsnCode</th>
											<th class="col-md-1">Delivery Date</th>
											<th class="col-md-1">Base MRP</th>
											<th class="col-md-1">CGST %</th>
											<th class="col-md-1">SGST %</th>
											<th class="col-md-1">CGST Rs</th>
											<th class="col-md-1">SGST Rs</th>
											<th class="col-md-1">Total</th>

											<th class="col-md-1">Advance</th>
											<th class="col-md-1">Remaining</th> -->
										</tr>
									</thead>
								</table>
							</div>
							<div class="table-wrap">
								<table id="table_grid" class="main-table" border="1">
									<thead>
										<tr class="bgpink">
											<th class="col-md-1">Sr No</th>
											<th class="col-md-2">Invoice No</th>
											<th class="col-md-3">Item Name</th>
											<th class="col-md-2">HsnCode</th>
											<th class="col-md-1">Delivery Date</th>
											<th class="col-md-1">Base MRP</th>
											<th class="col-md-1">CGST %</th>
											<th class="col-md-1">SGST %</th>
											<th class="col-md-1">CGST Rs</th>
											<th class="col-md-1">SGST Rs</th>
											<th class="col-md-1">Total</th>
											<th class="col-md-1">Advance</th>
											<th class="col-md-1">Remaining</th>

										</tr>
									</thead>
									<tbody>
								</table>
							</div>

						</div>

						<div class="col-sm-3  controls">
							<input type="button" id="expExcel" class="btn btn-primary"
								value="EXPORT TO Excel" onclick="exportToExcel();"
								disabled="disabled">
						</div>

					</div>
					<!--tabNavigation-->
				</form>
			</div>
			<!--rightSidebar-->
		</div>
		<!--fullGrid-->
	</div>
	<!--rightContainer-->
</div>
<!--wrapper-end-->

<script type="text/javascript">
	function searchSpTaxAdvRepo() {
		$('#table_grid td').remove();
		var fromDate = document.getElementById("datepicker").value;
		var toDate = document.getElementById("datepicker2").value;

		$.getJSON('${getSpAdvTaxReport}', {

			fromDate : fromDate,
			toDate : toDate,
			ajax : 'true'

		}, function(data) {

			var baseMrpTotal = 0;

			var cgstTotal = 0;
			var sgstTotal = 0;

			var totalAmt = 0;
			var advanceAmt = 0;
			var totalRemaining = 0;

			$.each(data, function(key, spTax) {

				if (data != null) {
					document.getElementById("pdf").disabled = false;
					document.getElementById("expExcel").disabled = false;

				}

				var tr = $('<tr></tr>');

				tr.append($('<td class="col-md-1"></td>').html(key + 1));
				tr
						.append($('<td class="col-md-2"></td>').html(
								spTax.invoiceNo));
				tr.append($('<td class="col-md-3"></td>').html(spTax.spName));
				tr.append($('<td class="col-md-2"></td>').html(spTax.spHsncd));
				tr.append($('<td class="col-md-1"></td>').html(spTax.delDate));
				tr.append($(
						'<td class="col-md-1" style="text-align:right"></td>')
						.html(spTax.baseMrp.toFixed(2)));
				tr.append($(
						'<td class="col-md-1" style="text-align:right"></td>')
						.html(spTax.tax1));
				tr.append($(
						'<td class="col-md-1" style="text-align:right"></td>')
						.html(spTax.tax2));
				tr.append($(
						'<td class="col-md-1" style="text-align:right"></td>')
						.html(spTax.tax1Amt));
				tr.append($(
						'<td class="col-md-1" style="text-align:right"></td>')
						.html(spTax.tax2Amt));
				tr.append($(
						'<td class="col-md-1" style="text-align:right"></td>')
						.html(spTax.total));
				tr.append($(
						'<td class="col-md-1" style="text-align:right"></td>')
						.html(spTax.spAdvance));
				tr.append($(
						'<td class="col-md-1" style="text-align:right"></td>')
						.html(spTax.rmAmount));

				baseMrpTotal = baseMrpTotal + spTax.baseMrp;
				cgstTotal = cgstTotal + spTax.tax1Amt;
				sgstTotal = sgstTotal + spTax.tax2Amt;
				totalAmt = totalAmt + spTax.total;
				advanceAmt = advanceAmt + spTax.spAdvance;
				totalRemaining = totalRemaining + spTax.rmAmount;

				$('#table_grid tbody').append(tr);

			})

			var tr = $('<tr></tr>');

			tr.append($('<td class="col-md-1"></td>').html(""));
			tr.append($('<td class="col-md-2"></td>').html(""));
			tr.append($('<td class="col-md-3"></td>').html(""));
			tr.append($('<td class="col-md-2"></td>').html(""));
			tr.append($('<td class="col-md-1"></td>').html("Total"));
			tr.append($('<td class="col-md-1" style="text-align:right"></td>')
					.html(baseMrpTotal.toFixed(2)));
			tr.append($('<td class="col-md-1" style="text-align:right"></td>')
					.html(""));
			tr.append($('<td class="col-md-1" style="text-align:right"></td>')
					.html(""));
			tr.append($('<td class="col-md-1" style="text-align:right"></td>')
					.html(cgstTotal.toFixed(2)));
			tr.append($('<td class="col-md-1" style="text-align:right"></td>')
					.html(sgstTotal.toFixed(2)));
			tr.append($('<td class="col-md-1" style="text-align:right"></td>')
					.html(totalAmt.toFixed(2)));
			tr.append($('<td class="col-md-1" style="text-align:right"></td>')
					.html(advanceAmt.toFixed(2)));
			tr.append($('<td class="col-md-1" style="text-align:right"></td>')
					.html(totalRemaining.toFixed(2)));

			$('#table_grid tbody').append(tr);

		});

		//}//if block
	}

	document.getElementById("pdf").disabled = true;
</script>




<script type="text/javascript">
	function validate() {

		var fromDate = $("#datepicker").val();
		var toDate = $("#datepicker2").val();

		var headeIdText = $("#headeIdText").val();
		alert(headeIdText);

		var isValid = true;
		if (headeIdText == "" || headeIdText == null) {
			isValid = false;
		} else if (fromDate == "" || fromDate == null) {

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
</script>

<script>
	function genPdf() {
		window.open('${pageContext.request.contextPath}/getSpAdvTaxPdf');
	}

	function exportToExcel() {

		window.open("${pageContext.request.contextPath}/exportToExcelNew");
		document.getElementById("expExcel").disabled = true;
	}
</script>

</body>
</html>