package com.kogan.android.ui;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;

import android.support.v4.view.ViewPager;
import android.util.Log;

import com.actionbarsherlock.app.ActionBar;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.kogan.android.R;
import com.kogan.android.adapters.CategoryAdapter;
import com.kogan.android.core.KoganService;
import com.kogan.android.core.Product;
import com.kogan.android.core.Category;
import com.viewpagerindicator.TitlePageIndicator;
import roboguice.inject.InjectView;
import roboguice.inject.InjectResource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kogan.android.adapters.ProductAdapter;


public class MainActivity extends BaseActivity implements ActionBar.OnNavigationListener{
    @InjectView(R.id.view_pager_example_pager)
    private ViewPager viewPager;
    
    @InjectView(R.id.view_pager_example_tpi)
    private TitlePageIndicator titlePageIndicator;


    @InjectResource(R.array.departments) String[] departments;
    @InjectResource(R.array.department_slugs) String[] department_slugs;
    private EditText search;
    private static LayoutInflater inflater = null;

    private ArrayList<ProductAdapter> arrayAdapters = new ArrayList<ProductAdapter>();
    public String department_slug;
    public List<Category> categories = new ArrayList<Category>();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.departments, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setListNavigationCallbacks(list, this);

        setContentView(R.layout.main);

        inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        search = (EditText) inflater.inflate(R.layout.collapsible_edittext, null);

        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // performSearch();
                    return true;
                }
                return false;
            }
        });

        department_slug = department_slugs[0];
        new GetCategoriesTask(MainActivity.this).execute();

    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        department_slug = department_slugs[itemPosition];
        new GetCategoriesTask(MainActivity.this).execute();
        return true;
    }

    private void setupViewPager() {

        for(Category cat : categories){
            arrayAdapters.add(new ProductAdapter(this, cat.getSlug()));
        }

        viewPager.setAdapter(new CategoryAdapter(this, department_slug, arrayAdapters));
        titlePageIndicator.setViewPager(viewPager);
        titlePageIndicator.setOnPageChangeListener(new DepartmentPageChangeListener());

        new ProgressTask(MainActivity.this, categories.get(0).getSlug()).execute();

    }

    private class DepartmentPageChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            if(arrayAdapters.get(position).getCount() < 1){
                new ProgressTask(MainActivity.this, categories.get(position).getSlug()).execute();
            }
        }
    }

    private class ProgressTask extends AsyncTask<String, Integer, Boolean> {
        private RoboSherlockActivity activity;
        private String category_slug;
        private List<Product> data;
        private ProgressDialog dialog;

        public ProgressTask(RoboSherlockActivity activity, String slug) {
            this.activity = activity;
            this.category_slug = slug;
        }

        protected void onPreExecute() {
            dialog = ProgressDialog.show(activity, "Please wait...", "Fetching " + category_slug + "...");
        }

        // TODO: Make this more elegant
        @Override
        protected void onPostExecute(final Boolean success) {
            for(int i=0; i<categories.size(); i++){
                if(categories.get(i).getSlug().equals(category_slug)){
                    for(Product p : data){
                        arrayAdapters.get(i).add(p);
                    }
                    arrayAdapters.get(i).notifyDataSetChanged();
                }
            }
            dialog.dismiss();
        }

        protected Boolean doInBackground(final String... args) {
            KoganService service = new KoganService();
            try{
                data = service.getProductsFor(department_slug, category_slug);
            } catch (IOException ignored) {
                Log.d("KOGANNNNNNN", "IOEXCEPTION");
            }

            return true;
        }
    }


    private class GetCategoriesTask extends AsyncTask<String, Integer, Boolean> {
        private RoboSherlockActivity activity;
        private ProgressDialog dialog;

        public GetCategoriesTask(RoboSherlockActivity activity) {
            this.activity = activity;
        }

        protected void onPreExecute() {
            dialog = ProgressDialog.show(activity, "Please wait...", "Fetching categories for " + department_slug);
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            setupViewPager();
            dialog.dismiss();
        }

        protected Boolean doInBackground(final String... args) {
            KoganService service = new KoganService();
            try{
                categories = service.getCategoriesForDepartment(department_slug);
            } catch (IOException ignored) {
                Log.d("KOGANNNNNNN", "IOEXCEPTION");
            }

            return true;
        }
    }
}
