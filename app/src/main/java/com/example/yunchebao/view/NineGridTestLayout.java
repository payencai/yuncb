package com.example.yunchebao.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.yunchebao.R;
import com.maning.imagebrowserlibrary.ImageEngine;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.listeners.OnClickListener;
import com.maning.imagebrowserlibrary.listeners.OnLongClickListener;
import com.maning.imagebrowserlibrary.listeners.OnPageChangeListener;
import com.maning.imagebrowserlibrary.model.ImageBrowserConfig;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.tool.PicassoImageEngine;

import java.util.ArrayList;
import java.util.List;

public class NineGridTestLayout extends NineGridLayout {
    public ImageBrowserConfig.TransformType transformType = ImageBrowserConfig.TransformType.Transform_Default;
    public ImageBrowserConfig.IndicatorType indicatorType = ImageBrowserConfig.IndicatorType.Indicator_Number;
    public ImageBrowserConfig.ScreenOrientationType screenOrientationType = ImageBrowserConfig.ScreenOrientationType.Screenorientation_Default;
    private ImageEngine imageEngine = new PicassoImageEngine();
    private int openAnim = R.anim.mn_browser_enter_anim;
    private int exitAnim = R.anim.mn_browser_exit_anim;
    protected static final int MAX_W_H_RATIO = 3;

    public NineGridTestLayout(Context context) {
        super(context);
    }

    public NineGridTestLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean displayOneImage(final RatioImageView imageView, String url, final int parentWidth) {

        ImageLoaderUtil.displayImage(mContext, imageView, url, ImageLoaderUtil.getPhotoImageOption(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap bitmap) {
                int w = bitmap.getWidth();
                int h = bitmap.getHeight();

                int newW;
                int newH;
                if (h > w * MAX_W_H_RATIO) {//h:w = 5:3
                    newW = parentWidth / 2;
                    newH = newW * 5 / 3;
                } else if (h < w) {//h:w = 2:3
                    newW = parentWidth * 2 / 3;
                    newH = newW * 2 / 3;
                } else {//newH:h = newW :w
                    newW = parentWidth / 2;
                    newH = h * newW / w;
                }
                setOneImageLayoutParams(imageView, newW, newH);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
        return false;
    }

    @Override
    protected void displayImage(RatioImageView imageView, String url) {
        ImageLoaderUtil.getImageLoader(mContext).displayImage(url, imageView, ImageLoaderUtil.getPhotoImageOption());
    }

    @Override
    protected void onClickImage(int i, String url, ArrayList<String> urlList, ImageView imageView) {
        //Toast.makeText(mContext, "点击了图片" + url, Toast.LENGTH_SHORT).show();
        MNImageBrowser.with(mContext)
                //页面切换效果
                .setTransformType(transformType)
                //指示器效果
                .setIndicatorType(indicatorType)
                //设置隐藏指示器
                .setIndicatorHide(false)
                //设置自定义遮盖层，定制自己想要的效果，当设置遮盖层后，原本的指示器会被隐藏

                //自定义ProgressView，不设置默认默认没有

                //当前位置
                .setCurrentPosition(i)
                //图片引擎
                .setImageEngine(imageEngine)
                //图片集合
                .setImageList(urlList)
                //方向设置
                .setScreenOrientationType(screenOrientationType)
                //点击监听
                .setOnClickListener(new com.maning.imagebrowserlibrary.listeners.OnClickListener() {
                    @Override
                    public void onClick(FragmentActivity activity, ImageView view, int position, String url) {

                    }
                })
                //长按监听
                .setOnLongClickListener(new com.maning.imagebrowserlibrary.listeners.OnLongClickListener() {
                    @Override
                    public void onLongClick(final FragmentActivity activity, final ImageView imageView, int position, String url) {
                        //showListDialog(activity, imageView);
                    }
                })
                //页面切换监听
                .setOnPageChangeListener(new OnPageChangeListener() {
                    @Override
                    public void onPageSelected(int position) {

                    }
                })
                //全屏模式
                .setFullScreenMode(false)
                //打开动画
                .setActivityOpenAnime(openAnim)
                //关闭动画
                .setActivityExitAnime(exitAnim)
                //关闭动画
                //显示：传入当前View
                .show(imageView);
    }
}
