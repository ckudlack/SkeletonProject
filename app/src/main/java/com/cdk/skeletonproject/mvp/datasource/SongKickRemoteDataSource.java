package com.cdk.skeletonproject.mvp.datasource;

import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.mvp.contract.SongKickDataContract;
import com.cdk.skeletonproject.network.SongKickService;

import java.util.List;

import rx.Observable;

public class SongKickRemoteDataSource implements SongKickDataContract.RemoteDataSource {

    private SongKickService service;

    public SongKickRemoteDataSource(SongKickService service) {
        this.service = service;
    }

    @Override
    public void close() {

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
    public Observable<List<SongKickEvent>> getEventsForArtist(Artist artist, String location, String apiKey) {
        return service.getEventsForArtist(artist.getUsername(), location, apiKey)
                .map(songKickEventResponse -> songKickEventResponse.getResultsPage().getResults().getResultsList())
                .flatMap(Observable::from)
                .map(SongKickEvent::initialize)
                .toList();
    }
}
