package com.yuyiyun.YYdata.modular.dataconfig.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataConfig;
import com.yuyiyun.YYdata.modular.dataconfig.mapper.DataConfigMapper;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * <p>
 * 数据配置表 服务类
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
@Service
public class DataConfigService extends ServiceImpl<DataConfigMapper, DataConfig> {

	/**
	 * 分页查询列表
	 * 
	 * @param dataConfig
	 * @return
	 */
	public LayuiPageInfo selectPageList(DataConfig dataConfig, int limit, int page) {
		// 创建分页查询对象
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);

		// 设置排序
		pageContext.setAsc("sort");

		// 分页查询数据
		List<Map<String, Object>> list = this.baseMapper.selectPage(pageContext, dataConfig);

		// 设置分页数据
		pageContext.setRecords(list);

		// 封装并返回结果
		return LayuiPageFactory.createPageInfo(pageContext);
	}

	/**
	 * 新增数据
	 * 
	 * @param dataConfig
	 */
	public int addDataConfig(DataConfig dataConfig) {
		// 创建排重查询对象
		QueryWrapper<DataConfig> queryWrapper = new QueryWrapper<DataConfig>()
				.and(i -> i.eq("`key`", dataConfig.getKey()).eq("data_source", dataConfig.getDataSource()));

		// 判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DC_EXISTED);
		}

		// 判断创建者是否存在并且设定
		if (ToolUtil.isEmpty(dataConfig.getCreator())) {
			dataConfig.setCreator(ShiroKit.getUser().getAccount());
		}

		// 返回插入结果
		return baseMapper.insert(dataConfig);
	}

	/**
	 * 修改数据
	 * 
	 * @param dataConfig
	 */
	public int editDataConfig(DataConfig dataConfig) {
		// 设定更新时间
		dataConfig.setUpdateTime(new Date());

		// 返回更新结果
		return baseMapper.updateById(dataConfig);
	}

	/**
	 * 查看详情
	 * 
	 * @param uuid
	 * @return
	 */
	public DataConfig getDetailsById(String uuid) {
		return baseMapper.selectById(uuid);
	}

	/**
	 * 删除数据
	 * 
	 * @param uuid
	 * @return
	 */
	public int deleteById(String uuid) {
		return baseMapper.deleteById(uuid);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteBatch(List<Long> ids) {
		return baseMapper.deleteBatchIds(ids);
	}

	

	/**
	 * 
	 * @param dsUuid
	 * @param key
	 * @return
	 */
	public Map<String, String> getValues(Long dsUuid, String key) {
		// 封装查询条件
		DataConfig dataConfig = new DataConfig();
		dataConfig.setDataSource(dsUuid);
		dataConfig.setKey(key);

		// 获取查询结果
		List<Map<String, Object>> list = this.selectListByEQ(dataConfig);

		// 创建返回对象
		Map<String, String> retMap = new HashMap<String, String>();

		// 循环处理数据
		for (Map<String, Object> map : list) {
			// 存储Key，Value数据
			retMap.put((String) map.get("key"), (String) map.get("value"));
		}

		// 返回结果
		return retMap;
	}
	
	/**
	 * 根据精确查询
	 * 
	 * @param dataConfig
	 * @return
	 */
	public List<Map<String, Object>> selectListByEQ(DataConfig dataConfig) {
		// 创建查询对象
		QueryWrapper<DataConfig> queryWrapper = new QueryWrapper<DataConfig>();

		// 设置查询条件
		if (ToolUtil.isNotEmpty(dataConfig.getDataSource())) {
			queryWrapper.eq("data_source", dataConfig.getDataSource());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getKey())) {
			queryWrapper.eq("`key`", dataConfig.getKey());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getCreator())) {
			queryWrapper.eq("creator", dataConfig.getCreator());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getDataDict())) {
			queryWrapper.eq("data_dict", dataConfig.getDataDict());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getName())) {
			queryWrapper.eq("name", dataConfig.getName());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getRemark())) {
			queryWrapper.eq("remark", dataConfig.getRemark());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getState())) {
			queryWrapper.eq("state", dataConfig.getState());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getSummary())) {
			queryWrapper.eq("summary", dataConfig.getSummary());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getUuid())) {
			queryWrapper.eq("uuid", dataConfig.getUuid());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getValue())) {
			queryWrapper.eq("`value`", dataConfig.getValue());
		}

		// 设置排序
		queryWrapper.orderByAsc("sort", "update_time", "create_time");

		// 返回查询结果
		return this.listMaps(queryWrapper);
	}

}