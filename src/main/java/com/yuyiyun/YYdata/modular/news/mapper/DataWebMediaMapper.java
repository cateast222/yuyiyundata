package com.yuyiyun.YYdata.modular.news.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.news.entity.DataWebMedia;
import com.yuyiyun.YYdata.modular.news.entity.DataWebSite;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据配置站点表 Mapper接口
 * @author duhao
 *
 */
public interface DataWebMediaMapper extends BaseMapper<DataWebMedia> {

    List<Map<String, Object>> getSites(@Param("page") Page page, @Param("website_name") String website_name);

}
