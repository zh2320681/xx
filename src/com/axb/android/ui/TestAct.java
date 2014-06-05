package com.axb.android.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.axb.android.R;

public class TestAct extends BaseActivity {
	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.test);
		
		ImageView view = (ImageView)findViewById(R.id.img);
		long start = System.currentTimeMillis();
		view.setBackgroundResource(R.drawable.aa);
		long end = System.currentTimeMillis();
		System.out.println("=========>"+(end-start));
	}
}
