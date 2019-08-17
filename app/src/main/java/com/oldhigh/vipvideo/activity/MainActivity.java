package com.oldhigh.vipvideo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.oldhigh.vipvideo.R;
import com.oldhigh.vipvideo.adapter.SearchAdapter;
import com.oldhigh.vipvideo.bean.VIPVideoBean;
import com.oldhigh.vipvideo.http.HttpManager;
import com.oldhigh.vipvideo.http.RxHelper;
import com.oldhigh.vipvideo.regular.RegularHtml;
import com.oldhigh.vipvideo.util.L;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

import static com.oldhigh.vipvideo.constant.Constant.BASE_URL;

public class MainActivity extends AppCompatActivity {

    private Button mSearchBtn;
    private Button mLookHistoryBtn;
    private Button mCollectionBtn;
    private RecyclerView mRvHome;
    private SearchAdapter mSearchAdapter;
    private ProgressBar mProgressBar;
    private List<VIPVideoBean> mVideoBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        initEvent();

        refreshHtml();


    }

    private void refreshHtml() {
        HttpManager.getAPIInterface().getIdentCodeCall("http://131zy.vip/")
                .compose(RxHelper.<ResponseBody>ioToMain())
                .map(new Function<ResponseBody, List<VIPVideoBean>>() {
                    @Override
                    public List<VIPVideoBean> apply(ResponseBody responseBody) throws Exception {

                        return RegularHtml.regularSearch(responseBody.string());
                    }
                })
                .subscribe(new Observer<List<VIPVideoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(final List<VIPVideoBean> titleBeanList) {

                        mProgressBar.setVisibility(View.GONE);

                        mSearchAdapter = new SearchAdapter();
                        mVideoBeanList = titleBeanList;
                        mSearchAdapter.addData(mVideoBeanList);

                        mRvHome.setAdapter(mSearchAdapter);


                        mSearchAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {


                                VideoActivity.start(getApplicationContext(), mVideoBeanList.get(position).getAddress(), mVideoBeanList.get(position).getName());
                                L.e("----", mVideoBeanList.size(), mVideoBeanList.get(position).toString());
                            }
                        });


                    }

                    @Override
                    public void onError(Throwable e) {

                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void initView() {
        mSearchBtn = findViewById(R.id.btn_search);
        mLookHistoryBtn = findViewById(R.id.btn_look_history);
        mCollectionBtn = findViewById(R.id.btn_collection);
        mRvHome = findViewById(R.id.rv_home);
        mProgressBar = findViewById(R.id.pb);
        mProgressBar.setVisibility(View.VISIBLE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvHome.setLayoutManager(layoutManager);
    }

    private void initEvent() {

        mLookHistoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshHtml();
            }
        });
    }


}
