
package com.monginis.ops.model;

import java.io.Serializable;

public class SchedulerList implements Serializable{

    private Integer schId;
    private String schDate;
    private String schTodate;
    private String schOccasionname;
    private String schMessage;
    private Integer schFrdttime;
    private Integer schTodttime;
    private Integer isActive;
    private Integer delStatus;

    public Integer getSchId() {
        return schId;
    }

    public void setSchId(Integer schId) {
        this.schId = schId;
    }

    public String getSchDate() {
        return schDate;
    }

    public void setSchDate(String schDate) {
        this.schDate = schDate;
    }

    public String getSchTodate() {
        return schTodate;
    }

    public void setSchTodate(String schTodate) {
        this.schTodate = schTodate;
    }

    public String getSchOccasionname() {
        return schOccasionname;
    }

    public void setSchOccasionname(String schOccasionname) {
        this.schOccasionname = schOccasionname;
    }

    public String getSchMessage() {
        return schMessage;
    }

    public void setSchMessage(String schMessage) {
        this.schMessage = schMessage;
    }

    public Integer getSchFrdttime() {
        return schFrdttime;
    }

    public void setSchFrdttime(Integer schFrdttime) {
        this.schFrdttime = schFrdttime;
    }

    public Integer getSchTodttime() {
        return schTodttime;
    }

    public void setSchTodttime(Integer schTodttime) {
        this.schTodttime = schTodttime;
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
		return "SchedulerList [schId=" + schId + ", schDate=" + schDate + ", schTodate=" + schTodate
				+ ", schOccasionname=" + schOccasionname + ", schMessage=" + schMessage + ", schFrdttime=" + schFrdttime
				+ ", schTodttime=" + schTodttime + ", isActive=" + isActive + ", delStatus=" + delStatus + "]";
	}
    

}
