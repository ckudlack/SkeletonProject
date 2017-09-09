package com.cdk.skeletonproject.network;

import com.cdk.skeletonproject.network.adapter.BigDecimalAdapter;
import com.cdk.skeletonproject.network.adapter.LocalDateAdapter;
import com.cdk.skeletonproject.network.adapter.LocalDateTimeAdapter;
import com.cdk.skeletonproject.network.adapter.LocalTimeAdapter;
import com.squareup.moshi.Moshi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public final class Api {

    private static final String BASE_URL = "";

    private static OkHttpClient okHttpClient;
    private static Moshi moshi;
    private static NetworkService networkService;

    private Api() {
    }

    public static NetworkService getNetworkService() {
        if (networkService != null) {
            return networkService;
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        okHttpClient = createOkHttpClient().addInterceptor(logging).build();

        networkService = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create(getMoshi()).asLenient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(NetworkService.class);

        return networkService;
    }

    private static OkHttpClient.Builder createOkHttpClient() {
        final OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(25, TimeUnit.SECONDS)
                .readTimeout(25, TimeUnit.SECONDS)
                .writeTimeout(25, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true);

        return builder;
    }

    private static Moshi getMoshi() {
        if (moshi == null) {
            moshi = new Moshi.Builder()
                    .add(new BigDecimalAdapter())
                    .add(new LocalDateAdapter())
                    .add(new LocalTimeAdapter())
                    .add(new LocalDateTimeAdapter())
                    .build();
        }

        return moshi;
    }
}
