package com.monginis.ops.model;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Tax2Report implements Serializable{

	private int billNo;
	
    private String invoiceNo;
	
	private String billDate;
	
    private String frName;
	
	private String frGstNo;
	
	
	private float taxableAmtZero;
	
	private float taxableAmtFive;
	
	private float taxableAmtTwelve;
	
	private float taxableAmtEighteen;
	
	private float taxableAmtTwentyEight;
	
	private float taxableAmt;
	
	private float sgstAmtZero;
	
    private float sgstAmtFive;
	
	private float sgstAmtTwelve;
	
	private float sgstAmtEighteen;
	
	private float sgstAmtTwentyEight;
	
	private float sgstSum;
	
    private float cgstAmtZero;
	
    private float cgstAmtFive;
	
	private float cgstAmtTwelve;
	
	private float cgstAmtEighteen;
	
	private float cgstAmtTwentyEight;
	
	private float cgstSum;

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

	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	public String getFrGstNo() {
		return frGstNo;
	}

	public void setFrGstNo(String frGstNo) {
		this.frGstNo = frGstNo;
	}

	public float getTaxableAmtZero() {
		return taxableAmtZero;
	}

	public void setTaxableAmtZero(float taxableAmtZero) {
		this.taxableAmtZero = taxableAmtZero;
	}

	public float getTaxableAmtFive() {
		return taxableAmtFive;
	}

	public void setTaxableAmtFive(float taxableAmtFive) {
		this.taxableAmtFive = taxableAmtFive;
	}

	public float getTaxableAmtTwelve() {
		return taxableAmtTwelve;
	}

	public void setTaxableAmtTwelve(float taxableAmtTwelve) {
		this.taxableAmtTwelve = taxableAmtTwelve;
	}

	public float getTaxableAmtEighteen() {
		return taxableAmtEighteen;
	}

	public void setTaxableAmtEighteen(float taxableAmtEighteen) {
		this.taxableAmtEighteen = taxableAmtEighteen;
	}

	public float getTaxableAmtTwentyEight() {
		return taxableAmtTwentyEight;
	}

	public void setTaxableAmtTwentyEight(float taxableAmtTwentyEight) {
		this.taxableAmtTwentyEight = taxableAmtTwentyEight;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getSgstAmtZero() {
		return sgstAmtZero;
	}

	public void setSgstAmtZero(float sgstAmtZero) {
		this.sgstAmtZero = sgstAmtZero;
	}

	public float getSgstAmtFive() {
		return sgstAmtFive;
	}

	public void setSgstAmtFive(float sgstAmtFive) {
		this.sgstAmtFive = sgstAmtFive;
	}

	public float getSgstAmtTwelve() {
		return sgstAmtTwelve;
	}

	public void setSgstAmtTwelve(float sgstAmtTwelve) {
		this.sgstAmtTwelve = sgstAmtTwelve;
	}

	public float getSgstAmtEighteen() {
		return sgstAmtEighteen;
	}

	public void setSgstAmtEighteen(float sgstAmtEighteen) {
		this.sgstAmtEighteen = sgstAmtEighteen;
	}

	public float getSgstAmtTwentyEight() {
		return sgstAmtTwentyEight;
	}

	public void setSgstAmtTwentyEight(float sgstAmtTwentyEight) {
		this.sgstAmtTwentyEight = sgstAmtTwentyEight;
	}

	public float getSgstSum() {
		return sgstSum;
	}

	public void setSgstSum(float sgstSum) {
		this.sgstSum = sgstSum;
	}

	public float getCgstAmtZero() {
		return cgstAmtZero;
	}

	public void setCgstAmtZero(float cgstAmtZero) {
		this.cgstAmtZero = cgstAmtZero;
	}

	public float getCgstAmtFive() {
		return cgstAmtFive;
	}

	public void setCgstAmtFive(float cgstAmtFive) {
		this.cgstAmtFive = cgstAmtFive;
	}

	public float getCgstAmtTwelve() {
		return cgstAmtTwelve;
	}

	public void setCgstAmtTwelve(float cgstAmtTwelve) {
		this.cgstAmtTwelve = cgstAmtTwelve;
	}

	public float getCgstAmtEighteen() {
		return cgstAmtEighteen;
	}

	public void setCgstAmtEighteen(float cgstAmtEighteen) {
		this.cgstAmtEighteen = cgstAmtEighteen;
	}

	public float getCgstAmtTwentyEight() {
		return cgstAmtTwentyEight;
	}

	public void setCgstAmtTwentyEight(float cgstAmtTwentyEight) {
		this.cgstAmtTwentyEight = cgstAmtTwentyEight;
	}

	public float getCgstSum() {
		return cgstSum;
	}

	public void setCgstSum(float cgstSum) {
		this.cgstSum = cgstSum;
	}

	@Override
	public String toString() {
		return "Tax2Report [billNo=" + billNo + ", invoiceNo=" + invoiceNo + ", billDate=" + billDate + ", frName="
				+ frName + ", frGstNo=" + frGstNo + ", taxableAmtZero=" + taxableAmtZero + ", taxableAmtFive="
				+ taxableAmtFive + ", taxableAmtTwelve=" + taxableAmtTwelve + ", taxableAmtEighteen="
				+ taxableAmtEighteen + ", taxableAmtTwentyEight=" + taxableAmtTwentyEight + ", taxableAmt=" + taxableAmt
				+ ", sgstAmtZero=" + sgstAmtZero + ", sgstAmtFive=" + sgstAmtFive + ", sgstAmtTwelve=" + sgstAmtTwelve
				+ ", sgstAmtEighteen=" + sgstAmtEighteen + ", sgstAmtTwentyEight=" + sgstAmtTwentyEight + ", sgstSum="
				+ sgstSum + ", cgstAmtZero=" + cgstAmtZero + ", cgstAmtFive=" + cgstAmtFive + ", cgstAmtTwelve="
				+ cgstAmtTwelve + ", cgstAmtEighteen=" + cgstAmtEighteen + ", cgstAmtTwentyEight=" + cgstAmtTwentyEight
				+ ", cgstSum=" + cgstSum + "]";
	}
	
	
	
}
