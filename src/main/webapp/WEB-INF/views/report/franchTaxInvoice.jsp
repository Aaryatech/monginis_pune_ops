<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.monginis.ops.constant.Constant" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Customer Bill</title>
<html lang="en">
<head>
<meta charset="UTF-8" />
<title></title>

</head>
<style type="text/css">
.style2 {
	font-size: 14px
}

.style5 {
	font-size: 10px
}

.style6 {
	font-size: 9px
}

.style7 {
	font-size: 12px;
	font-weight: bold;
}

.style8 {
	font-size: 11px;
	font-weight: bold;
}
-->
</style>
</head>

</head>
<body>

	<table width="250" border="0" cellspacing="0" cellpadding="0"
		style="padding: 2px; font-family: verdana; font-size: 11px; border: 1px solid #E7E7E7;">
		<tbody>
			<tr>
				<td colspan="2" align="center" style="padding:2px;border-bottom: 1px solid #E7E7E7;font-size: 12px;"><b>TAX
						INVOICE</b></td>
			</tr>
			<tr>
				<td colspan="2" align="center"
					style="padding: 2px; border-bottom: 1px solid #E7E7E7;">
						<b>${billList[0].frName}</b><br /> <span
							style="font-size: 10px; font-family: Arial;">(The Monginis
							Cake Shop)</span>
					</td>
			</tr>
			<tr>
				<td colspan="2" align="center"
					style="padding:2px; font-family: Arial; border-bottom: 1px solid #E7E7E7; font-size: 8px;">${billList[0].frAddress}
						<br /> Phone:<strong>${billList[0].frMob}</strong><br /> 
						<span style="font-size: 8px; font-family: Arial;">GSTIN:<b> ${billList[0].gstn}</b> State: ${Constant.STATE} <br>FSSAI :
							11515031000866
						</span></td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="2">
						<tbody>
							<tr>
								<td align="left">Inv No:</td>
								<td align="left">${billList[0].invoiceNo}</td>
								<td>Date:</td>
								 <fmt:parseDate pattern="yyyy-MM-dd" value="${billList[0].billDate}" var="dateparsed" />
      
								<td><fmt:formatDate pattern="dd-MM-yyyy" value="${dateparsed}" /></td>
							</tr>
							<tr>
								<td>Name</td>
								<td colspan="3">${billList[0].custName}</td>

							</tr>
							<tr>
        <td>GST No:</td>
      <td colspan="3">${billList[0].userGstNo}</td>
      </tr>
							<tr>
								<td colspan="4"><table width="100%" border="0"
										cellspacing="0" cellpadding="4" class="tbl-inner">
										<tbody>
											<tr>
												<th width="43%" align="left" bgcolor="#ECECEC">Item <span
													style="font-size: 8"> (HSN Code)</span></th>
												<th width="8%" bgcolor="#ECECEC">GST</th>
												<th width="8%" bgcolor="#ECECEC">Qty</th>
												<th width="13%" bgcolor="#ECECEC">Rate</th>
												<th width="29%" align="center" bgcolor="#ECECEC">Amt</th>
											</tr>
											<c:forEach items="${billList}" var="billList"
												varStatus="count">
												<tr>
													<td><span style="font-size: 11px">${billList.itemName}<br />
															<span style="font-size: 8px">HSN- ${billList.hsnCode}</span>
														</span></td>
													<td align="center"><span style="font-size: 11px">${billList.cgstPer+billList.sgstPer}%</span></td>
													<td align="center"><span style="font-size: 11px">${billList.qty}</span></td>
													<td align="center"><span style="font-size: 11px">${billList.mrp}</span></td>
													<td align="right"><span style="font-size: 11px"> <fmt:formatNumber type="number"
			maxFractionDigits="2" minFractionDigits="2" value=" ${billList.qty*billList.mrp}"/></span></td>
												</tr>
											</c:forEach>
											<tr>
												<td rowspan="3">&nbsp;</td>
												<td colspan="3" align="right"><span class="style5"><strong>Total
															:</strong></span></td>
												<td align="right"><span class="style5"><strong><c:choose><c:when test="${billType eq 'R'}">${billList[0].discountAmt}</c:when><c:otherwise>${billList[0].intBillAmt}</c:otherwise> </c:choose> </strong></span></td>
											</tr>
											<tr>
												<td colspan="3" align="right"><span class="style5"><strong>Discount
															${billList[0].discountPer}% :</strong></span></td>
												<td colspan="2" align="right"><span class="style5"><strong>${billList[0].intDiscAmt}</strong></span></td>

											</tr>
											<tr>
												<td colspan="3" align="right"><span class="style7">Bill
														Total:</span></td>
												<td align="right"><span class="style7"><c:choose><c:when test="${billType eq 'R'}">${billList[0].discountAmt-billList[0].intDiscAmt}</c:when><c:otherwise>${billList[0].intBillAmt}</c:otherwise> </c:choose></span></td>
											</tr>
										</tbody>
									</table></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2"><table width="100%" border="0" cellspacing="0"
						cellpadding="7">
						<tr>
							<th width="17%" align="left" bgcolor="#ECECEC" rowspan="2">Taxable<br />
								Value
							</th>
							<th bgcolor="#ECECEC" colspan="2">CGST</th>
							<th bgcolor="#ECECEC" colspan="2">SGST</th>
							<th width="25%" align="center" bgcolor="#ECECEC">Total</th>
						</tr>

						<tr>
							<th width="14%" bgcolor="#ECECEC">%</th>
							<th width="15%" bgcolor="#ECECEC">Amt</th>
							<th width="16%" bgcolor="#ECECEC">%</th>
							<th width="13%" bgcolor="#ECECEC">Amt</th>
							<th width="25%" align="center" bgcolor="#ECECEC">GST</th>
						</tr>
						<c:set var="taxAmount" value="${0}" />
						<c:set var="totaltax" value="${0 }" />
						<c:set var="cgst" value="${0 }" />
						<c:set var="sgst" value="${0 }" />
						<c:forEach items="${custBilltax}" var="custBilltax"
							varStatus="count">
							<tr>
								<td><fmt:formatNumber type="number"
										maxFractionDigits="2" value="${custBilltax.taxableAmt}"/></td>
								<c:set var="taxAmount"
									value="${taxAmount + custBilltax.taxableAmt}" />
								<td>${custBilltax.cgstPer}</td>
								<td><fmt:formatNumber type="number"
										maxFractionDigits="2" value="${custBilltax.cgstRs}"/></td>
								<c:set var="cgst" value="${cgst+custBilltax.cgstRs }" />
								<td>${custBilltax.sgstPer}</td>
								<td><fmt:formatNumber type="number"
										maxFractionDigits="2" value="${custBilltax.sgstRs}"/></td>
								<c:set var="sgst" value="${sgst+custBilltax.sgstRs }" />
								<td align="center"><fmt:formatNumber type="number"
										maxFractionDigits="2" value="${custBilltax.cgstRs+custBilltax.sgstRs}"/></td>
								<c:set var="totaltax"
									value="${totaltax+custBilltax.sgstRs+custBilltax.cgstRs }" />
							</tr>
						</c:forEach>
						
						<tr>
							<td bgcolor="#ECECEC"><b><fmt:formatNumber type="number"
										maxFractionDigits="2" value="${taxAmount}" /></b></td>

							<td bgcolor="#ECECEC"></td>
							<td bgcolor="#ECECEC"><fmt:formatNumber type="number"
									maxFractionDigits="2" value="${cgst}" /></td>
							<td bgcolor="#ECECEC"></td>
							<td bgcolor="#ECECEC"><fmt:formatNumber type="number"
									maxFractionDigits="2" value="${sgst}" /></td>
							<td align="center" bgcolor="#ECECEC"><fmt:formatNumber
									type="number" maxFractionDigits="2" value="${totaltax}" /></td>
						</tr>
						<tr>
							<td align="center"
								style="border-top: 1px solid #E7E7E7; padding: 3px;"
								colspan="6"><span style="font-weight: bold;">Thank You, Visit Again
									!!!
									</span></td>
						</tr>
						<tr>
							<td style="border-top: 1px solid #E7E7E7; padding: 2px;"
								colspan="6">We declare that this invoice shows the actual
								price of the goods<br /> described and that all particulars are
								true and correct.<br /> <span style="font-size: 10px"> I/
									We hereby certify that food/oods mentioned in this invoice are
									warranted to be the nature and quality which this
									purports/purport to be.</span>
							</td>
						</tr>
					</table></td>
			</tr>
			<tr>
				<td width="200" align="center"
					style="border-top: 1px solid #E7E7E7; padding: 5px 7px;"><strong>For
						${billList[0].frName}</strong></td>
			</tr>
		</tbody>
	</table>
</body>
	<body onload="directPrint()">
	<script>
	function directPrint()
	{
		//alert("JJ");
		window.print();
		window.close();
	}
	
	</script>
</body>

</html>