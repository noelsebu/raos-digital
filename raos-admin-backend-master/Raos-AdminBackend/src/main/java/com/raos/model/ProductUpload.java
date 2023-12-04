package com.raos.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductUpload {
	
	private int product_id;
	private String product_name;
    private int category_1;
    private int category_2;
    private int category_3;
	private String discount_flag;
	private String image_url;
	private int unit_metrics;
	private Date created;
	private Date updated;

}
