package com.kogan.android.core;

public interface KoganConstants {
	
	String URL_BASE = "http://www.kogan.com/api/v1/";
	String FORMAT = "?format=json";
	String URL_PRODUCT = URL_BASE + "product/" + FORMAT;
    String URL_DEPARTMENT = URL_BASE + "department/" + FORMAT;
}
