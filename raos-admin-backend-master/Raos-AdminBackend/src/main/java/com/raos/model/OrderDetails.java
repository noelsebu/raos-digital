package com.raos.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDetails {

	int order_id;
	int customer_id;
	int order_status;
	double order_total;
	double order_grand_total;
	int voucher_id;
	List<OrderItemsDetails> order_item_details;
	OrderDelivery orderDelivery;
	String order_date;
}
