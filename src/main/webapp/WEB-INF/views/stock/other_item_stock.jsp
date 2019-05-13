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


<c:url var="getOtherStock" value="/getOtherStock"></c:url>

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
					<h2 class="pageTitle">Other Item Stock Details</h2>
					<!--<h3 class="pageTitle2">Order Date : 22-02-2017 </h3>-->
				</div>

				<!-- 
				<div class="colOuter">
					<div class="col-md-2">
						<div class="col1title">Current Month: </div>
					</div>
					
				</div> -->
				<div class="colOuter">
					<!-- <div class="col-md-2">
						<div class="col1title">Select Category</div>
					</div> -->
					<%-- <div class="col-md-2">
						<select name="select_category" class="form-control chosen"
							tabindex="4" id="selectCategory" required>

							<option value="-1">Select Category</option>
							<c:forEach items="${category}" var="category" varStatus="count">
							<c:choose>
							<c:when test="${category.catId != '5' and category.catId != '6' and category.catId != '7' }">  <!-- and category.catId != '6' -->
							
								<option value="${category.catId}"><c:out value="${category.catName}" /></option>
							</c:when>
							</c:choose>
							</c:forEach>

						</select>
					</div> --%>



					<div class="col-md-2">
						<div class="col1title">Select View Option</div>
					</div>
					<div class="col-md-2">
						<select name="selectStock" class="form-control chosen"
							tabindex="6" id="selectStock" onchange="showDiv(this)" required>

							<option value="-1">Select Option</option>
							<option value="1" id="currentStock">Get Current Stock</option>

							<option value="3" id="dateStock">Stock Between Dates</option>

						</select>
					</div>
					<div class="col-md-2">
						<input name="search_stock" class="buttonsaveorder" value="Search"
							type="button" onclick="searchStock()">
					</div>

					<div class="col-md-1">
						<button type="button" class="btn  buttonsaveorder" id='pdf'
							onclick="genPdf()" disabled>Generate Pdf</button>
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

				<div class="col-md-2">
					<div class="col1title">
						<b>${monthName}</b> <b>${year}</b>
					</div>
				</div>






				<div class="row">
					<div class="col-md-12">
						<!--table-->
						<form action="otherItemMonthEndProcess" method="POST">
							<div class="clearfix"></div>
							<div class="col-md-9"></div>
							<label for="search" class="col-md-3" id="search"> <i
								class="fa fa-search" style="font-size: 20px"></i> <input
								type="text" id="myInput" onkeyup="myFunction()"
								style="border-radius: 25px;" placeholder="Search items by name"
								title="Type item name Or Code">
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
												<th class="col-md-1">Item Code</th>
												<th class="col-md-1">Item Name</th>
												<th class="col-md-1">Opening Stock</th>
												<th class="col-md-1">Purchase Qty</th>
												<th class="col-md-1">Sale Qty</th>
												<th class="col-md-1">Damage Qty</th>
												<th class="col-md-1">Current Stock</th>


												<%-- 	<c:if test="${isMonthCloseApplicable eq true}">
													<th>Physical Stock</th>
													<th>Stock Difference</th>
												</c:if> --%>

											</tr>

										</thead>
										<tbody>

										</tbody>
									</table>

								</div>

							</div>
							<div class="col-sm-3  controls">
								<input type="button" id="expExcel" class="btn btn-primary"
									value="EXPORT TO Excel" onclick="exportToExcel();"
									disabled="disabled">
							</div>



							<div class="colOuter" id="monthEnd" style="display: none">
								<div class="col2full">
									<input name="" class="buttonsaveorder" value="Month End"
										type="submit">
								</div>
							</div>
						</form>
					</div>
				</div>

			</div>
		</div>

		<!--rightSidebar-->

	</div>
	<!--fullGrid-->
</div>
<!--rightContainer-->

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
		//alert(elem.value);
		if (elem.value == 1) {
			//document.getElementById('select_month_year').style = "display:none";
			document.getElementById('select_date').style = "display:none";
			document.getElementById('monthEnd').style = "display:none";
		} else if (elem.value == 3) {
			document.getElementById('select_date').style.display = "block";
			//document.getElementById('select_month_year').style = "display:none";
			document.getElementById('monthEnd').style = "display:none";

		}
	}
</script>

<script type="text/javascript">
	function searchStock() {

		$('#loader').show();

		var isMonthClose = ${isMonthEndAppli};
		//alert("close " +isMonthClose);

		var selectedStockOption = $("#selectStock").val();
		//	alert("selectedStockOption"+selectedStockOption);
		var selectedFromDate = $("#fromdatepicker").val();
		var selectedToDate = $("#todatepicker").val();

		//document.getElementById('monthEnd').style.display = "block";

		$
				.getJSON(
						'${getOtherStock}',
						{
							show_option : selectedStockOption,
							fromDate : selectedFromDate,
							toDate : selectedToDate,
							ajax : 'true'
						},
						function(data) {

							$('#loader').hide();
							var len = data.length;
							//alert(len);					
							$('#table_grid td').remove();

							if (isMonthClose == 1 && selectedStockOption == 1)

								document.getElementById('monthEnd').style.display = "block";

							//alert(isMonthClose+ "month close");			
							//alert(data.monthClosed);	alert(selectedStockOption);

							//	alert(data);
							if (data != null) {

								 
								  document.getElementById("expExcel").disabled=false;
								document.getElementById("pdf").disabled = false;

							}
							$
									.each(
											data,
											function(key, item) {
												//alert(JSON.stringify(item));
												var tr = $('<tr class=bgpink></tr>');
												var curStock = (parseFloat(item.openingStock) + parseFloat(item.purchaseQty))
														- (parseFloat(item.sellQty));//+parseFloat(item.damagedStock)
												tr
														.append($(
																'<td class="col-md-1"></td>')
																.html(
																		item.itemId
																				+ '<input type="hidden" id="currStk'+item.id+'" value='+curStock+'  />'));
												tr
														.append($(
																'<td class="col-md-1"></td>')
																.html(
																		item.itemName));
												tr
														.append($(
																'<td class="col-md-1"></td>')
																.html(
																		item.openingStock));
												tr
														.append($(
																'<td class="col-md-1"></td>')
																.html(
																		item.purchaseQty));
												tr
														.append($(
																'<td class="col-md-1"></td>')
																.html(
																		item.sellQty));

												if (isMonthClose == 0) {
													tr
															.append($('<td class="col-md-1"> <input type=text min=0 style=width:80px; readonly onchange= updateStockDiff('
																	+ item.id
																	+ ','
																	+ curStock
																	+ ')  id= damagedStock'
																	+ item.id
																	+ ' name=damagedStock'
																	+ item.id
																	+ ' value = '
																	+ item.damagedStock
																	+ '></td>'));
												} else {

													tr
															.append($('<td class="col-md-1"> <input type=text min=0 style=width:80px;   onchange= updateStockDiff('
																	+ item.id
																	+ ','
																	+ curStock
																	+ ')  id= damagedStock'
																	+ item.id
																	+ ' name=damagedStock'
																	+ item.id
																	+ ' value = '
																	+ item.damagedStock
																	+ '><input type="hidden" id="currStk'+item.id+'" value='+curStock+'  /></td>'));
												}
												//	tr.append($('<td class="col-md-1"></td>').html(item.damagedStock));

												tr
														.append($(
																'<td class="col-md-1" id="stockDiff'+item.id+'"></td>')
																.html(curStock));

												$('#table_grid tbody').append(
														tr);

											})
						});
	}
</script>

<script type="text/javascript">
	function updateStockDiff(id, currentStock) {

		var physicalStockQty = $("#damagedStock" + id).val();
		var oldDiff = $('#stockDiff' + id).val();
		var currStk = $('#currStk' + id).val();
		var stockDiff = 0;

		if (currentStock >= physicalStockQty) {

			stockDiff = currentStock - physicalStockQty;
			$('#stockDiff' + id).html(stockDiff);
		} else {

			// stockDiff =physicalStockQty - currentStock ;
			alert("You can not enter damaged qty greator than current stock!!");

			document.getElementById('damagedStock' + id).value = 0;
			$('#stockDiff' + id).html(currStk);
		}

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
		var input, filter, table, tr, td, td1, i;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		table = document.getElementById("table_grid");
		tr = table.getElementsByTagName("tr");
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[1];
			td1 = tr[i].getElementsByTagName("td")[0];
			if (td) {
				if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else if (td1.innerHTML.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}
	}
</script>

<script>
	function genPdf() {
		//alert("Inside Gen Pdf ");

		// window.open('${pageContext.request.contextPath}/pdf?reportURL=pdf/getGrnPdf/'+fromDate+'/'+'/'+toDate+'/'+headerId+'/'+1+'/'+type);

		window.open('${pageContext.request.contextPath}/getOtherItemStockPdf');

	}
	
	function exportToExcel() {

		window.open("${pageContext.request.contextPath}/exportToExcel");
		document.getElementById("expExcel").disabled = true;
	}
</script>

</body>

</html>