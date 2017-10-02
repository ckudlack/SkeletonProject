package com.cdk.skeletonproject.mvp.contract;

import com.cdk.skeletonproject.data.Artist;
import com.cdk.skeletonproject.mvp.datasource.BaseDataSource;
import com.cdk.skeletonproject.mvp.repository.BaseRepository;

import java.util.List;

import rx.Observable;

public interface SoundCloudDataContract {

    interface Repository extends BaseRepository {
        Observable<List<Artist>> findUser(String userName, String clientId);

        Observable<List<Artist>> getFollowing(long userId, String clientId);

        Observable<Void> setDefaultUser(Artist user);

        Observable<Artist> getDefaultUser();

        Observable<Void> setFollowings(List<Artist> artists);

        void closeRealm();
    }

    interface DataSource extends BaseDataSource {
        Observable<List<Artist>> findUser(String userName, String clientId);

        Observable<List<Artist>> getFollowing(long userId, String clientId);

        Observable<Void> setDefaultUser(Artist user);

        Observable<Artist> getDefaultUser();

        Observable<Void> setFollowings(List<Artist> artists);
    }
}
