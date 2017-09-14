package com.cdk.skeletonproject.mvp.datasource;

import com.cdk.skeletonproject.data.FollowingsResponse;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.SoundCloudDataContract;
import com.cdk.skeletonproject.network.NetworkService;

import java.util.List;

import io.realm.Realm;
import rx.Observable;

public class SoundCloudRemoteDataSource implements SoundCloudDataContract.DataSource {

    private NetworkService service;
    private Realm realm;

    public SoundCloudRemoteDataSource(NetworkService service) {
        this.service = service;
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<List<SoundCloudUser>> findUser(String userName, String clientId) {
        return service.findUser(clientId, userName)
                .flatMap(Observable::from)
                .map(SoundCloudUser::initialize)
                .toList();
    }

    @Override
    public Observable<List<SoundCloudUser>> getFollowing(long userId, String clientId) {
        return service.getFollowing(userId, clientId)
                .map(FollowingsResponse::getUsers)
                .flatMap(Observable::from)
                .map(SoundCloudUser::initialize)
                .toList()
                .flatMap(soundCloudUsers -> {
                    try (Realm realm = Realm.getDefaultInstance()) {
                        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(soundCloudUsers));
                    }

                    final SoundCloudUser user = realm.where(SoundCloudUser.class).equalTo("id", userId).findFirst();
                    final List<SoundCloudUser> followings = realm.copyFromRealm(user.getFollowings());
                    return Observable.just(followings);
                });
    }

    @Override
    public Observable<Void> setDefaultUser(SoundCloudUser user) {
        return null;
    }

    @Override
    public Observable<SoundCloudUser> getDefaultUser() {
        return null;
    }

    @Override
    public void close() {
        realm.close();
    }
}
