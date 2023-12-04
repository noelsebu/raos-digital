package com.raos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItems {
	
	int product_id;
	int item_quantity;
	double item_total_price;

}
