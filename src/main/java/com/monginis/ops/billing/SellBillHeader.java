package com.monginis.ops.billing;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class SellBillHeader implements Serializable{
	
	private int sellBillNo;
	

	private String invoiceNo;
	
	
	private String billDate;
	
	
	private int frId;
	
	
	private String frCode;
	
	
	private float taxableAmt;
	
	
	private float discountPer;
	
	private float discountAmt;
	
	private float payableAmt;
	
	private float totalTax;
	

	private float grandTotal;
	
	
	private float paidAmt;
	

	private float remainingAmt;
	

	private int paymentMode;
	

	private String userName;
	
	
	private String userGstNo;
	
	
	private String userPhone;
	

	private int status;
	
	private int DelStatus;
 
	private char billType;
   
	public char getBillType() {
		return billType;
	}

	public void setBillType(char billType) {
		this.billType = billType;
	}

	List<SellBillDetail> sellBillDetailsList;
	
	public List<SellBillDetail> getSellBillDetailsList() {
		return sellBillDetailsList;
	}

	public void setSellBillDetailsList(List<SellBillDetail> sellBillDetailsList) {
		this.sellBillDetailsList = sellBillDetailsList;
	}

	public int getSellBillNo() {
		return sellBillNo;
	}

	public void setSellBillNo(int sellBillNo) {
		this.sellBillNo = sellBillNo;
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

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	

	public float getDiscountPer() {
		return discountPer;
	}

	public void setDiscountPer(float discountPer) {
		this.discountPer = discountPer;
	}

	public float getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(float discountAmt) {
		this.discountAmt = discountAmt;
	}

	public float getPayableAmt() {
		return payableAmt;
	}

	public void setPayableAmt(float payableAmt) {
		this.payableAmt = payableAmt;
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

	public float getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(float paidAmt) {
		this.paidAmt = paidAmt;
	}

	public float getRemainingAmt() {
		return remainingAmt;
	}

	public void setRemainingAmt(float remainingAmt) {
		this.remainingAmt = remainingAmt;
	}

	public int getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGstNo() {
		return userGstNo;
	}

	public void setUserGstNo(String userGstNo) {
		this.userGstNo = userGstNo;
	}

	

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
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

	@Override
	public String toString() {
		return "SellBillHeader [sellBillNo=" + sellBillNo + ", invoiceNo=" + invoiceNo + ", billDate=" + billDate
				+ ", frId=" + frId + ", frCode=" + frCode + ", taxableAmt=" + taxableAmt + ", discountPer="
				+ discountPer + ", discountAmt=" + discountAmt + ", payableAmt=" + payableAmt + ", totalTax=" + totalTax
				+ ", grandTotal=" + grandTotal + ", paidAmt=" + paidAmt + ", remainingAmt=" + remainingAmt
				+ ", paymentMode=" + paymentMode + ", userName=" + userName + ", userGstNo=" + userGstNo
				+ ", userPhone=" + userPhone + ", status=" + status + ", DelStatus=" + DelStatus
				+ ", sellBillDetailsList=" + sellBillDetailsList + "]";
	}
   
	
}
