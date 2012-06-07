package com.kogan.android.adapters;

import com.kogan.android.widget.amazinglist.*;
import com.kogan.android.widget.lazylist.ImageLoader;
import com.kogan.android.core.Product;
import com.kogan.android.R;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.util.List;
import java.util.ArrayList;

public class SectionProductAdapter extends AmazingAdapter {

    List<Pair<String, Product>> products;
    public ImageLoader imageLoader;
    public Activity activity;
    private static LayoutInflater inflater=null;

    public SectionProductAdapter(Activity a){
        activity = a;
        products = new ArrayList<Pair<String, Product>>();
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public void add(Product p){
        products.add(new Pair<String, Product>(p.getTitle(), p));
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Product getItem(int position) {
        Log.d("KOGANNNN", products.get(position).second.getTitle());
        return products.get(position).second;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void onNextPageRequested(int page) {
    }

    @Override
    protected void bindSectionHeader(View view, int position, boolean displaySectionHeader) {
        if (displaySectionHeader) {
            view.findViewById(R.id.header).setVisibility(View.VISIBLE);
            TextView lSectionTitle = (TextView) view.findViewById(R.id.header);
            lSectionTitle.setText(getSections()[getSectionForPosition(position)]);
            Log.d("KOGANNNNN", "bindSectionHeader: " + getSections()[getSectionForPosition(position)]);
        } else {
            Log.d("KOGANNNNN", "bindSectionHeader: GONE");
            view.findViewById(R.id.header).setVisibility(View.GONE);
        }
    }

    @Override
    public View getAmazingView(int position, View convertView, ViewGroup parent) {
        View res = convertView;
        if (res == null)
            res = inflater.inflate(R.layout.product_item, null);
        
        ImageView image =(ImageView) res.findViewById(R.id.image);
        imageLoader.DisplayImage(products.get(position).second.getImageUrl(), image);
        
        return res;
    }

    @Override
    public void configurePinnedHeader(View header, int position, int alpha) {
        TextView lSectionHeader = (TextView) header;
        lSectionHeader.setText(getSections()[getSectionForPosition(position)]);
    }

    @Override
    public int getPositionForSection(int section) {
        return section;
    }

    @Override
    public int getSectionForPosition(int position) {
        return position;
    }

    @Override
    public String[] getSections() {
        String[] res = new String[products.size()];
        for (int i = 0; i < products.size(); i++) {
            res[i] = products.get(i).first;
        }
        return res;
    }
    
}