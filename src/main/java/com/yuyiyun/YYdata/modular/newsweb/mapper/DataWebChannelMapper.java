package com.yuyiyun.YYdata.modular.newsweb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannelEntity;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebChannelParam;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebChannelVo;

public interface DataWebChannelMapper extends BaseMapper<DataWebChannelEntity> {
	
	
	/**
	 * 删除
	 * 修改
	 * 添加
	 * 批量删除
	 * 批量更新
	 * */
	int delete(String id);
	int update(DataWebChannelEntity data);
	int add(DataWebChannelEntity data);
	int deleteBacth(@Param("ids") String[] ids);
	int updateBacth(@Param("ids") String[] ids);
	
	
	/**
	 * 根据id查询当前行的信息，编辑回显
	 * 
	 * 
	 * */
	DataWebChannelEntity findById(String id);
	
	
	/**
	 * 分页查询加搜索
	 * 
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> selectPage(@Param("page") Page page,@Param("param") DataWebChannelParam param,String id);
	
	/**
	 * 查询当前登录用户
	 * 
	 * 
	 * */
	DataWebChannelVo selectUser(DataWebChannelVo DataWebChannelVo);
	
	/**
	 * 查询网站名字
	 * @param DataWebChannelVo
	 * @return
	 */
	DataWebChannelVo selectWebSiteName(DataWebChannelVo DataWebChannelVo);
}
