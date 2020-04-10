package com.yuyiyun.YYdata.modular.newspaper.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;

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
	 * 电子报纸主页获取电子报纸列表
	 * 
	 * @param dataSource
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	Page<Map<String, Object>> listFromNewspaper(@Param("page") Page page, @Param("dataSource") Long dataSource,
			@Param("condition") String condition);

	/**
	 * 报纸新闻主页获取电子报纸列表
	 * 
	 * @param page
	 * @param publish
	 * @param condition
	 * @return
	 */
	Page<Map<String, Object>> listFromNews(@Param("page") Page<?> page, @Param("publish") String publish,
			@Param("condition") String condition);

	/**
	 * 按归档日期分页查询电子报纸数据
	 * @param page
	 * @param sysUser
	 * @param archiveDate
	 * @return
	 */
	List<Map<String, Object>> selectArchive(@Param("page") Page<?> page, @Param("sysUser") Long sysUser,
			@Param("archiveDate") String archiveDate);

}
