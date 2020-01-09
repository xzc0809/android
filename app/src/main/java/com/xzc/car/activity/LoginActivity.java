package com.xzc.car.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.xzc.car.R;
import com.xzc.car.utils.HttpUtil;
import com.xzc.car.utils.NetworkRequestTool;
import com.xzc.car.utils.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private Button login;
    private TextView register;
    private EditText Tusername;
    private EditText Tpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login=findViewById(R.id.login);
        register=findViewById(R.id.register);
        Tusername=findViewById(R.id.tusername);
        Tpassword=findViewById(R.id.tpassword);
       login.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               final String username=Tusername.getText().toString().trim();
               String password=Tpassword.getText().toString().trim();
               Log.e(username,username);
               Log.e(password,password);
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
                           Integer data=jsonObject.getInt("data");

                           Intent intent=new Intent(LoginActivity.this,AppointmentActivity.class);
                           if (status==200){
                               Log.e("200","200");
                            if(username.equals("admin")){
                                /**
                                 * 需要修改处
                                 */

                                intent.putExtra("userId",data);
                                startActivity(intent);
//                                startActivity(new Intent(LoginActivity.this,Admin.class));
                            }

                               /**
                                * 传值
                                */
                            intent.putExtra("userId",data);
                            startActivity(intent);
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
               networkRequestTool.getNetworkRequest("http://192.168.43.38:8080/login?userName="+username+"&userPassword="+password, null);



           }
       });

       register.setOnClickListener(new View.OnClickListener(){
           @Override
           public void onClick(View v) {

               startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
           }
       });

    }
}
