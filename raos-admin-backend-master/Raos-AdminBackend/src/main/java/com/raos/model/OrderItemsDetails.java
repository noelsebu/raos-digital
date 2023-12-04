package com.raos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemsDetails {
	
	int product_id;
	int item_quantity;
	double item_total_price;
	String product_name;
	int order_item_id;
}
