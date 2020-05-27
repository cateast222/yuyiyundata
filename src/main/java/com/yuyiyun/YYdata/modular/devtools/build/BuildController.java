package com.yuyiyun.YYdata.modular.devtools.build;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;
import com.yuyiyun.YYdata.modular.dataconfig.service.DataDictService;
import com.yuyiyun.YYdata.modular.datasource.model.param.DataSourceParam;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;

@Controller
@RequestMapping("/dev")
public class BuildController extends BaseController{
	private String PREFIX = "/modular/dev";
	
	@Autowired
	private DataDictService dataDictService;

    @GetMapping("/build")
    public String index(Model model){
        return PREFIX+"/build/index.html";
    }
    
    @RequestMapping("/dataDictConfFroms")
	public String dataDictConfFroms(Long dataDictUuid, Model model) {
    	List<String> froms = dataDictService.getFroms(dataDictUuid);
    	model.addAttribute("froms", froms);
		return PREFIX+"/build/data_dict_from.html";
    }
}