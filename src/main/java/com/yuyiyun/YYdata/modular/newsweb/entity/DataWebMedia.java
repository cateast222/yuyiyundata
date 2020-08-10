package com.yuyiyun.YYdata.modular.newsweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * data_web_media
 * 
 * @author: jt
 * @date 2020-07-07 14:47
 */
@Data
@ApiModel(value = "DataWebMedia",description = "调取数据权限实体")
@TableName("data_web_media")
public class DataWebMedia extends DataWebBasic implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 媒体名称 */
	@ApiModelProperty(value = "媒体名称")
	@TableField("website_name")
	private String webSiteName;

	/** 媒体网址 */
	@ApiModelProperty(value = "媒体网址")
	@TableField("website_url")
	private String webSiteUrl;

	/** 一级域名 */
	@ApiModelProperty(value = "一级域名")
	@TableField("host")
	private String host;

	/** 国家 */
	@ApiModelProperty(value = "国家")
	@TableField("country")
	private String country;

	/** 省市区 */
	@ApiModelProperty(value = "省市区")
	@TableField("region")
	private String region;


	/** 境区 */
	@ApiModelProperty(value = "境区")
	@TableField("proxy")
	private String proxy;

	public String getWebSiteName() {
		return webSiteName;
	}

	public void setWebSiteName(String webSiteName) {
		this.webSiteName = webSiteName;
	}

	public String getWebSiteUrl() {
		return webSiteUrl;
	}

	public void setWebSiteUrl(String webSiteUrl) {
		this.webSiteUrl = webSiteUrl;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
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

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

	@Override
	public String toString() {
		return "DataWebMedia{" +
				"webSiteName='" + webSiteName + '\'' +
				", webSiteUrl='" + webSiteUrl + '\'' +
				", host='" + host + '\'' +
				", country='" + country + '\'' +
				", region='" + region + '\'' +
				", proxy='" + proxy + '\'' +
				'}';
	}
}
