package com.yuyiyun.YYdata.modular.system.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.system.entity.DataSourceInfo;
import com.yuyiyun.YYdata.modular.system.mapper.DataSourceInfoMapper;
import com.yuyiyun.YYdata.modular.system.model.params.DataSourceInfoParam;
import com.yuyiyun.YYdata.modular.system.model.result.DataSourceInfoResult;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * 数据配置信息服务实现类
 * @author duhao
 *
 */
@Service
public class DataSourceInfoService extends ServiceImpl<DataSourceInfoMapper, DataSourceInfo> {
	/**
	 * 新增
	 *
	 * @author stylefeng
	 * @Date 2019-03-13
	 */
	public void add(DataSourceInfoParam param) {

		// 判断是否已经存在同数据源网站名称或同数据源网站地址
		QueryWrapper<DataSourceInfo> dictQueryWrapper = new QueryWrapper<>();
		dictQueryWrapper.eq("website_name", param.getWebsiteName()).or().eq("website_url", param.getWebsiteUrl());
		List<DataSourceInfo> list = this.list(dictQueryWrapper);
		if (list != null && list.size() > 0) {
			throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
		}

		DataSourceInfo entity = getEntity(param);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		entity.setUuid(uuid);
		entity.setName(entity.getWebsiteName());
		this.save(entity);
	}

	/**
	 * 删除
	 *
	 * @author stylefeng
	 * @Date 2019-03-13
	 */
	public void delete(DataSourceInfoParam param) {
		this.removeById(getKey(param));
	}

	/**
	 * 更新
	 *
	 * @author stylefeng
	 * @Date 2019-03-13
	 */
	public void update(DataSourceInfoParam param) {
		DataSourceInfo oldEntity = getOldEntity(param);
		DataSourceInfo newEntity = getEntity(param);
		ToolUtil.copyProperties(newEntity, oldEntity);

		// 判断编码是否重复
		QueryWrapper<DataSourceInfo> wrapper = new QueryWrapper<DataSourceInfo>()
				.and(i -> i.eq("website_name", newEntity.getWebsiteName()).or().eq("website_url", newEntity.getWebsiteUrl()))
				.and(i -> i.ne("uuid", newEntity.getUuid()));
		int dicts = this.count(wrapper);
		if (dicts > 0) {
			throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
		}
		newEntity.setUpdateTime(new Date());
		this.updateById(newEntity);
	}

	/**
	 * 查询单条数据，Specification模式
	 *
	 * @author stylefeng
	 * @Date 2019-03-13
	 */
	public DataSourceInfoResult findBySpec(DataSourceInfoParam param) {
		return null;
	}

	/**
	 * 查询列表，Specification模式
	 *
	 * @author stylefeng
	 * @Date 2019-03-13
	 */
	public List<DataSourceInfoResult> findListBySpec(DataSourceInfoParam param) {
		return null;
	}

	/**
	 * 查询分页数据，Specification模式
	 *
	 * @author stylefeng
	 * @Date 2019-03-13
	 */
	public LayuiPageInfo findPageBySpec(DataSourceInfoParam param) {
		Page pageContext = getPageContext();
		QueryWrapper<DataSourceInfo> objectQueryWrapper = new QueryWrapper<>();
		if (ToolUtil.isNotEmpty(param.getCondition())) {
			objectQueryWrapper.and(i -> i.like("website_name", param.getCondition()).or().like("website_url", param.getCondition()));
		}
//		if (ToolUtil.isNotEmpty(param.getStatus())) {
//			objectQueryWrapper.and(i -> i.eq("status", param.getStatus()));
//		}
//		if (ToolUtil.isNotEmpty(param.getSystemFlag())) {
//			objectQueryWrapper.and(i -> i.eq("system_flag", param.getSystemFlag()));
//		}

		pageContext.setAsc("create_time");

		IPage page = this.page(pageContext, objectQueryWrapper);
		return LayuiPageFactory.createPageInfo(page);
	}

	private Serializable getKey(DataSourceInfoParam param) {
		return param.getUuid();
	}

	private Page getPageContext() {
		return LayuiPageFactory.defaultPage();
	}

	private DataSourceInfo getOldEntity(DataSourceInfoParam param) {
		return this.getById(getKey(param));
	}

	private DataSourceInfo getEntity(DataSourceInfoParam param) {
		DataSourceInfo entity = new DataSourceInfo();
		ToolUtil.copyProperties(param, entity);
		return entity;
	}

}
