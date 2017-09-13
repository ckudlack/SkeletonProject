package com.cdk.skeletonproject.mvp.usecase;

import com.cdk.skeletonproject.data.FollowingsResponse;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.SoundCloudDataContract;

import rx.Observable;
import rx.Subscriber;

public class MainUseCase extends UseCase {

    private SoundCloudDataContract.Repository repository;

    public MainUseCase(SoundCloudDataContract.Repository repository) {
        this.repository = repository;
    }

    public void findUser(String userName, String clientId, Subscriber subscriber) {
        final Observable<FollowingsResponse> observable = repository.findUser(userName, clientId).flatMap(soundCloudUsers -> {
            if (soundCloudUsers.size() > 0) {
                final SoundCloudUser soundCloudUser = soundCloudUsers.get(0);
                return repository.getFollowing(soundCloudUser.getId(), clientId);
            }

            return null;
        });

        execute(observable, subscriber);
    }

    public void getFollowing(long userId, String clientId, Subscriber subscriber) {
        execute(repository.getFollowing(userId, clientId), subscriber);
    }
}
