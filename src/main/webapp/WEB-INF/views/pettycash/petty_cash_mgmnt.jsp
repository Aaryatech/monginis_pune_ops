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

.form-control {
	text-align: left !important;
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

</head> --%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/loader.css">

<body>

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

	<!--datepicker-->
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


	<c:url var="getPettyCashData" value="/getPettyCashData"></c:url>


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
			<div class="wrapperIn2 single">

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
						<h2 class="pageTitle">Petty Cash Management</h2>
						<!--<h3 class="pageTitle2">Order Date : 22-02-2017 </h3>-->
					</div>
					<div class="order-right" align="right"
						style="padding-top: 2%; padding-bottom: 2%;">

						<a
							href="${pageContext.request.contextPath}/getPettyCashTransactions"><input
							type="button" value="Petty Cash Details" class="btn additem_btn">
						</a>
					</div>




					<form action="addPettyCash" method="post">


						<input type="hidden" value="${pettycash.pettycashId}"
							name="petty_id"> <input type="hidden"
							value="${pettycash.cashAmt}" name="cash_edit_amt">

						<div class="row">

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Date</div>
							</div>

							<div class="col-md-3">
								<input id="fromdatepicker" class="texboxitemcode texboxcal"
									autocomplete="off" placeholder="Date" name="cash_date"
									type="text" value="${pettycash.date}" onchange="compareDate()">
							</div>

							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Opening
									Amt</div>
							</div>

							<div class="col-md-3">
								<input id="opening_amt" class="form-control" readonly="readonly"
									value="${pettycash.openingAmt}" autocomplete="off"
									placeholder="Opening Amt" name="opening_amt" type="text">
							</div>

							<div class="col-md-1"></div>

						</div>

						<br>


						<div class="row">

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Cash Amt</div>
							</div>

							<div class="col-md-3">
								<input id="cash_amt" class="form-control"
									value="${totalSell+remAmt+advAmt}" autocomplete="off"
									placeholder="Cash Amt" name="cash_amt"
									onchange="calClosingAmt()" type="text">
							</div>

							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Withdrawal
									Amt</div>
							</div>

							<div class="col-md-3">
								<input id="withdrawal_amt" class="form-control"
									value="${pettycash.withdrawalAmt}" autocomplete="off"
									placeholder="Withdrawal Amt" name="withdrawal_amt" type="text"
									onchange="calClosingAmt()">
							</div>

							<div class="col-md-1"></div>



						</div>

						<br>

						<div class="row">

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Closing
									Amt</div>
							</div>

							<div class="col-md-3">
								<input id="closing_amt" class="form-control"
									value="${pettycash.openingAmt+totalSell+remAmt+advAmt-pettycash.withdrawalAmt}"
									readonly="readonly" autocomplete="off"
									placeholder="Closing Amt" name="closing_amt" type="text">
							</div>

							<div class="col-md-7"></div>


						</div>


						<br>

						<hr>

						<h4>Daily Sales</h4>

						<br>

						<div class="row">



							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Opening
									Stock</div>
							</div>

							<div class="col-md-3">
								<input id="openingStock" class="form-control"
									readonly="readonly" value="${dsrOpening}" autocomplete="off"
									placeholder="Openinng Stock" name="openingStock" type="text">
							</div>

							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Total
									Purchase</div>
							</div>

							<div class="col-md-3">
								<input id="purchase" class="form-control" readonly="readonly"
									value="${dsrPurchase}" autocomplete="off"
									placeholder="Purchase" name="purchase" type="text">
							</div>
							
							<input id="purIds" class="form-control" readonly="readonly"
									value="${purBillIds}" 
									name="purIds" type="hidden">



							<div class="col-md-1"></div>

						</div>

						<br>

						<div class="row">

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">GRN/GVN</div>
							</div>

							<div class="col-md-3">
								<input id="grngvn" class="form-control" readonly="readonly"
									value="${dsrGrngvn}" autocomplete="off" placeholder="GRN/GVN"
									name="grngvn" type="text">
							</div>



							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Total
									Sale</div>
							</div>

							<div class="col-md-3">
								<input id="totSale" class="form-control" readonly="readonly"
									value="${dsrSale}" autocomplete="off" placeholder="Total Sale"
									name="totSale" type="text">
							</div>



							<div class="col-md-1"></div>

						</div>

						<br>

						<div class="row">

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Closing
									Stock</div>
							</div>

							<div class="col-md-3">
								<input id="closingStock" class="form-control"
									readonly="readonly" value="${dsrClosing}" autocomplete="off"
									placeholder="Closing Stock" name="closingStock" type="text">
							</div>

							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Total
									Expense</div>
							</div>

							<div class="col-md-3">
								<input id="totExp" class="form-control" readonly="readonly"
									value="${dsrExpense}" autocomplete="off"
									placeholder="Total Expense" name="totExp" type="text">
							</div>
							
							<input id="expIds" class="form-control" readonly="readonly"
									value="${expIds}" 
									name="expIds" type="hidden">

							<div class="col-md-1"></div>

						</div>

						<br>

						<div class="row">

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Card
									Sale</div>
							</div>

							<div class="col-md-3">
								<input id="cardSale" class="form-control" value="0.0"
									autocomplete="off" placeholder="Card Sale" name="cardSale"
									type="text" onkeyup="calCashSale()">
							</div>

							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Cash
									Sale</div>
							</div>

							<div class="col-md-3">
								<input id="cashSale" class="form-control" readonly="readonly"
									value="${dsrSale}" autocomplete="off" placeholder="Cash Sale"
									name="cashSale" type="text">
							</div>

							<div class="col-md-1"></div>

						</div>

						<br>

						<div class="row">

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Physical
									Cash (cash in hand)</div>
							</div>

							<div class="col-md-3">
								<input id="phyCash" class="form-control" value="0.0"
									autocomplete="off" placeholder="Physical Cash" name="phyCash"
									type="text">
							</div>

							<div class="col-md-1"></div>

							<div class="col-md-2">
								<div class="col1title" style="text-align: right;">Deposit
									in Bank</div>
							</div>

							<div class="col-md-3">
								<input id="deposit" class="form-control" value="0.0"
									autocomplete="off" placeholder="Deposit" name="deposit"
									type="text">
							</div>

							<div class="col-md-1"></div>

						</div>

						<br>


						<div class="row">

							<div class="col-md-12">
								<input class="buttonsaveorder" value="Day End" type="submit"
									id="btnsub">
							</div>

						</div>

					</form>




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


	<script type="text/javascript">
		function calCashSale() {

			var sale = document.getElementById('totSale').value;

			var card = document.getElementById('cardSale').value;

			if (card=='') {

				document.getElementById('cardSale').value = 0.0;
				document.getElementById('cashSale').value = sale;

			
			} else if (parseFloat(card) > parseFloat(sale)) {

				document.getElementById('cardSale').value = 0.0;
				document.getElementById('cashSale').value = sale;

			} else {

				var cash = parseFloat(sale) - parseFloat(card);
				document.getElementById('cashSale').value = cash;

			}

		}
	</script>


	<script>
		function compareDate() {
			var selDate = $("#fromdatepicker").val();

			var today = new Date();
			var dd = String(today.getDate()).padStart(2, '0');
			var mm = String(today.getMonth() + 1).padStart(2, '0'); //January is 0!
			var yyyy = today.getFullYear();
			today = dd + '-' + mm + '-' + yyyy;

			var from_date = selDate;
			var to_date = today;
			var x = 0;
			//alert("Date---"+to_date+"*******++**** "+from_date);

			var fromdate = from_date.split('-');
			from_date = new Date();
			from_date.setFullYear(fromdate[2], fromdate[1] - 1, fromdate[0]);
			var todate = to_date.split('-');
			to_date = new Date();
			to_date.setFullYear(todate[2], todate[1] - 1, todate[0]);
			if (from_date > to_date) {
				// alert("buttn Disabled")
				document.getElementById("btnsub").disabled = true;
				alert("Invalid Date");
			} else {
				document.getElementById("btnsub").disabled = false;
			}
		}

		function calClosingAmt() {

			var cashAmt = parseFloat($("#cash_amt").val());
			var openAmt = parseFloat($("#opening_amt").val());
			var withdrawAmt = parseFloat($("#withdrawal_amt").val());

			var closeAmt = openAmt + cashAmt - withdrawAmt;

			document.getElementById("closing_amt").value = parseFloat(closeAmt);
		}
	</script>

	<!-- Select Only Month and Year -->
	<script>
		function getData() {
			var openAmt = parseFloat($("#opening_amt").val());
			var cashAmt = parseFloat($("#cash_amt").val());

			var sum = openAmt + cashAmt;
			document.getElementById("closing_amt").value = parseFloat(sum);

			var selectedFromDate = $("#fromdatepicker").val();
			$
					.getJSON(
							'${getPettyCashData}',

							{

								from_date : selectedFromDate,
								to_date : selectedFromDate,
								ajax : 'true'

							},
							function(data) {

								$('#table_grid tbody').remove();

								$
										.each(
												data,
												function(data) {
													$('#loader').hide();

													document
															.getElementById("expExcel").disabled = false;
													document
															.getElementById("PDFButton").disabled = false;

													if (data == "") {
														alert("No records found !!");
														document
																.getElementById("expExcel").disabled = true;
														document
																.getElementById("PDFButton").disabled = true;

													}

													var tr = $('<tr></tr>');

													tr.append($('<td></td>')
															.html(key + 1));
													tr.append($('<td></td>')
															.html("FGHJ"));
													tr.append($('<td></td>')
															.html("FGHJ"));
													tr.append($('<td></td>')
															.html("FGHJ"));
													tr.append($('<td></td>')
															.html("FGHJ"));
													tr.append($('<td></td>')
															.html("FGHJ"));
													tr
															.append($(
																	'<td></td>')
																	.html(
																			'<abbr title="Edit"><i id="edit'
																					+ key
																					+ '" onclick="edit('
																					+ key
																					+ ')" class="fa fa-edit"></i> </abbr><span style="visibility: hidden;" class="glyphicon glyphicon-ok" onclick="submit('
																					+ key
																					+ ');" id="ok'
																					+ key
																					+ '"></span><abbr title="Delete"><i onclick="del('
																					+ key
																					+ ')" class="fa fa-trash"></i></abbr>'));
													$('#table_grid tbody')
															.append(tr);

												})

							});

		}
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
															new Date(iYear,
																	iMonth, 1));
												},

												beforeShow : function() {
													if ((selDate = $(this)
															.val()).length > 0) {
														iYear = selDate
																.substring(
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
																		iMonth,
																		1));
														$(this).datepicker(
																'setDate',
																new Date(iYear,
																		iMonth,
																		1));
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

			var isMonthClose = $
			{
				isMonthCloseApplicable
			}
			;

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

								if (data.monthClosed
										&& selectedStockOption == 1) {

									if (stType == 1) {
										document.getElementById('monthEnd').style.display = "block";
									}

									$('#table_grid th').remove();
									var tr = $('<tr class=bgpink></tr>');
									tr.append($('<th align=left>Item Id</th>'));
									tr
											.append($('<th align=left>Item Name</th>'));
									tr
											.append($('<th align=center>Rate/MRP</th>'));
									tr
											.append($('<th align=left>Op Stock</th>'));
									tr
											.append($('<th align=left>Op Stock Value</th>'));
									tr.append($('<th align=left>Pur Qty</th>'));
									tr
											.append($('<th align=left>Pur Value</th>'));
									tr
											.append($('<th align=left>Grn-Gvn Qty</th>'));
									tr
											.append($('<th align=left>Grn-Gvn Value</th>'));
									tr
											.append($('<th align=left>Regular Sale</th>'));

									/* tr.append($('<th align=left>Reorder Qty</th>')); */
									tr
											.append($('<th align=left>Reg Sale Val</th>'));
									tr
											.append($('<th align=center>Cur Stock</th>'));
									tr
											.append($('<th align=center>CurStock Val</th>'));
									tr
											.append($('<th align=left>Physical Stock</th>'));

									tr
											.append($('<th align=left>Stock Diff</th>'));

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
																					regOpStockValue
																							.toFixed(2)));
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

															$(
																	'#table_grid tbody')
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
																						regOpStockValue
																								.toFixed(2))
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

															$(
																	'#table_grid tbody')
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

			window
					.open('${pageContext.request.contextPath}/showStockDetailsPdf/'
							+ selectRate);
			/* document.getElementById("expExcel").disabled = true; */

		}

		function exportToExcel() {

			window.open("${pageContext.request.contextPath}/exportToExcel");
			document.getElementById("expExcel").disabled = true;
		}
		$(function() {
			$('input').on(
					'input',
					function() {
						match = (/(\d{0,9})[^.]*((?:\.\d{0,2})?)/g)
								.exec(this.value.replace(/[^\d.]/g, ''));
						this.value = match[1] + match[2];
					});
		});
	</script>



</body>

</html>