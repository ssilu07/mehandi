package com.android.mehndidesigns.services;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.android.mehndidesigns.R;
import com.onesignal.OSNotificationOpenedResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class OnesignalPush extends Application {

    private static OnesignalPush mInstance;
    public OnesignalPush() {
        mInstance = this;
    }

    @Override
    public  void onCreate() {
        super.onCreate();
        mInstance = this;
        OneSignal.initWithContext(this);
        OneSignal.setAppId("636c3b5d-4c71-4526-b736-bf51b06462ed");
        OneSignal.unsubscribeWhenNotificationsAreDisabled(true);
        OneSignal.setNotificationOpenedHandler(new NotificationHandler());
    }

    class NotificationHandler implements  OneSignal.OSNotificationOpenedHandler{
        @Override
        public void notificationOpened(OSNotificationOpenedResult result) {
            SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.app_name),Context.MODE_PRIVATE);
            SharedPreferences.Editor prefEditor = sp.edit();
            JSONObject data = result.getNotification().getAdditionalData();
            if (data != null && data.has("message")){
                prefEditor.putString("message",data.optString("message"));
                prefEditor.apply();
            }
        }
    }

    public static  synchronized OnesignalPush getmInstance(){
        return mInstance;
    }

}
