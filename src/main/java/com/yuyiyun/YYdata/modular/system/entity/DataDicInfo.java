package com.yuyiyun.YYdata.modular.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 数据字典信息类
 * 
 * @describe 关联data_dic_info
 * @author duhao
 * @date 2019年9月12日下午4:34:20
 */
@TableName(value = "data_dic_info")
public class DataDicInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private String uuid;
	@TableField("type")
	private Integer type;
	@TableField("key")
	private String key;
	@TableField("value")
	private String value;
	@TableField("summary")
	private String summary;
	@TableField("remark")
	private String remark;
	@TableField("state")
	private Integer state;
	@TableField(value = "creator",fill = FieldFill.INSERT)
	private String creator;
	@TableField(value = "create_time", fill = FieldFill.INSERT)
	private Date createTime;
	@TableField(value = "update_time", fill = FieldFill.UPDATE)
	private Date updateTime;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "DataDicInfo [uuid=" + uuid + ", type=" + type + ", key=" + key + ", value=" + value + ", summary="
				+ summary + ", remark=" + remark + ", state=" + state + ", creator=" + creator + ", createTime="
				+ createTime + ", updateTime=" + updateTime + "]";
	}
}
