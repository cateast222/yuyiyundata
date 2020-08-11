package com.yuyiyun.YYdata.modular.newsweb.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.modular.newsweb.mapper.DataWebSiteMapper;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebSiteParam;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebsiteVo;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebSite;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

/**
 * @author YiZhiLong
 * @author WangShiPing
 */
@Service
public class DataWebSiteService extends ServiceImpl<DataWebSiteMapper, DataWebSite> {
	
	@Resource
	private DataWebSiteMapper datawebmapper;
	
	
	/**
     * 分页查询
     * @param sitevo
     * @return
     */
	@SuppressWarnings("rawtypes")
    public List<Map<String, Object>> getSites(Page page, DataWebSiteParam param,String id) {
        return baseMapper.getSites(page,param,id);
    }
    
    /**
     * 查询媒体名称
     * @param sitevo
     * @return
     */
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
     * 添加，判断重复
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

    
    /**
     * 根据id查询当前网站的信息
     * @param id
     * @return
     */
    public DataWebSite selectSiteById(String id) {
		DataWebSite dataWebSite=datawebmapper.selectSiteById(id);
		return dataWebSite;
	}
	
	/**
	 * 修改网站
	 * @param dataWebSite
	 * @return
	 */
	public int updateSite(DataWebSite dataWebSite) {
		return datawebmapper.updateSite(dataWebSite);
	}
	
	
	/**
	 * 网站删除
	 * @param id
	 * @return
	 */
	public int deleteSite(String id) {
		return datawebmapper.delete(id);
	}
	
	/**
	 * 根据媒体id删除
	 * @param id
	 * @return
	 */
	public int deleteByMediaId(String id) {
		return datawebmapper.deleteByMediaId(id);
	}
    
}
