package com.xzc.car.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xzc.car.R;
import com.xzc.car.beans.Car;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 刘道辉 on 2020/1/6.
 */

public class CarAdapter extends BaseAdapter {
    private Context context;
    private List<Car> cars = new ArrayList<>();

    public CarAdapter(List<Car> list) {
        this.cars=list;
    }

    public CarAdapter(Context context, List<Car> cars) {
        this.context = context;
        this.cars = cars;
    }

    //子项个数
    public int getCount() {
        return cars.size();
    }

    // 返回子项对象
    @Override
    public Object getItem(int position) {
        return cars.get(position);
    }

    // 返回子项下标
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 创建 ViewHolder 类
    class ViewHolder {
        TextView car_id;
        TextView user_name;
        TextView car_xin;
        TextView no_time;
    }

    // 返回子项视图
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Car car = (Car) getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_apt, null);

            viewHolder = new ViewHolder();
            viewHolder.user_name = (TextView) view.findViewById(R.id.tv_name);
            viewHolder.car_id = (TextView) view.findViewById(R.id.tv_p2);
            viewHolder.car_xin = (TextView) view.findViewById(R.id.tv_c2);
            viewHolder.no_time = (TextView) view.findViewById(R.id.tv_time2);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.user_name.setText(car.getCarType());
        viewHolder.car_id.setText(car.getCarNumber());
        viewHolder.car_xin.setText(car.getCarType());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:m:s");
        Date date=car.getCarFreetimeend();
        viewHolder.no_time.setText(format.format(date));
        return view;
    }
}
