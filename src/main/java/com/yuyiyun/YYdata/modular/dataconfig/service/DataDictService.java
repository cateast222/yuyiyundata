package com.yuyiyun.YYdata.modular.dataconfig.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;
import com.yuyiyun.YYdata.modular.dataconfig.mapper.DataDictMapper;

import cn.stylefeng.roses.core.util.ToolUtil;

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
		//封装Page对象
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);
		//设置排序
		pageContext.setAsc("sort");
		//获取分页数据
		List<Map<String, Object>> list = this.baseMapper.selectPage(pageContext, DataDict);
		//结果数据封装
		pageContext.setRecords(list);
		//返回数据
		return LayuiPageFactory.createPageInfo(pageContext);
	}

	/**
	 * 新增数据
	 * 
	 * @param DataDict
	 */
	public int addDataDict(DataDict dataDict) {
		//判断创建者是否存在并且设定
		if (ToolUtil.isEmpty(dataDict.getCreator())) {
			dataDict.setCreator(ShiroKit.getUser().getAccount());
		}
		return baseMapper.insert(dataDict);
	}

	/**
	 * 修改数据
	 * 
	 * @param DataDict
	 */
	public int editDataDict(DataDict dataDict) {
		//设定更新时间
		dataDict.setUpdateTime(new Date());
		return baseMapper.updateById(dataDict);
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
