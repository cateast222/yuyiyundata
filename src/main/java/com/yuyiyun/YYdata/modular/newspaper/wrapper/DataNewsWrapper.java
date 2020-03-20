package com.yuyiyun.YYdata.modular.newspaper.wrapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;

import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;

public class DataNewsWrapper extends BaseControllerWrapper {

	public DataNewsWrapper(Map<String, Object> single) {
        super(single);
    }

    public DataNewsWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public DataNewsWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public DataNewsWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		map.put("state_name", ConstantFactory.me().getDictsByName("新闻状态",(String) map.get("state")));
		map.put("language_name", ConstantFactory.me().getDictsByName("语种",(String) map.get("language")));
		map.put("provider_name", ConstantFactory.me().getDictsByName("数据源提供方",(String) map.get("provider")));
	}

}
