package com.raos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.raos.constants.OrderQueryConstants;
import com.raos.logger.CommonLogger;
import com.raos.model.OrderDelivery;
import com.raos.model.OrderDetails;
import com.raos.model.OrderItemsDetails;
import com.raos.request.OrderFilter;


@Component
public class OrderDaoImpl implements OrderDao {
	
	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	
	

	
	@Override
	public boolean updateOrderStatus(int order_id,int order_status) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(OrderQueryConstants.UPDATE_ORDER_STATUS);
			preStmt.setInt(1, order_status);
			preStmt.setInt(2, order_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE updateOrderStatus " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB updateOrderStatus " + e.getMessage());
			}

		}
		return updStatus;
	}

	@Override
	public List<OrderDetails> getOrderDetails(OrderFilter orderFilter) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		PreparedStatement preStmt2 = null;
		List<OrderDetails> ol = new ArrayList<OrderDetails>();
		ResultSet rs = null;
		ResultSet rs2 = null;
		StringBuilder queryBuilder = new StringBuilder();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			queryBuilder.append(OrderQueryConstants.GET_CUSTOMER_ORDERS);
			if(orderFilter.getFrom() !=null && orderFilter.getTo()!=null)
			{
				queryBuilder.append(" and o.created between to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.S') and to_timestamp(?,'YYYY-MM-DD HH24:MI:SS.S')");
			}
			else if(orderFilter.getCustomer_id()==0 && orderFilter.getOrder_id()==0 && orderFilter.getStatus()==0)
			{
				queryBuilder.append(" and o.created >=current_timestamp - interval '10 day' and o.created <=current_timestamp");
			}
			else if(orderFilter.getCustomer_id()!=0)
			{
				queryBuilder.append(" and o.customer_id = "+orderFilter.getCustomer_id());
			}
			else if(orderFilter.getOrder_id()!=0)
			{
				queryBuilder.append(" and o.order_id = "+orderFilter.getOrder_id());
			}
			else if(orderFilter.getStatus()!=0)
			{
				queryBuilder.append(" and o.order_status = "+orderFilter.getStatus());
			}		
			preStmt = connection.prepareStatement(queryBuilder.toString());
			if(orderFilter.getFrom() !=null && orderFilter.getTo()!=null)
			{
				preStmt.setString(1, orderFilter.getFrom());
				preStmt.setString(2, orderFilter.getTo());
			}
			rs = preStmt.executeQuery();
			while(rs.next())
			{
				OrderDetails od = new OrderDetails();
				od.setOrder_id(rs.getInt("order_id"));
				od.setCustomer_id(rs.getInt("customer_id"));
				od.setOrder_status(rs.getInt("order_status"));
				od.setVoucher_id(rs.getInt("voucher_id"));
				od.setOrder_total(rs.getDouble("order_total"));
				od.setOrder_grand_total(rs.getDouble("order_grand_total"));
				od.setOrder_date(rs.getString("created"));
				OrderDelivery odl = new OrderDelivery();
				odl.setAddress_type(rs.getInt("address_type"));
				odl.setPincode(rs.getString("pincode"));
				Gson gson = new Gson(); 
				OrderDelivery[] adarray = gson.fromJson(rs.getString("address_location"), OrderDelivery[].class); 
				for(OrderDelivery df : adarray) {
					odl.setName(df.getName());
					odl.setLat(df.getLat());
					odl.setLng(df.getLng());
				}
				od.setOrderDelivery(odl);
				List<OrderItemsDetails> odList = new ArrayList<OrderItemsDetails>();
				preStmt2 = connection.prepareStatement(OrderQueryConstants.GET_ORDER_ITEMS_BYID);
				preStmt2.setInt(1, od.getOrder_id());
				rs2 = preStmt2.executeQuery();
				while (rs2.next()) {
					OrderItemsDetails ot = new OrderItemsDetails();
					ot.setItem_quantity(rs2.getInt("item_quantity"));
					ot.setItem_total_price(rs2.getDouble("item_total_price"));
					ot.setProduct_id(rs2.getInt("product_id"));
					ot.setProduct_name(rs2.getString("product_name"));
					ot.setOrder_item_id(rs2.getInt("order_item_id"));
					odList.add(ot);				
				}
				rs2.close();
				preStmt2.close();
				od.setOrder_item_details(odList);
				ol.add(od);
			}
			
			

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getOrderDetails " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getOrderDetails " + e.getMessage());
			}

		}
		return ol;
	}



	
	
	
}
