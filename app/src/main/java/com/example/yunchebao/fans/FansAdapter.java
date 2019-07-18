package com.example.yunchebao.fans;

import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.vipcenter.model.MyFocus;

import java.util.List;

/**
 * 作者：凌涛 on 2019/1/28 19:30
 * 邮箱：771548229@qq..com
 */
public class FansAdapter extends BaseQuickAdapter<MyFocus,BaseViewHolder> {
    public FansAdapter(int layoutResId, @Nullable List<MyFocus> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFocus item) {
        helper.addOnClickListener(R.id.tv_focus);
        ImageView iv_head=helper.getView(R.id.iv_head);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_focus=helper.getView(R.id.tv_focus);
        helper.addOnClickListener(R.id.tv_chat);
        Glide.with(helper.itemView.getContext()).load(item.getHeadPortrait()).into(iv_head);
        tv_name.setText(item.getName());
        if(item.getIsFocus()==0){
            tv_focus.setText("未关注");
        }else{
            tv_focus.setText("已关注");
        }

    }
}
