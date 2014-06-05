package com.axb.android.service;

import java.util.List;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.axb.android.dto.MessageDto;
import com.axb.android.dto.Result;
import com.axb.android.ui.MessageListActivity;

public class MessageTask extends BaseAsyncTask {

	public MessageTask(Context ctx, String title, String content,
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
				context instanceof MessageListActivity){
			MessageListActivity mMessageListActivity = (MessageListActivity)context;
			mMessageListActivity.hideRequestUi();
		}
	}

	@Override
	void afterTask(Result result) {
		// TODO Auto-generated method stub
		Context mContext = ctx.get();
		if(mContext != null &&
				mContext instanceof MessageListActivity){
			MessageListActivity mMessageListActivity = (MessageListActivity)mContext;
			mMessageListActivity.hideRequestUi();
		}
		
		if(result.getSuccess()!=null && result.getSuccess().toLowerCase().equals("true")){
			List<MessageDto> lists = JSON.parseArray(result.getData(),MessageDto.class);
//			Page mPage = JSON.parseObject(result.getPage(), Page.class);
			Context context = ctx.get();
			if(context != null &&
					context instanceof MessageListActivity){
				MessageListActivity mMessageListActivity = (MessageListActivity)mContext;
				mMessageListActivity.taskSuccessDoing(lists, result.getPage());
			}
			
//			finish();
		}else{
//			Toast.makeText(ctx.get(), "请求消息失败", Toast.LENGTH_LONG).show();
			showNormalError("请求消息失败", result.getErrmessage());
		}
	}
	

}
