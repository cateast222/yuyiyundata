package com.yuyiyun.YYdata.modular.SiteTest.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.modular.SiteTest.entity.CjMediaInfoEntity;
import com.yuyiyun.YYdata.modular.SiteTest.mapper.CjMediaInfoMapper;
import com.yuyiyun.YYdata.modular.SiteTest.model.param.CjMediaInfoParam;
import com.yuyiyun.YYdata.modular.SiteTest.vo.CjMediaInfoVo;
@Service
public class CjMediaInfoService extends ServiceImpl<CjMediaInfoMapper, CjMediaInfoEntity>{
	@Resource
	private CjMediaInfoMapper cjMediaInfoMapper;
	
	public List<CjMediaInfoVo> findAll(CjMediaInfoEntity cjm){
		List<CjMediaInfoVo> list = cjMediaInfoMapper.findAll(cjm);
		return list;
	}
	
	public List<CjMediaInfoEntity> select(int curr,int limit){
		return cjMediaInfoMapper.select(curr, limit);
	}
	
	public List<CjMediaInfoEntity> findBy(CjMediaInfoEntity cjm){
		return cjMediaInfoMapper.findBy(cjm);
	}
	
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> listFromCjMedia(Page page, CjMediaInfoParam param) {
		return this.baseMapper.selectPage(page, param);
	}
	
	public CjMediaInfoEntity findById(int id) {
		CjMediaInfoEntity list = cjMediaInfoMapper.findById(id);
		return list;
	}
	
	public int delete(int id) {
		return cjMediaInfoMapper.delete(id);
	}
	
	public int update(CjMediaInfoEntity cjm) {
		return cjMediaInfoMapper.update(cjm);
	}
	
	public int add(CjMediaInfoEntity cjm) {
		return cjMediaInfoMapper.add(cjm);
	}
	public int selectCount() {
		return cjMediaInfoMapper.selectCount();
	}
}
