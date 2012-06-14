package com.kogan.android.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.ListView;
import android.widget.ArrayAdapter;


import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

import roboguice.inject.InjectView;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockListActivity;

import com.kogan.android.R;
import com.kogan.android.adapters.ProductDetailAdapter;
import com.kogan.android.core.Product;
import com.kogan.android.core.KoganService;

import java.util.List;
import java.io.IOException;

public class SearchActivity extends RoboSherlockListActivity {
    
    ListView productList;
    String[] productTitles;
    String query;
    public SharedPreferences sharedPreferences;
    public KoganService service;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("KOGAN_PREF", 0);
        service = new KoganService(sharedPreferences);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        
        query = getIntent().getStringExtra("query");
        productList = getListView();

        new GetProductsTask().execute();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class GetProductsTask extends AsyncTask<String, Integer, Boolean> {
        private ProgressDialog dialog;

        // public GetCategoriesTask(RoboSherlockActivity activity) {
        //     this.activity = activity;
        // }

        protected void onPreExecute() {
            dialog = ProgressDialog.show(SearchActivity.this, "Please wait...", "Searching products");
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            productList.setAdapter(new ArrayAdapter(SearchActivity.this, android.R.layout.simple_list_item_1, productTitles));
            dialog.dismiss();
        }

        protected Boolean doInBackground(final String... args) {
            try{
                List<Product> products = service.searchProducts(query);
                productTitles = new String[products.size()];
                for(int i=0; i<products.size(); i++){
                    productTitles[i] = products.get(i).getTitle();
                }
            } catch (IOException ignored) {
            }

            return true;
        }
    }

}