package com.axb.android.ui;

import android.content.Intent;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axb.android.R;
import com.axb.android.dto.BaseBo;
import com.axb.android.service.BaseAsyncTask;
import com.axb.android.service.Command;
import com.axb.android.service.MainCountTask;
import com.axb.android.service.VersionCheckTask;
import com.axb.android.ui.custom.MainItemLayout;
import com.axb.android.ui.custom.RoundImageView;

public class MainActivity extends BaseActivity {
	public static boolean isUpdateCount; // 是否更新数据
	// private Button
	// backBtn,unDoneBtn,recordBtn,safeBtn,personalBtn,reflashBtn,selfBtn;
	private Button backBtn;
	// private TextView unDoneView,safeMsgView;

	private RoundImageView mFace;// 圆形头像

	// 规定任务，案列借鉴，每日一题，自我学习，安全信息，排行榜
	private MainItemLayout mFixTask, mCaseReference, mQuestion, mStudy,
			mSafeInfo, mRanking;

//	private LinearLayout bottomView;
//	private View requestLayout;
//	private TextView requestInfo;

	private OnClickListener mClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == backBtn) {
				showExit();
				return;
			} else if (v == mFixTask) {
				// 规定任务
				Intent i = new Intent();
				i.putExtra("isUnDone", true);
				i.setClass(MainActivity.this, UndoneTaskActivity.class);
				startActivity(i);
			} else if (v == mCaseReference) {
				// 案列借鉴
				Intent i = new Intent();
				i.setClass(MainActivity.this, CaseReferenceActivity.class);
				startActivity(i);
			} else if (v == mQuestion) {
				// 每日一题
				Intent i = new Intent();
				i.setClass(MainActivity.this, DailyCaseActivity.class);
				startActivity(i);
			} else if (v == mStudy) {
				// 自我学习
				Intent i = new Intent();
				i.setClass(MainActivity.this, SelfMainActivity.class);
				startActivity(i);
			} else if (v == mSafeInfo) {
				// 安全信息
				Intent i = new Intent();
				i.setClass(MainActivity.this, MessageListActivity.class);
				startActivity(i);
			} else if (v == mRanking) {
				// 排行榜
				Intent i = new Intent();
				i.setClass(MainActivity.this, RankingActivity.class);
				i.putExtra("departGuid", "");
				i.putExtra("departName", "排行榜");
				startActivity(i);
			} else if (v == mFace) {
				// 个人中心
				Intent i = new Intent();
				i.setClass(MainActivity.this, PersonInfoActivity.class);
				startActivity(i);
			}
		}
	};

	@Override
	protected void initialize() {
		// TODO Auto-generated method stub

		isUpdateCount = true;
		init();

		VersionCheckTask mVersionCheckTask = new VersionCheckTask(this,
				"请求版本信息", "正在请求版本信息", false, new Handler());
		BaseBo mBaseBo = new BaseBo();
		mBaseBo.requestUrl = Command.getAction(Command.COMMAND_VERSION);
		mBaseBo.maps.put("userName", mApplication.mSetting.account);
		mBaseBo.maps.put("password", mApplication.mSetting.password);
		mVersionCheckTask.execute(mBaseBo);
	}

	private void init() {

		backBtn = (Button) findViewById(R.id.main_back);
		mFixTask = (MainItemLayout) findViewById(R.id.main_task);
		mCaseReference = (MainItemLayout) findViewById(R.id.main_case);
		mQuestion = (MainItemLayout) findViewById(R.id.main_question);
		mStudy = (MainItemLayout) findViewById(R.id.main_study);
		mSafeInfo = (MainItemLayout) findViewById(R.id.main_safe);
		mRanking = (MainItemLayout) findViewById(R.id.main_rank);
		mFace = (RoundImageView) findViewById(R.id.main_face);
		// bottomView = (LinearLayout) findViewById(R.id.main_bottomView);

		// requestLayout = findViewById(R.id.load_requestLayout);
		// requestInfo = (TextView) findViewById(R.id.load_requestInfo);
		// requestLayout.setVisibility(View.GONE);
		
	}

	@Override
	protected void addListener() {
		backBtn.setOnClickListener(mClick);
		mFixTask.setOnClickListener(mClick);
		mCaseReference.setOnClickListener(mClick);
		mQuestion.setOnClickListener(mClick);
		mStudy.setOnClickListener(mClick);
		mSafeInfo.setOnClickListener(mClick);
		mRanking.setOnClickListener(mClick);
		mFace.setOnClickListener(mClick);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (isUpdateCount) {
			isUpdateCount = false;

			MainCountTask mMainCountTask = new MainCountTask(this, "设置消息已读",
					"正在获取记录信息,请稍等...", BaseAsyncTask.PRE_TASK_CUSTOM);
			BaseBo mBaseBo = new BaseBo();
			mBaseBo.requestUrl = Command.getAction(Command.COMMAND_MAIN_COUNT);
			mBaseBo.maps.put("userName", mApplication.mSetting.account);
			mBaseBo.maps.put("password", mApplication.mSetting.password);
			mMainCountTask.execute(mBaseBo);
		} else {
			if (mApplication.unReadTask > 0) {
				mFixTask.getNumView().setText(mApplication.unReadTask + "");
			} else {
				mFixTask.getNumView().setVisibility(View.GONE);
			}

			if (mApplication.unReadMsg > 0) {
				mSafeInfo.getNumView().setText(mApplication.unReadMsg);
			} else {
				mSafeInfo.getNumView().setVisibility(View.GONE);
			}
		}
		
		if (mApplication.mLoginUser != null&& mApplication.mLoginUser.userimg != null) {
			mApplication.mImageLoader.displayImage(Command.getUserImgPath()+ mApplication.mLoginUser.userimg, mFace, false);
		}else{
			mFace.setBackgroundResource(R.drawable.male_face);
		}
		
		if(mApplication.mLoginUser.lastStudyTime != null && mApplication.mLoginUser.noStudyDays == 0){
			mQuestion.setImgSrc(R.drawable.main_question_done);
		}else{
			mQuestion.setImgSrc(R.drawable.main_question);
		}
	}

	private void showExit() {

		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
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
		// requestInfo.setText(info);
		// if (!requestLayout.isShown()) {
		// requestLayout.setVisibility(View.VISIBLE);
		// }
	}

	/**
	 * 隐藏请求时候 ui
	 * 
	 * @param info
	 */
	public void hideRequestUi() {
		// requestLayout.setVisibility(View.GONE);
	}

	public void taskSuccessDoing(int unReadTask, int unReadMsg,int unReadALJJ) {
		// TODO Auto-generated method stub
		if (unReadTask > 0) {
			mFixTask.getNumView().setVisibility(View.VISIBLE);
			mFixTask.getNumView().setText(unReadTask + "");
		} else {
			mFixTask.getNumView().setVisibility(View.GONE);
		}

		if (unReadMsg > 0) {
			mSafeInfo.getNumView().setVisibility(View.VISIBLE);
			mSafeInfo.getNumView().setText(unReadMsg + "");
		} else {
			mSafeInfo.getNumView().setVisibility(View.GONE);
		}
		
		if (unReadALJJ > 0) {
			mCaseReference.getNumView().setVisibility(View.VISIBLE);
			mCaseReference.getNumView().setText(unReadALJJ + "");
		} else {
			mCaseReference.getNumView().setVisibility(View.GONE);
		}
		mApplication.unReadMsg = unReadMsg;
		mApplication.unReadTask = unReadTask;
	}

}
