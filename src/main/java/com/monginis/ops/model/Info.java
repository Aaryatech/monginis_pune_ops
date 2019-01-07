package com.monginis.ops.model;

public class Info {
private boolean error;
private String message;
public boolean isError() {
	return error;
}
public void setError(boolean error) {
	this.error = error;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}
@Override
public String toString() {
	return "Info [error=" + error + ", message=" + message + "]";
}

}
