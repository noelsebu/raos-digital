package com.raos.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.raos.business.StoreBusiness;
import com.raos.logger.CommonLogger;
import com.raos.request.DeliverySlotsRequest;

@RestController
public class StoreController {
	
private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	

	@Autowired
	CommonLogger Logger;
	
	@Autowired
	StoreBusiness storeBusiness;

	@PostMapping("/api/store/getDeliverySlots")
	public Object getDelivery() {
		Logger.info(this.getClass(),"GET DELIVERY DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.getDelivery();
	}
	
	@PostMapping("/api/store/updateDeliverySlots")
	public Object updateDeliveryDetails(@RequestBody DeliverySlotsRequest slotsRequests) {
		Logger.info(this.getClass(),"UPDATE DELIVERY DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.updateDeliveryDetails(slotsRequests);
	}
	@PostMapping(value="/api/store/uploadproducts",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Object uploadProducts(@RequestParam("file") MultipartFile file) {
		Logger.info(this.getClass(),"UPLOAD PRODUCTS DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.uploadProducts(file);
	}	
	@PostMapping("/api/store/listproducts")
	public Object listProducts() {
		Logger.info(this.getClass(),"LIST PRODUCTS DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.listProducts();
	}
	@PostMapping("/api/store/editproduct")
	public Object editProduct(@RequestParam int product_id,@RequestParam String product_name,
			@RequestParam String img_url,@RequestParam String discount_flag,@RequestParam int status) {
		Logger.info(this.getClass(),"EDIT PRODUCT API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.editProducts(product_id,product_name,img_url,discount_flag,status);
	}
	@PostMapping("/api/store/deleteproduct")
	public Object deleteProduct(@RequestParam int product_id) {
		Logger.info(this.getClass(),"DELETE PRODUCT API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.deleteProduct(product_id);
	}
	@PostMapping("/api/store/listoffers")
	public Object listOffers() {
		Logger.info(this.getClass(),"LIST OFFERS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.listOffers();
	}
	@PostMapping("/api/store/addoffer")
	public Object addOffer(@RequestParam String offer_name,@RequestParam String[] offer_products) {
		Logger.info(this.getClass(),"ADD OFFER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.addOffer(offer_name,offer_products);
	}
	@PostMapping("/api/store/editoffer")
	public Object editOffer(@RequestParam int offer_id,@RequestParam String offer_name,@RequestParam String[] offer_products,@RequestParam int offer_status) {
		Logger.info(this.getClass(),"EDIT OFFER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.editOffer(offer_id,offer_name,offer_products,offer_status);
	}
	@PostMapping("/api/store/deleteoffer")
	public Object deleteOffer(@RequestParam int offer_id) {
		Logger.info(this.getClass(),"DELETE OFFER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.deleteOffer(offer_id);
	}
	
	@PostMapping(value="/api/store/uploadcategory",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Object uploadCategory(@RequestParam("file") MultipartFile file) {
		Logger.info(this.getClass(),"UPLOAD CATEGORY DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.uploadCategory(file);
	}
	
	@PostMapping("/api/store/listvouchers")
	public Object listVouchers() {
		Logger.info(this.getClass(),"LIST VOUCHERS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.listVouchers();
	}
	@PostMapping("/api/store/addvoucher")
	public Object addVoucher(@RequestParam String voucher_name,@RequestParam String voucher_code,
			@RequestParam String image_url) {
		Logger.info(this.getClass(),"ADD VOUCHER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.addVoucher(voucher_name,voucher_code,image_url);
	}
	@PostMapping("/api/store/editvoucher")
	public Object editVoucher(@RequestParam int voucher_id,@RequestParam String voucher_name,@RequestParam String voucher_code,
			@RequestParam String image_url,@RequestParam int status) {
		Logger.info(this.getClass(),"EDIT VOUCHER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.editVoucher(voucher_id,voucher_name,voucher_code,image_url,status);
	}
	@PostMapping("/api/store/deletevoucher")
	public Object deleteVoucher(@RequestParam int voucher_id) {
		Logger.info(this.getClass(),"DELETE VOUCHER API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.deleteVoucher(voucher_id);
	}
	
	@PostMapping(value="/api/store/uploadstocks",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public Object uploadStocks(@RequestParam("file") MultipartFile file) {
		Logger.info(this.getClass(),"UPLOAD STOCKS DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.uploadStocks(file);
	}
	
	@PostMapping("/api/store/viewstocks")
	public Object viewStocks() {
		Logger.info(this.getClass(),"VIEW STOCKS DETAILS API CALL STARTED AT "+dateFormat.format(new Date()));
		return storeBusiness.viewStocks();
	}

	
}
