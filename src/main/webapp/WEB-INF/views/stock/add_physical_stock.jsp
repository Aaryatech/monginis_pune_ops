<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tableSearch.css">

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


<c:url var="getStock" value="/getStockDetailsForPhysicalStock"></c:url>

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



			<!--rightSidebar-->
			<div class="sidebarright">
				<div class="order-left">
					<h2 class="pageTitle">Stock Details</h2>
					<!--<h3 class="pageTitle2">Order Date : 22-02-2017 </h3>-->
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
														<c:set var="month" value="August"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==9}">
														<c:set var="month" value="September"></c:set>
													</c:when>
													<c:when test="${getMonthList.month==10}">
														<c:set var="month" value="October"></c:set>
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
				<form action="insertSellBillHeader" method="POST">
					<div class="colOuter">
						<div class="col-md-2">
							<div class="col1title">Select Category</div>
						</div>
						<div class="col-md-2">
							<select name="select_category" class="form-control chosen"
								tabindex="4" id="selectCategory" required>

								<option value="-1">Select Category</option>
								<c:forEach items="${category}" var="category" varStatus="count">
									<c:choose>
										<c:when
											test="${category.catId != '5' and category.catId != '7' }">
											<!-- and category.catId != '6' -->

											<option value="${category.catId}"><c:out
													value="${category.catName}" /></option>

										</c:when>

									</c:choose>

								</c:forEach>

							</select>
						</div>



						<div class="col-md-2">
							<div class="col1title">Select View Option</div>
						</div>
						<div class="col-md-2">
							<select name="selectStock" class="form-control chosen"
								tabindex="6" id="selectStock" onchange="showDiv(this)" required>

								<option value="-1">Select Option</option>
								<option value="1" id="currentStock">Get Current Stock</option>

							</select>
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


							<div class="clearfix"></div>
							<div class="col-md-9"></div>
							<label for="search" class="col-md-3" id="search"> <i
								class="fa fa-search" style="font-size: 20px"></i> <input
								type="text" id="myInput" onkeyup="myFunction()"
								style="border-radius: 25px;" placeholder="Search items by name"
								title="Type item name">
							</label>


							<div id="table-scroll" class="table-scroll">
								<div id="faux-table" class="faux-table" aria="hidden">
									<!-- <table id="table_grid1" class="main-table" border=1>
										<thead>
											<tr class="bgpink">
												<th>Item Id</th>
												<th>Item_Name</th>
												<th>Reg Current Stock</th>
												<th>Physical Stock</th>
												<th>Stock Diff Stock</th>
												<th>MRP</th>
												<th>Value</th>
											</tr>
										</thead>
									</table> -->
								</div>
								<div class="table-wrap">
									<table id="table_grid" class="main-table" border="1">
										<thead>
											<tr class="bgpink">
												<th>Item Id</th>
												<th>Item_Name</th>
												<th>Reg Current Stock</th>
												<th>Physical Stock</th>
												<th>Stock Diff Stock</th>
												<th>MRP</th>
												<th>Value</th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>

							</div>




							<div class="colOuter" id="physicalStock" style="display: none">
								<input name="" class="buttonsaveorder" value="Physical Stock"
									type="submit">
							</div>


						</div>
					</div>
				</form>
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

		$('#loader').show();

		var selectedCat = $("#selectCategory").val();

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
							ajax : 'true'
						},
						function(data) {
							$('#loader').hide();

							var len = data.length;
							$('#table_grid td').remove();
							//alert(isMonthClose+ "month close");			
							var list = data.currentStockDetailList; //alert(data.monthClosed);	alert(selectedStockOption);
							//alert(data.monthClosed); 

							if (data != null) {
								document.getElementById('physicalStock').style.display = "block";
							}

							$
									.each(
											list,
											function(key, item) {

												var regCurrentStock = item.currentRegStock;
												var reOrderQty = item.reOrderQty;

												if (regCurrentStock > reOrderQty) {
													var tr = $('<tr ></tr>');

												} else {
													var tr = $('<tr class="re-order" ></tr>');
												}

												tr.append($('<td ></td>').html(
														item.itemId));
												tr.append($('<td  ></td>')
														.html(item.itemName));

												if (regCurrentStock < 0) {
													tr.append($('<td > </td>')
															.html(0));
												} else {
													tr
															.append($(
																	'<td > </td>')
																	.html(
																			regCurrentStock));
												}

												tr
														.append($('<td class="col-md-1" style="text-align:right;"> <input type=number min=0 style=width:80px; onkeyup= updateStockDiff('
																+ item.stockDetailId
																+ ','
																+ regCurrentStock
																+ ') onchange= updateStockDiff('
																+ item.stockDetailId
																+ ','
																+ regCurrentStock
																+ ')  id=physicalStockQty'
																+ item.stockDetailId
																+ ' name=physicalStockQty'
																+ item.stockDetailId
																+ ' value=0  ></td>'));
												tr
														.append($('<td  name=stockDiff'+ item.stockDetailId + ' id=stockDiff'+ item.stockDetailId + ' value =' + 0 + '  > 0</td>'));

												tr
														.append($('<td > </td>')
																.html(
																		item.spTotalPurchase));

												if (regCurrentStock < 0) {
													tr
															.append($(
																	'<td > </td>')
																	.html(
																			(item.spTotalPurchase) * (0)));

												} else {
													tr
															.append($(
																	'<td > </td>')
																	.html(
																			(item.spTotalPurchase)
																					* (regCurrentStock)));

												}

												$('#table_grid tbody').append(
														tr);

											})
						});
	}
</script>

<script type="text/javascript">
	function updateStockDiff(id, currentStock) {
		//alert("Hii");
		//alert(currentStock);

		var physicalStockQty = $("#physicalStockQty" + id).val();
		var oldDiff = $('#stockDiff' + id).val();

		var stockDiff = 0;

		if (currentStock >= physicalStockQty) {
			//alert("HIi");

			stockDiff = currentStock - physicalStockQty;

		} else {

			alert("Please enter valid physical Stock");
			physicalStockQty = 0;
			stockDiff = oldDiff;

		}

		$('#stockDiff' + id).html(stockDiff);
		$('#physicalStockQty' + id).html(physicalStockQty);
	}
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

</body>

</html>