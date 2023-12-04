package com.raos.business;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.raos.logger.CommonLogger;
import com.raos.logger.ConfigProperties;
import com.raos.model.CommonDataResponse;
import com.raos.model.CommonResponse;
import com.raos.model.CommonSingleResponse;
import com.raos.model.Offers;
import com.raos.model.StockDetails;
import com.raos.model.StoreDelivery;
import com.raos.model.StoreProducts;
import com.raos.model.Vouchers;
import com.raos.request.DeliverySlotsRequest;
import com.raos.service.StoreService;
import com.raos.util.CSVHelper;

@Component
public class StoreBusiness {

	@Autowired
	CommonLogger LOGGER;

	@Autowired
	ConfigProperties configProp;

	@Autowired
	CommonResponse commonResponse;

	@Autowired
	StoreService storeService;
	
	@Autowired
	CommonDataResponse commonDataResponse;

	@Autowired
	CommonSingleResponse commonSingleResponse;


	public Object getDelivery() {
		List<StoreDelivery> sclist  = storeService.getDelivery();
		if (sclist.size() > 0) {
			LOGGER.info(this.getClass(), "STORE DELIVERY DETAILS LISTED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Store Delivery details Listed Successfully");
			commonSingleResponse.setData(sclist);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "STORE DELIVERY DETAILS NOT FOUND");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("Store delivery details not found");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object updateDeliveryDetails(DeliverySlotsRequest slotsRequests) {
		int count = 0;
		for (StoreDelivery deliveryDetails : slotsRequests.getDeliveryDetails()) {
			if(storeService.updateDeliveryDetails(deliveryDetails))
			{
				count++;	
			}
		}
		if(count == slotsRequests.getDeliveryDetails().size())
		{
			LOGGER.error(this.getClass(), "ALL STORE SLOTS UPDATED");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Slots updated Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(this.getClass(), "SLOTS NOT UPDATED");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Slots not updated");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
		
	}


	public Object listProducts() {
		List<StoreProducts> sclist  = storeService.listProducts();
		if (sclist.size() > 0) {
			LOGGER.info(this.getClass(), "STORE PRODUCTS DETAILS LISTED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Store Products details Listed Successfully");
			commonSingleResponse.setData(sclist);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "STORE PRODUCTS DETAILS NOT FOUND");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("Store Products details not found");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object editProducts(int product_id, String product_name, String img_url, String discount_flag, int status) {
		StoreProducts sp = new StoreProducts();
		sp.setProduct_id(product_id);
		sp.setProduct_name(product_name);
		sp.setImgUrl(img_url);
		sp.setDiscount_flag(discount_flag);
		sp.setStatus(status);
		if(storeService.editProduct(sp))
		{
			LOGGER.error(this.getClass(), "PRODUCT UPDATED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Product updated Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(this.getClass(), "PRODUCT NOT UPDATED");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Product not updated");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object deleteProduct(int product_id) {
		if(storeService.deleteProduct(product_id))
		{
			LOGGER.error(this.getClass(), "PRODUCT DELETED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Product deleted Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(this.getClass(), "UNABLE TO DELETE PRODUCT");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Unable to delete product");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object listOffers() {
		List<Offers> sclist  = storeService.listOffers();
		if (sclist.size() > 0) {
			LOGGER.info(this.getClass(), "STORE OFFERS LISTED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Store Offers Listed Successfully");
			commonSingleResponse.setData(sclist);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "STORE OFFERS NOT FOUND");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("Store Offers not found");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object addOffer(String offer_name, String[] offer_products) {
		Offers off = new Offers();
		off.setOffer_name(offer_name);
		off.setOffer_products(Arrays.asList(offer_products).toString());
		if (storeService.addOffer(off)) {
			LOGGER.info(this.getClass(), "OFFER ADDED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Offer Added Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "UNABLE TO ADD OFFER");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Unable to add Offer");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
}


	public Object editOffer(int offer_id, String offer_name,String[] offer_products, int offer_status) {
		Offers off = new Offers();
		off.setOffer_name(offer_name);
		off.setOffer_products(Arrays.asList(offer_products).toString());
		off.setOffer_id(offer_id);
		off.setOffer_status(offer_status);
		if (storeService.editOffer(off)) {
			LOGGER.info(this.getClass(), "OFFER UPDATED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Offer Updated Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "UNABLE TO UPDATE OFFER");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Unable to update Offer");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object deleteOffer(int offer_id) {
		if(storeService.deleteOffer(offer_id))
		{
			LOGGER.error(this.getClass(), "OFFER DELETED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Offer deleted Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(this.getClass(), "UNABLE TO DELETE OFFER");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Unable to delete offer");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object uploadProducts(MultipartFile file) {
		if (CSVHelper.hasCSVFormat(file)) {
		      try {
		    	  storeService.saveProduct(file);
		        commonResponse.setStatus(HttpStatus.OK.toString());
				commonResponse.setMessage("File "+file.getOriginalFilename() + " Uploaded Successfully");
				return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		      } catch (Exception e) {
		    	  commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.toString());
				  commonResponse.setMessage("File "+file.getOriginalFilename() + "couldn't be uploaded");
				  return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		      }
		    }

		 commonResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
		 commonResponse.setMessage("Please upload CSV file");
		  return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
	}
	
	public Object uploadCategory(MultipartFile file) {
		if (CSVHelper.hasCSVFormat(file)) {
		      try {
		    	storeService.saveCategory(file);
		        commonResponse.setStatus(HttpStatus.OK.toString());
				commonResponse.setMessage("File "+file.getOriginalFilename() + " Uploaded Successfully");
				return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		      } catch (Exception e) {
		    	  commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.toString());
				  commonResponse.setMessage("File "+file.getOriginalFilename() + "couldn't be uploaded");
				  return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		      }
		    }

		 commonResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
		 commonResponse.setMessage("Please upload CSV file");
		  return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
	}


	public Object listVouchers() {
		List<Vouchers> sclist  = storeService.listVouchers();
		if (sclist.size() > 0) {
			LOGGER.info(this.getClass(), "STORE VOUCHERS LISTED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Store Vouchers Listed Successfully");
			commonSingleResponse.setData(sclist);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "STORE VOUCHERS NOT FOUND");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("Store Vouchers not found");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object addVoucher(String voucher_name, String voucher_code, String image_url) {
		Vouchers voucher = new Vouchers();
		voucher.setVoucherName(voucher_name);
		voucher.setVoucherCode(voucher_code);
		voucher.setImage_url(image_url);
		if (storeService.addVoucher(voucher)) {
			LOGGER.info(this.getClass(), "VOUCHER ADDED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Voucher Added Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "UNABLE TO ADD VOUCHER");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Unable to add Voucher");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object editVoucher(int voucher_id, String voucher_name, String voucher_code,
			String image_url, int status) {
		Vouchers voucher = new Vouchers();
		voucher.setVoucherName(voucher_name);
		voucher.setVoucherCode(voucher_code);
		voucher.setImage_url(image_url);
		voucher.setStatus(status);
		voucher.setVoucherId(voucher_id);
		if (storeService.editVoucher(voucher)) {
			LOGGER.info(this.getClass(), "VOUCHER UPDATED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Voucher Updated Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "UNABLE TO UPDATE VOUCHER");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Unable to Update Voucher ");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object deleteVoucher(int voucher_id) {
		if(storeService.deleteVoucher(voucher_id))
		{
			LOGGER.error(this.getClass(), "VOUCHER DELETED SUCCESSFULLY");
			commonResponse.setStatus(HttpStatus.OK.toString());
			commonResponse.setMessage("Voucher deleted Successfully");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
		else
		{
			LOGGER.error(this.getClass(), "UNABLE TO DELETE VOUCHER");
			commonResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			commonResponse.setMessage("Unable to delete Voucher");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	public Object uploadStocks(MultipartFile file) {
		if (CSVHelper.hasCSVFormat(file)) {
		      try {
		    	storeService.saveStocks(file);
		        commonResponse.setStatus(HttpStatus.OK.toString());
				commonResponse.setMessage("File "+file.getOriginalFilename() + " Uploaded Successfully");
				return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		      } catch (Exception e) {
		    	  commonResponse.setStatus(HttpStatus.EXPECTATION_FAILED.toString());
				  commonResponse.setMessage("File "+file.getOriginalFilename() + "couldn't be uploaded");
				  return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		      }
		    }

		 commonResponse.setStatus(HttpStatus.BAD_REQUEST.toString());
		 commonResponse.setMessage("Please upload CSV file");
		  return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
	}


	public Object viewStocks() {
		List<StockDetails> sclist  = storeService.viewStocks();
		if (sclist.size() > 0) {
			LOGGER.info(this.getClass(), "STORE STOCKS DETAILS LISTED SUCCESSFULLY");
			commonSingleResponse.setStatus(HttpStatus.OK.toString());
			commonSingleResponse.setMessage("Store Stocks details Listed Successfully");
			commonSingleResponse.setData(sclist);
			return new ResponseEntity<CommonSingleResponse>(commonSingleResponse, HttpStatus.OK);
		} else {
			LOGGER.error(this.getClass(), "STORE STOCKS DETAILS NOT FOUND");
			commonResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			commonResponse.setMessage("Store Stocks details not found");
			return new ResponseEntity<CommonResponse>(commonResponse, HttpStatus.OK);
		}
	}


	
}
