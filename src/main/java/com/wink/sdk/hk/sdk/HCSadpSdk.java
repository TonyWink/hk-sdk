package com.wink.sdk.hk.sdk;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;

public interface HCSadpSdk extends Library {

    HCSadpSdk INSTANCE = (HCSadpSdk) Native.loadLibrary(SdkPath.ROOT_PATH+"\\dll\\SadpSdk\\Sadp", HCSadpSdk.class);

    public static class SADP_DEVICE_INFO extends Structure {
        public  byte[]    szSeries = new byte[12];
        public  byte[]    szSerialNO = new byte[48];
        public  byte[]    szMAC = new byte[20];
        public  byte[]    szIPv4Address = new byte[16];
        public  byte[]    szIPv4SubnetMask = new byte[16];
        public  int       dwDeviceType;
        public  int       dwPort;
        public  int       dwNumberOfEncoders;
        public  int       dwNumberOfHardDisk;
        public  byte[]    szDeviceSoftwareVersion = new byte[48];
        public  byte[]    szDSPVersion = new byte[48];
        public  byte[]    szBootTime = new byte[48];
        public  int       iResult;
        public  byte[]    szDevDesc = new byte[24];
        public  byte[]    szOEMinfo = new byte[24];
        public  byte[]    szIPv4Gateway = new byte[16];
        public  byte[]    szIPv6Address = new byte[46];
        public  byte[]    szIPv6Gateway = new byte[46];
        public  byte      byIPv6MaskLen;
        public  byte      bySupport;
        public  byte      byDhcpEnabled;
        public  byte      byDeviceAbility;
        public  byte      wHttpPort;
        public  short     wDigitalChannelNum;
        public  byte[]    szCmsIPv4 = new byte[16];
        public  short     wCmsPort;
        public  byte      byOEMCode;
        public  byte      byActivated;
        public  byte[]    szBaseDesc = new byte[24];
        public  byte[]    byRes = new byte[16];
    }

    public static class SADP_DEV_NET_PARAM extends Structure {

        public  byte[]    szIPv4Address = new byte[16];
        public  byte[]    szIPv4SubnetMask = new byte[16];
        public  byte[]    szIPv4Gateway = new byte[16];
        public  byte[]    szIPv6Address = new byte[128];
        public  byte[]    szIPv6Gateway = new byte[128];
        public  short        wPort;
        public  byte         byIPv6MaskLen;
        public  byte         byDhcpEnable;
        public  short        wHttpPort;
        public  byte[]       byRes = new byte[126];
        
        public static class ByReference extends SADP_DEV_NET_PARAM implements Structure.ByReference{			
		}

        public static class ByValue extends SADP_DEV_NET_PARAM implements Structure.ByValue{
		}
    }

    boolean SADP_SetLogToFile(int nLogLevel, String strLogDir, int bAutoDel);
    boolean SADP_Start_V30(PDEVICE_FIND_CALLBACK pDeviceFindCallBack, int bInstallNPF ,Pointer pUserData);
    boolean SADP_SendInquiry();
    boolean SADP_Stop();
    boolean SADP_ActivateDevice(String sDevSerialNO,String sCommand);
    boolean SADP_ModifyDeviceNetParam(String sMAC,String sPassword,Pointer lpNetParam);

    public static interface PDEVICE_FIND_CALLBACK extends StdCallLibrary.StdCallCallback {
        public void invoke(SADP_DEVICE_INFO lpDeviceInfo,Pointer pUserData);
    }

    int SADP_GetLastError();
}
