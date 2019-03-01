package com.monginis.ops.model;

public class OtherStockItem {

	private int otherStockItemId;
	private int otherStockDetailId;
	private int otherStockHeaderId;
	private int otherStockItemCode;
	private String  otherStockItemName;
	private int openingStock;
	
	
	public int getOtherStockDetailId() {
		return otherStockDetailId;
	}
	public void setOtherStockDetailId(int otherStockDetailId) {
		this.otherStockDetailId = otherStockDetailId;
	}
	public int getOtherStockHeaderId() {
		return otherStockHeaderId;
	}
	public void setOtherStockHeaderId(int otherStockHeaderId) {
		this.otherStockHeaderId = otherStockHeaderId;
	}
	public int getOtherStockItemId() {
		return otherStockItemId;
	}
	public void setOtherStockItemId(int otherStockItemId) {
		this.otherStockItemId = otherStockItemId;
	}
	public int getOtherStockItemCode() {
		return otherStockItemCode;
	}
	public void setOtherStockItemCode(int otherStockItemCode) {
		this.otherStockItemCode = otherStockItemCode;
	}
	public String getOtherStockItemName() {
		return otherStockItemName;
	}
	public void setOtherStockItemName(String otherStockItemName) {
		this.otherStockItemName = otherStockItemName;
	}
	public int getOpeningStock() {
		return openingStock;
	}
	public void setOpeningStock(int openingStock) {
		this.openingStock = openingStock;
	}
	@Override
	public String toString() {
		return "OtherStockItem [otherStockItemId=" + otherStockItemId + ", otherStockDetailId=" + otherStockDetailId
				+ ", otherStockHeaderId=" + otherStockHeaderId + ", otherStockItemCode=" + otherStockItemCode
				+ ", otherStockItemName=" + otherStockItemName + ", openingStock=" + openingStock + "]";
	}
	
	
	
}
