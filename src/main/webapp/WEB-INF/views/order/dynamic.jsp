<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<!--rightNav-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/menuzord.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#menuzord").menuzord({
			align : "left"
		});
	});
</script>
<!--rightNav-->


</head>
<body> --%>
		<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>



	<!--topLeft-nav-->
	<div class="sidebarOuter"></div>
	<!--topLeft-nav-->

	<!--wrapper-start-->
	<div class="wrapper">

		<!--topHeader-->
		<jsp:include page="/WEB-INF/views/include/logo.jsp">
		
		</jsp:include>

		<!--topHeader-->

		<!--rightContainer-->
		<div class="fullGrid center">
			<!--fullGrid-->
			<div class="wrapperIn2">
				<jsp:include page="/WEB-INF/views/include/left.jsp">
					<jsp:param name="myMenu" value="${menuList}" />

				</jsp:include>


				<!--navInner-->

				<!--rightSidebar-->
				<div class="sidebarright">
					<div class="order-left">

						<h2 class="pageTitle">Order Savouries</h2>
						<h3 class="pageTitle2">Order Date : ${currentDate}</h3>
					</div>

					<div class="order-right">
						<div class="ordermto2px">
							<div class="orderclose">Order Closing Time :</div>
							<div class="ordercloser2">
								<span>${toTime}</span>
							</div>
						</div>
						<div class="ordermto20px">
							<div class="order-price">Total Amount :</div>
							<div class="order-amount">

								<label>INR: </label> <label id="grandtotal">${grandTotal}</label>

								<%-- <p class="totals" id="grandtotal">INR: ${grandTotal}</p> --%>
							</div>
						</div>
					</div>

					<form action="${pageContext.request.contextPath}/saveOrder"
						name="form1" method="post">

						<!--tabNavigation-->
						<div class="cd-tabs">
							<!--tabMenu-->
							<nav>
								<ul class="cd-tabs-navigation">

									<c:forEach var="tab" items="${subCatList}" varStatus="loop">


										<c:choose>
											<c:when test='${loop.index==0}'>
												<li><a data-content='${tab}' href="#0" class="selected"
													onClick="javascript:se_tab_id('${loop.index}')">${tab}</a></li>

											</c:when>
											<c:otherwise>
												<li><a data-content='${tab}' href="#0"
													onClick="javascript:se_tab_id('${loop.index}')">${tab}</a></li>

											</c:otherwise>
										</c:choose>


										<!-- <li><a data-content="tab2" href="#0"
										onClick="javascript:se_tab_id(2)">Tab     2</a></li>
									<li><a data-content="tab3" href="#0"
										onClick="javascript:se_tab_id(3)">Tab 3 dfdfsdf dfsdf</a></li>
 -->
									</c:forEach>

								</ul>
							</nav>
							<!--tabMenu-->
							<ul class="cd-tabs-content">
								<!--tab1-->

								<c:forEach var="tabs" items="${subCatList}" varStatus="loop">


									<c:choose>
										<c:when test='${loop.index==0}'>
											<li data-content='${tabs}' class="selected">
										</c:when>
										<c:otherwise>
											<li data-content='${tabs}'>
										</c:otherwise>
									</c:choose>


									<div class="table-responsive">
										<div class="shInnerwidth">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0" class="table">
												<tr>
													<td align="center" valign="middle" style="padding: 0px;"><table
															width="100%" border="0" cellspacing="0" cellpadding="0">
															<tr class="bgpink">

																<td>Item Name</td>
																<td>Quantity</td>
																<td>MRP</td>

																<td>Rate</td>
																<td>Total</td>
															</tr>


															<%-- <c:set var="menuTime" value="${menu.timing}" /> --%>


															<c:forEach var="items" items="${itemList}"
																varStatus="loop">
																<c:if test="${items.subCatName eq tabs}">

																	<tr>

																		<td><c:out value='${items.itemName}' /></td>
																		<td><input name='${items.itemId}'
																			id='${items.itemId}' value='${items.itemQty}'
																			class="tableInput" type="text"
																			onchange="onChange('${items.itemRate1}',${items.itemId})"></td>
																		<td><c:out value='${items.itemMrp1}' /></td>

																		<td><c:out value='${items.itemRate1}' /></td>
																		<c:set var="rate" value="${items.itemRate1}" />
																		<c:set var="qty" value="${items.itemQty}" />
																		<td id="total${items.itemId}"><c:out
																				value='${rate * qty}' /></td>
																	</tr>
																</c:if>
															</c:forEach>

														</table></td>
												</tr>
											</table>
										</div>
									</div>

								</c:forEach>

							</ul>
						</div>
						<!--tabNavigation-->

						<!--<div class="order-btn"><a href="#" class="saveOrder">SAVE ORDER</a></div>-->
						<div class="order-btn textcenter">

							<input name="" class="buttonsaveorder" value="SAVE ORDER"
								type="button" ONCLICK="button1()">
						</div>



					</form>

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

	<script>
		function openNav() {
			document.getElementById("mySidenav").style.width = "100%";
		}

		function closeNav() {
			document.getElementById("mySidenav").style.width = "0";
		}
		function openNav1() {
			document.getElementById("mySidenav1").style.width = "100%";
		}

		function closeNav1() {
			document.getElementById("mySidenav1").style.width = "0";
		}
		function openNav3() {
			document.getElementById("mySidenav3").style.width = "100%";
		}

		function closeNav3() {
			document.getElementById("mySidenav3").style.width = "0";
		}
	</script>

	<script type="text/javascript">
           
            function button1()
            {

             //   document.form1.buttonName.value = "SAVE ORDER";
                form1.submit();
            }    
           
        </script>

	<script type="text/javascript">
		function onChange(rate,id) {

			//calculate total value  
			var qty = $('#'+id).val();
			
			var total = rate * qty;
			
			  $('#total'+id).html(total);
			
		}
	</script>

	<script type="text/javascript">
		function getTotal() {
			
			   var list = '${itemList}';
			 	for (i = 0; i < list.length; ++i) {

			 	}
			
			<%-- document.getElementById('item_tot_' + item_id).innerHTML = parseFloat(
					final_rate).toFixed(2);
			
			<%System.out.println("Local time");%> --%>
			
		}
</script>
</body>
</html>
