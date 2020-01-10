package com.xzc.car.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.xzc.car.R;
import com.xzc.car.beans.Car;
import com.xzc.car.utils.NetworkRequestTool;

//车辆出租申请
public class CarRentalActivity extends Activity {

    private ListView listView;
    //封装联系人对象
    private  CarAdapter carAdapter;
    List<Car> list=new ArrayList();
    private Integer userId;

    /**
     * 时间选择器
     */
    int mYear, mMonth, mDay,mM,mShi,mFen;
    Button btn,btn_2,btn_3,btn_4,commit;
    TextView dateDisplay,dateDisplay_2,dateDisplay_3,dateDisplay_4,TcarNumber,TcarType;
    final int DATE_DIALOG = 1,DATE_DIALOG2 = 2,DATE_DIALOG3 = 3,DATE_DIALOG4 = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_rental);

        commit=findViewById(R.id.button_choose);
        btn = (Button) findViewById(R.id.dateChoose);
        btn_2 = (Button) findViewById(R.id.dateChoose2);
        btn_3 = (Button) findViewById(R.id.dateChoose3);
        btn_4 = (Button) findViewById(R.id.dateChoose4);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay);
        dateDisplay_2 = (TextView) findViewById(R.id.dateDisplay2);
        dateDisplay_3 = (TextView) findViewById(R.id.dateDisplay3);
        dateDisplay_4 = (TextView) findViewById(R.id.dateDisplay4);
        TcarNumber=findViewById(R.id.rental_car_id);
        TcarType=findViewById(R.id.rental_car_xin);
        final String date=dateDisplay.getText().toString().trim();
        final String carNumber=TcarNumber.getText().toString().trim();
        final String carType=TcarType.getText().toString().trim();
        Intent intent=getIntent();
        userId=intent.getIntExtra("id",0);

        commit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
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
                            Integer status=jsonObject.getInteger("status");


                            if (200==200){
                                Log.e("200","200");
                                //
                                Toast.makeText(getApplication(), "发布成功", Toast.LENGTH_SHORT).show();

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
                networkRequestTool.getNetworkRequest("http://192.168.43.38:8080/releasecar?userId="+userId+"&carType="+carType+"&carNumber="+carNumber, null);
                finish();
            }
        });

        btn.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        btn_2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG2);
            }
        });
        btn_3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG3);
            }
        });
        btn_4.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG4);
            }
        });

       Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);




    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
            case DATE_DIALOG2:
                return new DatePickerDialog(this, mdateListener2, mYear, mMonth, mDay);
            case DATE_DIALOG3:
                return new DatePickerDialog(this, mdateListener3, mYear, mMonth, mDay);
            case DATE_DIALOG4:
                return new DatePickerDialog(this, mdateListener4, mYear, mMonth, mDay);

        }
        return null;
    }

    /**
     * 设置日期 利用StringBuffer追加
     */
    public void display() {
        dateDisplay.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display();
        }
    };
    public void display2() {
        dateDisplay_2.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display2();
        }
    };
    public void display3() {
        dateDisplay_3.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener3 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display3();
        }
    };
    public void display4() {
        dateDisplay_4.setText(new StringBuffer().append(mMonth + 1).append("-").append(mDay).append("-").append(mYear).append(" "));
    }

    private DatePickerDialog.OnDateSetListener mdateListener4 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            display4();
        }
    };

    private void release(){

    }
}

