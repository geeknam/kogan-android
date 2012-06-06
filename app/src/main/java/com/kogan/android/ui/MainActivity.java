package com.kogan.android.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.util.Log;
import android.app.ProgressDialog;

import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.viewpagerindicator.TitlePageIndicator;
import roboguice.inject.InjectView;

import com.kogan.android.R;
import com.kogan.android.core.KoganService;
import com.kogan.android.core.Product;
import com.kogan.android.adapters.CustomPagerAdapter;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;


public class MainActivity extends BaseActivity {
    @InjectView(R.id.view_pager_example_pager)
    private ViewPager viewPager;
    
    @InjectView(R.id.view_pager_example_tpi)
    private TitlePageIndicator titlePageIndicator;

    private ArrayList<ArrayAdapter<String>> arrayAdapters = new ArrayList<ArrayAdapter<String>>();
    private Map<String, ArrayList<String>> DEPS = new HashMap<String, ArrayList<String>>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        DEPS.put("televisions", new ArrayList<String>());
        DEPS.put("phones", new ArrayList<String>());
        DEPS.put("cameras", new ArrayList<String>());

        new ProgressTask(MainActivity.this, DEPS).execute();

    }

    private void setupViewPager() {

        for(ArrayList<String> dep : DEPS.values()){
            arrayAdapters.add(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                dep.toArray(new String[dep.size()]))
            );
        }

        viewPager.setAdapter(new CustomPagerAdapter(this, DEPS, arrayAdapters));
        titlePageIndicator.setViewPager(viewPager);
    }

    private class ProgressTask extends AsyncTask<String, Integer, Boolean> {
        private RoboSherlockActivity activity;
        private Map<String, ArrayList<String>> departments;
        private ArrayList<String> slugs;
        private ProgressDialog dialog;

        public ProgressTask(RoboSherlockActivity activity, Map<String, ArrayList<String>> departments) {
            this.activity = activity;
            this.departments = departments;
            this.slugs = new ArrayList<String>(departments.keySet());

        }

        protected void onPreExecute() {
            dialog = ProgressDialog.show(activity, "Please wait...", "Fetching products...");
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            setupViewPager();
            dialog.dismiss();
        }

        protected void onProgressUpdate(Integer... progUpdate) {
            String msg = "Fetching " + this.slugs.get(progUpdate[0]) + "...";
            dialog.setMessage(msg);
         }

        protected Boolean doInBackground(final String... args) {
            int idx = 0;
            for(Map.Entry<String, ArrayList<String>> dep : this.departments.entrySet()){
                publishProgress(idx);
                String param = "&department=" + dep.getKey();
                KoganService service = new KoganService();
                try{
                    List<Product> products = service.getProducts(param);
                    for(Product p : products){
                        dep.getValue().add(p.getTitle());
                        // Log.d("KOGANNNNNNN", p.toString());
                        // Log.d("KOGANNNNNNN", p.getTitle());
                    }
                } catch (IOException ignored) {
                    // Log.d("KOGANNNNNNN", "IOEXCEPTION");
                }
                idx++;
            }

            return true;
        }
    }
}
