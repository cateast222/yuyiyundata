package com.yuyiyun.YYdata.modular.newspaper.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yuyiyun.YYdata.core.util.HtmlTagUtil;

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
	@TableId(value = "uuid", type = IdType.ID_WORKER)
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
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
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
	 * 数据提供方
	 */
	@TableField("provider")
	private String provider;

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

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	public void setOrgName(String orgName) {
		this.orgName = HtmlTagUtil.convertTag(orgName);
	}

	public void setChsName(String chsName) {
		this.chsName = HtmlTagUtil.convertTag(chsName);
	}

	public void setFullName(String fullName) {
		this.fullName = HtmlTagUtil.convertTag(fullName);
	}

	public void setPublish(Date publish) {
		this.publish = publish;
	}

	public void setType(String type) {
		this.type = HtmlTagUtil.convertTag(type);
	}

	public void setUrl(String url) {
		this.url = HtmlTagUtil.convertTag(url);
	}

	public void setCover(String cover) {
		this.cover = HtmlTagUtil.convertTag(cover);
	}

	public void setDataSource(Long dataSource) {
		this.dataSource = dataSource;
	}

	public void setProvider(String provider) {
		this.provider = HtmlTagUtil.convertTag(provider);
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
