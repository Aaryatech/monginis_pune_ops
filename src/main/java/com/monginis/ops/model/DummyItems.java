package com.monginis.ops.model;

public class DummyItems {
	
	String itemName;
    String mrp;
    String quantity;
    String rate;
    String value;
    
    
    
	public DummyItems(String itemName, String mrp, String quantity, String rate, String value) {
		super();
		this.itemName = itemName;
		this.mrp = mrp;
		this.quantity = quantity;
		this.rate = rate;
		this.value = value;
	}
	
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getMrp() {
		return mrp;
	}
	public void setMrp(String mrp) {
		this.mrp = mrp;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "DmmyItems [itemName=" + itemName + ", mrp=" + mrp + ", quantity=" + quantity + ", rate=" + rate
				+ ", value=" + value + "]";
	}
	
    
	    
}
