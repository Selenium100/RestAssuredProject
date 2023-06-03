package com.qa.pojo;

import java.util.List;

import org.apache.juneau.annotation.Beanc;

public class Orders {
	
	private List<OrderDetails> orders;
	
	@Beanc(properties = "orders")
	public Orders(List<OrderDetails> orders) {
		this.orders = orders;
	}

	public List<OrderDetails> getOrders() {
		return orders;
	}

	public void setOrders(List<OrderDetails> orders) {
		this.orders = orders;
	}
	
	

}
