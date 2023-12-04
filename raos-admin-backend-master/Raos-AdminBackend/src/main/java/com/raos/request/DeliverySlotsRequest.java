package com.raos.request;

import java.util.List;

import com.raos.model.StoreDelivery;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeliverySlotsRequest {

	List<StoreDelivery> deliveryDetails;
}
