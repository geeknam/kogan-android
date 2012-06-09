package com.kogan.android.core;

public interface KoganConstants {
	
    String ROOT_URL = "http://www.kogan.com/";
    String API_URL = "api/v1/";
	String URL_BASE = ROOT_URL + API_URL;
	String FORMAT = "?format=json";
    String LIMIT = "&limit=5";
	String URL_PRODUCT = URL_BASE + "product/" + FORMAT + LIMIT;
    String URL_DEPARTMENT = URL_BASE + "department/" + FORMAT;
    String URL_CATEGORY = URL_BASE + "category/" + FORMAT;

}
