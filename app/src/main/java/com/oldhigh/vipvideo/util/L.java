package com.oldhigh.vipvideo.util;

import android.util.Log;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class L {


    public static void d(String str) {

        Log.d(Thread.currentThread().getName(), str);
    }

    public static void e(String str) {
        Log.e(Thread.currentThread().getName(), str);

    }
}
