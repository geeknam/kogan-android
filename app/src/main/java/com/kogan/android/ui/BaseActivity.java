package com.kogan.android.ui;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.os.Bundle;

import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Menu;

import com.kogan.android.R;
// import com.kogan.android.widget.amazinglist.*;
import java.util.HashMap;


public class BaseActivity extends RoboSherlockActivity implements ActionBar.OnNavigationListener{

    public HashMap<String, String> departmentsMap = new HashMap<String, String>();
    public AmazingListView lsProduct;
    public ProductAdapter adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        String[] departments = getResources().getStringArray(R.array.departments);
        String[] department_slugs = getResources().getStringArray(R.array.department_slugs);

        for(int i=0; i<departments.length; i++){
            departmentsMap.put(department_slugs[i], departments[i]);
        }

        Context context = getSupportActionBar().getThemedContext();
        ArrayAdapter<CharSequence> list = ArrayAdapter.createFromResource(context, R.array.departments, R.layout.sherlock_spinner_item);
        list.setDropDownViewResource(R.layout.sherlock_spinner_dropdown_item);

        getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
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

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        return true;
    }

    // class ProductAdapter extends AmazingAdapter {

    // }

}