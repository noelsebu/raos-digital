package com.raos.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.raos.dao.OrderDao;
import com.raos.logger.CommonLogger;
import com.raos.model.OrderDetails;
import com.raos.request.OrderFilter;

@Component
public class OrderServiceImpl implements OrderService {

	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	OrderDao orderDao;


	@Override
	public boolean updateOrderStatus(int orderId, int orderStatus) {
		return orderDao.updateOrderStatus(orderId,orderStatus);
	}

	@Override
	public List<OrderDetails> getOrderDetails(OrderFilter orderFilter) {
		return orderDao.getOrderDetails(orderFilter);
	}
		



	

}
