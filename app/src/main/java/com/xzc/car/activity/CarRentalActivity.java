package com.xzc.car.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;
import com.xzc.car.R;
//车辆出租申请
public class CarRentalActivity extends Activity {



    /**
     * 时间选择器
     */
    int mYear, mMonth, mDay,mM,mShi,mFen;
    Button btn,btn_2,btn_3,btn_4;
    TextView dateDisplay,dateDisplay_2,dateDisplay_3,dateDisplay_4;
    final int DATE_DIALOG = 1,DATE_DIALOG2 = 2,DATE_DIALOG3 = 3,DATE_DIALOG4 = 4;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.dateChoose);
        btn_2 = (Button) findViewById(R.id.dateChoose2);
        btn_3 = (Button) findViewById(R.id.dateChoose3);
        btn_4 = (Button) findViewById(R.id.dateChoose4);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay);
        dateDisplay_2 = (TextView) findViewById(R.id.dateDisplay2);
        dateDisplay_3 = (TextView) findViewById(R.id.dateDisplay3);
        dateDisplay_4 = (TextView) findViewById(R.id.dateDisplay4);


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
}

