package com.gehad.todotask.app;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jakewharton.threetenabp.AndroidThreeTen;

import com.gehad.todotask.BuildConfig;
import timber.log.Timber;

public class TodoApp extends Application {

    private ApplicationComponent appComponent;

    public static ApplicationComponent getAppComponent(Context context) {
        return ((TodoApp) context.getApplicationContext()).appComponent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
     /*   if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);*/

        AndroidThreeTen.init(this);
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
            Timber.plant(new Timber.DebugTree());
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        // init dagger appComponent
        this.appComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
    public static TodoApp newInstance() {
        return new TodoApp();
    }

    public CollectionReference getTasksColliction() {
        return  FirebaseFirestore.getInstance().collection("Tasks");
    }
}
