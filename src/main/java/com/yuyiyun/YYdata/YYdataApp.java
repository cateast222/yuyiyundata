package com.yuyiyun.YYdata;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import cn.stylefeng.roses.core.config.WebAutoConfiguration;

/**
 * SpringBoot方式启动类
 *
 * @author stylefeng
 * @Date 2017/5/21 12:06
 */
@SpringBootApplication(exclude = {WebAutoConfiguration.class})
@EnableScheduling
public class YYdataApp {

    private final static Logger logger = LoggerFactory.getLogger(YYdataApp.class);

    public static void main(String[] args) {
        SpringApplication.run(YYdataApp.class, args);
        logger.info(YYdataApp.class.getSimpleName() + " is success!");
    }
}