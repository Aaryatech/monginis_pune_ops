package com.monginis.ops.model;

public class Flavour {
	private int spfId;
	private int spType;
	private String spfName;
	private double spfAdonRate;
	private int delStatus;
	public int getSpfId() {
		return spfId;
	}
	public void setSpfId(int spfId) {
		this.spfId = spfId;
	}
	public int getSpType() {
		return spType;
	}
	public void setSpType(int spType) {
		this.spType = spType;
	}
	public String getSpfName() {
		return spfName;
	}
	public void setSpfName(String spfName) {
		this.spfName = spfName;
	}

	public double getSpfAdonRate() {
		return spfAdonRate;
	}
	public void setSpfAdonRate(double spfAdonRate) {
		this.spfAdonRate = spfAdonRate;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	@Override
	public String toString() {
		return "Flavour [spfId=" + spfId + ", spType=" + spType + ", spfName=" + spfName + ", spfAdonRate="
				+ spfAdonRate + ", delStatus=" + delStatus + "]";
	}

}
