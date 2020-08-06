package com.yuyiyun.YYdata.modular.newsweb.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewsService;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannelEntity;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebChannelParam;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebChannelService;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebChannelVo;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

/**
 * @author meiren
 *  频道列表控制器
 */
@Controller
@RequestMapping("datawebchannel")
public class DataWebChannelController {
	private String PREFIX = "/modular/newsweb";
	@Autowired
	DataNewsService dataNewsService;
	@Autowired
	private DataWebChannelService channelService ;
	
	/**
	 * 跳转到频道列表
	 * 
	 * 
	 * */
	@GetMapping("/dataweb")
	public String datawebchannel(String id,HttpSession session) {
		session.setAttribute("dataId", id);
		return PREFIX + "/datawebchannel.html";
	}
	
	/**
	 * 跳转到新增页面
	 * 
	 * 
	 * */
	@GetMapping("/dataAdd")
	public String add() {
		return PREFIX + "/data_add.html";
	}
	
	/**
	 * 跳转到修改页面
	 * 
	 * 
	 * */
	@GetMapping(value = "/dataAddEdit")
	public String addEdit(String id,HttpSession session) {
		    session.setAttribute("id", id);
			return PREFIX + "/data_add_edit.html";
	}
	

	/**
	 * 分页查询
	 * 
	 * 
	 * */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/dataSelectPage")
	@ResponseBody
	public LayuiPageInfo listFromNewspaper(DataWebChannelParam param,HttpSession session) {
		String  attribute = (String)session.getAttribute("dataId");
		Page page = LayuiPageFactory.defaultPage();
		List<Map<String, Object>> list = this.channelService.selectPage(page, param,attribute);
		page.setRecords(list);
		return LayuiPageFactory.createPageInfo(page);
	}
	
	
	/**
	 * 点击编辑根据id查询数据回显
	 * 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/dataSelectById")
	public ResponseData selectById(HttpSession session) {
        String id = (String)session.getAttribute("id");
		DataWebChannelEntity list = channelService.findById(id);
		return ResponseData.success(0, "11", list);
	}
	
	/**
	 * 删除
	 * 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/dataDelete")
	public ResponseData delete(String id) {
		int i = channelService.delete(id);
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("删除失败");
		}
	}
	
	/**
	 * 批量删除
	 * 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/dataDeleteBacth")
	public ResponseData deleteBacth(String[] ids) {
		int i = channelService.deleteBacth(ids);
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("删除失败");
		}
	}
	
	/**
	 * 批量更新
	 * 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/dataUpdateBacth")
	public ResponseData dataUpdateBacth(String[] ids) {
		int i = channelService.updateBacth(ids);
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("更新失败");
		}
	}
	
	
	/**
	 * 修改
	 * 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/dataUpdate")
	public ResponseData update(DataWebChannelEntity data) {
		data.setUpdateTime(new Date());
		int i = channelService.update(data);
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("修改失败");
		}
	}
	
	
	/**
	 * 新增
	 * 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/dataInsert" , method = RequestMethod.POST)
	public ResponseData add(DataWebChannelEntity data) {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		data.setUuid(uuid);
		data.setCreateTime(new Date());
		int i = channelService.add(data);
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("新增失败");
		}
	}
	
	
	/**
	 * 查询当前登录用户名
	 * 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/dataSelectUser")
	public ResponseData selectUser(DataWebChannelVo datavo){
		Long userId = ShiroKit.getUser().getId();
		datavo.setUserid(Long.valueOf(userId));
		DataWebChannelVo user = channelService.selectUser(datavo);
		return ResponseData.success(0, "11", user);
	}
	
	
	/**
	 * 查询网站名称
	 * @param datavo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dataSelectWebeSiteName")
	public ResponseData selectWebeSiteName(DataWebChannelVo datavo,HttpSession session){
		String id = (String)session.getAttribute("dataId");
		datavo.setUuid(id);
		DataWebChannelVo siteName = channelService.selectWebeSiteName(datavo);
		return ResponseData.success(0, "11", siteName);
		
	}
	
}
