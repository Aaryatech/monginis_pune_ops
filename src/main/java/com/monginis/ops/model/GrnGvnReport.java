package com.monginis.ops.model;

import com.fasterxml.jackson.annotation.JsonFormat;

public class GrnGvnReport {
	 
	private int grnGvnId; 
	private String grnGvnDate; 
	private int itemId; 
	private String itemName; 
	private float taxRate; 
	private float taxableAmt; 
	private float totalTax; 
	private float grnGvnAmt; 
	private float aprTaxableAmt; 
	private float aprSgstRs; 
	private float aprCgstRs; 
	private float aprIgstRs; 
	private float aprGrandTotal;
	public int getGrnGvnId() {
		return grnGvnId;
	}
	public void setGrnGvnId(int grnGvnId) {
		this.grnGvnId = grnGvnId;
	}
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public String getGrnGvnDate() {
		return grnGvnDate;
	}
	public void setGrnGvnDate(String grnGvnDate) {
		this.grnGvnDate = grnGvnDate;
	}
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
	public float getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
	}
	public float getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	public float getTotalTax() {
		return totalTax;
	}
	public void setTotalTax(float totalTax) {
		this.totalTax = totalTax;
	}
	public float getGrnGvnAmt() {
		return grnGvnAmt;
	}
	public void setGrnGvnAmt(float grnGvnAmt) {
		this.grnGvnAmt = grnGvnAmt;
	}
	public float getAprTaxableAmt() {
		return aprTaxableAmt;
	}
	public void setAprTaxableAmt(float aprTaxableAmt) {
		this.aprTaxableAmt = aprTaxableAmt;
	}
	public float getAprSgstRs() {
		return aprSgstRs;
	}
	public void setAprSgstRs(float aprSgstRs) {
		this.aprSgstRs = aprSgstRs;
	}
	public float getAprCgstRs() {
		return aprCgstRs;
	}
	public void setAprCgstRs(float aprCgstRs) {
		this.aprCgstRs = aprCgstRs;
	}
	public float getAprIgstRs() {
		return aprIgstRs;
	}
	public void setAprIgstRs(float aprIgstRs) {
		this.aprIgstRs = aprIgstRs;
	}
	public float getAprGrandTotal() {
		return aprGrandTotal;
	}
	public void setAprGrandTotal(float aprGrandTotal) {
		this.aprGrandTotal = aprGrandTotal;
	}
	@Override
	public String toString() {
		return "GrnGvnReport [grnGvnId=" + grnGvnId + ", grnGvnDate=" + grnGvnDate + ", itemId=" + itemId
				+ ", itemName=" + itemName + ", taxRate=" + taxRate + ", taxableAmt=" + taxableAmt + ", totalTax="
				+ totalTax + ", grnGvnAmt=" + grnGvnAmt + ", aprTaxableAmt=" + aprTaxableAmt + ", aprSgstRs="
				+ aprSgstRs + ", aprCgstRs=" + aprCgstRs + ", aprIgstRs=" + aprIgstRs + ", aprGrandTotal="
				+ aprGrandTotal + "]";
	}
	
	

}
