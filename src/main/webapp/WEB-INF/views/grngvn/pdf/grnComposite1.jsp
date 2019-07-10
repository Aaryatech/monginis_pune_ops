<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.monginis.ops.constant.Constant" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta charset="UTF-8">
<title></title>

<style type="text/css">
<!--
.style2 {font-size: 14px}
.style5 {font-size: 10px}
.style6 {font-size: 9px}
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
<body>

				  <table width="250" border="0" cellspacing="0" cellpadding="0" style="padding:5px; font-family:verdana; font-size:12px; border:1px solid #E7E7E7;">
		<tbody>
			<tr style="height: 50px;">


				<c:choose>
					<c:when test="${grnPdf.type==1}">
						<td
							style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-bottom: 1px solid; border-left-color: #000000; border-top-color: #000000; border-bottom-color: #000000;"
							colspan=3><nobr> GOODS RETURN NOTE - GRN</nobr></td>
					</c:when>
					<c:otherwise>

						<td
							style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-bottom: 1px solid; border-left-color: #000000; border-top-color: #000000; border-bottom-color: #000000;"
							colspan=3><nobr> GOODS VARIATION NOTE - GVN</nobr></td>
					</c:otherwise>
				</c:choose>
				<td
					style="padding: 10px; font-family: Calibri; font-size: 20px; color: #000000; font-weight: bold; border-top: 1px solid; border-bottom: 1px solid; border-top-color: #000000; border-bottom-color: #000000;"
					colspan=8><nobr>Date-${grnPdf.date}</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr></td>
			</tr>
			<tr style="height: 33.2px;">
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 18px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-bottom: 1px solid; border-left-color: #000000; border-top-color: #000000; border-bottom-color: #000000;"
					colspan=3><nobr> DEBIT NOTE </nobr></td>
				<td
					style="padding: 10px; font-family: Calibri; font-size: 12px; color: #000000; font-weight: bold; border-left: 1px solid; border-bottom: 1px solid; border-left-color: #000000; border-bottom-color: #000000;"
					colspan=8><nobr>Sr No : ${grnPdf.srNo}</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr></td>
			</tr>
			<tr style="height: 33.2px;">
				<td
					style="padding: 10px; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-left-color: #000000; border-top-color: #000000;"
					colspan=3 rowspan=4><nobr> From, M/s ${grnPdf.frName}</nobr><br>${grnPdf.frAddress}</br> <nobr>
					</nobr></td>
				<td
					style="padding: 10px; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-left-color: #000000; border-top-color: #000000;"
					colspan=8><nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr></td>
			</tr>
			<tr style="height: 33.2px;">
				<td
					style="padding: 10px; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-left-color: #000000;"
					colspan=8><nobr>To, ${Constant.FACTORYNAME}</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr></td>
			</tr>
			<tr style="height: 34px;">
				<td colspan="8"
					style="padding: 10px; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-left-color: #000000;">
					<nobr>${Constant.FACTORYADDRESS}</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
				</td>
			</tr>
			<tr style="height: 33.2px;">
				<td colspan="8"
					style="padding: 10px; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-bottom: 1px solid; border-left-color: #000000; border-bottom-color: #000000;">
					<nobr>GST NO.${Constant.FACTORYGSTIN}</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
				</td>
			</tr>
			<tr style="height: 33.2px;">
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>SR.NO</nobr>
				</td>
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>HSN CODE</nobr>
				</td>
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>PARTICULARS</nobr>
				</td>
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>GFPL INV</nobr>
				</td>
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>QTY</nobr>
				</td>
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>RATE</nobr>
				</td>
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>VALUE</nobr>
				</td>
			</tr>

			<c:set var="qtySum" value="0"></c:set>

			<c:set var="totalSum" value="0"></c:set>

			<c:forEach items="${grnPdf.detail}" var="detail" varStatus="count">
				<tr style="height: 40px;">
					<td
						style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${count.index+1}</nobr>
					</td>
					<td
						style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${detail.itemHsncd}</nobr>
					</td>
					<td
						style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${detail.itemName}</nobr>
					</td>
					<td
						style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${detail.invoiceNo}</nobr>
					</td>
					<td
						style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${detail.grnGvnQty}</nobr>
					</td>
					<td
						style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">

						<nobr>
							<fmt:formatNumber type="number" maxFractionDigits="2"
								value="${detail.baseRate+detail.totalTax}" />
						</nobr>
					</td>
					<td
						style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${detail.grnGvnAmt}</nobr>
					</td>
					<c:set var="qtySum" value="${qtySum+detail.grnGvnQty}"></c:set>

					<c:set var="totalSum" value="${totalSum+ detail.grnGvnAmt}"></c:set>
				</tr>
			</c:forEach>
			
			
			<tr style="height: 40px;">
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>&nbsp;</nobr>
				</td>
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>&nbsp;</nobr>
				</td>
				<td
					style="padding: 10px; text-align: right; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-bottom: 1px solid; border-left-color: #000000; border-top-color: #000000; border-bottom-color: #000000;"
					colspan=2><nobr> Taxable Amt  </nobr></td>
				<td colspan="7"
					style="font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<fmt:formatNumber type = "number" 
         maxFractionDigits = "2" value ="${grnPdf.taxableAmt}" /></td>
			</tr>

			<tr style="height: 40px">
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #ff0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000">

				</td>
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #ff0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000">

				</td>
				<td
					style="padding: 10px; text-align: right; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-bottom: 1px solid; border-left-color: #000000; border-top-color: #000000; border-bottom-color: #000000"
					colspan="2">Total</td>

				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #ff0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000">
					${qtySum}</td>
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #ff0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000">

				</td>
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #ff0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000">
					${totalSum}</td>
			</tr>

			<tr style="height: 33.2px;">
				<td colspan="3"
					style="padding: 10px; font-family: Calibri; font-size: 11px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-left-color: #000000; border-top-color: #000000;">
					<nobr>Goods Received by__________________</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
				</td>
				<td colspan="8" rowspan="2"
					style="padding: 10px; font-family: Calibri; font-size: 11px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-left-color: #000000; border-top-color: #000000;">
					<nobr>For</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>STAMP &amp; SIGN</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>Authorised
						Sing</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
				</td>
			</tr>
			<tr style="height: 33.2px;">
				<td colspan="3"
					style="padding: 10px; font-family: Calibri; font-size: 11px; color: #000000; font-weight: bold; border-left: 1px solid; border-left-color: #000000;">
					<nobr>Goods Checked by___________________</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
				</td>
			</tr>
		</tbody>
	</table>
<script type="text/javascript">

function callPrint() {
	alert("calling print");
	window.print();
	
}

</script>
</body>
</html>