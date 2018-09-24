package com.deliveryboyapp.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

import com.deliveryboyapp.ApplicationClass;
import com.deliveryboyapp.Constants;
import com.deliveryboyapp.Utils;
import com.deliveryboyapp.net.APIEndPoints;
import com.deliveryboyapp.net.ViewModelFactory;

import java.io.File;

import javax.inject.Named;
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

    @Provides
    @Singleton
    File provideFile() {
        return new File(mApplicationClass.getCacheDir(), Constants.STR_RESPONSES);
    }

    @Provides
    @Singleton
    Cache provideCache(File file) {
        int cacheSize = 100 * 1024 * 1024; // 100 MB
        return new Cache(file, cacheSize);
    }

    @Provides
    @Singleton
    @Named("onlineInterceptor")
    Interceptor provideOnlineInterceptor(ApplicationClass applicationClass) {

        return chain -> {
            Response response = chain.proceed(chain.request());
            String headers = response.header("Cache-Control");
            if (Utils.isNetworkAvailable(applicationClass)
                    &&
                    (headers == null || headers.contains("no-store") || headers.contains("must-revalidate")
                            || headers.contains("no-cache") || headers.contains("max-age=0"))) {

                Log.e(TAG, "Returning fresh response");

                return response.newBuilder()
                        .header("Cache-Control", "public, max-age=600")
                        .build();
            } else {
                Log.e(TAG, "Returning old response");
                return response;
            }
        };
    }

    @Provides
    @Singleton
    @Named("offlineInterceptor")
    Interceptor provideOfflineInterceptor(ApplicationClass applicationClass) {

        return chain -> {
            Request request = chain.request();
            if (!Utils.isNetworkAvailable(applicationClass)) {
                request = request.newBuilder()
                        .header("Cache-Control", "public, only-if-cached")
                        .build();
            }
            return chain.proceed(request);
        };
    }

    @Provides
    @Singleton
    OkHttpClient provideHttpClient(@Named("onlineInterceptor") Interceptor onlineInterceptor,
                                   @Named("offlineInterceptor") Interceptor offlineInterceptor, Cache cache) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Log.e(TAG, "Called Url: " + request.url());
                    return chain.proceed(request);
                })
                .addNetworkInterceptor(onlineInterceptor)
                .addInterceptor(offlineInterceptor)
                .cache(cache)
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