package com.xzc.car.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtils {

	public static void  makeText(Context context,String msg)
	{
		Toast.makeText(context, msg, 0).show();
	}
}
