package com.axb.android.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.axb.android.R;
import com.axb.android.dto.BaseBo;
import com.axb.android.service.BaseAsyncTask;
import com.axb.android.service.Command;
import com.axb.android.service.MainCountTask;
import com.axb.android.service.VersionCheckTask;

public class MainActivity2 extends BaseActivity {
	public static boolean isUpdateCount; //是否更新数据 
	private Button backBtn,unDoneBtn,recordBtn,safeBtn,personalBtn,reflashBtn,selfBtn;
	private TextView unDoneView,safeMsgView;
	
	private View requestLayout;
	private TextView requestInfo;
	
	private OnClickListener mClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == backBtn){
				showExit();
				return;
			}else if(v == unDoneBtn){
				Intent i = new Intent();
				i.putExtra("isUnDone", true);
				i.setClass(MainActivity2.this, UndoneTaskActivity.class);
				startActivity(i);	
			}else if(v == recordBtn){
				Intent i = new Intent();
				i.setClass(MainActivity2.this, StudyRecordActivity.class);
				startActivity(i);
				
			}else if(v == safeBtn){
				Intent i = new Intent();
				i.setClass(MainActivity2.this, MessageListActivity.class);
				startActivity(i);
			}else if(v == personalBtn){
				Intent i = new Intent();
				i.setClass(MainActivity2.this, PersonInfoActivity.class);
				startActivity(i);
			}else if(v == reflashBtn){
				MainCountTask mMainCountTask = new MainCountTask(MainActivity2.this, "设置消息已读", "正在获取记录信息,请稍等...", BaseAsyncTask.PRE_TASK_CUSTOM);
				BaseBo mBaseBo = new BaseBo();
				mBaseBo.requestUrl = Command.getAction(Command.COMMAND_MAIN_COUNT);
				mBaseBo.maps.put("userName", mApplication.mSetting.account);
				mBaseBo.maps.put("password", mApplication.mSetting.password);
				mMainCountTask.execute(mBaseBo);
			}else if(v == selfBtn){
				//自学
				Intent i = new Intent();
				i.setClass(MainActivity2.this, SelfMainActivity.class);
				startActivity(i);
			}
		}
	};
	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.main);
		
		isUpdateCount = true;
		init();
		addListener();
		
		
		VersionCheckTask mVersionCheckTask = new VersionCheckTask(this, "请求版本信息", "正在请求版本信息", false, new Handler());
		BaseBo mBaseBo = new BaseBo();
		mBaseBo.requestUrl = Command.getAction(Command.COMMAND_VERSION);
		mBaseBo.maps.put("userName", mApplication.mSetting.account);
		mBaseBo.maps.put("password", mApplication.mSetting.password);
		mVersionCheckTask.execute(mBaseBo);
	}
	
	private void init(){
		backBtn = (Button)findViewById(R.id.main_back);
		unDoneBtn = (Button)findViewById(R.id.main_undone);
		recordBtn = (Button)findViewById(R.id.main_record);
		safeBtn = (Button)findViewById(R.id.main_safe);
		personalBtn = (Button)findViewById(R.id.main_personal);
		selfBtn = (Button)findViewById(R.id.main_self);
		
		reflashBtn = (Button)findViewById(R.id.main_reflash);
		
		unDoneView = (TextView)findViewById(R.id.main_undone_msg);
		safeMsgView = (TextView)findViewById(R.id.main_safe_msg);
		
//		unDoneView.setText(mApplication.unReadTask+"");
//		safeMsgView.setText(mApplication.unReadMsg+"");
		
		requestLayout = findViewById(R.id.load_requestLayout);
		requestInfo = (TextView)findViewById(R.id.load_requestInfo);
		requestLayout.setVisibility(View.GONE);
	}
	
	protected void addListener(){
		backBtn.setOnClickListener(mClick);
		unDoneBtn.setOnClickListener(mClick);
		recordBtn.setOnClickListener(mClick);
		safeBtn.setOnClickListener(mClick);
		personalBtn.setOnClickListener(mClick);
		reflashBtn.setOnClickListener(mClick);
		selfBtn.setOnClickListener(mClick);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isUpdateCount){
			isUpdateCount = false;
			
			MainCountTask mMainCountTask = new MainCountTask(this, "设置消息已读", "正在获取记录信息,请稍等...", BaseAsyncTask.PRE_TASK_CUSTOM);
			BaseBo mBaseBo = new BaseBo();
			mBaseBo.requestUrl = Command.getAction(Command.COMMAND_MAIN_COUNT);
			mBaseBo.maps.put("userName", mApplication.mSetting.account);
			mBaseBo.maps.put("password", mApplication.mSetting.password);
			mMainCountTask.execute(mBaseBo);
		}else{
			if(mApplication.unReadTask > 0){
				unDoneView.setText(mApplication.unReadTask+"");
			}else{
				unDoneView.setVisibility(View.GONE);
			}
			
			if(mApplication.unReadMsg > 0){
				safeMsgView.setText(mApplication.unReadMsg+"");
			}else{
				safeMsgView.setVisibility(View.GONE);
			}
		}
	}
	
	private void showExit(){
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		builder.setMessage("你确认退出程序?");
//		builder.setTitle("程序退出");
//		builder.setPositiveButton("确认",new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface arg0, int arg1) {
//				// TODO Auto-generated method stub
//				mApplication.mSetting.isAutoLogin = false;
//				mApplication.mSetting.account = "";
//				mApplication.mSetting.password="";
//				mApplication.mSetting.saveSettings(MainActivity.this);
//				
//				mApplication.mActivityManager.popAllActivity();
//				
//			}
//		} );
//		builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface arg0, int arg1) {
//				// TODO Auto-generated method stub
//			}
//		} );
//		builder.create().show();
		
		Intent intent=new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode == KeyEvent.KEYCODE_BACK){
			showExit();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	/**
	 * 显示请求时候 ui
	 * 
	 * @param info
	 */
	public void showRequestUi(String info) {
		requestInfo.setText(info);
		if (!requestLayout.isShown()) {
			requestLayout.setVisibility(View.VISIBLE);
			// requestView.setAnimation(AnimationUtils.loadAnimation(this,
			// R.anim.slide_right_in));
		}
	}

	/**
	 * 隐藏请求时候 ui
	 * 
	 * @param info
	 */
	public void hideRequestUi() {
		requestLayout.setVisibility(View.GONE);
	}

	public void taskSuccessDoing(int unReadTask,int unReadMsg ) {
		// TODO Auto-generated method stub
		if(unReadTask > 0){
			unDoneView.setVisibility(View.VISIBLE);
			unDoneView.setText(unReadTask+"");
		}else{
			unDoneView.setVisibility(View.GONE);
		}
		
		if(unReadMsg > 0){
			safeMsgView.setVisibility(View.VISIBLE);
			safeMsgView.setText(unReadMsg+"");
		}else{
			safeMsgView.setVisibility(View.GONE);
		}
		mApplication.unReadMsg = unReadMsg;
		mApplication.unReadTask = unReadTask;
	}
	
}
