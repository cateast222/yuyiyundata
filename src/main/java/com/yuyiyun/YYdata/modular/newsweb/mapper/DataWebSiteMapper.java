package com.yuyiyun.YYdata.modular.newsweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebSite;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebsiteVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据配置站点表 Mapper接口
 * @author duhao
 *
 */
public interface DataWebSiteMapper extends BaseMapper<DataWebSite> {

    List<Map<String, Object>> getSites(@Param("page") Page page,@Param("dataWebSite")DataWebSite dataWebSite,String id);
    
    DataWebsiteVo selectMediaName(DataWebsiteVo sitevo);
    DataWebSite	updateSiteById(String id);
	int updateSite(DataWebSite dataWebSite);
	int delete(String id);
	int add(DataWebSite data);
}
