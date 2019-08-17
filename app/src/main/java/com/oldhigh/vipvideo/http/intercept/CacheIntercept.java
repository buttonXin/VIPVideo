package com.oldhigh.vipvideo.http.intercept;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CacheIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        Response response1 = response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                //cache for 30 days
                .header("Cache-Control", "max-age=" + 3600 * 24 * 30)
                .build();
        return response1;
    }
}
