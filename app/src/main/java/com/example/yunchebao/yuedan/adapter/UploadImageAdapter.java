package com.example.yunchebao.yuedan.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yunchebao.R;

import java.util.List;

/**
 * 作者：凌涛 on 2019/2/25 16:31
 * 邮箱：771548229@qq..com
 */
public class UploadImageAdapter extends BaseAdapter {
    Context mContext;
    List<String> images;

    public UploadImageAdapter(Context context, List<String> images) {
        mContext = context;
        this.images = images;
    }
    OnDelClickListener onDelClickListener;

    public void setOnDelClickListener(OnDelClickListener onDelClickListener) {
        this.onDelClickListener = onDelClickListener;
    }

    public interface OnDelClickListener{
        void onClick(int pos);
    }
    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return images.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(mContext).inflate(R.layout.view_evaluationchoicwimageitem,null);
        ImageView iv_logo= (ImageView) convertView.findViewById(R.id.item_regularevaluation_img_addimage);
        ImageView iv_del= (ImageView) convertView.findViewById(R.id.item_regularevaluation_img_deleteimage);
        if(position==0){
            iv_logo.setImageResource(R.mipmap.camera_default);
            iv_del.setVisibility(View.GONE);
        }else{
            Glide.with(mContext).load(images.get(position)).into(iv_logo);
            iv_del.setVisibility(View.VISIBLE);
        }
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDelClickListener.onClick(position);
            }
        });

        return convertView;
    }
}
