package com.yuyiyun.YYdata.modular.newspaper.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewspaperParam;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewspaperService;
import com.yuyiyun.YYdata.modular.system.entity.DataSourceInfo;
import com.yuyiyun.YYdata.modular.system.model.params.DataSourceInfoParam;
import com.yuyiyun.YYdata.modular.wgelenewsdata.entity.WgEleNewsData;
import com.yuyiyun.YYdata.modular.wgelenewsdata.model.param.WgEleNewsDataParam;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author duhao
 * @since 2020-01-02
 */
@Api(value = "电子报纸controller", tags = { "电子报纸操作接口" })
@Controller
@RequestMapping({ "/newspaper", "/yydataApi/newspaper" })
public class DataNewspaperController extends BaseController {
	
	private String PREFIX = "/modular/newspaper";
	@Autowired
	DataNewspaperService dataNewspaperService;

	/**
	 * :数据测试页面
	 * @author duhao
	 * @return
	 */
	@RequestMapping("")
	public String index() {
		return PREFIX + "/newspaper/index.html";
	}
	
	/**
	 * :新增接口
	 * @author duhao
	 * @param dataNewspaper
	 * @return
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public ResponseData addItem(DataNewspaperParam param) {
		param.setCreator(ShiroKit.getUser().getAccount());
		this.dataNewspaperService.add(param);
		return ResponseData.success();
	}
	
	/**
	 * :删除接口
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
	 * :查看详情接口
	 * @author duhao
	 * @param dataSourceInfoParam
	 * @return
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public ResponseData detail(DataNewspaperParam param) {
		DataNewspaper detail = this.dataNewspaperService.getById(param.getUuid());
		return ResponseData.success(detail);
	}
	
	/**
	 * :查询列表
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
		DataNewspaper add = this.dataNewspaperService.add(param);
		if (add != null) {
			return ResponseData.success(add);
		} else {
			return ResponseData.error("数据添加异常！");
		}
	}
	
	/**
	 * Description: API数据排重
	 * @author duhao
	 * @param pubTime
	 * @param dsiUuid
	 * @return
	 */
	@ApiOperation(value = "数据排重", notes = "排重电子报纸")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "pubTime", value = "发布日期", required = true, paramType = "query", dataType = "String"),
		@ApiImplicitParam(name = "dsiUuid", value = "数据源", required = true, paramType = "query", dataType = "String")
	})
	@PostMapping("/isExistByApi")
	@ResponseBody
	public ResponseData isExist(String pubTime,String dsiUuid) {
		return dataNewspaperService.isExist(dsiUuid,pubTime);
	}
}
