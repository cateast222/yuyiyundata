package com.yuyiyun.YYdata.modular.datasource.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;
import com.yuyiyun.YYdata.modular.datasource.mapper.DataSourceMapper;
import com.yuyiyun.YYdata.modular.datasource.model.param.DataSourceParam;
import com.yuyiyun.YYdata.modular.datasource.wrapper.DataSourceWrapper;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNews;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewsService;
import com.yuyiyun.YYdata.modular.newspaper.service.DataNewspaperService;

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
public class DataSourceService extends ServiceImpl<DataSourceMapper, DataSource> {
	@Autowired
	DataNewspaperService dataNewspaperService;
	@Autowired
	DataNewsService dataNewsService;

	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> listFromNewspaper(Page page, String condition) {
		return this.baseMapper.listFromNewspaper(page, condition);
	}

	public DataSource addOrEdit(DataSourceParam param) {
		// 1、根据UUID检索判断是否存在
		DataSource byId = this.getById(param.getUuid());
		// 2、若存在执行更新，不存在执行新增
		DataSource dataSource = ToolUtil.isEmpty(byId) ? add(param) : update(param);
		// 3、数据返回
		return dataSource;
	}

	public DataSource add(DataSourceParam param) {
		// 1、创建排重查询对象
		QueryWrapper<DataSource> queryWrapper = new QueryWrapper<DataSource>()
				.and(i -> i.eq("platform", param.getPlatform()).eq("website_name", param.getWebsiteName()))
				.or(i -> i.eq("platform", param.getPlatform()).eq("website_url", param.getWebsiteUrl()));
		// 2、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DS_EXISTED);
		}
		// 3、对象转换
		DataSource entity = getEntity(param);
		if (ToolUtil.isEmpty(entity.getWebsiteName())) {
			entity.setWebsiteName(entity.getChsName());
		}
		if (ToolUtil.isEmpty(entity.getOrgName())) {
			entity.setOrgName(entity.getChsName());
		}
		if (ToolUtil.isEmpty(entity.getCreator())) {
			entity.setCreator(ShiroKit.getUser().getAccount());
		}
		// 4、数据存储
		this.save(entity);
		// 5、数据返回
		return entity;
	}

	public void delete(DataSourceParam param) {
		// 1、删除对应新闻数据
		dataNewsService.remove(new QueryWrapper<DataNews>().eq("data_source", param.getUuid()));
		// 2、删除电子报纸
		dataNewspaperService.remove(new QueryWrapper<DataNewspaper>().eq("data_source", param.getUuid()));
		// 3、删除数据源
		this.removeById(getKey(param));
	}

	public DataSource update(DataSourceParam param) {
		// 1、转换得到旧对象
		DataSource oldEntity = getOldEntity(param);
		// 2、转换得到新对象
		DataSource newEntity = getEntity(param);
		ToolUtil.copyProperties(newEntity, oldEntity);
		// 3、创建排重查询对象
		QueryWrapper<DataSource> queryWrapper = new QueryWrapper<DataSource>()
				.and(i -> i.eq("platform", param.getPlatform()).eq("website_name", param.getWebsiteName()))
				.or(i -> i.eq("platform", param.getPlatform()).eq("website_url", param.getWebsiteUrl()));
		// 4、判断电子报纸是否重复
		int count = this.count(queryWrapper);
		if (count > 1) {
			throw new ServiceException(BizExceptionEnum.DS_EXISTED);
		}
		// 5、更新数据
		newEntity.setUpdateTime(new Date());
		if (ToolUtil.isEmpty(newEntity.getWebsiteName())) {
			newEntity.setWebsiteName(newEntity.getChsName());
		}
		if (ToolUtil.isEmpty(newEntity.getOrgName())) {
			newEntity.setOrgName(newEntity.getChsName());
		}
		// 6、数据存储
		this.updateById(newEntity);
		// 7、数据回调
		return newEntity;
	}

	public LayuiPageInfo findPageBySpec(DataSourceParam param) {
		// 1、获取分页对象
		Page pageContext = getPageContext();
		// 2、创建查询对象
		QueryWrapper<DataSource> queryWrapper = new QueryWrapper<>();
		// 3、判断并模糊检索名称
		if (ToolUtil.isNotEmpty(param.getCondition())) {
			queryWrapper.and(i -> i.like("website_name", param.getCondition()))
					.or(i -> i.like("chs_name", param.getCondition()))
					.or(i -> i.like("org_name", param.getCondition()));
		}
		// 4、判断并检索平台
		if (ToolUtil.isNotEmpty(param.getPlatform())) {
			queryWrapper.and(i -> i.eq("platform", param.getPlatform()));
		}
		// 5、判断并检索状态
		if (ToolUtil.isNotEmpty(param.getState())) {
			queryWrapper.and(i -> i.eq("state", param.getState()));
		}
		// 6、根据创建时间进行排序
		pageContext.setDesc("update_time");
		// 7、封装分页数据
		Page<Map<String, Object>> pageMaps = (Page<Map<String, Object>>) this.pageMaps(pageContext, queryWrapper);
//		IPage page = this.page(pageContext, queryWrapper);
		Page<Map<String, Object>> wrap = new DataSourceWrapper(pageMaps).wrap();
		return LayuiPageFactory.createPageInfo(wrap);
	}

	private DataSource getOldEntity(DataSourceParam param) {
		return this.getById(getKey(param));
	}

	private Serializable getKey(DataSourceParam param) {
		return param.getUuid();
	}

	private DataSource getEntity(DataSourceParam param) {
		DataSource entity = new DataSource();
		ToolUtil.copyProperties(param, entity);
		return entity;
	}

	@SuppressWarnings("rawtypes")
	private Page getPageContext() {
		return LayuiPageFactory.defaultPage();
	}
}
