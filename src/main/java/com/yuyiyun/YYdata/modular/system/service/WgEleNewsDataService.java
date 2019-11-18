package com.yuyiyun.YYdata.modular.system.service;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.system.entity.DataSourceInfo;
import com.yuyiyun.YYdata.modular.system.entity.WgEleNewsData;
import com.yuyiyun.YYdata.modular.system.mapper.WgEleNewsDataMapper;
import com.yuyiyun.YYdata.modular.system.model.params.WgEleNewsDataParam;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * 闻歌电子报纸数据服务实现类
 * 
 * @author duhao
 *
 */
@Service
public class WgEleNewsDataService extends ServiceImpl<WgEleNewsDataMapper, WgEleNewsData> {

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

	private Page getPageContext() {
		return LayuiPageFactory.defaultPage();
	}

}
