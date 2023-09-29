package com.android.mehndidesigns;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

import static com.android.mehndidesigns.services.Constant.getAdSize;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.android.mehndidesigns.adapter.MehndiAdapter;
import com.android.mehndidesigns.model.Design;
import com.android.mehndidesigns.services.FavouriteList;

import java.util.Map;

public class DetailsActivity extends AppCompatActivity {

    public static Design mehndi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        MobileAds.initialize(this, initializationStatus -> {
            Map<String, AdapterStatus> statusMap = initializationStatus.getAdapterStatusMap();
            for (String adapterClass : statusMap.keySet()) {
                AdapterStatus status = statusMap.get(adapterClass);
                assert status != null;
            }
          /*  FrameLayout adContainerView = findViewById(R.id.adView);
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
            getSupportActionBar().setTitle(mehndi.getTitle());
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(view -> finish());
        ImageView imageView = findViewById(R.id.header);
        imageView.setImageResource(mehndi.getImage());
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        if(mehndi.getTitle().equalsIgnoreCase("Favourite")) {
            Map<String, ?> allEntries = FavouriteList.getAll(this);
            if(allEntries.isEmpty()) {
                makeText(this,"Favourite list is empty",LENGTH_SHORT).show();
            } else {
                recyclerView.setAdapter(new MehndiAdapter(this,"Favourite"));
            }
        } else {
            recyclerView.setAdapter(new MehndiAdapter(this,mehndi.getTitle()));
        }
    }
}