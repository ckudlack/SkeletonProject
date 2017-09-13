package com.cdk.skeletonproject.network;

import com.cdk.skeletonproject.data.FollowingsResponse;
import com.cdk.skeletonproject.data.SoundCloudUser;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface NetworkService {

    @GET("users")
    Observable<List<SoundCloudUser>> findUser(@Query("client_id") String clientId, @Query("q") String userName);

    @GET("users/{id}/followings")
    Observable<FollowingsResponse> getFollowing(@Path("id") long userId, @Query("client_id") String clientId);
}
