package com.axb.android.service;

import android.content.Context;

import com.axb.android.dto.Result;
import com.axb.android.ui.PersonInfoActivity;

public class UpdateUserSignTask extends BaseAsyncTask {

	public UpdateUserSignTask(Context ctx, String title, String content) {
		super(ctx, title, content, BaseAsyncTask.PRE_TASK_NORMAL);
		// TODO Auto-generated constructor stub
	}

	@Override
	void customPreExcuteDoing() {
		// TODO Auto-generated method stub
		Context context = ctx.get();
	}

	@Override
	void errorPositive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void afterTask(Result result) {
		// TODO Auto-generated method stub
		Context mContext = ctx.get();
		
		if(result.getSuccess()!=null && result.getSuccess().toLowerCase().equals("true")){
			if(mContext != null &&
					mContext instanceof PersonInfoActivity){
				PersonInfoActivity mPersonInfoActivity = (PersonInfoActivity)mContext;
				mPersonInfoActivity.afterUpdateTaskDoing();
			}
		}else{
			
			showNormalError("设置消息已读", result.getErrmessage());
		}
	}
	

}
