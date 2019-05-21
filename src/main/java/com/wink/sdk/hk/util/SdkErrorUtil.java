package com.wink.sdk.hk.util;

import org.apache.log4j.Logger;

import com.wink.sdk.hk.func.HCNetSdkFunc;
import com.wink.sdk.hk.func.HCSadpSdkFunc;

import cn.hutool.setting.dialect.Props;

public class SdkErrorUtil {
	private  static Logger logger = Logger.getLogger(SdkErrorUtil.class);
	private  static Props HkNetErrorProps;
	private  static Props HkSadpErrorProps;
	private  final static String HK_NET_ERROR_PREFIX="HK_NET_ERROR_";
	private  final static String HK_SADP_ERROR_PREFIX="HK_SADP_ERROR_";
	
	private static Props getHkNetErrorProps(){
		if(HkNetErrorProps==null){
			HkNetErrorProps=new Props("hk_net_error.properties");
		}
		return HkNetErrorProps;
	}
	
	private static Props getHkSadpErrorProps(){
		if(HkSadpErrorProps==null){
			HkSadpErrorProps=new Props("hk_sadp_error.properties");
		}
		return HkSadpErrorProps;
	}
	
	public static String getHCNetErrorMsg(){
		int code=HCNetSdkFunc.hcNetSdk.NET_DVR_GetLastError();
		if(code!=0){
			String error= getHkNetErrorProps().getStr(HK_NET_ERROR_PREFIX+code);
			if(logger.isDebugEnabled()){
				logger.debug(code+"--"+error);
			}
			return error;
		}
		return null; 
	}
	
	public static String getHCSadpErrorMsg(){
		int code=HCSadpSdkFunc.sadpSdk.SADP_GetLastError();
		if(code!=0){
			String error=getHkSadpErrorProps().getStr(HK_SADP_ERROR_PREFIX+code);
			logger.error(code+"--"+error);
			return error; 
		}
		return null; 
	}

}
