 <%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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
				<!-- 	<div class="col-md-3">
						<h6 class="pageTitle">Request Manual GRN</h6>
					</div>
					 -->
					<div class="col-md-3">

						<button type="button" class="buttonsaveorder" style="display: none;" id='searchButton'							onclick="getViewOption()" style="width: 100px; height: 40px">Search
							</button>
						<!--<button type="button" class="btn">Cancel</button>-->

					</div>

				<!-- </div>

				<div class="row"> -->
					<div class="col-md-12">
						<!--table-->
						<form
							action="${pageContext.request.contextPath}/getGrnBillDetail"
							name="grn" id="grn" method="get">

				<%-- 			<div class="row">
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
							</div> --%>
						</form>

					</div>
					<!-- 						</form>
 -->
					<form action="${pageContext.request.contextPath}/postManualGrn"
					onsubmit="return confirm('Do you really want to save ?');"
						name="grn_add" id="grn_add" method="post">
						
						<div class="col-md-9">
						<h6 class="pageTitle">Request GRN</h6>
					</div>
					<label for="search" class="col-md-3" id="search">
    <i class="fa fa-search" style="font-size:20px"></i>
									<input type="text" style="border-radius:25px;" id="myInput" onkeyup="myFunction()" placeholder="Search items by name.." title="Type in a name">
										</label>  
						

						<div class="clearfix"></div>

						<div id="table-scroll" class="table-scroll">
							<div id="faux-table" class="faux-table" aria="hidden" >
				 	<table id="table_grid1" class="main-table">
									<thead>
										<tr class="bgpink">
											<!--	<th class="col-md-1">Sr No.</th>
											<th class="col-md-1">Bill No</th>
											<th class="col-md-1">Date</th>
<th class="col-md-1">SELECT</th>
											<th class="col-md-1">Invoice No</th>
										<th class="col-md-2">Name</th>
										<th class="col-md-1">Type</th>
										<th class="col-md-2">Pur Qty</th>
										<th class="col-md-1">Rate</th>
										<th class="col-md-1">Grn Rate</th>
										<th class="col-md-1">Qty</th>
										<th class="col-md-1">Tax %</th>
										<th class="col-md-2">Taxable Amt</th>
										<th class="col-md-1">Tax Amt</th>
										<th class="col-md-1">Amount</th>
										<th class="col-md-1">Remark</th>
-->
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
<th class="col-md-1">SELECT</th>
											<th class="col-md-1" style="text-align: center;">Invoice No</th>
											<th class="col-md-2" style="text-align: center;">Invoice Date</th>
										<th class="col-md-2"  style="text-align: center;">Name</th>
										<th class="col-md-1"  style="text-align: center;">Type</th>
										<th class="col-md-2"  style="text-align: center;">Pur Qty</th>
										<th class="col-md-1"  style="text-align: center;">Rate</th>
									<!-- 	<th class="col-md-1">Grn Rate</th> -->
										<th class="col-md-1"  style="text-align: center;">Qty</th>
										<th class="col-md-1"  style="text-align: center;">Tax %</th>
										<th class="col-md-2"  style="text-align: center;">Taxable Amt</th>
										<th class="col-md-1"  style="text-align: center;">Tax Amt</th>
										<th class="col-md-2"  style="text-align: center;">Amount</th>
										<th class="col-md-1" style="text-align: center;">Remark</th>

										</tr>
										
									</thead>
									<tbody>

										<c:forEach items="${grnConfList}" var="grnConfList"
										varStatus="count">
										<tr id="row${grnConfList.billDetailNo}">

											<td class="col-md-1" style="text-align: center;"><input type="checkbox" 
													name="select_to_grn" id="${grnConfList.billDetailNo}"
													value="${grnConfList.billDetailNo}" /></td>
<td class="col-md-2"  style="text-align: center;"><fmt:formatDate pattern="dd-MM-yyyy" value="${grnConfList.billDate}" />
</td>
											<td class="col-md-1"  style="text-align: center;"><c:out
													value="${grnConfList.invoiceNo}"></c:out></td>
											<td class="col-md-2"  style="text-align: center;"><c:out
													value="${grnConfList.itemName}"></c:out></td>
											<c:choose>
												<c:when test="${grnConfList.grnType==0}">
													<td class="col-md-1"  style="text-align: center;"><c:out value="GRN 1(80%)"></c:out></td>
												</c:when>
												<c:when test="${grnConfList.grnType==1}">
													<td class="col-md-1"  style="text-align: center;"><c:out value="GRN 2(70%)"></c:out></td>
												</c:when>
												<c:when test="${grnConfList.grnType==2}">
													<td class="col-md-1"  style="text-align: center;"><c:out value="GRN 3(100%)"></c:out></td>
												</c:when>
												<c:when test="${grnConfList.grnType==3}">
													<td class="col-md-1" style="text-align: center;"><c:out value="No GRN"></c:out></td>
												</c:when>

												<c:when test="${grnConfList.grnType==4}">
													<td class="col-md-1" style="text-align: center;"><c:out value="GRN 3(100%)"></c:out></td>
												</c:when>
												<c:otherwise>
													<c:out value="No GRN"></c:out>
												</c:otherwise>

											</c:choose>
										
											<td class="col-md-1" style="text-align: center;"><c:out
													value="${grnConfList.billQty}"></c:out> <input
												type="hidden" name="grnqty${grnConfList.billDetailNo}"
												id="grnqty${grnConfList.billDetailNo}" size="3"
												readonly="readonly" value="${grnConfList.autoGrnQty}" /></td>

											<td class="col-md-1"  style="text-align: center;" id="grn_rate${grnConfList.billDetailNo}"><c:out
													value="${grnConfList.rate}"></c:out></td>
											

											<td class="col-md-1"><input type="text"
												name="grnqtyauto${grnConfList.billDetailNo}"
												value="0" 
												id='grnqtyauto${grnConfList.billDetailNo}' size="3"  style="text-align: center;"
												onkeyup="calcGrn(${grnConfList.grnType},${grnConfList.rate},${grnConfList.itemId},
																	${grnConfList.sgstPer},${grnConfList.cgstPer},${grnConfList.billQty},${grnConfList.billDetailNo},${grnConfList.discPer})" />


											</td>

											<td class="col-md-1"  style="text-align: center;" id="tax_per${grnConfList.billDetailNo}"><c:out
													value="${grnConfList.taxPer}"></c:out></td>

											<fmt:formatNumber var="taxableAmt" type="number"
												minFractionDigits="2" maxFractionDigits="2"
												value="${grnConfList.taxableAmt}" />

											<c:set var="taxableAmt" value="${taxableAmt}" />

											<td id='taxable_amt${grnConfList.billDetailNo}'  style="text-align: center;" class="col-md-1"><c:out value="${taxableAmt}"></c:out></td>


											<td id='tax_amt${grnConfList.billDetailNo}' class="col-md-1"><c:out value="${grnConfList.taxAmt}"></c:out></td>

											<fmt:formatNumber var="grnAmt" type="number"
												minFractionDigits="2" maxFractionDigits="2"
												value="${grnConfList.grnAmt}" />

											<c:set var="grnAmt" value="${grnAmt}" />

											<td class="col-md-2" id="grn_amt${grnConfList.billDetailNo}"><c:out
													value="${grnAmt}"></c:out></td>

											<td class="col-md-1"><select
												name="grn_remark${grnConfList.billDetailNo}" style="width: 200px" required="required"
												id="grn_remark${grnConfList.billDetailNo}" class="form-control" onchange="changeQty(${grnConfList.billDetailNo},${grnConfList.autoGrnQty})">
													<option selected value="0">Goods Expired</option>
													<c:forEach items="${remarkList}" var="remarkList">
																${remarkList.remark}
																<option value="${remarkList.remark}">${remarkList.remark}</option>
													</c:forEach>
											</select></td>

										</tr>
									</c:forEach>
									</tbody>
								</table>
							</div>
						</div>

						<div class="form-group">

							<button type="submit" class="buttonsaveorder">
								<i class="fa fa-check"></i> Save
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

<script type="text/javascript">
	
	function changeQty(billDetailNo,autoQty){
		//alert("HIII "+ itemId);
		
		var remark = document.getElementById("grn_remark"+billDetailNo).value;
	 //	alert(remark);
	 	if(remark==0){
	 		//alert("In remark ==0");
			    document.getElementById("grnqtyauto"+billDetailNo).value=autoQty;
	 	}else{
			 $("#grnqtyauto"+billDetailNo).removeAttr("readonly"); 
	 	}
		 
	}
	
	</script>

<script type="text/javascript">
	
	function calcGrn(grnType,rate,itemId,sgstPer,cgstPer,autoQty,detailId,discPer){
		
		document.getElementById(""+detailId).checked = false;
		document.getElementById("row"+detailId).style.backgroundColor="white";
		
		var baseRate=rate*100/(sgstPer+cgstPer+100);
	
		var grnBaseRate;
		
		var grnRate;
		var grnQty=$("#grnqtyauto"+detailId).val();
		
		if(parseInt(grnQty)>autoQty){
			alert("Edit Quantity can not be greater than Bill Quantity");
			//document.getElementById("grnqtyauto"+itemId).value=autoQty;
			document.getElementById("grnqtyauto"+detailId).value=0.00;
			//---------Extra-------------
			$("#grn_amt"+detailId).html(0);
			
			$("#taxable_amt"+detailId).html(0);

			$("#tax_amt"+detailId).html(0);
			//---------------------
			document.getElementById(""+detailId).checked = false;
			document.getElementById("row"+detailId).style.backgroundColor="white";
			//calcGrn(grnType,rate,itemId,sgstPer,cgstPer,autoQty)
		}else{
		
		if(grnType==0){
			
		
			/* $("#hidden_auto_qty"+itemId).html(grnQty);

			var hidden=$("#hidden_auto_qty"+itemId).val();
			alert(hidden); */
			
			var grnRate=$("#grn_rate"+detailId).text();
			
			grnBaseRate = baseRate * 80 / 100;
			 
			 grnRate=(rate * 80) / 100;
			
			//var grnAmt=parseFloat(grnQty)*parseFloat(grnRate);
			//grnAmt=grnAmt*80/100;	
			//$("#grn_amt"+itemId).html(grnAmt.toFixed(2));
		
		}
	 if(grnType==1){

		// var grnQty=$("#grnqtyauto"+itemId).val();
			/* $("#hidden_auto_qty"+itemId).html(grnQty);

			var hidden=$("#hidden_auto_qty"+itemId).val();
			alert(hidden); */
			
			var grnRate=$("#grn_rate"+detailId).text();
			
			grnBaseRate = baseRate * 70 / 100;
			
			grnRate=(rate * 70) / 100;
			
			//var grnAmt=parseFloat(grnQty)*parseFloat(grnRate);
			//grnAmt=grnAmt*70/100;	
			//$("#grn_amt"+itemId).html(grnAmt.toFixed(2));
		
			}
			if(grnType==2){
			
			
			//var grnQty=$("#grnqtyauto"+itemId).val();
			/* $("#hidden_auto_qty"+itemId).html(grnQty);
			
			var hidden=$("#hidden_auto_qty"+itemId).val();
			
			alert(hidden); */
			
			
			var grnRate=$("#grn_rate"+detailId).text();
			
			grnBaseRate=baseRate;
			grnRate=rate;
			
			   
			//var grnAmt=parseFloat(grnQty)*parseFloat(grnRate);
			
			//$("#grn_amt"+itemId).html(grnAmt.toFixed(2));
		
			}
			
			if(grnType==4){
				
				//var grnQty=$("#grnqtyauto"+itemId).val();
				var grnRate=$("#grn_rate"+detailId).text();
				grnBaseRate=baseRate;
				grnRate=rate;
				
				}
			
		var totTaxPer=parseFloat(sgstPer)+parseFloat(cgstPer);
			var taxableAmt=grnBaseRate*grnQty;
			var discAmt=(taxableAmt*discPer)/100;
			taxableAmt=taxableAmt-discAmt;
			var totalTax=taxableAmt*(cgstPer+sgstPer)/100;
			
			var grandTotal=taxableAmt+totalTax;
			/* alert(taxableAmt);
			alert(totalTax);
			alert(grandTotal);
			 */
		//$("#grn_rate"+itemId).html(baseRate.toFixed(2));

		$("#grn_amt"+detailId).html(grandTotal.toFixed(2));
		$("#tax_per"+detailId).html(totTaxPer.toFixed(2));
		
		$("#taxable_amt"+detailId).html(taxableAmt.toFixed(2));

		$("#tax_amt"+detailId).html(totalTax.toFixed(2));
		
		if(grnQty>0)
		{
		document.getElementById(""+detailId).checked = true;
		document.getElementById("row"+detailId).style.backgroundColor="pink";
		}
		/* var x=$("#grn_remark"+itemId).val();
		if(grnQty>0){
		
		if(x ==null ||x == ""){
			alert("Enter Remark");
			
		    document.getElementById("grn_remark"+itemId).focus();
		    
		}
		} */
		
		}//end of else
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
    td = tr[i].getElementsByTagName("td")[3];
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