package com.yuyiyun.YYdata.modular.datasource.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.datasource.entity.DataApiauth;
import com.yuyiyun.YYdata.modular.datasource.mapper.DataApiauthMapper;

import cn.stylefeng.roses.core.util.ToolUtil;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author duhao
 * @since 2020-04-07
 */
@Service
public class DataApiauthService extends ServiceImpl<DataApiauthMapper, DataApiauth> {

	/**
	 * 分页查询列表
	 * 
	 * @param dataAuth
	 * @return
	 */
	public LayuiPageInfo selectPageList(DataApiauth apiDataAuth, int limit, int page) {
		// 封装Page对象
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);
		// 设置排序
		pageContext.setAsc("uuid");
		// 分页查询数据
		List<Map<String, Object>> list = this.baseMapper.selectPage(pageContext, apiDataAuth);
		// 结果数据封装
		pageContext.setRecords(list);
		// 返回数据结果
		return LayuiPageFactory.createPageInfo(pageContext);
	}

	/**
	 * 新增数据
	 * 
	 * @param dataAuth
	 */
	public int addDataAuth(DataApiauth apiDataAuth) {
		// 判断创建者是否存在并且设定
		if (ToolUtil.isEmpty(apiDataAuth.getCreator())) {
			apiDataAuth.setCreator(ShiroKit.getUser().getAccount());
		}
		apiDataAuth.setUpdateTime(new Date());
		return baseMapper.insert(apiDataAuth);
	}

	/**
	 * 修改数据
	 * 
	 * @param dataAuth
	 */
	public int editDataAuth(DataApiauth apiDataAuth) {
		// 设定更新时间
		apiDataAuth.setUpdateTime(new Date());
		return baseMapper.updateById(apiDataAuth);
	}

	/**
	 * 查看详情
	 * 
	 * @param uuid
	 * @return
	 */
	public DataApiauth getDetailsById(String uuid) {
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
	 * 根据精确查询
	 * 
	 * @param DataApiauth
	 * @param columns
	 * @return
	 */
	public List<Map<String, Object>> getEQsByApi(DataApiauth apiDataAuth, String... columns) {
		// 设置查询字段
		String[] cs = { "uuid", "sys_user", "data_source", "validity" };
		if (ToolUtil.isNotEmpty(columns) && columns.length > 0) {
			cs = columns;
		}
		// 获取查询结果,返回结果
		return this.selectListByEQ(apiDataAuth, cs);
	}

	public int addDataAuths(List<Long> ids, List<String> chsnames, DataApiauth apiDataAuth) {
		int ret = 0;
		for (int i = 0, length = ids.size(); i < length; i++) {
			DataApiauth ada = new DataApiauth();
			ada.setDataSource(ids.get(i));
			ada.setSysUser(apiDataAuth.getSysUser());
			List<Map<String, Object>> list = this.selectListByEQ(ada, "uuid");
			int size = list.size();
			if (size == 0) {
				ada.setDataSourceChsName(chsnames.get(i));
				ada.setValidity(apiDataAuth.getValidity());
				ret += this.addDataAuth(ada);
			} else if (size == 1) {
				Map<String, Object> map = list.get(0);
				DataApiauth byId = this.getById((Serializable) map.get("uuid"));
				byId.setValidity(apiDataAuth.getValidity());
				ret += this.editDataAuth(byId);
			}
		}
		return ret;
	}

	private List<Map<String, Object>> selectListByEQ(DataApiauth apiDataAuth, String... columns) {
		// 创建查询对象
		QueryWrapper<DataApiauth> queryWrapper = new QueryWrapper<DataApiauth>().select(columns);
		// 添加查询条件
		if (ToolsUtil.isNotEmpty(apiDataAuth.getCreator())) {
			queryWrapper.eq("creator", apiDataAuth.getCreator());
		}
		if (ToolsUtil.isNotEmpty(apiDataAuth.getDataSource())) {
			queryWrapper.eq("data_source", apiDataAuth.getDataSource());
		}
		if (ToolsUtil.isNotEmpty(apiDataAuth.getDataSourceChsName())) {
			queryWrapper.eq("data_source_chsname", apiDataAuth.getDataSourceChsName());
		}
		if (ToolsUtil.isNotEmpty(apiDataAuth.getLevel())) {
			queryWrapper.eq("level", apiDataAuth.getLevel());
		}
		if (ToolsUtil.isNotEmpty(apiDataAuth.getRemark())) {
			queryWrapper.eq("remark", apiDataAuth.getRemark());
		}
		if (ToolsUtil.isNotEmpty(apiDataAuth.getState())) {
			queryWrapper.eq("state", apiDataAuth.getState());
		}
		if (ToolsUtil.isNotEmpty(apiDataAuth.getSysUser())) {
			queryWrapper.eq("sys_user", apiDataAuth.getSysUser());
		}
		if (ToolsUtil.isNotEmpty(apiDataAuth.getUuid())) {
			queryWrapper.eq("uuid", apiDataAuth.getUuid());
		}
		// 设置排序
		queryWrapper.orderByAsc("update_time", "create_time");
		// 返回查询结果
		return this.listMaps(queryWrapper);
	}
}
