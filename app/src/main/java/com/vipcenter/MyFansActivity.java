package com.vipcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.costans.PlatformContans;
import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;
import com.example.yunchebao.fans.FansAdapter;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.payencai.library.util.ToastUtil;
import com.tool.ActivityConstans;
import com.tool.UIControlUtils;
import com.vipcenter.adapter.FocusAdapter;
import com.vipcenter.model.MyFocus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imkit.RongIM;

public class MyFansActivity extends AppCompatActivity {

    List<MyFocus> mMyFocusList;
    FansAdapter mFocusAdapter;
    @BindView(R.id.rv_focus)
    RecyclerView rv_focus;
    int page=1;
    boolean isLoadMore=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_fans);
        UIControlUtils.UITextControlsUtils.setUIText(findViewById(R.id.title), ActivityConstans.UITag.TEXT_VIEW, "粉丝");
        ButterKnife.bind(this);
        initView();
    }
    @OnClick({R.id.back})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
        }
    }
    private void initView() {
        mMyFocusList=new ArrayList<>();
        mFocusAdapter=new FansAdapter(R.layout.item_focus,mMyFocusList);
        mFocusAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_focus);
        mFocusAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MyFocus myFocus = (MyFocus) adapter.getItem(position);
                if (view.getId() == R.id.tv_chat) {
                    RongIM.getInstance().startPrivateChat(MyFansActivity.this, myFocus.getUserId(), myFocus.getName());
                } else if (view.getId() == R.id.tv_focus) {
                    delfocus(myFocus.getUserId());

                }
            }
        });
        rv_focus.setLayoutManager(new LinearLayoutManager(this));
        rv_focus.setAdapter(mFocusAdapter);
        getData();
    }
    private void focus(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("otherId", userId);
        params.put("type", "1");
        HttpProxy.obtain().post(PlatformContans.User.addUserFocus, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("focus", result);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(MyFansActivity.this, "关注成功");
                        refresh();
                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtils.showLongToast(MyFansActivity.this, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(String error) {

            }
        });
    }

    private void refresh() {
        page = 1;
        mMyFocusList.clear();
        getData();
    }

    private void delfocus(String userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("otherId", userId);
        HttpProxy.obtain().post(PlatformContans.User.deleteUserFocus, MyApplication.token, params, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("focus", result);
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        ToastUtil.showToast(MyFansActivity.this, "取消成功");
                        refresh();
                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtils.showLongToast(MyFansActivity.this, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
    private void getData(){
        Map<String,Object> params=new HashMap<>();
        params.put("page",page);
        HttpProxy.obtain().get(PlatformContans.User.getOtherFocusList, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("result",result);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONArray data = jsonObject.getJSONArray("data");
                        for (int i = 0; i < data.length(); i++) {
                            JSONObject item = data.getJSONObject(i);
                            MyFocus carOrder = new Gson().fromJson(item.toString(), MyFocus.class);
                            mMyFocusList.add(carOrder);
                        }
                        if (isLoadMore) {
                            isLoadMore = false;
                            mFocusAdapter.addData(mMyFocusList);
                        } else {
                            mFocusAdapter.setNewData(mMyFocusList);
                            mFocusAdapter.loadMoreEnd(true);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        });
    }
}
