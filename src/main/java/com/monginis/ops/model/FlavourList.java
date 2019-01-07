package com.monginis.ops.model;

import java.util.List;

public class FlavourList {
	private List<Flavour> flavour = null;
	private Info info;

	public List<Flavour> getFlavour() {
	return flavour;
	}

	public void setFlavour(List<Flavour> flavour) {
	this.flavour = flavour;
	}

	public Info getInfo() {
	return info;
	}

	public void setInfo(Info info) {
	this.info = info;
	}

	@Override
	public String toString() {
		return "FlavourList [flavour=" + flavour + ", info=" + info + "]";
	}
}
