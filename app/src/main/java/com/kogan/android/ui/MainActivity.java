package com.kogan.android.ui;

import android.content.Context;
import android.os.Bundle;
import android.os.AsyncTask;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.util.Log;
import android.app.ProgressDialog;

import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitleProvider;
import roboguice.inject.InjectView;

import com.kogan.android.R;
import com.kogan.android.core.KoganService;
import com.kogan.android.core.Product;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;

import static com.kogan.android.core.KoganConstants.URL_PRODUCT;

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

        viewPager.setAdapter(new CustomPagerAdapter(this));
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
                String url = URL_PRODUCT + "&department=" + dep.getKey();
                KoganService service = new KoganService();
                try{
                    List<Product> products = service.getProducts(url);
                    for(Product p : products){
                        dep.getValue().add(p.getTitle());
                        Log.d("KOGANNNNNNN", p.toString());
                        Log.d("KOGANNNNNNN", p.getTitle());
                    }
                } catch (IOException ignored) {
                    Log.d("KOGANNNNNNN", "IOEXCEPTION");
                }
                idx++;
            }

            return true;
        }
    }

    private class CustomPagerAdapter extends PagerAdapter implements TitleProvider {
        private final Context context;

        private CustomPagerAdapter(final Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return DEPS.size();
        }

        @Override
        public boolean isViewFromObject(final View view, final Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final ListView listView = new ListView(context);
            listView.setAdapter(arrayAdapters.get(position));
            container.addView(listView, position);
            return listView;
        }

        @Override
        public void destroyItem(final ViewGroup container, final int position, final Object object) {
            container.removeView((ListView) object);
        }

        public String getTitle(final int position) {
            List<String> list = new ArrayList<String>(DEPS.keySet());
            return list.get(position);
        }

    }
}
