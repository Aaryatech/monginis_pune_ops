<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.monginis.ops.constant.Constant" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GRN - GVN Print</title>
<head>
<meta charset="UTF-8" />
<title></title>
</head>
<style type="text/css">
<!--
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

	<table width="250" border="0" cellspacing="0" cellpadding="0"
		style="padding: 5px; font-family: verdana; font-size: 10px; border: 1px solid #E7E7E7;">

		<tbody>
			<tr>
				<td align="right" style="padding: 0px;"></td>
			</tr>
			<tr>
				<c:choose>
					<c:when test="${grnPdf.type==1}">
						<td colspan="2" align="center" style="padding: 1px;"><p>Goods
								Return Note (DEBIT)</p></td>

					</c:when>
					<c:otherwise>
						<td colspan="2" align="center" style="padding: 1px;"><p>Goods
								Variation Note (DEBIT)</p></td>
					</c:otherwise>
				</c:choose>
								
				
			</tr>
			<tr>
				<td colspan="2" align="center"
					style="padding: 2px; border-bottom: 1px solid #E7E7E7;"><p
						class="style2">
						<b>${grnPdf.frName}</b><br /> <span
							style="font-size: 10px; font-family: Arial;">(The Monginis
							Cake Shop)</span>
					</p></td>
			</tr>
			<tr>
				<td colspan="2" align="center"
					style="padding: 3px; font-family: Arial; border-bottom: 1px solid #E7E7E7; font-size: 12px;"><p
						class="style5">
<%-- 						<br /> <strong>${grnPdf.frAddress}</strong><br /> <br />
 --%>					
                GSTIN:<strong>${sessionScope.frDetails.frGstNo}</strong><br/>
             
       
         <%--  Phone:<strong>${sessionScope.frDetails.frMob}</strong><br/>
          
           <c:choose>
          <c:when test="${frGstType==2000000}">
              <span style="font-size:9px; font-family: Arial;">COMPOSITION TAXABLE FIRM, NOT SUPPOSED TO<br />
                BE COLLECT TAX ON SUPPLIES</span>
          </c:when>
          <c:when test="${frGstType==10000000}">
           <span style="font-size:9px; font-family: Arial;">COMPOSITION TAXABLE PERSON, NOT TO<br />
          COLLECT TAX ON SUPPLIES        </span>
          
           </c:when>
        </c:choose> --%> 
			</tr>
			
				<tr>
			<td colspan="2" align="center"
					style="padding: 3px; font-family: Arial; border-bottom: 1px solid #E7E7E7; font-size: 12px;"><p
						class="style5">
						<br />To, <strong>${Constant.FACTORYNAME}</strong><br /> 
						<br /> ${Constant.FACTORYADDRESS}<br /> 
						<br />GSTIN: <strong>${Constant.FACTORYGSTIN}</strong><br />
               
                </p>
                </td>
			<tr>
			<tr>
			<td colspan="2" align="center"
					style="padding: 3px; font-family: Arial; border-bottom: 1px solid #E7E7E7; font-size: 12px;"><p
						class="style5">
						<br /> <strong>${sessionScope.frDetails. frAddress}</strong><br /> <br />
					
                 Phone:<strong>${sessionScope.frDetails.frMob}</strong><br/>
                </p>
                </td>
			<tr>
			
			
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="7">
						<tbody>
							<tr>
								<td style="font-size: 9px">Sr No:</td>
								<td style="font-size: 10px">${grnPdf.srNo}</td>
								<td style="font-size: 9px">Date:</td>

								<td style="font-size: 10px">${grnPdf.date}</td>
							</tr>
							<%--  <tr>
      <td>Det No</td>
      <td colspan="3">${exBill.sellBillDetailNo}</td>
     
      </tr>  --%>
							<tr>
								<td colspan="4"><table width="100%"  border="0"
										cellspacing="0" cellpadding="1" class="tbl-inner"><!-- cellpading was 5 -->
										<tbody>
											<tr>
												<th width="30%" align="left" bgcolor="#ECECEC">HsnCode-itemName</th>
												<!-- <th width="30%" bgcolor="#ECECEC">Particular</th> -->
												<th width="14%" bgcolor="#ECECEC">Type-InvoiceNo</th>
												<th width="13%" bgcolor="#ECECEC">Rate</th>
												<th width="13%" align="right" bgcolor="#ECECEC">Qty</th>
												<th width="30%" bgcolor="#ECECEC">Amt</th>
											</tr>

											<c:forEach items="${grnPdf.detail}" var="detail"
												varStatus="count">
												<tr>

													<c:set var="qtySum" value="${qtySum+detail.grnGvnQty}"></c:set>
													<c:set var="totalSum" value="${totalSum+ detail.grnGvnAmt}"></c:set>

													<td><p style="font-size: 10px">${detail.itemHsncd}</p>
														<p style="font-size: 10px">${detail.itemName}</p></td>
													<c:set var="rate" value="aa"></c:set>

													<c:choose>
														<c:when test="${detail.isGrn==0}">
															<c:set var="type" value="GVN"></c:set>
															<c:set var="rate" value="${detail.itemRate}"></c:set>
														</c:when>
														<c:otherwise>
															<c:choose>
																<c:when test="${detail.grnType==0}">
																	<c:set var="type" value="GRN 1(80%)"></c:set>
																	<c:set var="rate" value="${(detail.baseRate-(detail.baseRate*(detail.itemMrp/100)))* 0.80}"></c:set>

																</c:when>
																<c:when test="${detail.grnType==1}">
																	<c:set var="type" value="GRN 2(70%)"></c:set>
																	<c:set var="rate" value="${(detail.baseRate-(detail.baseRate*(detail.itemMrp/100)))* 0.70}"></c:set>

																</c:when>
																<c:when test="${detail.grnType==2 or detail.grnType==4}">
																	<c:set var="type" value="GRN 3(100%)"></c:set>
																	<c:set var="rate" value="${detail.baseRate}"></c:set>

																</c:when>
															</c:choose>
														</c:otherwise>
													</c:choose>

													<td align="center"><p style="font-size: 10px">${type}-${detail.invoiceNo}</p></td>
													<td align="center"><p style="font-size: 10px"><fmt:formatNumber
															type="number" maxFractionDigits="2" value="${rate}" /></p></td>

													<td align="center"><p style="font-size: 10px">${detail.grnGvnQty}</p></td>
													<td align="right"><p style="font-size: 10px"><fmt:formatNumber type="number" minFractionDigits="2" maxFractionDigits="2" value="${detail.grnGvnAmt}" /></p></td>
												</tr>
											</c:forEach>
											<tr>
												<td rowspan="3">&nbsp;</td>
												<td colspan="3" align="right"><span class="style5"><strong>Total
															Qty :</strong></span></td>
												<td align="right"><span class="style5"><strong>${qtySum}</strong></span></td>
											</tr>
											<tr>
												<td colspan="3" align="right"><span class="style7">
														Total:</span></td>
												<td align="right"><span class="style7"><fmt:formatNumber
															type="number" maxFractionDigits="2" value="${totalSum}" /></span></td>
											</tr>
										</tbody>
									</table></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>
			<%-- 			<c:choose>
				<c:when test="${frGstType==10000000 }">
					<tr>
						<td colspan="2"><table width="100%" border="0"
								cellspacing="0" cellpadding="7">
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
									<td>${custBilltax.taxableAmt}</td>
									<c:set var="taxAmount"
										value="${taxAmount + custBilltax.taxableAmt}" />
									<td>${custBilltax.cgstPer}</td>
									<td>${custBilltax.cgstRs}</td>
									<c:set var="cgst" value="${cgst+custBilltax.cgstRs }" />
									<td>${custBilltax.sgstPer}</td>
									<td>${custBilltax.sgstRs}</td>
									<c:set var="sgst" value="${sgst+custBilltax.sgstRs }" />

									<td><fmt:formatNumber type="number" maxFractionDigits="2"
											minFractionDigits="2"
											value="${custBilltax.cgstRs+custBilltax.sgstRs}" /></td>
									<c:set var="totaltax"
										value="${totaltax+custBilltax.sgstRs+custBilltax.cgstRs }" />
								</tr>
									</c:forEach>
								<td width="14%" colspan="6">&nbsp;</td>

								</tr>
								<tr>
									<td bgcolor="#ECECEC"><b><fmt:formatNumber
												type="number" maxFractionDigits="2" minFractionDigits="2"
												value="${taxAmount}" /></b></td>

									<td bgcolor="#ECECEC"></td>
									<td bgcolor="#ECECEC"><fmt:formatNumber type="number"
											maxFractionDigits="2" minFractionDigits="2" value="${cgst}" /></td>
									<td bgcolor="#ECECEC"></td>
									<td bgcolor="#ECECEC"><fmt:formatNumber type="number"
											maxFractionDigits="2" minFractionDigits="2" value="${sgst}" /></td>
									<td align="center" bgcolor="#ECECEC"><fmt:formatNumber
											type="number" maxFractionDigits="2" minFractionDigits="2"
											value="${totaltax}" /></td>
								</tr>

							</table></td>
					</tr>
				</c:when>
			</c:choose> --%>
			<tr>
		
				<td style="border-top: 1px solid #E7E7E7; padding: 5px 7px;"
					colspan="6">Goods Received By</span>
				</td>
			</tr>
							
			<tr></tr>
			<tr>
		
				<td style="border-top: 1px solid #E7E7E7; padding: 5px 7px;"
					colspan="2">Goods Checked By</span>
				</td>
			</tr>
			<tr>
			
			<td style="border-top: 1px solid #E7E7E7; padding: 20px 7px 2px 0px;"
				>Authorised Sign</span>
				</td>
				<%-- <td width="100" align="center"
					style="border-top: 1px solid #E7E7E7; padding: 5px 7px;"><strong>For
						${sessionScope.frDetails.frName}</strong></td> --%>
						
						
			<td style="border-top: 1px solid #E7E7E7; padding: 20px 0px 2px 7px;"
					><span>${sessionScope.frDetails.frName}</span>
				</td>
			</tr>
		</tbody>
	</table>
</body>
<body onload="directPrint()">
	<script>
	 function directPrint()
	{
		 
		window.print();
		//window.close();
	} 
	
	</script>
</body>
</html>