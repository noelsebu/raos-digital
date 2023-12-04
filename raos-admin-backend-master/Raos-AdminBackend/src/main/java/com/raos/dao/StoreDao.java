package com.raos.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.raos.model.CategoryUpload;
import com.raos.model.Offers;
import com.raos.model.ProductUpload;
import com.raos.model.StockDetails;
import com.raos.model.StocksUpload;
import com.raos.model.StoreDelivery;
import com.raos.model.StoreProducts;
import com.raos.model.Vouchers;

@Component
public interface StoreDao {
	
	public List<StoreDelivery> getDelivery();

	public boolean updateDeliveryDetails(StoreDelivery deliveryDetails);

	public List<StoreProducts> listProducts();

	public boolean editProduct(StoreProducts sp);

	public boolean deleteProduct(int product_id);

	public List<Offers> listOffers();

	public boolean addOffer(Offers off);

	public boolean editOffer(Offers off);

	public boolean deleteOffer(int offer_id);
	
	public void saveProducts(ProductUpload products);

	public void saveCategory(CategoryUpload categoryUpload);

	public List<Vouchers> listVouchers();

	public boolean addVoucher(Vouchers sp);

	public boolean editVoucher(Vouchers sp);

	public boolean deleteVoucher(int vocuher_id);

	public void saveStocks(StocksUpload stocksupload);
	
	public void createBackupStocksTable();
	
	public void deleteStocksTable();

	public List<StockDetails> viewStocks();
	
	}
