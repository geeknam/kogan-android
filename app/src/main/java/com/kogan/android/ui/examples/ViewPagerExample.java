package com.kogan.android.ui.examples;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;
import com.actionbarsherlock.app.ActionBar;
import com.kogan.android.R;
import com.viewpagerindicator.TitlePageIndicator;
import com.viewpagerindicator.TitleProvider;
import roboguice.inject.InjectView;

import com.kogan.android.core.KoganService;
import com.kogan.android.core.Product;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import android.os.AsyncTask;
import android.widget.Toast;
import android.util.Log;
import static com.kogan.android.core.KoganConstants.URL_PRODUCT;

public class ViewPagerExample extends RoboSherlockActivity implements ActionBar.OnNavigationListener{
    @InjectView(R.id.view_pager_example_pager)
    private ViewPager viewPager;
    
    @InjectView(R.id.view_pager_example_tpi)
    private TitlePageIndicator titlePageIndicator;

    private ArrayAdapter<String> countriesAdapter;
    private ArrayAdapter<String> statesAdapter;
    private String[] TELEVISIONS = null;
    private static final String[] STATES = {"Illinois", "Kentucky", "Maine", "Florida"};

    private String[] departments;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager_example);

        new ProgressTask(ViewPagerExample.this).execute();
        
        departments = getResources().getStringArray(R.array.departments);
        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.departments, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setListNavigationCallbacks(list, this);
    }

    private void setupViewPager() {

        countriesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, TELEVISIONS);
        statesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, STATES);

        viewPager.setAdapter(new CustomPagerAdapter(this));
        titlePageIndicator.setViewPager(viewPager);
    }

    private class ProgressTask extends AsyncTask<String, Void, Boolean> {
        private RoboSherlockActivity activity;
        private Context context;

        public ProgressTask(RoboSherlockActivity activity) {
            this.activity = activity;
            context = activity;
        }

        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            setupViewPager();
        }

        protected Boolean doInBackground(final String... args) {
            String url = URL_PRODUCT + "&department=televisions";
            KoganService service = new KoganService();
            List<String> titles = new ArrayList<String>();
            try{
                List<Product> products = service.getProducts(url);
                for(Product p : products){
                    titles.add(p.getTitle());
                    Log.d("KOGANNNNNNN", p.toString());
                    Log.d("KOGANNNNNNN", p.getTitle());
                }
            } catch (IOException ignored) {
                Log.d("KOGANNNNNNN", "IOEXCEPTION");
            }

            TELEVISIONS = new String[titles.size()];
            TELEVISIONS = titles.toArray(TELEVISIONS);

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
            return 2;
        }

        @Override
        public boolean isViewFromObject(final View view, final Object o) {
            return view == o;
        }

        @Override
        public Object instantiateItem(final ViewGroup container, final int position) {
            final ListView listView = new ListView(context);
            switch (position) {
                case 0:
                    listView.setAdapter(countriesAdapter);
                    break;
                case 1:
                    listView.setAdapter(statesAdapter);
                    break;
            }
            container.addView(listView, position);
            return listView;
        }

        @Override
        public void destroyItem(final ViewGroup container, final int position, final Object object) {
            container.removeView((ListView) object);
        }

        public String getTitle(final int position) {
            if (position == 0) {
                return "Televisions";
            } else if (position == 1) {
                return "States";
            } else {
                return "Unknown";
            }
        }
       
    }
}
