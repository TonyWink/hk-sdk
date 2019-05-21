package com.wink.sdk.hk.ui.handler;

import com.wink.sdk.hk.func.HCNetSdkFunc;
import com.wink.sdk.hk.ui.MessageBox;
import com.wink.sdk.hk.ui.vo.CameraVo;

public class DeviceLoginHandler extends AbstractHandler{

	private CameraVo camera;

	public DeviceLoginHandler(CameraVo camera) {
		super();
		this.camera = camera;
	}

	@Override
	protected boolean doRequest() {
		MessageBox.log(console,"设备 "+camera.getIp()+" 开始连接.");
		HCNetSdkFunc netSdk = new HCNetSdkFunc();
		hcSdk=netSdk;
		return netSdk.login(camera.getIp(), 
	                 camera.getUsername(), 
	                 camera.getPassword(), 
	                 camera.getPort())!=null;
	}

}
