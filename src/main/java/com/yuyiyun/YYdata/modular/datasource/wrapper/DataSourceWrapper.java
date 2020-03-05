package com.yuyiyun.YYdata.modular.datasource.wrapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;

import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;

public class DataSourceWrapper extends BaseControllerWrapper {

	public DataSourceWrapper(Map<String, Object> single) {
        super(single);
    }

    public DataSourceWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public DataSourceWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public DataSourceWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		map.put("stateName", ConstantFactory.me().getDictsByName("数据源状态",(String) map.get("state")));
		map.put("platformName", ConstantFactory.me().getDictsByName("数据源平台",(String) map.get("platform")));
		map.put("countryName", ConstantFactory.me().getDictsByName("国家地区",(String) map.get("country")));
		map.put("proxyName", ConstantFactory.me().getDictsByName("境区",(String) map.get("proxy")));
		map.put("languageName", ConstantFactory.me().getDictsByName("语种",(String) map.get("language")));
		map.put("encodedName", ConstantFactory.me().getDictsByName("字符集",(String) map.get("encoded")));
		map.put("providerName", ConstantFactory.me().getDictsByName("数据源提供方",(String) map.get("provider")));
	}

}
