package com.yuyiyun.YYdata.modular.newsweb.controller;


import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.annotion.Permission;
import com.yuyiyun.YYdata.core.common.constant.Const;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebSite;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebSiteService;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebsiteVo;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebChannelService;
import com.yuyiyun.YYdata.modular.system.warpper.LogWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

@Controller
@Api(value = "站点controller", tags = {"站点操作接口"})
@RequestMapping("/site")
public class DataWebSiteController {

    private String PREFIX = "/modular/newsweb";

    @Autowired
    private DataWebSiteService dataWebSiteService;
	@Autowired
	private DataWebChannelService datawebservice;

    @RequestMapping("/website")
    public String indexs(String id,HttpSession session) {
    	session.setAttribute("uuid",id);
        return PREFIX + "/site.html";
    }

    
    @GetMapping(value = "/dataEdit")
	public String addEdit(String id,HttpSession session) {
		    session.setAttribute("id", id);
			return PREFIX + "/edit.html";
	}
	

    @RequestMapping("/add_prefix")
    public String addPrefix() {
        return PREFIX + "/site_add.html";
    }
    
    
    @ResponseBody
    @RequestMapping("/add")
    public ResponseData add(DataWebSite data,HttpSession session) {
    	 String uuid = UUID.randomUUID().toString().replaceAll("-", "");
    	 String name = ShiroKit.getUser().getName();
    	 String webMedia = (String)session.getAttribute("uuid");
    	 data.setUuid(uuid);
    	 data.setDataWebMedia(webMedia);
    	 data.setCreateBy(name);
    	 data.setCreateTime(new Date());
         int i = dataWebSiteService.add(data);
         if(i>0) {
        	 return ResponseData.success();
         }else {
        	 return ResponseData.error("网址已经存在");
         }
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(DataWebSite dataWebSite,HttpSession session) {
    	String  id = (String)session.getAttribute("uuid");
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        List<Map<String, Object>> result = dataWebSiteService.getSites(page, dataWebSite,id);
        page.setRecords(new LogWrapper(result).wrap());
        return LayuiPageFactory.createPageInfo(page);
    }

	
	
	
	@ResponseBody
	@RequestMapping(value = "/updateById")
	public ResponseData updateSiteById(HttpSession session) {
		String id=(String)session.getAttribute("id");
		DataWebSite dataWebSite = dataWebSiteService.updateSiteById(id);
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
	
//	@ResponseBody
//	@RequestMapping("/deleteSite")
//	public ResponseData deleteSite(String id) {
//		List<DataWebChannelEntity> list = datawebservice.selectBySiteId(id);
//		if(list.isEmpty()) {
//			dataWebSiteService.deleteSite(id);
//			return ResponseData.success();
//		}else {
//			return ResponseData.error("请先删除频道");
//		}
//	}
	
	/**
	 * 删除网站时强制删除所属频道
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteSite")
	public ResponseData deleteSite(String id) {
		int deleteBySiteId = datawebservice.deleteBySiteId(id);
		if(deleteBySiteId>0) {
			dataWebSiteService.deleteSite(id);
			return  ResponseData.success();
		}else {
			return ResponseData.error("删除失败");
		}
	}
	
	
	
	/**
	 * 修改
	 * @param dataWebSite
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/updatesite")
	public ResponseData updateSite(DataWebSite dataWebSite) {
		int num = dataWebSiteService.updateSite(dataWebSite);
		if (num != 0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping("/SelectMediaName")
	public ResponseData selectMediaName(DataWebsiteVo sitevo,HttpSession session){
		//获取跳转过来的网站UUID，根据此id查询出对应的媒体UUID、媒体名称、网站名称
		String id = (String)session.getAttribute("uuid");
		sitevo.setUuid(id);
		DataWebsiteVo selectMediaName = dataWebSiteService.selectMediaName(sitevo);
		return ResponseData.success(0, "11", selectMediaName);
	}
}
