package com.cdk.skeletonproject.mvp.contract;

import com.cdk.skeletonproject.data.FollowingsResponse;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.datasource.BaseDataSource;
import com.cdk.skeletonproject.mvp.repository.BaseRepository;

import java.util.List;

import rx.Observable;

public interface SoundCloudDataContract {

    interface Repository extends BaseRepository {
        Observable<List<SoundCloudUser>> findUser(String userName, String clientId);

        Observable<FollowingsResponse> getFollowing(long userId, String clientId);
    }

    interface DataSource extends BaseDataSource {

    }
}
