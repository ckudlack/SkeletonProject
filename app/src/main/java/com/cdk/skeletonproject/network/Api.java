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

    private static final String BASE_URL_SOUNDCLOUD = "https://api.soundcloud.com/";
    private static final String BASE_URL_SONGKICK = "http://api.songkick.com/api/3.0/";

    private static Moshi moshi;
    private static SoundCloudService soundCloudService;
    private static SongKickService songKickService;

    private Api() {
    }

    public static SoundCloudService getSoundCloudService() {
        if (soundCloudService != null) {
            return soundCloudService;
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        soundCloudService = new Retrofit.Builder()
                .client(createOkHttpClient().addInterceptor(logging).build())
                .baseUrl(BASE_URL_SOUNDCLOUD)
                .addConverterFactory(MoshiConverterFactory.create(getMoshi()).asLenient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(SoundCloudService.class);

        return soundCloudService;
    }

    public static SongKickService getSongKickService() {
        if (songKickService != null) {
            return songKickService;
        }

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        songKickService = new Retrofit.Builder()
                .client(createOkHttpClient().addInterceptor(logging).build())
                .baseUrl(BASE_URL_SONGKICK)
                .addConverterFactory(MoshiConverterFactory.create(getMoshi()).asLenient())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(SongKickService.class);

        return songKickService;
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
