package com.raos.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.raos.model.Offers;
import com.raos.model.StockDetails;
import com.raos.model.StoreDelivery;
import com.raos.model.StoreProducts;
import com.raos.model.Vouchers;

@Service
public interface StoreService {
	
	public List<StoreDelivery> getDelivery();

	public boolean updateDeliveryDetails(StoreDelivery deliveryDetails);

	public List<StoreProducts> listProducts();

	public boolean editProduct(StoreProducts sp);

	public boolean deleteProduct(int product_id);

	public List<Offers> listOffers();

	public boolean addOffer(Offers off);

	public boolean editOffer(Offers off);

	public boolean deleteOffer(int offer_id);

	void saveProduct(MultipartFile file);

	public void saveCategory(MultipartFile file);

	public List<Vouchers> listVouchers();

	public boolean addVoucher(Vouchers voucher);

	public boolean editVoucher(Vouchers voucher);

	public boolean deleteVoucher(int voucher_id);

	public void saveStocks(MultipartFile file);

	public List<StockDetails> viewStocks();
}
