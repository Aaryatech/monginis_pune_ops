package com.monginis.ops.model;



public class BillWisePurchaseReport {
	
	private int billNo;
	
	private String invoiceNo;
	
	private String billDate;
	
	private float taxableAmt;
	
	private float igstRs;
	
	
	private float cgstRs;
	
	private float sgstRs;
	

	private float grandTotal;
	
	private float roundOff;

	
	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}

	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
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

	public float getRoundOff() {
		return roundOff;
	}

	public void setRoundOff(float roundOff) {
		this.roundOff = roundOff;
	}

	@Override
	public String toString() {
		return "BillWisePurchaseReport [billNo=" + billNo + ", billDate=" + billDate + ", taxableAmt=" + taxableAmt
				+ ", igstRs=" + igstRs + ", cgstRs=" + cgstRs + ", sgstRs=" + sgstRs + ", grandTotal=" + grandTotal
				+ ", roundOff=" + roundOff + "]";
	}
	
	
}
