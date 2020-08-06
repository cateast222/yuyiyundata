package com.yuyiyun.YYdata.modular.SiteTest.model.param;


import java.io.Serializable;


import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "CjMediaInfoParam", description = "媒体输入实体")
public class CjMediaInfoParam implements Serializable,BaseValidatingParam {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键")
	private Long mediaid;
	@ApiModelProperty(value = "媒体名称")
	private String mname;
	@ApiModelProperty(value = "境内外")
	private String domfor;
	@ApiModelProperty(value = "国家")
	private String countrycode;
	@ApiModelProperty(value = "省份")
	private String provincecode;
	@ApiModelProperty(value = "城市")
	private String citycode;
	@ApiModelProperty(value = "地区")
	private String areacode;
	@ApiModelProperty(value = "标签")
	private String label;
	@ApiModelProperty(value = "状态")
	private String state;
	@Override
	public String checkParam() {
		// TODO Auto-generated method stub
		return null;
	}
}
