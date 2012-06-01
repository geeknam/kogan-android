package com.kogan.android.core;
import com.google.gson.annotations.SerializedName;


public class Product {
	
	public String title;
	
	@SerializedName("short_title")
	public String shortTitle;
	
	@SerializedName("sub_title")
	public String subTitle;
	
	public String slug;
	
	public String sku;
	
	public String price;
	
	public String brand;
	
	public String image;
	
	public String description;
	
	@SerializedName("on_sale")
	public boolean onSale;
	
	@SerializedName("free_shipping")
	public boolean freeShipping;
	
	
}
