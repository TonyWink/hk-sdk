package com.wink.sdk.hk;

import com.wink.sdk.hk.func.HCNetSdkFunc;
import com.wink.sdk.hk.func.HCSadpSdkFunc;
import com.wink.sdk.hk.func.callBack.DeviceFindCallBack;

public class App 
{

    public static void main( String[] args )
    {

    	HCNetSdkFunc netSdk = new HCNetSdkFunc("192.168.30.123","admin","keyou2019", 8000);

    	HCSadpSdkFunc.getInstance().startSearchDevices(new DeviceFindCallBack());
        
    	try {
			Thread.sleep(20*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
