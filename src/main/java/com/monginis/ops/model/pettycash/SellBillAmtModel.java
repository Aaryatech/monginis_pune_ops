package com.monginis.ops.model.pettycash;

public class SellBillAmtModel {

	private int paymentMode;
	private float amt;

	public int getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(int paymentMode) {
		this.paymentMode = paymentMode;
	}

	public float getAmt() {
		return amt;
	}

	public void setAmt(float amt) {
		this.amt = amt;
	}

	@Override
	public String toString() {
		return "SellBillAmtModel [paymentMode=" + paymentMode + ", amt=" + amt + "]";
	}
	
}
