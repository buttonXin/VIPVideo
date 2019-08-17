package com.oldhigh.vipvideo.http.api;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * @author oldhigh
 */
public interface APIInterface {

    @GET
    Observable<String> getIdentCode(@Url String url);

    @GET
    Observable<ResponseBody> getIdentCodeCall(@Url String url);


}
