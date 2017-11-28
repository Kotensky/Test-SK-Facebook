package com.kotensky.testskfacebook.model.network;


import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.kotensky.testskfacebook.utils.SharedPreferencesManager;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiFactory {

    private static final int SHORT_TIME_OUT = 30;

    @NonNull
    public static ApiRequestService getApiRequestService() {
        return getRetrofitDefault(getOkHttpClient(SHORT_TIME_OUT)).create(ApiRequestService.class);
    }

    public static OkHttpClient getOkHttpClient(int timeOut) {
        return new OkHttpClient.Builder()
                .connectTimeout(timeOut, TimeUnit.SECONDS)
                .readTimeout(timeOut, TimeUnit.SECONDS)
                .writeTimeout(timeOut, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request().newBuilder()
                            .build();
                    return chain.proceed(request);
                }).build();
    }


    @NonNull
    private static Retrofit getRetrofitDefault(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl(NetworkVariables.MAIN_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(okHttpClient)
                .build();
    }

}
