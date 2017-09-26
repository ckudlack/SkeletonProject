package com.cdk.skeletonproject.mvp.datasource;

import com.cdk.skeletonproject.data.SongKickArtist;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.mvp.contract.SongKickDataContract;

import java.util.List;

import rx.Observable;

public class SongKickLocalDataSource implements SongKickDataContract.DataSource {

    // TODO: add Realm
    public SongKickLocalDataSource() {
    }

    @Override
    public void close() {

    }

    @Override
    public Observable<List<SongKickArtist>> getArtists(String artistName, String apiKey) {
        return null;
    }

    @Override
    public Observable<List<SongKickEvent>> getEventsForArtist(long artistId, String apiKey) {
        return null;
    }

    @Override
    public Observable<List<SongKickEvent>> getEventsForArtist(String artistName, String location, String apiKey) {
        return null;
    }

    @Override
    public boolean dataAvailable() {
        return false;
    }
}
