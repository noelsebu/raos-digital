package com.raos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreProducts {

	int product_id;
	String product_name;
	String category_1;
	String category_2;
	String category_3;
	int status;
	String discount_flag;
	String imgUrl;
}
