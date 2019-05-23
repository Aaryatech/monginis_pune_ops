<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>



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


<c:url var="getgvnReport" value="/getgvnReport" />

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
						<h2 class="pageTitle">View GVN Report</h2>
					</div>
				</div>

				<div class="colOuter">
					<div align="center">
						<div class="col1">
							<div class="col1title">
								<b>From&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b> <input
									id="fromdatepicker" autocomplete="off"
									placeholder="Delivery Date" name="from_Date" type="text"
									size="35">
							</div>
						</div>
						<div class="col2">
							<div class="col1title">
								<b>TO&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b> <input id="todatepicker"
									autocomplete="off" placeholder="Delivery Date" name="to_Date"
									type="text" size="35">
							</div>
						</div>
						<input type="hidden" name="frId" id="frId" value="${frId}">
						<input type="hidden" name="frName" id="frName" value="${frName}">

					</div>


					<div align="center">
						<button class="btn search_btn" onclick="searchSellBill()">HTML
							View</button>
						<%--   &nbsp;&nbsp;&nbsp;
		    	     <a href='${pageContext.request.contextPath}/pdf?reportURL=showSellTaxBillwiseReportpPdf' id="btn_pdf" class="btn search_btn" style="display: none">PDF</a> --%>
						<button class="btn btn-primary" value="PDF" id="PDFButton"
							onclick="genPdf()">PDF</button>
						<br>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<!--table-->
						<div class="clearfix"></div>


						<div id="table-scroll" class="table-scroll">
							<div id="faux-table" class="faux-table" aria="hidden">
								<table id="table_grid1" class="main-table" border="1">
									<thead>
										<tr class="bgpink">

											<!-- 					
								
									<th class="col-md-1" style="text-align:center;">Sr.No</th> 
									<th class="col-md-1" style="text-align:center;">Grn Gvn Date</th>
									<th class="col-md-1" style="text-align:center;">Item_Name</th>
									<th class="col-md-1" style="text-align:center;">Tax Rate</th>
									<th class="col-md-1" style="text-align:center;">Taxable Amt</th>
									<th class="col-md-1" style="text-align:center;">Total Tax</th>
									<th class="col-md-1" style="text-align:center;">Grn Gvn Amt</th> 
									<th class="col-md-1" style="text-align:center;">Aprv. Taxable Amt</th>
									<th class="col-md-1" style="text-align:center;">Aprv. CGST Amt</th>
									<th class="col-md-1" style="text-align:center;">Aprv. SGST Amt</th>
									<th class="col-md-1" style="text-align:center;">Aprv. IGST Amt</th> 
								 	<th class="col-md-1" style="text-align:center;">Total</th>   -->
										</tr>
									</thead>

									<tbody>

									</tbody>

								</table>
							</div>
							<div class="table-wrap">
								<table id="table_grid" class="main-table" border="1">
									<thead>
										<tr class="bgpink">



											<th class="col-md-1" style="text-align: center;">Sr.No.</th>
											<th class="col-md-1" style="text-align: center;">Grn Gvn
												Date</th>
											<th class="col-md-1" style="text-align: center;">Item_Name</th>
											<th class="col-md-1" style="text-align: center;">Tax
												Rate</th>
											<th class="col-md-1" style="text-align: center;">Taxable
												Amt</th>
											<th class="col-md-1" style="text-align: center;">Total
												Tax</th>
											<th class="col-md-1" style="text-align: center;">Grn Gvn
												Amt</th>
											<th class="col-md-1" style="text-align: center;">Aprv.
												Taxable Amt</th>
											<th class="col-md-1" style="text-align: center;">Aprv.
												CGST Amt</th>
											<th class="col-md-1" style="text-align: center;">Aprv.
												SGST Amt</th>
											<th class="col-md-1" style="text-align: center;">Aprv.
												IGST Amt</th>
											<th class="col-md-1" style="text-align: center;">Total</th>
										</tr>
									</thead>

									<tbody>

									</tbody>

								</table>


							</div>
							<div class="form-group" style="display: none;" id="range">



								<div class="col-sm-3  controls">
									<input type="button" id="expExcel" class="btn btn-primary"
										value="EXPORT TO Excel" onclick="exportToExcel();"
										disabled="disabled">
								</div>
							</div>
						</div>
						<!--table end-->

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
	function searchSellBill() {

		$('#table_grid td').remove();

		var isValid = validate();

		if (isValid) {
			//document.getElementById('btn_pdf').style.display = "block";
			var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;

			$
					.getJSON(
							'${getgvnReport}',
							{

								fromDate : fromDate,
								toDate : toDate,
								ajax : 'true',

							},
							function(data) {

								if (data == "") {
									alert("No records found !!");
									document.getElementById("expExcel").disabled = true;
								}

								var taxTotal = 0;
								var igstTotal = 0;
								var cgstTotal = 0;
								var sgstTotal = 0;
								var taxableTotal = 0;
								var grandTotal = 0;
								var grnGvnAmt = 0;
								var aprTaxableTotal = 0;
								$
										.each(
												data,
												function(key, list) {

													document
															.getElementById("expExcel").disabled = false;
													document
															.getElementById('range').style.display = 'block';

													var tr = $('<tr></tr>');

													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			key + 1));
													tr
															.append($(
																	'<td class="col-md-1" style="font-size:14px;"></td>')
																	.html(
																			list.grnGvnDate));
													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			list.itemName));
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			list.taxRate));
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			list.taxableAmt
																					.toFixed(2)));
													taxableTotal = taxableTotal
															+ list.taxableAmt;
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			list.totalTax
																					.toFixed(2)));
													taxTotal = taxTotal
															+ list.totalTax;
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			list.grnGvnAmt
																					.toFixed(2)));
													grnGvnAmt = grnGvnAmt
															+ list.grnGvnAmt;
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			list.aprTaxableAmt
																					.toFixed(2)));
													aprTaxableTotal = aprTaxableTotal
															+ list.aprTaxableAmt;
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			list.aprCgstRs
																					.toFixed(2)));
													cgstTotal = cgstTotal
															+ list.aprCgstRs;
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			list.aprSgstRs
																					.toFixed(2)));
													sgstTotal = sgstTotal
															+ list.aprSgstRs;
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			list.aprIgstRs
																					.toFixed(2)));
													igstTotal = igstTotal
															+ list.aprIgstRs;
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right"></td>')
																	.html(
																			list.aprGrandTotal
																					.toFixed(2)));
													grandTotal = grandTotal
															+ list.aprGrandTotal;
													$('#table_grid tbody')
															.append(tr);

												})

								var tr = "<tr>";
								var total = "<td colspan='4'>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";

								var totalAmt = "<td style='text-align:right'>&nbsp;&nbsp;&nbsp;<b>"
										+ taxableTotal.toFixed(2);
								+"</b></td>";
								var totalTax = "<td style='text-align:right'>&nbsp;&nbsp;&nbsp;<b>"
										+ taxTotal.toFixed(2);
								+"</b></td>";

								var totalGrnGvn = "<td style='text-align:right'>&nbsp;&nbsp;&nbsp;<b>"
										+ grnGvnAmt.toFixed(2);
								+"</b></td>";
								var totalAprTaxableTotal = "<td style='text-align:right'>&nbsp;&nbsp;&nbsp;<b>"
										+ aprTaxableTotal.toFixed(2);
								+"</b></td>";
								var cgst = "<td style='text-align:right'><b>&nbsp;&nbsp;&nbsp;"
										+ cgstTotal.toFixed(2);
								+"</b></td>";
								var sgst = "<td style='text-align:right'><b>&nbsp;&nbsp;&nbsp;"
										+ sgstTotal.toFixed(2);
								+"</b></td>";

								var igst = "<td style='text-align:right'><b>&nbsp;&nbsp;&nbsp;"
										+ igstTotal.toFixed(2);
								+"</b></td>";

								var grand = "<td style='text-align:right'><b>&nbsp;&nbsp;&nbsp;"
										+ grandTotal.toFixed(2);
								+"</b></td>";

								var trclosed = "</tr>";

								$('#table_grid tbody').append(tr);
								$('#table_grid tbody').append(total);

								$('#table_grid tbody').append(totalAmt);
								$('#table_grid tbody').append(totalTax)
								$('#table_grid tbody').append(totalGrnGvn);
								$('#table_grid tbody').append(
										totalAprTaxableTotal);
								$('#table_grid tbody').append(cgst);
								$('#table_grid tbody').append(sgst);
								$('#table_grid tbody').append(igst);

								$('#table_grid tbody').append(grand);
								$('#table_grid tbody').append(trclosed);

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
					.open('${pageContext.request.contextPath}/tSellReport?reportURL=pdf/getgvnReportPdf/'
							+ fromDate + '/' + toDate + '/' + frId + '/');
		}
	}
</script>

</body>
</html>
