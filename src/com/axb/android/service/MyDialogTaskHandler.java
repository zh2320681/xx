package com.axb.android.service;

import cn.tt100.base.util.rest.DialogTaskHandler;
import cn.tt100.base.util.rest.ZWResult;

import com.axb.android.dto.BaseResult;

public abstract class MyDialogTaskHandler<T extends BaseResult> extends
		DialogTaskHandler<T> {
	// private Class<?> parseClazz;

	public MyDialogTaskHandler(String title, String content) {
		super(title, content);
		// TODO Auto-generated constructor stub
		// this.parseClazz = parseClazz;
	}

	@Override
	public void postResult(ZWResult<T> arg0) {
		// TODO Auto-generated method stub

		// arg0.bodyObj.mineData = JSON.parseObject(arg0.bodyObj.data,
		// parseClazz);

		minePostResult(arg0.bodyObj);
	}

	public abstract void minePostResult(T arg0);

	@Override
	public void postError(ZWResult<T> result, Exception ex) {
		// TODO Auto-generated method stub
		String exInfo = ex.getMessage();
		if (exInfo.contains("com") || exInfo.contains("cn")
				|| exInfo.contains("org")) {
			ex = new Exception("请求服务器端出错,请检查手机网络！");
		}
		super.postError(result, ex);
	}

}
