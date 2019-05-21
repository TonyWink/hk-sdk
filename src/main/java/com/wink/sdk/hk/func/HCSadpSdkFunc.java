package com.wink.sdk.hk.func;

import org.apache.log4j.Logger;

import com.wink.sdk.hk.sdk.HCSadpSdk;
import com.wink.sdk.hk.sdk.SdkPath;
import com.wink.sdk.hk.sdk.HCSadpSdk.SADP_DEV_NET_PARAM;
import com.wink.sdk.hk.util.ByteUtil;
import com.wink.sdk.hk.util.SdkErrorUtil;

import cn.hutool.core.lang.Assert;

/**
 * 
 * @ClassName:  HkSadpSdkFunc   
 * @Description: 设备网络搜索SDK函数封装 
 * @author: ShenYue 
 * @date:   2019年4月23日 下午4:38:27
 */
public class HCSadpSdkFunc implements SdkFunc{
	
	private static Logger logger = Logger.getLogger(HCSadpSdkFunc.class);
	public  static HCSadpSdk sadpSdk = HCSadpSdk.INSTANCE;
	private static boolean isInit = false;
	private boolean isSearching = false;
	
	private static class HCSadpSdkFuncInstance {
        private static final HCSadpSdkFunc INSTANCE = new HCSadpSdkFunc();
    }

    public static HCSadpSdkFunc getInstance() {
        return HCSadpSdkFuncInstance.INSTANCE;
    }
	
	private HCSadpSdkFunc() {
		super();
		init();
	}

	@Override
	public void init() {
		if(!isInit){
			sadpSdk.SADP_SetLogToFile(3,SdkPath.ROOT_PATH+"\\log",1);
			isInit=true;
			logger.info("HCSadpSdk init success.");
		}
	}
	
	/**
	 * 开始搜索网络设备
	 */
	public boolean startSearchDevices(HCSadpSdk.PDEVICE_FIND_CALLBACK callBack){
		Assert.isFalse(isSearching,"device must stop searching first.");
		logger.info("HkSadpSdk search devices.");
		boolean isSuccess=sadpSdk.SADP_Start_V30(callBack,1,null);
		if(isSuccess){
			logger.info("searching devices start.");
			isSearching=true;
			return true;
		}else{
			logger.error(SdkErrorUtil.getHCSadpErrorMsg());
			return false;
		}
	}
	
	/**
	 * 手动刷新网络设备
	 */
	public boolean refreshDevices(){
		Assert.isTrue(isSearching,"must start devices searching first.");
		logger.info("refresh devices.");
		boolean isSuccess=sadpSdk.SADP_SendInquiry();
		if(isSuccess){
			logger.info("refresh devices success.");
			return true;
		}else{
			logger.error(SdkErrorUtil.getHCSadpErrorMsg());
			return false;
		}
	}
	
	/**
	 * 停止搜索网络设备
	 * @return 
	 */
	public  boolean stopSearchDevices(){
		Assert.isTrue(isSearching,"devices searching not start.");
		logger.info("stop search devices.");
		boolean isSuccess=sadpSdk.SADP_Stop();
		if(isSuccess){
			logger.info("stop search devices success.");
			isSearching=false;
			return true;
		}else{
			logger.error(SdkErrorUtil.getHCSadpErrorMsg());
			return false;
		}
	}
	
    /**
     * 激活设备
     * @param serialNO 设备序列号
     * @param password 初始密码
     * @return
     */
	public  boolean activeDevice(String serialNO,String password){
		logger.info("try to active devices,serialNO = "+serialNO+", password = "+password);
		boolean isSuccess=sadpSdk.SADP_ActivateDevice(serialNO,password);
		if(isSuccess){
			logger.info("active devices success.");
			return true;
		}else{
			logger.error(SdkErrorUtil.getHCSadpErrorMsg());
			return false;
		}
	}
	
	/**
	 * 修改设备网络参数
	 * @param sMAC  MAC地址
	 * @param password 设备密码
	 * @param ip IP地址
	 * @param gateWay 网关
	 * @param netMask 子网掩码
	 * @return 
	 */
	public static boolean setDeviceNetParam(String sMAC,String password,String ip,String gateWay,String netMask){
		logger.info("Modify device netParam -> sMAC = "+sMAC+",password= "+password+",ip= "+ip+",gateWay= "+gateWay+",netMask= "+netMask);
		SADP_DEV_NET_PARAM.ByValue netParam =new SADP_DEV_NET_PARAM.ByValue();
	    netParam.szIPv4Address=ByteUtil.setStrToByteArr(ip, 16);
	    netParam.szIPv4Gateway=ByteUtil.setStrToByteArr(gateWay, 16);
	    netParam.szIPv4SubnetMask=ByteUtil.setStrToByteArr(netMask, 16);
	    netParam.szIPv6Gateway=ByteUtil.setStrToByteArr("::", 128);
	    netParam.szIPv6Address=ByteUtil.setStrToByteArr("::", 128);
	    netParam.byDhcpEnable=0;
	    netParam.wPort=8000;
	    netParam.wHttpPort=80;
	    netParam.byIPv6MaskLen=0;
	    netParam.write();
		boolean isSuccess=sadpSdk.SADP_ModifyDeviceNetParam(sMAC,password, netParam.getPointer());
		if(isSuccess){
			logger.info("Modify devices params success.");
			return true;
		}else{
			logger.error(SdkErrorUtil.getHCSadpErrorMsg());
			return false;
		}
	}



	@Override
	public boolean isSuccess() {
		return false;
	}

}
