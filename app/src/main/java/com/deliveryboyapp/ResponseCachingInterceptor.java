package com.deliveryboyapp;

import android.content.Context;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ResponseCachingInterceptor implements Interceptor {

    private Context mContext;

    public ResponseCachingInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        return response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Access-Control-Allow-Origin")
                .removeHeader("Vary")
                .removeHeader("Cache-Control")
                .header("Cache-Control", "public, max-age=60")
                .build();
    }
}