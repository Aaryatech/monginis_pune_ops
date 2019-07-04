package com.monginis.ops.dailysales;

import java.util.List;


public class DailySalesReportDao {
	
	List<DailySalesRegular> dailySalesRegularList;
	List<SpDailySales> spDailySalesList;
	 
	
	public List<DailySalesRegular> getDailySalesRegularList() {
		return dailySalesRegularList;
	}

	public void setDailySalesRegularList(List<DailySalesRegular> dailySalesRegularList) {
		this.dailySalesRegularList = dailySalesRegularList;
	}

	public List<SpDailySales> getSpDailySalesList() {
		return spDailySalesList;
	}

	public void setSpDailySalesList(List<SpDailySales> spDailySalesList) {
		this.spDailySalesList = spDailySalesList;
	}

	@Override
	public String toString() {
		return "DailySalesReportDao [dailySalesRegularList=" + dailySalesRegularList + ", spDailySalesList="
				+ spDailySalesList + "]";
	}

	
}
