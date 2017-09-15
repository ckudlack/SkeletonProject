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
        // no-op
    }

    @Override
    public void onDestroy() {
        useCase.closeRealm();
    }

    @Override
    public void getDefaultUserFollowing(String clientId) {
        view.showLoading();
        useCase.getDefaultUserFollowing(clientId, new DefaultSubscriber<List<SoundCloudUser>>() {
            @Override
            public void onNext(List<SoundCloudUser> followings) {
                view.hideLoading();
                if (followings != null) {
                    view.setUserList(followings);
                }
            }
        });
    }

    @Override
    public void getDefaultUser(String clientId) {
        useCase.getDefaultUser(new DefaultSubscriber<SoundCloudUser>() {
            @Override
            public void onNext(SoundCloudUser soundCloudUser) {
                if (soundCloudUser == null) {
                    view.startUserSelectionActivity();
                } else {
                    view.setupUI(soundCloudUser.getUsername(), soundCloudUser.getAvatarUrl());
                    getDefaultUserFollowing(clientId);
                }
            }
        });
    }
}