package com.oldhigh.vipvideo.http;


import com.oldhigh.vipvideo.App;
import com.oldhigh.vipvideo.http.api.APIInterface;
import com.oldhigh.vipvideo.http.intercept.CacheIntercept;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/8/1 0001.
 * 网络请求的管理类 ， 用来获取retrofit实例，然后进行网络请求，
 * 一些网络的设置就在这里进行初始化配置
 */

public class HttpManager {

    private static HttpManager mHttpManager = null;

    private OkHttpClient mOkHttpClient;

    public APIInterface mAPIInterface;

    //私有化
    private HttpManager() {
    }

    private HttpManager(OkHttpClient okHttpClient) {

        File cacheFile = new File(App.getContext().getExternalCacheDir(), "cache");
        //缓存大小为10M
        int cacheSize = 10 * 1024 * 1024;
        //创建缓存对象
        Cache cache = new Cache(cacheFile, cacheSize);
        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(20, TimeUnit.SECONDS)
                    .addInterceptor(new CacheIntercept())
                    .cache(cache)
                    .build();
        } else {
            mOkHttpClient = okHttpClient;
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://131zy.vip/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(mOkHttpClient)
                .build();
        mAPIInterface = retrofit.create(APIInterface.class);


    }

    public static HttpManager getInstance() {
        return init(null);
    }

    public static HttpManager init(OkHttpClient okHttpClient) {
        if (mHttpManager == null) {
            synchronized (HttpManager.class) {
                if (mHttpManager == null) {
                    mHttpManager = new HttpManager(okHttpClient);
                }
            }
        }
        return mHttpManager;
    }

    /**
     * 直接调用这个就行了
     */
    public static APIInterface getAPIInterface() {

        return getInstance().mAPIInterface;
    }


}
