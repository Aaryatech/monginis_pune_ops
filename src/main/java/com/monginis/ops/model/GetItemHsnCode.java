package com.monginis.ops.model;
 

 
public class GetItemHsnCode {

	 
	private int itemId;
	 
	private String itemName;
	 
	private String hsncd;

	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getHsncd() {
		return hsncd;
	}

	public void setHsncd(String hsncd) {
		this.hsncd = hsncd;
	}

	@Override
	public String toString() {
		return "GetItemHsnCode [itemId=" + itemId + ", itemName=" + itemName + ", hsncd=" + hsncd + "]";
	}
	
	
	
}
