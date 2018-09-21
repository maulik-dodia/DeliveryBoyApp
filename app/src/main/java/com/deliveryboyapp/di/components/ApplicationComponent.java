package com.deliveryboyapp.di.components;

import com.deliveryboyapp.activities.BaseActivity;
import com.deliveryboyapp.di.modules.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {ApplicationModule.class})
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);
}