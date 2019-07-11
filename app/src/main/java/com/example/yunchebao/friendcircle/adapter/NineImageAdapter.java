package com.example.yunchebao.friendcircle.adapter;

import android.content.Context;

import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;

import java.util.List;

public class NineImageAdapter extends NineGridViewClickAdapter {

    public NineImageAdapter(Context context, List<ImageInfo> imageInfo) {
        super(context, imageInfo);
    }
}
