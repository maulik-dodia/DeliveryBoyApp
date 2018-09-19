package com.deliveryboyapp.net;

import android.content.Context;
import android.util.Log;

import com.deliveryboyapp.ResponseCachingInterceptor;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.deliveryboyapp.Constants.BASE_URL;
import static com.deliveryboyapp.Utils.isNetworkAvailable;

public class RetrofitClient {

    private static String TAG = RetrofitClient.class.getSimpleName();
    private static Context mContext;

    public static Retrofit getInstance(Context context) {

        Log.e(TAG, "getInstance..");

        mContext = context;

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(createHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                /*.addCallAdapterFactory(RxJava2CallAdapterFactory.create())*/
                .build();
    }

    private static OkHttpClient createHttpClient() {

        Log.e(TAG, "createHttpClient..");

        Cache cache = new Cache(mContext.getCacheDir(), 10 * 1024 * 1024); // 10 MB

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new ResponseCachingInterceptor(mContext))
                .addInterceptor(new ResponseCachingInterceptor(mContext))
                .cache(cache)
                .build();

        /*OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {

                    @Override
                    public Response intercept(Chain chain) throws IOException {

                        *//*Request request = chain.request().newBuilder()
                                .header("Cache-Control", "public, only-if-cached," + "max-stale=" + 60 * 60 * 24)
                                .build();

                        Log.e(TAG, "Called Url: " + request.url());

                        return chain.proceed(request);*//*

                        Request request = chain.request();

                        if (!isNetworkAvailable(mContext)) {

                            //onInternetUnavailable();

                            *//*request = request.newBuilder().header("Cache-Control",
                                    "public, only-if-cached, max-stale=" + 60 * 60 * 24).build();
                            Response response = chain.proceed(request);*//*

                            request = request.newBuilder().header("Cache-Control",
                                    "public, only-if-cached, max-stale=" + 60 * 60 * 24).build();
                            Response response = chain.proceed(request);

                            if (response.cacheResponse() == null) {
                                //onCacheUnavailable();
                            }
                            return response;
                        }
                        return chain.proceed(request);
                    }
                });*/

        return okHttpClient;
    }

    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {

        @Override
        public Response intercept(Chain chain) {

            Log.e(TAG, "intercept..");

            Response originalResponse = null;

            try {

                originalResponse = chain.proceed(chain.request());

            } catch (IOException e) {

                Log.e(TAG, "IOException: " + e.getMessage());
            }

            if (isNetworkAvailable(mContext)) {

                Log.e(TAG, "Net available");

                int maxAge = 60; // read from cache for 1 minute
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {

                Log.e(TAG, "Net not available");

                int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    };
}