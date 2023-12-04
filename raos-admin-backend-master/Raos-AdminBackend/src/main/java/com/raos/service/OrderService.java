package com.raos.service;

import java.util.List;
import org.springframework.stereotype.Service;

import com.raos.model.OrderDetails;
import com.raos.request.OrderFilter;

@Service
public interface OrderService {
	
	public boolean updateOrderStatus(int orderId, int orderStatus);

	public List<OrderDetails> getOrderDetails(OrderFilter orderFilter);

}
