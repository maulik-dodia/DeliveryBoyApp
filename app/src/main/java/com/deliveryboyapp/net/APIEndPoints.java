package com.deliveryboyapp.net;

import com.deliveryboyapp.beans.Delivery;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIEndPoints {

    @GET("deliveries")
    Observable<Response<List<Delivery>>> getDeliveries(@Query("offset") int offset,
                                                       @Query("limit") int limit);
}