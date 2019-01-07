

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

 

</head>
<body>
 --%>

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
							
								<div class="col1title" align="left"> <font size="8">Rules And Regulation</font></div>
								<div class="col1title" align="right"> 
						 
					</div>
						</div>
						
					<div class="colOuter">
						 
						<div class="col-md-3">
							 <font size="4"><b>Last Upload Date: ${date1}</b></font>
						</div>
						<div class="col-md-1">
							 <a href="${url}${document1}" target="blank" ><input type="button" value="View Document" class="btn btn-info">
										</a>
						</div>
 
					</div><br><br>
					
					
						<div class="col-md -3">
							
								<div class="col1title" align="left"><font size="8">Product Information</font></div>
								<div class="col1title" align="right"> 
						 
							</div>
						</div>
						
					<div class="colOuter">
						 
						<div class="col-md-3">
						<font size="4"><b>Last Upload Date: ${date2}</b></font> 

						</div>
						<div class="col-md-1">
							 <a href="${url}${document2}" target="blank" ><input type="button" value="View Document" class="btn btn-info">
										</a>
						</div>
 
					</div>
					
					<div class="col-md -3">
							
								<div class="col1title" align="left"><font size="8">SpCake Undertaking</font></div>
								<div class="col1title" align="right"> 
						 
							</div>
						</div>
						
					<div class="colOuter">
						 
						<div class="col-md-3">
						<font size="4"><b>Last Upload Date: ${date5}</b></font> 

						</div>
						<div class="col-md-1">
							 <a href="${url}${document5}" target="blank" ><input type="button" value="View Document" class="btn btn-info">
										</a>
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
