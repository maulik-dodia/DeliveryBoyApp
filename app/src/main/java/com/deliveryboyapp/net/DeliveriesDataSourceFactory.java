package com.deliveryboyapp.net;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.deliveryboyapp.beans.Delivery;

public class DeliveriesDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Delivery>> dataSourceMutableLiveData = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Delivery> create() {

        DeliveriesDataSource deliveriesDataSource = new DeliveriesDataSource();

        dataSourceMutableLiveData.postValue(deliveriesDataSource);

        return deliveriesDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Delivery>> getDataSourceMutableLiveData() {
        return dataSourceMutableLiveData;
    }
}