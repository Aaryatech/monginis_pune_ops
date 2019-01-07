package com.monginis.ops.model;

import java.io.Serializable;
import java.util.List;

public class BillWisePurchaseList implements Serializable{

	List<BillWisePurchaseReport> billWisePurchaseList;
	ErrorMessage errorMessage;
	public List<BillWisePurchaseReport> getBillWisePurchaseList() {
		return billWisePurchaseList;
	}
	public void setBillWisePurchaseList(List<BillWisePurchaseReport> billWisePurchaseList) {
		this.billWisePurchaseList = billWisePurchaseList;
	}
	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
	@Override
	public String toString() {
		return "BillWisePurchaseList [billWisePurchaseList=" + billWisePurchaseList + ", errorMessage=" + errorMessage
				+ "]";
	}
	
	
}
