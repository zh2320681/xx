package com.axb.android.dto;

public class Result {
	private String success;
	private String errmessage; 
	private String errcode;
	//Êý¾Ý
	private String data;
	private String requestError;
	
	private int unReadMsg;
	private int unReadTask;
	private int unReadALJJ;
	
	private Page page;
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getErrmessage() {
		return errmessage;
	}
	public void setErrmessage(String errmessage) {
		this.errmessage = errmessage;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	
	public String getRequestError() {
		return requestError;
	}
	public void setRequestError(String requestError) {
		this.requestError = requestError;
	}
	public int getUnReadMsg() {
		return unReadMsg;
	}
	public void setUnReadMsg(int unReadMsg) {
		this.unReadMsg = unReadMsg;
	}
	public int getUnReadTask() {
		return unReadTask;
	}
	public void setUnReadTask(int unReadTask) {
		this.unReadTask = unReadTask;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public int getUnReadALJJ() {
		return unReadALJJ;
	}
	public void setUnReadALJJ(int unReadALJJ) {
		this.unReadALJJ = unReadALJJ;
	}
	
	
}
