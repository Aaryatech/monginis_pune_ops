package com.monginis.ops.model.pettycash;

public class SpCakeAmtModel {

	private String id;
	private float remAmt;
	private float advAmt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public float getRemAmt() {
		return remAmt;
	}

	public void setRemAmt(float remAmt) {
		this.remAmt = remAmt;
	}

	public float getAdvAmt() {
		return advAmt;
	}

	public void setAdvAmt(float advAmt) {
		this.advAmt = advAmt;
	}

	@Override
	public String toString() {
		return "SpCakeAmtModel [id=" + id + ", remAmt=" + remAmt + ", advAmt=" + advAmt + "]";
	}

}
