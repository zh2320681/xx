package com.axb.android.dto;

import java.io.Serializable;

/**
 * 选中的 图片信息
 * @author Administrator
 *
 */
public class SelectImageDto implements Serializable{
	public int id;
	public String path;
	public long size;
	public String displayName;
	public String mimeType;
	public int width;
	public int height;
	
}
