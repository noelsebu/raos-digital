package com.raos.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.raos.constants.StoreQueryConstants;
import com.raos.logger.CommonLogger;
import com.raos.model.CategoryUpload;
import com.raos.model.DeliveryTimeSlot;
import com.raos.model.Offers;
import com.raos.model.ProductUpload;
import com.raos.model.StockDetails;
import com.raos.model.StocksUpload;
import com.raos.model.StoreDelivery;
import com.raos.model.StoreProducts;
import com.raos.model.Vouchers;


@Component
public class StoreDaoImpl implements StoreDao {
	
	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	JdbcTemplate jdbctemp;
	
	
	
	@Override
	public List<StoreDelivery> getDelivery() {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet rs = null;
		List<StoreDelivery> stList = new ArrayList<StoreDelivery>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_DELIVERY_SLOTS);
			rs = preStmt.executeQuery();
			while(rs.next())
			{
				StoreDelivery sd = new StoreDelivery();
				sd.setId(rs.getInt("id"));
				sd.setInstant_delivery(rs.getInt("instant_delivery"));
				sd.setDay(rs.getString("day"));
				sd.setStatus(rs.getInt("status"));
				Gson gson = new Gson(); 
				List<DeliveryTimeSlot> dtsList = new ArrayList<DeliveryTimeSlot>();
				DeliveryTimeSlot dts = new DeliveryTimeSlot();
				DeliveryTimeSlot[] adarray = gson.fromJson(rs.getString("delivery_slots"), DeliveryTimeSlot[].class); 
				for(DeliveryTimeSlot df : adarray) {
					dts.setFrom(df.getFrom());
					dts.setTo(df.getTo());
					dtsList.add(dts);
				}
				sd.setSlots(dtsList);
				stList.add(sd);
			}
			
			

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE getDelivery " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB getDelivery " + e.getMessage());
			}

		}
		return stList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateDeliveryDetails(StoreDelivery deliveryDetails) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		JSONArray ja = new JSONArray();
		for (DeliveryTimeSlot slots : deliveryDetails.getSlots()) {
			JSONObject jo = new JSONObject();
			jo.put("from", slots.getFrom());
			jo.put("to",slots.getTo());
			ja.add(jo);
		}
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.UPDATE_STORE_SLOTS);
			preStmt.setString(1, deliveryDetails.getDay());
			preStmt.setString(2, ja.toString());
			preStmt.setInt(3, deliveryDetails.getStatus());
			preStmt.setInt(4, deliveryDetails.getInstant_delivery());
			preStmt.setInt(5, deliveryDetails.getId());
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
	public List<StoreProducts> listProducts() {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<StoreProducts> prodList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_ALL_PRODUCTS);
			res = preStmt.executeQuery();
			while(res.next()) {
				StoreProducts sp = new StoreProducts();
				sp.setProduct_id(res.getInt(1));
				sp.setProduct_name(res.getString(2));
				sp.setDiscount_flag(res.getString(3));
				sp.setStatus(res.getInt(4));
				sp.setCategory_1(res.getString(5));
				sp.setCategory_2(res.getString(6));
				sp.setCategory_3(res.getString(7));
				prodList.add(sp);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE listProducts " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON listProducts " + e.getMessage());
			}

		}
		return prodList;
	}

	@Override
	public boolean editProduct(StoreProducts sp) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.UPDATE_STORE_PRODUCT);
			preStmt.setString(1, sp.getProduct_name());
			preStmt.setString(2, sp.getImgUrl());
			preStmt.setString(3, sp.getDiscount_flag());
			preStmt.setInt(4, sp.getStatus());
			preStmt.setInt(5, sp.getProduct_id());
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE editProduct " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB editProduct " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean deleteProduct(int product_id) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.DELETE_STORE_PRODUCT);
			preStmt.setInt(1, product_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE deleteProduct " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB deleteProduct " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public List<Offers> listOffers() {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<Offers> offList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_ALL_OFFERS);
			res = preStmt.executeQuery();
			while(res.next()) {
				Offers off = new Offers();
				off.setOffer_id(res.getInt(1));
				off.setOffer_name(res.getString(2));
				off.setOffer_products(res.getString(3));
				off.setOffer_status(res.getInt(4));
				offList.add(off);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE listOffers " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON listOffers " + e.getMessage());
			}

		}
		return offList;
	}

	@Override
	public boolean addOffer(Offers off) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.ADD_OFFERS);
			preStmt.setString(1, off.getOffer_name());
			preStmt.setString(2, off.getOffer_products());
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE addOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB addOffer " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean editOffer(Offers off) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.UPDATE_OFFER);
			preStmt.setString(1, off.getOffer_name());
			preStmt.setString(2, off.getOffer_products());
			preStmt.setInt(3, off.getOffer_status());
			preStmt.setInt(4, off.getOffer_id());
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE editOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB editOffer " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean deleteOffer(int offer_id){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.DELETE_OFFER);
			preStmt.setInt(1, offer_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE deleteOffer " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB deleteOffer " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public void saveProducts(ProductUpload products) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.ADD_PRODUCTS);
			preStmt.setInt(1, products.getProduct_id());
			preStmt.setString(2, products.getProduct_name());
			preStmt.setInt(3, products.getCategory_1());
			preStmt.setInt(4, products.getCategory_2());
			preStmt.setString(5, products.getDiscount_flag());
			preStmt.setInt(6, products.getUnit_metrics());
			preStmt.setString(7, products.getImage_url());
			preStmt.setInt(8, products.getCategory_3());
			preStmt.executeUpdate();

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE saveProducts " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB saveProducts " + e.getMessage());
			}

		}
	}
	
	@Override
	public void saveCategory(CategoryUpload categoryUpload) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.ADD_CATEGORY);
			preStmt.setInt(1, categoryUpload.getCategory_id());
			preStmt.setString(2, categoryUpload.getCategory_name());
			preStmt.setString(3, categoryUpload.getCategory_alias_name());
			preStmt.setString(4, categoryUpload.getCategory_type_code());
			preStmt.executeUpdate();

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE saveProducts " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB saveProducts " + e.getMessage());
			}

		}
	}
	
	@Override
	public List<Vouchers> listVouchers() {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<Vouchers> vouchList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_ALL_VOUCHERS);
			res = preStmt.executeQuery();
			while(res.next()) {
				Vouchers vouchers = new Vouchers();
				vouchers.setVoucherId(res.getInt(1));
				vouchers.setVoucherName(res.getString(2));
				vouchers.setVoucherCode(res.getString(3));
				vouchers.setStatus(res.getInt(4));
				vouchers.setImage_url(res.getString(5));
				vouchList.add(vouchers);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE listVouchers " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON listVouchers " + e.getMessage());
			}

		}
		return vouchList;
	}

	@Override
	public boolean addVoucher(Vouchers vouchers) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.ADD_VOUCHER);
			preStmt.setString(1, vouchers.getVoucherName());
			preStmt.setString(2, vouchers.getVoucherCode());
			preStmt.setString(3, vouchers.getImage_url());
			
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE addVoucher " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB addVoucher " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean editVoucher(Vouchers vouchers) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.UPDATE_VOUCHER);
			preStmt.setString(1, vouchers.getVoucherName());
			preStmt.setString(2, vouchers.getVoucherCode());
			preStmt.setInt(3, vouchers.getStatus());
			preStmt.setString(4, vouchers.getImage_url());
			preStmt.setInt(5, vouchers.getVoucherId());

			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE editVoucher " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB editVoucher " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	@Override
	public boolean deleteVoucher(int voucher_id){
		Connection connection = null;
		PreparedStatement preStmt = null;
		boolean updStatus = false;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.DELETE_VOUCHER);
			preStmt.setInt(1, voucher_id);
			if (preStmt.executeUpdate()>0) {
				updStatus = true;
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE deleteVoucher " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB deleteVoucher " + e.getMessage());
			}

		}
		return updStatus;
	}
	
	
	@Override
	public void saveStocks(StocksUpload stocksUpload) {
		Connection connection = null;
		PreparedStatement preStmt = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.ADD_STOCKS);
			preStmt.setInt(1, stocksUpload.getProduct_id());
			preStmt.setFloat(2, stocksUpload.getBalance_stock());
			preStmt.setFloat(3, stocksUpload.getSales_price());
			preStmt.setFloat(4, stocksUpload.getMrp_price());
			preStmt.executeUpdate();

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE saveProducts " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB saveProducts " + e.getMessage());
			}

		}
	}
	
	@Override
	public void createBackupStocksTable()
	{
		Connection connection = null;
		Statement stmt = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			stmt = connection.createStatement();
			stmt.executeUpdate("create table stocks_"+new SimpleDateFormat("ddMMyyyyHHmmss").format(new Date())+" as table stocks");
	
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE createBackupStocksTable " + e.getMessage());
			e.printStackTrace();
		}
		 finally {
				try {
					stmt.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
					LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB createBackupStocksTable " + e.getMessage());
				}

			}
	}
	@Override
	public void deleteStocksTable()
	{
		Connection connection = null;
		PreparedStatement preStmt = null;
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement("delete from stocks");
			preStmt.executeUpdate();
		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE deleteStocksTable " + e.getMessage());
			e.printStackTrace();
		}
		 finally {
				try {
					preStmt.close();
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
					LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB deleteStocksTable " + e.getMessage());
				}

			}
	}
	
	@Override
	public List<StockDetails> viewStocks() {
		Connection connection = null;
		PreparedStatement preStmt = null;
		ResultSet res = null;
		
		List<StockDetails> stockList = new ArrayList<>();
		try {
			connection = jdbctemp.getDataSource().getConnection();
			preStmt = connection.prepareStatement(StoreQueryConstants.GET_STOCKS);
			res = preStmt.executeQuery();
			while(res.next()) {
				StockDetails st = new StockDetails();
				st.setProductName(res.getString(1));
				st.setDiscountFlag(res.getString(2));
				st.setProductId(res.getInt(3));
				st.setBalanceStock(res.getInt(4));
				st.setMrpPrice(res.getInt(5));
				st.setSalesPrice(res.getInt(6));
				stockList.add(st);
			}

		} catch (Exception e) {
			LOGGER.debug(this.getClass(), "ERROR IN DB WHILE viewStocks " + e.getMessage());
			e.printStackTrace();
		} finally {
			try {
				preStmt.close();
				connection.close();
				res.close();
			} catch (SQLException e) {
				e.printStackTrace();
				LOGGER.error(this.getClass(), "ERROR IN DB WHILE CLOSING DB ON viewStocks " + e.getMessage());
			}

		}
		return stockList;
	}
}
