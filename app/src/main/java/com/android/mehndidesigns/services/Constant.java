package com.android.mehndidesigns.services;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.DisplayMetrics;
import android.view.Display;

import com.google.android.gms.ads.AdSize;

public class Constant {
    public static int interstitialLoadCount = 0;
    public static String ABOUT_URL = "https://google.com";
    public static String POLICY_URL = "https://google.com";
    public static String CONTACT_URL = "https://google.com";
    public static boolean isConnected(Context ctx) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
    public static AdSize getAdSize(Activity ctx) {
        Display display = ctx.getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;
        int adWidth = (int) (widthPixels / density);
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(ctx, adWidth);
    }
}
