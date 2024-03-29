package com.axb.android.service;

import android.content.Context;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.axb.android.dto.CaseQuestion;
import com.axb.android.dto.Result;
import com.axb.android.ui.AnguiStudyActivity;
import com.axb.android.ui.CaseStudyActivity;

public class SaveCaseAnswerTask extends BaseAsyncTask {

	public SaveCaseAnswerTask(Context ctx, String title, String content,
			byte type) {
		super(ctx, title, content, type);
		// TODO Auto-generated constructor stub
	}

	@Override
	void customPreExcuteDoing() {
		// TODO Auto-generated method stub
		Context context = ctx.get();
		if(context != null &&
				context instanceof CaseStudyActivity){
			CaseStudyActivity mCaseStudyActivity = (CaseStudyActivity)context;
			mCaseStudyActivity.showRequestUi(content);
		}
		
		if(context != null &&
				context instanceof AnguiStudyActivity){
			AnguiStudyActivity mAnguiStudyActivity = (AnguiStudyActivity)context;
			mAnguiStudyActivity.showRequestUi(content);
		}
	}

	@Override
	void errorPositive() {
		// TODO Auto-generated method stub
		Context context = ctx.get();
		if(context != null &&
				context instanceof CaseStudyActivity){
			CaseStudyActivity mCaseStudyActivity = (CaseStudyActivity)context;
			mCaseStudyActivity.hideRequestUi();
		}
		
		if(context != null &&
				context instanceof AnguiStudyActivity){
			AnguiStudyActivity mAnguiStudyActivity = (AnguiStudyActivity)context;
			mAnguiStudyActivity.hideRequestUi();
		}
		
	}

	@Override
	void afterTask(Result result) {
		// TODO Auto-generated method stub
		Context mContext = ctx.get();
		if(mContext != null &&
				mContext instanceof CaseStudyActivity){
			CaseStudyActivity mCaseStudyActivity = (CaseStudyActivity)mContext;
			mCaseStudyActivity.hideRequestUi();
		}
		
		if(mContext != null &&
				mContext instanceof AnguiStudyActivity){
			AnguiStudyActivity mAnguiStudyActivity = (AnguiStudyActivity)mContext;
			mAnguiStudyActivity.hideRequestUi();
		}
		
		if(result.getSuccess()!=null && result.getSuccess().toLowerCase().equals("true")){
//			if(mContext != null &&
//					mContext instanceof CaseStudyActivity){
//				CaseStudyActivity mCaseStudyActivity = (CaseStudyActivity)mContext;
//				Toast.makeText(mContext, "保存答案成功！", Toast.LENGTH_SHORT).show();
//			} 
//			
//			if(mContext != null &&
//					mContext instanceof AnguiStudyActivity){
//				AnguiStudyActivity mAnguiStudyActivity = (AnguiStudyActivity)mContext;
//				mAnguiStudyActivity.hideRequestUi();
//			}
			
			Toast.makeText(mContext, "保存答案成功！", Toast.LENGTH_SHORT).show();
//			finish();
		}else{
//			Toast.makeText(ctx.get(), "请求消息失败", Toast.LENGTH_LONG).show();
			showNormalError("保存答案", result.getErrmessage());
		}
	}
	

}
