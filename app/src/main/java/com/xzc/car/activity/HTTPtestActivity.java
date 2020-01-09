package com.xzc.car.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import com.xzc.car.R;
import com.xzc.car.utils.NetworkRequestTool;
import com.xzc.car.utils.ToastUtils;

public class HTTPtestActivity extends AppCompatActivity {

    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_httptest);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regUser();
            }
        });
    }

    public  void regUser(){

        NetworkRequestTool networkRequestTool = new NetworkRequestTool(new NetworkRequestTool.NetworkCallbackListener() {
            @Override
            public void networkSuccess(String resultString, List<NetworkRequestTool.ServerHeaderFieldBean> serverHeaderFieldBeanList) {

                for (int i = 0; i < serverHeaderFieldBeanList.size(); i++) {
                    NetworkRequestTool.ServerHeaderFieldBean serverHeaderFieldBean = serverHeaderFieldBeanList.get(i);

                    Log.e("----------->", serverHeaderFieldBean.getKey() + "   :  " + serverHeaderFieldBean.getValue());
                }

                try {
//                    JSONArray jsonArray=new JSONArray(resultString);
//                    JSONObject jsonObject=jsonArray.getJSONObject(0);
                    JSONObject jsonObject=new JSONObject(resultString);
                    Integer status=jsonObject.getInt("status");
                    if (status==200){
                        Log.e("200","200");

                        //

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
        networkRequestTool.getNetworkRequest("http://10.100.66.8:8080/user/login?username=1&password=1", null);

    }


    //消息处理工具类
    Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1) {
                if(msg.obj.toString().equals("OK")){

                    ToastUtils.makeText(HTTPtestActivity.this, "result:"+msg.obj.toString());

                    Intent intent=new Intent(HTTPtestActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
            }
        }
    };
}
