package com.raos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDetails {

	int productId;
	String productName;
	String discountFlag;
	int balanceStock;
	int mrpPrice;
	int salesPrice;
}
