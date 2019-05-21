package com.wink.sdk.hk.util;

import java.util.HashMap;
import java.util.Map;
import cn.hutool.setting.dialect.Props;

public class DicUtil {
	
	private static Props dicEn2CnProps;
	private static Props dicCn2EnProps;
	
	private static Props getDicCn2EnProps(){
		if(dicCn2EnProps==null){
			dicCn2EnProps=new Props("dic/dic_cn_en.properties");
		}
		return dicCn2EnProps;
	}
	
	private static Props getDicEn2CnProps(){
		if(dicEn2CnProps==null){
			dicEn2CnProps=new Props("dic/dic_en_cn.properties");
		}
		return dicEn2CnProps;
	}

	public static Map<String,Object> translateMap2CnKey(Map<String,Object> map,boolean skipNoneNote){
		return translateMapKey(map,getDicEn2CnProps(),skipNoneNote);
	}
	
	public static Map<String,Object> translateMap2EnKey(Map<String,Object> map,boolean skipNoneNote){
		return translateMapKey(map,getDicCn2EnProps(),skipNoneNote);
	}
	
	private static Map<String,Object> translateMapKey(Map<String,Object> map,Props dicProps,boolean skipNoneNote){
		Map<String,Object> currMap = new HashMap<>();
		for (Map.Entry<String,Object> entry : map.entrySet()) { 
			  if(dicProps.containsKey(entry.getKey())){
				  currMap.put(dicProps.getProperty(entry.getKey()),entry.getValue());
			  }else{
				  if(!skipNoneNote){
					  currMap.put(entry.getKey(),entry.getValue());
				  }
			  }
		}
		return currMap;
	}
}
