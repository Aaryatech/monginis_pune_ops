package com.monginis.ops.model;

import java.util.List;

public class FrItemList {

    private List<GetFrItem> frItemList = null;

	public List<GetFrItem> getFrItemList() {
		return frItemList;
	}

	public void setFrItemList(List<GetFrItem> frItemList) {
		this.frItemList = frItemList;
	}

	@Override
	public String toString() {
		return "FrItemList [frItemList=" + frItemList + "]";
	}

	
	
	
}
