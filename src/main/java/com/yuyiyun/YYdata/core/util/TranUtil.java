package com.yuyiyun.YYdata.core.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 对象转换工具类
 * 
 * @author duhao
 *
 */
public class TranUtil {

	/**
	 * Map转Object
	 * 
	 * @param map       Map对象
	 * @param beanClass Object类
	 * @return 返回Object对象
	 * @throws Exception
	 */
	public static Object mapToObj(Map<String, Object> map, Class<?> beanClass) throws Exception {
		Object object = JSON.parseObject(JSON.toJSONString(map), beanClass);
		return object;
	}

	/**
	 * Object转Map
	 * 
	 * @param obj Object对象
	 * @return 返回Map对象
	 */
	public static Map<String,Object> objToMap(Object obj) {
		
		return JSONObject.parseObject(JSON.toJSONString(obj));
	}
}
