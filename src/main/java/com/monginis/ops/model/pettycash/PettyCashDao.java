package com.monginis.ops.model.pettycash;

import java.util.List;

public class PettyCashDao {

	 List<SpCakeAdv> spCakAdv;
	
	 SellBillDetailAdv sellBillAdv;
	
	 OtherBillDetailAdv otherBillAdv;

	

	public List<SpCakeAdv> getSpCakAdv() {
		return spCakAdv;
	}

	public void setSpCakAdv(List<SpCakeAdv> spCakAdv) {
		this.spCakAdv = spCakAdv;
	}

	public SellBillDetailAdv getSellBillAdv() {
		return sellBillAdv;
	}

	public void setSellBillAdv(SellBillDetailAdv sellBillAdv) {
		this.sellBillAdv = sellBillAdv;
	}

	public OtherBillDetailAdv getOtherBillAdv() {
		return otherBillAdv;
	}

	public void setOtherBillAdv(OtherBillDetailAdv otherBillAdv) {
		this.otherBillAdv = otherBillAdv;
	}

	@Override
	public String toString() {
		return "PettyCashDao [spCakAdv=" + spCakAdv + ", sellBillAdv=" + sellBillAdv + ", otherBillAdv=" + otherBillAdv
				+ "]";
	}

	
	 
	
	
}
