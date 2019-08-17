package com.oldhigh.vipvideo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;

import com.oldhigh.vipvideo.util.L;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        String text = "http://131zy.vip/";
        String text = "http://131zy.vip/?m=vod-detail-id-29494.html";

//        HttpManager.getAPIInterface().getIdentCode(text)
//                .compose(RxHelper.<String>ioToMain())
//                .subscribe(new Observer<String>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//
//                        L.e(s);
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


        OkHttpClient client = new OkHttpClient.Builder()
                .build();
        Request request = new Request.Builder()
                .url(text)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                L.e("xxx");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                L.e("okok");
                StringBuilder stringBuilder = new StringBuilder(12 * 1000 * 1000);
                stringBuilder.append(response.body().string());
                saveFile(stringBuilder.toString());
                L.e("--->" + stringBuilder.toString());
                L.e("okok");
            }
        });
    }

    private void saveFile(String string) {
        FileWriter fw = null;
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/video");
        if (!file.mkdirs()) {
            file.mkdirs();
        }
        File f = new File(file, "xxx.txt");
        try {


            fw = new FileWriter(f/*"/sdcard/video" + "/cmd.txt"*/);//SD卡中的路径
            fw.flush();
            fw.write(string);
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
