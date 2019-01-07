package com.monginis.ops.model;

import java.util.List;

import com.monginis.ops.billing.SellBillDetail;

public class SellBillDetailList {
	
List<SellBillDetail> sellBillDetailList;
ErrorMessage errorMessage;

public List<SellBillDetail> getSellBillDetailList() {
	return sellBillDetailList;
}
public void setSellBillDetailList(List<SellBillDetail> sellBillDetailList) {
	this.sellBillDetailList = sellBillDetailList;
}
public ErrorMessage getErrorMessage() {
	return errorMessage;
}
public void setErrorMessage(ErrorMessage errorMessage) {
	this.errorMessage = errorMessage;
}

}
