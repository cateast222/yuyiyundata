package com.yuyiyun.YYdata.core.common.page;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * 分页结果的封装(for Layui Table)
 *
 * @author duhao
 * @Date 2019年1月25日22:07:36
 */
@Data
public class LayuiPageInfo {

    private Integer code = 0;

    private String msg = "请求成功";

    @SuppressWarnings("rawtypes")
	private List data;

    private long count;

	/**
	 * 
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getDataMapValues(String key) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) this.data;
		StringBuffer sbf = new StringBuffer();
		for (Map<String, Object> map : list) {
			sbf.append(map.get(key)+",");
		}
		return sbf.toString();
	}

}
