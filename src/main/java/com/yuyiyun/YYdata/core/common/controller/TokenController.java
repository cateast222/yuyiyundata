package com.yuyiyun.YYdata.core.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

@RestController
@RequestMapping(value = "/yydataApi")
public class TokenController {
	
	@RequestMapping("/auth")
	public ResponseData token(String grantType,String appId , String appSecret) {
		
		return ResponseData.success();		
	}

}
