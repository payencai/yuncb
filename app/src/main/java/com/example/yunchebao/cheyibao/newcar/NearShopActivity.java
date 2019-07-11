package com.example.yunchebao.cheyibao.newcar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.cheyibao.model.Shop;
import com.costans.PlatformContans;
import com.example.yunchebao.MyApplication;
import com.example.yunchebao.R;
import com.google.gson.Gson;
import com.http.HttpProxy;
import com.http.ICallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NearShopActivity extends AppCompatActivity {
    @BindView(R.id.rv_shop)
    RecyclerView rv_shop;
    NearShopAdapter nearShopAdapter;
    List<Shop> shops;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_shop);
        ButterKnife.bind(this);
        id=getIntent().getStringExtra("id");
        initView();

    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        shops=new ArrayList<>();
        nearShopAdapter=new NearShopAdapter(shops);
        nearShopAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Shop shop=shops.get(position);
                Intent intent;
                switch (view.getId()){
                    case R.id.tv_sale:
                        intent =new Intent(NearShopActivity.this,NewCarShopActivity.class);
                        intent.putExtra("flag",1);
                        intent.putExtra("data",shop);
                        startActivity(intent);
                        break;
                    case R.id.tv_comment:
                        intent =new Intent(NearShopActivity.this,NewCarShopActivity.class);
                        intent.putExtra("flag",2);
                        intent.putExtra("data",shop);
                        startActivity(intent);
                        break;
                    case R.id.tv_service:
                        break;
                }
            }
        });
        rv_shop.setLayoutManager(new LinearLayoutManager(this));
        rv_shop.setAdapter(nearShopAdapter);
        getShop();
    }

    private void getShop() {

        Map<String, Object> params = new HashMap<>();
        params.put("longitude", MyApplication.getaMapLocation().getLongitude() + "");
        params.put("latitude", MyApplication.getaMapLocation().getLatitude() + "");
        params.put("carCategoryDetailId", id);
        HttpProxy.obtain().get(PlatformContans.NewCar.getMerchantList, params, MyApplication.token, new ICallBack() {
            @Override
            public void OnSuccess(String result) {
                Log.e("getMerchantList", result);
                try {
                    JSONObject jsonObject = new JSONObject(result);

                    JSONArray data = jsonObject.getJSONArray("data");
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject item = data.getJSONObject(i);
                        Shop shop = new Gson().fromJson(item.toString(), Shop.class);
                        shops.add(shop);
                    }
                    nearShopAdapter.setNewData(shops);
                    //updateData();

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
