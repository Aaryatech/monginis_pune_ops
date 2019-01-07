package com.monginis.ops.model;

import java.util.List;

public class ItemWiseReportList {

	List<ItemWiseReport> itemWiseReportList;
	
	ErrorMessage errorMessage;

	public List<ItemWiseReport> getItemWiseReportList() {
		return itemWiseReportList;
	}

	public void setItemWiseReportList(List<ItemWiseReport> itemWiseReportList) {
		this.itemWiseReportList = itemWiseReportList;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String toString() {
		return "ItemWiseReportList [itemWiseReportList=" + itemWiseReportList + ", errorMessage=" + errorMessage + "]";
	}
	
	
}
