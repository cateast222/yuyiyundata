package com.yuyiyun.YYdata.modular.newsweb.controller;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannel;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebChannelParam;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebChannelService;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebChannelVo;

import cn.stylefeng.roses.core.reqres.response.ResponseData;

/**
 * @author WuXiangDong
 * @author TangJianRong
 *  频道列表控制器
 */
@Controller
@RequestMapping("datawebchannel")
public class DataWebChannelController {
	private String PREFIX = "/modular/newsweb";
	@Autowired
	private DataWebChannelService channelService ;

	/**
	 * 跳转到频道列表
	 * 
	 * 
	 * */
	@GetMapping("/dataweb")
	public String datawebchannel(Long id,HttpSession session){
		//网站列表跳转到频道列表传入网站的UUID保存在session中
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
	public String addEdit(Long id,HttpSession session) {
		//点击编辑按钮获取当前行的UUID并保存在Session中
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
	public LayuiPageInfo selectPage(DataWebChannelParam param,HttpSession session) {
		//获取跳转过来的UUID
		Long  id = (Long)session.getAttribute("dataId");
		Page page = LayuiPageFactory.defaultPage();
		List<Map<String, Object>> list = this.channelService.selectPage(page, param,id);
		page.setRecords(list);
		return LayuiPageFactory.createPageInfo(page);
	}


	/**
	 * 点击编辑根据频道id查询、用于数据回显
	 * 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/dataSelectById")
	public ResponseData selectById(HttpSession session) {
		//获取点击编辑拿到的UUID并根据此id查询出当前行的所有数据
		Long id = (Long)session.getAttribute("id");
		DataWebChannel data = channelService.selectById(id);
		if(data!=null) {
			return ResponseData.success(200, "获取数据成功", data);
		}else {
			return ResponseData.error(500, "获取数据失败", data);
		}
	}

	/**
	 * 删除
	 * 
	 * 
	 * */
	@ResponseBody
	@RequestMapping(value = "/dataDelete")
	public ResponseData delete(Long id) {
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
	public ResponseData deleteBacth(Long[] ids) {
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
	public ResponseData dataUpdateBacth(Long[] ids) {
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
	public ResponseData update(DataWebChannel data,HttpSession session) {
		Long  dataWebSite = (Long)session.getAttribute("dataId");
		//获取当前登录用户名
		String updateBy = ShiroKit.getUser().getName();
		data.setDataWebWebsite(dataWebSite);
		data.setUpdateBy(updateBy);
		data.setUpdateTime(new Date());
		int i = 0;
		List<Map> list = channelService.selectAllChannel(data);
		if(list.size()==1) {
			List<Map> channel = channelService.selectChannel(data);
			if(channel.size()==1) {
				 i = channelService.update(data);
			}
		}
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
	public ResponseData add(DataWebChannel data,HttpSession session) {
		//获取当前登录用户名
		String createBy = ShiroKit.getUser().getName();
		//获取跳转过来的UUID,将其设置为频道表的data_web_website
		Long webWebsite = (Long)session.getAttribute("dataId");
		data.setDataWebWebsite(webWebsite);
		data.setCreateBy(createBy);
		data.setCreateTime(new Date());
		int i = channelService.add(data);
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("新增失败");
		}
	}


	/**
	 * 查询媒体UUID、媒体名称、网站名称
	 * @param datavo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/dataSelectWebeSiteName")
	public ResponseData selectWebeSiteName(DataWebChannelVo datavo,HttpSession session){
		//获取跳转过来的网站UUID，根据此id查询出对应的媒体UUID、媒体名称、网站名称
		Long id = (Long)session.getAttribute("dataId");
		datavo.setUuid(id);
		DataWebChannelVo siteName = channelService.selectWebSiteName(datavo);
		return ResponseData.success(0, "11", siteName);
	}
}
