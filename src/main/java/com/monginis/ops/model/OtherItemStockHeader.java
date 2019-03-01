package com.monginis.ops.model;

import java.util.List;

public class OtherItemStockHeader {

	private int otherStockHeaderId;
	private int frId;
	private int Month;
	private int Year;
	private int delStatus;
	private int status;
	private int exInt1;
	private int exInt2;
	private String exVar1;
	private String exVar2;
	private float exFloat1;
	private float exFloat2;
	
	List<OtherItemStockDetail> otherItemStockList;	
	
	public List<OtherItemStockDetail> getOtherItemStockList() {
		return otherItemStockList;
	}
	public void setOtherItemStockList(List<OtherItemStockDetail> otherItemStockList) {
		this.otherItemStockList = otherItemStockList;
	}
	public int getOtherStockHeaderId() {
		return otherStockHeaderId;
	}
	public void setOtherStockHeaderId(int otherStockHeaderId) {
		this.otherStockHeaderId = otherStockHeaderId;
	}
	public int getFrId() {
		return frId;
	}
	public void setFrId(int frId) {
		this.frId = frId;
	}
	public int getMonth() {
		return Month;
	}
	public void setMonth(int month) {
		Month = month;
	}
	public int getYear() {
		return Year;
	}
	public void setYear(int year) {
		Year = year;
	}
	public int getDelStatus() {
		return delStatus;
	}
	public void setDelStatus(int delStatus) {
		this.delStatus = delStatus;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getExInt1() {
		return exInt1;
	}
	public void setExInt1(int exInt1) {
		this.exInt1 = exInt1;
	}
	public int getExInt2() {
		return exInt2;
	}
	public void setExInt2(int exInt2) {
		this.exInt2 = exInt2;
	}
	public String getExVar1() {
		return exVar1;
	}
	public void setExVar1(String exVar1) {
		this.exVar1 = exVar1;
	}
	public String getExVar2() {
		return exVar2;
	}
	public void setExVar2(String exVar2) {
		this.exVar2 = exVar2;
	}
	public float getExFloat1() {
		return exFloat1;
	}
	public void setExFloat1(float exFloat1) {
		this.exFloat1 = exFloat1;
	}
	public float getExFloat2() {
		return exFloat2;
	}
	public void setExFloat2(float exFloat2) {
		this.exFloat2 = exFloat2;
	}
	@Override
	public String toString() {
		return "OtherItemStockHeader [otherStockHeaderId=" + otherStockHeaderId + ", frId=" + frId + ", Month=" + Month
				+ ", Year=" + Year + ", delStatus=" + delStatus + ", status=" + status + ", exInt1=" + exInt1
				+ ", exInt2=" + exInt2 + ", exVar1=" + exVar1 + ", exVar2=" + exVar2 + ", exFloat1=" + exFloat1
				+ ", exFloat2=" + exFloat2 + ", otherItemStockList=" + otherItemStockList + "]";
	}	
	
	
	
	
}
