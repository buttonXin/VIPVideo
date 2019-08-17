package com.oldhigh.vipvideo.http.api;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * @author oldhigh
 */
public interface APIInterface {

    @GET
    Observable<String> getIdentCode(@Url String url);

    @GET
    Call<Object> getIdentCodeCall(@Url String url);


}
