<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<link rel="stylesheet"
	href="${pageContext.request.contextPath}/resources/css/tableSearch.css">
<style>
table, th, td {
	border: 1px solid #9da88d;
}
</style>
<style>
.alert1 {
	padding: 10px;
	background-color: #f44336;
	color: white;
}

.closebtn {
	margin-left: 25px;
	color: white;
	font-weight: bold;
	float: right;
	font-size: 18px;
	line-height: 10px;
	cursor: pointer;
	transition: 0.3s;
}

.closebtn:hover {
	color: black;
}

.form-control {
	text-align: left !important;
}
</style>

<body onload="getData()">

	<jsp:include page="/WEB-INF/views/include/header.jsp"></jsp:include>
	<link rel="stylesheet"
		href="${pageContext.request.contextPath}/resources/css/loader.css">
	<!--datepicker-->
	<script type="text/javascript"
		src="${pageContext.request.contextPath}/resources/js/jquery-ui.js"></script>
	<script>
		$(function() {
			$("#fromdatepicker").datepicker({
				dateFormat : 'dd-mm-yy'
			});
		});
		$(function() {
			$("#todatepicker").datepicker({
				dateFormat : 'dd-mm-yy'
			});
		});
	</script>
	<!--datepicker-->


	<c:url var="getPettyCashData" value="/getPettyCashData"></c:url>


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
			<div class="wrapperIn2 single">

				<!--leftNav-->

				<jsp:include page="/WEB-INF/views/include/left.jsp">
					<jsp:param name="myMenu" value="${menuList}" />
				</jsp:include>




				<!--rightSidebar-->
				<div class="sidebarright">
					<div class="order-left">
						<h2 class="pageTitle">Add Expense</h2>
						<!--<h3 class="pageTitle2">Order Date : 22-02-2017 </h3>-->
					</div>
					<div class="order-right" align="right"
						style="padding-top: 2%; padding-bottom: 2%;">

						<a href="${pageContext.request.contextPath}/showExpenseList"><button
								class="buttonsaveorder">Expense List</button></a>
					</div>

					<form name="frm_search" id="frm_search" method="post"
						enctype="multipart/form-data"
						action="${pageContext.request.contextPath}/addSubmitExpense">

						<input type="hidden" name="expId" value="${expEdit.expId}">

						<div class="row">

							<div class="col-md-1">
								<div class="col1title" align="left">Chalan No*</div>
							</div>
							<div class="col-md-3">
								<c:if test="${isEdit==0}">
									<input id="itemCode" class="form-control"
										placeholder="Chalan No" name="chalanNo" autocomplete="off"
										readonly="readonly" value="${chSeq}" type="text" required>
								</c:if>


								<c:if test="${isEdit==1}">
									<input id="itemCode" class="form-control"
										placeholder="Chalan No" name="chalanNo" autocomplete="off"
										readonly="readonly" value="${expEdit.chalanNo}" type="text"
										required>
								</c:if>
							</div>

							<div class="col-md-2"></div>

							<div class="col-md-1">
								<div class="col1title" align="left" style="text-align: right;">Date*</div>
							</div>
							<div class="col-md-3">
								<c:choose>
									<c:when test="${not empty expEdit.expDate}">
										<input id="fromdatepicker" class="form-control" required
											placeholder=" Date" name="fromdatepicker"
											value="${expEdit.expDate}" autocomplete="off" type="text"
											readonly="readonly">
									</c:when>
									<c:otherwise>
										<input id="fromdatepicker" class="form-control" required
											placeholder=" Date" name="fromdatepicker"
											value="${todaysDate}" autocomplete="off" type="text"
											readonly="readonly">
									</c:otherwise>
								</c:choose>
							</div>

							<div class="col-md-2"></div>

						</div>

						<br>

						<div class="row">

							<div class="col-md-1">
								<div class="col1title" align="left">Type*</div>
							</div>
							<div class="col-md-3">
								<select name="isActive" id="isActive" class="form-control"
									data-rule-required="true">
									<c:forEach items="${typeList}" var="type">
									<option value="${type.expTypeId}"  ${expEdit.expType == type.expTypeId ? 'selected' : ''} >${type.expTypeName}</option>
									</c:forEach>
<%-- 									<option value="1" ${expEdit.expType == 1 ? 'selected' : ''}>Regular</option>
									<option value="2" ${expEdit.expType == 2 ? 'selected' : ''}>Payment
										Chalan</option> --%>

								</select>
							</div>

							<div class="col-md-2"></div>

							<div class="col-md-1">
								<div class="col1title" align="left" style="text-align: right;">Amount*</div>
							</div>
							<div class="col-md-3">
								<input id="amount" class="form-control" placeholder="Amount"
									value="${expEdit.chAmt}" autocomplete="off" name="amount"
									type="text"
									oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');"
									required>
							</div>

							<div class="col-md-2"></div>

						</div>

						<br>

						<div class="row">

							<div class="col-md-1">
								<div class="col1title" align="left">Remark</div>
							</div>
							<div class="col-md-3">
								<textarea id="remark" class="form-control" autocomplete="off"
									name="remark" onkeyup="changeTax()">${expEdit.remark}</textarea>
							</div>

							<div class="col-md-2"></div>

							<div class="col-md-1">
								<div class="col1title" align="left" style="text-align: right;">Image</div>
							</div>
							<div class="col-md-3">

								<input type="file" class="btn btn-primary btn-file legitRipple"
									data-fouc="" id="1" name="photo"> <input type="hidden"
									value="${expEdit.imgName}" name="profPic"> <img
									id="blah" src="#" /> <input name="btnClear" value="Clear"
									type="button" id="btnClear" onclick="clearImage()" style="display: none;">
									
									
									

							</div>

							<div class="col-md-2"></div>

						</div>

						<br>

						<div class="row">

							<div class="col-md-12" style="text-align: center;">
								<input name="submit" class="buttonsaveorder" value="Submit"
									type="submit" id="submtbtn">
							</div>
						</div>


					</form>




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
	<!--easyTabs-->
	<script src="${pageContext.request.contextPath}/resources/js/main.js"></script>
	<!--easyTabs-->


	<script type="text/javascript">
		function readURL(input) {
			if (input.files && input.files[0]) {
				var reader = new FileReader();

				reader.onload = function(e) {
					$('#blah').attr('src', e.target.result);
				}

				reader.readAsDataURL(input.files[0]);
			}
		}

		$("#1").change(function() {
			readURL(this);
			document.getElementById("btnClear").style.display = "block";
		});
	</script>


	<script type="text/javascript">
		function clearImage() {

			document.getElementById("1").value = "";
			$('#blah').attr('src', '#');
			document.getElementById("btnClear").style.display = "none";
		}
	</script>

</body>

</html>