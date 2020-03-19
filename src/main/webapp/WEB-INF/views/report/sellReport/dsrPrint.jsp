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
												<td style="font-size: 10px; text-align: right;">${dsrModel.openingAmt}</td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">ADD
													PURCHASES</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.purchase}</td>
											</tr>

											<fmt:formatNumber type="number" maxFractionDigits="1"
												minFractionDigits="1" var="tot"
												value="${dsrModel.openingAmt+dsrModel.purchase}" />

											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">TOTAL</td>
												<td style="font-size: 10px; text-align: right;">${tot}</td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">LESS
													GRN / GVN</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.grnGvn}</td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">SALE</td>
												<td style="font-size: 10px; text-align: right;">${dsrModel.sale}</td>
											</tr>


											<fmt:formatNumber type="number" maxFractionDigits="1"
												minFractionDigits="1" var="closing"
												value="${dsrModel.openingAmt+dsrModel.purchase-dsrModel.grnGvn-dsrModel.sale}" />


											<tr>
												<td style="font-size: 10px; padding-left: 5px;">LESS
													Closing Stock</td>
												<td style="font-size: 10px; text-align: right;">${closing}</td>
											</tr>



											<tr>
												<td style="font-size: 10px; padding-left: 5px;">LESS
													Card Sale</td>
												<td style="font-size: 10px; text-align: right;"></td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">Shop
													Expense</td>
												<td style="font-size: 10px; text-align: right;"></td>
											</tr>

											<tr>
												<td style="font-size: 10px; padding-left: 5px;">Gift
													Voucher / Coupon</td>
												<td style="font-size: 10px; text-align: right;"></td>
											</tr>


											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">Net
													Cash</td>
												<td style="font-size: 10px; text-align: right;"></td>
											</tr>

											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">Deposit
													in Bank</td>
												<td style="font-size: 10px; text-align: right;"></td>
											</tr>

											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;">(Short)
													/ Excess</td>
												<td style="font-size: 10px; text-align: right;"></td>
											</tr>

											<tr></tr>
											<tr></tr>
											<tr></tr>
											<tr></tr>
											

											<tr>
												<td
													style="font-size: 10px; padding-left: 5px; text-align: right;"></td>
												<td style="font-size: 10px; text-align: center;">Manager
													/ Incharge</td>
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