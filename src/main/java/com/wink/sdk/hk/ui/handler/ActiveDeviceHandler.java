package com.wink.sdk.hk.ui.handler;

import com.wink.sdk.hk.func.HCSadpSdkFunc;
import com.wink.sdk.hk.ui.MessageBox;
import com.wink.sdk.hk.ui.vo.DeviceVo;
import com.wink.sdk.hk.ui.vo.NetParamVo;

public class ActiveDeviceHandler extends AbstractHandler{
	
	private DeviceVo device;
	private NetParamVo netParam;

	public ActiveDeviceHandler(DeviceVo device, NetParamVo netParam) {
		super();
		this.device = device;
		this.netParam = netParam;
	}

	@Override
	protected boolean doRequest() {
		MessageBox.log(console,"开始激活设备  "+device.getSerialNumber());
		return HCSadpSdkFunc.getInstance().activeDevice(device.getSerialNumber(),netParam.getPassword());
	}

}
