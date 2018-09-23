package com.deliveryboyapp.net;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.deliveryboyapp.beans.Delivery;

public class DeliveriesDataSourceFactory extends DataSource.Factory <Integer, Delivery> {

    private APIEndPoints mApiEndPoints;
    private MutableLiveData<DeliveriesDataSource> mDataSourceMutableLiveData;

    DeliveriesDataSourceFactory(APIEndPoints apiEndPoints) {
        this.mApiEndPoints = apiEndPoints;
        mDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Delivery> create() {
        DeliveriesDataSource deliveriesDataSource = new DeliveriesDataSource(mApiEndPoints);
        mDataSourceMutableLiveData.postValue(deliveriesDataSource);
        return deliveriesDataSource;
    }

    public MutableLiveData<DeliveriesDataSource> getDataSourceMutableLiveData() {
        return mDataSourceMutableLiveData;
    }
}