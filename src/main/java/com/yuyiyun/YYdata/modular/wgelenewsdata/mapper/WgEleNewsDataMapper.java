package com.yuyiyun.YYdata.modular.wgelenewsdata.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.wgelenewsdata.entity.WgEleNewsData;

/**
 * <p>
 * 闻歌电子报纸数据表 Mapper接口
 * </p>
 * 
 * @author duhao
 *
 */
public interface WgEleNewsDataMapper extends BaseMapper<WgEleNewsData> {

	/**
	 * :根据日期获取分类归档的数据
	 * 
	 * @param pubTime
	 * @param condition
	 * @return
	 */
	List<Map<String, Object>> getDateArchive(@Param("page") Page page, @Param("pubTime") String pubTime,
			@Param("condition") String condition);

}
