package com.yuyiyun.YYdata.modular.perfoapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yuyiyun.YYdata.modular.perfoapp.service.PerfoAppraisalService;

import cn.stylefeng.roses.core.base.controller.BaseController;

/**
 * 绩效页面控制器
 * @author Duhao
 * @date 2020-07-07 14:47
 */
@Controller
@RequestMapping("/caiji/perfoAppraisal")
public class PerfoAppraisalController extends BaseController{

    private String prefix = "/modules/perfoapp";
    @Autowired
    private PerfoAppraisalService perfoAppraisalService;

   
    @GetMapping()
    public String perfoAppraisal(){
        return prefix + "/perfoAppraisal";
    }
        
    @GetMapping("/add")
    public String add(){
        return prefix + "/add.html";
    }

     
    
    /**
     * 修改
     */
   
    @GetMapping("/edit/{uuid}")
    public String edit(@PathVariable("uuid") Long uuid, ModelMap mmap){
        return prefix + "/edit.html";
    }

}
