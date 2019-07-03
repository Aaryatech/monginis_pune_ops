package com.monginis.ops.model;

import java.util.List;

public class FrBillPrint {
	
	public List<GetBillDetailPrint> billDetailsList;
	
	int frId;
	int billNo;
	String frName;
	String frAddress;
	String invoiceNo;
	int isSameState;
	String billDate;
	
	String amtInWords;
	
	float grandTotal;
	private String partyName;//new -08 feb 19
	private String partyGstin;//new -08 feb 19
	private String partyAddress;//new -08 feb 19
	private String vehNo;//new -02 july 19
	private String billTime;//new -02 july 19
	private String exVarchar1;//new -02 july 19
	private String exVarchar2;//new -02 july 19
	
	
	public List<SubCategory> subCatList;
	
	Company company;
	
	
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
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public List<GetBillDetailPrint> getBillDetailsList() {
		return billDetailsList;
	}
	public void setBillDetailsList(List<GetBillDetailPrint> billDetails) {
		this.billDetailsList = billDetails;
	}
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
	}
	public int getBillNo() {
		return billNo;
	}
	public void setBillNo(int billNo) {
		this.billNo = billNo;
	}
	public String getFrName() {
		return frName;
	}
	public void setFrName(String frName) {
		this.frName = frName;
	}
	public String getFrAddress() {
		return frAddress;
	}
	public void setFrAddress(String frAddress) {
		this.frAddress = frAddress;
	}
	public int getIsSameState() {
		return isSameState;
	}
	public void setIsSameState(int isSameState) {
		this.isSameState = isSameState;
	}
	public String getBillDate() {
		return billDate;
	}
	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}
	
	
	public List<SubCategory> getSubCatList() {
		return subCatList;
	}
	public void setSubCatList(List<SubCategory> subCatList) {
		this.subCatList = subCatList;
	}
	public String getAmtInWords() {
		return amtInWords;
	}
	public void setAmtInWords(String amtInWords) {
		this.amtInWords = amtInWords;
	}
	public float getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(float grandTotal) {
		this.grandTotal = grandTotal;
	}
	@Override
	public String toString() {
		return "FrBillPrint [billDetailsList=" + billDetailsList + ", frId=" + frId + ", billNo=" + billNo + ", frName="
				+ frName + ", frAddress=" + frAddress + ", invoiceNo=" + invoiceNo + ", isSameState=" + isSameState
				+ ", billDate=" + billDate + ", amtInWords=" + amtInWords + ", grandTotal=" + grandTotal
				+ ", partyName=" + partyName + ", partyGstin=" + partyGstin + ", partyAddress=" + partyAddress
				+ ", vehNo=" + vehNo + ", billTime=" + billTime + ", exVarchar1=" + exVarchar1 + ", exVarchar2="
				+ exVarchar2 + ", subCatList=" + subCatList + ", company=" + company + "]";
	}
    

}
