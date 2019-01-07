<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

<!--topLeft-nav-->
<div class="sidebarOuter"></div>
<!--topLeft-nav-->

<!--wrapper-start-->
<div class="wrapper">

	<!--topHeader-->
	<c:url var="getGrnList" value="/getGrnList" />
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
					<h2 class="pageTitle">GRN Details</h2>

				</div>
				<div class="colOuter">
					<!-- copy div kalpesh -->
					<div class="calender-title"></div>


					<div class="col-md-6">
						<form action="" id="grnForm" method="get">

							<table border="1">
								<tr bgcolor="orange">
									<th width="30%" align="left">Grn Date</th>
									<th width="40%" align="left">Grn SrNo</th>
									<th width="30%" align="left">Aprroved Amt</th>
								</tr>
								<tbody>
									<tr>
										<td align="center">${grnDate}</td>
										<td align="center">${grnSrNo}</td>
										<td align="center">${aprAmt}</td>
									</tr>
								</tbody>
							</table>
						</form>
					</div>
				</div>

				<!--tabNavigation-->
				<div class="cd-tabs">
					<!--tabMenu-->

					<!--tabMenu-->
					<div id="table-scroll" class="table-scroll">
						<div id="faux-table" class="faux-table" aria="hidden"></div>
						<div class="table-wrap">
							<table id="table_grid" class="main-table">
								<thead>
									<tr class="bgpink">
										<th class="col-md-1">Invoice No</th>
										<th class="col-md-2">Item Name</th>
										<th class="col-md-1">Type</th>
										<th class="col-md-1">Bill Rate</th>
										<th class="col-md-1">Refund Rate</th>
										<th class="col-md-1">Qnty</th>
										<th class="col-md-1">Tot Refund requested</th>
										<th class="col-md-1">Approved Qnty</th>
										<th class="col-md-1">Approved Tot Refund</th>
										<th class="col-md-1">Approved BaseRate</th>
										<th class="col-md-1">Approved Tax Amt</th>
										<th class="col-md-1">Status</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${grnList}" var="grnList" varStatus="count">
										<c:set var="color" value="white"></c:set>
										<c:choose>
											<c:when test="${grnList.grnGvnQty!=grnList.aprQtyAcc || grnList.grnGvnStatus==7}">
												<c:set var="color" value="re-order"></c:set>
											</c:when>
											<c:otherwise>
												<c:set var="color" value="white"></c:set>
											</c:otherwise>
										</c:choose>
										<tr class="${color}">
											<td class="col-md-1"><c:out value="${grnList.invoiceNo}" /></td>
											<td class="col-md-1"><c:out value="${grnList.itemName}" /></td>

											<c:choose>
												<c:when test="${grnList.grnType==0}">
													<td class="col-md-1"><c:out value="GRN 1"></c:out></td>
												</c:when>
												<c:when test="${grnList.grnType==1}">
													<td class="col-md-1"><c:out value="GRN 2"></c:out></td>
												</c:when>
												<c:when test="${grnList.grnType==2}">
													<td class="col-md-1"><c:out value="GRN 3"></c:out></td>
												</c:when>
												<c:when test="${grnList.grnType==3}">
													<td class="col-md-1"><c:out value="No GRN"></c:out></td>
												</c:when>
												<c:when test="${grnList.grnType==4}">
													<td class="col-md-1"><c:out value="GRN 3"></c:out></td>
												</c:when>
											</c:choose>

											<td class="col-md-1"><c:out value="${grnList.itemRate}" /></td>

											<td class="col-md-1"><c:out value="${grnList.baseRate}" /></td>
											<td class="col-md-1"><c:out value="${grnList.grnGvnQty}" /></td>

											<td class="col-md-1"><c:out value="${grnList.grnGvnAmt}" /></td>

											<td class="col-md-1"><c:out value="${grnList.aprQtyAcc}" /></td>

											<td class="col-md-1"><c:out
													value="${grnList.aprGrandTotal}" /></td>

											<td class="col-md-1"><c:out
													value="${grnList.aprTaxableAmt}" /></td>
											<td class="col-md-1"><c:out
													value="${grnList.aprTotalTax}" /></td>
											<c:choose>
												<c:when test="${grnList.grnGvnStatus==1}">
													<td class="col-md-1"><c:out value="Pending"></c:out></td>
												</c:when>
												<c:when test="${grnList.grnGvnStatus==2}">
													<td class="col-md-1"><c:out
															value="Approved From Dispatch"></c:out></td>
												</c:when>
												<c:when test="${grnList.grnGvnStatus==3}">
													<td class="col-md-1"><c:out
															value="Reject From Dispatch"></c:out></td>
												</c:when>

												<c:when test="${grnList.grnGvnStatus==4}">
													<td class="col-md-1"><c:out
															value="Approved From Sales"></c:out></td>
												</c:when>

												<c:when test="${grnList.grnGvnStatus==5}">
													<td class="col-md-1"><c:out value="Reject From Sales"></c:out></td>
												</c:when>
												<c:when test="${grnList.grnGvnStatus==6}">
													<td class="col-md-1"><c:out
															value="Approved From Account"></c:out></td>
												</c:when>
												<c:when test="${grnList.grnGvnStatus==7}">
													<td class="col-md-1"><c:out
															value="Reject From Account"></c:out></td>
												</c:when>

											</c:choose>

										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>

				</div>
				<!--tabNavigation-->
			</div>
			<!--rightSidebar-->

		</div>
		<!--fullGrid-->
	</div>
	<!--rightContainer-->

</div>
<!--wrapper-end-->

<script type="text/javascript">
	function searchGRN() {

		$('#table_grid td').remove();

		var isValid = validate();

		if (isValid) {

			var fromDate = document.getElementById("datepicker").value;
			var toDate = document.getElementById("datepicker2").value;

			$.getJSON('${getGrnList}', {

				fromDate : fromDate,
				toDate : toDate,
				ajax : 'true'

			}, function(data) {

				//$('#table_grid td').remove();

				if (data == "") {
					alert("No records found !!");

				}

				$.each(data, function(key, grndata) {

					var tr = $('<tr></tr>');
					var calcTaxPer = parseFloat(grndata.sgstPer)
							+ parseFloat(grndata.cgstPer);

					var grnType;
					var grnStatus;
					if (grndata.grnType == 0)
						grnType = "GRN 1";
					if (grndata.grnType == 1)
						grnType = "GRN 2";
					if (grndata.grnType == 2)
						grnType = "GRN 3";
					if (grndata.grnType == 3)
						grnType = "No GRN";
					if (grndata.grnType == 4)
						grnType = "GRN 4";

					if (grndata.grnGvnStatus == 1)
						grnStatus = "Pending";
					if (grndata.grnGvnStatus == 2)
						grnStatus = "Approved From Dispatch";
					if (grndata.grnGvnStatus == 3)
						grnStatus = "Reject From Dispatch";
					if (grndata.grnGvnStatus == 4)
						grnStatus = "Approved From Sales";
					if (grndata.grnGvnStatus == 5)
						grnStatus = "Reject From Sales";
					if (grndata.grnGvnStatus == 6)
						grnStatus = "Approved From Account";
					if (grndata.grnGvnStatus == 7)
						grnStatus = "Reject From Account";

					tr.append($('<td class="col-md-1"></td>').html(
							grndata.invoiceNo));
					tr.append($('<td class="col-md-2"></td>').html(
							grndata.itemName));
					tr.append($('<td class="col-md-1"></td>').html(grnType));
					tr.append($('<td class="col-md-1"></td>').html(
							grndata.baseRate));
					tr.append($('<td class="col-md-1"></td>').html(
							grndata.grnGvnQty));

					tr.append($('<td class="col-md-1"></td>').html(
							grndata.grnGvnAmt));

					tr.append($('<td class="col-md-1"></td>').html(
							grndata.aprQtyAcc));

					tr.append($('<td class="col-md-1"></td>').html(
							grndata.aprGrandTotal));

					tr.append($('<td class="col-md-1"></td>').html(
							grndata.aprTotalTax));

					tr.append($('<td class="col-md-1"></td>').html(
							grndata.aprGrandTotal));

					tr.append($('<td class="col-md-1"></td>').html(
							grndata.aprGrandTotal));

					tr.append($('<td class="col-md-1"></td>').html(grnStatus));

					$('#table_grid tbody').append(tr);

				})

			});

		}
	}
</script>

<script type="text/javascript">
	function validate() {

		var fromDate = $("#datepicker").val();
		var toDate = $("#datepicker2").val();
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

</body>
</html>