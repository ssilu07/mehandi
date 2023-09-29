package com.android.mehndidesigns.adapter;

import static com.android.mehndidesigns.DetailsActivity.mehndi;
import static com.android.mehndidesigns.services.Constant.interstitialLoadCount;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
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
import com.android.mehndidesigns.MehndiList;
import com.android.mehndidesigns.PagerActivity;
import com.android.mehndidesigns.R;
import com.android.mehndidesigns.model.Mehndi;
import com.android.mehndidesigns.services.FavouriteList;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressLint("RecyclerView")
public class MehndiAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context ctx;
    private final List<Mehndi> mehndiList;
    private InterstitialAd mInterstitialAd;

    public MehndiAdapter(Context ctx,String cat) {
        this.ctx = ctx;
        LoadInterstitialAds();
        this.mehndiList = new ArrayList<>();
        String[] data = new String[0];
        if(cat.equalsIgnoreCase("Arm")) {
            data = new MehndiList().getArm();
        } else if(cat.equalsIgnoreCase("Back Hand")) {
            data = new MehndiList().getBack();
        } else if(cat.equalsIgnoreCase("Bridal")) {
            data = new MehndiList().getBridal();
        } else if(cat.equalsIgnoreCase("Casual")) {
            data = new MehndiList().getCasual();
        } else if(cat.equalsIgnoreCase("Eid")) {
            data = new MehndiList().getEid();
        } else if(cat.equalsIgnoreCase("Finger")) {
            data = new MehndiList().getFinger();
        } else if(cat.equalsIgnoreCase("Foot")) {
            data = new MehndiList().getFeet();
        } else if(cat.equalsIgnoreCase("Front Hand")) {
            data = new MehndiList().getFront();
        } else if(cat.equalsIgnoreCase("Gol Tikki")) {
            data = new MehndiList().getGol();
        } else if(cat.equalsIgnoreCase("Others")) {
            data = new MehndiList().getOther();
        } else if(cat.equalsIgnoreCase("Special")) {
            data = new MehndiList().getSpecial();
        }
        if(data.length != 0) {
            for (int i = 0; i < data.length; i++) {
                mehndiList.add(new Mehndi(String.valueOf(i),data[i]));
            }
        } else {
            Map<String, ?> allEntries = FavouriteList.getAll(ctx);
            for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
                mehndiList.add(new Mehndi(entry.getKey(),entry.getValue().toString()));
            }
        }
        Collections.shuffle(mehndiList);
        PagerActivity.mehndiPagerList = mehndiList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MehndiHolder(LayoutInflater.from(ctx).inflate(R.layout.mehndi,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MehndiHolder mehndiHolder = (MehndiHolder) holder;
        try {
            InputStream ims = ctx.getAssets().open("img/" + mehndiList.get(position).getImage());
            Drawable d = Drawable.createFromStream(ims, null);
            mehndiHolder.image.setImageDrawable(d);ims.close();
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        String key;
        if (mehndi.getTitle().equalsIgnoreCase("Favourite")) {
            key = mehndiList.get(position).getId();
        } else {
            key = mehndi.getTitle() + mehndiList.get(position).getId();
        }
        if (FavouriteList.isContains(ctx,key)) {
            mehndiHolder.like.setBackgroundResource(R.drawable.liked);
        } else {
            mehndiHolder.like.setBackgroundResource(R.drawable.like);
        }
        mehndiHolder.like.setOnClickListener(view -> {
            String fkey;
            if (mehndi.getTitle().equalsIgnoreCase("Favourite")) {
                fkey = mehndiList.get(position).getId();
            } else {
                fkey = mehndi.getTitle() + mehndiList.get(position).getId();
            }
            if (FavouriteList.isContains(ctx,fkey)) {
                FavouriteList.remove(ctx,fkey);
                mehndiHolder.like.setBackgroundResource(R.drawable.like);
            } else {
                FavouriteList.add(ctx,fkey,mehndiList.get(position).getImage());
                mehndiHolder.like.setBackgroundResource(R.drawable.liked);
            }
        });
        /*mehndiHolder.itemView.setOnClickListener(view -> {
            if(mInterstitialAd != null) {
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent();
                        LoadInterstitialAds();
                        PagerActivity.show = position;
                        ctx.startActivity(new Intent(ctx, PagerActivity.class));
                    }
                    @Override
                    public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {
                        super.onAdFailedToShowFullScreenContent(adError);
                        PagerActivity.show = position;
                        ctx.startActivity(new Intent(ctx, PagerActivity.class));
                    }
                });
                mInterstitialAd.show((Activity) ctx);
            } else {
                PagerActivity.show = position;
                ctx.startActivity(new Intent(ctx, PagerActivity.class));
            }
        });*/

        mehndiHolder.itemView.setOnClickListener(view -> {
            PagerActivity.show = position;
            ctx.startActivity(new Intent(ctx, PagerActivity.class));
        });
    }

    @Override
    public int getItemCount() {
        return mehndiList.size();
    }

    private static class MehndiHolder extends RecyclerView.ViewHolder{
        private final TextView like;
        private final ImageView image;
        public MehndiHolder(@NonNull View itemView) {
            super(itemView);
            like = itemView.findViewById(R.id.like);
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
