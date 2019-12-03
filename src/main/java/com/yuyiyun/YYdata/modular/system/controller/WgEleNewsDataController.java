package com.yuyiyun.YYdata.modular.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.system.entity.WgEleNewsData;
import com.yuyiyun.YYdata.modular.system.model.params.WgEleNewsDataParam;
import com.yuyiyun.YYdata.modular.system.service.WgEleNewsDataService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 闻歌电子报纸数据表控制器
 * 
 * @author duhao
 *
 */
@Api(value = "电子报纸数据controller", tags = { "电子报纸数据操作接口" })
@Controller
@RequestMapping({"/wendata","/yydataApi/wendata"})
public class WgEleNewsDataController extends BaseController {
	private String PREFIX = "/modular/system/wendata";
	@Autowired
	private WgEleNewsDataService wgEleNewsDataService;
	
	/**
	 * 查询列表
	 * @author duhao
	 * @Date 2019-11-18
	 */
	@RequestMapping("/list")
	@ResponseBody
	public LayuiPageInfo list(WgEleNewsDataParam wgEleNewsDataParam) {
		System.out.println(wgEleNewsDataParam);
		return this.wgEleNewsDataService.findPageBySpec(wgEleNewsDataParam);
	}
	
	/**
	 * API添加数据
	 * @param wgEleNewsDataParam
	 * @return
	 */
	@ApiOperation(value = "数据新增", notes = "新增电子报纸数据")
	@ApiImplicitParam(name = "wgEleNewsDataParam", value = "参数", required = false, paramType = "body", dataType = "WgEleNewsDataParam")
	@PostMapping("/addByApi")
	@ResponseBody
	public ResponseData addByApi(@RequestBody() WgEleNewsDataParam wgEleNewsDataParam) {
		WgEleNewsData add = this.wgEleNewsDataService.add(wgEleNewsDataParam);
		if (add!=null) {
			return ResponseData.success(add);
		}else {
			return ResponseData.error("数据添加异常！");
		}
	}
}
