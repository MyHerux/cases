package com.heroxu.cases.kafka.stream.model;

import lombok.Data;

@Data
public class Item {

	public Item(String itemName, String address, String type, double price) {
		this.itemName = itemName;
		this.address = address;
		this.type = type;
		this.price = price;
	}

	private String itemName;
	private String address;
	private String type;
	private double price;

	@Override
	public String toString() {
		return "Item{" +
				"itemName='" + itemName + '\'' +
				", address='" + address + '\'' +
				", type='" + type + '\'' +
				", price=" + price +
				'}';
	}
}