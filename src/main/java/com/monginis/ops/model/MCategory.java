package com.monginis.ops.model;

import java.util.List;


public class MCategory {
	  private Integer catId;
	    private String catName;
	    private int isSameDay;
	    private int delStatus;
	    private List<SubCategory> subCategoryList;
		public Integer getCatId() {
			return catId;
		}
		public void setCatId(Integer catId) {
			this.catId = catId;
		}
		public String getCatName() {
			return catName;
		}
		public void setCatName(String catName) {
			this.catName = catName;
		}
		public int getIsSameDay() {
			return isSameDay;
		}
		public void setIsSameDay(int isSameDay) {
			this.isSameDay = isSameDay;
		}
		public int getDelStatus() {
			return delStatus;
		}
		public void setDelStatus(int delStatus) {
			this.delStatus = delStatus;
		}
		public List<SubCategory> getSubCategoryList() {
			return subCategoryList;
		}
		public void setSubCategoryList(List<SubCategory> subCategoryList) {
			this.subCategoryList = subCategoryList;
		}
		@Override
		public String toString() {
			return "MCategory [catId=" + catId + ", catName=" + catName + ", isSameDay=" + isSameDay + ", delStatus="
					+ delStatus + ", subCategoryList=" + subCategoryList + "]";
		}
		
	    
}
