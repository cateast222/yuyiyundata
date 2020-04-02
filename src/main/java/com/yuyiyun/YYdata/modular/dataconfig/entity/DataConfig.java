package com.yuyiyun.YYdata.modular.dataconfig.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuyiyun.YYdata.core.util.HtmlTagUtil;

import lombok.Data;

/**
 * <p>
 * 数据配置表
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
@Data
@TableName("data_config")
public class DataConfig implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * uuid
	 */
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private Long uuid;

	/**
	 * 数据源
	 */
	@TableField("data_source")
	private Long dataSource;

	/**
	 * 数据字典
	 */
	@TableField("data_dict")
	private Long dataDict;

	/**
	 * 配置K值
	 */
	@TableField("`key`")
	private String key;

	/**
	 * 配置V值
	 */
	@TableField("`value`")
	private String value;

	/**
	 * 名称
	 */
	@TableField("name")
	private String name;

	/**
	 * 描述
	 */
	@TableField("summary")
	private String summary;

	/**
	 * 排序
	 */
	@TableField("sort")
	private Integer sort;

	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;

	/**
	 * 状态
	 */
	@TableField("state")
	private String state;

	/**
	 * 创建者
	 */
	@TableField("creator")
	private String creator;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(value = "update_time", fill = FieldFill.UPDATE)
	private Date updateTime;

	public void setValue(String value) {
		this.value = HtmlTagUtil.convertTag(value);
	}

	public void setKey(String key) {
		this.key = HtmlTagUtil.convertTag(key);
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public void setDataSource(Long dataSource) {
		this.dataSource = dataSource;
	}

	public void setDataDict(Long dataDict) {
		this.dataDict = dataDict;
	}

	public void setName(String name) {
		this.name = HtmlTagUtil.convertTag(name);
	}

	public void setSummary(String summary) {
		this.summary = HtmlTagUtil.convertTag(summary);
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setRemark(String remark) {
		this.remark = HtmlTagUtil.convertTag(remark);
	}

	public void setState(String state) {
		this.state = HtmlTagUtil.convertTag(state);
	}

	public void setCreator(String creator) {
		this.creator = HtmlTagUtil.convertTag(creator);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
