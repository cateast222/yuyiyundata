package com.yuyiyun.YYdata.modular.newspaper.controller;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.log.LogManager;
import com.yuyiyun.YYdata.core.log.factory.LogTaskFactory;
import com.yuyiyun.YYdata.core.util.DateTimeUtil;
import com.yuyiyun.YYdata.core.util.JwtTokenUtil;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.datasource.service.DataSourceService;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNews;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewsParam;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewspaperParam;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewsService;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewspaperService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;

/**
 * <p>
 * 电子报刊API服务
 * </p>
 *
 * @author duhao
 * @since
 */
@Controller
@RequestMapping("/yydataApi")
public class NewspaperApiServer extends BaseController {
	@Autowired
	DataSourceService dataSourceService;
	@Autowired
	DataNewspaperService dataNewspaperService;
	@Autowired
	DataNewsService dataNewsService;

	/**
	 * @Description: API添加数据
	 * @author duhao
	 * @date 2019年12月4日
	 * @version V1.0
	 * @param param
	 * @return
	 */
	@PostMapping("/newspaper/addByApi")
	@ResponseBody
	public ResponseData addByApi(@RequestBody() DataNewspaperParam param) {
		DataNewspaper add = this.dataNewspaperService.addOrEdit(param);
		if (ToolsUtil.isNotEmpty(add)) {
			return ResponseData.success(add);
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
	@PostMapping("/newspaper/isExistByApi")
	@ResponseBody
	public ResponseData isExistByApi(String pubTime, String url, String dsiUuid) {
		if ((ToolsUtil.isEmpty(url) && ToolsUtil.isEmpty(pubTime) || ToolsUtil.isEmpty(dsiUuid))) {
			return ResponseData.error("请求参数异常");
		}
		return this.dataNewspaperService.isExist(dsiUuid, url, pubTime);
	}

	/**
	 * @Description: API添加数据
	 * @author duhao
	 * @date 2020年1月5日
	 * @version V1.0
	 * @param param
	 * @return
	 */
	@PostMapping("/datanews/addByApi")
	@ResponseBody
	public ResponseData addByApi(@RequestBody() DataNewsParam param) {
		DataNews add = this.dataNewsService.addOrEdit(param);
		if (ToolsUtil.isNotEmpty(add)) {
			return ResponseData.success(add);
		} else {
			return ResponseData.error("数据添加异常！");
		}
	}

	/**
	 * Description:按归档日期分页查询电子报纸数据
	 * 
	 * @param sysUser
	 * @param archiveDate
	 * @param limit
	 * @param page
	 * @return
	 */
	@PostMapping("/newspaper/getArchiveByApi")
	@ResponseBody
	public ResponseData getArchiveNewspaper(String archiveDate, int limit, int page) {
		if (ToolsUtil.isEmpty(DateTimeUtil.stringToDate(archiveDate, "yyyy-MM-dd"))) {
			return ResponseData.error("请求参数archiveDate异常！");
		} else if (ToolsUtil.isEmpty(limit) || ToolsUtil.isEmpty(page)) {
			return ResponseData.error("请求参数page、limit异常！");
		} else {
			Long userId = Long.parseLong(JwtTokenUtil.getUsernameFromRequest());
			LayuiPageInfo info = dataNewspaperService.getArchiveNewspaper(userId, archiveDate, limit, page);
			// API服务日志
			LogManager.me().executeLog(LogTaskFactory.apiServerLog(userId, "报刊报纸" + archiveDate, getIp(),
					"ArchiveNewspaper", info.getDataMapValues("uuid")));
			return ResponseData.success(info);
		}
	}

	/**
	 * 分页查询报刊新闻数据
	 * 
	 * @param newspaperId
	 * @param limit
	 * @param page
	 * @return
	 */
	@PostMapping("/datanews/getArchiveByApi")
	@ResponseBody
	public ResponseData getArchiveDataNews(Long newspaperId, int limit, int page) {
		if (ToolsUtil.isEmpty(newspaperId)) {
			return ResponseData.error("请求参数异常！");
		} else if (ToolsUtil.isEmpty(limit) || ToolsUtil.isEmpty(page)) {
			return ResponseData.error("请求参数page、limit异常！");
		} else {
			Long userId = Long.parseLong(JwtTokenUtil.getUsernameFromRequest());
			LayuiPageInfo info = dataNewsService.getArchiveDataNews(newspaperId, limit, page);
			// API服务日志
			LogManager.me().executeLog(LogTaskFactory.apiServerLog(userId, "报刊新闻" + newspaperId, getIp(),
					"ArchiveDataNews", info.getDataMapValues("id")));
			return ResponseData.success(info);
		}
	}
}
