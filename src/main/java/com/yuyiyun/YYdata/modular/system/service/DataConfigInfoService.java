package com.yuyiyun.YYdata.modular.system.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.modular.system.entity.DataConfigInfo;
import com.yuyiyun.YYdata.modular.system.entity.DataSourceInfo;
import com.yuyiyun.YYdata.modular.system.mapper.DataConfigInfoMapper;
import com.yuyiyun.YYdata.modular.system.model.DataConfigdto;

/**
 * 数据配置信息服务实现类
 * @author duhao
 *
 */
@Service
public class DataConfigInfoService extends ServiceImpl<DataConfigInfoMapper, DataConfigInfo>{

	public DataConfigdto detail(String dsiUuid, String key) {
		QueryWrapper<DataConfigInfo> wrapper = new QueryWrapper<DataConfigInfo>();
		wrapper.and(i->i.eq("dsi_uuid", dsiUuid)).and(i->i.eq("ddi_key", key));
		List<DataConfigInfo> list = this.baseMapper.selectList(wrapper);
		DataConfigdto configdto = new DataConfigdto(dsiUuid, key);
		configdto.set(list);
		return configdto;
	}

	public void addAndEdit(DataConfigdto dataConfigdto,String creator) {
		System.out.println(dataConfigdto);
		List<DataConfigInfo> list = dataConfigdto.get();
		for (DataConfigInfo info : list) {
			info.setDsiUuid(dataConfigdto.getDsiUuid());
			QueryWrapper<DataConfigInfo> wrapper = new QueryWrapper<DataConfigInfo>()
					.and(i -> i.eq("dsi_uuid", info.getDsiUuid()))
					.and(i -> i.eq("ddi_value", info.getDdiValue()));
			int count = this.count(wrapper);
			DataConfigInfo configInfo = info;
			if (count==0) {
				configInfo.setUuid(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
				configInfo.setCreateTime(new Date());
				configInfo.setCreator(creator);
				configInfo.setRemark("");
				configInfo.setState(1);
				System.out.println("新增");
			}else if (count==1) {
				configInfo = this.list(wrapper).get(0);
				configInfo.setValue(info.getValue());
				System.out.println("更新");
			}
			configInfo.setUpdateTime(new Date());
			System.out.println(configInfo);
			this.saveOrUpdate(configInfo);
		}		
	}
	
	

}
