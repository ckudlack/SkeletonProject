package com.cdk.skeletonproject.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.R;
import com.cdk.skeletonproject.ScanCompleteEvent;
import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.mvp.datasource.SongKickLocalDataSource;
import com.cdk.skeletonproject.mvp.datasource.SongKickRemoteDataSource;
import com.cdk.skeletonproject.mvp.repository.SongKickRepository;
import com.cdk.skeletonproject.mvp.usecase.ScanningUseCase;
import com.cdk.skeletonproject.network.Api;
import com.cdk.skeletonproject.ui.MainActivity;

import java.util.List;

import de.greenrobot.event.EventBus;
import timber.log.Timber;

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
        createProgressNotification(progress, artists.size());

        Location location = intent.getParcelableExtra(MainActivity.EXTRA_LOCATION);
        String locationString = "geo:" + location.getLatitude() + "," + location.getLongitude();

        scanningUseCase.getEventsForArtists(artists,
                locationString, getApplicationContext().getString(R.string.key_songkick),
                new DefaultSubscriber<List<SongKickEvent>>() {
                    @Override
                    public void onCompleted() {
                        createTextNotification("Scan completed");
                        EventBus.getDefault().postSticky(new ScanCompleteEvent());
                        stopSelf();
                    }

                    @Override
                    public void onNext(List<SongKickEvent> events) {
                        createProgressNotification(++progress, artists.size());
                        Timber.d("Notification created, progress = " + progress);
                    }

                    @Override
                    public void onError(Throwable e) {
                        createTextNotification("An error occurred");
                        stopSelf();
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

    private void createProgressNotification(int progress, int listSize) {
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "scanning";

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, channelId).setVibrate(null).setSound(null).setOngoing(true)
                        .setProgress(listSize, progress, false)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Scanning your artists...")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence channelName = "Artist Scan";
            final int importance = NotificationManager.IMPORTANCE_HIGH;
            final NotificationChannel notificationChannel;

            notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableVibration(false);
            notificationChannel.enableLights(false);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(notificationId, builder.build());
    }

    private void createTextNotification(String text) {
        final NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String channelId = "scanning";

        final NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, channelId).setVibrate(null).setSound(null).setOngoing(false)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(text)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            CharSequence channelName = "Artist Scan";
            final int importance = NotificationManager.IMPORTANCE_HIGH;
            final NotificationChannel notificationChannel;

            notificationChannel = new NotificationChannel(channelId, channelName, importance);
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        notificationManager.notify(notificationId, builder.build());
    }

}
