package com.monginis.ops.model;

import java.util.List;

public class BillWiseTaxReportList {

	List<BillWiseTaxReport> billWiseTaxReportList;
	
	
	ErrorMessage errorMessage;


	public List<BillWiseTaxReport> getBillWiseTaxReportList() {
		return billWiseTaxReportList;
	}


	public void setBillWiseTaxReportList(List<BillWiseTaxReport> billWiseTaxReportList) {
		this.billWiseTaxReportList = billWiseTaxReportList;
	}


	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}


	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}


	@Override
	public String toString() {
		return "BillWiseTaxReportList [billWiseTaxReportList=" + billWiseTaxReportList + ", errorMessage="
				+ errorMessage + "]";
	}
	
	
	
}
