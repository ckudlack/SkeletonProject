package com.cdk.skeletonproject.network;

import com.cdk.skeletonproject.data.SongKickArtistResponse;
import com.cdk.skeletonproject.data.SongKickEventResponse;
import com.cdk.skeletonproject.data.SongKickResponse;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface SongKickService {

    @GET("search/artists.json")
    Observable<SongKickResponse<SongKickArtistResponse>> getArtists(@Query("query") String artistName, @Query("apikey") String apiKey);

    @GET("artists/{artistId}/calendar.json")
    Observable<SongKickResponse<SongKickEventResponse>> getEventsForArtist(@Path("artistId") long artistId, @Query("apikey") String apiKey);

    @GET("events.json")
    Observable<SongKickResponse<SongKickEventResponse>> getEventsForArtist(@Query("artist_name") String artistName, @Query("location") String location, @Query("apikey") String apiKey);
}
