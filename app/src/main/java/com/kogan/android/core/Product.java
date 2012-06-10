package com.kogan.android.core;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Product implements Serializable{
	
	public String id;

	public String title;
	
	@SerializedName("short_title")
	public String shortTitle;
	
	@SerializedName("sub_title")
	public String subTitle;
	
	public String slug;
	
	public String sku;
	
	public String price;

	public String price_type;
	
	public String brand;
	
	public String image;
	
	public String description;
	
	@SerializedName("on_sale")
	public boolean onSale;
	
	@SerializedName("free_shipping")
	public boolean freeShipping;

	public String getId(){
		return this.id;
	}

	public String getTitle(){
		return this.title;
	}

	public String getSlug(){
		return this.slug;
	}

	public String getImageUrl(){
		return "http://media.kogan.com/" + image;
	}

	public String getPrice(){
		if(price.equals("0"))
			return "Sold Out";
		return "$" + price;
	}

	public boolean isSoldOut(){
		if(price.equals("0")){
			return true;
		}
		return false;
	}

	public boolean isLivePrice(){
		if(price_type != null){
			if(price_type.equals("Live")){
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString(){
		return title;
	}
	
}
