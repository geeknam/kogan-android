package com.kogan.android.core;

import com.google.gson.annotations.SerializedName;
import static com.kogan.android.core.KoganConstants.MEDIA_URL;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable{
	
	public String id;

	public String title;
	
	@SerializedName("short_title")
	public String shortTitle;
	
	@SerializedName("sub_title")
	public String subTitle;
	
	public String slug;
	
	public String sku;

	public String url;
	
	public String price;

	public String price_type;
	
	public String brand;
	
	public String image;

	public ArrayList<String> images;
	
	public String description;
	
	@SerializedName("on_sale")
	public boolean onSale;
	
	@SerializedName("free_shipping")
	public boolean freeShipping;

	public ArrayList<Facet> facets;

	public ArrayList<String> features;

	public String getId(){
		return this.id;
	}

	public String getTitle(){
		return this.title;
	}

	public String getSlug(){
		return this.slug;
	}

	public String getUrl(){
		return this.url;
	}

	public String getImageUrl(){
		return MEDIA_URL + image;
	}

	public ArrayList<String> getAllImages(){
		ArrayList<String> ret = new ArrayList<String>();
		for(String i : images){
			ret.add(MEDIA_URL + i);
		}
		return ret;
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

	public ArrayList<Facet> getFacets(){
		return facets;
	}

	public ArrayList<String> getFeatures(){
		return features;
	}

	@Override
	public String toString(){
		return title;
	}
	
}
