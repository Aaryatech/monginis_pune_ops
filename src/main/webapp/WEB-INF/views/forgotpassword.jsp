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

<!--wrapper-start-->
<div class="wrapper">

<!--topHeader-->
<div class="fullGrid center logoBarbg slideposi">
	<div class="wrapperIn positionR">
    	<div class="logoBarLeft"><a href=""><img src="${pageContext.request.contextPath}/resources/images/monginislogo.png" alt="monginis"></a></div>  
        <div class="logoBarRight"><div id="menuzord" class="menuzord red menuzord-responsive">
            <ul class="menuzord-menu menuzord-right menuzord-indented scrollable">
               
            </ul>
        </div></div>
    </div>
</div>
<!--topHeader-->

<!--rightContainer-->
<div class="fullGrid center">
<!--fullGrid-->
<div class="wrapperIn2">

 <div class="loginInner">
	<%-- <h2>Login to your <span>Account</span></h2>
	<div class="loginBox">
		<div class="loginUser"><img src="${pageContext.request.contextPath}/resources/images/loginuser.png" align="img"></div>
		<div class="loginfildset"><input class="texboxlogin" placeholder="Username" name="" type="text"></div>
		<div class="loginfildset"><input class="texboxlogin" placeholder="Password" name="" type="text"></div>
		<div class="loginfildset"><input name="" class="buttonlogin" value="LOGIN" type="button"></div>
		<div class="loginfildset">
			<div class="logintexboxleft"><a href="#"><i class="fa fa-lock"></i> Forgot your Password</a></div>
			<div class="checkbox">
			<input id="check1" type="checkbox" name="check" value="check1">
			<label for="check1">Remember me</label>
		</div>
		</div>
	
	</div>
	 --%>
	<div class="loginBox">
		<div class="loginUser"><img src="${pageContext.request.contextPath}/resources/images/loginuser.png" align="img"></div>
		
		<h3> Forgot your Password ?</h3>
		
		<div class="loginfildset">
		<div class="loginfildset"><input class="texboxlogin" placeholder="Enter Mobile No" name="" type="text"></div>
		<div class="loginfildset"><input name="" class="buttonlogin" value="SUBMIT" type="button"></div>
		</div>
	
	</div>
</div> 
<!-- <div class="messages messagesErr">err message</div>
        <div class="messages messagesInfo">info message</div>
        <div class="messages messagesSuccess">success message </div>

</div> -->

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


</body>
</html>