package com.monginis.ops.model;

import java.util.List;

public class ItemResponse {
List<Item> itemList;
ErrorMessage errorMessage;
public List<Item> getItemList() {
	return itemList;
}
public void setItemList(List<Item> itemList) {
	this.itemList = itemList;
}
public ErrorMessage getErrorMessage() {
	return errorMessage;
}
public void setErrorMessage(ErrorMessage errorMessage) {
	this.errorMessage = errorMessage;
}

}
