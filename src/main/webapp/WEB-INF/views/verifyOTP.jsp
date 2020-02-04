<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<title>Monginis</title>
<link href="${pageContext.request.contextPath}/resources/css/monginis.css" rel="stylesheet" type="text/css"/>
<link rel="icon" href="${pageContext.request.contextPath}/resources/images/feviconicon.png" type="image/x-icon"/> 
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-1.10.2.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/common.js"></script>


<!--rightNav-->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/menuzord.js"></script>
<script type="text/javascript">
jQuery(document).ready(function(){
	jQuery("#menuzord").menuzord({
		align:"left"
	});
});
</script>



<!--rightNav-->

        
</head>
<body>
<c:url value="reGenOPSOtp" var="reGenOPSOtp"></c:url>
<!--wrapper-start-->
<div class="wrapper">



<!--topHeader-->
<div class="fullGrid center logoBarbg slideposi">
	<div class="wrapperIn positionR">
    	<div class="logoBarLeft"><a href=""><img src="${pageContext.request.contextPath}/resources/images/monginis1.png" alt="monginis"></a></div>  
        <div class="logoBarRight"><div id="menuzord" class="menuzord red menuzord-responsive">
            <ul class="menuzord-menu menuzord-right menuzord-indented scrollable">
               <%--  <li><a href="#"><div class="usericon">John doe</div> <div class="userimg"><img src="${pageContext.request.contextPath}/resources/images/userimg.jpg"></div> </a>
                	<ul class="dropdown">
                        <li><a href="#">My Account</a></li>
						<li><a href="#">Edit Profile</a></li>
						<li><a href="#">Setting</a></li>
						<li><a href="#">Log out</a></li>
					</ul>
                </li> --%>
            </ul>
        </div></div>
    </div>
</div>
<!--topHeader-->

<!--rightContainer-->
<div class="fullGrid center">
<!--fullGrid-->
<div class="wrapperIn2">


<!-- <form method="POST" action="uploadFile" enctype="multipart/form-data">
    File to upload: <input type="file" name="file" >
    <br />
    Name: <input type="text" name="name" >
    <br />
    <br />
    <input type="submit" value="Upload">
</form> -->


<c:if test="${not empty message}">
   <!-- here would be a message with a result of processing -->
    <div class="messages messagesErr"> ${message} </div>
        	
</c:if>


	<!-- <form id="form-login" action="loginProcess" method="post"> -->
<div class="loginInner">
	<h2><span>OTP Verification</span></h2>
	<div class="loginBox">
		<div class="loginUser"><img src="${pageContext.request.contextPath}/resources/images/loginuser.png" align="img"></div>
			<form action="OpsOTPVerification" class="form-horizontal"
										id="validation-form" method="post">
		
		
		<div class="loginfildset"><input class="texboxlogin" placeholder="" name="otp" id="otp" type="text"  data-rule-required="true"></div>
		
		<input class="texboxlogin" name="username" id="username" type="hidden" value="${frCode}" readonly="readonly" data-rule-required="true">
		
		<div class="loginfildset"><input name="" class="buttonlogin" value="Verify OTP" type="submit"></div>
		<div class="loginfildset">			
			<input
							type="button"  class="buttonlogin"
							onclick="reGenOPSOtp()" value="Re Generate OTP" />
			<%-- <a href="${pageContext.request.contextPath}/forgetPwd"><i class="fa fa-lock"></i>Re Generate OTP</a> --%>
		</div>
		<div class="loginfildset">
			<div class="logintexboxleft"><a href="${pageContext.request.contextPath}/"><i class="fa fa-lock"></i>Back To Login Page</a></div>
			
		</div>
</form>	
	</div>
	
	<%-- <div class="loginBox">
		<div class="loginUser"><img src="${pageContext.request.contextPath}/resources/images/loginuser.png" align="img"></div>
		
		<h3> Forgot your Password</h3>
		
		<div class="loginfildset">
		<div class="loginfildset"><input class="texboxlogin" placeholder="Enter Password" name="" type="text"></div>
		<div class="loginfildset"><input name="" class="buttonlogin" value="SUBMIT" type="button"></div>
		</div>
	
	</div> --%>
</div>
</form>
<!-- <div class="messages messagesErr">err message</div>
        <div class="messages messagesInfo">info message</div>
        <div class="messages messagesSuccess">success message </div>
 -->
</div>
<!--fullGrid-->
</div>
<!--rightContainer-->

</div>
<!--wrapper-end-->

<!--easyTabs-->
<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
<!--easyTabs-->
<script src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/additional-methods.min.js"></script>
		<script src="${pageContext.request.contextPath}/resources/assets/jquery-validation/dist/jquery.validate.min.js"></script>
	<script
				src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
			<script>
				window.jQuery
						|| document
								.write('<script src="resources/assets/jquery/jquery-2.0.3.min.js"><\/script>')
			</script>
			
	<script>
$("#login").validate();
</script>
<script type="text/javascript">

function reGenOPSOtp() {
	//alert("Hi");

	var form = document.getElementById("validation-form");
	
	form.setAttribute("method", "post");

	form.action = ("reGenOPSOtp");

	form.submit();

			 var username = document.getElementById("username").value;
			 //alert(username);
			
			$
					.getJSON(
							'${reGenOPSOtp}',
							{
								username : username,
							
								ajax : 'true',

							},
							function(data) {

									//alert("Data  " +JSON.stringify(data));
							location.reload(true);
								
							});

		}

</script>

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