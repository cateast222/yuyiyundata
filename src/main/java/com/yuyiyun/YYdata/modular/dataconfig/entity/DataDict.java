package com.yuyiyun.YYdata.modular.dataconfig.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yuyiyun.YYdata.core.util.HtmlUtil;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "DataDict", description = "数据字典实体")
@TableName("data_dict")
public class DataDict implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * uuid
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private Long uuid;

	/**
	 * 类型
	 */
	@ApiModelProperty(value = "类型")
	@TableField("type")
	private String type;

	/**
	 * 上级uuid
	 */
	@ApiModelProperty(value = "上级UUID")
	@TableField("parent_uuid")
	private Long parentUuid;

	/**
	 * 编号
	 */
	@ApiModelProperty(value = "编号")
	@TableField("code")
	private String code;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	@TableField("name")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	@TableField("summary")
	private String summary;

	/**
	 * 数据
	 */
	@ApiModelProperty(value = "数据")
	@TableField("datas")
	private String datas;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	@TableField("sort")
	private Integer sort;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	@TableField("remark")
	private String remark;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "状态")
	@TableField("state")
	private String state;

	/**
	 * 创建者
	 */
	@ApiModelProperty(value = "创建者")
	@TableField("creator")
	private String creator;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	@TableField(value = "update_time", fill = FieldFill.UPDATE)
	private Date updateTime;

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public void setType(String type) {
		this.type = HtmlUtil.convertTag(type);
	}

	public void setParentUuid(Long parentUuid) {
		this.parentUuid = parentUuid;
	}

	public void setCode(String code) {
		this.code = HtmlUtil.convertTag(code);
	}

	public void setName(String name) {
		this.name = HtmlUtil.convertTag(name);
	}

	public void setSummary(String summary) {
		this.summary = HtmlUtil.convertTag(summary);
	}

	public void setDatas(String datas) {
		this.datas = HtmlUtil.convertTag(datas);
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setRemark(String remark) {
		this.remark = HtmlUtil.convertTag(remark);
	}

	public void setState(String state) {
		this.state = HtmlUtil.convertTag(state);
	}

	public void setCreator(String creator) {
		this.creator = HtmlUtil.convertTag(creator);
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
