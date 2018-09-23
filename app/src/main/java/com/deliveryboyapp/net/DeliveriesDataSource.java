package com.deliveryboyapp.net;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.deliveryboyapp.Constants;
import com.deliveryboyapp.beans.Delivery;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static com.deliveryboyapp.Constants.FIRST_PAGE;
import static com.deliveryboyapp.Constants.LIMIT;

public class DeliveriesDataSource extends PageKeyedDataSource<Integer, Delivery> {

    private APIEndPoints mApiEndPoints;
    private static MutableLiveData<String> mLiveDataStatus;

    DeliveriesDataSource(APIEndPoints apiEndPoints) {
        this.mApiEndPoints = apiEndPoints;
        mLiveDataStatus = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Delivery> callback) {

        mApiEndPoints.getDeliveries(FIRST_PAGE, LIMIT)
                .subscribe(new Observer<List<Delivery>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mLiveDataStatus.postValue(Constants.STR_LOADING);
                    }

                    @Override
                    public void onNext(List<Delivery> deliveries) {
                        if (deliveries != null) {
                            callback.onResult(deliveries, null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        /*mApiEndPoints.getDeliveries(FIRST_PAGE, LIMIT)
                .enqueue(new Callback<List<Delivery>>() {

                    @Override
                    public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> response) {

                        if (response.body() != null) {
                            callback.onResult(response.body(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Delivery>> call, Throwable t) {

                    }
                });*/
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Delivery> callback) {

        /*mApiEndPoints.getDeliveries(params.key, LIMIT)
                .enqueue(new Callback<List<Delivery>>() {

                    @Override
                    public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> response) {

                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;

                        if (response.body() != null) {
                            callback.onResult(response.body(), adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Delivery>> call, Throwable t) {

                    }
                });*/
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Delivery> callback) {

        mApiEndPoints.getDeliveries(FIRST_PAGE, LIMIT)
                .subscribe(new Observer<List<Delivery>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mLiveDataStatus.postValue(Constants.STR_LOADING);
                    }

                    @Override
                    public void onNext(List<Delivery> deliveries) {

                        mLiveDataStatus.postValue(Constants.STR_LOADED);

                        if (deliveries != null) {
                            callback.onResult(deliveries, params.key + 1);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        /*mApiEndPoints.getDeliveries(params.key, LIMIT)
                .enqueue(new Callback<List<Delivery>>() {

                    @Override
                    public void onResponse(Call<List<Delivery>> call, Response<List<Delivery>> response) {

                        if (response.body() != null) {
                            callback.onResult(response.body(), params.key + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Delivery>> call, Throwable t) {

                    }
                });*/
    }

    public MutableLiveData<String> getLiveDataStatus() {
        return mLiveDataStatus;
    }
}