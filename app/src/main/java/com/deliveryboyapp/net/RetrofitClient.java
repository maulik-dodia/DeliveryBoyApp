package com.deliveryboyapp.net;

import android.content.Context;
import android.util.Log;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.deliveryboyapp.Constants.BASE_URL;

public class RetrofitClient {

    private static String TAG = RetrofitClient.class.getSimpleName();
    //private static Context mContext;

    public static Retrofit getInstance(/*Context context*/) {

        //mContext = context;

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private static OkHttpClient createHttpClient() {

        //Cache cache = new Cache(mContext.getCacheDir(), 10 * 1024 * 1024); // 10 MB

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.e("mk", "Called Url: " + request.url());
                        return chain.proceed(request);
                    }
                })
                /*.cache(cache)*/
                .build();

        return okHttpClient;
    }
}