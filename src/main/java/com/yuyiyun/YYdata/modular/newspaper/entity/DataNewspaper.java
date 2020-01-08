package com.yuyiyun.YYdata.modular.newspaper.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author duhao
 * @since 2020-01-02
 */
@Data
@TableName(value = "data_newspaper")
public class DataNewspaper implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "uuid",type = IdType.ID_WORKER)
	private Long uuid;

	/**
	 * 原名称
	 */
	@TableField("org_name")
	private String orgName;

	/**
	 * 中文名称
	 */
	@TableField("chs_name")
	private String chsName;

	/**
	 * 全名称（+日期）
	 */
	@TableField("full_name")
	private String fullName;

	/**
	 * 发布日期
	 */
	@TableField("publish")
	private Date publish;

	/**
	 * 类型
	 */
	@TableField("type")
	private String type;

	/**
	 * URL
	 */
	@TableField("url")
	private String url;

	/**
	 * 封面
	 */
	@TableField("cover")
	private String cover;

	/**
	 * 数据源
	 */
	@TableField("data_source")
	private Long dataSource;

	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;

	/**
	 * 状态
	 */
	@TableField("state")
	private Integer state;

	/**
	 * 创建者
	 */
	@TableField(value = "creator")
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
}
