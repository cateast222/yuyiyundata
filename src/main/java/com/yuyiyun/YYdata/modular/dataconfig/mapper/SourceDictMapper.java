package com.yuyiyun.YYdata.modular.dataconfig.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;
import com.yuyiyun.YYdata.modular.dataconfig.entity.SourceDict;

/**
 * <p>
 * 数据源字典关联表 Mapper 接口
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
public interface SourceDictMapper extends BaseMapper<SourceDict> {

	/**
	 * 分页查询
	 * 
	 * @param pageContext
	 * @param sourceDict
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> selectPage(@Param("page") Page pageContext, @Param("sourceDict") SourceDict sourceDict);
	
	/**
	 * 分页查询
	 * 
	 * @param pageContext
	 * @param sourceDict
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> selPageSourceDict(@Param("page") Page pageContext, @Param("sourceDict") SourceDict sourceDict,
			@Param("dataDict") DataDict dataDict);

	/**
	 * @Description 根据数据源与数据字典获取配置数据
	 * @author duhao
	 * @date 2020年5月28日
	 * @version V1.0
	 * @param dataSource
	 * @param dataDict
	 * @return
	 */
	List<Map<String, Object>> getDetails(@Param("dataSource")Long dataSource, @Param("dataDict") Long dataDict);

}
