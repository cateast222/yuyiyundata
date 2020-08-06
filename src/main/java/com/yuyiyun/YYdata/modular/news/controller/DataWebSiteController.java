package com.yuyiyun.YYdata.modular.news.controller;


import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.annotion.Permission;
import com.yuyiyun.YYdata.core.common.constant.Const;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.modular.news.entity.DataWebSite;
import com.yuyiyun.YYdata.modular.news.service.DataWebSiteService;
import com.yuyiyun.YYdata.modular.system.warpper.LogWrapper;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@Api(value = "站点controller", tags = {"站点操作接口"})
@RequestMapping("/site")
public class DataWebSiteController {

    private String PREFIX = "/modular/system/log/";

    @Autowired
    private DataWebSiteService dataWebSiteService;

    @RequestMapping("")
    public String indexs() {
        return PREFIX + "site.html";
    }

    @RequestMapping("condition")
    public ResponseData condition(@RequestParam(required = false) String medianame) {
        boolean flag = dataWebSiteService.condition(medianame);
        if (flag) {
            throw new RuntimeException("媒体名称不存在");
        }
        return ResponseData.success();
    }


    @RequestMapping("/add_prefix")
    public String addPrefix() {
        return PREFIX + "site_add.html";
    }

    @RequestMapping("/add")
    public String add(DataWebSite dataWebSite) {
        System.out.println("******************************");
        System.out.println(dataWebSite);
        dataWebSiteService.add(dataWebSite);
        System.out.println("******************************");
        return PREFIX + "site_add.html";
    }
    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/list")
    @Permission(Const.ADMIN_NAME)
    @ResponseBody
    public Object list(DataWebSite dataWebSite) {
        //获取分页参数
        Page page = LayuiPageFactory.defaultPage();
        System.out.println("******************************");
        System.out.println(dataWebSite);
        System.out.println("******************************");
        List<Map<String, Object>> result = dataWebSiteService.getSites(page, dataWebSite);
        page.setRecords(new LogWrapper(result).wrap());
        return LayuiPageFactory.createPageInfo(page);
    }


}
