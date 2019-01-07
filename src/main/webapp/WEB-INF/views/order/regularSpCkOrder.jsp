<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.LocalDate,java.util.*"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
    	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>


<!--datepicker-->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
	$(function() {

		var todaysDate = new Date();
		var menuId = $("#currentMenuId").val();
		var min;
		if(menuId==80){
		 min = new Date(todaysDate.setDate(todaysDate.getDate()+2));
		}
		else
			{
			 min = new Date(todaysDate.setDate(todaysDate.getDate()+1));
			}
		$("#datepicker").datepicker({
			dateFormat : 'dd-mm-yy',
			 minDate : min
		});
	});
	$(function() {
		$("#datepicker2").datepicker({
			dateFormat : 'dd-mm-yy'
		});
	});
</script>
<!--datepicker-->

<style type="text/css">
select {
	width: 130px;
	height: 30px;
}
</style>

 </head>
<body onload="onCatChange(${mainCatId})">
	<c:url var="findAllRegularSpCk" value="/getAllRegularSpCk" />
	<c:url var="findRegSpecialCkById" value="/getRegSpecialCkById" />
	<c:url var="findSubCategory" value="/findSubCategory" />
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
			<div class="wrapperIn2">

				<!--leftNav-->

				<jsp:include page="/WEB-INF/views/include/left.jsp">
					<jsp:param name="myMenu" value="${menuList}" />
				</jsp:include>

				<!--leftNav-->
				<!--rightSidebar-->
				<div class="sidebarright">
					<div class="order-left">
						<h2 class="pageTitle">${title}</h2>
					</div>
					<br><br><br>
					<form
						action="${pageContext.request.contextPath}/orderRegularSpCake"
						method="post" class="form-horizontal" name="from_reg_ord"
						id="validation-form" onsubmit="return validateForm()">
<input type="hidden" name="currentMenuId" id="currentMenuId" value="${currentMenuId}"/>

						<!--formBox-->
						<div class="ordercake">
							<!--leftForm-->
							<div class="left">


								<div class="fullform">
									<div class="cackleft">Category</div>
									<div class="cackright">
										<select name="catId" id="catId" class="form-control"  onchange="onCatChange(this.value)" readonly>
											<option value="">Select Category</option>

											<c:forEach items="${mCategories}"
												var="mCategoryList">
												
												<option value="${mCategoryList.catId}" selected><c:out value="${mCategoryList.catName}" /></option>
											</c:forEach>
										</select>
									</div>
								</div>
								<div class="fullform">
									<div class="cackleft">Sub Category</div>
									<div class="cackright">
										<select name="regular_sp_cake" id="regular_sp_cake" class="form-control" required>
											<option value="">Select Sub Category</option>

										</select>
									</div>
								</div>
								<div class="fullform">
									<div class="cackleft2">Regular Cake</div>
									<div class="cackrighttexbox">
										<select data-placeholder="Select Item" class="form-control" tabindex="7"
											id="regSpCkItem" name="regSpCkItem" required>
											
										</select>

									</div>
								</div>
                                &nbsp; &nbsp; 
								<div class="fullform">
									<div class="cackleft">Name</div>
									<div class="cackright" id="sp_name">
										<span class="cakename" id="rg_ck_name">- - - -</span>
									</div>
								</div>
								&nbsp; &nbsp; &nbsp;
                                     <br>
								<%-- <div class="fullform">
									<div class="cackimg">
										<div class="cackimglable"></div>
										<img src="${url}${specialCake.spImage}"
											onerror="this.src='${pageContext.request.contextPath}/resources/images/No_Image_Available.jpg';">
									</div>
								</div>
								&nbsp; --%>

								<div class="fullform">
									<div class="cackleft">Description</div>
									<div class="cackright" id="spDesc">
										<span class="cakename" id="reg_desc">- - - -</span>
									</div>
								</div>
 <c:set var="currentMenuId" value="${currentMenuId}"></c:set>
								<%
									// Create a Calendar object
									Calendar calendar = Calendar.getInstance();
							    int menuId = (int) pageContext.getAttribute("currentMenuId");
									// Get current day from calendar
									int day = calendar.get(Calendar.DATE);
									// Get current month from calendar
									int month = calendar.get(Calendar.MONTH) + 1;
									// Get current year from calendar
									int year = calendar.get(Calendar.YEAR);
									calendar.add(Calendar.DATE, 0);

									day = calendar.get(Calendar.DATE);
									month = calendar.get(Calendar.MONTH);
									year = calendar.get(Calendar.YEAR);

									Calendar cal = Calendar.getInstance();
									cal.setTime(new Date()); // Now use today date.
									if(menuId==80){
										cal.add(Calendar.DATE, 2); // Adding 1 days
									}else
									{
										cal.add(Calendar.DATE,1); // Adding 1 days
									}
									
								
									Date date = cal.getTime();
									SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

									String fDate = formatter.format(date);
									System.out.println("" + fDate);
									SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");

									String fDate1 = formatter1.format(date);
								%>
							</div>

							<input type="hidden" name="rg_sp_name" id="rg_sp_name"> <input
								type="hidden" name="rg_sp_desc" id="rg_sp_desc" value="NA">

							 <div class="center">

						<!--	<div class="colOuter">
								 	<div class="col1">
										<div class="col1title">Message</div>
									</div> 
									<div class="col2"> -->
										<%-- <select name="sp_event" id="sp_event" >

											<c:forEach items="${eventList.getEvent()}" var="eventList">
												<option value="${eventList.spMsgText}"><c:out value="${eventList.spMsgText}" /></option>
											</c:forEach>
										</select> --%>
										 <input 
											name="sp_event" type="hidden" id="sp_event" value="Birthday" required>
									<!--  </div>
									<div class="col3">  -->
										<input class="texboxitemcode"placeholder="Name" 
											name="event_name" type="hidden" id="event_name" value="happy bday" required>
							<!-- 	</div>
								</div>  -->

								<!-- <div class="colOuter"></div>
								&nbsp; -->

								<div class="colOuter">
									<div class="col1">
										<div class="col1title">Quantity</div>
									</div>
									<div class="col2full">
										<input class="texboxitemcode" placeholder="" name="sp_qty"
											type="text" id="sp_qty" onkeyup="calculatePerQuantity()"
											required value="1" >
									</div>
								</div>

								<div class="colOuter"></div>
							



								<div class="colOuter">
									<div class="col1">
										<div class="col1title">Delivery Date</div>
									</div>
									<div class="col2full">
										<input id="datepicker" class="texboxitemcode texboxcal"
											placeholder="Delivery Date" name="datepicker" type="text" value="<%=fDate %>"
											required>
									</div>
								</div>
								<div class="colOuter"></div>

								<div class="colOuter">
									<div class="col1">
										<div class="col1title">Order No:</div>
									</div>
									<div class="col2full">
										<input class="texboxitemcode" placeholder="Order No"
											name="sp_place" id="sp_place" type="text" value="${spNo}" readonly>
									</div>
								</div>


								<div class="colOuter"></div>


								<div class="colOuter">
									<div class="col1">
										<div class="col1title">Customer Name</div>
									</div>
									<div class="col2full">
										<input class="texboxitemcode texboxcal2"
											placeholder="Customer Name" name="sp_cust_name" type="text"
											id="sp_cust_name" required>
									</div>
								</div>

								<div class="colOuter"></div>

								<div class="colOuter">
									<div class="col1">
										<div class="col1title">Customer DOB</div>
									</div>
									<div class="col2full">
										<input id="datepicker2" class="texboxitemcode texboxcal"
											placeholder="Customer DOB" name="datepicker2" type="text"
											required>
									</div>
								</div>

								<div class="colOuter"></div>

								<div class="colOuter">
									<div class="col1">
										<div class="col1title">Customer Mobile No.</div>
									</div>
									<div class="col2full">
										<input class="texboxitemcode" placeholder="Mobile No."
											name="sp_cust_mobile_no" type="text" id="sp_cust_mobile_no"
											required>
									</div>
								</div>

								<div class="colOuter"></div>


								<div class="cackleft" id="error">
									<span class="cakename"> <c:if
											test="${not empty errorMessage}">
											<h3>
												<c:out value="${errorMessage}" />
											</h3>
										</c:if></span>
								</div>
							</div>

							<!--centerForm-->
							<!--rightForm-->
							<div class="right">
								<div class="priceBox">
									<h2 class="inrbox" id="INR">INR -</h2>
									<input type="hidden" name="sp_grand" id="sp_grand" value="">
									<div class="inrboxmiddle">
										<ul>

											<li>
												<div class="priceLeft">Price</div>
												<div class="priceRight" id="price"></div> <input
												name="sp_calc_price" id="sp_calc_price" value=""
												type="hidden">
											</li>

											<li>
												<div class="priceLeft">Sub Total</div>
												<div class="priceRight" id="subtotal"></div> <input
												name="sp_sub_total" id="sp_sub_total" type="hidden" value="">
											</li>
											<li>
												<div class="priceLeft">GST</div>
												<div class="priceRight" id="tax3">%</div> <input
												type="hidden" id="t3" name="t3" value="">
											</li>
											<li>
												<div class="priceLeft">GST IN RS.</div>
												<div class="priceRight" id="gstrs"></div> <input
												type="hidden" id="gst_rs" name="gst_rs" value="">
											</li>
											<li class="total">
												<div class="priceLeft" id="mgstamt">AMT-</div> <input
												type="hidden" name="m_gst_amt" id="m_gst_amt" value="">

												<div class="priceRight" id="tot">TOTAL-</div> <input
												type="hidden" name="total_amt" id="total_amt" value="">
											</li>

											<li class="advance">
												<div class="priceLeft">Advance</div>
												<div class="priceRight">
													<input name="adv" id="adv" value="00" class="tableInput"
														type="text" onkeyup="advanceFun()">
												</div>
											</li>
										</ul>
									</div>
									<div class="remainamount">
										<div class="priceLeft">Remaining Amount</div>
										<div class="priceRight" id="rmAmt"></div>
										<input type="hidden" name="rm_amount" id="rm_amount" value="">
									</div>


								</div>

								<div class="order-btn">
									<input name="" class="btnSubmit" value="SUBMIT" type="submit"
										onClick="return validateForm()"> <input name=""
										class="btnReset" value="RESET" type="hidden">
								</div>

							</div>
							<input type="hidden" id="t1" name="t1" value=""> <input
								type="hidden" id="t2" name="t2" value="">
								<input type="hidden" id="t1Amt" name="t1Amt" value=""> <input
								type="hidden" id="t2Amt" name="t2Amt" value="">
								
								 <input
								type="hidden" id="dbAdonRate" name="dbAdonRate"> <input
								type="hidden" id="dbPrice" name="dbPrice" value=""> <input
								type="hidden" id="sp_id" name="sp_id" value=""> <input
								type="hidden" id="frRateCat" name="frRateCat"
								value="${frDetails.getFrRateCat()}"> <input
								type="hidden" id="rate" name="rate" value=""> <input
								type="hidden" id="MRP" name="MRP" value="">
					</form>
					<!--rightForm-->

					<!--formBox-->
				</div>
				<!--rightSidebar-->

			</div>
			<!--fullGrid-->
		</div>
		<!--rightContainer-->
	</div>
	<!--wrapper-end-->
	
	
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
	
	function onCatChange(catId)
	{
		   $.getJSON('${findSubCategory}',
					{
						catId : catId,
						ajax : 'true'
					},
					function(data) {

						var len = data.length;

						$('#regular_sp_cake').find('option').remove().end()

						$("#regular_sp_cake").append($("<option></option>").attr("value",'-1').text('Select SubCategory'));

						for (var i = 0; i < len; i++) {

							$("#regular_sp_cake").append($("<option></option>").attr("value",data[i].subCatId).text(data[i].subCatName));
						}

						$("#regular_sp_cake").trigger("chosen:updated");

					});
		
	}
		$(document).ready(function() {$('#regular_sp_cake').change(function() {
			$('#regSpCkItem').find('option').remove().end()  
		/* 	 if($(this).val()!=14)
				{
			     document.getElementById("sp_qty").readOnly = true;
				}else
				{
					 document.getElementById("sp_qty").readOnly = false;
				}  */
			                                        $.getJSON('${findAllRegularSpCk}',
																{
																	regular_sp_cake : $(this).val(),
																	ajax : 'true'
																},
																function(data) {

																	var len = data.length;

																	$('#regSpCkItem').find('option').remove().end()

																	$("#regSpCkItem").append($("<option></option>").attr("value",'-1').text('Select Regular Cake'));

																	for (var i = 0; i < len; i++) {

																		$("#regSpCkItem").append($("<option></option>").attr("value",data[i].id).text(data[i].itemName));
																	}

																	$("#regSpCkItem").trigger("chosen:updated");

																});
											});
						});
	</script>
	<!--------------------------------------------------->
	<script type="text/javascript">
		$(document)
				.ready(
						function() {

							$('#regSpCkItem')
									.change(
											function() {
												$
														.getJSON(
																'${findRegSpecialCkById}',
																{
																	id : $(this).val(),
																	ajax : 'true'
																},
																function(data) {

																	var len = data.length;
																	var actqty =parseFloat($("#sp_qty").val());
																	var frRateCat = $("#frRateCat").val();
																	if (frRateCat == 1) {
																		data.itemMrp3 = data.itemMrp1;
																		document.getElementById("rate").setAttribute('value',data.itemRate1);

																	} else if (frRateCat == 2) {
																		data.itemMrp3 = data.itemMrp2;
																		document.getElementById("rate").setAttribute('value',data.itemRate2);
																	} else {
																		data.itemMrp3 = data.itemMrp3;
																		document.getElementById("rate").setAttribute('value',data.itemRate3);

																	}
																	document.getElementById("MRP").setAttribute('value',data.itemMrp3);

																	$("#rg_ck_name").text(data.itemName);
																	document.getElementById("rg_sp_name").setAttribute('value',data.itemName);
                                                                    
																	var calcPrice=(data.itemMrp3*actqty).toFixed(2);
																	$("#price").text(calcPrice);
																	document.getElementById("sp_calc_price").setAttribute('value',calcPrice);

																	$("#subtotal").text(calcPrice);
																	document.getElementById("sp_sub_total").setAttribute('value',calcPrice);

																	document.getElementById("t1").setAttribute('value',data.itemTax1);
																	document.getElementById("t2").setAttribute('value',data.itemTax2);


																	$("#tax3").html(data.itemTax3+ ' %');
																	document.getElementById("t3").setAttribute('value',data.itemTax3);
																
																	var mrp=((data.itemMrp3)*actqty)*100;
																	var taxPer3=parseFloat(data.itemTax3);
																	var taxPer2=parseFloat(data.itemTax2);
																	var taxPer1=parseFloat(data.itemTax1);

																	var mrpBaseRate = parseFloat(mrp/(taxPer3+100));
																
                                                        			var gstInRs=0;
                                                        			var taxPerPerc1=0;
                                                        			var taxPerPerc2=0;
                                                        			var tax1Amt=0;
                                                        			var tax2Amt=0;
                                                        			 var total=0;
                                                        			if(taxPer3==0)
                                                        				{
                                                        				    gstInRs=0;
                                                        				    total=mrpBaseRate+gstInRs;
                                                        				}
                                                        		    else
                                                        			{
                                                        			   gstInRs=(mrpBaseRate*taxPer3)/100;
                                                        			   total=mrpBaseRate+gstInRs;
                                                        			   
                                                        			   if(taxPer1==0)
                                                        				{
                                                        				   taxPerPerc1=0;
                                                        				}
                                                        			   else
                                                        				{
                                                        				    taxPerPerc1=parseFloat((taxPer1*100)/taxPer3);
                                                        				    tax1Amt=parseFloat((gstInRs*taxPerPerc1)/100);

                                                        				}
                                                        			   if(taxPer2==0)
                                                        				{
                                                        				   taxPerPerc2=0;
                                                        				}
                                                        			   else
                                                        				{
                                                        					taxPerPerc2=parseFloat((taxPer2*100)/taxPer3);
                                                        					tax2Amt=parseFloat((gstInRs*taxPerPerc2)/100);

                                                        				}
                                                        			   
                                                        			}
                                                        			
                                                        			document.getElementById("t1Amt").setAttribute('value',tax1Amt.toFixed(2));
																	document.getElementById("t2Amt").setAttribute('value',tax2Amt.toFixed(2));
																	
                                                                    $("#INR").text('INR-'+ total.toFixed(2));
																	document.getElementById("sp_grand").setAttribute('value',total.toFixed(2));
																	
																	$('#gstrs').html(gstInRs.toFixed(2));
																	document.getElementById("gst_rs").setAttribute('value',gstInRs.toFixed(2));

																	$('#tot').html('TOTAL-'+ total.toFixed(2));
																	document.getElementById("total_amt").setAttribute('value',total.toFixed(2));


																	$('#mgstamt').html('AMT-'+ mrpBaseRate.toFixed(2));
																	document.getElementById("m_gst_amt").setAttribute('value',mrpBaseRate.toFixed(2));

																	$('#rmAmt').html(total.toFixed(2));
																	document.getElementById("rm_amount").setAttribute('value',total.toFixed(2));

																	document.getElementById("sp_qty").setAttribute('value',1);

																});
											});
						});
	</script>

	<!-----------------------------------------------REMAINING AMOUNT ONKEYUP FUNCTION------------------->
	<script type="text/javascript">
		function advanceFun() {
			var advance = $("#adv").val();
			var rmamt = $("#total_amt").val();
			$('#rmAmt').html(rmamt - advance);
			document.getElementById("rm_amount").setAttribute('value',
					rmamt - advance);
		}
	</script>
	<!------------------------------------------------END------------------------------------------------>
	<!-------------------------------cALCULATIONS ON QUANTITY CHANGE------------------->
	<script type="text/javascript">
		function calculatePerQuantity() {
			var qty =parseFloat($("#sp_qty").val());

			var price = parseFloat($("#MRP").val());

			var taxPer3 = parseFloat($("#t3").val());
			
			var taxPer1 = parseFloat($("#t1").val());
			var taxPer2 = parseFloat($("#t2").val());

			var calcPrice = parseFloat(qty * price);


            var taxPlus100=parseFloat(taxPer3+100);
			var mrpBaseRate =parseFloat(calcPrice * 100 /(taxPlus100));
		
		
 			var gstInRs=0;
			var taxPerPerc1=0;
			var taxPerPerc2=0;
			var tax1Amt=0;
			var tax2Amt=0;
			 var total=0;
			if(taxPer3==0)
				{
				    gstInRs=0;
				    total=mrpBaseRate+gstInRs;
				}
		    else
			{
			   gstInRs=(mrpBaseRate*taxPer3)/100;
			   total=mrpBaseRate+gstInRs;
			   
			   if(taxPer1==0)
				{
				   taxPerPerc1=0;
				}
			   else
				{
				    taxPerPerc1=parseFloat((taxPer1*100)/taxPer3);
				    tax1Amt=parseFloat((gstInRs*taxPerPerc1)/100);

				}
			   if(taxPer2==0)
				{
				   taxPerPerc2=0;
				}
			   else
				{
					taxPerPerc2=parseFloat((taxPer2*100)/taxPer3);
					tax2Amt=parseFloat((gstInRs*taxPerPerc2)/100);

				}
			   
			}
			
			
 			document.getElementById("t1Amt").setAttribute('value',tax1Amt.toFixed(2));
			document.getElementById("t2Amt").setAttribute('value',tax2Amt.toFixed(2));
			
			$("#price").text(total);
			document.getElementById("sp_calc_price").setAttribute('value',
					total);

			$("#subtotal").text(total);
			document.getElementById("sp_sub_total").setAttribute('value',
					total);

			$("#INR").text('INR-' + total);
			document.getElementById("sp_grand")
					.setAttribute('value', total);

			$("#tax3").html(taxPer3 + ' %');
			document.getElementById("t3").setAttribute('value', taxPer3);

			$('#gstrs').html(gstInRs.toFixed(2));
			document.getElementById("gst_rs").setAttribute('value', gstInRs.toFixed(2));

			$('#tot').html('TOTAL-' + total);
			document.getElementById("total_amt").setAttribute('value',
					total);

			$('#mgstamt').html('AMT-' + mrpBaseRate.toFixed(2));
			document.getElementById("m_gst_amt").setAttribute('value', mrpBaseRate.toFixed(2));

			$('#rmAmt').html(total);
			document.getElementById("rm_amount").setAttribute('value',
					total);

			var advance = $("#adv").val();
			var rmamt = $("#total_amt").val();

			$('#rmAmt').html(rmamt - advance);
			document.getElementById("rm_amount").setAttribute('value',
					rmamt - advance);
		}
	</script>
<!------------------------------------------------END------------------------------------------------>
	
<!------------------VALIDATION ------------------->	
	<script type="text/javascript">
	function validateForm()
	{
		 var phoneNo = /^\d{10}$/;  
		 
		var rspId = $('#regSpCkItem').val();
		var phNo = $('#sp_cust_mobile_no').val();
	
		var isValid=true;
		if(rspId==-1)
			{
			        alert("Please Select Regular Special Cake!");
			        isValid= false;
			}
		else  if(phNo.match(phoneNo))  
		  {  
		      return true;  
		  }  
		  else  
		  {  
		     alert("Not a valid Mobile Number");  
		     return false;  
		  }  

		return isValid;
	}
	</script>
	<!------------------------------------------->
</body>
</html>