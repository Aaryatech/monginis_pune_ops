<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tableSearch.css">
<style>
table, th, td {
	border: 1px solid #9da88d;
}
</style>
<style>
.alert1 {
	padding: 10px;
	background-color: #f44336;
	color: white;
}

.closebtn {
	margin-left: 25px;
	color: white;
	font-weight: bold;
	float: right;
	font-size: 18px;
	line-height: 10px;
	cursor: pointer;
	transition: 0.3s;
}

.closebtn:hover {
	color: black;
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
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/feviconicon.png"
	type="image/x-icon" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>

<link
	href="${pageContext.request.contextPath}/resources/css/monginis.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link href="${pageContext.request.contextPath}/resources/css/custom.css"
	rel="stylesheet" type="text/css" />

<!--rightNav-->

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/loader.css">

</head>
<body> --%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/loader.css">
!--datepicker-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
	$(function() {
		$("#fromdatepicker").datepicker({
			dateFormat : 'dd-mm-yy'
		});
	});
	$(function() {
		$("#todatepicker").datepicker({
			dateFormat : 'dd-mm-yy'
		});
	});
</script>
<!--datepicker-->


<c:url var="getStock" value="/getStockDetails"></c:url>

<!--topLeft-nav-->
<div class="sidebarOuter"></div>
<!--topLeft-nav-->

<!--wrapper-start-->
<div class="wrapper">

	<!--topHeader-->


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
			<div class="colOuter">
				<div class="col-md-2"></div>
				<div class="col-md-8">
					<c:if test="${not empty message}">
						<div class="alert1">
							<span class="closebtn"
								onclick="this.parentElement.style.display='none';">&times;</span>
							${message}
						</div>
					</c:if>
				</div>
			</div>

			<!--rightSidebar-->
			<div class="sidebarright">
				<div class="order-left">
					<h2 class="pageTitle">Stock Details</h2>
					<!--<h3 class="pageTitle2">Order Date : 22-02-2017 </h3>-->
				</div>
				<div class="order-right" align="right">

					<a href="${pageContext.request.contextPath}/showFrOpeningStock"><input
						type="button" value="Add Opening Stock" class="btn btn-info">
					</a>

				</div>



				<div class="colOuter">
					<div class="col-md-2">
						<div class="col1title">Current Month:</div>
					</div>
					<div class="col-md-5">

						<c:forEach items="${category}" var="category" varStatus="count">
							<c:forEach items="${getMonthList}" var="getMonthList"
								varStatus="count">
								<c:choose>
									<c:when
										test="${(getMonthList.catId!=5) and (getMonthList.catId!=7)}">
										<c:choose>
											<c:when test="${getMonthList.catId==category.catId}">
												<c:choose>
													<c:when test="${getMonthList.month==1}">
														<c:set var="month" value="January"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==2}">
														<c:set var="month" value="February"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==3}">
														<c:set var="month" value="March"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==4}">
														<c:set var="month" value="April"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==5}">
														<c:set var="month" value="May"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==6}">
														<c:set var="month" value="June"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==7}">
														<c:set var="month" value="July"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==8}">
														<c:set var="month" value="Augast"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==9}">
														<c:set var="month" value="September"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==10}">
														<c:set var="month" value="Octomber"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==11}">
														<c:set var="month" value="November"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==12}">
														<c:set var="month" value="December"></c:set>
													</c:when>
													<c:otherwise>
														<c:set var="month" value=""></c:set>
													</c:otherwise>
												</c:choose>
												<strong> ${category.catName}</strong> : ${month}, 
													 
													 </c:when>
										</c:choose>
									</c:when>
								</c:choose>
							</c:forEach>
						</c:forEach>


					</div>

				</div>
				<div class="colOuter">
					<div class="col-md-1">
						<div class="col1title">Select Category</div>
					</div>
					<div class="col-md-2">
						<select name="select_category" class="form-control chosen"
							tabindex="4" id="selectCategory" required>

							<option value="-1">Select Category</option>
							<c:forEach items="${category}" var="category" varStatus="count">
								<c:choose>
									<c:when
										test="${category.catId != '5' and category.catId != '6' and category.catId != '7' }">
										<!-- and category.catId != '6' -->

										<option value="${category.catId}"><c:out
												value="${category.catName}" /></option>
									</c:when>
								</c:choose>
							</c:forEach>

						</select>
					</div>



					<div class="col-md-1">
						<div class="col1title">Select View Option</div>
					</div>
					<div class="col-md-2">
						<select name="selectStock" class="form-control chosen"
							tabindex="6" id="selectStock" onchange="showDiv(this)" required>

							<option value="-1">Select Option</option>
							<option value="1" id="currentStock">Get Current Stock</option>

							<option value="3" id="dateStock">Get Stock Between Dates</option>

						</select>
					</div>
					<div class="col-md-1">
						<div class="col1title">Type</div>
					</div>
					<div class="col-md-2">
						<select name="st_type" class="form-control chosen" tabindex="4"
							id="st_type" required>

							<option value="">Select Type</option>
							<option value="1">ALL</option>
							<option value="2">In Stock</option>
							<option value="3">0 Stock</option>

						</select>
					</div>


					<div class="col-md-2">
						<select name="select_rate" class="form-control chosen"
							tabindex="4" id="select_rate" onchange="searchStock()" required>
							<option value="">Select Rate Type</option>


							<option value="1">Rate</option>

							<option value="2">MRP</option>

						</select>
					</div>

				</div>



				<!-- <div class="colOuter" style="display: none" id=select_month_year>
					<div class="col-md-2">
						<div class="col1title">Select Month From :</div>
					</div>
					<div class="col-md-2" align="left">

						<input type='text' placeholder="Select From Month" id='txtDate'
							name="from_stockdate" required />
					</div>

					<div class="col3"></div>



					<div class="col-md-2">
						<div class="col1title">To :</div>
					</div>
					<div class="col-md-2" align="left">
						<input type='text' placeholder="Select To Month" id=txtDateto
							name="to_stockdate" required />
					</div>

				</div> -->



				<div class="colOuter" style="display: none" id=select_date>
					<div class="col-md-2">
						<div class="col1title">From Date:</div>
					</div>
					<div class="col-md-2" align="left">

						<input id="fromdatepicker" class="texboxitemcode texboxcal"
							autocomplete="off" placeholder="From Date" name="from_datepicker"
							type="text">

					</div>

					<div class="col3"></div>



					<div class="col-md-2">
						<div class="col1title">To Date:</div>
					</div>
					<div class="col-md-2" align="left">
						<input id="todatepicker" class="texboxitemcode texboxcal"
							autocomplete="off" placeholder="To Date" name="to_datepicker"
							type="text">
					</div>

				</div>




				<div class="colOuter">
					<div class="col1">
						<div class="col1title"></div>
					</div>
					<div class="col2">
						<input name="search_stock" class="buttonsaveorder" value="Search"
							type="button" onclick="searchStock()">

						<div align="center" id="loader" style="display: none">

							<span>
								<h4>
									<font color="#343690">Loading</font>
								</h4>
							</span> <span class="l-1"></span> <span class="l-2"></span> <span
								class="l-3"></span> <span class="l-4"></span> <span class="l-5"></span>
							<span class="l-6"></span>
						</div>
					</div>



				</div>



				<div class="row">
					<div class="col-md-12">
						<!--table-->
						<form action="monthEndProcess" method="POST"
							onsubmit="substk.disabled = true; return confirm('Do you want to Month End ?');">
							<div class="clearfix"></div>
							<div class="col-md-9"></div>
							<label for="search" class="col-md-3" id="search"> <i
								class="fa fa-search" style="font-size: 20px"></i> <input
								type="text" id="myInput" onkeyup="myFunction()"
								style="border-radius: 25px;" placeholder="Search items by name"
								title="Type item name">
							</label>


							<div id="table-scroll" class="table-scroll">
								<!-- <div id="faux-table" class="faux-table" aria="hidden"> -->
								<%-- <div class="table-wrap">	<table id="table_grid1" class="main-table">
										<thead>
											<tr class="bgpink">
							<th class="col-md-1">Item Id</th>
												<th class="col-md-1">Item_Name</th>
												<th class="col-md-1">Reg Op Stock</th>
												<th class="col-md-1">Sp Op Stock</th>
												<th class="col-md-1">Reg Pur Qty</th>
												<th class="col-md-1">Sp Pur Qty</th>
												<th class="col-md-1">Grn-Gvn Qty</th>
												<th class="col-md-1">Regular Sale</th>
												<th class="col-md-1">Sp Sale</th>
												<th>Reorder Qty</th>
												<th class="col-md-1">Reg Cur Stock</th>
												<th class="col-md-1">Sp Cur Stock</th>

												<c:if test="${isMonthCloseApplicable eq true}">
													<th>Physical Stock</th>
													<th>Stock Difference</th>
												</c:if>
											</tr>
										</thead></table></div> --%>
								<!-- </div> -->
								<div class="table-wrap">
									<table id="table_grid" class="main-table">
										<thead>
											<tr class="bgpink">
												<th class="col-md-1">Item Id</th>
												<th class="col-md-1">Item_Name</th>
												<th class="col-md-1">Rate/MRP</th><!---->
												<th class="col-md-1">Op Stock</th>
												<th class="col-md-1">Op Stock Value</th>
											<!-- 	<th class="col-md-1">Sp Op Stock</th> -->
												<th class="col-md-1">Pur Qty</th>
												<th class="col-md-1">Pur Value</th>
												<!-- <th class="col-md-1">Sp Pur Qty</th> -->
												<th class="col-md-1">Grn-Gvn Qty</th>
												<th class="col-md-1">Grn-Gvn VAlue</th>
												<th class="col-md-1">Reg Sale</th>
												<th class="col-md-1">Reg Sale Value</th>
												<th class="col-md-1">Curr Stock</th>
												<th class="col-md-1">Curr Stock Value</th>
												<c:if test="${isMonthCloseApplicable eq true}">
													<th>Physical Stock</th>
													<th>Stock Difference</th>
												</c:if>

											</tr>
										</thead>
										<tbody>

										</tbody>

										<!-- 	<tr>
											<td></td>
											<td></td>
											<td class="col-md-1">Total</td>
											<td><input type="text" id="opTotal" style="width: 80px;"
												name="opTotal" value="0" readonly></td>
											<td><input type="text" id="opTotalVal"
												style="width: 80px;" name="opTotalVal" value="0" readonly></td>


											<td><input type="text" id="purQty" style="width: 80px;"
												name="purQty" value="0" readonly></td>
											<td><input type="text" id="purQtyVal"
												style="width: 80px;" name="purQtyVal" value="0" readonly></td>



											<td><input type="text" id="grnGvnQty"
												style="width: 80px;" name="grnGvnQty" value="0" readonly></td>
											<td><input type="text" id="grnGvnQtyVal"
												style="width: 80px;" name="grnGvnQtyVal" value="0" readonly></td>



											<td><input type="text" id="regSale" style="width: 80px;"
												name="regSale" value="0" readonly></td>
											<td><input type="text" id="regSaleVal"
												style="width: 80px;" name="regSaleVal" value="0" readonly></td>


											<td><input type="text" id="reOrderQty"
												style="width: 80px;" name="reOrderQty" value="0" readonly></td>



											<td><input type="text" id="curStock"
												style="width: 80px;" name="curStock" value="0" readonly></td>
											<td><input type="text" id="curStockVal"
												style="width: 80px;" name="curStockVal" value="0" readonly></td>

										</tr> -->



									</table>







									&nbsp;
								</div>

							</div>


							<div class="col-md-2">

								<button type="button" class="btn btn-primary"
									onclick="exportToExcel();" id="expExcel"
									style="align-content: center; width: 200px; margin-left: 80px;">
									Export To Excel</button>
							</div>


							<div class="col-md-3">

								<button type="button" class="btn btn-primary" onclick="genPdf()"
									id="PDFButton"
									style="align-content: center; width: 100px; margin-left: 80px;">
									PDF</button>
							</div>


							<div class="colOuter" id="monthEnd" style="display: none">
								<div class="col4full" align="right">
									<input name="" class="buttonsaveorder" value="Month End"
										id="substk" type="submit">
								</div>
							</div>

						</form>
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
<!--easyTabs-->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
<!--easyTabs-->

<!-- Select Only Month and Year -->
<script>
	$(document)
			.ready(
					function() {
						$('#txtDate')
								.datepicker(
										{
											changeMonth : true,
											changeYear : true,
											dateFormat : 'MM yy',

											onClose : function() {
												var iMonth = $(
														"#ui-datepicker-div .ui-datepicker-month :selected")
														.val();
												var iYear = $(
														"#ui-datepicker-div .ui-datepicker-year :selected")
														.val();
												$(this).datepicker(
														'setDate',
														new Date(iYear, iMonth,
																1));
											},

											beforeShow : function() {
												if ((selDate = $(this).val()).length > 0) {
													iYear = selDate.substring(
															selDate.length - 4,
															selDate.length);
													iMonth = jQuery
															.inArray(
																	selDate
																			.substring(
																					0,
																					selDate.length - 5),
																	$(this)
																			.datepicker(
																					'option',
																					'monthNames'));
													$(this).datepicker(
															'option',
															'defaultDate',
															new Date(iYear,
																	iMonth, 1));
													$(this).datepicker(
															'setDate',
															new Date(iYear,
																	iMonth, 1));
												}
											}
										});
					});
</script>

<script>
	$(document)
			.ready(
					function() {
						$('#txtDateto')
								.datepicker(
										{
											changeMonth : true,
											changeYear : true,
											dateFormat : 'MM yy',

											onClose : function() {
												var iMonth = $(
														"#ui-datepicker-div .ui-datepicker-month :selected")
														.val();
												var iYear = $(
														"#ui-datepicker-div .ui-datepicker-year :selected")
														.val();
												$(this).datepicker(
														'setDate',
														new Date(iYear, iMonth,
																1));
											},

											beforeShow : function() {
												if ((selDate = $(this).val()).length > 0) {
													iYear = selDate.substring(
															selDate.length - 4,
															selDate.length);
													iMonth = jQuery
															.inArray(
																	selDate
																			.substring(
																					0,
																					selDate.length - 5),
																	$(this)
																			.datepicker(
																					'option',
																					'monthNames'));
													$(this).datepicker(
															'option',
															'defaultDate',
															new Date(iYear,
																	iMonth, 1));
													$(this).datepicker(
															'setDate',
															new Date(iYear,
																	iMonth, 1));
												}
											}
										});
					});
</script>
<script>
	function showDiv(elem) {
		if (elem.value == 1) {
			document.getElementById('select_month_year').style = "display:none";
			document.getElementById('select_date').style = "display:none";
		} else if (elem.value == 2) {
			document.getElementById('select_month_year').style.display = "block";
			document.getElementById('select_date').style = "display:none";
		} else if (elem.value == 3) {
			document.getElementById('select_date').style.display = "block";
			document.getElementById('select_month_year').style = "display:none";
		}
	}
</script>

<script type="text/javascript">
	function searchStock() {

		var selectRate = document.getElementById('select_rate').value;

		$('#loader').show();

		var isMonthClose = ${isMonthCloseApplicable};

		//alert(isMonthClose);

		var selectedCat = $("#selectCategory").val();
		var stType = $("#st_type").val();
		var selectedStockOption = $("#selectStock").val();

		var selectedFromDate = $("#fromdatepicker").val();
		var selectedToDate = $("#todatepicker").val();

		$
				.getJSON(
						'${getStock}',
						{
							cat_id : selectedCat,
							show_option : selectedStockOption,
							fromDate : selectedFromDate,
							toDate : selectedToDate,
							selectRate : selectRate,
							ajax : 'true'
						},
						function(data) {
							$('#loader').hide();

							document.getElementById("expExcel").disabled = false;
							document.getElementById("PDFButton").disabled = false;

							if (data == "") {
								alert("No records found !!");
								document.getElementById("expExcel").disabled = true;
								document.getElementById("PDFButton").disabled = true;

							}

							var len = data.length;
							$('#table_grid td').remove();
							//alert(isMonthClose+ "month close");			
							var list = data.currentStockDetailList; //alert(data.monthClosed);	alert(selectedStockOption);

							if (data.monthClosed && selectedStockOption == 1) {

								if (stType == 1) {
									document.getElementById('monthEnd').style.display = "block";
								}

								$('#table_grid th').remove();
								var tr = $('<tr class=bgpink></tr>');
								tr.append($('<th align=left>Item Id</th>'));
								tr.append($('<th align=left>Item Name</th>'));
								tr.append($('<th align=center>Rate/MRP</th>'));
								tr.append($('<th align=left>Op Stock</th>'));
								tr
										.append($('<th align=left>Op Stock Value</th>'));
								tr.append($('<th align=left>Pur Qty</th>'));
								tr.append($('<th align=left>Pur Value</th>'));
								tr.append($('<th align=left>Grn-Gvn Qty</th>'));
								tr
										.append($('<th align=left>Grn-Gvn Value</th>'));
								tr
										.append($('<th align=left>Regular Sale</th>'));

								/* tr.append($('<th align=left>Reorder Qty</th>')); */
								tr
										.append($('<th align=left>Reg Sale Val</th>'));
								tr.append($('<th align=center>Cur Stock</th>'));
								tr
										.append($('<th align=center>CurStock Val</th>'));
								tr
										.append($('<th align=left>Physical Stock</th>'));

								tr.append($('<th align=left>Stock Diff</th>'));

								$('#table_grid').append(tr);
							}

							$
									.each(
											list,
											function(key, item) {

												var regCurrentStock = item.currentRegStock;
												var reOrderQty = item.reOrderQty;
												if (stType == 1) {
													if (regCurrentStock > reOrderQty) {
														var tr = $('<tr ></tr>');

													} else {
														var tr = $('<tr class="re-order" ></tr>');
													}

													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			item.itemId));
													tr
															.append($(
																	'<td class="col-md-1" ></td>')
																	.html(
																			item.itemName));
													if (selectRate == 1) {

														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				item.spOpeningStock));
													} else

													{
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				item.spTotalPurchase));
													}
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html(
																			item.regOpeningStock));
													var regOpStockValue;
													if (selectRate == 1) {
														regOpStockValue = item.spOpeningStock
																* item.regOpeningStock;

														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				regOpStockValue));
													} else

													{
														regOpStockValue = item.spTotalPurchase
																* item.regOpeningStock;
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				regOpStockValue
																						.toFixed(2)));
													}

													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html(
																			item.regTotalPurchase
																					.toFixed(2)));

													if (selectRate == 1) {
														var regTotalPurchaseVal = item.spOpeningStock
																* item.regTotalPurchase;

														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				regTotalPurchaseVal
																						.toFixed(2)));
													} else

													{
														var regTotalPurchaseVal = item.spTotalPurchase
																* item.regTotalPurchase;
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				regTotalPurchaseVal
																						.toFixed(2)));
													}
													tr
															.append($(
																	'<td class="col-md-1" style="text-align:right;"></td>')
																	.html(
																			item.regTotalGrnGvn));

													if (selectRate == 1) {
														var regTotalGrnGvnVal = item.spOpeningStock
																* item.regTotalGrnGvn;

														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				regTotalGrnGvnVal
																						.toFixed(2)));
													} else

													{
														var regTotalGrnGvnVal = item.spTotalPurchase
																* item.regTotalGrnGvn;
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				regTotalGrnGvnVal
																						.toFixed(2)));
													}

													if (item.regTotalSell < 0) {
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(0));
													} else {
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				item.regTotalSell
																						.toFixed(2)));
													}
													if (selectRate == 1) {
														var regTotalSellVal = item.spOpeningStock
																* item.regTotalSell;

														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;" ></td>')
																		.html(
																				regTotalSellVal
																						.toFixed(2)));
													} else

													{
														var regTotalSellVal = item.spTotalPurchase
																* item.regTotalSell;
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				regTotalSellVal
																						.toFixed(2)));
													}

													/* tr
															.append($(
																	'<td class="col-md-1"> </td>')
																	.html(
																			reOrderQty));
													 */
													if (regCurrentStock < 0) {
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"> </td>')
																		.html(0));
													} else {
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"> </td>')
																		.html(
																				regCurrentStock));
													}

													if (selectRate == 1) {
														if (regCurrentStock < 0) {

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					0));
														} else {
															var regCurrentStockVal = item.spOpeningStock
																	* regCurrentStock;

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regCurrentStockVal
																							.toFixed(2)));
														}
													} else {
														if (regCurrentStock < 0) {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					0));
														} else {
															var regCurrentStockVal = item.spTotalPurchase
																	* regCurrentStock;
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regCurrentStockVal
																							.toFixed(2)));
														}
													}

													if (data.monthClosed
															&& selectedStockOption == 1) {

														tr
																.append($('<td class="col-md-1" style="text-align:right;"> <input type=number min=0 style=width:80px; onkeyup= updateStockDiff('
																		+ item.itemId
																		+ ','
																		+ regCurrentStock
																		+ ') onchange= updateStockDiff('
																		+ item.itemId
																		+ ','
																		+ regCurrentStock
																		+ ')  id= physicalStockQty'
																		+ item.itemId
																		+ ' name=physicalStockQty'
																		+ item.itemId
																		+ ' value = '
																		+ regCurrentStock
																		+ '></td>'));

														tr
																.append($('<td class="col-md-1"  style="text-align:right;"   name=stockDiff'+ item.itemId + ' id=stockDiff'+ item.itemId + ' value =' + 0 + '  > 0</td>'));
													}

													$('#table_grid tbody')
															.append(tr);

												} else if (stType == 2) {
													if (regCurrentStock > 0) {
														var tr = $('<tr ></tr>');

														tr
																.append($(
																		'<td class="col-md-1"></td>')
																		.html(
																				item.itemId));
														tr
																.append($(
																		'<td class="col-md-1" ></td>')
																		.html(
																				item.itemName));
														if (selectRate == 1) {

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					item.spOpeningStock));
														} else

														{
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					item.spTotalPurchase));
														}
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				item.regOpeningStock));

														if (selectRate == 1) {
															var regOpStockValue = item.spOpeningStock
																	* item.regOpeningStock;

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regOpStockValue
																							.toFixed(2)));
														} else

														{
															var regOpStockValue = item.spTotalPurchase
																	* item.regOpeningStock;
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regOpStockValue
																							.toFixed(2)));
														}

														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				item.regTotalPurchase));

														if (selectRate == 1) {
															var regTotalPurchaseVal = item.spOpeningStock
																	* item.regTotalPurchase;

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalPurchaseVal
																							.toFixed(2)));
														} else

														{
															var regTotalPurchaseVal = item.spTotalPurchase
																	* item.regTotalPurchase;
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalPurchaseVal
																							.toFixed(2)));
														}
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				item.regTotalGrnGvn));

														if (selectRate == 1) {
															var regTotalGrnGvnVal = item.spOpeningStock
																	* item.regTotalGrnGvn;

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalGrnGvnVal
																							.toFixed(2)));
														} else

														{
															var regTotalGrnGvnVal = item.spTotalPurchase
																	* item.regTotalGrnGvn;
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalGrnGvnVal
																							.toFixed(2)));
														}

														if (item.regTotalSell < 0) {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					0));
														} else {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					item.regTotalSell
																							.toFixed(2)));
														}
														if (selectRate == 1) {
															var regTotalSellVal = item.spOpeningStock
																	* item.regTotalSell;

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalSellVal
																							.toFixed(2)));
														} else

														{
															var regTotalSellVal = item.spTotalPurchase
																	* item.regTotalSell;
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalSellVal
																							.toFixed(2)));
														}

														/* 	tr
																	.append($(
																			'<td class="col-md-1"> </td>')
																			.html(
																					reOrderQty)); */

														if (regCurrentStock < 0) {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"> </td>')
																			.html(
																					0));
														} else {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"> </td>')
																			.html(
																					regCurrentStock
																							.toFixed(2)));
														}

														if (selectRate == 1) {
															if (regCurrentStock < 0) {

																tr
																		.append($(
																				'<td class="col-md-1" style="text-align:right;"></td>')
																				.html(
																						0));
															} else {
																var regCurrentStockVal = item.spOpeningStock
																		* regCurrentStock;

																tr
																		.append($(
																				'<td class="col-md-1" style="text-align:right;"></td>')
																				.html(
																						regCurrentStockVal
																								.toFixed(2)));
															}
														} else {
															if (regCurrentStock < 0) {
																tr
																		.append($(
																				'<td class="col-md-1" style="text-align:right;"></td>')
																				.html(
																						0));
															} else {
																var regCurrentStockVal = item.spTotalPurchase
																		* regCurrentStock;
																tr
																		.append($(
																				'<td class="col-md-1" style="text-align:right;"></td>')
																				.html(
																						regCurrentStockVal
																								.toFixed(2)));
															}
														}

														if (data.monthClosed
																&& selectedStockOption == 1) {

															tr
																	.append($('<td class="col-md-1" style="text-align:right;"> <input type=number min=0 style=width:80px; onkeyup= updateStockDiff('
																			+ item.itemId
																			+ ','
																			+ regCurrentStock
																			+ ') onchange= updateStockDiff('
																			+ item.itemId
																			+ ','
																			+ regCurrentStock
																			+ ')  id= physicalStockQty'
																			+ item.itemId
																			+ ' name=physicalStockQty'
																			+ item.itemId
																			+ ' value = '
																			+ regCurrentStock
																			+ '></td>'));

															tr
																	.append($('<td class="col-md-1"  style="text-align:right;"  name=stockDiff'+ item.itemId + ' id=stockDiff'+ item.itemId + ' value =' + 0 + '  > 0</td>'));
														}

														$('#table_grid tbody')
																.append(tr);
													}
												} else if (stType == 3) {
													if (regCurrentStock == 0) {

														var tr = $('<tr class="re-order" ></tr>');

														tr
																.append($(
																		'<td class="col-md-1"></td>')
																		.html(
																				item.itemId));
														tr
																.append($(
																		'<td class="col-md-1" ></td>')
																		.html(
																				item.itemName));
														if (selectRate == 1) {

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					item.spOpeningStock));
														} else

														{
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					item.spTotalPurchase));
														}
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				item.regOpeningStock));

														if (selectRate == 1) {
															var regOpStockValue = item.spOpeningStock
																	* item.regOpeningStock;

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regOpStockValue)
																			.toFixed(
																					2));
														} else

														{
															var regOpStockValue = item.spTotalPurchase
																	* item.regOpeningStock;
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regOpStockValue
																							.toFixed(2)));
														}

														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				item.regTotalPurchase
																						.toFixed(2)));

														if (selectRate == 1) {
															var regTotalPurchaseVal = item.spOpeningStock
																	* item.regTotalPurchase;

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalPurchaseVal
																							.toFixed(2)));
														} else

														{
															var regTotalPurchaseVal = item.spTotalPurchase
																	* item.regTotalPurchase;
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalPurchaseVal
																							.toFixed(2)));
														}
														tr
																.append($(
																		'<td class="col-md-1" style="text-align:right;"></td>')
																		.html(
																				item.regTotalGrnGvn));

														if (selectRate == 1) {
															var regTotalGrnGvnVal = item.spOpeningStock
																	* item.regTotalGrnGvn;

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalGrnGvnVal
																							.toFixed(2)));
														} else

														{
															var regTotalGrnGvnVal = item.spTotalPurchase
																	* item.regTotalGrnGvn;
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalGrnGvnVal
																							.toFixed(2)));
														}

														if (item.regTotalSell < 0) {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					0));
														} else {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					item.regTotalSell));
														}
														if (selectRate == 1) {
															var regTotalSellVal = item.spOpeningStock
																	* item.regTotalSell;

															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalSellVal
																							.toFixed(2)));
														} else

														{
															var regTotalSellVal = item.spTotalPurchase
																	* item.regTotalSell;
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"></td>')
																			.html(
																					regTotalSellVal
																							.toFixed(2)));
														}

														/* 	tr
																	.append($(
																			'<td class="col-md-1"> </td>')
																			.html(
																					reOrderQty));
														 */
														if (regCurrentStock < 0) {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"> </td>')
																			.html(
																					0));
														} else {
															tr
																	.append($(
																			'<td class="col-md-1" style="text-align:right;"> </td>')
																			.html(
																					regCurrentStock));
														}

														if (selectRate == 1) {
															if (regCurrentStock < 0) {

																tr
																		.append($(
																				'<td class="col-md-1" style="text-align:right;"></td>')
																				.html(
																						0));
															} else {
																var regCurrentStockVal = item.spOpeningStock
																		* regCurrentStock;

																tr
																		.append($(
																				'<td class="col-md-1" style="text-align:right;"></td>')
																				.html(
																						regCurrentStockVal
																								.toFixed(2)));
															}
														} else {
															if (regCurrentStock < 0) {
																tr
																		.append($(
																				'<td class="col-md-1" style="text-align:right;"></td>')
																				.html(
																						0));
															} else {
																var regCurrentStockVal = item.spTotalPurchase
																		* regCurrentStock;
																tr
																		.append($(
																				'<td class="col-md-1" style="text-align:right;"></td>')
																				.html(
																						regCurrentStockVal
																								.toFixed(2)));
															}
														}

														if (data.monthClosed
																&& selectedStockOption == 1) {

															tr
																	.append($('<td class="col-md-1" style="text-align:right;"> <input type=number min=0 style=width:80px; onkeyup= updateStockDiff('
																			+ item.itemId
																			+ ','
																			+ regCurrentStock
																			+ ') onchange= updateStockDiff('
																			+ item.itemId
																			+ ','
																			+ regCurrentStock
																			+ ')  id= physicalStockQty'
																			+ item.itemId
																			+ ' name=physicalStockQty'
																			+ item.itemId
																			+ ' value = '
																			+ regCurrentStock
																			+ '></td>'));

															tr
																	.append($('<td style="text-align:right;"  class="col-md-1" name=stockDiff'+ item.itemId + ' id=stockDiff'+ item.itemId + ' value =' + 0 + '  > 0</td>'));
														}

														$('#table_grid tbody')
																.append(tr);
													}
												}

											})
						});
	}
</script>

<script type="text/javascript">
	function updateStockDiff(id, currentStock) {

		var physicalStockQty = $("#physicalStockQty" + id).val();
		var oldDiff = $('#stockDiff' + id).val();

		var stockDiff = 0;

		if (currentStock > physicalStockQty) {

			stockDiff = currentStock - physicalStockQty;

		} else {

			stockDiff = physicalStockQty - currentStock;

		}

		$('#stockDiff' + id).html(stockDiff);
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
	function myFunction() {
		var input, filter, table, tr, td, i;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		table = document.getElementById("table_grid");
		tr = table.getElementsByTagName("tr");
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[1];
			if (td) {
				if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}
	}
</script>

<script type="text/javascript">
	function genPdf() {
		/* alert("hiii"); */
		/* var fromDate = document.getElementById("fromdatepicker").value;
		var toDate = document.getElementById("todatepicker").value;
		alert(fromDate);
		alert(toDate); */

		var selectRate = document.getElementById('select_rate').value;

		window.open('${pageContext.request.contextPath}/showStockDetailsPdf/'
				+ selectRate);
		/* document.getElementById("expExcel").disabled = true; */

	}

	function exportToExcel() {

		window.open("${pageContext.request.contextPath}/exportToExcel");
		document.getElementById("expExcel").disabled = true;
	}
</script>



</body>

</html>