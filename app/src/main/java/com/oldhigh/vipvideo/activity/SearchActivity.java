package com.oldhigh.vipvideo.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.oldhigh.vipvideo.R;
import com.oldhigh.vipvideo.base.BaseActivity;
import com.oldhigh.vipvideo.http.HttpManager;
import com.oldhigh.vipvideo.util.ToastUtil;

public class SearchActivity extends BaseActivity {

    private EditText mEditTextSearch;
    private Button mBtnSearch;

    @Override
    protected int layoutResID() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {

        mEditTextSearch = findViewById(R.id.et_search);
        mBtnSearch = findViewById(R.id.btn_search);
    }

    @Override
    protected void initEvent() {

        mBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = mEditTextSearch.getText().toString();
                if (TextUtils.isEmpty(text)) {
                    ToastUtil.showShort("内容不能为空");
                } else {

                    SearchResultActivity.start(getApplicationContext(), text);
                }
            }
        });
    }


    @Override
    protected void initData() {

    }
}
