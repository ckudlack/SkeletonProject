package com.cdk.skeletonproject.data;

import com.squareup.moshi.Json;

import java.util.List;

public class FollowingsResponse {

    @Json(name = "collection")
    List<SoundCloudUserResponse> users;
    @Json(name = "next_href")
    String nextPageUrl;

    public List<SoundCloudUserResponse> getUsers() {
        return users;
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }
}
