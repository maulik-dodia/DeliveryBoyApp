package com.deliveryboyapp.net;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.deliveryboyapp.beans.Delivery;

import static com.deliveryboyapp.Constants.LIMIT;

public class DeliveriesViewModel extends ViewModel {

    private DeliveriesDataSourceFactory mDeliveriesDataSourceFactory;

    private LiveData<String> mLiveDataStatus;
    private LiveData<PagedList<Delivery>> mLiveDataPagedList;

    DeliveriesViewModel(APIEndPoints apiEndPoints) {

        mLiveDataStatus = new MutableLiveData<>();
        mDeliveriesDataSourceFactory = new DeliveriesDataSourceFactory(apiEndPoints);

        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(LIMIT).build();

        mLiveDataPagedList = (new LivePagedListBuilder(mDeliveriesDataSourceFactory, pagedListConfig))
                .build();

        mLiveDataStatus = Transformations
                .switchMap(mDeliveriesDataSourceFactory.getDataSourceMutableLiveData(), DeliveriesDataSource::getLiveDataStatus);
    }

    public LiveData<String> getLiveDataStatus() {
        return mLiveDataStatus;
    }

    public LiveData<PagedList<Delivery>> getListLiveData() {
        return mLiveDataPagedList;
    }
}