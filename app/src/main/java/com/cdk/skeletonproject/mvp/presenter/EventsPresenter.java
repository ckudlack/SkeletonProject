package com.cdk.skeletonproject.mvp.presenter;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.mvp.contract.EventsContract;
import com.cdk.skeletonproject.mvp.usecase.EventsUseCase;

import java.util.List;

public class EventsPresenter implements EventsContract.Presenter {

    private EventsContract.View view;
    private EventsUseCase useCase;

    public EventsPresenter(EventsContract.View view, EventsUseCase useCase) {
        this.view = view;
        this.useCase = useCase;
    }

    @Override
    public void onStop() {
        useCase.clear();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void initialize(String apiKey) {
        useCase.getAllNearbyEvents(apiKey, new DefaultSubscriber<List<Artist>>() {
            @Override
            public void onNext(List<Artist> artists) {
                view.showEventsList(artists);
            }
        });
    }
}
