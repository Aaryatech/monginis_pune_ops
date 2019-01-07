package com.monginis.ops.model;

import java.util.List;

public class RegularSpCkItemResponse {
	private List<GetRegularSpCkItem> getRegularSpCkItems = null;
	private ErrorMessage errorMessage;

	public List<GetRegularSpCkItem> getGetRegularSpCkItems() {
	return getRegularSpCkItems;
	}

	public void setGetRegularSpCkItems(List<GetRegularSpCkItem> getRegularSpCkItems) {
	this.getRegularSpCkItems = getRegularSpCkItems;
	}

	public ErrorMessage getErrorMessage() {
	return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
	this.errorMessage = errorMessage;
	}
}
