package com.android.mehndidesigns;

import static com.android.mehndidesigns.services.Constant.CONTACT_URL;
import static com.android.mehndidesigns.services.Constant.POLICY_URL;
import static com.android.mehndidesigns.services.Constant.getAdSize;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.android.mehndidesigns.adapter.CategoryAdapter;
import com.android.mehndidesigns.adapter.SliderAdapter;
import com.android.mehndidesigns.model.Design;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, initializationStatus -> {
            Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
            for (String adapterClass : statusMap.keySet()) {
                AdapterStatus status = statusMap.get(adapterClass);
                assert status != null;
            }
           /* FrameLayout adContainerView = findViewById(R.id.adView);
            AdView adView = new AdView(this);
            adView.setAdUnitId(getString(R.string.banner_id));
            adContainerView.addView(adView);
            adView.setAdSize(getAdSize(this));
            adView.loadAd(new AdRequest.Builder().build());
            adContainerView.setVisibility(View.VISIBLE);*/
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        DrawerLayout drawerLayout = findViewById(R.id.drawer);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.app_name,R.string.app_name);
        drawerLayout.addDrawerListener(toggle);toggle.syncState();
        page = findViewById(R.id.my_pager) ;
        TabLayout tab = findViewById(R.id.my_tablayout);
        page.setAdapter(new SliderAdapter(this));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(),2000,3000);
        tab.setupWithViewPager(page,true);
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(new CategoryAdapter(this));
        findViewById(R.id.fav).setOnClickListener(view -> {
            DetailsActivity.mehndi = new Design("Favourite",R.drawable.design);
            startActivity(new Intent(this,DetailsActivity.class));
        });
        NavigationView navigationView = findViewById(R.id.navigationview);
        navigationView.setNavigationItemSelectedListener(item -> {
            drawerLayout.closeDrawer(GravityCompat.START);
            if(item.getItemId() == R.id.share) {
                startActivity(new Intent(Intent.ACTION_SEND)
                        .setType("text/plain")
                        .putExtra(Intent.EXTRA_TITLE,getString(R.string.app_name))
                        .putExtra(Intent.EXTRA_TEXT,getString(R.string.app_name) + "\n\nDownload the app from playstore \uD83D\uDC49 https://play.google.com/store/apps/details?id=" + getPackageName()));
            } else if(item.getItemId() == R.id.policy) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(POLICY_URL)));
            } else if(item.getItemId() == R.id.contact) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(CONTACT_URL)));
            } else if(item.getItemId() == R.id.about) {
                startActivity(new Intent(Intent.ACTION_VIEW).setData(Uri.parse(POLICY_URL)));
            }
            return false;
        });
    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(() -> {
                if (page.getCurrentItem() < 5) {
                    page.setCurrentItem(page.getCurrentItem() + 1);
                } else {
                    page.setCurrentItem(0);
                }
            });
        }
    }

}