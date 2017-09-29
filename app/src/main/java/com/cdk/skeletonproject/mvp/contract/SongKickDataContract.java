package com.cdk.skeletonproject.mvp.contract;

import com.cdk.skeletonproject.data.SongKickArtist;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.mvp.datasource.BaseDataSource;
import com.cdk.skeletonproject.mvp.repository.BaseRepository;

import java.util.List;

import rx.Observable;

public interface SongKickDataContract {

    interface Repository extends BaseRepository {
        Observable<List<SongKickArtist>> getArtists(String artistName, String apiKey);
        Observable<List<SongKickEvent>> getEventsForArtist(long artistId, String apiKey);
        Observable<List<SongKickEvent>> getEventsForArtist(String artistName, String location, String apiKey);
    }

    interface DataSource extends BaseDataSource {
        Observable<List<SongKickArtist>> getArtists(String artistName, String apiKey);
        Observable<List<SongKickEvent>> getEventsForArtist(long artistId, String apiKey);
        Observable<List<SongKickEvent>> getEventsForArtist(String artistName, String location, String apiKey);
        boolean dataAvailable();
        Observable<Void> setEventsForArtist(List<SongKickEvent> events);
    }
}
