<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

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



<c:url var="getTaxSellReport" value="/getTaxSellReport" />

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
						<h3 class="pageTitle">View Sell Tax Report Summary </h3>
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

					</div>


					<div align="center">
						<button class="btn search_btn" onclick="searchSellBill()">HTML
							View</button>
						<button class="btn search_btn" onclick="showChart()">Graph</button>
						<%-- 		    	    <a href='${pageContext.request.contextPath}/pdf?reportURL=showSellTaxReportpPdf' id="btn_pdf" class="btn search_btn" style="display: none">PDF</a>
 --%>
						<button class="btn btn-primary" value="PDF" id="PDFButton"
							onclick="genPdf()">PDF</button>

						<br>
					</div>
				</div>
				<div class="row" id="table">
					<div class="col-md-12">
						<!--table-->
						<div class="clearfix"></div>


						<div id="table-scroll" class="table-scroll">
							<div id="faux-table" class="faux-table" aria="hidden"></div>
							<div class="table-wrap">
								<table id="table_grid" class="main-table" border="1">
									<thead>
										<tr class="bgpink">

											<th class="col-md-1" style="text-align: center;">Sr.No.</th>
											<!-- <th class="col-md-1">Bill No</th> -->
											<th class="col-md-1" style="text-align: center;">Tax %</th>
											<th class="col-md-1" style="text-align: center;">Taxable
												Amt</th>
											<th class="col-md-1" style="text-align: center;">IGST</th>
											<th class="col-md-1" style="text-align: center;">CGST</th>
											<th class="col-md-1" style="text-align: center;">SGST</th>
											<th class="col-md-1" style="text-align: center;">CESS</th>
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

				<div id="chart">
					<br>
					<br>
					<br>
					<hr>
					<div>



						<div id="chart_div" style="width: 60%; height: 300; float: left;"
							style="overflow-y: scroll;"></div>

						<div id="pieChart_div"
							style="width: 40%%; height: 300; float: right;"></div>
						<div id="Piechart" style="width: 40%%; height: 300; float: right;"></div>
						<!-- <div   id="PieChart_div" style="width:40%%; height:300; float: right;" ></div>  -->
					</div>
					<!-- <hr style="height:1px; width:50%%;" color="black">
			<div class="colOuter" >
			 
				<div   id="PieChart_div" style="width:100%; height:300;" align="center" ></div>
				</div> -->

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
<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>
<!--easyTabs-->


<script type="text/javascript">
	function searchSellBill() {

		document.getElementById('table').style.display = "block";
		document.getElementById('chart').style = "display:none";
		// document.getElementById('showchart').style.display = "block";
		$('#table_grid td').remove();

		var isValid = validate();

		if (isValid) {

			var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			//document.getElementById('btn_pdf').style.display = "block";

			$
					.getJSON(
							'${getTaxSellReport}',
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
								var cessTotal = 0;
								$
										.each(
												data,
												function(key, sellTaxData) {

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
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html(
																			(sellTaxData.tax_per)
																					.toFixed(2)));

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html(
																			addCommas((sellTaxData.tax_amount)
																					.toFixed(2))));
													taxTotal = taxTotal
															+ sellTaxData.tax_amount;

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html(
																			addCommas((sellTaxData.igst)
																					.toFixed(2))));
													igstTotal = igstTotal
															+ sellTaxData.igst;

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html(
																			addCommas((sellTaxData.cgst)
																					.toFixed(2))));
													cgstTotal = cgstTotal
															+ sellTaxData.cgst;

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html(
																			addCommas((sellTaxData.sgst)
																					.toFixed(2))));
													sgstTotal = sgstTotal
															+ sellTaxData.sgst;

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html(
																			addCommas((sellTaxData.cess)
																					.toFixed(2))));
													cessTotal = cessTotal
															+ sellTaxData.cess;

													$('#table_grid tbody')
															.append(tr);

												})

								var tr = "<tr>";
								var total = "<td colspan='2'>&nbsp;&nbsp;&nbsp;<b> Total</b></td>";
								taxTotal = taxTotal.toFixed(2);
								var totalTax = "<td style='text-align:right;'>&nbsp;&nbsp;&nbsp;<b>"
										+ addCommas(taxTotal) + "</b></td>";

								var igst = "<td style='text-align:right;'><b>&nbsp;&nbsp;&nbsp;"
										+ addCommas(igstTotal.toFixed(2));
								+"</b></td>";
								var cgst = "<td style='text-align:right;'><b>&nbsp;&nbsp;&nbsp;"
										+ addCommas(cgstTotal.toFixed(2));
								+"</b></td>";
								var sgst = "<td style='text-align:right;'><b>&nbsp;&nbsp;&nbsp;"
										+ addCommas(sgstTotal.toFixed(2));
								+"</b></td>";
								var cess = "<td style='text-align:right;'><b>&nbsp;&nbsp;&nbsp;"
										+ addCommas(cessTotal) + "</b></td>";

								var trclosed = "</tr>";

								$('#table_grid tbody').append(tr);
								$('#table_grid tbody').append(total);
								$('#table_grid tbody').append(totalTax);
								$('#table_grid tbody').append(igst);
								$('#table_grid tbody').append(cgst);
								$('#table_grid tbody').append(sgst);
								$('#table_grid tbody').append(cess);
								$('#table_grid tbody').append(trclosed);

							});

		}
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
<script type="text/javascript">
	function showChart() {

		//$("#PieChart_div").empty();
		$("#chart_div").empty();

		document.getElementById('chart').style.display = "block";
		document.getElementById("table").style = "display:none";

		var fromDate = document.getElementById("fromdatepicker").value;
		var toDate = document.getElementById("todatepicker").value;
		var isValid = validate();

		if (isValid) {
			//	document.getElementById('btn_pdf').style.display = "block";
			$
					.getJSON(
							'${getTaxSellReport}',
							{

								fromDate : fromDate,
								toDate : toDate,
								ajax : 'true',

							},
							function(data) {
								//alert(data);
								if (data == "") {
									alert("No records found !!");

								}
								var i = 0;

								google.charts.load('current', {
									'packages' : [ 'corechart', 'bar' ]
								});
								google.charts.setOnLoadCallback(drawStuff);
								google.charts
										.setOnLoadCallback(drawAmtPieChart);
								google.charts
										.setOnLoadCallback(drawTaxPieChart);

								function drawStuff() {

									var chartDiv = document
											.getElementById('chart_div');
									document.getElementById("chart_div").style.border = "thin dotted red";
									var dataTable = new google.visualization.DataTable();

									dataTable
											.addColumn('string', 'Tax Percent'); // Implicit domain column.
									dataTable.addColumn('number', 'Amount'); // Implicit data column.
									// dataTable.addColumn({type:'string', role:'interval'});
									//  dataTable.addColumn({type:'string', role:'interval'});
									dataTable.addColumn('number', 'Total Tax');
									$.each(data, function(key, item) {

										//var tax=item.cgst + item.sgst;
										//var date= item.billDate+'\nTax : ' + item.tax_per + '%';
										var totalTax = item.cgst + item.sgst;
										dataTable.addRows([

										[ item.tax_per + ' %', item.tax_amount,
												totalTax, ]

										]);
									})

									var materialOptions = {
										width : 600,
										height : 450,
										chart : {
											title : ' Taxable Amount & Total Tax',
											subtitle : 'Tax percent wise Total Tax & Amount '
										},
										series : {
											0 : {
												axis : 'distance'
											}, // Bind series 0 to an axis named 'distance'.
											1 : {
												axis : 'brightness'
											}
										// Bind series 1 to an axis named 'brightness'.
										},
										axes : {
											y : {
												distance : {
													label : 'Taxable Amount'
												}, // Left y-axis.
												brightness : {
													side : 'right',
													label : 'Total Tax'
												}
											// Right y-axis.
											}
										}
									};
									//  var materialChart = new google.charts.Bar(chartDiv);

									function drawMaterialChart() {
										var materialChart = new google.charts.Bar(
												chartDiv);
										//  google.visualization.events.addListener(materialChart, 'select', selectHandler);    
										materialChart
												.draw(
														dataTable,
														google.charts.Bar
																.convertOptions(materialOptions));
										// button.innerText = 'Change to Classic';
										// button.onclick = drawClassicChart;
									}

									drawMaterialChart();

								}
								;

								function drawAmtPieChart() {

									var chartDiv = document
											.getElementById('pieChart_div');
									document.getElementById("pieChart_div").style.border = "thin dotted red";
									var dataTable = new google.visualization.DataTable();

									dataTable.addColumn('string', 'Per'); // Implicit domain column.
									dataTable.addColumn('number',
											'Taxable Amount'); // Implicit data column.
									//  dataTable.addColumn({type:'string', role:'interval'});
									//  dataTable.addColumn({type:'string', role:'interval'});
									//dataTable.addColumn('number', 'TaxableAmt');
									$.each(data, function(key, item) {

										var amt = item.tax_amount;
										var per = 'Tax :  ' + item.tax_per
												+ '%';
										//  Taxable Amt :   '+item.tax_amount;

										dataTable.addRows([
										//  [per, tax, item.tax_amount, ]
										[ per, amt, ]

										]);
									})

									var chart = new google.visualization.PieChart(
											document
													.getElementById('pieChart_div'));
									chart.draw(dataTable, {
										width : 400,
										height : 300,
										title : 'Taxable Amount'
									});

								}
								;
								function drawTaxPieChart() {

									var chartDiv = document
											.getElementById('Piechart');
									document.getElementById("Piechart").style.border = "thin dotted red";
									var dataTable = new google.visualization.DataTable();

									dataTable.addColumn('string', 'Per'); // Implicit domain column.
									dataTable.addColumn('number', 'Total Tax'); // Implicit data column.
									//  dataTable.addColumn({type:'string', role:'interval'});
									//  dataTable.addColumn({type:'string', role:'interval'});
									//dataTable.addColumn('number', 'TaxableAmt');
									$.each(data, function(key, item) {

										var tax = item.cgst + item.sgst;
										var per = 'Tax :  ' + item.tax_per
												+ '%';
										//  Taxable Amt :   '+item.tax_amount;

										dataTable.addRows([
										//  [per, tax, item.tax_amount, ]
										[ per, tax, ]

										]);
									})

									var chart = new google.visualization.PieChart(
											document.getElementById('Piechart'));
									chart.draw(dataTable, {
										width : 400,
										height : 300,
										title : 'Total Tax'
									});

								}
								;

							});
		}
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

		window.open("${pageContext.request.contextPath}/exportToExcel");
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
					.open('${pageContext.request.contextPath}/pdf?reportURL=pdf/showSellTaxReportpPdf/'
							+ fromDate + '/' + toDate + '/' + frId);
		}
	}
</script>
</body>
</html>