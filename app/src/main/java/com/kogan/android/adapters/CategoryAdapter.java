package com.kogan.android.adapters;

import android.support.v4.view.PagerAdapter;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.AdapterView;
import android.util.Log;

import com.viewpagerindicator.TitleProvider;

import com.kogan.android.widget.amazinglist.AmazingListView;
import com.kogan.android.adapters.ProductAdapter;
import com.kogan.android.ui.MainActivity;
import com.kogan.android.ui.ProductDetailActivity;
import com.kogan.android.R;

public class CategoryAdapter extends PagerAdapter implements TitleProvider {
    private MainActivity activity;
    private String department_slug;
    private ArrayList<ProductAdapter> arrayAdapters;
    private static LayoutInflater inflater = null;


    public CategoryAdapter(MainActivity a, String slug, ArrayList<ProductAdapter> arrayAdapters) {
        this.activity = a;
        this.department_slug = slug;
        this.arrayAdapters = arrayAdapters;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return activity.categories.size();
    }

    @Override
    public boolean isViewFromObject(final View view, final Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        Context c = (Context) activity;
        final AmazingListView alv = new AmazingListView(c);
        alv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(activity, ProductDetailActivity.class);
                intent.putExtra("product", arrayAdapters.get(position).getProduct(arg2));
                activity.startActivity(intent);
            }
        });
        alv.setPinnedHeaderView(inflater.inflate(R.layout.product_header, alv, false));
        alv.setLoadingView(inflater.inflate(R.layout.loading_view, null));
        alv.setAdapter(arrayAdapters.get(position));
        arrayAdapters.get(position).notifyMayHaveMorePages();
        container.addView(alv, 0);
        return alv;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((AmazingListView) object);
    }

    public String getTitle(final int position) {
        return activity.categories.get(position).getTitle().toUpperCase();
    }
}
