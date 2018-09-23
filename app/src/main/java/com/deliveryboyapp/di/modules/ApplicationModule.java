package com.deliveryboyapp.di.modules;

import android.arch.lifecycle.ViewModelProvider;
import android.util.Log;

import com.deliveryboyapp.net.APIEndPoints;
import com.deliveryboyapp.net.ViewModelFactory;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
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

    @Provides
    @Singleton
    OkHttpClient provideHttpClient() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        Log.e("mk", "Called Url: " + request.url());
                        return chain.proceed(request);
                    }
                })
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