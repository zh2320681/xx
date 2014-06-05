package com.axb.android.dto;

public class Page {
	private int pageIndex; //当前是第几页 从0开始
	private int pageSize; //每页的个数
	private int totalIndex; //总共有多少页
	private int totalSize; //一共有多少记录
	
	public Page(int pageIndex, int pageSize) {
		super();
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
	}
	
	
	
	public Page() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getTotalIndex() {
		return totalIndex;
	}
	public void setTotalIndex(int totalIndex) {
		this.totalIndex = totalIndex;
	}
	public int getTotalSize() {
		return totalSize;
	}
	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	//计算出当前 从多少开始
	public int getCurrentStartSize(){
		return pageIndex*pageSize;
	}
	
	//计算出当前 有多少页
	public int computeTotalIndex(){
		return pageSize == 0?0:
			(totalIndex=(totalSize%pageSize==0?totalSize/pageSize:totalSize/pageSize+1));
	}
	
	
	public boolean addPage(){
		if(getTotalIndex() == 0){
			pageIndex = 0;
			return false;
		}
		if(pageIndex >= getTotalIndex()-1){
			pageIndex = (getTotalIndex()-1);
			return false;
		}
		pageIndex++;
		return true;
	}
	
}
