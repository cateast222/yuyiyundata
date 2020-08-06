package com.yuyiyun.YYdata.modular.newsweb.model.param;

import java.io.Serializable;


import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
@Data
@ApiModel(value = "DataWebChannelParam", description = "频道输入实体")
public class DataWebChannelParam implements Serializable,BaseValidatingParam  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	  /** 所属媒体(关联媒体) */
	@ApiModelProperty(value = "所属媒体")
    private Long dataWebMedia;

    /** 媒体名称 */
	@ApiModelProperty(value = "媒体名称")
    private String websiteName;

    /** 所属网站(关联网站表) */
	@ApiModelProperty(value = "所属网站")
    private String dataWebWebsite;

    /** 网站名称 */
	@ApiModelProperty(value = "网站名称")
    private String websiteSubName;

    /** 频道名称 */
	@ApiModelProperty(value = "频道名称")
    private String moduleName;

    /** 频道网址 */
	@ApiModelProperty(value = "频道网址")
    private String subModuleUrl;

    /** 采集状态 */
	@ApiModelProperty(value = "采集状态")
    private String collectState;
	
	/** 状态 */
	@ApiModelProperty(value = "状态")
    private String state;
	
	@Override
	public String checkParam() {
		// TODO Auto-generated method stub
		return null;
	}

}
