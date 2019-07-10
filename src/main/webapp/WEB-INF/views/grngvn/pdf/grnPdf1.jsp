<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.monginis.ops.constant.Constant" %>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>

<c:set var="frName" value=""></c:set>
			
	<table class="" cellspacing=0 border=1>
		<tbody>
		
			<tr style="height: 30px;">
			
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
					style="padding: 10px; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-top: 1px solid; border-bottom: 1px solid; border-top-color: #000000; border-bottom-color: #000000;"
					colspan=8><nobr>Date-${grnPdf.date}</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr></td>
			</tr>
			<tr style="height: 30px;">
				<td
					style="padding: 10px; font-family: Calibri; text-align: center; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-bottom: 1px solid; border-left-color: #000000; border-top-color: #000000; border-bottom-color: #000000;"
					colspan=3><nobr> Tax Invoice</nobr></td>
				<td
					style="padding: 10px; font-family: Calibri; font-size: 12px; color: #000000; font-weight: bold; border-left: 1px solid; border-bottom: 1px solid; border-left-color: #000000; border-bottom-color: #000000;"
					colspan=8><nobr>No: ${grnPdf.srNo}</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr></td>
			</tr>
			<tr style="height: 30px;">
				<td
					style="padding: 10px; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-left-color: #000000; border-top-color: #000000;"
					colspan=3 rowspan=4><nobr> From, M/s</nobr>${grnPdf.frName} <nobr><br>${grnPdf.frAddress}</br>
					</nobr></td>
				<td
					style="padding: 10px; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-top: 1px solid; border-left-color: #000000; border-top-color: #000000;"
					colspan=8><nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr></td>
			</tr>
			<tr style="height: 30px;">
				<td
					style="padding: 10px; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-left-color: #000000;"
					colspan=8><nobr>To, ${Constant.FACTORYNAME}</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr></td>
			</tr>
			<tr style="height: 30px;">
				<td colspan="8"
					style="padding: 10px; font-family: Calibri; font-size: 12px; color: #000000; font-weight: bold; border-left: 1px solid; border-left-color: #000000;">
					<nobr>${Constant.FACTORYADDRESS}</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
				</td>
			</tr>
			<tr style="height: 30px;">
				<td colspan="8"
					style="padding: 10px; font-family: Calibri; font-size: 14px; color: #000000; font-weight: bold; border-left: 1px solid; border-bottom: 1px solid; border-left-color: #000000; border-bottom-color: #000000;">
					<nobr>GST NO.${Constant.FACTORYGSTIN}</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
					<nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr> <nobr>&nbsp;</nobr>
				</td>
			</tr>
			<tr style="height: 33.2px;">
				<td
					style="padding: 5px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>SR.NO</nobr>
				</td>
				<td
					style="padding: 7px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>HSN CODE</nobr>
				</td>
				<td
					style="padding: 7px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>PARTICULARS</nobr>
				</td>
				<td
					style="padding: 7px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>GFPL INV</nobr>
				</td>
				<td
					style="padding: 7px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>QTY</nobr>
				</td>
				<td
					style="padding: 7px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>RATE</nobr>
				</td>
				<td
					style="padding: 7px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>Amount</nobr>
				</td>
				<td
					style="padding: 7px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>CGST %</nobr>
				</td>
				<td
					style="padding: 7px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>CGST Amount</nobr>
				</td>
				<td
					style="padding: 7px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>SGST %</nobr>
				</td>
				<td
					style="padding: 7px; font-family: Calibri; text-align: center; font-size: 11px; color: #000000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<nobr>SGST Amount</nobr>
				</td>
			</tr>
			
			<c:set var="sgstSum" value="0"></c:set>
			<c:set var="cgstSum" value="0"></c:set>
			<c:set var="grandTotal" value="0"></c:set>

			<c:forEach items="${grnPdf.detail}" var="grnPdf" varStatus="count">
			
						
			
				<tr style="height: 40px;">
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${count.index+1}</nobr>
					</td>
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${grnPdf.itemHsncd}</nobr>
					</td>
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr> ${grnPdf.itemName}</nobr>
					</td>
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${grnPdf.invoiceNo}</nobr>
					</td>
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${grnPdf.grnGvnQty}</nobr>
					</td>
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${grnPdf.baseRate}</nobr>
					</td>
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${grnPdf.grnGvnAmt}</nobr>
					</td>
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${grnPdf.cgstPer}</nobr>
					</td>

					<c:set var="cgstRs"
						value="${(grnPdf.taxableAmt*grnPdf.sgstPer)/100}"></c:set>
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr><fmt:formatNumber type = "number" 
         maxFractionDigits = "2" value = "${cgstRs}" /></nobr>
					</td>
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr>${grnPdf.sgstPer}</nobr>
					</td>
					<td
						style="padding: 7px; font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
						<nobr><fmt:formatNumber type = "number" 
         maxFractionDigits = "2" value = "${cgstRs}"/></nobr>
					</td>
					
					<c:set var="sgstSum" value="${sgstSum+ cgstRs}"></c:set>
					
					<c:set var="cgstSum" value="${cgstSum+ cgstRs}"></c:set>
					
					<c:set var="grandTotal" value="${grandTotal+grnPdf.grnGvnAmt}"></c:set>
					
					
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
         maxFractionDigits = "2" value = "${grnPdf.taxableAmt}" /></td>
			</tr>

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
					colspan=2><nobr> Total CGST  </nobr></td>
				<td colspan="7"
					style="font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<fmt:formatNumber type = "number" 
         maxFractionDigits = "2" value = "${cgstSum}" /></td>
			</tr>
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
					colspan=2><nobr> Total SGST</nobr></td>
				<td colspan="7"
					style="font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<fmt:formatNumber type = "number" 
         maxFractionDigits = "2" value = "${sgstSum}" /></td>
			</tr>
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
					colspan=2><nobr> Total GST</nobr></td>
				<td colspan="7"
					style="font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<fmt:formatNumber type = "number" 
         maxFractionDigits = "2" value = "${sgstSum+cgstSum}" /></td>
			</tr>
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
					colspan=2><nobr> Grand Total </nobr></td>
				<td colspan="7"
					style="font-family: Calibri; text-align: center; font-size: 14px; color: #FF0000; font-weight: bold; border: 1px solid; border-left-color: #000000; border-right-color: #000000; border-top-color: #000000; border-bottom-color: #000000;">
					<fmt:formatNumber type = "number" 
         maxFractionDigits = "2" value = "${grandTotal}" /></td>
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

</body>
</html>