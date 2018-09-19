package com.deliveryboyapp.net;

import com.deliveryboyapp.beans.Delivery;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface APIEndPoints {

    @GET("deliveries")
    Call<List<Delivery>> getDeliveries();
}