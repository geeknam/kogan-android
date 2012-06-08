package com.kogan.android.ui;

import android.os.Bundle;

import com.github.rtyley.android.sherlock.roboguice.activity.RoboSherlockActivity;

import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Menu;

import com.kogan.android.R;


public class BaseActivity extends RoboSherlockActivity{

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Search")
            .setIcon(R.drawable.ic_search)
            .setActionView(R.layout.collapsible_edittext)
            .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);

        return true;
    }

}