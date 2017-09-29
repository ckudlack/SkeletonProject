package com.cdk.skeletonproject.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.R;
import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.data.SongKickEvent;
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
    private final static int notificationId = 4245;
    private int progress = 0;

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

        final List<Artist> artists = intent.getParcelableArrayListExtra("ARTISTS");

        createNotification(progress, artists.size());

        scanningUseCase.getEventsForArtists(artists,
                getApplicationContext().getString(R.string.key_songkick),
                new DefaultSubscriber<List<SongKickEvent>>() {
                    @Override
                    public void onCompleted() {
                        // TODO: Notify activity
                    }

                    @Override
                    public void onNext(List<SongKickEvent> events) {
                        createNotification(++progress, artists.size());
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

    private void createNotification(int progress, int listSize) {
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // TODO: Use notification channel
        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this).setVibrate(null).setSound(null).setOngoing(true)
                        .setProgress(listSize, progress, false)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Scanning your artists...")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        notificationManager.notify(notificationId, builder.build());
    }
}
