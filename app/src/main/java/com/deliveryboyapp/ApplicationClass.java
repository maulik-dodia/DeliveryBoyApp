package com.deliveryboyapp;

import android.app.Application;

import com.deliveryboyapp.di.components.ApplicationComponent;
import com.deliveryboyapp.di.components.DaggerApplicationComponent;
import com.deliveryboyapp.di.modules.ApplicationModule;

public class ApplicationClass extends Application {

    private ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }
}