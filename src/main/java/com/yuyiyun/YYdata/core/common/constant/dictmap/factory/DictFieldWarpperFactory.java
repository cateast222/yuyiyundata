package com.yuyiyun.YYdata.core.common.constant.dictmap.factory;

import java.lang.reflect.Method;

import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;
import com.yuyiyun.YYdata.core.common.constant.factory.IConstantFactory;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;

import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * 字典字段的包装器(从ConstantFactory中获取包装值)
 *
 * @author duhao
 * @date 2017-05-06 15:12
 */
public class DictFieldWarpperFactory {

	public static Object createFieldWarpper(Object parameter, String methodName) {
		IConstantFactory constantFactory = ConstantFactory.me();
		try {
			Method method = IConstantFactory.class.getMethod(methodName, parameter.getClass());
			return method.invoke(constantFactory, parameter);
		} catch (Exception e) {
			try {
				Method method = IConstantFactory.class.getMethod(methodName, Long.class);
				return method.invoke(constantFactory, Long.parseLong(parameter.toString()));
			} catch (Exception e1) {
				throw new ServiceException(BizExceptionEnum.ERROR_WRAPPER_FIELD);
			}
		}
	}

}
