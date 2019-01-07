package com.monginis.ops.model;

import java.util.List;

public class ItemOrderList {
	private List<ItemOrderHis> itemOrderList = null;
	private ErrorMessage errorMessage;

	

	public List<ItemOrderHis> getItemOrderList() {
		return itemOrderList;
	}

	public void setItemOrderList(List<ItemOrderHis> itemOrderList) {
		this.itemOrderList = itemOrderList;
	}

	public ErrorMessage getErrorMessage() {
	return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
	this.errorMessage = errorMessage;
	}
}
