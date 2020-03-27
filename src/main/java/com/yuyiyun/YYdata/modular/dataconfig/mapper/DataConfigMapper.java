package com.yuyiyun.YYdata.modular.dataconfig.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataConfig;

/**
 * <p>
 * 数据配置表 Mapper 接口
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
public interface DataConfigMapper extends BaseMapper<DataConfig> {

    /**
    * 分页查询
    * @param pageContext
    * @param dataConfig
    * @return
    */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> selectPage(@Param("page")Page pageContext, @Param("config")DataConfig dataConfig);

}

