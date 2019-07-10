<%@page contentType="text/html; charset=ISO8859_1"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.monginis.ops.constant.Constant"%>
<%@ page import="com.monginis.ops.model.Currency"%>

<%@ page import="java.lang.Math"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Credit Note</title>

</head>
<body>
	<c:forEach items="${crnPrint}" var="headerH" varStatus="count">
		<%-- 	<h4 align="center">Credit Note</h4>
    <h4 style="color:#000; font-size:16px; text-align:center; margin:0px;">${FACTORYNAME}</h4>
   <p style="color:#000; font-size:10px; text-align:center;margin:0px;">${FACTORYADDRESS}</p>
<br></br>  --%>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-left: 1px solid #313131; border-right: 1px solid #313131;">
			<tr>
				<td width="100%" colspan="12" align="left"
					style="border-top: 1px solid #313131; padding: 4px; font-size: 16px; font-weight: bold;"><img
					src="${pageContext.request.contextPath}/resources/images/monginislogo.png"
					alt="logo" width="70px" height="37px"></img>&nbsp;&nbsp;&nbsp;${Constant.FACTORYNAME}</td>

			</tr>
			<tr>
				<td width="100%" colspan="12" align="left"
					style="border-top: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">&nbsp;&nbsp;${FACTORYADDRESS}</td>

			</tr>
			<tr bgcolor="lightgrey">
				<td width="100%" colspan="12" align="center"
					style="border-top: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">CREDIT
					NOTE</td>

			</tr>
			<tr>
				<td width="30%" colspan="3" align="left"
					style="border-top: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">&nbsp;GSTIN
					Number&nbsp;&nbsp;&nbsp;: ${Constant.FACTORYGSTIN}</td>
				<td width="70%" colspan="6" align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;"></td>

			</tr>
			<tr>
				<td width="30%" colspan="3" align="left"
					style="border-top: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">&nbsp;Credit
					Note No.&nbsp;&nbsp;&nbsp;: ${headerH.creditHeader.crnNo}</td>
				<td width="70%" colspan="6" align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">&nbsp;Invoice
					No.&nbsp;&nbsp;&nbsp;: ${headerH.creditHeader.exVarchar1}</td>

			</tr>
			<tr>
				<td width="30%" colspan="3" align="left"
					style="border-top: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">&nbsp;Date&nbsp;&nbsp;&nbsp;:
					${headerH.creditHeader.crnDate}</td>
				<td width="70%" colspan="6" align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">&nbsp;Tax
					is Payble on reverse charges(Yes/No):No</td>

			</tr>
			<tr bgcolor="lightgrey">
				<td width="30%" colspan="3" align="left"
					style="border-top: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">&nbsp;Details
					Of Receiver</td>
				<td width="70%" colspan="6" align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">&nbsp;Details
					Of Returns of Goods</td>

			</tr>
			<tr>
				<td width="30%" colspan="3" align="left"
					style="border-top: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;"><b>${headerH.creditHeader.frName}</b>&nbsp;${headerH.creditHeader.frAddress}</td>
				<td width="70%" colspan="6" align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;"><b>${headerH.creditHeader.frName}</b>&nbsp;${headerH.creditHeader.frAddress}</td>

			</tr>

			<tr>
				<td width="30%" colspan="3" align="left"
					style="border-top: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">${Constant.CITY}
					Gr. ${Constant.STATE} 1</td>
				<td width="70%" colspan="6" align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">${Constant.CITY}
					Gr. ${Constant.STATE} 1</td>

			</tr>

			<tr>
				<td width="30%" colspan="3" align="left"
					style="border-top: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">GSTIIN:
					&nbsp;<b>${headerH.creditHeader.frGstNo}</b>
				</td>
				<td width="70%" colspan="6" align="left"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">GSTIIN:
					&nbsp;<b>${headerH.creditHeader.frGstNo}</b>&nbsp;&nbsp;&nbsp;&nbsp;State:
					&nbsp;<b>${Constant.STATE}</b>
				</td>

			</tr>
			<%--   <tr>
    <td  colspan="3" width="30%" style="border-top:1px solid #313131;padding:8px;color:#FFF; font-size:14px;">
       <p style="color:#000; font-size:13px; text-align:left;margin:0px;">To, &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>${headerH.creditHeader.frName}</b>&nbsp;${headerH.creditHeader.frAddress}</p>
        <p style="color:#000; font-size:13px; text-align:;left;margin:0px;">GSTIIN: &nbsp;<b>${headerH.creditHeader.frGstNo}</b>&nbsp;&nbsp;&nbsp;&nbsp;State: &nbsp;<b>Maharashtra</b><!-- &nbsp;&nbsp;&nbsp;&nbsp;<span> State:&nbsp;27 Maharashtra </span> --> </p>
    </td>

    <td  colspan="6" width="70%"style="border-top:1px solid #313131;border-left:1px solid #313131; padding:8px;color:#FFF; font-size:15px;">
        <p style="color:#000; font-size:13px; text-align:;left;margin:0px;">Pune Gr. Maharashtra 1 Credit Note No. : &nbsp;&nbsp;&nbsp;&nbsp;<b>${headerH.creditHeader.crnId}</b></p>
        <p style="color:#000; font-size:13px; text-align:left;margin:0px;">GSTIIN: &nbsp;<b>${headerH.creditHeader.frGstNo}</b>Date :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>${headerH.creditHeader.crnDate}</b></p>
    </td>
  </tr> --%>
			<tr>
				<c:choose>
					<c:when test="${headerH.creditHeader.isGrn==1}">
						<c:set var="type" value="GRN"></c:set>
					</c:when>
					<c:otherwise>
						<c:set var="type" value="GVN"></c:set>
					</c:otherwise>
				</c:choose>
				<td width="58.9%" colspan="4"
					style="border-top: 1px solid #313131; border-right: 1px solid #313131; padding: 7px; color: #FFF; font-size: 15px;">
					<p
						style="color: #000; font-size: 12px; text-align:; left; margin: 0px;">
						<b>Being the amount Credited to your Account towards ${type}
							as &nbsp; &nbsp;</b>
					</p>
				</td>

			</tr>
		</table>

		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-top: 1px solid #313131; border-right: 1px solid #313131">

			<tr bgcolor="lightgrey">
				<td rowspan="2" width="2%"
					style="border-bottom: 1px solid #313131; border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">No.</td>
				<td align="left" width="23%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 10px; text-align: left">Item
					Decription</td>
				<td align="center" width="5%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 0.2px; color: #000; font-size: 10px;">HSN
					Code</td>


				<td align="center" width="5%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Qty</td>
				<td align="center" width="5%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">UOM
				</td>
				<td align="center" width="5.3%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Rate</td>
				<td align="center" width="5.3%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Total</td>
				<td align="center" width="5.3%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Rtd%</td>
				<td align="center" width="5%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Value</td>
				<td align="center" width="10%" colspan="2"
					style="border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">
					CGST</td>
				<td align="center" width="10%" colspan="2"
					style="border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">SGST</td>
			</tr>
			<tr bgcolor="lightgrey">
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Rate%
				</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Rate%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>
			</tr>

			<c:set var="totalQty" value="0" />
			<c:set var="totalAmt" value="0" />
			<c:set var="totalCgst" value="0" />
			<c:set var="totalSgst" value="0" />

			<c:forEach items="${headerH.creditHeader.srNoDateList}" var="srNos">
				<tr>
					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>

					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">Ref
						No&nbsp; &nbsp;<b><c:out value="${srNos.srNo}"></c:out></b>&nbsp;
						&nbsp;&nbsp; &nbsp;&nbsp; &nbsp;<c:out value="${srNos.grnGvnDate}"></c:out>
					</td>

					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>

					<td align="left"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>
					<td align="center"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>
					<td align="center"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>
					<td align="center"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 0px;">-</td>
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 0px;">-</td>
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>
				</tr>
				<c:forEach items="${headerH.creditHeader.crnDetails}"
					var="crnDetail" varStatus="count1">

					<c:choose>
						<c:when test="${srNos.srNo eq crnDetail.grngvnSrno}">
							<tr>

								<td
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">${count1.index+1}</td>


								<td
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><c:out
										value="${crnDetail.itemName}"></c:out></td>

								<td
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><c:out
										value="${crnDetail.itemHsncd}"></c:out></td>

								<td align="right"
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">${crnDetail.grnGvnQty}</td>
								<td align="center"
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">${crnDetail.itemUom}</td>

								<td align="right"
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
										type="number" groupingUsed="false" maxFractionDigits="2"
										minFractionDigits="2" value="${crnDetail.baseRate}" /></td>
								<td align="right"
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;"><fmt:formatNumber
										type="number" groupingUsed="false" maxFractionDigits="2"
										minFractionDigits="2"
										value="${crnDetail.baseRate*crnDetail.grnGvnQty}" /></td>

								<td align="right"
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;">
									<c:choose>
										<c:when test="${headerH.creditHeader.isGrn==1}">
											<c:choose>
												<c:when test="${crnDetail.grnType==0}">20</c:when>
												<c:when test="${crnDetail.grnType==1}">10</c:when>
												<c:when test="${crnDetail.grnType==2}">0</c:when>
												<c:otherwise></c:otherwise>
											</c:choose>
										</c:when>
										<c:otherwise>0</c:otherwise>
									</c:choose>
								</td>
								<c:set var="totalQty" value="${totalQty+crnDetail.grnGvnQty}" />
								<td align="right"
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;"><fmt:formatNumber
										type="number" groupingUsed="false" maxFractionDigits="2"
										minFractionDigits="2" value="${crnDetail.taxableAmt}" /></td>
								<td align="right"
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;"><fmt:formatNumber
										type="number" groupingUsed="false" maxFractionDigits="2"
										minFractionDigits="2" value="${crnDetail.cgstPer}" /></td>
								<c:set var="totalAmt" value="${totalAmt+crnDetail.taxableAmt}" />
								<td align="right"
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
										type="number" groupingUsed="false" maxFractionDigits="2"
										minFractionDigits="2" value="${crnDetail.cgstRs}" /></td>
								<td align="right"
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
										type="number" groupingUsed="false" maxFractionDigits="2"
										minFractionDigits="2" value="${crnDetail.sgstPer}" /></td>
								<c:set var="totalCgst" value="${totalCgst+crnDetail.cgstRs}" />
								<td align="right"
									style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
										type="number" groupingUsed="false" maxFractionDigits="2"
										minFractionDigits="2" value="${crnDetail.sgstRs}" /></td>

								<c:set var="totalSgst" value="${totalSgst+crnDetail.sgstRs}" />

							</tr>
						</c:when>
						<%--   <c:otherwise>
      <tr>
           <td  style="border-left:1px solid #313131; padding:3px 5px;color:#000; font-size:0px;">-</td>
       
          <td style="border-left:1px solid #313131;  padding:3px 5px;color:#000; font-size:10px;"><c:out value="${srNo}"></c:out></td>
    
        <td style="border-left:1px solid #313131;  padding:3px 5px;color:#000; font-size:0px;">-</td>
    
    <td align="left" style="border-left:1px solid #313131;  padding:3px 5px;color:#000; font-size:0px;">-</td>
        <td align="center" style="border-left:1px solid #313131; padding:3px 5px;color:#000; font-size:0px;">-</td>
    <td align="right" style="border-left:1px solid #313131; padding:3px 5px;color:#000;font-size:0px;">-</td>
    <td align="right" style="border-left:1px solid #313131; padding:3px 4px;color:#000; font-size:0px;">-</td>
    <td align="right" style="border-left:1px solid #313131; padding:3px 4px;color:#000;font-size:0px;">-</td>
    <td align="right" style="border-left:1px solid #313131; padding:3px 5px;color:#000; font-size:0px;">-</td>
    <td align="right" style="border-left:1px solid #313131; padding:3px 5px;color:#000; font-size:0px;">-</td>
    <td align="right" style="border-left:1px solid #313131; padding:3px 5px;color:#000;font-size:0px;">-</td>
    </tr>
    </c:otherwise> --%>
					</c:choose>

				</c:forEach>
			</c:forEach>
			<tr>
				<td align="left"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td align="left"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b>Total</b></td>
				<td align="center"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${totalQty}" /></b></td>
				<td align="center"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td align="center"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>

				<td align="center"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td align="center"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${totalAmt}" /></b></td>
				<td align="center"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${totalCgst}" /></b></td>
				<td align="center"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${totalSgst}" /></b></td>
			</tr>
			<tr>

				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 10px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 12px;"><b>Gross
						Value:</b></td>
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" groupingUsed="false" minFractionDigits="2"
							maxFractionDigits="2" value="${totalAmt+totalCgst+totalSgst}" /></b></td>
			</tr>
			<tr>
				<fmt:formatNumber type="number" groupingUsed="false"
					minFractionDigits="0" maxFractionDigits="0"
					value="${totalAmt+totalCgst+totalSgst}" var="grossValue" />
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 10px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 12px;"><b>Round
						Off:</b></td>
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" minFractionDigits="2" groupingUsed="false"
							maxFractionDigits="2"
							value="${grossValue-(totalAmt+totalCgst+totalSgst)}" /></b></td>
				<%-- ${grossValue-(totalAmt+totalCgst+totalSgst)} --%>
			</tr>
			<tr>

				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: white; font-size: 10px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 10px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td
					style="border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 0px;">-</td>
				<td style="border-bottom: 1px solid #313131; font-size: 12px;"><b>Net
						Value:</b></td>
				<td align="right"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 12px;"><b><fmt:formatNumber
							type="number" minFractionDigits="0" maxFractionDigits="0"
							var="netValueFinal" value="${totalAmt+totalCgst+totalSgst}" />${netValueFinal}</b></td>
			</tr>

		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-right: 1px solid #313131">
			<tr bgcolor="lightgrey">
				<td width="100%" colspan="12" align="left"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; font-size: 10px; font-weight: bold;">Tax
					Summary</td>

			</tr>
			<tr bgcolor="lightgrey">
				<td rowspan="2" width="10%"
					style="border-bottom: 1px solid #313131; border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 5px; color: #000; font-size: 10px;">HSN</td>
				<td align="left" width="20%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 15px; color: #000; font-size: 10px; text-align: left">HSN
					Description</td>

				<td align="center" width="5%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Qty</td>
				<td align="center" width="10%" rowspan="2"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px;">Taxable
					Value</td>
				<td align="center" width="20%" colspan="2"
					style="border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">
					CGST</td>
				<td align="center" width="20%" colspan="2"
					style="border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 10px; text-align: center;">SGST</td>
				<td align="center" width="25%" rowspan="2"
					style="border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">HSN
					Total</td>

			</tr>
			<tr bgcolor="lightgrey">
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Rate%
				</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Rate%</td>
				<td align="center"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; border-bottom: 1px solid #313131; padding: 4px; color: #000; font-size: 10px;">Amount</td>
			</tr>
			<c:set var="hsnQty" value="0" />
			<c:set var="hsnTaxableAmt" value="0" />
			<c:set var="hsnTotalCgst" value="0" />
			<c:set var="hsnTotalSgst" value="0" />
			<c:set var="hsnTotal" value="0" />
			<c:forEach items="${headerH.creditHeader.crnDetailsSummaryList}"
				var="crnDetailsSummaryList" varStatus="cnt">


				<tr>
					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><c:out
							value="${crnDetailsSummaryList.itemHsncd}"></c:out></td>

					<td
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><c:out
							value="${crnDetailsSummaryList.itemHsncdesc}"></c:out></td>

					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;">${crnDetailsSummaryList.grnGvnQty}</td>
					<c:set var="hsnQty"
						value="${hsnQty+crnDetailsSummaryList.grnGvnQty}" />
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${crnDetailsSummaryList.taxableAmt}" /></td>
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${crnDetailsSummaryList.cgstPer}" /></td>
					<c:set var="hsnTaxableAmt"
						value="${hsnTaxableAmt+crnDetailsSummaryList.taxableAmt}" />
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${crnDetailsSummaryList.cgstRs}" /></td>
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${crnDetailsSummaryList.sgstPer}" /></td>
					<c:set var="hsnTotalCgst"
						value="${hsnTotalCgst+crnDetailsSummaryList.cgstRs}" />
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${crnDetailsSummaryList.sgstRs}" /></td>
					<td align="right"
						style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2"
							value="${crnDetailsSummaryList.taxableAmt+crnDetailsSummaryList.cgstRs+crnDetailsSummaryList.sgstRs}" /></td>
					<c:set var="hsnTotal"
						value="${hsnTotal+crnDetailsSummaryList.taxableAmt+crnDetailsSummaryList.cgstRs+crnDetailsSummaryList.sgstRs}" />
					<c:set var="hsnTotalSgst"
						value="${hsnTotalSgst+crnDetailsSummaryList.sgstRs}" />

				</tr>

			</c:forEach>
			<tr>
				<td
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>

				<td
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>

				<td align="right"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><b><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${hsnQty}" /></b></td>
				<td align="right"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 10px;"><b><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${hsnTaxableAmt}" /></b></td>
				<td align="right"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 4px; color: #000; font-size: 0px;">-</td>
				<td align="right"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><b><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${hsnTotalCgst}" /></b></td>
				<td align="right"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 0px;">-</td>
				<td align="right"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><b><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${hsnTotalSgst}" /></b></td>
				<td align="right"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 3px 5px; color: #000; font-size: 10px;"><b><fmt:formatNumber
							type="number" groupingUsed="false" maxFractionDigits="2"
							minFractionDigits="2" value="${hsnTotal}" /></b></td>


			</tr>
		</table>
		<table width="100%" border="0" cellpadding="0" cellspacing="0"
			style="border-right: 1px solid #313131;">

			<%--  <tr>
    <td colspan="6" width="50%" style="border-left:1px solid #313131; padding:8px;color:#000; font-size:12px;">
     <p style="color:#000; font-size:12px; text-align:left;margin:0px;">Narration<br>Being ${type} For the period of the dated ${headerH.creditHeader.fromDate} to ${headerH.creditHeader.toDate}</p>
</td>
    <td colspan="5" width="38%" rowspan="2" style="border-left:1px solid #313131; padding:8px;color:#000;font-size:15px;">&nbsp;</td>
  </tr> --%>

			<tr>
				<td colspan="6" width="50%"
					style="border-left: 1px solid #313131; padding: 8px; color: #000; font-size: 15px;">&nbsp;In
					Words-<b>${Currency.convertToIndianCurrency(netValueFinal)}</b>
				</td>
			</tr>


			<tr>

				<td colspan="12" width="100%"
					style="border-top: 1px solid #313131; border-left: 1px solid #313131; padding: 8px; color: #000; font-size: 15px;">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px; text-align: right;">
						<b>For ${Constant.FACTORYNAME}&nbsp;</b>
					</p>
				</td>
			</tr>

			<tr>
				<td colspan="7" width="90%"
					style="border-bottom: 1px solid #313131; border-left: 1px solid #313131; padding: 10px; color: #000; font-size: 11px;">
					<p
						style="color: #000; font-size: 11px; text-align: left; margin: 0px;">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Subject
							to Pune Jurisdiction</b>
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>Receiver's
							Signature</b>
					</p>
				</td>
				<td align="center" colspan="4" width="38%"
					style="border-bottom: 1px solid #313131; padding: 10px; color: #000; font-size: 11px; text-align: right;"><b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Authorised
						Signature</b></td>
			</tr>

		</table>

		<div style="page-break-after: always;"></div>
	</c:forEach>

</body>
</html>