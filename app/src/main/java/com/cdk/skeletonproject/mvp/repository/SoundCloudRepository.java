package com.cdk.skeletonproject.mvp.repository;

import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.SoundCloudDataContract;

import java.util.List;

import rx.Observable;

public class SoundCloudRepository implements SoundCloudDataContract.Repository {

    private SoundCloudDataContract.DataSource localDataSource;
    private SoundCloudDataContract.DataSource remoteDataSource;

    public SoundCloudRepository(SoundCloudDataContract.DataSource localDataSource, SoundCloudDataContract.DataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }

    @Override
    public Observable<List<SoundCloudUser>> findUser(String userName, String clientId) {
        return remoteDataSource.findUser(userName, clientId);
    }

    @Override
    public Observable<List<SoundCloudUser>> getFollowing(long userId, String clientId) {
        return remoteDataSource.getFollowing(userId, clientId);

        /*return localDataSource.getFollowing(userId, clientId).flatMap(soundCloudUsers -> {
            if (soundCloudUsers == null || soundCloudUsers.isEmpty()) {
                return remoteDataSource.getFollowing(userId, clientId);
            }
            return Observable.just(soundCloudUsers);
        });*/
    }

    @Override
    public Observable<Void> setDefaultUser(SoundCloudUser user) {
        return localDataSource.setDefaultUser(user);
    }

    @Override
    public Observable<SoundCloudUser> getDefaultUser() {
        return localDataSource.getDefaultUser();
    }

    @Override
    public void closeRealm() {
        localDataSource.close();
        remoteDataSource.close();
    }
}
