package com.axb.android.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.axb.android.R;
import com.axb.android.dto.BaseBo;
import com.axb.android.service.BaseAsyncTask;
import com.axb.android.service.Command;
import com.axb.android.service.ModifyPasswordTask;

public class ModifyPwActivity extends BaseActivity {
	private EditText oldView,newView,new1View;
	private Button backBtn,saveBtn,czBtn;
	private String requestPwStr;
	
	private OnClickListener mClick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == backBtn){
				finish();
			}else if(v == saveBtn){
				String oldStr = oldView.getText().toString();
				if(oldStr == null || oldStr.equals("")){
					Toast.makeText(ModifyPwActivity.this, "�����������!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				String newStr = newView.getText().toString();
				if(newStr == null || newStr.equals("")){
					Toast.makeText(ModifyPwActivity.this, "������������!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				String newStr1 = new1View.getText().toString();
				if(newStr1 == null || newStr1.equals("")){
					Toast.makeText(ModifyPwActivity.this, "����һ������������!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				if(!newStr.equals(newStr1)){
					Toast.makeText(ModifyPwActivity.this, "��������������벻һ��!", Toast.LENGTH_SHORT).show();
					newView.setText(null);
					new1View.setText(null);
					return;
				}
				
				if(!oldStr.equals(mApplication.mLoginUser.password)){
					Toast.makeText(ModifyPwActivity.this, "ԭ�����������!", Toast.LENGTH_SHORT).show();	
					oldView.setText(null);
					return;
				}
				
				if(newStr.equals(oldStr)){
					Toast.makeText(ModifyPwActivity.this, "������;�����һ��!", Toast.LENGTH_SHORT).show();	
					newView.setText(null);
					new1View.setText(null);
					return;
				}
				
				ModifyPasswordTask mModifyPasswordTask = new ModifyPasswordTask(ModifyPwActivity.this, "�޸�����", "���������޸�����,���Ե�...", BaseAsyncTask.PRE_TASK_NORMAL);
				BaseBo mBaseBo = new BaseBo();
				mBaseBo.requestUrl = Command.getAction(Command.COMMAND_MODIFY);
				requestPwStr = newStr;
				mBaseBo.maps.put("newPw", newStr);
				mBaseBo.maps.put("userName", mApplication.mSetting.account);
				mBaseBo.maps.put("password", mApplication.mSetting.password);
				mModifyPasswordTask.execute(mBaseBo);
			}else if(v == czBtn){
				oldView.setText(null);
				newView.setText(null);
				new1View.setText(null);
			}
		}
	};
	
	@Override
	protected void initialize() {
		// TODO Auto-generated method stub
//		setContentView(R.layout.modify);
		
		init();
		addHandler();
	}
	
	
	private void init(){
		oldView = (EditText)findViewById(R.id.modify_old);
		newView = (EditText)findViewById(R.id.modify_new);
		new1View = (EditText)findViewById(R.id.modify_new1);
		
		backBtn =  (Button)findViewById(R.id.modify_back);
		saveBtn =  (Button)findViewById(R.id.modify_save);
		czBtn =  (Button)findViewById(R.id.modify_cz);
	}
	
	private void addHandler(){
		backBtn.setOnClickListener(mClick);
		saveBtn.setOnClickListener(mClick);
		czBtn.setOnClickListener(mClick);
		
	}


	public void afterModifyTaskDoing() {
		// TODO Auto-generated method stub
		/**
		 * �����޸�����ɹ�
		 * �޸Ļ��� ��������
		 * ���ػ������� ����  Ҫ���´ε�¼
		 */
//		Toast.makeText(ModifyPwActivity.this, "�����޸ĳɹ�!", Toast.LENGTH_SHORT).show();
		AlertDialog.Builder build = new AlertDialog.Builder(this);
		build.setTitle("�����޸�")
				.setMessage("�����޸ĳɹ�!")
				.setCancelable(false)
				.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								finish();
							}
						});
		build.create().show();
//		String newStr = newView.getText().toString();
		oldView.setText(null);
		newView.setText(null);
		new1View.setText(null);
		mApplication.mLoginUser.password = requestPwStr;
		
		mApplication.mSetting.isAutoLogin = false;
		mApplication.mSetting.password=requestPwStr;
		mApplication.mSetting.saveSettings(this);
	}
	
}
