package com.cdk.skeletonproject.mvp.presenter;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.MainContract;
import com.cdk.skeletonproject.mvp.usecase.SoundCloudSearchUseCase;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private SoundCloudSearchUseCase useCase;

    public MainPresenter(MainContract.View view, SoundCloudSearchUseCase useCase) {
        this.view = view;
        this.useCase = useCase;
    }

    public void onStop() {
        useCase.clear();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {
        useCase.closeRealm();
    }

    @Override
    public void init(String clientId) {
        useCase.getDefaultUserFollowing(clientId, new DefaultSubscriber<List<SoundCloudUser>>() {
            @Override
            public void onNext(List<SoundCloudUser> soundCloudUsers) {
                if (soundCloudUsers == null) {
                    view.startUserSelectionActivity();
                } else {
                    view.setUserList(soundCloudUsers);
                }
            }
        });
    }

    @Override
    public void activityReturned(String clientId) {
        useCase.getDefaultUserFollowing(clientId, new DefaultSubscriber<List<SoundCloudUser>>() {
            @Override
            public void onNext(List<SoundCloudUser> soundCloudUsers) {
                if (soundCloudUsers == null) {
                    view.startUserSelectionActivity();
                } else {
                    view.setUserList(soundCloudUsers);
                }
            }
        });
    }

    @Override
    public void buttonClicked(String userName, String clientId) {

    }
}
