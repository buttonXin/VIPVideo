package com.oldhigh.vipvideo;

import android.app.Application;
import android.content.Context;

import com.kk.taurus.exoplayer.ExoMediaPlayer;
import com.kk.taurus.playerbase.config.PlayerConfig;
import com.kk.taurus.playerbase.config.PlayerLibrary;

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();


        mContext = this;

        //如果您想使用默认的网络状态事件生产者，请添加此行配置。
        //并需要添加权限 android.permission.ACCESS_NETWORK_STATE
        PlayerConfig.setUseDefaultNetworkEventProducer(true);
        //初始化库
        PlayerLibrary.init(this);

        //-------------------------------------------

        //如果添加了'cn.jiajunhui:exoplayer:xxxx'该依赖
        ExoMediaPlayer.init(this);

    }


    public static Context getContext() {
        return mContext;
    }
}
