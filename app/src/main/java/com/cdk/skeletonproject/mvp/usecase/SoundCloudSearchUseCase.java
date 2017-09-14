package com.cdk.skeletonproject.mvp.usecase;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.SoundCloudDataContract;

import rx.Observable;

public class SoundCloudSearchUseCase extends UseCase {

    private SoundCloudDataContract.Repository repository;

    public SoundCloudSearchUseCase(SoundCloudDataContract.Repository repository) {
        this.repository = repository;
    }

    public void findUser(String userName, String clientId, DefaultSubscriber subscriber) {
        execute(repository.findUser(userName, clientId), subscriber);
    }

    public void getFollowing(long userId, String clientId, DefaultSubscriber subscriber) {
        execute(repository.getFollowing(userId, clientId), subscriber);
    }

    public void setDefaultUser(SoundCloudUser user, DefaultSubscriber subscriber) {
        execute(repository.setDefaultUser(user), subscriber);
    }

    public void getDefaultUser(DefaultSubscriber subscriber) {
        execute(repository.getDefaultUser(), subscriber);
    }

    public void getDefaultUserFollowing(String clientId, DefaultSubscriber subscriber) {
        execute(repository.getDefaultUser().flatMap(soundCloudUser -> {
            if (soundCloudUser != null && soundCloudUser.getFollowings().isEmpty()) {
                return repository.getFollowing(soundCloudUser.getId(), clientId);
            }
            return Observable.just(soundCloudUser == null ? null : soundCloudUser.getFollowings());
        }), subscriber);
    }

    public void closeRealm() {
        repository.closeRealm();
    }
}
