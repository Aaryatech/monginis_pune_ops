package com.monginis.ops.dailysales;

public class DailySalesRegular{

	
	private int catId;
	
	private float billQty;
	
	private float billQtyRate;
	
	private float billQtyMrp;
	
	private float grnGvnQty;
	
	private float grnGvnAmt;
	
	private float sellQty;
	
	private float sellQtyRate;
	
	private float sellQtyMrp;
	
	private float regOpeningStock;
	
	private float regOpeningStockRate;
	
	private float regOpeningStockMrp;

	public int getCatId() {
		return catId;
	}

	public void setCatId(int catId) {
		this.catId = catId;
	}

	public float getBillQty() {
		return billQty;
	}

	public void setBillQty(float billQty) {
		this.billQty = billQty;
	}

	public float getBillQtyRate() {
		return billQtyRate;
	}

	public void setBillQtyRate(float billQtyRate) {
		this.billQtyRate = billQtyRate;
	}

	public float getBillQtyMrp() {
		return billQtyMrp;
	}

	public void setBillQtyMrp(float billQtyMrp) {
		this.billQtyMrp = billQtyMrp;
	}

	public float getGrnGvnQty() {
		return grnGvnQty;
	}

	public void setGrnGvnQty(float grnGvnQty) {
		this.grnGvnQty = grnGvnQty;
	}

	public float getGrnGvnAmt() {
		return grnGvnAmt;
	}

	public void setGrnGvnAmt(float grnGvnAmt) {
		this.grnGvnAmt = grnGvnAmt;
	}

	public float getSellQty() {
		return sellQty;
	}

	public void setSellQty(float sellQty) {
		this.sellQty = sellQty;
	}

	public float getSellQtyRate() {
		return sellQtyRate;
	}

	public void setSellQtyRate(float sellQtyRate) {
		this.sellQtyRate = sellQtyRate;
	}

	public float getSellQtyMrp() {
		return sellQtyMrp;
	}

	public void setSellQtyMrp(float sellQtyMrp) {
		this.sellQtyMrp = sellQtyMrp;
	}

	public float getRegOpeningStock() {
		return regOpeningStock;
	}

	public void setRegOpeningStock(float regOpeningStock) {
		this.regOpeningStock = regOpeningStock;
	}

	public float getRegOpeningStockRate() {
		return regOpeningStockRate;
	}

	public void setRegOpeningStockRate(float regOpeningStockRate) {
		this.regOpeningStockRate = regOpeningStockRate;
	}

	public float getRegOpeningStockMrp() {
		return regOpeningStockMrp;
	}

	public void setRegOpeningStockMrp(float regOpeningStockMrp) {
		this.regOpeningStockMrp = regOpeningStockMrp;
	}

	@Override
	public String toString() {
		return "DailySalesRegular [catId=" + catId + ", billQty=" + billQty + ", billQtyRate=" + billQtyRate
				+ ", billQtyMrp=" + billQtyMrp + ", grnGvnQty=" + grnGvnQty + ", grnGvnAmt=" + grnGvnAmt + ", sellQty="
				+ sellQty + ", sellQtyRate=" + sellQtyRate + ", sellQtyMrp=" + sellQtyMrp + ", regOpeningStock="
				+ regOpeningStock + ", regOpeningStockRate=" + regOpeningStockRate + ", regOpeningStockMrp="
				+ regOpeningStockMrp + "]";
	}
	
	
}
