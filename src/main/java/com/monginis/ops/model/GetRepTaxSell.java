package com.monginis.ops.model;

import java.sql.Date;


public class GetRepTaxSell {

	
	private int sellBillDetailNo;
	
	private String sellBillNo;
	
	private String billDate;

	private int frId;
	
	private String frName;
	
	private float tax_amount;
	
	private float tax_per;

	private String gstn;
	
	
	private float bill_amount;
	
	
	private float igst;
	
	
	private float cgst;
	
	
	private float sgst;

	private float cess;

	public int getSellBillDetailNo() {
		return sellBillDetailNo;
	}

	public void setSellBillDetailNo(int sellBillDetailNo) {
		this.sellBillDetailNo = sellBillDetailNo;
	}

	public String getSellBillNo() {
		return sellBillNo;
	}

	public void setSellBillNo(String sellBillNo) {
		this.sellBillNo = sellBillNo;
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

	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	public float getTax_amount() {
		return tax_amount;
	}

	public void setTax_amount(float tax_amount) {
		this.tax_amount = tax_amount;
	}

	public float getTax_per() {
		return tax_per;
	}

	public void setTax_per(float tax_per) {
		this.tax_per = tax_per;
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

	public float getIgst() {
		return igst;
	}

	public void setIgst(float igst) {
		this.igst = igst;
	}

	public float getCgst() {
		return cgst;
	}

	public void setCgst(float cgst) {
		this.cgst = cgst;
	}

	public float getSgst() {
		return sgst;
	}

	public void setSgst(float sgst) {
		this.sgst = sgst;
	}

	public float getCess() {
		return cess;
	}

	public void setCess(float cess) {
		this.cess = cess;
	}

	@Override
	public String toString() {
		return "GetRepTaxSell [sellBillDetailNo=" + sellBillDetailNo + ", sellBillNo=" + sellBillNo + ", billDate="
				+ billDate + ", frId=" + frId + ", frName=" + frName + ", tax_amount=" + tax_amount + ", tax_per="
				+ tax_per + ", gstn=" + gstn + ", bill_amount=" + bill_amount + ", igst=" + igst + ", cgst=" + cgst
				+ ", sgst=" + sgst + ", cess=" + cess + "]";
	}
	
	
}
