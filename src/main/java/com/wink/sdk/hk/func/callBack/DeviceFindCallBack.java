package com.wink.sdk.hk.func.callBack;

import com.sun.jna.Pointer;
import com.wink.sdk.hk.sdk.HCSadpSdk;
import com.wink.sdk.hk.util.ConvertUtil;

import cn.hutool.json.JSONUtil;

public class DeviceFindCallBack implements HCSadpSdk.PDEVICE_FIND_CALLBACK {
    
	@Override
    public void invoke(HCSadpSdk.SADP_DEVICE_INFO lpDeviceInfo, Pointer pUserData) {
	    System.out.println(JSONUtil.toJsonPrettyStr(ConvertUtil.struct2Map(lpDeviceInfo)));
	}
	
}
