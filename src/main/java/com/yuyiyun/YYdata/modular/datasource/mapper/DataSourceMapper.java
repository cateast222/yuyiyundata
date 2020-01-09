package com.yuyiyun.YYdata.modular.datasource.mapper;

import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * :数据源表Mapper 接口
 * </p>
 *
 * @author duhao
 * @since 2020-01-03
 */
public interface DataSourceMapper extends BaseMapper<DataSource> {

	/**
	 * :电子报纸主页获取数据源列表
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> listFromNewspaper(@Param("page") Page page,@Param("condition") String condition);

}
