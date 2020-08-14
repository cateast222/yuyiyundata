package com.yuyiyun.YYdata.modular.newsweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
@Data
@TableName("data_web_channel")
public class DataWebChannel extends DataWebBasic {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 所属媒体(关联媒体) */
    @TableField("data_web_media")
    private Long dataWebMedia;

    /** 媒体名称 */
    @TableField("website_name")
    private String websiteName;

    /** 所属网站(关联网站表) */
    @TableField("data_web_website")
    private Long dataWebWebsite;

    /** 网站名称 */
    @TableField("website_sub_name")
    private String websiteSubName;

    /** 频道名称 */
    @TableField("module_name")
    private String moduleName;

    /** 频道网址 */
    @TableField("sub_module_url")
    private String subModuleUrl;
    
    /** 采集标识 */
    @TableField("collect_sign")
    private String collectSign;

    /** 采集状态 */
    @TableField("collect_state")
    private String collectState;
	
	
}
