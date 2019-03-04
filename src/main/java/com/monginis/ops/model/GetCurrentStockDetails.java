package com.monginis.ops.model;

public class GetCurrentStockDetails {

	private int stockDetailId;

	private String itemId;

	private int id;

	private String itemName;

	private int regOpeningStock;

	private double spOpeningStock;

	private int regTotalPurchase;

	private double spTotalPurchase;

	private int regTotalGrnGvn;

	private int regTotalSell;

	private int spTotalSell;

	private int stockHeaderId;

	private int currentRegStock;

	private int currentSpStock;

	private int reOrderQty;

	public int getStockDetailId() {
		return stockDetailId;
	}

	public void setStockDetailId(int stockDetailId) {
		this.stockDetailId = stockDetailId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public int getRegOpeningStock() {
		return regOpeningStock;
	}

	public void setRegOpeningStock(int regOpeningStock) {
		this.regOpeningStock = regOpeningStock;
	}

	public double getSpOpeningStock() {
		return spOpeningStock;
	}

	public void setSpOpeningStock(double spOpeningStock) {
		this.spOpeningStock = spOpeningStock;
	}

	public int getRegTotalPurchase() {
		return regTotalPurchase;
	}

	public void setRegTotalPurchase(int regTotalPurchase) {
		this.regTotalPurchase = regTotalPurchase;
	}

	public double getSpTotalPurchase() {
		return spTotalPurchase;
	}

	public void setSpTotalPurchase(double spTotalPurchase) {
		this.spTotalPurchase = spTotalPurchase;
	}

	public int getRegTotalGrnGvn() {
		return regTotalGrnGvn;
	}

	public void setRegTotalGrnGvn(int regTotalGrnGvn) {
		this.regTotalGrnGvn = regTotalGrnGvn;
	}

	public int getRegTotalSell() {
		return regTotalSell;
	}

	public void setRegTotalSell(int regTotalSell) {
		this.regTotalSell = regTotalSell;
	}

	public int getSpTotalSell() {
		return spTotalSell;
	}

	public void setSpTotalSell(int spTotalSell) {
		this.spTotalSell = spTotalSell;
	}

	public int getStockHeaderId() {
		return stockHeaderId;
	}

	public void setStockHeaderId(int stockHeaderId) {
		this.stockHeaderId = stockHeaderId;
	}

	public int getCurrentRegStock() {
		return currentRegStock;
	}

	public void setCurrentRegStock(int currentRegStock) {
		this.currentRegStock = currentRegStock;
	}

	public int getCurrentSpStock() {
		return currentSpStock;
	}

	public void setCurrentSpStock(int currentSpStock) {
		this.currentSpStock = currentSpStock;
	}

	public int getReOrderQty() {
		return reOrderQty;
	}

	public void setReOrderQty(int reOrderQty) {
		this.reOrderQty = reOrderQty;
	}

	@Override
	public String toString() {
		return "GetCurrentStockDetails [stockDetailId=" + stockDetailId + ", itemId=" + itemId + ", id=" + id
				+ ", itemName=" + itemName + ", regOpeningStock=" + regOpeningStock + ", spOpeningStock="
				+ spOpeningStock + ", regTotalPurchase=" + regTotalPurchase + ", spTotalPurchase=" + spTotalPurchase
				+ ", regTotalGrnGvn=" + regTotalGrnGvn + ", regTotalSell=" + regTotalSell + ", spTotalSell="
				+ spTotalSell + ", stockHeaderId=" + stockHeaderId + ", currentRegStock=" + currentRegStock
				+ ", currentSpStock=" + currentSpStock + ", reOrderQty=" + reOrderQty + "]";
	}

}
