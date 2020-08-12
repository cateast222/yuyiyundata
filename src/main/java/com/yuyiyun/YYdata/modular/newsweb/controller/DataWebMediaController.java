package com.yuyiyun.YYdata.modular.newsweb.controller;


import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebMedia;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebMediaParam;
import com.yuyiyun.YYdata.modular.newsweb.service.DataWebMediaService;
import com.yuyiyun.YYdata.modular.newsweb.vo.Mediavo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 媒体添加修改页面
 * @author JT
 * @date 2020-07-07 14:47
 */
@Controller
@RequestMapping("/newsweb")
public class DataWebMediaController extends BaseController {
    private String PREFIX = "/modular/newsweb";
    @Autowired
    private DataWebMediaService dataWebMediaService;




    /**
     *跳转页面
     * @author JT
     * @return
     */
    @GetMapping("/media")
    public String achievements(Model model) {
        return PREFIX + "/media.html";
    }


    /**
     * 查询出所有的媒体
     * @param
     * @return
     */
    @RequestMapping("/selectMedia")
    @ResponseBody
    public LayuiPageInfo pageList(DataWebMediaParam param) {
        Page page = LayuiPageFactory.defaultPage();
        List<Map<String, Object>> list = this.dataWebMediaService.AllMedia(page, param);
        page.setRecords(list);
        return LayuiPageFactory.createPageInfo(page);
    }
    /**
     * 根据ID查询出媒体
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/selectByidMedia")
    public ResponseData selectByidMedia(DataWebMedia mediavo,HttpSession session){
    	Long attribute = (Long)session.getAttribute("uuid");
        mediavo.setUuid(attribute);
        List<Map> result = dataWebMediaService.ByidMedia(mediavo);
        return ResponseData.success(result);
    }

    /**
     * 根据ID删除媒体
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/delByidMedia")
    public boolean delByidMedia(DataWebMedia mediavo){
        boolean b = dataWebMediaService.removeById(mediavo);

        return b;
    }

    /**
     * 跳转修改页面
     * @param dataWebMedia
     *  @param uuid
     * @return
     */
    @GetMapping("/edidUnrevised")
    public String edidUnrevised(String uuid, DataWebMedia dataWebMedia,HttpSession session){
        session.setAttribute("uuid",uuid);
        return PREFIX + "/editmedia.html";
    }

    /**
     * 修改媒体
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/updateMedia")
    public ResponseData updatemedia(DataWebMedia dataWebMedia){
        //获取当前登录用户名
		String updateBy = ShiroKit.getUser().getName();
    	dataWebMedia.setUpdateBy(updateBy);
        List<Map> s = dataWebMediaService.selectAllMedia(dataWebMedia);
        if (s.size()==1){
            List<Map> a = dataWebMediaService.selectMedia(dataWebMedia);
            if (a.size()==1){
                int i = dataWebMediaService.updatemedia(dataWebMedia);
                return ResponseData.success(200, "11", i);
            }else{
                return ResponseData.success(500, "11", 12);
            }
        }else{
            return ResponseData.success(500, "11", 12);
        }
    }

    /**
     * 跳转新增页面
     * @param dataWebMedia
     * @return
     */
    @GetMapping("/add")
    public String addUnrevised(DataWebMedia dataWebMedia){
        return PREFIX + "/addmedia.html";
    }
    /**
     * 新增媒体
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/insertMedia")
    public ResponseData insertMedia(DataWebMedia media){
        List<Map> s = dataWebMediaService.selectMedia(media);
        if (ToolsUtil.isEmpty(s)){
            //获取当前登录用户名
    		String createBy = ShiroKit.getUser().getName();
            media.setCreateBy(createBy);
            int i = dataWebMediaService.inSerMedia(media);
            return ResponseData.success(200, "11", i);
        }else{
            return ResponseData.success(500, "11", 12);
        }

    }



    /**
     * 根据ID删除媒体
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping("/delMedia")
    public boolean delMedia(DataWebMedia mediavo){
        boolean b = dataWebMediaService.delMedia(mediavo);
        return b;
    }
}
