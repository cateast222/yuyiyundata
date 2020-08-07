package com.yuyiyun.YYdata.modular.news.controller;



import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.modular.news.entity.DataWebSite;
import com.yuyiyun.YYdata.modular.news.service.UpdateSiteService;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannelEntity;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebChannelService;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

@Controller
@RequestMapping("/site")
public class UpdateSiteController {
	private String PREFIX = "/modular/system/log";
	@Autowired
	private UpdateSiteService updateSiteService;
	@Autowired
	private DataWebChannelService datawebservice;
	
	@GetMapping(value = "/dataEdit")
	public String addEdit(String id,HttpSession session) {
		    session.setAttribute("id", id);
			return PREFIX + "/edit.html";
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/updateById")
	public ResponseData updateSiteById(HttpSession session) {
		String id=(String)session.getAttribute("id");
		DataWebSite dataWebSite = updateSiteService.updateSiteById(id);
		return ResponseData.success(dataWebSite);
	}
	
	/**
	 * 删除网站时提示先删除频道才能执行网站删除
	 * @param id
	 * @return
	 */
	/**
	 * 
	 */
	
	@ResponseBody
	@RequestMapping("/deleteSite")
	public ResponseData deleteSite(String id) {
		List<DataWebChannelEntity> list = datawebservice.selectBySiteId(id);
		if(list.isEmpty()) {
			updateSiteService.deleteSite(id);
			return ResponseData.success();
		}else {
			return ResponseData.error("请先删除频道");
		}
	}
	
	/**
	 * 删除网站时强制删除所属频道
	 * @param id
	 * @return
	 */
//	@ResponseBody
//	@RequestMapping("/deleteSite")
//	public ResponseData deleteSite(String id) {
//		int deleteBySiteId = datawebservice.deleteBySiteId(id);
//		if(deleteBySiteId>0) {
//			updateSiteService.deleteSite(id);
//			return  ResponseData.success();
//		}else {
//			return ResponseData.error("删除失败");
//		}
//	}
	
	
	
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
