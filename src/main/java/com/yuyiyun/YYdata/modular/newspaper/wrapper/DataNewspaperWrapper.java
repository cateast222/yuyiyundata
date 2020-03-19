package com.yuyiyun.YYdata.modular.newspaper.wrapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;

import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;

public class DataNewspaperWrapper extends BaseControllerWrapper {

	public DataNewspaperWrapper(Map<String, Object> single) {
		super(single);
	}

	public DataNewspaperWrapper(List<Map<String, Object>> multi) {
		super(multi);
	}

	public DataNewspaperWrapper(Page<Map<String, Object>> page) {
		super(page);
	}

	public DataNewspaperWrapper(PageResult<Map<String, Object>> pageResult) {
		super(pageResult);
	}

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		map.put("stateName", ConstantFactory.me().getDictsByName("新闻状态", (String) map.get("state")));
	}

}
