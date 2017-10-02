package com.cdk.skeletonproject.mvp.contract;

import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.mvp.datasource.BaseDataSource;
import com.cdk.skeletonproject.mvp.repository.BaseRepository;

import java.util.List;

import rx.Observable;

public interface SongKickDataContract {

    interface Repository extends BaseRepository {
        Observable<List<Artist>> getArtists(String apiKey);

        Observable<Artist> getArtist(long id, String apiKey);

        Observable<List<Artist>> getArtistsWithEvents(String apiKey);

        Observable<List<SongKickEvent>> getEventsForArtist(long artistId, String apiKey);

        Observable<List<SongKickEvent>> getEventsForArtist(Artist artist, String location, String apiKey);

    }

    interface RemoteDataSource extends BaseDataSource {
        Observable<List<SongKickEvent>> getEventsForArtist(long artistId, String apiKey);

        Observable<List<SongKickEvent>> getEventsForArtist(Artist artist, String location, String apiKey);
    }

    interface LocalDataSource extends BaseDataSource {
        Observable<List<Artist>> getArtists(boolean hasEvents, String apiKey);

        Observable<Artist> getArtist(long id, String apiKey);

        boolean dataAvailable();

        Observable<Void> setEventsForArtist(Artist artist, List<SongKickEvent> events);

        Observable<Void> setArtist(Artist artist);
    }
}
