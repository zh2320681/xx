package com.axb.android.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axb.android.R;
import com.axb.android.util.CommonUtil;

public class RecordTimeAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<Long> data;

	public RecordTimeAdapter(Context ctx, List<Long> data) {
		this.data = data;
		this.mInflater = LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public List<Long> getData() {
		return data;
	}

	public void setData(List<Long> data) {
		this.data = data;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TagHolder mTagHolder = null;
		if(convertView == null){
			mTagHolder = new TagHolder();
			convertView = mInflater.inflate(R.layout.record_item, null);
			mTagHolder.infoView = (TextView)convertView.findViewById(R.id.ri_info);
			
			convertView.setTag(mTagHolder);
		}else{
			mTagHolder = (TagHolder)convertView.getTag();
		}
		mTagHolder.infoView.setText(CommonUtil.formartServerTime(data.get(position)));
		return convertView;
	}

	class TagHolder {
		TextView infoView;
	}
}


