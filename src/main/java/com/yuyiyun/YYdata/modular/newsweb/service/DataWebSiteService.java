package com.yuyiyun.YYdata.modular.newsweb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.modular.newsweb.mapper.DataWebSiteMapper;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebsiteVo;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannelEntity;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebSite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service
public class DataWebSiteService extends ServiceImpl<DataWebSiteMapper, DataWebSite> {
	
	@Resource
	private DataWebSiteMapper datawebmapper;

    public List<Map<String, Object>> getSites(Page page, DataWebSite dataWebSite,String id) {
        return this.baseMapper.getSites(page,dataWebSite,id);
    }
    
    public DataWebsiteVo selectMediaName(DataWebsiteVo sitevo){
    	DataWebsiteVo mediaName = datawebmapper.selectMediaName(sitevo);
		return mediaName ;
    }
    
    
    /**
	 * 排重
	 * 
	 * */
	private boolean checkcolumnrepeat(DataWebSite data) {
		QueryWrapper<DataWebSite> wrapper = new QueryWrapper();
		wrapper.eq("website_sub_url", data.getWebsiteSubUrl());
		List<DataWebSite> dataWebList = datawebmapper.selectList(wrapper);
		List<String> subSiteUrlList = dataWebList.stream().map(DataWebSite::getWebsiteSubUrl).collect(Collectors.toList());
		//判断网址是否在集合内
		if (subSiteUrlList.contains(data.getWebsiteSubUrl())) {
			//重复
			return false;
		} else {
			//不重复
			return true;
		}
	}

    
    
    /**
     * 添加
     * @param dataWebSite
     * @return
     */
    public int add(DataWebSite data) {
    	//网站频道重复
		if (checkcolumnrepeat(data)) {
			 return  datawebmapper.add(data);
		}else {
			return 0;
		}
       
    }

    
    public DataWebSite updateSiteById(String id) {
		DataWebSite dataWebSite=datawebmapper.updateSiteById(id);
		return dataWebSite;
	}
	
	public int updateSite(DataWebSite dataWebSite) {
		return datawebmapper.updateSite(dataWebSite);
	}
	
	public int deleteSite(String id) {
		return datawebmapper.delete(id);
	}
    
}
