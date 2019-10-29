package com.yuyiyun.YYdata.modular.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yuyiyun.YYdata.modular.system.entity.DataSourceInfo;
import com.yuyiyun.YYdata.modular.system.service.DataConfigInfoService;
import com.yuyiyun.YYdata.modular.system.service.DataSourceInfoService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;

@Controller
@RequestMapping("/dataci")
public class DataConfigInfoController extends BaseController {
	private String PREFIX = "/modular/system/dataci";

	@Autowired
	private DataSourceInfoService dataSourceInfoService;
	@Autowired
	private DataConfigInfoService dataConfigInfoService;

	/**
	 * 跳转到主页面
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String index(@RequestParam("datasiUuid") String datasiUuid, Model model) {
		model.addAttribute("datasiUuid", datasiUuid);
		DataSourceInfo dataSourceInfo = dataSourceInfoService.getById(datasiUuid);
		if (dataSourceInfo == null) {
			throw new RequestEmptyException();
		}
		model.addAttribute("websiteName", dataSourceInfo.getWebsiteName());
		return PREFIX + "/index.html";
	}

	@RequestMapping("/edit")
	public String edit(String key, String summary, String dsiUuid, Integer state, Model model) {
		model.addAttribute("key", key);
		model.addAttribute("summary", summary);
		model.addAttribute("dsiUuid", dsiUuid);
		return PREFIX + "/config" + state + ".html";
	}

}
