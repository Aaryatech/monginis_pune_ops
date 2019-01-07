package com.monginis.ops.model;

import java.util.List;

public class SpOrderHisList {
	private List<SpOrderHis> spOrderList = null;
	private ErrorMessage errorMessage;
	public List<SpOrderHis> getSpOrderList() {
		return spOrderList;
	}
	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}
	public void setSpOrderList(List<SpOrderHis> spOrderList) {
		this.spOrderList = spOrderList;
	}
	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
	

}
