package com.yuyiyun.YYdata.modular.newsweb.model.param;

import java.io.Serializable;

import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebBasic;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value = "DataWebChannelParam", description = "网站输入实体")
public class DataWebSiteParam extends DataWebBasic implements Serializable,BaseValidatingParam{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "所属媒体")
	private String dataWebMedia; //所属媒体

	@ApiModelProperty(value = "媒体名称")
	private String websiteName;  //媒体名称

	@ApiModelProperty(value = "网站名称")
	private String websiteSubName;  //站点名称

	@ApiModelProperty(value = "网址")
	private String websiteSubUrl;//站点地址

	@ApiModelProperty(value = "域名")
	private String subHost;//多级域名

	@ApiModelProperty(value = "语言")
	private String language;//标签

	@ApiModelProperty(value = "境区")
	private String proxy;//模板

	@ApiModelProperty(value = "状态")
	private String state;

	
	
	@Override
	public String checkParam() {
		// TODO Auto-generated method stub
		return null;
	}

}
