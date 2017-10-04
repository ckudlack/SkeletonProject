package com.cdk.skeletonproject.mvp.datasource;

import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.mvp.contract.SoundCloudDataContract;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

public class SoundCloudLocalDataSource implements SoundCloudDataContract.DataSource {

    public SoundCloudLocalDataSource() {
    }

    @Override
    public Observable<List<Artist>> findUser(String userName, String clientId) {
        final List<Artist> artists;
        try (Realm realm = Realm.getDefaultInstance()) {
            final RealmResults<Artist> results = realm.where(Artist.class).findAll();
            artists = realm.copyFromRealm(results);
        }
        return Observable.just(artists);
    }

    @Override
    public Observable<List<Artist>> getFollowing(long userId, String clientId) {
        List<Artist> followings;
        try (Realm realm = Realm.getDefaultInstance()) {
            final Artist user = realm.where(Artist.class).equalTo("id", userId).findFirst();
            if (user == null) {
                return Observable.empty();
            }

            followings = realm.copyFromRealm(user.getFollowings());
        }

        return Observable.just(followings);
    }

    @Override
    public Observable<Void> setDefaultUser(Artist user) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(realm1 -> {
                realm1.delete(Artist.class);
                realm1.insert(user);
            });
        }
        return Observable.just(null);
    }

    @Override
    public Observable<Artist> getDefaultUser() {
        Artist defaultUser;
        try (Realm realm = Realm.getDefaultInstance()) {
            final Artist result = realm.where(Artist.class).equalTo("isDefaultUser", true).findFirst();
            defaultUser = result == null ? null : realm.copyFromRealm(result);
        }
        return Observable.just(defaultUser);
    }

    @Override
    public Observable<Void> setFollowings(List<Artist> artists) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.executeTransaction(realm1 -> {
                final Artist defaultUser = realm1.where(Artist.class).equalTo("isDefaultUser", true).findFirst();
                defaultUser.setFollowings(artists);
                realm1.insertOrUpdate(defaultUser);
            });
        }
        return Observable.just(null);
    }

    @Override
    public void close() {
    }
}
