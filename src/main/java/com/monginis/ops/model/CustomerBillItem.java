package com.monginis.ops.model;

public class CustomerBillItem {
	
private int id;
private String itemId;
private String itemName;
private int catId;
private double mrp;
private double rate;
private int qty;
private double itemTax1;
private double itemTax2;
private double itemTax3;
private int billStockType;

private int totalSpStock;
private int totalRegStock;

private boolean isSpStockGretor;
private boolean isSpStockLessthanQty;
private boolean isRegOpStockLess;
private boolean isRegOpStockGretor;
private boolean isCurrentStockOver;



public int getTotalSpStock() {
	return totalSpStock;
}
public void setTotalSpStock(int totalSpStock) {
	this.totalSpStock = totalSpStock;
}
public int getTotalRegStock() {
	return totalRegStock;
}
public void setTotalRegStock(int totalRegStock) {
	this.totalRegStock = totalRegStock;
}
public boolean isCurrentStockOver() {
	return isCurrentStockOver;
}
public void setCurrentStockOver(boolean isCurrentStockOver) {
	this.isCurrentStockOver = isCurrentStockOver;
}
public boolean isRegOpStockGretor() {
	return isRegOpStockGretor;
}
public void setRegOpStockGretor(boolean isRegOpStockGretor) {
	this.isRegOpStockGretor = isRegOpStockGretor;
}
public boolean isRegOpStockLess() {
	return isRegOpStockLess;
}
public void setRegOpStockLess(boolean isRegOpStockLess) {
	this.isRegOpStockLess = isRegOpStockLess;
}
public int getBillStockType() {
	return billStockType;
}
public void setBillStockType(int billStockType) {
	this.billStockType = billStockType;
}
public boolean isSpStockGretor() {
	return isSpStockGretor;
}
public void setSpStockGretor(boolean isSpStockGretor) {
	this.isSpStockGretor = isSpStockGretor;
}
public boolean isSpStockLessthanQty() {
	return isSpStockLessthanQty;
}
public void setSpStockLessthanQty(boolean isSpStockLessthanQty) {
	this.isSpStockLessthanQty = isSpStockLessthanQty;
}
public double getItemTax1() {
	return itemTax1;
}
public void setItemTax1(double itemTax1) {
	this.itemTax1 = itemTax1;
}
public double getItemTax2() {
	return itemTax2;
}
public void setItemTax2(double itemTax2) {
	this.itemTax2 = itemTax2;
}
public double getItemTax3() {
	return itemTax3;
}
public void setItemTax3(double itemTax3) {
	this.itemTax3 = itemTax3;
}
public void setMrp(double mrp) {
	this.mrp = mrp;
}
public void setRate(double rate) {
	this.rate = rate;
}
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
public int getCatId() {
	return catId;
}
public void setCatId(int catId) {
	this.catId = catId;
}


public double getMrp() {
	return mrp;
}
public double getRate() {
	return rate;
}
public int getQty() {
	return qty;
}
public void setQty(int qty) {
	this.qty = qty;
}

@Override
public String toString() {
	return "CustomerBillItem [id=" + id + ", itemId=" + itemId + ", itemName=" + itemName + ", catId=" + catId
			+ ", mrp=" + mrp + ", rate=" + rate + ", qty=" + qty + ", itemTax1=" + itemTax1 + ", itemTax2=" + itemTax2
			+ ", itemTax3=" + itemTax3 + ", billStockType=" + billStockType + ", totalSpStock=" + totalSpStock
			+ ", totalRegStock=" + totalRegStock + ", isSpStockGretor=" + isSpStockGretor + ", isSpStockLessthanQty="
			+ isSpStockLessthanQty + ", isRegOpStockLess=" + isRegOpStockLess + ", isRegOpStockGretor="
			+ isRegOpStockGretor + ", isCurrentStockOver=" + isCurrentStockOver + "]";
}


 

}
