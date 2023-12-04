package com.raos.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreDelivery {

	int id;
	int instant_delivery;
	List<DeliveryTimeSlot> slots;
	String day;
	int status;
}
