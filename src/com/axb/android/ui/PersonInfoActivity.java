package com.axb.android.ui;

import java.io.File;
import java.io.UnsupportedEncodingException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.tt100.base.util.rest.ZWAsyncTask;

import com.alibaba.fastjson.TypeReference;
import com.axb.android.R;
import com.axb.android.dto.BaseBo;
import com.axb.android.dto.BaseResult;
import com.axb.android.dto.SelectImageDto;
import com.axb.android.service.Command;
import com.axb.android.service.GetUserRateTask;
import com.axb.android.service.MyDialogTaskHandler;
import com.axb.android.service.UpdateUserFaceTask;
import com.axb.android.service.UpdateUserSignTask;
import com.axb.android.util.CommonUtil;

public class PersonInfoActivity extends BaseActivity {
	private static final int REQUEST_IMAGE = 0x02; // ͼƬ���
	public static final int REQUEST_IMAGE_OPERATOR = 0x03; // ͼƬ����

	private static final int MAX_IMAGE_SIZE = 1024 * 1024;

	private TextView nameView, starView, rateView, companyView, dailyCountView,selfCountView;
	private EditText signView;
	private Button editView, backBtn, switchUserBtn, lookOverBtn, modifyPwBtn;
	private Button lineBtn, captialConBtn, substationBtn, marketBtn;
	private String editText;
	private ImageView faceView;
	private String fileName;

	private boolean isLine, isCaptialCon, isSubstation, isMarket;

	@Override
	protected void initialize() {
		init();
		initPerInfo();
		addHandler();
		userRateTask();
	}

	private void init() {
		nameView = (TextView) findViewById(R.id.pi_name);
		starView = (TextView) findViewById(R.id.pi_star);
		rateView = (TextView) findViewById(R.id.pi_rate);
		companyView = (TextView) findViewById(R.id.pi_company);
		dailyCountView = (TextView) findViewById(R.id.pi_dailyNum);
		selfCountView = (TextView) findViewById(R.id.pi_selfNum);
		
		signView = (EditText) findViewById(R.id.pi_sign);
		signView.setEnabled(false);

		editView = (Button) findViewById(R.id.pi_edit);
		switchUserBtn = (Button) findViewById(R.id.pi_switch);
		modifyPwBtn = (Button) findViewById(R.id.pi_modify);

		faceView = (ImageView) findViewById(R.id.pi_face);

		backBtn = (Button) findViewById(R.id.pi_back);
		lookOverBtn = (Button) findViewById(R.id.pi_lookover);

		lineBtn = (Button) findViewById(R.id.pi_line);
		captialConBtn = (Button) findViewById(R.id.pi_captialCon);
		substationBtn = (Button) findViewById(R.id.pi_substation);
		marketBtn = (Button) findViewById(R.id.pi_market);
	}

	/**
	 * ��ʼ��������Ϣ
	 */
	private void initPerInfo() {
		if (mApplication.mLoginUser != null) {
			if (mApplication.mLoginUser.nickname != null) {
				nameView.setText(mApplication.mLoginUser.nickname);
			}
			if (mApplication.mLoginUser.department.departmentName != null) {
				companyView
						.setText(mApplication.mLoginUser.department.departmentName);
			}
			dailyCountView.setText(String.valueOf(mApplication.mLoginUser.dailyStudyCount)+"��");
		}

		if (mApplication.mLoginUser != null
				&& mApplication.mLoginUser.aqxy != null
				&& !"".equals(mApplication.mLoginUser.aqxy.trim())) {
			signView.setText(mApplication.mLoginUser.aqxy);
		}

		mApplication.mImageLoader.newInstance(this,
				mApplication.mSetting.cacheDir);
		mApplication.mImageLoader.displayImage(Command.getUserImgPath()
				+ mApplication.mLoginUser.userimg, faceView, false);

		// ����רҵ
		if (mApplication.mLoginUser.specialty == null
				|| "".equals(mApplication.mLoginUser.specialty)) {
			lineBtn.setBackgroundResource(R.drawable.checkbox_normal);
			captialConBtn.setBackgroundResource(R.drawable.checkbox_normal);
			substationBtn.setBackgroundResource(R.drawable.checkbox_normal);
			marketBtn.setBackgroundResource(R.drawable.checkbox_normal);
			isLine = isCaptialCon = isSubstation = isMarket = false;
		} else {
			if (mApplication.mLoginUser.specialty.contains("��·")) {
				lineBtn.setBackgroundResource(R.drawable.checkbox_pressed);
				isLine = true;
			} else {
				lineBtn.setBackgroundResource(R.drawable.checkbox_normal);
				isLine = false;
			}
			if (mApplication.mLoginUser.specialty.contains("����")) {
				captialConBtn
						.setBackgroundResource(R.drawable.checkbox_pressed);
				isCaptialCon = true;
			} else {
				captialConBtn.setBackgroundResource(R.drawable.checkbox_normal);
				isCaptialCon = false;
			}
			if (mApplication.mLoginUser.specialty.contains("���")) {
				substationBtn
						.setBackgroundResource(R.drawable.checkbox_pressed);
				isSubstation = true;
			} else {
				substationBtn.setBackgroundResource(R.drawable.checkbox_normal);
				isSubstation = false;
			}
			if (mApplication.mLoginUser.specialty.contains("Ӫ��")) {
				marketBtn.setBackgroundResource(R.drawable.checkbox_pressed);
				isMarket = true;
			} else {
				marketBtn.setBackgroundResource(R.drawable.checkbox_normal);
				isMarket = false;
			}
		}
	}

	private void addHandler() {
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				updatePerInfo();
			}
		});

		editView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				editText = signView.getText().toString().trim();
				if (!signView.isEnabled()) {
					signView.setEnabled(true);
					editView.setText("���");
					signView.requestFocus();
					if (editText == null || editText.equals("")) {
						signView.setHint("");
					}
					signView.setSelection(editText.length());
					return;
				}

				if (editText.equals(mApplication.mLoginUser.aqxy)) {
					Toast.makeText(PersonInfoActivity.this, "��δ�༭��ȫ����!",
							Toast.LENGTH_SHORT).show();
				} else {

					AlertDialog.Builder builder = new AlertDialog.Builder(
							PersonInfoActivity.this);
					builder.setTitle("��������");
					builder.setMessage("�Ƿ������������?");
					builder.setPositiveButton("ȷ��",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									UpdateUserSignTask mUpdateUserSignTask = new UpdateUserSignTask(
											PersonInfoActivity.this, "��������",
											"���ڸ����û�������Ϣ,���Ե�...");
									BaseBo mBaseBo = new BaseBo();
									mBaseBo.requestUrl = Command
											.getAction(Command.COMMAND_SIGN);
									mBaseBo.maps.put("userName",
											mApplication.mSetting.account);
									mBaseBo.maps.put("password",
											mApplication.mSetting.password);
									mBaseBo.maps.put("sign", editText);
									mUpdateUserSignTask.execute(mBaseBo);
								}
							});

					builder.setNegativeButton("ȡ��",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									finish();
								}
							});
					builder.create().show();

				}
			}
		});

		faceView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ʽ��ͼ
				try {
					Intent intent = new Intent(
							Intent.ACTION_PICK,
							android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					startActivityForResult(intent, REQUEST_IMAGE);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					Toast.makeText(getApplicationContext(), "�޷���ϵͳ��ͼ��!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		modifyPwBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(PersonInfoActivity.this, ModifyPwActivity.class);
				startActivity(intent);
			}
		});

		switchUserBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						PersonInfoActivity.this);
				builder.setTitle("�����û�");
				builder.setMessage("�Ƿ�ȷ�ϸ����û�?");
				builder.setPositiveButton("ȷ��",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mApplication.clearData();
								Intent i = getBaseContext().getPackageManager()
										.getLaunchIntentForPackage(
												getBaseContext()
														.getPackageName());
								i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(i);
							}
						});

				builder.setNegativeButton("ȡ��",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				builder.create().show();
			}
		});

		lookOverBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(PersonInfoActivity.this, StudyRecordActivity.class);
				startActivity(i);
			}
		});

		lineBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isLine) {
					lineBtn.setBackgroundResource(R.drawable.checkbox_normal);
					isLine = false;
				} else {
					lineBtn.setBackgroundResource(R.drawable.checkbox_pressed);
					isLine = true;
				}
			}
		});
		;
		captialConBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (isCaptialCon) {
					captialConBtn
							.setBackgroundResource(R.drawable.checkbox_normal);
					isCaptialCon = false;
				} else {
					captialConBtn
							.setBackgroundResource(R.drawable.checkbox_pressed);
					isCaptialCon = true;
				}
			}
		});
		substationBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isSubstation) {
					substationBtn
							.setBackgroundResource(R.drawable.checkbox_normal);
					isSubstation = false;
				} else {
					substationBtn
							.setBackgroundResource(R.drawable.checkbox_pressed);
					isSubstation = true;
				}
			}
		});
		;
		marketBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (isMarket) {
					marketBtn.setBackgroundResource(R.drawable.checkbox_normal);
					isMarket = false;
				} else {
					marketBtn
							.setBackgroundResource(R.drawable.checkbox_pressed);
					isMarket = true;
				}
			}
		});
	}

	private void userRateTask() {
		GetUserRateTask mGetUserRateTask = new GetUserRateTask(
				PersonInfoActivity.this, "�û������", "���ڻ�ȡ�û��������Ϣ,���Ե�...");
		BaseBo mBaseBo = new BaseBo();
		mBaseBo.requestUrl = Command.getAction(Command.COMMAND_RATE);
		mBaseBo.maps.put("userName", mApplication.mSetting.account);
		mBaseBo.maps.put("password", mApplication.mSetting.password);
		mGetUserRateTask.execute(mBaseBo);
	}

	public void afterUpdateTaskDoing() {
		Toast.makeText(this, "��ȫ���Ը��³ɹ���", Toast.LENGTH_LONG).show();
		mApplication.mLoginUser.aqxy = editText;
		signView.setEnabled(false);
		editView.setText("�༭");
		if (editText == null || editText.equals("")) {
			signView.setHint("������һ���Լ��İ�ȫ���԰ɣ�");
		}
	}

	public void afterFaceTaskDoing() {
		Toast.makeText(this, "ͷ����³ɹ���", Toast.LENGTH_LONG).show();
		mApplication.mLoginUser.userimg = fileName;
		// �������
		mApplication.mImageLoader.clear();
		mApplication.mImageLoader.displayImage(Command.getUserImgPath()
				+ fileName, faceView, false);
	}

	public void afterRateTaskDoing(String rate,int selfCount) {
		float f = Float.parseFloat(rate);
		rateView.setText(CommonUtil.formatFloat(f * 100) + "%");
//		starView.setText(CommonUtil.getStudyRateStar(f));
		selfCountView.setText(String.valueOf(selfCount));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE) {
			// ͼƬ��� ����
			// ���uri�����Ƿ��ص�ѡ�е�ͼƬ
			if (data != null) {
				Uri uri = data.getData();
				Cursor cursor = this.getContentResolver().query(uri, null,
						null, null, null);
				if (cursor.moveToFirst()) {
					SelectImageDto dto = new SelectImageDto();
					dto.id = cursor.getInt(0);
					dto.path = cursor.getString(1);
					dto.size = cursor.getLong(2);
					dto.displayName = cursor.getString(3);
					dto.mimeType = cursor.getString(4);
					dto.width = cursor.getInt(18);
					dto.height = cursor.getInt(19);
					// �����ϴ��ļ��Ĵ�С
					File f = new File(dto.path);
					if (f.length() > MAX_IMAGE_SIZE) {
						Toast.makeText(PersonInfoActivity.this,
								"�ϴ�ͼƬ̫��,������Ϊͷ��!", Toast.LENGTH_SHORT).show();
						return;
					}
					cursor.close();
					// ��ת�� ͼƬ�༭
					Intent i = new Intent();
					i.setClass(PersonInfoActivity.this,
							ImageOperatorActivity.class);
					i.putExtra("data", dto);
					startActivityForResult(i, REQUEST_IMAGE_OPERATOR);
				} else {
					Toast.makeText(PersonInfoActivity.this, "��δѡ��ͷ��ͼƬ!",
							Toast.LENGTH_SHORT).show();
				}
			}
		} else if (requestCode == REQUEST_IMAGE_OPERATOR
				&& resultCode == Activity.RESULT_OK) {
			fileName = data.getStringExtra("fileName");

			UpdateUserFaceTask mUpdateUserFaceTask = new UpdateUserFaceTask(
					PersonInfoActivity.this, "�û�ͷ����Ϣ", "���ڸ����û�ͷ����Ϣ,���Ե�...");
			BaseBo mBaseBo = new BaseBo();
			mBaseBo.requestUrl = Command.getAction(Command.COMMAND_FACE);
			mBaseBo.maps.put("userName", mApplication.mSetting.account);
			mBaseBo.maps.put("password", mApplication.mSetting.password);
			mBaseBo.maps.put("faceName", fileName);
			mUpdateUserFaceTask.execute(mBaseBo);
		}
	}

	/**
	 * �ϴ�������Ϣ
	 */
	private boolean updatePerInfo() {
		StringBuffer sb = new StringBuffer();
		if (isLine) {
			sb.append((sb.length() == 0 ? "" : ",") + "��·");
		}

		if (isCaptialCon) {
			sb.append((sb.length() == 0 ? "" : ",") + "����");
		}
		if (isSubstation) {
			sb.append((sb.length() == 0 ? "" : ",") + "���");
		}

		if (isMarket) {
			sb.append((sb.length() == 0 ? "" : ",") + "Ӫ��");
		}

		final String specialty = sb.toString();
		if ((mApplication.mLoginUser.specialty == null && specialty.length() == 0)
				|| specialty.equals(mApplication.mLoginUser.specialty)) {
			finish();
			return false;
		}

		// UpdateSpecialtySignTask mUpdateSpecialtySignTask = new
		// UpdateSpecialtySignTask(
		// PersonInfoActivity.this, "�û�רҵ��Ϣ", "�����ύ�û�רҵ��Ϣ,���Ե�...");
		// BaseBo mBaseBo = new BaseBo();
		// mBaseBo.requestUrl = Command.getAction(Command.COMMAND_FACE);
		// mBaseBo.maps.put("userName", mApplication.mSetting.account);
		// mBaseBo.maps.put("password", mApplication.mSetting.password);
		// mBaseBo.maps.put("specialty", specialty);
		// mUpdateSpecialtySignTask.execute(mBaseBo);

		AlertDialog.Builder build = new AlertDialog.Builder(
				PersonInfoActivity.this);
		build.setTitle("רҵ��Ϣ�ύ").setMessage("���רҵ��Ϣ�Ѿ��޸�,�Ƿ��ύ?")
				.setIcon(R.drawable.alert3)
				.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						updateUserSpecInfo(specialty);
					}
				})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						finish();
					}
				}).create();
		build.show();

		return true;
	}

	private void updateUserSpecInfo(final String specialty) {
		ZWAsyncTask
				.excuteTaskWithOutMethod(
						this,
						Command.getRestActionUrl(Command.COMMAND_SPECIALTY_COMMIT),
						new TypeReference<BaseResult>() {
						},
						new MyDialogTaskHandler<BaseResult>("�û����а�",
								"���������û����а�,���Ե�...") {

							@Override
							public void minePostResult(BaseResult arg0) {
								// TODO Auto-generated method stub
								if (arg0.isSuccess()) {
									Toast.makeText(getApplicationContext(),
											"�û�רҵ��Ϣ�ύ�ɹ�!", Toast.LENGTH_SHORT)
											.show();
									mApplication.mLoginUser.specialty = specialty;
								} else {
									Toast.makeText(getApplicationContext(),
											"�û�רҵ��Ϣ�ύʧ��!", Toast.LENGTH_SHORT)
											.show();
								}
								finish();
							}
						}, mApplication.mSetting.account,
						mApplication.mSetting.password, specialty);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return updatePerInfo();
		}
		return super.onKeyDown(keyCode, event);
	}
}
