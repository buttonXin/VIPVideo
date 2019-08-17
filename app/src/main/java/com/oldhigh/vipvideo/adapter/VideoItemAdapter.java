package com.oldhigh.vipvideo.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.oldhigh.vipvideo.R;
import com.oldhigh.vipvideo.bean.EachVideoBean;

/**
 * 描述：
 *
 * @author 老高
 * @date 2019/8/17
 */
public class VideoItemAdapter extends BaseQuickAdapter<EachVideoBean, BaseViewHolder> {

    public VideoItemAdapter() {
        super(R.layout.item_grade);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, EachVideoBean item) {

        helper.setText(R.id.tv_item_name, item.getGrade());


    }

}
