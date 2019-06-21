package com.monginis.ops.model;

import java.util.List;

import com.monginis.ops.billing.SellBillHeader;

public class SellBillEditBean {

	SellBillHeader sellBillHeader;
	
	List<SellBillDetailEdit> sellBillDetailList;

	public SellBillHeader getSellBillHeader() {
		return sellBillHeader;
	}

	public void setSellBillHeader(SellBillHeader sellBillHeader) {
		this.sellBillHeader = sellBillHeader;
	}

	public List<SellBillDetailEdit> getSellBillDetailList() {
		return sellBillDetailList;
	}

	public void setSellBillDetailList(List<SellBillDetailEdit> sellBillDetailList) {
		this.sellBillDetailList = sellBillDetailList;
	}

	@Override
	public String toString() {
		return "SellBillEditBean [sellBillHeader=" + sellBillHeader + ", sellBillDetailList=" + sellBillDetailList
				+ "]";
	}
	
	
}
