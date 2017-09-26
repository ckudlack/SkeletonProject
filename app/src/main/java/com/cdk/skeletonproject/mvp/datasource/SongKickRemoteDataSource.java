package com.cdk.skeletonproject.mvp.datasource;

import com.cdk.skeletonproject.data.SongKickArtist;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.mvp.contract.SongKickDataContract;
import com.cdk.skeletonproject.network.SongKickService;

import java.util.List;

import rx.Observable;

public class SongKickRemoteDataSource implements SongKickDataContract.DataSource {

    private SongKickService service;

    public SongKickRemoteDataSource(SongKickService service) {
        this.service = service;
    }

    @Override
    public void close() {

    }

    @Override
    public Observable<List<SongKickArtist>> getArtists(String artistName, String apiKey) {
        return service.getArtists(artistName, apiKey)
                .map(songKickResponse -> songKickResponse.getResultsPage().getResults().getResultsList())
                .flatMap(Observable::from)
                .map(SongKickArtist::initialize)
                .toList();
    }

    @Override
    public Observable<List<SongKickEvent>> getEventsForArtist(long artistId, String apiKey) {
        return service.getEventsForArtist(artistId, apiKey)
                .map(songKickEventResponse -> songKickEventResponse.getResultsPage().getResults().getResultsList())
                .flatMap(Observable::from)
                .map(SongKickEvent::initialize)
                .toList();
    }

    @Override
    public Observable<List<SongKickEvent>> getEventsForArtist(String artistName, String location, String apiKey) {
        return service.getEventsForArtist(artistName, location, apiKey)
                .map(songKickEventResponse -> songKickEventResponse.getResultsPage().getResults().getResultsList())
                .flatMap(Observable::from)
                .map(SongKickEvent::initialize)
                .toList();
    }

    @Override
    public boolean dataAvailable() {
        return true;
    }
}
