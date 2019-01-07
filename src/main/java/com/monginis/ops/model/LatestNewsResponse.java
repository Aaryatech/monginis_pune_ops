
package com.monginis.ops.model;

import java.util.List;

public class LatestNewsResponse {

    private List<SchedulerList> schedulerList = null;
    private Info info;

    public List<SchedulerList> getSchedulerList() {
        return schedulerList;
    }

    public void setSchedulerList(List<SchedulerList> schedulerList) {
        this.schedulerList = schedulerList;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

}
