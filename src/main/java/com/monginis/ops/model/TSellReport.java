package com.monginis.ops.model;
 
public class TSellReport {
	 
	private int itemId; 
	private String itemName; 
	private String hsnNo; 
	private float cgst; 
	private float sgst; 
	private float igst; 
	private float totalTax; 
	private float taxableAmt; 
	private float grandTotal;
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
	public String getHsnNo() {
		return hsnNo;
	}
	public void setHsnNo(String hsnNo) {
		this.hsnNo = hsnNo;
	}
	public float getCgst() {
		return cgst;
	}
	public void setCgst(float cgst) {
		this.cgst = cgst;
	}
	public float getSgst() {
		return sgst;
	}
	public void setSgst(float sgst) {
		this.sgst = sgst;
	}
	public float getIgst() {
		return igst;
	}
	public void setIgst(float igst) {
		this.igst = igst;
	}
	public float getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(float totalTax) {
		this.totalTax = totalTax;
	}
	public float getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	public float getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}
	@Override
	public String toString() {
		return "TSellReport [itemId=" + itemId + ", itemName=" + itemName + ", hsnNo=" + hsnNo + ", cgst=" + cgst
				+ ", sgst=" + sgst + ", igst=" + igst + ", totalTax=" + totalTax + ", taxableAmt=" + taxableAmt
				+ ", grandTotal=" + grandTotal + "]";
	}
	
	

}
