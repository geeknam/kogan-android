package com.kogan.android.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import android.support.v4.view.PagerAdapter;
import com.viewpagerindicator.TitleProvider;

import com.kogan.android.core.Product;
import com.kogan.android.core.Facet;


public class ProductDetailAdapter extends PagerAdapter implements TitleProvider {

    private Activity activity;
    private Product product;
    private final static String[] tabs = {"OVERVIEW", "SPECS", "WARRANTIES"};


    public ProductDetailAdapter(Activity activity, Product product) {
        this.activity = activity;
        this.product = product;
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
        final ListView lv = new ListView(c);
        switch (position){
            case 0:
                String[] features = new String[product.getFeatures().size()];
                product.getFeatures().toArray(features);
                lv.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_list_item_1, features));
                break;
            case 1:
                String [] facets = new String[product.getFacets().size()];
                int i = 0;
                for(Facet f : product.getFacets()){
                    facets[i] = f.getName() + ": " + f.getValue();
                    i++;
                }
                lv.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_list_item_1, facets));
                break;
            case 2:
                String[] warranties = {"12 Months Warranty", "3 Years Warranty", "5 Years Warranty"};
                lv.setAdapter(new ArrayAdapter(activity, android.R.layout.simple_list_item_1, warranties));
                break;
        }
        
        container.addView(lv, 0);
        return lv;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((ListView) object);
    }

    public String getTitle(final int position) {
        return tabs[position];
    }
}