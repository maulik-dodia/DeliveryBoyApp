package com.deliveryboyapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;

public class OfflineResponseCacheInterceptor implements Interceptor {

    private Context mContext;

    public OfflineResponseCacheInterceptor(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        NetworkInfo networkInfo = ((ConnectivityManager)
                (mContext.getSystemService(Context.CONNECTIVITY_SERVICE))).getActiveNetworkInfo();

        if (networkInfo == null) {
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Access-Control-Allow-Origin")
                    .removeHeader("Vary")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control",
                            "public, only-if-cached, max-stale= 60")
                    .build();
        }

        return chain.proceed(request);
    }
}