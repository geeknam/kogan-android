package com.kogan.android.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.ArrayAdapter;
import android.util.Log;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.kogan.android.R;
import com.kogan.android.adapters.CustomPagerAdapter;
import com.kogan.android.core.KoganService;
import com.kogan.android.core.Product;
import com.viewpagerindicator.TitlePageIndicator;
import roboguice.inject.InjectView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends BaseActivity {
    @InjectView(R.id.view_pager_example_pager)
    private ViewPager viewPager;
    
    @InjectView(R.id.view_pager_example_tpi)
    private TitlePageIndicator titlePageIndicator;

    private ArrayList<ArrayAdapter<String>> arrayAdapters = new ArrayList<ArrayAdapter<String>>();
    private ArrayList<String> DEPS = new ArrayList<String>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        DEPS.add("televisions");
        DEPS.add("phones");
        DEPS.add("cameras");
        DEPS.add("audio");

        setupViewPager();
    }

    private void setupViewPager() {

        for(String dep : DEPS){
            arrayAdapters.add(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1));
        }

        viewPager.setAdapter(new CustomPagerAdapter(this, DEPS, arrayAdapters));
        titlePageIndicator.setViewPager(viewPager);
        titlePageIndicator.setOnPageChangeListener(new DepartmentPageChangeListener());

        new ProgressTask(MainActivity.this, DEPS.get(0)).execute();

    }

    private class DepartmentPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            if(arrayAdapters.get(position).getCount() < 1){
                new ProgressTask(MainActivity.this, DEPS.get(position)).execute();
            }
        }
    }

    private class ProgressTask extends AsyncTask<String, Integer, Boolean> {
        private RoboSherlockActivity activity;
        private String slug;
        private List<Product> data;
        private ProgressDialog dialog;

        public ProgressTask(RoboSherlockActivity activity, String slug) {
            this.activity = activity;
            this.slug = slug;
        }

        protected void onPreExecute() {
            dialog = ProgressDialog.show(activity, "Please wait...", "Fetching " + slug + "...");
        }

        // TODO: Make this more elegant
        @Override
        protected void onPostExecute(final Boolean success) {
            for(int i=0; i<DEPS.size(); i++){
                if(DEPS.get(i).equals(slug)){
                    for(Product p : data){
                        arrayAdapters.get(i).add(p.getTitle());
                    }
                    arrayAdapters.get(i).notifyDataSetChanged();
                }
            }
            dialog.dismiss();
        }

        protected Boolean doInBackground(final String... args) {
            String param = "&department=" + slug;
            KoganService service = new KoganService();
            try{
                data = service.getProducts(param);
            } catch (IOException ignored) {
                Log.d("KOGANNNNNNN", "IOEXCEPTION");
            }

            return true;
        }
    }
}
