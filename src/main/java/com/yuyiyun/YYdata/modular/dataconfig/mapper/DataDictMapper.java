package com.yuyiyun.YYdata.modular.dataconfig.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;

/**
 * <p>
 * 数据字典表 Mapper 接口
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
public interface DataDictMapper extends BaseMapper<DataDict> {

    /**
    * 分页查询
    * @param pageContext
    * @param dataDict
    * @return
    */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> selectPage(@Param("page")Page pageContext, @Param("dataDict")DataDict dataDict);

}

