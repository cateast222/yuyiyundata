package com.yuyiyun.YYdata.modular.datasource.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataConfig;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;
import com.yuyiyun.YYdata.modular.dataconfig.service.DataConfigService;
import com.yuyiyun.YYdata.modular.dataconfig.service.DataDictService;
import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;
import com.yuyiyun.YYdata.modular.datasource.model.param.DataSourceParam;
import com.yuyiyun.YYdata.modular.datasource.service.DataSourceService;
import com.yuyiyun.YYdata.modular.system.entity.DataConfigInfo;
import com.yuyiyun.YYdata.modular.system.entity.DataDicInfo;
import com.yuyiyun.YYdata.modular.system.entity.DataSourceInfo;
import com.yuyiyun.YYdata.modular.system.entity.Dict;
import com.yuyiyun.YYdata.modular.system.service.DataConfigInfoService;
import com.yuyiyun.YYdata.modular.system.service.DataDicInfoService;
import com.yuyiyun.YYdata.modular.system.service.DataSourceInfoService;

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
@RequestMapping("/datasource")
public class DataSourceController {
	private String PREFIX = "/modular/datasource";
	@Autowired
	DataSourceService dataSourceService;
	@Autowired
	DataSourceInfoService dataSourceInfoService;
	@Autowired
	DataConfigInfoService dataConfigInfoService;

	@Autowired
	DataDicInfoService dataDicInfoService;
	@Autowired
	DataDictService dataDictService;
	@Autowired
	DataConfigService dataConfigService;

	/**
	 * :数据源主页面
	 * 
	 * @author duhao
	 * @return
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Dict> dataSourceState = ConstantFactory.me().findInDict("数据源状态");
		model.addAttribute("dataSourceState", dataSourceState);
		List<Dict> dataSourcePlatform = ConstantFactory.me().findInDict("数据源平台");
		model.addAttribute("dataSourcePlatform", dataSourcePlatform);
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
		List<Dict> dataSourceState = ConstantFactory.me().findInDict("数据源状态");
		model.addAttribute("dataSourceState", dataSourceState);
		List<Dict> dataSourcePlatform = ConstantFactory.me().findInDict("数据源平台");
		model.addAttribute("dataSourcePlatform", dataSourcePlatform);
		List<Dict> nationalArea = ConstantFactory.me().findInDict("国家地区");
		model.addAttribute("dataSourceNationalArea", nationalArea);
		List<Dict> proxy = ConstantFactory.me().findInDict("境区");
		model.addAttribute("dataSourceProxy", proxy);
		List<Dict> language = ConstantFactory.me().findInDict("语种");
		model.addAttribute("dataSourceLanguage", language);
		List<Dict> encoded = ConstantFactory.me().findInDict("字符集");
		model.addAttribute("dataSourceEncoded", encoded);
		List<Dict> provider = ConstantFactory.me().findInDict("数据源提供方");
		model.addAttribute("dataSourceProvider", provider);
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
	public String newspaper(Model model) {
		List<Dict> dataSourceState = ConstantFactory.me().findInDict("数据源状态");
		model.addAttribute("dataSourceState", dataSourceState);
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
		List<Dict> dataSourceState = ConstantFactory.me().findInDict("数据源状态");
		model.addAttribute("dataSourceState", dataSourceState);
		List<Dict> nationalArea = ConstantFactory.me().findInDict("国家地区");
		model.addAttribute("dataSourceNationalArea", nationalArea);
		List<Dict> proxy = ConstantFactory.me().findInDict("境区");
		model.addAttribute("dataSourceProxy", proxy);
		List<Dict> language = ConstantFactory.me().findInDict("语种");
		model.addAttribute("dataSourceLanguage", language);
		List<Dict> encoded = ConstantFactory.me().findInDict("字符集");
		model.addAttribute("dataSourceEncoded", encoded);
		List<Dict> provider = ConstantFactory.me().findInDict("数据源提供方");
		model.addAttribute("dataSourceProvider", provider);
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
	 * :电子报纸主页获取数据源列表
	 * 
	 * @param condition
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/listFromNewspaper")
	@ResponseBody
	public LayuiPageInfo listFromNewspaper(String condition) {
		Page page = LayuiPageFactory.defaultPage();
		List<Map<String, Object>> list = this.dataSourceService.listFromNewspaper(page, condition);
		page.setRecords(list);
		return LayuiPageFactory.createPageInfo(page);
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
	@GetMapping("/detailByApi")
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
	@ApiOperation(value = "获取数据源", notes = "动态查询数据源")
	@PostMapping("/getEQsByApi")
	@ResponseBody
	public ResponseData getEQsByApi(@RequestBody() DataSource dataSource, String... columns) {
		if (ToolsUtil.isPojoEmpty(dataSource)) {
			return ResponseData.error("参数异常，请检查！");
		} else {
			List<Map<String, Object>> list = this.dataSourceService.getEQsByApi(dataSource, columns);
			return ResponseData.success(list);
		}
	}

	/**
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("/ds")
	public String ds() {
		List<DataSourceInfo> list = dataSourceInfoService.list(new QueryWrapper<DataSourceInfo>().eq("state", "1"));
		System.out.println(list.size());
		int i = 0;
		for (DataSourceInfo ds : list) {
			DataSourceParam dataSource = new DataSourceParam();
			dataSource.setChsName(ds.getName());
			dataSource.setCountry("CHN");
			dataSource.setCreateTime(ds.getCreateTime());
			dataSource.setEncoded("6");
			dataSource.setLanguage("zh_CN");
			dataSource.setOrgName(ds.getName());
			dataSource.setPlatform("1");
			dataSource.setProxy(ds.getProxy().toString());
			dataSource.setRegion(ds.getRegion());
			dataSource.setState("1");
			dataSource.setUpdateTime(new Date());
			dataSource.setProvider("YYCJ_java02");
			dataSource.setWebsiteName(ds.getWebsiteName());
			dataSource.setWebsiteUrl(ds.getWebsiteUrl());
			dataSource.setRemark(ds.getUuid());

			System.out.println("完成：" + (++i));
			this.dataSourceService.add(dataSource);
		}
		return PREFIX + "";
	}

	/**
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("/migration")
	public String migration() {
		List<DataSource> list = dataSourceService.list(
				new QueryWrapper<DataSource>().eq("platform", "1").eq("state", "1").eq("provider", "YYCJ_java02"));
		System.out.println(list.size());
		int i = 0;
		for (DataSource ds : list) {
			System.out.println("~~~~~开始迁移：" + ds.getChsName());
			List<DataConfigInfo> list2 = dataConfigInfoService
					.list(new QueryWrapper<DataConfigInfo>().eq("dsi_uuid", ds.getRemark()));
			int j = 0;
			for (DataConfigInfo dc : list2) {
				System.out.println("~~~~~~~~~开始迁移：" + dc.getDdiValue());
				DataDict dict = dataDictService
						.getOne(new QueryWrapper<DataDict>().eq("type", "102").eq("code", dc.getDdiValue().replace("newspicturesDescription", "newspicturesDeion")));
				DataConfig dataConfig = new DataConfig();
				dataConfig.setCreator(dc.getCreator());
				dataConfig.setDataDict(dict.getUuid());
				dataConfig.setDataSource(ds.getUuid());
				dataConfig.setKey(dict.getCode());
				dataConfig.setName(dict.getName());
				dataConfig.setSort(++j);
				dataConfig.setState("1");
				dataConfig.setCreateTime(dc.getCreateTime());
				dataConfig.setUpdateTime(new Date());
				dataConfig.setValue(dc.getValue());
				if (dataConfig.getKey().endsWith("DataExtRul")) {
					String level = "";
					if (dataConfig.getValue() != null) {
						if (dataConfig.getValue().startsWith("channel_Page.")) {
							level = "channel_Page.";
						} else if (dataConfig.getValue().startsWith("channel_List.")) {
							level = "channel_List.";
						} else if (dataConfig.getValue().startsWith("news.")) {
							level = "news.";
						} else if (dataConfig.getValue().startsWith("newslist_Page.")) {
							level = "newslist_Page.";
						} else if (dataConfig.getValue().startsWith("newslist_List.")) {
							level = "newslist_List.";
						}
						dataConfig.setValue(dataConfig.getValue().replace(level, ""));
					} else {
						dataConfig.setValue("");
					}
					this.dataConfigService.addDataConfig(dataConfig);

					if (!level.equals("")) {
						dict = dataDictService.getOne(
								new QueryWrapper<DataDict>().eq("type", "102").eq("code", (dc.getDdiKey() + "-level").replace("newspicturesDescription", "newspicturesDeion")));
						dataConfig.setDataDict(dict.getUuid());
						dataConfig.setKey(dict.getCode());
						dataConfig.setName(dict.getName());
						dataConfig.setSort(++j);
						dataConfig.setValue(level);
						dataConfig.setUuid(null);
						this.dataConfigService.addDataConfig(dataConfig);
					}
				} else {
					this.dataConfigService.addDataConfig(dataConfig);
				}
				System.out.println("~~~~~~~~~完成迁移（剩余" + (list2.size() - (j)) + "）：" + dc.getDdiValue());
			}
			System.out.println("~~~~~完成迁移（剩余" + (list.size() - (i)) + "）：" + ds.getChsName());
		}
		return PREFIX + "";
	}

	/**
	 * @param uuid
	 * @param model
	 * @return
	 */
	@RequestMapping("/config")
	public String config() {
		List<DataSource> list = dataSourceService.list(new QueryWrapper<DataSource>().ne("state", 0));
		System.out.println(list.size());
		int i = 0;
		for (DataSource dataSourceInfo : list) {
			System.out.println("~~~~~开始迁移：" + dataSourceInfo.getChsName());
			DataConfigInfo dataConfigInfo = new DataConfigInfo();
			dataConfigInfo.setDsiUuid(dataSourceInfo.getRemark().split("\n\\*\\^\\*\n")[1]);
			List<DataConfigInfo> configInfos = dataConfigInfoService.list(dataConfigInfo);
			for (DataConfigInfo configInfo : configInfos) {
				System.out.println("~~~~~~~~~~开始迁移配置：" + configInfo.getDdiValue());
				configInfo.setDsiUuid(dataSourceInfo.getUuid().toString());
				configInfo.setUpdateTime(new Date());
				dataConfigInfoService.add(configInfo);
				System.out.println("~~~~~~~~~~完成迁移配置：" + configInfo.getDdiValue());
			}
			System.out.println("~~~~~完成迁移（剩余" + (list.size() - (++i)) + "）：" + dataSourceInfo.getChsName());
		}
		return PREFIX + "";
	}

}
