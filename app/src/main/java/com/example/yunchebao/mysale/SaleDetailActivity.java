package com.example.yunchebao.mysale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cheyibao.model.OldCar;
import com.example.yunchebao.R;
import com.tool.MathUtil;
import com.tool.StringUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SaleDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_time)
    TextView tv_time;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_state)
    TextView tv_state;
    @BindView(R.id.tv_value1)
    TextView tv_value1;
    @BindView(R.id.tv_value2)
    TextView tv_value2;
    @BindView(R.id.tv_value3)
    TextView tv_value3;
    @BindView(R.id.tv_value4)
    TextView tv_value4;
    @BindView(R.id.tv_value5)
    TextView tv_value5;
    @BindView(R.id.tv_value6)
    TextView tv_value6;
    @BindView(R.id.tv_value7)
    TextView tv_value7;
    @BindView(R.id.iv_car)
    ImageView iv_car;
    @BindView(R.id.iv_photo1)
    ImageView iv_photo1;
    @BindView(R.id.iv_photo2)
    ImageView iv_photo2;
    @BindView(R.id.iv_photo3)
    ImageView iv_photo3;
    @BindView(R.id.rv_photo)
    RecyclerView rv_photo;
    @BindView(R.id.tv_des)
    TextView tv_des;
    CarPhotoAdapter carPhotoAdapter;
    NewPublish item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);
        ButterKnife.bind(this);
        item = (NewPublish) getIntent().getSerializableExtra("data");

        initView();
    }

    private void initView() {
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (item != null)
            setData();
    }

    private void setData() {
        String name = item.getFirstName() + " " + item.getSecondName() + " " + item.getThirdName();
        name = name.replace("null", "");
        tv_name.setText(name);
        if (item.getOldPrice() > 10000)
            tv_price.setText(MathUtil.getDoubleTwo((item.getOldPrice() / 10000)) + "万");
        else {
            tv_price.setText(MathUtil.getDoubleTwo(item.getOldPrice()) + "元");
        }
        tv_time.setText(item.getCreateTime().substring(0, 10));
        String imgs = item.getCarImage();
        if (!TextUtils.isEmpty(imgs)) {
            if (imgs.contains(",")) {
                String[] images = imgs.split(",");
                imgs = images[0];
            }
            Glide.with(this).load(imgs).into(iv_car);
            carPhotoAdapter=new CarPhotoAdapter(StringUtils.StringToArrayList(imgs,","));
            rv_photo.setLayoutManager(new LinearLayoutManager(this));
            rv_photo.setAdapter(carPhotoAdapter);
        }
        if (item.getAudit() == 3) {
            tv_state.setText("已拒绝");

        } else if (item.getAudit() == 2) {
            if (item.getState() == 1) {
                tv_state.setText("售卖中");

            } else {
                tv_state.setText("已完成");

            }

        }
        tv_value1.setText(item.getRegistrationAddress());
        tv_value2.setText(item.getRegistrationTime().substring(0,10));
        tv_value3.setText(item.getDistance() + "km");
        tv_value4.setText(item.getLastValidateCar().substring(0,10));
        tv_value5.setText(item.getInsuranceValidTime());
        tv_value6.setText(item.getChange() + "次");
        tv_value7.setText(item.getColor());
        tv_des.setText(item.getCarDescribe());
        Glide.with(this).load(item.getLinkmanRegistrationCertificate()).into(iv_photo1);
        Glide.with(this).load(item.getLinkmanDrivingLicense()).into(iv_photo2);
        Glide.with(this).load(item.getLinkmanBuyCarInvoice()).into(iv_photo3);
    }
}
