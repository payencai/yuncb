package com.example.yunchebao.cheyibao.newcar;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.cheyibao.model.Shop;
import com.example.yunchebao.R;
import com.tool.MathUtil;

import java.util.List;

public class NearShopAdapter extends BaseQuickAdapter<Shop, BaseViewHolder> {
    public NearShopAdapter(@Nullable List<Shop> data) {
        super(R.layout.item_near_shop, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Shop item) {
        helper.addOnClickListener(R.id.tv_sale).addOnClickListener(R.id.tv_comment).addOnClickListener(R.id.tv_service);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_grade=helper.getView(R.id.tv_grade);
        TextView tv_dis=helper.getView(R.id.tv_dis);
        TextView tv_address=helper.getView(R.id.tv_address);

        tv_name.setText(item.getName());
        tv_grade.setText(item.getGrade()+"");
        tv_dis.setText(MathUtil.getDoubleTwo(item.getDistance())+"km");
        tv_address.setText(item.getProvince()+item.getCity()+item.getDistrict()+item.getAddress());
    }
}
