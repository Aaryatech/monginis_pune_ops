package com.monginis.ops.model;


public class MonthWiseReport {

	
	private int billNo;
	
	private String month;
	
	private float taxableAmt;
	
	private float igstRs;
	
	private float cgstRs;
	
	private float sgstRs;
	
	private float grandTotal;

	
	private float sess;
	
	
	private float roundOff;
	
	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getIgstRs() {
		return igstRs;
	}

	public void setIgstRs(float igstRs) {
		this.igstRs = igstRs;
	}

	public float getCgstRs() {
		return cgstRs;
	}

	public void setCgstRs(float cgstRs) {
		this.cgstRs = cgstRs;
	}

	public float getSgstRs() {
		return sgstRs;
	}

	public void setSgstRs(float sgstRs) {
		this.sgstRs = sgstRs;
	}

	public float getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}

	public float getSess() {
		return sess;
	}

	public void setSess(float sess) {
		this.sess = sess;
	}

	public float getRoundOff() {
		return roundOff;
	}

	public void setRoundOff(float roundOff) {
		this.roundOff = roundOff;
	}

	@Override
	public String toString() {
		return "MonthWiseReport [billNo=" + billNo + ", month=" + month + ", taxableAmt=" + taxableAmt + ", igstRs="
				+ igstRs + ", cgstRs=" + cgstRs + ", sgstRs=" + sgstRs + ", grandTotal=" + grandTotal + ", sess=" + sess
				+ ", roundOff=" + roundOff + "]";
	}
	
	
	
}
