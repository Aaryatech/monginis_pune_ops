
package com.monginis.ops.model;


public class FrLoginResponse {

    private LoginInfo loginInfo;
    private Franchisee franchisee;

    
    public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}


	public Franchisee getFranchisee() {
		return franchisee;
	}

	public void setFranchisee(Franchisee franchisee) {
		this.franchisee = franchisee;
	}

	@Override
	public String toString() {
		return "FrLoginResponse [loginInfo=" + loginInfo + ", franchisee=" + franchisee + "]";
	}

}
