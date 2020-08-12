package com.yuyiyun.YYdata.modular.newsweb.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannelEntity;
import com.yuyiyun.YYdata.modular.newsweb.mapper.DataWebChannelMapper;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebChannelParam;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebChannelVo;


/**
 * @author WuXiangDong
 * @author TangJianRong
 *  频道列表服务层
 */
@Service
public class DataWebChannelService  extends ServiceImpl<DataWebChannelMapper, DataWebChannelEntity>{
	@Resource
	private DataWebChannelMapper channelMapper;



	/**
	 * 根据网站id查询
	 * @return
	 */
	public List<DataWebChannelEntity> selectBySiteId(String id){
		List<DataWebChannelEntity> list = channelMapper.selectBySiteId(id);
		return list;
	}


	/**
	 * 查询媒体UUID、媒体名称、网站名称
	 * @param datavo
	 * @return
	 */
	public DataWebChannelVo selectWebSiteName(DataWebChannelVo datavo){
		DataWebChannelVo siteName = channelMapper.selectWebSiteName(datavo);
		return siteName;

	}

	/**
	 * 分页查询
	 * 
	 * */
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> selectPage(Page page, DataWebChannelParam param,String id) {
		return this.channelMapper.selectPage(page, param,id);
	}

	/**
	 * 排重
	 * 
	 * */
	private boolean checkcolumnrepeat(DataWebChannelEntity dataWebChannelEntity) {
		QueryWrapper<DataWebChannelEntity> wrapper = new QueryWrapper();
		wrapper.eq("sub_module_url", dataWebChannelEntity.getSubModuleUrl()).or()
		  				.eq("module_name", dataWebChannelEntity.getModuleName())
		  				.eq("data_web_website", dataWebChannelEntity.getDataWebWebsite());
		
		List<DataWebChannelEntity> dataChannelList = channelMapper.selectList(wrapper);
		//遍历出mouduleList和subModuleUrl
		List<String> moduleNameList = dataChannelList.stream().map(DataWebChannelEntity::getModuleName).collect(Collectors.toList());
		List<String> subModuleUrlList = dataChannelList.stream().map(DataWebChannelEntity::getSubModuleUrl).collect(Collectors.toList());
		//判断网站名称是否在集合内
		if (moduleNameList.contains(dataWebChannelEntity.getModuleName()) ||
				subModuleUrlList.contains(dataWebChannelEntity.getSubModuleUrl())) {
			//重复
			return false;
		} else {
			//不重复
			return true;
		}
	}


	/**
	 * 根据频道id查询
	 * 
	 * */
	public DataWebChannelEntity selectById(String id) {
		DataWebChannelEntity list = channelMapper.selectById(id);
		return list;
	}

	/**
	 * 删除
	 * 
	 * */
	public int delete(String id) {
		return channelMapper.delete(id);
	}

	/**
	 * 根据媒体id删除
	 * 
	 * */
	public int deleteByMediaId(String id) {
		return channelMapper.deleteByMediaId(id);
	}

	/**
	 * 根据网站id删除
	 * 
	 * */
	public int deleteBySiteId(String id) {
		return channelMapper.deleteBySiteId(id);
	}

	/**
	 * 批量删除
	 * 
	 * */
	public int deleteBacth(String[] id) {
		return channelMapper.deleteBacth(id);
	}

	/**
	 * 批量更新
	 * 
	 * */
	public int updateBacth(String[] id) {
		return channelMapper.updateBacth(id);
	}

	/**
	 * 修改
	 * 
	 * */
	public int update(DataWebChannelEntity data) {
		return channelMapper.update(data);
	}

	/**
	 * 新增，判断是否重复
	 * 
	 * */
	public int add(DataWebChannelEntity data) {
		//媒体频道重复
		if (checkcolumnrepeat(data)) {
			return channelMapper.add(data);
		} else {
			return 0;
		}
	}

	/**
     * 判断新增频道是否存在
     * @param p
     * @return
     */
    public List<Map> selectChannel(DataWebChannelEntity data){
        List<Map> s = channelMapper.selectChannel(data);
        return s;
    }
    /**
     * 判断新增频道，网址是否存在
     * @param p
     * @return
     */
    public List<Map> selectAllChannel(DataWebChannelEntity data){
        List<Map> s = channelMapper.selectAllChannel(data);
        return s;
    }
}