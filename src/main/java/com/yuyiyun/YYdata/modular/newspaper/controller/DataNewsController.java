package com.yuyiyun.YYdata.modular.newspaper.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.util.DateTimeUtil;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;
import com.yuyiyun.YYdata.modular.datasource.service.DataSourceService;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNews;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewsParam;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewsService;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewspaperService;

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
 * @since 2020-01-03
 */
@Api(value = "新闻数据controller", tags = { "新闻数据操作接口" })
@Controller
@RequestMapping("/datanews")
public class DataNewsController extends BaseController {

	private String PREFIX = "/modular/newspaper";
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
		return PREFIX + "/news/index.html";
	}

	/**
	 * :新增更新页面
	 * 
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("/addAndEdit")
	public String addAndEdit(Long dataNewspaper, Long uuid, Model model) {
		if (ToolUtil.isEmpty(uuid)) {
			model.addAttribute("uuid", null);
			model.addAttribute("dataNewspaper", dataNewspaper);
			model.addAttribute("title", "新增");
		}
		if (ToolUtil.isEmpty(dataNewspaper)) {
			model.addAttribute("uuid", uuid);
			model.addAttribute("dataNewspaper", null);
			model.addAttribute("title", "编辑");
		}
		return PREFIX + "/news/add_edit.html";
	}

	/**
	 * :新增接口
	 * 
	 * @author duhao
	 * @param DataNews
	 * @return
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public ResponseData addItem(DataNewsParam param) {
		this.dataNewsService.add(param);
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
	public ResponseData delete(DataNewsParam param) {
		this.dataNewsService.delete(param);
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
	public ResponseData editItem(DataNewsParam param) {
		this.dataNewsService.update(param);
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
	public ResponseData addAndEditItem(DataNewsParam param) {
		this.dataNewsService.addOrEdit(param);
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
	public ResponseData detail(DataNewsParam param) {
		DataNews detail = this.dataNewsService.getById(param.getUuid());
		if (ToolUtil.isEmpty(detail) && ToolUtil.isNotEmpty(param.getDataNewspaper())) {
			DataNewspaper dataNewspaper = dataNewspaperService.getById(param.getDataNewspaper());
			DataSource dataSource = dataSourceService.getById(dataNewspaper.getDataSource());
			detail = new DataNews();
			detail.setChsName(dataNewspaper.getChsName());
			detail.setDataNewspaper(dataNewspaper.getUuid());
			detail.setDataSource(dataNewspaper.getDataSource());
			detail.setPubtime(dataNewspaper.getPublish());
			detail.setLanguage(dataSource.getLanguage());
		}
		JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(detail));
		jsonObject.put("pubtime", DateTimeUtil.dateToString(detail.getPubtime()));
		jsonObject.put("languageName", ConstantFactory.me().getDictsByName("语种", detail.getLanguage()));
		return ResponseData.success(jsonObject);
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
	public LayuiPageInfo list(DataNewsParam param) {
		return this.dataNewsService.findPageBySpec(param);
	}

	/**
	 * :报纸新闻主页获取报纸新闻列表
	 * 
	 * @author duhao
	 * @param dataSourceInfoParam
	 * @return
	 */
	@RequestMapping("/listFromNews")
	@ResponseBody
	public LayuiPageInfo listFromNews(DataNewsParam param) {
		return this.dataNewsService.findPageBySpec(param);
	}

	/**
	 * 
	 * @Description: API添加数据
	 * @author duhao
	 * @date 2020年1月5日
	 * @version V1.0
	 * @param param
	 * @return
	 */
	@ApiOperation(value = "数据新增", notes = "新增报纸新闻")
	@ApiImplicitParam(name = "param", value = "参数", required = false, paramType = "body", dataType = "DataNewsParam")
	@PostMapping("/addByApi")
	@ResponseBody
	public ResponseData addByApi(@RequestBody() DataNewsParam param) {
		DataNews add = this.dataNewsService.addOrEdit(param);
		if (add != null) {
			return ResponseData.success(add);
		} else {
			return ResponseData.error("数据添加异常！");
		}
	}

	/**
	 * 分页查询报刊数据
	 * 
	 * @param newspaperId
	 * @param limit
	 * @param page
	 * @return
	 */
	@ApiOperation(value = "数据归档", notes = "获取归档数据")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "newspaperId", value = "报纸ID", required = true, paramType = "query", dataType = "long"),
			@ApiImplicitParam(name = "page", value = "页数", required = true, paramType = "query", dataType = "int"),
			@ApiImplicitParam(name = "limit", value = "条数", required = true, paramType = "query", dataType = "int") })
	@PostMapping("/getArchiveByAPi")
	@ResponseBody
	public ResponseData getArchiveDataNews(Long newspaperId, int limit, int page) {
		if (ToolsUtil.isEmpty(newspaperId)) {
			return ResponseData.error("请求参数异常！");
		} else if (limit < 1 || page < 0) {
			return ResponseData.error("请求参数page、limit异常！");
		} else {
//			Long userId = ShiroKit.getUser().getId();
			LayuiPageInfo info = dataNewsService.getArchiveDataNews(newspaperId, limit, page);
			return ResponseData.success(info);
		}
	}

	/**
	 * 批量修改
	 * @author duhao
	 * @return
	 */
	@ApiOperation(value = "批量修改", notes = "批量修改数据")
	@PostMapping("/updateBatchByApi")
	@ResponseBody
	public ResponseData updateBatch(@RequestBody() List<DataNews> dataNews) {
		if (ToolsUtil.isEmpty(dataNews)) {
			return ResponseData.error("参数异常！");
		}
		boolean updateBatch = dataNewsService.updateBatch(dataNews);
		return ResponseData.success(updateBatch);
	}
	
	/**
	 * 获取推送数据
	 * @author duhao
	 * @param limit
	 * @return
	 */
	@ApiOperation(value = "推送数据", notes = "获取推送数据")
	@PostMapping("/getPushDatasByApi")
	@ResponseBody
	public ResponseData getPushDatas(Integer start,Integer limit) {
		if (ToolsUtil.isEmpty(start)||start<=0) {
			limit = 0;
		}
		if (ToolsUtil.isEmpty(limit)||limit<=0) {
			limit = 50;
		}
		Long userId = ShiroKit.getUser().getId();
		List<Map<String,Object>> pushDatas = dataNewsService.getPushDatas(userId, limit,start);
		return ResponseData.success(pushDatas);
	}
}
