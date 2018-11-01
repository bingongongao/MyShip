package com.example.huuph.myship.uis.fragment;


import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.huuph.myship.Manifest;
import com.example.huuph.myship.uis.fragment.FragmentNews;
import com.example.huuph.myship.adapter.PageAdapter;
import com.example.huuph.myship.R;
import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;

public class main_main extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private String[] PERMISSION = {
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
    };

    //test
    String datasent = "gui tu main_main";


    private ActionBarDrawerToggle toggle;
    private PagerAdapter adapter;
    private DrawerLayout drawerLayout;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    Toolbar toolbar;
    private ActionBarDrawerToggle drawerToggle;


    String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_main);

        if (!checkPermission()) {
            return;
        } else {
            initPager();
            setUpActionBar();
            initSliding();
        }


        //nhan thong tin nguoi dung


    }


    public boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISSION) {
                if (checkSelfPermission(p) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(PERMISSION, 0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!checkPermission()) {
            finish();
        }

    }

    private void initPager() {
        adapter = new PageAdapter(getSupportFragmentManager());
        drawerLayout = findViewById(R.id.drawer_layout);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        toolbar = findViewById(R.id.toolbar);
        // main = findViewById(R.id.main);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
        tabLayout.setupWithViewPager(viewPager);

        drawerLayout.addDrawerListener(drawerToggle);

    }

    private void initSliding() {
        toggle = new ActionBarDrawerToggle(this,
                drawerLayout,
                R.string.app_name,
                R.string.app_name);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setUpActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        if (i == 0) {
            FragmentNews.getInstance();
        }
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public String getToken() {
        return token;
    }
}
