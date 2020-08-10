package com.yuyiyun.YYdata.modular.newsweb.model.param;

import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel(value = "DataWebMediaParam", description = "频道输入实体")
public class DataWebMediaParam implements Serializable,BaseValidatingParam  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	


    /** 媒体名称 */
	@ApiModelProperty(value = "媒体名称")
    private String webSiteName;

    /** 媒体网址 */
	@ApiModelProperty(value = "媒体网址")
    private Long webSiteUrl;

    /** 一级域名 */
	@ApiModelProperty(value = "一级域名")
    private String host;

    /** 国家 */
	@ApiModelProperty(value = "国家")
    private String country;

    /** 省市区 */
	@ApiModelProperty(value = "省市区")
    private String region;

    /** 境区 */
	@ApiModelProperty(value = "境区")
    private String proxy;
	
	/** 状态 */
	@ApiModelProperty(value = "状态")
    private String state;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "uuid")
	private String uuid;



	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark;

	/**
	 * 创建者
	 */
	@ApiModelProperty("创建者")
	private String createBy;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 更新者
	 */
	@ApiModelProperty("更新者")
	private String updateBy;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
	
	@Override
	public String checkParam() {
		// TODO Auto-generated method stub
		return null;
	}

}
