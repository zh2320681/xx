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
import com.axb.android.dto.User;
import com.axb.android.service.Command;
import com.axb.android.util.CommonUtil;
import com.axb.android.util.load.MyImageLoader;

public class UserRateAdapter extends BaseAdapter {
	private List<User> data;
	private LayoutInflater mInflater;
	private MyImageLoader mImageLoader;
	
	public UserRateAdapter(Context ctx,List<User> data,MyImageLoader mImageLoader){
		this.data = data;
		mInflater = LayoutInflater.from(ctx);
		this.mImageLoader = mImageLoader;
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

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TagHolder mTagHolder = null;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.level_adapter, null);
			mTagHolder = new TagHolder();
			
			mTagHolder.level = (TextView)convertView.findViewById(R.id.la_level);
			mTagHolder.name = (TextView)convertView.findViewById(R.id.la_name);
			mTagHolder.rate = (TextView)convertView.findViewById(R.id.la_rate);
			mTagHolder.rateInfo = (TextView)convertView.findViewById(R.id.la_rateInfo);
			mTagHolder.faceImg =(ImageView)convertView.findViewById(R.id.la_face);
			
			convertView.setTag(mTagHolder);
		}else{
			mTagHolder = (TagHolder)convertView.getTag();
		}
		User mUser = data.get(position);
		mTagHolder.level.setText((position+1)+"");
		mTagHolder.name.setText(mUser.nickname);
		mTagHolder.rate.setText(CommonUtil.getStudyRateStar(mUser.studyRate));
		mTagHolder.rateInfo.setText(CommonUtil.formatFloat(mUser.studyRate*100)+"%");
		mImageLoader.displayImage(Command.getUserImgPath()+mUser.userimg, mTagHolder.faceImg, false);
		return convertView;
	}

	
	class TagHolder{
		TextView level,name,rate,rateInfo;
		ImageView faceImg;
	}
}
