package com.cdk.skeletonproject.mvp.presenter;

import com.cdk.skeletonproject.data.FollowingsResponse;
import com.cdk.skeletonproject.mvp.contract.MainContract;
import com.cdk.skeletonproject.mvp.usecase.MainUseCase;

import rx.Subscriber;
import timber.log.Timber;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainUseCase useCase;

    public MainPresenter(MainContract.View view, MainUseCase useCase) {
        this.view = view;
        this.useCase = useCase;
    }

    public void onStop() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void buttonClicked(String userName, String clientId) {
        useCase.findUser(userName, clientId, new Subscriber<FollowingsResponse>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "onError");
            }

            @Override
            public void onNext(FollowingsResponse soundCloudUsers) {
                Timber.d("onNext");
            }
        });
    }
}
