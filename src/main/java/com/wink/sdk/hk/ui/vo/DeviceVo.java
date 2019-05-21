package com.wink.sdk.hk.ui.vo;

public class DeviceVo {

	/*序列号*/
	private String serialNumber;
	/*IP*/
	private String ip;
	/*网关*/
	private String gateWay;
	/*子网掩码*/
	private String netMask;
	/*Mac地址*/
	private String macAddr;
	/*软件版本*/
	private String dversion;
	/*激活状态*/
	private String activeStatus;
	
	public DeviceVo(String serialNumber, String ip, String gateWay,String netMask, String macAddr, String dversion, byte activeStatus) {
		super();
		this.serialNumber = serialNumber;
		this.ip = ip;
		this.gateWay = gateWay;
		this.netMask= netMask;
		this.macAddr = macAddr;
		this.dversion = dversion;
		this.activeStatus = activeStatus==0 ? "已激活":"未激活" ;
	}

	public String getSerialNumber() {
		return serialNumber;
	}



	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}



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



	public String getMacAddr() {
		return macAddr;
	}



	public void setMacAddr(String macAddr) {
		this.macAddr = macAddr;
	}



	public String getDversion() {
		return dversion;
	}



	public void setDversion(String dversion) {
		this.dversion = dversion;
	}



	public String getActiveStatus() {
		return activeStatus;
	}



	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((serialNumber == null) ? 0 : serialNumber.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceVo other = (DeviceVo) obj;
        if (!serialNumber.equals(other.serialNumber))
			return false;
		return true;
	}

	
}
