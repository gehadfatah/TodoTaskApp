package com.gehad.todotask.app;

import android.app.Application;
import android.content.Context;


public class todoApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }
    public Context getSnashContext() {

        return mContext;
    }

    public static todoApplication newInstance() {
        return new todoApplication();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
       // MultiDex.install(this);
    }

}
