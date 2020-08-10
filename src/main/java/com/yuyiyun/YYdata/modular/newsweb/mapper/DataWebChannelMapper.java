package com.yuyiyun.YYdata.modular.newsweb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannelEntity;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebChannelParam;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebChannelVo;


/**
 * @author WuXiangDong
 * @author TangJianRong
 *  频道管理接口
 */
public interface DataWebChannelMapper extends BaseMapper<DataWebChannelEntity> {
	
	
	/**
	 * 删除
	 * 
	 * 
	 * */
	int delete(String id);
	
	/**
	 * 根据媒体id删除
	 * @param id
	 * @return
	 */
	int deleteByMediaId(String id);
	
	/**
	 * 根据网站id删除
	 * @param id
	 * @return
	 */
	int deleteBySiteId(String id);
	
	/**
	 * 修改
	 * @param data
	 * @return
	 */
	int update(DataWebChannelEntity data);
	
	/**
	 * 添加
	 * @param data
	 * @return
	 */
	int add(DataWebChannelEntity data);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int deleteBacth(@Param("ids") String[] ids);
	
	/**
	 * 批量更新
	 * @param ids
	 * @return
	 */
	int updateBacth(@Param("ids") String[] ids);
	
	
	/**
	 * 根据id查询当前行的信息，编辑回显
	 * 
	 * 
	 * */
	DataWebChannelEntity selectById(String id);
	
	
	/**
	 * 分页查询加搜索
	 * 
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> selectPage(@Param("page") Page page,@Param("param") DataWebChannelParam param,String id);
	
	/**
	 * 根据网站id查询
	 * 
	 * 
	 * */
	List<DataWebChannelEntity> selectBySiteId(String id);
	
	
	/**
	 * 查询媒体UUID、媒体名称、网站名称
	 * @param DataWebChannelVo
	 * @return
	 */
	DataWebChannelVo selectWebSiteName(DataWebChannelVo DataWebChannelVo);
}
