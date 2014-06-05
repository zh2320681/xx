package com.axb.android.util;

import android.content.Context;
import android.content.Intent;

import com.axb.android.ui.ShowWebImageActivity;

public class JavascriptInterface {
	private Context context;

	public JavascriptInterface(Context context) {
		this.context = context;
	}

	public void openImage(String img) {
		System.out.println(img);
		//
		Intent intent = new Intent();
		intent.putExtra("image", img);
		intent.setClass(context, ShowWebImageActivity.class);
		context.startActivity(intent);
		System.out.println(img);
	}
}
