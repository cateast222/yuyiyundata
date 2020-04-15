package com.yuyiyun.YYdata.core.util;

import java.lang.reflect.Field;

import cn.stylefeng.roses.kernel.model.util.ValidateUtil;

public class ToolsUtil extends ValidateUtil {

	/**
	 * 判断实体对象是否为空
	 * 
	 * @param obj 实体对象
	 * @return T：对象或者所有属性为空，F：不为空。
	 */
	public static boolean isPojoEmpty(Object obj) {
		// 对象本身就为null
		if (isEmpty(obj)) {
			return true;
		}
		//获取所有成员变量
		Field[] fieldList = obj.getClass().getDeclaredFields();
		try {
			for (int i = 0; i < fieldList.length; i++) {
				Field f = fieldList[i];
				//设置属性是可以访问的（私有的也可以）
				f.setAccessible(true);
				String fn = f.getName();
				Object fv = f.get(obj);
				//去掉实例化常量
				if ((!fn.equals("serialVersionUID")) && isNotEmpty(fv)) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

}
