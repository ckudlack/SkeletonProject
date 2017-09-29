package com.cdk.skeletonproject.mvp.usecase;

import com.cdk.skeletonproject.DefaultSubscriber;
import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.mvp.contract.SongKickDataContract;
import com.cdk.skeletonproject.mvp.contract.SoundCloudDataContract;

import rx.Observable;

public class MainUseCase extends UseCase {

    private SoundCloudDataContract.Repository soundCloudRepository;
    private SongKickDataContract.Repository songKickRepository;

    public MainUseCase(SoundCloudDataContract.Repository soundCloudRepository, SongKickDataContract.Repository songKickRepository) {
        this.soundCloudRepository = soundCloudRepository;
        this.songKickRepository = songKickRepository;
    }

    public void findUser(String userName, String clientId, DefaultSubscriber subscriber) {
        execute(soundCloudRepository.findUser(userName, clientId), subscriber);
    }

    public void getFollowing(long userId, String clientId, DefaultSubscriber subscriber) {
        execute(soundCloudRepository.getFollowing(userId, clientId), subscriber);
    }

    public void setDefaultUser(Artist user, DefaultSubscriber subscriber) {
        execute(soundCloudRepository.setDefaultUser(user), subscriber);
    }

    public void getDefaultUser(DefaultSubscriber subscriber) {
        execute(soundCloudRepository.getDefaultUser(), subscriber);
    }

    public void getDefaultUserFollowing(String clientId, DefaultSubscriber subscriber) {
        execute(soundCloudRepository.getDefaultUser().flatMap(soundCloudUser -> {
            if (soundCloudUser != null && soundCloudUser.getFollowings().isEmpty()) {
                return soundCloudRepository.getFollowing(soundCloudUser.getId(), clientId);
            }
            return Observable.just(soundCloudUser == null ? null : soundCloudUser.getFollowings());
        }), subscriber);
    }

    public void closeRealm() {
        soundCloudRepository.closeRealm();
    }
}
