package com.xzc.car.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ListView;

import com.xzc.car.R;

public class CarShenHeActivity extends AppCompatActivity {
    private ListView shen_he;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_shen_he);
        shen_he=findViewById(R.id.shen_he);
        Adapter adapter=new Adapter(CarShenHeActivity.this,list);
    }
}
