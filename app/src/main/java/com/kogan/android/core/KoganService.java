package com.kogan.android.core;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.Reader;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

import java.io.IOException;
import java.util.List;
import java.util.Collections;
// import android.util.Log;

import static com.kogan.android.core.KoganConstants.URL_PRODUCT;
import static com.kogan.android.core.KoganConstants.URL_DEPARTMENT;

public class KoganService {

    public static final Gson GSON = new Gson();

    private static class ProductsWrapper {
        private List<Product> objects;
    }

    private static class DepartmentsWrapper {
        private List<Department> objects;
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

    public List<Product> getProducts(String param) throws IOException {
        String url = URL_PRODUCT + param;
        try {
            HttpRequest request = execute(HttpRequest.get(url));
            ProductsWrapper response = fromJson(request, ProductsWrapper.class);
            if (response != null && response.objects != null){
                return response.objects;
            }
            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
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

}