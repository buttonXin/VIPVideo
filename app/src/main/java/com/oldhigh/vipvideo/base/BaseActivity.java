package com.oldhigh.vipvideo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 描述：基类
 *
 * @author 老高
 * @date 2019/8/19
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID());
        initView();
        initEvent();
        initData();
    }

    protected abstract int layoutResID();

    protected abstract void initView();

    protected abstract void initEvent();

    protected abstract void initData();
}
