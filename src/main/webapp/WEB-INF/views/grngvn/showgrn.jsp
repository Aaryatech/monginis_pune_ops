<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>

<style>
table, th, td {
    border: 1px solid #9da88d;
}
</style>
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


			<c:url var="getGrnData" value="/getGrnData" />



			<!--leftNav-->

			<jsp:include page="/WEB-INF/views/include/left.jsp">
				<jsp:param name="myMenu" value="${menuList}" />

			</jsp:include>


			<!--leftNav-->
			<!--rightSidebar-->

			<!-- Place Actual content of page inside this div -->
			<div class="sidebarright">


				<%-- 	<form
								action="${pageContext.request.contextPath}/insertGrnProcess"
								name="validation_form" id="validation_form" method="post">
								
 --%>
				<c:if test="${not empty alert}">
					<!-- here would be a message with a result of processing -->
					<div class="messages messagesErr">${alert}</div>
				</c:if>
				<div class="row">
					<div class="col-md-12">
						<h2 class="pageTitle">Request GRN</h2>
					</div>
				</div>

				<div class="clearfix"></div>
				<form action="${pageContext.request.contextPath}/insertGrnProcess"
					onsubmit="return confirm('Do you really want to save ?');"
					name="validation_form" id="validation_form" method="post">



					<div id="table-scroll" class="table-scroll">
						<div id="faux-table" class="faux-table" aria="hidden"></div>
						<div class="table-wrap">
				 			<table id="table_grid1" class="main-table">
								<thead>
									<tr class="bgpink">
									<!--	<th class="col-md-1">Invoice No</th>
										<th class="col-md-3">Name</th>
										<th class="col-md-2">Type</th>
										<th class="col-md-1">QTY</th>
										<th class="col-md-1">Bill Rate</th>
										<th class="col-md-1">Grn Rate</th>
										<th class="col-md-1">Edit Qty</th>
										<th class="col-md-1">Tax %</th>
										<th class="col-md-1">Taxable Amt</th>
										<th class="col-md-1">Tax Amt</th>
										<th class="col-md-1">Total Amt</th>
										<th class="col-md-1">Remark</th>-->
									</tr>

								</thead>
								<tbody>
								</table> 
								<div class="table-wrap">
							<table id="table_grid" class="main-table">
								<thead>
									<tr class="bgpink">
									<th class="col-md-2"  style="text-align: center;">Invoice Date</th>
										<th class="col-md-2"  style="text-align: center;">Invoice No</th>
										<th class="col-md-2"  style="text-align: center;">Name</th>
										<th class="col-md-2"  style="text-align: center;">Type</th>
										<th class="col-md-1"  style="text-align: center;">QTY</th>
										<th class="col-md-1"  style="text-align: center;">Rate</th>
									<!-- 	<th class="col-md-1">Grn Rate</th> -->
										<th class="col-md-1"  style="text-align: center;">Edit Qty</th>
										<th class="col-md-1" style="text-align: center;">Tax %</th>
										<th class="col-md-1" style="text-align: center;">Taxable Amt</th>
										<th class="col-md-1" style="text-align: center;">Tax Amt</th>
										<th class="col-md-1" style="text-align: center;">Amount</th>
										<th class="col-md-1" style="text-align: center;">Remark</th>
									</tr>

								</thead>
								<tbody>


									<%-- 	
							<form
								action="${pageContext.request.contextPath}/insertGrnProcess"
								name="grn" id="grn" method="post">
								 --%>

									<c:forEach items="${grnConfList}" var="grnConfList"
										varStatus="count">
										<tr>

										
											<td class="col-md-1"  style="text-align: center;"><fmt:formatDate pattern="dd-MM-yyyy" value="${grnConfList.billDate}" />
</td>
											<td class="col-md-1"  style="text-align: center;"><c:out
													value="${grnConfList.invoiceNo}"></c:out></td>
											<td class="col-md-3" style="text-align: center;"><c:out
													value="${grnConfList.itemName}"></c:out></td>
											<c:choose>
												<c:when test="${grnConfList.grnType==0}">
													<td class="col-md-1" style="text-align: center;"><c:out value="GRN 1(80%)"></c:out></td>
												</c:when>
												<c:when test="${grnConfList.grnType==1}">
													<td class="col-md-1" style="text-align: center;"><c:out value="GRN 2(70%)"></c:out></td>
												</c:when>
												<c:when test="${grnConfList.grnType==2}">
													<td class="col-md-1" style="text-align: center;"><c:out value="GRN 3(100%)"></c:out></td>
												</c:when>
												<c:when test="${grnConfList.grnType==3}">
													<td class="col-md-1"  style="text-align: center;"><c:out value="No GRN"></c:out></td>
												</c:when>

												<c:when test="${grnConfList.grnType==4}">
													<td class="col-md-1"  style="text-align: center;"><c:out value="GRN 3(100%)"></c:out></td>
												</c:when>
												<c:otherwise>
													<c:out value="No GRN"></c:out>
												</c:otherwise>

											</c:choose>
										
											<td class="col-md-1" style="text-align: center;"><c:out
													value="${grnConfList.autoGrnQty}"></c:out> <input
												type="hidden" name="grnqty${grnConfList.billDetailNo}"
												id="grnqty${grnConfList.billDetailNo}" size="3"
												readonly="readonly" value="${grnConfList.autoGrnQty}" /></td>

											<td class="col-md-1"  style="text-align: center;" id="grn_rate${grnConfList.billDetailNo}"><c:out
													value="${grnConfList.rate}"></c:out></td>
											

											<td class="col-md-1" style="text-align: center;"><input type="text"
												name="grnqtyauto${grnConfList.billDetailNo}"
												value="${grnConfList.autoGrnQty}" 
												id='grnqtyauto${grnConfList.billDetailNo}' size="3"
												onkeyup="calcGrn(${grnConfList.grnType},${grnConfList.rate},${grnConfList.itemId},
																	${grnConfList.sgstPer},${grnConfList.cgstPer},${grnConfList.autoGrnQty},${grnConfList.billDetailNo},${grnConfList.discPer},this.value)" />


											</td>

											<td class="col-md-1"  style="text-align: center;" id="tax_per${grnConfList.billDetailNo}"><c:out
													value="${grnConfList.taxPer}"></c:out></td>

											<fmt:formatNumber var="taxableAmt" type="number"
												minFractionDigits="2" maxFractionDigits="2"
												value="${grnConfList.taxableAmt}" />

										 <c:set var="taxableAmt" value="${taxableAmt}" />

											<td id='taxable_amt${grnConfList.billDetailNo}' style="text-align: center;" class="col-md-1">${taxableAmt}</td> 


											<td  style="text-align: center;" id='tax_amt${grnConfList.billDetailNo}' class="col-md-1">
											<fmt:formatNumber type="number"	minFractionDigits="2" maxFractionDigits="2"	value="${grnConfList.taxAmt}"/></td>

											<fmt:formatNumber var="grnAmt" type="number"
												minFractionDigits="2" maxFractionDigits="2"
												value="${grnConfList.grnAmt}" />

											<c:set var="grnAmt" value="${grnAmt}" />

											<td  style="text-align: center;" class="col-md-1" id="grn_amt${grnConfList.billDetailNo}"><c:out
													value="${grnAmt}"></c:out></td>

											<td id="grn_remark_other${grnConfList.billDetailNo}"  style="text-align: center;" class="col-md-1" ><select
												name="grn_remark${grnConfList.billDetailNo}" style="width: 200px" required="required"
												id="grn_remark${grnConfList.billDetailNo}" class="form-control" onchange="changeQty(${grnConfList.billDetailNo},${grnConfList.autoGrnQty},this.value)">
													<option  value="0">Goods Expired</option>
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
					</div>

					<input type="submit" class="btn btn-primary" id="submit"
						value="Save">
					


				</form>
			</div>
			<!--table end-->


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

<script type="text/javascript">
	
	
	/*  function showEdit(id,itemId,autoGrnQty) {
		
		 var x=$("#is_edit"+itemId).val();
		 
		if(x == 1){
		 
			 $("#grnqtyauto"+itemId).removeAttr("readonly"); 
	    
		}
		if(x== 0)
			{
			$("#grnqtyauto"+itemId).html(autoGrnQty);

			 $("#grnqtyauto" +itemId).setAttr("readonly"); 
			// document.getElementById("grnqtyauto"+itemId).prop="disabled"; 
			
			}
	} */
	</script>
	
	<script type="text/javascript">
	
	function changeQty(billDetailNo,autoQty,remark){
	
		
	//	var remark = document.getElementById("grn_remark"+billDetailNo).value;
    /* 	 if(remark=='Other'){
   		$("#grn_remark_other"+billDetailNo).append("<input type='text'  required id='grn_remark_text"+billDetailNo+"' name='grn_remark_text"+billDetailNo+"' >");
		  }
	  else{
		          $('#grn_remark_text'+billDetailNo).remove();
		  } */
		
	 //	alert(remark);
	 	if(remark==0){
			    document.getElementById("grnqtyauto"+billDetailNo).value=autoQty;
	 	}else{
			// $("#grnqtyauto"+billDetailNo).removeAttr("readonly"); //change
	 	}
		 
	}
	
	</script>

<script type="text/javascript">
	
	function calcGrn(grnType,rate,itemId,sgstPer,cgstPer,autoQty,billDetailNo,discPer,grnQty){
		
	
		var baseRate=rate*100/(sgstPer+cgstPer+100);
	
		var grnBaseRate;
		
		var grnRate;
		//var grnQty=$("#grnqtyauto"+billDetailNo).val();
		//alert(grnQty);
		if(parseInt(grnQty)>autoQty){
			alert("Edit Quantity can not be greater than Auto Quantity");
			document.getElementById("grnqtyauto"+billDetailNo).value=autoQty;
			//calcGrn(grnType,rate,itemId,sgstPer,cgstPer,autoQty)
		}else{
		
		if(grnType==0){
			
		
			/* $("#hidden_auto_qty"+itemId).html(grnQty);

			var hidden=$("#hidden_auto_qty"+itemId).val();
			alert(hidden); */
			
			var grnRate=$("#grn_rate"+billDetailNo).text();
		
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
			
			var grnRate=$("#grn_rate"+billDetailNo).text();
			
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
			
			
			var grnRate=$("#grn_rate"+billDetailNo).text();
			
			grnBaseRate=baseRate;
			grnRate=rate;
			
			   
			//var grnAmt=parseFloat(grnQty)*parseFloat(grnRate);
			
			//$("#grn_amt"+itemId).html(grnAmt.toFixed(2));
		
			}
			
			if(grnType==4){
				
				//var grnQty=$("#grnqtyauto"+itemId).val();
				var grnRate=$("#grn_rate"+billDetailNo).text();
				grnBaseRate=baseRate;
				grnRate=rate;
				
				}
			
		    var totTaxPer=parseFloat(sgstPer)+parseFloat(cgstPer);
			var taxableAmt=grnBaseRate*grnQty;
			var discAmt=(taxableAmt*discPer)/100;
			//alert("taxable " +taxableAmt + "discAmt " +discAmt);
			
			taxableAmt=taxableAmt-parseFloat(discAmt);
			//alert("taxable new " +taxableAmt);
			var totalTax=taxableAmt*(cgstPer+sgstPer)/100;
			
			var grandTotal=taxableAmt+totalTax;
			/* alert(taxableAmt);
			alert(totalTax);
			alert(grandTotal);
			 */
		//$("#grn_rate"+itemId).html(baseRate.toFixed(2));
		$("#grn_amt"+billDetailNo).html(grandTotal.toFixed(2));

		$("#tax_per"+billDetailNo).html(totTaxPer.toFixed(2));
		
		$("#taxable_amt"+billDetailNo).html(taxableAmt.toFixed(2));

		$("#tax_amt"+billDetailNo).html(totalTax.toFixed(2));

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

function getGrnData(id){
	
	
	var grnQty=$("#grnqtyauto"+id).val();
	var itemId=id;
	
	//alert(grnQty);
	//alert(itemId);
	
	$.getJSON('${getGrnData}',
			{
		
			qty:grnQty,
			id:itemId,
				 
				ajax : 'true',

           }
);
}
	
</script>


	

</body>
</html>
