<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

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
jQuery(document).ready(function(){
	jQuery("#menuzord").menuzord({
		align:"left"
	});
});
</script>
<!--rightNav-->


</head>
<body> --%>

	<!--topLeft-nav-->
	<div class="sidebarOuter"></div>
	<!--topLeft-nav-->

	<!--wrapper-start-->
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
				<div class="sidebarright">





					<div class="order-left">
						<h2 class="pageTitle">Edit Profile</h2>


						<input type="hidden" name="menuId" value="${menuId}"> <input
							type="hidden" name="rateCat" value="${frDetails.frRateCat}">

					</div>

					<%-- <div class="order-right">
						<div class="ordermto2px">
							<div class="orderclose">Order Closing Time :</div>
							<div class="ordercloser2">
								<span>${toTime}</span>
							</div>
						</div>
						<div class="ordermto20px">
							<div class="order-price">Total Amount :</div>
							<div class="order-amount">INR : ${grandTotal}</div>


						</div>
					</div>
					 --%>
				
							<form action="editProfile" class="form-horizontal"
								id="validation-form" method="post">


								<input type="hidden" name="mode_add" id="mode_add"
									value="add_att">
									<table border="0">
									<tr>
									<div class="form-group">
									<td>
									<label class="col-sm-3 col-lg-2 control-label">Franchisee
										Code</label></td>
									<div class="col-sm-9 col-lg-10 controls">
										<td><input type="text" name="fr_code"
											id=""fr_code"" placeholder="Franchisee Code"
											class="form-control" required />
											</td>
									</div>
									</div>
									
									</tr>
									
									<tr>
									<div class="form-group">
									<td><label class="col-sm-3 col-lg-2 control-label">Franchisee
										Rating</label></td>
									<div class="col-sm-9 col-lg-10 controls">
										<td><input type="text" name="fr_rating"
											id="fr_rating" placeholder="Rating"
											class="form-control" required /></td>
									</div>
									</div>
									</tr>
									<tr>
									<div class="form-group">
									<td><label class="col-sm-3 col-lg-2 control-label">Franchisee
										Name</label></td>
									<div class="col-sm-9 col-lg-10 controls">
										<td><input type="text" name="fr_name"
											id="fr_name" placeholder="Franchisee Name"
											class="form-control" required /></td>
									</div>
									</div>
									</tr>
									
									<tr>
									<div class="form-group">
									<td><label class="col-sm-3 col-lg-2 control-label">Mobile No
										</label></td>
									<td><div class="col-sm-9 col-lg-10 controls">
										<input type="text" name="fr_mobile"
											id="fr_mobile" placeholder="Franchisee Mobile"
											class="form-control" required />
									</div>
									</td>
									</div>
									</tr>
									
									<tr>
									<div class="form-group">
									<td><label class="col-sm-3 col-lg-2 control-label">Owner
										Name</label></td>
									<div class="col-sm-9 col-lg-10 controls">
										<td><input type="text" name="owner_name"
											id="owner_name" placeholder="Franchisee Name"
											class="form-control" required /></td>
									</div>
									</div>
									</tr>
								<tr>
								<div class="form-group">
									<td><label class="col-sm-3 col-lg-2 control-label">Opening
										Date</label></td>
									<div class="col-sm-5 col-lg-3 controls">
										<td><input class="form-control date-picker" id="dp1" size="16"
											type="text" name="opening_date" value="" required /></td>
									</div>
								</div>
								</tr>
								
								<tr>
								<div class="form-group">
											<td><label class="col-sm-3 col-lg-2 control-label"
												for="fr_password">Password</label></td>
											<div class="col-sm-6 col-lg-4 controls">
												<td><input type="password" name="fr_password" id="fr_password"
													class="form-control" data-rule-required="true"
													data-rule-minlength="6" /></td>
											</div>
										</div>
										</tr>
<tr>
										<div class="form-group">
											<td><label class="col-sm-3 col-lg-2 control-label"
												for="fr_confirm_password">Confirm Password</label></td>
											<div class="col-sm-6 col-lg-4 controls">
												<td><input type="password" name="fr_confirm_password"
													id="fr_confirm_password" class="form-control"
													data-rule-required="true" data-rule-minlength="6"
													data-rule-equalTo="#fr_password" /></td>
											</div>
										</div>

								</tr>
								<tr>
								
								<div class="form-group">
									<div class="col-sm-9 col-sm-offset-3 col-lg-10 col-lg-offset-2">
										<button type="submit" class="btn btn-primary">
											<i class="fa fa-check"></i> SUBMIT
										</button>
										</div>
										</div></tr>
										
								
										</table>
							</form>
						</div>
						
						<!--tabNavigation-->

						<!--<div class="order-btn"><a href="#" class="saveOrder">SAVE ORDER</a></div>-->
						<!-- <div class="order-btn textcenter">

							<input name="" class="buttonsaveorder" value="SAVE ORDER"
								type="button" ONCLICK="button1()">
						</div>

 -->

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

	
</body>
</html>
