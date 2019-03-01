package com.monginis.ops.model;

import java.util.Date;

public class GetBillHeader {

	private int billNo;
	private String frName;
	private String time;
	private int taxApplicable;
	private String invoiceNo;
	private String billDate;
	private int frId;
	private String frCode;
	private float grandTotal;
	private float taxableAmt;
	private float totalTax;
	private int status;
	private int DelStatus;
	private String remark;	
	private int suppId;
	private String suppName;

	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}


	public String getInvoiceNo() {
		return invoiceNo;
	}

	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}


	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public String getFrCode() {
		return frCode;
	}

	public void setFrCode(String frCode) {
		this.frCode = frCode;
	}

	
	public float getGrandTotal() {
		return grandTotal;
	}

	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getDelStatus() {
		return DelStatus;
	}

	public void setDelStatus(int delStatus) {
		DelStatus = delStatus;
	}

	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}
	
	public int getTaxApplicable() {
		return taxApplicable;
	}

	public void setTaxApplicable(int taxApplicable) {
		this.taxApplicable = taxApplicable;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	

	public int getSuppId() {
		return suppId;
	}

	public void setSuppId(int suppId) {
		this.suppId = suppId;
	}

	public String getSuppName() {
		return suppName;
	}

	public void setSuppName(String suppName) {
		this.suppName = suppName;
	}

	@Override
	public String toString() {
		return "GetBillHeader [billNo=" + billNo + ", frName=" + frName + ", time=" + time + ", taxApplicable="
				+ taxApplicable + ", invoiceNo=" + invoiceNo + ", billDate=" + billDate + ", frId=" + frId + ", frCode="
				+ frCode + ", grandTotal=" + grandTotal + ", taxableAmt=" + taxableAmt + ", totalTax=" + totalTax
				+ ", status=" + status + ", DelStatus=" + DelStatus + ", remark=" + remark + ", suppId=" + suppId
				+ ", suppName=" + suppName + "]";
	}
	



}
