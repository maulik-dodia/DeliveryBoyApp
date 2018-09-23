package com.deliveryboyapp.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

import com.deliveryboyapp.ApplicationClass;
import com.deliveryboyapp.Constants;
import com.deliveryboyapp.Utils;
import com.deliveryboyapp.net.APIEndPoints;
import com.deliveryboyapp.net.ViewModelFactory;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.deliveryboyapp.Constants.BASE_URL;

@Module
public class ApplicationModule {

    private static String TAG = ApplicationModule.class.getSimpleName();

    private ApplicationClass mApplicationClass;

    public ApplicationModule(ApplicationClass applicationClass) {
        this.mApplicationClass = applicationClass;
    }

    @Provides
    @Singleton
    ApplicationClass provideApplicationContext() {
        return mApplicationClass;
    }

    /*@Provides
    @Singleton
    File provideFile() {
        return new File(mApplicationClass.getCacheDir(), Constants.STR_RESPONSES);
    }*/

    /*@Provides
    @Singleton
    Cache provideCache(File file) {
        int cacheSize = 100 * 1024 * 1024; // 100 MB
        return new Cache(file, cacheSize);
    }*/

    /*@Provides
    @Singleton
    Interceptor provideInterceptor(ApplicationClass applicationClass) {

        return chain -> {
            Request request = chain.request();
            if (Utils.isNetworkAvailable(applicationClass)) {
                request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
            } else {
                request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
            }
            return chain.proceed(request);
        };

        *//*return chain -> {
            Response originalResponse = null;
            try {
                originalResponse = chain.proceed(chain.request());
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (Utils.isNetworkAvailable(mApplicationClass)) {

                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        };*//*
    }*/

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(/*Interceptor interceptor, Cache cache*/) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Log.e(TAG, "Called Url: " + request.url());
                    return chain.proceed(request);
                })
                /*.addNetworkInterceptor(interceptor)*/
                /*.cache(cache)*/
                .build();

        return okHttpClient;
    }

    @Provides
    @Singleton
    APIEndPoints provideEndPoints(OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build().create(APIEndPoints.class);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory provideViewModelFactory(APIEndPoints apiEndPoints) {
        return new ViewModelFactory(apiEndPoints);
    }
}