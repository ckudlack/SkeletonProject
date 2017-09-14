package com.cdk.skeletonproject.mvp.presenter;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.SetUserContract;
import com.cdk.skeletonproject.mvp.usecase.SoundCloudSearchUseCase;

import java.util.List;

public class SetUserPresenter implements SetUserContract.Presenter {

    private SetUserContract.View view;
    private SoundCloudSearchUseCase useCase;

    public SetUserPresenter(SetUserContract.View view, SoundCloudSearchUseCase useCase) {
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
    public void buttonClicked(String userName, String clientId) {
        useCase.findUser(userName, clientId, new DefaultSubscriber<List<SoundCloudUser>>() {
            @Override
            public void onNext(List<SoundCloudUser> soundCloudUsers) {
                view.setUserList(soundCloudUsers);
            }
        });
    }

    @Override
    public void setUserAsDefault(SoundCloudUser user) {
        useCase.setDefaultUser(user, new DefaultSubscriber());
    }
}
