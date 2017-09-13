package com.cdk.skeletonproject.mvp.repository;

import com.cdk.skeletonproject.data.FollowingsResponse;
import com.cdk.skeletonproject.data.SoundCloudUser;
import com.cdk.skeletonproject.mvp.contract.SoundCloudDataContract;
import com.cdk.skeletonproject.network.NetworkService;

import java.util.List;

import rx.Observable;

public class SoundCloudRepository implements SoundCloudDataContract.Repository {

    private NetworkService service;

    public SoundCloudRepository(NetworkService service) {
        this.service = service;
    }

    @Override
    public Observable<List<SoundCloudUser>> findUser(String userName, String clientId) {
        return service.findUser(clientId, userName);
    }

    @Override
    public Observable<FollowingsResponse> getFollowing(long userId, String clientId) {
        return service.getFollowing(userId, clientId);
    }
}
