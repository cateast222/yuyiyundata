package com.yuyiyun.YYdata.modular.datasource.entity;

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
 * 
 * </p>
 *
 * @author duhao
 * @since 2020-01-03
 */
@Data
@TableName("data_source")
public class DataSource implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(value = "uuid", type = IdType.ID_WORKER)
	private Long uuid;

	/**
	 * 中文名称
	 */
	@TableField("chs_name")
	private String chsName;

	/**
	 * 原名称
	 */
	@TableField("org_name")
	private String orgName;

	/**
	 * 网站地址
	 */
	@TableField("website_url")
	private String websiteUrl;

	/**
	 * 网站名称
	 */
	@TableField("website_name")
	private String websiteName;

	/**
	 * 所属平台（数据源平台字典）
	 */
	@TableField("platform")
	private String platform;

	/**
	 * 数据提供方（数据源提供方字典）
	 */
	@TableField("provider")
	private String provider;

	/**
	 * 国家（国家字典）
	 */
	@TableField("country")
	private String country;

	/**
	 * 地址（省市县）
	 */
	@TableField("region")
	private String region;

	/**
	 * 语种（语种字典）
	 */
	@TableField("language")
	private String language;

	/**
	 * 编码（编码字典）
	 */
	@TableField("encoded")
	private String encoded;

	/**
	 * 所属境区（境区字典）
	 */
	@TableField("proxy")
	private String proxy;

	/**
	 * 备注
	 */
	@TableField("remark")
	private String remark;

	/**
	 * 状态(数据源状态字典)：-2.弃用、-1.测试、0.正常、1.配置、2.提测、3.完成、4.通过、5.启用
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

	public void setChsName(String chsName) {
		this.chsName = HtmlTagUtil.convertTag(chsName);
	}

	public void setOrgName(String orgName) {
		this.orgName = HtmlTagUtil.convertTag(orgName);
	}

	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = HtmlTagUtil.convertTag(websiteUrl);
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = HtmlTagUtil.convertTag(websiteName);
	}

	public void setPlatform(String platform) {
		this.platform = HtmlTagUtil.convertTag(platform);
	}

	public void setProvider(String provider) {
		this.provider = HtmlTagUtil.convertTag(provider);
	}

	public void setCountry(String country) {
		this.country = HtmlTagUtil.convertTag(country);
	}

	public void setRegion(String region) {
		this.region = HtmlTagUtil.convertTag(region);
	}

	public void setLanguage(String language) {
		this.language = HtmlTagUtil.convertTag(language);
	}

	public void setEncoded(String encoded) {
		this.encoded = HtmlTagUtil.convertTag(encoded);
	}

	public void setProxy(String proxy) {
		this.proxy = HtmlTagUtil.convertTag(proxy);
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
