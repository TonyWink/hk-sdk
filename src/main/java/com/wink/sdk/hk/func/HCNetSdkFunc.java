package com.wink.sdk.hk.func;

import java.util.Calendar;
import java.util.Map;

import org.apache.log4j.Logger;

import com.sun.jna.Memory;
import com.sun.jna.NativeLong;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.wink.sdk.hk.sdk.HCNetSDK;
import com.wink.sdk.hk.sdk.SdkPath;
import com.wink.sdk.hk.sdk.HCNetSDK.NET_DVR_COMPRESSIONCFG_V30;
import com.wink.sdk.hk.sdk.HCNetSDK.NET_DVR_DEVICEINFO_V30;
import com.wink.sdk.hk.sdk.HCNetSDK.NET_DVR_GBT28181_ACCESS_CFG;
import com.wink.sdk.hk.sdk.HCNetSDK.NET_DVR_PICCFG_V30;
import com.wink.sdk.hk.util.ConvertUtil;
import com.wink.sdk.hk.util.SdkErrorUtil;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONUtil;

/**
 * 
 * @ClassName:  HCNetSdkFunc   
 * @Description: 海康设备网络SDK函数封装   
 * @author: ShenYue 
 * @date:   2019年4月22日 下午1:52:30
 */
public class HCNetSdkFunc implements SdkFunc {
   
	private static Logger logger = Logger.getLogger(HCNetSdkFunc.class);
	public  static HCNetSDK hcNetSdk=HCNetSDK.INSTANCE;;
	private static boolean isInit = false;
	
	private NativeLong userId;
	private NET_DVR_DEVICEINFO_V30 deviceInfo;
	
	public HCNetSdkFunc() {
		super();
	}

	public HCNetSdkFunc(String ip,String userName,String password,int port) {
		super();
		init();
		login(ip, userName, password, port);
	}

	@Override
	public void init(){
		if(!isInit){
			hcNetSdk.NET_DVR_Init();
			//设置超时时间
			hcNetSdk.NET_DVR_SetConnectTime(2000,1);
			//设置重连时间
			hcNetSdk.NET_DVR_SetReconnect(3000,true);
			hcNetSdk.NET_DVR_SetLogToFile(true, SdkPath.ROOT_PATH+"\\log", true);
			HCNetSdkFunc.isInit=true;
			logger.info("HCNetSdk init success.");
		}
	}
	
	@Override
	public boolean isSuccess() {
		return hcNetSdk.NET_DVR_GetLastError() == 0;
	}
	
    private NativeLong getUserId(){
		Assert.notNull(userId,"device must login first.");
		return userId;
	}
	
    /**
     * 设备登录
     * @param ip 设备IP地址
     * @param userName 用户名
     * @param password 密码
     * @param port 设备端口  默认为8000
     * @return 设备相关信息 ,操作失败返回NULL
     */
	public NET_DVR_DEVICEINFO_V30 login(String ip,String userName,String password,int port){
		logger.info("try to connect device: "+ip);
		deviceInfo=new HCNetSDK.NET_DVR_DEVICEINFO_V30();
		NativeLong currUserId=hcNetSdk.NET_DVR_Login_V30(ip,(short) port,userName,password,deviceInfo);
		if(isSuccess()){
			deviceInfo.read();
			userId=currUserId;
			logger.info("connect device success: "+ConvertUtil.struct2Json(deviceInfo));
			return deviceInfo;
		}else{
			logger.error(SdkErrorUtil.getHCNetErrorMsg());
		}
		return null;
	}
	
	/**
	 * 获取设备 GBT28181 平台参数
	 * @return GBT28181 平台参数,操作失败返回值为NULL
	 */
	public NET_DVR_GBT28181_ACCESS_CFG getGBT28181Config(){
		logger.info("try to get GBT28181 params.");
		NET_DVR_GBT28181_ACCESS_CFG gbt28181=new NET_DVR_GBT28181_ACCESS_CFG();
		gbt28181.write();
        Pointer lpOutBuffer=gbt28181.getPointer();
        Pointer lpBytesReturned=new Memory(1000);
        hcNetSdk.NET_DVR_GetDVRConfig(getUserId(),3249,new NativeLong(deviceInfo.byStartChan),lpOutBuffer,1000,lpBytesReturned);
        if(isSuccess()){
        	gbt28181.read();
        	logger.info("get GBT28181 params success: "+ConvertUtil.struct2Json(gbt28181));
        }else{
        	logger.error(SdkErrorUtil.getHCNetErrorMsg());
        }
        return null;
	}
	
	/**
	 * 设置GBT28181平台参数
	 * @param params 需要配置的平台参数
	 * @return 是否操作成功
	 */
	public boolean setGBT28181Config(Map<String,Object> params){
		logger.info("try to set gbt28181 as "+JSONUtil.toJsonStr(params));
		//先获取参数配置，无需改动的参数使用改动前的默认值
		NET_DVR_GBT28181_ACCESS_CFG gbt28181=getGBT28181Config();
		gbt28181=ConvertUtil.map2Struct(gbt28181, params);
		gbt28181.dwSize=gbt28181.size();
		gbt28181.write();
		Pointer lpInBuffer=gbt28181.getPointer();
		boolean isSuccess=hcNetSdk.NET_DVR_SetDVRConfig(getUserId(),3250,new NativeLong(0xFFFFFFFF), lpInBuffer,1000);
		if(isSuccess){
			logger.info("set GBT28181 params success: "+ConvertUtil.struct2Json(gbt28181));
		}else{
			logger.error(SdkErrorUtil.getHCNetErrorMsg());
		}
		return isSuccess;
	}
	
	/**
	 * 获取通道压缩参数
	 * @return 通道压缩参数,操作失败返回值为NULL
	 */
	public NET_DVR_COMPRESSIONCFG_V30 getChanCompressionConfig(){
		logger.info("try to get chanCompressionConfig params.");
		NET_DVR_COMPRESSIONCFG_V30 compressionConfig = new NET_DVR_COMPRESSIONCFG_V30();
		IntByReference bytesReturned = new IntByReference(0);
		compressionConfig.write();
	    Pointer configPointer =compressionConfig.getPointer();
	    boolean isSuccess=hcNetSdk.NET_DVR_GetDVRConfig(getUserId(), HCNetSDK.NET_DVR_GET_COMPRESSCFG_V30,
	                new NativeLong(deviceInfo.byStartChan),configPointer,compressionConfig.size(),bytesReturned);
	    if(isSuccess){
	    	compressionConfig.read();
	    	logger.info("get chanCompressionConfig params success: "+ConvertUtil.struct2Json(compressionConfig));
	    	return compressionConfig;
	    }else{
	    	logger.error(SdkErrorUtil.getHCNetErrorMsg());
	    }
        return null;
	}
	
	/**
	 * 设置通道压缩参数
	 * @param params 参数map
	 */
	public boolean setChanCompressionConfig(Map<String,Object> params){
		logger.info("try to set chanCompressionConfig as "+JSONUtil.toJsonStr(params));
		NET_DVR_COMPRESSIONCFG_V30 compressionConfig=getChanCompressionConfig();
		compressionConfig=ConvertUtil.map2Struct(compressionConfig, params);
		compressionConfig.dwSize=compressionConfig.size();
		compressionConfig.write();
		Pointer pointer=compressionConfig.getPointer();
		boolean isSuccess=hcNetSdk.NET_DVR_SetDVRConfig(getUserId(), HCNetSDK.NET_DVR_SET_COMPRESSCFG_V30,
	                new NativeLong(deviceInfo.byStartChan),pointer,compressionConfig.size());
	    if(isSuccess){
	    	compressionConfig.read();
	    	logger.info("set chanCompressionConfig params success: "+ConvertUtil.struct2Json(compressionConfig));
	    	return true;
	    }else{
	    	logger.error(SdkErrorUtil.getHCNetErrorMsg());
	    }
        return false;
	}
	
	/**
	 * 获取图像参数
	 * @return
	 */
	public NET_DVR_PICCFG_V30 getPictureConfig(){
		logger.info("try to get pictureConfig params.");
		IntByReference bytesReturned = new IntByReference(0);
		NET_DVR_PICCFG_V30 picConfig = new NET_DVR_PICCFG_V30();
		picConfig.write();
		Pointer pointer=picConfig.getPointer();
		boolean isSuccess=hcNetSdk.NET_DVR_GetDVRConfig(getUserId(),HCNetSDK.NET_DVR_GET_PICCFG_V30,
                new NativeLong(deviceInfo.byStartChan), pointer, picConfig.size(),bytesReturned);
	    if(isSuccess){
	    	picConfig.read();
	    	logger.info("get pictureConfig params success: "+ConvertUtil.struct2Json(picConfig));
	    	return picConfig;
	    }else{
	    	logger.error(SdkErrorUtil.getHCNetErrorMsg());
	    }
        return null;
	}
	
	/**
	 * 修改图像参数
	 * @param params
	 * @return
	 */
	public boolean setPictureConfig(Map<String,Object> params){
		logger.info("try to set pictureConfig as "+JSONUtil.toJsonStr(params));
		NET_DVR_PICCFG_V30 picConfig =getPictureConfig();
		picConfig=ConvertUtil.map2Struct(picConfig, params);
		picConfig.dwSize=picConfig.size();
		picConfig.write();
		Pointer pointer=picConfig.getPointer();
	    boolean isSuccess=hcNetSdk.NET_DVR_SetDVRConfig(getUserId(), HCNetSDK.NET_DVR_SET_PICCFG_V30,
	                new NativeLong(deviceInfo.byStartChan), pointer, picConfig.size());
	    if(isSuccess){
	    	picConfig.read();
	    	logger.info("set pictureConfig params success: "+ConvertUtil.struct2Json(picConfig));
	    	return true;
	    }else{
	    	logger.error(SdkErrorUtil.getHCNetErrorMsg());
	    }
	    return false;
	}
	
	/**
	 * 校准摄像头时间
	 */
	public boolean setDeviceCurrentTime(){
		logger.info("try to set device time as system time.");
		Calendar calendar = Calendar.getInstance();
		HCNetSDK.NET_DVR_TIME currentTime = new HCNetSDK.NET_DVR_TIME();
        currentTime.dwYear = calendar.get(Calendar.YEAR);
        currentTime.dwMonth = calendar.get(Calendar.MONTH)+1;
        currentTime.dwDay = calendar.get(Calendar.DAY_OF_MONTH);
        currentTime.dwHour = calendar.get(Calendar.HOUR_OF_DAY);
        currentTime.dwMinute = calendar.get(Calendar.MINUTE);
        currentTime.dwSecond = calendar.get(Calendar.SECOND);
        currentTime.write();
        logger.info("set device time as"+ConvertUtil.struct2Json(currentTime));
        Pointer pointer = currentTime.getPointer();
        boolean isSuccess=hcNetSdk.NET_DVR_SetDVRConfig(getUserId(), HCNetSDK.NET_DVR_SET_TIMECFG,
                new NativeLong(deviceInfo.byStartChan), pointer, currentTime.size());
        if(isSuccess){
        	currentTime.read();
	    	logger.info("update device time success.");
	    	return true;
	    }else{
	    	logger.error(SdkErrorUtil.getHCNetErrorMsg());
	    }
	    return false; 
	}

}
