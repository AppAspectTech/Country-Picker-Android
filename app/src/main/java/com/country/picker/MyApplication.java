package com.country.picker;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.text.TextUtils;
import androidx.multidex.MultiDex;


public class MyApplication extends Application {

    public static final String APP_TAG = MyApplication.class.getSimpleName();
    private static MyApplication mInstance;

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance=this;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
