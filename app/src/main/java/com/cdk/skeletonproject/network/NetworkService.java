package com.cdk.skeletonproject.network;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface NetworkService {

    @FormUrlEncoded
    @POST("some/url/appended/to/base")
    Call<Object> postStringToServer(@Field("aString") String string);

    /**
     * Sample RequestBody creation:
     *
     * RequestBody body = RequestBody.create(MediaType.parse("image/*"), aFile);
     */
    @POST("some/url/{pathName}/to/base")
    Call<Object> postImageWithOtherParams(@Path("pathName") long param,
                                          @QueryMap Map<String, String> params,
                                          @Body RequestBody image);

    @GET("some/url/appended/to/base")
    Call<String> getStringFromServer(@Query("paramName") String someParameter);

    @GET("some/url/appended/to/base")
    Call<Object> getItemFromServerWithLotsOfParams(@QueryMap Map<String, String> params);
}
