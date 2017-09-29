package com.cdk.skeletonproject.mvp.usecase;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.SongKickDataContract;

import java.util.List;

import rx.Observable;

public class ScanningUseCase extends UseCase {

    private SongKickDataContract.Repository repository;

    public ScanningUseCase(SongKickDataContract.Repository repository) {
        this.repository = repository;
    }

    public void getEventsForArtist(String artistName, String location, String apiKey, DefaultSubscriber subscriber) {
        execute(repository.getEventsForArtist(artistName, location, apiKey), subscriber);
    }

    public void getEventsForArtists(List<SoundCloudUser> artists, String apiKey, DefaultSubscriber subscriber) {
        // TODO: Use location from repo instead of hardcoded one
        final Observable<List<SongKickEvent>> listObservable = Observable.from(artists).map(SoundCloudUser::getUsername)
                .flatMap(s -> repository.getEventsForArtist(s, "geo:37.744115,-122.425240", apiKey));

        execute(listObservable, subscriber);
    }
}
