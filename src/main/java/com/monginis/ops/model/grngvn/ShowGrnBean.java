package com.monginis.ops.model.grngvn;

import java.util.Date;

public class ShowGrnBean {

	    private int billDetailNo;
	    private Integer itemId;
	    private Integer frId;
	    private String itemName;
	    private Integer grnType;
	    private Integer billNo;
	    private float rate;
	    private float mrp;
	    private Integer billQty;
	    Date billDate;
	    float sgstPer;
	    float cgstPer;
	    float igstPer;
	    float calcBaseRate;
	    private int menuId;
		private int catId;
		private String invoiceNo;
	    private int autoGrnQty;
    	private float taxPer;
	    private float grnAmt;
	    private String billDateTime;
        private float taxableAmt;
        private float grnRate;
        private String hsnCode;
        private float taxAmt;
        private float discPer;
       
        
        
	    public String getHsnCode() {
			return hsnCode;
		}

		public void setHsnCode(String hsnCode) {
			this.hsnCode = hsnCode;
		}

		public float getDiscPer() {
		return discPer;
	}

	public void setDiscPer(float discPer) {
		this.discPer = discPer;
	}

		public float getTaxableAmt() {
			return taxableAmt;
		}

		public void setTaxableAmt(float taxableAmt) {
			this.taxableAmt = taxableAmt;
		}

		public float getGrnRate() {
			return grnRate;
		}

		public void setGrnRate(float grnRate) {
			this.grnRate = grnRate;
		}

		public int getMenuId() {
			return menuId;
		}

		public void setMenuId(int menuId) {
			this.menuId = menuId;
		}

		public int getCatId() {
			return catId;
		}

		public void setCatId(int catId) {
			this.catId = catId;
		}

		public String getInvoiceNo() {
			return invoiceNo;
		}

		public void setInvoiceNo(String invoiceNo) {
			this.invoiceNo = invoiceNo;
		}

		public int getBillDetailNo() {
			return billDetailNo;
		}
		public void setBillDetailNo(int billDetailNo) {
			this.billDetailNo = billDetailNo;
		}
		public Integer getItemId() {
			return itemId;
		}
		public void setItemId(Integer itemId) {
			this.itemId = itemId;
		}
		public Integer getFrId() {
			return frId;
		}
		public void setFrId(Integer frId) {
			this.frId = frId;
		}
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public Integer getGrnType() {
			return grnType;
		}
		public void setGrnType(Integer grnType) {
			this.grnType = grnType;
		}
		public Integer getBillNo() {
			return billNo;
		}
		public void setBillNo(Integer billNo) {
			this.billNo = billNo;
		}
		
		public float getRate() {
			return rate;
		}

		public void setRate(float rate) {
			this.rate = rate;
		}

		public float getMrp() {
			return mrp;
		}

		public void setMrp(float mrp) {
			this.mrp = mrp;
		}

		public Integer getBillQty() {
			return billQty;
		}
		public void setBillQty(Integer billQty) {
			this.billQty = billQty;
		}
		
		
		
		public Date getBillDate() {
			return billDate;
		}

		public void setBillDate(java.util.Date date) {
			this.billDate = date;
		}

		public float getSgstPer() {
			return sgstPer;
		}
		public void setSgstPer(float sgstPer) {
			this.sgstPer = sgstPer;
		}
		public float getCgstPer() {
			return cgstPer;
		}
		public void setCgstPer(float cgstPer) {
			this.cgstPer = cgstPer;
		}
		public float getIgstPer() {
			return igstPer;
		}
		public void setIgstPer(float igstPer) {
			this.igstPer = igstPer;
		}
		public float getCalcBaseRate() {
			return calcBaseRate;
		}
		public void setCalcBaseRate(float calcBaseRate) {
			this.calcBaseRate = calcBaseRate;
		}

		public int getAutoGrnQty() {
			return autoGrnQty;
		}

		public void setAutoGrnQty(int autoGrnQty) {
			this.autoGrnQty = autoGrnQty;
		}

		public float getTaxPer() {
			return taxPer;
		}

		public float getGrnAmt() {
			return grnAmt;
		}

		public void setTaxPer(float taxPer) {
			this.taxPer = taxPer;
		}

		public void setGrnAmt(float grnAmt) {
			this.grnAmt = grnAmt;
		}

		public String getBillDateTime() {
			return billDateTime;
		}

		public void setBillDateTime(String billDateTime) {
			this.billDateTime = billDateTime;
		}

		public float getTaxAmt() {
			return taxAmt;
		}

		public void setTaxAmt(float taxAmt) {
			this.taxAmt = taxAmt;
		}

		@Override
		public String toString() {
			return "ShowGrnBean [billDetailNo=" + billDetailNo + ", itemId=" + itemId + ", frId=" + frId + ", itemName="
					+ itemName + ", grnType=" + grnType + ", billNo=" + billNo + ", rate=" + rate + ", mrp=" + mrp
					+ ", billQty=" + billQty + ", billDate=" + billDate + ", sgstPer=" + sgstPer + ", cgstPer="
					+ cgstPer + ", igstPer=" + igstPer + ", calcBaseRate=" + calcBaseRate + ", menuId=" + menuId
					+ ", catId=" + catId + ", invoiceNo=" + invoiceNo + ", autoGrnQty=" + autoGrnQty + ", taxPer="
					+ taxPer + ", grnAmt=" + grnAmt + ", billDateTime=" + billDateTime + ", taxableAmt=" + taxableAmt
					+ ", grnRate=" + grnRate + ", hsnCode=" + hsnCode + ", taxAmt=" + taxAmt + ", discPer=" + discPer
					+ "]";
		}
         
}
