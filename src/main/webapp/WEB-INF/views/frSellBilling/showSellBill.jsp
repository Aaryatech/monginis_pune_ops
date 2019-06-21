<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/WEB-INF/views/include/header.jsp"/>


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
<link href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${pageContext.request.contextPath}/resources/css/custom.css" rel="stylesheet" type="text/css"/>	
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">	
<link rel="icon"
	href="${pageContext.request.contextPath}/resources/images/feviconicon.png"
	type="image/x-icon" />
	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>	
	
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>

<!--rightNav-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/menuzord.js"></script>
	
<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery("#menuzord").menuzord({
		align:"left"
	});
});
</script>
<!--rightNav-->



</head> --%>

<!--datepicker-->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
  $( function() {
    $( "#todatepicker" ).datepicker({ dateFormat: 'dd-mm-yy' });
  } );
  $( function() {
    $( "#fromdatepicker" ).datepicker({ dateFormat: 'dd-mm-yy' });
  } );
 
  </script>
<!--datepicker--> 
<body onload="return searchSellBill()">

<c:url var="getSellBillHeader" value="/getSellBillHeader" />
	
	<div class="sidebarOuter"></div>
	
	<div class="wrapper">

		<!--topHeader-->

		<jsp:include page="/WEB-INF/views/include/logo.jsp">
			<jsp:param name="frDetails" value="${frDetails}" />

		</jsp:include>

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
				
				<!-- Place Actual content of page inside this div -->
				<div class="sidebarright">
				

<div class="row">
	    <div class="col-md-12"><h2 class="pageTitle">View Bills</h2></div>
	</div>
	 <%String frmDate = session.getAttribute("fromSellBillDate").toString();
	 String tDate = session.getAttribute("toSellBillDate").toString();%>
	<div class="row">
	
		<div class="col-md-2 from_date">
		    <h4 class="pull-left">From Date:-</h4>
		</div>
		<div class="col-md-2 ">
			<input id="fromdatepicker" class="texboxitemcode texboxcal" placeholder="DD-MM-YYYY" name="fromDate" type="text" value="<%=frmDate%>">
		</div>
		<div class="col-md-2">
		    <h4 class="pull-left">To Date:-</h4>
		</div>
		<div class="col-md-2 ">
			<input id="todatepicker" class="texboxitemcode texboxcal" placeholder="DD-MM-YYYY" name="toDate" type="text" value="<%=tDate%>">
		</div>
		<div class="col-md-2">
		    <button class="btn search_btn pull-left" onclick="searchSellBill()">Search </button>
		</div>
		
    </div>
	
	<div class="row">
		<div class="col-md-12">
		<!--table-->
			<div class="clearfix"></div>


				<div id="table-scroll" class="table-scroll">
					<div id="faux-table" class="faux-table" aria="hidden">
				<table width="100%" border="1" cellspacing="0"
														cellpadding="1" id="table_grid"  class="main-table">
								<thead>	<tr class="bgpink">
									<th style="text-align:center;">Sr No</th>
										<th style="text-align:center;">Invoice No</th>
									<th style="text-align:center;">Bill Date</th>
										<th style="text-align:center;">Grand Total</th>
									<th style="text-align:center;">Payable Amount</th>
									<th style="text-align:center;">Paid Amount</th>
									<th style="text-align:center;">Paymode</th>
									<th style="text-align:center;">Action</th>
								  </tr>
								</thead>
								  <tbody >
								
								</tbody></table>
					</div>
					<div class="table-wrap">
					
								<table width="100%" border="1" cellspacing="0"
														cellpadding="1" id="table_grid"  class="main-table">
								<thead>	<tr class="bgpink">
									<th style="text-align:center;">Sr No</th>
										<th style="text-align:center;" >Invoice No</th>
									<th style="text-align:center;">Bill Date</th>
										<th style="text-align:center;">Grand Total</th>
									<th style="text-align:center;">Payable Amount</th>
									<th style="text-align:center;">Paid Amount</th>
									<th style="text-align:center;">Paymode</th>
									<th style="text-align:center;">Action</th>
								  </tr>
								</thead>
								  <tbody >
								
								</tbody>
								  
								</table>
						
				</div>
			</div></div>
		<!--table end-->
		 
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
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
	<!--easyTabs-->

	
	<script type="text/javascript">
	function searchSellBill()
	{ 
		$('#table_grid td').remove();
		var isValid = validate();
		
		if (isValid) {
			
			var fromDate = document.getElementById("fromdatepicker").value;
			var toDate = document.getElementById("todatepicker").value;
			   
			
			$.getJSON('${getSellBillHeader}',{
				
								fromDate : fromDate,
								toDate : toDate,
								ajax : 'true',

							},
							function(data) {

								//$('#table_grid td').remove();
								
								

								if (data == "") {
									alert("No records found !!");

								}
								//alert(data);

								
								$.each(data,function(key, sellBillData) {

													var index = key + 1;

													var tr = $('<tr></tr>');
													tr.append($('<td></td>').html(key+1));
													tr.append($('<td></td>').html(sellBillData.invoiceNo));
													tr.append($('<td></td>').html(sellBillData.billDate));
													tr.append($('<td style="text-align:right;"></td>').html((sellBillData.grandTotal).toFixed(2)));
													tr.append($('<td style="text-align:right;"></td>').html((sellBillData.payableAmt).toFixed(2)));
													tr.append($('<td style="text-align:right;"></td>').html((sellBillData.paidAmt).toFixed(2)));
													var payMode="";
													if(sellBillData.paymentMode==1)
													payMode="Cash";
													else if(sellBillData.paymentMode==2)
													payMode="Card";	
													else if(sellBillData.paymentMode==3)
													payMode="Other";	
													
													tr.append($('<td style="text-align:center;"></td>').html(payMode));
													
													 if(sellBillData.billType=='S'){
														tr.append($(' <td style="text-align:center;"></td>').html('SP &nbsp; <a href="" onclick="return custBillPdf('+sellBillData.sellBillNo+',\'' + sellBillData.billType + '\');"><abbr title="PDF"><i class="fa fa-file-pdf-o"></i></abbr></a> '));
	
													}else{ 
													tr.append($('<td style="text-align:center;"></td>').html("<a href=${pageContext.request.contextPath}/viewBillDetails?sellBillNo="+ sellBillData.sellBillNo+'&billDate='+sellBillData.billDate+' class="action_btn" name='+'><abbr title="Details"><i class="fa fa-list"></i></abbr></a> &nbsp;&nbsp;<a href=${pageContext.request.contextPath}/editBillDetails?sellBillNo='+ sellBillData.sellBillNo+'&billDate='+sellBillData.billDate+' class="action_btn" name='+'><abbr title="edit"><i class="fa fa-edit"></i></abbr></a>&nbsp;&nbsp; <a href=""onclick="return custBillPdf('+sellBillData.sellBillNo+',\'' + sellBillData.billType + '\');"><abbr title="PDF"><i class="fa fa-file-pdf-o"></i></abbr></a> '));


													}
													
													$('#table_grid tbody').append(tr);
													/* var tr = "<tr>";

													

													var sellBillNo = "<td>&nbsp;&nbsp;&nbsp;"
															+ sellBillData.sellBillNo
															+ "</td>";
															var invoiceNo = "<td>&nbsp;&nbsp;&nbsp;"
																+ sellBillData.invoiceNo
																+ "</td>";
																var billDate = "<td>&nbsp;&nbsp;&nbsp;"
																	+ sellBillData.billDate
																	+ "</td>";

																	var grandTotal = "<td style='text-align:right;'>&nbsp;&nbsp;&nbsp;"
																		+ (sellBillData.grandTotal).toFixed(2)
																		+ "</td>";

																		var PayableAmt = "<td style='text-align:right;'>&nbsp;&nbsp;&nbsp;"
						 													+ (sellBillData.payableAmt).toFixed(2)
						 													+ "</td>";
																			
						  													var paidAmt = "<td style='text-align:right;'>&nbsp;&nbsp;&nbsp;"
																				+ (sellBillData.paidAmt).toFixed(2)
																				+ "</td>";
                                                                            
																		   
																			var paymentMode = "<td>&nbsp;&nbsp;&nbsp;"
																				+ sellBillData.paymentMode
																				+ "</td>";
																				
																				var viewBill = '<td>&nbsp;&nbsp;&nbsp;'
																				+'<a href="${pageContext.request.contextPath}/viewBillDetails?sellBillNo='+ sellBillData.sellBillNo+'&billDate='+sellBillData.billDate+'" class="action_btn" name='+'><abbr title="Details"><i class="fa fa-list"></i></abbr></a>'
																				+ "</td>";



													

													var trclosed = "</tr>";

													$('#table_grid tbody')
															.append(tr);
													$('#table_grid tbody')
															.append(sellBillNo);
													$('#table_grid tbody')
													.append(invoiceNo);
													$('#table_grid tbody')
													.append(billDate);
													$('#table_grid tbody')
													.append(grandTotal);
													$('#table_grid tbody')
													.append(PayableAmt);
													$('#table_grid tbody')
													.append(paidAmt);
													
													$('#table_grid tbody')
													.append(paymentMode);
													
													$('#table_grid tbody')
													.append(viewBill);
													
													$('#table_grid tbody')
													.append(trclosed); */
													
													

												})
													

							});

		}
	}
	
	function custBillPdf(sellBillNo,type)
	{
		    var loginWindow = window.open('', 'UserLogin');
			if(type=='S')
				{
				loginWindow.location.href = '${pageContext.request.contextPath}/printSpCkBillPrint/'+sellBillNo;
				}else{
		         loginWindow.location.href = '${pageContext.request.contextPath}/pdfSellBill?billNo='+ sellBillNo+'&type='+type;
				}
	}
	</script>
	<script type="text/javascript">
	function validate() {
	
	
		var fromDate =$("#fromdatepicker").val();
		var toDate =$("#todatepicker").val();
		

		var isValid = true;

	 if (fromDate == "" || fromDate == null) {

			isValid = false;
			alert("Please select From Date");
		}
	 else if (toDate == "" || toDate == null) {

			isValid = false;
			alert("Please select To Date");
		}
		return isValid;

	}
	
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
