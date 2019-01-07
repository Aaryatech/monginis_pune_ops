package com.monginis.ops.model;

public class Type {
private int spType;
private String spTypeName;
public int getSpType() {
	return spType;
}
public void setSpType(int spType) {
	this.spType = spType;
}
public String getSpTypeName() {
	return spTypeName;
}
public void setSpTypeName(String spTypeName) {
	this.spTypeName = spTypeName;
}
@Override
public String toString() {
	return "Type [spType=" + spType + ", spTypeName=" + spTypeName + "]";
}

}
