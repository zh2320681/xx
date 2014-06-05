package com.axb.android.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.axb.android.R;
import com.axb.android.dto.CaseDto;
import com.axb.android.dto.CaseQuestion;
import com.axb.android.dto.UserAnswer;

public class LoadPaperUtil {
	public static final String SPLIT_CHAR = "\\r\\n";
	public static final String SPLIT_CHAR1 = "ξ";

	private static final int DANX_QUESTION = 0x01;
	private static final int DUOX_QUESTION = 0x02;
	private static final int PD_QUESTION = 0x03;
	private static final int TK_QUESTION = 0x04;

	public static void loadPaperQuestions(Context ctx,
			LayoutInflater mInflater, CaseDto mCaseDto, ViewGroup questionLayout,boolean isLoadAnswer) {
		for (CaseQuestion mCaseQuestion : mCaseDto.allCaseQuestion) {
			// 得到你的答案
			UserAnswer mUserAnswer = mCaseQuestion.mUserAnswer;
			//得到标准答案
			String qAnswer = mCaseQuestion.answer;
			
			
			int count = questionLayout.getChildCount() + 1;
			switch (mCaseQuestion.answerFlag) {
			case DANX_QUESTION:
				LinearLayout commonView = (LinearLayout) mInflater.inflate(
						R.layout.question_common, null);
				TextView titleView = (TextView) commonView
						.findViewById(R.id.qc_title);
				titleView.setText(count + "、 " + mCaseQuestion.questionname);

				DanXuanTagHolder mDanXuanTagHolder = new DanXuanTagHolder();
				mDanXuanTagHolder.mCaseQuestion = mCaseQuestion;
				// mDanXuanTagHolder.mViewGroup =
				// (RadioGroup)mInflater.inflate(R.layout.question_danx, null);
				RadioGroup mRadioGroup = (RadioGroup) mInflater.inflate(
						R.layout.question_danx, null);
				int myDanxAnswer = -1;
				if(isLoadAnswer){
					//加载正确答案
					if (qAnswer != null) {
						myDanxAnswer = CommonUtil.parseInt(qAnswer, -1);
					}
				}else{
					//加载我的答案
					if (mUserAnswer != null) {
						myDanxAnswer = CommonUtil.parseInt(
								mUserAnswer.userradioanswer, -1);
					}
				}
				
				String[] opts = mCaseQuestion.optionname.split(SPLIT_CHAR);
				int selectId = -1;
				for (int i = 0; i < opts.length; i++) {
					String str = opts[i];
					if (str == null || str.equals("")) {
						continue;
					}
					RadioButton rb = new RadioButton(ctx);
					rb.setText(str);
					rb.setTextColor(Color.BLACK);
					rb.setTextSize(14);
					if (i == myDanxAnswer) {
						rb.setChecked(true);
						rb.setId(i);
						selectId = rb.getId();
					}
//					if (CommonUtil.judgeIntAvild(mCaseDto.isFinish)) {
//						// 已经完成 将不可以选
//						// rb.setSelected(false);
//						rb.setEnabled(treu);
//					} else {
						rb.setEnabled(true);
//					}

					mDanXuanTagHolder.allSelectBtn.add(rb);
					mRadioGroup.addView(rb);
				}

				mRadioGroup.check(selectId);
//				if (CommonUtil.judgeIntAvild(mCaseDto.isFinish)) {
//					// 已经完成 将不可以选
//					mRadioGroup.setSelected(false);
//				}
				
				commonView.addView(mRadioGroup);
				commonView.setTag(mDanXuanTagHolder);

				questionLayout.addView(commonView);
				break;
			case DUOX_QUESTION:
				LinearLayout commonView1 = (LinearLayout) mInflater.inflate(
						R.layout.question_common, null);
				TextView titleView1 = (TextView) commonView1
						.findViewById(R.id.qc_title);
				titleView1.setText(count + "、 " + mCaseQuestion.questionname);

				DuoXuanTagHolder mDuoXuanTagHolder = new DuoXuanTagHolder();
				mDuoXuanTagHolder.mCaseQuestion = mCaseQuestion;
				LinearLayout duoxuanLayout = (LinearLayout) mInflater.inflate(
						R.layout.question_duox, null);
				String[] myDuoxAnswer = null;
				
				if(isLoadAnswer){
					//加载正确答案
					if (qAnswer != null
							&& mUserAnswer.usercheckboxanswer != null) {
						myDuoxAnswer = qAnswer.split(SPLIT_CHAR);
					}
				}else{
					//加载我的答案
					if (mUserAnswer != null
							&& mUserAnswer.usercheckboxanswer != null) {
						myDuoxAnswer = mUserAnswer.usercheckboxanswer
								.split(SPLIT_CHAR);
					}
				}
				

				String[] duoxuanOpts = mCaseQuestion.optionname
						.split(SPLIT_CHAR);
				for (int i = 0; i < duoxuanOpts.length; i++) {
					String str = duoxuanOpts[i];
					if (str == null || str.equals("")) {
						continue;
					}
					CheckBox cb = new CheckBox(ctx);
					cb.setText(str);
					cb.setTextColor(Color.BLACK);
					if (myDuoxAnswer != null) {
						for (String answer : myDuoxAnswer) {
							if (answer.equals(String.valueOf(i))) {
								cb.setChecked(true);
								break;
							}
						}
					}
//					if (CommonUtil.judgeIntAvild(mCaseDto.isFinish)) {
//						// 已经完成 将不可以选
//						// cb.setChecked(false);
//						cb.setEnabled(false);
//					}
					// cb.setTextSize(14);
					duoxuanLayout.addView(cb);

					mDuoXuanTagHolder.allSelectCheckBoxs.add(cb);
				}
				commonView1.addView(duoxuanLayout);
				commonView1.setTag(mDuoXuanTagHolder);

				questionLayout.addView(commonView1);
				break;
			case PD_QUESTION:
				final JudgeTagHolder mJudgeclass = new JudgeTagHolder();
				mJudgeclass.mCaseQuestion = mCaseQuestion;
				View view = mInflater.inflate(R.layout.question_pd, null);
				mJudgeclass.img = (ImageView) view
						.findViewById(R.id.qpd_switch);
				TextView pdTitle = (TextView) view.findViewById(R.id.qpd_title);
				pdTitle.setText(count + "、 " + mCaseQuestion.questionname);
				int myPDAnswer = -1;
				if(isLoadAnswer){
					//加载正确答案
					if (qAnswer != null) {
						myPDAnswer = Integer.parseInt(qAnswer,-1);
					}
				}else{
					//加载我的答案
					if (mUserAnswer != null) {
						myPDAnswer = mUserAnswer.userjudgeanswer;
					}
				}
				

				mJudgeclass.img.setImageResource(CommonUtil.judgeIntAvild(myPDAnswer) ? R.drawable.question_ok
						: R.drawable.question_error);
				mJudgeclass.isCheck = CommonUtil.judgeIntAvild(myPDAnswer);

//				if (CommonUtil.judgeIntAvild(mCaseDto.isFinish)) {
//					// 已经完成 将不可以选
//					mJudgeclass.img.setEnabled(false);
//				}

				mJudgeclass.img.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mJudgeclass.isCheck = !mJudgeclass.isCheck;
						mJudgeclass.img.setImageResource(mJudgeclass.isCheck ? R.drawable.question_ok
								: R.drawable.question_error);
					}
				});
				view.setTag(mJudgeclass);
				questionLayout.addView(view);
				break;
			case TK_QUESTION:
				EditTagHolder mEditTagHolder = new EditTagHolder();
				mEditTagHolder.mCaseQuestion = mCaseQuestion;
				View editLayout = mInflater.inflate(R.layout.question_edit,
						null);
				TextView titleView3 = (TextView) editLayout
						.findViewById(R.id.qe_title);
				titleView3.setText(count + "、 " + mCaseQuestion.questionname);
				mEditTagHolder.mEditText = (EditText) editLayout
						.findViewById(R.id.qe_edit);
				
				if(isLoadAnswer){
					//加载正确答案
					mEditTagHolder.mEditText.setText(qAnswer);
				}else{
					//加载我的答案
					if (mUserAnswer != null) {
						mEditTagHolder.mEditText
								.setText(mUserAnswer.userinputanswer);
					}

				}
				
//				if (CommonUtil.judgeIntAvild(mCaseDto.isFinish)) {
//					// 已经完成 将不可以选
//					mEditTagHolder.mEditText.setEnabled(false);
//				}

				editLayout.setTag(mEditTagHolder);
				questionLayout.addView(editLayout);
				break;
			}

		}
	}

	/**
	 * 
	 * @param questionLayout
	 * @param taskGuid
	 * @param isCheckFinish
	 *            是否检测完成度
	 * @return
	 * @throws Exception
	 */
	public static String getUserAnswer(ViewGroup questionLayout,
			String taskGuid, boolean isCheckFinish) throws Exception {
		StringBuffer sb = new StringBuffer();
		int childCount = questionLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Object obj = questionLayout.getChildAt(i).getTag();
			UserAnswer mUserAnswer = new UserAnswer();
			mUserAnswer.taskGuid = taskGuid;
			if (obj instanceof DanXuanTagHolder) {
				DanXuanTagHolder mDanXuanTagHolder = (DanXuanTagHolder) obj;
				CaseQuestion mCaseQuestion = mDanXuanTagHolder.mCaseQuestion;
				mUserAnswer.answerflag = mCaseQuestion.answerFlag;
				mUserAnswer.questionsguid = mCaseQuestion.guid;
				boolean isExistChecked = false;
				for (int j = 0; j < mDanXuanTagHolder.allSelectBtn.size(); j++) {
					if (mDanXuanTagHolder.allSelectBtn.get(j).isChecked()) {
						mUserAnswer.userradioanswer = String.valueOf(j);
						isExistChecked = true;
						break;
					}
				}

				if (!isExistChecked && isCheckFinish) {
					// 有题目没有完成
					throw new RuntimeException("还有任务没有完成");
				}

			} else if (obj instanceof DuoXuanTagHolder) {
				DuoXuanTagHolder mDuoXuanTagHolder = (DuoXuanTagHolder) obj;
				CaseQuestion mCaseQuestion = mDuoXuanTagHolder.mCaseQuestion;
				mUserAnswer.answerflag = mCaseQuestion.answerFlag;
				mUserAnswer.questionsguid = mCaseQuestion.guid;
				StringBuffer sbs = new StringBuffer();

				boolean isExistChecked = false; // 判断用户是否选择
				for (int j = 0; j < mDuoXuanTagHolder.allSelectCheckBoxs.size(); j++) {
					if (mDuoXuanTagHolder.allSelectCheckBoxs.get(j).isChecked()) {
						isExistChecked = true;
						if (sbs.length() == 0) {
							sbs.append(String.valueOf(j));
						} else {
							sbs.append(SPLIT_CHAR + String.valueOf(j));
						}
					}
				}
				mUserAnswer.usercheckboxanswer = sbs.toString();
				if (!isExistChecked && isCheckFinish) {
					// 有题目没有完成
					throw new RuntimeException("还有任务没有完成");
				}
			} else if (obj instanceof JudgeTagHolder) {
				JudgeTagHolder mJudgeTagHolder = (JudgeTagHolder) obj;
				CaseQuestion mCaseQuestion = mJudgeTagHolder.mCaseQuestion;
				mUserAnswer.answerflag = mCaseQuestion.answerFlag;
				mUserAnswer.questionsguid = mCaseQuestion.guid;
				mUserAnswer.userjudgeanswer = mJudgeTagHolder.isCheck ? 1 : 0;

			} else if (obj instanceof EditTagHolder) {
				EditTagHolder mEditTagHolder = (EditTagHolder) obj;
				CaseQuestion mCaseQuestion = mEditTagHolder.mCaseQuestion;
				mUserAnswer.answerflag = mCaseQuestion.answerFlag;
				mUserAnswer.questionsguid = mCaseQuestion.guid;
				mUserAnswer.userinputanswer = mEditTagHolder.mEditText
						.getText().toString().trim();
				
//				if (isCheckFinish) {
//					if (mUserAnswer.userinputanswer == null
//							|| mUserAnswer.userinputanswer.equals("")) {
//						// 有题目没有完成
//						throw new RuntimeException("还有任务没有完成");
//					}
//				}
				
			}

			if (sb.length() == 0) {
				sb.append(mUserAnswer.toString());
			} else {
				sb.append(SPLIT_CHAR1 + mUserAnswer.toString());
			}
		}

		return sb.toString();
	}

	public static void setUserAnswerLayoutDisable(ViewGroup questionLayout){
		int childCount = questionLayout.getChildCount();
		for (int i = 0; i < childCount; i++) {
			Object obj = questionLayout.getChildAt(i).getTag();
			UserAnswer mUserAnswer = new UserAnswer();
			if (obj instanceof DanXuanTagHolder) {
				DanXuanTagHolder mDanXuanTagHolder = (DanXuanTagHolder) obj;
				
				for (int j = 0; j < mDanXuanTagHolder.allSelectBtn.size(); j++) {
					mDanXuanTagHolder.allSelectBtn.get(j).setEnabled(false);
				}

			} else if (obj instanceof DuoXuanTagHolder) {
				DuoXuanTagHolder mDuoXuanTagHolder = (DuoXuanTagHolder) obj;
				CaseQuestion mCaseQuestion = mDuoXuanTagHolder.mCaseQuestion;
				mUserAnswer.answerflag = mCaseQuestion.answerFlag;
				mUserAnswer.questionsguid = mCaseQuestion.guid;

				for (int j = 0; j < mDuoXuanTagHolder.allSelectCheckBoxs.size(); j++) {
					mDuoXuanTagHolder.allSelectCheckBoxs.get(j).setEnabled(false);
				}
				
			} else if (obj instanceof JudgeTagHolder) {
				JudgeTagHolder mJudgeTagHolder = (JudgeTagHolder) obj;
//				CaseQuestion mCaseQuestion = mJudgeTagHolder.mCaseQuestion;
				mJudgeTagHolder.img.setEnabled(false);
			} else if (obj instanceof EditTagHolder) {
				EditTagHolder mEditTagHolder = (EditTagHolder) obj;
				mEditTagHolder.mEditText.setEnabled(false);
			}
		
		}

	}

	
	static class DanXuanTagHolder {
		List<RadioButton> allSelectBtn;
		CaseQuestion mCaseQuestion;

		public DanXuanTagHolder() {
			allSelectBtn = new ArrayList<RadioButton>();
		}
	}

	static class DuoXuanTagHolder {
		List<CheckBox> allSelectCheckBoxs;
		CaseQuestion mCaseQuestion;

		public DuoXuanTagHolder() {
			allSelectCheckBoxs = new ArrayList<CheckBox>();
		}
	}

	static class JudgeTagHolder {
		ImageView img;
		boolean isCheck;
		CaseQuestion mCaseQuestion;
	}

	static class EditTagHolder {
		EditText mEditText;
		CaseQuestion mCaseQuestion;
	}
}
