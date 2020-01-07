package com.yuyiyun.YYdata.modular.datasource.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;
import com.yuyiyun.YYdata.modular.datasource.model.param.DataSourceParam;
import com.yuyiyun.YYdata.modular.datasource.service.DataSourceService;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author duhao
 * @since 2020-01-03
 */
@Api(value = "数据源controller", tags = { "数据源操作接口" })
@Controller
@RequestMapping({ "/datasource", "/yydataApi/datasource" })
public class DataSourceController {
	private String PREFIX = "/modular/datasource";
	@Autowired
	DataSourceService dataSourceService;

	/**
	 * :数据源主页面
	 * 
	 * @author duhao
	 * @return
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "/datasource/index.html";
	}
	
	/**
	 * :新增更新(数据源)页面
	 * 
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("/addAndEdit")
	public String addAndEdit(Long uuid, Model model) {
		if (ToolUtil.isEmpty(uuid)) {
			model.addAttribute("uuid", null);
			model.addAttribute("title", "新增");
		} else {
			model.addAttribute("uuid", uuid);
			model.addAttribute("title", "编辑");
		}
		return PREFIX + "/datasource/add_edit.html";
	}

	/**
	 * :电子报纸数据源主页面
	 * 
	 * @author duhao
	 * @return
	 */
	@RequestMapping("/newspaper")
	public String newspaper() {
		return PREFIX + "/newspaper/index.html";
	}
	
	/**
	 * :新增更新(电子报纸数据源)页面
	 * 
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("/addAndEditNewspaper")
	public String addAndEditNewspaper(Long uuid, Model model) {
		if (ToolUtil.isEmpty(uuid)) {
			model.addAttribute("uuid", null);
			model.addAttribute("title", "新增");
		} else {
			model.addAttribute("uuid", uuid);
			model.addAttribute("title", "编辑");
		}
		return PREFIX + "/newspaper/add_edit.html";
	}


	/**
	 * :新增更新接口
	 * 
	 * @author duhao
	 * @param dataNewspaper
	 * @return
	 */
	@RequestMapping("/addAndEditItem")
	@ResponseBody
	public ResponseData addAndEditItem(DataSourceParam param) {
		param.setCreator(ShiroKit.getUser().getAccount());
		this.dataSourceService.addOrEdit(param);
		return ResponseData.success();
	}

	/**
	 * :新增接口
	 * 
	 * @author duhao
	 * @param dataNewspaper
	 * @return
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public ResponseData addItem(DataSourceParam param) {
		param.setCreator(ShiroKit.getUser().getAccount());
		this.dataSourceService.add(param);
		return ResponseData.success();
	}

	/**
	 * :删除接口
	 * 
	 * @author duhao
	 * @param dataSourceInfoParam
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseData delete(DataSourceParam param) {
		this.dataSourceService.delete(param);
		return ResponseData.success();
	}

	/**
	 * :编辑接口
	 * 
	 * @author duhao
	 * @param param
	 * @return
	 */
	@RequestMapping("/editItem")
	@ResponseBody
	public ResponseData editItem(DataSourceParam param) {
		this.dataSourceService.update(param);
		return ResponseData.success();
	}

	/**
	 * :查看详情接口
	 * 
	 * @author duhao
	 * @param dataSourceInfoParam
	 * @return
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public ResponseData detail(DataSourceParam param) {
		DataSource detail = this.dataSourceService.getById(param.getUuid());
		return ResponseData.success(detail);
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
	public LayuiPageInfo list(DataSourceParam param) {
		return this.dataSourceService.findPageBySpec(param);
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
	@ApiOperation(value = "获取数据源", notes = "根据UUID查询数据源")
	@ApiImplicitParam(name = "uuid", value = "uuid", required = true, paramType = "query", dataType = "String")
	@GetMapping("/detail")
	@ResponseBody
	public ResponseData detailByApi(String uuid) {
		DataSource dataSource = this.dataSourceService.getById(uuid);
		return ResponseData.success(dataSource);
	}
}