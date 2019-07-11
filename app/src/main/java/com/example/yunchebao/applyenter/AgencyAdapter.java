package com.example.yunchebao.applyenter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/4/30 11:07
 * 邮箱：771548229@qq..com
 */
public class AgencyAdapter extends BaseQuickAdapter<Agency, BaseViewHolder> {
    public AgencyAdapter(@Nullable List<Agency> data) {
        super(R.layout.item_agency_service, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Agency item) {
        TextView tv_dis=helper.getView(R.id.tv_dis);
        if(!TextUtils.isEmpty(item.getLatitude())){
            LatLng latLng2 = new LatLng(MyApplication.getaMapLocation().getLatitude(), MyApplication.getaMapLocation().getLongitude());
            LatLng latLng = new LatLng(Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude()));
            float distance = AMapUtils.calculateLineDistance(latLng, latLng2)/1000;
            tv_dis.setText("距离："+distance+"km");
        }else{
            tv_dis.setVisibility(View.GONE);
        }

        TextView tv_name=helper.getView(R.id.tv_name);

        tv_name.setText(item.getName());

        helper.addOnClickListener(R.id.select);
}
}
