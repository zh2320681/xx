package com.axb.android.ui.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.axb.android.R;

public class MainItemLayout extends LinearLayout {
	String textInfo;
	int iconSrc,numIconSrc;
	boolean isShowNum;
	
	private TextView textView,numView;
	private ImageView iconView;
	
	private int upColor,downColor;
	private OnClickListener mOnClickListener;
	
	public MainItemLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.main_item);
		int textInfoId = typedArray.getResourceId(R.styleable.main_item_textInfo,0);

		if (textInfoId == 0){
			textInfo = typedArray.getString(R.styleable.main_item_textInfo);
		}else{
			textInfo = getResources().getString(textInfoId);
		}
		
		if (textInfo == null){
			throw new RuntimeException("必须设置主界面 TextView属性.");
		}
		
		iconSrc = typedArray.getResourceId(R.styleable.main_item_iconSrc,0);
		if (iconSrc == 0){
			throw new RuntimeException("必须设置图标.");
		}
		
		numIconSrc  = typedArray.getResourceId(R.styleable.main_item_numIconSrc,0);
		
		int showNum = typedArray.getInt(R.styleable.main_item_showNum,0);
		isShowNum = (showNum == 1);
		
		LayoutInflater mInflater = LayoutInflater.from(context);
		LinearLayout linearLayout = (LinearLayout)mInflater.inflate(R.layout.homegridview_item, this);
	
		textView = (TextView)linearLayout.findViewById(R.id.hgi_note);
		numView = (TextView)linearLayout.findViewById(R.id.hgi_ammount);
		iconView = (ImageView)linearLayout.findViewById(R.id.hgi_image);
		
		
		textView.setText(textInfo);
		iconView.setImageResource(iconSrc);
		
		
		if(isShowNum){
			numView.setVisibility(View.VISIBLE);
			if (numIconSrc != 0){
				numView.setBackgroundResource(numIconSrc);
			}else{
				numView.setBackgroundResource(R.drawable.pao);
			}
//			iconView.setPadding(numView.getWidth(), numView.getHeight(), numView.getWidth(), 0);
		}else{
			numView.setVisibility(View.GONE);
		}
		
		
		
		downColor = Color.parseColor("#907CBAF8");
		upColor = Color.parseColor("#00ffffff");
	}
	
	
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				setBackgroundColor(downColor);
				return true;
			case MotionEvent.ACTION_UP:
				setBackgroundColor(upColor);
				//点击后 做啥
				if(mOnClickListener != null){
					mOnClickListener.onClick(this);
				}
				break;
		}
		return super.onTouchEvent(event);
	}



	@Override
	public void setOnClickListener(OnClickListener l) {
		// TODO Auto-generated method stub
//		super.setOnClickListener(l);
		this.mOnClickListener = l;
	}

	
	
	public void setImgSrc(int srcId){
//		textView.setText(textStr);
		iconView.setImageResource(srcId);
	}
	
	public void setContent(int srcId,String textStr){
		textView.setText(textStr);
		iconView.setImageResource(srcId);
	}
	
	public void setContent(int srcId,String textStr,boolean isShowNum){
		setContent(srcId,textStr);
		this.isShowNum = isShowNum;
//		if(isShowNum){
//			numView.setVisibility(View.VISIBLE);
//		}else{
//			numView.setVisibility(View.GONE);
//		}
	}
	
//	public void setTextColor(ColorStateList scl){
//		textView.setTextColor(scl);
//	}
	
	public void setTextColor(int color){
		textView.setTextColor(color);
	}
	
	public void setTextSize(float size){
		textView.setTextSize(size);
	}
	
	public TextView getNumView(){
		return numView;
	}
}
