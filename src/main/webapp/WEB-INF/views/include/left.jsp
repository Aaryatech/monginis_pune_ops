<%@page import="com.monginis.ops.model.Menu"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.time.LocalTime"%>
<%@ page import="java.time.ZoneId"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.SimpleDateFormat"%>
<style type="text/css">
.topSlide2 {
	padding: 20px 60px 20px 20px;
}
</style>
<div class="sidebarhome">
	<div class="content mCustomScrollbar">
		<ul>
			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Order Booking'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if
				test="${(info.accessRight==1 or info.accessRight==2) && flag==1 }">
				<li style="border: 1px;"><a href="#" onClick="openNav()"><div
							class="img">
							<img
								src="${pageContext.request.contextPath}/resources/images/nav-orderbook.png"
								alt="img">
						</div>
						<div class="title">
							Order <span>Booking</span>
						</div></a></li>
			</c:if>

			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Express Bill'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if test="${flag==1}">
				<li><a
					href="${pageContext.request.contextPath}/showExpressBill"><div
							class="img">
							<i class="fa fa-file-pdf-o icon"
								style="font-size: 22px !important;"></i>
						</div>
						<div class="title">
							Express Bill <span></span>
						</div></a></li>
			</c:if>
			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Customer Bill'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if test="${  flag==1 }">
				<li><a
					href="${pageContext.request.contextPath}/showCustomerBill"><div
							class="img">
							<i class="fa fa-file-pdf-o icon"
								style="font-size: 22px !important;"></i>
						</div>
						<div class="title">
							Customer Bill <span></span>
						</div></a></li>
			</c:if>
			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Goods Return'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if
				test="${(info.accessRight==1 or info.accessRight==2) && flag==1}">
				<li><a href="#" onClick="openNav1()"><div class="img">
							<img
								src="${pageContext.request.contextPath}/resources/images/nav-goodsreturn.png"
								alt="img">
						</div>
						<div class="title">
							Goods <span>Return</span>
						</div></a></li>
			</c:if>
			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Order History'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if
				test="${(info.accessRight==1 or info.accessRight==2 or info.accessRight==3) && flag==1}">

				<li><a href="${pageContext.request.contextPath}/orderHistory"><div
							class="img">
							<i class="fa fa-file-text-o icon"
								style="font-size: 22px !important;"></i>
						</div>
						<div class="title">
							Order <span>History</span>
						</div></a></li>



			</c:if>

			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'View Purchase Bills'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if test="${flag==1}">
				<li><a href="${pageContext.request.contextPath}/showBill"><div
							class="img">
							<i class="fa fa-money icon" style="font-size: 22px !important;"></i>
						</div>
						<div class="title">
							View Purchase <span>Bills</span>
						</div></a></li>
			</c:if>


			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Other Item Stock'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if test="${flag==1}">
				<li><a
					href="${pageContext.request.contextPath}/showOthItemStock"><div
							class="img">
							<i class="fa fa-file-pdf-o icon"
								style="font-size: 22px !important;"></i>
						</div>
						<div class="title">Other Item Stock</div></a></li>
			</c:if>

			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Other Purchase Bill'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if test="${ flag==1}">
				<li><a href="${pageContext.request.contextPath}/showOtherBill"><div
							class="img">
							<i class="fa fa-file-pdf-o icon"
								style="font-size: 22px !important;"></i>
						</div>
						<div class="title">Other Purchase Bill</div></a></li>
			</c:if>

			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Reports'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if test="${info.accessRight==1  && flag==1}">

				<li><a href="#" onClick="openNav4()"><div class="img">
							<i class="fa fa-file-o icon" style="font-size: 22px !important;"></i>
						</div>
						<div class="title">
							<span>Reports</span>
						</div></a></li>
			</c:if>

			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Rule And Regulation'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if test="${ flag==1}">
				<li><a
					href="${pageContext.request.contextPath}/showRuleFilePdf"><div
							class="img">
							<i class="fa fa-file-pdf-o icon"
								style="font-size: 22px !important;"></i>
						</div>
						<div class="title">
							Rule And Regulation <span></span>
						</div></a></li>
			</c:if>




			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Stock Details'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>
			<c:if
				test="${(info.accessRight==1 or info.accessRight==2) && flag==1}">
				<li><a
					href="${pageContext.request.contextPath}/showstockdetail"><div
							class="img">
							<img
								src="${pageContext.request.contextPath}/resources/images//nav-orerhistory.png"
								alt="img">
						</div>
						<div class="title">
							Stock <span>Details</span>
						</div></a></li>
			</c:if>

			<c:set var="flag" value="${0}"></c:set>
			<c:forEach items="${setList}" var="setting" varStatus="count">
				<c:choose>

					<c:when test="${setting.settingKey eq 'Edit Profile'}">
						<c:set var="flag" value="${1}"></c:set>
					</c:when>


				</c:choose>
			</c:forEach>


			<c:if test="${info.accessRight==1 && flag==1}">

				<li><a
					href="${pageContext.request.contextPath}/showeditprofile"><div
							class="img">

							<img
								src="${pageContext.request.contextPath}/resources/images/nav-editprofile.png"
								alt="img">
						</div>
						<div class="title">
							Edit <span>Profile</span>
						</div></a></li>
			</c:if>
		</ul>
	</div>
</div>
<!--leftNav-->

<!--navInner-->

<!--1nav-->
<div id="mySidenav" class="sidenav">
	<div class="topSlide">
		<div class="topSlide2">
			<a href="javascript:void(0)" class="closebtn" onClick="closeNav()"><img
				src="${pageContext.request.contextPath}/resources/images/closebtn.png"
				alt="monginis"></a>
			<div class="listarea">
				<ul>

					<c:forEach var="menu" items="${menuList}" varStatus="loop">
						<c:set var="menuToTime" value="${menu.toTime}" />
						<c:set var="menuFromTime" value="${menu.fromTime}" />
						<c:set var="menuDesc" value="${menu.menuDesc}" />

						<c:set var="frId" value="${menu.frId}" />
						<c:set var="isSameDayApplicable"
							value="${menu.isSameDayApplicable}" />
						<c:set var="settingType" value="${menu.settingType}" />

						<c:set var="catId" value="${menu.catId}" />

						<%
							ZoneId z = ZoneId.of("Asia/Calcutta");
								LocalTime now = LocalTime.now(z); // Explicitly specify the desired/expected time zone.

								String menuToTiming = (String) pageContext.getAttribute("menuToTime");
								String menuFromTiming = (String) pageContext.getAttribute("menuFromTime");

								String menuDesc = (String) pageContext.getAttribute("menuDesc");

								SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
								SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
								Date fromTime12Hrs = _24HourSDF.parse(menuFromTiming);
								Date toTime12Hrs = _24HourSDF.parse(menuToTiming);

								// System.out.println(_24HourDt);
								//  System.out.println(_12HourSDF.format(_24HourDt));

								pageContext.setAttribute("fromTime", _12HourSDF.format(fromTime12Hrs));
								pageContext.setAttribute("toTime", _12HourSDF.format(toTime12Hrs));

								int isSameDayApplicable = (int) pageContext.getAttribute("isSameDayApplicable");
								int catId = (Integer) pageContext.getAttribute("catId");
								int settingType = (int) pageContext.getAttribute("settingType");

								System.out.println("\n\n\nMenu To Timing" + menuToTiming);
								System.out.println("Menu From Timing" + menuFromTiming);

								LocalTime toTime = LocalTime.parse(menuToTiming);
								LocalTime fromTime = LocalTime.parse(menuFromTiming);

								Boolean isLate = now.isAfter(toTime);
								Boolean isEarly = now.isBefore(fromTime);

								System.out.println("\nLocal time" + now + "Is Early :" + isLate);
								System.out.println("Local time" + now + "Is Late :" + isLate);

								/* 	try {
									    final SimpleDateFormat sdf = new SimpleDateFormat("H:mm");
									    final Date dateObj = sdf.parse(time);
									    System.out.println(dateObj);
									    System.out.println(new SimpleDateFormat("K:mm").format(dateObj));
									} catch (final ParseException e) {
									    e.printStackTrace();
									} */
								Boolean isSameDay = fromTime.isBefore(toTime);
								Boolean isValid = false;

								if (isSameDay) {
									System.out.println("in isSameDay if loop");

									if (!isLate && !isEarly) {

										isValid = true;
									}
								} else {
									System.out.println("in isSameDay else loop");

									if (now.isAfter(fromTime)) {
										isValid = true;
									} else if (toTime.isAfter(now)) {
										isValid = true;
									}
								}

								if (isValid) {
						%>

						<li>
							<%
								if (catId != 5) {

											if (isSameDayApplicable == 3) {
												// if
												if (settingType != 1) {
							%> <abbr title='${menu.menuDesc}'>
								<div class="listareaBoxBlue">
						</abbr> <a class="listareaBoximg"
							href="${pageContext.request.contextPath}/showRegularSpCakeOrder/${loop.index}">



								<%
									//else
													} else {
								%> <abbr title='${menu.menuDesc}'><a class="listareaBox"
									href="${pageContext.request.contextPath}/showRegularSpCakeOrder/${loop.index}"><div
											class="listareaBoximg"></abbr> <%
 	}

 				} else {
 					if (settingType != 1)

 					{

 						//if
 %> <abbr title='${menu.menuDesc}'><a class="listareaBoxBlue"
									href="${pageContext.request.contextPath}/showSavouries/${loop.index}">
										<div class="listareaBoximg"></abbr> <%
 	} else {
 %> <abbr title='${menu.menuDesc}'> <a class="listareaBox"
									href="${pageContext.request.contextPath}/showSavouries/${loop.index}">
										<div class="listareaBoximg"></abbr> <%
 	}

 				}
 			} else if (catId == 5) {

 				// if
 				if (settingType != 1) {
 %> <abbr title='${menu.menuDesc}'><a class="listareaBoxBlue"
									href="${pageContext.request.contextPath}/showSpCakeOrder/${loop.index}">
										<div class="listareaBoximg"></abbr> <%
 	//else
 				} else {
 %> <abbr title='${menu.menuDesc}'><a class="listareaBox"
									href="${pageContext.request.contextPath}/showSpCakeOrder/${loop.index}">
										<div class="listareaBoximg"></abbr> <%
 	}

 			}
 %> <img
								src="${pageContext.request.contextPath}/resources/images/${menu.menuImage}"
								alt="monginis"> <img
								src="${pageContext.request.contextPath}/resources/images/${menu.selectedMenuImage}"
								alt="monginis">
			</div>
			<h2>
				<c:out value='${menu.menuTitle}' />
			</h2>
			<h3>
				Booking -
				<c:out value='${fromTime}' />
				To
				<c:out value='${toTime}' />
			</h3>
			</a>
			</li>


			<%
				} else {
			%>


			<li>

				<div class="listareaBox" style="background-color: #eedfdf;">

					<a class="listareaBoximg"> <img
										src="${pageContext.request.contextPath}/resources/images/${menu.menuImage}"
										alt="monginis"> <img
										src="${pageContext.request.contextPath}/resources/images/${menu.selectedMenuImage}"
										alt="monginis"> </a>

				
									<h2 style="color:grey;">
										<c:out value='${menu.menuTitle}' />
									</h2>
								 	<h3 style="color:grey;">
										Booking - <c:out value='${fromTime}' /> To 
											<c:out value='${toTime}' />
									</h3>
					</a>
				</div>

			</li>
			<%
				}
			%>
			</c:forEach>




			</ul>
		</div>

	</div>
</div>
</div>
<!--1nav-->



<!--2nav-->
<div id="mySidenav1" class="sidenav">
	<div class="topSlide">
		<div class="topSlide2">
			<a href="javascript:void(0)" class="closebtn" onClick="closeNav1()"><img
				src="${pageContext.request.contextPath}/resources//images/closebtn.png"
				alt="monginis"></a>
			<div class="listarea">


				<ul>
					<li class="small-box">

						<div class="ibox">
							<div class="ibox-head">
								<div class="ibox-title">GRN</div>
							</div>


							<a href="${pageContext.request.contextPath}/getGrnBillDetail"><i
								class="fa fa-plus-circle icon"></i> Request GRN </a>
							<div class="clearfix"></div>
							<a href="${pageContext.request.contextPath}/displayGrn"><i
								class="fa fa-search-plus icon"></i> View GRN </a>
							<div class="clearfix"></div>

							<%-- <a href="${pageContext.request.contextPath}/getGrnBillDetail"><i class="fa fa-search-plus icon"></i> Manual GRN </a> --%>

						</div>
					</li>

					<li class="small-box">

						<div class="ibox">
							<div class="ibox-head">
								<div class="ibox-title">GVN</div>
							</div>


							<a href="${pageContext.request.contextPath}/showGvn"><i
								class="fa fa-plus-circle icon"></i> Request GVN </a> <a
								href="${pageContext.request.contextPath}/displayGvn"><i
								class="fa fa-search-plus icon"></i> View GVN </a>

						</div>

					</li>

					<%-- <li><div class="listareaBox">
								<a class="listareaBoximg"> <img
									src="${pageContext.request.contextPath}/resources/images/icon8.png"
									alt="monginis"> <img
									src="${pageContext.request.contextPath}/resources/images/icon8-h.png"
									alt="monginis"></a>
								<h2>GRN2</h2>
								<h3>Booking Up to 12.30 PM</h3>
								<div class="listareaBoxdown">
									<a href="order-savouries.html">Add <i class="fa fa-pencil"></i></a>
								</div>
								<div class="listareaBoxdown">
									<a href="order-savouries.html">Upload <i
										class="fa fa-cloud-upload"></i></a>
								</div>
							</div></li>

						<li><div class="listareaBox">
								<a class="listareaBoximg"> <img
									src="${pageContext.request.contextPath}/resources/images/icon9.png"
									alt="monginis"> <img
									src="${pageContext.request.contextPath}/resources/images/icon9-h.png"
									alt="monginis"></a>
								<h2>GVN</h2>
								<h3>At Any Time Till Shop Closing</h3>
								<div class="listareaBoxdown">
									<a href="order-savouries.html">Add <i class="fa fa-pencil"></i></a>
								</div>
								<div class="listareaBoxdown">
									<a href="order-savouries.html">Upload <i
										class="fa fa-cloud-upload"></i></a>
								</div>
							</div></li> --%>

				</ul>
			</div>

		</div>
	</div>
</div>
<!--2nav-->

<!--4nav-->
<div id="mySidenav4" class="sidenav">
	<div class="topSlide">
		<div class="topSlide2">
			<a href="javascript:void(0)" class="closebtn" onClick="closeNav4()"><img
				src="${pageContext.request.contextPath}/resources//images/closebtn.png"
				alt="monginis"></a>
			<div class="listarea">
				<ul>
					<%-- <li class="small-box">
						
							<div class="ibox">
						<div class="ibox-head">
						<div class="ibox-title">Punching Based Reports</div>
						</div>
						<a href="${pageContext.request.contextPath}/viewBillwiseSell"><i class="fa fa-files-o icon"></i>Billwise Sale Report</a>
						<a href="${pageContext.request.contextPath}/viewDatewiseSellBill"><i class="fa fa-calendar-o icon"></i>Datewise Sale Report</a>
						<a href="${pageContext.request.contextPath}/viewMonthwiseSellBill"><i class="fa fa-calendar-o  icon"></i>Monthwise Sale Report</a>
						<a href="${pageContext.request.contextPath}/viewItemwiseSellBill"><i class="fa fa-chevron-circle-down icon"></i>Categorywise- Itemwise Sale Report</a>
					<a href="${pageContext.request.contextPath}/viewDateItemwiseSellBill"> <i class="fa fa-chevron-circle-down icon"></i>Datewise-Itemwise Sale Report</a>
						
								
						</div>
						
					</li> --%>

					<li class="small-box">

						<div class="ibox">
							<div class="ibox-head">
								<div class="ibox-title">Purchase Reports</div>
							</div>

							<a
								href="${pageContext.request.contextPath}/viewBillWisePurchaseReport"><i
								class="fa fa-files-o icon"></i>Billwise Purchase Report</a> <a
								href="${pageContext.request.contextPath}/viewMonthWisePurchaseReport"><i
								class="fa fa-files-o icon"></i>Monthwise Purchase Report</a> <a
								href="${pageContext.request.contextPath}/viewItemWiseDetailReport"><i
								class="fa fa-files-o icon"></i>Itemwise-Billwise-Datewise
								Purchase Report</a> <a
								href="${pageContext.request.contextPath}/viewItemWiseReport"><i
								class="fa fa-files-o icon"></i>Itemwise Purchase Report</a> <a
								href="${pageContext.request.contextPath}/viewBillTaxPurchaseReport"><i
								class="fa fa-files-o icon"></i>Purchase Billwise Tax (Input)
								Report</a>

						</div>
					</li>
					<!-- 	
							<li class="small-box">
							
								<div class="ibox">
						<div class="ibox-head">
						<div class="ibox-title">Item wise</div>
						</div>
						
								
						</div>
						
							</li> -->

					<li class="small-box">


						<div class="ibox">
							<div class="ibox-head">
								<div class="ibox-title">GRN GVN Report</div>
							</div>
							<%-- <a href="${pageContext.request.contextPath}/viewFrTaxSellBill"><i class="fa fa-file-text-o icon"></i> Tax Report summary (Sell) </a>
						<a href="${pageContext.request.contextPath}/viewFrDatewiseTaxSellBill"><i class="fa fa-calendar icon"></i> Date Report (Sell) </a>
						<a href="${pageContext.request.contextPath}/viewFrBillwiseTaxSellBill"><i class="fa fa-files-o icon"></i> Bill Report (Sell)</a>
						<a href="${pageContext.request.contextPath}/hsnWiseReport"><i class="fa fa-files-o icon"></i> HSN Code wise Report (Sell)</a> --%>
							<a href="${pageContext.request.contextPath}/grnReport"><i
								class="fa fa-files-o icon"></i> GRN Report (Sell)</a> <a
								href="${pageContext.request.contextPath}/gvnReport"><i
								class="fa fa-files-o icon"></i> GVN Report (Sell)</a>



						</div>

					</li>

					<li class="small-box">

						<div class="ibox">
							<div class="ibox-head">
								<div class="ibox-title">Other Reports</div>
							</div>

							<a href="${pageContext.request.contextPath}/showSpAdvanceReport"><i
								class="fa fa-files-o icon"></i>SP Advance Report</a> <a
								href="${pageContext.request.contextPath}/showSpAdvTaxReport"><i
								class="fa fa-files-o icon"></i>Sp Advance Tax Report</a> <a
								href="${pageContext.request.contextPath}/showInsertCreditNote"><i
								class="fa fa-files-o icon"></i>Credit Note Report</a>

						</div>
					</li>


					<li class="small-box">

						<div class="ibox">
							<div class="ibox-head">
								<div class="ibox-title">Purchase Sale Reports</div>
							</div>

							<a href="${pageContext.request.contextPath}/showTaxReport"><i
								class="fa fa-files-o icon"></i>Tax Report 1</a> <a
								href="${pageContext.request.contextPath}/showTax2Report"><i
								class="fa fa-files-o icon"></i>Tax Report 2</a> <a
								href="${pageContext.request.contextPath}/showCRNoteRegister"><i
								class="fa fa-files-o icon"></i>Credit Note Register Report</a> <a
								href="${pageContext.request.contextPath}/showCRNoteRegisterDone"><i
								class="fa fa-files-o icon"></i>Credit Note Register Done Report</a>
							<a
								href="${pageContext.request.contextPath}/showHSNwiseReportBetDate"><i
								class="fa fa-files-o icon"></i>HSN wise Report Report</a> <a
								href="${pageContext.request.contextPath}/showGstRegister"><i
								class="fa fa-files-o icon"></i>GST Register Done Report</a>



						</div>
					</li>



				</ul>
			</div>

		</div>
	</div>
</div>
<!--4nav-->

<!--3nav-->
<%-- <div id="mySidenav3" class="sidenav">

		<div class="topSlide">
			<div class="topSlide2 textcen">
				<a href="javascript:void(0)" class="closebtn" onClick="closeNav3()"><img
					src="${pageContext.request.contextPath}/resources/images/closebtn.png"
					alt="monginis"></a>
				<div class="profileinsite">

					<div class="profileinsiteLeft">

						<div class="profile">
							<div class="profilefildset">Franchisee Name</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="franchisee Name"
									name="fr_name" type="text">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Franchisee Profile Pic</div>
							<div class="profileinput">
								<div class="editimg">
									<div class="editpics">
										<div class="fileUpload">
											<span><i class="fa fa-pencil"></i></span> <input type="file"
												class="upload" />
										</div>
									</div>
									<img
										src="${pageContext.request.contextPath}/resources/images/editimg.jpg"
										alt="img">
								</div>
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">City</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="City Name"
									name="fr_city" type="text">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Email id</div>
							<div class="profileinput">
								<input class="texboxitemcode" name="fr_email"
									placeholder="example@gmail.com" type="text">
							</div>
						</div>



					</div>

					<div class="profileinsiteRight">
						<div class="profile">
							<div class="profilefildset">Mobile No.</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="9876543201"
									name="fr_mobile" type="text">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Owner Name</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="Owner Name"
									name="fr_owner" type="text">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Shop Opening Date</div>
							<div class="profileinput mardis">26 Feb. 2017</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Edit Password</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="Edit Password"
									name="fr_password" type="text">
							</div>
						</div>

						<div class="profile">
							<div class="profilefildset">Confirm Password</div>
							<div class="profileinput">
								<input class="texboxitemcode" placeholder="Confirm Password"
									name="fr_password" type="text" data-rule-equalTo="#fr_password">
							</div>
						</div>


						<div class="profile">
							<div class="profileinput">
								<input name="" class="buttonsaveorder" value="SUBMIT"
									type="button">
							</div>
						</div>

					</div>

					<div class="messages messagesErr">err message</div>
					<div class="messages messagesInfo">info message</div>
					<div class="messages messagesSuccess">success message</div>


				</div>
			</div>
		</div>
	</div> --%>
<!--3nav-->

<!--navInner-->
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
	function openNav4() {
		document.getElementById("mySidenav4").style.width = "100%";
	}

	function closeNav4() {
		document.getElementById("mySidenav4").style.width = "0";
	}
</script>

<script
	src="${pageContext.request.contextPath}/resources/js/owl.carousel.min.js"></script>
<script>
	$(document).ready(function() {
		$('.imgpath').click(function() {
			$(this).hide();
			$('.videoWrapper').show();
			$(".sproutvideo-player")[0].src += "?autoplay=1";
		});
		var owl = $("#owl-example");
		owl.owlCarousel({
			items : 2,
			loop : false,
			autoplay : false,
		});

	});
</script>

<script type="text/javascript">
	function showWindow(fromTime, toTime) {
		confirm("Timeout:\n You can place order from " + fromTime + " To "
				+ toTime);
	}
</script>


<!-- custom scrollbar plugin added by kalpesh -->
<script type="text/javascript"
	src="${pageContext.request.contextPath}/resources/js/jquery.mCustomScrollbar.concat.min.js"></script>