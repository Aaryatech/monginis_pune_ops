package com.monginis.ops.model.pettycash;

public class PettyCashData {
 
	private int pettycashId;
	private String date;
	private float cashAmt;
	private float openingAmt;
	private float withdrawalAmt;
	private float closingAmt;
	private float opnEditAmt;
	private float cashEditAmt;

	
	public int getPettycashId() {
		return pettycashId;
	}
	public void setPettycashId(int pettycashId) {
		this.pettycashId = pettycashId;
	}
	public float getOpnEditAmt() {
		return opnEditAmt;
	}
	public void setOpnEditAmt(float opnEditAmt) {
		this.opnEditAmt = opnEditAmt;
	}
	public float getCashEditAmt() {
		return cashEditAmt;
	}
	public void setCashEditAmt(float cashEditAmt) {
		this.cashEditAmt = cashEditAmt;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	public float getCashAmt() {
		return cashAmt;
	}
	public void setCashAmt(float cashAmt) {
		this.cashAmt = cashAmt;
	}
	
	public float getOpeningAmt() {
		return openingAmt;
	}
	public void setOpeningAmt(float openingAmt) {
		this.openingAmt = openingAmt;
	}
	public float getWithdrawalAmt() {
		return withdrawalAmt;
	}
	public void setWithdrawalAmt(float withdrawalAmt) {
		this.withdrawalAmt = withdrawalAmt;
	}
	public float getClosingAmt() {
		return closingAmt;
	}
	public void setClosingAmt(float closingAmt) {
		this.closingAmt = closingAmt;
	}
	@Override
	public String toString() {
		return "PettyCashData [pettycashId=" + pettycashId + ", date=" + date + ", cashAmt=" + cashAmt + ", openingAmt="
				+ openingAmt + ", withdrawalAmt=" + withdrawalAmt + ", closingAmt=" + closingAmt + ", opnEditAmt="
				+ opnEditAmt + ", cashEditAmt=" + cashEditAmt + "]";
	}
		
	
}
