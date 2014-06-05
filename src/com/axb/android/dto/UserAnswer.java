package com.axb.android.dto;

import java.io.Serializable;

public class UserAnswer implements Serializable{
	
	public int answerflag; //答案类别-1单选，2多选，3判断，4填写项
	public String questionsguid;
	public String userinputanswer; //文本
	public String usercheckboxanswer;//多选
	public String userradioanswer; //单选
	public int userjudgeanswer; //判断题
//	public String userId;
	public String taskGuid;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return questionsguid+","+answerflag+","+userradioanswer+","+usercheckboxanswer+","+userjudgeanswer+","+userinputanswer+","+taskGuid;
	}
}
