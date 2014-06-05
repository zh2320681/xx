package com.axb.android.dto;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 单例模式(请求的格式)
 * @author Administrator
 *
 */
public class BaseBo {
//	private static BaseBo myBo;
	
	public Map<String,String> maps;  //参数列表
	public String requestUrl="";//请求地址
//	public String action;//命令字
//	public String guid;
	
	public BaseBo(){
		maps = new HashMap<String, String>();
//		UUID u=UUID.randomUUID();
//		guid= u.toString();
	}
	
//	public static BaseBo newInstance(){
//		if(myBo == null){
//			myBo = new BaseBo();
//		}
//		return new BaseBo();
//	}
	
	public void clearData(){
		requestUrl="";
		maps.clear();
//		maps.put("guid", guid);
	}
	
	public void clearDataWithGuid(){
		requestUrl="";
		maps.clear();
		UUID u=UUID.randomUUID();
//		guid= u.toString();
//		maps.put("guid", guid);
	}
	
	public void clearDataWithOutGuid(){
		requestUrl="";
		maps.clear();
//		maps.put("guid", guid);
	}
	
}
