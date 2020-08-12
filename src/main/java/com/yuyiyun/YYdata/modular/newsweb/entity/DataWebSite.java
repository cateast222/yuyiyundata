package com.yuyiyun.YYdata.modular.newsweb.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 数据配置信息类
 * 
 * @describe 关联data_config_info
 * @author duhao
 * @date 2019年9月12日下午4:34:06
 * 
 */
@Data
@TableName("data_web_website")
public class DataWebSite extends DataWebBasic implements Serializable {


	private static final long serialVersionUID = 1L;

	@TableField("data_web_media")
	private Long dataWebMedia; //所属媒体

	@TableField("website_name")
	private String websiteName;  //媒体名称

	@TableField("website_sub_name")
	private String websiteSubName;  //站点名称

	@TableField("website_sub_url")
	private String websiteSubUrl;//站点地址

	@TableField("sub_host")
	private String subHost;//多级域名

	@TableField("language")
	private String language;//标签

	@TableField("proxy")
	private String proxy;//模板

	@TableField("state")
	private String state;

}
