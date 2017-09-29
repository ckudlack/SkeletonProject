package com.cdk.skeletonproject.mvp.datasource;

import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.data.SongKickEvent;
import com.cdk.skeletonproject.mvp.contract.SongKickDataContract;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import rx.Observable;

public class SongKickLocalDataSource implements SongKickDataContract.LocalDataSource {

    // TODO: add Realm
    public SongKickLocalDataSource() {
    }

    @Override
    public void close() {

    }

    @Override
    public Observable<List<Artist>> getArtists(String apiKey) {
        Realm realm = Realm.getDefaultInstance();
        final RealmResults<Artist> all = realm.where(Artist.class).findAll();
        List<Artist> artists = new ArrayList<>();
        artists.addAll(realm.copyFromRealm(all));
        realm.close();
        return Observable.just(artists);
    }

    @Override
    public Observable<Artist> getArtist(long id, String apiKey) {
        Realm realm = Realm.getDefaultInstance();
        final Artist realmResult = realm.where(Artist.class).equalTo("id", id).findFirst();
        final Artist artist = realm.copyFromRealm(realmResult);
        realm.close();
        return Observable.just(artist);
    }

    @Override
    public boolean dataAvailable() {
        final Realm realm = Realm.getDefaultInstance();
        final long count = realm.where(Artist.class).count();
        realm.close();
        return count > 0;
    }

    @Override
    public Observable<Void> setEventsForArtist(Artist artist, List<SongKickEvent> events) {
        artist.setEvents(events);
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(artist));
        realm.close();
        return Observable.just(null);
    }

    @Override
    public Observable<Void> setArtist(Artist artist) {
        final Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(artist));
        realm.close();
        return Observable.just(null);
    }
}
