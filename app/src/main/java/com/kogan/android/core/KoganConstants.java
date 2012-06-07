package com.kogan.android.core;

public interface KoganConstants {
	
	String URL_BASE = "http://www.kogan.com/api/v1/";
	String FORMAT = "?format=json";
    String LIMIT = "&limit=5";
    String PARAMS = FORMAT + LIMIT;
	String URL_PRODUCT = URL_BASE + "product/" + PARAMS;
    String URL_DEPARTMENT = URL_BASE + "department/" + PARAMS;

}
