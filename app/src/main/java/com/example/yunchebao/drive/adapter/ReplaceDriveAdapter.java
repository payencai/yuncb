package com.example.yunchebao.drive.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.MyApplication;
import com.example.yunchebao.drive.model.ReplaceDrive;
import com.example.yunchebao.R;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.tool.MathUtil;
import com.willy.ratingbar.ScaleRatingBar;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/11 18:13
 * 邮箱：771548229@qq..com
 */
public class ReplaceDriveAdapter extends BaseQuickAdapter<ReplaceDrive,BaseViewHolder> {
    public ReplaceDriveAdapter(int layoutResId, @Nullable List<ReplaceDrive> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ReplaceDrive item) {
        ImageView iv_img=helper.getView(R.id.iv_img);

        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_grade=helper.getView(R.id.tv_grade);
        TextView tv_dis=helper.getView(R.id.tv_dis);
        TextView tv_address=helper.getView(R.id.tv_address);
        ScaleRatingBar simpleRatingBar=helper.getView(R.id.sb_score);
        simpleRatingBar.setRating(item.getScore());
        Glide.with(mContext).load(item.getLogo()).into(iv_img);
        tv_name.setText(item.getShopName());
        tv_grade.setText(""+item.getGrade());
        tv_address.setText(item.getAddress());
        if(item.getLatitude()!=null){
            LatLng latLng2 = new LatLng(MyApplication.getaMapLocation().getLatitude(), MyApplication.getaMapLocation().getLongitude());
            LatLng latLng = new LatLng(Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude()));
            float distance = AMapUtils.calculateLineDistance(latLng, latLng2)/1000;
            tv_dis.setText( "距离"+MathUtil.getDoubleTwo(distance) + "km");
        }else{
            tv_dis.setVisibility(View.GONE);
        }


    }
    public static String doubleTranString(double num)
    {
        if(num % 1.0 == 0)
        {
            return String.valueOf((long)num);
        }

        return String.valueOf(num);
    }
}
