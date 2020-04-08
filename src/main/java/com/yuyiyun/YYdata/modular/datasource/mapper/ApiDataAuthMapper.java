package com.yuyiyun.YYdata.modular.datasource.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.datasource.entity.ApiDataAuth;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author duhao
 * @since 2020-04-07
 */
public interface ApiDataAuthMapper extends BaseMapper<ApiDataAuth> {

	/**
	 * 分页查询
	 * 
	 * @param pageContext
	 * @param dataAuth
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> selectPage(@Param("page") Page pageContext,
			@Param("apiDataAuth") ApiDataAuth apiDataAuth);

}
