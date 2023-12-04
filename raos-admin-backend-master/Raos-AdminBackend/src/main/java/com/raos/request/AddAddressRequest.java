package com.raos.request;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class AddAddressRequest {

	public int customer_id;
	public String address_type;
	public String door_no;
	public String street_name;
	public String city;
	public String pincode;
	public String state;
	public String country;
	public String latitude;
	public String longitude;
}
