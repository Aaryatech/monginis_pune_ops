package com.monginis.ops.model.grngvn;


public class StockForAutoGrnGvn {

	private int id;
	
	private String itemId;

	private String itemName;

	private int regCurrentStock;

	private int spCurrentStock;
	
	private int purchaseQty;

	private int pushQty;

	private int regSellQty;

	private int spSellQty;

	private int grnGvnQty;
	
	
	
	

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

	public int getRegCurrentStock() {
		return regCurrentStock;
	}

	public void setRegCurrentStock(int regCurrentStock) {
		this.regCurrentStock = regCurrentStock;
	}

	public int getSpCurrentStock() {
		return spCurrentStock;
	}

	public void setSpCurrentStock(int spCurrentStock) {
		this.spCurrentStock = spCurrentStock;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPurchaseQty() {
		return purchaseQty;
	}

	public void setPurchaseQty(int purchaseQty) {
		this.purchaseQty = purchaseQty;
	}

	public int getPushQty() {
		return pushQty;
	}

	public void setPushQty(int pushQty) {
		this.pushQty = pushQty;
	}

	
	public int getRegSellQty() {
		return regSellQty;
	}

	public void setRegSellQty(int regSellQty) {
		this.regSellQty = regSellQty;
	}

	public int getSpSellQty() {
		return spSellQty;
	}

	public void setSpSellQty(int spSellQty) {
		this.spSellQty = spSellQty;
	}

	public int getGrnGvnQty() {
		return grnGvnQty;
	}

	public void setGrnGvnQty(int grnGvnQty) {
		this.grnGvnQty = grnGvnQty;
	}

	@Override
	public String toString() {
		return "StockForAutoGrnGvn [id=" + id + ", itemId=" + itemId + ", itemName=" + itemName + ", regCurrentStock="
				+ regCurrentStock + ", spCurrentStock=" + spCurrentStock + ", purchaseQty=" + purchaseQty + ", pushQty="
				+ pushQty + ", regSellQty=" + regSellQty + ", spSellQty=" + spSellQty + ", grnGvnQty=" + grnGvnQty
				+ "]";
	}


}
