package com.deliveryboyapp.net;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import javax.inject.Inject;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private APIEndPoints mApiEndPoints;

    @Inject
    public ViewModelFactory(APIEndPoints apiEndPoints) {
        this.mApiEndPoints = apiEndPoints;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if (modelClass.isAssignableFrom(DeliveriesViewModel.class)) {
            return (T) new DeliveriesViewModel(mApiEndPoints);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}