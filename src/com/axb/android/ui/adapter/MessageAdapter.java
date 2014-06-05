package com.axb.android.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.axb.android.R;
import com.axb.android.dto.MessageDto;
import com.axb.android.util.CommonUtil;

public class MessageAdapter extends BaseAdapter {
	private Context mContext;
	private List<MessageDto> data;
	private LayoutInflater mInflater;

	public MessageAdapter(Context ctx, List<MessageDto> messageDtos) {
		this.mContext = ctx;
		this.data = messageDtos;
		mInflater = LayoutInflater.from(ctx);
	}

	public List<MessageDto> getData() {
		return data;
	}

	public void setData(List<MessageDto> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View content, ViewGroup arg2) {
		// TODO Auto-generated method stub
		MessageDto m = data.get(arg0);
		TagHolder mTagHolder = null;
		if(content == null){
			mTagHolder = new TagHolder();
			content = mInflater.inflate(R.layout.message_item, null);
			mTagHolder.iconView = (ImageView) content.findViewById(R.id.mi_icon);
			mTagHolder.titleView = (TextView) content.findViewById(R.id.mi_title);
			mTagHolder.timeView = (TextView) content.findViewById(R.id.mi_time);
			
			content.setTag(mTagHolder);
		}else{
			mTagHolder = (TagHolder)content.getTag();
		}
		// View fenge = view.findViewById(R.id.mi_fenge);
		mTagHolder.titleView.setText(m.title);
		mTagHolder.timeView.setText(CommonUtil.formartServerTime(m.addtime));
		mTagHolder.iconView.setImageResource(m.lookTime == 0 ? R.drawable.message_unread
				: R.drawable.message_read);
		return content;
	}

	
	public void addData(List<MessageDto> addData){
		this.data.addAll(addData);
		notifyDataSetChanged();
	}
	
	
	public void clearData(){
		this.data.clear();
		notifyDataSetChanged();
	}
}

	class TagHolder {
		ImageView iconView;
		TextView titleView,timeView;
	}
