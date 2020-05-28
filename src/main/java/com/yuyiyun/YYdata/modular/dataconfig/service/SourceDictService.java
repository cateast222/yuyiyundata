package com.yuyiyun.YYdata.modular.dataconfig.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.util.JwtTokenUtil;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataConfig;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;
import com.yuyiyun.YYdata.modular.dataconfig.entity.SourceDict;
import com.yuyiyun.YYdata.modular.dataconfig.mapper.SourceDictMapper;
import com.yuyiyun.YYdata.modular.dataconfig.wrapper.SourceDictWrapper;
import com.yuyiyun.YYdata.modular.system.entity.User;
import com.yuyiyun.YYdata.modular.system.service.UserService;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * <p>
 * 数据字典表 服务实现类
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
@Service
public class SourceDictService extends ServiceImpl<SourceDictMapper, SourceDict> {
	@Autowired
	private DataConfigService configService;
	@Autowired
	UserService userService;
	@Autowired
	DataDictService dataDictService;

	/**
	 * 分页查询列表
	 * 
	 * @param dict
	 * @return
	 */
	public LayuiPageInfo selectPageList(SourceDict sourceDict, int limit, int page) {
		// 封装Page对象
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);
		// 设置排序
		pageContext.setAsc("uuid");
		// 获取分页数据
		List<Map<String, Object>> list = this.baseMapper.selectPage(pageContext, sourceDict);
		list = new SourceDictWrapper(list).wrap();
		// 结果数据封装
		pageContext.setRecords(list);
		// 返回数据
		return LayuiPageFactory.createPageInfo(pageContext);
	}
	
	public Map<String, Object> getDetails(Long dataSource, Long dataDict) {
		List<String> froms2Name = dataDictService.getFroms2Name(dataDict);
		
		QueryWrapper<DataConfig> queryWrapper = new QueryWrapper<DataConfig>().eq("data_source", dataSource).in("`key`", froms2Name);
		List<DataConfig> list = configService.list(queryWrapper);
		HashMap<String,Object> map = new HashMap<String, Object>();
		for (DataConfig dataConfig : list) {
			map.put(dataConfig.getKey(), dataConfig.getValue());
		}
		return map;
	}

	/**
	 * 分页查询列表
	 * 
	 * @param dict
	 * @return
	 */
	public LayuiPageInfo selPageSourceDict(SourceDict sourceDict, DataDict dataDict, int limit, int page) {
		// 封装Page对象
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);
		// 设置排序
		pageContext.setAsc("uuid");
		// 获取分页数据
		List<Map<String, Object>> list = this.baseMapper.selPageSourceDict(pageContext, sourceDict, dataDict);
		list = new SourceDictWrapper(list).wrap();
		// 结果数据封装
		pageContext.setRecords(list);
		// 返回数据
		return LayuiPageFactory.createPageInfo(pageContext);
	}

	/**
	 * 新增数据
	 * 
	 * @param SourceDict
	 */
	public int addSourceDict(SourceDict sourceDict) {

		// 1、创建排重查询对象
		QueryWrapper<SourceDict> queryWrapper = new QueryWrapper<SourceDict>()
				.and(i -> i.eq("data_source", sourceDict.getDataSource()).eq("data_dict", sourceDict.getDataDict()));
		// 2、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DD_EXISTED);
		}
		return baseMapper.insert(sourceDict);
	}

	/**
	 * 修改数据
	 * 
	 * @param SourceDict
	 */
	public int editSourceDict(SourceDict sourceDict) {
		// 1、转换得到旧对象
		SourceDict oldEntity = getOldEntity(sourceDict);
		// 2、转换得到新对象
		SourceDict newEntity = getEntity(sourceDict);
		ToolUtil.copyProperties(newEntity, oldEntity);
		// 1、创建排重查询对象
		QueryWrapper<SourceDict> queryWrapper = new QueryWrapper<SourceDict>()
				.and(i -> i.eq("data_source", newEntity.getDataSource()).eq("data_dict", newEntity.getDataDict()))
				.and(i -> i.ne("uuid", newEntity.getUuid()));
		// 2、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DD_EXISTED);
		}
		return baseMapper.updateById(newEntity);
	}

	public boolean editSourceDict(Map<String, Object> map, Long dataSource, Long dataDict) {
		ArrayList<DataConfig> arrayList = new ArrayList<DataConfig>();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			QueryWrapper<DataConfig> queryWrapper = new QueryWrapper<DataConfig>().eq("data_source", dataSource)
					.eq("`key`", entry.getKey());
			DataConfig one = configService.getOne(queryWrapper);
			if (ToolsUtil.isNotEmpty(one)) {
				one.setValue(entry.getValue().toString());
				one.setUpdateTime(new Date());
				arrayList.add(one);
			} else {
				DataConfig dataConfig = new DataConfig();
				dataConfig.setCreateTime(new Date());
				if (ToolsUtil.isNotEmpty(ShiroKit.getUser())) {
					dataConfig.setCreator(ShiroKit.getUser().getAccount());
				} else if (ToolsUtil.isNotEmpty(JwtTokenUtil.getUsernameFromRequest())) {
					User user = userService.getById(Long.parseLong(JwtTokenUtil.getUsernameFromRequest()));
					dataConfig.setCreator(user.getAccount());
				}
				dataConfig.setDataDict(dataDict);
				dataConfig.setDataSource(dataSource);
				dataConfig.setKey(entry.getKey());
				dataConfig.setUpdateTime(new Date());
				dataConfig.setValue(entry.getValue().toString());
				arrayList.add(dataConfig);
			}
		}
		return configService.saveOrUpdateBatch(arrayList);
	}
	
	/**
	 * 删除数据
	 * 
	 * @param uuid
	 * @return
	 */
	public int deleteById(String uuid) {
		SourceDict sourceDict = getById(uuid);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data_dict", sourceDict.getDataDict());
		map.put("data_source", sourceDict.getDataSource());
		configService.removeByMap(map);
		return baseMapper.deleteById(uuid);
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	public int deleteBatch(List<Long> ids) {
		for (Long uuid : ids) {
			SourceDict sourceDict = getById(uuid);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data_dict", sourceDict.getDataDict());
			map.put("data_source", sourceDict.getDataSource());
			configService.removeByMap(map);
		}
		return baseMapper.deleteBatchIds(ids);
	}

	private SourceDict getOldEntity(SourceDict sourceDict) {
		return this.getById(sourceDict.getUuid());
	}

	private SourceDict getEntity(SourceDict sourceDict) {
		SourceDict entity = new SourceDict();
		ToolUtil.copyProperties(sourceDict, entity);
		return entity;
	}

}
