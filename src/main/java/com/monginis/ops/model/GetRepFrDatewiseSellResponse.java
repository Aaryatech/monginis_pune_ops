package com.monginis.ops.model;



public class GetRepFrDatewiseSellResponse {

	
private int sellBillNo;
	
 	private String billDate;
	
 	private int frId;
	
 	private String month;
 	private String day;
	private float cash;
	private float card;
	private float other;
	
 	private String frName;

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public int getSellBillNo() {
		return sellBillNo;
	}

	public void setSellBillNo(int sellBillNo) {
		this.sellBillNo = sellBillNo;
	}

	public String getBillDate() {
		return billDate;
	}

	public void setBillDate(String billDate) {
		this.billDate = billDate;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public float getCash() {
		return cash;
	}

	public void setCash(float cash) {
		this.cash = cash;
	}

	public float getCard() {
		return card;
	}

	public void setCard(float card) {
		this.card = card;
	}

	public float getOther() {
		return other;
	}

	public void setOther(float other) {
		this.other = other;
	}

	public String getFrName() {
		return frName;
	}

	public void setFrName(String frName) {
		this.frName = frName;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	@Override
	public String toString() {
		return "GetRepFrDatewiseSellResponse [sellBillNo=" + sellBillNo + ", billDate=" + billDate + ", frId=" + frId
				+ ", month=" + month + ", cash=" + cash + ", card=" + card + ", other=" + other + ", frName=" + frName
				+ "]";
	}

	 
 	
}
