package com.yuyiyun.YYdata.modular.newsweb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannel;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebChannelParam;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebChannelVo;


/**
 * @author WuXiangDong
 * @author TangJianRong
 *  频道管理接口
 */
public interface DataWebChannelMapper extends BaseMapper<DataWebChannel> {
	
	
	/**
	 * 删除
	 * 
	 * 
	 * */
	int delete(Long id);
	
	/**
	 * 根据媒体id删除
	 * @param id
	 * @return
	 */
	int deleteByMediaId(Long id);
	
	/**
	 * 根据网站id删除
	 * @param id
	 * @return
	 */
	int deleteBySiteId(Long id);
	
	/**
	 * 修改
	 * @param data
	 * @return
	 */
	int update(DataWebChannel data);
	
	/**
	 * 添加
	 * @param data
	 * @return
	 */
	int add(DataWebChannel data);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int deleteBacth(@Param("ids") Long[] ids);
	
	/**
	 * 批量更新
	 * @param ids
	 * @return
	 */
	int updateBacth(@Param("ids") Long[] ids);
	
	
	/**
	 * 根据id查询当前行的信息，编辑回显
	 * 
	 * 
	 * */
	DataWebChannel selectById(Long id);
	
	
	/**
	 * 分页查询加搜索
	 * 
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	List<Map<String, Object>> selectPage(@Param("page") Page page,@Param("param") DataWebChannelParam param,Long id);
	
	/**
	 * 根据网站id查询
	 * 
	 * 
	 * */
	List<DataWebChannel> selectBySiteId(Long id);
	
	
	/**
	 * 查询媒体UUID、媒体名称、网站名称
	 * @param DataWebChannelVo
	 * @return
	 */
	DataWebChannelVo selectWebSiteName(DataWebChannelVo DataWebChannelVo);
	
	 /**
     * 判断新增频道是否存在
     * @param dataWebMedia
     * @return
     */
    List<Map> selectChannel(DataWebChannel data);

    /**
     * 判断频道总数是否存在
     * @param dataWebMedia
     * @return
     */
    List<Map> selectAllChannel(DataWebChannel data);
    
    /**
	 * 分页查询加搜索
	 * 
	 * 
	 * */
	List<Map<String, Object>> selectPageByApi(int page,int limit,Long id);

}
