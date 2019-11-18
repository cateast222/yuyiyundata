package com.yuyiyun.YYdata.modular.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.system.model.params.DataSourceInfoParam;
import com.yuyiyun.YYdata.modular.system.model.params.WgEleNewsDataParam;
import com.yuyiyun.YYdata.modular.system.service.WgEleNewsDataService;

import cn.stylefeng.roses.core.base.controller.BaseController;

/**
 * 闻歌电子报纸数据表控制器
 * 
 * @author duhao
 *
 */
@Controller
@RequestMapping("/wendata")
public class WgEleNewsDataController extends BaseController {
	private String PREFIX = "/modular/system/wendata";
	@Autowired
	private WgEleNewsDataService wgEleNewsDataService;
	
	/**
	 * 查询列表
	 *
	 * @author duhao
	 * @Date 2019-11-18
	 */
	@RequestMapping("/list")
	@ResponseBody
	public LayuiPageInfo list(WgEleNewsDataParam wgEleNewsDataParam) {
		System.out.println(wgEleNewsDataParam);
		return this.wgEleNewsDataService.findPageBySpec(wgEleNewsDataParam);
	}
}
