package com.yuyiyun.YYdata.config.datasource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 多数据源配置，多数据源配置因为和单数据源冲突，所以现在默认版本删除了多数据源配置
 *
 * 可参考 https://gitee.com/stylefeng/guns/tree/multi-datasource/
 *
 * @author duhao
 * @Date 2017/5/20 21:58
 */
@Configuration
@ConditionalOnProperty(prefix = "YYdata.muti-datasource", name = "open", havingValue = "false", matchIfMissing = true)
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(basePackages = {"com.yuyiyun.YYdata.modular.*.mapper"})
public class SingleDataSourceConfig {

}

