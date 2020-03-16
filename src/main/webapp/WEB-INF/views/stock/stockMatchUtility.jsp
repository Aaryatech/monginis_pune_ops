<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tableSearch.css">

<style>
 .main-table tbody > tr:hover{
  background-color: #ffa;
}
.alert {
	padding: 15px;
	background-color: #f44336;
	color: white;
}

.closebtn {
	margin-left: 15px;
	color: white;
	font-weight: bold;
	float: right;
	font-size: 22px;
	line-height: 20px;
	cursor: pointer;
	transition: 0.3s;
}

.closebtn:hover {
	color: black;
}
a:link {
    color: black;
}
a:hover {
    color: black;
}

</style>

<!--topLeft-nav-->
<div class="sidebarOuter"></div>
<!--topLeft-nav-->

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
			 	<div class="row"><div class="sidebarright">
			 	<form action="${pageContext.request.contextPath}/showStockMatchUtility" method="POST" >
			
				<div class="order-left">
					<h2 class="pageTitle">Bill As Per Physical Stock</h2>
					<!--<h3 class="pageTitle2">Order Date : 22-02-2017 </h3>-->
				</div>
				<div class="order-right" align="right">
             	</div>
				<div class="colOuter">
					<div class="col-md-2">
						<div class="col1title">Select Category</div>
					</div>
					<div class="col-md-3">
						<select name="cat_id" class="form-control chosen"
							tabindex="4" id="cat_id"  required>

							<option value="-1">Select Category</option>
							<c:forEach items="${category}" var="category" varStatus="count">
								<c:choose>
									<c:when
										test="${category.catId != '5' and category.catId != '6' and category.catId != '7' }">
										<!-- and category.catId != '6' -->
                                     <c:choose>
									<c:when test="${category.catId==catId}">
										<option value="${category.catId}" selected><c:out value="${category.catName}" /></option>
									</c:when>
									<c:otherwise>
								    <option value="${category.catId}" ><c:out value="${category.catName}" /></option>
									</c:otherwise>
									</c:choose>
									</c:when>
								</c:choose>
							</c:forEach>

						</select>
					</div>
                  <input type="hidden" name="selectStock" id="selectStock" value="1" />
				  <input type="hidden" name="st_type" id="st_type" value="1" />
                 <input type="hidden" name="selectRate" id="selectRate" value="2" />
			

					<div class="col2">
						<input name="search" class="buttonsaveorder" value="Search"
							type="submit" >
					</div>
             	</div>	
             	</form>
             	                   <c:set var="btnDisplayStyle" value="none;color:grey;" />
             	
					<div class="col-md-12">
						<!--table-->
						<form action="${pageContext.request.contextPath}/addSellBill" method="POST"
							onsubmit="substk.disabled = true; return confirm('Do you want to Submit ?');">
							<div class="clearfix"></div>
							<div class="col-md-9"></div>
						  <div class="cd-tabs" style="margin-top: 2px;">
						<nav>
							<ul class="cd-tabs-navigation">

								<c:forEach var="tab" items="${subCatListTitle}" varStatus="loop">


									<c:choose>
										<c:when test='${loop.index==0}'>
											<li><a data-content='${tab.header}' href="#0"
												class="selected"
												onClick="javascript:se_tab_id('${loop.index}')">${tab.name}</a></li>

										</c:when>
										<c:otherwise>
											<li><a data-content='${tab.header}' href="#0"
												onClick="javascript:se_tab_id('${loop.index}')">${tab.name}</a></li>

										</c:otherwise>
									</c:choose>


								</c:forEach>

							</ul>
						</nav> 
						<!--tabMenu-->
						<ul class="cd-tabs-content">
							<!--tab1-->
							<c:forEach var="tabs" items="${subCatListTitle}" varStatus="loop"> 

								 <c:choose>
									<c:when test='${loop.index==0}'>
										<li data-content='${tabs.header}' class="selected">
									</c:when>
									<c:otherwise>
										<li data-content='${tabs.header}'>
									</c:otherwise>
								</c:choose>

								<div id="table-scroll" >
							 
									<div id="faux-table" class="faux-table" aria="hidden" style="display: none;">
									 <table id="table_grid" class="main-table" >
											<thead >
												<tr class="bgpink"style="background-color: #ee578f;color:#ffffff;">
                                                 <td class="col-md-1">Sr.No</td>
                                                 <td class="col-md-2">Item Name</td>
                                                 <td class="col-md-1">Current Stock</td>
                                                 <td class="col-md-1">Physical Qty</td>
                                                 <td class="col-md-1">Bill Qty</td>
                                                 <td class="col-md-1">MRP</td>
                                                 <td class="col-md-1">Total</td>
												</tr>
												</thead>
												</table> 
									
									</div>
									<div class="table-wrap">
									
										<table id="table_grid1" class="responsive-table">
											<thead >
												<tr class="bgpink"style="background-color: #ee578f;color:#ffffff;">
												 <td class="col-md-1" style="text-align: left;">Sr.No</td>
                                                 <td class="col-md-2" style="text-align: left;" >Item Name</td>
                                                 <td class="col-md-1">Current Stock</td>
                                                 <td class="col-md-1">Physical Qty</td>
                                                 <td class="col-md-1">Bill Qty</td>
                                                 <td class="col-md-1">MRP</td>
                                                 <td class="col-md-1">Total</td>
												</tr>
												</thead>
												<tbody>
											<c:forEach var="stockDetailList" items="${stockDetailList}" varStatus="loop">
											  <c:if test="${stockDetailList.subCatId eq tabs.header}">

						                <c:set var="color" value="" />
   												<c:set var="flag" value="0" />
                                             	<c:if test="${stockDetailList.currentRegStock<=0}">
                                             	  <c:set var="color" value="red" />	<c:set var="flag" value="1" />
                                             	</c:if>
                                             	<c:if test="${stockDetailList.currentRegStock>0}">
                                             	<c:set var="btnDisplayStyle" value="block" />
                                             	</c:if>
                                       		 	<tr class="bgpink" style="color:${color}">
												 <td class="col-md-1" style="text-align: left;">${(loop.index)+1}</td>
                                                 <td class="col-md-2" style="text-align: left;">${stockDetailList.itemName}</td>
                                                 <td class="col-md-1">${stockDetailList.currentRegStock}</td>
                                                 <td class="col-md-1"><c:choose>
                                                 <c:when test="${flag==0}">
                                                  <input type="number" class="form-control" style="width: 80%;" name="physicalQty${stockDetailList.id}" id="physicalQty${stockDetailList.id}" value="0"  onchange="onChange(${stockDetailList.spTotalPurchase},${stockDetailList.id},${stockDetailList.currentRegStock})"/>
                                                 </c:when>
                                                 <c:otherwise>
                                                  <input type="number" class="form-control" style="width: 80%;" name="physicalQty${stockDetailList.id}" id="physicalQty${stockDetailList.id}" value="0"  onchange="onChange(${stockDetailList.spTotalPurchase},${stockDetailList.id},${stockDetailList.currentRegStock})" readonly/>
                                                 </c:otherwise>
                                                 </c:choose>
                                                </td>
                                                <input type="hidden" name="qty${stockDetailList.id}" id="qty${stockDetailList.id}"  />
                                                 <td class="col-md-1" id="billQty${stockDetailList.id}">0 </td>
                                                 <td class="col-md-1">${stockDetailList.spTotalPurchase}</td>
                                                 <td class="col-md-1" id="total${stockDetailList.id}">0</td>
												</tr>
 										 	</c:if>  
 										 </c:forEach>
												
											   </tbody>

						</table>
					</div>
				</div>
						 	</c:forEach>  
								

						</ul>
					</div>

							<div class="colOuter" id="sellBillAdd">
								<div class="col4full" align="right">
									<input name="" class="buttonsaveorder" value="Add Sell Bill"
										id="substk" type="submit" style="display:${btnDisplayStyle}">
								</div>
							</div>

						</form>
					</div>
				</div>
			</div>

		</div>
	</div>

	<!--rightSidebar-->

</div>
<!--wrapper-end-->

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

              form1.submit();
                
    		/* 	if(isSameDayApplicable!=2)
    				{
    				   form1.submit();
    				}
    		else if(isSameDayApplicable==2)
    				{
   			   
    				  $.getJSON(
    							'${qtyValidation}',
    							{
    								
    								ajax : 'true'
    							},
    							function(data) {
    							
    							//	alert(data.error);
        							
   								if (data.error) {
   								//	alert("hii");
    									alert(data.message);
    									 window.location.reload();
    								
    								}
    								else
    								{
    									 form1.submit();
    								
    								}	
    							});
    				}   
 */
            }    
           
        </script>

<script type="text/javascript">
		function onChange(rate,id,stkQty) {

			//calculate total value  
			var qty = $('#physicalQty'+id).val();
			
			if(qty<=stkQty){
				var insertQty=stkQty-qty;
				
			    var total = rate * insertQty;
			   $('#billQty'+id).html(insertQty);
			   $('#qty'+id).val(insertQty);
			   $('#total'+id).html(total.toFixed(2));
			}else
			{   alert("Please Enter Valid Qty!!");
				
				$('#total'+id).html(0);$('#physicalQty'+id).val(0);
			}
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
		        if (((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105))) {
		            e.preventDefault();
		        }
		   
			
		}
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
        if (((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105))) {
            e.preventDefault();
        }
    });
});
</script>

<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	
<script>
function sortTable() {
  var table, rows, switching, i, x, y, shouldSwitch;
  table = document.getElementById("table_grid1");
  switching = true;
  /*Make a loop that will continue until
  no switching has been done:*/
  while (switching) {
    //start by saying: no switching is done:
    switching = false;
    rows = table.getElementsByTagName("TR");
    /*Loop through all table rows (except the
    first, which contains table headers):*/
    for (i = 1; i < (rows.length - 1); i++) {
      //start by saying there should be no switching:
      shouldSwitch = false;
      /*Get the two elements you want to compare,
      one from current row and one from the next:*/
      x = rows[i].getElementsByTagName("TD")[0];
      y = rows[i + 1].getElementsByTagName("TD")[0];
      //check if the two rows should switch place:
      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
        //if so, mark as a switch and break the loop:
        shouldSwitch = true;
        break;
      }
    }
    if (shouldSwitch) {
      /*If a switch has been marked, make the switch
      and mark that a switch has been done:*/
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
    }
  }
}
</script>

<script>
	function myFunction() {
		var input, filter, table, tr, td, i;
		input = document.getElementById("myInput");
		filter = input.value.toUpperCase();
		table = document.getElementById("table_grid1");
		tr = table.getElementsByTagName("tr");
		for (i = 0; i < tr.length; i++) {
			td = tr[i].getElementsByTagName("td")[0];
			if (td) {
				if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
					tr[i].style.display = "";
				} else {
					tr[i].style.display = "none";
				}
			}
		}
	}
</script>
</body>
</html>
