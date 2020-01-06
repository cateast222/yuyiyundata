package com.yuyiyun.YYdata.modular.newspaper.model.param;

import java.io.Serializable;
import java.util.Date;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "DataNewspaperParam", description = "电子报纸输入实体")
public class DataNewspaperParam implements Serializable, BaseValidatingParam {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
	private Long uuid;

	@ApiModelProperty(value = "原名称")
	private String orgName;

	@ApiModelProperty(value = "中文名称")
	private String chsName;

	@ApiModelProperty(value = "全名称（+日期）")
	private String fullName;

	@ApiModelProperty(value = "发布日期")
	private Date publish;

	@ApiModelProperty(value = "类型")
	private String type;

	@ApiModelProperty(value = "URL")
	private String url;

	@ApiModelProperty(value = "封面")
	private String cover;

	@ApiModelProperty(value = "数据源")
	private String dataSource;

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

	@ApiModelProperty(value = "查询条件")
	private String condition;

	@Override
	public String checkParam() {
		return null;
	}

}
