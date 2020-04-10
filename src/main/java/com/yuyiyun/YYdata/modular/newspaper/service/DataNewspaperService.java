package com.yuyiyun.YYdata.modular.newspaper.service;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.constant.Const;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;
import com.yuyiyun.YYdata.modular.datasource.service.DataSourceService;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNews;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;
import com.yuyiyun.YYdata.modular.newspaper.mapper.DataNewspaperMapper;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewspaperParam;
import com.yuyiyun.YYdata.modular.newspaper.wrapper.DataNewspaperWrapper;

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
	@Autowired
	DataNewsService dataNewsService;
	@Autowired
	DataSourceService dataSourceService;

	@SuppressWarnings({ "rawtypes" })
	public LayuiPageInfo listFromNews(String publish, String condition) {
		Page pageContext = getPageContext();
		if (!(publish == null || publish.equals(""))) {
			publish += " 00:00:00";
		}
		Page<Map<String, Object>> page = this.baseMapper.listFromNews(pageContext, publish, condition);
		Page<Map<String, Object>> wrap = new DataNewspaperWrapper(page).wrap();
		return LayuiPageFactory.createPageInfo(wrap);
	}

	@SuppressWarnings("rawtypes")
	public LayuiPageInfo listFromNewspaper(Long dataSource, String condition) {
		Page pageContext = LayuiPageFactory.defaultPage();
		Page<Map<String, Object>> page = this.baseMapper.listFromNewspaper(pageContext, dataSource, condition);
		Page<Map<String, Object>> wrap = new DataNewspaperWrapper(page).wrap();
		return LayuiPageFactory.createPageInfo(wrap);
	}

	public DataNewspaper addOrEdit(DataNewspaperParam param) {
		DataSource dataSource = dataSourceService.getById(param.getDataSource());
		param.setChsName(dataSource.getChsName());
		param.setOrgName(dataSource.getOrgName());
		param.setProvider(dataSource.getProvider());
		// 1、根据UUID检索判断是否存在
		DataNewspaper byId = this.getById(param.getUuid());
		// 2、若存在执行更新，不存在执行新增
		DataNewspaper dataNewspaper = ToolUtil.isEmpty(byId) ? add(param) : update(param);
		// 3、数据返回
		return dataNewspaper;
	}

	public DataNewspaper add(DataNewspaperParam param) {
		// 1、创建查询对象，根据数据源、发布时间和URL
		QueryWrapper<DataNewspaper> queryWrapper = new QueryWrapper<DataNewspaper>()
				.eq("data_source", param.getDataSource())
				.and(i -> i.eq("publish", param.getPublish()).or().eq("url", param.getUrl()));
		// 2、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DNP_EXISTED);
		}
		// 3、对象转换
		DataNewspaper entity = getEntity(param);
		entity.setCreator(
				ToolUtil.isEmpty(entity.getCreator()) ? ShiroKit.getUser().getAccount() : entity.getCreator());
		entity.setFullName(entity.getChsName() + new SimpleDateFormat("-yyyy-MM-dd").format(entity.getPublish()));// 《ABC》报-yyyy-MM-dd
		// 4、数据存储
		this.save(entity);
		// 5、回调数据
		return entity;
	}

	public void delete(DataNewspaperParam param) {
		// 1、删除对应新闻数据
		dataNewsService.remove(new QueryWrapper<DataNews>().eq("data_newspaper", param.getUuid()));
		// 2、删除电子报纸
		this.removeById(getKey(param));
	}

	public DataNewspaper update(DataNewspaperParam param) {
		// 1、转换得到旧对象
		DataNewspaper oldEntity = getOldEntity(param);
		// 2、转换得到新对象
		DataNewspaper newEntity = getEntity(param);
		ToolUtil.copyProperties(newEntity, oldEntity);
		// 3、创建查询对象，根据数据源、发布时间和URL
		QueryWrapper<DataNewspaper> queryWrapper = new QueryWrapper<DataNewspaper>()
				.eq("data_source", newEntity.getDataSource())
				.and(i -> i.eq("publish", newEntity.getPublish()).or().eq("url", newEntity.getUrl()))
				.ne("uuid", newEntity.getUuid());
		// 4、判断电子报纸是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DNP_EXISTED);
		}
		// 5、更新数据
		newEntity.setUpdateTime(new Date());
		newEntity.setFullName(
				newEntity.getChsName() + new SimpleDateFormat("-yyyy-MM-dd").format(newEntity.getPublish()));
		this.updateById(newEntity);
		// 6、数据回调
		return newEntity;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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

	public ResponseData isExist(String dsiUuid, String url, String pubTime) {
		// 1、创建查询对象，根据数据源和发布时间
		QueryWrapper<DataNewspaper> queryWrapper = new QueryWrapper<DataNewspaper>()
				.and(i -> i.eq("data_source", dsiUuid).eq("publish", pubTime + " 00:00:00"))
				.or(i -> i.eq("data_source", dsiUuid).eq("url", url));
		// 2、判断电子报纸是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			return ResponseData.success(0, "该日期发布的电子报纸已经存在！", false);
		} else {
			return ResponseData.success(1, "该日期发布的电子报纸可以创建！", true);
		}
	}

	/**
	 * :按归档日期分页查询电子报纸数据
	 * 
	 * @param limit
	 * @param page
	 * @param sysUser
	 * @param archiveDate
	 * @return
	 */
	public LayuiPageInfo getArchiveNewspaper(Long sysUser, String archiveDate, int limit, int page) {
		if (ToolsUtil.isNotEmpty(limit) && limit > 50) {
			limit = Const.API_MAX_PAGESIZE;
		}
		// 创建分页查询对象
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);
		// 设置排序
		pageContext.setAsc("uuid");
		// 分页查询数据
		List<Map<String, Object>> list = this.baseMapper.selectArchive(pageContext, sysUser, archiveDate);
		// 设置分页数据
		pageContext.setRecords(list);
		// 封装并返回结果
		return LayuiPageFactory.createPageInfo(pageContext);
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
