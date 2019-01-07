package com.monginis.ops.model;




public class PostFrItemStockDetail {

	 

	private int openingStockDetailId;
	
	private int openingStockHeaderId;

	private String 	itemId;
	
	private int regOpeningStock;
	
	private int spOpeningStock;

	
	private int physicalStock;
	
	private int stockDifference;
	
	private int regTotalPurchase;
	
	private int spTotalPurchase;

	private int regTotalGrnGvn;
	
	private int regTotalSell;
	
	private int spTotalSell;

	
	private String remark;
	private String itemName;



	public int getOpeningStockDetailId() {
		return openingStockDetailId;
	}


	public void setOpeningStockDetailId(int openingStockDetailId) {
		this.openingStockDetailId = openingStockDetailId;
	}


	public int getOpeningStockHeaderId() {
		return openingStockHeaderId;
	}


	public void setOpeningStockHeaderId(int openingStockHeaderId) {
		this.openingStockHeaderId = openingStockHeaderId;
	}


	

	public String getItemId() {
		return itemId;
	}


	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public int getRegOpeningStock() {
		return regOpeningStock;
	}


	public void setRegOpeningStock(int regOpeningStock) {
		this.regOpeningStock = regOpeningStock;
	}


	public int getSpOpeningStock() {
		return spOpeningStock;
	}


	public void setSpOpeningStock(int spOpeningStock) {
		this.spOpeningStock = spOpeningStock;
	}


	public int getPhysicalStock() {
		return physicalStock;
	}


	public void setPhysicalStock(int physicalStock) {
		this.physicalStock = physicalStock;
	}


	public int getStockDifference() {
		return stockDifference;
	}


	public void setStockDifference(int stockDifference) {
		this.stockDifference = stockDifference;
	}


	public int getRegTotalPurchase() {
		return regTotalPurchase;
	}


	public void setRegTotalPurchase(int regTotalPurchase) {
		this.regTotalPurchase = regTotalPurchase;
	}


	public int getSpTotalPurchase() {
		return spTotalPurchase;
	}


	public void setSpTotalPurchase(int spTotalPurchase) {
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


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	@Override
	public String toString() {
		return "PostFrItemStockDetail [openingStockDetailId=" + openingStockDetailId + ", openingStockHeaderId="
				+ openingStockHeaderId + ", itemId=" + itemId + ", regOpeningStock=" + regOpeningStock
				+ ", spOpeningStock=" + spOpeningStock + ", physicalStock=" + physicalStock + ", stockDifference="
				+ stockDifference + ", regTotalPurchase=" + regTotalPurchase + ", spTotalPurchase=" + spTotalPurchase
				+ ", regTotalGrnGvn=" + regTotalGrnGvn + ", regTotalSell=" + regTotalSell + ", spTotalSell="
				+ spTotalSell + ", remark=" + remark + "]";
	}

	
	
	
	

}
