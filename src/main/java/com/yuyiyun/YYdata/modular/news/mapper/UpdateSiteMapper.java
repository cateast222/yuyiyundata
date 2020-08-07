package com.yuyiyun.YYdata.modular.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yuyiyun.YYdata.modular.news.entity.DataWebSite;

public interface UpdateSiteMapper extends BaseMapper<DataWebSite> {
		DataWebSite	updateSiteById(String id);
	
		int updateSite(DataWebSite dataWebSite);
		int delete(String id);
}
