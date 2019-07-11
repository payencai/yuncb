package com.tool;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.maning.imagebrowserlibrary.ImageEngine;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * author : maning
 * time   : 2018/04/10
 * desc   : PicassoImageEngine
 * version: 1.0
 */
public class PicassoImageEngine implements ImageEngine {

    @Override
    public void loadImage(Context context, String url, ImageView imageView, final View progressView) {
        Picasso.get()
                .load(url)
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressView.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressView.setVisibility(View.GONE);
                    }
                });
    }

}
