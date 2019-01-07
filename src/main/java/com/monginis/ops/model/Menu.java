package com.monginis.ops.model;

public class Menu {

	public String imageFirst;
	public String imageSecond;
	public String name;
	public String timing;
	public boolean isItem;
	
	
	
	public String getImageFirst() {
		return imageFirst;
	}
	public void setImageFirst(String imageFirst) {
		this.imageFirst = imageFirst;
	}
	public String getImageSecond() {
		return imageSecond;
	}
	public void setImageSecond(String imageSecond) {
		this.imageSecond = imageSecond;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTiming() {
		return timing;
	}
	public void setTiming(String timing) {
		this.timing = timing;
	}
		
	
	
	
	
	public boolean isItem() {
		return isItem;
	}
	public void setItem(boolean isItem) {
		this.isItem = isItem;
	}
	@Override
	public String toString() {
		return "Menu [imageFirst=" + imageFirst + ", imageSecond=" + imageSecond + ", name=" + name + ", timing="
				+ timing + ", isItems=" + isItem + "]";
	}
	
	

}
