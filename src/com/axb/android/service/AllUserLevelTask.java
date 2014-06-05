package com.axb.android.service;

import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.axb.android.dto.Result;
import com.axb.android.dto.User;
import com.axb.android.ui.RateLevelAcitvity;

public class AllUserLevelTask extends BaseAsyncTask {

	public AllUserLevelTask(Context ctx, String title, String content) {
		super(ctx, title, content, BaseAsyncTask.PRE_TASK_CUSTOM);
		// TODO Auto-generated constructor stub
	}

	@Override
	void customPreExcuteDoing() {
		// TODO Auto-generated method stub
		Context context = ctx.get();
		if(context != null &&
				context instanceof RateLevelAcitvity){
			RateLevelAcitvity mRateLevelAcitvity = (RateLevelAcitvity)context;
			mRateLevelAcitvity.showRequestUi(content);
		}
		
	}

	@Override
	void errorPositive() {
		// TODO Auto-generated method stub
		Context context = ctx.get();
		if(context != null &&
				context instanceof RateLevelAcitvity){
			RateLevelAcitvity mRateLevelAcitvity = (RateLevelAcitvity)context;
			mRateLevelAcitvity.hideRequestUi();
		}
	}

	@Override
	void afterTask(Result result) {
		// TODO Auto-generated method stub	
		if(result.getSuccess()!=null && result.getSuccess().toLowerCase().equals("true")){
			List<User> users = JSON.parseArray(result.getData(), User.class);
			Context context = ctx.get();
			if(context != null &&
					context instanceof RateLevelAcitvity){
				RateLevelAcitvity mRateLevelAcitvity = (RateLevelAcitvity)context;
				mRateLevelAcitvity.taskSuccessDoing(users);
			}
//			finish();
		}else{
//			Toast.makeText(ctx.get(), "请求消息失败", Toast.LENGTH_LONG).show();
			showNormalError("获取等级列表失败", result.getErrmessage());
		}
		
		Context mContext = ctx.get();
		if(mContext != null &&
				mContext instanceof RateLevelAcitvity){
			RateLevelAcitvity mRateLevelAcitvity = (RateLevelAcitvity)mContext;
			mRateLevelAcitvity.hideRequestUi();
		}
	}
	
	
	
	

}
