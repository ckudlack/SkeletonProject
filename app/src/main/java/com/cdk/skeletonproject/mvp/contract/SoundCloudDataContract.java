package com.cdk.skeletonproject.mvp.contract;

import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.datasource.BaseDataSource;
import com.cdk.skeletonproject.mvp.repository.BaseRepository;

import java.util.List;

import rx.Observable;

public interface SoundCloudDataContract {

    interface Repository extends BaseRepository {
        Observable<List<SoundCloudUser>> findUser(String userName, String clientId);

        Observable<List<SoundCloudUser>> getFollowing(long userId, String clientId);

        Observable<Void> setDefaultUser(SoundCloudUser user);

        Observable<SoundCloudUser> getDefaultUser();

        void closeRealm();
    }

    interface DataSource extends BaseDataSource {
        Observable<List<SoundCloudUser>> findUser(String userName, String clientId);

        Observable<List<SoundCloudUser>> getFollowing(long userId, String clientId);

        Observable<Void> setDefaultUser(SoundCloudUser user);

        Observable<SoundCloudUser> getDefaultUser();
    }
}