package com.cdk.skeletonproject.mvp.repository;

import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.mvp.contract.SongKickDataContract;

import java.util.List;

import rx.Observable;

public class SongKickRepository implements SongKickDataContract.Repository {

    private SongKickDataContract.LocalDataSource localDataSource;
    private SongKickDataContract.RemoteDataSource remoteRemoteDataSource;

    public SongKickRepository(SongKickDataContract.LocalDataSource localDataSource, SongKickDataContract.RemoteDataSource remoteRemoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteRemoteDataSource = remoteRemoteDataSource;
    }

    @Override
    public Observable<List<Artist>> getArtists(String apiKey) {
        return localDataSource.getArtists(false, apiKey);
    }

    @Override
    public Observable<Artist> getArtist(long id, String apiKey) {
        return null;
    }

    @Override
    public Observable<List<Artist>> getArtistsWithEvents(String apiKey) {
        return localDataSource.getArtists(true, apiKey);
    }

    @Override
    public Observable<List<SongKickEvent>> getEventsForArtist(long artistId, String apiKey) {
        return remoteRemoteDataSource.getEventsForArtist(artistId, apiKey);
    }

    @Override
    public Observable<List<SongKickEvent>> getEventsForArtist(Artist artist, String location, String apiKey) {
        return remoteRemoteDataSource.getEventsForArtist(artist, location, apiKey).doOnNext(events -> {
            artist.setEvents(events);
            localDataSource.setArtist(artist);
        });
    }
}
