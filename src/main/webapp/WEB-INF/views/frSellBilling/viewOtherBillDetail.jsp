

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

 

</head>
<body>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<c:url var="editFrSupplier" value="/editFrSupplier"></c:url>

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
	<!-- <style>
table, th, td {
    border: 1px solid #9da88d;
}

</style> -->
<!--datepicker-->

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


		 
			<div class="sidebarright">
				 
				<form name="frm_search" id="frm_search" method="post"
					action="${pageContext.request.contextPath}/insertSupplier">
					<input type="hidden" name="mod_ser" id="mod_ser"
						value="search_result">

					
						<div class="col-md -3">
							
								<div class="col1title" align="left"><h3>Other Purchase Bill Detail</h3></div>
						</div>
						
					<div class="colOuter">
						<div class="col-md-2">
							<div class="col1title" align="left">Invoice No.: </div>
						</div>
						<div class="col-md-1" align="left">
							${otherBillHeader.invoiceNo}

						</div>
						<div class="col-md-1">
							 
						</div>

						<div class="col-md-2">
							<div class="col1title" align="left">Bill Date: </div>
						</div>
						<div class="col-md-2" align="left">
							${otherBillHeader.billDate}

						</div>
					 
					</div>
					
					<div class="colOuter">
						<div class="col-md-2" align="left">
							<div class="col1title">Supplier Name: </div>
						</div>
						<div class="col-md-1" align="left">
							${supplier.suppName}

						</div>
						<div class="col-md-1">
							 
						</div>

						<div class="col-md-2">
							<div class="col1title" align="left">Discount AMT: </div>
						</div>
						<div class="col-md-1" align="left">
							${otherBillHeader.discAmt}
					 

						</div>
				 
					</div>
					
					<div class="colOuter">
						<div class="col-md-2" >
							<div class="col1title" align="left">Taxable AMT: </div>
						</div>
						<div class="col-md-1" align="left">
							${otherBillHeader.taxableAmt}

						</div>
						<div class="col-md-1">
							 
						</div>

						<div class="col-md-2">
							<div class="col1title" align="left">CGST AMT: </div>
						</div>
						<div class="col-md-1" align="left">
							${otherBillHeader.cgstSum}
						</div>
				 
					</div>
					
					<div class="colOuter">
						<div class="col-md-2">
							<div class="col1title" align="left">SGST AMT: </div>
						</div>
						<div class="col-md-1" align="left">
							${otherBillHeader.sgstSum}

						</div>
						<div class="col-md-1">
							 
						</div>

						<div class="col-md-2">
							<div class="col1title" align="left">IGST AMT: </div>
						</div>
						<div class="col-md-1" align="left">
							${otherBillHeader.igstSum}

						</div>
				 
					</div>
					
					<div class="colOuter">
						<div class="col-md-2">
							<div class="col1title" align="left">Total: </div>
						</div>
						<div class="col-md-1" align="left">
							${otherBillHeader.grandTotal}

						</div>
						 
				 
					</div>
					
					  
					
					<div id="table-scroll" class="table-scroll">
					<div id="faux-table" class="faux-table" aria="hidden"></div>
					<div class="table-wrap">
						<table   id="table_grid" class="main-table">

							<thead>
								<tr class="bgpink">
								
									<th class="col-md-1" >Sr No</th>
									<th class="col-md-1" >Item Name</th> 
									<th class="col-md-1" style="text-align:right;">Qty</th>
									<th class="col-md-1" style="text-align:right;">Base Rate</th> 
									<th class="col-md-1" style="text-align:right;">Discount Amt</th>
									<th class="col-md-1" style="text-align:right;">Taxable Amt</th>
									<th class="col-md-1" style="text-align:right;">Tax Amt</th>
									<th class="col-md-1" style="text-align:right;">Total Amt</th>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${otherBillHeader.otherBillDetailList}" var="otherBillDetailList"
									varStatus="count">
									<tr>
										 <td class="col-md-1"><c:out value="${count.index+1}" /></td>
										 <td class="col-md-1">
										 <c:set var="name" value=""></c:set>
										  <c:forEach items="${itemsList}" var="itemsList" >
												 <c:choose> 
												 <c:when test="${otherBillDetailList.itemId==itemsList.id}">
												 <c:set var="name" value="${itemsList.itemName}"></c:set>
												
												 </c:when>
												 </c:choose>
										 </c:forEach>${name}</td>
										
										<td class="col-md-1" style="text-align:right">
										<fmt:formatNumber type="number"	minFractionDigits="2" maxFractionDigits="2"	value="${otherBillDetailList.billQty}"/>
										</td>
										<td class="col-md-1" style="text-align:right"><fmt:formatNumber type="number"	minFractionDigits="2" maxFractionDigits="2"	value="${otherBillDetailList.baseRate}" /></td>
										<td class="col-md-1" style="text-align:right"><fmt:formatNumber type="number"	minFractionDigits="2" maxFractionDigits="2"	value="${otherBillDetailList.discRs}" /></td>
										<td class="col-md-1" style="text-align:right"><fmt:formatNumber type="number"	minFractionDigits="2" maxFractionDigits="2"	value="${otherBillDetailList.taxableAmt}" /></td>
										<td class="col-md-1" style="text-align:right"><fmt:formatNumber type="number"	minFractionDigits="2" maxFractionDigits="2"	value="${otherBillDetailList.totalTax}" /></td>
										<td class="col-md-1" style="text-align:right"><fmt:formatNumber type="number"	minFractionDigits="2" maxFractionDigits="2"	value="${otherBillDetailList.grandTotal}" /></td>
										 
									</tr>
								</c:forEach>
						</table>

					</div>
				</div>

				</form>

				 
			</div>
			<!--tabNavigation-->
			<!--<div class="order-btn"><a href="#" class="saveOrder">SAVE ORDER</a></div>-->
			<%-- <div class="order-btn textcenter">
						<a
							href="${pageContext.request.contextPath}/showBillDetailProcess/${billNo}"
							class="buttonsaveorder">VIEW DETAILS</a>
						<!--<input name="" class="buttonsaveorder" value="EXPORT TO EXCEL" type="button">-->
					</div> --%>


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
