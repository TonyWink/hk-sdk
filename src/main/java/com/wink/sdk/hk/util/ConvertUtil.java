package com.wink.sdk.hk.util;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.sun.jna.Structure;

import cn.hutool.json.JSONUtil;

/**
 * 
 * @ClassName:  ConvertUtil   
 * @Description: 数据转换工具类   
 * @author: ShenYue 
 * @date:   2019年4月22日 下午3:22:59
 */
public class ConvertUtil {
	
	private static Logger logger = Logger.getLogger(ConvertUtil.class);

	/**
	 * 结构体转map
	 * @param t 待转换的结构体
	 * @param ignoreFields 忽略的字段列表
	 * @return 转换后的map,传入的结构体为NULL时,返回值也为NULL
	 */
	public static <T> Map<String,Object> struct2Map(T t,String...ignoreFields){
		if(t == null){
			return null;
		}
		Class<?> clazz = t.getClass();
		List<String> ignoreList=Arrays.asList(ignoreFields);
		Map<String,Object> map = new HashMap<>(32);
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			Class<?> type=field.getType();
			String fieldName=field.getName();
			field.setAccessible(true);
			try{
				if(!ignoreList.contains(fieldName)){
					if(isBaseType(type)){
					    map.put(fieldName,field.get(t));
					}else if(type == byte[].class){
						byte[] bytes=(byte[]) field.get(t);
						int validByteLen=ByteUtil.getValidLength(bytes);
						map.put(fieldName,new String(bytes,0,validByteLen));
					}else if(type.newInstance() instanceof Structure){
						map.put(fieldName,struct2Map(field.get(t)));
					}else{
						logger.warn("unsupport type "+type);
					}
				}
		    } catch ( InstantiationException e) {
		    	logger.error("can not instance obj.");
		    	e.printStackTrace();
             } catch (IllegalAccessException e) {
            	logger.error("no access to convert field.");
				e.printStackTrace();
			}
		}
		return map;
	}
	
	/**
	 * 结构体转换为json
	 * @param t 待转换的结构体
	 * @param ignoreFields 忽略的字段列表
	 * @return 
	 */
    public static <T> String struct2Json(T t,String...ignoreFields){
		return JSONUtil.toJsonPrettyStr(struct2Map(t,ignoreFields));
    }
	
    /**
     * 将map中的参数值赋值到结构体中
     * @param t 待赋值的结构体
     * @param params 封装相关参数的map
     * @return 赋值完成的结构体,传入的结构体为NULL时,返回值也为NULL
     */
	public static <T> T map2Struct(T t,Map<String,Object> params){
		if(t == null){
			return null;
		}
		Class<?> clazz = t.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			Class<?> type=field.getType();
			String fieldName=field.getName();
			field.setAccessible(true);
			try{
				if(params.containsKey(fieldName) && !ComnUtil.isEmpty(params.get(fieldName))){
				    if(type == byte[].class){
				    	int byteLen = ((byte[])field.get(t)).length;
						field.set(t,ComnUtil.isEmpty(params.get(fieldName).toString())? field.get(t) : ByteUtil.setStrToByteArr(params.get(fieldName).toString(),byteLen));
				    }else if(type == byte.class){
						field.set(t,Byte.valueOf(String.valueOf(params.get(fieldName))).intValue());
					}else if(type == int.class){
						field.set(t,Integer.valueOf(String.valueOf(params.get(fieldName))).intValue());
					}else if(type == short.class){
						field.set(t,Short.valueOf(String.valueOf(params.get(fieldName))).intValue());
					}else if(type == double.class){
						field.set(t,Double.valueOf(String.valueOf(params.get(fieldName))).intValue());
					}else if(type == float.class){
						field.set(t,Float.valueOf(String.valueOf(params.get(fieldName))).intValue());
					}else if(type == char.class){
						String source=String.valueOf(params.get(fieldName));
						if(source.length()!=1){
							logger.error("can not convert "+type+" to char.");
							throw new RuntimeException("can not convert "+type+" to char.");
						}
						field.set(t,String.valueOf(params.get(fieldName)).charAt(0));
					}else {
						logger.warn("unsupport type "+type+","+fieldName+" is skiped.");
					}
				}
			}catch (IllegalAccessException e) {
				logger.error("no access to convert field.");
				e.printStackTrace();
			}
		}
		return t;
	}
	
	/**
	 * list转obj二维数组
	 * @param list 待转换的list
	 * @return 
	 */
	public static <T> Object[][] toObjArray(List<T> list){
		try{
			if(list== null || list.size()==0){
				return null;
			}
			Field[] fields = list.get(0).getClass().getDeclaredFields();
			Object[][] objs=new Object[list.size()][fields.length];
			for(int row=0;row<list.size();row++){
				T t=list.get(row);
				fields =t.getClass().getDeclaredFields();
				for(int col=0;col<fields.length;col++){
					fields[col].setAccessible(true);
					objs[row][col]=fields[col].get(t);
				}
			}
			return objs;
		}catch(Exception e){
			logger.error("no access to convert field.");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * map转对象
	 * @param paramMap 待转换map
	 * @param cls 转换对象的class
	 * @return
	 */
	public static <T> T map2Object(Map<?, ?> paramMap, Class<T> cls) {
		return JSONObject.parseObject(JSONObject.toJSONString(paramMap), cls);
	}
	
	/**
	 * json转map
	 * @param jsonObj json字符串
	 * @return
	 */
    public static Map<String, Object> jsonToMap(String jsonObj) {
        return (Map<String, Object>) JSONObject.parseObject(jsonObj);
    }

    /**
    * 判断是否为基本类型
    * @param type 传入类型
    * @return
    */
    public static boolean isBaseType(Class<?> type) {
        if (type.equals(int.class)    ||
        	type.equals(byte.class)   ||
        	type.equals(long.class)   ||
        	type.equals(double.class) ||
        	type.equals(float.class)  ||
        	type.equals(char.class)   ||
        	type.equals(short.class)  ||
        	type.equals(boolean.class)) {
            return true;
        }
        return false;
    }
    
}
