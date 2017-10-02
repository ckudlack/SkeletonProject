package com.cdk.skeletonproject.mvp.presenter;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.mvp.contract.MainContract;
import com.cdk.skeletonproject.mvp.usecase.MainUseCase;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainUseCase useCase;

    public MainPresenter(MainContract.View view, MainUseCase useCase) {
        this.view = view;
        this.useCase = useCase;
    }

    public void onStop() {
        useCase.clear();
        view.unregisterEventBus();
    }

    @Override
    public void onStart() {
        view.registerEventBus();
    }

    @Override
    public void onDestroy() {
        useCase.closeRealm();
    }

    @Override
    public void getDefaultUserFollowing(String clientId) {
        view.showLoading();
        useCase.getDefaultUserFollowing(clientId, new DefaultSubscriber<List<Artist>>() {
            @Override
            public void onNext(List<Artist> followings) {
                view.hideLoading();
                if (followings != null) {
                    view.setUserList(followings);
                }
            }
        });
    }

    @Override
    public void getDefaultUser(String clientId) {
        useCase.getDefaultUser(new DefaultSubscriber<Artist>() {
            @Override
            public void onNext(Artist artist) {
                if (artist == null) {
                    view.startUserSelectionActivity();
                } else if (artist.getFollowings().size() > 0) {
                    view.startEventsActivity();
                } else {
                    getDefaultUserFollowing(clientId);
                }
            }
        });
    }

    @Override
    public void getEventsForArtist(String artist, String location, String apiKey) {

    }

    @Override
    public void dialogContinueClicked(List<Artist> artists) {
        useCase.setFollowings(artists, new DefaultSubscriber<Void>() {
            @Override
            public void onNext(Void aVoid) {
                view.startScanningService(artists);
            }
        });
    }

    @Override
    public void onScanComplete() {
        view.startEventsActivity();
    }
}
