<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>


<!--topLeft-nav-->
<div class="sidebarOuter"></div>
<!--topLeft-nav-->

<!--wrapper-start-->
<div class="wrapper">

	<!--topHeader-->
	<c:url var="findAddOnRate" value="/getAddOnRate" />
	<c:url var="getItemListByMenu" value="/getItemListById"></c:url>
	<c:url var="findItemsByCatId" value="/getFlavourBySpfId" />
	<c:url var="findAllMenus" value="/getAllTypes" />
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
					<h2 class="pageTitle">Opening Stock Details</h2>
					<!--<h3 class="pageTitle2">Order Date : 22-02-2017 </h3>-->
				</div>


				<div id="main-content">
					<!-- BEGIN Page Title -->
					<div class="page-title"></div>
					<!-- END Page Title -->

					<!-- BEGIN Main Content -->
					<div class="box">
						<div class="box-title"></div>

						<div class="box-content">
							<div class="row">


								<div class="form-group col-md-9">
									--> <label class=" col-md-2 control-label menu_label">Select
										Category</label>
									<div class=" col-md-3 controls menu_select">

										<select data-placeholder="Choose Category"
											class="form-control chosen" tabindex="6" id="selectMenu"
											name="selectMenu">
											<c:forEach items="${catList}" var="catIdName"
												varStatus="count">
												<option value="${catIdName.catId}"><c:out
														value="${catIdName.catName}" /></option>
											</c:forEach>
										</select>
									</div>
									<div class="col-md-1">
										<button class="btn btn-primary" onclick="getItems()">Search</button>

									</div>
								</div>



							</div>
							<div class="form-group col-md-9">
								<div align="center" id="loader" style="display: none">

									<span>
										<h4>
											<font color="#343690">Loading</font>
										</h4>
									</span> <span class="l-1"></span> <span class="l-2"></span> <span
										class="l-3"></span> <span class="l-4"></span> <span
										class="l-5"></span> <span class="l-6"></span>
								</div>
								<!-- <div class="row" align="left">
							<div class="col-md-12" style="text-align: center">
								<button class="btn btn-primary" onclick="getItems()">Search</button>

							</div>

						</div> -->
							</div>
						</div>
					</div>
					<div class="box">


						<form id="openingStockForm"
							action="${pageContext.request.contextPath}/saveFrOpeningStockProcess"
							method="post">
							<div class=" box-content">
								<div class="row">
									<div class="col-md-12 table-responsive">
										<table class="table table-bordered table-striped fill-head "
											style="width: 50%" id="table_grid" align="left">
											<thead>
												<tr>
													<th width="10">Sr.No.</th>
													<th width="40">Item Id</th>
													<th width="200">Item Name</th>
													<th width="50">Opening Quantity</th>

												</tr>
											</thead>
											<tbody>

											</tbody>
										</table>
									</div>
								</div>

								<div class="row">
									<div class="col-md-offset-6 col-md-6">
										<button id="submitStock"   style="display: none;"
											class="btn btn-info pull-center" style="margin-right: 5px;"
											onclick="submitForm()">Submit</button>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
				<!--rightContainer-->
			</div>
		</div>
	</div>

</div>
<!--wrapper-end-->

<!--easyTabs-->
<!--easyTabs-->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
<!--easyTabs-->

<script type="text/javascript">
	function getItems() {
		//alert("Hiiii");
		var selectedMenu = $("#selectMenu").val();

		$('#loader').show();

		$
				.getJSON(
						'${getItemListByMenu}',
						{
							menu_id : selectedMenu,

							ajax : 'true'
						},
						function(data) {

							var len = data.length;

							$('#table_grid td').remove();
							$('#loader').hide();

							if (data == "" || data == null) {
								alert("No Items found !!");
								$('#submitStock').hide();
							} else {
								$
										.each(
												data,
												function(key, item) {

													var index = key + 1;

													var tr = "<tr>";

													var index = "<td>&nbsp;&nbsp;&nbsp;"
															+ index + "</td>";

													var itemCode = "<td>&nbsp;&nbsp;&nbsp;"
															+ item.itemCode
															+ "</td>";

													var itemName = "<td>&nbsp;&nbsp;&nbsp;"
															+ item.itemName
															+ "</td>";

													var itemStockQty = "<td align=center><input type=number min=0 max=500 class=form-control   id= stockQty"
									+ item.itemId
									+ " name=stockQty"
									+ item.itemId
									+ " value = "
									+ item.regOpeningStock
									+ "></td>";

													var trclosed = "</tr>";

													$('#table_grid tbody')
															.append(tr);
													$('#table_grid tbody')
															.append(index);

													$('#table_grid tbody')
															.append(itemCode);
													$('#table_grid tbody')
															.append(itemName);
													$('#table_grid tbody')
															.append(
																	itemStockQty);

													$('#table_grid tbody')
															.append(trclosed);
													$('#submitStock').show();

												});

							}

						});

	}
</script>


</body>
</html>