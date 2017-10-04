package com.cdk.skeletonproject.mvp.datasource;

import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.data.FollowingsResponse;
import com.cdk.skeletonproject.mvp.contract.SoundCloudDataContract;
import com.cdk.skeletonproject.network.SoundCloudService;

import java.util.List;

import io.realm.Realm;
import rx.Observable;

public class SoundCloudRemoteDataSource implements SoundCloudDataContract.DataSource {

    private SoundCloudService service;
    private Realm realm;

    public SoundCloudRemoteDataSource(SoundCloudService service) {
        this.service = service;
        this.realm = Realm.getDefaultInstance();
    }

    @Override
    public Observable<List<Artist>> findUser(String userName, String clientId) {
        return service.findUser(clientId, userName)
                .flatMap(Observable::from)
                .map(Artist::initialize)
                .toList();
    }

    @Override
    public Observable<List<Artist>> getFollowing(long userId, String clientId) {
        return service.getFollowing(userId, 1000, clientId)
                .map(FollowingsResponse::getUsers)
                .flatMap(Observable::from)
                .map(Artist::initialize)
                .toList();
    }

    @Override
    public Observable<Void> setDefaultUser(Artist user) {
        return null;
    }

    @Override
    public Observable<Artist> getDefaultUser() {
        return null;
    }

    @Override
    public Observable<Void> setFollowings(List<Artist> artists) {
        return null;
    }

    @Override
    public void close() {
        realm.close();
    }
}
