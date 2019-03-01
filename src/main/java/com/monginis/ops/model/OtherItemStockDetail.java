package com.monginis.ops.model;

public class OtherItemStockDetail {

	private int otherStockDetailId;
	private int otherStockHeaderId;
	private int otherItemId;
	private int openingStock;
	private int damageStock;
	private int closingStock;
	private int purchaseStock;
	private int salesStock;
	private int delStatus;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private float exFloat1;
	private float exFloat2;
	private String otherItemName;
	
	
	public String getOtherItemName() {
		return otherItemName;
	}
	public void setOtherItemName(String otherItemName) {
		this.otherItemName = otherItemName;
	}
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
	public int getOtherItemId() {
		return otherItemId;
	}
	public void setOtherItemId(int otherItemId) {
		this.otherItemId = otherItemId;
	}
	public int getOpeningStock() {
		return openingStock;
	}
	public void setOpeningStock(int openingStock) {
		this.openingStock = openingStock;
	}
	public int getDamageStock() {
		return damageStock;
	}
	public void setDamageStock(int damageStock) {
		this.damageStock = damageStock;
	}
	public int getClosingStock() {
		return closingStock;
	}
	public void setClosingStock(int closingStock) {
		this.closingStock = closingStock;
	}
	public int getPurchaseStock() {
		return purchaseStock;
	}
	public void setPurchaseStock(int purchaseStock) {
		this.purchaseStock = purchaseStock;
	}
	public int getSalesStock() {
		return salesStock;
	}
	public void setSalesStock(int salesStock) {
		this.salesStock = salesStock;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public int getExInt1() {
		return exInt1;
	}
	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}
	public int getExInt2() {
		return exInt2;
	}
	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}
	public String getExVar1() {
		return exVar1;
	}
	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}
	public String getExVar2() {
		return exVar2;
	}
	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}
	public float getExFloat1() {
		return exFloat1;
	}
	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}
	public float getExFloat2() {
		return exFloat2;
	}
	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
	}
	@Override
	public String toString() {
		return "OtherItemStockDetail [otherStockDetailId=" + otherStockDetailId + ", otherStockHeaderId="
				+ otherStockHeaderId + ", otherItemId=" + otherItemId + ", openingStock=" + openingStock
				+ ", damageStock=" + damageStock + ", closingStock=" + closingStock + ", purchaseStock=" + purchaseStock
				+ ", salesStock=" + salesStock + ", delStatus=" + delStatus + ", exInt1=" + exInt1 + ", exInt2="
				+ exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exFloat1=" + exFloat1 + ", exFloat2="
				+ exFloat2 + ", otherItemName=" + otherItemName + "]";
	}
	
	
	
	
}
