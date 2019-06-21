<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Sp Invoice</title>
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
				<td colspan="2" align="center"
					style="padding: 10px; border-bottom: 1px solid #E7E7E7; font-size: 12px; font-weight: bold;"><p>ORDER
						MEMO</p></td>
			</tr>
		<%-- 	<tr>
				<td colspan="2" align="center"
					style="padding: 3px; border-bottom: 1px solid #E7E7E7;"><span
					style="font-size: 12px; font-family: Times New Roman;"><b>${sessionScope.frDetails.frName}</b></span><br />
					<span style="font-size: 10px; font-family: Times New Roman;">(The
						Monginis Cake Shop)</span></td>
			</tr> --%>
			<tr>
				<td colspan="2">
					<table width="250" border="0" cellspacing="0" cellpadding="0"
						style="padding: 5px; font-family: verdana; font-size: 12px; border: 1px solid #E7E7E7;">
						<tbody>
							<tr>
								<td width="20%" align="left"><strong>&nbsp;&nbsp;Shop:</strong></td>
								<td width="80%" align="left">${shopName}</td>
								<td width="0%"><strong></td>
							</tr>
							<tr>
								<td><strong>&nbsp;&nbsp;Tel:</strong></td>
								<td>${tel}</td>
								<td><strong></td>
							</tr>
							<tr>
								<td><strong>&nbsp;&nbsp;Date:</strong></td>
								<td>${currDate}</td>
								<td><strong></td>
							</tr>
							<tr>
								<td><strong>&nbsp;&nbsp;Time:</strong></td>
								<td>${currTime}</td>
								<td><strong></td>
							</tr>
							<tr>
								<td colspan="3"><table width="100%" border="0"
										cellspacing="0" cellpadding="5" class="tbl-inner">
										<tbody>
											<tr>
												<th align="left" bgcolor="#ECECEC">&nbsp;&nbsp;Name</th>
												<th bgcolor="#ECECEC">Kg.</th>
												<th bgcolor="#ECECEC">Rate</th>
												<th align="center" bgcolor="#ECECEC">Amt</th>
											</tr>
											<tr>
												<td><p style="font-size: 12px">&nbsp;&nbsp;${spCakeOrder.itemId}</p></td>
												<td align="center"><p style="font-size: 12px">${spCakeOrder.spSelectedWeight}</p></td>
												<td align="center"><p style="font-size: 12px">${(spCakeOrder.spSubTotal)/spCakeOrder.spSelectedWeight}</p></td>
												<td align="right"><p style="font-size: 12px">${spCakeOrder.spSubTotal}&nbsp;&nbsp;</p></td>
											</tr>
											<tr>
												<td><p style="font-size: 12px">&nbsp;&nbsp;${flavourName}</p></td>
											<tr>
												<td><p style="font-size: 12px">&nbsp;&nbsp;${spCakeOrder.spInstructions}</p></td>


											</tr>

											<tr>
												<td rowspan="3">&nbsp;</td>
												<td colspan="2" align="right"><strong>Total :</strong></td>
												<td align="right"><strong><fmt:formatNumber
															type="number" maxFractionDigits="2" minFractionDigits="2"
															value="${spCakeOrder.spSubTotal}" /></strong>&nbsp;&nbsp;</td>
											</tr>
											<tr>
												<td colspan="2" align="right"><strong>Advance
														:</strong></td>
												<td align="right"><strong>${spCakeOrder.spAdvance}&nbsp;&nbsp;</strong></td>
											</tr>
											<tr>
												<td colspan="2" align="right"><strong>Balance
														:</strong></td>
												<td align="right"><strong>${spCakeOrder.rmAmount}&nbsp;&nbsp;</strong></td>
											</tr>
										</tbody>
									</table></td>
							</tr>
						</tbody>
					</table>


				</td>
			</tr>
			<tr>
				<td colspan="2">
					<table width="100%" border="0" cellspacing="0" cellpadding="7">

						<tr>
							<td width="200"
								style="border-top: 1px solid #E7E7E7; padding: 5px 7px;"><strong>Delivery
									Date : </strong> ${spCakeOrder.spDeliveryDate}</td>

						</tr>
						<tr>
							<td width="200"
								style="border-top: 1px solid #E7E7E7; padding: 5px 7px;"><strong>Order
									No:</strong> ${spCakeOrder.spDeliveryPlace}</td>
						</tr>

					</table>

				</td>
			</tr>
			<tr>
				<td colspan="2" width="200"
					style="border-top: 1px solid #E7E7E7; border-right: 1px solid #E7E7E7; padding: 5px 7px;"><strong>Customer
						Name : </strong> ${spCakeOrder.spCustName}</td>

			</tr>

			<tr>
				<td colspan="2" width="200"
					style="border-top: 1px solid #E7E7E7; border-right: 1px solid #E7E7E7; padding: 5px 7px;"><strong>Customer
						Phno : </strong> ${spCakeOrder.spCustMobNo}</td>
			</tr>
			<tr>
				<td colspan="2"
					style="border-top: 1px solid #E7E7E7; border-right: 1px solid #E7E7E7; padding: 5px 7px;"><p
						style="font-size: 13px;">While we shall take every care to
						execute your order as per your instruction, We shall not be liable
						for delay/non delivery or for variations in the order and
						decoration due to circumstances beyond our control.</p>
					<p style="font-size: 13px;">Fresh cream items should be stored
						under refrigeration.Please present this receipt at the time of
						delivery. Order once given will not be cancelled/reversed at any
						cost.</p></td>
			</tr>




		</tbody>
	</table>
</body>

</html>