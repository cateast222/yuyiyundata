package com.yuyiyun.YYdata.modular.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.system.entity.DataSourceInfo;
import com.yuyiyun.YYdata.modular.system.model.params.DataSourceInfoParam;
import com.yuyiyun.YYdata.modular.system.service.DataSourceInfoService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;

/**
 * 数据源信息表控制器
 * @author duhao
 *
 */
@Controller
@RequestMapping("/datasi")
public class DataSourceInfoController extends BaseController {
	private String PREFIX = "/modular/system/datasi";

	@Autowired
	private DataSourceInfoService dataSourceInfoService;

	/**
	 * 跳转到主页面
	 * 
	 * @return
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "/index.html";
	}

	/**
	 * 新增页面
	 * 
	 * @return
	 */
	@RequestMapping("/add")
	public String add() {
		return PREFIX + "/add.html";
	}

	/**
	 * 编辑页面
	 * 
	 * @author duhao
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit() {
		return PREFIX + "/edit.html";
	}

	/**
	 * 新增接口
	 *
	 * @author duhao
	 * 
	 * @Date 2019-03-13
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public ResponseData addItem(DataSourceInfoParam dataSourceInfoParam) {
		dataSourceInfoParam.setCreator(ShiroKit.getUser().getAccount());
		this.dataSourceInfoService.add(dataSourceInfoParam);
		return ResponseData.success();
	}

	/**
	 * 编辑接口
	 *
	 * @author duhao
	 * @Date 2019-03-13
	 */
	@RequestMapping("/editItem")
	@ResponseBody
	public ResponseData editItem(DataSourceInfoParam dataSourceInfoParam) {
		this.dataSourceInfoService.update(dataSourceInfoParam);
		return ResponseData.success();
	}

	/**
	 * 删除接口
	 *
	 * @author duhao
	 * @Date 2019-03-13
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseData delete(DataSourceInfoParam dataSourceInfoParam) {
		this.dataSourceInfoService.delete(dataSourceInfoParam);
		return ResponseData.success();
	}

	/**
	 * 查看详情接口
	 *
	 * @author duhao
	 * @Date 2019-03-13
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public ResponseData detail(DataSourceInfoParam dataSourceInfoParam) {
		DataSourceInfo detail = this.dataSourceInfoService.getById(dataSourceInfoParam.getUuid());
		return ResponseData.success(detail);
	}

	/**
	 * 查询列表
	 *
	 * @author duhao
	 * @Date 2019-03-13
	 */
	@RequestMapping("/list")
	@ResponseBody
	public LayuiPageInfo list(DataSourceInfoParam dataSourceInfoParam) {
		return this.dataSourceInfoService.findPageBySpec(dataSourceInfoParam);
	}
}
