package com.cdk.skeletonproject.network;

import com.cdk.skeletonproject.data.FollowingsResponse;
import com.cdk.skeletonproject.data.SoundCloudUserResponse;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface SoundCloudService {

    @GET("users")
    Observable<List<SoundCloudUserResponse>> findUser(@Query("client_id") String clientId, @Query("q") String userName);

    @GET("users/{id}/followings")
    Observable<FollowingsResponse> getFollowing(@Path("id") long userId, @Query("page_size") int pageSize, @Query("client_id") String clientId);
}
