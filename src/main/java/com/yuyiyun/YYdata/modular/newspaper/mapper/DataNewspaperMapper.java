package com.yuyiyun.YYdata.modular.newspaper.mapper;

import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * <p>
 * :电子报纸表Mapper 接口
 * </p>
 *
 * @author duhao
 * @since 2020-01-02
 */
public interface DataNewspaperMapper extends BaseMapper<DataNewspaper> {

	/**
	 * :电子报纸主页获取电子报纸列表
	 * 
	 * @param dataSource
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> listFromNewspaper(@Param("page") Page page, @Param("dataSource") Long dataSource,
			@Param("condition") String condition);

}
