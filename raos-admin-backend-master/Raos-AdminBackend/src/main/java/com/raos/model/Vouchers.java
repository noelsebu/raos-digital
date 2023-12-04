package com.raos.model;


import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Vouchers {

	int voucherId;
	String voucherName;
	String voucherCode;
	int status;
	String image_url;
}
