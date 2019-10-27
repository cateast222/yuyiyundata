package com.yuyiyun.YYdata.modular.system.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据源信息类
 * 
 * @describe 关联data_source_info
 * @author duhao
 * @date 2019年9月12日下午4:34:34
 */
public class DataConfigInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String dsiUuid;
	private String ddiKey;
	private String ddiValue;
	private String value;
	private String remark;
	private Integer state;
	private String creator;
	private Date createTime;
	private Date updateTime;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getDsiUuid() {
		return dsiUuid;
	}

	public void setDsiUuid(String dsiUuid) {
		this.dsiUuid = dsiUuid;
	}

	public String getDdiKey() {
		return ddiKey;
	}

	public void setDdiKey(String ddiKey) {
		this.ddiKey = ddiKey;
	}

	public String getDdiValue() {
		return ddiValue;
	}

	public void setDdiValue(String ddiValue) {
		this.ddiValue = ddiValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
		return "DataConfigInfo [uuid=" + uuid + ", dsiUuid=" + dsiUuid + ", ddiKey=" + ddiKey + ", ddiValue=" + ddiValue
				+ ", value=" + value + ", remark=" + remark + ", state=" + state + ", creator=" + creator
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

}
