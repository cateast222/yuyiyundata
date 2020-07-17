package com.yuyiyun.YYdata.modular.perfoapp.controller;

import java.util.*;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.util.JwtTokenUtil;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewsService;
import com.yuyiyun.YYdata.modular.perfoapp.entity.PerfoAppraisalEntity;
import com.yuyiyun.YYdata.modular.perfoapp.vo.PerfoAppVo;
import org.apache.tomcat.jni.Mmap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;
import com.yuyiyun.YYdata.modular.perfoapp.service.PerfoAppraisalService;
import com.yuyiyun.YYdata.modular.system.entity.Dict;

import cn.stylefeng.roses.core.base.controller.BaseController;

/**
 * 绩效页面控制器
 * @author Duhao
 * @date 2020-07-07 14:47
 */
@Controller
@RequestMapping("/perfoAppraisal")
public class PerfoAppraisalController extends BaseController{

	private String PREFIX = "/modular/perfoapp";
    @Autowired
    private PerfoAppraisalService perfoAppraisalService;
	@Autowired
	DataNewsService dataNewsService;




   

    /**
	 * 
	 * 
	 * @author 
	 * @return
	 */
	@GetMapping("/achievements")
	public String achievements(Model model) {
		return PREFIX + "/achievements.html";
	}


	/**
	 * 查询出指定用户的历史记录
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectSelf")
	public ResponseData selectSelf(PerfoAppraisalEntity p,PerfoAppVo perfoAppVo){
		Long userId = ShiroKit.getUser().getId();
		perfoAppVo.setUserid(Long.valueOf(userId));
		List<PerfoAppVo> list = perfoAppraisalService.selectSelf(perfoAppVo);
		return ResponseData.success(0,"ll",list);
	}


	/**
	 * 保存绩效自评
	 * @param perfoAppraisalEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/insertPer")
    public String insertPer(PerfoAppraisalEntity perfoAppraisalEntity){
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		perfoAppraisalEntity.setUuid(uuid);
		String s = perfoAppraisalService.insertPer(perfoAppraisalEntity);
		return s;
	}
	/**
	 * 保存绩效自评
	 * @param perfoAppraisalEntity
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectById")
	public ResponseData selectById(PerfoAppraisalEntity perfoAppraisalEntity){
		System.err.println(perfoAppraisalEntity.getUuid());
		List<PerfoAppraisalEntity> list= perfoAppraisalService.selectById(perfoAppraisalEntity);
		return ResponseData.success(0,"ll",list);
	}
	/**
	 * 查询出当前启用的用户
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/select")
	public ResponseData select(){
		List<PerfoAppVo> select = perfoAppraisalService.select();
		return ResponseData.success(0,"ll",select);
	}
	/**
	 * 查询出当前登录的用户名
	 * @param
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/selectUser")
	public ResponseData selectUser(PerfoAppVo p){
		Long userId = ShiroKit.getUser().getId();
		p.setUserid(Long.valueOf(userId));
		List<PerfoAppVo> select = perfoAppraisalService.selectUser(p);
		return ResponseData.success(0,"ll",select);
	}








}
