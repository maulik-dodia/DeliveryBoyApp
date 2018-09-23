package com.deliveryboyapp.net;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.util.Log;

import com.deliveryboyapp.Constants;
import com.deliveryboyapp.beans.Delivery;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.deliveryboyapp.Constants.FIRST_PAGE;
import static com.deliveryboyapp.Constants.LIMIT;

public class DeliveriesDataSource extends PageKeyedDataSource<Integer, Delivery> {

    private static String TAG = DeliveriesDataSource.class.getSimpleName();

    private APIEndPoints mApiEndPoints;
    private static MutableLiveData<String> mLiveDataStatus;

    DeliveriesDataSource(APIEndPoints apiEndPoints) {
        this.mApiEndPoints = apiEndPoints;
        mLiveDataStatus = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Delivery> callback) {

        mApiEndPoints.getDeliveries(FIRST_PAGE, LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<Delivery>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mLiveDataStatus.postValue(Constants.STR_LOADING);
                    }

                    @Override
                    public void onNext(Response<List<Delivery>> listResponse) {

                        if (listResponse != null) {

                            if (listResponse.raw().cacheResponse() != null) {

                                Log.e(TAG, "init response came from cache");
                                callback.onResult(listResponse.body(), null, FIRST_PAGE + 1);
                            }

                            if (listResponse.raw().networkResponse() != null) {

                                Log.e(TAG, "init response came from server");
                                callback.onResult(listResponse.body(), null, FIRST_PAGE + 1);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLiveDataStatus.postValue(Constants.STR_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Delivery> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Delivery> callback) {

        mApiEndPoints.getDeliveries(params.key, LIMIT)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<List<Delivery>>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mLiveDataStatus.postValue(Constants.STR_LOADING);
                    }

                    @Override
                    public void onNext(Response<List<Delivery>> listResponse) {

                        mLiveDataStatus.postValue(Constants.STR_LOADED);

                        if (listResponse != null) {

                            if (listResponse.raw().cacheResponse() != null) {

                                Log.e(TAG, "response came from cache");
                                callback.onResult(listResponse.body(), params.key + 1);
                            }

                            if (listResponse.raw().networkResponse() != null) {

                                Log.e(TAG, "response came from server");
                                callback.onResult(listResponse.body(), params.key + 1);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        mLiveDataStatus.postValue(Constants.STR_ERROR);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public MutableLiveData<String> getLiveDataStatus() {
        return mLiveDataStatus;
    }
}