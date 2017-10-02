package com.cdk.skeletonproject.mvp.usecase;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.mvp.contract.SongKickDataContract;

public class EventsUseCase extends UseCase {

    private SongKickDataContract.Repository repository;

    public EventsUseCase(SongKickDataContract.Repository repository) {
        this.repository = repository;
    }

    public void getAllNearbyEvents(String apiKey, DefaultSubscriber subscriber) {
        execute(repository.getArtistsWithEvents(apiKey), subscriber);
    }
}
