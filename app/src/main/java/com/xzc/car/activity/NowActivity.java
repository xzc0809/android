package com.xzc.car.activity;

import androidx.appcompat.app.AppCompatActivity;
import com.xzc.car.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class NowActivity extends AppCompatActivity {
    private Button btn_mine,btn_all,btn_now,btn_over;
    private ListView lv2_all;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_now);
        btn_all=findViewById(R.id.button_all);
        btn_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NowActivity.this,AllActivity.class);
                startActivity(intent);
            }
        });
        btn_now=findViewById(R.id.button_now);
        btn_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NowActivity.this,NowActivity.class);
                startActivity(intent);
            }
        });

        btn_over=findViewById(R.id.button_over);
        btn_over.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(NowActivity.this,OverActivity.class);
                startActivity(intent);
            }
        });
        lv2_all=findViewById(R.id.lv2_all);
    }
}
