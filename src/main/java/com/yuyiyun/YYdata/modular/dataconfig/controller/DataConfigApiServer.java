package com.yuyiyun.YYdata.modular.dataconfig.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataConfig;
import com.yuyiyun.YYdata.modular.dataconfig.service.DataConfigService;

import cn.stylefeng.roses.core.reqres.response.ResponseData;


/**
 * <p>
 * 数据配置API服务
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
@Controller
@RequestMapping({"/yydataApi"})
public class DataConfigApiServer {
    @Autowired
    private DataConfigService dataConfigService;
    
    @PostMapping("/dataconfig/getEQsByApi")
	@ResponseBody
    public ResponseData getValuesByApi(@RequestBody() DataConfig dataConfig, String... columns) {
    	if (ToolsUtil.isPojoEmpty(dataConfig)) {
    		return ResponseData.error("参数异常，请检查！");
		}else {
			List<Map<String,Object>> list = dataConfigService.getEQsByApi(dataConfig, columns);
			return ResponseData.success(list);
		}
    }
    

}
