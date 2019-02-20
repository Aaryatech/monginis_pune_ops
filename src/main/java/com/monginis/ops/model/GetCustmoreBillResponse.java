package com.monginis.ops.model;


public class GetCustmoreBillResponse {


private int sellBillDetailNo;

private int sellBillNo;

private String invoiceNo;

private String billDate;

private String custName;

private String frAddress;

private int frId;

private String frMob;

private String frName;

private int itemId;

private String itemName;

private String hsnCode;

private float taxableAmt;

private float discountPer;


private float discountAmt;

 
private float intBillAmt;

private float intDiscAmt;

private float cgstPer;

private float sgstPer;

private float igstPer;


private String gstn;

private float bill_amount;

private float mrp;

private int qty;

private float igstRs;

private float cgstRs;

private float sgstRs;

private String userGstNo;//new

private String userPhone;//new
private String billType;//new


public String getBillType() {
	return billType;
}

public void setBillType(String billType) {
	this.billType = billType;
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

public String getHsnCode() {
	return hsnCode;
}

public void setHsnCode(String hsnCode) {
	this.hsnCode = hsnCode;
}

public int getSellBillDetailNo() {
	return sellBillDetailNo;
}

public void setSellBillDetailNo(int sellBillDetailNo) {
	this.sellBillDetailNo = sellBillDetailNo;
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

public String getCustName() {
	return custName;
}

public void setCustName(String custName) {
	this.custName = custName;
}

public String getFrAddress() {
	return frAddress;
}

public void setFrAddress(String frAddress) {
	this.frAddress = frAddress;
}

public int getFrId() {
	return frId;
}

public void setFrId(int frId) {
	this.frId = frId;
}

public String getFrMob() {
	return frMob;
}

public void setFrMob(String frMob) {
	this.frMob = frMob;
}

public String getFrName() {
	return frName;
}

public void setFrName(String frName) {
	this.frName = frName;
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

public float getTaxableAmt() {
	return taxableAmt;
}

public void setTaxableAmt(float taxableAmt) {
	this.taxableAmt = taxableAmt;
}

public float getCgstPer() {
	return cgstPer;
}

public void setCgstPer(float cgstPer) {
	this.cgstPer = cgstPer;
}

public float getSgstPer() {
	return sgstPer;
}

public void setSgstPer(float sgstPer) {
	this.sgstPer = sgstPer;
}

public float getIgstPer() {
	return igstPer;
}

public void setIgstPer(float igstPer) {
	this.igstPer = igstPer;
}

public String getGstn() {
	return gstn;
}

public void setGstn(String gstn) {
	this.gstn = gstn;
}

public float getBill_amount() {
	return bill_amount;
}

public void setBill_amount(float bill_amount) {
	this.bill_amount = bill_amount;
}

public float getMrp() {
	return mrp;
}

public void setMrp(float mrp) {
	this.mrp = mrp;
}

public int getQty() {
	return qty;
}

public void setQty(int qty) {
	this.qty = qty;
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

public float getIntBillAmt() {
	return intBillAmt;
}

public void setIntBillAmt(float intBillAmt) {
	this.intBillAmt = intBillAmt;
}

public float getIntDiscAmt() {
	return intDiscAmt;
}

public void setIntDiscAmt(float intDiscAmt) {
	this.intDiscAmt = intDiscAmt;
}

@Override
public String toString() {
	return "GetCustmoreBillResponse [sellBillDetailNo=" + sellBillDetailNo + ", sellBillNo=" + sellBillNo
			+ ", invoiceNo=" + invoiceNo + ", billDate=" + billDate + ", custName=" + custName + ", frAddress="
			+ frAddress + ", frId=" + frId + ", frMob=" + frMob + ", frName=" + frName + ", itemId=" + itemId
			+ ", itemName=" + itemName + ", hsnCode=" + hsnCode + ", taxableAmt=" + taxableAmt + ", discountPer="
			+ discountPer + ", discountAmt=" + discountAmt + ", intBillAmt=" + intBillAmt + ", intDiscAmt=" + intDiscAmt
			+ ", cgstPer=" + cgstPer + ", sgstPer=" + sgstPer + ", igstPer=" + igstPer + ", gstn=" + gstn
			+ ", bill_amount=" + bill_amount + ", mrp=" + mrp + ", qty=" + qty + ", igstRs=" + igstRs + ", cgstRs="
			+ cgstRs + ", sgstRs=" + sgstRs + ", userGstNo=" + userGstNo + ", userPhone=" + userPhone + ", billType="
			+ billType + "]";
}


}
