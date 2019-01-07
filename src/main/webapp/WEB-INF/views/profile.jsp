<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
    
<%-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
	jQuery(document).ready(function() {
		jQuery("#menuzord").menuzord({
			align : "left"
		});
	});
	$(".toggle").on("click", function() {
		$(".marquee").toggleClass("microsoft");
	});
</script> --%>
<style>
.fileUpload1 input.upload1{
    position: absolute;
    top: 0;
    right: 0;
    margin: 0;
    padding: 0;
    cursor: pointer;
    opacity: 0;
    filter: alpha(opacity=0);
    width: 100%;
    height: 33px;
}
/* div.topSlide {
    width: auto;
    height: 600px;
    overflow: scroll;
} */
</style>
<!--rightNav-->
<link href="${pageContext.request.contextPath}/resources/css/style.css"
	rel="stylesheet" type="text/css" />

</head>
<body>
<c:url var="checkUserAuthority" value="/checkUserAuthority"/>
<c:url var="updateUserPasswords" value="/updateUserPasswords"/>
<c:url var="updateAdminPassword" value="/updateAdminPassword"/>

	<!--topLeft-nav-->
	<div class="sidebarOuter"></div>
	<!--topLeft-nav-->

	<!--wrapper-start-->
	<div class="wrapper">

		<!--topHeader-->
		<%-- <jsp:include page="/WEB-INF/views/include/header.jsp">
		
			<jsp:param name="fr" value="${frDetails}"/>
		</jsp:include> --%>

<jsp:include page="/WEB-INF/views/include/logo.jsp"></jsp:include>



		<!--topHeader-->

		<!--rightContainer-->
		<div class="fullGrid center">
			<!--fullGrid-->
			<div class="wrapperIn2">

				<jsp:include page="/WEB-INF/views/include/left.jsp">
					<jsp:param name="myMenu" value="${menuList}" />

				</jsp:include>

				<!--rightSidebar-->
				<div class="sidebarright">
										


					<!--slider-->
					<!--slider thum size : width:850px height:350px-->
					<div id="owl-example" class="owl-carousel">




						<%-- <c:forEach items="${msgList}" var="msgList">

							<div class="item">
								<div class="screen4plan">
									<div class="homesliderImg">
									 <img src="${url}${msgList.msgImage}" height="300" width="300" />
									

									</div>
									<h3 class="homesliderTitle" ><center>${msgList.msgHeader}</center></h3>


								</div>
							</div>
						</c:forEach> --%>

						
					</div>
					<!--slider-->

					
					
					<div class="topSlide">
			<div class="topSlide2 textcen">
				
				<div class="profileinsite">
		<form name="updateprofile" id="updateprofile" method="post" action="${pageContext.request.contextPath}/updateprofile"enctype="multipart/form-data" >
					<div class="profileinsiteLeft">

						<div class="profile">
							<div class="profilefildset">Franchisee Name</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="franchisee Name"
									name="fr_name" type="text" value="${frDetails.frName}">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Franchisee Profile Pic</div>
							<div class="profileinput">
								<div class="editimg">
									<div class="editpics">
										<div class="fileUpload1">
											<span><i class="fa fa-pencil"></i></span> <input type="file"
												class="upload1"name="fr_image" id="fr_image"/>
										</div>
									</div>
									<img
										src="${URL}${frImageName}"
										alt="img" id="img">
								</div>
							</div>
						</div>
                         <input type="hidden" name="prevImage" value="${frImageName}">
						<div class="profile">
							<div class="profilefildset">City</div>
							<div class="profileinput">
							
								<input class="texboxitemcode" placeholder="City Name"
									name="fr_city" type="text" value="${frDetails.frCity}">
									
							</div>
						</div>
						<div class="profile">
							<div class="profilefildset">Route</div>
							<div class="profileinput mardis">${frDetails.routeName}</div>
							
						</div>
						<div class="profile">
							<div class="profilefildset">Tax Type</div>
							<div class="profileinput mardis">
							<c:choose>
							<c:when test="${frDetails.frGstType==0}">
							Non-Registered
							</c:when>
							<c:when test="${frDetails.frGstType==2000000}">
							Composite
							</c:when>
							<c:when test="${frDetails.frGstType==10000000}">
							Regular
							</c:when>
							<c:otherwise>
							Composite
							</c:otherwise>
							</c:choose>
							
							</div>
							
						</div>
						<div class="profile">
							<div class="profilefildset">1/2 KG Limit</div>
							<div class="profileinput mardis">${frDetails.frKg2}</div>
							
						</div>
						
						<div class="profile">
							<div class="profilefildset">Owner's Birthdate</div>
							<div class="profileinput mardis">${frDetails.ownerBirthDate}</div>
							
						</div>
						<div class="profile">
							<div class="profilefildset">FDA License Date</div>
							<div class="profileinput mardis">${frDetails.fbaLicenseDate}</div>
							
						</div>
						
                        <div class="profile">
							<div class="profilefildset">Edit Admin Password</div>
							<div class="col2">
								<input class="texboxitemcode" placeholder="Enter new Password"
									name="fr_password" type="password" onChange="checkPasswordMatch();"    value="${frDetails.frPassword}" id="txtNewPassword" disabled="disabled"
									style="font-size: 16pt; height: 33px; width:130px; background-color:LightGrey;">
							</div>
						</div>  
						 <%-- <div class="profile">
							<div class="profilefildset">Owner Password</div>
							<div class="col2">
								<input class="texboxitemcode" placeholder="Owner Password"
									name="user1_password" type="password"  value="${frSup.pass1}" id="user1_password" 
									style="font-size: 10pt; height: 33px; width:130px; background-color:LightGrey;" disabled="disabled">
								
							</div>
						</div> --%>
						  <div class="profile">
							<div class="profilefildset">Captain Password</div>
							<div class="col2">
								<input class="texboxitemcode" placeholder="Captain Password"
									name="user2_password" type="password"  value="${frSup.pass2}" id="user2_password" 
									style="font-size: 16pt; height: 33px; width:130px; background-color:LightGrey;" disabled="disabled">
								
							</div>
						</div>
						
                         
               
					</div>
                    
                   
					<div class="profileinsiteRight">
					<div class="profile">
							<div class="profilefildset">Email id</div>
							<div class="profileinput">
								<input class="texboxitemcode" name="fr_email"
									placeholder="example@gmail.com" type="text" value="${frDetails.frEmail}">
							</div>
						</div>
					&nbsp;
					
						<div class="profile">
							<div class="profilefildset">Mobile No.</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="Mobile No."
									name="fr_mobile" type="text" value="${frDetails.frMob}">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Owner Name</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="Owner Name"
									name="fr_owner" type="text" value="${frDetails.frOwner}">
							</div>
						</div>
                        <div class="profile">
							<div class="profilefildset">Rate Type</div>
							<div class="profileinput mardis">
							<c:choose>
							<c:when test="${frDetails.frRateCat==1}">
							Local Rate
							</c:when>
							<c:when test="${frDetails.frRateCat==2}">
							Out-Station Rate
							</c:when>
							<c:when test="${frDetails.frRateCat==3}">
							Special Rate
							</c:when>
							</c:choose></div>
							
						</div>
						 <div class="profile">
							<div class="profilefildset">1 KG Limit</div>
							<div class="profileinput mardis">${frDetails.frKg3}</div>
							
						</div>
						<div class="profile">
							<div class="profilefildset">Pastries Limit</div>
							<div class="profileinput mardis">${frDetails.frKg1}</div>
							
						</div>
						<div class="profile">
							<div class="profilefildset">Shop Opening Date</div>
							<div class="profileinput mardis">${frDetails.frOpeningDate}</div>
						</div>
                        <div class="profile">
							<div class="profilefildset">Agreement Date</div>
							<div class="profileinput mardis">${frDetails.frAgreementDate}</div>
							
						</div>
						<div class="profile">
							<div class="profilefildset">Pest Control Date</div>
							<div class="profileinput mardis">${frSup.pestControlDate}</div>
							
						</div>
						<!--  <div class="profile">
							<div class="profilefildset"></div>
							<div class="profileinput mardis"></div>
							
						</div> -->
						

						<div class="profile">
							<div class="profilefildset">Confirm Admin Password</div>
							<div class="col2">
								<input class="texboxitemcode" placeholder="Confirm new Password"
									name="fr_password" type="password"  value="${frDetails.frPassword}" id="txtConfirmPassword" onChange="checkPasswordMatch();" disabled="disabled"
									style="font-size: 16pt; height: 33px; width:130px; background-color:LightGrey;">
								
							</div>
						
							<div class="form-group">
						
								<input name="" class="" value="Change Admin Password"
									type="button" id="changePwd1" onclick="showDiv()"style="font-size: 8pt; height: 33px; width:143px;">
							
						</div>	
							<div  class="update FormAlert" id="divCheckPasswordMatch">
							</div>
							
						</div>
						
                  <div class="profile">
							<div class="profilefildset">CSP Password</div>
							<div class="col2">
								<input class="texboxitemcode" placeholder="CSP Password"
									name="user3_password" type="password"  value="${frSup.pass3}" id="user3_password" 
									style="font-size: 16pt; height: 33px; width:130px; background-color:LightGrey;"disabled="disabled">
								
							</div>
							<div class="form-group">
								<input name="" class="" value="Change Password"
									type="button" id="changePwd2" onclick="showDiv1()"style="font-size: 8pt; height: 33px; width:90px;">
						</div>	
						
						</div>
						          <div class="profile" style="display: none;"id="adminDiv">
							<div class="profilefildset">Admin Password</div>
							<div class="col2">
								<input class="texboxitemcode" placeholder="Enter Admin Password"
									name="admin_password" type="password" value="" id="admin_password"
									style="font-size: 8pt; height: 30px; width:130px; ">
							</div>
						 
						 <div class="form-group">
						
								<input name="" class="btn btn-info" value="Submit"
									type="button" id="btnupdate_profile"style="font-size: 13pt; height: 33px; width:75px; "onclick="return checkAuthority()">
							
						</div>
						
                       </div> 
                    <div id="updateDiv" class="colOuter"style="display: none;">
						<div class="col3full">
								<input name="updateAdminPwd" class="btn btn-primary" value="Update Password" onclick="updateAdminPassword()"
									type="button" id="btnupdate_profile" >
							</div>
						</div>
					
					
					
                     <div class="profile" style="display: none;"id="adminDiv1">
							<div class="profilefildset">Admin Password</div>
							<div class="col2">
								<input class="texboxitemcode" placeholder="Enter Admin Password"
									name="admin_password1" type="password" value="" id="admin_password1"
									style="font-size: 8pt; height: 30px; width:130px; ">
							</div>
						 
						 <div class="form-group">
						
								<input name="" class="btn btn-info" value="Submit"
									type="button" id="btnupdate_profile"style="font-size: 13pt; height: 33px; width:75px; "onclick="return checkAuthForPassChange()">
							
						</div>
						
                       </div> 
                    <div id="updateDiv1" class="colOuter"style="display: none;">
						<div class="col3full">
								<input name="updateUserPwd" class="btn btn-primary" value="Update Password"
									type="button" id="btnupdate_profile" onclick="return updateUserPasswords()">
							</div>
						</div>

					</div>
					
					<div class="profile">
							<div class="profileinput">
								<input name="" class="btn additem_btn" value="SUBMIT"
									type="submit" id="btnupdate_profile">
							</div>
						</div>
						
						</form>

				</div>
			</div>
		</div>
					
					
					
					
					<!--latestNews-->

				</div>
				<!--rightSidebar-->

			</div>
			<!--fullGrid-->
		</div>
		<!--rightContainer-->

	</div>
	<!--wrapper-end-->


</body>


<script type="text/javascript">
function checkPasswordMatch() {
    var password = $("#txtNewPassword").val();
    var confirmPassword = $("#txtConfirmPassword").val();

    if (password != confirmPassword)
    	{
    	 document.getElementById("divCheckPasswordMatch").style.color = "#ff0000";
        $("#divCheckPasswordMatch").html("Passwords do not match!");
        document.getElementById("btnupdate_profile").disabled = true;
    	}
    else
    	{
    	document.getElementById("divCheckPasswordMatch").style.color = "green";
        $("#divCheckPasswordMatch").html("Passwords match.");
        document.getElementById("btnupdate_profile").disabled = false;
    	}
}

$(document).ready(function () {
   $("#txtConfirmPassword").keyup(checkPasswordMatch);
});

</script>
<script type="text/javascript">
function showDiv() {
	   document.getElementById('adminDiv').style.display = "block";
	   document.getElementById('adminDiv1').style.display = "none";

	   document.getElementById('admin_password').value = "";
	}
function showDiv1() {
	   document.getElementById('adminDiv1').style.display = "block";
	   document.getElementById('adminDiv').style.display = "none";

	   document.getElementById('admin_password1').value = "";

	}
</script>
<script type="text/javascript">

function checkAuthority() {
	
	var adminPwd = document.getElementById("admin_password").value;

			$.getJSON('${checkUserAuthority}', {
				
				adminPwd:adminPwd,
				ajax : 'true'
			}, function(data) {
				
				if(data.accessRight==1)
					{
				    document.getElementById("changePwd1").disabled = true;
					document.getElementById('txtNewPassword').removeAttribute('disabled');
					document.getElementById('txtConfirmPassword').removeAttribute('disabled');
  
					  $('#txtNewPassword').css('background-color' , 'white'); // change the background color
					   $('#txtConfirmPassword').css('background-color' , 'white'); // change the background color
					   document.getElementById('adminDiv').style.display = "none";
					   document.getElementById('updateDiv').style.display = "block";

					}
				else
				{
				 alert("Invalid Credentials");

				}
			}
			
			
);
}
function checkAuthForPassChange() {
	
	var adminPwd = document.getElementById("admin_password1").value;
			$.getJSON('${checkUserAuthority}', {
				
				adminPwd:adminPwd,
				ajax : 'true'
			}, function(data) {
				
				if(data.accessRight==1)
					{
					 document.getElementById("changePwd2").disabled = true;
				//	document.getElementById('user1_password').removeAttribute('disabled');
					document.getElementById('user2_password').removeAttribute('disabled');
					document.getElementById('user3_password').removeAttribute('disabled');
					
					//  $('#user1_password').css('background-color' , 'white'); // change the background color
					  $('#user2_password').css('background-color' , 'white'); // change the background color
					  $('#user3_password').css('background-color' , 'white'); // change the background color

					   document.getElementById('adminDiv1').style.display = "none";
					   document.getElementById('updateDiv1').style.display = "block";

					}
				else
					{
					 alert("Invalid Credentials");

					}
			}
			
			
);
}
function updateUserPasswords() {
	
	//var pass1=document.getElementById('user1_password').value;

	var pass2=document.getElementById('user2_password').value;

	var pass3=document.getElementById('user3_password').value;
	$.getJSON('${updateUserPasswords}', {
		
		//pass1:pass1,
		pass2:pass2,
		pass3:pass3,

		ajax : 'true'
	}, function(data) {
		if(data.error==false)
		{
			
			document.getElementById("changePwd2").removeAttribute('disabled');
		//	document.getElementById('user1_password').disabled = true;
			document.getElementById('user2_password').disabled = true;
			document.getElementById('user3_password').disabled = true;
			
			//  $('#user1_password').css('background-color' , 'LightGrey'); // change the background color
			  $('#user2_password').css('background-color' , 'LightGrey'); // change the background color
			  $('#user3_password').css('background-color' , 'LightGrey');
			   document.getElementById('updateDiv1').style.display = "none";

			
				 alert("User Passwords Updated Successfully");

			
		}
		
		
		
	}
	);
}

function updateAdminPassword() {
	
	var adminPwd=document.getElementById('txtNewPassword').value;
	$.getJSON('${updateAdminPassword}', {
		
		adminPwd:adminPwd,
	
		ajax : 'true'
	}, function(data) {
		
		if(data.error==false)
			{
			document.getElementById('changePwd1').removeAttribute('disabled');
			document.getElementById('txtNewPassword').disabled = true;
			document.getElementById('txtConfirmPassword').disabled = true;

			  $('#txtNewPassword').css('background-color' , 'LightGrey'); // change the background color
			  $('#txtConfirmPassword').css('background-color' , 'LightGrey'); // 
			   document.getElementById('updateDiv').style.display = "none";

			 alert("Admin Password Updated Successfully");
			
			}
		
	}
	);
}
</script>
<script>
 document.getElementById("fr_image").onchange = function () {
    var reader = new FileReader();

    reader.onload = function (e) {
        // get loaded data and render thumbnail.
        document.getElementById("img").src = e.target.result;
    };

    // read the image file as a data URL.
    reader.readAsDataURL(this.files[0]);
};
</script>
</html>