package com.wink.sdk.hk.ui.vo;

public class CameraVo {

	//设备参数
	private String ip;
	private String username;
	private String password;
	private int port;
	
	//通道参数
	private String chanName;
	
	//平台参数
    private String szSipAuthenticateID;
    private String wServerSipPort;  
    private String wLocalSipPort; 
    private String byStreamType; 
    private String byEnable;
    private String dwRegisterInterval; 
    private String szDeviceDomain; 
    private String dwRegisterValid;
    private String byTransProtocol; 
    private String byProtocolVersion; 
    private String szServerID;  
    private String szSipAuthenticatePasswd;  
    private String dwAutoAllocChannelID;  
    private String szServerDomain; 
    private String byHeartbeatInterval; 
    private String byDeviceStatus;
    private String szSipServerAddress;  
    private String szSipUserName;  
    private String byMaxHeartbeatTimeOut;
	
	private String status;
	private String msg;
	
	public CameraVo() {
		super();
		this.status="未处理";
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getChanName() {
		return chanName;
	}

	public void setChanName(String chanName) {
		this.chanName = chanName;
	}

	public String getSzSipAuthenticateID() {
		return szSipAuthenticateID;
	}

	public void setSzSipAuthenticateID(String szSipAuthenticateID) {
		this.szSipAuthenticateID = szSipAuthenticateID;
	}

	public String getwServerSipPort() {
		return wServerSipPort;
	}

	public void setwServerSipPort(String wServerSipPort) {
		this.wServerSipPort = wServerSipPort;
	}

	public String getwLocalSipPort() {
		return wLocalSipPort;
	}

	public void setwLocalSipPort(String wLocalSipPort) {
		this.wLocalSipPort = wLocalSipPort;
	}

	public String getByStreamType() {
		return byStreamType;
	}

	public void setByStreamType(String byStreamType) {
		this.byStreamType = byStreamType;
	}

	public String getByEnable() {
		return byEnable;
	}

	public void setByEnable(String byEnable) {
		this.byEnable = byEnable;
	}

	public String getDwRegisterInterval() {
		return dwRegisterInterval;
	}

	public void setDwRegisterInterval(String dwRegisterInterval) {
		this.dwRegisterInterval = dwRegisterInterval;
	}

	public String getSzDeviceDomain() {
		return szDeviceDomain;
	}

	public void setSzDeviceDomain(String szDeviceDomain) {
		this.szDeviceDomain = szDeviceDomain;
	}

	public String getDwRegisterValid() {
		return dwRegisterValid;
	}

	public void setDwRegisterValid(String dwRegisterValid) {
		this.dwRegisterValid = dwRegisterValid;
	}

	public String getByTransProtocol() {
		return byTransProtocol;
	}

	public void setByTransProtocol(String byTransProtocol) {
		this.byTransProtocol = byTransProtocol;
	}

	public String getByProtocolVersion() {
		return byProtocolVersion;
	}

	public void setByProtocolVersion(String byProtocolVersion) {
		this.byProtocolVersion = byProtocolVersion;
	}

	public String getSzServerID() {
		return szServerID;
	}

	public void setSzServerID(String szServerID) {
		this.szServerID = szServerID;
	}

	public String getSzSipAuthenticatePasswd() {
		return szSipAuthenticatePasswd;
	}

	public void setSzSipAuthenticatePasswd(String szSipAuthenticatePasswd) {
		this.szSipAuthenticatePasswd = szSipAuthenticatePasswd;
	}

	public String getDwAutoAllocChannelID() {
		return dwAutoAllocChannelID;
	}

	public void setDwAutoAllocChannelID(String dwAutoAllocChannelID) {
		this.dwAutoAllocChannelID = dwAutoAllocChannelID;
	}

	public String getSzServerDomain() {
		return szServerDomain;
	}

	public void setSzServerDomain(String szServerDomain) {
		this.szServerDomain = szServerDomain;
	}

	public String getByHeartbeatInterval() {
		return byHeartbeatInterval;
	}

	public void setByHeartbeatInterval(String byHeartbeatInterval) {
		this.byHeartbeatInterval = byHeartbeatInterval;
	}

	public String getByDeviceStatus() {
		return byDeviceStatus;
	}

	public void setByDeviceStatus(String byDeviceStatus) {
		this.byDeviceStatus = byDeviceStatus;
	}

	public String getSzSipServerAddress() {
		return szSipServerAddress;
	}

	public void setSzSipServerAddress(String szSipServerAddress) {
		this.szSipServerAddress = szSipServerAddress;
	}

	public String getSzSipUserName() {
		return szSipUserName;
	}

	public void setSzSipUserName(String szSipUserName) {
		this.szSipUserName = szSipUserName;
	}

	public String getByMaxHeartbeatTimeOut() {
		return byMaxHeartbeatTimeOut;
	}

	public void setByMaxHeartbeatTimeOut(String byMaxHeartbeatTimeOut) {
		this.byMaxHeartbeatTimeOut = byMaxHeartbeatTimeOut;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
