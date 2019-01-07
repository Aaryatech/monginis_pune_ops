<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.time.LocalDate,java.util.*"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
    
    	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>


<!--datepicker-->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>

<!--datepicker--> 
<style type="text/css">
select {
    width: 130px;
    height: 30px;
}
</style>   

<!-- </head>
<body> -->

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
<jsp:param name="myMenu" value="${menuList}"/>
         </jsp:include>

<!--leftNav-->


<!--rightSidebar-->
<div class="sidebarright">
<div class="order-left">
<h2 class="pageTitle">Regular Special Cake Order</h2>
</div>
<div class="row"></div>
<c:if test="${isFound==true}">
<div class="alert alert-success">
  <strong>Success!</strong> Your Order Saved Successfully,Thank you!!
</div>
</c:if>
<c:if test="${isFound==false}">
<div class="alert alert-danger">
    <strong>Failed!</strong> Oops,Your Order Not Saved!!
  </div>

</c:if>
<%-- <form action="${pageContext.request.contextPath}/orderRegularSpCake"  method="post" class="form-horizontal" name="from_reg_ord" id="validation-form" >
 --%>

 <!--formBox-->
<div class="ordercake">


<!--leftForm-->
<div class="left">


    <div class="fullform">
		<div class="cackleft2">Special Cake</div>
		<div class="cackrighttexbox">
		    Regular 
	   </div>
     </div>
	
	
	<div class="fullform">
		<div class="cackleft">Name</div>
		<div class="cackright" id="sp_name"><span class="cakename" id="rg_ck_name">  ${spName}</span></div>
	</div>
 &nbsp; &nbsp; &nbsp;
 
	<%-- <div class="fullform">
		<div class="cackimg">
		<div class="cackimglable"></div>
		<img src='${pageContext.request.contextPath}/resources/images/No_Image_Available.jpg' onerror="this.src='${pageContext.request.contextPath}/resources/images/No_Image_Available.jpg';"></div>
	</div>
	&nbsp;
	 --%>
     <div class="fullform">
		<div class="cackleft">Description</div>
		<div class="cackright" id="spDesc"><span class="cakename" id="reg_desc">- - - -</span></div>
	</div>
	
	<%
// Create a Calendar object
Calendar calendar = Calendar.getInstance();

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

cal.set(year, month, day);
Date date = cal.getTime();
SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");

String fDate = formatter.format(date);
System.out.println(""+fDate);
SimpleDateFormat formatter1 = new SimpleDateFormat("dd MMM yyyy");

String fDate1 = formatter1.format(date);%>

</div>
 <input type="hidden" name="rg_sp_name" id="rg_sp_name" >
 <input type="hidden" name="rg_sp_desc" id="rg_sp_desc" value="NA">

<!--centerForm-->	
 <div class="center">
<%--	<div class="colOuter">
		<div class="col1"><div class="col1title">Event</div></div>
		<div class="col2"><div class="col1title">${rspEvents}</div></div>
		<div class="col3"><div class="col1title">${rspEventsName}</div>
		</div>
	</div>
<div class="colOuter">
	</div> --%>
	
	<div class="colOuter">
			<div class="col1"><div class="col1title">Quantity</div></div>
		   <div class="col1"> <div class="col1title"> ${qty}</div>    </div>
	</div>
	<div class="colOuter"></div>

	<div class="colOuter">
		<div class="col1"><div class="col1title">Delivery Date</div></div>
		<div class="col2full"><div class="col1title">${rspDeliveryDt}</div> 
		</div>
	</div>
	<div class="colOuter"></div>
	
	<div class="colOuter">
		<div class="col1"><div class="col1title">Order No:</div></div>
		<div class="col2full"><div class="col1title">${spPlace}</div> </div>
	</div>    
	
	
	<div class="colOuter">
		
	</div>
	
	<div class="colOuter">
	<div class="col1"><div class="col1title">Customer Name</div></div>
	<div class="col2full"><div class="col1title">${rspCustName}</div></div>
	</div>
	
	<div class="colOuter"></div>
	
	<div class="colOuter">
		<div class="col1"><div class="col1title">Customer DOB</div></div>
		<div class="col2full"><div class="col1title">${spCustDOB}</div></div>
	</div>
	
	<div class="colOuter"></div>
	
	<div class="colOuter">
			<div class="col1"><div class="col1title">Customer Mobile No.</div></div>
		   <div class="col2full"><div class="col1title">${rspCustMobileNo}</div></div>
	</div>
	
	<div class="colOuter"></div>
	
	<div class="colOuter">
		<div class="col1"><input class="texboxitemcode texboxcal2" placeholder="Booked For" name="sp_booked_for_name" type="hidden"id="sp_booked_for_name"></div>
		<div class="col2"><input id="datepicker5" class="texboxitemcode texboxcal" placeholder="" name="datepicker5" type="hidden"></div>
		<div class="col3"><input class="texboxitemcode" placeholder="Mobile No." name="sp_book_for_number" type="hidden"id="sp_book_for_number"></div>
	</div>
	
	
	<div class="cackleft" id="error"><span class="cakename">
<c:if test="${not empty errorMessage}">
<h3><c:out value="${errorMessage}"/></h3>
</c:if></span></div>
	
	
</div>

<!--centerForm-->
	
<!--rightForm-->	
<div class="right">
	<div class="priceBox">
		<h2 class="inrbox" id="INR">INR - ${rgCkPrice} </h2>
		
		<div class="inrboxmiddle">
			<ul>
				
				<li>
					<div class="priceLeft">Price </div>
					<div class="priceRight" id="price">${rgCkPrice}</div>
					
				</li>
				
				<li>
					<div class="priceLeft">Sub Total </div>
					<div class="priceRight"id="subtotal">${rspSubTotal}</div>
				</li>
				<li>
					<div class="priceLeft">GST </div>
					<div class="priceRight" id="tax3">${tax}%</div>
				</li>
				<li>
					<div class="priceLeft">GST IN RS.</div>
					<div class="priceRight" id="gstrs">${gstRs}</div>
				</li>
				<li class="total">
					<div class="priceLeft" id="mgstamt">AMT- ${rgGstAmount}</div>
					
				
					<div class="priceRight"id="tot">TOTAL-${totalAmt}</div>
					
				</li>
				
				<li class="advance">
					<div class="priceLeft">Advance</div>
					<div class="priceRight">${rspAdvanceAmt}</div>
					
				</li>
			</ul>
		</div>
		<div class="remainamount">
			<div class="priceLeft">Remaining Amount</div>
					<div class="priceRight" id="rmAmt">${rspRemainingAmt}</div>
				  
		</div>
	
	
	</div>
	
	
</div>

<!-- </form> -->
<!--rightForm-->
<!--formBox-->
</div>
<!--rightSidebar-->
</div>
<!--fullGrid-->
</div>
<!--rightContainer-->
<div>
	<a href="${pageContext.request.contextPath}/showRegularSpCakeOrder/${globalIndex}"><h1> << Back</h1></a>
	<br/>    
	<a href="${pageContext.request.contextPath}/showRegCakeOrderPDF" target="_blank"><h1>PDF</h1></a>
</div>
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

</body>
</html>