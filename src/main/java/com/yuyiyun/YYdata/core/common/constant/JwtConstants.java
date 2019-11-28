package com.yuyiyun.YYdata.core.common.constant;

/**
 * jwt相关配置
 *
 * @author duhao
 * @date 2017-08-23 9:23
 */
public interface JwtConstants {

    String AUTH_HEADER = "Authorization";

    String SECRET = "defaultSecret";

    Long EXPIRATION = 604800L;

    String AUTH_PATH = "/yydataApi/auth";
    
    String GRANT_TYPE_TOKEN = "token";
    String GRANT_TYPE_REFRESHTTOKEN = "refreshToken";

}
