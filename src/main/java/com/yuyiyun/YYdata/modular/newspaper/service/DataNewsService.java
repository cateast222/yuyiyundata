package com.yuyiyun.YYdata.modular.newspaper.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.constant.Const;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.util.DateTimeUtil;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.datasource.entity.DataSource;
import com.yuyiyun.YYdata.modular.datasource.service.DataSourceService;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNews;
import com.yuyiyun.YYdata.modular.newspaper.entity.DataNewspaper;
import com.yuyiyun.YYdata.modular.newspaper.mapper.DataNewsMapper;
import com.yuyiyun.YYdata.modular.newspaper.model.param.DataNewsParam;
import com.yuyiyun.YYdata.modular.newspaper.wrapper.DataNewsWrapper;

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

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(DataNewsService.class);

	@Autowired
	DataNewspaperService dataNewspaperService;
	@Autowired
	DataSourceService dataSourceService;

	/**
	 * 批量修改
	 * 
	 * @author duhao
	 * @param dataNews
	 * @return
	 */
	public boolean updateBatch(List<DataNews> dataNews) {
		return this.updateBatchById(dataNews);
	}

	/**
	 * 获取需要推送的数据
	 * 
	 * @author duhao
	 * @param limit
	 * @return
	 */
	public List<Map<String, Object>> getPushDatas(Long userId, Integer limit,Integer start) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (ToolsUtil.isNotEmpty(userId) || limit > 0) {
			list = this.baseMapper.getPushDatas(userId, limit,start);
		}
		return list;
	}

	/**
	 * 新闻数据新增和修改服务
	 * 
	 * @param param 新闻数据输入
	 * @return 更新后的新闻数据
	 */
	public DataNews addOrEdit(DataNewsParam param) {
		if (ToolUtil.isEmpty(param.getUuid())) {
			// 获取对应的电子报纸
			DataNewspaper dataNewspaper = dataNewspaperService.getById(param.getDataNewspaper());
			// 获取对应的报刊数据源
			DataSource dataSource = dataSourceService.getById(dataNewspaper.getDataSource());
			// 修改属性值
			param.setDataSource(dataSource.getUuid());
			param.setLanguage(dataSource.getLanguage());
			param.setPubtime(dataNewspaper.getPublish());
			param.setChsName(dataNewspaper.getChsName());
			param.setOrgName(dataNewspaper.getOrgName());
			param.setProvider(dataNewspaper.getProvider());
			param.setState(dataNewspaper.getState());
			return add(param);
		} else {
			return update(param);
		}
		// 通过UUID查询是否存在数据
//		DataNews byId = this.getById(param.getUuid());
		// 数据存在进行更新操作，不存在进行新增操作
//		DataNews dataNews = ToolUtil.isEmpty(param.getUuid()) ? add(param) : update(param);

		/*
		 * // 数据上传至DAMS,在数据新增和修改时上传 // 判断数据状态，对数据源状态为“启用-1”的数据进行上传 // if
		 * (dataSource.getState().equals("5")) { // log.debug("数据准备上传！UUID为：" +
		 * dataNews.getUuid()); //// System.out.println("数据准备上传！UUID为：" +
		 * dataNews.getUuid()); // // 通过数据UUID获取最新的数据信息 // DataNews byId2 =
		 * this.getById(dataNews.getUuid()); // // 数据上传操作 // boolean b =
		 * DamsApiUpload.dataOnLineToDAMS(byId2); // // 判断是否上传成功 // if (!b) { //
		 * log.debug("数据上传异常！UUID为：" + byId2.getUuid()); // // 上传失败，数据存在异常，修改标记状态为"-1"
		 * // byId2.setState("-1"); // // 数据对象装换（DataNews --> DataNewsParam） //
		 * DataNewsParam dataNewsParam = getEntity(byId2); // // 更新异常数据并返回 // dataNews =
		 * update(dataNewsParam); // } // }
		 */
//		return dataNews;
	}

	public DataNews add(DataNewsParam param) {
		// 1、创建查询对象，根据电子报纸和URL
		QueryWrapper<DataNews> queryWrapper = new QueryWrapper<DataNews>()
				.eq("data_newspaper", param.getDataNewspaper()).eq("url", param.getUrl());
		// 2、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DN_EXISTED);
		}
		// 3、对象转换
		DataNews entity = getEntity(param);

		if (ToolUtil.isEmpty(entity.getCreator())) {
			entity.setCreator(ShiroKit.getUser().getAccount());
		}
		if (ToolUtil.isEmpty(entity.getInsertTime())) {
			entity.setInsertTime(new Date());
		}
		entity.setUpdateTime(new Date());

		// 推送数据处理
		entity.setPushState(1);

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
				.eq("data_newspaper", newEntity.getDataNewspaper()).eq("url", newEntity.getUrl())
				.ne("uuid", newEntity.getUuid());
		// 4、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DN_EXISTED);
		}
		// 5、更新数据
		newEntity.setUpdateTime(new Date());

		// 推送数据处理
		newEntity.setPushState(3);

		this.updateById(newEntity);
		// 6、数据回
		return newEntity;
	}

	@SuppressWarnings({ "unchecked" })
	public LayuiPageInfo findPageBySpec(DataNewsParam param) {
		// 1、获取分页对象
		Page<DataNews> pageContext = getPageContext();
		// 2、创建查询对象
		QueryWrapper<DataNews> queryWrapper = new QueryWrapper<>();
		// 3、判断并检索
		queryWrapper.and(i -> i.like(ToolsUtil.isNotEmpty(param.getCondition()), "title", param.getCondition())
				.eq(ToolsUtil.isNotEmpty(param.getPushState()), "push_state", param.getPushState())
				.eq(ToolsUtil.isNotEmpty(param.getDataNewspaper()), "data_newspaper", param.getDataNewspaper())
				.like(ToolsUtil.isNotEmpty(param.getChsName()), "chs_name", param.getChsName())
				.like(ToolsUtil.isNotEmpty(param.getTitle()), "title", param.getTitle())
				.between(ToolsUtil.isNotEmpty(param.getPubtime()), "pubtime",
						DateTimeUtil.getDayBegin(param.getPubtime()), DateTimeUtil.getDayEnd(param.getPubtime())));
		// 5、根据创建时间进行排序
		pageContext.setAsc("paper_count");
		// 6、封装分页数据
		Page<Map<String, Object>> pageMaps = (Page<Map<String, Object>>) this.pageMaps(pageContext, queryWrapper);
//		IPage page = this.page(pageContext, queryWrapper);
		Page<Map<String, Object>> wrap = new DataNewsWrapper(pageMaps).wrap();
		return LayuiPageFactory.createPageInfo(wrap);
	}

	/**
	 * 分页查询报刊新闻数据
	 * 
	 * @param newspaperId
	 * @param limit
	 * @param page
	 * @return
	 */
	public LayuiPageInfo getArchiveDataNews(Long newspaperId, int limit, int page) {
		if (ToolsUtil.isNotEmpty(limit) && limit > 50) {
			limit = Const.API_MAX_PAGESIZE;
		}
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);
		// 设置排序
		pageContext.setAsc("uuid");
		// 分页查询数据
		List<Map<String, Object>> list = this.baseMapper.selectArchive(pageContext, newspaperId);
		list = new DataNewsWrapper(list).wrap();
		// 设置分页数据
		pageContext.setRecords(list);
		// 封装并返回结果
		return LayuiPageFactory.createPageInfo(pageContext);
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
