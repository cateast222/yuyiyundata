package com.yuyiyun.YYdata.modular.dataconfig.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yuyiyun.YYdata.core.common.constant.factory.ConstantFactory;
import com.yuyiyun.YYdata.core.common.exception.BizExceptionEnum;
import com.yuyiyun.YYdata.core.common.page.LayuiPageFactory;
import com.yuyiyun.YYdata.core.common.page.LayuiPageInfo;
import com.yuyiyun.YYdata.core.shiro.ShiroKit;
import com.yuyiyun.YYdata.core.util.HtmlUtil;
import com.yuyiyun.YYdata.core.util.ToolsUtil;
import com.yuyiyun.YYdata.modular.dataconfig.entity.DataDict;
import com.yuyiyun.YYdata.modular.dataconfig.mapper.DataDictMapper;
import com.yuyiyun.YYdata.modular.dataconfig.wrapper.DataDictWrapper;
import com.yuyiyun.YYdata.modular.system.entity.Dict;

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
public class DataDictService extends ServiceImpl<DataDictMapper, DataDict> {

	/**
	 * 分页查询列表
	 * 
	 * @param dict
	 * @return
	 */
	public LayuiPageInfo selectPageList(DataDict dataDict, int limit, int page) {
		// 封装Page对象
		Page<Map<String, Object>> pageContext = new Page<Map<String, Object>>(page, limit);
		// 设置排序
		pageContext.setAsc("sort");
		// 获取分页数据
		List<Map<String, Object>> list = this.baseMapper.selectPage(pageContext, dataDict);
		list = new DataDictWrapper(list).wrap();
		// 结果数据封装
		pageContext.setRecords(list);
		// 返回数据
		return LayuiPageFactory.createPageInfo(pageContext);
	}

	/**
	 * 新增数据
	 * 
	 * @param DataDict
	 */
	public int addDataDict(DataDict dataDict) {

		// 1、创建排重查询对象
		QueryWrapper<DataDict> queryWrapper = new QueryWrapper<DataDict>()
				.and(i -> i.eq("type", dataDict.getType()).eq("code", dataDict.getCode()));
		// 2、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DD_EXISTED);
		}
		// 判断创建者是否存在并且设定
		if (ToolUtil.isEmpty(dataDict.getCreator())) {
			dataDict.setCreator(ShiroKit.getUser().getAccount());
		}
		dataDict.setUpdateTime(new Date());
		return baseMapper.insert(dataDict);
	}

	/**
	 * 修改数据
	 * 
	 * @param DataDict
	 */
	public int editDataDict(DataDict dataDict) {
		// 1、转换得到旧对象
		DataDict oldEntity = getOldEntity(dataDict);
		// 2、转换得到新对象
		DataDict newEntity = getEntity(dataDict);
		ToolUtil.copyProperties(newEntity, oldEntity);
		// 1、创建排重查询对象
		QueryWrapper<DataDict> queryWrapper = new QueryWrapper<DataDict>()
				.and(i -> i.eq("type", newEntity.getType()).eq("code", newEntity.getCode()))
				.and(i -> i.ne("uuid", newEntity.getUuid()));
		// 2、判断是否重复
		int count = this.count(queryWrapper);
		if (count > 0) {
			throw new ServiceException(BizExceptionEnum.DD_EXISTED);
		}
		// 设定更新时间
		newEntity.setUpdateTime(new Date());
		return baseMapper.updateById(newEntity);
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
	 * 获取对应的表单配置
	 * 
	 * @param uuid
	 * @return
	 */
	public List<String> getFroms(Long uuid) {
		String datas = getById(uuid).getDatas();
		List<String> arrayList = new ArrayList<String>();
		if (ToolsUtil.isNotEmpty(datas)) {
			arrayList = HtmlUtil.getJXNodes(datas, "//div[contains(@class,'layui-form-item')]");
		}
		return arrayList;
	}
	
	/**
	 * 获取对应的表单属性name
	 * 
	 * @param uuid
	 * @return
	 */
	public List<String> getFroms2Name(Long uuid) {
		String datas = getById(uuid).getDatas();
		List<String> arrayList = new ArrayList<String>();
		if (ToolsUtil.isNotEmpty(datas)) {
			arrayList = HtmlUtil.getJXNodes(datas, "//div[@class='layui-form-item']//*[@name]/@name");
		}
		return arrayList;
	}
	
	

	/**
	 * 删除数据
	 * 
	 * @param uuid
	 * @return
	 */
	public int deleteById(String uuid) {
		this.remove(new QueryWrapper<DataDict>().eq("parent_uuid", uuid));
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
	 * @param dataDict
	 * @return
	 */
	public List<Map<String, Object>> selectListByEQ(DataDict dataDict) {
		// 创建查询对象
		QueryWrapper<DataDict> queryWrapper = new QueryWrapper<DataDict>();

		// 添加查询条件
		if (ToolUtil.isNotEmpty(dataDict.getCode())) {
			queryWrapper.and(i -> i.eq("code", dataDict.getCode()));
		}
		if (ToolUtil.isNotEmpty(dataDict.getDatas())) {
			queryWrapper.and(i -> i.eq("datas", dataDict.getDatas()));
		}
		if (ToolUtil.isNotEmpty(dataDict.getName())) {
			queryWrapper.and(i -> i.eq("name", dataDict.getName()));
		}
		if (ToolUtil.isNotEmpty(dataDict.getParentUuid())) {
			queryWrapper.and(i -> i.eq("parent_uuid", dataDict.getParentUuid()));
		}
		if (ToolUtil.isNotEmpty(dataDict.getSort())) {
			queryWrapper.and(i -> i.eq("sort", dataDict.getSort()));
		}
		if (ToolUtil.isNotEmpty(dataDict.getRemark())) {
			queryWrapper.and(i -> i.eq("remark", dataDict.getRemark()));
		}
		if (ToolUtil.isNotEmpty(dataDict.getState())) {
			queryWrapper.and(i -> i.eq("state", dataDict.getState()));
		}
		if (ToolUtil.isNotEmpty(dataDict.getSummary())) {
			queryWrapper.and(i -> i.eq("summary", dataDict.getSummary()));
		}
		if (ToolUtil.isNotEmpty(dataDict.getType())) {
			queryWrapper.and(i -> i.eq("type", dataDict.getType()));
		}
		if (ToolUtil.isNotEmpty(dataDict.getUuid())) {
			queryWrapper.and(i -> i.eq("uuid", dataDict.getUuid()));
		}

		// 设置排序
		queryWrapper.orderByAsc("sort", "update_time", "create_time");

		// 返回查询结果
		return this.listMaps(queryWrapper);
	}

	private DataDict getOldEntity(DataDict dataDict) {
		return this.getById(dataDict.getUuid());
	}

	private DataDict getEntity(DataDict dataDict) {
		DataDict entity = new DataDict();
		ToolUtil.copyProperties(dataDict, entity);
		return entity;
	}
	
	/**
	 * @describe 系统字典（数据字典类型）
	 * @author duhao
	 * @date 2020年5月23日下午11:27:13
	 * @return
	 */
	public List<Dict> getSysDict2DataDicType() {
		return ConstantFactory.me().findInDict("数据字典类型");
	}
}
