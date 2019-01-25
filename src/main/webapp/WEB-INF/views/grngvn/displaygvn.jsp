<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
 <style>
table, th, td {
    border: 1px solid #9da88d;
}
</style>

<!--topLeft-nav-->
<div class="sidebarOuter"></div>
<!--topLeft-nav-->

<!--wrapper-start-->
<div class="wrapper">

	<!--topHeader-->
	<c:url var="getGvnList" value="/getGvnList" />

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
					<h2 class="pageTitle">GVN Details</h2>
					<!--<h3 class="pageTitle2">Order Date : 22-02-2017 </h3>-->
				</div>
				<div class="colOuter">
					<!-- copy div kalpesh -->
					<div class="calender-title"></div>

					<div class="col-md-6">
						<form action="" id="gvnForm" method="get">

							<table border="1">
								<tr bgcolor="orange">
									<th width="30%" align="left">Gvn Date</th>
									<th width="40%" align="left">Gvn SrNo</th>
									<th width="30%" align="left">Aprroved Amt</th>
								</tr>
								<tbody>
									<tr>
										<td align="center">${gvnDate}</td>
										<td align="center">${gvnSrNo}</td>
										<td align="center">${aprAmt}</td>
									</tr>
								</tbody>
							</table>
							<%-- 	GVN Date -<b> ${gvnDate}</b><br>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Grn SrNo -<b>${gvnSrNo}</b>
				    Appproved Amt -<b>${aprAmt}</b>
				     --%>
						</form>
					</div>
				</div>

				<input type="hidden" name="mod_ser" id="mod_ser"
					value="search_result">



				<div class="clearfix"></div>


				<div id="table-scroll" class="table-scroll">
					<div id="faux-table" class="faux-table" aria="hidden">
						<table id="table_grid1" class="main-table" style="width: 100%">
							<thead>
								<tr class="bgpink">
									<!-- <th class="col-md-1">Invoice No</th>
									<th class="col-md-2">Item Name</th>
									<th class="col-md-1">Bill Rate</th>
									<th class="col-md-1">Refund Rate</th>
									<th class="col-md-1">Gvn Qty</th>
									<th class="col-md-2">Total Refund Requested</th>
									<th class="col-md-1">Approved Qty</th>
									<th class="col-md-1">Approved total Refund</th>
									<th class="col-md-1">Approved Base Rate</th>
									<th class="col-md-1">Approved Tax Amount</th>
									<th class="col-md-1">Photo 1</th>
									<th class="col-md-1">Photo 2</th>
									<th class="col-md-1">Status</th> -->
								</tr>
							</thead></table></div>
					<div class="table-wrap">
						<table id="table_grid" class="main-table" style="width: 100%">
							<thead>
								<tr class="bgpink">
						
									<th class="col-md-1">Invoice No</th>
									<th class="col-md-2">Item Name</th>
									<th class="col-md-1">Bill Rate</th>
									<th class="col-md-1">Refund Rate</th>
									<th class="col-md-1">Gvn Qty</th>
									<th class="col-md-2">Total Refund Requested</th>
									<th class="col-md-1">Approved Qty</th>
									<th class="col-md-1">Approved total Refund</th>
									<th class="col-md-1">Approved Base Rate</th>
									<th class="col-md-1">Approved Tax Amount</th>
									<th class="col-md-1">Photo 1</th>
									<th class="col-md-1">Photo 2</th>
									<th class="col-md-1">Status</th>
								</tr>
							</thead>
							<tbody>


								<c:set var="status" value=""></c:set>

								<c:forEach items="${gvnList}" var="gvnList" varStatus="count">

									<c:set var="color" value="a"></c:set>

									<c:set var="color" value="white"></c:set>
									<c:choose>
										<c:when test="${gvnList.grnGvnQty!=gvnList.aprQtyAcc or gvnList.grnGvnStatus==7}">
											<c:set var="color" value="re-order"></c:set>
										</c:when>
										<c:otherwise>
											<c:set var="color" value="white"></c:set>
										</c:otherwise>
									</c:choose>

									<tr class="${color}">


										<td class="col-md-1"><c:out value="${gvnList.invoiceNo}" /></td>
										<td class="col-md-2"><c:out value="${gvnList.itemName}" /></td>
										<td class="col-md-1"><c:out value="${gvnList.itemRate}" /></td>
										<td class="col-md-1"><c:out value="${gvnList.itemRate}" /></td>

										<td class="col-md-1"><c:out value="${gvnList.grnGvnQty}" /></td>


										<td class="col-md-2"><c:out value="${gvnList.grnGvnAmt}" /></td>
										<td class="col-md-1"><c:out value="${gvnList.aprQtyAcc}" /></td>
										<td class="col-md-1"><c:out
												value="${gvnList.aprGrandTotal}" /></td>
										<td class="col-md-1"><c:out
												value="${gvnList.aprTaxableAmt}" /></td>
										<td class="col-md-1"><c:out
												value="${gvnList.aprTotalTax}" /></td>

										<td class="col-md-1"><a href="${url}${gvnList.gvnPhotoUpload1}"
											data-lightbox="image-1">Image 1</a></td>
										<td class="col-md-1"><a href="${url}${gvnList.gvnPhotoUpload2}"
											data-lightbox="image-2">Image 2</a></td>

										<c:choose>
											<c:when test="${gvnList.grnGvnStatus==1}">
												<c:set var="status" value="Pending"></c:set>
											</c:when>

											<c:when test="${gvnList.grnGvnStatus==2}">
												<c:set var="status" value="Approved From Dispatch"></c:set>
											</c:when>

											<c:when test="${gvnList.grnGvnStatus==3}">
												<c:set var="status" value="Reject From Dispatch"></c:set>
											</c:when>

											<c:when test="${gvnList.grnGvnStatus==4}">
												<c:set var="status" value="Approved By Sales"></c:set>
											</c:when>

											<c:when test="${gvnList.grnGvnStatus==5}">
												<c:set var="status" value="Reject From Sales"></c:set>
											</c:when>

											<c:when test="${gvnList.grnGvnStatus==6}">
												<c:set var="status" value="Approved From Account"></c:set>
											</c:when>

											<c:when test="${gvnList.grnGvnStatus==7}">
												<c:set var="status" value="Reject From Account"></c:set>
											</c:when>
										</c:choose>
										<td class="col-md-1"><c:out value="${status}" /></td>
								</c:forEach>

							</tbody>

						</table>
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

<script type="text/javascript">
	function searchGVN() {

		$('#table_grid td').remove();

		var isValid = validate();

		if (isValid) {

			var fromDate = document.getElementById("datepicker").value;
			var toDate = document.getElementById("datepicker2").value;

			$
					.getJSON(
							'${getGvnList}',
							{

								fromDate : fromDate,
								toDate : toDate,
								ajax : 'true'

							},
							function(data) {

								//$('#table_grid td').remove();

								if (data == "") {
									alert("No records found !!");

								}

								$
										.each(
												data,
												function(key, gvndata) {

													var tr = $('<tr></tr>');

													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			gvndata.invoiceNo));
													tr
															.append($(
																	'<td class="col-md-2"></td>')
																	.html(
																			gvndata.itemName));
													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			gvndata.itemRate));
													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			gvndata.itemRate));
													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			gvndata.grnGvnQty));
												
													var status;
													if (gvndata.grnGvnStatus == 1)
														status = "Pending";
													else if (gvndata.grnGvnStatus == 2)
														status = "Approved From Dispatch";
													else if (gvndata.grnGvnStatus == 3)
														status = "Reject From Dispatch";
													else if (gvndata.grnGvnStatus == 4)
														status = "Approved From Sales";
													else if (gvndata.grnGvnStatus == 5)
														status = "Reject From Sales";
													else if (gvndata.grnGvnStatus == 6)
														status = "Approved From Account";
													else	if (gvndata.grnGvnStatus == 7)
														status = "Reject From Account";


													tr
															.append($(
																	'<td class="col-md-2"></td>')
																	.html(
																			grnGvnAmt));
													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			gvndata.aprQtyAcc
																					.toFixed(2)));
													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			gvndata.aprGrandTotal
																					.toFixed(2)));
													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			aprTaxableAmt));
													
													
													tr
													.append($(
															'<td class="col-md-1"></td>')
															.html(
																	aprTotalTax));
													
													tr
													.append($(
															'<td class="col-md-1"></td>')
															.html(
																	aprTaxableAmt));
													
													
													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			'<a href="'
																							+gvndata.gvnPhotoUpload1+'" data-lightbox="image-1" data-title="My caption"><i class="fa fa-image icon2"></i></a>'));
													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			'<a href="'
																							+gvndata.gvnPhotoUpload2+'" data-lightbox="image-1" data-title="My caption"><i class="fa fa-image icon2"></i></a>'));

													tr
															.append($(
																	'<td class="col-md-1"></td>')
																	.html(
																			gvndata.status));

													/* 
													 var index = key + 1;

													 var tr = "<tr>";
													

													 var billNo = "<td>"
													 + gvndata.billNo
													 + "</td>";
													 var grnGvnDate = "<td>"
													 + gvndata.grnGvnDate
													 + "</td>";
													 var itemName = "<td>"
													 + gvndata.itemName
													 + "</td>";

													 var itemRate = "<td>"
													 + gvndata.baseRate
													 + "</td>";

													 var grnGvnQty = "<td>"
													 + gvndata.grnGvnQty
													 + "</td>";
													
													
													 var gvnTaxPer = "<td>"
													 + calcTaxPer
													 + "</td>";
													
													 var totalTax = "<td>"
													 + gvndata.totalTax
													 + "</td>";
													
													
													

													 var grnGvnAmt = "<td>"
													 + gvndata.grnGvnAmt
													 + "</td>";

													
													
													
													 var grnGvnStatus = "<td>"
													 + gvndata.grnGvnStatus
													 + "</td>";

													 var gvnPhotoUpload1 = '<td> <a href="'
																							+gvndata.gvnPhotoUpload1+'" data-lightbox="image-1" data-title="My caption">Image 1</a>'
													 + '</td>';

													 var gvnPhotoUpload2 = '<td> <a href="'
																							+gvndata.gvnPhotoUpload2+'" data-lightbox="image-1" data-title="My caption">Image 2</a>'
													 + '</td>';

													 var frGrnGvnRemark = "<td>"
													 + gvndata.frGrnGvnRemark
													 + "</td>";

													 var trclosed = "</tr>";

													 $('#table_grid tbody')
													 .append(tr);
													

													 $('#table_grid tbody')
													 .append(billNo);
													 $('#table_grid tbody')
													 .append(
													 grnGvnDate);
													 $('#table_grid tbody')
													 .append(
													 itemName);
													 $('#table_grid tbody')
													 .append(
													 itemRate);
													 $('#table_grid tbody')
													 .append(
													 grnGvnQty);

													 $('#table_grid tbody')
													 .append(
													 gvnTaxPer);

													
													 $('#table_grid tbody')
													 .append(
													 totalTax);

													
													
													
													 $('#table_grid tbody')
													 .append(
													 grnGvnAmt);

													 $('#table_grid tbody')
													 .append(
													 grnGvnStatus);
													 $('#table_grid tbody')
													 .append(
													 gvnPhotoUpload1);
													 $('#table_grid tbody')
													 .append(
													 gvnPhotoUpload2);
													 $('#table_grid tbody')
													 .append(
													 frGrnGvnRemark);
													 */
													$('#table_grid tbody')
															.append(tr);

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



<script type="text/javascript">
	function show_image(data) {
	//	alert(data);
		var image = new Image();
		image.src = data;

		var w = window.open("");
		w.document.write(image.outerHTML);
	}
</script>



</body>
</html>
