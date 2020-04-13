package com.yuyiyun.YYdata.modular.system.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.system.entity.DataDicInfo;
import com.yuyiyun.YYdata.modular.system.mapper.DataDicInfoMapper;
import com.yuyiyun.YYdata.modular.system.model.DatadiDto;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * 数据字典信息服务实现类
 * @author duhao
 *
 */
@Service
public class DataDicInfoService extends ServiceImpl<DataDicInfoMapper, DataDicInfo> {

	public LayuiPageInfo config() {
		QueryWrapper<DataDicInfo> datadiQueryWrapper = new QueryWrapper<DataDicInfo>();
		datadiQueryWrapper.and(i->i.eq("type",201)).and(i->i.eq("state", 1).or().eq("state", 2).or().eq("state", 3)).groupBy("`key`").orderByAsc("state");
		List<DataDicInfo> list = this.baseMapper.selectList(datadiQueryWrapper);
		for (DataDicInfo dataDicInfo : list) {
			dataDicInfo.setSummary(dataDicInfo.getSummary().split("》》")[0]);
		}
		System.out.println(list);
		LayuiPageInfo info = new LayuiPageInfo();
		info.setData(list);
		info.setCount(list.size());
		return info;
	}
	
	@SuppressWarnings("unused")
	private DatadiDto getEntity(DataDicInfo param) {
        DatadiDto dto = new DatadiDto();
        ToolUtil.copyProperties(param, dto);
        return dto;
    }
	
}