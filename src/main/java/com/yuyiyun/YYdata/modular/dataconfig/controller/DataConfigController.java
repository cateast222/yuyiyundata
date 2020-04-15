package com.yuyiyun.YYdata.modular.dataconfig.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataConfig;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;
import com.yuyiyun.YYdata.modular.dataconfig.service.DataConfigService;
import com.yuyiyun.YYdata.modular.dataconfig.service.DataDictService;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


/**
 * <p>
 * 数据配置表前端控制器
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
@Api(value = "数据源配置controller", tags = { "数据源配置操作接口" })
@Controller
@RequestMapping("/dataconfig")
public class DataConfigController {

    private static final String PREFIX = "/modular/dataconfig/dataconfig";

    @Autowired
    private DataConfigService dataConfigService;
    @Autowired
    private DataDictService dataDictServices;

    /**
    * 跳转到主页面
    */
    @GetMapping
    public String index() {
        return PREFIX + "/index.html";
    }

    /**
    * 跳转到新增页面
    */
    @GetMapping("/add")
    public String add(DataDict dataDict,Model model) {
    	model.addAttribute("dict", dataDictServices.selectListByEQ(dataDict));
        return PREFIX + "/add.html";
    }

    /**
    * 跳转到修改页面
    */
    @GetMapping("/edit")
    public String edit() {
        return PREFIX + "/edit.html";
    }

    /**
    * 查询列表
    * @param dataConfig
    * @param limit
    * @param page
    * @return
    */
    @RequestMapping("/list")
    @ResponseBody
    public LayuiPageInfo pageList(DataConfig dataConfig, int limit, int page) {
    	return dataConfigService.selectPageList(dataConfig,limit,page);
    }

    /**
    * 新增数据
    * @param dataConfig
    * @return
    */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addConfig(DataConfig dataConfig) {
        dataConfigService.addDataConfig(dataConfig);
        return ResponseData.success();
    }

    /**
    * 修改数据
    * @param config
    * @return
    */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editConfig(DataConfig dataConfig) {
        dataConfigService.editDataConfig(dataConfig);
        return ResponseData.success();
    }

    /**
    * 查看详情
    * @param uuid
    * @return
    */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(String uuid) {
        DataConfig dataConfig = dataConfigService.getDetailsById(uuid);
        return ResponseData.success(dataConfig);
    }

    /**
    * 根据ID删除记录
    * @param uuid
    * @return
    */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(String uuid) {
        dataConfigService.deleteById(uuid);
        return ResponseData.success();
    }

    /**
    * 批量删除数据
    * @param ids ID集合
    * @return
    */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    public ResponseData deleteBatch(@RequestParam(value = "ids[]",required = true) List<Long> ids) {
        dataConfigService.deleteBatch(ids);
        return ResponseData.success();
    }
    
    @ApiOperation(value = "获取数据配置", notes = "动态查询数数据配置")
	@PostMapping("/getEQsByApi")
	@ResponseBody
    public ResponseData getValuesByApi(@RequestBody() DataConfig dataConfig, String... columns) {
    	if (ToolsUtil.isPojoEmpty(dataConfig)) {
    		return ResponseData.error("参数异常，请检查！");
		}else {
			List<Map<String,Object>> list = dataConfigService.getEQsByApi(dataConfig, columns);
			return ResponseData.success(list);
		}
    }
    
    @ApiOperation(value = "设置数据配置", notes = "设置数据配置")
    @PostMapping("/putByApi")
    @ResponseBody
    public ResponseData putValueByApi(Long id, String key, String value) {
    	if (ToolsUtil.isEmpty(id)||ToolsUtil.isEmpty(key)||ToolsUtil.isEmpty(value)) {
    		return ResponseData.error("参数异常，请检查！");
    	}else {
    		DataConfig dataConfig = dataConfigService.putDataAuths(id, key, value);
    		return ResponseData.success(dataConfig);
    	}
    }

}
