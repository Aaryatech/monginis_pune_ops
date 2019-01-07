
package com.monginis.ops.model;

import java.io.Serializable;

public class Message implements Serializable{

    private Integer msgId;
    private String msgFrdt;
    private String msgTodt;
    private String msgImage;
    private String msgHeader;
    private String msgDetails;
    private Integer isActive;
    private Integer delStatus;

    public Integer getMsgId() {
        return msgId;
    }

    public void setMsgId(Integer msgId) {
        this.msgId = msgId;
    }

    public String getMsgFrdt() {
        return msgFrdt;
    }

    public void setMsgFrdt(String msgFrdt) {
        this.msgFrdt = msgFrdt;
    }

    public String getMsgTodt() {
        return msgTodt;
    }

    public void setMsgTodt(String msgTodt) {
        this.msgTodt = msgTodt;
    }

    public String getMsgImage() {
        return msgImage;
    }

    public void setMsgImage(String msgImage) {
        this.msgImage = msgImage;
    }

    public String getMsgHeader() {
        return msgHeader;
    }

    public void setMsgHeader(String msgHeader) {
        this.msgHeader = msgHeader;
    }

    public String getMsgDetails() {
        return msgDetails;
    }

    public void setMsgDetails(String msgDetails) {
        this.msgDetails = msgDetails;
    }

    public Integer getIsActive() {
        return isActive;
    }

    public void setIsActive(Integer isActive) {
        this.isActive = isActive;
    }

    public Integer getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(Integer delStatus) {
        this.delStatus = delStatus;
    }

	@Override
	public String toString() {
		return "Message [msgId=" + msgId + ", msgFrdt=" + msgFrdt + ", msgTodt=" + msgTodt + ", msgImage=" + msgImage
				+ ", msgHeader=" + msgHeader + ", msgDetails=" + msgDetails + ", isActive=" + isActive + ", delStatus="
				+ delStatus + "]";
	}

    
    
}
