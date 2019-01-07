package com.monginis.ops.model;

public class SpCakeOrderRes {
	SpCakeOrder spCakeOrder;
	ErrorMessage errorMessage;
	public SpCakeOrder getSpCakeOrder() {
		return spCakeOrder;
	}
	public void setSpCakeOrder(SpCakeOrder spCakeOrder) {
		this.spCakeOrder = spCakeOrder;
	}
	public ErrorMessage getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(ErrorMessage errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	
	@Override
	public String toString() {
		return "SpCakeOrderRes [spCakeOrder=" + spCakeOrder + ", errorMessage=" + errorMessage + "]";
	}
	
	
	
}
