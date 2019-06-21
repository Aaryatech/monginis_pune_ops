package com.monginis.ops.model;


public class SellBillDetailEdit{


	private int sellBillDetailNo;
		
	private int sellBillNo;
	
	private int catId;
	
	private int itemId;
	
	private String itemName;

	private float mrp;
	
	private int qty;
	
	private float mrpBaseRate;
	
	private float taxableAmt;
	
	private float sgstPer;
	
	private float sgstRs;
	
	private float cgstPer;
	
	private float cgstRs;
	
	private float igstPer;
	
	private float igstRs;
	
	private float totalTax;
	
	private float grandTotal;
	
	private String remark;
	
	private int delStatus;

	private int billStockType;

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

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
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

	public float getMrpBaseRate() {
		return mrpBaseRate;
	}

	public void setMrpBaseRate(float mrpBaseRate) {
		this.mrpBaseRate = mrpBaseRate;
	}

	public float getTaxableAmt() {
		return taxableAmt;
	}

	public void setTaxableAmt(float taxableAmt) {
		this.taxableAmt = taxableAmt;
	}

	public float getSgstPer() {
		return sgstPer;
	}

	public void setSgstPer(float sgstPer) {
		this.sgstPer = sgstPer;
	}

	public float getSgstRs() {
		return sgstRs;
	}

	public void setSgstRs(float sgstRs) {
		this.sgstRs = sgstRs;
	}

	public float getCgstPer() {
		return cgstPer;
	}

	public void setCgstPer(float cgstPer) {
		this.cgstPer = cgstPer;
	}

	public float getCgstRs() {
		return cgstRs;
	}

	public void setCgstRs(float cgstRs) {
		this.cgstRs = cgstRs;
	}

	public float getIgstPer() {
		return igstPer;
	}

	public void setIgstPer(float igstPer) {
		this.igstPer = igstPer;
	}

	public float getIgstRs() {
		return igstRs;
	}

	public void setIgstRs(float igstRs) {
		this.igstRs = igstRs;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getBillStockType() {
		return billStockType;
	}

	public void setBillStockType(int billStockType) {
		this.billStockType = billStockType;
	}

	@Override
	public String toString() {
		return "SellBillDetailEdit [sellBillDetailNo=" + sellBillDetailNo + ", sellBillNo=" + sellBillNo + ", catId="
				+ catId + ", itemId=" + itemId + ", itemName=" + itemName + ", mrp=" + mrp + ", qty=" + qty
				+ ", mrpBaseRate=" + mrpBaseRate + ", taxableAmt=" + taxableAmt + ", sgstPer=" + sgstPer + ", sgstRs="
				+ sgstRs + ", cgstPer=" + cgstPer + ", cgstRs=" + cgstRs + ", igstPer=" + igstPer + ", igstRs=" + igstRs
				+ ", totalTax=" + totalTax + ", grandTotal=" + grandTotal + ", remark=" + remark + ", delStatus="
				+ delStatus + ", billStockType=" + billStockType + "]";
	}
	
	
}
