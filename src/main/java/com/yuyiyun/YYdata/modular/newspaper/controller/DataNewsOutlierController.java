package com.yuyiyun.YYdata.modular.newspaper.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.util.DateTimeUtil;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.datasource.service.DataSourceService;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewsParam;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewsService;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewspaperService;

import cn.stylefeng.roses.core.base.controller.BaseController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author duhao
 * @since 2020-01-03
 */
@Controller
@RequestMapping("/newsOutlier")
public class DataNewsOutlierController extends BaseController {

	private String PREFIX = "/modular/newspaper/newsoutlier";
	@Autowired
	DataSourceService dataSourceService;
	@Autowired
	DataNewsService dataNewsService;
	@Autowired
	DataNewspaperService dataNewspaperService;

	/**
	 * @Description: 数据页面
	 * @author duhao
	 * @date 2020年1月5日
	 * @version V1.0
	 * @return
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "/index.html";
	}

	/**
	 * :查询列表
	 * 
	 * @author duhao
	 * @param dataSourceInfoParam
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public LayuiPageInfo list(DataNewsParam param,String pubtimeStr) {
		param.setPushState(5);
		if (ToolsUtil.isNotEmpty(pubtimeStr)) {
			param.setPubtime(DateTimeUtil.stringToDate(pubtimeStr, "yyyy-MM-dd"));
		}
		return this.dataNewsService.findPageBySpec(param);
	}
}
