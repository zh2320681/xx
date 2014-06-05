package com.axb.android.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 案例的  问题 
 * @author shrek
 *
 */
public class CaseQuestion implements Serializable{
	public String guid;
	public String questionname;  //问题
	public String optionname; //选项
	public int answerFlag;
	public String answer; //回答
	public String addUserGuid;
	public Date addTime;
	
	public UserAnswer mUserAnswer;
}
