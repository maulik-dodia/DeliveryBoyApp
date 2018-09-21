package com.deliveryboyapp.net;

import com.deliveryboyapp.beans.Delivery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIEndPoints {

    @GET("deliveries")
    Call<List<Delivery>> getDeliveries(@Query("offset") int offset,
                                       @Query("limit") int limit);
}