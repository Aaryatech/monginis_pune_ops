package com.monginis.ops.model;

import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SpCakeOrder {

	private int spOrderNo;
	private int frId;
	private String frCode;
	private Integer spType;
	private Integer spId;
	private String itemId;
	private int spFlavourId;
	private float spSelectedWeight;
	private float spBackendRate;
	private String spDeliveryPlace;
	private float spMinWeight;
	private float spMaxWeight;
	private int spProdTime;
	private Date spEstDeliDate;
	private Date spProdDate;
	private String spEvents;
	private String spEventsName;
	private String spInstructions;
	private Date spDeliveryDate;
	private String spCustName;
	private int menuId;
	private Date spCustDob;
	private String spCustMobNo;
	private String spBookedForName;
	private Date spBookForDob;
	private String spBookForMobNo;
	private float spGrandTotal;
	private float spPrice;
	private float spTotalAddRate;
	private float spSubTotal;
	private float spAdvance;
	private float rmAmount;
	private float tax1;
	private float tax2;
	private float tax1Amt;
	private float tax2Amt;
	private String orderPhoto;
	private String orderDate;
	private String orderPhoto2;
	private int isSlotUsed;
	private int isBillGenerated;
	private int isAllocated;

	private float extraCharges;

	private float disc;

	private String custGstin;//new

	private String custEmail;//new

	private int exInt1;

	private int exInt2;

	private String exVar1;

	private String exVar2;
	
	private String slipNo;
	

	public String getSlipNo() {
		return slipNo;
	}

	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	public String getCustGstin() {
		return custGstin;
	}

	public void setCustGstin(String custGstin) {
		this.custGstin = custGstin;
	}

	public String getCustEmail() {
		return custEmail;
	}

	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}

	public float getExtraCharges() {
		return extraCharges;
	}

	public void setExtraCharges(float extraCharges) {
		this.extraCharges = extraCharges;
	}

	public float getDisc() {
		return disc;
	}

	public void setDisc(float disc) {
		this.disc = disc;
	}

	public int getExInt1() {
		return exInt1;
	}

	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}

	public int getExInt2() {
		return exInt2;
	}

	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}

	public String getExVar1() {
		return exVar1;
	}

	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}

	public String getExVar2() {
		return exVar2;
	}

	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}

	public int getIsAllocated() {
		return isAllocated;
	}

	public void setIsAllocated(int isAllocated) {
		this.isAllocated = isAllocated;
	}

	public int getSpOrderNo() {
		return spOrderNo;
	}

	public void setSpOrderNo(int spOrderNo) {
		this.spOrderNo = spOrderNo;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public String getFrCode() {
		return frCode;
	}

	public void setFrCode(String frCode) {
		this.frCode = frCode;
	}

	public Integer getSpType() {
		return spType;
	}

	public void setSpType(Integer spType) {
		this.spType = spType;
	}

	public Integer getSpId() {
		return spId;
	}

	public void setSpId(Integer spId) {
		this.spId = spId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getSpFlavourId() {
		return spFlavourId;
	}

	public void setSpFlavourId(int spFlavourId) {
		this.spFlavourId = spFlavourId;
	}

	public float getSpSelectedWeight() {
		return spSelectedWeight;
	}

	public void setSpSelectedWeight(float spSelectedWeight) {
		this.spSelectedWeight = spSelectedWeight;
	}

	public float getSpBackendRate() {
		return spBackendRate;
	}

	public void setSpBackendRate(float spBackendRate) {
		this.spBackendRate = spBackendRate;
	}

	public String getSpDeliveryPlace() {
		return spDeliveryPlace;
	}

	public void setSpDeliveryPlace(String spDeliveryPlace) {
		this.spDeliveryPlace = spDeliveryPlace;
	}

	public float getSpMinWeight() {
		return spMinWeight;
	}

	public void setSpMinWeight(float spMinWeight) {
		this.spMinWeight = spMinWeight;
	}

	public float getSpMaxWeight() {
		return spMaxWeight;
	}

	public void setSpMaxWeight(float spMaxWeight) {
		this.spMaxWeight = spMaxWeight;
	}

	public int getSpProdTime() {
		return spProdTime;
	}

	public void setSpProdTime(int spProdTime) {
		this.spProdTime = spProdTime;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getSpEstDeliDate() {
		return spEstDeliDate;
	}

	public void setSpEstDeliDate(Date spEstDeliDate) {
		this.spEstDeliDate = spEstDeliDate;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getSpProdDate() {
		return spProdDate;
	}

	public void setSpProdDate(Date spProdDate) {
		this.spProdDate = spProdDate;
	}

	public String getSpEvents() {
		return spEvents;
	}

	public void setSpEvents(String spEvents) {
		this.spEvents = spEvents;
	}

	public String getSpEventsName() {
		return spEventsName;
	}

	public void setSpEventsName(String spEventsName) {
		this.spEventsName = spEventsName;
	}

	public String getSpInstructions() {
		return spInstructions;
	}

	public void setSpInstructions(String spInstructions) {
		this.spInstructions = spInstructions;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getSpDeliveryDate() {
		return spDeliveryDate;
	}

	public void setSpDeliveryDate(Date spDeliveryDate) {
		this.spDeliveryDate = spDeliveryDate;
	}

	public String getSpCustName() {
		return spCustName;
	}

	public void setSpCustName(String spCustName) {
		this.spCustName = spCustName;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getSpCustDob() {
		return spCustDob;
	}

	public void setSpCustDob(Date spCustDob) {
		this.spCustDob = spCustDob;
	}

	public String getSpCustMobNo() {
		return spCustMobNo;
	}

	public void setSpCustMobNo(String spCustMobNo) {
		this.spCustMobNo = spCustMobNo;
	}

	public String getSpBookedForName() {
		return spBookedForName;
	}

	public void setSpBookedForName(String spBookedForName) {
		this.spBookedForName = spBookedForName;
	}

	@JsonFormat(locale = "hi", timezone = "Asia/Kolkata", pattern = "dd-MM-yyyy")
	public Date getSpBookForDob() {
		return spBookForDob;
	}

	public void setSpBookForDob(Date spBookForDob) {
		this.spBookForDob = spBookForDob;
	}

	public String getSpBookForMobNo() {
		return spBookForMobNo;
	}

	public void setSpBookForMobNo(String spBookForMobNo) {
		this.spBookForMobNo = spBookForMobNo;
	}

	public float getSpGrandTotal() {
		return spGrandTotal;
	}

	public void setSpGrandTotal(float spGrandTotal) {
		this.spGrandTotal = spGrandTotal;
	}

	public float getSpPrice() {
		return spPrice;
	}

	public void setSpPrice(float spPrice) {
		this.spPrice = spPrice;
	}

	public float getSpTotalAddRate() {
		return spTotalAddRate;
	}

	public void setSpTotalAddRate(float spTotalAddRate) {
		this.spTotalAddRate = spTotalAddRate;
	}

	public float getSpSubTotal() {
		return spSubTotal;
	}

	public void setSpSubTotal(float spSubTotal) {
		this.spSubTotal = spSubTotal;
	}

	public float getSpAdvance() {
		return spAdvance;
	}

	public void setSpAdvance(float spAdvance) {
		this.spAdvance = spAdvance;
	}

	public float getRmAmount() {
		return rmAmount;
	}

	public void setRmAmount(float rmAmount) {
		this.rmAmount = rmAmount;
	}

	public float getTax1() {
		return tax1;
	}

	public void setTax1(float tax1) {
		this.tax1 = tax1;
	}

	public float getTax2() {
		return tax2;
	}

	public void setTax2(float tax2) {
		this.tax2 = tax2;
	}

	public float getTax1Amt() {
		return tax1Amt;
	}

	public void setTax1Amt(float tax1Amt) {
		this.tax1Amt = tax1Amt;
	}

	public float getTax2Amt() {
		return tax2Amt;
	}

	public void setTax2Amt(float tax2Amt) {
		this.tax2Amt = tax2Amt;
	}

	public String getOrderPhoto() {
		return orderPhoto;
	}

	public void setOrderPhoto(String orderPhoto) {
		this.orderPhoto = orderPhoto;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderPhoto2() {
		return orderPhoto2;
	}

	public void setOrderPhoto2(String orderPhoto2) {
		this.orderPhoto2 = orderPhoto2;
	}

	public int getIsSlotUsed() {
		return isSlotUsed;
	}

	public void setIsSlotUsed(int isSlotUsed) {
		this.isSlotUsed = isSlotUsed;
	}

	public int getIsBillGenerated() {
		return isBillGenerated;
	}

	public void setIsBillGenerated(int isBillGenerated) {
		this.isBillGenerated = isBillGenerated;
	}

	@Override
	public String toString() {
		return "SpCakeOrder [spOrderNo=" + spOrderNo + ", frId=" + frId + ", frCode=" + frCode + ", spType=" + spType
				+ ", spId=" + spId + ", itemId=" + itemId + ", spFlavourId=" + spFlavourId + ", spSelectedWeight="
				+ spSelectedWeight + ", spBackendRate=" + spBackendRate + ", spDeliveryPlace=" + spDeliveryPlace
				+ ", spMinWeight=" + spMinWeight + ", spMaxWeight=" + spMaxWeight + ", spProdTime=" + spProdTime
				+ ", spEstDeliDate=" + spEstDeliDate + ", spProdDate=" + spProdDate + ", spEvents=" + spEvents
				+ ", spEventsName=" + spEventsName + ", spInstructions=" + spInstructions + ", spDeliveryDate="
				+ spDeliveryDate + ", spCustName=" + spCustName + ", menuId=" + menuId + ", spCustDob=" + spCustDob
				+ ", spCustMobNo=" + spCustMobNo + ", spBookedForName=" + spBookedForName + ", spBookForDob="
				+ spBookForDob + ", spBookForMobNo=" + spBookForMobNo + ", spGrandTotal=" + spGrandTotal + ", spPrice="
				+ spPrice + ", spTotalAddRate=" + spTotalAddRate + ", spSubTotal=" + spSubTotal + ", spAdvance="
				+ spAdvance + ", rmAmount=" + rmAmount + ", tax1=" + tax1 + ", tax2=" + tax2 + ", tax1Amt=" + tax1Amt
				+ ", tax2Amt=" + tax2Amt + ", orderPhoto=" + orderPhoto + ", orderDate=" + orderDate + ", orderPhoto2="
				+ orderPhoto2 + ", isSlotUsed=" + isSlotUsed + ", isBillGenerated=" + isBillGenerated + ", isAllocated="
				+ isAllocated + ", extraCharges=" + extraCharges + ", disc=" + disc + ", custGstin=" + custGstin
				+ ", custEmail=" + custEmail + ", exInt1=" + exInt1 + ", exInt2=" + exInt2 + ", exVar1=" + exVar1
				+ ", exVar2=" + exVar2 + ", slipNo=" + slipNo + "]";
	}

	

}
