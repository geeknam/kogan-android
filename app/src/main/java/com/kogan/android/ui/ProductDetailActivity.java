package com.kogan.android.ui;

import android.os.Bundle;
import android.content.Intent;
import android.widget.ImageView;
import android.net.Uri;
import android.util.Log;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.ShareActionProvider;

import android.support.v4.view.ViewPager;
import com.viewpagerindicator.TabPageIndicator;
import roboguice.inject.InjectView;

import com.kogan.android.R;
import com.kogan.android.widget.ribbonmenu.RibbonMenuView;
import com.kogan.android.widget.ribbonmenu.iRibbonMenuCallback;
import com.kogan.android.adapters.ProductDetailAdapter;
import com.kogan.android.core.Product;
import com.kogan.android.widget.lazylist.ImageLoader;
import static com.kogan.android.core.KoganConstants.ROOT_URL;


public class ProductDetailActivity extends BaseActivity implements iRibbonMenuCallback {

    @InjectView(R.id.product_details_pager)
    private ViewPager detailsPager;
    
    @InjectView(R.id.product_details_pager_indicator)
    private TabPageIndicator tabPageIndicator;

    @InjectView(R.id.image)
    private ImageView image;    

    @InjectView(R.id.ribbonMenuView)
    private RibbonMenuView rbmView;

    Product product;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        rbmView.setMenuClickCallback(this);
        rbmView.setMenuItems(R.menu.ribbon_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageLoader imageLoader = new ImageLoader(getApplicationContext());
        product = (Product) getIntent().getSerializableExtra("product");

        imageLoader.DisplayImage(product.getImageUrl(), image);

        detailsPager.setAdapter(new ProductDetailAdapter(this, product));
        tabPageIndicator.setViewPager(detailsPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            rbmView.toggleMenu();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void RibbonMenuItemClick(int itemId) {
        switch (itemId){
            case 0:
                onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getSupportMenuInflater().inflate(R.menu.share_action_provider, menu);

        MenuItem actionItem = menu.findItem(R.id.menu_item_share_action_provider_action_bar);
        ShareActionProvider actionProvider = (ShareActionProvider) actionItem.getActionProvider();
        actionProvider.setShareHistoryFileName(ShareActionProvider.DEFAULT_SHARE_HISTORY_FILE_NAME);
        actionProvider.setShareIntent(createShareIntent());

        return true;
    }

    private Intent createShareIntent() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, ROOT_URL + product.getUrl());
        return shareIntent;
    }

}