package com.monginis.ops.model;

public class Tabs {

	private String subCatName;
	private String subCatId;
	public String getSubCatName() {
		return subCatName;
	}
	public void setSubCatName(String subCatName) {
		this.subCatName = subCatName;
	}
	public String getSubCatId() {
		return subCatId;
	}
	public void setSubCatId(String subCatId) {
		this.subCatId = subCatId;
	}
	@Override
	public String toString() {
		return "Tabs [subCatName=" + subCatName + ", subCatId=" + subCatId + "]";
	}

	
}
