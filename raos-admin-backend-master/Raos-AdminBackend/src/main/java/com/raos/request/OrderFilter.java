package com.raos.request;



import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderFilter {

	int order_id;
	int customer_id;
	int status;
	String from;
	String to;
	
}
