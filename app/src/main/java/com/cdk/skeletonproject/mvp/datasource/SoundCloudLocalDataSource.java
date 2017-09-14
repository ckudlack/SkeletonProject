package com.cdk.skeletonproject.mvp.datasource;

import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.SoundCloudDataContract;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

public class SoundCloudLocalDataSource implements SoundCloudDataContract.DataSource {

    private Realm realm;

    public SoundCloudLocalDataSource(Realm realm) {
        this.realm = realm;
    }

    @Override
    public Observable<List<SoundCloudUser>> findUser(String userName, String clientId) {
        final RealmResults<SoundCloudUser> results = realm.where(SoundCloudUser.class).findAll();
        final List<SoundCloudUser> soundCloudUsers = realm.copyFromRealm(results);
        return Observable.just(soundCloudUsers);
    }

    @Override
    public Observable<List<SoundCloudUser>> getFollowing(long userId, String clientId) {
        final SoundCloudUser user = realm.where(SoundCloudUser.class).equalTo("id", userId).findFirst();
        if (user == null) {
            return Observable.empty();
        }

        final List<SoundCloudUser> followings = realm.copyFromRealm(user.getFollowings());
        return Observable.just(followings);
    }

    @Override
    public Observable<Void> setDefaultUser(SoundCloudUser user) {
        realm.executeTransaction(realm -> {
            final SoundCloudUser defaultUser = realm.where(SoundCloudUser.class).equalTo("id", user.getId()).findFirst();
            if (defaultUser != null) {
                defaultUser.setDefaultUser(true);
                realm.insertOrUpdate(defaultUser);
            } else {
                realm.insert(user);
            }
        });
        return Observable.just(null);
    }

    @Override
    public Observable<SoundCloudUser> getDefaultUser() {
        final SoundCloudUser defaultUser = realm.where(SoundCloudUser.class).equalTo("isDefaultUser", true).findFirst();
        return defaultUser == null ? Observable.just(null) : Observable.just(realm.copyFromRealm(defaultUser));
    }

    @Override
    public void close() {
        realm.close();
    }
}
