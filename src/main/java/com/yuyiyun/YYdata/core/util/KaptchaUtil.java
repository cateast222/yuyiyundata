package com.yuyiyun.YYdata.core.util;

import com.yuyiyun.YYdata.config.properties.YYdataProperties;
import cn.stylefeng.roses.core.util.SpringContextHolder;

/**
 * 验证码工具类
 */
public class KaptchaUtil {

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOnOff() {
        return SpringContextHolder.getBean(YYdataProperties.class).getKaptchaOpen();
    }
}