package com.kogan.android.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonParseException;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

import java.io.IOException;
import java.io.Reader;
import java.io.File;
import java.util.List;
import java.util.HashMap;
import java.util.Collections;
import java.lang.reflect.Type;

import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import static com.kogan.android.core.KoganConstants.URL_PRODUCT;
import static com.kogan.android.core.KoganConstants.URL_DEPARTMENT;
import static com.kogan.android.core.KoganConstants.URL_CATEGORY;

public class KoganService {

    public static final Gson GSON = new Gson();
    public HashMap<String, String> cacheMap = new HashMap<String, String>();
    public Type productToken = new TypeToken<List<Product>>(){}.getType();
    public StateManager stateManager;

    public KoganService(SharedPreferences sp){
        stateManager = new StateManager(sp);
    }

    private static class ProductsWrapper {
        private Meta meta;
        private List<Product> objects;
    }

    private static class DepartmentsWrapper {
        private List<Department> objects;
    }

    private static class CategoriesWrapper {
        private List<Category> objects;
    }

    protected HttpRequest execute(HttpRequest request) throws IOException {
        return request;
    }

    private <V> V fromJson(HttpRequest request, Class<V> target) throws IOException {
        Reader reader = request.bufferedReader();
        try {
            return GSON.fromJson(reader, target);
        } catch (JsonParseException e) {
            // Log.d("KOGAANNNNNN", "JsonParseException");
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
                // Ignored
            }
        }
        return null;
    }

    private void cacheProducts(String key, List<Product> products){
        stateManager.saveString(key, GSON.toJson(products, productToken));
    }

    private List<Product> getCachedProducts(String key){
        return GSON.fromJson(stateManager.loadString(key), productToken);
    }

    public List<Product> getProducts(String param) throws IOException {
        String url = URL_PRODUCT + param;
        if (stateManager.contains(url)){
            return getCachedProducts(url);
        }
        else{
            try {
                HttpRequest request = execute(HttpRequest.get(url));
                ProductsWrapper response = fromJson(request, ProductsWrapper.class);
                if (response != null && response.objects != null){
                    cacheProducts(url, response.objects);
                    return response.objects;
                }
                return Collections.emptyList();
            } catch (HttpRequestException e) {
                throw e.getCause();
            }
        }
    }

    public List<Product> getProductsFor(String department_slug, String category_slug, int offset) throws IOException {
        return getProducts("&department=" + department_slug + "&category=" + category_slug + "&offset=" + offset);
    } 

    public List<Product> searchProducts(String keyword) throws IOException {
        return getProducts("&keywords=" + keyword);
    }

    public List<Department> getDepartments(String param) throws IOException {
        String url = URL_DEPARTMENT + param;
        try {
            HttpRequest request = execute(HttpRequest.get(url));
            DepartmentsWrapper response = fromJson(request, DepartmentsWrapper.class);
            if (response != null && response.objects != null){
                return response.objects;
            }
            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

    public List<Category> getCategoriesForDepartment(String department_slug) throws IOException {
        String url = URL_CATEGORY + "&department__slug=" + department_slug;
        try {
            HttpRequest request = execute(HttpRequest.get(url));
            CategoriesWrapper response = fromJson(request, CategoriesWrapper.class);
            if (response != null && response.objects != null){
                return response.objects;
            }
            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }

}