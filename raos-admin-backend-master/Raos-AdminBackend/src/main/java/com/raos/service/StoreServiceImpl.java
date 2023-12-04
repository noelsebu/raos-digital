package com.raos.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.raos.dao.StoreDao;
import com.raos.logger.CommonLogger;
import com.raos.model.CategoryUpload;
import com.raos.model.Offers;
import com.raos.model.ProductUpload;
import com.raos.model.StockDetails;
import com.raos.model.StocksUpload;
import com.raos.model.StoreDelivery;
import com.raos.model.StoreProducts;
import com.raos.model.Vouchers;
import com.raos.util.CSVHelper;

@Component
public class StoreServiceImpl implements StoreService {

	@Autowired
	CommonLogger LOGGER;
	
	@Autowired
	StoreDao storeDao;
		
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");


	@Override
	public List<StoreDelivery> getDelivery() {
		return storeDao.getDelivery();
	}


	@Override
	public boolean updateDeliveryDetails(StoreDelivery slotsRequests) {
		return storeDao.updateDeliveryDetails(slotsRequests);
	}


	@Override
	public List<StoreProducts> listProducts() {
		return storeDao.listProducts();
	}


	@Override
	public boolean editProduct(StoreProducts sp) {
		return storeDao.editProduct(sp);
	}


	@Override
	public boolean deleteProduct(int product_id) {
		return storeDao.deleteProduct(product_id);
	}


	@Override
	public List<Offers> listOffers() {
		return storeDao.listOffers();
	}


	@Override
	public boolean addOffer(Offers off) {
		return storeDao.addOffer(off);
	}


	@Override
	public boolean editOffer(Offers off) {
		return storeDao.editOffer(off);
	}


	@Override
	public boolean deleteOffer(int offer_id) {
		return storeDao.deleteOffer(offer_id);
	}
		

	@Override
    public void saveProduct(MultipartFile file) {
	    try {
	      List<ProductUpload> products = CSVHelper.csvToProducts(file.getInputStream());
		     System.out.println("STARTED TO INSERT :"+dateFormat.format(new Date()));
	     for (ProductUpload productUpload : products) {
			storeDao.saveProducts(productUpload);
		}
			 System.out.println("INSERT COMPLETED :"+dateFormat.format(new Date()));
	    } catch (Exception e) {
	      LOGGER.info(this.getClass(),"FAILED TO STORE PRODUCTS CSV " + e.getMessage());
	      throw new RuntimeException("FAILED TO STORE PRODUCTS CSV " + e.getMessage());
	    
	    }
	  }

	@Override
    public void saveCategory(MultipartFile file) {
	    try {
	      List<CategoryUpload> category = CSVHelper.csvToCategories(file.getInputStream());
	     System.out.println("STARTED TO INSERT :"+dateFormat.format(new Date()));
	     for (CategoryUpload categoryUpload : category) {
	    	 storeDao.saveCategory(categoryUpload);
		}
	     
		 System.out.println("INSERT COMPLETED :"+dateFormat.format(new Date()));

	    } catch (Exception e) {
	      LOGGER.info(this.getClass(),"FAILED TO STORE CATEGORY CSV " + e.getMessage());
	      throw new RuntimeException("FAILED TO STORE CATEGORY CSV " + e.getMessage());
	    
	    }
	  }
	
	@Override
	public List<Vouchers> listVouchers() {
		return storeDao.listVouchers();
	}


	@Override
	public boolean addVoucher(Vouchers sp) {
		return storeDao.addVoucher(sp);
	}
	
	@Override
	public boolean editVoucher(Vouchers sp) {
		return storeDao.editVoucher(sp);
	}

	@Override
	public boolean deleteVoucher(int vocuher_id) {
		return storeDao.deleteVoucher(vocuher_id);
	}

	@Override
    public void saveStocks(MultipartFile file) {
	    try {
	      List<StocksUpload> stocks = CSVHelper.csvToStocks(file.getInputStream());
	      storeDao.createBackupStocksTable();
	      storeDao.deleteStocksTable();
	     System.out.println("STARTED TO INSERT :"+dateFormat.format(new Date()));
	     for (StocksUpload stocksupload : stocks) {
	    	 storeDao.saveStocks(stocksupload);
		}
		 System.out.println("INSERT COMPLETED :"+dateFormat.format(new Date()));

	    } catch (Exception e) {
	      LOGGER.info(this.getClass(),"FAILED TO STORE STOCKS CSV " + e.getMessage());
	      throw new RuntimeException("FAILED TO STORE STOCKS CSV " + e.getMessage());
	    
	    }
	  }
	
	@Override
	public List<StockDetails> viewStocks() {
		return storeDao.viewStocks();
	}

	

}
