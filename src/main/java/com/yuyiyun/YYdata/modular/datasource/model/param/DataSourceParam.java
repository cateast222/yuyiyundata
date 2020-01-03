package com.yuyiyun.YYdata.modular.datasource.model.param;

import java.io.Serializable;
import java.util.Date;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DataSourceParam", description = "数据源输入实体")
public class DataSourceParam implements Serializable,BaseValidatingParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键")
	private Long uuid;

	@ApiModelProperty(value = "中文名称")
	private String chsName;

	@ApiModelProperty(value = "原名称")
	private String orgName;

	@ApiModelProperty(value = "网站地址")
	private String websiteUrl;

	@ApiModelProperty(value = "网站名称")
	private String websiteName;

	@ApiModelProperty(value = "所属平台")
	private Integer platform;

	@ApiModelProperty(value = "国家")
	private String country;

	@ApiModelProperty(value = "地址（省市县）")
	private String region;

	@ApiModelProperty(value = "语种")
	private String language;

	@ApiModelProperty(value = "编码")
	private String encoded;

	@ApiModelProperty(value = "所属境区")
	private Integer proxy;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "状态")
	private Integer state;

	@ApiModelProperty(value = " 创建者")
	private String creator;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = " 更新时间")
	private Date updateTime;
	
	@ApiModelProperty(value = "查询条件")
	private String condition;

	@Override
	public String checkParam() {
		return null;
	}

}
