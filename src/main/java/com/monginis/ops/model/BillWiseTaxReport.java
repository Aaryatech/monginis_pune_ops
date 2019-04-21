package com.monginis.ops.model;

public class BillWiseTaxReport {
	
    private String billNo;
	
    private int billDetailNo;

    
	private String billDate;
	
	private float taxableAmt;
	
	private float taxRate;
	
	private float igstRs;
	
	private float cgstRs;
	
	private float sgstRs;

	private float grandTotal;
	
	private float cess;
	
	public int getBillDetailNo() {
		return billDetailNo;
	}

	public void setBillDetailNo(int billDetailNo) {
		this.billDetailNo = billDetailNo;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
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

	public float getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(float taxRate) {
		this.taxRate = taxRate;
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

	public float getCess() {
		return cess;
	}

	public void setCess(float cess) {
		this.cess = cess;
	}

	@Override
	public String toString() {
		return "BillWiseTaxReport [billNo=" + billNo + ", billDate=" + billDate + ", taxableAmt=" + taxableAmt
				+ ", taxRate=" + taxRate + ", igstRs=" + igstRs + ", cgstRs=" + cgstRs + ", sgstRs=" + sgstRs
				+ ", grandTotal=" + grandTotal + ", cess=" + cess + "]";
	}
	
	
}
