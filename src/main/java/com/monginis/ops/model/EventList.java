package com.monginis.ops.model;

import java.util.List;

public class EventList {
	private List<Event> event = null;
	private Info info=null;
	public List<Event> getEvent() {
		return event;
	}
	public void setEvent(List<Event> event) {
		this.event = event;
	}
	public Info getInfo() {
		return info;
	}
	public void setInfo(Info info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return "EventList [event=" + event + ", info=" + info + "]";
	}
	
}
