package com.raos.model;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
public class CommonResponse {

	public String status;
	public String message;
}
