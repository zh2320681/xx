package com.axb.android.ui;

import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.axb.android.R;
import com.axb.android.dto.BaseBo;
import com.axb.android.dto.User;
import com.axb.android.service.AllUserLevelTask;
import com.axb.android.service.Command;
import com.axb.android.ui.adapter.UserRateAdapter;
import com.axb.android.util.load.MyImageLoader;

public class RateLevelAcitvity extends BaseActivity{
	private Button backBtn;
	private ListView contentView;
	
	private View requestLayout;
	private TextView requestInfo;
	
	
	@Override
	protected void onBaseCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setContentView(R.layout.rate_level);
		
		init();
		addHandler();
		task();
	}
	
	
	private void init(){
		backBtn = (Button)findViewById(R.id.rl_back);
		contentView = (ListView)findViewById(R.id.rl_content);
		
		requestLayout = findViewById(R.id.load_requestLayout);
		requestInfo = (TextView)findViewById(R.id.load_requestInfo);
		requestLayout.setVisibility(View.GONE);
	}
	
	private void addHandler(){
		backBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	private void task(){
		AllUserLevelTask mAllUserLevelTask = new AllUserLevelTask(RateLevelAcitvity.this, "获取排名", "正在获取所有用户排名信息,请稍等...");
		BaseBo mBaseBo = new BaseBo();
		mBaseBo.requestUrl = Command.getAction(Command.COMMAND_LEVEL);
		mBaseBo.maps.put("userName", mApplication.mSetting.account);
		mBaseBo.maps.put("password", mApplication.mSetting.password);
		mAllUserLevelTask.execute(mBaseBo);
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

	public void taskSuccessDoing(List<User> allUser) {
		// TODO Auto-generated method stub
		MyImageLoader mImageLoader = MyImageLoader.newInstance(this, mApplication.mSetting.cacheDir);
		UserRateAdapter mUserRateAdapter = new UserRateAdapter(this, allUser, mImageLoader);
		contentView.setAdapter(mUserRateAdapter);
	}
}
