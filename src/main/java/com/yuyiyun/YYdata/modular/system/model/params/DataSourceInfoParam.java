package com.yuyiyun.YYdata.modular.system.model.params;

import java.io.Serializable;
import java.util.Date;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import lombok.Data;

@Data
public class DataSourceInfoParam implements Serializable, BaseValidatingParam {
	private static final long serialVersionUID = 1L;
	private String uuid;
	private String name;
	private String websiteUrl;
	private String websiteName;
	private Integer platform;
	private String country;
	private String region;
	private Integer proxy;
	private String language;
	private String remark;
	private Integer state;
	private String creator;
	private Date createTime;
	private Date updateTime;
	/**
	 * 查询条件
	 */
	private String condition;

	@Override
	public String checkParam() {
		return null;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}



	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWebsiteUrl() {
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}

	public String getWebsiteName() {
		return websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Integer getProxy() {
		return proxy;
	}

	public void setProxy(Integer proxy) {
		this.proxy = proxy;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DataSourceInfoParam [id=" + uuid + ", name=" + name + ", websiteUrl=" + websiteUrl + ", websiteName="
				+ websiteName + ", platform=" + platform + ", country=" + country + ", region=" + region + ", proxy="
				+ proxy + ", language=" + language + ", remark=" + remark + ", state=" + state + ", creator=" + creator
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", condition=" + condition + "]";
	}
	
	

}
