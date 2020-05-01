package com.medparliament;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.crashlytics.android.Crashlytics;
import com.medparliament.Internet.NewModel.Result;

import io.fabric.sdk.android.Fabric;


public class MyApplication extends Application {
  public static Result result;


    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());
        Fresco.initialize(this);
        // Required initialization logic here!
    }
}
