package com.yuyiyun.YYdata.modular.newsweb.controller;


import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.annotion.Permission;
import com.yuyiyun.YYdata.core.common.constant.Const;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebSite;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebSiteParam;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebSiteService;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebsiteVo;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebChannelService;
import com.yuyiyun.YYdata.modular.system.warpper.LogWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

/**
 * @author YiZhiLong
 * @author WangShiPing
 *
 */
@Controller
@Api(value = "站点controller", tags = {"站点操作接口"})
@RequestMapping("/site")
public class DataWebSiteController {

    private String PREFIX = "/modular/newsweb";

    @Autowired
    private DataWebSiteService dataWebSiteService;
	@Autowired
	private DataWebChannelService datachannelservice;
	
	
	 /**
	 * 网站页面入口
     * @param id
     * @param session
     * @return
     */
    @RequestMapping("/website")
    public String indexs(Long id,HttpSession session) {
    	session.setAttribute("uuid",id);
        return PREFIX + "/site.html";
    }

    
    /**
     * 跳转到编辑页面
     * @param id
     * @param session
     * @return
     */
    @GetMapping(value = "/dataEdit")
	public String addEdit(Long id,HttpSession session) {
		    session.setAttribute("id", id);
			return PREFIX + "/edit.html";
	}
	

    /**
     * 跳转到新增页面
     * @return
     */
    @RequestMapping("/add_prefix")
    public String addPrefix() {
        return PREFIX + "/site_add.html";
    }
    
    
    /**
     * 新增
     * @param data
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/add")
    public ResponseData add(DataWebSite data,HttpSession session) {
    	 String name = ShiroKit.getUser().getName();
    	 Long webMedia = (Long)session.getAttribute("uuid");
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
    
    
    /**
     * 分页查询
     * @param dataWebSite
     * @param session
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/list")
    @ResponseBody
    public LayuiPageInfo list(DataWebSiteParam param,HttpSession session) {
    	Long  id = (Long)session.getAttribute("uuid");
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        List<Map<String, Object>> result = dataWebSiteService.getSites(page, param,id);
        page.setRecords(result);
        return LayuiPageFactory.createPageInfo(page);
    }

	
	
	
	/**
	 * 根据id查询网站
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/updateById")
	public ResponseData selectSiteById(HttpSession session) {
		Long id=(Long)session.getAttribute("id");
		DataWebSite dataWebSite = dataWebSiteService.selectSiteById(id);
		return ResponseData.success(dataWebSite);
	}
	
	
	/**
	 * 删除网站时强制删除所属频道
	 * @param id
	 * @return
	 */
	@Transactional
	@ResponseBody
	@RequestMapping("/deleteSite")
	public ResponseData deleteSite(Long id) {
		datachannelservice.deleteBySiteId(id);
		int i = dataWebSiteService.deleteSite(id);
		if(i>0) {
			return  ResponseData.success();
		}else {
			return ResponseData.error("删除失败");
		}
	}
	
	
	
	/**
	 * 修改网站
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
	
	
	/**
	 * 查询出当前网站的媒体名称
	 * @param sitevo
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/SelectMediaName")
	public ResponseData selectMediaName(DataWebsiteVo sitevo,HttpSession session){
		//获取跳转过来的网站UUID，根据此id查询出对应的媒体名称
		Long id = (Long)session.getAttribute("uuid");
		sitevo.setUuid(id);
		DataWebsiteVo selectMediaName = dataWebSiteService.selectMediaName(sitevo);
		return ResponseData.success(0, "11", selectMediaName);
	}
}
