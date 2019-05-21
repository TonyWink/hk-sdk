package com.wink.sdk.hk.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

import cn.hutool.core.lang.Assert;

public class StructUtil {
	
	public static void  copyProperties(Object source,Object target,String...ignoreFields){
		Assert.isTrue(target instanceof Structure && source instanceof Structure);
		try{
			List<String> ignoreFieldList=Arrays.asList(ignoreFields);
			Field[] srcFields=source.getClass().getFields();
			Field[] tarFields=target.getClass().getFields();
			for(Field srcField : srcFields){
				for(Field tarField : tarFields){
					String srcFieldName=srcField.getName();
					String tarFieldName=tarField.getName();
					if(srcFieldName.equals(tarFieldName)
							&& tarField.getType() == srcField.getType()
							&& !Modifier.isFinal(tarField.getModifiers())
							&& !ignoreFieldList.contains(srcFieldName)){
						tarField.setAccessible(true);
						if(tarField.getType() == byte[].class){
							int valByteLen=ByteUtil.getValidLength((byte[])srcField.get(source));
							if(valByteLen!=0){
							   int arrLen=((byte[])tarField.get(target)).length;
							   tarField.set(target,null);
							   tarField.set(target,ByteUtil.setStrToByteArr(new String((byte[])srcField.get(source),0,valByteLen),arrLen));
							}
						}else{
							tarField.set(target,srcField.get(source));
						}
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("copy properties fail.");
		}
	}
}
