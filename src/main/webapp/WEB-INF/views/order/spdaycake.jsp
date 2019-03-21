<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>

<!--datepicker-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
$(function() {

		var todaysDate = new Date();
		var min = new Date(todaysDate.setDate(todaysDate.getDate()));

		$("#datepicker").datepicker({
			dateFormat : 'dd-mm-yy',
			//minDate : min,
			//maxDate: min
		});
	});
	$(function() {
		$("#datepicker2").datepicker({
			dateFormat : 'dd-mm-yy'
		});
		
});

</script>
<!--selectlistbox-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.selectlistbox.js"></script>
	
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<title>Monginis</title>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>


<!--selectlistbox-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.selectlistbox.js"></script>
<script>
function createByJson() {
	var jsonData = [					
					{description:'Choos your payment gateway', value:'', text:'Payment Gateway'},					
					{image:'${pageContext.request.contextPath}/resources/images/msdropdown/icons/Amex-56.png', description:'My life. My card...', value:'amex', text:'Amex'},
					{image:'${pageContext.request.contextPath}/resources/images/msdropdown/icons/Discover-56.png', description:'It pays to Discover...', value:'Discover', text:'Discover'},
					{image:'${pageContext.request.contextPath}/resources/images/msdropdown/icons/Mastercard-56.png', title:'For everything else...', description:'For everything else...', value:'Mastercard', text:'Mastercard'},
					{image:'${pageContext.request.contextPath}/resources/images/msdropdown/icons/Cash-56.png', description:'Sorry not available...', value:'cash', text:'Cash on devlivery', disabled:true},
					{image:'${pageContext.request.contextPath}/resources/images/msdropdown/icons/Visa-56.png', description:'All you need...', value:'Visa', text:'Visa'},
					{image:'${pageContext.request.contextPath}/resources/images/msdropdown/icons/Paypal-56.png', description:'Pay and get paid...', value:'Paypal', text:'Paypal'}
					];
	$("#byjson").msDropDown({byJson:{data:jsonData, name:'payments2'}}).data("dd");
}
$(document).ready(function(e) {		
	//no use
	try {
		var pages = $("#pages").msDropdown({on:{change:function(data, ui) {
												var val = data.value;
												if(val!="")
													window.location = val;
											}}}).data("dd");

		var pagename = document.location.pathname.toString();
		pagename = pagename.split("/");
		pages.setIndexByValue(pagename[pagename.length-1]);
		$("#ver").html(msBeautify.version.msDropdown);
	} catch(e) {
		//console.log(e);	
	}
	
	$("#ver").html(msBeautify.version.msDropdown);
		
	//convert
	$("select").msDropdown({roundedBorder:false});
	createByJson();
	$("#tech").data("dd");
});
function showValue(h) {
	console.log(h.name, h.value);
}
$("#tech").change(function() {
	console.log("by jquery: ", this.value);
})
//
</script>
<!--selectlistbox-->
	
	
    <jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>


	<c:url var="findDelToAndFromDate" value="/getDelToAndFromDate" />

	<!--topLeft-nav-->
	<div class="sidebarOuter"></div>
	<!--topLeft-nav-->

	<!--wrapper-start-->
	<div class="wrapper">

		<!--topHeader-->
		<c:url var="findAddOnRate" value="/getAddOnRate" />
		<c:url var="findItemsByCatId" value="/getFlavourBySpfId" />
		<c:url var="findAllMenus" value="/getAllTypes" />


		<!--topHeader-->
         <jsp:include page="/WEB-INF/views/include/logo.jsp">
		
			<jsp:param name="fr" value="${frDetails}"/>
		</jsp:include>


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
						<h2 class="pageTitle">Advance Order</h2>
					</div>

					<form name="frm_search" id="frm_search" method="post"
						action="${pageContext.request.contextPath}/searchItems" onsubmit="return validate()">
						<input type="hidden" name="mod_ser" id="mod_ser"
							value="search_result">

                     <input type="hidden" id="flag" name="flag" value="1"/>
						<div class="colOuter">
							<div class="col1">
								<div class="col1title">Select Menu</div>
							</div>
							<div class="col2">
								<select name="spdayId" id="spdayId" onchange="onChangeDay()"
									required>
									<option value="-1">Select Day</option>

									<c:forEach items="${configureSpDayFrList}"
										var="configureSpDayFr">

										<c:choose>
											<c:when test="${spdayId==configureSpDayFr.spdayId}">


												<option value="${configureSpDayFr.spdayId}" selected>${configureSpDayFr.spdayName}</option>

											</c:when>

											<c:when test="${spdayId!=configureSpDayFr.spdayId}">

												<option value="${configureSpDayFr.spdayId}">${configureSpDayFr.spdayName}</option>

											</c:when>
										</c:choose>
									</c:forEach>
								</select>
							</div>
						</div>
						 <div class="col2full" id="availDate" style="text-align:center; text-color:red;">  </div>
						<div class="colOuter">
							<div class="col1">
								<div class="col1title">Delivery Date</div>
							</div>
							<div class="col2">
								<input id="datepicker111" class="texboxitemcode texboxcal"
									placeholder="Delivery Date" name="datepicker" type="text"  readonly="readonly"
									value="${delDate}" >
							</div>
							
							<input name="btn" id="btn" class="buttonsaveorder" value="Search" 
									type="submit" >
									
						</div>
                    
					<input id="fromDate" name="fromDate" type="hidden" value="${fromDate}"/>
					<input id="toDate"  name="toDate" type="hidden" value="${toDate}"/>
							
						
						
					</form>
					<form
						action="${pageContext.request.contextPath}/saveSpDayCakeOrder"
						name="form1" method="post">

					 	<!--tabNavigation-->
					<!--	<div class="cd-tabs">
							tabMenu

							tabMenu
							<ul class="cd-tabs-content">
								tab1
								<li data-content="tab1" class="selected">
									<div class="table-responsive"> 
										<div class="shInnerwidth">-->
           

											<input type="hidden" name="menuId" value="${menuId}">
											<input type="hidden" name="rateCat"
												value="${frDetails.frRateCat}">

											<c:set var="selectedMenu" scope="session"
												value="${selectedMenu}" />
											<div class="clearfix"></div>

											<div id="table-scroll" class="table-scroll">
												<div id="faux-table" class="faux-table" aria="hidden">
												<table id="table_grid3" class="main-table">
														<thead>
															<tr class="bgpink">
																<th class="col-md-1">Sr No.</th>
																<th class="col-md-3">Item Name</th>
																<th class="col-md-1">Quantity</th>
																<th class="col-md-1">MRP</th>
																<th class="col-md-1">Rate</th>
																<th class="col-md-1">Total</th>
															</tr>
														</thead>
														<tbody>
														</tbody>
														</table>
												</div>
												<div class="table-wrap">
													<table id="table_grid" class="main-table">
														<thead>
															<tr class="bgpink">
																<th class="col-md-1">Sr No.</th>
																<th class="col-md-3">Item Name</th>
																<th class="col-md-1">Quantity</th>
																<th class="col-md-1">MRP</th>
																<th class="col-md-1">Rate</th>
																<th class="col-md-1">Total</th>
															</tr>
														</thead>
														<tbody>
															<c:forEach var="items" items="${itemList}"
																varStatus="loop">

																<c:choose>
																	<c:when test="${frDetails.frRateCat=='1'}">
																		<tr>
																			<td class="col-md-1"><c:out value='${loop.index+1}' /></td>

																			<td class="col-md-1"><c:out value='${items.itemName}' /></td>
																			<td class="col-md-1"><input name='${items.id}' id='${items.id}'
																				value='${items.itemQty}' class="tableInput"
																				type="text" onkeydown="myFunction()"
																				onchange="onChange('${items.itemRate1}',${items.id})"></td>
																			<td class="col-md-1"><c:out value='${items.itemMrp1}' /></td>

																			<td class="col-md-1"><c:out value='${items.itemRate1}' /></td>
																			<c:set var="rate" value="${items.itemRate1}" />
																			<c:set var="qty" value="${items.itemQty}" />
																			<td class="col-md-1" id="total${items.id}"><c:out
																					value='${rate * qty}' /></td>
																		</tr>
																	</c:when>

																	<c:when test="${frDetails.frRateCat=='2'}">
																		<tr>
																			<td class="col-md-1"><c:out value='${loop.index+1}' /></td>

																			<td class="col-md-1"><c:out value='${items.itemName}' /></td>
																			<td class="col-md-1"><input name='${items.id}' id='${items.id}'
																				value='${items.itemQty}' class="tableInput"
																				type="text"
																				onchange="onChange('${items.itemRate2}',${items.id})"></td>
																			<td class="col-md-1"><c:out value='${items.itemMrp2}' /></td>

																			<td class="col-md-1"><c:out value='${items.itemRate2}' /></td>
																			<c:set var="rate" value="${items.itemRate2}" />
																			<c:set var="qty" value="${items.itemQty}" />
																			<td class="col-md-1" id="total${items.id}"><c:out
																					value='${rate * qty}' /></td>
																		</tr>
																	</c:when>

																	<c:when test="${frDetails.frRateCat=='3'}">
																		<tr>
																			<td class="col-md-1"><c:out value='${loop.index+1}' /></td>

																			<td class="col-md-1"><c:out value='${items.itemName}' /></td>
																			<td class="col-md-1"><input name='${items.id}' id='${items.id}'
																				value='${items.itemQty}' class="tableInput"
																				type="text"
																				onchange="onChange('${items.itemRate3}',${items.id})"></td>
																			<td class="col-md-1"><c:out value='${items.itemMrp3}' /></td>

																			<td class="col-md-1"><c:out value='${items.itemRate3}' /></td>
																			<c:set var="rate" value="${items.itemRate3}" />
																			<c:set var="qty" value="${items.itemQty}" />
																			<td class="col-md-1" id="total${items.id}"><c:out
																					value='${rate * qty}' /></td>
																		</tr>
																	</c:when>
																</c:choose>
															</c:forEach>
							</tbody>

						</table>
					</div>
				</div>
         	<!-- 	</div>
											</div>
							</li>
						
							</ul>
						</div>
						tabNavigation

						<div class="order-btn"><a href="#" class="saveOrder">SAVE ORDER</a></div> -->
						<div class="order-btn textcenter">

							<input name="saveorder123" class="buttonsaveorder" value="SAVE ORDER"
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
	<!--easyTabs-->
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
            var isValidated=validate();
            	//alert(isValidated)
            	 if(isValidated)
            		{
                form1.submit();
            		}  
            	 //form1.submit();
            	
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
		function onKeyDown(id) {
			alert("alert");
			var e = $('#'+id).val();
			if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
		             // Allow: Ctrl/cmd+A
		            (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) ||
		             // Allow: Ctrl/cmd+C
		            (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true)) ||
		             // Allow: Ctrl/cmd+X
		            (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true)) ||
		             // Allow: home, end, left, right
		            (e.keyCode >= 35 && e.keyCode <= 39)) {
		                 // let it happen, don't do anything
		                 return;
		        }
		        // Ensure that it is a number and stop the keypress
		        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105))) {
		            e.preventDefault();
		        }
		    };
</script>
	<script type="text/javascript">
$(document).ready(function() {
    $("#5").keydown(function (e) {
        // Allow: backspace, delete, tab, escape, enter and .
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110, 190]) !== -1 ||
             // Allow: Ctrl/cmd+A
            (e.keyCode == 65 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: Ctrl/cmd+C
            (e.keyCode == 67 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: Ctrl/cmd+X
            (e.keyCode == 88 && (e.ctrlKey === true || e.metaKey === true)) ||
             // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105))) {
            e.preventDefault();
        }
    });
});
</script>
<!-- 
	<script type="text/javascript">
function onChangeDay() {

	var spdayId = $('#spdayId').find(":selected").val();

	 $.getJSON(
				'${findDelToAndFromDate}',
				{
					spdayId : spdayId,
					ajax : 'true'
				},
				function(data) {
					//alert(data.deliveryFromDate);
					//alert(data.orderFromDate);

					  $( function() {
					   // $( "#datepicker" ).datepicker({ dateFormat: 'dd-mm-yy', minDate:data.orderFromDate ,maxDate:data.orderToDate}
					    	/* ); */
						  document.getElementById("availDate").innerHTML=" <strong>Delivery From Date: </strong>&nbsp;&nbsp;&nbsp;&nbsp;"+data.deliveryFromDate+", &nbsp; &nbsp; &nbsp;<strong>To Date :</strong> &nbsp;&nbsp;&nbsp;&nbsp;"+data.deliveryToDate;
						  document.getElementById("availDate").style.color = "green";
						  document.getElementById("fromDate").value=data.deliveryFromDate;
						  document.getElementById("toDate").value=data.deliveryToDate;
					  } );
				
				});
}
</script> -->
	<script type="text/javascript">
function onChangeDay() {

	var spdayId = $('#spdayId').find(":selected").val();
	document.getElementById("btn").disabled = false;
	//document.getElementById("saveorder123").disabled = false;
	
	 $.getJSON(
				'${findDelToAndFromDate}',
				{
					spdayId : spdayId,
					ajax : 'true'
				},
				function(data) {
					
					//alert(data.deliveryFromDate);
					//alert(data.orderFromDate);
				var time = new Date();
				
				
				var a=data.fromTime;
				var b=data.toTime;
				var c=data.currTime; 
				
					  $( function() {
					   // $( "#datepicker" ).datepicker({ dateFormat: 'dd-mm-yy', minDate:data.orderFromDate ,maxDate:data.orderToDate}
					    	/* ); */
						  document.getElementById("availDate").innerHTML="&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp<strong>From Time: </strong>"+data.fromTime+" &nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp <strong> To Time: </strong> "+data.toTime;
						  document.getElementById("availDate").style.color = "green";
						  document.getElementById("fromDate").value=data.deliveryFromDate;
						  document.getElementById("toDate").value=data.deliveryToDate;
						  document.getElementById("datepicker111").value=data.deliveryToDate;
						  
					  } );
					  
					  var timeA = new Date(); 
					  timeA.setHours(a.split(":")[0],a.split(":")[1],a.split(":")[2]);
					  timeB = new Date();
					  timeB.setHours(b.split(":")[0],b.split(":")[1],b.split(":")[2]); 
					  timeC = new Date();
					  timeC.setHours(c.split(":")[0],c.split(":")[1],c.split(":")[2]); 
					  
					  if(timeC>=timeA && timeC<=timeB)
						  {
						  document.getElementById("btn").disabled = false;
						  //document.getElementById("saveorder").disabled = false;
						  
						  }
					  else
						  {
						  var timeString =data.fromTime;

						  var H = +timeString.substr(0, 2);
						  var h = (H % 12) || 12;
						  var ampm = H < 12 ? "AM" : "PM";
						  timeString = h + timeString.substr(2, 3) + ampm;
						  
						  var timeString1 =data.toTime;

						  var H = +timeString1.substr(0, 2);
						  var h = (H % 12) || 12;
						  var ampm = H < 12 ? " AM" : " PM";
						  timeString1 = h + timeString1.substr(2, 3) + ampm;
						  
						  document.getElementById("btn").disabled = true;
						  //document.getElementById("saveorder").disabled = true;
                          alert("Order Time is From "+timeString+" to "+timeString1+" currently it's not available!!"); 
						  }
				
				});
}
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
});


	</script>
	<script type="text/javascript">
	function validate()
	{
		//alert("Please Select Special Day."+spdayId);
		var spdayId = $('#spdayId').find(":selected").val();
		var isValid=true;
		/* var selectedDate = $('#datepicker').val();//delivery Date
		var fromDate = $('#fromDate').val();
		var toDate = $('#toDate').val();
		
		
		var firstValue = fromDate.split('-');
		var secondValue = toDate.split('-');
		var selectedValue = selectedDate.split('-');
		
		var frDate=new Date();
		frDate.setFullYear(firstValue[0],(firstValue[1] - 1 ),firstValue[2]);
		var tDate=new Date();
		tDate.setFullYear(secondValue[0],(secondValue[1] - 1 ),secondValue[2]);
		var sDate=new Date();
		sDate.setFullYear(selectedValue[0],(selectedValue[1] - 1 ),selectedValue[2]); */

		 
		if(spdayId==-1)
			{
			  
			 alert("Please Select Menu.")
			 return false;
			}
		else
			{
			return true;
			}
		 
		/* else if(sDate<frDate || sDate>tDate)
			{
				 alert("Please Select Delivery Date Between  --"+   fromDate+"-&-"+toDate)
				 return false;

			}
		 else if(selectedDate=="")
			{
				 alert("Please Select Valid Delivery Date")
				 return false;

			} */
           return isValid;
		
	}
	
	</script>
</body>
</html>
