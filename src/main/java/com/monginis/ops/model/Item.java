package com.monginis.ops.model;

public class Item {

		private int id;
		private String itemId;
		private String itemName;
		private Integer itemGrp1;
		private Integer itemGrp2;
		private Integer itemGrp3;
		private Double itemRate1;
		private Double itemRate2;
		private Double itemMrp1;
		private Double itemMrp2;
		private Double itemMrp3;
		private String itemImage;
		private Double itemTax1;
		private Double itemTax2;
		private Double itemTax3;
		private Integer itemIsUsed;
		private Double itemSortId;
		private Integer grnTwo;
		private Integer delStatus;
		private Double itemRate3;
		private Integer minQty;
		private Integer shelfLife;
		
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getItemId() {
			return itemId;
		}
		public void setItemId(String itemId) {
			this.itemId = itemId;
		}
		public String getItemName() {
			return itemName;
		}
		public void setItemName(String itemName) {
			this.itemName = itemName;
		}
		public Integer getItemGrp1() {
			return itemGrp1;
		}
		public void setItemGrp1(Integer itemGrp1) {
			this.itemGrp1 = itemGrp1;
		}
		public Integer getItemGrp2() {
			return itemGrp2;
		}
		public void setItemGrp2(Integer itemGrp2) {
			this.itemGrp2 = itemGrp2;
		}
		public Integer getItemGrp3() {
			return itemGrp3;
		}
		public void setItemGrp3(Integer itemGrp3) {
			this.itemGrp3 = itemGrp3;
		}
		public Double getItemRate1() {
			return itemRate1;
		}
		public void setItemRate1(Double itemRate1) {
			this.itemRate1 = itemRate1;
		}
		public Double getItemRate2() {
			return itemRate2;
		}
		public void setItemRate2(Double itemRate2) {
			this.itemRate2 = itemRate2;
		}
		public Double getItemMrp1() {
			return itemMrp1;
		}
		public void setItemMrp1(Double itemMrp1) {
			this.itemMrp1 = itemMrp1;
		}
		public Double getItemMrp2() {
			return itemMrp2;
		}
		public void setItemMrp2(Double itemMrp2) {
			this.itemMrp2 = itemMrp2;
		}
		public Double getItemMrp3() {
			return itemMrp3;
		}
		public void setItemMrp3(Double itemMrp3) {
			this.itemMrp3 = itemMrp3;
		}
		public String getItemImage() {
			return itemImage;
		}
		public void setItemImage(String itemImage) {
			this.itemImage = itemImage;
		}
		public Double getItemTax1() {
			return itemTax1;
		}
		public void setItemTax1(Double itemTax1) {
			this.itemTax1 = itemTax1;
		}
		public Double getItemTax2() {
			return itemTax2;
		}
		public void setItemTax2(Double itemTax2) {
			this.itemTax2 = itemTax2;
		}
		public Double getItemTax3() {
			return itemTax3;
		}
		public void setItemTax3(Double itemTax3) {
			this.itemTax3 = itemTax3;
		}
		public Integer getItemIsUsed() {
			return itemIsUsed;
		}
		public void setItemIsUsed(Integer itemIsUsed) {
			this.itemIsUsed = itemIsUsed;
		}
		public Double getItemSortId() {
			return itemSortId;
		}
		public void setItemSortId(Double itemSortId) {
			this.itemSortId = itemSortId;
		}
		public Integer getGrnTwo() {
			return grnTwo;
		}
		public void setGrnTwo(Integer grnTwo) {
			this.grnTwo = grnTwo;
		}
		public Integer getDelStatus() {
			return delStatus;
		}
		public void setDelStatus(Integer delStatus) {
			this.delStatus = delStatus;
		}
		public Double getItemRate3() {
			return itemRate3;
		}
		public void setItemRate3(Double itemRate3) {
			this.itemRate3 = itemRate3;
		}
		public Integer getMinQty() {
			return minQty;
		}
		public void setMinQty(Integer minQty) {
			this.minQty = minQty;
		}
		public Integer getShelfLife() {
			return shelfLife;
		}
		public void setShelfLife(Integer shelfLife) {
			this.shelfLife = shelfLife;
		}
		@Override
		public String toString() {
			return "Item [id=" + id + ", itemId=" + itemId + ", itemName=" + itemName + ", itemGrp1=" + itemGrp1
					+ ", itemGrp2=" + itemGrp2 + ", itemGrp3=" + itemGrp3 + ", itemRate1=" + itemRate1 + ", itemRate2="
					+ itemRate2 + ", itemMrp1=" + itemMrp1 + ", itemMrp2=" + itemMrp2 + ", itemMrp3=" + itemMrp3
					+ ", itemImage=" + itemImage + ", itemTax1=" + itemTax1 + ", itemTax2=" + itemTax2 + ", itemTax3="
					+ itemTax3 + ", itemIsUsed=" + itemIsUsed + ", itemSortId=" + itemSortId + ", grnTwo=" + grnTwo
					+ ", delStatus=" + delStatus + ", itemRate3=" + itemRate3 + ", minQty=" + minQty + ", shelfLife="
					+ shelfLife + "]";
		}

		
}
