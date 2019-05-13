<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

<style>
.pageTitle {
	margin-top: 0px;
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

<!--rightNav-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/menuzord.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#menuzord").menuzord({
			align : "left"
		});
	});
	$(".toggle").on("click", function() {
		$(".marquee").toggleClass("microsoft");
	});
</script>
<!--rightNav-->

</head>
<body> comment by sachin --%>
<style type="text/css">
.fit-img {
	position: absolute;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	margin: auto;
	max-width: 60%;
	max-height: 60%
}

.fit-img-bottom {
	top: auto;
}

.fit-img-tight {
	max-width: 60%;
	max-height: 60%
}
</style>

<!--topLeft-nav-->
<div class="sidebarOuter"></div>
<!--topLeft-nav-->

<!--wrapper-start-->
<div class="wrapper">

	<!--topHeader-->
	<jsp:include page="/WEB-INF/views/include/logo.jsp">

		<jsp:param name="fr" value="${frDetails}" />
	</jsp:include>




	<!--topHeader-->

	<!--rightContainer-->
	<div class="fullGrid center">
		<!--fullGrid-->
		<div class="wrapperIn2">

			<jsp:include page="/WEB-INF/views/include/left.jsp">
				<jsp:param name="myMenu" value="${menuList}" />

			</jsp:include>
			<%--     <input type="hidden" id="achievedTarget" value="${sessionScope.achievedTarget}">
       <input type="hidden" id="target" value="${sessionScope.fraTarget}"> --%>
			<%--  <c:choose>
       <c:when test="${loginInfo.accessRight==1}">
                  	<a href="${pageContext.request.contextPath}/viewMonthwiseSellBill">  <div id="chart_div" style="width: 220px;height: 70px;  float:right;margin-right: 60px;margin-top: 10px;"></div></a>
       
       </c:when>
       
       </c:choose> --%>

			<!--rightSidebar-->
			<div class="sidebarright">



				<!--slider-->
				<!--slider thum size : width:850px height:350px-->
				<div class="row">
					<div class="col-md-12">
						<h2 class="pageTitle">Credit Note Details List for Credit
							Note Id : ${creditNoteHeaders.crnId}</h2>
					</div>
				</div>
				<div id="owl-example" class="owl-carousel"></div>
				<!--slider-->

				<table style="width: 100%; border: 1px dashed; font-size: 14px;" >
					<tbody>
						<tr>
							<td style="border: 1px dashed; font-weight: bold;">&nbsp;CREDIT
								NOTE NO:</td>
							<td style="border: 1px dashed;">&nbsp; <b>
									${creditNoteHeaders.crnNo} </b></td>
							<td style="border: 1px dashed; font-weight: bold;">&nbsp;DATE:</td>
							<td style="border: 1px dashed;"><b> <input
									class="form-control date-picker" id="date" size="19"
									style="width: 170px;" placeholder="dd-mm-yyyy" type="text"
									name="date" value="${creditNoteHeaders.crnDate}" required />
							</b></td>
						</tr>
						<tr style="border: 1px dashed;">
							<td style="border: 1px dashed; font-weight: bold;">&nbsp;INVOICE
								NO:</td>
							<td style="border: 1px dashed;">&nbsp;<b>
									${creditNoteHeaders.exVarchar1} </b></td>
							<td style="border: 1px dashed; font-weight: bold;">&nbsp;FRANCHISEE
								NAME:</td>
							<td style="border: 1px dashed;">&nbsp;<b>
									${creditNoteHeaders.frName} </b></td>
						</tr>
						<tr>
							<td style="border: 1px dashed; font-weight: bold;">&nbsp;TAXABLE
								AMOUNT:</td>
							<td style="border: 1px dashed;">&nbsp;<b>
									${creditNoteHeaders.crnTaxableAmt} </b></td>
							<td style="border: 1px dashed; font-weight: bold;">&nbsp;TAX
								AMOUNT:</td>
							<td style="border: 1px dashed;">&nbsp;<b>
									${creditNoteHeaders.crnTotalTax} </b></td>
						</tr>
						<tr>
							<td colspan="2" style="border: 1px dashed;">&nbsp;</td>
							<td style="border: 1px dashed; font-weight: bold;">&nbsp;TOTAL:</td>
							<td style="border: 1px dashed;">&nbsp;<b>
									${creditNoteHeaders.crnGrandTotal}</b></td>
						</tr>

					</tbody>
				</table>
				<br> <br>

				<div class="table-wrap">
					<table width="100%"
						 class="main-table" 
						id="table2">
						<thead >
							<tr>

								<th>Sr No</th>
								<th>GrnGvn Sr No</th>
								<th>GrnGvn Date</th>
								<th>Invoice No</th>
								<th>Item Name</th>
								<th>Type</th>
								<th>GrnBaseRate</th>
								<th>Quantity</th>
								<th>Tax %</th>
								<th>Taxable Amt</th>
								<th>Tax Amt</th>
								<th>Amount</th>
							</tr>

						</thead>
						<tbody>
							<c:forEach items="${crnDetailList}" var="crnDetail"
								varStatus="count">

								<tr>

									<td><c:out value="${count.index+1}" /></td>

									<td align="left"><c:out value="${crnDetail.grngvnSrno}" /></td>

									<td align="left"><c:out value="${crnDetail.grnGvnDate}" /></td>


									<td align="left"><c:out value="${crnDetail.refInvoiceNo}" /></td>

									<td align="left"><c:out value="${crnDetail.itemName}" /></td>


									<c:set var="type" value="aa"></c:set>

									<c:choose>
										<c:when test="${crnDetail.isGrn==1}">

											<c:choose>
												<c:when test="${crnDetail.grnType==0}">
													<c:set var="type" value="GRN -(1)"></c:set>
												</c:when>

												<c:when test="${crnDetail.grnType==1}">
													<c:set var="type" value="GRN -(2)"></c:set>
												</c:when>

												<c:when test="${crnDetail.grnType==2}">
													<c:set var="type" value="GRN -(3)"></c:set>
												</c:when>

												<c:when test="${crnDetail.grnType==4}">
													<c:set var="type" value="GRN -(3)"></c:set>
												</c:when>

											</c:choose>

										</c:when>
										<c:when test="${crnDetail.isGrn==0}">

											<c:set var="type" value="GVN"></c:set>

										</c:when>
									</c:choose>

									<td align="left"><c:out value="${type}"></c:out></td>
									<fmt:formatNumber type="number" maxFractionDigits="2"
										minFractionDigits="2"
										value="${crnDetail.taxableAmt/crnDetail.grnGvnQty}"
										var="grnBaseRate" />

									<td align="left">${grnBaseRate}<input type="hidden"
										name="grnBaseRate${crnDetail.crndId}"
										id="grnBaseRate${crnDetail.crndId}" value="${grnBaseRate}" />
									</td>
									<td align="left"><input type="text" class="form-control"
										style="width: 80px;" name="grnGvnQty${crnDetail.crndId}"
										id="grnGvnQty${crnDetail.crndId}"
										value="${crnDetail.grnGvnQty}" readonly
										onchange="calCrnValues(${crnDetail.crndId},${grnBaseRate})" />
									</td>
									<td align="left"><input type="text" class="form-control"
										style="width: 80px;" name="totalTaxPer${crnDetail.crndId}"
										id="totalTaxPer${crnDetail.crndId}" readonly
										value="${crnDetail.cgstPer+crnDetail.sgstPer}"
										onchange="calCrnValues(${crnDetail.crndId},${grnBaseRate})" />
									</td>
									<td align="left" id="taxableAmt${crnDetail.crndId}"><c:out
											value="${crnDetail.taxableAmt}"></c:out></td>

									<td align="left" id="totalTax${crnDetail.crndId}"><c:out
											value="${crnDetail.totalTax}"></c:out></td>

									<td align="left" id="grnGvnAmt${crnDetail.crndId}"><c:out
											value="${crnDetail.grnGvnAmt}"></c:out></td>
								</tr>

							</c:forEach>

						</tbody>

					</table>
				</div>



			</div>
			<!--rightSidebar-->

		</div>
		<!--fullGrid-->
	</div>
	<!--rightContainer-->

</div>
<!--wrapper-end-->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>

<script type="text/javascript"
	src="https://www.gstatic.com/charts/loader.js"></script>


</body>
</html>
