package com.yuyiyun.YYdata.modular.news.service;

import javax.annotation.Resource;

import org.apache.poi.hssf.record.DateWindow1904Record;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.modular.news.entity.DataWebSite;
import com.yuyiyun.YYdata.modular.news.mapper.UpdateSiteMapper;

@Service
public class UpdateSiteService extends ServiceImpl<UpdateSiteMapper, DataWebSite> {
	@Resource
	private UpdateSiteMapper updateSiteMapper;
	
	public DataWebSite updateSiteById(String id) {
		DataWebSite dataWebSite=updateSiteMapper.updateSiteById(id);
		return dataWebSite;
	}
	
	public int updateSite(DataWebSite dataWebSite) {
		return updateSiteMapper.updateSite(dataWebSite);
	}
	
	public int deleteSite(String id) {
		return updateSiteMapper.delete(id);
	}
}
