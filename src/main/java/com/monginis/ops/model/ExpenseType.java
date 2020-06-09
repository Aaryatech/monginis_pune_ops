package com.monginis.ops.model;

public class ExpenseType {

	private int expTypeId;

	private String expTypeName;

	private int isActive;

	private int delStatus;

	public int getExpTypeId() {
		return expTypeId;
	}

	public void setExpTypeId(int expTypeId) {
		this.expTypeId = expTypeId;
	}

	public String getExpTypeName() {
		return expTypeName;
	}

	public void setExpTypeName(String expTypeName) {
		this.expTypeName = expTypeName;
	}

	public int getIsActive() {
		return isActive;
	}

	public void setIsActive(int isActive) {
		this.isActive = isActive;
	}

	public int getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}

	@Override
	public String toString() {
		return "ExpenseType [expTypeId=" + expTypeId + ", expTypeName=" + expTypeName + ", isActive=" + isActive
				+ ", delStatus=" + delStatus + "]";
	}
	
}
