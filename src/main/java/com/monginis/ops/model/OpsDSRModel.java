package com.monginis.ops.model;

public class OpsDSRModel {

	private float openingAmt;
	private float purchase;
	private float grnGvn;
	private float sale;

	public float getOpeningAmt() {
		return openingAmt;
	}

	public void setOpeningAmt(float openingAmt) {
		this.openingAmt = openingAmt;
	}

	public float getPurchase() {
		return purchase;
	}

	public void setPurchase(float purchase) {
		this.purchase = purchase;
	}

	public float getGrnGvn() {
		return grnGvn;
	}

	public void setGrnGvn(float grnGvn) {
		this.grnGvn = grnGvn;
	}

	public float getSale() {
		return sale;
	}

	public void setSale(float sale) {
		this.sale = sale;
	}

	@Override
	public String toString() {
		return "OpsDSRModel [openingAmt=" + openingAmt + ", purchase=" + purchase + ", grnGvn=" + grnGvn + ", sale="
				+ sale + "]";
	}
}
