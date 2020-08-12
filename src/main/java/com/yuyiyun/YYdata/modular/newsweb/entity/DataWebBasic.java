package com.yuyiyun.YYdata.modular.newsweb.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

public class DataWebBasic implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private Long uuid;
	
	/**
	 * 状态
	 */
	@TableField("state")
	private String state;
	
	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;
	
	/**
	 * 创建者
	 */
	@TableField("create_by")
	private String createBy;
	
	/**
	 * 创建时间
	 */
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;
	
	/**
	 * 更新者
	 */
	@TableField("update_by")
	private String updateBy;
	
	

	public Long getUuid() {
		return uuid;
	}

	public void setUuid(Long uuid) {
		this.uuid = uuid;
	}

	/**
	 * 更新时间
	 */
	@TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
	

	public void setState(String state) {
		this.state = state;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}


	public String getState() {
		return state;
	}

	public String getRemark() {
		return remark;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	
}
