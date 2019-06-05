package com.monginis.ops.model;

public class CustList {

	private String userPhone;

	private String userName;

	private String userGstNo;

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGstNo() {
		return userGstNo;
	}

	public void setUserGstNo(String userGstNo) {
		this.userGstNo = userGstNo;
	}

	@Override
	public String toString() {
		return "CustList [userPhone=" + userPhone + ", userName=" + userName + ", userGstNo=" + userGstNo + "]";
	}

}
