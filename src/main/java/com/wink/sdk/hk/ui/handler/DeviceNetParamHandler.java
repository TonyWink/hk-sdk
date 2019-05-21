package com.wink.sdk.hk.ui.handler;

import com.wink.sdk.hk.func.HCSadpSdkFunc;
import com.wink.sdk.hk.ui.MessageBox;
import com.wink.sdk.hk.ui.vo.DeviceVo;
import com.wink.sdk.hk.ui.vo.NetParamVo;

/**
 * 
 * @ClassName:  DeviceNetParamHandler   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: ShenYue 
 * @date:   2019年5月5日 上午11:11:00
 */
public class DeviceNetParamHandler extends AbstractHandler{
    
	private DeviceVo device;
	private NetParamVo netParam;
	
	public DeviceNetParamHandler(DeviceVo device, NetParamVo netParam) {
		super();
		this.device = device;
		this.netParam = netParam;
	}

	@Override
	protected boolean doRequest() {
		MessageBox.log(console,"设备  "+device.getSerialNumber()+"开始设置配置IP地址、子网掩码、网关。");
		return HCSadpSdkFunc.setDeviceNetParam(
				            device.getMacAddr(),
	    					netParam.getPassword(), 
	    					netParam.getIp(), 
	    					netParam.getGateWay(), 
	    					netParam.getNetMask());
	}

}
