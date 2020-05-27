package com.yuyiyun.YYdata.modular.dataconfig.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.WebUtils;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataConfig;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;
import com.yuyiyun.YYdata.modular.dataconfig.entity.SourceDict;
import com.yuyiyun.YYdata.modular.dataconfig.service.DataDictService;
import com.yuyiyun.YYdata.modular.dataconfig.service.SourceDictService;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

@Controller
@RequestMapping("/sourcedict")
public class SourceDictController {
	private static final String PREFIX = "/modular/dataconfig/sourcedict";
	@Autowired
	private SourceDictService sourceDictService;
	@Autowired
	private DataDictService dataDictService;

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
	public String add(DataDict dataDict, Model model) {
		model.addAttribute("dict", dataDictService.selectListByEQ(dataDict));
		return PREFIX + "/add.html";
	}

	/**
	 * 跳转到修改页面
	 */
	@GetMapping("/edit")
	public String edit(Long dataDict, Model model) {
		List<String> froms = dataDictService.getFroms(dataDict);
		model.addAttribute("froms", froms);
		return PREFIX + "/edit.html";
	}

	/**
	 * 新增数据
	 * 
	 * @param sourceDict
	 * @return
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public ResponseData addItem(SourceDict sourceDict) {
		sourceDictService.addSourceDict(sourceDict);
		return ResponseData.success();
	}

	/**
	 * 修改数据
	 * 
	 * @param config
	 * @return
	 */
	@RequestMapping("/editItem/{dataSource}/{dataDict}")
	@ResponseBody
	public ResponseData editItem(ServletRequest request, @PathVariable Long dataSource, @PathVariable Long dataDict) {
		Map<String, Object> map = WebUtils.getParametersStartingWith(request, null);
		sourceDictService.editSourceDict(map, dataSource, dataDict);
		return ResponseData.success();
	}
	
	/**
	    * 查看详情
	    * @param uuid
	    * @return
	    */
	    @RequestMapping("/detail")
	    @ResponseBody
	    public ResponseData detail(Long dataSource,Long dataDict) {
	        Map<String, Object> dataConfig = sourceDictService.getDetails(dataSource,dataDict);
	        return ResponseData.success(dataConfig);
	    }

	/**
	 * 根据ID删除记录
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public ResponseData delete(String uuid) {
		sourceDictService.deleteById(uuid);
		return ResponseData.success();
	}

	/**
	 * 批量删除数据
	 * 
	 * @param ids ID集合
	 * @return
	 */
	@RequestMapping("/deleteBatch")
	@ResponseBody
	public ResponseData deleteBatch(@RequestParam(value = "ids[]", required = true) List<Long> ids) {
		sourceDictService.deleteBatch(ids);
		return ResponseData.success();
	}

	/**
	 * 查询列表
	 * 
	 * @param sourceDict
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public LayuiPageInfo pageList(SourceDict sourceDict, int limit, int page) {
		return sourceDictService.selectPageList(sourceDict, limit, page);
	}

	/**
	 * 查询列表
	 * 
	 * @param sourceDict
	 * @param dataDict
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping("/sourceDictList")
	@ResponseBody
	public LayuiPageInfo pageSourceDictList(SourceDict sourceDict, DataDict dataDict, int limit, int page) {
		return sourceDictService.selPageSourceDict(sourceDict, dataDict, limit, page);
	}

}
