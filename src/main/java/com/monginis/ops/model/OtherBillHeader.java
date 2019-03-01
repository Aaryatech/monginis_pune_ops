package com.monginis.ops.model;
   
import java.util.List;
 
import com.fasterxml.jackson.annotation.JsonFormat;
  

public class OtherBillHeader {
	   
	private int billNo;  
	private String invoiceNo; 
	private String billDate; 
	private int frId; 
	private String frCode; 
	private float taxApplicable; 
	private float taxableAmt; 
	private float totalTax; 
	private float grandTotal; 
	private int status; 
	private int delStatus; 
	private String time; 
	private float roundOff; 
	private float sgstSum; 
	private float cgstSum; 
	private float igstSum; 
	private float discAmt; 
	private int suppId; 
	List<OtherBillDetail> otherBillDetailList;
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
	@JsonFormat(locale = "hi",timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
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
	public float getTaxApplicable() {
		return taxApplicable;
	}
	public void setTaxApplicable(float taxApplicable) {
		this.taxApplicable = taxApplicable;
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
	public float getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public float getRoundOff() {
		return roundOff;
	}
	public void setRoundOff(float roundOff) {
		this.roundOff = roundOff;
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
	public int getSuppId() {
		return suppId;
	}
	public void setSuppId(int suppId) {
		this.suppId = suppId;
	}
	public List<OtherBillDetail> getOtherBillDetailList() {
		return otherBillDetailList;
	}
	public void setOtherBillDetailList(List<OtherBillDetail> otherBillDetailList) {
		this.otherBillDetailList = otherBillDetailList;
	}
	@Override
	public String toString() {
		return "OtherBillHeader [billNo=" + billNo + ", invoiceNo=" + invoiceNo + ", billDate=" + billDate + ", frId="
				+ frId + ", frCode=" + frCode + ", taxApplicable=" + taxApplicable + ", taxableAmt=" + taxableAmt
				+ ", totalTax=" + totalTax + ", grandTotal=" + grandTotal + ", status=" + status + ", delStatus="
				+ delStatus + ", time=" + time + ", roundOff=" + roundOff + ", sgstSum=" + sgstSum + ", cgstSum="
				+ cgstSum + ", igstSum=" + igstSum + ", discAmt=" + discAmt + ", suppId=" + suppId
				+ ", otherBillDetailList=" + otherBillDetailList + "]";
	}
 
	

}
