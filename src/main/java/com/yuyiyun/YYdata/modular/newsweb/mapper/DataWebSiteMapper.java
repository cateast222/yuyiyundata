package com.yuyiyun.YYdata.modular.newsweb.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebSite;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebSiteParam;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebsiteVo;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据配置站点表 Mapper接口
 * @author YiZhiLong
 * @author WangShiPing
 *
 */
public interface DataWebSiteMapper extends BaseMapper<DataWebSite> {
	/**
	 * 网站修改
	 *
	 */
	int updateSite(DataWebSite dataWebSite);
	/**
	 * 删除网站
	 *
	 */
	int delete(Long id);
	/**
	 * 根据媒体id删除
	 *
	 */
	int deleteByMediaId(Long id);
	/**
	 * 新增
	 *
	 */
	int add(DataWebSite data);
	/**
	 * 分页查询
	 *
	 */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> getSites(@Param("page") Page page,@Param("param")DataWebSiteParam param,Long id);
	/**
	 * 查询媒体名称
	 *
	 */
	DataWebsiteVo selectMediaName(DataWebsiteVo sitevo);
	/**
	 * 根据id查询网站信息
	 *
	 */
	DataWebSite	selectSiteById(Long id);


}
