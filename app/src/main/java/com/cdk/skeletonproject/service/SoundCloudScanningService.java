package com.cdk.skeletonproject.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.R;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.datasource.SongKickLocalDataSource;
import com.cdk.skeletonproject.mvp.datasource.SongKickRemoteDataSource;
import com.cdk.skeletonproject.mvp.repository.SongKickRepository;
import com.cdk.skeletonproject.mvp.usecase.ScanningUseCase;
import com.cdk.skeletonproject.network.Api;

import java.util.List;

/**
 * The point of this service is to scan all of the users
 */
public class SoundCloudScanningService extends Service {

    private ScanningUseCase scanningUseCase;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public SoundCloudScanningService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent == null) {
            stopSelf();
            return START_NOT_STICKY;
        }

        final List<SoundCloudUser> artists = intent.getParcelableArrayListExtra("ARTISTS");
        scanningUseCase.getEventsForArtists(artists,
                getApplicationContext().getString(R.string.key_songkick),
                new DefaultSubscriber<List<SongKickEvent>>() {
                    @Override
                    public void onNext(List<SongKickEvent> events) {
                        // TODO: Update progress notification here
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });

        return START_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initUseCase();
    }

    private void initUseCase() {
        scanningUseCase = new ScanningUseCase(
                new SongKickRepository(
                        new SongKickLocalDataSource(),
                        new SongKickRemoteDataSource(Api.getSongKickService())));
    }
}
