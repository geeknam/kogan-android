package com.kogan.android.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.app.ActionBar;
import com.kogan.android.R;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitleProvider;
import roboguice.inject.InjectView;

import com.kogan.android.core.KoganService;
import com.kogan.android.core.Product;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import java.io.IOException;
import android.os.AsyncTask;
import android.widget.Toast;
import android.util.Log;
import android.app.ProgressDialog;
import static com.kogan.android.core.KoganConstants.URL_PRODUCT;

public class MainActivity extends RoboSherlockActivity implements ActionBar.OnNavigationListener{
    @InjectView(R.id.view_pager_example_pager)
    private ViewPager viewPager;
    
    @InjectView(R.id.view_pager_example_tpi)
    private TitlePageIndicator titlePageIndicator;

    private ArrayList<ArrayAdapter<String>> arrayAdapters = new ArrayList<ArrayAdapter<String>>();

    private String[] departments;
    private Map<String, ArrayList<String>> DEPS = new HashMap<String, ArrayList<String>>();


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_example);

        DEPS.put("televisions", new ArrayList<String>());
        DEPS.put("phones", new ArrayList<String>());
        DEPS.put("cameras", new ArrayList<String>());

        new ProgressTask(MainActivity.this, DEPS).execute();

        departments = getResources().getStringArray(R.array.departments);
        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.departments, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Search")
            .setIcon(R.drawable.ic_search)
            .setActionView(R.layout.collapsible_edittext)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        return true;
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

    
    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return true;
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
            Log.d("KOGANNNNNNN", "POSITION: " + position);
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
