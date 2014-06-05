package com.axb.android.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.axb.android.dto.Result;
import com.axb.android.exception.CallJSONException;
import com.axb.android.exception.NetworkAvailableException;
import com.axb.android.util.CommonUtil;

public class BaseService {
	private static final String TAG = "BaseService";
	private Context context;
	
	public BaseService(Context context) {
		this.context = context;
	}
	

	/**
	 * @param postParamsMap
	 * @param url
	 * @return
	 * @throws NetworkAvailableException
	 * @throws CallJSONException
	 */
	public Result callJSON(Map<String, String> paramsMap, String url)
			throws NetworkAvailableException, CallJSONException {
		Result r = null;
		
		if (CommonUtil.isNetworkAvailable(context)) {	
//			for(String key : paramsMap.keySet()){
//				url+="&"+key+"="+paramsMap.get(key);
//			}
//			HttpGet httpGet = new HttpGet(url);
			HttpPost httpGet = new HttpPost(url);
			HttpResponse httpResponse = null;
			HttpClient httpClient=new DefaultHttpClient();
			//请求超时 设置为20秒 20000
			httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,Command.CONN_TIME_OUT); 
			//读取超时设置为40秒
			httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, Command.READ_TIME_OUT);
			try {
				if (paramsMap != null && paramsMap.size() > 0) {
					List<NameValuePair> params = new ArrayList<NameValuePair>();
					for (Map.Entry<String, String> map : paramsMap.entrySet()) {
						params.add(new BasicNameValuePair(map.getKey(), map.getValue()));
						
					}
					httpGet.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));
				
				}
				String retSrc = "";
				httpResponse = httpClient.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					retSrc = EntityUtils.toString(httpResponse.getEntity());
				}
				System.out.println("retSrc==>"+retSrc);
				r = new Result();
				if (retSrc != null && !retSrc.equals("")) {
//					 	r.setSuccess("true");
//						r.re=retSrc.replaceAll("\r", "");
						r = JSON.parseObject(retSrc, Result.class);
				}

			} catch (Exception e) {
				//Log.e(TAG, e.toString());
				System.out.println(" CallJSONException!!");
				throw new CallJSONException(e.getMessage());
			}

		} else {
			//Log.e(TAG, "请开启移动数据连接");
			System.out.println(" NetworkAvailableException!!");
			throw new NetworkAvailableException("请开启移动数据连接");

		}

		return r;

	}

}
