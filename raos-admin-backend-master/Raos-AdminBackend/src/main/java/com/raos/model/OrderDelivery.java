package com.raos.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDelivery {

	public int address_type;
	public String name;
	public String lat;
	public String lng;
	public String pincode;
}
