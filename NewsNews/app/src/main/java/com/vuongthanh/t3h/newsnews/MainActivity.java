package com.vuongthanh.t3h.newsnews;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;

import com.vuongthanh.t3h.newsnews.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

   // private ActivityMainBinding binding;
    private ActivityMainBinding binding;
    private ActionBarDrawerToggle toggle;// kích vào để đóng mở menu
    private PageAdapter adapter;
    private SearchView searchView;

    private String[] PERMISSIONS = {
            Manifest.permission.INTERNET,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpActionBar();
        initSliding();
        initPager();
        if (checkPermissions() == false) {
            return;
        }
    }

    private void setUpActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initPager() {
        adapter = new PageAdapter(getSupportFragmentManager());
        binding.mainLayout.myViewPager.setAdapter(adapter);
        binding.mainLayout.myViewPager.addOnPageChangeListener(this);
        binding.mainLayout.tabLayout.setupWithViewPager(binding.mainLayout.myViewPager);
    }
    private void initSliding() {
        toggle = new ActionBarDrawerToggle(this,
                binding.drawerLayout,
                R.string.open_menu,
                R.string.close_menu);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();// đồng bộ trạng thái khi đóng mở drawerlayout với navi button
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_main, menu);
        searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                binding.mainLayout.myViewPager.setCurrentItem(0);
                FragmentNews.getInstance().SearchNews(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
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

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String p : PERMISSIONS) {
                int status = checkSelfPermission(p);
                if (status == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(PERMISSIONS, 0);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (checkPermissions() == true) {

        } else {
            finish();
        }
    }
}
