package com.yuyiyun.YYdata.modular.newspaper.service;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNews;
import com.yuyiyun.YYdata.modular.newspaper.mapper.DataNewsMapper;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewsParam;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author duhao
 * @since 2020-01-03
 */
@Service
public class DataNewsService extends ServiceImpl<DataNewsMapper, DataNews> {

	public DataNews add(DataNewsParam param) {
		// 1、创建查询对象，根据电子报纸和URL
		QueryWrapper<DataNews> queryWrapper = new QueryWrapper<DataNews>()
				.and(i -> i.eq("data_newspaper", param.getDataNewspaper()).eq("url", param.getUrl()));
		// 2、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DN_EXISTED);
		}
		// 3、对象转换
		DataNews entity = getEntity(param);
		// 4、数据存储
		this.save(entity);
		// 5、数据回调
		return entity;
	}

	public void delete(DataNewsParam param) {
		// 1、删除报纸数据
		this.removeById(getKey(param));
	}

	public DataNews update(DataNewsParam param) {
		// 1、获取旧对象
		DataNews oldEntity = getOldEntity(param);
		// 2、转换得到新对象
		DataNews newEntity = getEntity(param);
		ToolUtil.copyProperties(newEntity, oldEntity);
		// 3、创建查询对象，根据电子报纸和URL
		QueryWrapper<DataNews> queryWrapper = new QueryWrapper<DataNews>()
				.and(i -> i.eq("data_newspaper", param.getDataNewspaper()).eq("url", param.getUrl()));
		// 4、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DN_EXISTED);
		}
		// 5、更新数据
		newEntity.setUpdateTime(new Date());
		this.updateById(newEntity);
		// 6、数据回
		return newEntity;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LayuiPageInfo findPageBySpec(DataNewsParam param) {
		// 1、获取分页对象
		Page<DataNews> pageContext = getPageContext();
		// 2、创建查询对象
		QueryWrapper<DataNews> queryWrapper = new QueryWrapper<>();
		// 3、判断并检索电子报纸全名称
		if (ToolUtil.isNotEmpty(param.getCondition())) {
			queryWrapper.and(i -> i.like("title", param.getCondition()));
		}
		// 4、判断并检索电子报纸发布日期
		if (ToolUtil.isNotEmpty(param.getDataNewspaper())) {
			queryWrapper.and(i -> i.eq("data_newspaper", param.getDataNewspaper()));
		}
		// 5、根据创建时间进行排序
		pageContext.setAsc("paper_count");
		// 6、封装分页数据
		IPage page = this.page(pageContext, queryWrapper);
		return LayuiPageFactory.createPageInfo(page);
	}

	@SuppressWarnings("rawtypes")
	private Page getPageContext() {
		return LayuiPageFactory.defaultPage();
	}

	private DataNews getOldEntity(DataNewsParam param) {
		return this.getById(getKey(param));
	}

	private DataNews getEntity(DataNewsParam param) {
		DataNews entity = new DataNews();
		ToolUtil.copyProperties(param, entity);
		return entity;
	}

	private Serializable getKey(DataNewsParam param) {
		return param.getUuid();
	}

	

}
