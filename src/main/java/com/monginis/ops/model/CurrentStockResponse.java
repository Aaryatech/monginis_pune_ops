package com.monginis.ops.model;

import java.util.List;

public class CurrentStockResponse {

	private boolean isMonthClosed;
	private  List<GetCurrentStockDetails> currentStockDetailList;
	
	public boolean isMonthClosed() {
		return isMonthClosed;
	}
	public void setMonthClosed(boolean isMonthClosed) {
		this.isMonthClosed = isMonthClosed;
	}
	public List<GetCurrentStockDetails> getCurrentStockDetailList() {
		return currentStockDetailList;
	}
	public void setCurrentStockDetailList(List<GetCurrentStockDetails> currentStockDetailList) {
		this.currentStockDetailList = currentStockDetailList;
	}
	@Override
	public String toString() {
		return "CurrentStockResponse [isMonthClosed=" + isMonthClosed + ", currentStockDetailList="
				+ currentStockDetailList + "]";
	}
	
	
	
	
	
}
