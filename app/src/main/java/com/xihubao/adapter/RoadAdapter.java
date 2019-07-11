package com.xihubao.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.tool.MathUtil;
import com.xihubao.model.Road;

import java.util.List;

/**
 * 作者：凌涛 on 2019/3/6 10:51
 * 邮箱：771548229@qq..com
 */
public class RoadAdapter extends BaseQuickAdapter<Road, BaseViewHolder> {
    public RoadAdapter(int layoutResId, @Nullable List<Road> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Road item) {
        ImageView iv_logo = helper.getView(R.id.iv_logo);
        TextView tv_name = helper.getView(R.id.tv_name);
//
//        SimpleRatingBar sb_score=helper.getView(R.id.sb_score);
        TextView tv_detail = helper.getView(R.id.tv_detail);
        TextView tv_content = helper.getView(R.id.tv_content);
        tv_content.setText(item.getServe());
        tv_detail.setText(item.getRemark());
        TextView tv_dis = helper.getView(R.id.tv_dis);
        tv_dis.setVisibility(View.GONE);
        tv_name.setText(item.getShopName());
//
//       // tv_addr.setText(item.getProvince()+item.getCity()+item.getArea()+item.getAddress());
//        tv_dis.setText(MathUtil.getDoubleTwo(item.getDistance())+"km");
//        sb_score.setRating((float) item.getScore());
//        tv_num.setText("评价"+item.getNumber()+"|"+"订单"+item.getOrderNum());
        Glide.with(helper.itemView.getContext()).load(item.getLogo()).into(iv_logo);
    }
}
