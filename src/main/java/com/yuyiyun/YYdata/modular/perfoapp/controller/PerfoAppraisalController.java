package com.yuyiyun.YYdata.modular.perfoapp.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yuyiyun.YYdata.modular.perfoapp.entity.PerfoAppraisalEntity;
import com.yuyiyun.YYdata.modular.perfoapp.service.PerfoAppraisalService;

import cn.hutool.core.util.PageUtil;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;

/**
 * 绩效页面控制器
 * @author Duhao
 * @date 2020-07-07 14:47
 */
@Controller
@RequestMapping("/caiji/perfoAppraisal")
public class PerfoAppraisalController extends BaseController{

    private String prefix = "modules/caiji/perfoAppraisal";
    @Autowired
    private PerfoAppraisalService perfoAppraisalService;

   
    @GetMapping()
    public String perfoAppraisal(){
        return prefix + "/perfoAppraisal";
    }
        
    @RequestMapping("/list")
    
       
    @GetMapping("/add")
    public String add(){
        return prefix + "/add";
    }

     
    
    /**
     * 修改
     */
   
    @GetMapping("/edit/{PERFOID}")
    public String edit(@PathVariable("PERFOID") Long PERFOID, ModelMap mmap){
    //    mmap.put("perfoAppraisal", perfoAppraisal);
        return prefix + "/edit";
    }

   

   
}
