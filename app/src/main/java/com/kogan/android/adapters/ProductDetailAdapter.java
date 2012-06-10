package com.kogan.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.TextView;

import android.support.v4.view.PagerAdapter;
import com.viewpagerindicator.TitleProvider;


public class ProductDetailAdapter extends PagerAdapter implements TitleProvider {

    private Activity activity;
    private String product_slug;
    private final static String[] tabs = {"FEATURES", "SPECS", "WARRANTIES"};


    public ProductDetailAdapter(Activity a, String slug) {
        this.activity = a;
        this.product_slug = slug;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(final View view, final Object o) {
        return view == o;
    }


    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        Context c = (Context) activity;
        final TextView tv = new TextView(c);
        tv.setText("Test");

        container.addView(tv, 0);
        return tv;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((TextView) object);
    }

    public String getTitle(final int position) {
        return tabs[position];
    }
}