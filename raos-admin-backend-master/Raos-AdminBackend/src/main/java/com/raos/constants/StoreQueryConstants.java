package com.raos.constants;

public interface StoreQueryConstants {
	
	
	String GET_DELIVERY_SLOTS  = "select id,instant_delivery,day,delivery_slots,status from delivery_details";
	
	String UPDATE_STORE_SLOTS = "update delivery_details set day=?,delivery_slots=?,status=?,instant_delivery=? where id = ?";
	
	String UPDATE_STORE_PRODUCT = "update products set product_name=?,image_url=?,discount_flag=?,status=? where product_id=?";
	
	String DELETE_STORE_PRODUCT = "delete from products where product_id=?";

	String GET_ALL_PRODUCTS = "select p.product_id,p.product_name,p.discount_flag,p.status," + 
			"(select c.category_name from category c where p.category_1=c.category_id ) as category_1,\r\n" + 
			"(select c.category_name from category c where p.category_2=c.category_id ) as category_2,\r\n" + 
			"(select c.category_name from category c where p.category_3=c.category_id ) as category_3 from products p";
	
	String GET_ALL_OFFERS = "select offer_id,offer_name,offer_products,offer_status from offers";
	
	String ADD_OFFERS = "insert into offers (offer_id,offer_name,offer_products,created,updated) values (nextval('seq_offer_id'),?,?,current_date,current_date)";
	
	String UPDATE_OFFER = "update offers set offer_name=?,offer_products=?,offer_status=?,updated=current_date where offer_id=?";
	
	String DELETE_OFFER = "delete from offers where offer_id=?";
	
	String ADD_PRODUCTS = "insert INTO products\r\n" + 
			"(product_id, product_name, category_1, category_2, discount_flag,created, updated, unit_metrics, image_url, category_3)\r\n" + 
			"VALUES(?, ?, ?, ?, ?,current_date, current_date, ?, ?, ?) on conflict (product_id) do UPDATE SET product_name = excluded.product_name, \r\n" + 
			"category_1=excluded.category_1,category_2=excluded.category_2,discount_flag = excluded.discount_flag," + 
			"updated=current_date,unit_metrics=excluded.unit_metrics, image_url=excluded.image_url, category_3=excluded.category_3";

	String ADD_CATEGORY = "INSERT INTO category (category_id, category_name, category_alias_name, created, updated, category_type_code) \r\n" + 
			"VALUES(?, ?, ?, NULL, NULL, ?) on conflict (category_id) do UPDATE SET category_name=excluded.category_name,\r\n" + 
			"category_alias_name=excluded.category_alias_name,category_type_code=excluded.category_type_code,updated=current_date";
	
	String GET_ALL_VOUCHERS = "SELECT id, voucher_name, voucher_code, status, image_url FROM vouchers";
	
	String ADD_VOUCHER = "INSERT INTO vouchers (id, voucher_name, voucher_code, status, created, updated, image_url) VALUES(nextval('seq_voucher_id'), ?, ?, 1, current_date,current_Date,?)";
	
	String UPDATE_VOUCHER = "update vouchers set voucher_name=?,voucher_code=?,status=?,image_url=?,updated=current_date where id=?";
	
	String DELETE_VOUCHER = "delete from vouchers where id=?";
	
	String ADD_STOCKS = "INSERT INTO stocks (product_id, balance_stock, sales_price, mrp_price, created, updated, stock_id) VALUES(?, ?, ?, ?, current_date, current_date, nextval('seq_stock_id'))";

	String GET_STOCKS = "select 	p.product_name,	p.discount_flag,s.product_id,sum(s.balance_stock) as stock,	max(s.mrp_price) as mrp_price,	max(s.sales_price) as sales_price\r\n" + 
			"from stocks s,products p where	p.product_id = s.product_id	group by s.product_id,p.product_name,p.discount_flag order by s.product_id";
}
