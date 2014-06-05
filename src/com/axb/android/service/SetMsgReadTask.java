package com.axb.android.service;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.axb.android.dto.Result;
import com.axb.android.ui.MessageInfoActivity;
import com.axb.android.ui.MessageListActivity;

public class SetMsgReadTask extends BaseAsyncTask {

	public SetMsgReadTask(Context ctx, String title, String content,
			byte type) {
		super(ctx, title, content, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	void customPreExcuteDoing() {
		// TODO Auto-generated method stub
		Context context = ctx.get();
		if(context != null &&
				context instanceof MessageListActivity){
			MessageListActivity mMessageListActivity = (MessageListActivity)context;
			mMessageListActivity.showRequestUi(content);
		}
		
	}

	@Override
	void errorPositive() {
		// TODO Auto-generated method stub
		Context context = ctx.get();
		if(context != null &&
				context instanceof MessageInfoActivity){
			MessageInfoActivity mMessageInfoActivity = (MessageInfoActivity)context;
			mMessageInfoActivity.hideRequestUi();
		}
	}

	@Override
	void afterTask(Result result) {
		// TODO Auto-generated method stub
		Context mContext = ctx.get();
		if(mContext != null &&
				mContext instanceof MessageInfoActivity){
			MessageInfoActivity mMessageInfoActivity = (MessageInfoActivity)mContext;
			mMessageInfoActivity.hideRequestUi();
		}
		if(result.getSuccess()!=null && result.getSuccess().toLowerCase().equals("true")){
			Toast.makeText(ctx.get(), "消息已经设置已读！", Toast.LENGTH_LONG).show();
			if(mContext != null &&
					mContext instanceof MessageInfoActivity){
				MessageInfoActivity mMessageInfoActivity = (MessageInfoActivity)mContext;
				mMessageInfoActivity.setResult(Activity.RESULT_OK);
			}
//			finish();
		}else{
//			Toast.makeText(ctx.get(), "请求消息失败", Toast.LENGTH_LONG).show();
			showNormalError("设置消息已读", result.getErrmessage());
		}
	}
	

}
