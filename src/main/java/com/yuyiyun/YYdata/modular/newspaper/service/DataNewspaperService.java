package com.yuyiyun.YYdata.modular.newspaper.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;
import com.yuyiyun.YYdata.modular.newspaper.mapper.DataNewspaperMapper;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewspaperParam;

import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author duhao
 * @since 2020-01-02
 */
@Service
public class DataNewspaperService extends ServiceImpl<DataNewspaperMapper, DataNewspaper> {

	public DataNewspaper add(DataNewspaperParam param) {
		QueryWrapper<DataNewspaper> queryWrapper = new QueryWrapper<DataNewspaper>()
				.and(i -> i.eq("data_source", param.getDataSource()).eq("publish", param.getPublish()))
				.or(i -> i.eq("data_source", param.getDataSource()).eq("url", param.getUrl()));
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DNP_EXISTED);
		}
		DataNewspaper entity = getEntity(param);
		entity.setFullName(new SimpleDateFormat(entity.getChsName() + "-yyyy-MM-dd").format(entity.getPublish()));
		this.save(entity);
		return entity;
	}

	public void delete(DataNewspaperParam param) {
		// 1、删除对应新闻数据

		// 2、删除电子报纸
		this.removeById(getKey(param));
	}

	public void update(DataNewspaperParam param) {
		// 1、转换得到旧对象
		DataNewspaper oldEntity = getOldEntity(param);
		// 2、转换得到新对象
		DataNewspaper newEntity = getEntity(param);
		ToolUtil.copyProperties(newEntity, oldEntity);
		// 3、判断电子报纸是否重复
		QueryWrapper<DataNewspaper> queryWrapper = new QueryWrapper<DataNewspaper>()
				.and(i -> i.eq("data_source", param.getDataSource()).eq("publish", param.getPublish()))
				.or(i -> i.eq("data_source", param.getDataSource()).eq("url", param.getUrl()));
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DNP_EXISTED);
		}
		// 4、更新数据
		newEntity.setUpdateTime(new Date());
		newEntity.setFullName(
				new SimpleDateFormat(newEntity.getChsName() + "-yyyy-MM-dd").format(newEntity.getPublish()));
		this.updateById(newEntity);
	}

	public LayuiPageInfo findPageBySpec(DataNewspaperParam param) {
		// 1、获取分页对象
		Page pageContext = getPageContext();
		// 2、创建查询对象
		QueryWrapper<DataNewspaper> queryWrapper = new QueryWrapper<>();
		// 3、判断并检索电子报纸全名称
		if (ToolUtil.isNotEmpty(param.getCondition())) {
			queryWrapper.and(i -> i.like("full_name", param.getCondition()));
		}
		// 4、判断并检索电子报纸发布日期
		if (ToolUtil.isNotEmpty(param.getPublish())) {
			queryWrapper.and(i -> i.eq("publish", param.getPublish()));
		}
		// 5、根据创建时间进行排序
		pageContext.setAsc("create_time");
		// 6、封装分页数据
		IPage page = this.page(pageContext, queryWrapper);
		return LayuiPageFactory.createPageInfo(page);
	}

	public ResponseData isExist(String dsiUuid, String pubTime) {
		QueryWrapper<DataNewspaper> queryWrapper = new QueryWrapper<DataNewspaper>()
				.and(i -> i.eq("data_source", dsiUuid).eq("publish", pubTime+" 00:00:00"));
		int count = this.count(queryWrapper);
		if (count > 0) {
			return ResponseData.success(0, "该日期发布的电子报纸已经存在！", false);
		} else {
			return ResponseData.success(1, "该日期发布的电子报纸可以创建！", true);
		}
	}

	private DataNewspaper getOldEntity(DataNewspaperParam param) {
		return this.getById(getKey(param));
	}

	private Serializable getKey(DataNewspaperParam param) {
		return param.getUuid();
	}

	private DataNewspaper getEntity(DataNewspaperParam param) {
		DataNewspaper entity = new DataNewspaper();
		ToolUtil.copyProperties(param, entity);
		return entity;
	}

	@SuppressWarnings("rawtypes")
	private Page getPageContext() {
		return LayuiPageFactory.defaultPage();
	}

}
