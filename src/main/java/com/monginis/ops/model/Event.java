package com.monginis.ops.model;

public class Event {
	private int speId;
	private String speName;
	private int delStatus;
	public int getSpeId() {
		return speId;
	}
	public void setSpeId(int speId) {
		this.speId = speId;
	}
	public String getSpeName() {
		return speName;
	}
	public void setSpeName(String speName) {
		this.speName = speName;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	@Override
	public String toString() {
		return "Event [speId=" + speId + ", speName=" + speName + ", delStatus=" + delStatus + "]";
	}
	
	
}
