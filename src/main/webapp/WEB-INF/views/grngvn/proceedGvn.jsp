<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
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
					<div class="col-md-12">
						<h2 class="pageTitle">Proceed GVN</h2>
					</div>
				</div>

				<br />

				<div class="row">
					<div class="col-md-12">
						<!--table-->
						<form
							action="${pageContext.request.contextPath}/getGvnBillDetails"
							name="grn" id="grn" method="get">

			<%-- 				<div class="colOuter">
								<div class="col-md-2">
									<h4>Select Bill</h4>
								</div>

								<div class="col-md-3">
									<select name="bill_no" id="bill_no"
										style="width: 250px; background-color: white; height: 40px">
										<option value="0">--Select BILL--</option>

										<c:forEach items="${frBillList}" var="frBillList">
											<c:choose>

												<c:when test="${selctedBillNo == frBillList.billNo}">
													<option selected value="${frBillList.billNo}">Bill No- ${frBillList.billNo} Bill Date- ${frBillList.billDate}</option>
												</c:when>

												<c:otherwise>
													<option value="${frBillList.billNo}">Bill No- ${frBillList.billNo} Bill Date- ${frBillList.billDate}</option>
												</c:otherwise>

											</c:choose>
										</c:forEach>

									</select>

								</div>

								<br>


								<!-- 	<div class="form-group"> -->
								<div class="col-sm-9 col-sm-offset-6 col-lg-5 col-lg-offset-0">


									<!-- 								<div class="col-md-4">
 -->
									<button type="submit" class="buttonsaveorder"
										style="width: 100px; height: 40px">Search</button>
									<!--<button type="button" class="btn">Cancel</button>-->

								</div>
							</div> --%>
							<!-- </div> -->
						</form>

					</div>
					<!-- 						</form>
 -->
					<form action="${pageContext.request.contextPath}/addGvnProcess"
						name="proceed_gvn" id="proceed_gvn" enctype="multipart/form-data"
						method="post" onsubmit="return confirm('Do you really want to Proceed ?');">

						<div class="clearfix"></div>

						<div id="table-scroll" class="table-scroll">
							<div id="faux-table" class="faux-table" aria="hidden">
								<table id="table_grid1" class="main-table">
									<thead>
										<tr class="bgpink">
											<!-- <th class="col-md-1">Item Name</th>
											<th class="col-md-1">Purchase</th>
											<th class="col-md-1">QTY</th>
											<th class="col-md-1">Rate</th>
											<th class="col-md-1">Tax %</th>
											<th class="col-md-1">Amount</th>
											<th class="col-md-1">Remark</th>
											<th class="col-md-1">PHOTO 1</th>
											<th class="col-md-1">PHOTO 2</th>-->
										</tr>
									</thead>
									</table> 
							
							</div>
							<div class="table-wrap">
								<table id="table_grid" class="main-table">
									<thead>
										<tr class="bgpink">
											<th class="col-md-2" style="text-align: center;">Item Name</th>
											<th class="col-md-1" style="text-align: center;">Purchase</th>
											<th class="col-md-1" style="text-align: center;">GVN Qty</th>
											<th class="col-md-1" style="text-align: center;">Rate</th>
											<th class="col-md-1" style="text-align: center;">Tax %</th>
											<th class="col-md-1" style="text-align: center;">Amount</th>
											<th class="col-md-1" style="text-align: center;">Remark</th>
											<th class="col-md-1" style="text-align: center;">PHOTO 1</th>
											<th class="col-md-1"style="text-align: center;" >PHOTO 2</th>
										</tr>
									</thead>
									<tbody>

										<c:forEach items="${tempGvnList}" var="gvnConfList"
											varStatus="count">

											<input type="hidden" id="b_qty${gvnConfList.billDetailNo}"
												value="${gvnConfList.billQty}" />

											<tr>

												<td class="col-md-2" style="text-align: left;">${gvnConfList.itemName}</td>
												<td class="col-md-1" style="text-align: center;">${gvnConfList.billQty}</td>
												<td class="col-md-1" style="text-align: center;">${gvnConfList.autoGrnQty}</td>

												<td class="col-md-1" style="text-align: center;">${gvnConfList.rate}</td>

												<td class="col-md-1" style="text-align: center;" id="tax_per${gvnConfList.billDetailNo}"><c:out
														value="${gvnConfList.sgstPer + gvnConfList.cgstPer}"></c:out></td>

												<c:set var="taxPer"
													value="${gvnConfList.sgstPer + gvnConfList.cgstPer}" />
												<c:set var="taxableAmt"
													value="${gvnConfList.calcBaseRate * gvnConfList.autoGrnQty}" />

												<c:set var="totalTax" value="${taxableAmt * taxPer/100}" />

												<c:set var="grandTotal" value="${taxableAmt+ totalTax}" />

												<td class="col-md-1" style="text-align: center;" id="gvn_amt${gvnConfList.billDetailNo}">
													<fmt:formatNumber value="${grandTotal}" type="number"
														maxFractionDigits="2" />
												</td>

												<td class="col-md-1" style="text-align: center;"  id="gvn_remark_other${gvnConfList.billDetailNo}"><select
													name="gvn_remark${gvnConfList.billDetailNo}" style="width: 200px"
													id="gvn_remark${gvnConfList.billDetailNo}" class="form-control" required="required" onchange="getDynamicTextRemark(this.value,${gvnConfList.billDetailNo})">
														<option selected value="">Select Remark</option>
														<c:forEach items="${remarkList}" var="remarkList">
																 
																<option value="${remarkList.remark}">${remarkList.remark}</option>
														</c:forEach>
												</select></td>

												<td class="col-md-1" style="text-align: center;">

													<div class="form-group">


														<div class="fileUpload">
															<input class="upload upld" type='file' name="gvn_photo1" 
																id="gvn_photo1${gvnConfList.billDetailNo}"
																data-rule-required="true" /> <img style="width: 45px"
																src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=no+image"
																alt="" />
														</div>


														<!-- <a href="#" data-dismiss="fileupload"><i
															class="fa fa-close"></i></a>
 -->
													</div> <!-- </div> -->
												</td>

												<td class="col-md-1" style="text-align: center;">
													<div class="form-group">
														<div>
															<div class="fileUpload">
																<input class="upload upld" type='file' name="gvn_photo2" 
																	id="gvn_photo2${gvnConfList.billDetailNo}"
																	data-rule-required="true" /> <img style="width: 45px"
																	src="http://www.placehold.it/200x150/EFEFEF/AAAAAA&amp;text=no+image"
																	alt="" />
															</div>


														</div>


													</div> <!-- 								</div>
 -->
												</td>

											</tr>

										</c:forEach>

									</tbody>

								</table>
							</div>
						</div>

<br>
						<div class="form-group">

							<button type="submit" class="buttonsaveorder" onclick="callSubmit()">
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

<script>
	document.getElementById("order_photo1").onchange = function() {
		var reader = new FileReader();

		reader.onload = function(e) {
			// get loaded data and render thumbnail.
			document.getElementById("image").src = e.target.result;
		};

		// read the image file as a data URL.
		reader.readAsDataURL(this.files[0]);
	};
</script>

<script type="text/javascript">
	function readURL(input) {

		if (input.files && input.files[0]) {
			var reader = new FileReader();

			reader.onload = function(e) {
				$(input).next('img').attr('src', e.target.result);
			}

			reader.readAsDataURL(input.files[0]);
		}
	}

	$(".upld").change(function() {
		readURL(this);
	});
	
	/* function callSubmit(){
		
		alert("Hi");
		
		
		
		
			/*  var form = document.getElementById("proceed_gvn");
		    form.action ="addGvnProcess";
		    form.submit();
		 
		
	} */
</script>

<script>
function getDynamicTextRemark(remark,billDetailId){
	
	  if(remark==='Other'){
      		$("#gvn_remark_other"+billDetailId).append("<input type='text'  required id='gvn_remark_text"+billDetailId+"' name='gvn_remark_text"+billDetailId+"' style='width:100%; border-radius: 25px;   border-color: pink;padding-left:6px;' />");
		  }
	  else{
		          $('#gvn_remark_text'+billDetailId).remove();
		  }
}
</script>

</body>
</html>