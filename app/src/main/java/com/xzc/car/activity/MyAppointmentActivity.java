package com.xzc.car.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

/**
 * 我的预约
 */
public class MyAppointmentActivity extends AppCompatActivity {
    private ListView listView;
    //封装联系人对象
    private  CarAdapter carsAdapter;
    List<Car> list=new ArrayList();
    private Integer userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointment);
        Intent intent=getIntent();
       userId =  intent.getIntExtra("id",0);

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
                    carsAdapter=new CarAdapter(MyAppointmentActivity.this,list);
                    for (Car car:list){

                        System.out.println(car.getCarState());
                        System.out.println(car.getCarType());
                        System.out.println(car.getCarId());
                    }

                    listView.setAdapter(carsAdapter);
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
        networkRequestTool.getNetworkRequest("http://192.168.43.38:8080/myreserve?renUserId="+userId, null);

    }
}
