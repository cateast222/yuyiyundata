package com.yuyiyun.YYdata.modular.newsweb.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.constant.Const;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebChannel;
import com.yuyiyun.YYdata.modular.newsweb.mapper.DataWebChannelMapper;
import com.yuyiyun.YYdata.modular.newsweb.model.param.DataWebChannelParam;
import com.yuyiyun.YYdata.modular.newsweb.vo.DataWebChannelVo;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;


/**
 * @author WuXiangDong
 * @author TangJianRong
 *  频道列表服务层
 */
@Service
public class DataWebChannelService  extends ServiceImpl<DataWebChannelMapper, DataWebChannel>{
	@Resource
	private DataWebChannelMapper channelMapper;



	/**
	 * 根据网站id查询
	 * @return
	 */
	public List<DataWebChannel> selectBySiteId(Long id){
		List<DataWebChannel> list = channelMapper.selectBySiteId(id);
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
	public List<Map<String, Object>> selectPage(Page page, DataWebChannelParam param,Long id) {
		return this.channelMapper.selectPage(page, param,id);
	}

	/**
	 * 排重
	 * 
	 * */
	private boolean checkcolumnrepeat(DataWebChannel dataWebChannelEntity) {
		QueryWrapper<DataWebChannel> wrapper = new QueryWrapper();
		wrapper.eq("sub_module_url", dataWebChannelEntity.getSubModuleUrl()).or()
		  				.eq("module_name", dataWebChannelEntity.getModuleName())
		  				.eq("data_web_website", dataWebChannelEntity.getDataWebWebsite());
		
		List<DataWebChannel> dataChannelList = channelMapper.selectList(wrapper);
		//遍历出mouduleList和subModuleUrl
		List<String> moduleNameList = dataChannelList.stream().map(DataWebChannel::getModuleName).collect(Collectors.toList());
		List<String> subModuleUrlList = dataChannelList.stream().map(DataWebChannel::getSubModuleUrl).collect(Collectors.toList());
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
	public DataWebChannel selectById(Long id) {
		DataWebChannel list = channelMapper.selectById(id);
		return list;
	}

	/**
	 * 删除
	 * 
	 * */
	public int delete(Long id) {
		return channelMapper.delete(id);
	}

	/**
	 * 根据媒体id删除
	 * 
	 * */
	public int deleteByMediaId(Long id) {
		return channelMapper.deleteByMediaId(id);
	}

	/**
	 * 根据网站id删除
	 * 
	 * */
	public int deleteBySiteId(Long id) {
		return channelMapper.deleteBySiteId(id);
	}

	/**
	 * 批量删除
	 * 
	 * */
	public int deleteBacth(Long[] id) {
		return channelMapper.deleteBacth(id);
	}

	/**
	 * 批量更新
	 * 
	 * */
	public int updateBacth(Long[] id) {
		return channelMapper.updateBacth(id);
	}

	/**
	 * 修改
	 * 
	 * */
	public int update(DataWebChannel data) {
		return channelMapper.update(data);
	}

	/**
	 * 新增，判断是否重复
	 * 
	 * */
	public int add(DataWebChannel data) {
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
    public List<Map> selectChannel(DataWebChannel data){
        List<Map> s = channelMapper.selectChannel(data);
        return s;
    }
    /**
     * 判断新增频道，网址是否存在
     * @param p
     * @return
     */
    public List<Map> selectAllChannel(DataWebChannel data){
        List<Map> s = channelMapper.selectAllChannel(data);
        return s;
    }
    
    
    /**
     * 接口新增
     * @param p
     * @return
     */
    public DataWebChannel insert(DataWebChannelParam param) {
    	QueryWrapper<DataWebChannel> wrapper = new QueryWrapper();
    	wrapper.and(i -> i.eq("sub_module_url", param.getSubModuleUrl()));
    	int count = this.count(wrapper);
    	if (count > 0) {
			throw new ServiceException(BizExceptionEnum.WEN_EXISTED);
		}
    	DataWebChannel data = getEntity(param);
    	String createBy = ShiroKit.getUser().getName();
    	data.setCreateBy(createBy);
    	data.setCreateTime(new Date());
    	channelMapper.add(data);
		return data;
    }
    
    /**
     * 修改接口
     * @param p
     * @return
     */
    public DataWebChannel updateByApi(DataWebChannelParam param) {
    	QueryWrapper<DataWebChannel> wrapper = new QueryWrapper();
    	wrapper.and(i -> i.eq("module_name", param.getModuleName()));
    	wrapper.and(i -> i.eq("sub_module_url", param.getSubModuleUrl()));
    	int count = this.count(wrapper);
    	if (count > 0) {
			throw new ServiceException(BizExceptionEnum.WEN_EXISTED);
		}
    	DataWebChannel data = getEntity(param);
    	String updateBy = ShiroKit.getUser().getName();
    	data.setUpdateBy(updateBy);
    	data.setUpdateTime(new Date());
    	channelMapper.update(data);
		return data;
    	
    }
    
    private DataWebChannel getEntity(DataWebChannelParam param) {
    	DataWebChannel entity = new DataWebChannel();
		ToolUtil.copyProperties(param, entity);
		return entity;
	}
    
    
    /**
     * 接口删除
     * @param p
     * @return
     */
    public void deleteChannel(Long id){
    	this.removeById(id);
    	
    }
    
    /**
     * 分页查询接口
     * @param 
     * @return
     */
    public LayuiPageInfo selectPageByApi(int page,int limit,Long id) {
    	if (ToolsUtil.isNotEmpty(limit) && limit > 50) {
			limit = Const.API_MAX_PAGESIZE;
		}
    	// 创建分页查询对象
    	Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);
    	// 设置排序
    	pageContext.setAsc("uuid");
    	// 分页查询数据
    	List<Map<String, Object>> list = channelMapper.selectPageByApi(page, limit, id);
    	pageContext.setRecords(list);
		// 封装并返回结果
		return LayuiPageFactory.createPageInfo(pageContext);
    }
}