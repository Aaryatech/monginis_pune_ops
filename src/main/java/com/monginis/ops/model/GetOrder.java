package com.monginis.ops.model;

import java.util.Date;


public class GetOrder {
	
	
	private int orderId;

	private String frName;
	
	private String catName;

	private String itemName;
	
	private int orderQty;
	
	private int Id;
	
	private String deliveryDate;
	
	private int isEdit;
	
	private int isPositive;
	
	private float editQty;


	public int getOrderId() {
		return orderId;
	}


	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public String getFrName() {
		return frName;
	}


	public void setFrName(String frName) {
		this.frName = frName;
	}


	public String getCatName() {
		return catName;
	}


	public void setCatName(String catName) {
		this.catName = catName;
	}


	public String getItemName() {
		return itemName;
	}


	public void setItemName(String itemName) {
		this.itemName = itemName;
	}


	public int getOrderQty() {
		return orderQty;
	}


	public void setOrderQty(int orderQty) {
		this.orderQty = orderQty;
	}


	


	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}


	public String getDeliveryDate() {
		return deliveryDate;
	}


	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}


	public int getIsEdit() {
		return isEdit;
	}


	public void setIsEdit(int isEdit) {
		this.isEdit = isEdit;
	}


	public int getIsPositive() {
		return isPositive;
	}


	public void setIsPositive(int isPositive) {
		this.isPositive = isPositive;
	}


	public float getEditQty() {
		return editQty;
	}


	public void setEditQty(float editQty) {
		this.editQty = editQty;
	}


	@Override
	public String toString() {
		return "GetOrder [orderId=" + orderId + ", frName=" + frName + ", catName=" + catName + ", itemName=" + itemName
				+ ", orderQty=" + orderQty + ", Id=" + Id + ", deliveryDate=" + deliveryDate + ", isEdit=" + isEdit
				+ ", isPositive=" + isPositive + ", editQty=" + editQty + "]";
	}



	
}
