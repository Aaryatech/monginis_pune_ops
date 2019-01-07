package com.monginis.ops.model;

import java.io.Serializable;

public class DateResponse implements Serializable{

	private String deliveryToDate;
	
	private String deliveryFromDate;

	private String fromTime;
	
	private String toTime;
	
	private String currTime;
	
	
	
	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getCurrTime() {
		return currTime;
	}

	public void setCurrTime(String currTime) {
		this.currTime = currTime;
	}

	public String getDeliveryToDate() {
		return deliveryToDate;
	}

	public void setDeliveryToDate(String deliveryToDate) {
		this.deliveryToDate = deliveryToDate;
	}

	public String getDeliveryFromDate() {
		return deliveryFromDate;
	}

	public void setDeliveryFromDate(String deliveryFromDate) {
		this.deliveryFromDate = deliveryFromDate;
	}

	@Override
	public String toString() {
		return "DateResponse [deliveryToDate=" + deliveryToDate + ", deliveryFromDate=" + deliveryFromDate + "]";
	}

    
}
