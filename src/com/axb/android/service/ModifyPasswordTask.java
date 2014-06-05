package com.axb.android.service;

import android.content.Context;

import com.axb.android.dto.Result;
import com.axb.android.ui.ModifyPwActivity;

public class ModifyPasswordTask extends BaseAsyncTask {

	public ModifyPasswordTask(Context ctx, String title, String content,
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
	}

	@Override
	void afterTask(Result result) {
		// TODO Auto-generated method stub
		Context mContext = ctx.get();
		
		if(result.getSuccess()!=null && result.getSuccess().toLowerCase().equals("true")){
			
			if(mContext != null &&
					mContext instanceof ModifyPwActivity){
				ModifyPwActivity mModifyPwActivity = (ModifyPwActivity)mContext;
				mModifyPwActivity.afterModifyTaskDoing();
			}
			
		}else{
//			Toast.makeText(ctx.get(), "«Î«Ûœ˚œ¢ ß∞‹", Toast.LENGTH_LONG).show();
			showNormalError("–ﬁ∏ƒ√‹¬Î ß∞‹", result.getErrmessage());
		}
	}
	

}
