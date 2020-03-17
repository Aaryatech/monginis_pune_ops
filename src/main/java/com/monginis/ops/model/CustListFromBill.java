package com.monginis.ops.model;

public class CustListFromBill {

	private String id;
	private String userName;
	private String userPhone;
	private String userGstNo;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getUserGstNo() {
		return userGstNo;
	}
	public void setUserGstNo(String userGstNo) {
		this.userGstNo = userGstNo;
	}
	@Override
	public String toString() {
		return "CustListFromBill [id=" + id + ", userName=" + userName + ", userPhone=" + userPhone + ", userGstNo="
				+ userGstNo + "]";
	}
	
}
