package com.wink.sdk.hk.sdk;

import java.io.UnsupportedEncodingException;

public class SdkPath {

	 public static String ROOT_PATH;
	 
	 static{
        String path=(SdkPath.class.getResource("/").getPath()).replaceAll("%20", " ").substring(1).replace("/", "\\");
        try {
            ROOT_PATH=java.net.URLDecoder.decode(path,"utf-8");
            ROOT_PATH=ROOT_PATH.substring(0, ROOT_PATH.indexOf("\\target")).trim();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
	 
}

