package com.axb.android.util;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyWebViewClient extends WebViewClient {
	
	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {

		return super.shouldOverrideUrlLoading(view, url);
	}

	@Override
	public void onPageFinished(WebView view, String url) {

		view.getSettings().setJavaScriptEnabled(true);

		super.onPageFinished(view, url);
		// html加载完成之后，添加监听图片的点击js函数
		addImageClickListner(view);

	}

	@Override
	public void onPageStarted(WebView view, String url, Bitmap favicon) {
		view.getSettings().setJavaScriptEnabled(true);

		super.onPageStarted(view, url, favicon);
	}

	@Override
	public void onReceivedError(WebView view, int errorCode,
			String description, String failingUrl) {

		super.onReceivedError(view, errorCode, description, failingUrl);

	}

	// 注入js函数监听
	private void addImageClickListner(WebView contentWebView) {
		// 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
		contentWebView.loadUrl("javascript:(function(){"
				+ "var objs = document.getElementsByTagName(\"img\"); "
				+ "for(var i=0;i<objs.length;i++)  " + "{"
				+ "    objs[i].onclick=function()  " + "    {  "
				+ "        window.imagelistner.openImage(this.src);  "
				+ "    }  " + "}" + "})()");
	}
}
