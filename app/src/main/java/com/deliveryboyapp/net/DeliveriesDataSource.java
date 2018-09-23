package com.deliveryboyapp.net;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.deliveryboyapp.beans.Delivery;
import com.deliveryboyapp.net.APIEndPoints;
import com.deliveryboyapp.net.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.deliveryboyapp.Constants.FIRST_PAGE;
import static com.deliveryboyapp.Constants.LIMIT;

public class DeliveriesDataSource extends PageKeyedDataSource<Integer, Delivery> {

    private APIEndPoints mApiEndPoints;

    DeliveriesDataSource(APIEndPoints apiEndPoints) {
        this.mApiEndPoints = apiEndPoints;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Delivery> callback) {

        mApiEndPoints.getDeliveries(FIRST_PAGE, LIMIT)
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
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Delivery> callback) {

        mApiEndPoints.getDeliveries(params.key, LIMIT)
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
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Delivery> callback) {

        mApiEndPoints.getDeliveries(params.key, LIMIT)
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
                });
    }
}