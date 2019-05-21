package com.wink.sdk.hk.ui.vo;

public class NetParamVo {

	/*IP*/
	private String ip;
	/*网关*/
	private String gateWay;
	/*子网掩码*/
	private String netMask;
	/*初始密码*/
	private String password;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getGateWay() {
		return gateWay;
	}
	public void setGateWay(String gateWay) {
		this.gateWay = gateWay;
	}
	public String getNetMask() {
		return netMask;
	}
	public void setNetMask(String netMask) {
		this.netMask = netMask;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
