package com.android.mehndidesigns;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.android.mehndidesigns.DetailsActivity.mehndi;
import static com.android.mehndidesigns.services.Constant.getAdSize;

import android.Manifest;
import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.AdapterStatus;
import com.android.mehndidesigns.model.Mehndi;
import com.android.mehndidesigns.services.CustomImageView;
import com.android.mehndidesigns.services.FavouriteList;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class PagerActivity extends AppCompatActivity {

    public static int show;
    private boolean rtl = false;
    public static List<Mehndi> mehndiPagerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pager);
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
        ViewPager2 viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new RecyclerView.Adapter<RecyclerView.ViewHolder>() {
            @NonNull
            @Override
            public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new MehndiHolder(LayoutInflater.from(PagerActivity.this).inflate(R.layout.page,parent,false));
            }
            @Override
            public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
                MehndiHolder mehndiHolder = (MehndiHolder) holder;
                try {
                    InputStream ims = getAssets().open("img/" + mehndiPagerList.get(position).getImage());
                    Drawable d = Drawable.createFromStream(ims, null);
                    mehndiHolder.image.setImageDrawable(d);ims.close();
                } catch(IOException ex) {
                    ex.printStackTrace();
                }
                String key;
                if (mehndi.getTitle().equalsIgnoreCase("Favourite")) {
                    key = mehndiPagerList.get(position).getId();
                } else {
                    key = mehndi.getTitle() + mehndiPagerList.get(position).getId();
                }
                if (FavouriteList.isContains(PagerActivity.this, key)) {
                    mehndiHolder.like.setBackgroundResource(R.drawable.liked);
                } else {
                    mehndiHolder.like.setBackgroundResource(R.drawable.like);
                }
                mehndiHolder.share.setOnClickListener(view -> {
                    if (ContextCompat.checkSelfPermission(PagerActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Bitmap bitmap = loadBitmapFromView(mehndiHolder.image);
                        String title = mehndi.getTitle() + "_mehndi_" + mehndiPagerList.get(position).getId();
                        String description = "This is saved from " + getString(R.string.app_name) + " App";
                        String path = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,title,description);
                        try {
                            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                            Uri screenshotUri = Uri.parse(path);
                            sharingIntent.setType("image/jpg");
                            sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
                            sharingIntent.putExtra(Intent.EXTRA_TEXT,getString(R.string.app_name) + "\n\nDownload the app from playstore \uD83D\uDC49 https://play.google.com/store/apps/details?id=" + getPackageName());
                            startActivity(sharingIntent);
                        } catch (Exception e) {
                            makeText(PagerActivity.this,"Error! " + e.getMessage(),LENGTH_SHORT).show();
                        }
                    } else {
                        ActivityCompat.requestPermissions(PagerActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }
                });
                mehndiHolder.wall.setOnClickListener(view -> {
                    if (ContextCompat.checkSelfPermission(PagerActivity.this,Manifest.permission.SET_WALLPAPER) == PackageManager.PERMISSION_GRANTED) {
                        WallpaperManager wallpaperManager = WallpaperManager.getInstance(PagerActivity.this);
                        try {
                            InputStream ims = getAssets().open("img/" + mehndiPagerList.get(position).getImage());
                            Drawable d = Drawable.createFromStream(ims, null);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                wallpaperManager.setBitmap(((BitmapDrawable)d).getBitmap(),null,false,WallpaperManager.FLAG_SYSTEM);
                            } else {
                                wallpaperManager.setBitmap(((BitmapDrawable)d).getBitmap());
                            }
                            makeText(PagerActivity.this,"Home Screen Wallpaper Changed",LENGTH_SHORT).show();
                        } catch (IOException e) {
                            makeText(PagerActivity.this,e.getMessage(),LENGTH_SHORT).show();
                        }
                    } else {
                        makeText(PagerActivity.this,"Accept Permission first",LENGTH_SHORT).show();
                        ActivityCompat.requestPermissions(PagerActivity.this,new String[]{Manifest.permission.SET_WALLPAPER},101);
                    }
                });
                mehndiHolder.save.setOnClickListener(view -> {
                    if (ContextCompat.checkSelfPermission(PagerActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Bitmap bitmap = loadBitmapFromView(mehndiHolder.image);
                        String title = mehndi.getTitle() + "_mehndi_" + mehndiPagerList.get(position).getId();
                        String description = "This is saved from " + getString(R.string.app_name) + " App";
                        String path = MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,title,description);
                        if(path != null) {
                            makeText(PagerActivity.this,"Saved",LENGTH_SHORT).show();
                        } else {
                            makeText(PagerActivity.this,"Error!",LENGTH_SHORT).show();
                        }
                    } else {
                        ActivityCompat.requestPermissions(PagerActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},1);
                    }
                });
                mehndiHolder.favorite.setOnClickListener(view -> {
                    String fkey;
                    if (mehndi.getTitle().equalsIgnoreCase("Favourite")) {
                        fkey = mehndiPagerList.get(position).getId();
                    } else {
                        fkey = mehndi.getTitle() + mehndiPagerList.get(position).getId();
                    }
                    if (FavouriteList.isContains(PagerActivity.this,fkey)) {
                        FavouriteList.remove(PagerActivity.this,fkey);
                        mehndiHolder.like.setBackgroundResource(R.drawable.like);
                    } else {
                        FavouriteList.add(PagerActivity.this,fkey,mehndiPagerList.get(position).getImage());
                        mehndiHolder.like.setBackgroundResource(R.drawable.liked);
                    }
                });
                mehndiHolder.change.setOnClickListener(view -> {
                    if(rtl) {
                        mehndiHolder.image.setScaleX(1);
                        rtl = false;
                    } else {
                        mehndiHolder.image.setScaleX(-1);
                        rtl = true;
                    }
                });
                mehndiHolder.itemView.setOnClickListener(view -> {
                    Dialog dialog = new Dialog(PagerActivity.this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                    dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
                    dialog.setContentView(R.layout.popup);dialog.setCancelable(false);
                    try {
                        InputStream ims = getAssets().open("img/" + mehndiPagerList.get(position).getImage());
                        Drawable d = Drawable.createFromStream(ims, null);
                        ((CustomImageView)dialog.findViewById(R.id.image)).setImageDrawable(d);ims.close();
                    } catch(IOException ex) {
                        ex.printStackTrace();
                    }
                    dialog.findViewById(R.id.canceled).setOnClickListener(v -> dialog.dismiss());
                    dialog.show();
                });
            }
            @Override
            public int getItemCount() {
                return mehndiPagerList.size();
            }
        });
        viewPager.setCurrentItem(show);
    }

    private static class MehndiHolder extends RecyclerView.ViewHolder{
        private final TextView like;
        private final ImageView image;
        private final LinearLayout share;
        private final LinearLayout save;
        private final LinearLayout wall;
        private final LinearLayout favorite;
        private final LinearLayout change;
        public MehndiHolder(@NonNull View itemView) {
            super(itemView);
            like = itemView.findViewById(R.id.like);
            image = itemView.findViewById(R.id.image);
            save = itemView.findViewById(R.id.save);
            wall = itemView.findViewById(R.id.wall);
            share = itemView.findViewById(R.id.share);
            favorite = itemView.findViewById(R.id.fav);
            change = itemView.findViewById(R.id.change);
        }
    }

    private Bitmap loadBitmapFromView(View v) {
        Bitmap b = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);
        v.layout(v.getLeft(), v.getTop(), v.getRight(), v.getBottom());
        v.draw(c);
        return b;
    }

}