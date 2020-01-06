package com.yuyiyun.YYdata.modular.wgelenewsdata.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.wgelenewsdata.entity.WgEleNewsData;
import com.yuyiyun.YYdata.modular.wgelenewsdata.mapper.WgEleNewsDataMapper;
import com.yuyiyun.YYdata.modular.wgelenewsdata.model.param.WgEleNewsDataParam;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * 闻歌电子报纸数据服务实现类
 * 
 * @author duhao
 *
 */
@Service
public class WgEleNewsDataService extends ServiceImpl<WgEleNewsDataMapper, WgEleNewsData> {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LayuiPageInfo findPageBySpec(WgEleNewsDataParam param) {
		Page pageContext = getPageContext();
		QueryWrapper<WgEleNewsData> dsitQueryWrapper = new QueryWrapper<>();
		dsitQueryWrapper.and(i -> i.eq("dsi_uuid", param.getDsiUuid()));

		if (ToolUtil.isNotEmpty(param.getCondition())) {
			dsitQueryWrapper.and(i -> i.like("title", param.getCondition()));
		}
		if (ToolUtil.isNotEmpty(param.getState())) {
			dsitQueryWrapper.and(i -> i.eq("state", param.getState()));
		}

		pageContext.setAsc("paper_count");

		IPage page = this.page(pageContext, dsitQueryWrapper);
		return LayuiPageFactory.createPageInfo(page);
	}

	public WgEleNewsData add(WgEleNewsDataParam param) {
		// 判断是否已经存在同数据源网站名称或同数据源网站地址
		QueryWrapper<WgEleNewsData> dsitQueryWrapper = new QueryWrapper<>();
		dsitQueryWrapper.and(i -> i.eq("url", param.getUrl()));
		int count = this.count(dsitQueryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.WEN_EXISTED);
		}
		WgEleNewsData entity = getEntity(param);
		String uuid = UUID.randomUUID().toString().replaceAll("-", "").toUpperCase();
		entity.setUuid(uuid);
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		entity.setInsertTime(new Date());
		this.save(entity);
		return entity;
	}

	@SuppressWarnings("rawtypes")
	private Page getPageContext() {
		return LayuiPageFactory.defaultPage();
	}

	private WgEleNewsData getEntity(WgEleNewsDataParam param) {
		WgEleNewsData entity = new WgEleNewsData();
		ToolUtil.copyProperties(param, entity);
		return entity;
	}

	/**
	 * :根据日期获取分类归档的数据
	 * 
	 * @param pubTime
	 * @param condition
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<Map<String, Object>> getDateArchive(Page page, String pubTime, String condition) {
		return this.baseMapper.getDateArchive(page, pubTime, condition);
		// TODO Auto-generated method stub

	}

	/**
	 * :获取某份报纸归档的新闻数据
	 * @param dsiUuid
	 * @param pubTime
	 * @param condition
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public LayuiPageInfo getDateNewslist(String dsiUuid, String pubTime, String condition) {
		Page pageContext = getPageContext();
		QueryWrapper<WgEleNewsData> wendQueryWrapper = new QueryWrapper<>();
		wendQueryWrapper.and(i -> i.eq("dsi_uuid", dsiUuid));
		if (ToolUtil.isNotEmpty(condition)) {
			wendQueryWrapper.and(i -> i.like("title", condition));
		}
		if (ToolUtil.isNotEmpty(pubTime)) {
			wendQueryWrapper.and(i -> i.between("pubtime", pubTime + " 00:00:00", pubTime + " 23:59:59"));
		}
		pageContext.setAsc("paper_count");
		IPage page = this.page(pageContext, wendQueryWrapper);
		return LayuiPageFactory.createPageInfo(page);
	}
}
