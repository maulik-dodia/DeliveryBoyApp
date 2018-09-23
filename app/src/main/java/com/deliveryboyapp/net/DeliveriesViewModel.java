package com.deliveryboyapp.net;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.deliveryboyapp.beans.Delivery;

import static com.deliveryboyapp.Constants.LIMIT;

public class DeliveriesViewModel extends ViewModel {

    public LiveData<PagedList<Delivery>> itemPagedList;
    private LiveData<PageKeyedDataSource<Integer, Delivery>> liveDataSource;

    public DeliveriesViewModel(APIEndPoints apiEndPoints) {

        DeliveriesDataSourceFactory deliveriesDataSourceFactory = new DeliveriesDataSourceFactory(apiEndPoints);

        liveDataSource = deliveriesDataSourceFactory.getDataSourceMutableLiveData();

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(LIMIT).build();

        itemPagedList = (new LivePagedListBuilder(deliveriesDataSourceFactory, pagedListConfig))
                .build();
    }
}