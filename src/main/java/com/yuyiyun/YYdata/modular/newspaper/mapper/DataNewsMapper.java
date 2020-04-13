package com.yuyiyun.YYdata.modular.newspaper.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNews;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author duhao
 * @since 2020-01-03
 */
public interface DataNewsMapper extends BaseMapper<DataNews> {
	/**
	 * 分页查询报刊新闻数据
	 * @param pageContext
	 * @param newspaperId
	 * @return
	 */
	List<Map<String, Object>> selectArchive(@Param("page") Page<Map<String, Object>> pageContext,
			@Param("newspaperId") Long newspaperId);

}
