package com.yuyiyun.YYdata.modular.newspaper.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.util.DateTimeUtil;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;
import com.yuyiyun.YYdata.modular.datasource.service.DataSourceService;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewspaperParam;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewspaperService;
import com.yuyiyun.YYdata.modular.system.entity.Dict;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author duhao
 * @since 2020-01-02
 */
@Api(value = "报刊报纸controller", tags = { "报刊报纸操作接口" })
@Controller
@RequestMapping("/newspaper")
public class DataNewspaperController extends BaseController {

	private String PREFIX = "/modular/newspaper";
	@Autowired
	DataNewspaperService dataNewspaperService;
	@Autowired
	DataSourceService dataSourceService;

	/**
	 * :数据页面
	 * 
	 * @author duhao
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Dict> provider = ConstantFactory.me().findInDict("数据源提供方");
		model.addAttribute("provider", provider);
		return PREFIX + "/newspaper/index.html";
	}

	/**
	 * :新增更新页面
	 * 
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("/addAndEdit")
	public String addAndEdit(Long uuid, Long dataSource, Model model) {
		List<Dict> newsState = ConstantFactory.me().findInDict("新闻状态");
		model.addAttribute("newsState", newsState);
		if (ToolUtil.isEmpty(uuid)) {
			model.addAttribute("uuid", null);
			model.addAttribute("title", "新增");
		} else {
			model.addAttribute("uuid", uuid);
			model.addAttribute("title", "编辑");
		}
		model.addAttribute("dataSource", dataSource);
		return PREFIX + "/newspaper/add_edit.html";
	}
	
	@RequestMapping("check")
	public String check() {
		return PREFIX + "/newspaper/news_check.html";
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
	public ResponseData addItem(DataNewspaperParam param) {
		this.dataNewspaperService.add(param);
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
	public ResponseData delete(DataNewspaperParam param) {
		this.dataNewspaperService.delete(param);
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
	public ResponseData editItem(DataNewspaperParam param) {
		this.dataNewspaperService.update(param);
		return ResponseData.success();
	}

	/**
	 * :新增更新接口
	 * 
	 * @author duhao
	 * @param param
	 * @return
	 */
	@RequestMapping("/addAndEditItem")
	@ResponseBody
	public ResponseData addAndEditItem(DataNewspaperParam param) {
		this.dataNewspaperService.addOrEdit(param);
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
	public ResponseData detail(DataNewspaperParam param) {
		DataNewspaper detail = this.dataNewspaperService.getById(param.getUuid());
		if (ToolUtil.isEmpty(detail) && ToolUtil.isNotEmpty(param.getDataSource())) {
			DataSource dataSource = dataSourceService.getById(param.getDataSource());
			detail = new DataNewspaper();
			detail.setDataSource(dataSource.getUuid());
			detail.setChsName(dataSource.getChsName());
			detail.setOrgName(dataSource.getOrgName());
			detail.setState("1");
		}
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
	public LayuiPageInfo list(DataNewspaperParam param) {
		return this.dataNewspaperService.findPageBySpec(param);
	}

	/**
	 * :电子报纸主页获取电子报纸列表
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping("/listFromNewspaper")
	@ResponseBody
	public LayuiPageInfo listFromNewspaper(Long dataSource, String condition) {
		return this.dataNewspaperService.listFromNewspaper(dataSource, condition);
	}

	/**
	 * :报纸新闻主页获取电子报纸列表
	 * 
	 * @param condition
	 * @return
	 */
	@RequestMapping("/listFromNews")
	@ResponseBody
	public LayuiPageInfo listFromNews(String publish, String condition) {
		return this.dataNewspaperService.listFromNews(publish, condition);
	}

	/**
	 * @Description: API添加数据
	 * @author duhao
	 * @date 2019年12月4日
	 * @version V1.0
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "数据新增", notes = "新增电子报纸")
	@ApiImplicitParam(name = "param", value = "参数", required = false, paramType = "body", dataType = "DataNewspaperParam")
	@PostMapping("/addByApi")
	@ResponseBody
	public ResponseData addByApi(@RequestBody() DataNewspaperParam param) {
		DataNewspaper add = this.dataNewspaperService.addOrEdit(param);
		if (add != null) {
			return ResponseData.success(add);
		} else {
			return ResponseData.error("数据添加异常！");
		}
	}
	
	/**
	 * @Description: API删除数据
	 * @author duhao
	 * @date 2020年1月5日
	 * @version V1.0
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "数据删除", notes = "删除电子报纸")
	@ApiImplicitParam(name = "param", value = "参数", required = false, paramType = "body", dataType = "DataNewspaperParam")
	@PostMapping("/delByApi")
	@ResponseBody
	public ResponseData delByApi(@RequestBody() DataNewspaperParam param) {
		if (ToolsUtil.isNotEmpty(param.getUuid())) {
			this.dataNewspaperService.delete(param);
			return ResponseData.success();
		} else {
			return ResponseData.error("数据添加异常！");
		}
	}

	/**
	 * Description: API数据排重
	 * 
	 * @author duhao
	 * @param pubTime
	 * @param dsiUuid
	 * @return
	 */
	@ApiOperation(value = "数据排重", notes = "排重电子报纸")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pubTime", value = "发布日期", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "url", value = "URL", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "dsiUuid", value = "数据源", required = true, paramType = "query", dataType = "String") })
	@PostMapping("/isExistByApi")
	@ResponseBody
	public ResponseData isExistByApi(String pubTime, String url, String dsiUuid) {
		if ((ToolsUtil.isEmpty(url) && ToolsUtil.isEmpty(pubTime) || ToolsUtil.isEmpty(dsiUuid))) {
			return ResponseData.error("请求参数异常");
		}
		return this.dataNewspaperService.isExist(dsiUuid, url, pubTime);
	}

	/**
	 * :按归档日期分页查询电子报纸数据
	 * 
	 * @param sysUser
	 * @param archiveDate
	 * @param limit
	 * @param page
	 * @return
	 */
	@ApiOperation(value = "数据归档", notes = "按日期获取归档数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "archiveDate", value = "归档日期yyyy-MM-dd", required = true, paramType = "query", dataType = "String"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "limit", value = "条数", required = true, paramType = "query", dataType = "int") })
	@PostMapping("/getArchiveByApi")
	@ResponseBody
	public ResponseData getArchiveNewspaper(String archiveDate, int limit, int page) {
		if (ToolsUtil.isEmpty(archiveDate) && ToolsUtil.isEmpty(DateTimeUtil.stringToDate(archiveDate, "yyyy-MM-dd"))) {
			return ResponseData.error("请求参数archiveDate异常！");
		} else if (limit < 1 || page < 0) {
			return ResponseData.error("请求参数page、limit异常！");
		} else {
			Long userId = ShiroKit.getUser().getId();
			LayuiPageInfo info = dataNewspaperService.getArchiveNewspaper(userId, archiveDate, limit, page);
			return ResponseData.success(info);
		}
	}
}
