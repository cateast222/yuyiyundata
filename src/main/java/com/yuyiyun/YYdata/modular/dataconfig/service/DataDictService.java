package com.yuyiyun.YYdata.modular.dataconfig.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;
import com.yuyiyun.YYdata.modular.dataconfig.mapper.DataDictMapper;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
@Service
public class DataDictService extends ServiceImpl<DataDictMapper, DataDict> {

	/**
	 * 分页查询列表
	 * 
	 * @param dict
	 * @return
	 */
	public LayuiPageInfo selectPageList(DataDict DataDict, int limit, int page) {
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);
		List<Map<String, Object>> list = this.baseMapper.selectPage(pageContext, DataDict);
		pageContext.setRecords(list);
		return LayuiPageFactory.createPageInfo(pageContext);
	}

	/**
	 * 新增数据
	 * 
	 * @param DataDict
	 */
	public int addDataDict(DataDict DataDict) {
		return baseMapper.insert(DataDict);
	}

	/**
	 * 修改数据
	 * 
	 * @param DataDict
	 */
	public int editDataDict(DataDict DataDict) {
		return baseMapper.updateById(DataDict);
	}

	/**
	 * 查看详情
	 * 
	 * @param uuid
	 * @return
	 */
	public DataDict getDetailsById(String uuid) {
		return baseMapper.selectById(uuid);
	}

	/**
	 * 删除数据
	 * 
	 * @param uuid
	 * @return
	 */
	public int deleteById(String uuid) {
		return baseMapper.deleteById(uuid);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteBatch(List<Long> ids) {
		return baseMapper.deleteBatchIds(ids);
	}

}
