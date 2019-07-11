package com.example.yunchebao.mysale;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;

import java.util.List;

public class CarPhotoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public CarPhotoAdapter( @Nullable List<String> data) {
        super(R.layout.item_home, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        ImageView iv_logo=helper.getView(R.id.iv_logo);
        Glide.with(mContext).load(item).into(iv_logo);
    }
}
