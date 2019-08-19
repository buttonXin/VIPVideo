package com.oldhigh.vipvideo.http.api;

import com.oldhigh.vipvideo.bean.PostSearchBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
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
    Observable<ResponseBody> getIdentCodeCall(@Url String url);

    @GET("/")
    Observable<ResponseBody> getIdentCodeCall();


    @FormUrlEncoded
    @POST("/index.php?m=vod-search&submit=search")
    Observable<ResponseBody> getSearch(@Field("wd") String  searchName);


}
