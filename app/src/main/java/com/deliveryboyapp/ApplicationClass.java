package com.deliveryboyapp;

import android.app.Application;

import com.deliveryboyapp.di.components.DaggerApplicationComponent;
import com.deliveryboyapp.di.modules.ApplicationModule;

public class ApplicationClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule()).build();
    }
}