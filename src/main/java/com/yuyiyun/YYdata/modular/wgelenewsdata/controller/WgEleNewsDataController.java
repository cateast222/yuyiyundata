package com.yuyiyun.YYdata.modular.wgelenewsdata.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.system.model.params.DataSourceInfoParam;
import com.yuyiyun.YYdata.modular.system.service.DataSourceInfoService;
import com.yuyiyun.YYdata.modular.wgelenewsdata.entity.WgEleNewsData;
import com.yuyiyun.YYdata.modular.wgelenewsdata.model.param.WgEleNewsDataParam;
import com.yuyiyun.YYdata.modular.wgelenewsdata.service.WgEleNewsDataService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @ClassName: WgEleNewsDataController
 * @Description: 闻歌电子报纸数据表控制器
 * @author duhao
 * @date 2019年12月4日
 */
@Api(value = "电子报纸数据controller", tags = { "电子报纸数据操作接口" })
@Controller
@RequestMapping({ "/wendata", "/yydataApi/wendata" })
public class WgEleNewsDataController extends BaseController {
	private String PREFIX = "/modular/wgelenewsdata";
	@Autowired
	private WgEleNewsDataService wgEleNewsDataService;
	@Autowired
	private DataSourceInfoService dataSourceInfoService;
	
	/**
	 * :数据测试页面
	 * 
	 * @author duhao
	 * @return
	 */
	@RequestMapping("/datatest")
	public String datatest() {
		return PREFIX + "/datatest/datatest.html";
	}
	
	/**
	 * :数据测试页面
	 * 
	 * @author duhao
	 * @return
	 */
	@RequestMapping("/dataresult")
	public String dataresult() {
		return PREFIX + "/dataResults/index.html";
	}
	/**
	 * 加入测试接口
	 *
	 * @author duhao
	 * @Date 2019-11-13
	 */
	@RequestMapping("/datatestRun")
	@ResponseBody
	public ResponseData datatestRun(DataSourceInfoParam dataSourceInfoParam) {
		// 清除之前的测试数据
		HashMap<String, Object> wgEleNewsDataColumnMap = new HashMap<String, Object>();
		wgEleNewsDataColumnMap.put("dsi_uuid", dataSourceInfoParam.getUuid());
		wgEleNewsDataColumnMap.put("state", -2);
		this.wgEleNewsDataService.removeByMap(wgEleNewsDataColumnMap);
		this.dataSourceInfoService.update(dataSourceInfoParam);
		return ResponseData.success();
	}

	/**
	 * 测试通过接口
	 *
	 * @author duhao
	 * @Date 2019-11-13
	 */
	@RequestMapping("/datatestPass")
	@ResponseBody
	public ResponseData datatestPass(DataSourceInfoParam dataSourceInfoParam) {
		// 清除之前的测试数据
		HashMap<String, Object> wgEleNewsDataColumnMap = new HashMap<String, Object>();
		wgEleNewsDataColumnMap.put("dsi_uuid", dataSourceInfoParam.getUuid());
		wgEleNewsDataColumnMap.put("state", -2);
		this.wgEleNewsDataService.removeByMap(wgEleNewsDataColumnMap);
		this.dataSourceInfoService.update(dataSourceInfoParam);
		return ResponseData.success();
	}

	/**
	 * 测试失败接口
	 *
	 * @author duhao
	 * @Date 2019-11-13
	 */
	@RequestMapping("/datatestFail")
	@ResponseBody
	public ResponseData datatestFail(DataSourceInfoParam dataSourceInfoParam) {
		// 清除之前的测试数据
		HashMap<String, Object> wgEleNewsDataColumnMap = new HashMap<String, Object>();
		wgEleNewsDataColumnMap.put("dsi_uuid", dataSourceInfoParam.getUuid());
		wgEleNewsDataColumnMap.put("state", -2);
		this.wgEleNewsDataService.removeByMap(wgEleNewsDataColumnMap);
		this.dataSourceInfoService.update(dataSourceInfoParam);
		return ResponseData.success();
	}

	/**
	 * @Description: 查询列表
	 * @author duhao
	 * @date 2019年12月4日
	 * @version V1.0
	 * @param wgEleNewsDataParam
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public LayuiPageInfo list(WgEleNewsDataParam wgEleNewsDataParam) {
		System.out.println(wgEleNewsDataParam);
		return this.wgEleNewsDataService.findPageBySpec(wgEleNewsDataParam);
	}

	/**
	 * @Description: API添加数据
	 * @author duhao
	 * @date 2019年12月4日
	 * @version V1.0
	 * @param wgEleNewsDataParam
	 * @return
	 */
	@ApiOperation(value = "数据新增", notes = "新增电子报纸数据")
	@ApiImplicitParam(name = "wgEleNewsDataParam", value = "参数", required = false, paramType = "body", dataType = "WgEleNewsDataParam")
	@PostMapping("/addByApi")
	@ResponseBody
	public ResponseData addByApi(@RequestBody() WgEleNewsDataParam wgEleNewsDataParam) {
		WgEleNewsData add = this.wgEleNewsDataService.add(wgEleNewsDataParam);
		if (add != null) {
			return ResponseData.success(add);
		} else {
			return ResponseData.error("数据添加异常！");
		}
	}
	
	/**
	 * :根据日期获取分类归档的数据
	 * @param pubTime
	 * @param condition
	 * @return
	 */
	@RequestMapping("/getDateArchive")
	@ResponseBody
	public LayuiPageInfo getDateArchive(String pubTime,String condition) {
		System.out.println(pubTime+":"+condition);
		 //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
		List<Map<String,Object>> dateArchive = wgEleNewsDataService.getDateArchive(page,pubTime,condition);
		page.setRecords(dateArchive);
		return LayuiPageFactory.createPageInfo(page);
	}
	
	/**
	 * :根据日期获取分类归档的数据
	 * @param pubTime
	 * @param condition
	 * @return
	 */
	@RequestMapping("/getDateNewslist")
	@ResponseBody
	public LayuiPageInfo getDateNewslist(String pubTime,String condition,String dsiUuid) {
		System.out.println(pubTime+":"+condition+":"+dsiUuid);
		return wgEleNewsDataService.getDateNewslist(dsiUuid,pubTime,condition);
	}
}
