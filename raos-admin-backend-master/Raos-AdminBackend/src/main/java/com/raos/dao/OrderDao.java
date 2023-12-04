package com.raos.dao;

import java.util.List;

import org.springframework.stereotype.Component;
import com.raos.model.OrderDetails;
import com.raos.request.OrderFilter;

@Component
public interface OrderDao {
	

	public boolean updateOrderStatus(int orderId, int orderStatus);

	public List<OrderDetails> getOrderDetails(OrderFilter orderFilter);
	
	}
