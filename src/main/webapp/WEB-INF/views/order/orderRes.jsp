<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
   <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>   
<%-- <!DOCTYPE html>
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

</script>   --%>
<!--rightNav-->

<!--selectlistbox-->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.selectlistbox.js"></script>
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



        
<!-- </head>
<body>
 -->


<!--datepicker-->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
<script>
  $( function() {
    $( "#datepicker" ).datepicker();
  } );
  $( function() {
    $( "#datepicker2" ).datepicker();
  } );
  $( function() {
    $( "#datepicker3" ).datepicker();
  } );
  $( function() {
    $( "#datepicker4" ).datepicker();
  } );
  $( function() {
    $( "#datepicker5" ).datepicker();
  } );
  </script>
<!--datepicker--> 

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
<h2 class="pageTitle">${menuTitle}</h2>
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
<!--formBox-->
<div class="ordercake">
<!--leftForm-->
  


<div class="left">


	<div class="fullform">
		<div class="cackleft2">Item Code</div>
		<div class="cackrighttexbox">${specialCake.itemId}</div>
	</div>




	<div class="fullform">
		<div class="cackleft">Name</div>
		<div class="cackright"><span class="cakename">${spName}</span></div>
	</div>
	
	<div class="fullform">
		<div class="cackimg">
	<img src="${url}${spImage}" onerror="this.src='${pageContext.request.contextPath}/resources/images/No_Image_Available.jpg';"></div>
		<!--<div class="cackimglable"><img src="http://monginisaurangabad.com/images/lable-regular.png" alt="img"></div>-->
		<%-- <img src="${url}${spImage}" alt="Tier -Series"></div> --%>
	</div>
        
	<div class="fullform">
		<div class="cackleft">Min Weight</div>
		<div class="cackright">${specialCake.spMinWeight} kg</div>
	</div>
	
	<div class="fullform">
		<div class="cackleft">Max Weight</div>
		<div class="cackright">${specialCake.spMaxWeight} kg</div>
	</div>
	
	<div class="fullform">
		<div class="cackleft">Production Time</div>
		<div class="cackright">${productionTime} Days</div>
	</div>
	
	<div class="fullform">
		<div class="cackleft">Earliest Delivery Date</div>
		<div class="cackright">
		<fmt:parseDate value="${specialCake.spEstDeliDate}" pattern="yyyy-MM-dd" var="spEstDeliDateFmt"/>
		<fmt:formatDate value="${spEstDeliDateFmt}" var="formatedEarDelDate" pattern="dd-MM-yyyy"/>
		${formatedEarDelDate}</div>
	</div>
	
</div>
<!--leftForm-->


	
<!--centerForm-->	
<div class="center">

	<%-- <div class="fullform">
		<div class="cackleft">Type</div>
		<div class="cackright">
			<c:choose>
		<c:when test="${spType=='0'}">
				FC
		</c:when>
		<c:when test="${spType=='1'}">
				
				Chocolate
		</c:when>				
				
						
	</c:choose>
			</div>
	</div> --%>
    
<div class="fullform">
		<div class="cackleft">Flavour</div>
		<div class="cackright">
        	${flavourName}			</div>
	</div>    
	
	<div class="fullform">
		<div class="cackleft">Weight</div>
		<div class="cackright">
      ${specialCake.spSelectedWeight} Kg		</div>
	</div>
	
	<div class="fullform">
		<div class="cackleft">Message</div>
		<div class="cackright">
       ${specialCake.spEvents} - ${specialCake.spEventsName}		</div>
	</div>
	<c:choose>
		<c:when test="${isCustCh=='1'}">
	<div class="fullform">
		<div class="cackleft">Order Photo</div>
		<div class="cackright">
       <img src="${SPCAKE_URL}${specialCake.orderPhoto}"alt="image"/>
        </div>
	</div>
	<div class="fullform">
		<div class="cackleft">Customer Choice Photo</div>
		<div class="cackright">
       <img src="${PHOTO_URL}${specialCake.orderPhoto2}"alt="image"/>
        </div>
	</div>
	
	
    </c:when>
    <c:when test="${spPhoUpload=='1'}">
    <div class="fullform">
		<div class="cackleft">Order Photo</div>
		<div class="cackright">
       <img src="${SPCAKE_URL}${specialCake.orderPhoto}"alt="img"/>
        </div>
	</div>
    
    
       </c:when>
    </c:choose>
    
	<div class="fullform">
		<div class="cackleft">Special Instructions</div>
		<div class="cackright"><c:choose>
            <c:when test="${specialCake.spInstructions==''}">NA</c:when> 
            
            <c:otherwise>
                   ${specialCake.spInstructions}
            </c:otherwise>
        </c:choose></div>
	</div>
	
	<div class="fullform">
		<div class="cackleft">Delivery Date</div>
		<div class="cackright">
		<fmt:parseDate value="${specialCake.spDeliveryDate}" pattern="yyyy-MM-dd" var="spDeliveryDateFmt"/>
		<fmt:formatDate value="${spDeliveryDateFmt}" var="formatedDelDate" pattern="dd-MM-yyyy"/>
		${formatedDelDate}
		
		</div>
	</div>
	
	<div class="fullform">
		<div class="cackleft">Customer Name</div>
       <div class="cackright"> ${specialCake.spCustName}  </div>
       </div>
       
       <div class="fullform">
		<div class="cackleft"> Customer DOB</div>
		<div class="cackright">
		<c:choose>
            <c:when test="${specialCake.spCustDob==''}">NA</c:when> 
            
            <c:otherwise>
            <fmt:parseDate value="${specialCake.spCustDob}" pattern="yyyy-MM-dd" var="spCustDobFmt"/>
		    <fmt:formatDate value="${spCustDobFmt}" var="spCustDobFormat" pattern="dd-MM-yyyy"/>
	              ${spCustDobFormat}
            </c:otherwise>
        </c:choose></div>
        </div>
        
        <div class="fullform">
		<div class="cackleft">Customer Mob</div>
		 <div class="cackright">${specialCake.spCustMobNo}     
           </div>
	</div>
	
	<%-- <div class="colOuter">
		<div class="cackright">
		 ${specialCake.spBookedForName} 
                </div>
		<div class="cackright">
		 ${specialCake.spBookForDOB} 
                </div>
		<div class="cackright">
		 ${specialCake.spBookForNumber} 
                </div>
	</div> --%>
	
	
</div>
<!--centerForm-->

<!--rightForm-->	
<div class="right">
	<div class="priceBox">
		<h2 class="inrbox"  style="font-size: 20px;">INR - 
        <span id="pro_grand_totla_1"   style="font-size: 20px;">
		 ${specialCake.spGrandTotal}</span></h2>
		<div class="inrboxmiddle">
			<ul>
				<li>
					<div class="priceLeft">Type </div>
					<div class="priceRight"><span>Premium</span></div>
				</li>
				<li>
					<div class="priceLeft">Price </div>
					<div class="priceRight" id="pro_price">${specialCake.spPrice}</div>
				</li>
				<li>
					<div class="priceLeft">Add Rate </div>
					<div class="priceRight" id="pro_adon_price">${specialCake.spTotalAddRate}</div>
                    
				</li>
				<li>
					<div class="priceLeft">Extra Charges </div>
					<div class="priceRight" id="ex_charges">${exCharges}</div>
                   
				</li>
				<li>
					<div class="priceLeft">Discount(%) </div>
					<div class="priceRight" id="ex_disc">${disc}</div>
                   
				</li>
				<li>
					<div class="priceLeft">Sub Total </div>
					<div class="priceRight" id="pro_total_price">${specialCake.spSubTotal}</div>
                   
				</li>
				
				<li class="total">
					<div class="priceLeft">TOTAL</div>
					<div class="priceRight" id="pro_grand_totla">
					${specialCake.spGrandTotal}</div>
				</li>
				
				<li class="advance">
					<div class="priceLeft">Advance</div>
					<div class="priceRight">${specialCake.spAdvance}</div>
				</li>
			</ul>
		</div>
		<div class="remainamount">
			<div class="priceLeft">Remaining Amount</div>
           
					<div class="priceRight" id="rem_amount">
					
				${specialCake.rmAmount}        </div>
		</div>

	
	</div>	
    
    </div>
    

<!--rightForm-->

<!--<div class="messages messagesErr">err message</div>
<div class="messages messagesInfo">info message</div>
<div class="messages messagesSuccess">success message </div>-->
	
</div>


<!--formBox-->

 
		
</div>
	<a href="${pageContext.request.contextPath}/showSpCakeOrder/${globalIndex}"><h1><< Back</h1></a>
<br/>    
	<a href="${pageContext.request.contextPath}/showSpCakeOrderPDF" target="_blank"><h1>PDF</h1></a>
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


</body>
</html>
