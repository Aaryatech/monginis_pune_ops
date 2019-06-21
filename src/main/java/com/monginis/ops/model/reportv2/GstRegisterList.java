package com.monginis.ops.model.reportv2;

import java.util.List;

public class GstRegisterList {
	
	
	List<GstRegisterItem> gstRegItemList;
	
	List<GstRegisterSp> gstRegSpList;

	public List<GstRegisterItem> getGstRegItemList() {
		return gstRegItemList;
	}

	public void setGstRegItemList(List<GstRegisterItem> gstRegItemList) {
		this.gstRegItemList = gstRegItemList;
	}

	public List<GstRegisterSp> getGstRegSpList() {
		return gstRegSpList;
	}

	public void setGstRegSpList(List<GstRegisterSp> gstRegSpList) {
		this.gstRegSpList = gstRegSpList;
	}

	@Override
	public String toString() {
		return "GstRegisterList [gstRegItemList=" + gstRegItemList + ", gstRegSpList=" + gstRegSpList + "]";
	}

}
