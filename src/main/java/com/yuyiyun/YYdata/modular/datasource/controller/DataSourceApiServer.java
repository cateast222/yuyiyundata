package com.yuyiyun.YYdata.modular.datasource.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.datasource.entity.DataApiauth;
import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;
import com.yuyiyun.YYdata.modular.datasource.service.DataApiauthService;
import com.yuyiyun.YYdata.modular.datasource.service.DataSourceService;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

/**
 * <p>
 * 数据源API服务
 * </p>
 *
 * @author duhao
 * @since 2020-04-07
 */
@Controller
@RequestMapping("/yydataApi")
public class DataSourceApiServer {


	@Autowired
	private DataApiauthService apiDataAuthService;
	@Autowired
	private DataSourceService dataSourceService;
	
	/**
	 * 
	 * @Description: API获取详情
	 * @author duhao
	 * @date 2020年1月5日
	 * @version V1.0
	 * @param uuid
	 * @return
	 */
	@GetMapping("/datasource/detailByApi")
	@ResponseBody
	public ResponseData detailByApi(String uuid) {
		DataSource dataSource = this.dataSourceService.getById(uuid);
		return ResponseData.success(dataSource);
	}

	/**
	 * 
	 * @Description: API获取详情
	 * @author duhao
	 * @date 2020年1月5日
	 * @version V1.0
	 * @param uuid
	 * @return
	 */
	@PostMapping("/datasource/getEQsByApi")
	@ResponseBody
	public ResponseData getEQsByApi(@RequestBody() DataSource dataSource, String... columns) {
		if (ToolsUtil.isPojoEmpty(dataSource)) {
			return ResponseData.error("参数异常，请检查！");
		} else {
			List<Map<String, Object>> list = this.dataSourceService.getEQsByApi(dataSource, columns);
			return ResponseData.success(list);
		}
	}

	@PostMapping("/apidataauth/getEQsByApi")
	@ResponseBody
	public ResponseData getValuesByApi(@RequestBody() DataApiauth apiDataAuth, String... columns) {
		if (ToolsUtil.isPojoEmpty(apiDataAuth)) {
			return ResponseData.error("参数异常，请检查！");
		} else {
			List<Map<String, Object>> list = apiDataAuthService.getEQsByApi(apiDataAuth, columns);
			return ResponseData.success(list);
		}
	}

}
