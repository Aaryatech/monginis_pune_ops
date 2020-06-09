<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Daily Sales Report</title>
<html lang="en">
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
<body>

	

	<table width="250" border="0" cellspacing="0" cellpadding="0"
		style="padding: 0px; font-family: Times New Roman; font-size: 12px; border: 1px solid #E7E7E7;">

		<tbody>
			<tr>
				<td colspan="2" align="center" style="padding-top: 3px;">DAILY
					SALES REPORT</td>

			</tr>
			<tr>
				<td colspan="2" align="center"
					style="padding: 3px; border-bottom: 1px solid #E7E7E7;"><span
					style="font-size: 12px; font-family: Times New Roman;"><b>${sessionScope.frDetails.frName}</b></span><br />
					<span style="font-size: 10px; font-family: Times New Roman;">(The
						Monginis Cake Shop)</span></td>
			</tr>
			<tr>
				<td colspan="2" align="center"
					style="padding-left: 5px; padding: 2px; font-family: Times New Roman; border-bottom: 1px solid #E7E7E7; font-size: 12px;"><span
					style="font-size: 10px;"> <strong>${sessionScope.frDetails.frAddress}</strong><br />
						Phone:<strong>${sessionScope.frDetails.frMob}</strong><br />
				</span></td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="2">
						<tbody>

							<tr>
								<td style="font-size: 10px; text-align: center;">Date :
									${date}</td>
							</tr>


							<tr>
								<td colspan="4"><table width="100%" border="0"
										cellspacing="0" cellpadding="2" class="tbl-inner">
										<tbody>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">OPENING
													STOCK</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.openingStock}</td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;"><u>ADD
														PURCHASES</u></td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.totalPurchase}</td>
											</tr>

											<tr>

												<td style="font-size: 10px; padding-left: 5px;">
													<table style="width: 100%;">
														<tr>
															<td style="text-align: center;"><u>Bill No.</u></td>
															<td style="text-align: center;"><u>Date</u></td>
														</tr>

													</table>

												</td>

												<td style="font-size: 10px; text-align: right;"></td>

											</tr>


											<c:forEach items="${purList}" var="pur">
												<tr>

													<td style="font-size: 10px; padding-left: 5px;">
														<table style="width: 100%;">
															<tr>
																<td>${pur.invoiceNo}</td>
																<td style="text-align: right;">${pur.billDate}</td>
															</tr>


														</table>

													</td>

													<td style="font-size: 10px; text-align: right;">${pur.grandTotal}</td>

												</tr>

											</c:forEach>




											<fmt:formatNumber type="number" maxFractionDigits="1"
												minFractionDigits="1" var="tot"
												value="${dsrModel.openingStock+dsrModel.totalPurchase}" />

											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">TOTAL</td>
												<td style="font-size: 10px; text-align: right;">${tot}</td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">LESS
													GRN / GVN</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.totalGrnGvn}</td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">LESS
													Closing Stock</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.closingStock}</td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">SALE</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.totalSale}</td>
											</tr>








											<tr>
												<td style="font-size: 10px; padding-left: 5px;">LESS
													Card Sale</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.cardSale}</td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">LESS
													Cash Sale</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.cashSale}</td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">Shop
													Expense</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.totalExp}</td>
											</tr>

											<tr>

												<td style="font-size: 10px; padding-left: 5px;">
													<table style="width: 100%;">
														<tr>
															<td style="text-align: center;"><u>Expense</u></td>
															<td style="text-align: center;"><u>Date</u></td>
														</tr>

													</table>

												</td>

												<td style="font-size: 10px; text-align: right;"></td>

											</tr>


											<c:forEach items="${expList}" var="exp">
												<tr>
													<td style="font-size: 10px; padding-left: 5px;">
														<table style="width: 100%;">
															<tr>
																<td>${exp.exVar1}</td>
																<td style="text-align: right;">${exp.expDate}</td>
															</tr>


														</table>

													</td>

													<td style="font-size: 10px; text-align: right;">${exp.chAmt}</td>

												</tr>
											</c:forEach>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">Gift
													Voucher / Coupon</td>
												<td style="font-size: 10px; text-align: right;"></td>
											</tr>
											
												<tr>
												<td style="font-size: 10px; padding-left: 5px;">&nbsp;</td>
												<td style="font-size: 10px; text-align: right;">&nbsp;</td>
											</tr>


											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">Net
													Cash</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.netCash}</td>
											</tr>
											
											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">Physical Cash</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.physicalCash}</td>
											</tr>

											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">Deposit
													in Bank</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.depositInBank}</td>
											</tr>

											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">(Short)
													/ Excess</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.excess}</td>
											</tr>

											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">&nbsp;</td>
												<td style="font-size: 10px; text-align: center;">Manager
													/ Incharge</td>
											</tr>
											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">&nbsp;</td>
												<td style="font-size: 10px; text-align: center;">&nbsp;</td>
											</tr>
											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">&nbsp;</td>
												<td style="font-size: 10px; text-align: center;">&nbsp;</td>
											</tr>
										


											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;"></td>
												<td style="font-size: 10px; text-align: center;">Signature</td>
											</tr>



											<%-- <tr>

												<td colspan="3" align="right"><span class="style5"><strong>Total
															:</strong></span></td>
												<td align="right"><span class="style5"><strong>${dsrModel.openingAmt}</strong></span></td>
											</tr> --%>

										</tbody>
									</table></td>
							</tr>
						</tbody>
					</table>
				</td>
			</tr>




		</tbody>
	</table>
</body>
<body onload="directPrint()">
	<script>
		function directPrint() {

			window.print();
			//window.close();
		}
	</script>
</body>
</html>