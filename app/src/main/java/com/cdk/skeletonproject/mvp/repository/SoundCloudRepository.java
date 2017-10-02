package com.cdk.skeletonproject.mvp.repository;

import com.cdk.skeletonproject.data.Artist;
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
    public Observable<List<Artist>> findUser(String userName, String clientId) {
        return remoteDataSource.findUser(userName, clientId);
    }

    @Override
    public Observable<List<Artist>> getFollowing(long userId, String clientId) {
        return remoteDataSource.getFollowing(userId, clientId);
    }

    @Override
    public Observable<Void> setDefaultUser(Artist user) {
        return localDataSource.setDefaultUser(user);
    }

    @Override
    public Observable<Artist> getDefaultUser() {
        return localDataSource.getDefaultUser();
    }

    @Override
    public Observable<Void> setFollowings(List<Artist> artists) {
        return localDataSource.setFollowings(artists);
    }

    @Override
    public void closeRealm() {
        localDataSource.close();
        remoteDataSource.close();
    }
}
