package com.monginis.ops.model;

public class AddItemInOtherBill {
	
	private int id;
	private String itemId;
	private String itemName;
	private Integer itemGrp1;
	private Double itemRate1;
	private Integer qty;
	private Double baseRate;
	private float discPer;
	private float discAmt;
	private Double taxableAmt;
	private Double itemMrp1;
	private Double itemTax1;
	private Double itemTax2;
	private Double itemTax3;
	private Double itemTax1rs;
	private Double itemTax2rs;
	private Double itemTax3rs;
	private Double grandTotal;
	private Integer shelfLife;
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
	public Integer getItemGrp1() {
		return itemGrp1;
	}
	public void setItemGrp1(Integer itemGrp1) {
		this.itemGrp1 = itemGrp1;
	}
	public Integer getQty() {
		return qty;
	}
	public void setQty(Integer qty) {
		this.qty = qty;
	}
	public Double getItemMrp1() {
		return itemMrp1;
	}
	public void setItemMrp1(Double itemMrp1) {
		this.itemMrp1 = itemMrp1;
	}
	public Double getItemTax1() {
		return itemTax1;
	}
	public void setItemTax1(Double itemTax1) {
		this.itemTax1 = itemTax1;
	}
	public Double getItemTax2() {
		return itemTax2;
	}
	public void setItemTax2(Double itemTax2) {
		this.itemTax2 = itemTax2;
	}
	public Double getItemTax3() {
		return itemTax3;
	}
	public void setItemTax3(Double itemTax3) {
		this.itemTax3 = itemTax3;
	}
	public Double getItemTax1rs() {
		return itemTax1rs;
	}
	public void setItemTax1rs(Double itemTax1rs) {
		this.itemTax1rs = itemTax1rs;
	}
	public Double getItemTax2rs() {
		return itemTax2rs;
	}
	public void setItemTax2rs(Double itemTax2rs) {
		this.itemTax2rs = itemTax2rs;
	}
	public Double getItemTax3rs() {
		return itemTax3rs;
	}
	public void setItemTax3rs(Double itemTax3rs) {
		this.itemTax3rs = itemTax3rs;
	} 
	public Double getItemRate1() {
		return itemRate1;
	}
	public void setItemRate1(Double itemRate1) {
		this.itemRate1 = itemRate1;
	}
	

	public Double getTaxableAmt() {
		return taxableAmt;
	}
	public void setTaxableAmt(Double taxableAmt) {
		this.taxableAmt = taxableAmt;
	}
	
	public Double getGrandTotal() {
		return grandTotal;
	}
	public void setGrandTotal(Double grandTotal) {
		this.grandTotal = grandTotal;
	}
	
	
	public Double getBaseRate() {
		return baseRate;
	}
	public void setBaseRate(Double baseRate) {
		this.baseRate = baseRate;
	}
	
	public float getDiscPer() {
		return discPer;
	}
	public void setDiscPer(float discPer) {
		this.discPer = discPer;
	}
	public float getDiscAmt() {
		return discAmt;
	}
	public void setDiscAmt(float discAmt) {
		this.discAmt = discAmt;
	}
	
	public Integer getShelfLife() {
		return shelfLife;
	}
	public void setShelfLife(Integer shelfLife) {
		this.shelfLife = shelfLife;
	}
	@Override
	public String toString() {
		return "AddItemInOtherBill [id=" + id + ", itemId=" + itemId + ", itemName=" + itemName + ", itemGrp1="
				+ itemGrp1 + ", itemRate1=" + itemRate1 + ", qty=" + qty + ", baseRate=" + baseRate + ", discPer="
				+ discPer + ", discAmt=" + discAmt + ", taxableAmt=" + taxableAmt + ", itemMrp1=" + itemMrp1
				+ ", itemTax1=" + itemTax1 + ", itemTax2=" + itemTax2 + ", itemTax3=" + itemTax3 + ", itemTax1rs="
				+ itemTax1rs + ", itemTax2rs=" + itemTax2rs + ", itemTax3rs=" + itemTax3rs + ", grandTotal="
				+ grandTotal + ", shelfLife=" + shelfLife + "]";
	}
	
	

}
