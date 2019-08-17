package com.oldhigh.vipvideo.activity;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.oldhigh.vipvideo.R;
import com.oldhigh.vipvideo.adapter.VideoItemAdapter;
import com.oldhigh.vipvideo.bean.EachVideoBean;
import com.oldhigh.vipvideo.http.HttpManager;
import com.oldhigh.vipvideo.http.RxHelper;
import com.oldhigh.vipvideo.regular.RegularHtml;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

public class VideoActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String HTML_URL = "HTML_URL";
    public static final String NAME = "NAME";
    private View mViewBack;
    private TextView mTvName;
    private ProgressBar mProgressBar;
    private AppCompatSeekBar mSeekBar;
    private TextView mTvGrade;
    private RecyclerView mRvGrade;
    private VideoView mVideoView;
    private VideoItemAdapter mVideoItemAdapter;
    private List<EachVideoBean> mEachVideoBeanList;
    private String mName;
    private String mHtmlUrl;


    public static void start(Context context, String htmlUrl, String name) {
        Intent intent = new Intent(context, VideoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(HTML_URL, htmlUrl);
        intent.putExtra(NAME, name);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_video);
        initView();

        initEvent();

        initData();


    }

    private void initView() {
        mViewBack = findViewById(R.id.iv_back);
        mTvName = findViewById(R.id.tv_name);
        mProgressBar = findViewById(R.id.pb_video);
        mSeekBar = findViewById(R.id.seek_bar);
        mTvGrade = findViewById(R.id.tv_grade);

        initVideo();

        mRvGrade = findViewById(R.id.rv_grade);
        mVideoItemAdapter = new VideoItemAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRvGrade.setLayoutManager(layoutManager);
        mRvGrade.setAdapter(mVideoItemAdapter);

        mRvGrade.setVisibility(View.GONE);

        visibleView(View.VISIBLE);

    }

    private void initVideo() {
        mVideoView = findViewById(R.id.video_view);


    }

    private void initEvent() {

        mViewBack.setOnClickListener(this);
        mTvName.setOnClickListener(this);
        mTvGrade.setOnClickListener(this);
        mVideoView.setOnClickListener(this);
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mProgressBar.setVisibility(View.GONE);
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                visibleView(View.VISIBLE);
                mRvGrade.setVisibility(View.VISIBLE);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (seekBarState == CLICK) {
                    int position = (int) (mVideoView.getDuration() * progress / 100f);
                    mVideoView.seekTo(position);
                }


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mVideoItemAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mVideoView.setVideoURI(Uri.parse(mEachVideoBeanList.get(position).getAddress()));
                mVideoView.start();
                if (mEachVideoBeanList.size() >= 2) {
                    mTvName.setText(mName + " " + mEachVideoBeanList.get(position).getGrade());
                }
            }
        });

    }

    private void visibleView(int visibleGone) {
        mViewBack.setVisibility(visibleGone);
        mTvGrade.setVisibility(visibleGone);
        mTvName.setVisibility(visibleGone);
        mSeekBar.setVisibility(visibleGone);

    }

    private void initData() {
        mHtmlUrl = getIntent().getStringExtra(HTML_URL);
        mName = getIntent().getStringExtra(NAME);

        mTvName.setText(mName);

        HttpManager.getAPIInterface().getIdentCodeCall(mHtmlUrl)
                .compose(RxHelper.<ResponseBody>ioToMain())
                .map(new Function<ResponseBody, List<EachVideoBean>>() {
                    @Override
                    public List<EachVideoBean> apply(ResponseBody responseBody) throws Exception {
                        return RegularHtml.regularEach(responseBody.string());
                    }
                })
                .subscribe(new Observer<List<EachVideoBean>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<EachVideoBean> eachVideoBeanList) {

                        mEachVideoBeanList = eachVideoBeanList;
                        mVideoItemAdapter.addData(mEachVideoBeanList);

                        if (eachVideoBeanList.size() > 0) {
                            mVideoView.setVideoURI(Uri.parse(eachVideoBeanList.get(0).getAddress()));
                            mVideoView.start();
                            resetSeek();
                        }
                        if (mEachVideoBeanList.size() >= 2) {
                            mTvName.setText(mName + eachVideoBeanList.get(0).getGrade());
                        }


                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    /**
     * 重新设置进度
     */
    public static final int INTERVAL = 1;
    public static final int CLICK = 2;
    private int seekBarState = CLICK;

    private void resetSeek() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Long aLong) {
                        seekBarState = INTERVAL;
                        if (mVideoView.isPlaying()) {
                            float duration = mVideoView.getDuration();
                            int currentPosition = mVideoView.getCurrentPosition();
                            float seekPosition = currentPosition / duration * 100;
                            if (mSeekBar != null) {
                                mSeekBar.setProgress((int) seekPosition);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.iv_back:
            case R.id.tv_name:
                finish();
                break;
            case R.id.tv_grade:
                mRvGrade.setVisibility(View.VISIBLE);
                break;
            case R.id.video_view:
                visibleView(View.VISIBLE);
                break;
            case R.id.seek_bar:
                seekBarState = CLICK;
                break;

            default:
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (mVideoView != null) {
            mVideoView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mVideoView != null) {
            mVideoView.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.stopPlayback();
        }
    }
}
