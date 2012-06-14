package com.kogan.android.adapters;

import com.kogan.android.widget.amazinglist.*;
import com.kogan.android.widget.lazylist.ImageLoader;
import com.kogan.android.core.Product;
import com.kogan.android.core.KoganService;
import com.kogan.android.ui.MainActivity;
import com.kogan.android.R;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;

import android.util.Log;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;

public class ProductAdapter extends AmazingAdapter {

    List<Pair<String, Product>> products;
    public ImageLoader imageLoader;
    public MainActivity activity;
    private static LayoutInflater inflater = null;
    private AsyncTask<Integer, Void, List<Product>> backgroundTask;
    private String department;
    private String category;

    public ProductAdapter(MainActivity a, String d, String c){
        activity = a;
        department = d;
        category = c;
        products = new ArrayList<Pair<String, Product>>();
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader = new ImageLoader(activity.getApplicationContext());
    }

    public void add(Product p){
        products.add(new Pair<String, Product>(p.getTitle(), p));
    }

    public Product getProduct(int index){
        return products.get(index).second;
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

    public void reset() {
        Log.d("KOGAANNNNN", "RESET");
    }

    @Override
    protected void onNextPageRequested(int page) {
        Log.d("KOGAANNNNN", "Page requested: " + page);

        if (backgroundTask != null) {
            backgroundTask.cancel(false);
        }

        backgroundTask = new AsyncTask<Integer, Void, List<Product>>() {
            @Override
            protected List<Product> doInBackground(Integer... params) {
                //TODO: refactor this
                int page = (params[0] - 1) * 5;
                List<Product> data = new ArrayList<Product>();
                try{
                    data = activity.service.getProductsFor(department, category, page);
                } catch (IOException ignored) {
                    Log.d("KOGANNNNNNN", "IOEXCEPTION");
                }

                return data;
            }
            
            @Override
            protected void onPostExecute(List<Product> result) {
                if (isCancelled()) return; 
                
                for(Product p : result){
                    products.add(new Pair<String, Product>(p.getTitle(), p));
                }

                nextPage();
                notifyDataSetChanged();
                if(!result.isEmpty()) {
                    notifyMayHaveMorePages();
                } else {
                    notifyNoMorePages();
                }
            };
        }.execute(page);
    }

    @Override
    protected void bindSectionHeader(View view, int position, boolean displaySectionHeader) {
        if (displaySectionHeader) {
            view.findViewById(R.id.header).setVisibility(View.VISIBLE);
            TextView lSectionTitle = (TextView) view.findViewById(R.id.header);
            lSectionTitle.setText(getSections()[getSectionForPosition(position)]);
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
        
        Product p = products.get(position).second;

        TextView price = (TextView) res.findViewById(R.id.price);
        price.setText(p.getPrice());
        if(p.isLivePrice()){
            price.setTextColor(Color.parseColor("#bf0909"));
        }

        ImageView image = (ImageView) res.findViewById(R.id.image);
        imageLoader.DisplayImage(p.getImageUrl(), image);
        
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