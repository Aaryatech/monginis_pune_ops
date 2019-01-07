package com.monginis.ops.model;

import java.util.List;

public class SpHistoryExBill {

	SpOrderHis[] spCakeOrder=null;
	
	GetRegSpCakeOrders[] regularSpCkOrders=null;

	public SpOrderHis[] getSpCakeOrder() {
		return spCakeOrder;
	}

	public void setSpCakeOrder(SpOrderHis[] spCakeOrder) {
		this.spCakeOrder = spCakeOrder;
	}

	public GetRegSpCakeOrders[] getRegularSpCkOrders() {
		return regularSpCkOrders;
	}

	public void setRegularSpCkOrders(GetRegSpCakeOrders[] regularSpCkOrders) {
		this.regularSpCkOrders = regularSpCkOrders;
	}

	
}
