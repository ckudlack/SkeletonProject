package com.cdk.skeletonproject.mvp.presenter;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.MainContract;
import com.cdk.skeletonproject.mvp.usecase.SoundCloudSearchUseCase;

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
    public void init() {
        useCase.getDefaultUser(new DefaultSubscriber<SoundCloudUser>() {
            @Override
            public void onNext(SoundCloudUser soundCloudUser) {
                if (soundCloudUser == null) {
                    view.startUserSelectionActivity();
                } else {

                }
            }
        });
    }

    @Override
    public void buttonClicked(String userName, String clientId) {
        /*useCase.findUserAndFollowing(userName, clientId, new Subscriber<FollowingsResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "onError");
            }

            @Override
            public void onNext(FollowingsResponse soundCloudUsers) {
                view.setUserList(soundCloudUsers.getUsers());
            }
        });*/
    }
}
