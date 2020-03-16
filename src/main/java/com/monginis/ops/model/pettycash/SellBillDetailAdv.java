package com.monginis.ops.model.pettycash;

public class SellBillDetailAdv {
	
	private String id;
	private float sellQtyMrp;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getSellQtyMrp() {
		return sellQtyMrp;
	}

	public void setSellQtyMrp(float sellQtyMrp) {
		this.sellQtyMrp = sellQtyMrp;
	}

	@Override
	public String toString() {
		return "SellBillDetailAdv [id=" + id + ", sellQtyMrp=" + sellQtyMrp + "]";
	}

}
