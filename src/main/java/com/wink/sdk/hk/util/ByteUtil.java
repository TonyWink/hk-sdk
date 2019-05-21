package com.wink.sdk.hk.util;

import cn.hutool.core.lang.Assert;

public class ByteUtil {

	public static byte[] setStrToByteArr(String str,int arrLen){
		 byte [] arr = new byte[arrLen];
		 byte [] strBytes=str.getBytes();
		 Assert.isTrue(arrLen>strBytes.length,"arrLen is too small.");
		 for(int i=0;i<strBytes.length;i++){
		     arr[i]=strBytes[i];
	     }
		 return arr;
	}
	
	public static int getValidLength(byte[] bytes){
	    int i = 0;
	    if (null == bytes || 0 == bytes.length)
	        return i ;
	    for (; i < bytes.length; i++) {
	        if (bytes[i] == '\0')
	            break;
	    }
	    return i;
	}
}
