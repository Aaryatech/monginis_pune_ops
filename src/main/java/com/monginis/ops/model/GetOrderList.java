package com.monginis.ops.model;

import java.util.List;

public class GetOrderList {
	 List<GetOrder> getOrder;
	Info info;
	public List<GetOrder> getGetOrder() {
		return getOrder;
	}
	public void setGetOrder(List<GetOrder> getOrder) {
		this.getOrder = getOrder;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return "GetOrderList [getOder=" + getOrder + ", info=" + info + "]";
	}
	
	

}
