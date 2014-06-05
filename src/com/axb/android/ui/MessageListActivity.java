package com.axb.android.ui;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.axb.android.R;
import com.axb.android.dto.BaseBo;
import com.axb.android.dto.MessageDto;
import com.axb.android.dto.Page;
import com.axb.android.service.BaseAsyncTask;
import com.axb.android.service.Command;
import com.axb.android.service.MessageTask;
import com.axb.android.ui.adapter.MessageAdapter;

public class MessageListActivity extends BaseActivity {
	private final int request_code = 0x01; 
	private Button backBtn;
	
	private ListView mContent;
	private View requestLayout;
	private TextView requestInfo;
	private MessageAdapter mMessageAdapter;
	/*
	 * 分页(non-Javadoc)
	 * @see com.wei.android.anxun.ui.BaseActivity#onCreate(android.os.Bundle)
	 */
	private Page mPage;
	
	
	@Override
	protected void initialize() {
		init();
		addHandler();
		requestData();
	}
	
	private void init(){
		mPage = new Page(0, mApplication.mSetting.pageSize);
		
		backBtn = (Button)findViewById(R.id.ml_back);
		mContent = (ListView)findViewById(R.id.ml_list);
		requestLayout = findViewById(R.id.load_requestLayout);
		requestInfo = (TextView)findViewById(R.id.load_requestInfo);
		requestLayout.setVisibility(View.GONE);
	}
	
	private void addHandler(){
		mContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				MessageDto dto = mMessageAdapter.getData().get(arg2);
				Intent i = new Intent();
				i.setClass(MessageListActivity.this, MessageInfoActivity.class);
				i.putExtra("MessageDto", dto);
				startActivityForResult(i, request_code);
			}
			
		});
		
		mContent.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            	
            }
            
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem==0){
                }
                if(visibleItemCount+firstVisibleItem==totalItemCount){
                	//如果还有数据
                    if(mPage.addPage()){
                    	requestData();
                    }
                }
            }
        });
		
		backBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == request_code
				&& resultCode == Activity.RESULT_OK){
			MainActivity.isUpdateCount = true;
			mMessageAdapter.clearData();
			mPage.setPageIndex(0);
			requestData();
		}
	}
	
	private void requestData(){
		BaseBo mBaseBo = new BaseBo();
		mBaseBo.requestUrl = Command.getAction(Command.COMMAND_MESSAGE);
		mBaseBo.maps.put("userName", mApplication.mSetting.account);
		mBaseBo.maps.put("password", mApplication.mSetting.password); 
		mBaseBo.maps.put("pageIndex", mPage.getPageIndex()+"");
		mBaseBo.maps.put("pageSize", mPage.getPageSize()+""); 
		MessageTask mMessageTask = new MessageTask(this, "消息数据", 
				"正在请求消息"+(mMessageAdapter==null?"":"第("+mPage.getPageIndex()+"/"+mPage.getTotalIndex()+")页")+",请稍等.."
				, BaseAsyncTask.PRE_TASK_CUSTOM);
		mMessageTask.execute(mBaseBo);
	}
	
	public void taskSuccessDoing(List<MessageDto> lists, Page page){
		this.mPage = page;
		if(lists.size() == 0){
			return;
		}
		if(mMessageAdapter == null){
			mMessageAdapter = new MessageAdapter(this, lists);
			mContent.setAdapter(mMessageAdapter);
		}
		else{
			mMessageAdapter.addData(lists);
			mMessageAdapter.notifyDataSetChanged();
		}
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
}
