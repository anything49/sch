package com.project.schedulerapplication.core;

import android.app.Application;

import com.beardedhen.androidbootstrap.TypefaceProvider;


public class ScheduleApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        TypefaceProvider.registerDefaultIconSets();
    }
}
