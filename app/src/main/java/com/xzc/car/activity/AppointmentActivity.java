package com.xzc.car.activity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.xzc.car.R;
import com.xzc.car.beans.Car;
import com.xzc.car.utils.NetworkRequestTool;

import javax.xml.transform.Source;


public class AppointmentActivity extends AppCompatActivity {
    private ListView listView;
    //封装联系人对象
    private  CarAdapter carsAdapter;
    List<Car> list=new ArrayList();
    private Button btn_mine;
    private Integer id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        listView=findViewById(R.id.LV_apt);
        btn_mine=findViewById(R.id.btn_mine);
        Intent intent=getIntent();
       id=intent.getIntExtra("id",0);

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
                    carsAdapter=new CarAdapter(AppointmentActivity.this,list);
                    for (Car car:list){

                        System.out.println(car.getCarState());
                        System.out.println(car.getCarType());
                        System.out.println(car.getCarId());
                    }

                    listView.setAdapter(carsAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Button btn_yvyue=view.findViewById(R.id.btn_yvyue);
                            btn_yvyue.setText("预约成功");

                            final EditText et = new EditText(null);
                            new AlertDialog.Builder(null).setTitle("请输入联系电话")
                                    .setPositiveButton("确定预约", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //按下确定键后的事件
                                            Toast.makeText(getApplicationContext(), "预约成功！",Toast.LENGTH_LONG).show();
                                        }
                                    }).setNegativeButton("取消",null).show();



                        }
                    });

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
        networkRequestTool.getNetworkRequest("http://192.168.43.38:8080/list", null);

        for (Car car:list){
            System.out.println(car.getCarType());
        }
    btn_mine.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(AppointmentActivity.this,MyActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        }
    });


    }
}
