package com.wink.sdk.hk.test;

import org.junit.Test;

import com.wink.sdk.hk.func.HCNetSdkFunc;

public class HKNetSdkTest {
	
	private String ip="192.168.30.116";
	private String userName="admin";
	private String password="Admin12345";
	private int port=8000;
	
	@Test
	public void testLogin(){
		new HCNetSdkFunc(ip,userName,password,port);
	}
	
	@Test
	public void testGBt28181(){
		HCNetSdkFunc netSdk=new HCNetSdkFunc(ip,userName,password,port);
		netSdk.getGBT28181Config();
	}
	
	@Test
	public void testCompressionConfig(){
		HCNetSdkFunc netSdk=new HCNetSdkFunc(ip,userName,password,port);
		netSdk.getChanCompressionConfig();
	}
	
	@Test
	public void testPictureConfig(){
		HCNetSdkFunc netSdk=new HCNetSdkFunc(ip,userName,password,port);
		netSdk.getPictureConfig();
	}
	
	@Test
	public void testSetDeviceTime(){
		HCNetSdkFunc netSdk=new HCNetSdkFunc(ip,userName,password,port);
		netSdk.setDeviceCurrentTime();
	}
}
