package com.kogan.android.adapters;

import android.support.v4.view.PagerAdapter;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.util.Log;

import com.viewpagerindicator.TitleProvider;


public class CustomPagerAdapter extends PagerAdapter implements TitleProvider {
    private final Context context;
    private ArrayList<String> departments;
    private ArrayList<ArrayAdapter<String>> arrayAdapters;

    public CustomPagerAdapter(final Context context, ArrayList<String> departments, ArrayList<ArrayAdapter<String>> arrayAdapters) {
        this.context = context;
        this.departments = departments;
        this.arrayAdapters = arrayAdapters;
    }

    @Override
    public int getCount() {
        return departments.size();
    }

    @Override
    public boolean isViewFromObject(final View view, final Object o) {
        return view == o;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ListView listView = new ListView(context);
        listView.setAdapter(arrayAdapters.get(position));
        container.addView(listView, 0);
        return listView;
    }

    @Override
    public void destroyItem(final ViewGroup container, final int position, final Object object) {
        container.removeView((ListView) object);
    }

    public String getTitle(final int position) {
        return departments.get(position);
    }
}
