package com.monginis.ops.model;


public class SpOrderHis {

	private int spOrderNo;

	private String frCode;
	
	private int spType;
	
	private int spId;
	
	private int frId; 

	private String itemId;
	
	private int menuId;
	
	private int spFlavourId;
	
	private float spSelectedWeight;
	
	private String spDeliveryPlace;
	
	private String spDeliveryDt;
	
	private float spMinWeight;
	
	private float spMaxWeight;
	
	private int spProdTime;
	
	private String spEstDeliDate;
	
	private String spProdDate;
	
	private String spEvents;
	
	private String spEventsName;
	
	private String spInstructions;
	
	private String spDeliveryDate;
	
	private String spCustName;
	
	private String spCustDob;
	
	private String spCustMobNo;
	
	private String custGstNo;
	
	private String spBookedForName;
	
	private String spBookForDob;
	
	private String spBookForMobNo;
	
	private float spGrandTotal;
	
	private float spPrice;
	
	private float spTotalAddRate;
	
	private float spBackendRate;
	
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

    private String spfName;

    private String spName;

    private float spAddRate;
    
    private float spGrand;
    
    private int isBillGenerated;

    
    
	public String getCustGstNo() {
		return custGstNo;
	}

	public void setCustGstNo(String custGstNo) {
		this.custGstNo = custGstNo;
	}

	public int getIsBillGenerated() {
		return isBillGenerated;
	}

	public void setIsBillGenerated(int isBillGenerated) {
		this.isBillGenerated = isBillGenerated;
	}

	public float getSpGrand() {
		return spGrand;
	}

	public void setSpGrand(float spGrand) {
		this.spGrand = spGrand;
	}

	public float getSpAddRate() {
		return spAddRate;
	}

	public void setSpAddRate(float spAddRate) {
		this.spAddRate = spAddRate;
	}

	public String getSpDeliveryDt() {
		return spDeliveryDt;
	}

	public void setSpDeliveryDt(String spDeliveryDt) {
		this.spDeliveryDt = spDeliveryDt;
	}

	public int getSpOrderNo() {
		return spOrderNo;
	}

	public void setSpOrderNo(int spOrderNo) {
		this.spOrderNo = spOrderNo;
	}

	public String getFrCode() {
		return frCode;
	}

	public void setFrCode(String frCode) {
		this.frCode = frCode;
	}

	public int getSpType() {
		return spType;
	}

	public void setSpType(int spType) {
		this.spType = spType;
	}

	public int getSpId() {
		return spId;
	}

	public void setSpId(int spId) {
		this.spId = spId;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public int getMenuId() {
		return menuId;
	}

	public void setMenuId(int menuId) {
		this.menuId = menuId;
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

	public String getSpEstDeliDate() {
		return spEstDeliDate;
	}

	public void setSpEstDeliDate(String spEstDeliDate) {
		this.spEstDeliDate = spEstDeliDate;
	}

	public String getSpProdDate() {
		return spProdDate;
	}

	public void setSpProdDate(String spProdDate) {
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

	public String getSpDeliveryDate() {
		return spDeliveryDate;
	}

	public void setSpDeliveryDate(String spDeliveryDate) {
		this.spDeliveryDate = spDeliveryDate;
	}

	public String getSpCustName() {
		return spCustName;
	}

	public void setSpCustName(String spCustName) {
		this.spCustName = spCustName;
	}

	public String getSpCustDob() {
		return spCustDob;
	}

	public void setSpCustDob(String spCustDob) {
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

	public String getSpBookForDob() {
		return spBookForDob;
	}

	public void setSpBookForDob(String spBookForDob) {
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

	public float getSpBackendRate() {
		return spBackendRate;
	}

	public void setSpBackendRate(float spBackendRate) {
		this.spBackendRate = spBackendRate;
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

	public String getSpfName() {
		return spfName;
	}

	public void setSpfName(String spfName) {
		this.spfName = spfName;
	}

	public String getSpName() {
		return spName;
	}

	public void setSpName(String spName) {
		this.spName = spName;
	}

	@Override
	public String toString() {
		return "SpOrderHis [spOrderNo=" + spOrderNo + ", frCode=" + frCode + ", spType=" + spType + ", spId=" + spId
				+ ", frId=" + frId + ", itemId=" + itemId + ", menuId=" + menuId + ", spFlavourId=" + spFlavourId
				+ ", spSelectedWeight=" + spSelectedWeight + ", spDeliveryPlace=" + spDeliveryPlace + ", spDeliveryDt="
				+ spDeliveryDt + ", spMinWeight=" + spMinWeight + ", spMaxWeight=" + spMaxWeight + ", spProdTime="
				+ spProdTime + ", spEstDeliDate=" + spEstDeliDate + ", spProdDate=" + spProdDate + ", spEvents="
				+ spEvents + ", spEventsName=" + spEventsName + ", spInstructions=" + spInstructions
				+ ", spDeliveryDate=" + spDeliveryDate + ", spCustName=" + spCustName + ", spCustDob=" + spCustDob
				+ ", spCustMobNo=" + spCustMobNo + ", custGstNo=" + custGstNo + ", spBookedForName=" + spBookedForName
				+ ", spBookForDob=" + spBookForDob + ", spBookForMobNo=" + spBookForMobNo + ", spGrandTotal="
				+ spGrandTotal + ", spPrice=" + spPrice + ", spTotalAddRate=" + spTotalAddRate + ", spBackendRate="
				+ spBackendRate + ", spSubTotal=" + spSubTotal + ", spAdvance=" + spAdvance + ", rmAmount=" + rmAmount
				+ ", tax1=" + tax1 + ", tax2=" + tax2 + ", tax1Amt=" + tax1Amt + ", tax2Amt=" + tax2Amt
				+ ", orderPhoto=" + orderPhoto + ", orderDate=" + orderDate + ", orderPhoto2=" + orderPhoto2
				+ ", isSlotUsed=" + isSlotUsed + ", spfName=" + spfName + ", spName=" + spName + ", spAddRate="
				+ spAddRate + ", spGrand=" + spGrand + ", isBillGenerated=" + isBillGenerated + "]";
	}
    
}
