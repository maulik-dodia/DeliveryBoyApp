package com.deliveryboyapp.net;

import com.deliveryboyapp.beans.Delivery;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface APIEndPoints {

    @GET("deliveries")
    Call<List<Delivery>> getDeliveries(@QueryMap Map<String, String> options);
}