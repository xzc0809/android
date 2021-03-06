package com.xzc.car.activity;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xzc.car.R;
import com.xzc.car.beans.Car;
import com.xzc.car.utils.NetworkRequestTool;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class CarShenHeActivity extends AppCompatActivity {
    private ListView listView;
    //封装联系人对象
    private  CarAdater_shen carsAdapter_shen;
    List<Car> list=new ArrayList();
    private Button btn_mine;
    private Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_shen_he);
        listView=findViewById(R.id.shen_he);
        NetworkRequestTool networkRequestTool = new NetworkRequestTool(new NetworkRequestTool.NetworkCallbackListener() {

            @Override
            public void networkSuccess(String resultString, List<NetworkRequestTool.ServerHeaderFieldBean> serverHeaderFieldBeanList) {


                for (int i = 0; i < serverHeaderFieldBeanList.size(); i++) {
                    NetworkRequestTool.ServerHeaderFieldBean serverHeaderFieldBean = serverHeaderFieldBeanList.get(i);

                    Log.e("----------->", serverHeaderFieldBean.getKey() + "   :  " + serverHeaderFieldBean.getValue());
                }

                try {
//                    JSONArray jsonArray=new JSONArray(resultString);
                    JSONObject jsonObject = JSONObject.parseObject(resultString);
                    JSONArray data=jsonObject.getJSONArray("data");
                    list= JSON.parseArray(data.toJSONString(), Car.class);

                    /**
                     * 需要修改适配器的位置
                            */
                    carsAdapter_shen=new CarAdater_shen(CarShenHeActivity.this,list);
                    for (Car car:list){

                        System.out.println(car.getCarState());
                        System.out.println(car.getCarType());
                        System.out.println(car.getCarId());
                    }

                    /**
                     * 需要修改适配器的位置
                     */
                    listView.setAdapter(carsAdapter_shen);
                    if (200==200){
                        Log.e("200","200");
                        //

                    }else{
                        Toast.makeText(getApplication(), "用户名或密码错误，请重试", Toast.LENGTH_SHORT).show();
                    }
//                    button.setText(status);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
//                button.setText(resultString);
                Log.e("----------->", resultString);

            }

            @Override
            public void networkFailure() {
                Log.e("----------->", "网络失败");
            }

            @Override
            public void networkError(String errorString) {

                Toast.makeText(getApplication(), errorString, Toast.LENGTH_SHORT).show();
            }
        });
        networkRequestTool.getNetworkRequest("http://192.168.43.38:8080/uncheck", null);

    }
}
