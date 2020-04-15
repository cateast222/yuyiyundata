package com.yuyiyun.YYdata.modular.datasource.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.datasource.entity.ApiDataAuth;
import com.yuyiyun.YYdata.modular.datasource.model.param.DataSourceParam;
import com.yuyiyun.YYdata.modular.datasource.service.ApiDataAuthService;
import com.yuyiyun.YYdata.modular.datasource.service.DataSourceService;
import com.yuyiyun.YYdata.modular.system.entity.Dict;
import com.yuyiyun.YYdata.modular.system.service.UserService;
import com.yuyiyun.YYdata.modular.system.warpper.UserWrapper;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author duhao
 * @since 2020-04-07
 */
@Controller
@Api(value = "数据源权限controller", tags = { "数据源权限操作接口" })
@RequestMapping("/apidataauth")
public class ApiDataAuthController {

	private String PREFIX = "/modular/datasource";

	@Autowired
	private ApiDataAuthService apiDataAuthService;
	@Autowired
	private UserService userService;
	@Autowired
	private DataSourceService dataSourceService;

	/**
	 * 跳转到主页面
	 */
	@GetMapping
	public String index() {
		return PREFIX + "/apidataauth/index.html";
	}

	/**
	 * 跳转到新增页面
	 */
	@GetMapping("/add")
	public String add(Model model) {
		List<Dict> dataSourcePlatform = ConstantFactory.me().findInDict("数据源平台");
		model.addAttribute("dataSourcePlatform", dataSourcePlatform);
		return PREFIX + "/apidataauth/add.html";
	}

	/**
	 * 跳转到修改页面
	 */
	@GetMapping("/edit")
	public String edit() {
		return PREFIX + "/apidataauth/edit.html";
	}

	/**
	 * 查询列表
	 * 
	 * @param dataAuth
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public LayuiPageInfo pageList(ApiDataAuth apiDataAuth, int limit, int page) {
		LayuiPageInfo info = apiDataAuthService.selectPageList(apiDataAuth, limit, page);
		return info;
	}

	/**
	 * 新增数据
	 * 
	 * @param dataAuth
	 * @return
	 */
	@RequestMapping("/addItems")
	@ResponseBody
	public ResponseData addItems(@RequestParam(value = "ids[]", required = true) List<Long> ids,
			@RequestParam(value = "chsnames[]", required = true) List<String> chsnames, ApiDataAuth apiDataAuth) {
		apiDataAuthService.addDataAuths(ids,chsnames,apiDataAuth);
		return ResponseData.success();
	}

	public ResponseData addItem(ApiDataAuth apiDataAuth) {
		apiDataAuthService.addDataAuth(apiDataAuth);
		return ResponseData.success();
	}

	/**
	 * 修改数据
	 * 
	 * @param dataAuth
	 * @return
	 */
	@RequestMapping("/editItem")
	@ResponseBody
	public ResponseData editItem(ApiDataAuth apiDataAuth) {
		apiDataAuthService.editDataAuth(apiDataAuth);
		return ResponseData.success();
	}

	/**
	 * 查看详情
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public ResponseData detail(String uuid) {
		ApiDataAuth apiDataAuth = apiDataAuthService.getDetailsById(uuid);
		return ResponseData.success(apiDataAuth);
	}

	/**
	 * 根据ID删除记录
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseData delete(String uuid) {
		apiDataAuthService.deleteById(uuid);
		return ResponseData.success();
	}

	/**
	 * 批量删除数据
	 * 
	 * @param ids ID集合
	 * @return
	 */
	@RequestMapping("/deleteBatch")
	@ResponseBody
	public ResponseData deleteBatch(@RequestParam(value = "ids[]", required = true) List<Long> ids) {
		apiDataAuthService.deleteBatch(ids);
		return ResponseData.success();
	}

	/**
	 * 获取客户数据
	 * 
	 * @param userName 客户名称
	 * @param deptId   部门id
	 * @return
	 */
	@RequestMapping("/getcustomer")
	@ResponseBody
	public LayuiPageInfo getCustomer(String userName, Long deptId) {
		Page<Map<String, Object>> users = userService.selectUsers(null, userName, null, null, deptId);
		Page<?> wrapped = new UserWrapper(users).wrap();
		LayuiPageInfo info = LayuiPageFactory.createPageInfo(wrapped);
		return info;
	}

	/**
	 * :获取数据源数据
	 * 
	 * @author duhao
	 * @param DataSourceParam
	 * @return
	 */
	@RequestMapping("/getdatasource")
	@ResponseBody
	public LayuiPageInfo getDataSource(DataSourceParam param) {
		return dataSourceService.findPageBySpec(param);
	}

	@ApiOperation(value = "获取数据权限", notes = "动态查询调取数据权限")
	@PostMapping("/getEQsByApi")
	@ResponseBody
	public ResponseData getValuesByApi(@RequestBody() ApiDataAuth apiDataAuth, String... columns) {
		if (ToolsUtil.isPojoEmpty(apiDataAuth)) {
			return ResponseData.error("参数异常，请检查！");
		} else {
			List<Map<String, Object>> list = apiDataAuthService.getEQsByApi(apiDataAuth, columns);
			return ResponseData.success(list);
		}
	}

}
