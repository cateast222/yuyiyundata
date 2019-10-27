package com.yuyiyun.YYdata.modular.system.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * 数据配置信息类
 * 
 * @describe 关联data_config_info
 * @author duhao
 * @date 2019年9月12日下午4:34:06
 */
@TableName(value = "data_source_info")
public class DataSourceInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private String uuid;
	@TableField("name")
	private String name;
	@TableField("website_url")
	private String websiteUrl;
	@TableField("website_name")
	private String websiteName;
	@TableField("platform")
	private Integer platform;
	@TableField("country")
	private String country;
	@TableField("region")
	private String region;
	@TableField("proxy")
	private Integer proxy;
	@TableField("language")
	private String language;
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

	/**
	 * @Description 数据源ID
	 * @Author duhao
	 * @Date 2019年9月13日下午8:12:13
	 * @return 数据源ID
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @Description 数据源ID
	 * @Author duhao
	 * @Date 2019年9月13日下午8:12:34
	 * @param uuid 数据源ID
	 */
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

	@Override
	public String toString() {
		return "DataSourceInfo [uuid=" + uuid + ", name=" + name + ", websiteUrl=" + websiteUrl + ", websiteName="
				+ websiteName + ", platform=" + platform + ", country=" + country + ", region=" + region + ", proxy="
				+ proxy + ", language=" + language + ", remark=" + remark + ", state=" + state + ", creator=" + creator
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
}
