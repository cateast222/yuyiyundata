package com.yuyiyun.YYdata.modular.newsweb.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import com.yuyiyun.YYdata.modular.newsweb.entity.DataWebBasic;
import java.io.Serializable;

/**
 * 数据配置信息类
 * 
 * @describe 关联data_config_info
 * @author duhao
 * @date 2019年9月12日下午4:34:06
 */
@Data
@TableName(value = "data_web_media")
public class DataWebMedia extends DataWebBasic implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@TableField("website_name")
	private String websiteName;  //媒体名称

	@TableField("website_url")
	private String websiteUrl;  //媒体网址

	@TableField("host")
	private String host;//一级域名

	@TableField("region")
	private String region;//省市县

	@TableField("country")
	private String country;//1中国

	@TableField("proxy")
	private String proxy;//境区


}
