package com.cheyibao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cheyibao.model.RentShop;
import com.example.yunchebao.MyApplication;
import com.cheyibao.model.CarModelsFirstLevel;
import com.cheyibao.model.SubCarModels;
import com.cheyibao.util.RentCarUtils;
import com.cheyibao.view.RentCarAddressView;
import com.cheyibao.view.RentCarTimeView;
import com.common.AvoidOnResult;
import com.common.BaseModel;
import com.common.DateUtils;
import com.common.EndLoadDataType;
import com.common.HandlerData;
import com.common.IdentityCardVerify;
import com.common.LoadDataType;
import com.common.ResourceUtils;
import com.coorchice.library.SuperTextView;
import com.costans.PlatformContans;
import com.example.yunchebao.R;
import com.example.yunchebao.yuedan.SelectCarTypeActivity;
import com.google.gson.reflect.TypeToken;
import com.http.HttpProxy;
import com.http.ICallBack;
import com.payencai.library.util.ToastUtil;
import com.system.X5WebviewActivity;
import com.system.model.AddressBean;
import com.vipcenter.RegisterActivity;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.qqtheme.framework.picker.NumberPicker;

public class LongRentAppliationActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.searchLay)
    LinearLayout searchLay;
    @BindView(R.id.superText)
    SuperTextView superText;
    @BindView(R.id.textBtn)
    TextView textBtn;
    @BindView(R.id.shareBtn)
    ImageView shareBtn;
    @BindView(R.id.shopCartBtn)
    ImageView shopCartBtn;
    @BindView(R.id.menuBtn)
    ImageView menuBtn;
    @BindView(R.id.userBtn)
    ImageView userBtn;

    @BindView(R.id.rent_car_time_View)
    RentCarTimeView rentCarTimeView;
    @BindView(R.id.select_car_model_view)
    TextView selectCarModelView;
    @BindView(R.id.select_shop_count)
    TextView selectShopCount;
    @BindView(R.id.contact_person_name_et)
    EditText contactPersonNameEt;
    @BindView(R.id.contact_person_phone_et)
    EditText contactPersonPhoneEt;
    @BindView(R.id.identify_card_et)
    EditText identifyCardEt;
    @BindView(R.id.remarks_et)
    EditText remarksEt;
    @BindView(R.id.mergency_contact_person_name_et)
    EditText mergencyContactPersonNameEt;
    @BindView(R.id.emergency_contact_phone_et)
    EditText emergencyContactPhoneEt;
    @BindView(R.id.submit_application_tv)
    TextView submitApplicationTv;
    @BindView(R.id.is_to_home_service_view)
    CheckBox checkBox;
    @BindView(R.id.take_car_city_text_view)
    TextView tvGetCity;
    @BindView(R.id.take_car_address_text_view)
    TextView tvGetAddr;
    @BindView(R.id.return_the_car_city_text_view)
    TextView tvBackCity;
    @BindView(R.id.return_the_car_address_text_view)
    TextView tvBackAddr;
    @BindView(R.id.ll_get)
    LinearLayout ll_get;
    @BindView(R.id.ll_back)
    LinearLayout ll_back;
    private Context context;
    RentShop rentShop;
    AddressBean addressBean1;
    AddressBean addressBean2;
    private CarModelsFirstLevel carModelsFirstLevel;
    private CarModelsFirstLevel lastCarModelsFirstLevel;
    private SubCarModels.ParamBeanX paramBeanX;
    Boolean isOnlyAddress=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_rent_appliation);
        ButterKnife.bind(this);
        title.setText("长租申请");
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnlyAddress){
                    checkBox.setButtonDrawable(R.mipmap.carrental_btn_checkthe_normal);
                    isOnlyAddress=false;
                }else{
                    isOnlyAddress=true;
                    checkBox.setButtonDrawable(R.mipmap.carrental_btn_checkthe_selected);
                }
            }
        });
        ll_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnlyAddress){
                    Intent intent = new Intent(LongRentAppliationActivity.this, X5WebviewActivity.class);
                    startActivityForResult(intent,187);
                }else{
                    Intent intent = new Intent(LongRentAppliationActivity.this,ShopListActivity.class);
                    startActivityForResult(intent,188);
                }
            }
        });
        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOnlyAddress){
                    Intent intent = new Intent(LongRentAppliationActivity.this, X5WebviewActivity.class);
                    startActivityForResult(intent,189);
                }else{
                    Intent intent = new Intent(LongRentAppliationActivity.this,ShopListActivity.class);
                    startActivityForResult(intent,190);
                }
            }
        });
    }

    @OnClick(R.id.back)
    public void onBackClicked() {
        onBackPressed();
    }
    String carType;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(data!=null){
            switch (requestCode){
                case 200:
                    carType=data.getStringExtra("name");
                    selectCarModelView.setText(carType);
                    break;
                case 187:
                    addressBean1= (AddressBean) data.getSerializableExtra("address");
                    tvGetCity.setText(addressBean1.getCityname());
                    tvGetAddr.setText(addressBean1.getPoiaddress());
                    if(addressBean2==null){
                        addressBean2=addressBean1;
                        tvBackCity.setText(addressBean2.getCityname());
                        tvBackAddr.setText(addressBean2.getPoiaddress());
                    }
                    break;
                case 188:
                case 190:
                    rentShop=data.getParcelableExtra("rent_shop");
                    tvGetCity.setText(rentShop.getCity());
                    tvGetAddr.setText(rentShop.getAddress());
                    tvBackCity.setText(rentShop.getCity());
                    tvBackAddr.setText(rentShop.getAddress());
                    break;
                case 189:
                    addressBean2= (AddressBean) data.getSerializableExtra("address");
                    tvBackCity.setText(addressBean2.getCityname());
                    tvBackAddr.setText(addressBean2.getPoiaddress());
                    if(addressBean1==null){
                        addressBean1=addressBean2;
                        tvBackCity.setText(addressBean1.getCityname());
                        tvBackAddr.setText(addressBean1.getPoiaddress());
                    }
                    break;

            }

        }
    }

    @OnClick(R.id.select_car_model_view)
    public void onSelectCarModelViewClicked() {
        startActivityForResult(new Intent(LongRentAppliationActivity.this,SelectCarTypeActivity.class),200);

    }

    @OnClick(R.id.select_shop_count)
    public void onSelectShopCountClicked() {
        NumberPicker numberPicker = new NumberPicker(this);
        numberPicker.setRange(1,10);
        String index = selectShopCount.getText().toString();
        if (TextUtils.isEmpty(index)){
            numberPicker.setSelectedItem(1);
        }else {
            numberPicker.setSelectedItem(Integer.parseInt(index));
        }
        numberPicker.setOnNumberPickListener(new NumberPicker.OnNumberPickListener() {
            @Override
            public void onNumberPicked(int index, Number item) {
                selectShopCount.setText(String.format("%s",item));
            }
        });
        numberPicker.setItemWidth(100);
        numberPicker.setDividerColor(ResourceUtils.getColorByResource(context,R.color.split_line_color));
        numberPicker.setTopLineColor(ResourceUtils.getColorByResource(context,R.color.split_line_color));
        numberPicker.setTextColor(ResourceUtils.getColorByResource(context,R.color.yellow_65),ResourceUtils.getColorByResource(context,R.color.gray_99));
        numberPicker.setCancelTextColor(ResourceUtils.getColorByResource(context,R.color.yellow_65));
        numberPicker.setSubmitTextColor(ResourceUtils.getColorByResource(context,R.color.yellow_65));
        numberPicker.setPressedTextColor(ResourceUtils.getColorByResource(context,R.color.yellow_65));
        numberPicker.setTextSize(20);
        numberPicker.show();
    }



    @OnClick(R.id.submit_application_tv)
    public void onSubmitApplicationTvClicked() {
        if (MyApplication.isLogin){
            loadDataType.submitData();
        }else {
            AvoidOnResult avoidOnResult = new AvoidOnResult(this);
            avoidOnResult.startForResult(RegisterActivity.class, 1, (requestCode, resultCode, data) -> {
                loadDataType.submitData();
            });
        }
    }

    private LoadDataType loadDataType = new LoadDataType() {
        @Override
        public Map<String, Object> initParam() {
            Map<String,Object> map = new HashMap<>();
            String name = contactPersonNameEt.getText().toString();
            if(rentShop==null&&addressBean1==null){
                ToastUtil.showToast(context,"请选择地址");
                return null;
            }
            if (TextUtils.isEmpty(name)){
                ToastUtil.showToast(context,"联系人姓名不能为空");
                contactPersonNameEt.requestFocus();
                return null;
            }
            String telephone = contactPersonPhoneEt.getText().toString();
            if (TextUtils.isEmpty(telephone)){
                ToastUtil.showToast(context,"联系人电话不能为空");
                return null;
            }
            if (telephone.length()<11){
                ToastUtil.showToast(context,"请输入11位电话号码");
                return null;
            }
            String callName = mergencyContactPersonNameEt.getText().toString();
            if (TextUtils.isEmpty(callName)){
                ToastUtil.showToast(context,"紧急联系人姓名不能为空");
                return null;
            }
            String callTelephone = emergencyContactPhoneEt.getText().toString();
            if (TextUtils.isEmpty(callTelephone)){
                ToastUtil.showToast(context,"紧急联系人电话不能为空");
                return null;
            }
            if (callTelephone.length()<11){
                ToastUtil.showToast(context,"请输入11位手机号");
                return null;
            }
            String idNumber = identifyCardEt.getText().toString();
            if (TextUtils.isEmpty(idNumber)){
                ToastUtil.showToast(context,"联系人身份证号不能为空");
                return null;
            }
            if (idNumber.length()<11){
                ToastUtil.showToast(context,"请输入18位身份证号");
                return null;
            }

            if (!IdentityCardVerify.cardCodeVerify(idNumber)){
                ToastUtil.showToast(context,"请输入合法的身份证号码！");
                return null;
            }

            String shopNumber = selectShopCount.getText().toString();
           int shopCount = TextUtils.isEmpty(shopNumber)?0:(TextUtils.isDigitsOnly(shopNumber)?Integer.parseInt(shopNumber):0);
           if (shopCount<1){
               ToastUtil.showToast(context,"请选择报价商家数量");
               return null;
           }

            map.put("name",name);
            map.put("telephone",telephone);
            map.put("callName",callName);
            map.put("callTelephone",callTelephone);
            map.put("idNumber",idNumber);
            map.put("brand",carModelsFirstLevel==null ? "":carModelsFirstLevel.getName());
            map.put("carTategory",carType);
            map.put("variableBox",paramBeanX==null?"":paramBeanX.getVariableBox());
            map.put("seat",paramBeanX==null?"":paramBeanX.getSeat());
            map.put("image",carModelsFirstLevel==null ? "" : carModelsFirstLevel.getImage());
            map.put("remark",remarksEt.getText().toString());
            map.put("rentDay",rentCarTimeView.getDay());
            if(isOnlyAddress){
                map.put("isTake",1);
                map.put("takeCarLongitude",addressBean1.getLatlng().getLng());
                map.put("takeCarLatitude",addressBean1.getLatlng().getLat());
                map.put("takeCarAddress", addressBean1.getProvince()+addressBean1.getCityname()+addressBean1.getDistrict()+addressBean1.getPoiaddress());
                map.put("isReturn",1);
                map.put("returnCarLongitude", addressBean2.getLatlng().getLng());
                map.put("returnCarLatitude", addressBean2.getLatlng().getLat());
                map.put("returnCarAddress", addressBean2.getProvince()+addressBean2.getCityname()+addressBean2.getDistrict()+addressBean2.getPoiaddress());
            }else{
                map.put("isTake",2);
                map.put("takeCarLongitude",rentShop.getLongitude());
                map.put("takeCarLatitude",rentShop.getLatitude());
                map.put("takeCarAddress", rentShop.getProvince()+rentShop.getCity()+rentShop.getArea()+rentShop.getAddress());
                map.put("isReturn",2);
                map.put("returnCarLongitude",rentShop.getLongitude());
                map.put("returnCarLatitude",rentShop.getLatitude());
                map.put("returnCarAddress", rentShop.getProvince()+rentShop.getCity()+rentShop.getArea()+rentShop.getAddress());
            }
            map.put("takeCarTime", DateUtils.formatDateTime(rentCarTimeView.getStartTime(),"yyyy-MM-dd HH:mm:ss"));
            map.put("returnCarTime", DateUtils.formatDateTime(rentCarTimeView.getEndTime(),"yyyy-MM-dd HH:mm:ss"));
            if (MyApplication.getaMapLocation().getLongitude()==0){
                map.put("longitude", Objects.requireNonNull(map.get("takeCarLongitude")));
            }else {
                map.put("longitude", MyApplication.getaMapLocation().getLongitude());
            }
            if (MyApplication.getaMapLocation().getLatitude()==0){
                map.put("latitude", Objects.requireNonNull(map.get("takeCarLatitude")));
            }else {
                map.put("latitude", MyApplication.getaMapLocation().getLatitude());
            }
            map.put("shopNumber",shopCount);
            return map;
        }

        @Override
        public void submitData() {
            Map<String,Object> map = initParam();
            if (map ==null)return;
            HttpProxy.obtain().post(PlatformContans.CarRent.addRentCarApply,MyApplication.token, map, new ICallBack() {
                @Override
                public void OnSuccess(String result) {
                    Type type = new TypeToken<BaseModel<String>>(){}.getType();
                    HandlerData.handlerData(result, type, new EndLoadDataType<String>() {
                        @Override
                        public void onFailed() {
                            ToastUtil.showToast(LongRentAppliationActivity.this,"长租申请提交失败！");
                        }

                        @Override
                        public void onSuccess(String s) {
                            ToastUtil.showToast(LongRentAppliationActivity.this,s);
                            finish();
                        }

                        @Override
                        public void onSuccessBaseModel(BaseModel baseModel) {
                            super.onSuccessBaseModel(baseModel);
                            if (baseModel!=null){
                                ToastUtil.showToast(LongRentAppliationActivity.this,baseModel.getMessage());
                            }
                        }
                    });
                }

                @Override
                public void onFailure(String error) {
                    ToastUtil.showToast(LongRentAppliationActivity.this,"长租申请提交失败！");
                }
            });
        }
    };
}
