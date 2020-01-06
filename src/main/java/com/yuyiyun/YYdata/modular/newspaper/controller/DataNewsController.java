package com.yuyiyun.YYdata.modular.newspaper.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNews;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewsParam;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewspaperParam;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewsService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author duhao
 * @since 2020-01-03
 */
@Api(value = "报纸新闻controller", tags = { "报纸新闻操作接口" })
@Controller
@RequestMapping({ "/datanews", "/yydataApi/datanews" })
public class DataNewsController extends BaseController {

	private String PREFIX = "/modular/newspaper";
	@Autowired
	DataNewsService dataNewsService;

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
	 * :新增接口
	 * 
	 * @author duhao
	 * @param DataNews
	 * @return
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public ResponseData addItem(DataNewsParam param) {
		param.setCreator(ShiroKit.getUser().getAccount());
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
	public LayuiPageInfo list(DataNewsParam param) {
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
		DataNews add = this.dataNewsService.add(param);
		if (add != null) {
			return ResponseData.success(add);
		} else {
			return ResponseData.error("数据添加异常！");
		}
	}
}
