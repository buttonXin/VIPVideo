package com.oldhigh.vipvideo.http;


import com.oldhigh.vipvideo.http.api.APIInterface;

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

        if (okHttpClient == null) {
            mOkHttpClient = new OkHttpClient();
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
