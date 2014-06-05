package com.axb.android.util;

import android.util.Log;

public class MyLog {
	public static void printLog(Object obj,String msg){
		Log.i(obj.getClass().getSimpleName(), msg);
	}
}
