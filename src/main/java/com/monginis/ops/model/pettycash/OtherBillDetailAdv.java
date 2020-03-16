package com.monginis.ops.model.pettycash;

public class OtherBillDetailAdv {


	private String id;
	private float billDetailItemMrp;

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getBillDetailItemMrp() {
		return billDetailItemMrp;
	}

	public void setBillDetailItemMrp(float billDetailItemMrp) {
		this.billDetailItemMrp = billDetailItemMrp;
	}

	@Override
	public String toString() {
		return "OtherBillDetailAdv [id=" + id + ", billDetailItemMrp=" + billDetailItemMrp + "]";
	}
	
}
