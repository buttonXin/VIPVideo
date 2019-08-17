package com.oldhigh.vipvideo.util;

import android.util.Log;

import java.util.Arrays;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

public class L {


    public static void d(Object... str) {

        Log.d(Thread.currentThread().getName(), Arrays.toString(str));
    }

    public static void e(Object... str) {
        Log.e(Thread.currentThread().getName(), Arrays.toString(str));

    }
}
