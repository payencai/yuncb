package com.example.yunchebao.friendcircle.adapter;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.example.yunchebao.MyApplication;
import com.bbcircle.view.SampleCoverVideo;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.yunchebao.R;
import com.example.yunchebao.drive.activity.SimplePlayerActivity;
import com.example.yunchebao.friendcircle.FriendLocationActivity;
import com.example.yunchebao.friendcircle.NewFriendCircleActivity;
import com.example.yunchebao.friendcircle.model.GoodsShare;
import com.example.yunchebao.maket.GoodDetailActivity;
import com.example.yunchebao.maket.MarketSelectListActivity;
import com.example.yunchebao.maket.model.GoodList;
import com.example.yunchebao.view.NineGridTestLayout;
import com.example.yunchebao.washrepair.NewWashrepairDetailActivity;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.google.gson.Gson;
import com.luffy.imagepreviewlib.core.PictureConfig;
import com.lzy.ninegrid.ImageInfo;
import com.lzy.ninegrid.NineGridView;
import com.lzy.ninegrid.NineGridViewAdapter;
import com.lzy.ninegrid.preview.NineGridViewClickAdapter;
import com.maning.imagebrowserlibrary.ImageEngine;
import com.maning.imagebrowserlibrary.MNImageBrowser;
import com.maning.imagebrowserlibrary.listeners.OnClickListener;
import com.maning.imagebrowserlibrary.listeners.OnLongClickListener;
import com.maning.imagebrowserlibrary.listeners.OnPageChangeListener;
import com.maning.imagebrowserlibrary.model.ImageBrowserConfig;
import com.newversion.CircleData;


import com.newversion.DynamicCommonsAdapter;
import com.newversion.DynamicPicGridAdapter;
import com.newversion.InputTextMsgDialog;
import com.newversion.LikesView;
import com.newversion.MyGridView;
import com.payencai.library.view.CircleImageView;
import com.tool.ActivityAnimationUtils;
import com.tool.ActivityConstans;
import com.tool.LogUtil;
import com.tool.PicassoImageEngine;
import com.tool.listview.PersonalListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import indi.liyi.viewer.ImageViewer;

/**
 * 作者：凌涛 on 2019/4/26 17:30
 * 邮箱：771548229@qq..com
 */
public class CircleDataAdapter extends BaseMultiItemQuickAdapter<CircleData, BaseViewHolder> {
    private NewFriendCircleActivity friendsCircleActivity;


    /**
     * 动态类型  0 图片说说  1 小视频说说 2 转发链接  3 纯文字说说
     */

    private InputTextMsgDialog inputTextMsgDialog;

    public CircleDataAdapter( @Nullable List<CircleData> data) {
        super(data);
        addItemType(CircleData.TEXT, R.layout.item_friends_text);
        addItemType(CircleData.IMG, R.layout.item_friends_img);
        addItemType(CircleData.VIDEO, R.layout.item_friends_video);
        addItemType(CircleData.GOODS, R.layout.item_friends_goods);
    }

    public CircleDataAdapter(Context context, @Nullable List<CircleData> data) {
        this(data);
        friendsCircleActivity = (NewFriendCircleActivity) context;
    }
    private void bindCommomDataUI(BaseViewHolder helper, CircleData item){
        helper.addOnClickListener(R.id.iv_head);
        CircleImageView head = helper.getView(R.id.iv_head);
        TextView name = helper.getView(R.id.tv_name);
        TextView time = helper.getView(R.id.tv_time);
        TextView content = helper.getView(R.id.tv_dynamic_content);
        TextView tv_location = helper.getView(R.id.tv_location);
        ImageView iv_delete = helper.getView(R.id.iv_delete);
        ImageView iv_replay = helper.getView(R.id.iv_replay);
        LikesView likeView = helper.getView(R.id.likeView);
        PersonalListView lv_comment = helper.getView(R.id.lv_comment);
        ImageView ivPrise = helper.getView(R.id.iv_prise);
        List<CircleData.CommentListBean> commentListBeanList = new ArrayList<>();
        if (item.getCommentList() != null && item.getCommentList().size() > 0) {
            commentListBeanList.addAll(item.getCommentList());
        }
        DynamicCommonsAdapter commonsAdapter = new DynamicCommonsAdapter(mContext, commentListBeanList);
        lv_comment.setAdapter(commonsAdapter);
        if (!TextUtils.isEmpty(item.getAddress())) {
            tv_location.setText(item.getAddress());
        }
        tv_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(item.getLatitude())){
                    Intent intent=new Intent(mContext, FriendLocationActivity.class);
                    intent.putExtra("address",item.getAddress());
                    intent.putExtra("latitude",Double.parseDouble(item.getLatitude()));
                    intent.putExtra("longitude",Double.parseDouble(item.getLongitude()));
                    mContext.startActivity(intent);
                }

            }
        });
        if (item.getCommentList() != null && item.getCommentList().size() > 0) {
            lv_comment.setVisibility(View.VISIBLE);
        } else {
            lv_comment.setVisibility(View.GONE);
        }
        likeView.setListener(new LikesView.onItemClickListener() {
            @Override
            public void onItemClick(CircleData.ClickListBean bean) {
                String userId = bean.getUserId();
                // 跳转到他人朋友圈
                Intent intent = new Intent(mContext, NewFriendCircleActivity.class);
                intent.putExtra("userId", userId);
                mContext.startActivity(intent);
            }
        });
        if (item.getClickList() == null || item.getClickList().size() == 0) {
            likeView.setVisibility(View.GONE);
        } else {
            likeView.setVisibility(View.VISIBLE);
            likeView.setList(item.getClickList());
            likeView.notifyDataSetChanged();
        }
        Glide.with(mContext).load(item.getHeadPortrait()).into(head);
        name.setText(item.getName());
        time.setText(item.getCreateTime());
        if (TextUtils.isEmpty(item.getContent())) {
            content.setVisibility(View.GONE);
        } else {
            content.setVisibility(View.VISIBLE);
            content.setText(item.getContent());
        }
        if (TextUtils.equals(item.getUserId(), MyApplication.getUserInfo().getId())) {
            //我自己的朋友圈
            iv_delete.setVisibility(View.VISIBLE);
            iv_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    showDelMyFriend(mContext, iv_delete, item.getId(), helper.getAdapterPosition());
                }
            });
        } else {
            iv_delete.setVisibility(View.GONE);
        }
        iv_replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addReplay(item, helper.getAdapterPosition(), commonsAdapter);
            }
        });
        if (item.getIsClick().equals("1")) {
            ivPrise.setImageResource(R.mipmap.icon_praise_selected);
        } else {
            ivPrise.setImageResource(R.mipmap.icon_praise_normal);
        }
        ivPrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean didPraise;

                if (item.getIsClick().equals("1")) {
                    didPraise = false;
                    item.setIsClick("0");
                    ivPrise.setImageResource(R.mipmap.icon_praise_normal);

                    for (int i = 0; i < item.getClickList().size(); i++) {
                        if (item.getClickList().get(i).getUserId().equals(MyApplication.getUserInfo().getId())) {
                            item.getClickList().remove(i);
                        }
                    }

                } else {
                    didPraise = true;
                    item.setIsClick("1");
                    ivPrise.setImageResource(R.mipmap.icon_praise_selected);

                    CircleData.ClickListBean clickListBean = new CircleData.ClickListBean();
                    clickListBean.setHeadPortrait(MyApplication.getUserInfo().getHeadPortrait());
                    clickListBean.setName(MyApplication.getUserInfo().getName());
                    clickListBean.setUserId(MyApplication.getUserInfo().getId());

                    if (!item.getClickList().contains(clickListBean)) {
                        item.getClickList().add(clickListBean);
                    }
                }
                likeView.setList(item.getClickList());

                if (item.getClickList() == null || item.getClickList().size() == 0) {
                    likeView.setVisibility(View.GONE);
                } else {
                    likeView.setVisibility(View.VISIBLE);
                    likeView.notifyDataSetChanged();
                }
                //notifyItemChanged(helper.getAdapterPosition());
                friendsCircleActivity.performPraise(didPraise, item.getId());
            }
        });
    }
    @Override
    protected void convert(BaseViewHolder helper, CircleData item) {
        bindCommomDataUI(helper,item);
        switch (helper.getItemViewType()) {
            case CircleData.TEXT:
                break;
            case CircleData.IMG:
                NineGridTestLayout gv_nine = helper.getView(R.id.gv_nine);
                if (!TextUtils.isEmpty(item.getImgs())) {
                    List<String> dynamicPicList = Arrays.asList(item.getImgs().split(","));
                    gv_nine.setIsShowAll(false); //当传入的图片数超过9张时，是否全部显示
                    gv_nine.setSpacing(5); //动态设置图片之间的间隔
                    gv_nine.setUrlList(dynamicPicList); //最后再设置图片url
                    gv_nine.setVisibility(View.VISIBLE);
                } else {
                    gv_nine.setVisibility(View.GONE);
                }
                break;
            case CircleData.VIDEO:
                ImageView iv_video = helper.getView(R.id.iv_video);
                Glide.with(mContext).load(item.getVimg()).into(iv_video);
                iv_video.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, SimplePlayerActivity.class);
                        intent.putExtra("img", item.getVimg());
                        intent.putExtra("video", item.getVideo());
                        mContext.startActivity(intent);
                    }
                });
                break;
            case CircleData.GOODS:
                LinearLayout ll_goods = helper.getView(R.id.ll_goods);
                TextView tv_goods = helper.getView(R.id.tv_goods);
                ImageView iv_goods = helper.getView(R.id.iv_goods);
                if (!TextUtils.isEmpty(item.getUrl())) {
                    String json = item.getUrl();
                    GoodsShare goodsShare = new Gson().fromJson(json, GoodsShare.class);
                    tv_goods.setText(goodsShare.getShare_title());
                    Glide.with(mContext).load(goodsShare.getShare_image()).into(iv_goods);
                    ll_goods.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            GoodList goodList = new GoodList();
                            goodList.setId(goodsShare.getId());
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("data", goodList);
                            ActivityAnimationUtils.commonTransition(friendsCircleActivity, GoodDetailActivity.class, ActivityConstans.Animation.FADE, bundle);
                        }
                    });
                } else {
                    ll_goods.setVisibility(View.GONE);
                }
                break;
        }

    }

    /**
     * 展示删除动态弹窗
     */
    public void showDelMyFriend(Context context, View view, String circleId, int pos) {
        View contentView = LayoutInflater.from(context).inflate(
                R.layout.poppupwindow_delete_friends_circle, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, true);

        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                remove(pos);
                notifyDataSetChanged();
                friendsCircleActivity.deleteFriendsCircle(circleId);
            }
        });

        popupWindow.setTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);

        popupWindow.showAsDropDown(view, 0, 0, Gravity.RIGHT);

    }

    /**
     * 弹窗输入回复内容
     *
     * @param circleInfo
     */
    public void addReplay(CircleData circleInfo, int pos, DynamicCommonsAdapter commonsAdapter) {
        inputTextMsgDialog = new InputTextMsgDialog(mContext, R.style.dialog_center);
        inputTextMsgDialog.setHint("评论");
        inputTextMsgDialog.setmOnTextSendListener(new InputTextMsgDialog.OnTextSendListener() {
            @Override
            public void onTextSend(String msg) {
                friendsCircleActivity.addReplay(circleInfo.getId(), msg);
                CircleData.CommentListBean commentListBean = new CircleData.CommentListBean();
                commentListBean.setContent(msg);
                commentListBean.setName(MyApplication.getUserInfo().getName());
                commentListBean.setUserId(MyApplication.getUserInfo().getId());

                commentListBean.setType(1);//表示添加回复

                circleInfo.getCommentList().add(commentListBean);
                if (commonsAdapter != null) {
                    commonsAdapter.notifyDataSetChanged();
                }
                notifyItemChanged(pos);
            }
        });

        inputTextMsgDialog.show();
    }
}
