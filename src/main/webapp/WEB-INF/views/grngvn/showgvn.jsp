 <%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/tableSearch.css">
<style type="text/css">
label::before {
    width: 0px;
    height: 0px;
    border: 0px;
    }
    
 .main-table tbody > tr:hover{
  background-color: #ffa;
}

</style>

 <style>
table, th, td {
    border: 1px solid #9da88d;
}
</style>
<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

<c:url var="getViewGvnOption" value="/getViewGvnOption" />

<div class="sidebarOuter"></div>

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

			<!-- Place Actual content of page inside this div -->
			<div class="sidebarright">

				<div class="row">
					<div class="col-md-2">
						<h2 class="pageTitle">Request GVN</h2>
					</div>
                   

					<div class="col-md-3">
						<br> <select name="view_opt" id="view_opt" class="form-control"
							style="width: 250px; background-color: white; height: 40px" onchange="showDate()">
							<option value="0">Select From Bill</option>

							<option value="1">Select From Date</option>

						</select>
					</div>

					<br>


					<div class="col-md-2">
						<input id="datepicker" class="texboxitemcode texboxcal" value="0" class="form-control"
							name="bill_date" type="text" style="display: none;">
					</div>


					<div class="col-md-2">

						<button type="button" class="buttonsaveorder" style="display: none;" id='searchButton'							onclick="getViewOption()" style="width: 100px; height: 40px">Search
							</button>
						<!--<button type="button" class="btn">Cancel</button>-->

					</div>

				</div>

				<br />

				<div class="row">
					<div class="col-md-12">
						<!--table-->
						<form
							action="${pageContext.request.contextPath}/getGvnBillDetails"
							name="grn" id="grn" method="get">

							<div class="row">
							<div class="col-md-2">
						<h2 class="pageTitle"> </h2>
					</div>
								<div class="col-md-3">
									<select name="bill_no" id="bill_no" class="form-control"
										style="width: 250px; background-color: white; height: 40px">
									

										<c:forEach items="${frBillList}" var="frBillList">
											<c:choose>

												<c:when test="${selctedBillNo == frBillList.billNo}">
													<option selected value="${frBillList.billNo}">Invoice No- ${frBillList.invoiceNo} Bill Date- ${frBillList.billDate}</option>
												</c:when>

												<c:otherwise>
													<option value="${frBillList.billNo}">Invoice No- ${frBillList.invoiceNo} Bill Date- ${frBillList.billDate}</option>
												</c:otherwise>

											</c:choose>
										</c:forEach>

									</select>

								</div>

								<!-- <div class="form-group"> -->
									<div class="col-sm-2 col-sm-offset-1 col-lg-1 col-lg-offset-0">

										<button type="submit" class="buttonsaveorder"
											style="width: 110px; height:40px">BillDetail</button>
										<!--<button type="button" class="btn">Cancel</button>-->

									</div>

								<!-- </div> -->
							</div>
						</form>

					</div>
					<!-- 						</form>
 -->
					<form action="${pageContext.request.contextPath}/addTempGvn"
						name="grn_add" id="grn_add" method="post">
						
						<div class="col-md-9" ></div> 
					<label for="search" class="col-md-3" id="search">
    <i class="fa fa-search" style="font-size:20px"></i>
									<input type="text"  id="myInput" onkeyup="myFunction()" placeholder="Search items by name.." title="Type in a name">
										</label>  
						

						<div class="clearfix"></div>

						<div id="table-scroll" class="table-scroll">
							<div id="faux-table" class="faux-table" aria="hidden" >
							<table id="table_grid1" class="main-table">
									<thead>
										<tr class="bgpink">
											 <!-- 	<th class="col-md-1">Sr No.</th>
											<th class="col-md-1">Bill No</th>
											<th class="col-md-1">Date</th> -->

										<!--	<th class="col-md-1">SELECT</th>
											<th class="col-md-3">Item Name</th>
											<th class="col-md-2">Purchase</th>

											<th class="col-md-2">Gvn Qty</th>
											<th class="col-md-2">Rate</th>
											<th class="col-md-2">Tax %</th>
											<th class="col-md-2">Amount</th> -->

										</tr>
									</thead>
							</table>
							
							
							
							
							</div>
							<div class="table-wrap">
								<table id="table_grid" class="main-table">
									<thead>
										<tr class="bgpink">
											<!-- 	<th class="col-md-1">Sr No.</th>
											<th class="col-md-1">Bill No</th>
											<th class="col-md-1">Date</th> -->

											<th class="col-md-1" style="text-align: center;">SELECT</th>
											<th class="col-md-3" style="text-align: center;">Item Name</th>
											<th class="col-md-2" style="text-align: center;">Purchase</th>

											<th class="col-md-1" style="text-align: center;">Gvn Qty</th>
											<th class="col-md-1" style="text-align: center;">Rate</th>
											<th class="col-md-2" style="text-align: center;">Tax %</th>
											<th class="col-md-2" style="text-align: center;">Amount</th>

										</tr>
										
									</thead>
									<tbody>

										<c:forEach items="${gvnConfList}" var="gvnConfList"
											varStatus="count">

											<input type="hidden" id="b_qty${gvnConfList.itemId}"
												value="${gvnConfList.billQty}" />

											<tr>
												<td class="col-md-1" style="text-align: center;"><input type="checkbox" 
													name="select_to_gvn" id="${gvnConfList.billDetailNo}"
													value="${gvnConfList.billDetailNo}" /></td>

												<td class="col-md-3">${gvnConfList.itemName}</td>
												<td class="col-md-2" style="text-align: right;">${gvnConfList.billQty}</td>
												<td class="col-md-1" style="text-align: center;"><input type="text"
													name="gvn_qty${gvnConfList.itemId}"
													id='gvn_qty${gvnConfList.itemId}' size="5" value="0" 
													onkeyup="calcGvn(${gvnConfList.calcBaseRate},${gvnConfList.itemId},${gvnConfList.sgstPer},${gvnConfList.cgstPer})" /></td>

												<td class="col-md-1" style="text-align: right;">${gvnConfList.rate}</td>

												<td class="col-md-1" id="tax_per${gvnConfList.itemId}" style="text-align: right;"><c:out
														value="00"></c:out></td>

												<td class="col-md-2" id="gvn_amt${gvnConfList.itemId}" style="text-align: right;"><c:out
														value="00"></c:out></td>

											</tr>

										</c:forEach>

									</tbody>

								</table>
							</div>
						</div>

                        <br>
						<div class="form-group">

							<button type="submit" class="buttonsaveorder">
								<i class="fa fa-check"></i> Proceed
							</button>
							<!--<button type="button" class="btn">Cancel</button>-->

						</div>
					</form>
				</div>


				<!--table end-->

			</div>
		</div>

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

<script>

 document.getElementById("order_photo1").onchange = function () {
    var reader = new FileReader();

    reader.onload = function (e) {
        // get loaded data and render thumbnail.
        document.getElementById("image").src = e.target.result;
    };

    // read the image file as a data URL.
    reader.readAsDataURL(this.files[0]);
};

</script>

<script type="text/javascript">
function calcGvn(baseRate,itemId,sgstPer,cgstPer){
	
	var gvnQty=$("#gvn_qty"+itemId).val();
	var billQty=$("#b_qty"+itemId).val();
//	alert("gvnQty ="+gvnQty+ "Bill Qty "+billQty);
	if(parseInt(gvnQty) > parseInt(billQty)){
		
		document.getElementById("gvn_qty"+itemId).value=0;
		alert("GVN Qty can not be greater than Purchase Qty ");
		//var zero=0;
		//$("#gvn_qty"+itemId).value=0;
	}
	else{
	//alert(gvnQty);
	//$("#gvn_qty"+itemId).html(gvnQty);

	
	var gvnAmt=parseFloat(gvnQty)*parseFloat(baseRate);
	//alert(gvnAmt);
	//$("#gvn_amt"+itemId).html(gvnAmt.toFixed(2));

	 var taxableAmt=baseRate*gvnQty;
		
		var totalTax=(taxableAmt*(sgstPer+cgstPer))/100;
		
		var grandTotal=taxableAmt+totalTax;
		
		$("#gvn_amt"+itemId).html(grandTotal.toFixed(2));
		
		var taxPer=parseFloat(sgstPer)+parseFloat(cgstPer);
		$("#tax_per"+itemId).html(taxPer.toFixed(2));

var x=$("#gvn_remark"+itemId).val();

};
}


</script>

<script type="text/javascript">
function readURL(input) {

    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $(input).next('img').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}

$(".upld").change(function () {
    readURL(this);
});

</script>
<script type="text/javascript">

function getViewOption(){
	
	//alert("HJFHKJ");
	
	var viewOpt = $("#view_opt").val();
	var bill_date = $("#datepicker").val();
	
	//alert("option "+viewOpt);
	//alert("bill_date "+bill_date);

	$.getJSON('${getViewGvnOption}', {
		view_opt : viewOpt,
		bill_date : bill_date,
		ajax : 'true',

	});
	
	window.location.reload();
}

</script>


<script>
function myFunction() {
  var input, filter, table, tr, td, i;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  table = document.getElementById("table_grid");
  tr = table.getElementsByTagName("tr");
  for (i = 0; i < tr.length; i++) {
    td = tr[i].getElementsByTagName("td")[1];
    if (td) {
      if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    }       
  }
}

function showDate(){
//	alert("HELlo");
	var viewOpt = $("#view_opt").val();
	
	if(viewOpt==1){
		document.getElementById("datepicker").style.display= "block";
		document.getElementById("searchButton").style.display= "block";

		$('#table_grid td').remove();
		
	}
	else{
		document.getElementById("datepicker").style="display:none";
		
		document.getElementById("searchButton").style="display:none";
		$('#table_grid td').remove();
	}
}
</script>



</body>
</html>