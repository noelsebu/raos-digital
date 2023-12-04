package com.raos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StocksUpload {

	private int product_id;
	private float balance_stock;
	private float sales_price;
	private float mrp_price;

	
}
