package com.axb.android.service;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.axb.android.dto.Result;
import com.axb.android.dto.User;
import com.axb.android.ui.LauncherActivity;
import com.axb.android.ui.LoginActivity;

public class LoginTask extends BaseAsyncTask {

	public LoginTask(Context ctx, String title, String content,
			byte type) {
		super(ctx, title, content, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	void customPreExcuteDoing() {
		// TODO Auto-generated method stub

	}

	@Override
	void errorPositive() {
		// TODO Auto-generated method stub
		Context context = ctx.get();
		if(context != null
				&& context instanceof LauncherActivity){
			LauncherActivity mLauncherActivity = (LauncherActivity)context;
			
			Intent i = new Intent();
			i.setClass(mLauncherActivity, LoginActivity.class);
			mLauncherActivity.startActivity(i);
			mLauncherActivity.finish();
		}
	}

	@Override
	void afterTask(Result result) {
		// TODO Auto-generated method stub
		Context context = ctx.get();
		if(result.getSuccess()!=null && result.getSuccess().toLowerCase().equals("true")){
			User mUser = JSON.parseObject(result.getData(),User.class);
			if(context != null){
				if(context instanceof LoginActivity){
//					LoginActivity mLoginActivity = (LoginActivity)context;
//					mLoginActivity.loginTaskSuccessDoing(mUser);
				}else if(context instanceof LauncherActivity){
					LauncherActivity mLauncherActivity = (LauncherActivity)context;
					mLauncherActivity.loginTaskSuccessDoing(mUser);
				}
				
			}
			
//			finish();
		}else{
			Toast.makeText(ctx.get(), "×Ô¶¯µÇÂ¼Ê§°Ü", Toast.LENGTH_LONG).show();
			if(context != null
					&& context instanceof LauncherActivity){
				LauncherActivity mLauncherActivity = (LauncherActivity)context;
				
				Intent i = new Intent();
				i.setClass(mLauncherActivity, LoginActivity.class);
				mLauncherActivity.startActivity(i);
				mLauncherActivity.finish();
			}
			
		}
	}

}
