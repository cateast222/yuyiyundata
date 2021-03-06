package com.yuyiyun.YYdata.modular.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.system.entity.DataSourceInfo;
import com.yuyiyun.YYdata.modular.system.model.params.DataSourceInfoParam;
import com.yuyiyun.YYdata.modular.system.service.DataSourceInfoService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 数据源信息表控制器
 * 
 * @author duhao
 *
 */
@Api(value = "闻歌电子报纸controller", tags = { "闻歌电子报纸操作接口" })
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
//	@ApiOperation(value = "获取数据源信息", notes = "根据条件查询数据源")
//	@ApiImplicitParam(name = "dataSourceInfoParam", value = "查询条件", required = false, paramType = "body", dataType = "DataSourceInfoParam")
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

	/**
	 * 报纸数据测试查询列表
	 *
	 * @author duhao
	 * @Date 2019-03-13
	 */
	@RequestMapping("/datatestlist")
	@ResponseBody
	public LayuiPageInfo datatestlist(DataSourceInfoParam dataSourceInfoParam) {
		return this.dataSourceInfoService.findDataTestPageBySpec(dataSourceInfoParam);
	}

	/**
	 * 查看详情接口 *
	 * 
	 * @author duhao
	 * @Date 2019-03-13
	 */
	@ApiOperation(value = "获取数据源信息", notes = "根据UUID查询数据源")
	@ApiImplicitParam(name = "uuid", value = "uuid", required = true, paramType = "query", dataType = "String")
	@GetMapping("/detail")
	@ResponseBody
	public ResponseData detailByApi(String uuid) {
		DataSourceInfo detail = this.dataSourceInfoService.getById(uuid);
		return ResponseData.success(detail);
	}
}
