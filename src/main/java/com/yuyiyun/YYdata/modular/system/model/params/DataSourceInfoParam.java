package com.yuyiyun.YYdata.modular.system.model.params;

import java.io.Serializable;
import java.util.Date;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DataSourceInfoParam", description = "数据源输入实体")
public class DataSourceInfoParam implements Serializable, BaseValidatingParam {
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "UUID")
	private String uuid;
	@ApiModelProperty(value = "数据源名称")
	private String name;
	@ApiModelProperty(value = "数据源网站地址")
	private String websiteUrl;
	@ApiModelProperty(value = "数据源网站名称")
	private String websiteName;
	@ApiModelProperty(value = "数据源所属平台")
	private Integer platform;
	@ApiModelProperty(value = "数据源所属国家")
	private String country;
	@ApiModelProperty(value = "数据源所属地址")
	private String region;
	@ApiModelProperty(value = "数据源所属境区")
	private Integer proxy;
	@ApiModelProperty(value = "数据源语种")
	private String language;
	@ApiModelProperty(value = "备注")
	private String remark;
	@ApiModelProperty(value = "状态")
	private Integer state;
	@ApiModelProperty(value = "创建者")
	private String creator;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	/**
	 * 查询条件
	 */
	@ApiModelProperty(value = "查询条件")
	private String condition;

	@Override
	public String checkParam() {
		return null;
	}

	@Override
	public String toString() {
		return "DataSourceInfoParam [id=" + uuid + ", name=" + name + ", websiteUrl=" + websiteUrl + ", websiteName="
				+ websiteName + ", platform=" + platform + ", country=" + country + ", region=" + region + ", proxy="
				+ proxy + ", language=" + language + ", remark=" + remark + ", state=" + state + ", creator=" + creator
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + ", condition=" + condition + "]";
	}

}
