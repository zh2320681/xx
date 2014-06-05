package com.axb.android.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axb.android.R;
import com.axb.android.dto.CaseDto;

public class RecordTaskAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<CaseDto> data;

	public RecordTaskAdapter(Context ctx, List<CaseDto> data) {
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

	
	public List<CaseDto> getData() {
		return data;
	}

	public void setData(List<CaseDto> data) {
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
		mTagHolder.infoView.setText(data.get(position).title);
		return convertView;
	}

	
	class TagHolder {
		TextView infoView;
	}
}


