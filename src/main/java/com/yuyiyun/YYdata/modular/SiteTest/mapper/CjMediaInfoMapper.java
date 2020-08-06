package com.yuyiyun.YYdata.modular.SiteTest.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.SiteTest.entity.CjMediaInfoEntity;
import com.yuyiyun.YYdata.modular.SiteTest.model.param.CjMediaInfoParam;
import com.yuyiyun.YYdata.modular.SiteTest.vo.CjMediaInfoVo;

public interface CjMediaInfoMapper extends BaseMapper<CjMediaInfoEntity> {
	List<CjMediaInfoVo> findAll(CjMediaInfoEntity cjm);
	List<CjMediaInfoEntity> findBy(CjMediaInfoEntity cjm);
	CjMediaInfoEntity findById(int id);
	
	
	
	int selectCount();
	int delete(int id);
	int update(CjMediaInfoEntity cjm);
	int add(CjMediaInfoEntity cjm);
	
	
	
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> selectPage(@Param("page") Page page,@Param("limit") CjMediaInfoParam param);
	List<CjMediaInfoEntity> select(int curr,int limit);
}
