package com.xzc.car.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpUtil {
    public Message httpGet(String url1) {
        final String url2 = url1;
        Message msg=null;
        new Thread() {
            @Override
            public void run() {
                super.run();
                URL url = null;
                try {


                    url = new URL(url2);//请求路径
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
//

//                    conn.connect();


                    //获取后端发送的消息

                    InputStream is = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line = reader.readLine();
                    //对获取的数据进行解析
                    JSONObject object = new JSONObject(line);//解析数据
                    //获取msg
                    String status = object.getString("status");
                    String result=object.getString("msg");
                    Log.i("status", status);
                    Log.i("result",result);
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = result;//返回结果status
                    mhandler.sendMessage(msg);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }.start();
        return msg;
    }

    //消息处理工具类
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
}
