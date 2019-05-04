package com.monginis.ops.model.othitemstock;


public class OtherItemCurStock {
	
	private int id;
	private String itemId;
	private String itemName;
	private String frName;
	
	private float openingStock;
	
	private float damagedStock;
	private float purchaseQty;
	private float sellQty;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getFrName() {
		return frName;
	}
	public void setFrName(String frName) {
		this.frName = frName;
	}
	public float getOpeningStock() {
		return openingStock;
	}
	public void setOpeningStock(float openingStock) {
		this.openingStock = openingStock;
	}
	public float getDamagedStock() {
		return damagedStock;
	}
	public void setDamagedStock(float damagedStock) {
		this.damagedStock = damagedStock;
	}
	public float getPurchaseQty() {
		return purchaseQty;
	}
	public void setPurchaseQty(float purchaseQty) {
		this.purchaseQty = purchaseQty;
	}
	public float getSellQty() {
		return sellQty;
	}
	public void setSellQty(float sellQty) {
		this.sellQty = sellQty;
	}
	
	@Override
	public String toString() {
		return "OtherItemCurStock [id=" + id + ", itemId=" + itemId + ", itemName=" + itemName + ", frName=" + frName
				+ ", openingStock=" + openingStock + ", damagedStock=" + damagedStock + ", purchaseQty=" + purchaseQty
				+ ", sellQty=" + sellQty + "]";
	}

}
