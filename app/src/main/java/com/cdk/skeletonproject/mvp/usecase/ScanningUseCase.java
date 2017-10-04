package com.cdk.skeletonproject.mvp.usecase;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.mvp.contract.SongKickDataContract;

import java.util.List;

import rx.Observable;

public class ScanningUseCase extends UseCase {

    private SongKickDataContract.Repository repository;

    public ScanningUseCase(SongKickDataContract.Repository repository) {
        this.repository = repository;
    }

    public void getEventsForArtists(List<Artist> artists, String locationString, String apiKey, DefaultSubscriber subscriber) {
        final Observable<List<SongKickEvent>> listObservable = Observable.from(artists)
                .flatMap(user -> repository.getEventsForArtist(user, locationString, apiKey));

        execute(listObservable, subscriber);
    }
}
