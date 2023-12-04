package com.raos.business;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


import com.raos.logger.CommonLogger;
import com.raos.logger.ConfigProperties;
import com.raos.model.CommonDataResponse;
import com.raos.model.CommonResponse;
import com.raos.model.CommonSingleResponse;
import com.raos.model.OrderDetails;
import com.raos.request.OrderFilter;
import com.raos.service.OrderService;

@Component
public class OrderBusiness {

	@Autowired
	CommonLogger LOGGER;

	@Autowired
	ConfigProperties configProp;

	@Autowired
	CommonResponse commonResponse;

	@Autowired
	OrderService orderService;

	@Autowired
	CommonDataResponse commonDataResponse;

	@Autowired
	CommonSingleResponse commonSingleResponse;


	public Object updateOrderStatus(int orderId,int orderStatus) {
		if (orderService.updateOrderStatus(orderId,orderStatus)) {
			LOGGER.info(this.getClass(), "ORDER UPDATED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Order Updated Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "UNABLE TO UPDATE ORDER");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("Unable to Update Order");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}

	public Object getOrderDetails(OrderFilter orderFilter) {
		List<OrderDetails> oList =  orderService.getOrderDetails(orderFilter);
		if (oList.size() > 0) {
			LOGGER.info(this.getClass(), "ORDERS LISTED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Orders Listed Successfully");
			commonSingleResponse.setData(oList);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "NO ORDERS FOUND");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("No Orders found");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}

	
}
