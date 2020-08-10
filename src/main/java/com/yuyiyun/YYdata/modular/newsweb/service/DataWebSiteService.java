package com.yuyiyun.YYdata.modular.newsweb.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.modular.newsweb.mapper.DataWebSiteMapper;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebsiteVo;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebSite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
     * 添加
     * @param dataWebSite
     * @return
     */
    public int add(DataWebSite dataWebSite) {
        return datawebmapper.add(dataWebSite);
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
