package com.wink.sdk.hk.ui.handler;

import com.wink.sdk.hk.ui.MessageBox;
import com.wink.sdk.hk.ui.vo.CameraVo;
import com.wink.sdk.hk.util.SdkErrorUtil;

import cn.hutool.core.bean.BeanUtil;

public class GBT28181Handler extends AbstractHandler{

	private CameraVo camera;
	
	public GBT28181Handler(CameraVo camera) {
		super();
		this.camera = camera;
	}

	@Override
	protected boolean doRequest() {
		MessageBox.log(console,"设备 "+camera.getIp()+" 获取GBT28181.");
		if(hcSdk.getGBT28181Config()==null){
			camera.setStatus("操作失败");
			camera.setMsg(SdkErrorUtil.getHCNetErrorMsg());
			return false;
		}
		MessageBox.log(console,"设备 "+camera.getIp()+" 获取GBT28181参数成功.");
		boolean isSuccess=hcSdk.setGBT28181Config(BeanUtil.beanToMap(camera));
		if(!isSuccess){
			camera.setStatus("操作失败");
			camera.setMsg(SdkErrorUtil.getHCNetErrorMsg());
		}
		return isSuccess;
	}

}
