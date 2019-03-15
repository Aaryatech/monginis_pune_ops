package com.monginis.ops.model;

public class ItemWiseDetail {

    private String billNo;
	
	private int itemId;
	
	private int billDetailNo;
	
	private float rate;
	
	private int qty;
	
	private int grnType;
	
	private float total;
	
	private String itemName;

	private String billDate;
	
	
	public int getGrnType() {
		return grnType;
	}

	public void setGrnType(int grnType) {
		this.grnType = grnType;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}


	public int getItemId() {
		return itemId;
	}

	public void setItemId(int itemId) {
		this.itemId = itemId;
	}

	public int getBillDetailNo() {
		return billDetailNo;
	}

	public void setBillDetailNo(int billDetailNo) {
		this.billDetailNo = billDetailNo;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "ItemWiseDetail [billNo=" + billNo + ", itemId=" + itemId + ", billDetailNo=" + billDetailNo + ", rate="
				+ rate + ", qty=" + qty + ", grnType=" + grnType + ", total=" + total + ", itemName=" + itemName
				+ ", billDate=" + billDate + "]";
	}

	
	
}
