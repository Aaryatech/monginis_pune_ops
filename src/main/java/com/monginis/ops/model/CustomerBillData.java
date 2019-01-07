package com.monginis.ops.model;

import java.util.List;

public class CustomerBillData {
	
private String custName;
private String gstNo;
private long phoneNo;
List<CustomerBillItem> customerBillList;
private double total;
private double discount;
private double grandTotal;
private double paidAmount;
private double remainingAmount;


public double getPaidAmount() {
	return paidAmount;
}


public void setPaidAmount(double paidAmount) {
	this.paidAmount = paidAmount;
}


public double getRemainingAmount() {
	return remainingAmount;
}


public void setRemainingAmount(double remainingAmount) {
	this.remainingAmount = remainingAmount;
}


public String getCustName() {
	return custName;
}


public void setCustName(String custName) {
	this.custName = custName;
}


public String getGstNo() {
	return gstNo;
}


public void setGstNo(String gstNo) {
	this.gstNo = gstNo;
}


public long getPhoneNo() {
	return phoneNo;
}


public void setPhoneNo(long phoneNo) {
	this.phoneNo = phoneNo;
}


public List<CustomerBillItem> getCustomerBillList() {
	return customerBillList;
}


public void setCustomerBillList(List<CustomerBillItem> customerBillList) {
	this.customerBillList = customerBillList;
}


public double getTotal() {
	return total;
}


public void setTotal(double total) {
	this.total = total;
}


public double getDiscount() {
	return discount;
}


public void setDiscount(double discount) {
	this.discount = discount;
}


public double getGrandTotal() {
	return grandTotal;
}


public void setGrandTotal(double grandTotal) {
	this.grandTotal = grandTotal;
}


@Override
public String toString() {
	return "CustomerBillData [custName=" + custName + ", gstNo=" + gstNo + ", phoneNo=" + phoneNo
			+ ", customerBillList=" + customerBillList + ", total=" + total + ", discount=" + discount + ", grandTotal="
			+ grandTotal + ", paidAmount=" + paidAmount + ", remainingAmount=" + remainingAmount + "]";
}




}
