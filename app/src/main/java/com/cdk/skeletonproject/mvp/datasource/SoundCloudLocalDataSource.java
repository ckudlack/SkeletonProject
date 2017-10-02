package com.cdk.skeletonproject.mvp.datasource;

import com.cdk.skeletonproject.data.Artist;
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
    public Observable<List<Artist>> findUser(String userName, String clientId) {
        final RealmResults<Artist> results = realm.where(Artist.class).findAll();
        final List<Artist> artists = realm.copyFromRealm(results);
        return Observable.just(artists);
    }

    @Override
    public Observable<List<Artist>> getFollowing(long userId, String clientId) {
        final Realm defaultInstance = Realm.getDefaultInstance();
        final Artist user = defaultInstance.where(Artist.class).equalTo("id", userId).findFirst();
        if (user == null) {
            return Observable.empty();
        }

        final List<Artist> followings = defaultInstance.copyFromRealm(user.getFollowings());
        defaultInstance.close();
        return Observable.just(followings);
    }

    @Override
    public Observable<Void> setDefaultUser(Artist user) {
        realm.executeTransaction(realm -> {
            realm.delete(Artist.class);
            realm.insert(user);
        });
        return Observable.just(null);
    }

    @Override
    public Observable<Artist> getDefaultUser() {
        final Artist defaultUser = realm.where(Artist.class).equalTo("isDefaultUser", true).findFirst();
        return defaultUser == null ? Observable.just(null) : Observable.just(realm.copyFromRealm(defaultUser));
    }

    @Override
    public Observable<Void> setFollowings(List<Artist> artists) {
        realm.executeTransaction(realm -> {
            final Artist defaultUser = realm.where(Artist.class).equalTo("isDefaultUser", true).findFirst();
            defaultUser.setFollowings(artists);
            realm.insert(defaultUser);
        });
        return Observable.just(null);
    }

    @Override
    public void close() {
        realm.close();
    }
}
