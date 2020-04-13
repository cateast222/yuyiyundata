package com.yuyiyun.YYdata.modular.wgelenewsdata.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.modular.system.entity.DataSourceInfo;
import com.yuyiyun.YYdata.modular.system.service.DataSourceInfoService;
import com.yuyiyun.YYdata.modular.wgelenewsdata.entity.WgEleNewsData;
import com.yuyiyun.YYdata.modular.wgelenewsdata.model.param.WgEleNewsDataParam;
import com.yuyiyun.YYdata.modular.wgelenewsdata.service.WgEleNewsDataService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;

/**
 * @ClassName: WgEleNewsDataController
 * @Description: 闻歌电子报纸数据表控制器
 * @author duhao
 * @date 2019年12月4日
 */
@Controller
@RequestMapping("/yydataApi")
public class WgEleNewsDataApiServer extends BaseController {
	@Autowired
	private WgEleNewsDataService wgEleNewsDataService;
	@Autowired
	private DataSourceInfoService dataSourceInfoService;

	/**
	 * 查看详情接口
	 * 
	 * @author duhao
	 * @Date 2019-03-13
	 */
	@GetMapping("/datasi/detail")
	@ResponseBody
	public ResponseData detailByApi(String uuid) {
		DataSourceInfo detail = this.dataSourceInfoService.getById(uuid);
		return ResponseData.success(detail);
	}

	/**
	 * @Description: API添加数据
	 * @author duhao
	 * @date 2019年12月4日
	 * @version V1.0
	 * @param wgEleNewsDataParam
	 * @return
	 */
	@PostMapping("/wendata/addByApi")
	@ResponseBody
	public ResponseData addByApi(@RequestBody() WgEleNewsDataParam wgEleNewsDataParam) {
		WgEleNewsData add = this.wgEleNewsDataService.add(wgEleNewsDataParam);
		if (add != null) {
			return ResponseData.success(add);
		} else {
			return ResponseData.error("数据添加异常！");
		}
	}

}
