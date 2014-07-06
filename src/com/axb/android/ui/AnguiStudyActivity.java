package com.axb.android.ui;

import java.lang.ref.WeakReference;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.axb.android.R;
import com.axb.android.dto.BaseBo;
import com.axb.android.dto.CaseDto;
import com.axb.android.dto.CaseQuestion;
import com.axb.android.service.BaseAsyncTask;
import com.axb.android.service.Command;
import com.axb.android.service.CommitCaseAnswerTask;
import com.axb.android.service.GetCaseContentTask;
import com.axb.android.service.GetCaseQuestionsTask;
import com.axb.android.util.CommonUtil;
import com.axb.android.util.LoadPaperUtil;

public class AnguiStudyActivity extends BaseActivity {
	private static final int ANGUI_INDEX = 0x01;
	private static final int TEST_INDEX = 0x02;
	private static final int ANSWER_INDEX = 0x03;
	
	private int selectIndex;
	
//	private boolean isSelf; //�Ƿ�����ѧ
	private int answerFlag;//��ת��ʶ
	
	private View testLayout,answerLayout,commitLayout;
	private WebView anguiLayout;
	private Button backBtn, anguiBtn, testBtn, answerBtn,commitBtn;
	
	private View requestLayout;
	private TextView requestInfo;
	private LinearLayout questionLayout;
	
	private CaseDto mCaseDto;
	private WeakReference<LayoutInflater> mInflaterCache;
	
	private boolean isHasLoadAnswer = false,isHasLoadQuestion = false;
	/**
	 * ��
	 */
	private LinearLayout answerContentLayout;
	private View answerImg;
	
	private OnClickListener myClick = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v == backBtn) {
				finish();
			} else if (v == anguiBtn) {
				setBtnSelectByIndex(ANGUI_INDEX);
			} else if (v == testBtn) {
				setBtnSelectByIndex(TEST_INDEX);
			} else if (v == answerBtn) {
				setBtnSelectByIndex(ANSWER_INDEX);
			} else if(v == commitBtn){
				AlertDialog.Builder builder = new AlertDialog.Builder(AnguiStudyActivity.this);
				builder.setTitle("�ύ��");
				builder.setMessage("�Ƿ��ύ��?");
				builder.setPositiveButton("�ύ", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						try {
							CommitCaseAnswerTask mCommitCaseAnswerTask = new CommitCaseAnswerTask(AnguiStudyActivity.this, "�ύ��", "�����ύ��,���Ե�..", BaseAsyncTask.PRE_TASK_CUSTOM);
							BaseBo mBaseBo = getTaskBaseBo(true);
							mCommitCaseAnswerTask.execute(mBaseBo);
						} catch (Exception e) {
							Toast.makeText(AnguiStudyActivity.this, "�㻹����Ŀû����,�����ύ!", Toast.LENGTH_LONG).show();
							
						}
						
					}
				});
				
				builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				builder.create().show();
				return;
			}
		}
	};
	
	@Override
	protected void initialize(){
		// TODO Auto-generated method stub
//		setContentView(R.layout.anguistudy); 
		
		mCaseDto = (CaseDto)getIntent().getSerializableExtra("TaskDto");
		answerFlag = getIntent().getIntExtra("answerFlag", SelfListActivity.SELF_FLAG);
		
		init();
		addHandler();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		try {
			anguiLayout.stopLoading();
		} catch (Exception e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
		}
	}
	
	private void init(){
		anguiLayout = (WebView)findViewById(R.id.ags_angui);
		testLayout = findViewById(R.id.ags_test);
		answerLayout = findViewById(R.id.ags_answerLayout);
		
		backBtn = (Button)findViewById(R.id.ags_back);
		anguiBtn = (Button)findViewById(R.id.ags_anguiBtn);
		testBtn = (Button)findViewById(R.id.ags_testBtn);
		answerBtn = (Button)findViewById(R.id.ags_answerBtn);
		
		commitBtn = (Button)findViewById(R.id.ags_commit);
		
		commitLayout = findViewById(R.id.ags_commitLayout);
		
		String content = mCaseDto.getContent(); 
		if(content != null){
			CommonUtil.webViewCommon(this, anguiLayout, content);
		}
		
		requestLayout = findViewById(R.id.load_requestLayout);
		requestInfo = (TextView)findViewById(R.id.load_requestInfo);
		requestLayout.setVisibility(View.GONE);
		
		questionLayout = (LinearLayout)findViewById(R.id.ags_questionLayout);
		
		answerContentLayout = (LinearLayout)findViewById(R.id.ags_answerContent);
		answerImg = findViewById(R.id.ags_answerImg);
		
		
		setBtnSelectByIndex(ANGUI_INDEX);
	}
	
	private void addHandler(){
		backBtn.setOnClickListener(myClick);
		anguiBtn.setOnClickListener(myClick);
		testBtn.setOnClickListener(myClick);
		answerBtn.setOnClickListener(myClick);
		commitBtn.setOnClickListener(myClick);
	}
	
	private void setBtnSelectByIndex(int index) {
		if(selectIndex == index){
			return;
		}
		int old = selectIndex;
		selectIndex = index;
		switch (old) {
		case ANGUI_INDEX:
			anguiBtn.setBackgroundResource(R.drawable.down_nor);
			anguiBtn.setTextColor(getResources().getColorStateList(R.color.undone_text1));
			anguiLayout.setVisibility(View.GONE);
			break;
		case TEST_INDEX:
			testBtn.setBackgroundResource(R.drawable.down_nor);
			testBtn.setTextColor(getResources().getColorStateList(R.color.undone_text1));
			testLayout.setVisibility(View.GONE);
			break;
		case ANSWER_INDEX:
			answerBtn.setBackgroundResource(R.drawable.down_nor);
			answerBtn.setTextColor(getResources().getColorStateList(R.color.undone_text1));
			answerLayout.setVisibility(View.GONE);
			break;
		}

		switch (selectIndex) {
		case ANGUI_INDEX:
			anguiBtn.setBackgroundResource(R.drawable.down_pre);
			anguiBtn.setTextColor(getResources().getColorStateList(R.color.undone_text2));
			anguiLayout.setVisibility(View.VISIBLE);
			String content = mCaseDto.getContent(); 
			if(content == null){
				GetCaseContentTask mGetCaseContentTask = new GetCaseContentTask(AnguiStudyActivity.this, "��ȡ����", "���ڻ�ȡ��������,���Ե�..", BaseAsyncTask.PRE_TASK_NORMAL);
				BaseBo mBaseBo = new BaseBo();
				mBaseBo.requestUrl = Command.getAction(Command.COMMAND_CASE_CONTENT);
				mBaseBo.maps.put("userName", mApplication.mSetting.account);
				mBaseBo.maps.put("password", mApplication.mSetting.password);
				mBaseBo.maps.put("caseGuid", mCaseDto.guid);
				mGetCaseContentTask.execute(mBaseBo);
			}
			break;
		case TEST_INDEX:
			testBtn.setBackgroundResource(R.drawable.down_pre);
			testBtn.setTextColor(getResources().getColorStateList(R.color.undone_text2));
			testLayout.setVisibility(View.VISIBLE);
//			if(CommonUtil.judgeIntAvild(mCaseDto.isFinish)){
//				//����Ѿ����  �������ύ
//				commitLayout.setVisibility(View.GONE);
//			}else{
			commitLayout.setVisibility(View.VISIBLE);
//			}
			getCaseQuestion();
			break;
		case ANSWER_INDEX:
			answerBtn.setBackgroundResource(R.drawable.down_pre);
			answerBtn.setTextColor(getResources().getColorStateList(R.color.undone_text2));
			answerLayout.setVisibility(View.VISIBLE);
			answerContentLayout.setVisibility(View.VISIBLE);
			answerImg.setVisibility(View.GONE);
			getCaseQuestion();
			break;
		}
	}
	
	
	private void getCaseQuestion(){
		//!CommonUtil.judgeIntAvild(mCaseDto.isFinish)
		if(mCaseDto != null && mCaseDto.allCaseQuestion == null){
			GetCaseQuestionsTask mGetCaseQuestionsTask = new GetCaseQuestionsTask(this, "��ȡ������Ŀ", "�������ȡ������Ŀ,���Ե�...", BaseAsyncTask.PRE_TASK_CUSTOM);
			BaseBo mBaseBo = new BaseBo();
			mBaseBo.requestUrl = Command.getAction(Command.COMMAND_QUESTIONS);
			mBaseBo.maps.put("userName", mApplication.mSetting.account);
			mBaseBo.maps.put("password", mApplication.mSetting.password); 
			mBaseBo.maps.put("caseGuid", mCaseDto.guid);
			mBaseBo.maps.put("taskGuid", mCaseDto.taskGuid);
			mBaseBo.maps.put("answerFlag",answerFlag+"");
			mGetCaseQuestionsTask.execute(mBaseBo);
		}else{
			loadCurrentView();
		}
	}
	
	
	/**
	 * ��ʾ����ʱ�� ui
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
	 * ��������ʱ�� ui
	 * 
	 * @param info
	 */
	public void hideRequestUi() {
		requestLayout.setVisibility(View.GONE);
	}
	
	
	public void afterTaskDoing(List<CaseQuestion> data){
		mCaseDto.allCaseQuestion = data;
		loadCurrentView();
		
	}
	
	
	private void loadCurrentView(){
		if(selectIndex == TEST_INDEX){
			if(!isHasLoadQuestion){
				LoadPaperUtil.loadPaperQuestions(this, getInflater(), mCaseDto, questionLayout,false);
				isHasLoadQuestion = true;
			}
		}else{
			if(!isHasLoadAnswer){
				LayoutInflater mInflater = getInflater();
				
//				for(CaseQuestion mCaseQuestion : mCaseDto.allCaseQuestion){
//					int count = answerContentLayout.getChildCount()+1;
//					TextView answerItemView = (TextView)mInflater.inflate(R.layout.answer_item, null);
//					answerItemView.setText(count+"�� "+mCaseQuestion.answer);
//					answerContentLayout.addView(answerItemView);
//				}
				
				for(CaseQuestion mCaseQuestion : mCaseDto.allCaseQuestion){
					int count = answerContentLayout.getChildCount()/2+1;
					TextView answerItemView = (TextView)mInflater.inflate(R.layout.answer_item, null);
					answerItemView.setText(count+"�� "+mCaseQuestion.questionname);
					
					TextView answerItemView1 = (TextView)mInflater.inflate(R.layout.answer_item, null);
//					answerItemView1.setText("��:  "+mCaseQuestion.answer);
					answerItemView1.setText(mCaseQuestion.answer);
					
					answerContentLayout.addView(answerItemView);
					answerContentLayout.addView(answerItemView1);
				}
//				LoadPaperUtil.loadPaperQuestions(this, getInflater(), mCaseDto, answerContentLayout,true);
				isHasLoadAnswer = true;
			}
			
		}
	}
	
	public void afterContentTaskDoing(String content){
		mCaseDto.setContent(content);
		CommonUtil.webViewCommon(this, anguiLayout, content);
	}
	
	public void afterCommitTaskDoing(){
		//���ؽ���
		mCaseDto.isFinish = 1;
		if (answerFlag == SelfListActivity.SELF_FLAG) {
			// ����ѧϰ
			SelfListActivity.isUpdateData = true;
			SelfMainActivity.isUpdateCount = true;
		} else if(answerFlag == SelfListActivity.NOTOK_FLAG){
			// ֪ͨ �������
			MainActivity.isUpdateCount = true;
			// ֪ͨδ���������
			UndoneTaskActivity.isUpdateData = true;
		}else if(answerFlag == SelfListActivity.DAILY_FLAG){
			//��ÿ��һ�������Ѿ�ѧϰ
			mApplication.mLoginUser.noStudyDays = 0;
		}
		
		//�Զ�������ҳ��
		setBtnSelectByIndex(ANSWER_INDEX);
		//disable �� ���ɱ༭
//		LoadPaperUtil.setUserAnswerLayoutDisable(questionLayout);
	}
	
	
	/**
	 * �õ�layout
	 * @param data
	 */
	private LayoutInflater getInflater(){
		LayoutInflater mInflater = null;
		if(mInflaterCache == null){
			mInflater = LayoutInflater.from(this);
			mInflaterCache = new WeakReference<LayoutInflater>(mInflater);
		}else{
			mInflater = mInflaterCache.get();
			if(mInflater == null){
				mInflater = LayoutInflater.from(this);
				mInflaterCache = new WeakReference<LayoutInflater>(mInflater);
			}
		}
		return mInflater;
	}
	
	
	/**
	 * ��ȡBaseBo
	 * @return
	 * @throws Exception 
	 */
	private BaseBo getTaskBaseBo(boolean isCommit) throws Exception {
		BaseBo mBaseBo = new BaseBo();
		mBaseBo.maps.put("userName",mApplication.mSetting.account);
		mBaseBo.maps.put("password",mApplication.mSetting.password);
		mBaseBo.maps.put("split",LoadPaperUtil.SPLIT_CHAR1);
		mBaseBo.maps.put("caseGuid",mCaseDto.guid);
		mBaseBo.maps.put("answer", LoadPaperUtil.getUserAnswer(questionLayout, mCaseDto.taskGuid,isCommit));
		
		switch (answerFlag) {
			case SelfListActivity.NOTOK_FLAG:
				//δ�������
				if(isCommit){
					//�ύ
					mBaseBo.requestUrl = Command.getAction(Command.COMMAND_COMMIT);
				}else{
					//����
					mBaseBo.requestUrl = Command.getAction(Command.COMMAND_SAVE);
				}
				mBaseBo.maps.put("taskGuid",mCaseDto.taskGuid);
				break;
			case SelfListActivity.SELF_FLAG:
				//��ѧ
				if(isCommit){
					mBaseBo.requestUrl = Command.getAction(Command.COMMAND_SELF_COMMIT);
				}else{
					mBaseBo.requestUrl = Command.getAction(Command.COMMAND_SELF_SAVE);
				}
				
				break;
			case SelfListActivity.DAILY_FLAG:
				//ÿ��һ��
				mBaseBo.requestUrl = Command.getAction(Command.COMMAND_DAILY_COMMIT);
				break;
			case SelfListActivity.ALJJ_FLAG:
				//�������
				mBaseBo.requestUrl = Command.getAction(Command.COMMAND_ALJJ_COMMIT);
				break;
			}
		return mBaseBo;
	}
}
