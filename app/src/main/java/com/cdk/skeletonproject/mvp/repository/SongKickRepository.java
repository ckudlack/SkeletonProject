package com.cdk.skeletonproject.mvp.repository;

import com.cdk.skeletonproject.data.SongKickArtist;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.mvp.contract.SongKickDataContract;

import java.util.List;

import rx.Observable;

public class SongKickRepository implements SongKickDataContract.Repository {

    private SongKickDataContract.DataSource localDataSource;
    private SongKickDataContract.DataSource remoteDataSource;

    public SongKickRepository(SongKickDataContract.DataSource localDataSource, SongKickDataContract.DataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<List<SongKickArtist>> getArtists(String artistName, String apiKey) {
        return remoteDataSource.getArtists(artistName, apiKey);
    }

    @Override
    public Observable<List<SongKickEvent>> getEventsForArtist(long artistId, String apiKey) {
        return remoteDataSource.getEventsForArtist(artistId, apiKey);
    }

    @Override
    public Observable<List<SongKickEvent>> getEventsForArtist(String artistName, String location, String apiKey) {
        return remoteDataSource.getEventsForArtist(artistName, location, apiKey);
    }
}
