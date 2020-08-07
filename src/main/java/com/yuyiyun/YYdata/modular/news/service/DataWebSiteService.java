package com.yuyiyun.YYdata.modular.news.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.news.entity.DataWebMedia;
import com.yuyiyun.YYdata.modular.news.mapper.DataWebSiteMapper;
import com.yuyiyun.YYdata.modular.news.entity.DataWebSite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DataWebSiteService extends ServiceImpl<DataWebSiteMapper, DataWebSite> {

@Autowired
DataWebMediaService dataWebMediaService;


    public List<Map<String, Object>> getSites(Page page, DataWebSite dataWebSite) {

        if (dataWebSite.getProxy()!=null && !"".equals(dataWebSite.getProxy())){

            if ("0".equals(dataWebSite.getProxy())){
                dataWebSite.setProxy("境内");
            }else {
                dataWebSite.setProxy("境外");
            }

        }

        if (dataWebSite.getState()!=null && !"".equals(dataWebSite.getState())){

            if ("0".equals(dataWebSite.getState())){
                dataWebSite.setState("启用");
            }else {
                dataWebSite.setState("废弃");
            }

        }



        return this.baseMapper.getSites(page,dataWebSite);
    }

    /**
     * 😂😂😂
     * @param dataWebSite
     * @return
     */
    public boolean add(DataWebSite dataWebSite) {


        //先从数据库查询对应的媒体名称
        Wrapper<DataWebMedia> webMediaWrapper=new QueryWrapper<>();
        ((QueryWrapper<DataWebMedia>) webMediaWrapper).eq("website_name",dataWebSite.getWebsiteName());
        DataWebMedia dataWebMedia = dataWebMediaService.getOne(webMediaWrapper);

        if (dataWebMedia==null){throw new RuntimeException("媒体名称不存在");}

        dataWebSite.setDataWebMedia(dataWebMedia.getUuid());

        dealState(dataWebSite);
        String name = ShiroKit.getUser().getName();

        dataWebSite.setCreateBy(name);
        dataWebSite.setUpdateBy(name);
        dataWebSite.setUpdateTime(new Date());
        dataWebSite.setUpdateTime(new Date());
        return this.baseMapper.insert(dataWebSite)>0;
    }

    private void dealState(DataWebSite dataWebSite) {
        if ("0".equals(dataWebSite.getState())){
            dataWebSite.setState("启用");
        }else {
            dataWebSite.setState("禁用");
        }

        if ("0".equals(dataWebSite.getProxy())){
            dataWebSite.setProxy("境内");
        }else {
            dataWebSite.setProxy("境外");
        }
    }

    public boolean condition(String medianame) {
        Wrapper<DataWebMedia> webMediaWrapper=new QueryWrapper<>();
        ((QueryWrapper<DataWebMedia>) webMediaWrapper).eq("website_name",medianame);
        DataWebMedia dataWebMedia = dataWebMediaService.getOne(webMediaWrapper);
       return dataWebMedia==null;
    }
}
