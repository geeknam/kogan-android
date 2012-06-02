package com.kogan.android.core;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import java.io.Reader;

import com.github.kevinsawicki.http.HttpRequest;
import com.github.kevinsawicki.http.HttpRequest.HttpRequestException;

import java.io.IOException;
import java.util.List;
import java.util.Collections;
import android.util.Log;

public class KoganService {

    public static final Gson GSON = new Gson();

    private static class ProductsWrapper {
        private List<Product> objects;
    }

    protected HttpRequest execute(HttpRequest request) throws IOException {
        return request;
    }

    private <V> V fromJson(HttpRequest request, Class<V> target) throws IOException {
        Reader reader = request.bufferedReader();
        try {
            return GSON.fromJson(reader, target);
        } catch (JsonParseException e) {
            Log.d("KOGAANNNNNN", "JsonParseException");
            // throw new JsonException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException ignored) {
                // Ignored
            }
        }
        return null;
    }

    public List<Product> getProducts(String url) throws IOException {
        try {
            HttpRequest request = execute(HttpRequest.get(url));
            ProductsWrapper response = fromJson(request, ProductsWrapper.class);
            if (response != null && response.objects != null){
                Log.d("KOGAANNNNNN", "Got the products");
                return response.objects;
            }
            return Collections.emptyList();
        } catch (HttpRequestException e) {
            throw e.getCause();
        }
    }
}