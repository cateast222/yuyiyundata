package com.yuyiyun.YYdata.modular.SiteTest.controller;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.SiteTest.entity.CjMediaInfoEntity;
import com.yuyiyun.YYdata.modular.SiteTest.model.param.CjMediaInfoParam;
import com.yuyiyun.YYdata.modular.SiteTest.service.CjMediaInfoService;
import com.yuyiyun.YYdata.modular.SiteTest.vo.CjMediaInfoVo;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewsService;

import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;

@Controller
@RequestMapping("/cjmediainfo")
public class CjMediaInfoController extends BaseController {
	private String PREFIX = "/modular/SiteTest";
	@Autowired
	DataNewsService dataNewsService;
	@Autowired
	private CjMediaInfoService cjMediaInfoService;

	@GetMapping("/cjmedia")
	public String cjmediainfo(Model model) {
		return PREFIX + "/cjmediainfo.html";
	}
	
	@GetMapping("/add")
	public String add(Model model) {
		return PREFIX + "/add.html";
	}
	
	@GetMapping(value = "/edit")
	public String edit(int id,HttpSession session) {
			session.setAttribute("id", id);
			return PREFIX + "/edit.html";
	}
	@GetMapping(value = "/addEdit")
	public String addEdit(int id,HttpSession session) {
			session.setAttribute("aid", id);
			return PREFIX + "/add_edit.html";
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectAll")
	public ResponseData selectAll(CjMediaInfoVo cjm) {
		List<CjMediaInfoVo> list = cjMediaInfoService.findAll(cjm);
		return ResponseData.success(0, "11", list);
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/findBy")
	public ResponseData findBy(HttpServletRequest request) {
		System.err.println("findBy被执行了");
		CjMediaInfoEntity cj = new CjMediaInfoEntity();
		String mname = request.getParameter("mname");
		String country = request.getParameter("country");
		String domfor = request.getParameter("domfor");
		cj.setCountrycode(country);
		cj.setDomfor(domfor);
		cj.setMname(mname);
		System.err.println("cj的值"+cj);
		List<CjMediaInfoEntity> list = cjMediaInfoService.findBy(cj);
		return ResponseData.success(0, "11", list);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping("/selectPage")
	@ResponseBody
	public LayuiPageInfo listFromNewspaper(CjMediaInfoParam param) {
		System.err.println("selectPage执行啦");
		Page page = LayuiPageFactory.defaultPage();
		List<Map<String, Object>> list = this.cjMediaInfoService.listFromCjMedia(page, param);
		page.setRecords(list);
		return LayuiPageFactory.createPageInfo(page);
	}
	
	@ResponseBody
	@RequestMapping(value = "/selectById")
	public ResponseData selectById(HttpSession session) {
		int id = (int)session.getAttribute("aid");
		CjMediaInfoEntity list = cjMediaInfoService.findById(id);
		return ResponseData.success(0, "11", list);
	}
	@ResponseBody
	@RequestMapping(value = "/selectByMId")
	public ResponseData selectByMId(HttpSession session) {
		int id = (int)session.getAttribute("id");
		CjMediaInfoEntity list = cjMediaInfoService.findById(id);
		return ResponseData.success(0, "11", list);
	}
	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResponseData delete(int id) {
		int i = cjMediaInfoService.delete(id);
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("删除失败");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/update")
	public ResponseData update(HttpServletRequest request) {
		CjMediaInfoEntity cjm = new CjMediaInfoEntity();
		String id = request.getParameter("mediaid");
		String domfor = request.getParameter("domfor");
		String name = request.getParameter("mname");
		String countrycode = request.getParameter("countrycode");
		String provincecode = request.getParameter("provincecode");
		String citycode = request.getParameter("citycode");
		String areacode = request.getParameter("areacode");
		String label = request.getParameter("label");
		String state = request.getParameter("state");
		Long mediaid = Long.parseLong(id);
		cjm.setMediaid(mediaid);
		cjm.setMname(name);
		cjm.setDomfor(domfor);
		cjm.setCountrycode(countrycode);
		cjm.setProvincecode(provincecode);
		cjm.setCitycode(citycode);
		cjm.setAreacode(areacode);
		cjm.setLabel(label);
		cjm.setState(state);
		int i = cjMediaInfoService.update(cjm);
		System.err.println("update方法被执行了");
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("修改失败");
		}
	}
	
	@ResponseBody
	@RequestMapping(value = "/add" , method = RequestMethod.POST)
	public ResponseData add(HttpServletRequest request) {
		CjMediaInfoEntity cjm = new CjMediaInfoEntity();
		String name = request.getParameter("mname");
		String domfor = request.getParameter("domfor");
		String countrycode = request.getParameter("countrycode");
		String provincecode = request.getParameter("provincecode");
		String citycode = request.getParameter("citycode");
		String areacode = request.getParameter("areacode");
		String label = request.getParameter("label");
		String state = request.getParameter("state");
		cjm.setMname(name);
		cjm.setDomfor(domfor);
		cjm.setCountrycode(countrycode);
		cjm.setProvincecode(provincecode);
		cjm.setCitycode(citycode);
		cjm.setAreacode(areacode);
		cjm.setLabel(label);
		cjm.setState(state);
		int i = cjMediaInfoService.add(cjm);
		System.err.println("add方法被执行了");
		if(i>0) {
			return ResponseData.success();
		}else {
			return ResponseData.error("新增失败");
		}
	}
}
