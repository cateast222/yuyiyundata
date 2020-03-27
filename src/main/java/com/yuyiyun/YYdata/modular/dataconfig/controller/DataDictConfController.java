package com.yuyiyun.YYdata.modular.dataconfig.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;
import com.yuyiyun.YYdata.modular.dataconfig.service.DataDictService;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

/**
 * <p>
 * 数据字典配置表前端控制器
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
@Controller
@RequestMapping("/datadictconf")
public class DataDictConfController {

	private static final String PREFIX = "/modular/dataconfig/datadictconf";

	@Autowired
	private DataDictService dataDictService;

	/**
	 * 跳转到主页面
	 */
	@GetMapping
	public String index(Model model) {
		model.addAttribute("type", "102");
		return PREFIX + "/index.html";
	}

	/**
	 * 跳转到新增页面
	 */
	@GetMapping("/add")
	public String add(Model model) {
		model.addAttribute("type", "102");
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
	 * 
	 * @param dataDict
	 * @param limit
	 * @param page
	 * @return
	 */
	@RequestMapping("/list")
	@ResponseBody
	public LayuiPageInfo pageList(DataDict dataDict, int limit, int page) {
		return dataDictService.selectPageList(dataDict, limit, page);
	}

	/**
	 * 新增数据
	 * 
	 * @param dataDict
	 * @return
	 */
	@RequestMapping("/addItem")
	@ResponseBody
	public ResponseData adddataDict(DataDict dataDict) {
		dataDictService.addDataDict(dataDict);
		return ResponseData.success();
	}

	/**
	 * 修改数据
	 * 
	 * @param dataDict
	 * @return
	 */
	@RequestMapping("/editItem")
	@ResponseBody
	public ResponseData editdataDict(DataDict dataDict) {
		dataDictService.editDataDict(dataDict);
		return ResponseData.success();
	}

	/**
	 * 查看详情
	 * 
	 * @param uuid
	 * @return
	 */
	@RequestMapping("/detail")
	@ResponseBody
	public ResponseData detail(String uuid) {
		DataDict dataDict = dataDictService.getDetailsById(uuid);
		return ResponseData.success(dataDict);
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
		dataDictService.deleteById(uuid);
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
		dataDictService.deleteBatch(ids);
		return ResponseData.success();
	}

}
