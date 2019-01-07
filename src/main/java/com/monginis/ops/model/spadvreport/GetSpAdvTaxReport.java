package com.monginis.ops.model.spadvreport;

public class GetSpAdvTaxReport {

	
	
	private int spOrderNo;

	private String invoiceNo;

	

	private float tax1;
	private float tax2;

	private float tax1Amt;
	private float tax2Amt;

	private String delDate;
	private float spAdvance;

	private float rmAmount;
	private float spGrandTotal;

	private float baseMrp;
	private String spName;
	private String spHsncd;
	
	private float total;
	
	public int getSpOrderNo() {
		return spOrderNo;
	}
	public void setSpOrderNo(int spOrderNo) {
		this.spOrderNo = spOrderNo;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public float getTax1() {
		return tax1;
	}
	public void setTax1(float tax1) {
		this.tax1 = tax1;
	}
	public float getTax2() {
		return tax2;
	}
	public void setTax2(float tax2) {
		this.tax2 = tax2;
	}
	public float getTax1Amt() {
		return tax1Amt;
	}
	public void setTax1Amt(float tax1Amt) {
		this.tax1Amt = tax1Amt;
	}
	public float getTax2Amt() {
		return tax2Amt;
	}
	public void setTax2Amt(float tax2Amt) {
		this.tax2Amt = tax2Amt;
	}
	
	public String getDelDate() {
		return delDate;
	}
	public void setDelDate(String delDate) {
		this.delDate = delDate;
	}
	public float getSpAdvance() {
		return spAdvance;
	}
	public void setSpAdvance(float spAdvance) {
		this.spAdvance = spAdvance;
	}
	public float getRmAmount() {
		return rmAmount;
	}
	public void setRmAmount(float rmAmount) {
		this.rmAmount = rmAmount;
	}
	public float getSpGrandTotal() {
		return spGrandTotal;
	}
	public void setSpGrandTotal(float spGrandTotal) {
		this.spGrandTotal = spGrandTotal;
	}
	public float getBaseMrp() {
		return baseMrp;
	}
	public void setBaseMrp(float baseMrp) {
		this.baseMrp = baseMrp;
	}
	public String getSpName() {
		return spName;
	}
	public void setSpName(String spName) {
		this.spName = spName;
	}
	public String getSpHsncd() {
		return spHsncd;
	}
	public void setSpHsncd(String spHsncd) {
		this.spHsncd = spHsncd;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
}
