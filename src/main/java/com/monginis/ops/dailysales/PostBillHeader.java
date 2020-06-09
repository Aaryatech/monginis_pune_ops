package com.monginis.ops.dailysales;

import java.util.Date;
import java.util.List;


public class PostBillHeader {

	private int billNo;

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

	private String time;

	private String billDateTime;

	private float sgstSum;

	private float cgstSum;

	private float igstSum;

	private float discAmt;

	private String partyName;

	private String partyGstin;

	private String partyAddress;

	private String vehNo;

	private String billTime;

	private String exVarchar1;

	private String exVarchar2;
	
	List<PostBillDetail> postBillDetailsList;

	public int getBillNo() {
		return billNo;
	}

	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}

	public int getTaxApplicable() {
		return taxApplicable;
	}

	public void setTaxApplicable(int taxApplicable) {
		this.taxApplicable = taxApplicable;
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

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getBillDateTime() {
		return billDateTime;
	}

	public void setBillDateTime(String billDateTime) {
		this.billDateTime = billDateTime;
	}

	public float getSgstSum() {
		return sgstSum;
	}

	public void setSgstSum(float sgstSum) {
		this.sgstSum = sgstSum;
	}

	public float getCgstSum() {
		return cgstSum;
	}

	public void setCgstSum(float cgstSum) {
		this.cgstSum = cgstSum;
	}

	public float getIgstSum() {
		return igstSum;
	}

	public void setIgstSum(float igstSum) {
		this.igstSum = igstSum;
	}

	public float getDiscAmt() {
		return discAmt;
	}

	public void setDiscAmt(float discAmt) {
		this.discAmt = discAmt;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getPartyGstin() {
		return partyGstin;
	}

	public void setPartyGstin(String partyGstin) {
		this.partyGstin = partyGstin;
	}

	public String getPartyAddress() {
		return partyAddress;
	}

	public void setPartyAddress(String partyAddress) {
		this.partyAddress = partyAddress;
	}

	public String getVehNo() {
		return vehNo;
	}

	public void setVehNo(String vehNo) {
		this.vehNo = vehNo;
	}

	public String getBillTime() {
		return billTime;
	}

	public void setBillTime(String billTime) {
		this.billTime = billTime;
	}

	public String getExVarchar1() {
		return exVarchar1;
	}

	public void setExVarchar1(String exVarchar1) {
		this.exVarchar1 = exVarchar1;
	}

	public String getExVarchar2() {
		return exVarchar2;
	}

	public void setExVarchar2(String exVarchar2) {
		this.exVarchar2 = exVarchar2;
	}
	
	

	public List<PostBillDetail> getPostBillDetailsList() {
		return postBillDetailsList;
	}

	public void setPostBillDetailsList(List<PostBillDetail> postBillDetailsList) {
		this.postBillDetailsList = postBillDetailsList;
	}
	
	

	@Override
	public String toString() {
		return "PostBillHeader [billNo=" + billNo + ", taxApplicable=" + taxApplicable + ", invoiceNo=" + invoiceNo
				+ ", billDate=" + billDate + ", frId=" + frId + ", frCode=" + frCode + ", grandTotal=" + grandTotal
				+ ", taxableAmt=" + taxableAmt + ", totalTax=" + totalTax + ", status=" + status + ", DelStatus="
				+ DelStatus + ", remark=" + remark + ", time=" + time + ", billDateTime=" + billDateTime + ", sgstSum="
				+ sgstSum + ", cgstSum=" + cgstSum + ", igstSum=" + igstSum + ", discAmt=" + discAmt + ", partyName="
				+ partyName + ", partyGstin=" + partyGstin + ", partyAddress=" + partyAddress + ", vehNo=" + vehNo
				+ ", billTime=" + billTime + ", exVarchar1=" + exVarchar1 + ", exVarchar2=" + exVarchar2
				+ ", postBillDetailsList=" + postBillDetailsList + "]";
	}

}
