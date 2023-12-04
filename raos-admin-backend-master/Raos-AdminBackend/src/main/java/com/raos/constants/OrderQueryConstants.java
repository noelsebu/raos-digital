package com.raos.constants;

public interface OrderQueryConstants {
	
	
	String GET_CUSTOMER_ORDERS  = "select o.order_id,o.customer_id,o.order_total,o.order_grand_total,o.order_status,o.voucher_id,od.address_type,od.address_location,o.created,od.pincode from orders o,order_delivery od where o.order_id=od.order_id ";
	
	String GET_ORDER_ITEMS_BYID= "select oi.order_item_id,oi.item_total_price,oi.item_quantity,p.product_name,p.product_id from order_items oi,products p where oi.product_id = p.product_id and oi.order_id = ?";
	
	String UPDATE_ORDER_STATUS = "update orders set order_status= ? where order_id = ?";

	
	}
