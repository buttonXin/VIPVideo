package com.oldhigh.vipvideo.util;

import android.widget.Toast;

import com.oldhigh.vipvideo.App;

public class ToastUtil {


    private static Toast sToast;

    public static void showShort(String text) {

        if (sToast == null) {
            sToast = Toast.makeText(App.getContext(), text, Toast.LENGTH_SHORT);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }

    public static void showLong(String text) {

        if (sToast == null) {
            sToast = Toast.makeText(App.getContext(), text, Toast.LENGTH_LONG);
        } else {
            sToast.setText(text);
        }
        sToast.show();
    }
}
