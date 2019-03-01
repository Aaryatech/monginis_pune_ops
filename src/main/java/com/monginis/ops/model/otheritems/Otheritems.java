package com.monginis.ops.model.otheritems;

public class Otheritems {
	
	private int itemId;
	private String itemCode;
	private String itemName;
	private String uom;
	private String hsnCode;
	private String taxDesc;
	private float purchaseRate;
	private float sellRate;
	private float cgstPer;
	private float sgstPer;
	private float igstPer;
	private float cessPer;
	private float totalPer;
	private int frId;     //franchise Id
	private int isActive;
	private int delStatus;
	private int exInt1;
	private int exInt2;
	private int exInt3;
	private String exVar1;
	private String exVar2;
	private String exVar3;
	private float exFloat1;
	private float exFloat2;
	private float exFloat3;
	private float exFloat4;
	
	public float getExFloat4() {
		return exFloat4;
	}
	public void setExFloat4(float exFloat4) {
		this.exFloat4 = exFloat4;
	}
	public int getItemId() {
		return itemId;
	}
	public void setItemId(int itemId) {
		this.itemId = itemId;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getHsnCode() {
		return hsnCode;
	}
	public void setHsnCode(String hsnCode) {
		this.hsnCode = hsnCode;
	}
	public String getTaxDesc() {
		return taxDesc;
	}
	public void setTaxDesc(String taxDesc) {
		this.taxDesc = taxDesc;
	}
	public float getPurchaseRate() {
		return purchaseRate;
	}
	public void setPurchaseRate(float purchaseRate) {
		this.purchaseRate = purchaseRate;
	}
	public float getSellRate() {
		return sellRate;
	}
	public void setSellRate(float sellRate) {
		this.sellRate = sellRate;
	}
	public float getCgstPer() {
		return cgstPer;
	}
	public void setCgstPer(float cgstPer) {
		this.cgstPer = cgstPer;
	}
	public float getSgstPer() {
		return sgstPer;
	}
	public void setSgstPer(float sgstPer) {
		this.sgstPer = sgstPer;
	}
	public float getIgstPer() {
		return igstPer;
	}
	public void setIgstPer(float igstPer) {
		this.igstPer = igstPer;
	}
	public float getTotalPer() {
		return totalPer;
	}
	public void setTotalPer(float totalPer) {
		this.totalPer = totalPer;
	}	
	public int getIsActive() {
		return isActive;
	}
	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public float getCessPer() {
		return cessPer;
	}
	public void setCessPer(float cessPer) {
		this.cessPer = cessPer;
	}
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
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
	public int getExInt3() {
		return exInt3;
	}
	public void setExInt3(int exInt3) {
		this.exInt3 = exInt3;
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
	public String getExVar3() {
		return exVar3;
	}
	public void setExVar3(String exVar3) {
		this.exVar3 = exVar3;
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
	public float getExFloat3() {
		return exFloat3;
	}
	public void setExFloat3(float exFloat3) {
		this.exFloat3 = exFloat3;
	}
	@Override
	public String toString() {
		return "Otheritems [itemId=" + itemId + ", itemCode=" + itemCode + ", itemName=" + itemName + ", uom=" + uom
				+ ", hsnCode=" + hsnCode + ", taxDesc=" + taxDesc + ", purchaseRate=" + purchaseRate + ", sellRate="
				+ sellRate + ", cgstPer=" + cgstPer + ", sgstPer=" + sgstPer + ", igstPer=" + igstPer + ", cessPer="
				+ cessPer + ", totalPer=" + totalPer + ", frId=" + frId + ", isActive=" + isActive + ", delStatus="
				+ delStatus + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exInt3=" + exInt3 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", exVar3=" + exVar3 + ", exFloat1=" + exFloat1 + ", exFloat2=" + exFloat2
				+ ", exFloat3=" + exFloat3 + ", exFloat4=" + exFloat4 + "]";
	}
		
}
