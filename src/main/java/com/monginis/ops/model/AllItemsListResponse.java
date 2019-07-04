package com.monginis.ops.model;

import java.util.List;

public class AllItemsListResponse {

	List<Item> items;
	ErrorMessage errorMessage;

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}

}
