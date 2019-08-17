package com.oldhigh.vipvideo.adapter;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.oldhigh.vipvideo.R;
import com.oldhigh.vipvideo.bean.VIPVideoBean;

/**
 * 描述：
 *
 * @author 老高
 * @date 2019/8/17
 */
public class SearchAdapter extends BaseQuickAdapter<VIPVideoBean, BaseViewHolder> {

    public SearchAdapter() {
        super(R.layout.item_home);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VIPVideoBean item) {

        helper.setText(R.id.tv_item_name, item.getName())
                .setText(R.id.tv_item_type, item.getType());


    }

}
