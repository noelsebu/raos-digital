package com.raos.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.raos.model.CategoryUpload;
import com.raos.model.ProductUpload;
import com.raos.model.StocksUpload;


public class CSVHelper {
	
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	  public static String TYPE = "csv";
	  
	  public static boolean hasCSVFormat(MultipartFile file) {

	    if (!TYPE.equals(file.getOriginalFilename().substring(file.getOriginalFilename().length()-3, file.getOriginalFilename().length()))) {
	      return false;
	    }

	    return true;
	  }

	public static List<ProductUpload> csvToProducts(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
	      List<ProductUpload> products = new ArrayList<ProductUpload>();
	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

	      for (CSVRecord csvRecord : csvRecords) {
	    	  ProductUpload prod = new ProductUpload();
	    	  prod.setProduct_id(Integer.parseInt(csvRecord.get("MIH_ITEM_CODE")));
	    	  prod.setProduct_name(csvRecord.get("MIH_ITEM_NAME"));
	    	  prod.setCategory_1(Integer.parseInt(csvRecord.get("MIH_CATEGORY_1")));
	    	  prod.setCategory_2(Integer.parseInt(csvRecord.get("MIH_CATEGORY_2")));
	    	  prod.setCategory_3(Integer.parseInt(csvRecord.get("MIH_CATEGORY_3")));
	          prod.setDiscount_flag(csvRecord.get("mih_allow_discount"));
	          prod.setImage_url(csvRecord.get("image_url"));
	          prod.setUnit_metrics(Integer.parseInt(csvRecord.get("unit_metrics")));
	          products.add(prod);
	      }

	      return products;
	    } catch (Exception e) {
	      throw new RuntimeException("FAILED TO PARSE CATEGORY CSV FILE " + e.getMessage());
	    }
	  }
	
	public static List<CategoryUpload> csvToCategories(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
	      List<CategoryUpload> category = new ArrayList<CategoryUpload>();
	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
	      for (CSVRecord csvRecord : csvRecords) {
	    	  CategoryUpload cat = new CategoryUpload();
	    	  cat.setCategory_id(Integer.parseInt(csvRecord.get("MCD_CAT_CODE")));
	    	  cat.setCategory_name(csvRecord.get("MCD_CAT_NAME"));
	    	  cat.setCategory_alias_name(csvRecord.get("MCD_CAT_ANAME"));
	    	  cat.setCategory_type_code(csvRecord.get("MCD_TYPE_CODE"));
	          category.add(cat);
	      }
	      return category;
	    } catch (Exception e) {
	      throw new RuntimeException("FAILED TO PARSE CATEGORY CSV FILE " + e.getMessage());
	    }
	  }
	
	public static List<StocksUpload> csvToStocks(InputStream is) {
	    try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        CSVParser csvParser = new CSVParser(fileReader,
	        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
	      List<StocksUpload> stockList = new ArrayList<StocksUpload>();
	      Iterable<CSVRecord> csvRecords = csvParser.getRecords();
	      for (CSVRecord csvRecord : csvRecords) {
	    	  StocksUpload stocks = new StocksUpload();
	    	  stocks.setProduct_id(Integer.parseInt(csvRecord.get("MID_ITEM_CODE")));
	    	  stocks.setBalance_stock(Float.parseFloat(csvRecord.get("MID_BAL_STOCK")));
	    	  stocks.setSales_price(Float.parseFloat(csvRecord.get("Saleprice")));
	    	  stocks.setMrp_price(Float.parseFloat(csvRecord.get("MRP")));
	    	  stockList.add(stocks);
	      }
	      return stockList;
	    } catch (Exception e) {
	      throw new RuntimeException("FAILED TO PARSE STOCK CSV FILE " + e.getMessage());
	    }
	  }
}