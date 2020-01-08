package com.yuyiyun.YYdata.modular.datasource.mapper;

import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
	List<Map<String, Object>> listFromNewspaper(String condition);

}
