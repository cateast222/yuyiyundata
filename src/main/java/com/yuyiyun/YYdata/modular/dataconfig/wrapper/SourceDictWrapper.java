package com.yuyiyun.YYdata.modular.dataconfig.wrapper;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;

import cn.stylefeng.roses.core.base.warpper.BaseControllerWrapper;
import cn.stylefeng.roses.kernel.model.page.PageResult;

public class SourceDictWrapper extends BaseControllerWrapper{

	public SourceDictWrapper(Map<String, Object> single) {
        super(single);
    }

    public SourceDictWrapper(List<Map<String, Object>> multi) {
        super(multi);
    }

    public SourceDictWrapper(Page<Map<String, Object>> page) {
        super(page);
    }

    public SourceDictWrapper(PageResult<Map<String, Object>> pageResult) {
        super(pageResult);
    }

	@Override
	protected void wrapTheMap(Map<String, Object> map) {
		map.put("type_name", ConstantFactory.me().getDictsByName("数据字典类型",(String) map.get("type")));
	}

}
