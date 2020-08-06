package com.yuyiyun.YYdata.modular.news.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.modular.news.entity.DataWebSite;
import com.yuyiyun.YYdata.modular.news.service.UpdateSiteService;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

@Controller
@RequestMapping("/site")
public class UpdateSiteController {
	private String PREFIX = "/modular/system/log";
	@Autowired
	private UpdateSiteService updateSiteService;
	
	@GetMapping(value = "/dataEdit")
	public String addEdit(Long id,HttpSession session) {
		    session.setAttribute("id", id);
			return PREFIX + "/edit.html";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateById")
	public ResponseData updateSiteById(HttpSession session) {
		Long id=(Long)session.getAttribute("id");
		DataWebSite dataWebSite = updateSiteService.updateSiteById(id);
		return ResponseData.success(dataWebSite);
	}
	
	
	@ResponseBody
	@RequestMapping("/deleteSite")
	public ResponseData deleteSite(Long id) {
		System.err.println(id);
		int i = updateSiteService.deleteSite(id);
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("删除失败");
		}
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("/updatesite")
	public ResponseData updateSite(DataWebSite dataWebSite) {
		int num = updateSiteService.updateSite(dataWebSite);
		if (num != 0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("修改失败");
		}
	}
	
	
}
