<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


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
	
	<link href="${pageContext.request.contextPath}/resources/css/monginis.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet" type="text/css"/>

<!--rightNav-->

<!--datepicker-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>



</head>
<body> --%>
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>


	<!--topLeft-nav-->
	<div class="sidebarOuter"></div>
	<!--topLeft-nav-->

	<!--wrapper-start-->
	<div class="wrapper">

		<!--topHeader-->
		<c:url var="findAddOnRate" value="/getAddOnRate" />
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
						<h2 class="pageTitle">Stock Details For Current Month</h2>
						<!--<h3 class="pageTitle2">Order Date : 22-02-2017 </h3>-->
					</div>
						 			
	<div class="row">
		<div class="col-md-12">
		<!--table-->
		<form action="end_StockMonth" methos="POST">
			<div class="table-responsive">
				<div class="shInnerwidth">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class="table">
						<tr>
							<td align="center" valign="middle" style="padding:0px;">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr class="bgpink">
												<th width="100" align="left">Item Id</th>
												<th width="100" align="left">Item Name</th>
												<th width="120" align="left">Opening Stock</th>
												
												<th width="159" align="left"> Total Purchase Amt</th>
												<th width="150" align="left">GRN Qty</th>
												<th width="105" align="left">GVN Qty</th>

												<th width="105" align="left">Total Sells</th>
												<th width="105" align="left">Current Stock</th>
												<th width="105" align="left">Is Edit</th>
												<th width="105" align="left">Closing Stock</th>
												<th width="105" align="left">Remark</th>
																						
											</tr>

													
											<%-- <c:forEach items="${billHeader}" var="billHeader" varStatus="count">
												<tr>
													<td><c:out value="${count.index+1}" /></td>
													<td><c:out value="${billHeader.billNo}" /></td>
													<td><c:out value="${billHeader.billDate}" /></td>
													<td><c:out value="${billHeader.taxableAmt}" /></td>
													<td><c:out value="${billHeader.totalTax}" /></td>
													<td><c:out value="${billHeader.grandTotal}" /></td>
													<td><c:out value="${billHeader.status}" /></td>
													<td><c:out value="${billHeader.remark}" /></td>
													
													
												</tr>
												</c:forEach> --%>
											
										</table>
										</td>
										</tr>
										</table>

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


</body>
</html>
