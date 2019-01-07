package com.monginis.ops.model;

import java.util.List;

public class MonthWiseReportList {

	List<MonthWiseReport> monthWiseReportList;
	
	ErrorMessage errorMessage;

	public List<MonthWiseReport> getMonthWiseReportList() {
		return monthWiseReportList;
	}

	public void setMonthWiseReportList(List<MonthWiseReport> monthWiseReportList) {
		this.monthWiseReportList = monthWiseReportList;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
}
