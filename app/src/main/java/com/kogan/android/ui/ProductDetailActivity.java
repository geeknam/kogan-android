package com.kogan.android.ui;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;

import com.actionbarsherlock.view.MenuItem;

import android.support.v4.view.ViewPager;
import com.viewpagerindicator.TabPageIndicator;
import roboguice.inject.InjectView;

import com.kogan.android.R;
import com.kogan.android.adapters.ProductDetailAdapter;
import com.kogan.android.core.Product;
import com.kogan.android.widget.lazylist.ImageLoader;


public class ProductDetailActivity extends BaseActivity {

    @InjectView(R.id.product_details_pager)
    private ViewPager detailsPager;
    
    @InjectView(R.id.product_details_pager_indicator)
    private TabPageIndicator tabPageIndicator;

    Product product;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.product_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageLoader imageLoader = new ImageLoader(getApplicationContext());
        product = (Product) getIntent().getSerializableExtra("product");

        ImageView image = (ImageView) findViewById(R.id.image);
        imageLoader.DisplayImage(product.getImageUrl(), image);

        detailsPager.setAdapter(new ProductDetailAdapter(this, product.getSlug()));
        tabPageIndicator.setViewPager(detailsPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}