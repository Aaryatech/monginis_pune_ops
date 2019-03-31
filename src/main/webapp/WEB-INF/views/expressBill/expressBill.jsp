<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
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
<link href="${pageContext.request.contextPath}/resources/css/custom.css"
	rel="stylesheet" type="text/css" />
<link
	href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="icon"
	href="${pageContext.request.contextPath}/images/feviconicon.png"
	type="image/x-icon" />
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<script src="https://code.jquery.com/jquery-1.9.1.js"></script>
<script src="https://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/autocomplete.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/autocomplete.css">
<script
	src="${pageContext.request.contextPath}/resources/css/easy-responsive-tabs.css"></script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>

<!--rightNav-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/menuzord.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>

<script>
  $( function() {
    $( "#todatepicker" ).datepicker({ dateFormat: 'dd-mm-yy' });
  } );
  $( function() {
    $( "#fromdatepicker" ).datepicker({ dateFormat: 'dd-mm-yy' });
  } );
 
  </script>
<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#menuzord").menuzord({
			align : "left"
		});
	});
</script>
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/jquery.mCustomScrollbar.css">
<script
	src="${pageContext.request.contextPath}/resources/css/bootstrap.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/bootstrap-select.min.css" />
<script
	src="${pageContext.request.contextPath}/resources/css/bootstrap-select.min.js"></script>
<!-- 1 css and 2 js for search item   -->
<style>
.switch {
	position: relative;
	display: inline-block;
	width: 60px;
	height: 25px;
	margin-top: 14px;
}

.switch input {
	display: none;
}

.slider {
	position: absolute;
	cursor: pointer;
	top: -2px;
	left: 0px;
	right: 0;
	bottom: 0;
	background-color: #ccc;
	-webkit-transition: .4s;
	transition: .4s;
}

.slider:before {
	position: absolute;
	content: "";
	height: 19px;
	width: 21px;
	left: 5px;
	bottom: 4px;
	background-color: white;
	-webkit-transition: .3s;
	transition: .3s;
}

input:checked+.slider {
	background-color: #2196F3;
}

input:focus+.slider {
	box-shadow: 0 0 1px #2196F3;
}

input:checked+.slider:before {
	-webkit-transform: translateX(26px);
	-ms-transform: translateX(26px);
	transform: translateX(26px);
}
/* Rounded sliders */
.slider.round {
	border-radius: 52px;
}

.slider.round:before {
	border-radius: 50%;
}

#overlay {
	position: fixed;
	display: none;
	width: 100%;
	height: 100%;
	top: 0;
	left: 0;
	right: 0;
	bottom: 0;
	background-color: rgba(101, 113, 119, 0.5);
	z-index: 2;
	cursor: pointer;
}

#text {
	position: absolute;
	top: 50%;
	left: 50%;
	font-size: 25px;
	color: white;
	transform: translate(-50%, -50%);
	-ms-transform: translate(-50%, -50%);
}
</style>
</head>
<body>
	<div id="overlay">
		<div id="text">
			Please Wait...
			<%-- <img id="loading-image1" src="${pageContext.request.contextPath}/resources/images/loader1.gif" alt="Loading..." /> --%>
		</div>
	</div>

	<c:url var="getItemDetails" value="/findItemDetails" />
	<c:url var="insertItemOrder" value="/insertItem" />
	<c:url var="insertHeader" value="/insertHeader" />
	<div class="sidebarOuter"></div>
	<c:url var="calcStock" value="/calcStock" />
	<c:url var="printExBill" value="/printExBill" />
	<c:url var="deleteItem" value="/deleteItem" />
	<c:url var="getSelectedIdForPrint" value="/getSelectedIdForPrint" />
	<c:url var="getSpOrders" value="/getSpOrders" />
	<c:url var="getSpOrder" value="/getSpOrder" />
	<c:url var="dayClose" value="/dayClose" />
	<div class="wrapper">
		<jsp:include page="/WEB-INF/views/include/logo.jsp"></jsp:include>
		<!--rightContainer-->
		<div class="fullGrid">
			<!--fullGrid-->
			<div class="wrapperIn2">
				<!--leftNav-->
				<jsp:include page="/WEB-INF/views/include/left.jsp">
					<jsp:param name="myMenu" value="${menuList}" />
				</jsp:include>
				<!--leftNav-->
				<!--rightSidebar-->
				<!------------ Place Actual content of page inside this div ----------->
				<div class="sidebarright">
					<div class="col-md-2">
						<h2 class="pageTitle" style="color: #e53878;">Express Bill</h2>
					</div>
					<div class="col-md-12 text-left bill-date">
						<c:choose>

							<c:when test="${count == 0}">
								<div class="colOuter">
									<div>
										<center>
											<input name="" class="buttonsaveorder" value="Start Day"
												type="button" id="start" onclick="start(); hideMe(this.id);">
										</center>
									</div>
								</div>
							</c:when>

							<c:when test="${count ==2}">


								<div>

									<h4>
										<B> Bill No :${sellBillHeader.sellBillNo} &nbsp; &nbsp;
											&nbsp; Bill Date :${sellBillHeader.billDate}</B>
									</h4>
								</div>
					</div>
					<div class="col-md-2">
						<span
							style="padding-top: 0px; float: left; margin-top: 13px; font-size: 16px;">Single
							Print</span><label class="switch"> <input type="checkbox"
							id="id"> <span class="slider round"></span>
					</div>
					</label>
					<div class="row">
						<div class="col-md-2">
							<span
								style="padding-top: 0px; float: left; margin-top: 13px; font-size: 14px;">SP</span>
							<label class="switch"> <input type="checkbox" id="sp"
								name="sp"> <span class="slider round"></span>
							</label>
						</div>

						<div class="col-md-2" style="display: none;" id="tp">
							<select class="form-control" data-placeholder="Search Type"
								name="type" tabindex="-1" id="type" data-rule-required="true"
								onchange="onchangetype()">
								<option value="">Select Search Type</option>
								<option value="1">Search By Date</option>
								<option value="2">Search By Order No.</option>
							</select>
						</div>

						<div class="row" style="display: none;" id="byDate">
							<form name="frm_search" id="frm_search" method="post"
								action="itemHistory">

								<div class="col-md-2">
									<select name="group" id="group" placeholder="Select Menu"
										class="form-control" required>
										<option value="">Select Type</option>
										<option value="0">All</option>
										<c:forEach items="${menusList}" var="menusList">
											<c:choose>
												<c:when
													test="${menusList.mainCatId==5 || menusList.menuId==42}">

													<option value="${menusList.menuId}">${menusList.menuTitle}</option>
												</c:when>

											</c:choose>
										</c:forEach>
									</select>
								</div>
								<div class="col-md-2">
									<input id="todatepicker" class="texboxitemcode texboxcal"
										placeholder="Delivery Date" name="datepicker" type="text"
										autocomplete="off">
								</div>
								<div class="col-md-1">
									<input name="" class="buttonsaveorder" value="Search"
										type="button" onclick="searchOrders()">
								</div>
							</form>
						</div>

						<div style="display: none;" id="byOrderNo">
							<form name="frm_search" id="frm_search" method="post"
								action="itemHistory">
								<div class="col-md-2">
									<div class="col1title">Order No.</div>
								</div>
								<div class="col-md-2">
									<input id="orderno" class="form-control" placeholder="Order No"
										name="orderno" type="text">
								</div>
								<div class="col-md-1">
									<input name="" class="buttonsaveorder" value="Search"
										type="button" onclick="searchOrder()">
								</div>
							</form>
						</div>
						<div class="clearfix"></div>
						<div class="table-wrap" id="orderHistory" style="display: none;">

							<div id="table-scroll" class="table-scroll">
								<div id="faux-table" class="faux-table1" aria="hidden"></div>
								<table id="table_history" class="main-table" border="1px">
									<thead>
										<tr class="bgpink">
											<th class="col-md-1" style="text-align: center;">Type</th>
											<th class="col-md-1" style="text-align: center;">Order
												No</th>
											<th class="col-md-2" style="text-align: center;">Item
												Name</th>
											<th class="col-md-1" style="text-align: center;">Flavour</th>
											<th class="col-md-1" style="text-align: center;">Qty</th>
											<th class="col-md-1" style="text-align: center;">Delivery
												Date</th>
											<th class="col-md-1" style="text-align: center;">Rate</th>
											<th class="col-md-1" style="text-align: center;">Add On
												Rate</th>
											<th class="col-md-1" style="text-align: center;">Total</th>
											<th class="col-md-1" style="text-align: center;">Advance</th>
											<th class="col-md-1" style="text-align: center;">Memo &
												Bill</th>

										</tr>
									</thead>
									<tbody>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<div class="table">
						<div class="shInnerwidth">
							<table width="100%" border="0" cellspacing="0" cellpadding="0"
								class="table">
								<tr>
									<td align="center" valign="middle" style="padding: 0px;">
										<table width="100%" border="0" cellspacing="0" cellpadding="0">
											<tr class="bgpink">
												<td>BARCODE</td>
												<td>QTY</td>
												<td>ITEM NAME</td>
												<td>RATE</td>
											</tr>
											<tr>
												<td><input type="text" class="form-control"
													style="border-radius: 18px;"
													data-placeholder="Enter Barcode" id='input' autofocus
													onchange='onInput()' /></td>
												<td><input type="number" min="1" max="500"
													style="border-radius: 18px;" class="form-control"
													placeholder="1" name="qty1" onkeypress="onQty(event)"
													id="qty1" value="1" onfocusout="myFunction1()"></td>
												<td><input list="items" id="itemName" name="itemName"
													class="form-control chosen" autocomplete="off"
													placeholder="Item Name" onchange="onSelectItem()"
													style="border-radius: 18px;" type="text"> <datalist
														id="items">
														<c:forEach items="${itemsList}" var="itemsList">
															<option value='${itemsList.itemId}'
																data-value='${itemsList.itemName}'
																data-id='${itemsList.itemName}'>${itemsList.itemName}</option>
														</c:forEach>
													</datalist></td>
												<td>&nbsp;&nbsp;<input type="text" name="rateTdVal1"
													id="rateTdVal1" value="00" oninput="onRateChange(this.value)"
													style="width: 65px; border-radius: 18px; text-align: center;"  />
												</td>
											</tr>
										</table>
									</td>
								</tr>

							</table>
						</div>
					</div>
					<input name="rate1" id="rate1" type="hidden" value="00" />
					<div class="row">
						<div class="col-md-12">
							<center>
								<button class="btn btn-primary" onclick="insertItem1()"
									disabled="disabled" id="insertItemButton">Submit Item</button>
								<button style="float: right; margin-top: 13px;" type="button"
									class="btn btn-primary" onclick="printExBill()" disabled
									id="printExBill">Print</button>
								&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <label id="itemNameForZeroMrp"></label>
							</center>
							<div align="center" id="loader11"
								style="display: none; color: BLUE; font-size: 20px;">

								Loading....</div>
						</div>

						<hr></hr>

					</div>
					<!-- Form End -->
					<div class="table-responsive">
						<div class="shInnerwidth">

							<table class="table table-bordered" width="100%" border="0"
								cellspacing="0" cellpadding="0 " id="table_grid1">
								<tr class="bgpink">
									<th>PRINT</th>
									<th>Sr.</th>
									<th>DETAIL ID</th>
									<th>BARCODE</th>
									<th>ITEM NAME</th>
									<th style="width: 130px;">QTY</th>
									<th>RATE</th>
									<th>AMT</th>
									<th>ACTION</th>
								</tr>
								<c:forEach items="${sellBillDetails}" var="sellBillDetails"
									varStatus="count">
									<tr>
										<td><input type="checkbox" name="select_to_print"
											onchange="selectToPrint()" style="width: 25px; height: 25px;"
											id="select_to_print"
											value="${sellBillDetails.sellBillDetailNo}"></td>


										<td><c:out value="${listSize}" /></td>
										<c:set value="${listSize-1}" var="listSize"></c:set>
										<td><c:out value="${sellBillDetails.sellBillDetailNo}" /></td>
										<td><c:out value="${sellBillDetails.itemId}" /></td>
										<td><c:out value="${sellBillDetails.itemName}" /></td>
										<td style="text-align: right;"><c:out
												value="${sellBillDetails.qty}" /></td>
										<td style="text-align: right;"><c:out
												value="${sellBillDetails.mrp}" /></td>
										<td style="text-align: right;"><fmt:formatNumber
												type="number" minFractionDigits="1" maxFractionDigits="1"
												value="${sellBillDetails.grandTotal}" /></td>

										<td style="text-align: center;"><a href="#"
											class="action_btn"
											onclick="deleteItem(${sellBillDetails.sellBillDetailNo},${sellBillDetails.qty},${sellBillDetails.itemId})"><abbr
												title="Delete"><i class="fa fa-trash"></i></abbr></a></td>
									</tr>
								</c:forEach>
							</table>
						</div>


						<!-- Loader Div -->
						<div align="center" id="loader" style="display: none">
							<span>
								<h4>
									<font color="#343690">Loading</font>
								</h4>
							</span> <span class="l-1"></span> <span class="l-2"></span> <span
								class="l-3"></span> <span class="l-4"></span> <span class="l-5"></span>
							<span class="l-6"></span>
						</div>

						<!--End of  Loader Div -->
					</div>

					<center>
						<input type="button" class="btn btn-primary"
							onclick="todaysDayClose()" value="DAY CLOSE" id="dayClose1" />
					</center>
					<!--here input para was bill No  -->
					<!-- </li> -->

					</c:when>
					<c:when test="${count ==3}">
						<li>
							<div class="row"></div> <br /> <br />
							<div class="table">
								<div class="shInnerwidth">

									<table class="table table-bordered" width="100%" border="0"
										cellspacing="0" cellpadding="0 " id="table_grid">
										<tr class="bgpink">
											<th>Bill No.</th>
											<th>Bill Date</th>
											<th style="width: 130px;">Taxable Amount</th>
											<th>Payable amount</th>
											<th>Amount</th>
											<th>Action</th>
										</tr>
										<tr>
											<td><c:out value="${sellBillHeader.sellBillNo}" /></td>
											<td><c:out value="${sellBillHeader.billDate}" /></td>
											<td><c:out value="${sellBillHeader.taxableAmt}" /></td>
											<td><c:out value="${sellBillHeader.payableAmt}" /></td>
											<td><c:out value="${sellBillHeader.grandTotal}" /></td>
											<td><input type="button" class="btn btn-primary"
												id="dayClose1" onclick="todaysDayClose()" value="DAY CLOSE"
												id="callSubmit" /> <!--here input para was bill No  --></td>
										</tr>
									</table>
								</div>
							</div>
						</li>

						<li>

							<div class="table-responsive">
								<div class="shInnerwidth">

									<table class="table table-bordered" width="100%" border="0"
										cellspacing="0" cellpadding="0 " id="table_grid">
										<tr class="bgpink">

											<th>Sr.No.</th>
											<th>Detail Id</th>
											<th>Barcode</th>
											<th>Item Name</th>
											<th style="width: 130px;">Qty</th>
											<th>Rate</th>
											<th>Amount</th>
										</tr>

										<c:forEach items="${sellBillDetails}" var="sellBillDetails"
											varStatus="count">
											<tr>
												<%--  <td><input type="checkbox"  style="width:28px;height:28px;" name="select_to_print" onchange="selectToPrint()"
																id="select_to_print"
																value="${sellBillDetails.sellBillDetailNo}" ></td> --%>
												<td><c:out value="${listSize}" /></td>
												<c:set value="${listSize-1}" var="listSize"></c:set>
												<td><c:out value="${sellBillDetails.sellBillNo}" /></td>
												<td><c:out value="${sellBillDetails.itemId}" /></td>
												<td><c:out value="${sellBillDetails.itemName}" /></td>
												<td style="text-align: right;"><c:out
														value="${sellBillDetails.qty}" /></td>
												<td style="text-align: right;"><c:out
														value="${sellBillDetails.mrp}" /></td>
												<td style="text-align: right;"><fmt:formatNumber
														type="number" minFractionDigits="1" maxFractionDigits="1"
														value="${sellBillDetails.grandTotal}" /></td>
											</tr>
										</c:forEach>
									</table>
								</div>
							</div>
						</li>

					</c:when>
					<c:otherwise>

					</c:otherwise>
					</c:choose>

					<li class="selected">
						<div class="row" id="loadmsg"
							style="display: none; text-align: center; color: blue;">
							<b>Please wait...</b>
						</div> <br /> <br />
						<div class="table" id="div1" style="display: none;">
							<div class="shInnerwidth">
								<table width="100%" border="0" cellspacing="0" cellpadding="0"
									class="table">
									<tr>
										<td align="center" valign="middle" style="padding: 0px;">
											<table width="100%" border="0" cellspacing="0"
												cellpadding="0">
												<tr class="bgpink">
													<td>Baarcode</td>
													<td>Item Name</td>
													<td>Qty</td>
													<td>Rate</td>
												</tr>
												<tr>
													<td><input type='text' class="form-control"
														onkeypress='onInput()' min='0' max='500'
														placeholder="Enter Barcode" id='input' list='dlist' /> <datalist
															id='dlist'>
															<c:forEach items="${itemsList}" var="itemsList">
																<option value="${itemsList.itemId}"><c:out
																		value="${itemsList.itemId}" /></option>
															</c:forEach>
														</datalist></td>

													<td><select class="selectpicker"
														data-show-subtext="true" data-live-search="true"
														data-placeholder="Enter Item Name" name="itemName"
														class="form-control" tabindex="-1" id="itemName"
														onchange="onSelectItem()" data-rule-required="true">
															<option value="">Item Name</option>
															<c:forEach items="${itemsList}" var="itemsList">
																<option value="${itemsList.itemId}">${itemsList.itemName}</option>
															</c:forEach>
													</select></td>
													<td><input type="number" min="1" max="500"
														class="form-control" placeholder="1" name="qty1" id="qty1"
														onkeypress="onQty(event)" value="1"></td>
													<td>&nbsp;&nbsp;<input type="text" name="rateTdVal1"
														id="rateTdVal1" value="00"
														style="width: 65px; border-radius: 18px; text-align: center;" oninput="onRateChange(this.value)" /></td>
												</tr>
											</table>
										</td>
									</tr>

								</table>

							</div>

						</div> <input name="rate1" id="rate1" type="hidden" value="00" />
						<div class="row">
							<div class="col-md-12">
								<center>
									<button class="btn btn-primary" onclick="insertItem1()"
										disabled="disabled" style="display: none;"
										id="insertItemButton">Submit Item</button>
									<!-- <button style="float: right;margin-top: 13px;" type="button" class="btn btn-primary"
						onclick="printExBill()" disabled id="printExBill">Print</button> -->
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<label id="itemNameForZeroMrp"></label>
								</center>



							</div>

						</div> <br /> <!-- Form End -->
						<div class="table-responsive" style="display: none;" id="div2">
							<div class="shInnerwidth">

								<table class="table table-bordered" width="100%" border="0"
									cellspacing="0" cellpadding="0 " id="table_grid">
									<tr class="bgpink">
										<th style="width: 130px;">Sr no.</th>
										<th>Barcode</th>
										<th>Item Name</th>
										<th style="width: 130px;">Qty</th>
										<th>Rate</th>
										<th>Amount</th>
										<th>Action</th>
									</tr>
									<tr>

									</tr>
								</table>
							</div>
						</div>
						<hr /> <br />
						<center>
							<button class="btn additem_btn" onclick="todaysDayClose()"
								style="display: none;" id="dayClose1">DAY CLOSE</button>
						</center>
						<center>
							<input type="submit" class="btn btn-primary"
								onclick="todaysDayClose()" style="display: none;" id="dayClose1"
								value="DAY CLOSE">
						</center>



					</li>
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

	<!--easyTabs-->
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
	<!--easyTabs-->
	<script type="text/javascript">
function start(){
	$("#loadmsg").show();
    //$("#div1").show();
    //$("#div2").show();
   // $("#insertItemButton").show();
    //$("#dayClose1").show();
    
   // $("#insertItemButton").show();
    $("#loader").show();

  //  alert("started");
    
    $.getJSON('${insertHeader}',{

    	ajax : 'true',

    }, function(data) {
		window.location.reload();
	    $("#loader").hide();


	});
}

function  hideMe(startId){
	 $("#"+startId).hide();
	// $("#insertItemButton").show();
	 //window.location.reload();
	
}

</script>
<script type="text/javascript">
function onRateChange(rate)
{
   if(rate>0)
	   {
		document.getElementById("insertItemButton").disabled = false;

	   }else
	   {
			document.getElementById("insertItemButton").disabled = true;

	   }
}
</script>
	<script type="text/javascript">
	function onInput() {
		
	    var val = document.getElementById("input").value;
	    
	   // alert("Barcode "+val);
	 
	    $.getJSON('${getItemDetails}',{
			
            //itemId : JSON.stringify(val),
			 itemId : val,
			ajax : 'true',

		}, function(data) {
			//alert("name="+data.itemName);
			
			
			$('#itemName').selectpicker('val',''+data.itemId);

			document.getElementById("rateTdVal1").value=data.mrp;
			document.getElementById("itemNameForZeroMrp").innerText=data.itemName;
			//$('#insertItemButton').focus();
			
			// 
			 if(data.mrp>0 ){
				 insertItem1();
			 }
			 else if(val!="" )
				 {
				  alert("Please Enter Rate");
				 }
			 $('#input').focus();
		});
		//$('#insertItemButton').focus();
	    $('#input').focus();
	  }
	
	function onSelectItem() {
 
		var iId=document.getElementById("itemName").value;
	  
	    	val=iId;
	    	
	    //alert("iID " +iId);
		  
	    $.getJSON('${getItemDetails}',{
	  
           // itemId : JSON.stringify(val),
           
           itemId : val,
			ajax : 'true',

		}, function(data) {

		//$('#itemName').val(data.itemId).prop('selected', true);
			$('#itemName').selectpicker('val',''+data.itemId);
		document.getElementById("input").value=val;
			
		document.getElementById("rateTdVal1").value=data.mrp;
		document.getElementById("itemNameForZeroMrp").innerText=data.itemName;
		
			//$('#insertItemButton').focus();
			 $('#input').focus();
			 
			 if(data.mrp>0){
				insertItem1();
			 } else if(iId!="")
			 {
				  alert("Please Enter Rate");
				 
			 }
		});
		
	}
	</script>
	<script type="text/javascript">
	function insertItem1() {
		
		document.getElementById("insertItemButton").disabled = false;
		
		$('#loader11').show();
		var itemname=$("#items option[value='" + $('#itemName').val() + "']").attr('data-id');
		$('#loader11').html('Adding '+itemname);

	    var val = document.getElementById("input").value;
	    var qty = document.getElementById("qty1").value;
	    var mrp = document.getElementById("rateTdVal1").value;
	    if(parseInt(qty)>0){

	    $('#qty1').focus();
	    
	    $.getJSON('${calcStock}',{

	    	itemId : val,
            qty:qty,
			ajax : 'true',

	}, function(data) {
		if(data<qty)
			{
			$('#loader11').hide();
			$('#loader11').html('Loading ...');
			alert("Stock Not Available. Can not place Item ");
			document.getElementById("input").value="";
			
			 $('#qty1').focus();
			 
			}
		else
	    {
		

		 $.getJSON('${insertItemOrder}',{
				

	            itemId : val,
	            qty:qty,
	            mrp:mrp,
				ajax : 'true',

			}, function(data) {

				document.getElementById("rateTdVal1").value=00;
				document.getElementById("itemName").value="";

				$('#loader11').hide();
				var len = data.length;

				$('#table_grid1 td').remove();

				$.each(data,function(key, item) {
					key=len;
				var tr = $('<tr></tr>');
				tr.append($('<td ></td>').html(' <input type="checkbox" style="width:25px;height:25px;"  onchange="selectToPrint()" name="select_to_print" id="select_to_print" value="'+item.sellBillDetailNo+'" >'));
			
					//tr.append($('<td></td>').html(key));
					
					tr.append($('<td></td>').html(len));
						
						len=len-1;

				tr.append($('<td></td>').html(item.sellBillDetailNo));
				
			  	tr.append($('<td></td>').html(item.itemId));

			  	tr.append($('<td></td>').html(item.itemName));

				tr.append($('<td style="text-align: right;"></td>').html(item.qty));

				tr.append($('<td style="text-align: right;"></td>').html(item.mrp));

				tr.append($('<td style="text-align: right;"></td>').html((item.grandTotal).toFixed(1)));
				
				//tr.append($('<td ><input type="button" id="deleteButton" onclick="deleteItem('+item.sellBillDetailNo+')" /><i class='fa fa-trash'></i></td>'));

				//tr.append($('<td ><a href="#" >< i class="fa fa-trash" id="deleteButton" onclick="deleteItem('+item.sellBillDetailNo+')" ></i></a></td>'));

				
				tr.append($('<td style="text-align:center;"><a href="#" class="action_btn" onclick="deleteItem('+item.sellBillDetailNo+','+item.qty+','+item.itemId+')"><abbr title="Delete"><i class="fa fa-trash"></i></abbr></a></td>'));
				$('#table_grid1 tbody').append(tr);

			});
				 if(document.getElementById("id").checked){
					 
					// alert("print");
					 window.open("${pageContext.request.contextPath}/printExBill");
				 }
	         });
		
	}
	});
	    document.getElementById("input").value="";

		//$('#itemName').selectpicker('val',''+"");
				document.getElementById("itemName").value="";
				document.getElementById("itemNameForZeroMrp").innerText="";
		 $('#qty1').focus();
	}//if 
	else{
		alert("Please Enter Atleast One Quantity");
		$('#loader11').hide();
		document.getElementById("itemName").value="";

	   document.getElementById("qty1").value="1";
		//$('#itemName').selectpicker('val',''+"");
		document.getElementById("input").value="";

	}

	}
		
	</script>

	<script type="text/javascript">
function myFunction1() {
	
	 $('#itemName').focus();
	 var x = document.getElementById("itemName").length;

	 document.getElementById("itemName").size = x;
}
</script>


	<script type="text/javascript">
	function todaysDayClose(){
			//alert("Hi ");
			 document.getElementById("dayClose1").disabled = true;
			 $('#loader11').show();

							$.getJSON('${dayClose}',{

								ajax : 'true',
							 },
							 function(data) {
								 //alert(data);
								 
								 window.location.reload();
								 
			});
			$('#loader11').hide();
		//	alert("Hi End  ");
	}
	
	</script>




	<!-- <script type="text/javascript">
	function todaysDayClose(){
			alert("Hi ");
							$('#loader11').show();

			$.ajax({

				type : "get",
				url : "dayClose", 
			
				 complete: function() {
					
					 window.location.reload();
				 }
			});
			$('#loader11').hide();
			alert("Hi End  ");
	}
	
	</script> -->

	<script type="text/javascript">
	function deleteItem(sellBillDetailNo,qty,id){
		
		$('#loader').show();

		//alert("Hello");
			var billNo=sellBillDetailNo;
			
			 $.getJSON('${deleteItem}',{

				sellBillDetailNo:billNo,
				qty:qty,
				id:id,
				type : "get",
			

			 },
			 function(data) {
					//$('#loader').hide();


					$('#loader').hide();
					var len = data.length;

					$('#table_grid1 td').remove();

					$.each(data,function(key, item) {

					var tr = $('<tr></tr>');
					
					 
						
						tr.append($('<td></td>').html(' <input type="checkbox" style="width:25px;height:25px;" onchange="selectToPrint()" name="select_to_print" id="select_to_print" value="'+item.sellBillDetailNo+'" >'));
						
						//tr.append($('<td></td>').html(key+1));
						
						tr.append($('<td></td>').html(len));
						
						len=len-1;
						
						tr.append($('<td></td>').html(item.sellBillDetailNo));
						
				  	tr.append($('<td></td>').html(item.itemId));

				  	tr.append($('<td></td>').html(item.itemName));

					tr.append($('<td style="text-align: right;"></td>').html(item.qty));

					tr.append($('<td style="text-align: right;"></td>').html(item.mrp));

					tr.append($('<td style="text-align: right;"></td>').html((item.grandTotal).toFixed(1)));
					
				//	tr.append($('<td ><input type="button" id="deleteButton" onclick="deleteItem('+item.sellBillDetailNo+')" value="Delete" /></td>'));
				    tr.append($('<td style="text-align:center;"><a href="#" class="action_btn" onclick="deleteItem('+item.sellBillDetailNo+','+item.qty+','+item.itemId+')"><abbr title="Delete"><i class="fa fa-trash"></i></abbr></a></td>'));


					$('#table_grid1 tbody').append(tr);

				});
			
		         });
		}
	
	
	function printExBill()
	{
		//alert("in print");
		
		var checkedId=[];
		var checkboxes=document.getElementsByName("select_to_print");
		
		 
			for (var i = 0, n = checkboxes.length; i < n; i++) {
				if(checkboxes[i].checked) {
					
					checkedId.push(checkboxes[i].value );
					
				}
			}
			if(checkedId.length>0){
			//alert(checkboxes);
			 $.getJSON('${getSelectedIdForPrint}',{

					id :  JSON.stringify(checkedId),
					ajax : 'true',
				

				 });
				  
		window.open("${pageContext.request.contextPath}/printSelectedOrder");
			}
			else
				{
				 alert("Please Select atleast one item!!")
				}
	}
	
	function selectToPrint()
	{
		//alert("hh");
	 
		var checkboxes=document.getElementsByName("select_to_print");
		//alert(checkboxes[0].value);
		var flag=0;
			for (var i = 0, n = checkboxes.length; i < n; i++) {
				if(checkboxes[i].checked) {
					 
					flag=1;
				}
			}
		if(flag==1)
			{
		 //alert("KK");
			document.getElementById("printExBill").disabled=false;
			}
		else{
		document.getElementById("printExBill").disabled=true;
		}
		
		 
	}
	
	function onQty(event) {
		var x = event.which || event.keyCode;
		if (x == 13) {
			//document.getElementById("b" + token).focus();
			
			   document.getElementById("qty1").value="1";

		}
	}
	</script>
	<script>

(function() {
  var fauxTable = document.getElementById("faux-table");
  var mainTable = document.getElementById("table_history");
  var clonedElement = table_grid.cloneNode(true);
  var clonedElement2 = table_grid.cloneNode(true);
  clonedElement.id = "";
  clonedElement2.id = "";
  fauxTable.appendChild(clonedElement);
  fauxTable.appendChild(clonedElement2);
});

$('#sp').change(function() {
	   if($(this).is(":checked")) {
		   $("#tp").show();
		   document.getElementById("type").value="";
		   $("#orderHistory").hide();
	      return;
	   }
	   else
		   {
		   $("#tp").hide();
		   $("#byOrderNo").hide();
		   $("#byDate").hide();
		   $("#orderHistory").hide();
		   return;
		   }
	   //'unchecked' event code
	});
	
	function onchangetype()
	{
		var selectedValue = document.getElementById("type").value;

		if(selectedValue==1)
		{
			  $("#byDate").show();
			  $("#byOrderNo").hide();
			  $("#orderHistory").show();
				$('#table_history td').remove();
				document.getElementById("group").value="";
		}
		else if(selectedValue==2)
			{
			  $("#byOrderNo").show();
			  $("#byDate").hide();
			  $("#orderHistory").show();
				$('#table_history td').remove();
				document.getElementById("group").value="";
				document.getElementById("orderno").value="";
			}
		
	}
	</script>
	<script type="text/javascript">
	function searchOrders()
	{ 
		
		$('#table_history td').remove();
		on();
			var date = document.getElementById("todatepicker").value;
			var group=document.getElementById("group").value;
			
			$.getJSON('${getSpOrders}',{
				
				date : date,
				group:group,
				ajax : 'true',

							},
							function(data) {
								if (data == "") {
									off();
									alert("No records found !!");
								}
								off();
								$.each(data.spCakeOrder,function(key, order) {

								
									var tr = $('<tr></tr>');
									tr.append($('<td class="col-md-1"></td>').html("SP"));
									tr.append($('<td class="col-md-1"></td>').html(order.spDeliveryPlace));
								  	tr.append($('<td class="col-md-1"></td>').html(order.spName));
									tr.append($('<td class="col-md-1"></td>').html(order.spfName));	
									tr.append($('<td class="col-md-1"></td>').html("NA"));
									var price=parseFloat(order.spGrandTotal-order.spTotalAddRate);
									tr.append($('<td class="col-md-1"></td>').html(order.spDeliveryDate));	
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(price));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.spTotalAddRate));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.spGrandTotal));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.spAdvance));
									tr.append($('<td class="col-md-1"></td>').html("&nbsp;&nbsp;&nbsp;&nbsp;<a href='${pageContext.request.contextPath}/showSpCakeOrderHisPDF/"+order.spOrderNo+"' target='_blank' >	<abbr title='Order Memo'><i class='fa fa-file-pdf-o'></i></abbr></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='${pageContext.request.contextPath}/printSpCkBill/"+order.spOrderNo+"' target='_blank'><abbr title='Bill'><i class='fa fa-file-pdf-o'></i></abbr></a>"));
									$('#table_history tbody').append(tr);


												})
									$.each(data.regularSpCkOrders,function(key, order) {

								
									var tr = $('<tr></tr>');
									tr.append($('<td class="col-md-1"></td>').html("Reg SP"));
									tr.append($('<td class="col-md-1"></td>').html(order.rspPlace));

								  	tr.append($('<td class="col-md-1"></td>').html(order.itemName));
									tr.append($('<td class="col-md-1"></td>').html("NA"));
									tr.append($('<td class="col-md-1"></td>').html(order.qty));	
									tr.append($('<td class="col-md-1"></td>').html(order.rspDeliveryDt));	
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.rate));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(0));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.rspSubTotal));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.rspAdvanceAmt));
									tr.append($('<td class="col-md-1"></td>').html("&nbsp;&nbsp;&nbsp;&nbsp;<a href='${pageContext.request.contextPath}/showRegCakeOrderHisPDF/"+order.rspId+"' target='_blank' >	<abbr title='Order Memo'><i class='fa fa-file-pdf-o'></i></abbr></a>"));
									$('#table_history tbody').append(tr);


												})		
								
								
							});

		
	}
	function searchOrder()
	{ 
		
		$('#table_history td').remove();
		on();
			var orderno = document.getElementById("orderno").value;
			
			$.getJSON('${getSpOrder}',{
				
				orderno : orderno,
				ajax : 'true',

							},
							function(data) {
								if (data == null) {
									off();
									alert("No records found !!");
								}
							
								off();
								$.each(data.spCakeOrder,function(key, order) {
									var tr = $('<tr></tr>');
									tr.append($('<td class="col-md-1"></td>').html("SP"));
									tr.append($('<td class="col-md-1"></td>').html(order.spDeliveryPlace));
								  	tr.append($('<td class="col-md-1"></td>').html(order.spName));
									tr.append($('<td class="col-md-1"></td>').html(order.spfName));	
									tr.append($('<td class="col-md-1"></td>').html("NA"));
									var price=parseFloat(order.spGrandTotal-order.spTotalAddRate);
									tr.append($('<td class="col-md-1"></td>').html(order.spDeliveryDate));	
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(price));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.spTotalAddRate));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.spGrandTotal));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.spAdvance));
									tr.append($('<td class="col-md-1"></td>').html("&nbsp;&nbsp;&nbsp;&nbsp;<a href='${pageContext.request.contextPath}/showSpCakeOrderHisPDF/"+order.spOrderNo+"' target='_blank' >	<abbr title='Order Memo'><i class='fa fa-file-pdf-o'></i></abbr></a>&nbsp;&nbsp;&nbsp;&nbsp;<a href='${pageContext.request.contextPath}/printSpCkBill/"+order.spOrderNo+"' target='_blank'><abbr title='Bill'><i class='fa fa-file-pdf-o'></i></abbr></a>"));
									$('#table_history tbody').append(tr);
								  
								})		
								$.each(data.regularSpCkOrders,function(key, order) {

									
									var tr = $('<tr></tr>');
									tr.append($('<td class="col-md-1"></td>').html("Reg SP"));
									tr.append($('<td class="col-md-1"></td>').html(order.rspPlace));

								  	tr.append($('<td class="col-md-1"></td>').html(order.itemName));
									tr.append($('<td class="col-md-1"></td>').html("NA"));	
									tr.append($('<td class="col-md-1"></td>').html(order.qty));	

									tr.append($('<td class="col-md-1"></td>').html(order.rspDeliveryDt));	
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.rate));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(0));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.rspSubTotal));
									tr.append($('<td class="col-md-1" style="text-align:right;"></td>').html(order.rspAdvanceAmt));
									tr.append($('<td class="col-md-1"></td>').html("&nbsp;&nbsp;&nbsp;&nbsp;<a href='${pageContext.request.contextPath}/showRegCakeOrderHisPDF/"+order.rspId+"' target='_blank' >	<abbr title='Order Memo'><i class='fa fa-file-pdf-o'></i></abbr></a>"));
									$('#table_history tbody').append(tr);


												})					
											
								
								
							});

		
	}
	</script>
	<script type="text/javascript">
/* 	$(".table tbody tr").click(function(e) {
	    if($(e.target).is(':checkbox')) return; //ignore when click on the checkbox
	    document.getElementById("printExBill").disabled=false;
	    var $cb = $(this).find(':checkbox');
	    $cb.prop('checked', !$cb.is(':checked'));
	  
	}); */
	
/* 	  if(this.style.background == "" || this.style.background =="white") {
          $(this).css('background', 'lightpink');
          
      }
      else {
          $(this).css('background', 'white');
      } */
	</script>
	<script>
function on() {
    document.getElementById("overlay").style.display = "block";
}

function off() {
    document.getElementById("overlay").style.display = "none";
}
</script>
</body>
</html>
