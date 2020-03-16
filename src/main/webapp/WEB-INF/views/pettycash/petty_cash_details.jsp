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

</head>--%>
<body onload="getData()"> 

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/loader.css">
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
				<div class="order-right" align="right" style="padding-top:2%;">

					<a href="${pageContext.request.contextPath}/showPattyCashMgmnt"><input
						type="button" value="Petty Cash" class="btn additem_btn">
					</a>
				</div>
		<!-- <form action="addPettyCash" method="post"> -->
				
				<div class="colOuter">
					<div class="col-md-2">
						<div class="col1title" align="left">From Date</div>
					</div>
					<div class="col-md-3">
							<input id="datepicker" class="texboxitemcode texboxcal" autocomplete="off"
								value="${firstDate}" name="from_Date" type="text">
					</div>
					
					<div class="col-md-1"></div>
					
					<div class="col-sm-2">
						<div class="col1title" align="left">To Date</div>
					</div>
					<div class="col-md-3">
							<input id="datepicker2" class="texboxitemcode texboxcal" autocomplete="off"
								value="${currentDate}" name="to_Date" type="text">
					</div>
					
					
				</div>
				
				

				
				

				<div class="colOuter">
					<div class="col1">
						<div class="col1title"></div>
					</div>
					<div class="col2">
						<input class="buttonsaveorder" value="Submit"
							type="submit" onclick="getData()">

 
								<button type="button" class="btn btn-primary" onclick="genPdf()" disabled
									id="pdf"
									style="padding: 8px 12px;">
									PDF</button>
							
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
				
				<!-- </form> -->



				<div class="row">
					<div class="col-md-12">
						<!--table-->
						<form action="monthEndProcess" method="POST"
							onsubmit="substk.disabled = true; return confirm('Do you want to Month End ?');">
							<div class="clearfix"></div>
							<div class="col-md-9"></div>
							<!-- <label for="search" class="col-md-3" id="search"> <i
								class="fa fa-search" style="font-size: 20px"></i> <input
								type="text" id="myInput" onkeyup="myFunction()"
								style="border-radius: 25px;" placeholder="Search items by name"
								title="Type item name">
							</label>
 -->

							<div id="table-scroll"  class="table-scroll responsive-table-one"><!-- class="table-scroll" -->								
								<div><!--  class="table-wrap" -->
									<table id="table_grid" class="responsive-table"><!-- class="main-table" -->
										<thead>											
											<tr class="bgpink">
												<th class="col-md-1">Sr. No</th>
												<th class="col-md-1">Date</th>
												<th class="col-md-1">Opening Amt</th>
												<th class="col-md-1">Cash Amt</th>
												<th class="col-md-1">Withdrawal Amt</th>
												<th class="col-md-1">Closing Amt</th>
												<!-- <th class="col-md-1">Action</th> -->
											</tr>
										
										</thead>
										<tbody>
										
										
										</tbody>
										<%--  <tbody>
										<c:forEach items="${pettyList}" var="pettyList"
														varStatus="count">
											<tr>
												<td >${count.index+1}</td>
												<td>${pettyList.date}</td>
												<td>${pettyList.openingAmt}</td>
												<td>${pettyList.cashAmt}</td>
												<td>${pettyList.withdrawalAmt}</td>
												<td>${pettyList.closingAmt}</td>
												<!-- pettycashId -->
												<td>
														<a href="${pageContext.request.contextPath}/editPettyCash?pettyCashIdId=${pettyList.pettycashId}" title="Edit">
														<i class="fa fa-edit"></i></a>
		
													<abbr title="Delete"><i class="fa fa-trash"><a href="${pageContext.request.contextPath}/editPettyCash?pettyCashIdId=${pettyList.pettycashId}"></a></i></abbr>
												</td>
											</tr>
											</c:forEach>
										</tbody>  --%>										
									</table>
								</div>

							</div>


							<!-- <div class="col-md-2">

								<button type="button" class="btn btn-primary"
									onclick="exportToExcel();" id="expExcel">
									Export To Excel</button>
						 	</div> -->


							<!-- <div class="col-md-3">
 
								<button type="button" class="btn btn-primary" onclick="genPdf()" disabled
									id="pdf"
									>
									PDF</button>
							</div> -->

						</form>
					</div>
				</div>
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

<script>
function calClosingAmt(){
	
	var cashAmt = parseFloat($("#cash_amt").val());
	var openAmt = parseFloat($("#opening_amt").val());
	var withdrawAmt = parseFloat($("#withdrawal_amt").val());
	
	var closeAmt = openAmt+cashAmt-withdrawAmt;

	document.getElementById("closing_amt").value = parseFloat(closeAmt);	
}


function editPettyCashDetails(pettyCashIdId){
//alert("LogId--"+logId);
window.open("${pageContext.request.contextPath}/editPettyCash?pettyCashIdId="+pettyCashIdId);
}
</script>

<!-- Select Only Month and Year -->
<script>
 function getData(){
	 $('#loader').show();

		var from_date = $("#datepicker").val();
		var to_date = $("#datepicker2").val();
		$
		.getJSON(
				'${getPettyCashData}',

				{
					 
					from_date : from_date,
					to_date : to_date, 
					ajax : 'true'

				},
				function(data) {
					if (data != null) {						
						document.getElementById("pdf").disabled = false;	
						 $('#loader').hide();
					}
					//alert("Petty Info1----"+JSON.stringify(data));
				$('#table_grid td').remove(); 
		  
	  $.each(
					data,
					function(i, v) {
												
					//	var acButton = '&nbsp;&nbsp;<a href="#" onclick="editPettyCashDetails('+ v.pettycashId+')"><i class="fa fa-edit" style="color: black;"></i></a>';
				
						var tr = $('<tr></tr>');
						
						tr.append($('<td></td>').html(i+1));
						tr.append($('<td></td>').html(v.date));
						tr.append($('<td></td>').html(v.openingAmt));
					  	tr.append($('<td></td>').html(v.cashAmt));
					  	tr.append($('<td></td>').html(v.withdrawalAmt));
					  	tr.append($('<td></td>').html(v.closingAmt));
						//tr.append($('<td></td>').html(acButton));
						$('#table_grid tbody').append(tr);						 

					})  
				
				});
		
		
	}
</script>

<script type="text/javascript">
	function genPdf() {
		var fromDate = $("#datepicker").val();
		var toDate = $("#datepicker2").val();
		window.open('${pageContext.request.contextPath}/getPettyCashDetailPdf/'+ fromDate + '/' + toDate);
	}

	/* function exportToExcel() {

		window.open("${pageContext.request.contextPath}/exportToExcel");
		document.getElementById("expExcel").disabled = true;
	} */
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






</body>

</html>