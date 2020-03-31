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
 * 数据字典表
 * </p>
 *
 * @author duhao
 * @since 2020-03-26
 */
@Data
@TableName("data_dict")
public class DataDict implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * uuid
	 */
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private Long uuid;

	/**
	 * 类型
	 */
	@TableField("type")
	private String type;

	/**
	 * 上级uuid
	 */
	@TableField("parent_uuid")
	private Long parentUuid;

	/**
	 * 编号
	 */
	@TableField("code")
	private String code;

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
	 * 数据
	 */
	@TableField("datas")
	private String datas;

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

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public void setType(String type) {
		this.type = HtmlTagUtil.convertTag(type);
	}

	public void setParentUuid(Long parentUuid) {
		this.parentUuid = parentUuid;
	}

	public void setCode(String code) {
		this.code = HtmlTagUtil.convertTag(code);
	}

	public void setName(String name) {
		this.name = HtmlTagUtil.convertTag(name);
	}

	public void setSummary(String summary) {
		this.summary = HtmlTagUtil.convertTag(summary);
	}

	public void setDatas(String datas) {
		this.datas = HtmlTagUtil.convertTag(datas);
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
