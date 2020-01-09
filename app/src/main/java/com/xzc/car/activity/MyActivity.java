package com.xzc.car.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.xzc.car.R;

public class MyActivity extends AppCompatActivity {
    TextView textView1;
    TextView textView2;
    TextView textView3;
    TextView textView4;
    private Integer id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        textView1=(TextView)findViewById(R.id.textView1);//我的预约
        textView2=(TextView)findViewById(R.id.textView2);//关于我们
        textView3=(TextView)findViewById(R.id.textView3);//发布消息
        textView4=(TextView)findViewById(R.id.textView4);//管理员审核

        Intent intent=getIntent();
         id=intent.getIntExtra("id",0);

        //文本框监听
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this,MyAppointmentActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(MyActivity.this,WeActivity.class);
                intent2.putExtra("id",id);
                startActivity(intent2);
            }
        });
        //发布车辆出租信息
        textView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(MyActivity.this,CarRentalActivity.class);
                intent3.putExtra("id",id);
                startActivity(intent3);
            }
        });
        //管理员审核
        textView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(MyActivity.this,CarShenHeActivity.class);
                intent4.putExtra("id",id);
                startActivity(intent4);
            }
        });

    }
}
