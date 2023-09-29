package com.android.mehndidesigns.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.Map;

public class FavouriteList {

    private static SharedPreferences sp = null;
    private static final String PREFS_NAME = "MEHNDI_DESIGN";

    public FavouriteList() {
        super();
    }

    public static void add(Context context, String key, String value) {
        sp = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
        Toast.makeText(context,"Added to Favourite",Toast.LENGTH_SHORT).show();
    }

    public static void remove(Context context, String key) {
        sp = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.remove(key);
        edit.apply();
        Toast.makeText(context,"Remove from Favourite",Toast.LENGTH_SHORT).show();
    }

    public static boolean isContains(Context context, String key) {
        sp = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    public static Map<String, ?> getAll(Context context){
        sp = context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
        return sp.getAll();
    }
}
