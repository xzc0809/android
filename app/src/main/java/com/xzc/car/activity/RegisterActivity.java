package com.xzc.car.activity;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.xzc.car.R;
import com.xzc.car.utils.NetworkRequestTool;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class RegisterActivity extends AppCompatActivity {

    private EditText Eusername;
    private EditText Epassword;
    private EditText Erepassword;
    private EditText Ename;
    private RadioGroup Esex;
    private EditText Etel;
    private RadioButton selectedRadio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView()
    {
        Eusername=findViewById(R.id.et_user_name);
        Epassword=findViewById(R.id.et_psw);
        Erepassword=findViewById(R.id.et_psw_again);
        Esex=findViewById(R.id.SexRadio);
        Etel=findViewById(R.id.Etel);

        Esex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
            {
                selectedRadio = findViewById(checkedId);

            }
        });
    }

    /**
     * 注册
     * */
    public void add(View v)
    {

        String username = Eusername.getText().toString().trim(); //获取姓名
        String password = Epassword.getText().toString().trim();
        String repassword = Erepassword.getText().toString().trim();

        String sex=selectedRadio.getText().toString().trim();
        String tel=Etel.getText().toString().trim();

        System.out.println("s"+sex);
        System.out.println("u"+username);
        System.out.println("p"+password);
        System.out.println("re"+repassword);
        System.out.println("tel"+tel);
        if(!password.equals(repassword)){
            Toast.makeText(getApplication(), "两次密码不一致，请重试", Toast.LENGTH_SHORT).show();
            return;
        }else {
            try {

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
                            JSONObject jsonObject = new JSONObject(resultString);
                            Integer status = jsonObject.getInt("status");
                            if (status == 200) {
                                Log.e("200", "200");
                                Toast.makeText(getApplication(), "注册成功请登陆", Toast.LENGTH_SHORT).show();
                                //
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(getApplication(), "失败，请重试", Toast.LENGTH_SHORT).show();
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
                networkRequestTool.getNetworkRequest("http://192.168.43.38:8080/register?userName=" + username + "&userPassword=" + password+"&userTel="+tel, null);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }


}
