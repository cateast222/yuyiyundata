package com.yuyiyun.YYdata.modular.dataconfig.service;

import java.util.Date;
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
import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;

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
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);
		pageContext.setAsc("sort");
		List<Map<String, Object>> list = this.baseMapper.selectPage(pageContext, dataConfig);
		pageContext.setRecords(list);
		return LayuiPageFactory.createPageInfo(pageContext);
	}

	/**
	 * 新增数据
	 * 
	 * @param dataConfig
	 */
	public int addDataConfig(DataConfig dataConfig) {
		// 1、创建排重查询对象
		QueryWrapper<DataConfig> queryWrapper = new QueryWrapper<DataConfig>()
				.and(i -> i.eq("`key`", dataConfig.getKey()).eq("data_source", dataConfig.getDataSource()));
		// 2、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DC_EXISTED);
		}

		// 判断创建者是否存在并且设定
		if (ToolUtil.isEmpty(dataConfig.getCreator())) {
			dataConfig.setCreator(ShiroKit.getUser().getAccount());
		}
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

}