package com.yuyiyun.YYdata.modular.dataconfig.service;

import java.io.Serializable;
import java.util.Date;
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
import com.yuyiyun.YYdata.modular.dataconfig.mapper.DataConfigMapper;
import com.yuyiyun.YYdata.modular.system.entity.User;
import com.yuyiyun.YYdata.modular.system.service.UserService;

import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;

/**
 * <p>
 * 数据配置表 服务类
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
@Service
public class DataConfigService extends ServiceImpl<DataConfigMapper, DataConfig> {
	@Autowired
	UserService userService;

	/**
	 * 分页查询列表
	 * 
	 * @param dataConfig
	 * @return
	 */
	public LayuiPageInfo selectPageList(DataConfig dataConfig, int limit, int page) {
		// 创建分页查询对象
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);

		// 设置排序
		pageContext.setAsc("sort");

		// 分页查询数据
		List<Map<String, Object>> list = this.baseMapper.selectPage(pageContext, dataConfig);

		// 设置分页数据
		pageContext.setRecords(list);

		// 封装并返回结果
		return LayuiPageFactory.createPageInfo(pageContext);
	}

	/**
	 * 新增数据
	 * 
	 * @param dataConfig
	 */
	public int addDataConfig(DataConfig dataConfig) {
		// 创建排重查询对象
		QueryWrapper<DataConfig> queryWrapper = new QueryWrapper<DataConfig>()
				.and(i -> i.eq("`key`", dataConfig.getKey()).eq("data_source", dataConfig.getDataSource()));

		// 判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DC_EXISTED);
		}

		// 判断创建者是否存在并且设定
		if (ToolsUtil.isNotEmpty(dataConfig.getCreator())) {
		} else if (ToolsUtil.isNotEmpty(ShiroKit.getUser())) {
			dataConfig.setCreator(ShiroKit.getUser().getAccount());
		} else if (ToolsUtil.isNotEmpty(JwtTokenUtil.getUsernameFromRequest())) {
			User user = userService.getById(Long.parseLong(JwtTokenUtil.getUsernameFromRequest()));
			dataConfig.setCreator(user.getAccount());
		}
		dataConfig.setUpdateTime(new Date());

		// 返回插入结果
		return baseMapper.insert(dataConfig);
	}

	/**
	 * 修改数据
	 * 
	 * @param dataConfig
	 */
	public int editDataConfig(DataConfig dataConfig) {
		// 设定更新时间
		dataConfig.setUpdateTime(new Date());

		// 返回更新结果
		return baseMapper.updateById(dataConfig);
	}

	/**
	 * 查看详情
	 * 
	 * @param uuid
	 * @return
	 */
	public DataConfig getDetailsById(String uuid) {
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

	/**
	 * 根据动态精确查询
	 * 
	 * @param dataConfig
	 * @param columns
	 * @return
	 */
	public List<Map<String, Object>> getEQsByApi(DataConfig dataConfig, String... columns) {
		// 设置查询字段
		String[] cs = { "`key`", "`value`" };
		if (ToolUtil.isNotEmpty(columns) && columns.length > 0) {
			cs = columns;
		}
		// 获取查询结果,返回结果
		return this.selectListByEQ(dataConfig, cs);
	}

	/**
	 * 设置数据配置信息
	 * @param dataSource
	 * @param key
	 * @param value
	 * @return
	 */
	public DataConfig putDataAuths(Long dataSource, String key, String value) {
		// 创建查询对象并进行赋值
		DataConfig dc = new DataConfig();
		dc.setDataSource(dataSource);
		dc.setKey(key);
		// 获取查询结果
		List<Map<String, Object>> list = this.selectListByEQ(dc, "uuid");
		int size = list.size();
		// 判断记录是否存在
		if (size == 0) {
			// 记录不存在，属性赋值并进行新增
			dc.setName(key);
			dc.setValue(value);
			// 执行新增
			this.addDataConfig(dc);
		} else if (size == 1) {
			// 记录存在，获取记录主键
			Map<String, Object> map = list.get(0);
			// 通过主键获取完整记录数据
			DataConfig byId = this.getById((Serializable) map.get("uuid"));
			// 修改记录属性值并更新
			byId.setValue(value);
			// 执行修改
			this.editDataConfig(byId);
			//成功后赋值给返回对象
			dc = byId;
		}
		// 返回结果
		return dc;
	}

	private List<Map<String, Object>> selectListByEQ(DataConfig dataConfig, String... columns) {
		// 创建查询对象并指定查询字段
		QueryWrapper<DataConfig> queryWrapper = new QueryWrapper<DataConfig>().select(columns);
		// 设置查询条件
		if (ToolUtil.isNotEmpty(dataConfig.getDataSource())) {
			queryWrapper.eq("data_source", dataConfig.getDataSource());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getKey())) {
			queryWrapper.eq("`key`", dataConfig.getKey());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getCreator())) {
			queryWrapper.eq("creator", dataConfig.getCreator());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getDataDict())) {
			queryWrapper.eq("data_dict", dataConfig.getDataDict());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getName())) {
			queryWrapper.eq("name", dataConfig.getName());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getRemark())) {
			queryWrapper.eq("remark", dataConfig.getRemark());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getState())) {
			queryWrapper.eq("state", dataConfig.getState());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getSummary())) {
			queryWrapper.eq("summary", dataConfig.getSummary());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getUuid())) {
			queryWrapper.eq("uuid", dataConfig.getUuid());
		}
		if (ToolUtil.isNotEmpty(dataConfig.getValue())) {
			queryWrapper.eq("`value`", dataConfig.getValue());
		}
		// 设置排序
		queryWrapper.orderByAsc("sort", "update_time", "create_time");
		// 返回查询结果
		return this.listMaps(queryWrapper);
	}

}