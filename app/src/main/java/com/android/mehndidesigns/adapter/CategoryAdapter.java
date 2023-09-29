package com.android.mehndidesigns.adapter;

import static com.android.mehndidesigns.services.Constant.interstitialLoadCount;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.android.mehndidesigns.DetailsActivity;
import com.android.mehndidesigns.model.Design;
import com.android.mehndidesigns.R;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("RecyclerView")
public class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context ctx;
    private final List<Design> mehndiList;
    private InterstitialAd mInterstitialAd;

    public CategoryAdapter(Context ctx) {
        this.ctx = ctx;
        LoadInterstitialAds();
        this.mehndiList = new ArrayList<>();
        mehndiList.add(new Design("Arm", R.drawable.arm));
        mehndiList.add(new Design("Back Hand",R.drawable.back));
        mehndiList.add(new Design("Bridal",R.drawable.bridal));
        mehndiList.add(new Design("Casual",R.drawable.casual));
        mehndiList.add(new Design("Eid",R.drawable.eid));
        mehndiList.add(new Design("Finger",R.drawable.finger));
        mehndiList.add(new Design("Foot",R.drawable.leg));
        mehndiList.add(new Design("Front Hand",R.drawable.front));
        mehndiList.add(new Design("Gol Tikki",R.drawable.gol));
        mehndiList.add(new Design("Others",R.drawable.other));
        mehndiList.add(new Design("Special",R.drawable.special));
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryHolder(LayoutInflater.from(ctx).inflate(R.layout.category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CategoryHolder categoryHolder = (CategoryHolder) holder;
        categoryHolder.title.setText(mehndiList.get(position).getTitle());
        categoryHolder.image.setImageResource(mehndiList.get(position).getImage());
        /*categoryHolder.itemView.setOnClickListener(view -> {
            if(mInterstitialAd != null) {
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        LoadInterstitialAds();
                        DetailsActivity.mehndi = mehndiList.get(position);
                        ctx.startActivity(new Intent(ctx, DetailsActivity.class));
                    }
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        DetailsActivity.mehndi = mehndiList.get(position);
                        ctx.startActivity(new Intent(ctx, DetailsActivity.class));
                    }
                });
                mInterstitialAd.show((Activity) ctx);
            } else {
                DetailsActivity.mehndi = mehndiList.get(position);
                ctx.startActivity(new Intent(ctx, DetailsActivity.class));
            }
        });*/
        //this is delete when use ads
        categoryHolder.itemView.setOnClickListener(view ->{
            DetailsActivity.mehndi = mehndiList.get(position);
            ctx.startActivity(new Intent(ctx, DetailsActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return mehndiList.size();
    }

    private static class CategoryHolder extends RecyclerView.ViewHolder{
        private final TextView title;
        private final ImageView image;
        public CategoryHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
        }
    }

    private void LoadInterstitialAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(ctx, ctx.getString(R.string.interstitial_id), adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                if(interstitialLoadCount < 10) {
                    interstitialLoadCount++;
                    LoadInterstitialAds();
                }
            }
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                super.onAdLoaded(interstitialAd);
                mInterstitialAd = interstitialAd;
            }
        });
    }
}
