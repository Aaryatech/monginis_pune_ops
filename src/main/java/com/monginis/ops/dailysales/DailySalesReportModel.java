package com.monginis.ops.dailysales;

public class DailySalesReportModel {

	private int dsrId;

	private int frId;
	private String dsrDate;
	private float openingStock;
	private float totalPurchase;
	private String purchaseBillIds;
	private float totalGrnGvn;
	private float closingStock;
	private float totalSale;
	private float cardSale;
	private float cashSale;
	private String expDetail;
	private float totalExp;
	private float physicalCash;
	private float netCash;
	private float depositInBank;
	private float excess;
	private int delStatus;
	private int exInt;
	private float exFloat;
	private String exVarchar;

	public DailySalesReportModel() {
		super();
	}

	public DailySalesReportModel(int dsrId, int frId, String dsrDate, float openingStock, float totalPurchase,
			String purchaseBillIds, float totalGrnGvn, float closingStock, float totalSale, float cardSale,
			float cashSale, String expDetail, float totalExp, float physicalCash, float netCash, float depositInBank,
			float excess, int delStatus, int exInt, float exFloat, String exVarchar) {
		super();
		this.dsrId = dsrId;
		this.frId = frId;
		this.dsrDate = dsrDate;
		this.openingStock = openingStock;
		this.totalPurchase = totalPurchase;
		this.purchaseBillIds = purchaseBillIds;
		this.totalGrnGvn = totalGrnGvn;
		this.closingStock = closingStock;
		this.totalSale = totalSale;
		this.cardSale = cardSale;
		this.cashSale = cashSale;
		this.expDetail = expDetail;
		this.totalExp = totalExp;
		this.physicalCash = physicalCash;
		this.netCash = netCash;
		this.depositInBank = depositInBank;
		this.excess = excess;
		this.delStatus = delStatus;
		this.exInt = exInt;
		this.exFloat = exFloat;
		this.exVarchar = exVarchar;
	}

	public int getDsrId() {
		return dsrId;
	}

	public void setDsrId(int dsrId) {
		this.dsrId = dsrId;
	}

	public int getFrId() {
		return frId;
	}

	public void setFrId(int frId) {
		this.frId = frId;
	}

	public String getDsrDate() {
		return dsrDate;
	}

	public void setDsrDate(String dsrDate) {
		this.dsrDate = dsrDate;
	}

	public float getOpeningStock() {
		return openingStock;
	}

	public void setOpeningStock(float openingStock) {
		this.openingStock = openingStock;
	}

	public float getTotalPurchase() {
		return totalPurchase;
	}

	public void setTotalPurchase(float totalPurchase) {
		this.totalPurchase = totalPurchase;
	}

	public String getPurchaseBillIds() {
		return purchaseBillIds;
	}

	public void setPurchaseBillIds(String purchaseBillIds) {
		this.purchaseBillIds = purchaseBillIds;
	}

	public float getTotalGrnGvn() {
		return totalGrnGvn;
	}

	public void setTotalGrnGvn(float totalGrnGvn) {
		this.totalGrnGvn = totalGrnGvn;
	}

	public float getClosingStock() {
		return closingStock;
	}

	public void setClosingStock(float closingStock) {
		this.closingStock = closingStock;
	}

	public float getTotalSale() {
		return totalSale;
	}

	public void setTotalSale(float totalSale) {
		this.totalSale = totalSale;
	}

	public float getCardSale() {
		return cardSale;
	}

	public void setCardSale(float cardSale) {
		this.cardSale = cardSale;
	}

	public float getCashSale() {
		return cashSale;
	}

	public void setCashSale(float cashSale) {
		this.cashSale = cashSale;
	}

	public String getExpDetail() {
		return expDetail;
	}

	public void setExpDetail(String expDetail) {
		this.expDetail = expDetail;
	}

	public float getTotalExp() {
		return totalExp;
	}

	public void setTotalExp(float totalExp) {
		this.totalExp = totalExp;
	}

	public float getPhysicalCash() {
		return physicalCash;
	}

	public void setPhysicalCash(float physicalCash) {
		this.physicalCash = physicalCash;
	}

	public float getNetCash() {
		return netCash;
	}

	public void setNetCash(float netCash) {
		this.netCash = netCash;
	}

	public float getDepositInBank() {
		return depositInBank;
	}

	public void setDepositInBank(float depositInBank) {
		this.depositInBank = depositInBank;
	}

	public float getExcess() {
		return excess;
	}

	public void setExcess(float excess) {
		this.excess = excess;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	public int getExInt() {
		return exInt;
	}

	public void setExInt(int exInt) {
		this.exInt = exInt;
	}

	public float getExFloat() {
		return exFloat;
	}

	public void setExFloat(float exFloat) {
		this.exFloat = exFloat;
	}

	public String getExVarchar() {
		return exVarchar;
	}

	public void setExVarchar(String exVarchar) {
		this.exVarchar = exVarchar;
	}

	@Override
	public String toString() {
		return "DailySalesReportModel [dsrId=" + dsrId + ", frId=" + frId + ", dsrDate=" + dsrDate + ", openingStock="
				+ openingStock + ", totalPurchase=" + totalPurchase + ", purchaseBillIds=" + purchaseBillIds
				+ ", totalGrnGvn=" + totalGrnGvn + ", closingStock=" + closingStock + ", totalSale=" + totalSale
				+ ", cardSale=" + cardSale + ", cashSale=" + cashSale + ", expDetail=" + expDetail + ", totalExp="
				+ totalExp + ", physicalCash=" + physicalCash + ", netCash=" + netCash + ", depositInBank="
				+ depositInBank + ", excess=" + excess + ", delStatus=" + delStatus + ", exInt=" + exInt + ", exFloat="
				+ exFloat + ", exVarchar=" + exVarchar + "]";
	}

}
