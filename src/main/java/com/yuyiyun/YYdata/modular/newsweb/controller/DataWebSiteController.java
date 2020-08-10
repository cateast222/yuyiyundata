package com.yuyiyun.YYdata.modular.newsweb.controller;


import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.annotion.Permission;
import com.yuyiyun.YYdata.core.common.constant.Const;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebSite;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebSiteService;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannelEntity;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebChannelService;
import com.yuyiyun.YYdata.modular.system.warpper.LogWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

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
    public String indexs() {
        return PREFIX + "/site.html";
    }

    @RequestMapping("condition")
    public ResponseData condition(@RequestParam(required = false) String medianame) {
        boolean flag = dataWebSiteService.condition(medianame);
        if (flag) {
            throw new RuntimeException("媒体名称不存在");
        }
        return ResponseData.success();
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

    @RequestMapping("/add")
    public String add(DataWebSite dataWebSite) {
        dataWebSiteService.add(dataWebSite);
        return PREFIX + "/site_add.html";
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(DataWebSite dataWebSite) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        List<Map<String, Object>> result = dataWebSiteService.getSites(page, dataWebSite);
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
	
	@ResponseBody
	@RequestMapping("/deleteSite")
	public ResponseData deleteSite(String id) {
		List<DataWebChannelEntity> list = datawebservice.selectBySiteId(id);
		if(list.isEmpty()) {
			dataWebSiteService.deleteSite(id);
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
	
}
