package com.deliveryboyapp.net;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.deliveryboyapp.Constants.BASE_URL;

public class RetrofitClient {

    private static String TAG = RetrofitClient.class.getSimpleName();

    public static Retrofit getInstance() {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                /*.addCallAdapterFactory(RxJava2CallAdapterFactory.create())*/
                .build();
    }

    private static OkHttpClient createHttpClient() {

        OkHttpClient.Builder OkHttpClient = new OkHttpClient.Builder();

        OkHttpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {

                Request request = chain.request();
                Log.e(TAG, "Called Url: " + request.url());
                return chain.proceed(request);
            }
        });

        return OkHttpClient.build();
    }
}