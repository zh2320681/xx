package com.axb.android.ui.adapter;

import java.text.DecimalFormat;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.axb.android.R;
import com.axb.android.R.color;
import com.axb.android.dto.DepartmentRanking;
import com.axb.android.dto.Ranking;
import com.axb.android.dto.User;

public class RankingAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<? extends Ranking> mListData;
	private int myRanking;

	public RankingAdapter(Context ctx, List<? extends Ranking> mListData,int myRanking) {
		this.myRanking = myRanking;
		this.mListData = mListData;
		this.mInflater = LayoutInflater.from(ctx);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mListData.size();
	}

	@Override
	public Ranking getItem(int position) {
		// TODO Auto-generated method stub
		return mListData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	
	public List<? extends Ranking> getData() {
		return mListData;
	}

	public void setData(List<? extends Ranking> mListData) {
		this.mListData = mListData;
	}

	
	
	public int getMyRanking() {
		return myRanking;
	}

	public void setMyRanking(int myRanking) {
		this.myRanking = myRanking;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TagHolder mTagHolder = null;
		if(convertView == null){
			mTagHolder = new TagHolder();
			convertView = mInflater.inflate(R.layout.rank_layout_item, null);
			mTagHolder.mRank = (TextView)convertView.findViewById(R.id.rli_rank);
			mTagHolder.mName = (TextView)convertView.findViewById(R.id.rli_name);
			mTagHolder.mTeam = (TextView)convertView.findViewById(R.id.rli_team);
			mTagHolder.mTask = (TextView)convertView.findViewById(R.id.rli_task);
			mTagHolder.mDailyCase = (TextView)convertView.findViewById(R.id.rli_dailycase);
			mTagHolder.mMineStudy = (TextView)convertView.findViewById(R.id.rli_mineStudy);
			
			convertView.setTag(mTagHolder);
		}else{
			mTagHolder = (TagHolder)convertView.getTag();
		}
		
		Ranking mRanking = mListData.get(position);
		if(position == 0 && myRanking != -1){
			mTagHolder.mName.setVisibility(View.VISIBLE);
			mTagHolder.mRank.setText((myRanking+1)+"");
			convertView.setBackgroundResource(R.drawable.listfirstbg);
			mTagHolder.mRank.setTextColor(color.list_firsttext_color);
			mTagHolder.mName.setTextColor(color.list_firsttext_color);
			mTagHolder.mTeam.setTextColor(color.list_firsttext_color);
			mTagHolder.mTask.setTextColor(color.list_firsttext_color);
			mTagHolder.mDailyCase.setTextColor(color.list_firsttext_color);
			mTagHolder.mMineStudy.setTextColor(color.list_firsttext_color);
			
		}else{
			mTagHolder.mName.setVisibility(View.VISIBLE);
			if(myRanking != -1){
				//有添加自己排名
				mTagHolder.mRank.setText(position+"");
			}else {
				//无添加自己排名
				mTagHolder.mRank.setText((position+1)+"");
			}
			
			convertView.setBackgroundResource(R.drawable.listbg);
			mTagHolder.mRank.setTextColor(color.ranking_ap_item);
			mTagHolder.mName.setTextColor(color.ranking_ap_item);
			mTagHolder.mTeam.setTextColor(color.ranking_ap_item);
			mTagHolder.mTask.setTextColor(color.ranking_ap_item);
			mTagHolder.mDailyCase.setTextColor(color.ranking_ap_item);
			mTagHolder.mMineStudy.setTextColor(color.ranking_ap_item);
		}
		
		if(mRanking instanceof User){
			User user = (User)mRanking;
			mTagHolder.mName.setVisibility(View.VISIBLE);
			mTagHolder.mName.setText(user.nickname);
			mTagHolder.mTeam.setText(user.department.departmentName);
			System.out.println("==========>"+user.department.departmentName);
			mTagHolder.mTask.setText(getTaskNum(user.taskRanking));
			mTagHolder.mDailyCase.setText(user.dailyNum+"");
			mTagHolder.mMineStudy.setText(user.selfNum+"");		 
		}else if(mRanking instanceof DepartmentRanking){
			DepartmentRanking departmentRanking = (DepartmentRanking)mRanking;
//			mTagHolder.mName.setText(departmentRanking.departmentName);
			mTagHolder.mName.setVisibility(View.GONE);
			mTagHolder.mTeam.setText(departmentRanking.departmentName);
			mTagHolder.mTask.setText(getTaskNum(departmentRanking.taskRanking));
			mTagHolder.mDailyCase.setText(departmentRanking.dailyNum+"");
			mTagHolder.mMineStudy.setText(departmentRanking.selfNum+"");
		}
		
		return convertView;
	}

	/**
	 * 获取百分比数字（保留2位小数）
	 * @param number
	 * @return
	 */
	private String getTaskNum(double number) {
		if(number == 0){
			return 0+"";
		}
		DecimalFormat df = new DecimalFormat("#.00");
		return df.format(number*100+0.005f)+"%";
	}

	class TagHolder {
		TextView mRank,mName,mTeam,mTask,mDailyCase,mMineStudy;
	}
	
}


