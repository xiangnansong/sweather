package com.example.song.sweathor.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.example.song.sweathor.R;

import com.example.song.sweathor.fragment.Page;
import com.example.song.sweathor.fragment.Table;

import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.offers.OffersManager;


/**
 * Created by song on 15-2-14.
 */

public class WeatherActivity extends FragmentActivity {
    private DrawerLayout mDrawer;
    private ListView list;
    private Page page1;
    private Page page2;
    private Page page3;
    private Page page4;
    private Page page5;
    private Table table;
    private String countyCode;
    private ActionBarDrawerToggle toggle;
    private String[] mlist;
    int currentposition;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        list = (ListView) findViewById(R.id.left_drawer);
        mlist = getResources().getStringArray(R.array.list);
        list.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mlist));
        currentposition = 0;
        table = new Table();
        page1 = new Page();
        page2 = new Page();
        page3 = new Page();
        page4 = new Page();
        page5 = new Page();


        AdView adView = new AdView(this, AdSize.FIT_SCREEN);
        LinearLayout adLayout=(LinearLayout)findViewById(R.id.adLayout);
        adLayout.addView(adView);

        manager = getSupportFragmentManager();
        countyCode = getIntent().getStringExtra("county_code");
        Log.e("song", countyCode);
        mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        list.setOnItemClickListener(new DrawerListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        toggle = new ActionBarDrawerToggle(this,
                mDrawer,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                setTitle("");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                setTitle("设置");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawer.setDrawerListener(toggle);
//        manager.beginTransaction().replace(R.id.content_frame, page1).commit();
        Bundle args = new Bundle();
        args.putString("county_code", countyCode);
        args.putInt("currentpage", currentposition);
        table.setArguments(args);
        manager.beginTransaction().replace(R.id.content_frame, table).commit();
    }

    @Override
    protected void onDestroy() {
OffersManager.getInstance(getApplicationContext()).onAppExit();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you
        if (item != null && item.getItemId() == android.R.id.home) {
            if (mDrawer.isDrawerVisible(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
            } else {
                mDrawer.openDrawer(GravityCompat.START);
            }
            return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        toggle.onConfigurationChanged(newConfig);
    }

    public void seleteitem(int position) {
        if (position == 0) {
            if (currentposition != 0) {
                FragmentTransaction transaction = manager.beginTransaction();

                Bundle args = new Bundle();
                args.putString("county_code", countyCode);
                args.putInt("currentpage", currentposition);

                if (!table.isAdded()) {
                    table.setArguments(args);
                    transaction.hide(getFrag()).add(R.id.content_frame, table).commit();
                } else {
                    transaction.hide(getFrag()).show(table).commit();
                }
                currentposition = 0;
            }
        } else if (position == 1) {
            if (currentposition != 1) {
                FragmentTransaction transaction = manager.beginTransaction();
                Bundle args = new Bundle();
                args.putString("county_code", countyCode);
                args.putInt("currentpage", currentposition);

                if (!page1.isAdded()) {
                    page1.setArguments(args);
                    transaction.hide(getFrag()).add(R.id.content_frame, page1).commit();
                } else {
                    transaction.hide(getFrag()).show(page1).commit();
                }
                currentposition = 1;
            }
        } else if (position == 2) {
            if (currentposition != 2) {
                FragmentTransaction transaction = manager.beginTransaction();
                Bundle args = new Bundle();
                args.putString("county_code", countyCode);
                args.putInt("currentpage", currentposition);

                if (!page2.isAdded()) {
                    page2.setArguments(args);
                    transaction.hide(getFrag()).add(R.id.content_frame, page2).commit();
                } else {
                    transaction.hide(getFrag()).show(page2).commit();
                }
                currentposition = 2;
            }
        } else if (position == 3) {
            if (currentposition != 3) {
                FragmentTransaction transaction = manager.beginTransaction();
                Bundle args = new Bundle();
                args.putString("county_code", countyCode);
                args.putInt("currentpage", currentposition);

                if (!page3.isAdded()) {
                    page3.setArguments(args);
                    transaction.hide(getFrag()).add(R.id.content_frame, page3).commit();
                } else {
                    transaction.hide(getFrag()).show(page3).commit();
                }
                currentposition = 3;
            }
        } else if (position == 4) {
            if (currentposition != 4) {
                FragmentTransaction transaction = manager.beginTransaction();
                Bundle args = new Bundle();
                args.putString("county_code", countyCode);
                args.putInt("currentpage", currentposition);

                if (!page4.isAdded()) {
                    page4.setArguments(args);
                    transaction.hide(getFrag()).add(R.id.content_frame, page4).commit();
                } else {
                    transaction.hide(getFrag()).show(page4).commit();
                }
                currentposition = 4;
            }
        } else if (position == 5) {
            if (currentposition != 5) {
                FragmentTransaction transaction = manager.beginTransaction();
                Bundle args = new Bundle();
                args.putString("county_code", countyCode);
                args.putInt("currentpage", currentposition);

                if (!page5.isAdded()) {
                    page5.setArguments(args);
                    transaction.hide(getFrag()).add(R.id.content_frame, page5).commit();
                } else {
                    transaction.hide(getFrag()).show(page5).commit();
                }
                currentposition = 5;
            }
        } else if (position == 6) {
//            Intent intent = new Intent(WeatherActivity.this, ChooseArea.class);
//            startActivity(intent);
//            finish();
            OffersManager.getInstance(this).showOffersWall();
        } else if (position == 7) {
            finish();
        }
        mDrawer.closeDrawer(GravityCompat.START);

    }

    Fragment getFrag() {
        Fragment fragment = new Fragment();
        switch (currentposition) {
            case 0:
                fragment = table;
                break;
            case 1:
                fragment = page1;
                break;
            case 2:
                fragment = page2;
                break;
            case 3:
                fragment = page3;
                break;
            case 4:
                fragment = page4;
                break;
            case 5:
                fragment = page5;
                break;

        }
        return fragment;
    }
    private class DrawerListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            seleteitem(position);
        }
    }


}
